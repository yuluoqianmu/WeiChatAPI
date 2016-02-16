function checkOneMobile(String){
	
	if(String.length==0){
		;
	}else{
	
		if(String.length==11){
			if(isNaN(String)==true){ 
				alert("请输入数字!");
				return false;
			}
		}
		
		var pre = String.substr(0,2);
		var pre2 = String.substr(0,1);
		var pre3 = String.substr(0,3);
			
		if(pre!='13' && pre!='14' && pre!='15' && pre!='17' && pre!='18'){ 
			alert("请输入有效电话号码!");
			return false;
		}	
		
		if((pre=='13' || pre=='14' || pre=='15' || pre=='17' || pre=='18') && String.length!=11){
			alert("请输入11位手机号");
			return false;
		}
		
		if(String.length==11){
			if(isNaN(String)==true){
				alert("请输入数字!");
				return false;
			}
		}
		var mobile = String;
		
		if(isNaN(mobile)==true ){ 
			alert("请输入有效数字!");
			return false;
		} 
	}
	return true;
}

function CheckOneMobile_CUS(String){
	
	if(String.length==0){
		;
	}else{
	
		if(String.length==11){
			if(isNaN(String)==true){ 
				alert("请输入数字!");
				return false;
			}
		}
		var mobile = String;
		
		if(isNaN(mobile)==true ){ 
			alert("请输入有效数字!");
			return false;
		} 
	}
	return true;
}

function CheckValidateChar( String ){ 
	var Letters = "!@#$%^&*()-=+_\\|<>?/;':,.\n";
	var i;
	var c;
	if(String.charAt( 0 )=='-')	return false;
	if( String.charAt( String.length - 1 ) == '-' ) return false;
	for( i = 0; i < String.length; i ++ ){
		c = String.charAt( i );
		if (Letters.indexOf( c ) > 0) return false;        
	}
	return true;
}

function CheckValidateCharProduct( String ){ 
	var Letters = "!@#$%^&*=+\\|<>?/'\n";
	var i;
	var c;
	if(String.charAt( 0 )=='-')	return false;
	if( String.charAt( String.length - 1 ) == '-' ) return false;
	for( i = 0; i < String.length; i ++ ){
		c = String.charAt( i );
		if (Letters.indexOf( c ) > 0) return false;        
	}
	return true;
}

function CheckAllValidateChar( String ){ 
	var Letters = "\"{}!@#$%^&*()-=+_\\|<>?/;':,.\n";
	var i;
	var c;
	if(String.charAt( 0 )=='-')	return false;
	if( String.charAt( String.length - 1 ) == '-' ) return false;
	for( i = 0; i < String.length; i ++ ){
		c = String.charAt( i );
		if (Letters.indexOf( c ) > 0) return false;        
	}
	return true;
}

function CheckValidateReplaceChar( String ){ 
	var Letters = "!#$%^&*()-=+_\\|<>?/;':,.\n";
	var i;
	var c;
	if(String.charAt( 0 )=='-')	return false;
	if( String.charAt( String.length - 1 ) == '-' ) return false;
	for( i = 0; i < String.length; i ++ ){
		c = String.charAt( i );
		if (Letters.indexOf( c ) > 0) return false;        
	}
	return true;
}


function CheckPhValidateChar( String ){ 
	var Letters = "～￥％……&×（）——＋|、＝－`~@#$%^&*()-=+_\\|<>?/｛｝〔〕{}[]\n";
	var i;
	var c;
	if(String.charAt( 0 )=='-')	return false;
	if( String.charAt( String.length - 1 ) == '-' ) return false;
	for( i = 0; i < String.length; i ++ ){
		c = String.charAt( i );
		if (Letters.indexOf( c ) > 0) return false;        
	}
	return true;
}

function CheckPhValidateReplaceChar( String ){ 
	var Letters = "";
	var i;
	var c;
	if(String.charAt( 0 )=='-')	return false;
	if( String.charAt( String.length - 1 ) == '-' ) return false;
	for( i = 0; i < String.length; i ++ ){
		c = String.charAt( i );
		if (Letters.indexOf( c ) > 0) return false;        
	}
	return true;
}

function CheckTimeValidateReplaceChar( String ){ 
	var Letters = "!#$%^&*()-=+_\\|<>?/;',.\n";
	var i;
	var c;
	if(String.charAt( 0 )=='-')	return false;
	if( String.charAt( String.length - 1 ) == '-' ) return false;
	for( i = 0; i < String.length; i ++ ){
		c = String.charAt( i );
		if (Letters.indexOf( c ) > 0) return false;        
	}
	return true;
}