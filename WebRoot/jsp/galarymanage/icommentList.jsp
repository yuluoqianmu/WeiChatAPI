<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <base href="<%=basePath%>"> 
    <title>My JSP 'icommentList.jsp' starting page</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
  </head>
  
  <body>
     <table style=" width:900px;" class="tablelist">
       	<thead>
    	<tr>
         <th><input name="" type="checkbox" value="" id="checkAll"/></th> 
        <th>序号<i class="sort"><img src="images/px.gif" /></i></th>
        <th>发布内容</th>
        <th>发布者</th>
        <th>发布时间</th>
        <th>操作</th>
        </tr>
        </thead>
        <tbody>
     <c:if test="${listcomment != null && fn:length(listcomment)>0}">
	 <c:forEach items="${listcomment}" var ="item" varStatus="i">
         <tr>
         <td><input name="" type="checkbox" value="${item.commentId}" /></td> 
        <td>${i.index + 1}</td>
        <td>${item.commentDetail}</td>
        <td>${item.lpUser.nickName }</td>       
        <td><fmt:formatDate value="${item.createTime }" pattern="yyyy/MM/dd HH:mm:ss"/></td>
        <td><a href="#" class="tablelink">回复</a>     <a href="javascript:deleteCommentById()" class="tablelink"> 删除</a></td>
        </tr> 
    </c:forEach>
       </c:if>
        </tbody>
        </table>
  </body>
</html>
