(function() {
  var App, e, getAjaxID, language, properties, rootApp, rootWindow, startedAjaxList, title, win;

  commonUtils=window.commonUtils={
	 clearVirtualProperty:function(json){
		 for(var item in json){  
		    if(item.indexOf("__")==0){  
		    	eval("delete json."+item);  
		    }   
		 }   
	 }
  };
  win = window.parent;

  while (win) {
    try {
      if (win.App) {
        rootApp = win.App;
        rootWindow = win;
        break;
      }
      if (win === win.parent) {
        break;
      }
      win = win.parent;
    } catch (_error) {
      e = _error;
    }
  }

  if (!rootApp) {
  	var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var rootPath = pathName.substr(0,index+1);
    properties = {
      mainView: "./frame/main-one.html",
      loginPath: "./frame/account/login.html",
      longPollingTimeout: 0,
      longPollingInterval: 2000,
      "service.messagePull": "./service/message/pull",
      "service.login": "./security_check_",
      "service.logout": "./security_logout_",
      "service.menus": "./service/frame/url/menus/",
      "service.user.detail": rootPath+"/service/frame/user/detail/",
      "menu.exclusive": false,
      "message.action": {
	      path: "http://cola-ui.com",
	      type: "subWindow",
	      label: "我的消息",
	      closeable: true,
	      icon: "",
	      icon: "icon menu"
      },
      "task.action": {
	      path: "http://cola-ui.com",
	      type: "subWindow",
	      label: "我的任务",
	      closeable: true,
	      icon: "icon alarm outline"
      },
      "workbench": {
          path: "/frame/workbench/workbench.html",
          type: "subWindow",
          label: "我的工作台",
          closeable: false,
          icon: "icon dashboard"
        },
      "app.logo.path": "resources/images/logo.png",
//      "app.name":"银联催收联盟平台",
      title: "美味空间",
      contextPath:"/llz"
    };
  }

  App = window.App = {
    _tabs: {},
    getRootWindow: function() {
      if (rootApp) {
        return rootWindow;
      } else {
        return window;
      }
    },
    open: function(path, config) {
      var tab, viewTab;
      path=App.prop("contextPath")+path;
      
      collectionStatus(path);
      
      if (rootApp) {
        rootApp.open(path, config);
      } else {
        viewTab = cola.widget("viewTab");
        if (App._tabs[path]) {
          tab = viewTab.getTab(path);
          viewTab.setCurrentTab(tab);
        } else {
          if (!config.type || config.type === "subWindow") {
            tab = new cola.TabButton({
              afterClose: (function(_this) {
                return function(self, arg) {
                  return App.close(self.get("name"));
                };
              })(this),
              content: {
                $type: "iFrame",
                path: path,
                class: "contentIFrame"
              },
              icon: config.icon,
              name: path,
              closeable: config.closeable,
              caption: config.label,
              click: function(self, arg){
            	  var url = self.get("name");
            	  collectionStatus(url);
              },
              destroy: function(self, arg){
            	  var tabbutton = $("c-tabbutton");
            	  if(tabbutton.length == 1){
            		  $("#collece-showHide").hide();
            	  }
              }
            });
            viewTab = cola.widget("viewTab");
            App._tabs[path] = tab;
            viewTab.addTab(tab);
            return viewTab.setCurrentTab(tab);
          } else {
            window.open(path);
          }
        }
      }
    },
    getContextPath: function(){
			var pathName = document.location.pathname;
	    var index = pathName.substr(1).indexOf("/");
	    var result = pathName.substr(0,index+1);
	    return result;
    },
    getBasePath: function(){
	    var host = window.location.host;
			var protocol = window.location.protocol;	
			var contextPath = App.getContextPath();
			var basePath = protocol + "\//" + host + contextPath;
    	return basePath;
    },
    close: function(path) {
      return delete App._tabs[path];
    },
    goLogin: function(callback) {
      if (rootApp) {
        return rootApp.goLogin(callback);
      } else {
        return login(callback);
      }
    },
    setTitle: function(title) {
      if (rootApp) {
        return rootApp.setTitle(path);
      } else {
        return document.title = title;
      }
    },
    setFavicon: function(path) {
      var i, icon, len, ref, rel, results;
      if (rootApp) {
        return rootApp.setFavicon(path, config);
      } else {
        ref = ["icon", "shortcut icon"];
        results = [];
        for (i = 0, len = ref.length; i < len; i++) {
          rel = ref[i];
          icon = $("link[rel='" + rel + "']");
          if (icon.length > 0) {
            results.push(icon.attr("href", path));
          } else {
            results.push(document.head.appendChild($.xCreate({
              tagName: "link",
              rel: "icon",
              href: path
            })));
          }
        }
        return results;
      }
    },
    refreshMessage: function() {
      if (rootApp) {
        return rootApp.refreshMessage();
      } else {
        return typeof refreshMessage === "function" ? refreshMessage() : void 0;
      }
    },
    prop: function(key, value) {
      var i, len, p, results;
      if (rootApp) {
        return rootApp.prop(key, value);
      } else {
        if (arguments.length === 1) {
          if (typeof key === "string") {
            return properties[key];
          } else if (key) {
            results = [];
            for (i = 0, len = key.length; i < len; i++) {
              p = key[i];
              if (key.hasOwnProperty(p)) {
                results.push(properties[p] = key[p]);
              } else {
                results.push(void 0);
              }
            }
            return results;
          }
        } else {
          return properties[key] = value;
        }
      }
    },
    getLoginUser:function(){
    	var loginUser={
			username:$.cookie("_cap_userName1_"),
			cname:$.cookie("_cap_userCname_"),
			companyName:$.cookie("_cap_cpName_"),
			companyFName:$.cookie("_cap_cpFName_"),
			orgCode:$.cookie("_cap_cpOrgcode1_"),
			companyId:$.cookie("_cap_userCpId_"),
			bankCode:$.cookie("_cap_bankCode_"),
			instCode:$.cookie("_cap_instCode_"),
			companyType:$.cookie("_cap_cpType_"),
			position:$.cookie("_cap_position_"),
			mecNo:$.cookie("_cap_mecNo_"),
			administrator:$.cookie("_cap_administrator_")
    	};
    	return loginUser;
    },
    resetComponentAuth:function(params){
	  $.ajax("./service/frame/component/auth", {data:params}).success(function(result) {
		  if(result){
   	    	   for(var i=0;i<result.length;i++){
   	    		   var auth=result[i];
   	    		   if(!auth.visible){
		   	    	   $("#"+auth.id).css('display','none');
		   	    	   continue;
   	    		   }
   	    		   var widget=cola.widget(auth.id);
   	    		   if(widget){
   	    		   var type=widget.constructor.CLASS_NAME;
   	    		   if(type=='button'){
   	    			   widget.set('disabled',auth.disabled);
   	    		   }else if(type.indexOf("input")>=0){
   	    			   widget.set('readOnly',auth.disabled);
   	    		   }}
   	    	   }
	   	   }
	  });
    }
  };
  //返回字典函数 
  window.getDictItems = function(code) {
	  var items = window.top._picchDict[code];
	  if (items){
		  return JSON.parse(JSON.stringify(items));
	  }
	  else {
		  return [];
	  }
  };

  title = App.prop("title");

  if (title) {
    App.setTitle(title);
  }

  if (window.cola) {
    cola.defaultAction("setting", function(key) {
      return App.prop(key);
    });
    cola.defaultAction("numberString", function(number) {
      return ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen"][number - 1];
    });
    language = $.cookie("_language") || window.navigator.language;
//    if (language) {
      document.write("<script src=\""+App.getContextPath()+"/resources/i18n/zh/cola.js\"></script>");
//      document.write("<script src=\"resources/i18n/" + language + "/common.js\"></script>");
//    }
    $(NProgress.done);
    $(document).ajaxError(function(event, jqXHR) {
      var message;
      if (jqXHR.status === 401) {
        App.goLogin();
        return false;
      } else {
        message = jqXHR.responseJSON;
        if (message) {
          throw new cola.Exception(message);
        }
      }
    });
    getAjaxID = function(event) {
      var id, key, value;
      id = "";
      for (key in event) {
        value = event[key];
        if (key.indexOf("jQuery") === 0) {
          id = key;
          break;
        }
      }
      if (id) {
        if (!(parseInt(id.replace("jQuery", "")) > 0)) {
          id = "";
        }
      }
      return id;
    };
    startedAjaxList = [];
    $(document).ajaxStart(function(event) {
      var id;
      id = getAjaxID(event);
      startedAjaxList.push(id);
      if (!NProgress.isStarted()) {
        return NProgress.start();
      }
    });
    $(document).ajaxComplete(function(event) {
      var id, index;
      id = getAjaxID(event);
      index = startedAjaxList.indexOf(id);
      if (index > -1) {
        startedAjaxList.splice(index, 1);
      }
      if (startedAjaxList.length === 0) {
        return NProgress.done();
      }
    });
  }

  function collectionStatus(url){
	   //判断是否已经收藏过,从而改变收藏按钮的样式
	    if(url.indexOf("/workbench/workbench") != -1){
	    	$("#collece-showHide").hide();
	    	//重新获取收藏列表
	    	if($(".contentIFrame")[0]){
	    		$(".contentIFrame")[0].children[1].contentWindow.getHinsCollectListToo();
	    	}
	    }else{
	    	$("#collece-showHide").show();
	    	$.ajax({
	    		url: "service/isExistHinsCollect",
	    		type: "POST",
	    		datatype: "json",
	    		data:JSON.stringify({url:url}),
	    		contentType:'application/json;charset=UTF-8',
	    		success: function(data) {
	    			if(data.status){
	    				$("#collece-showHide").find("i").removeClass("empty");
	    			}else{
	    				$("#collece-showHide").find("i").addClass("empty");
	    			}
	    		},
	    		error: function(){
	    			cola.alert("数据异常!", {
	    				level: "warning"
	    			});
	    		}
	    	}); 
	    }
  }
  
}).call(this);
