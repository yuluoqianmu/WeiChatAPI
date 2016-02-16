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
    
    <title>My JSP 'comment.jsp' starting page</title>
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/css/select.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <script type="text/javascript">
   function  deletecomment(commentId){
    if(confirm("确定要删除吗?")){
       var option = {
			url:"deleteCommentAjax.action",
			type:"post",
			dataType:"json",
			data:{
				commentId:commentId
			},
			success:function(responseText){
				 alert("删除成功"); 
				 location.reload();
			},
			error:function(){
				alert("系统错误");
			}
	};
    
	    $.ajax(option);
	    }
    }
  
  
  
  </script>
  <body>
  <div class="rightinfo">
    <form id="commentForm" name="commentForm" action="<%=request.getContextPath() %>/queryComment.action">
      <input type="hidden" name="userId" value="${userId }">
     <table class="tablelist">
    	<thead>
    	    <th>序号<i class="sort"><img src="images/px.gif" /></i></th>
    	    <th>评论时间</th>
    	    <th>所属作品集</th>
    		<th>评论内容</th>
    		<th>操作</th>
    	</thead>
    	<tbody> 
    		<c:forEach items="${commentInfo }" var="comment" varStatus="status">
    		  <tr >
    		    <td><c:out value="${status.index+1}"> </c:out></td>
    		    <td><fmt:formatDate value="${comment.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    		    <td><a href="<%=request.getContextPath() %>/galleryManAction_toEdit.action?galaryId=${comment.lpGalary.galaryId}&JSP=galleryJSP" target="_self"><c:out value="${comment.lpGalary.subjectName }"/></a></td>
    			<td><c:out value="${comment.commentDetail }"></c:out></td>
    			<td><a href="javascript:void(0)" onclick="deletecomment('${comment.commentId }')">删除</a></td>
    	     <tr>
    		</c:forEach>
    	 </tbody>
    </table>
   
     <table width="98%" class="splitPage">
		<tr id=null>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="commentForm" title="记录" unit="条记录" actionName="queryComment.action"/>
			</td>
		</tr>
	</table>
	 </form>
	 </div>
  </body>
</html>
