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
    
    <title>My JSP 'serviceModify.jsp' starting page</title>
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.0.min.js"></script>
 
  <script type="text/javascript">
    var serviceName="true";
    var price="true";
    var shootTime="true";
    var picture="true";
    var truning="true";
    var ins="true";
    var styleChecked=0;
    var camName="true";
    
  	window.onload=function(){ 
  	    var clothes='<%=request.getAttribute("clothes")%>';
 	    var clothes_All=document.getElementsByName("clothes");
 	      for(var i=0;i<clothes_All.length;i++){
 	  		if(clothes==clothes_All[i].value){
 	  		  clothes_All[i].checked='checked';
 	  		}    
 	  	}      
 	  	
  		var facepaint='<%=request.getAttribute("facepaint")%>';
  		var facepaint_All=document.getElementsByName("facepaint");
  		for(var i=0;i<facepaint_All.length;i++){
  			if(facepaint==facepaint_All[i].value){
  				facepaint_All[i].checked='checked';
  			}
  		}
  		
  		var priceUnit='<%=request.getAttribute("priceUnit")%>'; 
  		var priceUnit_All=document.getElementsByName("priceUnit");
  		for(var i=0;i<priceUnit_All.length;i++){ 
  			if(priceUnit==priceUnit_All[i].value){
  				priceUnit_All[i].checked='checked';
  			}
  		}
  		
  		var style='<%=request.getAttribute("styleId")%>';
  		var styleId=style.split(";");
  		var style_All=document.getElementsByName("styleId");
  		for(var i=0;i<styleId.length;i++){ 
  			for(var j=0;j<style_All.length;j++){ 
  				if(styleId[i]==style_All[j].value){
  					style_All[j].checked='checked';
  					continue;
  				}	
  			}
  		}
  	}
  	 
  	function check(){
  		if(document.getElementById("serviceName").value==""){
  			serviceName="false";
  		 	document.getElementById("err_name").innerHTML="请填写服务主题";
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
  		if(document.getElementById("shoot_time").value==""){
  			shootTime="false";
  			document.getElementById("err_time").innerHTML="请填写拍摄时间";
  			return false;
  		}else if(isNaN(document.getElementById("shoot_time").value)){
  			shootTime="false";
  			document.getElementById("err_time").innerHTML="拍摄时间必须为数字";
  			return false;
  		}else if(document.getElementById("shoot_time").value<0)
  		{
  		    shootTime="false";
  			document.getElementById("err_time").innerHTML="拍摄时间不应小于0天";
  			return false;
  		}
  		else if(document.getElementById("shoot_time").value>100)
  		{
  		    shootTime="false";
  			document.getElementById("err_time").innerHTML="拍摄时间应小于100天";
  			return false;
  		}
  		var truVal=document.getElementById("truing_num").value;
  		var picVal=document.getElementById("picture_num").value;
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
//   		if(document.getElementById("instructions").value==""){
//   			ins="false";
//   			document.getElementById("err_ins").innerHTML="请填写附加说明";
//   			return false;
//   		}
       if(document.getElementById("instructions").value!=""){
	  		    if(document.getElementById("instructions").value.length>200)
	  		    {
	  		        ins="false";
	  			    document.getElementById("err_ins").innerHTML="附加说明内容应在200字内";
	  			    return false;
	  		    }
	  			
	  		}
  		if(camName=="false"){ 
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
  <c:if test="${cameraServiceJSP ne 'is'}">
    <div class="place">
    	<span>位置：</span>
    	<ul class="placeul">
    	<li>首页</li>
        <li>服务管理</li>
        <li>修改服务</li>
    	</ul>
    </div>
  </c:if>
    <br/>
    <ul class="forminfo">
      <form action="<%=request.getContextPath() %>/serviceAction_modifyCommitService.action" method="post" onsubmit="return check()" name="modifyServiceForm" id="modifyServiceForm">
       	<input type="hidden" id="cameraServiceJSP" name="cameraServiceJSP" value="${cameraServiceJSP }"/>
        <input type="hidden" id="serviceId" name="serviceId" value="${modifyServiceInfo.serviceId }"/>
        <input type="hidden" id="serviceStatus" name="serviceStatus" value="${modifyServiceInfo.serviceStatus }"/>
        <input type="hidden" id="createTime" name="createTime" value="${modifyServiceInfo.createTime }"/>
        <input type="hidden" id="detailId" name="detailId" value="${modifyServiceInfo.lpServiceDetail.detailId }"/>
        <input type="hidden" id="orderNum" name="orderNum" value="${modifyServiceInfo.orderNum }"/>
      <%--  <li><label>摄影师</label><input type="text" class="dfinput" name="cameraName" id="cameraName" onchange="checkCamera(this.value)" value="${modifyServiceInfo.lpUser.userName }" disabled="disabled"/><font id="err_camera" color="red"></font></li>
       <%--  <input type="text" class="dfinput" name="cameraName" id="cameraName" onchange="checkCamera(this.value)" value="${modifyServiceInfo.lpUser.userName }" disabled="disabled"/><font id="err_camera" color="red"></font>
        --%> 
        <li><label>摄影师</label>
          <select class="dfinput" name="cameraName" id="cameraName">
            <c:forEach items="${cameraList }" var="camera">
               <c:if test="${camera.userName==modifyServiceInfo.lpUser.userName }">
                  <option value="${camera.userName }" selected="selected">${camera.nickName }</option>
               </c:if>
               <c:if test="${camera.userName!=modifyServiceInfo.lpUser.userName }">
                  <option value="${camera.userName }" >${camera.nickName }</option>
               </c:if>
            </c:forEach>
          </select>
        </li>
        <li><label>主题</label><input type="text" class="dfinput" name="serviceName" id="serviceName" value="${modifyServiceInfo.serviceName }" onclick="clearName()"/><font id="err_name" color="red"></font></li>
        <li><label>价格</label><input type="text" class="dfinput" name="price" id="price" value="${modifyServiceInfo.lpServiceDetail.price }" onclick="clearPrice()"/>
        	<input type="radio" name="priceUnit" value="天"/>天
        	<input type="radio" name="priceUnit" value="套"/>套<font id="err_price" color="red"></font>
        </li>
        <li><label>风格</label>
        	<c:forEach items="${style }" var="s">
        		<input type="checkbox" name="styleId" value="${s.styleId }" onclick="clearStyle()"/>
        		<c:out value="${s.styleName }"></c:out>
        	</c:forEach>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<font color="red" id="err_style"></font>
        </li>
        <li><label>拍摄天数</label><input type="text" class="dfinput" name="shoot_time" id="shoot_time"value="${modifyServiceInfo.lpServiceDetail.shootTime }" onclick="clearTime()"/><font color="red" id="err_time"></font></li>
        <li><label>照片张数</label><input type="text" class="dfinput" name="picture_num" id="picture_num"value="${modifyServiceInfo.lpServiceDetail.pictureNum }" onclick="clearPicture()"/><font color="red" id="err_picture"></font></li>
        <li><label>精修张数</label><input type="text" class="dfinput" name="truing_num" id="truing_num"value="${modifyServiceInfo.lpServiceDetail.truingNum }" onclick="clearTruning()"/><font color="red" id="err_truning"></font></li>
        <li><label>服装</label><input type="radio" name="clothes" value="自带" checked="checked"/>自带&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="clothes" value="一套"/>一套&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="clothes" value="二套"/>二套&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="clothes" value="三套"/>三套&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="clothes" value="四套"/>四套&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="clothes" value="五套"/>五套&nbsp;&nbsp;&nbsp;
        <li><label>化妆</label><input type="radio" name="facepaint" value="提供" checked="checked"/>提供&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="facepaint" value="不提供"/>不提供</li>
        <li><label>附加说明</label><textarea class="dfinput" name="instructions" id="instructions" onclick="clearIns()">${modifyServiceInfo.lpServiceDetail.instructions }</textarea><font color="red" id="err_ins"></font></li>
        <input type="submit" value="确认" class="btn" id="save"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="reset" vallue="重置" class="btn"/>
      </form>
    </ul>
  </body>
</html>
