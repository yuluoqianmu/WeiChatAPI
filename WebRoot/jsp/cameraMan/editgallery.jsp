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
<link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/select.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/jquery.autocomplete.css" rel="stylesheet" type="text/css"  />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.lightbox.packed.css" type="text/css" media="screen" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/select-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/editor/kindeditor.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath()%>/jquery/jquert.custom.progressbar.js">
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.lightbox.js"></script>
 <style>
  #label1{
    left:50px;
    width:1000px; 
  }
  #commentTable{
    
     width:900px; 
  }
  #twoDIV{
    margin-left:50px;
    width:1100px; 
  }
  #textTable{
     margin-left:50px; 
     width:400px;
     border:0px;  
   }
  #firstDIV{ width:100%; clear:both; overflow:hidden;}
  #subjectDiv{ width:50%; float:left;}
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
    #div1 {
        background-color:#F0F5F7;
        border:5px solid rgba(0,0,0, 0.4);
        height:380px;
        left:50%;
        padding:1px; 
        width:540px;  
        position:absolute;  
        display:none;  
        left:0;  
        top:0;  
        z-index:2; 
 }
 #div1 p {
margin:0 0 12px;
height:24px;
line-height:24px;
background:#CCCCCC;
}
#div1 p.close {
text-align:right;
padding-right:10px;
}
#div1 p.close a {
 color:#fff;
 text-decoration:none;
} 
/*遮罩**/
     #mask{  
        display:none;  
        position:absolute;  
        background-color:black;  
        filter:alpha(opactiy=20);  
        opacity:0.2;  
        z-index:1;  
    }
    
/**--------上传照片----------*/    
.add_img{
	height:24px;
	float:left;
	display:inline-block;
	background-image:url("images/t01.png");
	width:24px;
	z-index:0;
	text-align:left;
	position:relative;
    margin-left:20px;
    }
    .add_img_file{
	height:24px;
	width:24px;
	right:0;
	top:0;
	opacity:0;
    filter:alpha(opacity=0);
    cursor:pointer;
    z-index:2
   }
  .imgsContainer {
   border-top-width:1px;border-bottom-width:1px;border-left-width:0px; border-right-width:0px;border-color:#b6b6b6;border-style:solid;width:100%;height:80%;overflow:auto;
   }
      .regfont{
    font-family:"\5B8B\4F53";
    font-size:12px;
   
   }
/*浮动 **/
   #uploadImgTable {
        background-color:#fff;
        border:5px solid rgba(0,0,0, 0.4);
        height:100%;
        left:50%;
        padding:1px; 
        width:800px;  
        position:absolute;  
        display:none;  
        left:0;  
        top:0;  
        z-index:2;  
 }
 
 #checkDiv{
        height:80px;
        width:900px;  
        position:absolute;  
        left:50px;  
        top:95px;
        height:300px;  
 }
 #selectDiv{ 
        height:200px;
        width:900px;  
        position:absolute;  
        left:50px;  
        top:300px;
        height:300px; 
 
 }
 /*遮罩**/
     #mask{  
        display:none;  
        position:absolute;  
        background-color:black;  
        filter:alpha(opactiy=20);  
        opacity:0.2;  
        z-index:1;  
    }
#uploadImgTable p {
margin:0 0 12px;
height:24px;
line-height:24px;
background:#CCCCCC;
}
#uploadImgTable p.close {
text-align:right;
padding-right:10px;
}
#uploadImgTable p.close a {
 color:#fff;
 text-decoration:none;
} 
 #userId{
  margin-left:50px;
  margin-top:260px;
  position:absolute;
 
 }


#personDesc{
  margin-left:60px;
  margin-top:460px;
  position:absolute;

}

#uploadOne{
position:relative;
margin-left:40px;
}
#submitForm{

 margin-left:100px;
 margin-top:500px;
 position:absolute;

}

.Stylelist{
clear:both; 
overflow:hidden; 
margin-bottom:20px; 
margin-left:40px;
}
.Stylelist li{width:100px; 
border:solid 1px #fff;
height:20px;
float:left; 
margin-right:8px; 
margin-bottom:10px; 
cursor:pointer;
}
.part{
text-align:left;
width:30%;
height:200px;
margin-top:20px;
margin-left:20px;
float:left;
position:relative;
border: 1px solid #b6b6b6;
}
.part_img{
width:100%;
height:100%;
z-index:1;
position:absolute;
float:left;
}
._close_part_img{
   z-index:2;
   cursor:pointer;
   float:left;
   position: absolute;
}
/**--------上传照片----------*/    
    
/**----------回复----------*/  
	/*浮动 **/
#addReply {
background-color:#F0F5F7;
    border:5px solid rgba(0,0,0, 0.4);
    height:480px;
    left:50%;
    padding:1px; 
    width:450px;  
    position:absolute;  
    display:none;  
    left:0;  
    top:0;  
    z-index:2;  
}
#addReply p {
margin:0 0 12px;
height:24px;
line-height:24px;
background:#CCCCCC;
}
#addReply p.close {
text-align:right;
padding-right:10px;
}
#addReply p.close a {
 color:#fff;
 text-decoration:none;
}     
/**----------修改浏览数,赞数量----------*/  
	/*浮动 **/
#updateNumberTable {
background-color:#F0F5F7;
    border:5px solid rgba(0,0,0, 0.4);
    height:480px;
    left:50%;
    padding:1px; 
    width:450px;  
    position:absolute;  
    display:none;  
    left:0;  
    top:0;  
    z-index:2;  
}
#updateNumberTable p {
margin:0 0 12px;
height:24px;
line-height:24px;
background:#CCCCCC;
}
#updateNumberTable p.close {
text-align:right;
padding-right:10px;
}
#updateNumberTable p.close a {
 color:#fff;
 text-decoration:none;
}     
 </style>
  <script type="text/javascript">
     var divNum=0;
     $(function () {  
        var $win = $(window),  
                $div = $('#div1'),  
                $mask = $('#mask'),  
                $close = $('#close'),  
                flag = false;  
      
      
       $("#addComment").click(function () {                  
            var clientH = $win.height(),  
                clientW = $win.width(),  
                divH = $div.height(),  
                divW = $div.width();  
            var t = (clientH - divH) / 2 + $win.scrollTop();  
            var l = (clientW - divW) / 2 + $win.scrollLeft();  
            $div.show(500).offset({ 'top': t , 'left': l });       
            $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
            flag = true;  
      
            $("#closeComment").click(function () {  
                flag = false;  
                $div.hide(500);  
                $mask.hide();  
            });  
      
      
            $(window).scroll(setMask).resize(setMask);  
      
            function setMask() {  
                if (flag) {  
                    $div.show(500).offset({ 'top': ($win.height() - $div.height()) / 2 + $win.scrollTop(), 'left': ($win.width() - $div.width()) / 2 + $win.scrollLeft() });  
                    $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
                } else {  
                    $div.hide(500);  
                    $mask.hide();  
                }  
            }  
        });
       
       
       /**-----------------*/
       var maxPart=9;
       var part=1;
       $(window).unload(function(){
    	  $.ajax({
    		  type:"post",
  			  url:"clearTempImages.action",
  			  async:false,
  			  dataType:"json",
  			  success:function(data){
  				  
  			  }
    	  })
      })
       $("._close_part_img").live("click",function(){
    	  $("#uploadImgTable").loading();
    	  var id=$(this).parent().attr("id");
    	  $.ajax({
    		  type:"post",
  			  url:"delImage.action",
  			  data:{id:id},
  			  async:true,
  			  dataType:"json",
  			  success:function(data){
  				$("#uploadImgTable").finishLoading();
  				  if(data.result==true){
  					 $("#"+id).remove();
  				 }else{
  					 alert("删除失败");
  					 setTimeout(function(){alert("删除失败");},300);
  				 }
  			 }
    	  })
      })
       $("#img_iframe").bind("load",function(){
    	  $("#uploadImgTable").finishLoading();
    	  var msgError=$(this).contents().find("span[id=result]");
    	  $(".add_img_file").val("");
    	  if(msgError.length==0){
    		  return;
		  }else if(msgError.text()=="success"){
		  var _div=$("<div id='part_"+part+"' class='part'></div>");
		  var closeImg=$("<img title='删除' class='_close_part_img' src='images/close.png'/>")
		  var _img=$("<img class='part_img' src='showImage?partNumber=part_"+part+"'/>");
		  $("#imgsContainer").append(_div);
		  $("#part_"+part).append(closeImg);
		  $("#part_"+part).append(_img);
		  part++;
		  }else{
			  var response=msgError.text().trim();
			  alert(response);
		  }
      });
       //上传图片，点击提交按钮
      $("#submitImage").bind("click",function(){
    	  /**获取相关参数*/
    	  var galaryId = "<s:property value='#request.gallery.galaryId'/>";
    	  if(galaryId == ""){
    		  alert("程序异常，请联系系统管理员");
    		  return;
    	  }
    	  /**进度条*/
    	  if($(".part").length==0){
    		  alert("请点击左上角处按扭添加图片");
    		  return;
    	  }
    	  $("#uploadImgTable").progressBar();
    	  $.ajax({
    		  type:"post",
  			  url:"persistentImage_updateDetail.action",
  			  async:true,
  			  data:{galaryId:galaryId},
  			  dataType:"json",
  			  success:function(data){
  				$("#uploadImgTable").finishProgressBar();
  				if(data.result==0){
  					$("#imgsContainer").empty();
  					setTimeout(function(){
  						alert("上传成功");
  						$("#close").trigger("click");
  						location.reload();
  					},300);
  				}else if(data.result==1){
  					alert("请点击左上角处按扭添加图片");
  				}else{
  					setTimeout(function(){
  						alert("上传失败");
  					},300);
  				}
  			  }
    	  });
      });
      $(".add_img_file").bind("change",function(){
    	  if($(this).val()==""){
    		  return;
    	  }
    	  if($(".part").length==maxPart){
    		  alert("图片数量不能超过"+maxPart+"张");
    		  return;
    	  }
    	  $("#uploadImgTable").loading();
    	  if($(".add_img").find("form").length<=0){
    		  var form=$("<form target='img_iframe' enctype='multipart/form-data' action='galleryManAction_uploadTmpImg.action' method='post'></form>");
    		  var param=$("<input name='partNumber' type='hidden' value='part_"+part+"'>");
    		  form.append(param);
    		  form.append($(".add_img_file"));
    		  $(".add_img").append(form);
    		  $(".add_img").find("form").submit();
    	  }else{
    		  $("input[name=partNumber]").val("part_"+part);
    		  $(".add_img").find("form").submit();
    	  }
      });
       $("#uploadImg").click(function () {                  
           var clientH = $(window).height();
           var clientW = $(window).width(); 
           var divH = $('#uploadImgTable').height();
           var divW = $('#uploadImgTable').width();  
           var t = (clientH - divH) / 2 + $(window).scrollTop();  
           var l = (clientW - divW) / 2 + $(window).scrollLeft();  
           $('#uploadImgTable').show(500).offset({ 'top': t , 'left': l });       
           $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $(window).scrollLeft(), top: $(window).scrollTop() });  
           flag = true;  
     
           $close.click(function () {  
               flag = false;  
               $('#uploadImgTable').hide(500);  
               $mask.hide();  
           });  
     
     
           $(window).scroll(setMask).resize(setMask);  
     
           function setMask() {  
               if (flag) {  
            	   $('#uploadImgTable').show(500).offset({ 'top': ($(window).height() - $('#uploadImgTable').height()) / 2 + $(window).scrollTop(), 'left': ($(window).width() - $('#uploadImgTable').width()) / 2 + $(window).scrollLeft() });  
                   $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $(window).scrollLeft(), top: $(window).scrollTop() });  
               } else {  
            	   $('#uploadImgTable').hide(500);  
                   $mask.hide();  
               }  
           }  
       });
       
	    $(".replyComment").click(function () { 
		       var clientH = $(window).height();
		       var clientW = $(window).width(); 
		       var  divH = $('#addReply').height();
		       var  divW = $('#addReply').width(); 
		       var t = (clientH - divH) / 2 + $(window).scrollTop();  
		       var l = (clientW - divW) / 2 + $(window).scrollLeft();  
		       $('#addReply').css('display', 'block').offset({ 'top': t , 'left': l });  
		       $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $(window).scrollLeft(), top: $(window).scrollTop() });  
		       flag = true;  
		       //设置评论的ID
		       $("#replayToId").attr("value",this.lang);
		       
		       $("#closeReply").click(function () {  
		           flag = false;
		           $('#addReply').hide();  
		           $mask.hide();  
		       });  
		       $(window).scroll(setMask).resize(setMask);  
		       function setMask() {  
		           if (flag) {  
		        	   $('#addReply').css('display', 'block').offset({ 'top': ($(window).height() - $('#addReply').height()) / 2 + $(window).scrollTop(), 'left': ($(window).width() - $('#addReply').width()) / 2 + $(window).scrollLeft() });  
		               $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $(window).scrollLeft(), top: $(window).scrollTop() });  
		           } else {
		        	   $('#addReply').hide();  
		               $mask.hide();  
		           }  
		       }  
		  });
	    
	    //修改浏览数,赞数
	    $("#updateNumber").click(function () { 
		       var clientH = $(window).height();
		       var clientW = $(window).width(); 
		       var  divH = $('#updateNumberTable').height();
		       var  divW = $('#updateNumberTable').width(); 
		       var t = (clientH - divH) / 2 + $(window).scrollTop();  
		       var l = (clientW - divW) / 2 + $(window).scrollLeft();  
		       $('#updateNumberTable').css('display', 'block').offset({ 'top': t , 'left': l });  
		       $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $(window).scrollLeft(), top: $(window).scrollTop() });  
		       flag = true;  
		       
		       $("#closeUpdateNumber").click(function () {  
		           flag = false;
		           $('#updateNumberTable').hide();  
		           $mask.hide();  
		       });  
		       $(window).scroll(setMask).resize(setMask);  
		       function setMask() {  
		           if (flag) {  
		        	   $('#updateNumberTable').css('display', 'block').offset({ 'top': ($(window).height() - $('#updateNumberTable').height()) / 2 + $(window).scrollTop(), 'left': ($(window).width() - $('#updateNumberTable').width()) / 2 + $(window).scrollLeft() });  
		               $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $(window).scrollLeft(), top: $(window).scrollTop() });  
		           } else {
		        	   $('#updateNumberTable').hide();  
		               $mask.hide();  
		           }  
		       }  
		  });
	    
	    //跳转到编辑页面
	    $("#toTrueEditGalary").click(function () {
	    	var galleryId=$("#galleryid").val();
	    	var jsp=$("#JSP").val();
		      location.href = "<%=request.getContextPath()%>/galleryManAction_toTrueEditGalary.action?galleryId="+galleryId+"&jsp="+jsp;
		  });
	    
	    
	    
    });
<%--     function addComment(){
     alert(1111);
     $("#Form1").submit();
     var galleryId=$("#galleryid").val();
     alert(galleryId);
     var url="<%=request.getContextPath()%>/galleryManAction_getCommentsByGalleryId.action?galleryId="+galleryId+"&time="+new Date();
     alert(url)
     $("#iframe1").attr("src",url)
     //alert($("#iframe1"));
     
    } --%>
    
    function deleteGallery(){
         var f = document.getElementsByName("reallyPath");
           var galleryId=$("#galleryid").val();
           var jsp=$("#JSP").val();
			var c=0;	
			var reallyPaths = "";
			var reallyPath = "";
			for(var j=0; j<f.length ;j++ ){
			        reallyPath = f[j].value;
			        // alert(reallyPath);
			        reallyPaths = reallyPaths + "&reallyPath=" + reallyPath;		
			}

			if(confirm("确定要删除吗?"))
			{
			    //alert("<%=request.getContextPath()%>/galleryManAction_deleteGalleryById.action?galaryId="+galleryId+reallyPaths);
				document.location="<%=request.getContextPath()%>/galleryManAction_deleteGalleryById.action?galaryId="+galleryId+reallyPaths+"&JSP="+jsp;
				
			}
  }
    function deleteCommentById(commentID){
     if(confirm("确定要删除吗?"))
			{
			     var jsp=$("#JSP").val();
			     var galleryId=$("#galleryid").val();
				document.location="<%=request.getContextPath()%>/galleryManAction_deleteCommentById.action?commentid="+commentID+"&galaryId="+galleryId+"&JSP="+jsp;
				
			}
    
    }
    
    //从作品集详情中删除一张照片
    function deletePhotoFromDetail(photoid){
    	var galaryId = "<s:property value='#request.gallery.galaryId'/>";
    	var jsp= $("#JSP").val();
    	alert(jsp);
    	var url = "galleryManAction_deletePhotoById.action?photoid="+ photoid +"&galaryId=" + galaryId+"&JSP=" +jsp;
    	location.href = url;
    }
    
    var v_product_Store ;
$().ready(function() {
			$("#commentAccount").autocomplete("findUserByAccount.action", {  //当用户输入关键字的时候 ，通过 url的方式调用action的findStoreProdctListNameForJson方法
				minChars: 1,  //最小显示条数
				max: 12,  //最大显示条数
				autoFill: false,
				dataType : "json",  //指定数据类型的渲染方式
				extraParams: 
		        {   
		             ajaxAccount: function() 
		              { 
		               return $("#commentAccount").val();    //url的参数传递
		              }
		           },

				 //进行对返回数据的格式处理
        			 parse: function(data) 
         			{
		             var rows = [];
		               for(var i=0; i<data.length; i++)
		           		 {	
			                 rows[rows.length] = {
			                   data:data[i],
			                   value:data[i],
			                  //result里面显示的是要返回到列表里面的值  
			                   result:data[i]
			                 };
		               }           
	               	return rows;
	           	},
				formatItem: function(item) {
					//没有特殊的要求,直接返回了
                  		return item;
				}
			});
			
	$("#replyAccount").autocomplete("findUserByAccount.action", {  //当用户输入关键字的时候 ，通过 url的方式调用action的findStoreProdctListNameForJson方法
				minChars: 1,  //最小显示条数
				max: 12,  //最大显示条数
				autoFill: false,
				dataType : "json",  //指定数据类型的渲染方式
				extraParams: 
		        {   
		             ajaxAccount: function() 
		              { 
		               return $("#replyAccount").val();    //url的参数传递
		              }
		           },

				 //进行对返回数据的格式处理
        			 parse: function(data) 
         			{
		             var rows = [];
		               for(var i=0; i<data.length; i++)
		           		 {	
			                 rows[rows.length] = {
			                   data:data[i],
			                   value:data[i],
			                  //result里面显示的是要返回到列表里面的值  
			                   result:data[i]
			                 };
		               }           
	               	return rows;
	           	},
				formatItem: function(item) {
					//没有特殊的要求,直接返回了
                  		return item;
				}
			});
		
		});
		
		var flag=false;
function DrawImage(ImgD){
var image=new Image();
var iwidth = 169;            //定义允许图片宽度，当宽度大于这个值时等比例缩小
var iheight = 127;            //定义允许图片高度，当宽度大于这个值时等比例缩小
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
  </script> 
 
 
 </head>
  
  <body>
 
  
   <div id="label1" class="formtitle"><span><a href="javascript:void(0)" >单个作品集</a></span></div>
      
     <div id="firstDIV">
      <div id="subjectDiv">
       <table id="textTable" class="tablelist">
       
       <tbody>
        <tr>
        <td>作品主题：</td>
        <td>
        	<div><s:property value="#request.gallery.subjectName"/></div>
        </td>
        </tr> 
         <tr>
        <td>发布时间：</td>
        <td><s:date name="#request.gallery.creatTime" format="yyyy-MM-dd HH:mm:ss"/></td>
        </tr> 
         <tr>
        <td>作品描述：</td>
        <td>
        	<div>        	
        	<!-- <s:property value="#request.gallery.galaryDesc"/> -->
        	<s:if test="#request.gallery.galaryDesc.length()>25">
			    <s:property value="#request.gallery.galaryDesc.substring(0,25)"/>····
			</s:if>
			<s:else>
			    <s:property value="#request.gallery.galaryDesc"/>
		    </s:else>
        	
        	</div>
        </td>
        </tr> 
        </tbody>
       </table>
        </div>
      <!-- 按钮框 -->
      <div class="buttonDiv">
        <input id="uploadImg" name="uploadImg" type="button" class="btn" value="上传图片" />
        <input id="toTrueEditGalary" name="toTrueEditGalary" type="button" class="btn" value="编辑" />
       <!--  <input name="" type="button" class="btn" onclick="deleteGallery()" value="删除本作品集" />    -->
      </div>
      </div>
      
	<div  id="uploadImgTable" style="text-align: center;margin: 0px;padding:0px;">
		<p class="close"><a href="javascript:void(0)" id="close">关闭</a></p>
		<div id="addDiv" class="add_img"><input name="imgFile"  class='add_img_file' title='添加图片' type='file'></div> 
		<div id="imgsContainer" class="imgsContainer"></div>
		<input  type="button" class="btn" id="submitImage" style="margin-top:5px;padding:0" value="上传" />
	</div>
	<iframe id='img_iframe'  name='img_iframe' style="display:none"></iframe>
      
     <!-- 照片列表 -->
     <div id="twoDIV">
        <ul class="imglist">
	<c:if test="${listphoto!=null&& fn:length(listphoto)>0}">
    <c:forEach items="${listphoto}" var="item">
		
    <li class="selected">
    <span style="text-align: center;">
    <a  href="${item.photoSrc}" title="照片" rel="lightbox-myGroup">
    <img width="169px" onload="javascript:DrawImage(this)" src="${item.photoSrc}" title="点击看大图"/>
    </a>
    </span>
    <input type="hidden" name="reallyPath" value="${item.photoSrc}"/>
    
  <%--   <p><a href="javascript:deletePhotoFromDetail('${item.photoId}');">删&nbsp;除</a></p> --%>
    </li>
  </c:forEach>  
 </c:if> 
     </ul>      
     </div> 
     
      <div id="label1" class="formtitle"><span>所属服务：
      <s:property value='#request.gallery.lpService.serviceName'/></span></div>
       
       <div id="descDiv">
       <s:if test="#request.extend!=null">
        <table id="commentTable" style="margin-left: 2%;" class="tablelist">
       	<thead>
    	<tr>
        <th>价格</th>
        <th>拍摄时间</th>
        <th>照片张数</th>
        <th>精修张数</th>
        <th>化妆</th>
        <th>服装</th>
        </tr>
        </thead>
        <tbody>
         <tr>
        <td>${serviceDetail.price }元/天</td>
        <td>${serviceDetail.shootTime }天</td>
        <td>${serviceDetail.pictureNum}张</td>
        <td>${serviceDetail.truingNum }张</td>
        <td>${serviceDetail.facepaint }</td>
        <td>${serviceDetail.clothes }</td>
        </tr> 
        </tbody>
        </table>
        </br>
        <label>附加说明</label>
        </br>
         <div class="introDiv">${serviceDetail.instructions }</div>               
        </s:if> 
       <s:else>
                   私人订制  
       </s:else>
       </div>
       
    
     <div id="label1" class="formtitle"><span><a href="javascript:void(0)" >评论列表</a></span></div>
       <div id="commentNum" >
       <table id="textTable" class="tablelist">
       <tbody>
        <tr>
        <td><img src="images/view.png" title="浏览数" style="height: 24px"/></td>
        <td>
         <div id="gallviewNum" style="width:70px;">
         	<c:if test="${gallery!=null && gallery.controlSource == 0}">
         		${gallery.viewNumber}&nbsp;&nbsp;
         	</c:if>
         	<c:if test="${gallery!=null && gallery.controlSource == 1}">
         		${extend.viewNumber }&nbsp;&nbsp;
         	</c:if>
         </div>
         <div><input type="text" id="viewNum" name="viewNumber" style="display: none" /> </div>
        
        </td>
        <td><img src="images/like.jpg" title="赞数" style="width:29px;height: 24px"/></td>
        <td>
         <div id="galllikeNumber" style="width:70px;">
         	<c:if test="${gallery!=null && gallery.controlSource == 0}">
         		${gallery.likeNumber}&nbsp;&nbsp;
         	</c:if>
         	<c:if test="${gallery!=null && gallery.controlSource == 1}">
         		 ${extend.likeNumber}&nbsp;&nbsp;
         	</c:if>
         </div>
        	<div ><input type="text" id="likeNum" name="likeNumber" style="display: none" style="width:40px;"/> </div>        
        </td>
        
        <td><img src="images/comment.png" title="评论数" style="height: 24px"/></td>
        <td>
        	<div style="width:70px;">${fn:length(listcomment)}&nbsp;&nbsp;</div>
        </td>
        <td><span id="addComment"><img src="images/t01.png" title="添加评论"/></span></td>
        <td><span><a id="updateNumber" href="javascript:updateNumber();"><img src="images/t02.png" style="height: 24px" title="修改浏览数量、赞数量"/></a></span></td>
        </tr>
        </tbody>
      
       </table>      
       </div>
       </br>    
     <div id="commentDiv">
     <%-- <iframe id="iframe1"  src="<%=request.getContextPath()%>/galleryManAction_getCommentsByGalleryId.action?galleryId=${gallery.galaryId}" frameBorder=0 scrolling=no width="100%" height="800px";  name="send" ></iframe> --%>
   <table id="commentTable" class="tablelist" style="margin-left: 2%;">
       	<thead>
    	<tr>
        <!-- <th><input name="" type="checkbox" value="" id="checkAll"/></th> -->
        <th>序号<i class="sort"><img src="images/px.gif" /></i></th>
        <th>发布内容</th>
        <th>发布者</th>
        <th>发布时间</th>
        <th>操作</th>
        </tr>
        </thead>
        <tbody>
     <c:if test="${listcomment != null && fn:length(listcomment)>0}">
	 <c:forEach items="${listcomment}" var ="item" varStatus="i">
         <tr>
       <%--  <td><input name="" type="checkbox" value="${item.commentId}" /></td> --%>
        <td>${i.index + 1}</td>
        <td>${item.commentDetail}</td>
        <td>${item.lpUser.nickName }</td>       
        <td><fmt:formatDate value="${item.createTime }" pattern="yyyy/MM/dd HH:mm:ss"/></td>
        <td><a href="javascript:void(0)" id="replyGalary" class="replyComment" lang="${item.commentId}"><img  src="<%=basePath%>images/t02.png" title='回复' /></a>&nbsp;&nbsp;&nbsp;     
        <a href="Javascript:deleteCommentById('${item.commentId}')"  class="tablelink"><img  src="<%=basePath%>images/t03.png" title='删除' /></a></td>
        </tr> 
    </c:forEach>
       </c:if>
        </tbody>
        </table> 
     <div>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </div>
  </div> 
   <div  id="div1">
    <p class="close"><a href="javascript:void(0)" id="closeComment">关闭</a></p>
    <div class="formbody">
    
    <div class="formtitle"><span>添加评论</span></div>
     <ul class="forminfo">
    <form id="Form1" name="Form1" method="post" action="<%=request.getContextPath()%>/galleryManAction_addComment.action">
     <li><label>评  论  人</label><input id="commentAccount" name="userName" type="text" class="dfinput" /></li>
     <li><label>评论内容</label><textarea name="comment" style="width:72%;height:100px;"></textarea><i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;145字以内</i></li>
     <input type='hidden' id="galleryid" name="galaryId"  value="<s:property value='#request.gallery.galaryId'/>"/>
     <br><br>
     <li> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;
     <input type="hidden" name="JSP" id="JSP" value="YES"/> 
     <input name="" type="submit"   class="btn" value="确认保存" class="dfinput"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input name="" type="reset" class="btn" value="重置" class="dfinput"/>
    </ul>    
    </form>
    </div>
    </div>
      <div  id="mask">
   </div>   
   
   
    <!--新增回复浮动框  -->
    <div  id="addReply">
	    <p class="close"><a href="javascript:void(0)" id="closeReply">关闭</a></p>
	    <div class="formbody">
		    <div class="formtitle"><span>添加回复</span></div>
		    <ul class="forminfo">
		    <form id="addReplyForm" name="addReplyForm" method="post" action="<%=request.getContextPath()%>/galleryManAction_addReply.action">
		    <input type='hidden' id="galleryid" name="galaryId"  value="<s:property value='#request.gallery.galaryId'/>"/>
		    <input type='hidden' id="replayToId" name="replayToId"/>
		    <li><label>评  论  人</label><input id="replyAccount" name="userName" type="text" class="dfinput" /></li>
		    <li><label>回复内容</label></li>
		    <li><textarea id="replyContext" name="replyContext" style="width:90%;height:100px;"></textarea><i>&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;145字以内</i></li>
		    </br>
		    <li>
		     <input type="hidden" name="JSP" id="JSP2" value="YES"/> 
		    <input name="" type="submit" class="btn" value="确认保存" class="dfinput"/>
		    <input name="" type="reset" class="btn" value="重置" class="dfinput"/>
		    </li>
		    </form>
		    </ul>
	    </div>
    </div>
    
     <!--修改浏览数、赞数浮动框  -->
    <div  id="updateNumberTable">
	    <p class="close"><a href="javascript:void(0)" id="closeUpdateNumber">关闭</a></p>
	    <div class="formbody">
		    <div class="formtitle"><span>修改浏览数、赞数</span></div>
		    <ul class="forminfo">
		    <form id="updateNumberForm" name="updateNumberForm" method="post" action="<%=request.getContextPath()%>/galleryManAction_updateNumber.action">
		    <input type='hidden' id="galleryid" name="galaryId"  value="<s:property value='#request.gallery.galaryId'/>"/>
		    <li><label>浏览数</label><input id="viewNumber" name="viewNumber" type="text" class="dfinput" /></li>
		    <li><label>赞数</label><input id="likeNumber" name="likeNumber" type="text" class="dfinput" /></li>
		    </br>
		    <li>
		    <input type="hidden" name="JSP" id="JSP3" value="YES"/> 
		    <input name="" type="submit" class="btn" value="确认保存" class="dfinput"/>
		    <input name="" type="reset" class="btn" value="重置" class="dfinput"/>
		    </li>
		    </form>
		    </ul>
	    </div>
    </div>
  </body>
</html>
