<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>"/>
    <title>用户反馈列表</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/style1.css" rel="stylesheet" type="text/css" />
<link href="css/select.css" rel="stylesheet" type="text/css" />
<link href="css/mystyle.css" rel="stylesheet" type="text/css" />
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.0.min.js"></script>
    <script type="text/javascript">
        
   
    </script>
    <style type="text/css">
    
    </style>
  </head>
  
  <body>
  <div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">数据表</a></li>
    <li><a href="#">基本内容</a></li>
    </ul>
    </div>
  <div class="rightinfo">
   <div class="tools">
    
    	<ul class="toolbar">
       <!--  <li class="click"><span><img src="images/t01.png" /></span>添加</li>
        <li id="deleteall"><span><img id="delete" src="images/t03.png" /></span>删除</li> -->
        </ul>        
        <ul class="toolbar1">
        </ul>
    
    </div>
   <form id="feedbackForm" name="feedbackForm" method="post" action="<%=request.getContextPath()%>/queryFeedBack.action" enctype="multipart/form-data"> 
    <table style="width:90%" class="tablelist">
       	<thead>
    	<tr>
       <!--   <th><input name="" type="checkbox" value="" id="checkAll"/></th>  -->
        <th width="10%">序号<i class="sort"><img src="images/px.gif" /></i></th>
        <th width="17%">用户账号</th>
        <th width="37%">反馈信息</th>
        <th width="18%">用户QQ</th>
        <th width="18%">反馈时间</th>
        </tr>
        </thead>
        <tbody>
     <c:if test="${FeedbackView != null && fn:length(FeedbackView)>0}">
	 <c:forEach items="${FeedbackView}" var ="item" varStatus="i">
         <tr>
        <td>${i.index + 1}</td>
        <td>${item.id.userName }</td>
        <td >${item.id.userContent }</td>
        <td>${item.id.userQq }</td>       
        <td><fmt:formatDate value="${item.id.feedTime }" pattern="yyyy/MM/dd HH:mm:ss"/></td>
        <td></td>
        </tr> 
    </c:forEach>
       </c:if>
        </tbody>
        </table>
         <table width="90%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="feedbackForm" title="记录" unit="条记录" actionName="queryFeedBack.action"/>
			</td>
		</tr>
	    </table>
        </form>
        </div>
  </body>
</html>
