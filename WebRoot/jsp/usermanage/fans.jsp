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
    
    <title>My JSP 'fans.jsp' starting page</title>
     <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" /> 
    <link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
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
  function  deletefollowByid(followId){
    if(confirm("确定要删除吗?")){
       var option = {
			url:"deleteFollowById.action",
			type:"post",
			dataType:"json",
			data:{
				followId:followId
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
  <form id="fansForm" name="fansForm" action="<%=request.getContextPath() %>/queryFans.action">
    <input type="hidden" name="userId" value="${userId }">
    <table style="width: 80%;margin-left:2%;" class="tablelist">
    	<thead>
    	<th>序号<i class="sort"><img src="images/px.gif" /></i></th>
    	    <th>关注时间</th>
    	    <th>粉丝账号</th>
    	    <th>粉丝昵称</th>
    		<th>操作</th>
    	</thead>
    	<tbody> 
    		<c:forEach items="${fansInfo }" var="fans" varStatus="status">
    		  <tr>
    		   <td><c:out value="${status.index+1}"> </c:out></td>
    			<td><fmt:formatDate value="${fans.followTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    			<td><a href="<%=request.getContextPath() %>/queryUserById.action?userId=${fans.lpUserByUserId.userId }" target="_self"> <c:out value="${fans.lpUserByUserId.userName }"/></a> </td>
    			<td><c:out value="${fans.lpUserByUserId.nickName }"/></td> 
    			<td><a href="javascript:void(0)" onclick="deletefollowByid('${fans.followId}')"> 删除</a></td> 
    		  </tr>
    		</c:forEach>
    		
        </tbody>
    </table>
    
    <table width="80%" class="splitPage">
		<tr id=null>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="fansForm" title="记录" unit="条记录" actionName="queryFans.action"/>
			</td>
		</tr>
	</table>
	</form>
	</div>
  </body>
</html>
