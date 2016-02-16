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
<%-- <link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" /> --%>
<link href="<%=request.getContextPath()%>/css/jquery.Jcrop.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/showLoading.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.Jcrop.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.showLoading.min.js"></script>

  </head>
   
 <style type="text/css">
  .addCamera{
     margin-left:70px;
  }
   .regfont{
    font-family:"\5B8B\4F53";
    font-size:12px;
   
   }
    #imgInfo{
       height:200px;
       width:900px; 
       margin-left:70px;   
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
 
     
  /*遮罩**/
     #mask{  
        display:none;  
        position:absolute;  
        background-color:black;  
        filter:alpha(opactiy=20);  
        opacity:0.2;  
        z-index:1;  
    }   

 </style>
 
 <script type="text/javascript">

 
 
 $(function(){
        var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
        var jcrop_api;
        var $win = $(window),  
                $div = $('#MyImg'),  
                $mask = $('#mask'),  
                //$close = $('#close'),  
                flag = false;  
 
 
	$("#userForm").submit(function(){
	if($("#userName").val() == "")
	{
		if($("#warning1").length == 0)
		{
			$("#userName").after("<i><font id='warning1' style='color:red'> 账号不能为空！</font></i>");
		}
		return false;
	}else
	{
		    $("#warning1").remove();

		   if(reg.test($("#userName").val())){	
		    if($("#warnZH").length == 0)
			 {	 			 			   
			 $("#userName").after("<i><font id='warnZH' color='red'>用户名不能包含汉字!</font></i>");
			 }
			  return false;
			}
			else{
			  $("#warnZH").remove();
			} 
		
		
	}
	if($("#userPassword").val() == "")
	{
		if($("#warning2").length == 0)
		{
			$("#userPassword").after("<i><font id='warning2' style='color:red'> 密码不能为空！</font></i>");
		}
		return false;
	}else
	{
		$("#warning2").remove();
	}
	  var one = $("#userPassword").val();
	  var two = $("#password").val();
			if(two != one)
			{
				if($("#error").length == 0)
				{
					$("#password").after("<i><font id='error' color='red'> 两次密码输入不一致！</font></i>")
				}
				return false;
			}
			 else
			{
				$("#error").remove();
			} 
	
	if($("#nickName").val() == "")
	{
		if($("#warning3").length == 0)
		{
			$("#nickName").after("<i><font id='warning3' style='color:red'> 昵称不能为空！</font></i>");
		}
		return false;
	}else
	{
		$("#warning3").remove();
	}
	if($("#realName").val() == "")
	{
		if($("#warning4").length == 0)
		{
			$("#realName").after("<i><font id='warning4' style='color:red'> 姓名不能为空！</font></i>");
		}
		return false;
	}else
	{
		$("#warning4").remove();
	}
	if($("#grapherCarmer").val() == "")
	{
		if($("#warning5").length == 0)
		{
			$("#grapherCarmer").after("<i><font id='warning5' style='color:red'> 器材不能为空！</font></i>");
		}
		return false;
	}else
	{
		$("#warning5").remove();
	}
	if($("#cityID").val() == ""){
	
	   if($("#warning8").length == 0){
	    $("#cityID").after("<i><font id='warning8' style='color:red'> 请选择城市！</font></i>");     
	   }
	   return false;
	
	}else{
	
	 $("#warning8").remove();
	}	
	if($("#headimgsFile").val() == "")
	{
		if($("#warning6").length == 0)
		{
			$("#headimgsFile").after("<i><font id='warning6' style='color:red'>请上传头像！</font></i>");
		}
		return false;
	}else
	{
		$("#warning6").remove();
	}	
	if($("#idimgsFile").val() == "")
	{
		if($("#warning7").length == 0)
		{
			$("#idimgsFile").after("<i><font id='warning7' style='color:red'>请上传身份证！</i></font>");
		}
		return false;
	}else
	{
		$("#warning7").remove();
	}
	if($("#warn2").length > 0)
	{
		return false;
	}
	if($("#error").length > 0)
	{
		return false;
	}
	var file = $("#headimgsFile")
        file.after(file.clone().val(""));
        file.remove(); 
    var Idfile = $("#idimgsFile")
        Idfile.after(Idfile.clone().val(""));
        Idfile.remove();    	
	return true;
	}); 
	$("#userName").change(function(){
		var logonName = $(this).val();
		 var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
		 if(reg.test(logonName)){
		 if($("#wrn4").length==0){
    	 $("#userName").after("<font id='wrn4' color='red'>用户名不能包含汉字</font>");
    	 }
    	  $("#userName").val("");
    	  this.focus(); 
    	  return false;
		  
    	}
		 
		//  alert(logonName);
		$.post("checkUser.action",{"userAccount":logonName},function(data){
    		//如果栈顶是模型驱动的对象，取值的时候应该使用data.message的方式
    		//如果栈顶是模型驱动的对象的某个属性，取值的时候应该使用data即可
    		 if(data==1){
    		  if($("#warn1")){
    		   $("#warn1").remove();
    		   }
    		   if($("#warn2")){
    		   $("#warn2").remove();
    		  }
    		   if($("#wrn4")){
    		   $("#wrn4").remove();
    		  }
    		  if($("#warn3").length == 0){
    		   $("#userName").after("<i><font id='warn3' color='red'>账号不能为空!</font></i>");   		   
    		  }
    		 }
    		 
    		 
    		 else if(data==2){
    		 	if($("#warn1")){
    		   $("#warn1").remove();
    		   }
    		   if($("#warn3")){
    		   $("#warn3").remove();
    		  }
    		  		   if($("#wrn4")){
    		   $("#wrn4").remove();
    		  }
	    		 if($("#warn2").length == 0)
	    		 {
	    		 	$("#userName").after("<i><font id='warn2' color='red'>账号已经存在!</font></i>");
	    		 }
			}
			else{
			 if($("#warn2")){
    		   $("#warn2").remove();
    		   }
    		   if($("#warn3")){
    		   $("#warn3").remove();
    		  }
    		  		   if($("#wrn4")){
    		   $("#wrn4").remove();
    		  }
				if($("#warn1").length == 0)
				{
					$("#userName").after("<i><font id='warn1' color='green'>该账号可以使用~</font></i>");
				}
			}
    	});
	});
	$("#password").change(function(){
		var one = $("#userPassword").val();
		var two = $(this).val();
		if(one != "")
		{
			if(two != one)
			{
				if($("#error").length == 0)
				{
					$("#password").after("<i><font id='error' color='red'>两次密码输入不一致！</font></i>")
				}
			}
			 else
			{
				$("#error").remove();
			} 
		}
	});  
    $("#headimgsFile").change(function(){
    
        var valTyle=$("#headimgsFile").val();
          if(!valTyle){
          return false;
          }
          if(!Check_FileType(valTyle)){
          return false;
          }
             var img=new Image();
       img.src=valTyle;   
        if(img.fileSize>0){
    if(img.fileSize>5*1024){      
            alert("图片不能大于500KB。");
            return false;
            }
    } 
            $("body").showLoading();
          $("#ishead").attr("value","YES");
                        if(jcrop_api){
     jcrop_api.destroy();
         } 
	   var option = {
			url:"<%=request.getContextPath()%>/cameraManAction_uploadPic.action",//如果option中存在url那么就提交到url上，如果不存在url，提交到表单的action上
			type:"post",
			dataType:"text",
			data:{
				headimgsFile:"headimgsFile"
			},
			success:function(responseText){
			$("body").hideLoading();
			    //$("#warn6").remove();
				 //把json字符串转换成json对象
				 //alert(responseText);
				//$("#MyImg").show();
				/* showCutImg ();
				 if(jcrop_api){
                    jcrop_api.destroy();
                    }  */
				var jsonObj = $.parseJSON(responseText);
				var number= (new Date()).valueOf();
				var realPath=jsonObj.realPath+"?"+number;
				/* 
				 $("#cutImage").attr("src",realPath);
				
				$("#previewId").attr("src",realPath); */
				$("#headImgSrc").attr("src",realPath);
				$("#headImage").val(jsonObj.relativePath);
				$("#imagePath").val(jsonObj.relativePath); 
				
					
				
			},
			error:function(){
				alert("系统错误");
			}
	};
	//通过ajax方式提交表单，页面没跳转
	$("#userForm").ajaxSubmit(option);
      });
 
   $("#headImgSrc").click(function(){
      var viewPath=$("#headImgSrc").attr("src");
   
    if(jcrop_api){
     jcrop_api.destroy();
         } 
         showCutImg ();
      $("#cutImage").attr("src",viewPath);   
      $("#previewId").attr("src",viewPath);
   var $cutImg=$("#cutImage");
   $cutImg.attr("onload","AutoResizeImage(400,0,this)");
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
                $div.hide();  
                $mask.hide(); 
            
 
 
 })
 
 function showCutImg () {                  
            var clientH = $win.height(),  
                clientW = $win.width(),  
                divH = $div.height(),  
                divW = $div.width();  
            var t = (clientH - divH) / 2 + $win.scrollTop();  
            var l = (clientW - divW) / 2 + $win.scrollLeft();  
            $div.css('display', 'block').offset({ 'top': t , 'left': l });  
      
            $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
            flag = true;  
      
/*             $close.click(function () {  
                flag = false;  
                $div.hide();  
                $mask.hide();  
            });  
       */
      
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
        }
 
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
				 var jsonObj = $.parseJSON(responseText);
				var number=(new Date()).valueOf();
				var realPath=jsonObj.realPath;
			   $("#headImgSrc").attr("src",realPath);
			    //alert(jsonObj.relativePath);
			    $("#headImage").val(jsonObj.relativePath);
			      $("#warning6").remove();
                flag = false;  
                $div.hide();  
                $mask.hide(); 
                 $("#MyImg").hideLoading(); 
			},
			error:function(){
			 $("#MyImg").hideLoading(); 
				alert("系统错误");
			}
	};
  $("#Form2").ajaxSubmit(option);
  
  
  
  })
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 });
   //图片上传依赖表单
function submitUpload(){
        $("#ishead").attr("value","YES");
	var option = {
			url:"<%=request.getContextPath()%>/cameraManAction_uploadPic.action",//如果option中存在url那么就提交到url上，如果不存在url，提交到表单的action上
			type:"post",
			dataType:"text",
			data:{
				headimgsFile:"headimgsFile"
			},
			success:function(responseText){
			    $("#warning6").remove();
				 //把json字符串转换成json对象
				 //alert(responseText);
				var jsonObj = $.parseJSON(responseText);
				//alert(jsonObj.realPath);
				$("#headImgSrc").attr("src",jsonObj.realPath);
				alert(jsonObj.realPath);
				$("#headImage").val(jsonObj.relativePath);
				alert(jsonObj.relativePath);  
			},
			error:function(){
				alert("系统错误");
			}
	};
	//通过ajax方式提交表单，页面没跳转
	$("#userForm").ajaxSubmit(option);
}
 function idCardUpload(){
   
  var valTyle=$("#idimgsFile").val();
          Check_FileType(valTyle);
 var img=new Image();
       img.src=valTyle;   
        if(img.fileSize>0){
    if(img.fileSize>5*1024){      
            alert("图片不能大于500KB。");
            return false;
            }
    } 
    $("body").showLoading();
  $("#ishead").attr("value","");
	var option = {
			url:"<%=request.getContextPath()%>/cameraManAction_uploadPic.action",//如果option中存在url那么就提交到url上，如果不存在url，提交到表单的action上
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
				$("#idCardImage").val(jsonObj.relativePath);  
				$("#warning6").remove();
			},
			error:function(){
				alert("系统错误");
			}
	};
	//通过ajax方式提交表单，页面没跳转
	$("#userForm").ajaxSubmit(option);
}

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
   }
   ;
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
 else 
 {
 $("#warnImg").remove();
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
 </script>
 
 
  <body style="background-color: #eef4fa;">
   <form id="userForm" name="userForm" method="post" action="<%=request.getContextPath()%>/cameraManAction_addCameraman.action" enctype="multipart/form-data">
   <input type="hidden" name="userType" value="1"/>
   <input type="hidden" name="accountSource" value="0"/>
   <input type="hidden" name="userId" value="${user.userId }"/> 
  <div id="label1" style="margin-left:50px; margin-top:60px; width:900px" class="formtitle"><span>基础信息</span></div>
      <div class="addCamera" id="baseInfo">
     <ul class="forminfo">
    <li><label class="regfont">账号</label><input id="userName" name="userName" type="text" class="myinput" value="${user.userName }"/></li>
    <li><label class="regfont">密码</label><input id="userPassword" name="userPassword" type="password" class="myinput" value="${user.userName }"/></li>
    <li><label class="regfont">确认密码</label><input name="" id="password" type="password"  class="myinput" value="${user.userName }"/></li>
    <li><label class="regfont">昵称</label><input id="nickName" name="nickName" type="text" class="myinput" value="${user.nickName }"/></li>
    <li><label class="regfont">真实姓名</label><input id="realName" name="realName" type="text"  class="myinput" /></li>
    <li><label class="regfont">性别</label>
    <input name="userGender" type="radio"  value="0" checked="checked"/><font class="regfont" >男</font>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <input name="userGender" type="radio"  value="1"  class="regfont"/><font class="regfont" >女</font>     
     </li>
    <li><label class="regfont">器材</label><input id="grapherCarmer" name="grapherCarmer" type="text" class="myinput" /></li>
    <li><label class="regfont">城市</label>
     <s:select list="#request.citylist" name="cityID" id="cityID"
		listKey="cityId" listValue="cityName"
		headerKey="" headerValue="请选择"
	   class="myinput">
	</s:select>
    </li> 
    <li><label class="regfont">联系电话</label><input id="mobile" name="mobile" type="text"  class="myinput" /></li>      
    </ul>  
    
              
      </div>
  <div id="label1" style="margin-left:50px;width:900px" class="formtitle"><span>头像信息</span></div>
      <div class="addCamera" id="imgInfo">
         <div id="headImg" style="float:left; width:430px;">
           <s:if test="#request.user!=null">
            <ul class="forminfo">
             <li><label>头像</label><img id='headImgSrc1' src="${user.headImage}" height="100" width="100" /></li>
             </ul>
           </s:if>
           <s:else>
            <ul class="forminfo">
             <li><label>头像</label><img id='headImgSrc' src="images/camera.jpg" height="100" width="100" /><i><font style="color: #0D0D0D;">照片不超过2M(点击头像剪裁)</font></i></li>
             <input type='hidden' name="headImage" id='headImage'  value='' reg2="^.+$" tip="亲！您忘记上传图片了。" />
             <input type="hidden" id="ishead" name="ishead" value=""/>
             <li><label>上传</label><input type="file" id="headimgsFile" name='headimgsFile' class="myinput" style="width:300px;" /></li>
            </ul>
            </s:else>
         </div>
         <div id="idImg" style="float:left;  width:430px; ">
           <ul class="forminfo">
             <li><label>身份证照片</label><img id='idImgSrc' src="images/camera.jpg" height="100" width="150" /></li>
             <input type='hidden' name="idCardImage" id='idCardImage'  value='' reg2="^.+$" tip="亲！您忘记上传图片了。" />
             <li><label>上传</label><input type="file" id="idimgsFile" name='idimgsFile' class="myinput" style="width:300px;" onchange="idCardUpload()"/></li>
            </ul>        
         </div>      
      </div>
  <div id="label1" style="margin-left:50px;width:900px" class="formtitle"><span>个人简介</span></div>
      <div class="addCamera" id="personDesc">
       <textarea cols="80" rows="8" name="grapherDesc" id="grapherDesc"> </textarea> 
      </div>
   <div id="label1" style="margin-left:50px;width:900px" class="formtitle"><span>选择风格</span></div>   
   <div class="addCamera" id="personStyle" style="width:800px">
        <ul class="Stylelist" > 
    <s:if test="#request.stylelist!=null && #request.stylelist.size()>0"> 
    <s:iterator value="#request.stylelist" status="st"> 
    <li>
    <input id="<s:property value='styleId'/>" name="styleId" type="checkbox" value="<s:property value='styleId'/>" />&nbsp;&nbsp;<label for="<s:property value='styleId'/>"><s:property value='styleName'/></label>
    </li>
     </s:iterator>
  </s:if>
    </ul>
      
      </div> 
      
   <div id="submitForm" style="margin-left:100px;">
   <input name="" type="submit" class="btn" value="确认保存" />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <input name="" type="reset" class="btn" value="重置" /> 
   </div>
   <div id="submitForm" style="margin-left:100px;">
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </br>
   </div> 
   </form> 
     <div  id="mask">
    </div> 
         <div id="MyImg"> 
    <div class="formtitle" style="width:790px;margin:0 auto;margin-top: 25px;"><span>上传头像</span></div> 
     <form id="Form2" name="Form2" method="post" action="<%=request.getContextPath()%>/cutImage.action" enctype="multipart/form-data"> 
       <input type="hidden" id="x" name="x">
	   <input type="hidden" id="y" name="y">
	   <input type="hidden" id="w" name="w">
	   <input type="hidden" id="h" name="h">
       <input type="hidden" id="imagePath" name="imagePath" value="">
      
     <div id="imginfo" style="margin-top:50px; width:900px; height:600px;">
        <div id="img_cut_div" style="float:left; width:700px; height:600px;">
      <div id="forCut" style="margin-left:150px; ">
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
