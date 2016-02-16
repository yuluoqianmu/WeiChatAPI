<%@page import="com.laipai.orderInfo.pojo.LpOrder"%>
<%@page import="com.laipai.userManInfo.pojo.LpComment"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>订单列表</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css"/>
    <link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
  <script type="text/javascript">
  	<%-- function cancel(orderId){
  	   if(confirm("是否取消此订单？")){
  	   	location.href="<%=request.getContextPath()%>/orderAction_cancelOrder.action?orderId="+orderId;
  	   }
  	} --%>
<%--   	function deleteorder(orderId){
  	   if(confirm("是否删除此订单？")){
  	   	location.href="<%=request.getContextPath()%>/orderAction_deleteOrderByUser.action?orderId="+orderId;
  	   }
  	} --%>
  	function sortOrder(id){
  		var galaryId ='<%=request.getAttribute("galaryId")%>';
  		location.href="<%=request.getContextPath()%>/orderAction_sortOrder.action?id="+id+"&galaryId="+galaryId;
  	}
  	 $(document).ready(function(){
     	$('.imgtable tbody tr:odd').addClass('odd');
     })
  	function sendMessage(orderId){
		location.href = "<%=request.getContextPath()%>/orderAction_toSendMessagePage.action?orderId="+orderId;  		 
  	}
  	
  	  function deleteorder(orderId){
   if (confirm("确定要删除此订单吗?")) {
     //alert(codeId); 
    /*  var page=$("#hiddenPage").val(); */

      $.ajax({
             url:"deleteOrderByUser.action",
			type:"post",
			dataType:"json",
			data:{
				orderId:orderId
			},
			success:function(responseText){
				 //alert("发送成功"); 
				 location.replace(location.href);
			},
			error:function(){
				alert("系统错误");
			}
			
			
         });
   
   
   }
  }
  
   function cancel(orderId){
   if (confirm("确定要取消此订单吗?")) {
     //alert(codeId); 
    /*  var page=$("#hiddenPage").val(); */

      $.ajax({
             url:"cancelOrder.action",
			type:"post",
			dataType:"json",
			data:{
				orderId:orderId
			},
			success:function(responseText){
				 //alert("发送成功"); 
				 location.replace(location.href);
			},
			error:function(){
				alert("系统错误");
			}
			
			
         });
   
   
   }
  }
  </script>
  </head>
  
  <body>
  <form id="orderForm" name="orderForm" action="orderAction_sortOrder.action" method="post">
    <div class="place">
    	<span>位置：</span>
    	<ul class="placeul">
    	<li>首页</li>
    	<li>订单管理</li>
    	<li>订单列表</li>
    	</ul>
    </div>
    <br/>
    <div class="rightinfo">
    <div class="tools">
    
    	<ul class="toolbar">
        <li id="smartIndex" onclick="sortOrder('status')"><span><img src="images/t04.png" /></span>按状态排序</li> 
        <li id="smartIndex" onclick="sortOrder('cameraman')"><span><img src="images/t04.png" /></span>按摄影师排序</li>
        <li id="smartIndex" onclick="sortOrder('userman')"><span><img src="images/t04.png" /></span>按预订人排序</li>
        </ul>
        
        
        <ul class="toolbar1">
      <!--   <li><span><img src="images/t05.png" /></span>设置</li> -->
        </ul>
    
    </div>
    <table class="tablelist">
    	<thead>
    		<%--<th><input type="checkbox"/></th>--%>
    		<th>序号</th>
    		<th>预订人</th>
    		<th>手机号</th>
    		<th>留言</th>	
    		<th>预定主题</th>
    		<th>摄影师</th>
    		<th>当前状态</th>
    		<th>操作</th>
    		<th>操作</th>
    	</thead>
    	<tbody>
    		<c:forEach items="${orderinfo }" var="order" varStatus="status">
    		  <tr>
    		  	  <td>${status.count }</td>
	    		  <td><a href="<%=request.getContextPath()%>/toeditUser.action?userId=${order.userId}"><c:out value="${order.userNickName }"/></td>
	    		  <td><c:out value="${order.orderMobile }"/></td>
	    		  <td><c:out value="${order.commentDetail }"></c:out></td>
	    		  <c:if test="${empty order.galaryId }">
	    		    <td><c:out value="抱歉，该作品集已被删除"/></td>
	    		  </c:if>
	    		  <c:if test="${!empty order.galaryId}">
	    		    <td><a href="<%=request.getContextPath() %>/galleryManAction_toEdit.action?galaryId=${order.galaryId}"><c:out value="${order.subjectName }"/></td>
	    		  </c:if><td><a href="<%=request.getContextPath()%>/cameraManAction_toeditCameraman.action?userId=${order.cameraId}"><c:out value="${order.cameraNickName }"/> </td>
	    		  <c:if test="${order.orderStatus==0 }">
	    		    <td>已支付</td>
	    		  </c:if> 
	    		  <c:if test="${order.orderStatus==1 }">
	    		    <td>未支付</td>
	    		  </c:if>
	    		  <c:if test="${order.orderStatus==2 }">
	    		    <td>已回电</td>
	    		  </c:if>
	    		  <c:if test="${order.orderStatus==3 }">
	    		    <td>待回电</td>
	    		  </c:if>
	    		  <c:if test="${order.orderStatus==4 }">
	    		    <td>已取消</td>
	    		  </c:if>
	    		  <td><a onclick="cancel('${order.orderId}')" style="cursor: pointer;" class="tablelink">取消</a>
	    		      &nbsp;&nbsp;
	    		      <a onclick="deleteorder('${order.orderId}')" style="cursor: pointer;" class="tablelink">删除</a>
	    		  </td>
	    		  <td><a onclick="sendMessage('${order.orderId}')" id="reply" class="tablelink" style="cursor: pointer;">系统回复</a></td>
	    		  </tr>
    		  </c:forEach>
    	</tbody>
    	</table>
    <table width="98%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="orderForm" title="记录" unit="条记录" actionName="orderAction_sortOrder.action"/>
			</td>
		</tr>
	</table>
	</div>
  </body>
  </form>
</html>
