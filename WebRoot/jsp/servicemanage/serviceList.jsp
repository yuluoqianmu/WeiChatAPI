<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="/struts-tags" prefix="s"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'servicelist.jsp' starting page</title>
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
  </head>
  <script type="text/javascript">
    function add(){
    	location.href="<%=request.getContextPath() %>/serviceAction_getStyle.action"
    }
    function modify(){
    	var serviceId_All=document.getElementsByName("serviceId");
    	var checkedNum=0;
    	var serviceId="";
    	for(var i=0;i<serviceId_All.length;i++){
    		if(serviceId_All[i].checked){
    			checkedNum++;
    			serviceId=serviceId_All[i].value;
    		}
    	}
    	if(checkedNum>1){
    		alert("只能选择一项服务进行更改");
    		return;
    	}
    	if(checkedNum==0){
    		alert("请选择服务");
    		return;
    	}
    	location.href="<%=request.getContextPath()%>/serviceAction_modifyService.action?serviceId="+serviceId;
    }
    function removeServices(){
    	var serviceId_All=document.getElementsByName("serviceId");
    	var checkedNum=0;
    	var serviceId="";
    	for(var i=0;i<serviceId_All.length;i++){
    		if(serviceId_All[i].checked){
    			checkedNum++;
    			serviceId+=serviceId_All[i].value+";";
    		}
    	}
    	if(checkedNum==0){
    		alert("请选择至少一项服务");
    	}else{
    		serviceId=serviceId.substring(0, serviceId.length-1);
    		if(confirm("确定删除这"+checkedNum+"项服务吗？")){
    			location.href="<%=request.getContextPath()%>/serviceAction_deleteService.action?serviceId="+serviceId;
    		}
    	}
    }
    
    function on_off_Service(serviceId,serviceStatus){
    	var jsp="serviceListJSP";
    	if(serviceStatus=='1'){
    		if(window.confirm("是否下架?")){
    			location.href="<%=request.getContextPath()%>/serviceAction_offService.action?serviceId="+serviceId+"&serviceListJSP="+jsp;
    		}
    	}else{
    		if(window.confirm("是否上架?")){
    			location.href="<%=request.getContextPath()%>/serviceAction_onService.action?serviceId="+serviceId+"&serviceListJSP="+jsp;
    		}
    	}
    }
    
    function selectService(serviceId){
     	location.href="<%=request.getContextPath()%>/serviceAction_queryById.action?serviceId="+serviceId;
    }
    
    $(function () {   
        $("#checkAll").click(function(){
	          //alert(this.checked);
	         if(this.checked){
	          $("input[type='checkbox']").each(function(){this.checked=true;});
	         }else{
	          $("input[type='checkbox']").each(function(){this.checked=false;});
	         	         
	         }
	      
	      
	      });
        $(document).ready(function(){
        	$('.tablelist tbody tr:odd').addClass('odd');
        })
       });               
  </script>
  <form id="serviceForm" name="serviceForm" action="serviceAction_queryAllService.action" method="post">
  <body>
    <div class="place">
    	<span>位置：</span>
    	<ul class="placeul">
    		<li><a >首页</a></li>
    		<li><a >服务管理</a></li>
    		<li><a >服务列表</a></li>
    	</ul>
    </div>
    <div class="rightinfo">
    <div class="tools">
      <ul class="toolbar">
      	<li onclick="javascript:add()"><span><img src="images/t01.png"></span>添加</li>
      	<li onclick="javascript:modify()"><span><img src="images/t02.png"></span>修改</li>
      	<li onclick="javascript:removeServices()"><span><img src="images/t03.png"></span>删除</li>
      </ul>
    </div>
    </div>
    <table class="tablelist">
    	<thead>
    	<tr>
          <th><input type="hidden"/></th>
    	  <th><input id="checkAll" type="checkbox"></th>
    	  <th>序号</th>
    	  <th>主题</th>
    	  <th>创建者</th>
    	  <th>创建时间</th>
    	  <th>当前状态</th>
    	  <th>操作</th>	
    	</tr>
    	</thead>
    	
    	<tbody>
    	<c:forEach items="${serviceInfo }" var="service" varStatus="status">
    	  <tr ondblclick="selectService('${service.serviceId}')" align="center">
    	    <td><input type="hidden" id="serviceId" value="${service.serviceId}"/></td>
    	    <td><input type="checkbox"  name="serviceId" id="serviceId" value="${service.serviceId }"/></td>
    	    <td><c:out value="${status.count }"></c:out></td>
    		<td><c:out value="${service.serviceName }"/></td>
    		<td>${service.nickName}</td>
    		<td><fmt:formatDate value="${service.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    		<td><c:out value="${service.serviceStatus==true?'上架':'已下架' }" /></td>
    		<c:if test="${service.serviceStatus==true }">
    		    <td><a onclick="on_off_Service('${service.serviceId}','1')" style="cursor: pointer;" ><img  src="<%=basePath%>images/offline.png" title='下线' /></a></td>	
    		</c:if>
    		<c:if test="${service.serviceStatus==false }">
    			<td><a onclick="on_off_Service('${service.serviceId}','0')" style="cursor: pointer;" ><img src='<%=basePath%>images/online.png' title='上线' /></a></td>
    		</c:if>
    		
    	<!--	<td><input type="button" id="on" value="${service.serviceStatus==true?'下架':'上架' }" onclick="on_off_Service('${service.serviceId}','${service.serviceStatus==true?'1':'0'}')"/></td>
    	 -->
    	  </tr>
    	</c:forEach>
    	
    	</tbody>
    </table>
     <table width="98%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="serviceForm" title="记录" unit="条记录" actionName="serviceAction_queryAllService.action"/>
			</td>
		</tr>
	</table>
  </body>
  </form>
</html>
