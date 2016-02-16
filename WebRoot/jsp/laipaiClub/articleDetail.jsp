<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<link href="<%=request.getContextPath()%>/css/select.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/jquery.autocomplete.css" rel="stylesheet" type="text/css"  />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.lightbox.packed.css" type="text/css" media="screen" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/select-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/editor/kindeditor.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath()%>/jquery/jquert.custom.progressbar.js">
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.lightbox.js"></script>
	<style type="text/css">  
	/*浮动 **/
    #addComment {
        background-color:#F0F5F7;
        border:5px solid rgba(0,0,0, 0.4);
        height:380px;
        left:50%;
        padding:1px; 
        width:540px;  
        position:absolute;  
        display:none;  
        left:0;  
        top:0;  
        z-index:2; 
 }
 #addComment p {
margin:0 0 12px;
height:24px;
line-height:24px;
background:#CCCCCC;
}
#addComment p.close {
text-align:right;
padding-right:10px;
}
#addComment p.close a {
 color:#fff;
 text-decoration:none;
} 
/**----------回复----------*/  
	/*浮动 **/
#addReply {
background-color:#F0F5F7;
    border:5px solid rgba(0,0,0, 0.4);
    height:480px;
    left:50%;
    padding:1px; 
    width:450px;  
    position:absolute;  
    display:none;  
    left:0;  
    top:0;  
    z-index:2;  
}
#addReply p {
margin:0 0 12px;
height:24px;
line-height:24px;
background:#CCCCCC;
}
#addReply p.close {
text-align:right;
padding-right:10px;
}
#addReply p.close a {
 color:#fff;
 text-decoration:none;
}    
    </style>  	
<script type="text/javascript">
				
   $(function () {  
	   var $win = $(window),  
       $div = $('#addComment'),  
       $mask = $('#mask'),  
       $close = $('#closeComment'),  
       flag = false;  
		
		$("#addLpComment").click(function () {                  
		   var clientH = $win.height(),  
		       clientW = $win.width(),  
		       divH = $div.height(),  
		       divW = $div.width();  
		   var t = (clientH - divH) / 2 + $win.scrollTop();  
		   var l = (clientW - divW) / 2 + $win.scrollLeft();  
		   $div.show(500).offset({ 'top': t , 'left': l });       
		   $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
		   flag = true;  
		
		   $("#closeComment").click(function () {  
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
		
		
		$(".replyComment").click(function () { 
		       var clientH = $(window).height();
		       var clientW = $(window).width(); 
		       var  divH = $('#addReply').height();
		       var  divW = $('#addReply').width(); 
		       var t = (clientH - divH) / 2 + $(window).scrollTop();  
		       var l = (clientW - divW) / 2 + $(window).scrollLeft();  
		       $('#addReply').css('display', 'block').offset({ 'top': t , 'left': l });  
		       $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $(window).scrollLeft(), top: $(window).scrollTop() });  
		       flag = true;  
		       
		       $("#closeReply").click(function () {  
		           flag = false;
		           $('#addReply').hide();  
		           $mask.hide();  
		       });  
		       $(window).scroll(setMask).resize(setMask);  
		       function setMask() {  
		           if (flag) {  
		        	   $('#addReply').css('display', 'block').offset({ 'top': ($(window).height() - $('#addReply').height()) / 2 + $(window).scrollTop(), 'left': ($(window).width() - $('#addReply').width()) / 2 + $(window).scrollLeft() });  
		               $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $(window).scrollLeft(), top: $(window).scrollTop() });  
		           } else {
		        	   $('#addReply').hide();  
		               $mask.hide();  
		           }  
		       }  
		  });
   });    
   
    
    //图片上传依赖表单
	function submitUpload(){
	        //alert(111);
		var option = {
				url:"<%=request.getContextPath()%>/uploadPic.action",//如果option中存在url那么就提交到url上，如果不存在url，提交到表单的action上
				type:"post",
				dataType:"text",
				data:{
					imgsFile:"imgsFile"
				},
				success:function(responseText){
					 //把json字符串转换成json对象
					var jsonObj = $.parseJSON(responseText);
					$("#ImgSrc").attr("src",jsonObj.realPath);
					$("#headImage").val(jsonObj.relativePath);  
				},
				error:function(){
					alert("系统错误");
				}
		};
		//通过ajax方式提交表单，页面没跳转
		$("#articleForm").ajaxSubmit(option);
	}
	    
	function online(laipaiId){
		if(confirm("确定上线此文章,在app中显示？")){
			var url= "<%=request.getContextPath()%>/updateArticleOnline.action?laipaiId="+laipaiId+"&status=1&flag=detail";
			location.href = url;
		}
	}
	
	function offline(laipaiId){
		if(confirm("确定下线此文章,在app中隐藏？")){
			var url= "<%=basePath%>/updateArticleOnline.action?laipaiId="+laipaiId+"&status=0&flag=detail";
			location.href = url;
		}
	}
	
	function getArticleDetail(){
		var url= "<%=basePath%>/getArticleDetail.action?laipaiId="+laipaiId;
		location.href = url;
	}
	
	function editArticle(laipaiId){
		var url= "<%=basePath%>/toEdit.action?laipaiId="+laipaiId;
		location.href = url;
	}
	
	function deleteLpComment(commentId){
		if(confirm("确定删除此评论？")){
			var laipaiId = "<s:property value='#request.club.laipaiId'/>";
			var url= "<%=basePath%>/deleteLpComment.action?commentId="+commentId+"&laipaiId="+laipaiId;
			location.href = url;
		}
	}
	
	$().ready(function() {
		$("#commenUserName").autocomplete("findUserByAccount.action", {  //当用户输入关键字的时候 ，通过 url的方式调用action的findStoreProdctListNameForJson方法
			minChars: 1,  //最小显示条数
			max: 12,  //最大显示条数
			autoFill: false,
			dataType : "json",  //指定数据类型的渲染方式
			extraParams: 
	        {   
	             ajaxAccount: function() 
	              { 
	               return $("#commenUserName").val();    //url的参数传递
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
	
</script>

</head>


<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="javascript:<%=request.getContextPath()%>/queryAllArticle.action;">来拍社管理</a></li>
    <li><a href="#">文章列表</a></li>
    <li><a href="#">文章详情</a></li>
    </ul>
    </div>
    <div class="rightinfo">
    <div class="tools">
    
    	<ul class="toolbar">
        <li class="click" id="editStatus">
	        <s:if test="#request.club.status == 0">
		        <a href="javascript:online('<s:property value="#request.club.laipaiId"/>');">
		        	<span><img src="images/online.png" title="点击修改状态为上线"/></span>上线 
		        </a>
	        </s:if>
			<s:if test="#request.club.status == 1">
				<a href="javascript:offline('<s:property value="#request.club.laipaiId"/>');">
					<span><img src="images/offline.png" title="点击修改状态为下线"/></span>下线
				</a>
			</s:if>
        </li>
        <li id="editArticle" onclick="editArticle('<s:property value="#request.club.laipaiId"/>');"><span><img src="images/t02.png" /></span>编辑</li>
        </ul>        
        <ul class="toolbar1">
        </ul>
    
    </div>
   	<div class="formtitle"><span>文章详情</span></div>
	   	<div class="formbody">
	    <ul class="forminfo">
	    <form id="articleForm" name="articleForm" method="post" action="saveArticle.action" enctype="multipart/form-data">
	    	<li>
	    		<label style="width: 100%;" >标题：&nbsp;&nbsp;<font style="font-size:15px;font-weight:bold"><s:property  value="#request.club.tile"/></font></label>
	    		<label><input type="button"/></label>
	    		<label><input type="button"/></label>
	    	</li>
			<li>
				<label style="width:10%">浏览量 ：<s:property  value="#request.club.viewNumber"/></label>
				<label style="width:10%">喜欢量：<s:property  value="#request.club.likeNumber"/></label>
				<label style="width:10%">位置：<s:property  value="#request.club.laipaiClubIndex"/></label>
				<label style="width:10%">状态：
					<s:if test="#request.club.status == 0"><font style="font-size:15px;font-weight:bold;color:red">已下线 </font></s:if>
					<s:if test="#request.club.status == 1"><font style="font-size:15px;font-weight:bold;color:green">上线 </font></s:if>
				</label>
			</li>
			<li><label>正文：</label></li>
			<li>
				<span style="width:500px;height:100%;"><c:out value="${club.content}" escapeXml="false"/></span>
			</li>
	    </div> 
    </div>
    
    <div class="formtitle">
    	<span>评论列表</span>
    </div>
    <div class="tools">
    	<ul class="toolbar">
        <li id="addLpComment"><span><img src="images/t01.png" /></span>添加评论</li>
        </ul>
    </div>
    <div class="formbody">
	    <ul class="forminfo">
	    <form id="lpCommentForm" name="lpCommentForm" method="post" action="queryArticleComment.action">
		    <table class="tablelist" align="center" width="100%" cellspacing="0" cellpadding="0">
	    	<thead>
	    	<tr>
		        <th width="5%">序号<i class="sort"><img src="images/px.gif" /></i></th>
		        <th width="9%">发布时间</th>
		        <th width="9%">发布者</th>
		        <th width="30%">发布内容</th>
		        <th width="6%">操作</th>
	        </tr>
	        </thead>
	        <tbody>
	   		
			<s:if test="#request.commentList!=null && #request.commentList.size()>0">
				<s:iterator value="#request.commentList" id="list" status="i">
			        <tr align="center">
				        <td><s:property  value="#i.getCount()"/></td>
				        <td><s:date format="yyyy-MM-dd HH:mm:ss" name="#list.createTime"/></td>
				        <td><s:property  value="#list.lpUser.nickName"/></td>
				        <td><s:property  value="#list.commentDetail"/></td>
				        <td>
					        <a href="javascript:void(0)" id="replyComment" class="replyComment" lang="${item.commentId}"><img  src="<%=basePath%>images/t02.png" title='回复' /></a>&nbsp;&nbsp;&nbsp;
					        <a href="javascript:deleteLpComment('<s:property value="#list.commentId"/>');"><img  src="<%=basePath%>images/t03.png" title='删除' /></a>
				        </td>
			        </tr>
		     	</s:iterator>
			</s:if>        
		    </table>
    
		    <table width="98%" class="splitPage">
				<tr>
					<td class="zi_3F6293" align="right">
						<page:page name="pageController" frmName="lpCommentForm" title="记录" unit="条记录" actionName="queryArticleComment.action"/>
					</td>
				</tr>
			</table>
  
	       </form>
	    </div> 
    </div>
   
    <!--新增评论浮动框  -->
    <div  id="addComment">
	    <p class="close"><a href="javascript:void(0)" id="closeComment">关闭</a></p>
	    <div class="formbody">
		    <div class="formtitle"><span>添加评论</span></div>
		    <ul class="forminfo">
		    <form id="addLpCommonForm" name="addLpCommonForm" method="post" action="addLpComment.action">
		    <input type="hidden" id="laipaiId" name="laipaiId" value="<s:property value='#request.club.laipaiId'/>"/>
		    <li><label>评论人</label><input type="text" id="commenUserName" name="commenUserName" style="width:72%;height:30px;"/></li>
		    <li><label>评论内容</label><textarea id="commentDetail" name="commentDetail" style="width:72%;height:130px;"></textarea><i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;145字以内</i></li>
		    <li><input name="" type="submit" class="btn" value="确认保存" class="dfinput"/>
		     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    <input name="" type="reset" class="btn" value="重置" class="dfinput"/>
		    </li>
		    </ul>
	    </div>
    </div>
    <div  id="mask">
    </div>
    
    <!--新增回复浮动框  -->
    <div  id="addReply">
	    <p class="close"><a href="javascript:void(0)" id="closeReply">关闭</a></p>
	    <div class="formbody">
		    <div class="formtitle"><span>添加回复</span></div>
		    <ul class="forminfo">
			    <form id="addReplyForm" name="addReplyForm" method="post" action="<%=request.getContextPath()%>/adduser.action">
			    <input type="hidden" id="laipaiId" name="laipaiId" value="<s:property value='#request.club.laipaiId'/>"/>
			    <li><label>回复内容</label><textarea id="reply" name="reply" style="width:330px;height:170px;"></textarea><i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;200字以内</i></li>
			    <li><input name="" type="submit" class="btn" value="确认保存" class="dfinput"/>
			    <input name="" type="reset" class="btn" value="重置" class="dfinput"/>
			    </li>
		    </ul>
	    </div>
    </div>
    <div  id="mask2">
    </div>    
  
 </form>
</body>

</html>
