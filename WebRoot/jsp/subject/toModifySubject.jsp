<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>专题编辑</title>
        <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
        <link href="<%=request.getContextPath()%>/css/jquery.Jcrop.css" rel="stylesheet" type="text/css" />
            <link href="<%=request.getContextPath()%>/css/showLoading.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.Jcrop.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/select-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/editor/kindeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.showLoading.min.js"></script>
<style type="text/css">
#localImag
{
	text-align: center;
	width: 200px;
	height: 200px;
	margin-left: 10%;
}

.jcrop-holder #preview-pane {
   display: block;
    z-index: 2000; 
   top: 10px;
     right: -280px; 
   padding: 6px;
   border: 1px rgba(0,0,0,.4) solid;
  /* background-color: white;*/

  -webkit-border-radius: 6px;
   -moz-border-radius: 6px;
   border-radius: 6px;

  -webkit-box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
   -moz-box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
   box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
 }

#preview-pane .preview-container {
   width: 300px;
   height: 300px;
   overflow: hidden;
 }
 
  #MyImg {
        background-color:#fff;
        border:5px solid rgba(0,0,0, 0.4);
        height:120%;
        left:50%;
        padding:1px; 
        width:90%;  
        position:absolute;  
        display:none;  
        left:8%;  
        top:14%;  
        z-index:2;  
  
 
 }
    
 
</style>
<script type="text/javascript">
		function backToList()
		{
			window.location.href="${pageContext.request.contextPath}/subject_subjectList.action";
		}
	$(function(){
		 var jcrop_api;
			
		$("#Form1").submit(function(){
		var likeNumber = $("#likeNumber").val();
	    var viewNumber = $("#viewNumber").val();
		if($("#subject_name").val() =="")
		{
			if($("#nameStyle").length == 0)
			{
				$("#subject_name").after("<font id='nameStyle' style='color:red'>专题名称不能为空</font>");
			}
			return false;		
		}else
		{
			$("#nameStyle").remove();
		}
		
		
			var testNum =/^\d+(\.\d+)?$/; 
		  	if(viewNumber!="" && !testNum.test(viewNumber)){
		  		alert("浏览量必须为数字");	
		  		return false;
		  	}
		  	if(likeNumber!="" && !testNum.test(likeNumber)){
		  		alert("浏览量必须为数字");	
		  		return false;
		  	}
		    if(viewNumber>999999999){
		  	    alert("浏览量不要超过999999999");	
		  		return false;
		  	}
		  	if(likeNumber>999999999){
		  	    alert("赞量不要超过999999999");	
		  		return false;
		  	}      
		
		
	    var file = $("#subjectimg")
        file.after(file.clone().val(""));
        file.remove(); 
		return true;
		});
		/**
		$("#subjectimg").change(function(){
			var docObj=document.getElementById("subjectimg");
			var imgObjPreview=document.getElementById("preview");
			var localImagId = document.getElementById("localImag");
			if(docObj.files && docObj.files[0]){
			//火狐下，直接设img属性
			localImagId.style.display ='block';
			imgObjPreview.style.display = 'block';
			imgObjPreview.style.width = '100%';
			imgObjPreview.style.height = '100%';
			//imgObjPreview.src = docObj.files[0].getAsDataURL();
			//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
			}else{
			//IE下，使用滤镜
			docObj.select();
			var imgSrc = document.selection.createRange().text;
			localImagId = document.getElementById("localImag");
			//必须设置初始大小
			//localImagId.style.width = "250px";
			//localImagId.style.height = "200px";
			//图片异常的捕捉，防止用户修改后缀来伪造图片
			try{
			localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
			}catch(e){
			alert("您上传的图片格式不正确，请重新选择!");
			return false;
			}
			localImagId.style.display = 'block';
			imgObjPreview.style.display = 'none';
			document.selection.empty();
			}
			return true; 
		}); */
				$("#subjectimg").change(function(){
		   $("body").showLoading();
			 var option = {
			url:"<%=request.getContextPath()%>/subjectImgUpload.action",//如果option中存在url那么就提交到url上，如果不存在url，提交到表单的action上
			type:"post",
			dataType:"text",
			data:{
				subjectimg:"subjectimg"
			},
			success:function(responseText){
			  $("body").hideLoading();
			    //$("#warn6").remove();
				 //把json字符串转换成json对象
				 //alert(responseText);
				/* $("#MyImg").show(); */
				//showCutImg ();
				 if(jcrop_api){
                    jcrop_api.destroy();
                    } 
				var jsonObj = $.parseJSON(responseText);
				var number= (new Date()).valueOf();
				var realPath=jsonObj.realPath+"?"+number;
				 //alert(realPath)
				/*  $("#cutImage").attr("src",realPath); */
				 $("#coverImg").attr("src",realPath);
				/* $("#previewId").attr("src",realPath); */
			/* 	$("#headImage").val(jsonObj.relativePath); */
				$("#imagePath").val(jsonObj.relativePath); 
				$("#coverPath").val(jsonObj.relativePath);
					
			
			},
			error:function(){
				alert("系统错误");
			}
	};
	//通过ajax方式提交表单，页面没跳转
	$("#Form1").ajaxSubmit(option);
      });
      
     $("#coverImg").click(function (){
      if($("#imagePath").val()==""){
      
       return false;
      }
     
	  $("#MyImg").show();
	  var viewPath=$("#coverImg").attr("src");
	   $("#MyImg").show();
      if(jcrop_api){
     jcrop_api.destroy();
         } 
      $("#cutImage").attr("src",viewPath);   
      $("#previewId").attr("src",viewPath);
   var $cutImg=$("#cutImage");
   $cutImg.attr("onload","AutoResizeImage(600,0,this)");
   	setTimeout(function(){             				
   jcrop_api =$.Jcrop("#cutImage",{
			     //aspectRatio:1,
			   /*  setSelect: [80,50,380,400], //setSelect是Jcrop插件内部已定义的运动方法
			    onSelect: updateCoords */			    
			     onChange:showCoords,
                aspectRatio : 1,
                 sideHandles: false,
                 cornerHandles:true, 
                 
		      },
		      function(){
       // Use the API to get the real image size
       var bounds = this.getBounds();
       boundx = bounds[0];
       boundy = bounds[1];
       // Store the API in the jcrop_api variable
       jcrop_api = this;
      // Move the preview into the jcrop container for css positioning
       $preview.appendTo(jcrop_api.ui.holder);
     }  
     );		

				
			},1000);
   
	
	
	})
	
	 
      
      
      
      
      
      
      
      
    	 $("#submitCutImg").click(function(){
    $("#MyImg").showLoading();
   var x= $("#x").val();
   var y= $("#y").val();
   var w= $("#w").val();
   var h= $("#h").val();
   var imagePath= $("#imagePath").val();
    var divw=$("#forCut").css("width");
   //alert(divw);
   var divh=$("#forCut").css("height"); 
   //alert(divh);
   //alert(x);
   var option = {
			type:"post",			
			dataType:"text",
			data:{
				x:x,y:y,w:w,h:h,imagePath:imagePath,divw:divw,divh:divh
			},
			success:function(responseText){
			     if(jcrop_api){
     jcrop_api.destroy();
     }
      $("#MyImg").hideLoading(); 
			    $("#MyImg").hide();
			    //$("#localImag").show();
				 var jsonObj = $.parseJSON(responseText);
				var number=(new Date()).valueOf();
				var realPath=jsonObj.realPath;
				//alert(realPath);
			   $("#coverImg").attr("src",realPath+"?"+number);
			    //alert(jsonObj.relativePath);
			  /*   $("#headImage").val(jsonObj.relativePath); */
			   /*    $("#warning6").remove(); */
			},
			error:function(){
			 $("#MyImg").hideLoading(); 
				alert("系统错误");
			}
	};
  $("#Form2").ajaxSubmit(option);
  
  
  
  });
  
    
	
      
    $("#cancalCut").click(function(e){
     //alert(jcrop_api);
     if(jcrop_api){
     jcrop_api.destroy();
     }
     var $cutimg=$("#cutImage");
     var $previewId=$("#previewId");
     //alert($cutimg.attr("src"));
     if($cutimg.attr("src")!=""){
     $cutimg.attr("src","");
     }if($previewId.attr("src")!=""){
        $previewId.attr("src","");
     }
               
               $("#MyImg").hide();
 
 
 });  
      
      
      
      
      
      });
      
		 function showCoords(obj){
		        $("#x").val(obj.x);
		        $("#y").val(obj.y);
		        $("#w").val(obj.w);
		        $("#h").val(obj.h);
		    }
		    
		    
	function AutoResizeImage(maxWidth, maxHeight, objImg) {
    var img = new Image();
    img.src = objImg.src;
    var hRatio;
    var wRatio;
    var Ratio = 1;
    var w = img.width;
    var h = img.height;
    wRatio = maxWidth / w;
    hRatio = maxHeight / h;
    if (maxWidth == 0 && maxHeight == 0) {
        Ratio = 1;
    } else if (maxWidth == 0) { //
        if (hRatio < 1) Ratio = hRatio;
    } else if (maxHeight == 0) {
        if (wRatio < 1) Ratio = wRatio;
    } else if (wRatio < 1 || hRatio < 1) {
        Ratio = (wRatio <= hRatio ? wRatio: hRatio);
    }
    if (Ratio < 1) {
        w = w * Ratio;
        h = h * Ratio;
    }
    
    $("#forCut").css("width",w);
    $("#forCut").css("height",h);
    //objImg.style.display='inline-block';
    objImg.height = h;
    objImg.width = w;
}	   

function showCoords(obj){
   $("#x").val(obj.x);
   $("#y").val(obj.y);
   $("#w").val(obj.w);
   $("#h").val(obj.h);
   if(parseInt(obj.w)>0){
     //计算预览区域图片缩放的比例，通过计算显示区域的宽度(与高度)与剪裁的宽度(与高度)之比得到   
                var rx = 300 / obj.w;  
                var ry = 300 / obj.h;  
                 //通过比例值控制图片的样式与显示   
                $('#preview-pane .preview-container img').css( {  
                   width : Math.round(rx * $("#cutImage").width()) + "px", //预览图片宽度为计算比例值与原图片宽度的乘积  
                    height : Math.round(ry* $("#cutImage").height()) + "px", //预览图片高度为计算比例值与原图片高度的乘积  
                     marginLeft : "-" + Math.round(rx * obj.x) + "px",  
                     marginTop : "-" + Math.round(ry * obj.y) + "px"  
                }); 
                
      }
   }
   ;
	</script>
  </head>
  
  <body>
   <div class="formbody">
    <div class="formtitle"><span>专题编辑</span><span style="float: left;margin-left: 1110px;height: 30px"><img height="30px" width="30px" onclick="backToList()" src="${pageContext.request.contextPath}/images/test/16.png"></span></div>
     <ul class="forminfo">
   	<form id="Form1" name="Form1" method="post"  enctype="multipart/form-data" action="${pageContext.request.contextPath}/subject_modifySubject.action">
   			<input type="hidden"  id="subject_id" name="subject_id" value="${requestScope.subject.subject_id}">
   			<%-- <li><label>当前序号：</label><input name="seq_number" type="text" class="dfinput" value="${requestScope.subject.seq_number}" /></li> --%>
    		<li><label>专题名称：</label><input id="subject_name" name="subject_name" type="text" class="dfinput" value="${requestScope.subject.subject_name}" /></li>
    		<div id="localImag"><img src="${subject.subject_img}?tempid=<%=Math.random() %>" id="coverImg" height="150px">
    		 <div>(点击图片剪裁封面)</div>
    		</div>
    		<li><label><input type="hidden" name="subject_img" value="${subject.subject_img}"></label></li>
    			<!-- <input type="hidden" id="x" name="x">
	         <input type="hidden" id="y" name="y">
	         <input type="hidden" id="w" name="w">
	         <input type="hidden" id="h" name="h"> -->
             <input type="hidden" id="coverPath" name="coverPath" value=""/> 
    		<li><label>封面更改：</label><input id="subjectimg" name="subjectimg"  type="file" onchange="uploadImg()" class="dfinput" /></li>
    		<%-- <li><label>作品集选取：</label>
    		<div  style="width:350px;height:200px;float:left; overflow:auto;">
    		<c:forEach items="${sessionScope.galleryList}" var="gallery" >
    			<div><span><img src="${pageContext.request.contextPath}/images/3)J7X1{SNS{_G_2@BB}ONVW.gif"/></span></div>
    			<input type="checkbox" name="select"   <c:forEach items="${requestScope.detailList}" var="SubjectDetail"><c:if test="${SubjectDetail.gallery_id == gallery.gallery_id}"> checked</c:if> </c:forEach>   value="${gallery.gallery_id}" onchange="deleteGallery()">
    		</c:forEach>
    		</div>
    		</li> --%>
    		<li><label>创建时间：</label><input name="create_time" type="text" class="dfinput" value="${requestScope.subject.create_time}" readonly="readonly"/></li>
    		<li><label>创建人   ：</label><input name="create_user_name" type="text" class="dfinput"  value="${requestScope.subject.create_user_name}"  readonly="readonly"/></li>
    		<li><label>修改人   ：</label><input name="modify_user_name" type="text" class="dfinput" value="${sessionScope.admin.userName}"  readonly="readonly"/></li>
    		<%-- <li><label>修改时间：</label><input name="modify_time" type="text" class="dfinput" value="${requestScope.subject.modify_time}"  readonly="readonly"/></li> --%>
    		<li><label>作品数量：</label><input name="gallery_number" type="text" class="dfinput" value="${requestScope.subject.gallery_number}"  readonly="readonly"/></li>
    		<li><label>访问量   ：</label><input id="viewNumber" name="access_number" type="text" class="dfinput"  value="${requestScope.subject.access_number}" /></li>
    		<li><label>点赞量   ：</label><cite><input id="likeNumber" name="like_number" type="text"  class="dfinput" value="${requestScope.subject.like_number}" /></li>
   			<li><label>首页位置：</label>
   				<%--<input id="index_location" name="index_location" type="text" class="dfinput" value="${requestScope.subject.index_location}" />--%>
   				<select id="index_location" name="index_location" class="dfinput">
   					<option value="0" selected >不显示</option>
					<c:forEach begin="1" end="99" var="index"><option value="${index}" <c:if test="${requestScope.subject.index_location == index}"> selected</c:if> >${index}</option></c:forEach>
				</select>
   			</li>
   			<li>
   				<label>专题位置：</label>
   				<%--<input  id="subject_location" name="subject_location" type="text" class="dfinput" value="${requestScope.subject.subject_location}" />--%>
   				<select id="subject_location" name="subject_location" class="dfinput">
					<c:forEach begin="1" end="99" var="s_location">
						<option value="${s_location}"<c:if test="${requestScope.subject.subject_location == s_location}">selected</c:if> >${s_location}</option>					
					</c:forEach>
				</select>
   			</li>
   			<li><label>是否上线：</label>
   										<input name="subject_status" type="radio"  value="1"  <c:if test="${requestScope.subject.subject_status == 1}" var="online">checked="checked"</c:if>/>上线
   										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   										<input name="subject_status" type="radio"  value="0" <c:if test="${!online}">checked="checked"</c:if> />下线
   										</li>
			<li><label>上线时间：</label><input name=online_time type="text" class="dfinput" value="${requestScope.subject.online_time}"  readonly="readonly"/></li>   										
    		<li><input name=""  type="submit" class="btn" value="提交" class="dfinput"/>
    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    		<input name="" type="reset" class="btn" value="重置" class="dfinput" />
    		</li>
    		</form>
    		</div>
    		
    		    <div id="MyImg"> 
    <div class="formtitle" style="width:790px;margin:0 auto;margin-top: 25px;"><span>封面剪裁</span></div> 
     <form id="Form2" name="Form2" method="post" action="<%=request.getContextPath()%>/cutSubjectImage.action" enctype="multipart/form-data"> 
       <input type="hidden" id="x" name="x">
	   <input type="hidden" id="y" name="y">
	   <input type="hidden" id="w" name="w">
	   <input type="hidden" id="h" name="h">
       <input type="hidden" id="imagePath" name="imagePath" value="">
      
     <div id="imginfo" style="margin-top:50px; width:1200px; height:800px;">
        <div id="img_cut_div" style="float:left; width:750px; height:700px;">
      <div id="forCut" style="margin-left:80px; ">
        <img id="cutImage" src="" src=""  width="0" height="0"/>
        </div>
        </div>
      <div id="preview-pane" >
     <div class="preview-container">
       <img src="" id="previewId" class="jcrop-preview" alt="Preview" />   
     </div>
      </div>
       <div>
      <div style="margin-left:70%; margin-top: 20%">
     <input name="" type="button"  class="btn" value="确认"  id="submitCutImg" class="dfinput"/></div>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <div style="margin-left:70%"><input name="" type="button" class="btn" id="cancalCut" value="取消" class="dfinput" />
    </div>
     </div>
      </div>
   
     </form>   
    </div>
  </body>
</html>
