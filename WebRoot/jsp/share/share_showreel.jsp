<%@page import="com.laipai.img.ImgUtil"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ page import="com.laipai.galaryManInfo.pojo.LpGalary"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String headImage = "";
LpGalary lgGalary = (LpGalary)request.getAttribute("lgGalary");
String head = ImgUtil.getImgUrl(lgGalary.getLpUser().getHeadImage(), "jpg", 100);
if(head == null || "".equals(head.trim())){
	headImage = "http://img.ilaipai.com/laipailogo@100w_100h_2e.jpg";
}else if(!head.contains("img.ilaipai.com")){/*不是来自阿里云图片服务器的*/
	headImage = head;
}else{
	String[] temp = head.split("@");
	temp[1] = "100w_100h_2e.jpg";
	headImage =	temp[0]+"@"+temp[1]; 
}

%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
   
   <title>摄影师:<s:property value='#request.lgGalary.lpUser.realName'/>的作品</title>
   
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="作品集">
<meta http-equiv="description" content="来拍">
<!-- IE8浏览器支持 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- 自适应屏幕宽度 -->
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<!-- 引入BootStarp核心 -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
<link href="css/share2.css" rel="stylesheet" type="text/css" />
   <script type="text/javascript" src="jquery/jquery-1.8.0.min.js"></script>
   <script type="text/javascript" src="jquery/download.js"></script>
  <style>
   
  </style>
 <script type="text/javascript">
  $(function(){
	  var ids="<s:property value='#request.ids'/>";
      var count="<s:property value='#request.count' />";
  	  var galaryId="<s:property value='#request.lgGalary.galaryId' />";
	    $("#selectMore").click(function(){
  		$("#selectMore").hide();
  		$("#_loading").show();
  		var agrs={ids:ids,galaryId:galaryId};
      setTimeout(function(){
    	var index=false;
		$.ajax({
	         type : "post",  
	          url : "loadMorePictures.action",  
	          data : agrs,
	          async : false,
	          dataType:"json",
	          success : function(data){
	        	$("#_loading").hide();
	        	if(data.results.length==0){
	        		index=true;
	        	}
	  			$.each(data.results,function(index,item){
					ids=ids+item.photoId+",";
					$("#selectMore").before("<div class='showeel'>"+
						    "<div style=' position:absolute; width: 100%;height:auto;z-index:1; text-align: center;'><img  style='height:700px'  src='"+item.photoSrc+"' onload='javascript:DrawImage(this)'/></div>"
							+"</div>");
				})
			}
	     })
	    var l=ids.split(",").length;
		if(index){
			$("#hideMore").show();
			$("#selectMore").hide();
			$("#_loading").hide();
		}else{
			$("#selectMore").show();
		} 
		},500);
  		
  	})
	  
  })

var initFirstImageInfo=function(imgWidth,imgHeight){
	var divBg=$('.transparent_white');
	var divFg=$('.transparent_white_show');
	divBg.width(imgWidth-200);
	divBg.height(imgHeight-200);
	divBg.css('top','130px');
	divBg.css('left','100px');
	
	divFg.width(imgWidth-200);
	divFg.height(imgHeight-200);
	divFg.css('top','130px');
	divFg.css('left','100px');
}

var initImageText=function(imgWidth,imgHeight){
	var divBg=$('.transparent_black');
	var divFg=$('.transparent_black_show');
}	
$(function(){
	try{
	var winWidth=$(window).width();
	$('.showeel').each(function(index, element) {
		$(this).find('img[type=zoom]').each(function(index2, element) {
			$(this).load(function(e) {
				var width=$(this).width();
				var height=$(this).height();
				if(width!=winWidth){
					height=height*(winWidth/width);
					width=winWidth;
					$(this).width(width);
					$(this).height(height);
				}
				
				if(index==0){
				 initFirstImageInfo(width,height); 
				}
				if(index==1){
					/* initImageText(width,height); */
				}
			});
		});
	 });
	}catch(e){alert('init image error:'+e)};
});

  </script>
 </head>
 <body>
 <div class="container-fluid">
 
	<div class="row" style="height: 10%;background-color: #fff">
		<!--  	
		<img src="css/images/logo.png" class="img-responsive"/>
		</div>
			
		<div class="col-xs-6 col-md-6">
		<h3 style="margin-top: 18%;margin-left: -1.5%;">来拍
		<p class="small" style="margin-top: 1.5%;">移动互联网影像平台</p>
		</h3>
		</div>
		<div class="col-xs-4 col-md-4">			
		<input type="button" class="btn btn-danger" value="立刻下载" onclick="javascript:window.location='phonepage.html'">
		</div> -->
		<img src="css/images/head.jpeg" class="img-responsive" style="width: 100%;max-width: 100%;" onclick="javascript:window.location='phonepage.html'"/>
	</div>
	<!-- 头像 -->
	<div class="row-fluid" style="background-color: #fff;">
	<s:if test="#request.lgGalary.lpUser.headImage==null">
	 <div class="col-xs-12 col-md-12">
		<img src="/css/images/defaultouxiang.jpeg" class="center-block img-circle img-responsive">
	 </div>
	 </s:if>
	 <s:else>
	  <div class="col-xs-12 col-md-12" style="margin-top: 5%;">
		<img src="<%=headImage%>" class="center-block img-circle img-responsive">
	 </div>
	 </s:else>
	 <div class="col-xs-12 col-md-12" style="margin-top: 5%;text-align:center;margin-bottom: 5%;">
	 	<div>
	 	<span class="cameraMan">摄影师:<s:property value="#request.lgGalary.lpUser.realName"/></span>
	 	</div>
	 	<div><span class="camera_city"><s:property value="#request.lgGalary.lpUser.lpCity.cityName" />
	 	</span></div>
	 </div>
	 </div>
	
	<!-- 文字 -->		
	<hr/>
 		<div class="row-fluid" style="background-color: #fff;margin-top: 5%;">
 			<div class="col-xs-12 col-md-12" style="backgroud-color:#fff;">
 			
 			<div style="text-align: center"><span class="text2">
 			  <s:property value="#request.lgGalary.subjectName"/>
 			 </span>
 			 </div>
 			 <div style="margin-bottom: 2%;text-align: center;"><span class="text1">
 			  <s:property value="#request.lgGalary.galaryDesc"/></span></div>
 			</div>
 		</div> 
 		 
    <s:iterator value="#request.photos" id="p" status="status">
       <div class="row" style="margin-top: 0.5%;">
       <div class="col-xs-12 col-md-12" style="padding :0%;">
       <img class="img-responsive" style="width: 100%;max-width: 100%;" src="<s:property value="#p.photoSrc"/>">
       </div>
       </div>
	</s:iterator>
	
		<footer>
		</footer>
		</div>
  </body>
</html>
