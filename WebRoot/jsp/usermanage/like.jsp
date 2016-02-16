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
    
    <title>My JSP 'like.jsp' starting page</title>
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/css/select.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">


  </head>
  <script type="text/javascript">
   function  deleteLikeByid(likeId){
    if(confirm("确定要删除吗?")){
       var option = {
			url:"deleteLikeById.action",
			type:"post",
			dataType:"json",
			data:{
				likeId:likeId
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
  <form id="likeForm" name="likeForm" action="<%=request.getContextPath() %>/queryLike.action">
     <input type="hidden" name="userId" value="${userId }">
    <table style="width: 80%;margin-left:2%;"  class="tablelist">
    	<thead>
    	      <th>序号<i class="sort"><img src="images/px.gif" /></i></th>
    	    <th>喜欢时间</th>
    	    <th>作品集</th>
    		<th>操作</th>
    	</thead>
    	<tbody> 
    		<c:forEach items="${likeInfo }" var="like" varStatus="status">
    		  <tr>
    		   <td><c:out value="${status.index+1}"> </c:out></td>
    			<td><fmt:formatDate value="${like.likeTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    			<td><a href="<%=request.getContextPath() %>/galleryManAction_toEdit.action?galaryId=${like.lpGalary.galaryId }&JSP=galleryJSP" target="_self"> <c:out value="${like.lpGalary.subjectName}"/></a> </td>
    			<td><a href="javascript:void(0)"  onclick="deleteLikeByid('${like.likeId}')"> 删除</a></td>
    		  </tr>
    		</c:forEach>
        </tbody>
    </table>
   
    <table width="80%" class="splitPage">
		<tr id=null>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="likeForm" title="记录" unit="条记录" actionName="queryLike.action"/>
			</td>
		</tr>
	</table>
	 </form>
	 </div>
  </body>
</html>
