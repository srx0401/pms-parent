Ext.define('App.util.Security', { 
	statics: {
		getDefaultSalt:function(){
			return "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		},
		encryptByMD5:function(content){
			if(content == undefined || content == ""){
				return content;
			}
	    	return MD5(content);
		},
		encryptByAES:function(content,salt){  
			debugger;
			if(content == undefined || content == "" || salt == undefined || salt == "" ){
				return content;
			}
	    	salt =  salt ? salt : App.util.Security.getDefaultSalt();
			var block = 16;
			var dataBase64 = CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(content));
			salt  = CryptoJS.enc.Hex.parse( CryptoJS.SHA256(salt).toString() );
			var iv   = CryptoJS.enc.Latin1.parse('');
			return  CryptoJS.AES.encrypt(dataBase64, salt,{iv:iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding}).toString();
	    },  
	    decryptByAES:function(content,salt){
	    	debugger;
	    	if(content == undefined || content == "" || salt == undefined || salt == "" ){
				return content;
			}
	    	try{
	    		salt =  salt ? salt : App.util.Security.getDefaultSalt();
		    	salt  = CryptoJS.enc.Hex.parse( CryptoJS.SHA256( salt ).toString() );
				var iv   = CryptoJS.enc.Latin1.parse('');
				var decrypted = CryptoJS.AES.decrypt(content, salt, {iv:iv,padding:CryptoJS.pad.ZeroPadding});
				var dataBase64 =  decrypted.toString(CryptoJS.enc.Utf8);
				return CryptoJS.enc.Base64.parse(dataBase64).toString(CryptoJS.enc.Utf8);
	    	}catch(e){
	    		throw "解密异常";
	    	}
		}
    }
}); 