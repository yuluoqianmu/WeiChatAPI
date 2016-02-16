<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <base href="<%=basePath%>">
    <title>专题编辑</title>
     <link href="${pageContext.request.contextPath}/css/jbox/jbox.css" rel="stylesheet" type="text/css" />
       <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/style1.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.lightbox.packed.css" type="text/css" media="screen" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.form.min.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.lightbox.js"></script>
<style type="text/css">  
	/*浮动 **/
   #div1 {
  background-color:#fff;
        border:5px solid rgba(0,0,0, 0.4);
        height:480px;
        left:50%;
        padding:1px; 
        width:700px;  
        position:absolute;  
        display:none;  
        left:0;  
        top:0;  
        z-index:2;  
 }
 	/*浮动 **/
   #div2 {
  background-color:#fff;
        border:5px solid rgba(0,0,0, 0.4);
        height:480px;
        left:50%;
        padding:1px; 
        width:700px;  
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
#divSelect a:hover
{
	color: #056dae;
	background-color: #d6e8f1;
}

</style>  	
<script type="text/javascript">
	//新增页面
 function toSaveSubject()
	{
		window.location.href = "${pageContext.request.contextPath}/jsp/subject/toSaveSubject.jsp";
	}
	//修改专题页面
//function updateSubject(subject_id)
	//{
		//window.location.href = "${pageContext.request.contextPath}/subject_findSubjectById.action?subject_id="+subject_id;
	//}
	//修改专题状态
    function changeOnLine(onlineValue,subject_id,subject_name)
    {
    	var ison;
    	if(onlineValue == 0)
    	{
    		ison="上";
    	}
    	else
    	{
    		ison="下";
    	}
    	if(confirm("您确定要将【"+subject_name+"】专题"+ison+"线么？"))
    	{
    		 window.location.href="${pageContext.request.contextPath}/subject_changeStatus.action?subject_status="+onlineValue+"&id="+subject_id;
    	}
   		
    }
    function gotoPage(pageSize,nowPage)
    {
    	window.location.href="${pageContext.request.contextPath}/subject_subjectList.action?currentPage="+nowPage+"&pageSize="+pageSize;
    }
    function isDelete(subject_name,subject_id)
    {
    	if(confirm("您确定要删除【"+subject_name+"】该专题么？"))
    	{
    		window.location.href="${pageContext.request.contextPath}/subject_deleteSubject.action?subject_id="+subject_id;
    	}
    }
    function updateGallery(subject_id)
    {
    	window.location.href = "${pageContext.request.contextPath}/subject_editGalary.action?subject_id="+subject_id;
    }
    //行拖动
    var beginMoving=false;
 function MouseDownToMove(obj){
 obj.style.zIndex=1;
 obj.mouseDownY=event.clientY;
 obj.mouseDownX=event.clientX;
 beginMoving=true;
 obj.setCapture();
 }

function MouseMoveToMove(obj){
 if(!beginMoving) return false;
 obj.style.top = (event.clientY-obj.mouseDownY);
 obj.style.left = (event.clientX-obj.mouseDownX);
 }

 function MouseUpToMove(obj){
 if(!beginMoving) return false;
 obj.releaseCapture();
 obj.style.top=0;
 obj.style.left=0;
 obj.style.zIndex=0;
 beginMoving=false;
 var tempTop=event.clientY-obj.mouseDownY;
 var tempRowIndex=(tempTop-tempTop%25)/25;
 if(tempRowIndex+obj.rowIndex <0 )tempRowIndex=-1;
 else tempRowIndex=tempRowIndex+obj.rowIndex;
 if(tempRowIndex >= obj.parentElement.rows.length-1) tempRowIndex = obj.parentElement.rows.length-1;
 obj.parentElement.moveRow(obj.rowIndex,tempRowIndex);
 }
  $(function(){
	  $("#topage").change(function(){
		  var page = $(this).val();
		  window.location.href="${pageContext.request.contextPath}/subject_subjectList.action?currentPage="+page;
	  });
	  
		$(".locations").change(function(){
			var id = $(this).attr("id");
			var newLocation = $(this).val();
			window.location.href="${pageContext.request.contextPath}/subject_updateSubjectLocation.action?subject_id="+id+"&newLocation="+newLocation;
	});
		$(document).ready(function(){
        	$('.tablelist tbody tr:odd').addClass('odd');
        })
  }); 

</script>
  </head>
  
  <body>
   <div class="place">
    	<span>位置：</span>
    	<ul class="placeul">
    	<li>首页</li>
    	<li>发现管理</li>
    	<li>专题编辑</li>
    	</ul>
    </div>
  <div class="rightinfo">
     <div class="tools">
    	<ul class="toolbar">
        <li class="click" id="addSubject" onclick="toSaveSubject()"><span ><img src="${pageContext.request.contextPath}/images/t01.png" /></span>添加</li>
        </ul>        
        <ul class="toolbar1">
        </ul>
    </div>
    </div>
     <form id="serviceForm" name="serviceForm" action="subject_subjectList.action" method="post">
    <table class="tablelist" width="100%">
    	<tr>
    		<!-- <th>编号</th> -->
    		<th width="5%">专题序号</th> 
    		<th width="10%">专题名称</th>
    		<th width="10%">专题封面</th>
    		<th width="6%">作品数量</th>
    		<th width="10%">上线时间</th>
    		<th width="6%">浏览量</th>
    		<th width="6%">赞总量</th>
    		<th width="5%">上线状态</th>
    		<th width="6%">操作</th>
    	</tr>
    	<s:if test="#request.subjectList!=null && #request.subjectList.size()>0">
    		<c:forEach items="${requestScope.subjectList}" var="subject">
    		<tr align="center" ondblclick="updateGallery('${subject.subject_id}')"> <!-- style="cursor:move ;position:relative;" onmousedown='MouseDownToMove(this)' onmousemove='MouseMoveToMove(this)' onmouseup='MouseUpToMove(this);' -->
    			<%-- <td>${subject.seq_number}</td> --%>
    			<td>
    				<select id="${subject.subject_id}" class="locations" title="请选择专题在app首页的位置">
    					<c:forEach begin="1" end="${requestScope.maxLocation}" var="subject_location">
    						<option value="${subject_location}" <c:if test="${subject.subject_location == subject_location}">selected</c:if> >${subject_location}</option>
    					</c:forEach>
    				</select><%--
    			${subject.subject_location}--%>
    			</td>
    			<td><div id="divSelect"><a href="javascript:updateGallery('${subject.subject_id}');" style="display: block;">${subject.subject_name}</a></div></td>
    			<td>
    			<img height='70'  src="${subject.subject_img}?tempid=<%=Math.random()%>" title="点击看大图">         
    			</div></td>
    			<td>${subject.gallery_number}</td>
    			<td>${subject.online_time}</td>
    			<td>${subject.access_number}</td>
    			<td>${subject.like_number}</td>
    			<td>
	    			<c:if test="${subject.subject_status==1}">上线 </c:if>
	    			<c:if test="${subject.subject_status==0}">下线</c:if>
    			</td>
    			<td>
    				<c:if test="${subject.subject_status==1}">
    					<a href="javascript:changeOnLine(${subject.subject_status},'${subject.subject_id}','${subject.subject_name}');"><img src='<%=basePath%>images/offline.png' title='下线' /></a>
	    			</c:if>
	    			<c:if test="${subject.subject_status==0}">
	    				<a href="javascript:changeOnLine(${subject.subject_status},'${subject.subject_id}','${subject.subject_name}');"><img src='<%=basePath%>images/online.png' title='上线' /></a>
	    			</c:if>
	    			&nbsp;&nbsp;&nbsp;&nbsp;
    				<a href="javascript:isDelete('${subject.subject_name}','${subject.subject_id}');"><img src='<%=basePath%>images/t03.png' title='删除' /></a> 
    			</td>
    		</tr>
    		</c:forEach>
    		</s:if> 
    </table>
     <table width="98%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="serviceForm" title="记录" unit="条记录" actionName="subject_subjectList.action"/>
			</td>
		</tr>
	</table>
	</form>
  </body>
</html>
