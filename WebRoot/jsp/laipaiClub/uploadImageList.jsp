<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>上传图片消息提示</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/style1.css"/>
	<script type="text/javascript">
	function unloadShow(){
		var path="<s:property value="#request.imgPath"/>";
		if(window.parent.frames['editFrame']!=null&&path!=null&&path!=""){
		window.parent.contentAppend(path);
		}
	}
	</script>
  </head>
  <body  style="text-align:center;"onunload="unloadShow()">
  <s:if test="#request.imgPath!=null">
      <div  style="vertical-align: middle;line-height:140px;"><span style="font-weight:bold;"><h2><img src="<%=basePath%>images/1.gif">上传成功！</h2></span></div>
  </s:if>
  <s:else>
  <div  style="vertical-align: middle;line-height:140px;"><span style="font-weight:bold;"><h2><img src="<%=basePath%>images/3.gif">上传失败，请检查文件类型!</h2></span></div>
  </s:else>
  </body>
</html>
