<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改</title>
            <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.8.0.min.js"></script>
	<link href="<%=request.getContextPath()%>/css/select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/select-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/editor/kindeditor.js"></script>
   <script type="text/javascript">
   	$(function(){
   	$("#Form1").submit(function(){
   	if($("#styleName").val() == "")
   	{
   		if($("#theName").length == 0)
   		{
   			$("#styleName").after("<font id='theName' style='color:red'>不能为空!</font>");
   		}
   		return false;
   	}else{
   		$("#theName").remove();
   	}
   	return true;
   	});
	   	
   	
   	
   	});
   
   
   </script>
  </head>
  
  <body>
    <div class="formbody">
    <div class="formtitle"><span>专题编辑</span><span style="float: left;margin-left: 1110px;height: 30px"><img height="30px" width="30px" onclick="backToList()" src="${pageContext.request.contextPath}/images/test/16.png"></span></div>
     <ul class="forminfo">
   	<form id="Form1" name="Form1" method="post" action="${pageContext.request.contextPath}/style_updateStyle.action">
   	<input type="hidden" value="${requestScope.lpStyle.styleId}">
    <li><label>风格名称:</label><input id="styleName" name="styleName" type="text" class="dfinput" value="${requestScope.lpStyle.styleName}"/></li><br/><br/>
    <li><label>风格类型:</label>
    用户风格<input name="styleType" type="radio" value="0" <c:if test="${requestScope.lpStyle.styleType == 0 }">checked</c:if>/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   作品集风格<input name="styleType" type="radio" value="1" <c:if test="${requestScope.lpStyle.styleType == 1 }">checked</c:if> />
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    服务风格<input name="styleType" type="radio" value="2" <c:if test="${requestScope.lpStyle.styleType == 2 }">checked</c:if>/>
    </li><br/><br/><br/>
    <li><label>显示布局:</label>
    	全局显示<input name="styleStatus" type="radio"  value="0" <c:if test="${requestScope.lpStyle.styleStatus == 0 }">checked</c:if> />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	摄影师显示<input name="styleStatus" type="radio"  value="1" <c:if test="${requestScope.lpStyle.styleStatus == 1 }">checked</c:if>/>
    </li><br/><br/><br/>
    <li><input name="" type="submit" class="btn" value="确认保存" class="dfinput"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input name="" type="reset" class="btn" value="重置" class="dfinput"/>
    </li>
    </form>
    		</div>
  </body>
</html>
