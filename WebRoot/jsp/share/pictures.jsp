<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>摄影师:<s:property value='#request.lpUser.realName'/></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="作品集">
	<meta http-equiv="description" content="来拍">
	<link href="css/share.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="jquery/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="jquery/download.js"></script>
    <style type="text/css">
     .up_bt{
     border-bottom-color: #D1D1D1;
	 border-bottom-style: solid;
	 border-bottom-width: 1px;
	 border-top-color: #D1D1D1;
	border-top-style: solid;
	border-top-width: 1px;
     }
    </style>
    <script type="text/javascript">
    $(function(){
    	var ids="<s:property value='#request.ids'/>";
    	var count="<s:property value='#request.count' />";
    	var galaryId="<s:property value='#request.lgGalary.galaryId' />";
    	$("#expand").click(function(){
    		$("#service").show();
    		$(this).hide();
    		$("#shrink").show();
    	})
    	$("#shrink").click(function(){
    		$("#expand").show();
    		$(this).hide();
    		$("#service").hide();
    	})
    	$("#selectMore").click(function(){
    		$("#selectMore").hide();
    		$("#_loading").show();
    		var agrs={ids:ids,galaryId:galaryId};
        setTimeout(function(){
        var index=false;
		$.ajax({
	         type : "post",  
	          url : "loadMorePictures.action",  
	          data : agrs,
	          async : false,
	          dataType:"json",
	          success : function(data){
	        	$("#_loading").hide();
	        	if(data.results.length==0){
	        		index=true;
	        	}
	  			$.each(data.results,function(index,item){
					ids=ids+item.photoId+",";
					$("#select_tr").before("<tr><td><img class='cover' src='"+item.photoSrc+"'/></td></tr>");
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
    </script>
  </head>
  
  <body>
  <div class="top">
			<table border="0" class="t1">
				<tr>
					<td><img src="css/images/logo.png" style="margin-left:20px;" width="150" height="160"/></td>
					<td>
							<div><span class="big500">来拍</span></div>
							<div><span>移动互联网影像平台</span></div>
						</td>
					<td><input type="button" class="bt" value="立刻下载"></td>
				</tr>
			</table>
		</div>
	<div class="middle" style="width:90%;padding-top:5%;">
	<div class="t3"  style="height: auto;">
	<table cellpadding="10" cellspacing="10">
	<tr><td><img src="<s:property value="#request.lpUser.headImage" />" style="float:left;margin-left:30px"   class="image150" />
	<div style="float:left;margin-left:30px;margin-top:30px;"><table style="height:100%;text-align: left;">
	<tr><td class="big300">摄影师:<s:property value='#request.lpUser.realName'/></td></tr>
	<tr><td><s:property value="#request.lpUser.lpCity.cityName" /></td></tr></table></div>
	</td></tr>
	</table>
	</div>
	<div >
	<table style="height:auto;width:100%"  cellpadding="10" cellspacing="10">
	<tr>
	<td class="big300">
	<s:property value="#request.lgGalary.subjectName" />
	</td>
	</tr>
	<tr>
	<td align="center">
	<div style="width:100%;text-align: left;"><span><s:property value="#request.lpGalary.galaryDesc"/></span>
	</div>
	</td>
	</tr>
	<tr>
	<td>
	<table border="0" cellpadding="20" cellspacing="5"   style="text-align: left;width:100%;display:none;height:auto" id="service">
	<tr><td align="left" class="up_bt"><div style="float:left">服务价格</div><div style="float:right"><s:property value="#request.lgGalary.lpService.lpServiceDetail.price"/>/<s:property value="#request.lgGalary.lpService.lpServiceDetail.priceUnit"/></div></td></tr>
	<tr><td align="left" class="t3"><div style="float:left">拍摄天数</div><div style="float:right"><s:property value="#request.lgGalary.lpService.lpServiceDetail.shootTime"/>天</div></td></tr>
	<tr><td align="left" class="t3"><div style="float:left">照片张数</div><div style="float:right"><s:property value="#request.lgGalary.lpService.lpServiceDetail.pictureNum"/>张</div></td></tr>
	<tr><td align="left" class="t3"><div style="float:left">精修张数</div><div style="float:right"><s:property value="#request.lgGalary.lpService.lpServiceDetail.truingNum"/>张</div></td></tr>
	<tr><td align="left" class="t3"><div style="float:left">化妆</div><div style="float:right"><s:property value="#request.lgGalary.lpService.lpServiceDetail.facepaint"/></div></td></tr>
	<tr><td align="left" class="t3"><div style="float:left">服装</div><div style="float:right"><s:property value="#request.lgGalary.lpService.lpServiceDetail.clothes"/></div></td></tr>
	<tr><td align="left" class="t3">
	<ul>
	<li>附加说明</li>
	<s:if test="#request.lgGalary.lpService.lpServiceDetail.instructions != null && #request.lgGalary.lpService.lpServiceDetail.instructions != ''">
		<li><s:property value="#request.lgGalary.lpService.lpServiceDetail.instructions"/></li>
	</s:if>
	<s:else>
	    <li>无</li>
	</s:else>
	</ul>
	</td></tr>
	</table>
	</td>
	</tr>
	
	<tr>
	<td><a href="javascript:void(0)" id="expand">展开拍摄服务详细介绍</a><a href="javascript:void(0)" style="display: none;" id="shrink">收起</a></td>
	</tr>
	</table>
	<table>
	<s:iterator value="#request.photos" id="p">
	<tr>
	<td><img class="cover" src="<s:property value="#p.photoSrc" />"/></td>
	</tr>
	</s:iterator>
	<tr id="select_tr">
	<td>
	<s:if test="#request.count>#request.pageSize">
		<div class="middle"  id="selectMore"><a href="javascript:void(0)">查看更多照片</a></div>
		<div class="middle" style="display:none;" id="_loading"><img src="images/loading.gif" width="100" height="100"/></div>
		<div class="middle" style="display:none;" id="hideMore"><a href="javascript:void(0)" style="color:#857f7f">暂无更多照片</a></div>
	</s:if><s:else>
		<div class="middle" style="display: block;" id="hideMore"><a href="javascript:void(0)" style="color:#857f7f">暂无更多照片</a></div>
	</s:else>
	</td>
	</tr>
	</table>
	</div>
	</div>
  </body>
</html>
