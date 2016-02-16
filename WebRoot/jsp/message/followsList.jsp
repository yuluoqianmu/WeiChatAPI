<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    
    <title>My JSP 'follow.jsp' starting page</title>
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/css/select.css" rel="stylesheet" type="text/css" />
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
  
  <body>
  <div class="rightinfo">
  <form id="followForm" name="followForm" action="<%=request.getContextPath() %>/messageAction_queryAllFollows.action" method="post">
     <input type="hidden" name="userId" value="${userId }">
     <table style="width: 80%" class="tablelist">
    	<thead>
    	    <th>关注时间</th>
    	    <th>关注的账号</th>
    	    <th>关注的昵称</th>
    	</thead>
    	<tbody> 
    		<c:forEach items="${followInfo }" var="follow">
    		  <tr>
    			<td><fmt:formatDate value="${follow.followTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    			<td><a href="<%=request.getContextPath() %>/cameraManAction_getCameraman.action" > <c:out value="${follow.lpUserByCameraId.userName }"/></a> </td>
    			<td><c:out value="${follow.lpUserByCameraId.nickName }"/></td>
    		  </tr>
    		</c:forEach>
        </tbody>
    </table>
   
    <table style="width: 80%" class="splitPage">
		<tr id=null>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="followForm" title="记录" unit="条记录" actionName="messageAction_queryAllFollows.action"/>
			</td>
		</tr>
	</table>
	 </form>
	 </div>
  </body>
</html>
