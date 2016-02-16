<%@page import="com.laipai.orderInfo.pojo.LpOrder"%>
<%@page import="com.laipai.userManInfo.pojo.LpComment"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>消息列表</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css"/>
    <link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	  function sendMessage(){ 
		  location.href="<%=request.getContextPath()%>/messageAction_getMessageId.action";
	  }
	  <%--
	  function on_off_line(messageType,messageStatus){
		  //上线
		  if(messageStatus=="0"){
			   if(window.confirm("确定上线该功能?")){
				   location.href="<%=request.getContextPath()%>/messageAction_onOffLine.action?messageType="+messageType+"&messageStatus="+messageStatus;
			  } 
		  }else if(messageStatus=="1"){
			  if(window.confirm("确定下线该功能?")){
				  location.href="<%=request.getContextPath()%>/messageAction_onOffLine.action?messageType="+messageType+"&messageStatus="+messageStatus;
			  }
		  }
		    
	  }
	  //通知（开启,关闭）
	  function openNotice(messageType,selectName,text){  
		  //得到下拉菜单值
		  var messageNotice=getValue(selectName);
		  var url="<%=request.getContextPath()%>/messageAction_openNotice.action?messageType="+messageType+"&messageNotice="+messageNotice;
		  if(messageNotice==0){ 
			  if(window.confirm(text+"-通知 功能开启？")){
				  $.ajax({
						type:"post",
						url:url,
						success:function(info){
							if(info=="success"){
								alert("通知功能已开启");
							}else{
								alert("通知功能开启失败");
							}
						}
					});  
			  }
		  }else{ 
			  if(window.confirm(text+"-通知功能关闭?")){
				  $.ajax({
						type:"post",
						url:url,
						success:function(info){
							if(info=="success"){
								alert("通知功能已关闭");
							}else{
								alert("通知功能关闭失败");
							}
						}
					}); 
			  }
		  }
	  }
	  function getValue(selectId){  
		  var obj=document.getElementById(selectId);
		  var selectValue=obj.options[obj.selectedIndex].value;
		  return selectValue;
	  }
	  //红泡（开启,关闭）
	  function openPush(messageType,selectName,text){
		  var messagePush=getValue(selectName);
		  var url="<%=request.getContextPath()%>/messageAction_openPush.action?messageType="+messageType+"&messagePush="+messagePush;
		  if(messagePush==0){
			  if(window.confirm(text+"-红泡 功能开启？")){
				  $.ajax({
					  type:"post",
					  url:url,
					  success:function(info){
						  if(info=="success"){
							  alert("红泡功能已开启");
						  }else{
							  alert("红泡功能开启失败");
						  }
					  }
				  });
			  }
		  }else{
			  if(window.confirm(text+"-红泡 功能关闭？")){
				  $.ajax({
					  type:"post",
					  url:url,
					  success:function(info){
						  if(info=="success"){
							  alert("红泡功能已关闭");
						  }else{
							  alert("红泡功能关闭失败");
						  }
					  }
				  });
			  }
		  }
	  }
	  --%>
	  
	  //修改功能状态
	  function updateMessageCode(messageCodeId,type,status,confirmWord){
		  if(confirm(confirmWord)){
		       location.href = "messageAction_updateMessageCode.action?messageCodeId="+messageCodeId+"&type="+type+"&status="+status;
		  }
	  }
	  
	  function msgTemplate(messageCodeId){
		  location.href = "messageAction_editMsgTemplate.action?messageCodeId="+messageCodeId;
	  }
	</script>
  
 
  </head>
  
  <body>
    <div class="place">
    	<span>位置：</span>
    	<ul class="placeul">
    	<li>首页</li>
    	<li>消息管理</li>
    	<li>消息列表</li>
    	</ul>
    </div>
    <br/>
    <div class="tools">
      <ul class="toolbar">
      	<li onclick="sendMessage()"><span><img src="images/t01.png"></span>发布消息</li>
      </ul>
    </div>
    <br/>
    <table class="tablelist">
    	<thead>
    		<%--<th><input type="checkbox"/></th>--%>
    		<th>序号</th>
    		<th>消息类型</th>
    		<th>数目</th>
    		<th>当前状态</th>
    		<th>通知</th>
    		<th>红泡</th>
    		<th>短信提醒</th>
    		<th>上线操作</th>
    	</thead>
    	<tbody>
    		<tr> 
    		    <td>1</td>
	    		<td><a href="<%=request.getContextPath() %>/orderAction_queryAllOrders.action">预约</a></td>
	    		<td>${orderCount}</td>
	    		<td>${orderMessage.onlineStatus==0?'上线':'下线'}</td>
	    		<td>
	    			<c:if test="${orderMessage.noticeAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_order','notice_alert','0','确定开启通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭通知，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${orderMessage.noticeAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_order','notice_alert','1','确定关闭通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启通知，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${orderMessage.redAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_order','red_alert','0','确定开启红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭红泡，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${orderMessage.redAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_order','red_alert','1','确定关闭红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启红泡，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<a href="javascript:msgTemplate('messageType_order');" style="vertical-align: center">短信模板</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    			<c:if test="${orderMessage.messageAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_order','message_alert','0','确定开启短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭短信提醒，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${orderMessage.messageAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_order','message_alert','1','确定关闭短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启短信提醒，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${orderMessage.onlineStatus==0 }">
	    				<a onclick="updateMessageCode('messageType_order','online_status','1','确定下线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/offline.png" title="当前为上线，点击此图标下线"/></a>
	    			</c:if>
	    			<c:if test="${orderMessage.onlineStatus==1 }">
	    				<a onclick="updateMessageCode('messageType_order','online_status','0','确定上线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/online.png" title="当前为下线，点击此图标上线"/></a>
	    			</c:if>
	    		</td>
    		</tr>
    		<tr>
    		    <td>2</td>
	    		<td><a href="<%=request.getContextPath() %>/messageAction_queryAllComments.action">评论</a></td>
	    		<td>${commentCount }</td>
	    		<td>${commentMessage.onlineStatus==0?'上线':'下线' }</td>
	    		<td>
	    			<c:if test="${commentMessage.noticeAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_comment','notice_alert','0','确定开启通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭通知，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${commentMessage.noticeAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_comment','notice_alert','1','确定关闭通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启通知，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${commentMessage.redAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_comment','red_alert','0','确定开启红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭红泡，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${commentMessage.redAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_comment','red_alert','1','确定关闭红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启红泡，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<a href="javascript:msgTemplate('messageType_comment');" style="vertical-align: center">短信模板</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    			<c:if test="${commentMessage.messageAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_comment','message_alert','0','确定开启短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭短信提醒，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${commentMessage.messageAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_comment','message_alert','1','确定关闭短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启短信提醒，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${commentMessage.onlineStatus==0 }">
	    				<a onclick="updateMessageCode('messageType_comment','online_status','1','确定下线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/offline.png" title="当前为上线，点击此图标下线"/></a>
	    			</c:if>
	    			<c:if test="${commentMessage.onlineStatus==1 }">
	    				<a onclick="updateMessageCode('messageType_comment','online_status','0','确定上线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/online.png" title="当前为下线，点击此图标上线"/></a>
	    			</c:if>
	    		</td>
    		</tr>
    		<tr>
    		    <td>3</td>
	    		<td><a href="<%=request.getContextPath()%>/messageAction_queryReply.action">回复</a></td>
	    		<td>${replyCount }</td>
	    		<td>${replyMessage.onlineStatus==0?'上线':'下线' }</td>
	    		<td>
	    			<c:if test="${replyMessage.noticeAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_reply','notice_alert','0','确定开启通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭通知，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${replyMessage.noticeAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_reply','notice_alert','1','确定关闭通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启通知，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${replyMessage.redAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_reply','red_alert','0','确定开启红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭红泡，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${replyMessage.redAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_reply','red_alert','1','确定关闭红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启红泡，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<a href="javascript:msgTemplate('messageType_reply');" style="vertical-align: center">短信模板</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    			<c:if test="${replyMessage.messageAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_reply','message_alert','0','确定开启短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭短信提醒，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${replyMessage.messageAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_reply','message_alert','1','确定关闭短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启短信提醒，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${replyMessage.onlineStatus==0 }">
	    				<a onclick="updateMessageCode('messageType_reply','online_status','1','确定下线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/offline.png" title="当前为上线，点击此图标下线"/></a>
	    			</c:if>
	    			<c:if test="${replyMessage.onlineStatus==1 }">
	    				<a onclick="updateMessageCode('messageType_reply','online_status','0','确定上线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/online.png" title="当前为下线，点击此图标上线"/></a>
	    			</c:if>
	    		</td>
    		</tr>
    		<tr>
    		    <td>4</td>
	    		<td><a href="<%=request.getContextPath() %>/messageAction_queryAllFollows.action">关注</a></td>
	    		<td>${followCount }</td>
	    		<td>${followMessage.onlineStatus==0?'上线':'下线' }</td>
	    		<td>
	    			<c:if test="${followMessage.noticeAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_follow','notice_alert','0','确定开启通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭通知，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${followMessage.noticeAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_follow','notice_alert','1','确定关闭通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启通知，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${followMessage.redAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_follow','red_alert','0','确定开启红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭红泡，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${followMessage.redAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_follow','red_alert','1','确定关闭红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启红泡，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<a href="javascript:msgTemplate('messageType_follow');" style="vertical-align: center">短信模板</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    			<c:if test="${followMessage.messageAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_follow','message_alert','0','确定开启短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭短信提醒，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${followMessage.messageAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_follow','message_alert','1','确定关闭短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启短信提醒，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${followMessage.onlineStatus==0 }">
	    				<a onclick="updateMessageCode('messageType_follow','online_status','1','确定下线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/offline.png" title="当前为上线，点击此图标下线"/></a>
	    			</c:if>
	    			<c:if test="${followMessage.onlineStatus==1 }">
	    				<a onclick="updateMessageCode('messageType_follow','online_status','0','确定上线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/online.png" title="当前为下线，点击此图标上线"/></a>
	    			</c:if>
	    		</td>
    		</tr>
    		<tr>
    		    <td>5</td>
	    		<td><a href="<%=request.getContextPath()%>/messageAction_queryAllSysMessage.action">系统消息</a></td>
	    		<td>${laipaiCount }</td>
	    		<td>${messageMessage.onlineStatus==0?'上线':'下线' }</td>
	    		<td>
	    			<c:if test="${messageMessage.noticeAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_systemMsg','notice_alert','0','确定开启通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭通知，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${messageMessage.noticeAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_systemMsg','notice_alert','1','确定关闭通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启通知，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${messageMessage.redAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_systemMsg','red_alert','0','确定开启红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭红泡，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${messageMessage.redAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_systemMsg','red_alert','1','确定关闭红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启红泡，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<a href="javascript:msgTemplate('messageType_systemMsg');" style="vertical-align: center">短信模板</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    			<c:if test="${messageMessage.messageAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_systemMsg','message_alert','0','确定开启短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭短信提醒，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${messageMessage.messageAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_systemMsg','message_alert','1','确定关闭短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启短信提醒，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${messageMessage.onlineStatus==0 }">
	    				<a onclick="updateMessageCode('messageType_systemMsg','online_status','1','确定下线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/offline.png" title="当前为上线，点击此图标下线"/></a>
	    			</c:if>
	    			<c:if test="${messageMessage.onlineStatus==1 }">
	    				<a onclick="updateMessageCode('messageType_systemMsg','online_status','0','确定上线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/online.png" title="当前为下线，点击此图标上线"/></a>
	    			</c:if>
	    		</td>
    		</tr>
    		<tr>
    		    <td>6</td>
	    		<td><a href="<%=request.getContextPath() %>/messageAction_queryAllAudits.action?checkStatus=1">审核通过</a></td>
	    		<td>${auditPassCount }</td>
	    		<td>${auditMessage.onlineStatus==0?'上线':'下线' }</td>
	    		<td>
	    			<c:if test="${auditMessage.noticeAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_check_success','notice_alert','0','确定开启通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭通知，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${auditMessage.noticeAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_check_success','notice_alert','1','确定关闭通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启通知，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${auditMessage.redAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_check_success','red_alert','0','确定开启红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭红泡，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${auditMessage.redAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_check_success','red_alert','1','确定关闭红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启红泡，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<a href="javascript:msgTemplate('messageType_check_success');" style="vertical-align: center">短信模板</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    			<c:if test="${auditMessage.messageAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_check_success','message_alert','0','确定开启短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭短信提醒，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${auditMessage.messageAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_check_success','message_alert','1','确定关闭短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启短信提醒，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${auditMessage.onlineStatus==0 }">
	    				<a onclick="updateMessageCode('messageType_check_success','online_status','1','确定下线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/offline.png" title="当前为上线，点击此图标下线"/></a>
	    			</c:if>
	    			<c:if test="${auditMessage.onlineStatus==1 }">
	    				<a onclick="updateMessageCode('messageType_check_success','online_status','0','确定上线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/online.png" title="当前为下线，点击此图标上线"/></a>
	    			</c:if>
	    		</td>
    		</tr>
    		<tr>
    		    <td>7</td>
	    		<td><a href="<%=request.getContextPath() %>/messageAction_queryAllAudits.action?checkStatus=-1">审核未通过</a></td>
	    		<td>${auditNotPassCount }</td>
	    		<td>${auditNotMessage.onlineStatus==0?'上线':'下线' }</td>
	    		<td>
	    			<c:if test="${auditNotMessage.noticeAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_check_failure','notice_alert','0','确定开启通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭通知，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${auditNotMessage.noticeAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_check_failure','notice_alert','1','确定关闭通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启通知，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${auditNotMessage.redAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_check_failure','red_alert','0','确定开启红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭红泡，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${auditNotMessage.redAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_check_failure','red_alert','1','确定关闭红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启红泡，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<a href="javascript:msgTemplate('messageType_check_failure');" style="vertical-align: center">短信模板</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    			<c:if test="${auditNotMessage.messageAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_check_failure','message_alert','0','确定开启短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭短信提醒，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${auditNotMessage.messageAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_check_failure','message_alert','1','确定关闭短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启短信提醒，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${auditNotMessage.onlineStatus==0 }">
	    				<a onclick="updateMessageCode('messageType_check_failure','online_status','1','确定下线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/offline.png" title="当前为上线，点击此图标下线"/></a>
	    			</c:if>
	    			<c:if test="${auditNotMessage.onlineStatus==1 }">
	    				<a onclick="updateMessageCode('messageType_check_failure','online_status','0','确定上线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/online.png" title="当前为下线，点击此图标上线"/></a>
	    			</c:if>
	    		</td>
    		</tr>
    		<tr>
    		    <td>8</td>
	    		<td><a href="<%=request.getContextPath()%>/messageAction_queryAllVersions.action">系统push</a></td>
	    		<td>${versionCount }</td>
	    		<td>${versionMessage.onlineStatus==0?'上线':'下线' }</td>
	    		<td>
	    			<c:if test="${versionMessage.noticeAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_system_push','notice_alert','0','确定开启通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭通知，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${versionMessage.noticeAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_system_push','notice_alert','1','确定关闭通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启通知，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${versionMessage.redAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_system_push','red_alert','0','确定开启红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭红泡，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${versionMessage.redAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_system_push','red_alert','1','确定关闭红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启红泡，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<a href="javascript:msgTemplate('messageType_system_push');" style="vertical-align: center">短信模板</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    			<c:if test="${versionMessage.messageAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_system_push','message_alert','0','确定开启短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭短信提醒，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${versionMessage.messageAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_system_push','message_alert','1','确定关闭短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启短信提醒，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${versionMessage.onlineStatus==0 }">
	    				<a onclick="updateMessageCode('messageType_system_push','online_status','1','确定下线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/offline.png" title="当前为上线，点击此图标下线"/></a>
	    			</c:if>
	    			<c:if test="${versionMessage.onlineStatus==1 }">
	    				<a onclick="updateMessageCode('messageType_system_push','online_status','0','确定上线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/online.png" title="当前为下线，点击此图标上线"/></a>
	    			</c:if>
	    		</td>
    		</tr>
    		<tr>
    		    <td>9</td>
	    		<td><a href="<%=request.getContextPath()%>/messageAction_queryAllLikeGallary.action">赞</a></td>
	    		<td>${likeCount }</td>
	    		<td>${likeMessage.onlineStatus==0?'上线':'下线' }</td>
	    		<td>
	    			<c:if test="${likeMessage.noticeAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_like','notice_alert','0','确定开启通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭通知，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${likeMessage.noticeAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_like','notice_alert','1','确定关闭通知功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启通知，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${likeMessage.redAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_like','red_alert','0','确定开启红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭红泡，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${likeMessage.redAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_like','red_alert','1','确定关闭红泡功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启红泡，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<a href="javascript:msgTemplate('messageType_like');" style="vertical-align: center">短信模板</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    			<c:if test="${likeMessage.messageAlert==1 }">
	    				<a onclick="updateMessageCode('messageType_like','message_alert','0','确定开启短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/close.jpg" width="45" title="当前为关闭短信提醒，点击此图标开启"/></a>
	    			</c:if>
	    			<c:if test="${likeMessage.messageAlert==0 }">
	    				<a onclick="updateMessageCode('messageType_like','message_alert','1','确定关闭短信提醒功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/open.jpg" width="45" title="当前为开启短信提醒，点击此图标关闭"/></a>
	    			</c:if>
	    		</td>
	    		<td>
	    			<c:if test="${likeMessage.onlineStatus==0 }">
	    				<a onclick="updateMessageCode('messageType_like','online_status','1','确定下线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/offline.png" title="当前为上线，点击此图标下线"/></a>
	    			</c:if>
	    			<c:if test="${likeMessage.onlineStatus==1 }">
	    				<a onclick="updateMessageCode('messageType_like','online_status','0','确定上线此功能？')" style="cursor: pointer;"><img src="<%=basePath%>images/online.png" title="当前为下线，点击此图标上线"/></a>
	    			</c:if>
	    		</td>
    		</tr>
    		
    		
    	</tbody>
    	</table>

    <table width="98%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="orderForm" title="记录" unit="条记录" actionName="orderAction_sortOrder.action"/>
			</td>
		</tr>
	</table>
  </body>
  </form>
</html>
