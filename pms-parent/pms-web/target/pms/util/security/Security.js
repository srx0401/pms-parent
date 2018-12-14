function Security(){
	debugger;
	var DEFAULT_SALT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	/**
	 * aes加密方法
	 * @param data	明文
	 * @param key	加密key
	 * @returns		密文
	 */
	this.encryptByAES =function (data,key){
		//密钥
		key =  key ? key : DEFAULT_SALT;
		var block = 16;
		var dataBase64 = Base64.encode(data).replace(/\+/g, "-").replace(/\//g, "_").replace(/=/g, "");
		var length = dataBase64.length % block;
		var pad = block - length;
		dataBase64 += new Array( pad + 1 ).join(  String.fromCharCode(pad) );
		key  = CryptoJS.enc.Hex.parse( CryptoJS.SHA256(key).toString() );
		var iv   = CryptoJS.enc.Latin1.parse('');
		return  CryptoJS.AES.encrypt(dataBase64, key,{iv:iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding}).toString();
		
	}
	/**
	 * aes解密方法
	 * @param data	密文
	 * @param key	解密key
	 * @returns		明文
	 */
	this.decryptByAES = function(data,key){
		key =  key ? key : DEFAULT_SALT;
		key  = CryptoJS.enc.Hex.parse( CryptoJS.SHA256( key ).toString() );
		var iv   = CryptoJS.enc.Latin1.parse('');
		var decrypted = CryptoJS.AES.decrypt(data, key, {iv:iv,padding:CryptoJS.pad.ZeroPadding});
		var dataBase64 =  decrypted.toString(CryptoJS.enc.Utf8);
		dataBase64 = dataBase64.replace(/-/g, "+").replace(/_/g, "/");
		var length = dataBase64.length;
		var slast = dataBase64.charAt(length-1).charCodeAt();
		return Base64.decode( dataBase64.substr(0, length-slast) );

	}
}