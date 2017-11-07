(function() {
  cola(function(model) {
  	
  	//当前时间
   function getCurrentTime(){
	    var time = cola.defaultAction.formatDate(new Date(), 'yyyy-MM-dd hh:mm');
	    model.set("currentTime", time);
		setTimeout(getCurrentTime, 10000);
	}
   getCurrentTime();
  	
  	//页未数据
  	model.set("usersFooter",{total:888,online:100,onlinedoctor:20});
  	
  	//登录用户包含头像
		var user = App.getLoginUser();
	    user.avatar = "resources/images/avatars/alex.png";
    model.set("user", user);
    
    //收藏列表  
    getHinsCollectList();
    
    function getHinsCollectList(){
    	$.ajax({
    		url: App.getContextPath()+"/service/hinsCollectList",
    		type: "GET",
    		datatype: "json",
    		contentType:'application/json;charset=UTF-8',
    		success: function(data) {
    			model.set("hinsCollectList", data);
    		},
    		error: function() {
    			cola.alert("数据异常!", {
    				level: "warning"
    			});
    		}
    	});
    }
    //common调用收藏
    window.getHinsCollectListToo = function(){
    	  getHinsCollectList();
    }
		
    model.action({
    	openCollectItem :function(item){
    		//打开收藏项
    		var url="", config;
    		var contextPath = App.getContextPath();
    		url = item.get("url").replace(contextPath,""); 
    		
    		config = {
    				closeable: true,
    				icon: item.get("icon"),
    				label: item.get("name"),
    				path : url,
    				type: "subWindow"
    		}
    		parent.App.open(url, config);
    	},
    	 toDecimal:function(x) {   
    		 if(x>1000)
    			{
	    		  var f = parseFloat(x);    
		    		if (isNaN(f)) {   
		    		  return;    
		    		}          
		    		f = Math.round(x/1000*10)/10;  
		    		return f +"K"; 
    			}
    		 return x;
       
    		},
    	showDeletIcon: function(dom){
    		//显示取消按钮
    		var iRemove = $(dom).find("i.remove");
    		iRemove.css("display", "inline-block");
    	},
    	
    	hideDeletIcon: function(dom){
    		//隐藏取消按钮
    		var iRemove = $(dom).find("i.remove");
    		iRemove.css("display", "none");
    	},
    	
    	deletCollect: function(item){
    		//删除收藏项
    		event && event.stopPropagation();
    		
    		var deletCollect = item.toJSON();
    		$.ajax({
    			url: App.getContextPath()+"/service/hinsCollectListDelet",
    			type: "POST",
    			data: JSON.stringify(deletCollect),
    			datatype: "json",
    			contentType:'application/json;charset=UTF-8',
    			success: function(data) {
    				item.remove();
    			},
    			error: function(){
    				 cola.alert("数据异常!", {
                          level: "warning"
                      });
    			}
    		});
    	},
    	
    	showHideCorner: function(value){
    		if(!value){
    			return "hideCorner";
    		}else{
    			return "showCorner";
    		}
    	}
    })
  });
}).call(this);