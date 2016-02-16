<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
     <title><s:property value='#request.laipaiBean.tile'/></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="来拍社">
	<meta http-equiv="description" content="来拍">
	<!--  <link href="css/share2.css" rel="stylesheet" type="text/css" /> -->
    <link href="css/share.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="jquery/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="jquery/download.js"></script>
   <style>
/*    p{
text-indent: 2em; /*em是相对单位，2em即现在一个字大小的两倍*/
} */

   </style>
  
  <script type="text/javascript">
  
  
$(function(){
	try{
	var winWidth=$('#contentDIV').width();
	$('#contentDIV').find('img').each(function(index, element) {
		$(this).load(function(e) {
            var width=$(this).width();
			var height=$(this).height();
			if(width!=winWidth){
				$(this).width(winWidth);
				$(this).height("auto");
			}
        });
    });
	}catch(e){alert('init image error:'+e)};
});
  </script>
  </head>
  
  <body>
  <div class="body">
		<!-- 头 -->
		<div class="top">
			<table border="0" class="t1">
				<tr>
					<td><img src="css/images/logo.png" style="margin-left:20px;" width="150" height="160"/></td>
					<td>
							<div><span class="big500">来拍</span></div>
							<div><span>移动互联网影像平台</span></div>
						</td>
					<td><input type="button" class="bt" value="立刻下载" onclick="javascript:window.location='phonepage.html'"></td>
				</tr>
			</table>
		</div>
		<!-- 封面 -->
				
		 <div  id='contentDIV' class="middle" style="width:90%;padding-top:5%;">
		   <div class="showeel">
		    <div style="position:relative; width: 100%;height:auto;z-index:1;"><img class="cover" style="height:500px" id="test" value="" src="${laipaiBean.coverUrl }"/></div>    
<!-- 		      <div class="transparent_black" style="width:45%;top:40%;height:150px;float:left;left:0;">
		     </div>  -->
		     <div class="transparent_black_show" style="width:80%;text-align: center;top:60%;height:240px;font-size:70px;left:0;"> 
		     <div style="padding:10px;"><s:property value='#request.laipaiBean.tile'/></div>
		     </div>
		  </div>
		  <div style="text-align:left; margin-top: 5%; font-family:微软雅黑; font-size:45px; color:#857F7F;"><span>
		                       喜欢<s:property value='#request.laipaiBean.likeNumber'/> .看过 <s:property value='#request.laipaiBean.viewNumber'/> .评论 <s:property value='#request.laipaiBean.commentNumber'/></span>
		                       </div>
		  <div id="content" style="text-align:left;margin-top:5%; font-size:40px;">
         <c:out value="${laipaiBean.content}" escapeXml="false"/>
         </br>
         </br>
         </br>
         </br>
		  </div>                     
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 </div>
		
 </div>	

  </body>
</html>
