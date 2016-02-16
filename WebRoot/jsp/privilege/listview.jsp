<%@page import="com.laipai.userManInfo.pojo.LpUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'order.jsp' starting page</title>
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" /> 
	<link href="<%=request.getContextPath()%>/css/select.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/css/jquery.Jcrop.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/css/showLoading.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" media="screen" type="text/css" href="<%=request.getContextPath()%>/css/zoomimage.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.lightbox.packed.css" type="text/css" media="screen" /> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.showLoading.min.js"></script>
    <script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.Jcrop.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.showLoading.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.lightbox.js"></script>
  </head>
  
   <style>
     .fontstyle{
     font-family:"微软雅黑";
     font-size:20px;    
    }
    
    .labelfontstyle{
     font-family:"微软雅黑";
     font-size:15px;
     font-style:normal;    
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
    
    function changeNick(){
      if(confirm("您确定要修改该用户的昵称？")){  
      var oldnick=$("#nick").val();
      $("#nick").attr("readonly",false);
      if(!$("#nick").readonly){
      $("#nick").focus();
      $("#nick").change(function(){
         //alert(1111);
      var userId=$("#userID").val();
      var nickName=$("#nick").val();
       if(nickName!=null&&nickName.length>0){
         if(oldnick!=nickName){
            $.ajax({
    		  type:"post",
  			  url:"changeNick.action",
  			  data:{userId:userId,nickName:nickName},
  			  async:true,
  			  dataType:"json", 
  			  success:function(data){
  			    $("#nick").attr("readonly",true) ;
  			    alert("修改成功"); 
  			    return false;
  			  },
  			  error:function(){
				alert("系统错误");
			}
    	  }); 
    	  }      
     }
    })
    }  
    } 
    }
    function disable(){
      if(confirm("确定要禁用该用户？")){
        var status= $("#userStatus").val();
        var userId=$("#userID").val(); 
        //alert(status);                               
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
  			  $("#stus").html("封号");
  			    alert("该用户被禁用"); 
  			    window.location.href="queryUserById.action?userId="+userId;     
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
  			  $("#stus").html("正常");
  			    alert("该用户已被解封");
  			    window.location.href="queryUserById.action?userId="+userId;   
  			  },
  			  error:function(){
				alert("系统错误");
			}
    	  });      
         }
         }
    }
   /**
           升级成为摄影师
   */
   function upgrade(){
     var userID=$("#userID").val();
     window.parent.location.href="<%=request.getContextPath()%>/cameraManAction_toAddCameraman.action?userId="+userID;
   }
   

    //图片上传依赖表单
function submitUpload(){
 var userId=$("#userID").val();
	var option = {
			url:"<%=request.getContextPath()%>/changeHeadImg.action?userId="+userId,//如果option中存在url那么就提交到url上，如果不存在url，提交到表单的action上
			type:"post",
			dataType:"text",
			data:{
				imgsFile:"imgsFile"
			},
			success:function(responseText){
				var jsonObj = $.parseJSON(responseText);
				
				var number= (new Date()).valueOf();
				var realPath=jsonObj.realPath+"?"+number;
				$("#MyImg").show();
				$("#cutImage").attr("src",realPath);				
				$("#previewId").attr("src",realPath);
				//$("#ImgSrc").attr("src",jsonObj.realPath);
				$("#headImage").val(jsonObj.relativePath);
				//alert("修改成功"); 
				     file = $("#imgsFile");  
                     file.after(file.clone());  
                     file.remove(); 
                     $("#imageLi").hide(1000); 
			},
			error:function(){
				alert("系统错误");
			}
	};
	//通过ajax方式提交表单，页面没跳转
	$("#Form1").ajaxSubmit(option);
}
   
    $(function(){ 
      var jcrop_api;
      var $win = $(window),  
                $div = $('#MyImg'),  
                $mask = $('#mask'),  
                $close = $('#close'),  
                flag = false;  
       $("#changeImage").click(function(){ 
                var imgsrc=$("#ImgSrc").attr("src");
              /* var imagePath=$("#imagePath").val();
               $("#cutImage").attr("src",imgsrc);
               $("#previewId").attr("src",imgsrc);
               $("#HeadPath").val(imagePath);    */ 
                $("#previewId").attr("src",imgsrc);  
                var clientH = $win.height(),  
                clientW = $win.width(),  
                divH = $div.height(),  
                divW = $div.width();  
            var t = (clientH - divH) / 2 + $win.scrollTop();  
            var l = (clientW - divW) / 2 + $win.scrollLeft();  
            $div.css('display', 'block').offset({ 'top': t , 'left': l });  
      
            $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
            flag = true;  
            
/*             jcrop_api =$.Jcrop("#cutImage",{			    
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
		
		      ); */ 	
            
            
            
            
            
            
            
            
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
   })

    $("#imgsFile").change(function(){
         var valTyle=$("#imgsFile").val();
          Check_FileType(valTyle);
          
          var img=new Image();
       img.src=valTyle;   
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

        // Grab some information about the preview pane
         $preview = $('#preview-pane'),
         $pcnt = $('#preview-pane .preview-container'),
         $pimg = $('#preview-pane .preview-container img');
                     if(jcrop_api){
     jcrop_api.destroy();
         }  
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
				var realPath=jsonObj.realPath;
				//$("#MyImg").show();
				//alert(jcrop_api);
				/*  if(jcrop_api){
                    jcrop_api.destroy();
                    }       */              
                //$("#cutImage").attr("src",realPath);				
				$("#previewId").attr("src",realPath);
				 $("#cutImage").attr("src","");  
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
			     onChange:showCoords,
                 onSelect:[100,100],
                 minSize:[100,100],
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

				
			},1000);
   
   
 
 })



 $("#cancalCut").click(function(e){
     $("#cutImage").attr("src",""); 
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
 
 
 }
/*   $("#userHead").imgbox({
		'speedIn'		: 0,
		'speedOut'		: 0,
		'alignment'		: 'center',
		'overlayShow'	: true,
		'allowMultiple'	: false
	}); */
  
 
 )
   
   
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
   if(imagePath==""){
    alert("您没有做任何操作。。。");
    return false;
   }
   var divw=$("#forCut").css("width");
   //alert(divw);
   var divh=$("#forCut").css("height");
  // alert(divh);
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
 
 
  $("#deleteEmployee").click(function(){
    
     if(confirm("确定要删除该用户？")){
     var userId=$("#userID").val();
      var url = "<%=request.getContextPath()%>/deleteEmployee.action?userId="+userId;
      window.location.href=url;
     }
    
  })
 
 
 
 
 
 
 
 
 
 
 
 
 
 });
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
   </script>
  
  
  
  <body>
   <div style="margin-left: 5%;margin-top: 5%;">
<%--      <div style="float:left; width:450px; margin-left:50px;"><font class="fontstyle">状态:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
       <c:choose> 
         <c:when test="${user.userStatus==1 }">        
                <font class="fontstyle" color='red'>封号</font>             
         </c:when>
         <c:otherwise>
                <font class="fontstyle" color='green'>正常</font>
         </c:otherwise>
         </c:choose>
     </div> --%>
<!--      <div id="btnDiv" style="float:left; width:430px; margin-left:30px;">
      <input style="width:27%" type="button" id="edit"  class="btn" value="升级为摄影师" onclick="upgrade()" class="dfinput"/>
      <input style="width:27%" type="button" id="disable"  class="btn" value="封号"  onclick="disable()" class="dfinput"/>
      <input style="width:27%" type="button" id="enable"  class="btn" value="解禁" onclick="enable()" class="dfinput"/>   
     </div> -->
     </br>
     </br>
     </br>
    <div id="tab1" class="tabson">
    <div class="formbody">
    <ul class="forminfo">
    <form id="Form1" name="Form1" method="post" action="<%=request.getContextPath()%>/changeemployee.action" enctype="multipart/form-data">
     <input type="hidden" id="userID" name="userId" value="${user.userId }"/>
     <input type="hidden" value="${user.userStatus }" id="userStatus"/>
    <!--  <input type="hidden" id="imagePath" name="imagePath" value=""> -->
    <li><label > <font class="labelfontstyle">账号:</font></font></label><input name="userName" type="text" class="dfinput" readonly="readonly" value="${user.userName }"/></li>
    <li><label ><font class="labelfontstyle">密码:</font></label><input name="userPassword" type="password" class="dfinput" value="******" readonly="readonly"/><i><a href="javascript:void(0)" onclick="resetpsw()"><font class="fontstyle" color='#4098CA'>重置密码</font></a></i></li>
    <li><label ><font class="labelfontstyle">邮箱 ：</font></label><input name="userEmail" type="text" class="dfinput"  value="${user.userEmail}"/></li>
    <li><label ><font class="labelfontstyle">姓名:</font></label><input name="realName" type="text" class="dfinput" value="${user.realName }"/></li>
   <%--  <li><label ><font class="labelfontstyle">昵称:</font></label><input name="nickName" type="text" id="nick" class="dfinput" value="${user.nickName}" readonly="readonly"/><i><a href="javascript:void(0)" onclick="changeNick()"><font class="fontstyle" color='#4098CA'>修改昵称</font></a></li> --%>
   <%--  <li><label ><font class="labelfontstyle">手机号:</font></label><input name="mobile" type="text" class="dfinput" readonly="readonly"  value="${user.mobile}"/></li>
    <li ><label><font class="labelfontstyle">头像:</font></label>
    <a href="${user.headImage}?tempid=<%=Math.random() %>" rel="lightbox" title="头像">
    <img id='ImgSrc' src="${user.headImage}?tempid=<%=Math.random() %>" height="100" width="100"  title="点击头像看大图"/></a>
    <i><a id="changeImage" href="javascript:void(0)"><font class="fontstyle" color='#4098CA'>修改头像</font></a></i></li>   --%>
    <li><input name="" type="submit" class="btn" value="确认保存" class="dfinput"/>
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    <input name="" type="button" class="btn" id="deleteEmployee" value="删除" class="dfinput"/>
			    </li>
    </ul>
    
       </form>
    </div>
    </div>
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
      <div id="img_cut_div" style="float:left; width:630px; height:600px">
      <div id="forCut" style="margin-left:150px; ">
        <img id="cutImage" src=""  width="0" height="0"/>
        </div>
        </div>
      <div id="preview-pane" >
     <div class="preview-container">
       <img src="" id="previewId" class="jcrop-preview" alt="Preview" />   
     </div>
      </div>
        <div>
        <div>请上传不超过2M的照片(点击头像截图)</div>
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
    
     <div  id="mask">
    </div>  


  </body>
</html>
