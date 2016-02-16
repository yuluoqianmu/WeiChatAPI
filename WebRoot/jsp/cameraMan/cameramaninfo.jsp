<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
<link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/jquery.Jcrop.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/showLoading.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.lightbox.packed.css" type="text/css" media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.Jcrop.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.showLoading.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.lightbox.js"></script>
</head>
  <style>
  #btnDiv{
   margin-left:34%;
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
 /*浮动 **/
   #div1 {
  background-color:#E5EFF8;
        border:5px solid rgba(0,0,0, 0.4);
        height:580px;
        left:50%;
        padding:1px; 
        width:450px;  
        position:absolute;  
        display:none;  
        left:0;  
        top:0;  
        z-index:2;  
  
 
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
  #editStyleinfo{
   margin-left:10px;
   width:450px;  
  
  }
 .editStyle{
    list-style:none; /* 去掉ul前面的符号 */  
    margin: 0px; /* 与外界元素的距离为0 */  
    padding: 0px; /* 与内部元素的距离为0 */  
    width: auto; /* 宽度根据元素内容调整 */  
    overflow:hidden; 
 } 
 
  .editStyle li{ 
    float:left; /* 向左漂移，将竖排变为横排 */ 
    width: 70px;
    height:20px;
  }
       .fontstyle{
     font-family:"微软雅黑";
     font-size:17px;    
    }
    
    
    #MyImg {
        background-color:#fff;
        border:5px solid rgba(0,0,0, 0.4);
        height:120%;
        left:50%;
        padding:1px; 
        width:80%;  
        position:absolute;  
        display:none;  
        left:0;  
        top:0;  
        z-index:2;  
        overflow-y:auto;
 
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
   width: 200px;
   height: 200px;
   overflow: hidden;
 }
 
  </style>
  
  <script type="text/javascript">
     $(function () {  
      var jcrop_api;
        var $win = $(window),  
                $div = $('#div1'), 
                $MyImg = $('#MyImg'), 
                $mask = $('#mask'),  
                $close = $('#close'),  
                flag = false;  
      
      
       $("#edit").click(function () {                  
            var clientH = $win.height(),  
                clientW = $win.width(),  
                divH = $div.height(),  
                divW = $div.width();  
            var t = (clientH - divH) / 2 + $win.scrollTop();  
            var l = (clientW - divW) / 2 + $win.scrollLeft();  
            $div.css('display', 'block').offset({ 'top': t , 'left': l });  
      
            $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
            flag = true;  
      
            $close.click(function () {  
                flag = false;  
                $div.hide();  
                $mask.hide();  
            });  
      
      
            $(window).scroll(setMask).resize(setMask);  
      
            function setMask() {  
                if (flag) {  
                    $div.css('display', 'block').offset({ 'top': ($win.height() - $div.height()) / 2 + $win.scrollTop(), 'left': ($win.width() - $div.width()) / 2 + $win.scrollLeft() });  
                    $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
                } else {  
                    $div.hide();  
                    $mask.hide();  
                }  
            }  
        });
        
           $("#editCam").click(function(){
            var userId=$("#userID").val();
	 var option = {
	        type:"post",
			dataType:"text",
			success:function(responseText){
			   
			    flag = false;  
                $div.hide();  
                $mask.hide(); 
              window.location.href="cameraManAction_getCameraman.action?userId="+userId;    
                alert("修改成功"); 
			    
			              
			},
			error:function(){
				alert("系统错误");
			}
	};
	//通过ajax方式提交表单，页面没跳转
	$("#Form1").ajaxSubmit(option);
}  ); 
    
           $("#changeImage").click(function(){ 
          /*  alert(1111); */
               var imgsrc=$("#ImgSrc").attr("src");
            /*    var imagePath=$("#imagePath").val();
              // alert(imagePath);
               $("#cutImage").attr("src",imgsrc); */
               $("#previewId").attr("src",imgsrc);
            /*    $("#HeadPath").val(imagePath);    */   
                var clientH = $win.height(),  
                clientW = $win.width(),  
                divH = $MyImg.height(),  
                divW = $MyImg.width();  
            var t = (clientH - divH) / 2 + $win.scrollTop();  
            var l = (clientW - divW) / 2 + $win.scrollLeft();  
            $MyImg.css('display', 'block').offset({ 'top': t , 'left': l });  
      
            $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
            flag = true;             
       /*      jcrop_api =$.Jcrop("#cutImage",{			    
			     onChange:showCoords,
                 onSelect:[300,300],
                  minSize:[300,300],
                 allowResize: false,
                 
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
		
		      ); 	 */
            
             
      
      
            $(window).scroll(setMask).resize(setMask);  
      
            function setMask() {  
                if (flag) {  
                    $MyImg.css('display', 'block').offset({ 'top': ($win.height() - $div.height()) / 2 + $win.scrollTop(), 'left': ($win.width() - $div.width()) / 2 + $win.scrollLeft() });  
                    $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
                } else {  
                    $MyImg.hide();  
                    $mask.hide();  
                }  
            }     
   })
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
     flag = false;  
      $MyImg.hide();  
      $mask.hide();  
 
 
 })
    
    
    function showCoords(obj){
   $("#x").val(obj.x);
   $("#y").val(obj.y);
   $("#w").val(obj.w);
   $("#h").val(obj.h);
   if(parseInt(obj.w)>0){
     //计算预览区域图片缩放的比例，通过计算显示区域的宽度(与高度)与剪裁的宽度(与高度)之比得到   
                var rx = 200 / obj.w;  
                var ry = 200 / obj.h;  
                 //通过比例值控制图片的样式与显示   
                $('#preview-pane .preview-container img').css( {  
                   width : Math.round(rx * $("#cutImage").width()) + "px", //预览图片宽度为计算比例值与原图片宽度的乘积  
                    height : Math.round(ry* $("#cutImage").height()) + "px", //预览图片高度为计算比例值与原图片高度的乘积  
                     marginLeft : "-" + Math.round(rx * obj.x) + "px",  
                     marginTop : "-" + Math.round(ry * obj.y) + "px"  
                }); 
                
      }
   };
    
    
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
   //alert(x);
   var option = {
			type:"post",			
			dataType:"text",
			data:{
				x:x,y:y,w:w,h:h,imagePath:imagePath,divw:divw,divh:divh
			},
			success:function(responseText){
				/* var jsonObj = $.parseJSON(responseText);
				//var number=(new Date()).valueOf();
				var realPath=jsonObj.realPath;
			    $("#ImgSrc").attr("src",realPath);
			    alert(jsonObj.relativePath);
			    $("#headImage").val(jsonObj.relativePath);
                flag = false;  
                $div.hide();  
                $mask.hide();  
				 */
				 location.reload();
			},
			error:function(){
			  $("#MyImg").hideLoading();
				alert("系统错误");
			}
	};
  $("#Form2").ajaxSubmit(option);
  
  
  
  }) 
    
    
     $("#imgsFile").change(function(){
     
         var fileType= $("#imgsFile").val();
         Check_FileType(fileType);
            var img=new Image();
       img.src=fileType;   
        if(img.fileSize>0){
    if(img.fileSize>5*1024){      
            alert("图片不能大于500KB。");
            return false;
            }
    } 
         $("#MyImg").showLoading();
         var userId=$("#userID").val();
         var boundx,
             boundy,
        // Grab smation about the preview pane
         $preview = $('#preview-pane'),
         $pcnt = $('#preview-pane .preview-container'),
         $pimg = $('#preview-pane .preview-container img');
	var option = {
			url:"<%=request.getContextPath()%>/changeHeadImg.action?userId="+userId,//如果option中存在url那么就提交到url上，如果不存在url，提交到表单的action上
			type:"post",
			dataType:"text",
			//async:false,
			data:{
				imgsFile:"imgsFile"
			},
			success:function(responseText){
			 $("#MyImg").hideLoading();
				var jsonObj = $.parseJSON(responseText);
				var number= (new Date()).valueOf();
				var realPath=jsonObj.realPath+"?tid="+number;
				//$("#MyImg").show();
				//alert(jcrop_api);
				 if(jcrop_api){
                    jcrop_api.destroy();
                    }                    
                //$("#cutImage").attr("src",realPath);				
				$("#previewId").attr("src",realPath);
				$("#headImage").val(jsonObj.relativePath);
				$("#imagePath").val(jsonObj.relativePath); 
				
	
			},
			error:function(){
				alert("系统错误");
			}
	};
	//通过ajax方式提交表单，页面没跳转
	$("#Form2").ajaxSubmit(option);
 });
    
    $("#previewId").click(function (){
     var  checkvalue= $("#headImage").val();
       if(checkvalue==""){
        return false;
       }
   var viewPath=$("#previewId").attr("src");
    if(jcrop_api){
     jcrop_api.destroy();
         } 
    $("#cutImage").attr("src",viewPath);   
    $("#previewId").attr("src",viewPath);
 var $cutImg=$("#cutImage");
   $cutImg.attr("onload","AutoResizeImage(400,0,this)");
   	setTimeout(function(){             				
   jcrop_api =$.Jcrop("#cutImage",{
			     //aspectRatio:1,
			   /*  setSelect: [80,50,380,400], //setSelect是Jcrop插件内部已定义的运动方法
			    onSelect: updateCoords */			    
			      //aspectRatio: 0.5,
			      onChange:showCoords,
                 aspectRatio : 1,
                 sideHandles: false,
                 cornerHandles:true,
                 //allowResize: false,
                 
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

				
			},2000);
   
   
 
 }) 
    
   $("#changeIdImage").click(function(){
    $("#fileDiv").show();
     //alert(1111);
   });
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
        
    });     
    
    /**-重置密码-*/
    function resetpsw(){
       if(confirm("您确定要重置该用户的密码？")){
       
        var userId=$("#userID").val();
            $.ajax({
    		  type:"post",
  			  url:"changePSW.action",
  			  data:{userId:userId},
  			  async:true,
  			  dataType:"json", 
  			  success:function(data){
  			    alert("该用户密码被重置为111111");   
  			  },
  			  error:function(){
				alert("系统错误");
			}
    	  });      
     
         } 
    }
    
    function disable(){
      if(confirm("确定要禁用该用户？")){
        var status= $("#userStatus").val();
        var userId=$("#userID").val();
         if(status==1){
          alert("该用户已被禁用，不能使用该操作");
          return false;
         }else{
            $.ajax({
    		  type:"post",
  			  url:"disableCamera.action",
  			  data:{userId:userId},
  			  async:true,
  			  dataType:"json", 
  			  success:function(data){
  			    alert("该用户被禁用"); 
  			    window.location.href="cameraManAction_getCameraman.action?userId="+userId;  
  			  },
  			  error:function(){
				alert("系统错误");
			}
    	  });      
         }
         }
    }
    
    function enable(){
      if(confirm("确定要解封该用户？")){
        var status= $("#userStatus").val();
        //alert(status);
        var userId=$("#userID").val();
         if(status==0||status==""){
          alert("该用户为正常用户，不用解封");
          return false;
         }else{
            $.ajax({
    		  type:"post",
  			  url:"enableCamera.action",
  			  data:{userId:userId},
  			  async:true,
  			  dataType:"json", 
  			  success:function(data){
  			    alert("该用户已被解封"); 
  			    window.location.href="cameraManAction_getCameraman.action?userId="+userId;    
  			  },
  			  error:function(){
				alert("系统错误");
			}
    	  });      
         }
         }
    }
    /* function editCam(){
      var option = {
			type:"post",
			dataType:"text",
			success:function(responseText){
				alert("修改成功")
			},
			error:function(){
				alert("系统错误");
			}
	};
	//通过ajax方式提交表单，页面没跳转
	$("#Form1").ajaxSubmit(option);
    } */
    
    function Check_FileType(str)
{
 var pos = str.lastIndexOf(".");
 var lastname = str.substring(pos,str.length)  //此处文件后缀名也可用数组方式获得str.split(".") 
 if (lastname.toLowerCase()!=".jpg" && lastname.toLowerCase()!=".gif"&&lastname.toLowerCase()!=".png")
 {
     alert("您上传的文件类型为"+lastname+"，图片必须为.jpg,.gif,.png类型！！！");
     document.myform.pic.focus();
     return false;
 }
 else 
 {
  return true;
 }
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

function idCardUpload(){
   
  var valTyle=$("#idimgsFile").val();
          Check_FileType(valTyle);

    $("body").showLoading();
  var userId=$("#userID").val();
	var option = {
			url:"<%=request.getContextPath()%>/cameraManAction_changeIdImg.action?userId="+userId,//如果option中存在url那么就提交到url上，如果不存在url，提交到表单的action上
			type:"post",
			dataType:"text",
			data:{
				idimgsFile:"idimgsFile"
			},
			success:function(responseText){
			$("body").hideLoading();
				 //把json字符串转换成json对象
				 //alert(responseText);
				var jsonObj = $.parseJSON(responseText);
			    //alert(jsonObj.realPath);
				$("#idImgSrc").attr("src",jsonObj.realPath);
				 $("#fileDiv").hide();
			},
			error:function(){
				alert("系统错误");
			}
	};
	//通过ajax方式提交表单，页面没跳转
	$("#userForm").ajaxSubmit(option);
}
 function Check_FileType(str)
{
 var pos = str.lastIndexOf(".");
 var lastname = str.substring(pos,str.length)  //此处文件后缀名也可用数组方式获得str.split(".") 
 if (lastname.toLowerCase()!=".jpg" && lastname.toLowerCase()!=".gif"&&lastname.toLowerCase()!=".png")
 {
      if($("#warnImg").length == 0)
	{
	$("#headimgsFile").after("<i><font id='warnImg' color='red'>请选择正确格式（.jpg、.gif、.png）!</font></i>");
	}
      $("#headimgsFile").val("");
      $("#headimgsFile").focus();
      
      return false;
 }
 }
 
 
 var flag=false;
function DrawImage(ImgD){
var image=new Image();
var iwidth = 300;            //定义允许图片宽度，当宽度大于这个值时等比例缩小
var iheight = 240;            //定义允许图片高度，当宽度大于这个值时等比例缩小
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
  <body>
   <div id="btnDiv">
      <input name="" type="button" id="edit"  class="btn" value="编辑" class="dfinput"/>
      <input name="" type="button" id="disable"  class="btn" value="封号"  onclick="disable()" class="dfinput"/>
      <input name="" type="button" id="enable"  class="btn" value="解禁" onclick="enable()" class="dfinput"/>   
   </div>
  
   <div id="baseInfo" style="margin-top: 2%">
     <input type="hidden" value="${cameraman.userStatus }" id="userStatus"/>
     <div id="tableinfo" style="margin-left:50px;">
      <table id="mytable" class="tablelist" style="width:800px;border:0px;border-collapse:separate; border-spacing:10px; margin-left:1%;">
      <thead>
       <tr>
        <td>用户账号</td>
        <td>${cameraman.userName}</td>
        <td>状态</td> 
        <td id="stus">
        <c:choose> 
         <c:when test="${cameraman.userStatus==1 }">        
           <font class="fontstyle" color='red'>封号</font>
         </c:when>
         <c:otherwise>
           <font class="fontstyle" color='green'>正常</font>
         </c:otherwise>
         </c:choose>
        </td> 
       </tr>
        <tr>
         <td>密码</td>
          <td><input type="password" readonly="readonly" value="******" style="backgroundColor:#E5EFF8;"/></td>
          <td><a href="javascript:void(0)" onclick="resetpsw()"/><font class="fontstyle" color='#4098CA'>重置密码</font></td>
        </tr>
        
        <tr>
         <td>注册时间</td>
         <td><fmt:formatDate value="${cameraman.registerTime }"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
         <td>登录信息</td>
         <td>
          <c:if test="${cameraman.accountSource==0 }">
                            注册用户
          
          </c:if>
          <c:if test="${cameraman.accountSource==1 }">
                            微博用户
          
          </c:if>
          <c:if test="${cameraman.accountSource==2 }" >
                             微信用户
          </c:if>
         
         </td>
         <td>昵称</td>
         <td>${cameraman.nickName }</td>
        </tr>
        
        
        <tr>
         <td>真实姓名</td>
         <td>${cameraman.realName }</td>
         <td>性别</td>
         <td>
          <c:if test="${cameraman.userGender==0 }">
                               男
          </c:if>
           <c:if test="${cameraman.userGender==1 }">
                               女
           </c:if>
         
         </td>
         <td>所在地</td>
         <td>${cameraman.lpCity.cityName }</td>
         <td>器材</td>
         <td>${cameraman.grapherCarmer }</td>
        </tr>
      </thead>
      </table>
     </div>
   
   </div>
   <form id="userForm" name="userForm" method="post"  enctype="multipart/form-data">
    <div id="imginfo" style="margin-left:50px; width:1000px; height:280px;">
      <div id="headinfo" style="float:left; width:430px; margin-left:30px;">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font class="tablebody">头像信息</font>
        </br></br>
        <span>
         <a href="${cameraman.headImage }?tempid=<%=Math.random() %>" rel="lightbox" title="头像">
        <img id="ImgSrc" src="${cameraman.headImage }?tempid=<%=Math.random()%>" width="200px"  title="点击看大图"/> </a>&nbsp;&nbsp;&nbsp;&nbsp;<a  id="changeImage" href="javascript:void(0)"><font class="fontstyle" color='#4098CA'>修改头像</font></span>
        <%--  <input type="hidden" id="imagePath" name="imagePath" value="${cameraman.imagePath}">       --%>
      </div>
      <div id="idcardinfo" style="float:left; width:430px; margin-left:30px;">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font class="tablebody">身份证信息</font>
        </br></br>
        <span>
          <a href="${cameraman.idCardImage }" rel="lightbox" title="身份证">
        <img id="idImgSrc" src="${cameraman.idCardImage }" width="300px"  onload="javascript:DrawImage(this)" title="点击看大图"/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a id="changeIdImage" href="javascript:void(0)"><font class="fontstyle" color='#4098CA'>修改照片</font></span> 
         <div id="fileDiv" style="display: none;"><input type="file" id="idimgsFile" name='idimgsFile' class="dfinput" style="width:300px;" onchange="idCardUpload()"/></div>         
      </div>
    </div>  
    </form>
     <div id="label1" class="formtitle" style="width:900px; margin-left:50px;"><span><a href="javascript:void(0)" >个人简介</a></span></div> 
    <div id="personinfo" style="margin-left:50px;">
    <textarea cols="80" rows="4" name="grapherDesc" readonly="readonly">${cameraman.grapherDesc}</textarea> 
    </div>
     <div id="label1" class="formtitle" style="width:900px;margin-left:50px;"><span><a href="javascript:void(0)" >摄影风格</a></span></div>
     <div id="styleinfo"  style="margin-left: 50px">
      <ul class="editStyle"> 
     <c:forEach items="${listStyle}" var="style">  
    <li>
    ${style.styleName}
    </li>
      </c:forEach>    
    </ul>
     
     </div>
   
    <div id="label1"  style="width:500px; margin-left:50px;" class="formtitle"><span><a href="javascript:void(0)" >登录日志</a></span></div>
    <s:if test="#request.loginList!=null && #request.loginList.size()>0">
    <div id="loginlog" style="margin-left:50px;">
	    <table class="tablelist" style="width:500px;" ALIGN="LEFT">
	    	<thead>
	    	<tr>
	        <th>登录时间</th>
	        <th>时长</th>
	        </tr>
	        </thead>
	        <tbody>	     
		
			<s:iterator value="#request.loginList" status="st">
		    <tr>
		        <td><s:date name="loginTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		        <td><s:property value="loginDuration"/></td>
		    </tr> 
		    </s:iterator>
		    </tbody>
     </div>
     </s:if>
     
     <!--编辑摄影师浮动框  -->
    <div  id="div1">
    <!-- //<input type="button" value="关闭" id="close" /> -->
    <p class="close"><a href="javascript:void(0)" id="close">关闭</a></p>
    <div class="formbody">
    
    <div class="formtitle"><span>编辑摄影师</span></div>
    
    <ul class="forminfo">
    <form id="Form1" name="Form1" method="post" action="<%=request.getContextPath()%>/editCameraman.action" enctype="multipart/form-data">
      <input type="hidden" value="${cameraman.userId }" id="userID" name="userId"/>
    <li><label>昵称</label><input name="nickName" type="text" value="${cameraman.nickName }" class="myinput" /></li>
    <li><label>性别</label>
    <input name="userGender" type="radio"  value="0" <c:if test="${cameraman.userGender==0 }">checked="checked" </c:if> /><font class="regfont" >男</font>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <input name="userGender" type="radio"  value="1"  <c:if test="${cameraman.userGender==1 }">checked="checked" </c:if> class="regfont"/><font class="regfont" >女</font>        
    </li>
    <li><label>所在地</label><cite>
    <select name="cityId" class="myinput">
      <c:forEach items="${listCity}" var="city" > 
         <option <c:if test="${cameraman.lpCity.cityId==city.cityId}">selected</c:if> value="${city.cityId }">${city.cityName }</option>
 		</c:forEach> 
    </select>
    
    </li>
    <li><label>联系电话</label>
    <input name="mobile" type="text" value="${cameraman.mobile }"  class="myinput" /> 
    </li>
    <li><label>所用器材</label><input name="grapherCarmer" type="text" value="${cameraman.grapherCarmer }" class="myinput" /></li>
    <li><label>个人简介</label></li>
    <li>
     <textarea cols="45" rows="4" name="grapherDesc">${cameraman.grapherDesc}</textarea> 
    </li>
   </ul>
    </br>
   <div id="editStyleinfo">
      <ul class="editStyle"> 
     <c:forEach items="${allStyle}" var="style">  
      <li>
      <input id="${style.styleId }" name="styleId" type="checkbox" value="${style.styleId }" 
      <c:forEach items="${listStyle}" var="userStyle">
      <c:if test="${style.styleId eq userStyle.styleId }">checked</c:if>     
      </c:forEach>/>&nbsp;&nbsp;<label for="${style.styleId }">${style.styleName }</label>
       </li>
      </c:forEach>    
      </ul>        
   </div>
    </br>
     </br>
   <div >
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" class="btn"  id="editCam" value="确认保存" class="dfinput" />
   </div>   
    </div>
        
       </form>
          
    </div> 
    <div  id="mask">
    </div> 
    
    
    <div id="MyImg"> 
    <div class="formtitle" style="width:790px;margin:0 auto;margin-top: 15px;"><span>上传头像</span></div> 
     <form id="Form2" name="Form2" method="post" action="<%=request.getContextPath()%>/cutImage.action" enctype="multipart/form-data"> 
       <input type="hidden" id="x" name="x">
	   <input type="hidden" id="y" name="y">
	   <input type="hidden" id="w" name="w">
	   <input type="hidden" id="h" name="h">
       <input type="hidden" id="imagePath" name="imagePath" value="">
      
     <div id="imginfo" style="margin-top:25px; width:1100px; height:600px;">
      
      <div id="img_cut_div" style="float:left; width:630px; height:600px;">
       <div id="forCut" style="margin-left:150px;">
        <img id="cutImage" src=""  width="0" height="0"/>
        </div>
        </div>
      <div id="preview-pane" >
     <div class="preview-container">
       <img src="" id="previewId" class="jcrop-preview" alt="Preview" />   
     </div>
      </div>
      <div> 请上传不超过2M的照片(点击头像剪裁)</div>
       <div>
      <div style="margin-top:80px; margin-left:32px;">
     <label><font class="fontstyle" color='black'>上传：</font></label><input type="file"  id="imgsFile" name='imgsFile' class="dfinput"  reg1="(.*)(.jpg|.bmp|.gif)$"/>
    <input type='hidden' name="headImage" id='headImage'  value='' reg2="^.+$" tip="亲！您忘记上传图片了。" />
     <div style="margin-top:15px; margin-left:150px;">
     <input name="" type="button"  class="btn" value="确认"  id="submitCutImg" class="dfinput"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input name="" type="button" class="btn" id="cancalCut" value="取消" class="dfinput" />
    </div>
     </div>
     </div>
      
      
      </div>
     </form>   
    </div>
  </body>
</html>
