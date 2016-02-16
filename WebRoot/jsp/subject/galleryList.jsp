<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <title>作品集选取</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/style1.css" rel="stylesheet" type="text/css" />
<link href="css/select.css" rel="stylesheet" type="text/css" />
<link href="css/mystyle.css" rel="stylesheet" type="text/css" />
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.0.min.js"></script>
    <script type="text/javascript">
     
    $(function(){
  	var array=[];
  	var ids;
  	var subject_id = "${requestScope.subject_id}";
  		//点击取消勾号
       $(".right_img_box").live("click",function(){
                  var id=$(this).prev().find("img").attr("id");
                  array.splice($.inArray(id,array),1);
                  $(this).remove();
        });
        //点击添加勾号
    	$('._img').click(function(){
	    	//判断是否有勾号
	    	 if($(this).find(".right_img").length>0){
	    	           return;
	    	 }
    	 var id=$(this).find("img").attr("id");
    	 array.push(id);
         var right_img=$("<img class='right_img' src='images/subjectUp/11.png'/>");
         var box=$("<div class='right_img_box'></div>");
         box.append(right_img);
         $(this).parent().append(box);
    	});
    	//将选择作品集提交
    	$("#post").click(function(){
    	//将以打勾的图片id放入数组
    	$(".right_img").each(function(){
    	   array.push($(this).attr("id"));
    	})
    		ids = array.join(",");
    		window.location.href="${pageContext.request.contextPath}/subject_addGallery.action?subject_id=${requestScope.subject_id}&ids="+ids;
    	});
    });
    </script>
    <style type="text/css">
    ._img{
        z-index:1;
        width: 100%;height: 70%;
        position: absolute;
    }
    .right_img{
        z-index:2;
        float:right;
        position:relative;
        width:20px;
        height: 20px;
    }
    .right_img_box{
      width:100%;
      height:100%;
      z-index:10;
      background-color:transparent;
      position:absolute;
    }
    </style>
  </head>
  
  <body style="overflow:hidden;">
  <div style="width:90%;text-align:right; height: 50px;margin-top: 30px;">
  	<span style="border: 0px;border-style: solid;padding-left:20px;float:right;"><a href="${pageContext.request.contextPath}/subject_galleryList.action?subject_id=${requestScope.subject_id}&scanType=1">按上传时间查看</a></span>
  	<span style="border: 0px;border-style: solid;padding-left:20px;float:right;"><a href="${pageContext.request.contextPath}/subject_galleryList.action?subject_id=${requestScope.subject_id}&scanType=2">按赞次数查看</a></span>
  	<span style="border: 0px;border-style: solid;float:right;"><a href="${pageContext.request.contextPath}/subject_galleryList.action?subject_id=${requestScope.subject_id}&scanType=3">按摄影师查看</a></span>
  </div>
  <div style="width: 85%;height: 300px;overflow:auto;background-color: white;margin-bottom: 20px;margin-left: auto;margin-right:auto">
  	<c:forEach items="${requestScope.galleryList}" var="gallery">
  	<div style="width: 220px;position:relative; float:left; height: 300px;margin-left: 25px;margin-right: 25px;margin-top: 30px;">
  		<div class='_img'>
  		<!--判断作品集是否选中是否选中 -->
  		<c:if test="${requestScope.detailList != null}" var="isNotEmpty">
  			<c:forEach items="${requestScope.detailList}" var="subjectDetail">
				  			<c:if test="${subjectDetail.gallery_id == gallery.galaryId}">
				  				<div class='right_img_box'><img id="${gallery.galaryId}" class='right_img' src='images/subjectUp/11.png'/></div>
				  			</c:if>
  			</c:forEach>
  		</c:if>
  			<img style="width:100%; height:120%;z-index:1;"  id="${gallery.galaryId}" src="${gallery.galaryCover}">
  			<div style="height:100px;width:100%;z-index:1;margin-top: 10px">
  			<img src="<%=request.getContextPath() %>/images/camera.png" width="15px" height="15px"/><b style="color: blue">${gallery.lpUser.userName}</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  			<img src="<%=request.getContextPath() %>/images/view.png" width="20px" height="15px"/><b style="color: blue">${gallery.viewNumber}</b>&nbsp;
  			<img src="<%=request.getContextPath() %>/images/praise.png" width="20px" height="15px"/><b style="color: blue">${gallery.likeNumber}</b></div>
  		</div>
  	</div>
  	</c:forEach>
  </div>
    <div style="width:100%;text-align:center; height: 50px;margin-top: 30px;">
	 <input id="post" class="btn" type="button" value="提交">
  </div>
 
  </body>
</html>
