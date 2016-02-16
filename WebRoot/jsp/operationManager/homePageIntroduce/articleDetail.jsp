<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<head>
<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <base href="<%=basePath%>">
<title>菜单页</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/ckfinder/ckfinder.js"></script>
		
<script type="text/javascript">
	function online(introduceId){
		var hasOtherOnline = $("#hasOtherOnline").val();
		if(hasOtherOnline == "true"){
			if(confirm("已经有上线的文章，是否仍然要上线此文章？")){
				var url= "<%=request.getContextPath()%>/updateHomePageArticleOnline.action?introduceId="+introduceId+"&status=1";
				location.href = url;
			}
		}else{
			if(confirm("确定上线此文章,在app中显示？")){
				var url= "<%=request.getContextPath()%>/updateHomePageArticleOnline.action?introduceId="+introduceId+"&status=1";
				location.href = url;
			}
		}
		
	}

	function offline(introduceId){
		if(confirm("确定下线此文章,在app中隐藏？")){
			var url= "<%=basePath%>/updateHomePageArticleOnline.action?introduceId="+introduceId+"&status=0";
			location.href = url;
		}
	}
	
	function editArticle(introduceId){
		var url= "<%=basePath%>toHomePageEdit.action?introduceId="+introduceId;
		location.href = url;
	}
	
</script>

</head>


<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">运营后台</a></li>
    <li><a href="#">首页推广</a></li>
    <li><a href="#">推广文章详情</a></li>
    </ul>
    </div>
    <div class="rightinfo">
    <div class="tools">
    
    	<ul class="toolbar">
        <li class="click" id="editStatus">
	        <s:if test="#request.introduce.status == 0">
		        <a href="javascript:online('<s:property value="#request.introduce.introduceId"/>');">
		        	<span><img src="images/online.png" title="点击修改状态为上线"/></span>上线 
		        </a>
	        </s:if>
			<s:if test="#request.introduce.status == 1">
				<a href="javascript:offline('<s:property value="#request.introduce.introduceId"/>');">
					<span><img src="images/offline.png" title="点击修改状态为下线"/></span>下线
				</a>
			</s:if>
        </li>
        <li id="editArticle" onclick="editArticle('<s:property value="#request.introduce.introduceId"/>');"><span><img src="images/t02.png" /></span>编辑</li>
        </ul>        
        <ul class="toolbar1">
        </ul>
    
    </div>
   	<div class="formtitle"><span>文章详情</span></div>
	   	<div class="formbody">
	    <ul class="forminfo">
	    <form id="articleForm" name="articleForm" method="post" action="saveArticle.action" enctype="multipart/form-data">
	    	<input type="hidden"  name="hasOtherOnline" id="hasOtherOnline" value="<s:property  value='#request.hasOtherOnline'/>"  />
	    	<li>
	    		<label style="width: 100%;" >标题：&nbsp;&nbsp;<font style="font-size:15px;font-weight:bold"><s:property  value="#request.introduce.title"/></font></label>
	    	</li>
			<li>
				<label style="width:100%">推广封面 ：<img src='<s:property  value="#request.introduce.imgUrl"/>' height='100' /></label>
				<label style="width:100%">上线时间：<s:property  value="#request.introduce.onLineTime"/></label>
				<label style="width:100%">下线时间：<s:property  value="#request.introduce.offLineTime"/></label>
				<label style="width:100%">状态：
					<s:if test="#request.introduce.status == 0"><font style="font-size:15px;font-weight:bold;color:red">已下线 </font></s:if>
					<s:if test="#request.introduce.status == 1"><font style="font-size:15px;font-weight:bold;color:green">上线 </font></s:if>
				</label>
			</li>
			<li><label>正文：</label></li>
			<li>
				<span style="width:330px;height:100%;"><c:out value="${introduce.content}" escapeXml="false"/></span>
			</li>
	    </div> 
    </div>
    
  
  
 </form>
</body>

</html>
