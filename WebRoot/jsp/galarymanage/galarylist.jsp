<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <base href="<%=basePath%>">
<title>菜单页</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/jquery.alerts.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/adipoli.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.lightbox.packed.css" type="text/css" media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery.alerts.js"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery.ui.draggable.js"></script>
    <script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery.adipoli.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.lightbox.js"></script>
  </head>

<script language="javascript">
$(function(){	
	//导航切换
	$(".imglist li").click(function(){
		$(".imglist li.selected").removeClass("selected")
		$(this).addClass("selected");
	})
	
	/* $('img').adipoli({
		'startEffect' : 'normal',
		'hoverEffect' : 'popout'
	});  */	
})	
</script>
<script type="text/javascript">
$(function(){	
 var spanflag=true;
$("tr[id!=null]").dblclick(function(){
   var galleryID=$(this).attr('id');		
   window.location.href="galleryManAction_toEdit.action?galaryId="+galleryID;
});

$("#handIndex").click(function(){
   
   $(".showIndex").show();
    spanflag=false;

});

$("#smartIndex").click(function(){
 if(confirm("您确定要智能排序吗？（酱紫会打乱原有的手工排序滴。。。）"))  {
		
 if(!spanflag){
  $(".showIndex").hide();
  }
	$.ajax({
			url: "smartIndex.action",
			type: "post",
			success : function(result) {
			    alert("已去除手工排序");
			    location.reload();
			},
		    error:function(){
			alert('系统错误');
			}

		});	
			
			
			
			
			}
});




/* $("tr[id!=null]").click(function(){
 var galleryID=$(this).attr('id');
   jPrompt('输入位置号:','位置号', '调整作品集位置号', function(r) {
     if( r ) {
      if(isIntegerD( r )){      
	  $.ajax({
			url: "changeGalleryIndex.action",
			type: "post",
			data:{
				galleryID:galleryID,
				galleryIndex:r
					},
			success : function(result) {
			  alert(result);
			},
		    error:function(){
			alert('系统错误');
			}

		});

	} else {
			alert("请输入数字");
		return false;

			}

 }
 });

		}); */

		$("#addgalary").click(function() {
			window.location = "galleryManAction_toAddGallery.action"
		});

		$("#checkAll").click(function() {
			//alert(this.checked);
			if (this.checked) {
				$("input[type='checkbox']").each(function() {
					this.checked = true;
				});
			} else {
				$("input[type='checkbox']").each(function() {
					this.checked = false;
				});

			}

		});

	});
	$(document).ready(function() {

		$("#deleteall").click(function() {

			deleteUser();
		});

	});
	function deleteUser() {
		var f = document.getElementsByName("ids");
		var c = 0;
		var ids = "";
		var id = "";
		var i = 0;
		for ( var j = 0; j < f.length; j++) {
			if (f[j].checked) {
				i++;
				id = f[j].value;
				ids = ids + "&galleryId=" + id;
			}
		}
		ids = ids.substring(1, ids.length);
		if (i == 0) {
			alert("请至少选择一个作品集!");
			return;
		}
		if (confirm("确定要删除吗?")) {
			var url = "<%=request.getContextPath()%>/galleryManAction_deleteGallery.action?" + ids;
			document.location = url;
		}
  }
  
  function changeIndex(galleryID,subjectName,cameraAccount,galaryIndex){
   jPrompt(subjectName+'('+cameraAccount+')',galaryIndex, '调整作品集位置号', function(r) {
     if( r ) {
    var gCount =$("#galleryCount").val(); 
   /*  alert(r) ;
    alert(gCount);  */
      if(isIntegerD( r )){ 
       if(r-gCount>0){
        alert("请输入小于"+gCount+"的位置号");
        return false;
       }
           
	  $.ajax({
			url: "changeGalleryIndex.action",
			type: "post",
			data:{
				galleryID:galleryID,
				galleryIndex:r
					},
			success : function(result) {
			    alert("修改成功");
			    location.replace(location.href);
			    
			},
		    error:function(){
			alert('系统错误');
			}

		});

	} else {
	    alert("请输入数字");
		return false;

			}

 }
 });
     
  
  
  }
   function isIntegerD( str ){
   var galleryCount =$("#galleryCount").val(); 
    var regu = /^[-]{0,1}[0-9]{1,}$/;
    return regu.test(str);
  }
 function hideOrShowGallery(gallaryId,status){
    if(confirm("确定要做此操作?")){
    var page=$("#hiddenPage").val();
     //alert(page);
     var url="galleryManAction_queryall.ation&page="+page;
       var option = {
			url:"hideGallery.action",
			type:"post",
			dataType:"json",
			data:{
				galaryId:gallaryId,
				status:status
			},
			success:function(responseText){
				 alert("修改成功"); 
				 location.replace(location.href);
			},
			error:function(){
				alert("系统错误");
			}
	};
    
	    $.ajax(option);
	    }
 
 
 }
 
   
</script>
</head>


<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">作品集列表</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
    	<ul class="toolbar">
        <li id="addgalary"><span><img src="images/t01.png" /></span>添加</li>       
        <li id="deleteall"><span><img src="images/t03.png" /></span>删除</li>
        <li id="smartIndex"><span><img src="images/t04.png" /></span>智能排序</li> 
        <li id="handIndex"><span><img src="images/t02.png" /></span>手工排序</li> 
        </ul>
        
        
        <ul class="toolbar1">
      <!--   <li><span><img src="images/t05.png" /></span>设置</li> -->
        </ul>
    
    </div>
    
    <form id="galleryForm" name="galleryForm" method="post" action="<%=request.getContextPath()%>/galleryManAction_queryall.action" enctype="multipart/form-data">
    <table class="imgtable">
    
    
    <thead>
    <tr id=null>
    <th width="4%"><input name="" type="checkbox" value="" id="checkAll"/></th>
    <!-- <th width="5%">序号</th> -->
    <th width="16%">封面</th>
    <th width="9%">摄影师</th>
    <th width="13%">发布时间</th>
    <th width="6%">价格</th>
    <th width="6%">订单量</th>
    <th width="6%">观看数</th>
    <th width="6%">喜欢数</th>
    <th width="6%">评论数</th>
    <th width="8%">展示位置</th>
    <th width="14%">操作</th>
    </tr>
    </thead>
    <input type="hidden" id="hiddenPage" name="currentPage" value="${pageController.currentPage }"/>
    <s:if test="#request.galaryList!=null && #request.galaryList.size()>0">
      <input type="hidden" id="galleryCount" name="galleryCount" value="${galleryCount }"/>
      
		<s:iterator value="#request.galaryList" id="galary" status="st">
	<tr  id="<s:property value='#galary.galaryId'/>"> 
    <td><input  type="checkbox" name="ids" value="<s:property value='#galary.galaryId'/>" /></td>
   
    <td class="imgtd">
     <a href="<s:property value='galaryCover'/>" rel="lightbox" title="<s:property value='#galary.subjectName'/>">
    <img src="<s:property value='galaryCover'/>" height='70' title="点击看大图" />
     </a>
    </td>
    <td><s:property value="#galary.nickName"/></td>
    <td><s:date name="#galary.creatTime" format="yyyy-MM-dd HH:mm:ss"/></td>
    <td><s:property value="#galary.price"/></td>
    <td><s:property value="#galary.orderNum"/></td>
    <td><s:property value="#galary.viewNumber"/></td>
    <td><s:property value="#galary.likeNumber"/></td>
    <td><s:property value="#galary.commentNumber"/></td>
      <td>     
      <s:if test="#galary.controlIndex==1">
        <s:property value="#galary.galaryIndex"/>
        <input type="hidden" id="galaryIndex" value="<s:property value='#galary.galaryIndex'/>"/>     
      </s:if>
      <s:else>
                 智能排序
      </s:else>
     </td>
     <td>
      <s:if test="#galary.galaryStatus==0">
     <a href="javascript:void(0)" onclick="hideOrShowGallery('<s:property value='#galary.galaryId'/>',1)"  class="tablelink">APP中隐藏</a>
      </s:if>
      <s:if test="#galary.galaryStatus==1">
     <a href="javascript:void(0)" onclick="hideOrShowGallery('<s:property value='#galary.galaryId'/>',0)" class="tablelink">APP中显示</a>
      </s:if>
      <span class="showIndex" style="display:none; margin-top:3px;"><a href="javascript:void(0)" onclick="changeIndex('<s:property value='#galary.galaryId'/>','<s:property value='#galary.subjectName'/>','<s:property value="#galary.lpUser.userName"/>','<s:property value="#galary.galaryIndex"/>')">修改展示位</a></span>
     </td>
    </tr>
    </P>
     </s:iterator>
  </s:if>        
    
    </table> 
   <table width="98%" class="splitPage">
		<tr id=null>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="galleryForm" title="记录" unit="条记录" actionName="galleryManAction_queryall.action"/>
			</td>
		</tr>
	</table>
    
  </form>  
    <div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
        
      <div class="tipinfo">
        <span><img src="images/ticon.png" /></span>
        <div class="tipright">
        <p>是否确认对信息的修改 ？</p>
        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
        </div>
        </div>
        
        <div class="tipbtn">
        <input name="" type="button"  class="sure" value="确定" />&nbsp;
        <input name="" type="button"  class="cancel" value="取消" />
        </div>
    
    </div>
    
    </div>
    
    <div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
        
      <div class="tipinfo">
        <span><img src="images/ticon.png" /></span>
        <div class="tipright">
        <p>是否确认对信息的修改 ？</p>
        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
        </div>
        </div>
        
        <div class="tipbtn">
        <input name="" type="button"  class="sure" value="确定" />&nbsp;
        <input name="" type="button"  class="cancel" value="取消" />
        </div>
    
    </div>
    
<script type="text/javascript">
	$('.imgtable tbody tr:odd').addClass('odd');
	</script>
    
</body>

</html>

