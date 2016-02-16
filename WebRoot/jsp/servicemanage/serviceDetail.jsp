<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'service_detail.jsp' starting page</title>
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>

  </head>
  <style>
    #imgbox img{
    }
   #img {
   		border:#ccc 3px solid;
   		width:200px; height:150px;
   		
   		}
   #img:hover{
   		border:#ccc 5px solid;
   		width:200px; height:150px;
   		}
  #addImg h3{
  	padding: 0px 0px 0px 5px;
  	height:27px;
	line-height:27px; 
	color: #393939;
	font-size: 12px;
	font-weight: normal;
  }
   #addImg h3 img{
    vertical-align:middle
  }
   </style>
  <script type="text/javascript">
  
  	function modify(serviceId,cameraServiceJSP){
  		location.href="<%=request.getContextPath()%>/serviceAction_modifyService.action?serviceId="+serviceId+"&JSP="+cameraServiceJSP;
  	}
  	 function onService(serviceId){
  		if(window.confirm("是否上架?")){
  		    var jsp=document.getElementById("cameraServiceJSP").value;
  		 	location.href="<%=request.getContextPath()%>/serviceAction_onService.action?serviceId="+serviceId+"&JSP="+jsp;
  		 }
     }
     function offService(serviceId){
    	if(window.confirm("是否下架?")){
    		var jsp=document.getElementById("cameraServiceJSP").value;
    		location.href="<%=request.getContextPath()%>/serviceAction_offService.action?serviceId="+serviceId+"&JSP="+jsp;
    	}
     }
     
     function removeService(serviceId){
    	 if(confirm("确定删除这项服务吗？")){
    	    var jsp=document.getElementById("cameraServiceJSP").value;
    	    var userId=document.getElementById("userId").value;
 			location.href="<%=request.getContextPath()%>/serviceAction_deleteService.action?serviceId="+serviceId+"&JSP="+jsp+"&userId="+userId;
 		}
     }
     function queryGalary(galaryId){
    	 location.href="<%=request.getContextPath()%>/galleryManAction_toEdit.action?galaryId="+galaryId;
     }
     function deleteGalary(galaryId,serviceId){
    	 if(window.confirm("是否删除该作品集")){
    		 var cameraServiceJSP=document.getElementById("cameraServiceJSP").value;
    		 location.href="<%=request.getContextPath()%>/serviceAction_deleteGalleryById.action?galaryId="+galaryId+"&serviceId="+serviceId+"&cameraServiceJSP="+cameraServiceJSP;
        	 }
     }
     
     function modifyGalary(galaryId){
    	 location.href="<%=request.getContextPath()%>/galleryManAction_toEdit.action?galaryId="+galaryId;
     }
  	
  </script>
  
  <body > 
  <input type="hidden" value="${cameraServiceJSP }" id="cameraServiceJSP"/>
  <input type="hidden" value="${userId }" id="userId">
  	<c:if test="${cameraServiceJSP ne 'is' }">
    <div class="place">
    	<span>位置：</span>
    	<ul class="placeul">
    	<li>首页</li>
        <li>服务管理</li>
        <li>服务列表</li>
        <li>${service.serviceName }</li>
    	</ul>
    </div>
    </c:if>
    <br/>
    
    <span id="titleSpan">服务主题:${service.serviceName } &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	<a onclick="modify('${service.serviceId}','${cameraServiceJSP }')" style="cursor: pointer;" ><img src="images/t02.png" width="20px" height="15px">编辑</a>
    	<a onclick="removeService('${service.serviceId}')" style="cursor: pointer;" ><img src="images/t03.png" width="20px" height="15px">删除服务</a>
    	<c:if test="${service.serviceStatus==true }">
    		<a onclick="offService('${service.serviceId}')" style="cursor: pointer;" ><img  src="<%=basePath%>images/offline.png" title='下架' />下架</a>
    	</c:if>
    	<c:if test="${service.serviceStatus==false }">
    		<a onclick="onService('${service.serviceId}')" style="cursor: pointer;" ><img src='<%=basePath%>images/online.png' title='上架' />上架</a>
    	</c:if>
	    <input type="hidden" value="${service.createTime }" name="createTime" id="createTime"/>
    </span>
    <br/>
    <div id="detailDiv">
    <table class="tablelist">
    	<thead>
    	<tr>
    	  <th>价格</th>
    	  <th>风格</th>
    	  <th>拍摄天数</th>
    	  <th>照片张数</th>
    	  <th>精修张数</th>
    	  <th>化妆</th>
    	  <th>服装</th>
    	  <th>当前状态</th>
    	</tr>
    	</thead>
    	
    	<tbody>
    	<tr>
    	<td>${service.lpServiceDetail.price }元/${service.lpServiceDetail.priceUnit }</td>
    	<td><c:forEach items="${service.lpServiceDetail.lpStyle }" var="style">
    		<c:out value="${style.styleName }"></c:out>
    	</c:forEach></td>
    	<td>${service.lpServiceDetail.shootTime }天</td>
    	<td>${service.lpServiceDetail.pictureNum }张</td>
    	<td>${service.lpServiceDetail.truingNum }张</td>
    	<td>${service.lpServiceDetail.facepaint }</td>
    	<td>${service.lpServiceDetail.clothes }</td>
    	<td>${service.serviceStatus==true?'上架':'已下架' }</td>
    	</tr>
    	
     </tbody>
    </table>
    </div>
    <br/>
    <tr>
                附加说明：
     <td>${service.lpServiceDetail.instructions }</td>
    </tr>
  	<br/>
  	<div id="addImg">
      <h3>相关作品--${fn:length(service.lpGalaries)} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <a href="<%=request.getContextPath() %>/galleryManAction_toAddGallery.action?serviceId=${service.serviceId}&cameraServiceJSP=${cameraServiceJSP }"><img src="<%=request.getContextPath()%>/images/add.png">添加作品</a></h3> 
    </div>
    <div>
      <c:if test="${service.lpGalaries!=null && fn:length(service.lpGalaries)>0}">
   	    <c:forEach items="${service.lpGalaries }" var="galaryInfo">
   	    <br/>
   	    <div id="imgbox"> 
   	  	  <img id="img" src='${galaryInfo.galaryCover}' onclick="queryGalary('${galaryInfo.galaryId}')" style="cursor: pointer;"/>
   	  	  <td class="toolbar">
		    <a onclick="modifyGalary('${galaryInfo.galaryId}')" style="cursor: pointer;" ><img src="images/t02.png" width="20px" height="15px">编辑</a> 
		    <a onclick="deleteGalary('${galaryInfo.galaryId}','${service.serviceId }')" style="cursor: pointer;"><img src="images/t03.png" width="20px" height="15px">删除</a>
	      </td>
   	  	  <div>
   	  	  	<td><img src="<%=request.getContextPath() %>/images/camera.png" width="20px" height="18px"/><a href="<%=request.getContextPath() %>/queryUserById.action?userId=${service.lpUser.userId }">${service.lpUser.nickName }</a></td>
   	  	  	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	  	  	<img  src="<%=request.getContextPath() %>/images/view.png" width="25px" height="15px"/>
   	  	  	<c:if test="${galaryInfo!=null && galaryInfo.controlSource == 0}">
         		${galaryInfo.viewNumber}&nbsp;&nbsp;
         	</c:if>
         	<c:if test="${galaryInfo!=null && galaryInfo.controlSource == 1}">
         		${galaryInfo.lpGalaryExtends.viewNumber }&nbsp;&nbsp;
         	</c:if>
   	  	  	<img  src="<%=request.getContextPath() %>/images/comment.png" width="25px" height="15px"/>${galaryInfo.commentNumber }
   	  	  </div>
   	    </div>
   	  </c:forEach>
     </c:if>
    </div>
  </body>
</html>
