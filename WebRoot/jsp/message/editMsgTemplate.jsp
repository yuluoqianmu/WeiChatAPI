<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>短信模板页面</title>
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/default.css"/>

	<script type="text/javascript">
		function backToList()
		{
			window.location.href="messageAction_queryMessageCount.action";
		}
		
		
	</script>
  </head>
  <body>
    <div class="formbody">
    <div class="formtitle">
    	<span>修改短信模板</span>
    </div>
    <ul class="forminfo">
    <form id="editForm" name="editForm" method="post" action="messageAction_saveMsgTemplate.action" >
    	<input type="hidden" id="messageCodeId" name="messageCodeId" value="<s:property  value='#request.messageCode.messageCodeId'/>" />
    	
		<li><label style="width:800px;">内容编辑：</label></li>
		<li>
			<textarea class="dfinput" name="messageTemplate" id="messageTemplate" style="width:60%;height: 60%" rows="7" ><s:property value="#request.messageCode.messageTemplate"/></textarea>
		</li>
		<li><input name="submit"  type="submit" class="btn" value="提交" class="dfinput" />
			<input name="reset" type="reset" class="btn" value="重置" class="dfinput" />
			<input name="back" type="button" class="btn" value="返回" class="dfinput" onclick="backToList();"/></li>
       </form>
    </div> 
  </body>
</html>
