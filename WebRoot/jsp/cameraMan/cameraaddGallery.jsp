<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <base href="<%=basePath%>">
<title>菜单页</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/select.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/mystyle.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/jquery.autocomplete.css" rel="stylesheet" type="text/css"  />
<link href="<%=request.getContextPath()%>/css/jquery.Jcrop.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/showLoading.css" rel="stylesheet" type="text/css" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>

<script type="text/javascript"  src="<%=request.getContextPath()%>/jquery/jquert.custom.progressbar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.Jcrop.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.showLoading.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>
</script>

<style>
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
   #div1 {
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
        top:70px;
        height:300px;  
 }
 #selectDiv{ 
        height:200px;
        width:900px;  
        position:absolute;  
        left:50px;  
        top:60px;
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
  
#label1{
 margin-left:50px;
 margin-top:5px;
 position:absolute;
 width:1000px; 
}
#label2{
 margin-left:50px;
 margin-top:10px;
 position:absolute;
 width:1000px; 

}
 #userId{
  margin-left:50px;
   margin-top:20px;
  position:absolute;
 
 }
#label4{
  margin-left:50px;
  margin-top:230px;
  position:absolute;
  width:1000px;
}

#personDesc{
  margin-left:30px;
  margin-top:300px;
  position:absolute;

}

#label3{
 margin-left:50px;
 margin-top:450px;
 position:absolute;
 width:1000px;

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

#queryservice{
 margin-left:50px;
 margin-top:40px;
 position:absolute;
 width:1000px; 

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
text-align:center;
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

#MyImg {
  background-color:#E5EFF8;
        border:5px solid rgba(0,0,0, 0.4);
        height:140%;
        left:50%;
        padding:1px; 
        width:90%;  
        position:absolute;  
        display:none;  
        left:8%;    
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
 
  function DrawImage(ImgD){
var image=new Image();
var iwidth = 230;            //定义允许图片宽度，当宽度大于这个值时等比例缩小
var iheight = 200;            //定义允许图片高度，当宽度大于这个值时等比例缩小
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
 
 var jcrop_api;
 var divNum=0;
  $(function () {
	  var maxPart=9;
	  var $win = $(window),  
                $div = $('#div1'),  
                $mask = $('#mask'),  
                $close = $('#close'),  
                flag = false;
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
    	  $("#div1").loading();
    	  var id=$(this).parent().attr("id");
    	  $.ajax({
    		  type:"post",
  			  url:"delImage.action",
  			  data:{id:id},
  			  async:true,
  			  dataType:"json",
  			  success:function(data){
  				$("#div1").finishLoading();
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
    	  $("#div1").finishLoading();
    	  var msgError=$(this).contents().find("span[id=result]");
    	  $(".add_img_file").val("");
    	  if(msgError.length==0){
    		  return;
		  }else if(msgError.text()=="success"){
		  var _div=$("<div id='part_"+part+"' class='part'></div>");
		  var closeImg=$("<img title='删除' class='_close_part_img' src='images/close.png'/>")
		  var _img=$("<img width=230 src='showImage?partNumber=part_"+part+"' onload='javascript:DrawImage(this)' onclick='selectCover(this)'/>");
		  $("#imgsContainer").append(_div);
		  $("#part_"+part).append(closeImg);
		  $("#part_"+part).append(_img);
		  part++;
		  }else{
			  var response=msgError.text().trim();
			  //alert(response);
		  }
      })
      //上传图片，点击提交按钮
      $("#submitImage").bind("click",function(){
    	  /**获取相关参数*/
    	   var obj=document.getElementsByName('styleId'); //风格
           var ids = ""; //风格id串联   &styleId=1&styleId=2
			var id = ""; //风格id
			for(var j=0; j<obj.length ;j++ ){
			     if(obj[j].checked){
			        id = obj[j].value;
			        ids = ids + "&styleId=" + id;
			       }		
			}
    	    //s=s.ToString().RTrim(',');
    	    //s.split(",");
    	    //服务Id 
    	   var serviceId =$('input:radio:checked').val();
    	   //服务主题
    	   var gallerySubject=$('#subjectName').val();
    	   if(gallerySubject==null||gallerySubject==""){
    	      alert("亲~您没有填写作品主题哦~");
    	      return false;
    	    }
    	   //服务描述
    	   var galleryDesc=$("#galaryDesc").val();
    	   //alert(galleryDesc);
    	  /*   if(galleryDesc==null||galleryDesc==""){
    	      alert("亲~作品描述也是要有的哦~");
    	       return false;
    	    } */
    	   //用户账号
    	   var  account=$("#account").val();
    	    if(account==null||account==""){
    	      alert("亲~没有填写用户账号~");
    	       return false;
    	    }
    	     var img =$("#hiddenPara").val();
    	    if(img==null||img==""){
    	      alert("请先选择封面（点击照片裁剪封面）");
    	       return false;
    	     }
    	  // alert("persistentImage.action?"+ids+"&account="+account);
    	  /**进度条*/
    	  if($(".part").length==0){
    		  alert("请点击左上角处按扭添加图片");
    		  return;
    	  }
    	  $("#div1").progressBar();
    	  var userId=$("#userId").val();
    	  var coverName=$("#coverName").val();
    	 
    	     var imgNumber= img.substr(-6,6);
    	  $.ajax({
    		  type:"post",
  			  url:"persistentImage.action?"+ids+"&account="+account,
  			  async:true,
  			  data:{serviceId:serviceId,gallerySubject:gallerySubject,galleryDesc:galleryDesc,coverName:coverName,imgNumber:imgNumber},
  			  dataType:"json",
  			  contentType: "application/x-www-form-urlencoded; charset=utf-8", 
  			  success:function(data){
  				$("#div1").finishProgressBar();
  				if(data.result==0){
  					$("#imgsContainer").empty();
  					setTimeout(function(){
  						//alert("上传成功");
  						window.location.href="queryGalary.action?userId="+userId;
  						$("#close").trigger("click");
  					},300);
  				}else if(data.result==1){
  					alert("请点击左上角处按扭添加图片");
  				}else{
  					setTimeout(function(){
  						alert("上传失败");
  					},300);
  				}
  			  }
    	  })
    	  
      })
      $(".add_img_file").bind("change",function(){
    	  if($(this).val()==""){
    		  return;
    	  }
    	  if($(".part").length==maxPart){
    		  alert("图片数量不能超过"+maxPart+"张");
    		  return;
    	  }
    	  $("#div1").loading();
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
      })
       $("#label3").click(function () {                  
            var clientH = $win.height(),  
                clientW = $win.width(),  
                divH = $div.height(),  
                divW = $div.width();  
            var t = (clientH - divH) / 2 + $win.scrollTop();  
            var l = (clientW - divW) / 2 + $win.scrollLeft();  
            $div.show(500).offset({ 'top': t , 'left': l });       
            $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
            flag = true;  
      
            $close.click(function () {  
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
        
    $("input[type=file]").change(function(){$(this).parents(".uploader").find(".filename").val($(this).val());});
	$("input[type=file]").each(function(){
	if($(this).val()==""){$(this).parents(".uploader").find(".filename").val("No file selected...");}
	});     
	
    });

    function queryByAccount(){
     var  Account=$("#account").val();
     var userAccount =mytrim(Account);
      $("#conDiv").remove();
      if(userAccount!=null){
        $.ajax({
    		  type:"post",
  			  url:"queryUserService.action",
  			  data:{userAccount:userAccount},
  			  async:true,
  			  dataType:"json", 
  			  success:function(data){
  			    //alert(data.length);
  			   if(data!=null && data.length>0){
  			     var $div=$("<div id='conDiv'></div>");
  			    $("#selectDiv").append($div); 
  			    $ul=$("<ul id='serviceStyle' class='Stylelist' ></ul> "); 
  			   	$div.append($ul);
  			    for(var i=0;i<data.length;i++){
	   		       	var serviceId = data[i].serviceId;
	   		       	var serviceName = data[i].serviceName;
	   		       	//alert(serviceName);
	   		       	var $li=$("<li></li>");
	   		       	$ul.append($li);	 
	   		       	var  $option=$("<input type='radio' name='service'/>")
	   		       	 $option.attr("value",serviceId);
	   		       	$li.append($option);
	   		       	$option.after(serviceName);
	   		       	
	   	        }
  			    
  			    }else{
  			     alert("该用户暂时没有服务");
  			    }
  			  
  			  }
			
/*   			  error:function(){
				alert("系统错误");
			  } */
    	  }); 
    	  }    
    }
　  function mytrim(str){ //删除左右两端的空格  
　　     return str.replace(/(^\s*)|(\s*$)/g, "");  
　　 } 
var v_product_Store ;
$().ready(function() {
			$("#account").autocomplete("findUserByAccount.action", {  //当用户输入关键字的时候 ，通过 url的方式调用action的findStoreProdctListNameForJson方法
				minChars: 1,  //最小显示条数
				max: 12,  //最大显示条数
				autoFill: false,
				dataType : "json",  //指定数据类型的渲染方式
				extraParams: 
		        {   
		             ajaxAccount: function() 
		              { 
		               return $("#account").val();    //url的参数传递
		              } , 
		              userType: function() 
		              { 
		               return 1;    //url的参数传递
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
		
     function 	selectCover(obj){
    $("#MyImg").show();  
   /* alert(jcrop_api); */
    if(jcrop_api){
     jcrop_api.destroy();
   
     } 
     
     
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
   var img =$("#hiddenPara").val();
   var imgNumber= img.substr(-6,6);
   var divw=$("#forCut").css("width");
   //alert(divw);
   var divh=$("#forCut").css("height");
   //alert(divh);
  // var imagePath= $("#imagePath").val();
   //alert(x);
   var option = {
			type:"post",			
			dataType:"text",
			data:{
				x:x,y:y,w:w,h:h,divw:divw,divh:divh,imgNumber:imgNumber
			},
			success:function(responseText){
				/* var jsonObj = $.parseJSON(responseText);
				var number=Math.random();
				var realPath=jsonObj.realPath+"?"+number;
			    //alert(realPath);
			    $("#ImgSrc").attr("src",realPath);
			   $("#headImage").val(jsonObj.relativePath);
			  
			  
                 $("#MyImg").hide(); */
                   $("#MyImg").hideLoading();
                 var jsonObj = $.parseJSON(responseText);
                 var coverName=jsonObj.coverName;
                 /* alert(coverName); */
                 $("#coverName").val(coverName);
                
                  $("#MyImg").hide();
                
				
			},
			error:function(){
				alert("系统错误");
			}
	};
  $("#Form2").ajaxSubmit(option);
 }		
 </script>
<body>
<%-- <form id="Form1" name="Form1" method="post" action="<%=request.getContextPath()%>/galleryManAction_addGallery.action" enctype="multipart/form-data">--%>
<%-- <div id="label1" class="formtitle"><span><a href="javascript:void(0)" >选择风格</a></span></div>
  <div id="checkDiv">
  <ul class="Stylelist" > 
    <s:if test="#request.stylelist!=null && #request.stylelist.size()>0"> 
    <s:iterator value="#request.stylelist" status="st"> 
    <li>
    <input id="<s:property value='styleId'/>" name="styleId" type="checkbox" value="<s:property value='styleId'/>" />&nbsp;&nbsp;<label for="<s:property value='styleId'/>"><s:property value='styleName'/></label>
    </li>
     </s:iterator>
  </s:if>        
    </ul>
    </div> --%>

<div id="label2" class="formtitle"><span><a href="javascript:void(0)" >选择服务</a></span></div>
 <div id="queryservice">
     </br> </br>
        <ul id='serviceStyle' >
         <input type="hidden" id="account" name="userId" value="${cameraman.userName}"/>
         <input type="hidden" id="userId"  value="${cameraman.userId}"/>
         <li style='margin-bottom:12px'><input id="private" type='radio' name='service' value="private" checked="checked"/>&nbsp;&nbsp;<label for="private">私人订制</label></li>
         <s:if test="#request.servicelist!=null && #request.servicelist.size()>0">       
         <s:iterator value="#request.servicelist" status="st">  
         <li style='margin-bottom:12px'><input id="<s:property value='serviceId'/>" type='radio' name='service' value="<s:property value='serviceId'/>"/>&nbsp;&nbsp;<label for="<s:property value='serviceId'/>"><s:property value='serviceName'/></label></li>
         </s:iterator> 
         </ul> 
        </s:if>
        <s:else>
         
        </s:else>
        
   </div>
  
   <div id="label4"  class="formtitle"><span>作品集主题</span></div>
      <div class="addCamera" id="personDesc">
      <ul class="forminfo" > 
       <li><label class="regfont">作品主题</label><input name="subjectName" id="subjectName" class="myinput"/></li>
       <li><label class="regfont">作品描述</label></br>
       <textarea id="galaryDesc" cols="80" rows="5" name="galaryDesc" > </textarea> 
       </li>
       </ul>
   <input type="hidden" name="coverName" id="coverName" value=""/>
   </div>
<div id="label3" class="formtitle"><span><a href="javascript:void(0)" >上传照片</a></span></div>
  <div  id="div1" style="text-align: center;margin: 0px;padding:0px;">
    <p class="close"><a href="javascript:void(0)" id="close">关闭</a></p>
<div id="addDiv" class="add_img"><input name="imgFile"  class='add_img_file' title='添加图片' type='file'></div> 
<div><font style="color: #329E1C;">(点击图片可选择封面)</font></div>
<div id="imgsContainer" class="imgsContainer">
</div>
<input  type="button" class="btn" id="submitImage" style="margin-top:5px;padding:0" value="上传" />
 </div> 
   <iframe id='img_iframe'  name='img_iframe' style="display:none"></iframe>
   
   <div id="MyImg"> 
    <div class="formtitle" style="width:950px;margin:0 auto;margin-top: 25px;"><span>裁剪封面</span></div> 
     <form id="Form2" name="Form2" method="post" action="<%=request.getContextPath()%>/selectCover.action" enctype="multipart/form-data"> 
       <input type="hidden" id="x" name="x">
	   <input type="hidden" id="y" name="y">
	   <input type="hidden" id="w" name="w">
	   <input type="hidden" id="h" name="h">
     <!--   <input type="hidden" id="imagePath" name="imagePath" value=""> -->
      
     <div id="imginfo" style="margin-top:15px; width:1200px; height:800px;">
      <div id="img_cut_div" style="float:left; width:750px;height:700px;">
      <div id="forCut" style="margin-left:100px; " >
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
   
   
   
   
</body>
</html>