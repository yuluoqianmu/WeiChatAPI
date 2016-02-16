<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <base href="<%=basePath%>">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="${pageContext.request.contextPath}"/>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/style1.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.0.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/style1.css" rel="stylesheet" type="text/css" />
    	<style type="text/css">  
	/*浮动 **/
   #div1 {
  		background-color:#fff;
        border:5px solid rgba(0,0,0, 0.4);
        height:300px;
        padding:1px; 
        width:550px;  
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
.delete{
  float:center;
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
div.findAll a:hover
{
	color: #056dae;
	background-color: #d6e8f1;
}
  
    </style>  
    <script type="text/javascript">
    $(function(){
    	$(".locations").change(function(){
    			var id = $(this).attr("id");
    			var newLocation = $(this).val();
    			window.location.href="${pageContext.request.contextPath}/operation_updateCityLocation.action?cityId="+id+"&newLocation="+newLocation;
    	});
    	
    	//点击删除城市
    	$(".delete").click(function(){
    		var cityId = $(this).attr("id");
    		var personNum = $(this).attr("num");
    		if(personNum > 0)
			{
				alert("不可删除！");
				return false;
			}
    		if(confirm("您确定要删除该城市么？"))
    		{
    			window.location.href="${pageContext.request.contextPath}/operation_deleteCity.action?cityId="+cityId;
    		}
    	});
    	//点击增加城市
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
        
        $("#Form1").submit(function(){
        	if($("#cityName").val() == "")
        	{
        		if($("#theName").length == 0)
        		{
        			$("#cityName").after("<font id='theName' style='color:red'>不能为空!</font>");
        		}
        		return false;
        	}else{
        		$("#theName").remove();
        	}
        	if($("#warning").length > 0)
        	{
        		return false;
        	}
        	return true;
        });
        
        $("#topage").change(function(){
  		  var page = $(this).val();
  		  window.location.href="${pageContext.request.contextPath}/operation_cityList.action?nowPage="+page;
  	  });
        
        $("#cityName").change(function(){
        var val = $(this).val();
        	$.ajax({
        	type:"post",
					url:"${pageContext.request.contextPath}/validateCity.action",
					cache:false,
					dataType:"json",
					data:{"cityName":val},
					success:function(data)
					{
						if(data.cunzai == true)
						{
						    if($("#warning").length == 0)
						   {
						   		$("#cityName").after("<font style='color:red' id='warning'>已存在！</font>");
						   }
						}else
						{
							$("#warning").remove();
						}
					}
        	});
        });
        $(".findAll").click(function(){
        	var cityId= $(this).attr("attr");
    		window.location.href="${pageContext.request.contextPath}/operation_findAllMan.action?cityId="+cityId;    	
        });	
        $(document).ready(function(){
        	$('.tablelist tbody tr:odd').addClass('odd');
        })
    });
    //修改专题状态
    function changeOnLine(cityStatus,cityId,cityName)
    {
    
   // alert("ssss");
    //alert(cityStatus+" "+cityId+" "+ cityName);
    	 var ison;
    	if(cityStatus == "false")
    	{
    		ison="上";
    	}
    	if(cityStatus == "true")
    	{
    		ison="下";
    	}
    	if(confirm("您确定要将【"+cityName+"】"+ison+"线么?"))
    	{
    		window.location.href="${pageContext.request.contextPath}/operation_changeStatus.action?cityId="+cityId+"&cityStatus="+cityStatus;
    	}
    }
    function gotoPage(pageTo)
    {
    	window.location.href="${pageContext.request.contextPath}/operation_cityList.action?nowPage="+pageTo;
    }
    
    </script>
    <title>城市列表</title>
  </head>
  
 <body>
 <div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="javascript:void(0)">运营后台</a></li>
    <li><a href="#">城市</a></li>
    </ul>
    </div>
  <div class="rightinfo">
     <div class="tools">
    	<ul class="toolbar">
        <li class="click"><span ><img src="${pageContext.request.contextPath}/images/t01.png" /></span>添加</li>
        </ul>        
        <ul class="toolbar1">
        </ul>
    </div>
    </div>
 <form id="articleForm" name="articleForm" method="post" action="operation_cityList.action">
    <table class="tablelist">
    	<tr>
    		<th width="8%">序号</th>
    		<th width="10%">城市名称</th> 
    		<th width="8%">摄影师数目</th>
    		<th width="15%">上线时间</th>
    		<%--<th width="20%">下线时间</th>--%>
    		<th width="5%">当前状态</th>
    		<th width="5%">操作</th>
    	</tr>
    	<c:forEach items="${requestScope.cityList}" var="city" varStatus="i">
    		<tr align="center">
    		<td>
    			<select id="${city.cityId}" class="locations">
    				<c:forEach begin="1" end="${fn:length(cityList)}" var="sequence">
    					<option value="${sequence}" <c:if test="${city.cityLocation == sequence}">selected</c:if> >${sequence}</option>
    				</c:forEach>
    			</select>
    		</td>
				<td>${city.cityName}</td>
				<td><div class="findAll" attr="${city.cityId}">${city.cammerManNumber}</div></td>
				<td>${city.onlineTime}</td>
				<td>
					<c:if test="${city.cityStatus == false}" var="isup">
						下线
					</c:if>
					<c:if test="${!isup}">
						上线
					</c:if>
				</td>    		
    			<td>
    			<div style="width:90%;margin-left: auto;margin-right: auto;">
					<c:choose>
						<c:when test="${city.cityStatus == false}">
							<img src="${pageContext.request.contextPath}/images/online.png" align="right" onclick="changeOnLine('${city.cityStatus}','${city.cityId}','${city.cityName}')" title="上线"/>
						</c:when>
						<c:otherwise>
							<img src="${pageContext.request.contextPath}/images/offline.png" align="right"  onclick="changeOnLine('${city.cityStatus}','${city.cityId}','${city.cityName}')" title="下线"/>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${city.cammerManNumber ==0 && city.cityStatus == false}">
							<div class="delete" num="${city.cammerManNumber}" id="${city.cityId}"  onclick="javascript:isDelete('${subject.subject_name}','${subject.subject_id}')"><img src="${pageContext.request.contextPath}/images/t03.png" title="此城市没有摄影师使用,并且已下线,可以删除"/></div>
						</c:when>
						<c:otherwise>
							<div><img src="${pageContext.request.contextPath}/images/notDelete.png" title="此城市已有摄影师使用，不允许删除"/></div>
						</c:otherwise>
					</c:choose>
    			</div>
    			</td>
    		</tr>
    	</c:forEach>
    </table>
<%--    operation_cityList.action--%>
  <table width="98%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="articleForm" title="记录" unit="条记录" actionName="operation_cityList.action"/>
			</td>
		</tr>
	</table>
</form>	
    <div  id="div1">
    <p class="close"><a href="javascript:void(0)" id="close">关闭</a></p>
   
     <div class="formtitle" style="margin-left:10px; width:530px;"><span>添加风格</span></div>
     <ul class="forminfo">
    <form id="Form1" name="Form1" method="post" action="${pageContext.request.contextPath}/operation_addCity.action">
   
    <li><label>城市名称:</label><input id="cityName" name="cityName" type="text" class="dfinput" /></li>
    <br/>
    <br/>
   <li><label> 是否上线:</label>
    	<input name="cityStatus" type="radio"  value="true" checked="checked"/>上线 
    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	<input name="cityStatus" type="radio"  value="false" />下线
    	</li>
    <input type="hidden" name="isTrueDelete" value="1">
   <br/>
   <br/>
   <li><input name="" type="submit" class="btn" value="确认保存" class="dfinput"/>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input name="" type="reset" class="btn" value="重置" class="dfinput"/></li>
     <ul class="forminfo">
    </form>
    </div>
  </body>
</html>
