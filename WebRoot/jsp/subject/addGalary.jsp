<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>巩固加强</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-ui.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/jquery-ui.min.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
    	body
    	{
    		font-family: "微软雅黑";
    		font-size: 9pt;
    		color: #333333;
    		background-color: #eef4fa;
    	}
    .mytable
    {
 	 	font-size: 12px;
	 	color: #336699;
	 	text-decoration: none;
	 	background-color: #FFFFFF;
	 	vertical-align: middle;
		text-align: center;
         BORDER: #336699;
         BORDER-COLLAPSE: collapse;
	}
	.locationIndex{
	     width:20px;
	     height:20px;
	}
    .galaryCover{
     cursor: pointer;
     border-color:#5B5B5B;
     border-width:1px;
     border-style:solid;
    }
    .btn
    {
    	display: inline-block;  
    zoom: 1; /* zoom and *display = ie7 hack for display:inline-block */  
    *display: inline;  
    vertical-align: baseline;  
    margin: 0 0px;  
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
    .textShow
    {
    	width: 80%;padding-left: 10%;
    }
    
    </style>
    <script type="text/javascript">
    	$(function(){
    		$( ".locationIndex" ).selectmenu({
    			width:60,
    			change: function( event, ui ) {
    				    			var newLocation = $(this).val();
    				    			var oldLocation = $(this).attr("id");
    				    			var gallaryId = $(this).attr("use");
    				    			window.location.href="${pageContext.request.contextPath}/subject_updateGalaryLocation.action?subjectId="+subject_id+"&gallaryId="+gallaryId+"&newLocation="+newLocation+"&oldLocation="+oldLocation;
    			}
    		});
    		var subject_id = $("#subject_id").val();
    		$("#toGalaryList").click(function(){
    			window.location.href="${pageContext.request.contextPath}/subject_galleryList.action?subject_id="+subject_id;
    		});
    		$("#editSubject").click(function(){
    			if(confirm("是否进入专题编辑"))
    				{
    				window.location.href = "${pageContext.request.contextPath}/subject_findSubjectById.action?subject_id="+subject_id;	
    				}
    				
    		});
<%--    		$(".locationIndex").change(function(){--%>
<%--    			var newLocation = $(this).val();--%>
<%--    			var oldLocation = $(this).attr("id");--%>
<%--    			var gallaryId = $(this).attr("use");--%>
<%--    			window.location.href="${pageContext.request.contextPath}/subject_updateGalaryLocation.action?subjectId="+subject_id+"&gallaryId="+gallaryId+"&newLocation="+newLocation+"&oldLocation="+oldLocation;--%>
<%--			});--%>
    		$(".galaryCover").click(function(){
    			if(confirm("您确定要查看该作品集详情么?"))
    				{
    					var id = $(this).attr("id");
    					window.location.href="${pageContext.request.contextPath}/galleryManAction_toEdit.action?galaryId="+id;
    				}
    		});
    	});
    	function deleteGalary(galaryId)
    	{
    		var subjectId = $("#subject_id").val();
    		if(confirm("您确定要删除么?"))
    		{
    			window.location.href="${pageContext.request.contextPath}/subject_deleteGalary.action?subjectId="+subjectId+"&galaryId="+galaryId;    			
    		}
    	}
    	function changeOnLine(subject_name,subject_id,status)
        {
        	var ison;
        	if(status == 1)
        	{
        		ison="下";
        	}
        	else
        	{
        		ison="上";
        	}
        	if(confirm("您确定要将【"+subject_name+"】专题"+ison+"线么？"))
        	{
        		 window.location.href="${pageContext.request.contextPath}/subject_changeStatus2.action?subject_status="+status+"&id="+subject_id;
        	}
       		
        }
    	var maxLocation = "${galary.maxLocation}";
    	function editLocation(oldLocation,subjectId,gallaryId)
    	{
    		var newLocation = $(".locationIndex").val();
    		//var newLocation = prompt("请输入要更换的位置！");
    		//输入位置和原位置相等 返回
    		if(newLocation == oldLocation)
    		{
    			return;
    		}
    		var reg=/^(?:0|[1-9][0-9]?|99)$/;
    		//输入位置不符合规范 返回
			if(!newLocation.match(reg) || newLocation > Integer.parseInt(maxLocation))
			{
			  return;
			}
    		window.location.href="${pageContext.request.contextPath}/subject_updateGalaryLocation.action?subjectId="+subjectId+"&gallaryId="+gallaryId+"&newLocation="+newLocation+"&oldLocation="+oldLocation;
    	}
    	
    	var flag=false;
function DrawImage(ImgD){
var image=new Image();
var iwidth = 110;            //定义允许图片宽度，当宽度大于这个值时等比例缩小
var iheight = 110;            //定义允许图片高度，当宽度大于这个值时等比例缩小
image.src=ImgD.src;
if(image.width>0 && image.height>0){         //假如图片长宽都不为零
flag=true;
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

function DrawImage2(ImgD){
var image=new Image();
var iwidth = 400;            //定义允许图片宽度，当宽度大于这个值时等比例缩小
var iheight = 190;            //定义允许图片高度，当宽度大于这个值时等比例缩小
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
    </script>
  </head>
  <body >
	 	<div style="width: 100%;height: 160px;position: relative;border: 1px solid gray;">
	 	<input type="hidden" id="subject_id" value="${requestScope.subject.subject_id}">
	 		<div style="width: 100%;float: left;position: relative;padding-left:auto;padding-right: auto;margin-bottom: 15px;margin-top: 10px">
	 			<div style="width: 20%;float: left;padding-left:2.5%;">专题名称:&nbsp;&nbsp;&nbsp;${requestScope.subject.subject_name}</div>
	 		</div>
	 		<div style="text-align: right;position: absolute;top: 0;right: 0">
						<input type="button" class="btn" id="editSubject" value="编辑">
						<input type="button"  class="btn" <c:if test="${requestScope.subject.subject_status == 1}">value="下线"</c:if> <c:if test="${requestScope.subject.subject_status == 0}">value="上线"</c:if>  onclick="changeOnLine('${requestScope.subject.subject_name}','${requestScope.subject.subject_id}','${requestScope.subject.subject_status}')">
				</div>
	 		<div style="width: 30%;height: 100px;float: left; position: relative">
	 			<div style="width: 35%;float: left;text-align: center;padding-top: 30px;">专题封面：</div>
	 			<div><img width="110" onload="javascript:DrawImage(this)" src="${requestScope.subject.subject_img}?tempid=<%=Math.random() %>"/></div>
	 		</div>
			<div style="width: 70%;height: 50px;float: left;margin-left: -8%;position: relative;">
				<div style="width: 33%;float: left;">当前状态：<span class="textShow"><c:if test="${requestScope.subject.subject_status == 1}" var="online">上线</c:if ><c:if test="${!online}">下线</c:if></span></div>
				<div style="width: 33%;float: left;margin-left: -10%;">上线时间：<span class="textShow"><fmt:formatDate value="${requestScope.subject.online_time}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
				<div style="width: 33%;float: left;">浏览量：<span class="textShow"> ${requestScope.subject.access_number}</span></div>
			</div>
			<div style="width: 70%;height: 50px;float: left;margin-left: -8%;">
				<div style="width: 33%;float: left;">专题位置:<span class="textShow">${requestScope.subject.subject_location}</span></div>
				<div style="width: 33%;float: left;margin-left: -10%;">首页位置：<span class="textShow"><c:if test="${requestScope.subject.index_location == 0}" var="is">首页不显示</c:if><c:if test="${!is}">${requestScope.subject.index_location}</c:if></span></div>
				<div style="width: 33%;float: left;">赞数量：<span class="textShow">${requestScope.subject.like_number}</span></div>
			</div>	 	
	 	</div>
  	 <div style="height: 40px;width: 100%;position: relative;margin-bottom: 20px;margin-top: 10px;">
  		 	<div style="top:0; left:0; position: absolute;">专题作品</div>
  	 		<div style="top:0; right:0; position: absolute;"><input id="toGalaryList" type="button"  class="btn" value="添加作品"/></div>
  	 </div>
  	 <div style="width: 100%;height:500px;margin-left: auto;margin-right: auto;overflow-x:hidden;overflow-y:auto;">
	  	 <c:forEach items="${selectGalary}" var="galary">
	  	 	<div style="width: 20%;  height:40%; float: left;margin-right: 5%;margin-left: 5%; margin-top: 6%;">
	  	 		<div style="left:0; width:100%;height:20px; bottom:0px;font-size:16px;color:#5B5B5B;text-align:center;">${galary.subjectName}</div>
	  	 		<div style="width: 100%; height: 110%;float: left; position: relative;">
	  	 			<img class="galaryCover" id="${galary.galaryId}"    src="${galary.galaryCover}" onload="javascript:DrawImage2(this)" title="点击查看作品集详情">
	  	 			<div style=" position:absolute; right:0; top:0px;">
	  	 			<img title="删除" style="width:16px;height:16px;cursor: pointer;" src="${pageContext.request.contextPath}/images/t03.png" onclick="deleteGalary('${galary.galaryId}')"/>
					</div>
	  	 			<div style=" position:absolute; right:0; bottom:0px; background-color:transparent; color:#404040; filter:alpha(opacity=0.2);-moz-opacity:10; opacity:10;">
	  	 			 <c:if test="${empty  galary.price}">
	  	 			   私人订制
	  	 			 </c:if>
	  	 			 <c:if test="${not empty  galary.price}">
	  	 			  ${galary.price}/元
	  	 			 </c:if>
	  	 			
	  	 			</div>
	  	 			<div style=" position:absolute; left:0; bottom:0px;">
	  	 				<div id="location" style="float: left;width:9%;text-align: left; margin-top: 5%;">
	  	 					<select class="locationIndex" id="${galary.subjectGalaryLocation}" use="${galary.galaryId}">
	  	 						<c:forEach begin="1" end="${maxLocation}" var="locationIndex">
	  	 							<option value="${locationIndex}" <c:if test="${galary.subjectGalaryLocation == locationIndex}">selected</c:if> >${locationIndex}</option>
	  	 						</c:forEach>
		  	 				</select>
		  	 			</div>
	  	 			</div>
	  	 		</div>
	  	 		
	  	 		<div style="height:20%;width:100%; color:#5B5B5B;font-size:10px;">
		  			<span style="display:block; float:left; width:25%;"><img src="${pageContext.request.contextPath}/images/camera.png"   width="15px" height="15px"/>${galary.nickName}</span>
		  			<span style="display:block; float:left; width:25%;"><img width="13px" height="13px" src="${pageContext.request.contextPath}/images/like.jpg" />${galary.likeNumber}</span>
		  			<span style="display:block; float:left; width:25%;"><img src="${pageContext.request.contextPath}/images/view.png"  width="20px" height="15px"/>${galary.viewNumber}</span>
		  			<span style="display:block; float:left; width:25%;"><img src="${pageContext.request.contextPath}/images/comment.png"  width="20px" height="15px"/>${galary.commentNumber}</span>
	  			 </div>
	  		</div>
  		</c:forEach>
  	 </div>
  </body>
</html>
