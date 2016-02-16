<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>巩固加强</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.0.min.js"></script>
    <style type="text/css">
    body
    	{
    		font-family: "微软雅黑";
    		font-size: 9pt;
    		color: #333333;
    		background-color: #eef4fa;
    	}
    	.fatherDiv
    	{
			height: 115%;
			/*让div居中显示*/
			/*margin-left: auto;*/
			/*margin-right: auto;*/
			/*让div的位置相对定位*/
			position: relative;
			/*如果div的内容超过高度让它可拉动*/
			/* overflow: auto; */
    	}
    	.sonDiv
    	{
    		height:45%;
    		width: 20%;
    		margin-left: 3%;
    		margin-right:2%;
    		/*让此div按照从左到右依次排列*/
    		float: left;
    		position: relative;
    		margin-top: 3%;
    	}
    	.text
    	{
    		height: 10%;
    		width: 100%;
    		background-color: white;
    		color:#5B5B5B;font-size:10px;
    	}
    	.theImg
    	{
    		width: 100%;
    		height: 90%;
    	}
    	.img_box
    	{
    		height: 100%;
    		z-index: 10;
    		position: absolute;
    		top: 20px;
    		right: 0px;
    	}
    	.right_img
    	{
    		position: relative;
    		float: left;
    		width:20px;
    		height:20px;
    		z-index: 100;
    	}
    	.smallImgDiv
    	{
    		height:70%;
    		width: 10%;
    		/*让此div按照从左到右依次排列*/
    		float: left;
    		position: relative;
    	}
    	.small
    	{
    		width: 100%;
    		height: 100%;
    	}
    a:link, a:visited
    {
    	text-decoration: none;
    	font-family: "微软雅黑", Helvetica, sans-serif;
		font-size: 9pt;
		color: #333333;
		text-decoration: none;
    }
    a:hover
    {
    	font-family: "微软雅黑", Helvetica, sans-serif;
		font-size: 10pt;
		color: #2A4C86;
    }
    .smallDiv
    {
    	border-left: 3px;
    	border-bottom:none;
    	border-right: 3px;
    	border-top:none;
    	border-left-style:solid;
    	border-right-style:solid;
    	border-color: #868686;
    	
    }
  
    .btn
    {
    	display: inline-block;  
    zoom: 1; /* zoom and *display = ie7 hack for display:inline-block */  
    *display: inline;  
    vertical-align: baseline;  
    margin: 0 2px;  
    outline: none;  
    cursor: pointer;  
    text-align: center;  
    text-decoration: none;  
    font: 9pt/100% "微软雅黑", Helvetica, sans-serif;  
    padding: .5em 2em .55em;  
    text-shadow: 0 1px 1px rgba(0,0,0,.3);  
    -webkit-border-radius: .5em;   
    -moz-border-radius: .5em;  
   border-radius: .5em;  
    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);  
    -moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);  
    box-shadow: 0 1px 2px rgba(0,0,0,.2); 
    }
.bigImgDiv
    	{
    		height:35%;
    		float: left;
    		margin-left:40px;
    		margin-bottom:20px;
    		margin-top:40px;
    		position: relative;
    		border: 2px solid gray;
    	}
    	.bigImg
    	{
    	 /* width: 100%; */
    	}
    	.btnDiv
    	{
    	    position: absolute;
    		/* margin-top:150px; */
    		right: 0px;
    		bottom:0px; 
    	}
    	.img_box
    	{
    		position: absolute;
    		z-index: 10;
    		width: 100%;
    		height: 100%;
    		top: 0px;
    		left: 0px;
    	}
    	.describeDiv
    	{
    		height: 10%;
    		width: 100%;
    		position:relative;
    	}
    </style>
    <script type="text/javascript">
    	function DrawImage(ImgD){
var image=new Image();
var iwidth = 420;            //定义允许图片宽度，当宽度大于这个值时等比例缩小
var iheight = 180;            //定义允许图片高度，当宽度大于这个值时等比例缩小
image.src=ImgD.src;
if(image.width>0 && image.height>0){         //假如图片长宽都不为零
     if(image.height/image.width>= iheight/iwidth){       //通过正弦值判断图片缩放后是否偏高
      if(image.height>iheight){        //如果图片比设定的要高
       ImgD.height=iheight;
       ImgD.width=(image.width*iheight)/image.height;
      }else{
       ImgD.width=image.width;
       ImgD.height=image.height;
      }
 
     ImgD.alt=image.width+"×"+image.height;
     }
 
     else{           //如果图片比例 小于 设定的比例
      if(image.width>iwidth){
       ImgD.width=iwidth;
       ImgD.height=(image.height*iwidth)/image.width;
      }else{
       ImgD.width=image.width;
       ImgD.height=image.height;
      }
     ImgD.alt=image.width+"×"+image.height;
     }
}
}	 
    	
    	
    	$(function(){
    	var selectId = [];
    	var ids;
    	var subject_id =$("#subject_id").val();
    	var scanType = 1;
    		//ajax：作品集分页显示
    		var nowPage = 1;
    		var pageCount;
    		pagingAjax(nowPage,1);
    		
    		function pagingAjax(nowPage,scanType)
    		{
    			$.ajax({
					type:"post",	    			
    				cache:false,
    				data:{"nowPage":nowPage,"scanType":scanType},
    				url:"${pageContext.request.contextPath}/galaryAjaxPaging.action",	
    				dataType:"json",
    				/* beforeSend:function(){}, */
    				success:function(data){
    				    gallaryCount = data.gallaryCount;
    					var bannerDiv =$("<div style='width: 100%;height: 20px;margin-bottom: 0px;position: relative;'></div>");
    					var allGalary = $("<div style='left: 0;top: 0;'>全部作品 :"+gallaryCount+"</div>");
    					var condition = $("<div style='position: absolute;right: 0;top: 0;'></div>");
    					var conditionText = $("<div style='right: 0;top: 0;'><a href='#' id='time'>按上传时间排序</a>&nbsp;&nbsp;<a href='#' id='style'>按风格排序</a>&nbsp;&nbsp;<a href='#' id='man'>按摄影师排序</a></div>");
    					condition.append(conditionText);
    					bannerDiv.append(allGalary);
    					bannerDiv.append(condition);
    					
    					$(".fatherDiv").append(bannerDiv);
    					$.each(data.list,function(index,comment){

    						var bigImgDiv = $("<div class='bigImgDiv'><img id='"+comment.galaryId+"' class='bigImg' width='100px' onload='javascript:DrawImage(this)'  src='"+comment.imgCover+"'></div>");

    						/* var bigImgDiv = $("<div class='bigImgDiv'><img id='"+comment.galaryId+"' onload='javascript:DrawImage(this)'  src='"+comment.imgCover+"'></div>"); */

    						var describeDiv = $("<div class='describeDiv'></div>");
    						var author = $("<span style='width:25%;heigth:100%; float:left;'><img height='15px' width='15px' src='${pageContext.request.contextPath}/images/camera.png'>"+comment.author+"</span>");
    						var likeNumber = $("<span style='width:25%;heigth:100%; float:left;'><img height='15px' width='15px' src='${pageContext.request.contextPath}/images/like.jpg'>"+comment.likeNumber+"</span>");
    						var viewNumber = $("<span style='width:25%;heigth:100%;float:left;'><img height='15px' width='15px' src='${pageContext.request.contextPath}/images/view.png'>"+comment.vierNumber+"</span>");
    						var commentNumber = $("<span style='width:25%;heigth:100%;float:left;'><img height='15px' width='15px' src='${pageContext.request.contextPath}/images/comment.png'>"+comment.commentNumber+"</span>");
    						var galarySubject = $("<div style='position:absolute;left:0px;bottom:10%;'>"+comment.subjectName+"</div>");
    						var price = $("<div style='position:absolute;right:0px;bottom:10%;color:red;background-color:transparent;'>"+comment.price+"元</div>");;
    						describeDiv.append(author);
    						describeDiv.append(likeNumber);
    						describeDiv.append(viewNumber);
    						describeDiv.append(commentNumber);
    						bigImgDiv.append(describeDiv);
    						bigImgDiv.append(price);
    						bigImgDiv.append(galarySubject);
    						
    						$(".fatherDiv").append(bigImgDiv); 
    					});
    					pageCount = data.pageCount;
    					var btnDiv = $("<div class='btnDiv'></div>");
    					var firstBtn = $("<input class='btn' type='button' name='first' value='首页'>");
    					var preBtn = $("<input  class='btn' type='button' name='pre' value='上一页'>");
    					var nextBtn = $("<input  class='btn' type='button' name='next' value='下一页'>");
    					var endBtn = $("<input  class='btn' type='button' name='end' value='末页'>");
    					btnDiv.append(firstBtn);
    					btnDiv.append(preBtn);
    					btnDiv.append(nextBtn);
    					btnDiv.append(endBtn);
    					$(".fatherDiv").append(btnDiv);
    					
    				}
    			});
    		}
    		
    		$(".btn").live("click",function(){
    			
    			var btnName = $(this).attr("name");
    			if('first' == $.trim(btnName))
    			{
    				if(nowPage == 1)
    				{
    					return;
    				}
    				$(".fatherDiv").empty();
    					nowPage = 1;
    				
    			}
    			if('pre' == $.trim(btnName))
    			{
    				if(nowPage == 1)
    				{
    					return;
    				}
    				$(".fatherDiv").empty();
    				nowPage--;
    			}
    			if('next' == $.trim(btnName))
    			{
    				if(nowPage == pageCount)
    				{
    					return;
    				}
    				$(".fatherDiv").empty();
    				nowPage++;
    			}
    			if('end' == $.trim(btnName))
    			{
    				if(nowPage == pageCount)
    				{
    					return;
    				}
    				$(".fatherDiv").empty();
    				nowPage = pageCount;
    			}
    			pagingAjax(nowPage,scanType);
    		});
    		
    		$(".bigImg").live("click",function(){
    			var cloneId = $(this).attr("id");
    			//alert(cloneId);
    			var selectIds = [];
    			$(".select").each(function(index,div){
    				var id = $(this).attr("id");
    				selectIds.push(id);
    			});
    			//如果已经选择 则直接返回
    			for(var i = 0; i < selectIds.length; i++)
    			{
    				var select = selectIds[i];
    				if(select == cloneId)return false;
    			}
    			var img_box = $("<div class='img_box' id='box"+cloneId+"'></div>");
    			var right_img = $("<img class='right_img' style='height:20px;width:20px;' src='${pageContext.request.contextPath}/images/subjectUp/11.png' />");
    			img_box.append(right_img);
    			var cloneImgSrc = $(this).attr("src");
    			
    			var clonDiv=$("<div style='width:10%;height:100px;float:left;margin-left:5px;border:2px solid;' id='"+cloneId+"'  class='cloneDiv'><img  height='100%' width='100%' src='"+cloneImgSrc+"' /></div>");
    			$("#smallDiv").append(clonDiv);
    			$(this).parent().append(img_box);
    		});
    			
    		$(".img_box").live("click",function(){
    			var clone_box_id = $(this).attr("id");
    			var clone_id = clone_box_id.substring(3,clone_box_id.length);
    			$("#"+clone_id+"").remove();
    			//$('#"+clone_id+"').remove();
    			$(this).remove();
    		});
    		
    		$(".cloneDiv").live("click",function(){
    			var colneId = $(this).attr("id");
    			$("#box"+colneId+"").remove();
    			$(this).remove();
    		});
    		$("#tijiao").click(function(){
        		//遍历所有打勾图片所在div，并将需求图片的id加入数组
        			var select = [];
        			$(".cloneDiv").each(function(){
        			var id = $(this).attr("id");
        				select.push(id);
        			});
        			ids = select.toString();
        			/**
        			if(ids ==""){
        				alert("请选择作品集");
        				return false;  
        			}
        			*/
        			window.location.href="${pageContext.request.contextPath}/subject_addGallery.action?subject_id="+subject_id+"&ids="+ids;
        		});
    		
    		$.ajax({
    			type:"post",
    			cache:false,
    			data:{"subject_id":subject_id},
    			url:"${pageContext.request.contextPath}/getSelectGalary.action",
    			dataType:"json",
    			success:function(data){
    				$(data.list).each(function(index,comment){
    					var selectDiv=$("<div style='width:10%;height:100px;float:left;margin-left:5px;border:2px solid;' id='"+comment.galaryId+"'  class='select'><img height='100%' width='100%' src='"+comment.imgCover+"' /></div>");
    					$("#smallDiv").append(selectDiv);
    				});
    				
    			}
    		});
    		
    		$("#time").live("click",function(){
    			$(".fatherDiv").empty();
    			scanType = 1;
    			pagingAjax(1,1);
    		});
			$("#style").live("click",function(){
				$(".fatherDiv").empty();
				scanType = 2;
				pagingAjax(1,2);
    		});
			$("#man").live("click",function(){
				$(".fatherDiv").empty();
				scanType = 3;
				pagingAjax(1,3);
    		});
    	});
    </script>
  </head>
  <body>
  <div class="smallDiv" id="smallDiv" style="height: 30%;width: 100%;overflow: auto;">
  	<div style="height: 30%;"><span>已选取作品:<span><span style="float: right;"><input type="button" id="tijiao" class="btn" value="完成"></span></div>
  	<div style="display: none;"><input type="hidden" id="subject_id" value="${requestScope.subject_id}"></div>
  </div>
  <div>
  <hr>
	  <div class="fatherDiv"></div>
  
  </body>
</html>
