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
    
    <title>所有评论列表</title>
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/css/select.css" rel="stylesheet" type="text/css" />
    
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
    <form id="commentForm" name="commentForm" action="<%=request.getContextPath() %>/messageAction_queryReply.action" method="post">
      <input type="hidden" name="userId" value="${userId }">
     <table class="tablelist">
    	<thead>
    	    <th>回复时间</th>
    	    <th>回复人</th>
    	    <th>回复内容</th>
    	    <th>发帖人</th>
    		<th>发帖内容</th>
    	</thead>
    	<tbody> 
    		<c:forEach items="${replyInfo }" var="reply">
    		<tr>
    			<td><fmt:formatDate value="${reply.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><c:out value="${reply.nickName }"></c:out> </td>
				<td><c:out value="${reply.commentDetail }"></c:out></td>
				<td><c:out value="${reply.replyNickName }"></c:out> </td>
				<td><c:out value="${reply.replyCommentDetail }"></c:out></td>
			</tr>
    		</c:forEach>
    		<%--<%
    			List<Map> replyMap=(List<Map>)request.getAttribute("replyInfo");
    			int i=1;
    			for(Map map:replyMap){
    				%>  <tr>
    				    <td><%=i++%></td>
    					<td><%=map.get("replyTime") %></td>
    					<td><%=map.get("user") %></td>
    					<td><%=map.get("commentDetail") %></td>
    					<td><%=map.get("replyUser") %></td>
    					<td><%=map.get("replyComment") %></td>
    					</tr>
    				<%
    			}
    		%>
    	 --%></tbody>
    </table>
   
     <table width="98%" class="splitPage">
		<tr id=null>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="commentForm" title="记录" unit="条记录" actionName="messageAction_queryReply.action"/>
			</td>
		</tr>
	</table>
	 </form>
	 </div>
  </body>
</html>
