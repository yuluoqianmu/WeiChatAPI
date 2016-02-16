<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>专题新增页面</title>
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
#jbox_gray {
	margin: 0; padding: 0;
	width: 100%;
	height: 800px;
	position: absolute;
	top:0;
	left:0;
	background: #000000;
	
	opacity: 0.5; /*这是最重要的，因为它是CSS标准.该属性支持firefox, Safari和 Opera. */
	filter:alpha(opacity=50); /*这个是为IE6设的，可取值在0-100，其它三个0到1.*/ 
	-moz-opacity: 0.5; /*这个是为了支持一些老版本的Mozilla浏览器。*/ 
	-khtml-opacity: 0.5; /*这个为了支持一些老版本的Safari浏览器。 */
	z-index: 1;
	display: none;
}
.jbox {
	background-color:tan;
	margin: 0; padding: 0;
	background:#ffffff;
	position: absolute;
	border: none;
	z-index: 2;
	left:0px;
	top:0px;
	display: none;
}
#localImag
{
	text-align: center;
	width: 200px;
	height: 200px;
	display: none;
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
		function backToList()
		{
			window.location.href="${pageContext.request.contextPath}/subject_subjectList.action";
		}
		
		$(function(){
			
	    var jcrop_api;
		$("#Form1").submit(function(){
		
		if($("#subject_name").val() =="")
		{
			if($("#nameStyle").length == 0)
			{
				$("#subject_name").after("<font id='nameStyle' style='color:red'>专题名称不能为空</font>");
			}
			return false;		
		}else{
			$("#nameStyle").remove();
		}
		
		if($("#subjectimg").val() =="")
		{
			if($("#imgIsEmpty").length == 0)
			{
				$("#subjectimg").after("<font id='imgIsEmpty' style='color:red'>请选择专题图片</font>");
			}
			return false;		
		}else{
			$("#imgIsEmpty").remove();
		}
		
		var file = $("#subjectimg")
        file.after(file.clone().val(""));
        file.remove(); 
		return true;
		});
		
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
			    $("#localImag").show();
			    //$("#warn6").remove();
				 //把json字符串转换成json对象
				 //alert(responseText);
				//$("#MyImg").show();
				//showCutImg ();
				 if(jcrop_api){
                    jcrop_api.destroy();
                    } 
				var jsonObj = $.parseJSON(responseText);
				var number= (new Date()).valueOf();
				var realPath=jsonObj.realPath+"?"+number;
				 //alert(realPath)
				  $("#coverImg").attr("src",realPath);
				
				/*$("#previewId").attr("src",realPath); */
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
	
	
	
	
	
	
	
	
	
	
	
	
	
		
		$("#cutImg").click(function(){
		      
			   var x= $("#x").val();
			   var y= $("#y").val();
			   var w= $("#w").val();
			   var h= $("#h").val();
			   //alert(h);
			   var imagePath= $("#imagePath").val();
			   //alert(imagePath);
			   var option = {
						type:"post",			
						dataType:"text",
						data:{
							x:x,y:y,w:w,h:h,imagePath:imagePath
						},
						success:function(responseText){
							//alert(12313);
						},
						error:function(){
							alert("系统错误");
						}
				};
			   $("#Form3").ajaxSubmit(option);
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
               
               $("#MyImg").hide();
 
 
 });
		
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
			 $("#MyImg").hideLoading(); 
			     if(jcrop_api){
     jcrop_api.destroy();
     }
			    $("#MyImg").hide();
			    $("#localImag").show();
				 var jsonObj = $.parseJSON(responseText);
				var number=(new Date()).valueOf();
				var realPath=jsonObj.realPath;
			   $("#coverImg").attr("src",realPath);
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
  
  
  
  })
 	
		}
	);	
		
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
  <%-- <span style="width: 40px; height: 40px" ><img style="width: 100%" src="${pageContext.request.contextPath}/images/subjectUp/repeat.png"></span> --%>
    <div class="formbody">
    <div style="width:100%;text-align:right; height: 50px;margin-top: 10px;">
  </div>
    <div class="formtitle"><span>创建专题</span><span style="float: left;margin-left: 1120px;height: 30px"><img height="30px" width="30px" onclick="backToList()" src="${pageContext.request.contextPath}/images/test/16.png"></span></div>
    <ul class="forminfo">
    <form id="Form1" name="Form1" method="post" action="${pageContext.request.contextPath}/subject_addSubject.action" enctype="multipart/form-data">
    		<!-- <li>
    			<label>当前序号：</label><input id="seq_number" name="seq_number" type="text"
				class="dfinput" />
			</li> -->
			<li><label>专题名称：</label><input id="subject_name" name="subject_name" type="text"
				class="dfinput" />&nbsp;&nbsp;&nbsp;</li>
      				
       <div id="localImag" style="diplay:none;">
			  <img id="coverImg" height="150px src=""/> 
			   <div>(点击图片剪裁封面)</div>
			 </div> 
<!-- 			
			 <%--<input type="button" id="cutImg" value="剪切"></p>--%>
			<!--  <input type="hidden" id="x" name="x">
	         <input type="hidden" id="y" name="y">
	         <input type="hidden" id="w" name="w">
	         <input type="hidden" id="h" name="h">-->
             <input type="hidden" id="coverPath" name="coverPath" value=""/> 
			<li><label>专题封面：</label><input id="subjectimg"  name="subjectimg" type="file"
				class="dfinput"/>
			</li>
			
			<li><label>创建人 ：</label><input name="create_user_name" type="text"
				class="dfinput" readonly="readonly" value="${sessionScope.admin.userName}" />
			</li>
			<li><label>首页位置：</label>
				<select id="index_location" name="index_location" class="dfinput">
					<option value="0" selected >不显示</option>
					<c:forEach begin="1" end="99" var="index">
						<option value="${index}">${index}</option>
					</c:forEach>
				</select>
			<%--<input id="index_location" name="index_location" type="text"
				class="dfinput" onblur="" />--%>
				
			</li>
			
			<%--<li><img width="200px" height="200px" src="${pageContext.request.contextPath}/images/subjectUp/519.png" id="target"/></li>
			--%><li>
			<label>专题位置：</label>
			<%--<input id="subject_location" name="subject_location" type="text" class="dfinput"/>--%>
				<select id="subject_location" name="subject_location" class="dfinput">
					<c:forEach begin="1" end="99" var="s_location">
						<option value="${s_location}" <c:if test="${s_location ==1 }">selected</c:if> >${s_location}</option>					
					</c:forEach>
				</select>
			</li>
			<li><label>是否上线：</label>
			 <input name="subject_status" type="radio" value="1" />上线
			 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 <input name="subject_status" type="radio" value="0" checked="checked"/>下线</li><br/>
				<li><input name="Submit"  type="submit" class="btn"
				value="提交" class="dfinput"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="" type="reset" class="btn"
				value="重置" class="dfinput" />
       </form>
       
       <form id=id="Form3" name="Form3" method="post" action="<%=request.getContextPath()%>/cutImage.action" enctype="multipart/form-data">
          
       </form>
       
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
       
       
       
    </div>
    <div  id="mask">
    </div> 
  </body>
</html>
