<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <base href="<%=basePath%>">
<title>菜单页</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/select.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/jquery.autocomplete.css" rel="stylesheet" type="text/css"  />
<link href="<%=request.getContextPath()%>/css/jquery.Jcrop.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/showLoading.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/select-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/editor/kindeditor.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath()%>/jquery/jquert.custom.progressbar.js">
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.Jcrop.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.showLoading.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>
 <style>
   #label1{
    left:50px;
    width:1000px; 
  }
    #label2{
    left:50px;
    width:1000px; 
  }
  #twoDIV{
    margin-left:50px;
    width:100%; 
  }
  #textTable{
     margin-left:50px; 
     width:400px;
     border:0px;  
   }
  #firstDIV{ width:100%; clear:both; overflow:hidden;}
  #subjectDiv{ width:100%; float:left;}
  #buttonDiv{ width:40%;  margin-left:850px;}
  #descDiv{
    margin-left:50px;
  }
  #commentDiv{
   margin-left:50px;
   }
   .introDiv{
    width: 500px;
    height: 30px;   
   }
   #galaryCover img.thisclass{border:1px solid red;}
   #MyImg {
  background-color:#E5EFF8;
        border:5px solid rgba(0,0,0, 0.4);
        height:140%;
        margin-left:4%;
        padding:1px;
        position:absolute;   
        width:90%;  
        display:none;  
        margin-top:2%;    
        z-index:2;  
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
   width: 400px;
   height: 300px;
   overflow: hidden;
 }
   
 </style>
  <script type="text/javascript">
	//选中作品集封面  	
	 var jcrop_api;
	function selectImg(photoId){
		$("img").removeClass("thisclass");
		$("#"+photoId).addClass("thisclass");
		var photoSrc = $("#"+photoId).attr("src");
		$("#galaryCover").attr("value",photoSrc);
	}
	
	function backToDetail(galleryID){
      var jsp="gallaryJSP";
		location.href="galleryManAction_toEdit.action?galaryId="+galleryID+"&jsp="+jsp;
	}
	
	
	var flag=false;
function DrawImage(ImgD){
var image=new Image();
var iwidth = 180;            //定义允许图片宽度，当宽度大于这个值时等比例缩小
var iheight = 120;            //定义允许图片高度，当宽度大于这个值时等比例缩小
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

 function selectCover(obj){
     $("#MyImg").show(); 
     $("#label1").css("display","none"); 
     $("#label2").css("display","none"); 
     /* $("selectId").val(obj.id); */
    /* alert(jcrop_api); */
    //alert(111);
    if(jcrop_api){
     jcrop_api.destroy();
   
     } 
    var coverId=obj.id;
    //alert(coverId);
    $("#selectId").val(obj.id);
    var coverSrc=obj.src;
    $("#cutImage").attr("src",coverSrc);
	$("#previewId").attr("src",coverSrc);
	$("#hiddenPara").val(coverSrc);
	var $cutImg=$("#cutImage");
				
				$cutImg.attr("onload","AutoResizeImage(600,0,this)");
				
	setTimeout(function(){             				
   jcrop_api =$.Jcrop("#cutImage",{
			     //aspectRatio:1,
			   /*  setSelect: [80,50,380,400], //setSelect是Jcrop插件内部已定义的运动方法
			    onSelect: updateCoords */			    
			     onChange:showCoords,
                 aspectRatio : 1.36,
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
  		
  
  }
function showCoords(obj){
   $("#x").val(obj.x);
   $("#y").val(obj.y);
   $("#w").val(obj.w);
   $("#h").val(obj.h);
   if(parseInt(obj.w)>0){
     //计算预览区域图片缩放的比例，通过计算显示区域的宽度(与高度)与剪裁的宽度(与高度)之比得到   
                var rx = 400 / obj.w;  
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
   function nothing(e){
			e.stopPropagation();
			e.preventDefault();
			return false;
		};
  function submitCutImg(){
   $("#MyImg").showLoading();
   var x= $("#x").val();
   var y= $("#y").val();
   var w= $("#w").val();
   var h= $("#h").val();
   var divw=$("#forCut").css("width");
   //alert(divw);
   var divh=$("#forCut").css("height");
   //alert(divh);
   var coverId= $("#selectId").val();
   //alert(coverId);
   var option = {
			type:"post",			
			dataType:"text",
			data:{
				x:x,y:y,w:w,h:h,divw:divw,divh:divh,coverId:coverId
			},
			success:function(responseText){
			//alert(responseText);
			var jsonObj = $.parseJSON(responseText);
			var realPath= jsonObj.coverName;
			   //alert(realPath);
			   $("#newCover").val(realPath);
			   $("#coverPhotoId").val(coverId);
               $("#MyImg").hideLoading();
               $("#label1").show(); 
               $("#label2").show();
               $("#MyImg").hide();
                
				
			},
			error:function(){
			$("#label1").show(); 
            $("#label2").show();
			$("#MyImg").hideLoading();
			$("#MyImg").hide();
				alert("系统错误");
			}
	};
  $("#Form2").ajaxSubmit(option);
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

 function cancalCut(){
   /*   alert(jcrop_api); */
      if(jcrop_api){
     jcrop_api.destroy();
   
     } 
     $("#MyImg").hide();
     return nothing(e);
 }
      function deletePhotoFromDetail(photoid){
   if (confirm("确定要删除此照片吗?")) {
      var photoclass =$("#"+photoid).attr("class");
         if(photoclass=="thisclass"){
            alert("删除此照片需要重新选择封面");
         }    	
      $.ajax({
             url:"deletePhotoById.action",
			type:"post",
			dataType:"json",
			data:{
				photoid:photoid
			},
			success:function(responseText){ 
			//alert(1111);
				 location.replace(location.href);
			},
			error:function(){
				alert("系统错误");
			}
			
			
         });
   
   
   }
  }
  </script> 
 
 
 </head>
  <body>
    <div id="MyImg"> 
    <div class="formtitle" style="width:950px;margin:0 auto;margin-top: 25px;"><span>裁剪封面</span></div> 
     <form id="Form2" name="Form2" method="post" action="<%=request.getContextPath()%>/selectCover2.action" enctype="multipart/form-data"> 
       <input type="hidden" id="x" name="x">
	   <input type="hidden" id="y" name="y">
	   <input type="hidden" id="w" name="w">
	   <input type="hidden" id="h" name="h">
	   <input type="hidden" id="selectId" name="coverId" value="">
      
     <div id="imginfo" style="margin-top:15px; width:1200px; height:800px;">
      <div id="img_cut_div" style="float:left; width:750px;height:700px;">
      <div id="forCut" style="margin-left:80px; " >
        <img id="cutImage"  src=""  width="0" height="0"/>
         <input type="hidden" id="hiddenPara" value=""/>
        </div>
       </div>
      <div id="preview-pane">
     <div class="preview-container">
       <img src="" id="previewId" class="jcrop-preview" alt="Preview" />   
     </div>
      </div>
      
      <div>
      <div style="margin-left:70%; margin-top: 20%">
     <input name="" type="button"  class="btn" value="确认"  onclick="submitCutImg()" class="dfinput"/></div>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <div style="margin-left:70%"><input name="" type="button" class="btn" onclick="cancalCut()" value="取消" class="dfinput" />
    </div>
     </div>
      </div>

     </form>
  
      </div>
  
  
  
  
  
  
  
  
   <form id="galary" name="galary" method="post" action="<%=request.getContextPath()%>/galleryManAction_saveEdit.action">
   	 <input type='hidden' id="galleryid" name="galaryId"  value="<s:property value='#request.gallery.galaryId'/>"/>
   	 <input type='hidden' id="newCover" name="galaryCover" value="<s:property value='#request.gallery.galaryCover'/>"/>
   	 <input type='hidden' id="userId" name="userId" value="<s:property value='#request.gallery.lpUser.userId'/>"/>
   	 <input type='hidden' id="gallaryJSP" name="gallaryJSP" value="<s:property value='#request.gallaryJSP'/>"/>
   	 <input type="hidden" id="coverPhotoId" name="coverPhotoId" value="">
   	<!-- <div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">作品集列表</a></li>
    <li><a href="#">作品集详情</a></li>
    <li><a href="#">作品集编辑</a></li>
    </ul>
    </div>
   	  -->
   <div id="label1" class="formtitle"><span><a href="javascript:void(0)" >作品集照片编辑 ( 编辑完成，请点击最底下的“提交”按钮，保存数据 )</a></span></div>
      <input type="hidden" id="jsp" name="JSP" value="YES"/>
     <div id="firstDIV">
       <table id="textTable" class="tablelist" style="width:60%;height: 50%">
       	<li style="margin-left: 5%"><label>作品主题：</label>
       	 <br/>
       	 <br/>
       	<input type="text" maxlength="60" class="dfinput" style="width:90%" id="subjectName" name="subjectName" value="<s:property value='#request.gallery.subjectName'/>"/></li>
       	<br/>
       	<li style="margin-left: 5%"><label>发布时间：</label>
       	 <br/>
       	 <br/>
       	<s:date name="#request.gallery.creatTime" format="yyyy-MM-dd HH:mm:ss"/></li>
       	<br/>
       	<li style="margin-left: 5%"><label>作品描述：</label>
       	<br/>
       	 <br/>
       	<textarea class="dfinput" style="width:90%;height: 60%" rows="5" id="galaryDesc" name="galaryDesc" onpropertychange="if(value.length>300) value=value.substr(0,300)"><s:property value='#request.gallery.galaryDesc'/></textarea></li>
       </table>
        </div>
      <div id="label2" class="formtitle"><span><a href="javascript:void(0)" >照片列表(若需要选择一幅作为封面，请在其中一幅上单击)</a></span></div>  
     <!-- 照片列表 -->
     <div id="twoDIV">
        <ul class="imglist" style="width: 96%;">
		<c:if test="${listphoto!=null&& fn:length(listphoto)>0}">
	       <c:forEach items="${listphoto}" var="item">
		    <li id="galaryCover" style="width: 180px;height: 150px;" >
		     <div onclick="selectImg('${item.photoId}');">
		    <span style="text-align: center;"><img id="${item.photoId}"   <c:if test="${item.galaryConver == true}">class="thisclass"</c:if> width="150px" src="${item.photoSrc}" onload="javascript:DrawImage(this)" onclick="selectCover(this)"/></span>
		    <input type="hidden" name="reallyPath" value="${item.photoSrc}"/> 
		    </div>
	       <div ><p><a href="javascript:deletePhotoFromDetail('${item.photoId}');">删除</a></p></div>
		    </li>
		   </c:forEach>  
	 	</c:if> 
     	</ul>      
     </div> 
     <li style="margin-left: 10%;"><input name="submit"  type="submit" class="btn" value="提交" class="dfinput" />
			<!-- <input name="reset" type="reset" class="btn" value="重置" class="dfinput" /> -->
			<input name="back" type="button" class="btn" value="返回" class="dfinput" onclick="backToDetail('<s:property value="#request.gallery.galaryId"/>');"/></li>
   </form>		
  </body>
</html>
