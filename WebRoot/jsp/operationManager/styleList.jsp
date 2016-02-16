<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>风格管理</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.0.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/style1.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css">
        	<style type="text/css">  
	/*浮动 **/
   #div1 {
  background-color:#fff;
        border:5px solid rgba(0,0,0, 0.4);
        height:300px;
        left:50%;
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
    	$(".delete").click(function(){
    		var styleId = $(this).attr("id");
    		var personNum = $(this).attr("num");
    		if(personNum > 0)
    			{
    				alert("不可删除！");
    				return false;
    			}
    		if(confirm("您确定要删除该风格么？")){
    		window.location.href="${pageContext.request.contextPath}/style_deleteStyle.action?styleId="+styleId;
    		}
    		
    	});
    	$("#Form1").submit(function(){
    	if($("#styleName").val() == "")
    	{
    		if($("#theName").length == 0)
    		{
    			$("#styleName").after("<font id='theName' style='color:red'>不能为空!</font>");
    		}
    		return false;
    	}else{
    		$("#theName").remove();
    	}
    	return true;
    	});
    	
    	   $("#topage").change(function(){
    	  		  var page = $(this).val();
    	  		  window.location.href="${pageContext.request.contextPath}/style_styleList.action?nowPage="+page;
    	  	  });
    	
    	$(".locations").change(function(){
    				var id = $(this).attr("id");
    				var newLocation = $(this).val();
    				window.location.href="${pageContext.request.contextPath}/style_updateStyleLocation.action?styleId="+id+"&newLocation="+newLocation;
    	});
    	$(".findAll").click(function(){
    		var styleId = $(this).attr("attr");
    		window.location.href="${pageContext.request.contextPath}/style_findAllMan.action?styleId="+styleId;    		
    	});
    });
     function gotoPage(pageTo)
    {
    	window.location.href="${pageContext.request.contextPath}/style_styleList.action?nowPage="+pageTo;
    }
    function updateStyle(styleId)
    {
    	window.location.href="${pageContext.request.contextPath}/style_findStyleById.action?styleId="+styleId;
    }
    function changeOnLine(styleStatus,styleId,styleName)
    {
   // alert("ssss");
    //alert(cityStatus+" "+cityId+" "+ cityName);
    	 var ison;
    	if(styleStatus == 0)
    	{
    		ison="上";
    	}
    	if(styleStatus == 1)
    	{
    		ison="下";
    	}
    	if(confirm("您确定要将【"+styleName+"】"+ison+"线么?"))
    	{
    		window.location.href="${pageContext.request.contextPath}/style_updateStyleStatus.action?styleId="+styleId+"&styleStatus="+styleStatus;
    	}
    }
    </script>
  </head>
  <body>
  <div class="rightinfo">
     <div class="tools">
    	<ul class="toolbar">
        <li class="click"><span ><img src="${pageContext.request.contextPath}/images/t01.png" /></span>添加</li>
        </ul>        
        <ul class="toolbar1">
        </ul>
    </div>
    </div>
<form id="styleForm" name="styleForm" method="post" action="style_styleList.action">
  <table class="tablelist">
    	<tr>
    		<th width="5%">序号</th>
    		
    		<th width="8%">风格名称</th>
    		<th width="5%">拥有摄影师数目</th> 
    		<th width="5%">风格类型</th> 
    		<th width="7%">上线时间</th>
    		<th width="5%">当前状态</th>
    		<th width="7%" >操作</th>
    	</tr>
    	<c:forEach items="${requestScope.list}" var="style" varStatus="i">
    		<tr align="center">
    			<td >
    				<select class="locations" id="${style.styleId}">
    					<c:forEach begin="1" end="${fn:length(list)}" var="lengthSize">
    						<option value="${lengthSize}" <c:if test="${style.styleLocation == lengthSize}">selected</c:if> >${lengthSize}</option>
    					</c:forEach>
    				</select>
    			</td>
				<td>${style.styleName}</td>
				<td><div  attr="${style.styleId}">${style.cammerManNumber}</div></td>
				<td>
					<c:if test="${style.styleStatus == 0}">系统风格</c:if>
					<c:if test="${style.styleStatus == 1}">摄影师自建风格</c:if>
				</td>
				<td ><fmt:formatDate value="${style.onlineTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<%--<td width="10%">
					<c:if test="${style.styleStatus == 0}">全局显示</c:if>
					<c:if test="${style.styleStatus == 1}">摄影师显示</c:if>
				</td>
    			--%>
    			<td  align="center">
    				<c:if test="${style.isOnline == 0}" var="isup">
						下线
					</c:if>
					<c:if test="${!isup}">
						上线
					</c:if>
    			</td>
    			<td >
    				<c:if test="${style.isOnline == 0}" var="isup">
						<img src="${pageContext.request.contextPath}/images/online.png" style="width: 20px;height: 20px;" onclick="changeOnLine('${style.isOnline}','${style.styleId}','${style.styleName}')" title="上线"/>
					</c:if>
					<c:if test="${!isup}">
						<img src="${pageContext.request.contextPath}/images/offline.png" style="width: 20px;height: 20px" onclick="changeOnLine('${style.isOnline}','${style.styleId}','${style.styleName}')" title="下线"/>
					</c:if>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<c:choose>
						<c:when test="${style.cammerManNumber ==0 && style.isOnline == 0}">
							<a class="delete" id="${style.styleId}"><img src="images/t03.png" title="此风格没有摄影师使用，并且已下线，可以删除"/></a>
						</c:when>
						<c:otherwise>
							<a><img src="${pageContext.request.contextPath}/images/notDelete.png" title="此风格有摄影师使用，不允许删除"/></a>
						</c:otherwise>
					</c:choose>
    		</td>
    			
    		</tr>
    	</c:forEach>
    </table>
    <table width="98%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="styleForm" title="记录" unit="条记录" actionName="style_styleList.action"/>
			</td>
		</tr>
	</table>
</form>         
      
     <div  id="div1">
    <!-- //<input type="button" value="关闭" id="close" /> -->
    <p class="close"><a href="javascript:void(0)" id="close">关闭</a></p>
      <div class="formbody">
    <div class="formtitle"><span>添加风格</span></div>
    <ul class="forminfo">
    <form id="Form1" name="Form1" method="post" action="${pageContext.request.contextPath}/style_addStyle.action">
    <li><label>风格名称:</label><input id="styleName" name="styleName" type="text" class="dfinput"  /></li><br/>
    <li><label>风格状态:</label>
    下线<input name="isOnline" type="radio" value="0" checked="checked"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
   上线<input name="isOnline" type="radio" value="1" />
    </li><br/><br/>
   <input type="hidden" name="isTrueDelete" value="1">
   <input type="hidden" name="styleStatus" value="0">
    <li><input name="" type="submit" class="btn" value="确认保存" class="dfinput"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input name="" type="reset" class="btn" value="重置" class="dfinput"/>
    </li>
    </form>
    </ul>
    </div>
  </body>
</html>
