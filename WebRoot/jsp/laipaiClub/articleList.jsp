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
<link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>
	<style type="text/css">  
	/*浮动 **/
   #div1 {
  background-color:#fff;
        border:5px solid rgba(0,0,0, 0.4);
        height:580px;
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
  
    </style>  	
<script type="text/javascript">
       $(function () {  
        var $win = $(window),  
                $div = $('#div1'),  
                $mask = $('#mask'),  
                $close = $('#close'),  
                flag = true;  
      
      
       $("#add").click(function () {
            location.href="toEdit.action";
        });
        
         $("#checkAll").click(function(){
	          //alert(this.checked);
	         if(this.checked){
	          $("input[type='checkbox']").each(function(){this.checked=true;});
	         }else{
	          $("input[type='checkbox']").each(function(){this.checked=false;});
	         	         
	         }
	      
	      
	      })       

    });     
  $(document).ready(function(){
	  $('.tablelist tbody tr:odd').addClass('odd');
  $("#deleteArticles").click(function(){
	  deleteArticles();
});


});
  function deleteArticles(){
        var f = document.getElementsByName("ids");
		var c=0;	
		var ids = "";
		var id = "";
		var i = 0;
		for(var j=0; j<f.length ;j++ ){
		    if(f[j].checked){
		    	i++;
		        id = f[j].value;
		        ids += id+",";
		    }
		}
					
		if(i == 0){
		     alert("请至少选择一个用户!");
		     return;
		}
		ids = ids.substring(0, ids.length-1);
		if(confirm("确定要删除吗?")) {
			document.location="<%=request.getContextPath()%>/deleteArticle.action?laiPaiId=" + ids;
		}
  }
  
    
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
				 //alert(responseText);
				var jsonObj = $.parseJSON(responseText);
				//alert(jsonObj.realPath);
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
		var url= "<%=request.getContextPath()%>/updateArticleOnline.action?laipaiId="+laipaiId+"&status=1&flag=list";
		location.href = url;
	}
}

function offline(laipaiId){
	if(confirm("确定上线此文章,在app中隐藏？")){
		var url= "<%=basePath%>/updateArticleOnline.action?laipaiId="+laipaiId+"&status=0&flag=list";
		location.href = url;
	}
}

function getArticleDetail(laipaiId){
	var url= "<%=basePath%>/getArticleDetail.action?laipaiId="+laipaiId;
	location.href = url;
}
</script>

</head>


<body>
<form id="articleForm" name="articleForm" method="post" action="<%=request.getContextPath()%>/queryAllArticle.action" enctype="multipart/form-data">
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="javascript:<%=request.getContextPath()%>/queryAllArticle.action;">来拍社管理</a></li>
    <li><a href="#">文章列表</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    <div class="tools">
    
    	<ul class="toolbar">
        <li class="click" id="add"><span><img src="images/t01.png" /></span>添加</li>
        <li id="deleteArticles"><span><img id="delete" src="images/t03.png" /></span>删除</li>
        </ul>        
        <ul class="toolbar1">
        </ul>
    
    </div>
    </div>
    
    <table class="tablelist" align="center" width="100%" cellspacing="0" cellpadding="0">
    	<thead>
    	<tr>
	        <th width="4%"><input type="checkbox" id="checkAll"/></th>
	        <th width="5%">序号<i class="sort"><img src="images/px.gif" /></i></th>
	        <th width="18%">文章标题</th>
	        <th width="18%">封面</th>
	        <th width="12%">上线时间</th>
	        <th width="6%">浏览量</th>
	        <th width="6%">赞总量</th>
	        <th width="6%">评论量</th>
	        <th width="6%">当前状态</th>
	        <th width="5%">操作</th>
        </tr>
        </thead>
        <tbody>
   
	<s:if test="#request.list!=null && #request.list.size()>0">
		<s:iterator value="#request.list" id="list" status="i">
	        <tr align="center" ondblclick="getArticleDetail('<s:property  value="#list.laipaiId"/>');">
	        <td><input type="checkbox"  name="ids" value="<s:property  value='#list.laipaiId'/>"  /></td>
	        <td><s:property  value="#i.getCount()"/></td>
	        <td title="<s:property  value='#list.tile'/>">
	        	<a href="javascript:getArticleDetail('<s:property  value="#list.laipaiId"/>');">
		        	<s:if test="#list.tile.length()>10">
			          <s:property value="#list.tile.substring(0,10)"/>····
			        </s:if>
			        <s:else>
			          <s:property value="#list.tile"/>
					</s:else>
				</a>
	        </td>
	        <td><img src='<s:property  value="#list.coverUrl"/>' title='上线' height='70' /></td>
	        <td><s:date format="yyyy-MM-dd HH:mm:ss" name="#list.onlineDate"/></td>
	        <td><s:property  value="#list.viewNumber"/></td>
	        <td><s:property  value="#list.likeNumber"/></td>
	        <td><s:property  value="#list.commentNumber"/></td>
	        <td>
		        <s:if test="#list.status == 0">已下线</s:if>
		        <s:else>上线</s:else>
	        </td>
	        <td>
		        <s:if test="#list.status == 0">
					<a href="javascript:online('<s:property value="#list.laipaiId"/>');"><img src='<%=basePath%>images/online.png' title='上线' /></a> 
				</s:if>
				<s:if test="#list.status == 1">
					<a href="javascript:offline('<s:property value="#list.laipaiId"/>');"><img  src="<%=basePath%>images/offline.png" title='下线' /></a>
				</s:if>
			</td>	
	        </tr>
     	</s:iterator>
	</s:if>        
    </table>
    
    <table width="98%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="articleForm" title="记录" unit="条记录" actionName="queryAllArticle.action"/>
			</td>
		</tr>
	</table>
  
    <div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
        
      <div class="tipinfo">
        <span><img src="images/ticon.png" /></span>
        <div class="tipright">
        <p>确认删除该信息</p>
        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
        </div>
        </div>
        
        <div class="tipbtn">
        <input name="" type="button"  class="sure" value="确定" />&nbsp;
        <input name="" type="button"  class="cancel" value="取消" />
        </div>
    
    </div>
     <!--新增用户浮动框  -->
    <div  id="div1">
    <p class="close"><a id="close">关闭</a></p>
    <div class="formbody">
    
    <div class="formtitle"><span>创建账号</span></div>
    
    <ul class="forminfo">
    
    <li><label>账号</label><input name="userName" type="text" class="dfinput" /><i>标题不能超过30个字符</i></li>
    <li><label>密码</label><input name="userPassword" type="password" class="dfinput" /><i>多个关键字用,隔开</i></li>
    <li><label>确认密码</label><cite><input name="" type="password"  class="dfinput" /></li>
    <li><label>昵称</label><input name="nickName" type="text" class="dfinput" /></li>
    <li><label>头像</label><img id='ImgSrc' src="images/jiejie.jpg" height="100" width="100" />
    <input type='hidden' name="headImage" id='headImage'  value='' reg2="^.+$" tip="亲！您忘记上传图片了。" />
    <input type='hidden' name="accountSource" value='0'/>
    <li><label>头像</label><input type="file" name='imgsFile' class="dfinput" onchange='submitUpload()'/></li>
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
  
 </form>
</body>

</html>
