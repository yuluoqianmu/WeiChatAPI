<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'service.jsp' starting page</title>
     <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" /> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <style>
     #addImg h3 img{
    vertical-align:middle
  }
  </style>
  <script type="text/javascript">
  function on_off_Service(serviceId,serviceStatus,userId){
  	if(serviceStatus=='1'){
  		if(window.confirm("确定下架该项服务？")){
  	  		location.href="<%=request.getContextPath()%>/offService.action?serviceId="+serviceId+"&userId="+userId;
  		}
  	}else{
  		if(window.confirm("确定上架该项服务？")){
  	  		location.href="<%=request.getContextPath()%>/onService.action?serviceId="+serviceId+"&userId="+userId;
  		}
  	}
  }
  
  function removeService(serviceId,userId){ 
 	 if(confirm("确定删除这项服务吗？")){
			location.href="<%=request.getContextPath()%>/deleteServiceById.action?serviceId="+serviceId+"&userId="+userId;
		}
  }
  function serviceDetail(serviceId){
	  var JSP="cameraServiceJSP";
	  window.location.href="serviceAction_queryById.action?serviceId="+serviceId+"&JSP="+JSP;
  }
  </script>
  <body>
  <div class="rightinfo">
  <form id="serviceForm" name="serviceForm" action="<%=request.getContextPath() %>/queryService.action">
   <input type="hidden" name="userId" value="${userId }">
     <table class="tablelist">
     	<a href="<%=request.getContextPath() %>/serviceAction_getStyle.action?userId=${userId}"><img src="<%=request.getContextPath()%>/images/add.png">新增服务</a></h3> 
    	<thead>
    	   <th>序号<i class="sort"><img src="images/px.gif" /></i></th>
    	    <th>服务名称</th>
    	    <th>上架时间</th>
    		<th>当前状态</th>
    		<th>操作</th>
    		<th>操作</th>
    	</thead>
    	<tbody> 
    		<c:forEach items="${serviceInfo }" var="service" varStatus="status">
    		  <tr><%--
    			<td><a href="serviceAction_queryById.action?serviceId=${service.serviceId }" target="_parent"> <c:out value="${service.serviceName }"/></a></td>
    			--%>
    			 <td><c:out value="${status.index+1}"> </c:out></td>
    			<td><a onclick="serviceDetail('${service.serviceId }')" style="cursor: pointer;"> <c:out value="${service.serviceName }"/></a></td>
    			
    			<td><fmt:formatDate value="${service.createTime }"/> </td>
    			<td><c:out value="${service.serviceStatus==true?'上架':'已下架' }" /></td>
    			<%--<td><input type="button" id="on" value="${service.serviceStatus==true?'下架':'上架' }" onclick="on_off_Service('${service.serviceId}','${service.serviceStatus==true?'1':'0'}','${service.lpUser.userId }')"/></td>--%>
    			
    			<c:if test="${service.serviceStatus==true }">
    		    <td>  <a onclick="on_off_Service('${service.serviceId}','1','${service.userId }')" style="cursor: pointer;" ><img  src="<%=basePath%>images/offline.png" title='下线' /> </a></td>
    	        </c:if>
    	        <c:if test="${service.serviceStatus==false }">
    		     <td><a onclick="on_off_Service('${service.serviceId}','0','${service.userId }')" style="cursor: pointer;" ><img src='<%=basePath%>images/online.png' title='上线' /> </a></td>  
    	        </c:if>
    			
    			<td><a onclick="removeService('${service.serviceId}','${service.userId }')">删除服务</a></td>
    		  </tr>
    		</c:forEach>
    		
        </tbody>
    </table>
    </form>
     <table width="98%" class="splitPage">
		<tr id=null>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="serviceForm" title="记录" unit="条记录" actionName="queryService.action"/>
			</td>
		</tr>
	</table>
	 </form>
	 </div>
  </body>
</html>
