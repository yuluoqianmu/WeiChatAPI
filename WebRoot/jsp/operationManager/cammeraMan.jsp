<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>该城市所属摄影师</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/style1.css" rel="stylesheet" type="text/css">
  </head>
  <body>
  <table class="tablelist" style="text-align: center;">
  <c:if test="${requestScope.userList == null}" var="isEmipty"><div style="text-align: center; margin-top: 100px; font-family: Arial;font-weight: bold;color: green;">该城市尚未有摄影师注册~<div></c:if>
  <c:if test="${!isEmipty}">
  	<tr>
  		<th>摄影师账号</th>
  		<th>名称</th>
  		<th>注册时间</th>
  		<th>认证时间</th>
  	</tr>
  </c:if>	
  	<c:forEach items="${requestScope.userList}" var="user">
  		<tr>
  			<td>${user.userName}</td>
  			<td>${user.nickName}</td>
  			<td>${user.registerTime}</td>
  			<td>${user.validTime}</td>
  		</tr>
  	</c:forEach>
  </table>
  </body>
</html>
