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
    
    <title>系统消息</title>
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
  <form id="sysMessageForm" name="sysMessageForm" action="<%=request.getContextPath() %>/messageAction_queryAllSysMessage.action" method="post">
     <input type="hidden" name="userId" value="${userId }">
     <table style="width: 80%" class="tablelist">
    	<thead>
    	    <th>标题</th>
    	    <th>发布对象</th>
    	    <th>用户昵称</th>
    	    <th>消息状态</th>
    	    <th>发送时间</th>
    	</thead>
    	<tbody>
    		<c:forEach items="${messageInfo }" var="message">
    		  <tr>
    			
    			<td><c:out value="${message.messageTitle }"></c:out> </td>
    			<c:if test="${message.messageSendPerson==0 }">
    				<td>全体用户+全体摄影师</td>
    			</c:if>
    			<c:if test="${message.messageSendPerson==1 }">
    				<td>全体用户</td>
    			</c:if>
    			<c:if test="${message.messageSendPerson==2 }">
    				<td>全体摄影师</td>
    			</c:if>
    			<c:if test="${message.messageSendPerson==3 }">
    				<td>单个用户</td>
    			</c:if>
    			<c:if test="${message.messageSendPerson==4 }">
    				<td>单个摄影师</td>
    			</c:if>
    			<td><c:out value="${message.nickName }"></c:out></td>
    			<c:if test="${message.messageStatus==0 }">
    				<td>未读</td>
    			</c:if>
    			<c:if test="${message.messageStatus==1 }">
    				<td>已读</td>
    			</c:if>
    		 	<td><fmt:formatDate value="${message.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    		  </tr>
    		</c:forEach>
        </tbody>
    </table>
   
    <table style="width: 80%" class="splitPage">
		<tr id=null>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="sysMessageForm" title="记录" unit="条记录" actionName="messageAction_queryAllSysMessage.action"/>
			</td>
		</tr>
	</table>
	 </form>
	 </div>
  </body>
</html>
