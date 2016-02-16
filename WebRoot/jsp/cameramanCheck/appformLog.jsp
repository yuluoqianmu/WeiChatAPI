<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<head>
<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <base href="<%=basePath%>">
<title>申请列表</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>
  </head>
  
  <body>
  <form id="logForm" name="logForm" method="post" action="<%=request.getContextPath()%>/checkAction_queryappformLog.action" enctype="multipart/form-data">
    <table class="tablelist" style="margin-left:80px;width:500px;">
    	<thead>
    	<tr>
        <th style="width: 5%;">申请时间</th>
        <th style="width: 5%;">审核时间</th>
        <th style="width: 15%;">未通过理由</th>
        </tr>
        </thead>
        <tbody>
         <c:forEach items="${appforlist}" var="appformlog"> 
        <tr>
        <td style="vertical-align:middle; text-align:center; width: 5%;">
        <fmt:formatDate value="${appformlog.applyTime }"  pattern="yyyy-MM-dd HH:mm:ss"/>
        </td>
        <td style="vertical-align:middle; text-align:center; width: 5%;">
         <fmt:formatDate value="${appformlog.checkTime }"  pattern="yyyy-MM-dd HH:mm:ss"/>
         </td>
        <td style="width:15%; vertical-align:middle; text-align:center;">${appformlog.rejectReason }</td>    
        </tr> 
        </c:forEach>    
    </table>
     <table width="98%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="logForm" title="记录" unit="条记录" actionName="checkAction_queryappformLog.action"/>
			</td>
		</tr>
	</table>
	</form>
  </body>
</html>
