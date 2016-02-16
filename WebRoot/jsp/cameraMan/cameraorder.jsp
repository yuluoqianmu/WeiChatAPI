<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'order.jsp' starting page</title>
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
  <script type="text/javascript">
  	function removeByCamera(orderId,cameraId){
  		if(confirm("确定删除该订单？")){
  			location.href="<%=request.getContextPath() %>/orderAction_deleteOrderByCamera.action?orderId="+orderId+"&userId="+cameraId;
  		}
  	}
  </script>
  
  <body>
   <div class="rightinfo">
  <form name="orderForm" id="orderForm" method="post" action="<%=request.getContextPath() %>/orderAction_queryOrderByCamera.action">
   <input type="hidden" name="userId" value="${userId }">
    <table   class="tablelist">
      	<thead>
    	    <th>预约时间</th>
    		<th>预约人</th>
    		<th>作品主题</th>
    		<th>电话</th>
    		<th>留言</th>
    		<th>操作</th>
    	</thead>
    	<c:forEach items="${orderInfo }" var="order">
    	   <tr>
    	    <td><fmt:formatDate value="${order.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    	 	<td><a href="<%=request.getContextPath() %>/queryUserById.action?userId=${order.userId}" target="_self"><c:out value="${order.userNickName}"/></a></td>
    	 	<td><a href="<%=request.getContextPath()%>/galleryManAction_toEdit.action?galaryId=${order.galaryId}&JSP=galleryJSP" target="_self"><c:out value="${order.subjectName }"/></a></td>
    	 	<td><c:out value="${order.orderMobile }"></c:out> </td>
    	 	<td><c:out value="${order.commentDetail }"/>	
    	 	</td>
    	 	<td><a onclick="removeByCamera('${order.orderId}','${order.cameraId}')" style="cursor: pointer;">删除</a></td>
    	   </tr>
    	 </c:forEach>
    </table>

   <table width="98%" class="splitPage">
		<tr id=null>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="orderForm" title="记录" unit="条记录" actionName="orderAction_queryOrderByCamera.action"/>
			</td>
		</tr>
	</table>
	 </form>
  </body>
  </div>
</html>
