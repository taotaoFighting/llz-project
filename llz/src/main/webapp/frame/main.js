(function() {
  cola(function(model) {
    var appName, errorCount, login, loginCallback, logo, longPollingTimeOut, showLoginMessage;
    logo = App.prop("app.logo.path");
    appName = App.prop("app.name");
    if (logo) {
      $("#appHeader").append($.xCreate({
        tagName: "img",
        "class": "img ui image",
        src: logo
      }));
    }
    if (appName) {
      $("#appHeader").append($.xCreate({
        tagName: "span",
        style:"letter-spacing:2px;",
        content: appName
      }));
    }
    model.describe("menus", {
      provider: {
        url: App.prop("service.menus")
      }
    });
    model.describe("user", {
      provider: {
        url: App.prop("service.user.detail")
      }
    });
    
    var loginUser=App.getLoginUser();
    var userName=loginUser.username;
    var cname=loginUser.cname;
    var companyId=loginUser.companyId;
    var welcomeUserMsg="欢迎您 "+cname;
    if(userName){
    	welcomeUserMsg+="（"+userName+"）";
    }
    model.set({"welcomeUserMsg":welcomeUserMsg});
    
    model.dataType({
      name: "Login",
      properties: {
        userName: {
          validators: {
            $type: "required",
            message: ""
          }
        },
        password: {
          validators: {
            $type: "required",
            message: ""
          }
        }
      }
    });
    model.describe("login", "Login");
    model.set("login", {});
    model.set("messages", {});
    errorCount = 0;
    longPollingTimeOut = null;
    window.refreshMessage = function() {
      var options;
      options = {};
      if (longPollingTimeOut) {
        clearTimeout(longPollingTimeOut);
      }
      if (App.prop("longPollingTimeout")) {
        options.timeout = App.prop("longPollingTimeout");
      }
      return $.ajax(App.prop("service.messagePull"), options).done(function(messages) {
        var i, len, message;
        if (messages) {
          errorCount = 0;
          for (i = 0, len = messages.length; i < len; i++) {
            message = messages[i];
            model.set("messages." + message.type, message.content);
          }
        }
        if (App.prop("liveMessage")) {
          return longPollingTimeOut = setTimeout(refreshMessage, App.prop("longPollingInterval"));
        }
      }).error(function(xhr, status, ex) {
        if (App.prop("liveMessage")) {
          if (status === "timeout") {
            return longPollingTimeOut = setTimeout(refreshMessage, App.prop("longPollingInterval"));
          } else {
            errorCount++;
            return longPollingTimeOut = setTimeout(refreshMessage, 5000 * Math.pow(2, Math.min(6, errorCount - 1)));
          }
        }
      });
    };
    longPollingTimeOut = setTimeout(refreshMessage, 1000);
    refreshMessage();
    loginCallback = null;
    window.login = function(callback) {
      cola.widget("loginDialog").show();
      if (callback && typeof callback === "function") {
        return loginCallback = callback;
      }
    };
    login = function() {
      var data;
      data = model.get("login");
      cola.widget("containerSignIn").addClass("loading");
      return $.ajax({
        type: "POST",
        url: App.prop("service.login"),
        data: JSON.stringify(data.toJSON()),
        contentType: "application/json"
      }).done(function(result) {
        var callback;
        cola.widget("containerSignIn").removeClass("loading");
        if (!result.type) {
          showMessage(result.message);
          return;
        }
        cola.widget("loginDialog").hide();
        if (loginCallback) {
          callback = loginCallback;
          loginCallback = null;
          return callback();
        }
      }).fail(function() {
        cola.widget("containerSignIn").removeClass("loading");
      });
    };
    model.widgetConfig({
      loginDialog: {
        $type: "dialog",
        width: 400
      },
      subMenuTree: {
        $type: "tree",
        autoExpand: true,
        bind: {
          expression: "menu in subMenu",
          child: {
            recursive: true,
            expression: "menu in menu.menus"
          }
        },
        itemClick: function(self, arg) {
          var data, menus;
          data = arg.item.get("data").toJSON();
          menus = data.menus;
          if (menus && menus.length > 0) {

          } else {
            App.open(data.path, data);
            return cola.widget("subMenuLayer").hide();
          }
        }
      },
      subMenuLayer: {
        beforeShow: function() {
          return $("#viewTab").parent().addClass("lock");
        },
        beforeHide: function() {
          return $("#viewTab").parent().removeClass("lock");
        }
      }
    });
    showLoginMessage = function(content) {
      return cola.widget("formSignIn").setMessages([
        {
          type: "error",
          text: content
        }
      ]);
    };
    model.action({
      signIn: function() {
        var data;
        cola.widget("formSignIn").setMessages(null);
        data = model.get("login");
        if (data.validate()) {
          return login();
        } else {
          return showLoginMessage("用户名或密码不能为空！");
        }
      },
      dropdownIconVisible: function(item) {
        var menus, result;
        menus = item.get("menus");
        result = false;
        if (menus && menus.entityCount > 0) {
          result = true;
        }
        return result;
      },
      showUserSidebar: function() {
        return cola.widget("userSidebar").show();
      },
      logout: function() {
    	 window.location=App.prop("service.logout");
        /*return $.ajax({
          type: "POST",
          url: App.prop("service.logout")
        }).done(function(result) {
          if (result.type) {
            return window.location.reload();
          }
        }).fail(function() {
          alert("退出失败，请检查网络连接！");
        });*/
      },
      menuItemClick: function(item) {
        var data, i, len, menu, menus, recursive;
        data = item.toJSON();
        menus = data.menus;
        recursive = function(d) {
          var i, len, ref, results;
          if (d.menus && d.menus.length > 0) {
            ref = d.menus;
            results = [];
            for (i = 0, len = ref.length; i < len; i++) {
              item = ref[i];
              results.push(recursive(item));
            }
            return results;
          } else {
            d.menus = null;
            return d.hasChild = false;
          }
        };
        if (menus && menus.length > 0) {
          for (i = 0, len = menus.length; i < len; i++) {
            menu = menus[i];
            recursive(data);
          }
          model.set("subMenu", menus);
          model.set("currentMenu", data);
          return cola.widget("subMenuLayer").show();
        } else {
          model.set("subMenu", []);
          cola.widget("subMenuLayer").hide();
          return App.open(data.path, data);
        }
      },
      hideSubMenuLayer: function() {
        return cola.widget("subMenuLayer").hide();
      },
      toggleSidebar: function() {
        var $dom, className;
        className = "collapsed";
        $dom = $("#frameworkSidebarBox");
        return $dom.toggleClass(className, !$dom.hasClass(className));
      },
      messageBtnClick: function() {
        var action;
        action = App.prop("message.action");
        if (action && typeof action === "object") {
          App.open(action.path, action);
        }
      },
      taskBtnClick: function() {
        var action;
        action = App.prop("task.action");
        if (action && typeof action === "object") {
          App.open(action.path, action);
        }
      }
    });
    $("#frameworkSidebar").accordion({
      exclusive: App.prop("menu.exclusive")
    }).delegate(".menu-item", "click", function() {
      $("#frameworkSidebar").find(".menu-item.current-item").removeClass("current-item");
      return $fly(this).addClass("current-item");
    });
    return $("#rightContainer>.layer-dimmer").on("click", function() {
      return cola.widget("subMenuLayer").hide();
    });
  });

  cola.ready(function() {
    var workbench;
    workbench = App.prop("workbench");
    if (workbench) {
      return App.open(workbench.path, workbench);
    }
  });

}).call(this);
