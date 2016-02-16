<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.laipai.userManInfo.pojo.LpUser"%>
<%

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

LpUser user = (LpUser)request.getAttribute("lpUser");
String head = user.getHeadImage();
String[] temp = head.split("@");
temp[1] = "100w_100h_2e.jpg";
String headImage = temp[0]+"@"+temp[1]; 
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>摄影师<s:property value='#request.lpUser.realName'/></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="摄影师,相册,来拍">
<meta http-equiv="description" content="分享">
<!-- IE8浏览器支持 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<link href="css/share.css" rel="stylesheet" type="text/css" />
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"/>

<script type="text/javascript" src="jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="jquery/download.js"></script>
<script type="text/javascript">
$(function(){
	var ids="<s:property value='#request.ids'/>";
	var userId="<s:property value='#request.lpUser.userId'/>";
	var count="<s:property value='#request.count' />";
	var ct="<s:property value='#request.lpUser.lpCity.cityName'/>";
	$(".cover").live("click",function(){
		var id=$(this).attr("id");
		window.location.href="cameraManAction_getpictures.action?galaryId="+id+"&userId="+userId;
	})
	$("#selectMore").click(function(){
		$("#selectMore").hide();
		$("#_loading").show();
		var agrs={ids:ids,userId:userId};
		setTimeout(function(){
		var index=false;
		$.ajax({
	         type : "post", 
	          url : "loadMoreGalary.action",
	          data : agrs,
	          async : false,
	          dataType:"json",
	          success : function(data){
	        	$("#_loading").hide();
	        	if(data.results.length==0){
	        		index=true;
	        	}
	  			$.each(data.results,function(index,item){
	  				var price="";
					ids=ids+item.galaryId+",";
					if(item.price!="0"){
						price="<div class='transparent_black' style='width:30%;top: 75%;height: 20%;'>"+
					     "</div>"+"<div class='transparent_black_show' style='width:30%;text-align: center;top: 75%;height: 20%;font-size:45px;'>"+
					     "<div style='padding:10px;'>￥"+item.price+"</div>";
					}
					$("#selectMore").before("<table><table class='t2' style='height:20%;' cellpadding='20' cellspacing='0'><tr><td>"+
					"<div class='showeel'>"+
				    "<div style='position:relative; width: 100%;height:auto;z-index:1;'><img class='cover' style='height:500px' id='"+item.galaryId+"'  src='"+item.galaryCover+"'/></div>"+
				      price+
				     "</div>"+
				     "</div>"+"</td></tr>"+
					"<tr><td align='left'><div class='big300' style='width:100%'><span>"+item.subjectName+"</span></div>"+
					"<div><span>"+ct+"."+item.styleName+".看过 "+item.viewNumber+" .评论 "+item.commentNumber+"</span></div>"
					+"</td></tr>"+"</table>");
				})
			}
	     })
	    var l=ids.split(",").length;
		if(index){
			$("#hideMore").show();
			$("#selectMore").hide();
			$("#_loading").hide();
		}else{
			$("#selectMore").show();
		} 
		},500);
	})
	
})

function DrawImage(ImgD){
var image=new Image();
var iwidth = 1600;            //定义允许图片宽度，当宽度大于这个值时等比例缩小
var iheight = 500;            //定义允许图片高度，当宽度大于这个值时等比例缩小
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

/* $(function(){
	try{
	var winWidth=$('#contentDIV').width()-40;
	$('.showeel').find('img').each(function(index, element) {
		$(this).load(function(e) {
            var width=$(this).width();
			var height=$(this).height();
			if(width!=winWidth){
				$(this).width(winWidth);
				$(this).height("auto");
				/* $(this).height(height*(winWidth/width)); 
			}
        });
    });
	}catch(e){alert('init image error:'+e)};
});
 */

function showGalay(id) {
	var galaryId = id+"";
	window.location.href="cameraManAction_shareShowreel.action?galaryId="+galaryId+"&appinstall=1?";
	
}
</script>
</head>
<body>
	<div class="container-fluid">
	<!-- 标题图片 -->
		 <div class="row" style="height: 10%;background-color: #fff">
		 <img src="css/images/head.jpeg" class="img-responsive" style="width: 100%;max-width: 100%;" onclick="javascript:window.location='phonepage.html'"/>
		 </div>
		
	<!-- 头像 -->
	<div class="row-fluid" style="background-color: #fff;">
	<s:if test="#request.lpUser.headImage==null">
	 <div class="col-xs-12 col-md-12">
		<img src="/css/images/defaultouxiang.jpeg" class="center-block img-circle img-responsive">
	 </div>
	 </s:if>
	 <s:else>
	  <div class="col-xs-12 col-md-12" style="margin-top: 5%;">
		<img src="<%=headImage %>" class="center-block img-circle img-responsive">
	 </div>
	 </s:else>
	 <div class="col-xs-12 col-md-12" style="margin-top: 5%;text-align:center;margin-bottom: 5%;">
	 	<div>
	 	<span class="cameraMan">摄影师:<s:property value="#request.lpUser.realName"/></span>
	 	</div>
	 	<div><span class="camera_city"><s:property value="#request.lpUser.lpCity.cityName" />
	 	</span></div>
	 </div>
	 </div>
	
	<!-- 文字 -->		
	<hr/>
 		<div class="row">
 			<div class="col-xs-12 col-md-12">
 			<span class="text1">风格:
 				<s:iterator value="#request.lpUser.lpStyle" id="style" status="ls">
				<s:if test="#ls.last==true">
				<s:property value="#style.styleName"/>
				</s:if>
				<s:else>
				<s:property value="#style.styleName"/>、
				</s:else>
				</s:iterator></span>
			</div>
			<div class="col-xs-12 col-md-12">
 			<span class="text1">器材:<s:property value="#request.lpUser.grapherCarmer"/></span>
 			</div>
 			<div class="col-xs-12 col-md-12">
			<span class="text1">个人简介:<s:property value="#request.lpUser.grapherDesc"/></span>
 			</div>
 		</div>	
		<hr/>
			
		<div style="margin-bottom: 4%;">
		<span class="text2">作品集  <s:property value="#request.count"/></span> 
		</div>
			
		<!-- 一个作品集就使用一个div -->
		<s:iterator  value="#request.lpGalarys" id="lg" status="ls">
		<div class="row" style="margin-top: 5%;">
	    <div class="col-xs-12 col-md-12" style="padding: 0%;">
	    <img class="img-responsive" style="width: 100%;max-width: 100%;" 
	    id="<s:property value="#lg.galaryId"/>" src="<s:property value="#lg.galaryCover"/>" onclick="showGalay('<s:property value="#lg.galaryId"/>')"/>
	    </div>
		</div>
		
		<div class="row" style="width: 100%;max-width: 100%; margin-top: 5%">	
		<div class="col-xs-12 col-md-12">
		<span class="text2"><s:property value="#lg.subjectName" /></span>
		</div>
		</div>
		
		<div style="width: 100%;max-width: 100%;"><span class="text1"><s:property value="#request.lpUser.lpCity.cityName"/>
		<s:iterator id="ls" value="#lg.lpService.lpServiceDetail.lpStyle" status="status">
		<s:if test="#status.first==true">
		.<s:property value="#ls.styleName" />
		</s:if>
		</s:iterator>
		.看过 <s:property value="#request.viewNumber"/>
		.评论 <s:property value="#request.commentNumber"/>
		</span>
		</div>
		</s:iterator>
		</div>
</body>
</html>
