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
    <form id="commentForm" name="commentForm" action="<%=request.getContextPath() %>/messageAction_queryAllComments.action" method="post">
      <input type="hidden" name="userId" value="${userId }">
     <table class="tablelist">
    	<thead>
    	    <th>序号</th>
    	    <th>评论时间</th>
    	    <th>评论类型</th>
    	    <th>所属作品集</th>
    	    <th>来拍社标题</th>
    		<th>评论内容</th>
    	</thead>
    	<tbody> 
    		<%	int i=1;
    			List<Map> commentMap=(List<Map>)request.getAttribute("commentInfo");
    			for(Map map:commentMap){
    				%>	
    				<tr>
    					<td><%=i++ %></td>
    					<td><%=map.get("commentTime") %></td>
    					<%
	    					int commentType=Integer.parseInt(map.get("commentType").toString());
	    				    if(commentType==0){
    					%>
    						<td>作品集评论</td>
    					<%
    				}else if(commentType==1){
    					%>
    						<td>来拍社评论</td>
    					<%
    				}
    				%>
    					<td><%=map.get("galary") %></td>
    					<td><%=map.get("club") %></td>
    					<td><%=map.get("commentDetail") %></td>
    				</tr>
    				<% 
    			}
    		%>
    	<!--  	<c:forEach items="${commentInfo }" var="comment">
    		  <tr >
    		    <td><fmt:formatDate value="${comment.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    		    <c:if test="${comment.commentType==0 }">
    		    	<td>作品集评论</td>
    		    </c:if>
    		    <c:if test="${comment.commentType==1 }">
    		    	<td>来拍社评论</td>
    		    </c:if>
    		    <td><a href="<%=request.getContextPath() %>/galleryManAction_toEdit.action?galaryId=${comment.lpGalary.galaryId}"><c:out value="${comment.lpGalary.subjectName }"/></a></td>
    			<td><c:out value="${comment.commentDetail }"></c:out></td>
    			<td><a href="deleteCommentById.action?commentId=${comment.commentId }&userId=${user.userId}">删除</a></td>
    	     <tr>
    		</c:forEach>
    	-->
    	 </tbody>
    </table>
   
     <table width="98%" class="splitPage">
		<tr id=null>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="commentForm" title="记录" unit="条记录" actionName="messageAction_queryAllComments.action"/>
			</td>
		</tr>
	</table>
	 </form>
	 </div>
  </body>
</html>
