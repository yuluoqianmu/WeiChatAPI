<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'serviceAdd.jsp' starting page</title>
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript">
		var serviceName="true";
	    var price="true";
	    var shootTime="true";
	    var picture="true";
	    var truning="true";
	    var ins="true";
	    var camName="true";
	    var styleChecked=0;
	    
	  	function check(){
	  		
	  		var serName=document.getElementById("serviceName").value;
	  		if(serName==""){
	  			serviceName="false";
	  		 	document.getElementById("err_name").innerHTML="请填写服务主题";
	  			return false;
	  		}else if(serName.length>15){
	  			serviceName="false";
	  		 	document.getElementById("err_name").innerHTML="服务主题在15字以内";
	  			return false;
	  		}
	  		
	  		var priceVal=document.getElementById("price").value;
	  		if(priceVal==""){
	  			price="false";
	  			document.getElementById("err_price").innerHTML="请添加价格";
	  			return false;
	  		}else if(isNaN(priceVal)){
	  			price="false";
	  			document.getElementById("err_price").innerHTML="价格必须为数字";
	  			return false;
	  		}else if(priceVal>999999){
	  			price="false";
	  			document.getElementById("err_price").innerHTML="价格应小于999999元";
	  			return false;
	  		}else if(priceVal.indexOf('.')!=-1){
	  			price="false";
	  			document.getElementById("err_price").innerHTML="价格应是整数";
	  			return false;
	  		}else if(priceVal<0)
	  		{
	  		    price="false";
	  			document.getElementById("err_price").innerHTML="价格不应该是负数";
	  			return false;
	  		}
	  		
	  		var style=document.getElementsByName("styleId");
	  		for(var i=0;i<style.length;i++){
	  			if(style[i].checked){
	  				styleChecked++;
	  			}
	  		}
	  		if(styleChecked==0){
	  			document.getElementById("err_style").innerHTML="请选择风格";
	  			return false;
	  		}
	  		
	  		var shootVal=document.getElementById("shoot_time").value;
	  		if(shootVal==""){
	  			shootTime="false";
	  			document.getElementById("err_time").innerHTML="请填写拍摄时间";
	  			return false;
	  		}else if(isNaN(shootVal)){
	  			shootTime="false";
	  			document.getElementById("err_time").innerHTML="拍摄时间必须为数字";
	  			return false;
	  		}else if(shootVal>100){
	  			shootTime="false";
	  			document.getElementById("err_time").innerHTML="拍摄时间应小于100天";
	  			return false;
	  		}else if(shootVal<0)
	  		{
	  		    shootTime="false";
	  			document.getElementById("err_time").innerHTML="拍摄时间不应小于0天";
	  			return false;
	  		}
	  		
	  		var picVal=document.getElementById("picture_num").value;
	  		var truVal=document.getElementById("truing_num").value;
	  		if(picVal==""){
	  			picture="false";
	  			document.getElementById("err_picture").innerHTML="请填写照片张数";
	  			return false;
	  		}else if(isInteger(picVal)){
	  			picture="false";
	  			document.getElementById("err_picture").innerHTML="照片张数必须为整数";
	  			return false;
	  		}else if(picVal>99999){
	  			picture="false";
	  			document.getElementById("err_picture").innerHTML="照片张数应小于99999张";
	  			return false;
	  		}else if(picVal<0)
	  		{
	  		    picture="false";
	  			document.getElementById("err_picture").innerHTML="照片张数不应小于0张";
	  			return false;
	  		}
	  		if(truVal==""){
	  			truning="false";
	  			document.getElementById("err_truning").innerHTML="请填写精修张数";
	  			return false;
	  		}else if(isInteger(truVal)){
	  			truning="false";
	  			document.getElementById("err_truning").innerHTML="精修张数必须为整数";
	  			return false;
	  		}else if(truVal>99999){
	  			truning="false";
	  			document.getElementById("err_truning").innerHTML="精修张数应小于99999张";
	  			return false;
	  		}else if(truVal<0)
	  		{
	  		    truning="false";
	  			document.getElementById("err_truning").innerHTML="精修张数不应小于0张";
	  			return false;
	  		}
			if(truVal-picVal>0){
				truning="false";
				document.getElementById("err_truning").innerHTML="精修张数应小于照片张数";
				return false;
			}  	
	  		
	  		if(document.getElementById("instructions").value!=""){
	  		    if(document.getElementById("instructions").value.length>200)
	  		    {
	  		        ins="false";
	  			    document.getElementById("err_ins").innerHTML="附加说明内容应在200字内";
	  			    return false;
	  		    }
	  			
	  		}
	  		if(cameraName=="false"){alert("err");
	  			document.getElementById("err_camera").innerHTML="该摄影师不存在";
	  			return false;
	  		}
	  		
	  	}
	  	
	    function clearName(){ 
	    	 if(serviceName=="false"){
	    	 	document.getElementById("err_name").innerHTML="";
	    	 }
	    }
	    function clearPrice(){
	    	if(price=="false"){
	    		document.getElementById("err_price").innerHTML="";
	    	}
	    }
	    function clearTime(){
	    	if(shootTime=="false"){
	    		document.getElementById("err_time").innerHTML="";
	    	}
	    }
	    function clearPicture(){
	    	if(picture=="false"){
	    		document.getElementById("err_picture").innerHTML="";
	    	}
	    }
	    function clearTruning(){
	    	if(truning=="false"){
	    		document.getElementById("err_truning").innerHTML="";
	    	}
	    }
	    function clearIns(){
	    	if(ins=="false"){
	    		document.getElementById("err_ins").innerHTML="";
	    	}
	    }
	    function clearStyle(){
	    	document.getElementById("err_style").innerHTML="";
	    }
	
	function isInteger(number){
	 var reg1 =  /^\d+$/;
    if(number.match(reg1) == null){
     return true
    } else{
     return false
    } 
	
	} 
	
	
	</script>
  </head>
  <body>
    <ul class="forminfo">
      <form action="<%=request.getContextPath() %>/serviceAction_addService.action" method="post" onsubmit="return check()" >
        <input type="hidden" name="serviceStatus" value="true"/>
        <li><label>摄影师</label><input type="hidden" value="${user.userName }" name="cameraName" id="cameraName"/><input type="text" class="dfinput"  value="${user.nickName }" disabled="disabled"/><font id="err_camera" color="red"></font></li>
        <li><label>主题</label><input type="text" class="dfinput" name="serviceName" id="serviceName" onclick="clearName()"/><font id="err_name" color="red"></font></li>
        <li><label>价格</label><input type="text" class="dfinput" name="price" id="price" onclick="clearPrice()"/><font id="err_price" color="red"></font>
        	<input type="radio" name="priceUnit" value="天" checked="checked" />天
        	<input type="radio" name="priceUnit" value="套"/>套
        </li>
        <li><label>风格</label>
        	<c:forEach items="${styleInfo }" var="style">
        		<input type="checkbox" value="${style.styleId }" id="styleId" name="styleId" onclick="clearStyle()">${style.styleName }</input>
        	</c:forEach>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<font color="red" id="err_style"></font>
        </li>
        <li><label>拍摄天数</label><input type="text" class="dfinput" name="shoot_time" id="shoot_time" onclick="clearTime()"/><font color="red" id="err_time"></font></li>
        <li><label>照片张数</label><input type="text" class="dfinput" name="picture_num" id="picture_num" onclick="clearPicture()"/><font color="red" id="err_picture"></font></li>
        <li><label>精修张数</label><input type="text" class="dfinput" name="truing_num" id="truing_num" onclick="clearTruning()"/><font color="red" id="err_truning"></font></li>
        <li><label>服装</label><input type="radio" name="clothes" value="自带" checked="checked"/>自带&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="clothes" value="一套" />一套&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="clothes" value="二套" />二套&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="clothes" value="三套" />三套&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="clothes" value="四套" />四套&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="clothes" value="五套" />五套&nbsp;&nbsp;&nbsp;
                              <font color="red" id="err_clothes"></font></li>
        <li><label>化妆</label><input type="radio" name="facepaint" value="提供" checked="checked"/>提供&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="facepaint" value="不提供" />不提供</li>
        <li><label>附加说明</label><textarea class="dfinput" name="instructions" id="instructions" onclick="clearIns()"></textarea><font color="red" id="err_ins"></font></li>
        <input type="submit" value="确认" class="btn"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="reset" vallue="重置" class="btn"/>
      </form>
    </ul>
  </body>
</html>
