<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'top.jsp' starting page</title>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
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

<script type="text/javascript">
$(function(){	
	
	$('.itab a').click(function(){
		window.parent.frames['view_frame'].location = $(this).attr("newUrl");
		$(".selected").each(function(){
		$(this).removeClass('selected');
		})
		$(this).addClass('selected');
	});
})	


</script>	
	

  <body>
    
      <div class="place">
	    <span>位置：</span>
	    <ul class="placeul">
	      <li><a>摄影师管理</a></li>
	      <li><a>摄影师信息</a></li>
	    </ul>
      </div>
    
    
    <div class="formbody">
    	<div id="usual1" class="usual"> 
    <div class="itab" id="message">
  	<ul> 
    	<li style="width: 10%;height: 10%"><a class='selected' href="<%=request.getContextPath()%>/cameraManAction_getCameraman.action?userId=${cameraman.userId}" target="view_frame">基础信息</a></li> 
    	<li style="width: 10%"><a href="<%=request.getContextPath() %>/queryService.action?userId=${cameraman.userId}" target="view_frame">服务--${serviceNumber }</a></li>
	    <li style="width: 10%"><a href="<%=request.getContextPath() %>/orderAction_queryOrderByCamera.action?userId=${cameraman.userId}" target="view_frame">订单--${orderNumber }</a></li>
	    <li style="width: 10%"><a href="<%=request.getContextPath() %>/queryGalary.action?userId=${cameraman.userId}" target="view_frame">作品集--${gallaryNumber }</a></li>
	    <li style="width: 10%"><a href="<%=request.getContextPath() %>/queryFollow.action?userId=${cameraman.userId}" target="view_frame">关注--${followNumber }</a></li>
	    <li style="width: 10%"><a href="<%=request.getContextPath() %>/queryFans.action?userId=${cameraman.userId}" target="view_frame">粉丝--${fansNumber }</a></li>
	    <li style="width: 10%"><a href="<%=request.getContextPath() %>/queryLike.action?userId=${cameraman.userId}" target="view_frame">喜欢--${likeNumber }</a></li> 
  	</ul>
    </div> 
    
    </div> 
    </div>
    
  </body>
</html>
