/** 
 * 异常拦截器，处理后台返回的有异常信息的请求 
 */  
Ext.define('App.web.ExceptionInterceptor',{  
    extend:'App.web.AbstractInterceptor',  
    interceptor:function(options,response){  
          debugger;
        var json = Ext.decode(response.responseText);  
          debugger;
          var cfg = {
            			title:"温馨提示",
            			msg : "请求错误,请稍候再试.",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
            	}
            	if(json){
            		if(json.success){
            			return true;
            		}
	        		if(json.key == "UNAUTHENTICATED"){
	        			/*
	        			Ext.MessageBox.confirm("","登录超时,立即登录?",function(btn){
			        		if(btn == 'yes'){
			        			location.href = __ctxPath;
			        		}  
			        	});
			        	*/
			        	Ext.MessageBox.alert('Session超时','Session超时，请重新登录！',function(){  
			                window.location = __ctxPath;  
			            });
	        		}else{
	        			cfg.msg = json.info;
	        			Ext.Msg.show(cfg);
	        		}
	        		return false;
            	}
            	return false;
    }  
      
}); 