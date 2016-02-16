<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@taglib uri="/struts-tags" prefix="s"%>
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

<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.Jcrop.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.showLoading.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/laydate/laydate.js"></script>

	<style type="text/css">  
	.crop_preview{position:absolute; left:520px; top:0; width:120px; height:120px; overflow:hidden;}
	/*浮动 **/
   #div1 {
  background-color:#fff;
        border:5px solid rgba(0,0,0, 0.4);
        height:530px;
        left:50%;
        padding:1px; 
        width:730px;  
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
  
   #MyImg {
  background-color:#E5EFF8;
        border:5px solid rgba(0,0,0, 0.4);
        height:120%;
        left:50%;
        padding:1px; 
        width:80%;  
        position:absolute;  
        display:none;  
        left:10%;  
        top:8%;  
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
 
    </style>  	
<script type="text/javascript">
       $(function () { 
    
        var $win = $(window),  
                $div = $('#div1'),  
                $mask = $('#mask'),  
                $close = $('#close'),  
                flag = false;  
      
      
       $(".click").click(function () {                  
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
        
         $("#checkAll").click(function(){
	          //alert(this.checked);
	         if(this.checked){
	          $("input[type='checkbox']").each(function(){this.checked=true;});
	         }else{
	          $("input[type='checkbox']").each(function(){this.checked=false;});
	         	         
	         }
	      
	      
	      })  
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
	})     

    });
   
  $(document).ready(function(){
  var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
  var jcrop_api;
 // alert(jcrop_api);
 //查询
  $("#search").click(function(){
    searchUser();
  });
  function searchUser(){
  	var userAccount =$("#user_account").val();
  	var nickName = $("#nick_name").val();
  	var registTime = $("#registTime").val();
  	document.location="<%=request.getContextPath()%>/queryall.action?userAccount="+userAccount+"&nickName="+nickName+"&registTime="+registTime ;
  }
  
  $("#deleteall").click(function(){
    deleteUser();
  });
	$("#Form1").submit(function(){
		if($("#userName").val() == "")
		{
			if($("#warn1").length == 0)
			{
				$("#userName").after("<i><font id='warn1' color='red'>用户名不能为空!</font></i>");
			}
			return false;
		}else
		{    
		     if($("#warn1")){
		     $("#warn1").remove();
		     }
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
			if($("#warn2").length == 0)
			{
				$("#userPassword").after("<i><font id='warn2' color='red'>请输入密码!</font></i>");
			}
			return false;
		}else
		{
			$("#warn2").remove();
		}
		 var one = $("#userPassword").val();
	     var two = $("#password").val();
			if(two != one)
			{
				if($("#error").length == 0)
				{
					$("#password").after("<i><font id='error' color='red'>两次密码输入不一致！</font></i>")
				}
				return false;
			}
			 else
			{
				$("#error").remove();
			} 
		if($("#nickName").val() == "")
		{
			if($("#warn3").length == 0)
			{
				$("#nickName").after("<i><font id='warn3' color='red'>请输入昵称!</font></i>");
			}
			return false;
		}else
		{
			$("#warn3").remove();
		}
		
		if($("#imgsFile").val() == "")
		{
			if($("#warn4").length == 0)
			{
				$("#imgsFile").after("<i><font id='warn4' color='red'>请上传头像!</font></i>");
			}
			return false;
		}else
		{
			$("#warn4").remove();
		}
		var file = $("#imgsFile")
        file.after(file.clone().val(""));
        file.remove(); 
	   return true;
	});


 $("#imgsFile").change(function(){
 
     var val= $("#imgsFile").val();
       Check_FileType(val);
      /*  checkImg("imgsFile","tempimg") */
        $("#div1").showLoading();
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
			url:"<%=request.getContextPath()%>/uploadPic.action",//如果option中存在url那么就提交到url上，如果不存在url，提交到表单的action上
			type:"post",
			dataType:"text",
			async:false,
			data:{
				imgsFile:"imgsFile"
			},
			success:function(responseText){
				 $("#div1").hideLoading();
				//releasePage()
				 //把json字符串转换成json对象
				 //alert(responseText);
				 $("#warn4").remove();
				var jsonObj = $.parseJSON(responseText);
				var number= (new Date()).valueOf();
				var realPath=jsonObj.realPath+"?"+number;				
				/* $("#MyImg").show();
				//alert(jcrop_api);
				 if(jcrop_api){
                    jcrop_api.destroy();
                    //return nothing(e);
                    } 
                     */
               /*  $("#cutImage").attr("src",realPath); */
				 $("#ImgSrc").attr("src",realPath);
			/* 	$("#previewId").attr("src",realPath); */
				$("#headImage").val(jsonObj.relativePath);
				$("#imagePath").val(jsonObj.relativePath);
				
			},
			error:function(){
				alert("系统错误");
			}
	};
	//通过ajax方式提交表单，页面没跳转
	$("#Form1").ajaxSubmit(option);
 });

   $("#ImgSrc").click(function(){
      var viewPath=$("#ImgSrc").attr("src");
     // alert(viewPath);
      $("#MyImg").show();
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
			    // aspectRatio: 0.5,
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

   });

 $("#cancalCut").click(function(e){
     //alert(jcrop_api);
     if(jcrop_api){
     jcrop_api.destroy();
   
     }
     var $cutimg=$("#cutImage");
     var $previewId=$("#previewId");
     //alert($cutimg.attr("src"));
    /*  if($cutimg.attr("src")!=""){
     $cutimg.attr("src","");
     }if($previewId.attr("src")!=""){
     $previewId.attr("src","");
     }   */
     
      $("#MyImg").hide();
       return nothing(e);
 });
 });

function nothing(e){
			e.stopPropagation();
			e.preventDefault();
			return false;
		};

/*    function submitUpload(){  
     
       
   }
  ;   */

  function cancelCut(){
    
    };
  function deleteUser(){
    var f = document.getElementsByName("ids");
			var c=0;	
			var ids = "";
			var id = "";
			var i = 0;
			for(var j=0; j<f.length ;j++ ){
			    if(f[j].checked){
			    	i++;
			        id = f[j].value;
			        ids = ids + "&userId=" + id;
			    }
			}

			if(i == 0){
			     alert("请至少选择一个用户!");
			     return;
			}

			if(confirm("确定要删除吗?"))
			{
				document.location="<%=request.getContextPath()%>/deleteUser.action?" + ids;
			}
  }
   //图片上传依赖表单

    /**校验登录名是否出现重复*/
    function checkUser(o){
    	//alert(o.value);//dom的写法
    	//alert($(o).val());//jquery的写法
    	var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
    	var logonName = $(o).val();
    	if(reg.test(logonName)){
    	 $("#userAccount").html("<font id='wrn4' color='red'>用户名不能包含汉字</font>");
    	  $(o).val("");
    	  $(o)[0].focus();
    	  return false;
    	}
    	//以登录名作为查询条件，查询该登录名是否在数据库表中存在记录
    	$.post("checkUser.action",{"userAccount":logonName},function(data){
    		//如果栈顶是模型驱动的对象，取值的时候应该使用data.message的方式
    		//如果栈顶是模型驱动的对象的某个属性，取值的时候应该使用data即可
    	 	if(data==1){
    		   if($("#wrn1")){
    		   $("#wrn1").remove();
    		   }
    		   if($("#wrn2")){
    		   $("#wrn2").remove();
    		  } if($("#wrn4")){
    		   $("#wrn4").remove();
    		  }
    		  
    		  if($("#wrn3").length == 0){
				$("#userAccount").html("<font id='wrn3' color='red'账号不能为空</font>");
				$(o)[0].focus();
				}
			}                      
            else if(data==2){
			   if($("#wrn1")){
    		   $("#warn1").remove();
    		   }
    		   if($("#wrn3")){
    		   $("#wrn3").remove();
    		  }
    		  if($("#wrn4")){
    		   $("#wrn4").remove();
    		  }
    		   if($("#wrn2").length == 0){
				$("#userAccount").html("<font id='wrn2' color='red'>账号已经存在</font>");
				$(o)[0].focus();
				}
			}
			else{
			     if($("#wrn2")){
    		   $("#wrn2").remove();
    		   }
    		   if($("#wrn3")){
    		   $("#wrn3").remove();
    		  }
    		  if($("#wrn4")){
    		   $("#wrn4").remove();
    		  }
    		   $("#warn1").remove();
    		  if($("#wrn1").length == 0)
				{
				$("#userAccount").html("<font id='wrn1' color='green'>该账号可以使用</font>");
				}
			}
    	});
    	
    	
    }

/*     function updateCoords(c){
	 $("#x").val(c.x);
	$("#y").val(c.y);
	$("#w").val(c.w);
	$("#h").val(c.h);
}; */

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
   var imagePath= $("#imagePath").val();
   //alert(x);
   var option = {
			type:"post",			
			dataType:"text",
			data:{
				x:x,y:y,w:w,h:h,imagePath:imagePath,divw:divw,divh:divh
			},
			success:function(responseText){
				var jsonObj = $.parseJSON(responseText);
				var number=Math.random();
				var realPath=jsonObj.realPath+"?"+number;
			    //alert(realPath);
			    $("#ImgSrc").attr("src",realPath);
			   $("#headImage").val(jsonObj.relativePath);
			   $("#MyImg").hideLoading();
                 $("#MyImg").hide();
				
			},
			error:function(){
			 $("#MyImg").hide();
				alert("系统错误");
			}
	};
  $("#Form2").ajaxSubmit(option);
 }
 
 function Check_FileType(str)
{
 var pos = str.lastIndexOf(".");
 var lastname = str.substring(pos,str.length)  //此处文件后缀名也可用数组方式获得str.split(".") 
 if (lastname.toLowerCase()!=".jpg" && lastname.toLowerCase()!=".gif"&&lastname.toLowerCase()!=".png")
 {
     if($("#warn5").length == 0)
	{
	$("#imgsFile").after("<i><font id='warn5' color='red'>请选择正确格式（.jpg、.gif、.png）!</font></i>");
	}
      $("#imgsFile").val("");
      $("#imgsFile").focus();
     return false;
 }
 else 
 {
  $("#warn5").remove();
  return true;
 }
}
 
 function detailInfo(url){
	 location.href = url;
 }
 
  function CheckChinese(val){     
　　var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
　　if(reg.test(val)){     
       return false;
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
   
   function checkImg(fileId,imgId){
        var maxsize = 1*1024;//1M
		var errMsg = "上传的附件文件不能超过1M！！！";
		var  browserCfg = {};
		var ua = window.navigator.userAgent;
		if (ua.indexOf("MSIE")>=1){
			browserCfg.ie = true;
		}else if(ua.indexOf("Firefox")>=1){
			browserCfg.firefox = true;
		}else if(ua.indexOf("Chrome")>=1){
			browserCfg.chrome = true;
		}

       checkfile(fileId,imgId,browserCfg,maxsize);
   
   }
   		function checkfile(fileId,imgId,browserCfg,maxsize){
			try{
			 	var obj_file = document.getElementById(fileId);
			 	if(obj_file.value==""){
			 		alert("请先选择上传文件");
			 		return false;
			 	}
			 	var filesize = 0;
			 	if(browserCfg.firefox || browserCfg.chrome ){
			 		filesize = obj_file.files[0].size;
			 	}else if(browserCfg.ie){
			 		var obj_img = document.getElementById(imgId);
			 		obj_img.dynsrc=obj_file.value;
			 		filesize = obj_img.fileSize;
			 	}	 	
			 	if(filesize>maxsize){
			 		alert("图片大于1M了");
			 		return false;
				}
			}catch(e){
				alert(e);
			}
		}
  
  //查询注册时间时显示日历
laydate.skin('danlan');
</script>
</head>
<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">数据表</a></li>
    <li><a href="#">基本内容</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    <div class="tools">
    
    	<ul class="toolbar">
        <li class="click"><span><img src="images/t01.png" /></span>添加</li>
        <li id="deleteall"><span><img id="delete" src="images/t03.png" /></span>删除</li>
        </ul>        
        <ul class="toolbar1">
        <li ><span style="margin-top:0px">用户账号：
       		<input type="text" id="user_account" name="用户账号" style="height:20px"/>
       	</span>
       	<span style="margin-top:0px">用户昵称：
       		<input type="text" id="nick_name" name="用户昵称" style="height:20px"/>
       	</span>
       	<span style="margin-top:0px">注册时间：
       		<input id="registTime" name="注册时间" style="height:20px"  class="laydate-icon" onclick="laydate({elem: '#registTime'});"/>
       	</span>
       	</li>
       	<li id="search" style="cursor:pointer"><span><img src="images/search.png"/></span>查询</li>
        </ul>
    
    </div>
     </div>
   <form id="userForm" name="userForm" method="post" action="<%=request.getContextPath()%>/queryall.action" enctype="multipart/form-data"> 
    <table class="tablelist">
    	<thead>
    	<tr>
        <th><input name="" type="checkbox" value="" id="checkAll"/></th>
        <th>序号<i class="sort"><img src="images/px.gif" /></i></th>
        <th>用户账号</th>
        <th>用户昵称</th>
        <th>注册时间</th>
        <th>账号来源</th>
        <th>操作</th>
        </tr>
        </thead>
        <tbody>
        
   
	<s:if test="#request.userList!=null && #request.userList.size()>0">
		<s:iterator value="#request.userList" status="st">
        <tr ondblclick="detailInfo('<%=request.getContextPath()%>/toeditUser.action?userId=<s:property value="userId"/>');">
        <td><input type="checkbox"  name="ids" value="<s:property value='userId'/>"  /></td>
        <td><s:property value="#st.index + 1"/></td>
        <td><s:property value="userName"/></td>
        <td><s:property value="nickName"/></td>
        <td><s:date name="registerTime" format="yyyy-MM-dd hh:mm:ss"/></td>
        <td>
          <s:if test="accountSource==0">
                                用户注册
           </s:if>
            <s:if test="accountSource==1">
                                 微博用户
            </s:if>
            <s:if test="accountSource==2">
                                 微信用户
            </s:if> 
        </li>     
        </td>

        <td><a href="<%=request.getContextPath()%>/toeditUser.action?userId=<s:property value='userId'/>" class="tablelink">查看</a>    
        </tr> 
     </s:iterator>
</s:if>        
    </table>
    

     <table width="98%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="userForm" title="记录" unit="条记录" actionName="queryall.action"/>
			</td>
		</tr>
	</table>

 </form>
    
    
    <div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
        
      <div class="tipinfo">
        <span><img src="images/ticon.png" /></span>
        <div class="tipright">
        <p>确认删除该信息</p>
        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
        </div>
        </div>
        
        <div class="tipbtn">
        <input name="" type="button"  class="sure" value="确定" />&nbsp;
        <input name="" type="button"  class="cancel" value="取消" />
        </div>
    
    </div>
    
    
    
    
     <!--新增用户浮动框  -->
    <div  id="div1">
    <!-- //<input type="button" value="关闭" id="close" /> -->
    <p class="close"><a href="javascript:void(0)" id="close">关闭</a></p>
    <div class="formbody">
    
    <div class="formtitle"><span>创建账号</span></div>
    
     <ul class="forminfo">
    <form id="Form1" name="Form1" method="post" action="<%=request.getContextPath()%>/adduser.action" enctype="multipart/form-data">
    <li><label>账号</label><input id="userName" name="userName" type="text"  class="dfinput" onblur="checkUser(this);" tip="账号不能为空"/><i id="userAccount"></i></li>
    <li><label>密码</label><input id="userPassword" name="userPassword" type="password" class="dfinput" id="psw1" tip="密码不能为空"/></li>
    <li><label>确认密码</label><cite><input name=""   type="password"  class="dfinput" id="password" onblur="checkPSW(this);" tip="请再次输入密码"/><i id="checkPSW"></i></li></li>
    <li><label>昵称</label><input name="nickName" id="nickName" type="text" class="dfinput" rg1="S{1,}" tip="昵称不能为空" /></li>
    <li><label>头像</label><img id='ImgSrc' src="images/camera.jpg" height="100" width="100" /><i><font style="color: #0D0D0D;">上传小于2M的照片（点击头像截图）</font></i>
    <input type='hidden' name="headImage" id='headImage'  value='' reg2="^.+$" tip="亲！您忘记上传图片了。" />
    <input type='hidden' name="accountSource" value="0"/>
    <img id="tempimg" dynsrc="" src="" style="display:none" />
    <li><label>头像</label><input type="file"  id="imgsFile" name='imgsFile' class="dfinput"  reg1="(.*)(.jpg|.bmp|.gif)$" tip="亲！文件类型不正确（请上传.jpg;.bmp;gif格式文件）。"/></li>
    <li><input name="" type="submit"  class="btn" value="确认保存" onsubmit="return toVaild()" class="dfinput"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input name="" type="reset" class="btn" value="重置" class="dfinput"/>
    </li>
    </ul>
    
    
    </div>
        
       </form>
          
    </div> 
    <div  id="mask">
    </div>  
    
    <div id="MyImg"> 
    <div class="formtitle" style="width:850px;margin:0 auto;margin-top: 25px;"><span>上传头像</span></div> 
     <form id="Form2" name="Form2" method="post" action="<%=request.getContextPath()%>/cutImage.action" enctype="multipart/form-data"> 
       <input type="hidden" id="x" name="x">
	   <input type="hidden" id="y" name="y">
	   <input type="hidden" id="w" name="w">
	   <input type="hidden" id="h" name="h">
       <input type="hidden" id="imagePath" name="imagePath" value="">
      
     <div id="imginfo" style="margin-top:15px; width:900px; height:600px;">
      <div id="img_cut_div" style="float:left; width:700px;height:600px;">
      <div id="forCut" style="margin-left:150px; " >
        <img id="cutImage"  src=""  width="0" height="0"/>
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
    <div style="margin-left:70%"><input name="" type="button" class="btn" id="cancalCut" value="取消" class="dfinput" />
    </div>
     </div>
      </div>

     </form>
    
    
    </div>
     
    

    </div>
    
    <script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');
	</script>

</body>

</html>
