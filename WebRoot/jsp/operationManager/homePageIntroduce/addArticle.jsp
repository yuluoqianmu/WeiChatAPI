<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>专题新增页面</title>
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
	<link href="<%=request.getContextPath()%>/css/select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/select-ui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/default.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath }/jquery/kindeditor-all-min.js"></script>

	<script type="text/javascript">
		function backToList()
		{
			window.location.href="queryHomePageIntroduce.action";
		}
		
		var editor;
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="content"]', {
				allowFileManager : true,
				 items : [

                          'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'cut', 'copy', 'paste',

                          'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',

                          'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',

                          'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '|',

                          'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',

                          'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',

                          'table', 'hr', 'emoticons', 'map',  'pagebreak', 'anchor'

                      ],

                filterMode : false

			});
			K('input[name=clear]').click(function(e) {
				editor.html('');
			});
				
		});
		
		function contentAppend(content){
			if(editor.html().indexOf(content)==-1){
			   var img="<img src="+content+">";
			   editor.appendHtml(img);
			}
		}
		
		function validateDate(){
			var laipaiId = "<s:property  value='#request.introduce.introduceId'/>";
			var imgUrl = $("#imgUrl").val();
			var content = $("#content").val();
			var title = $("#title").val();
			var fileExtStr = "";
			//内容检测
			if(title =="" || title.length <=0){
				alert("请输入文章标题！");
				return false;
			}
			//封面图片
		  	if(imgUrl == "" || imgUrl.length <= 0){
		  		if(laipaiId == "" || laipaiId.length <= 0){
		  			alert("请选择封面图片！");	
		  			$("#imgUrl").attr("value","");
			  		return false;
				}
		  	}else{
		  		$("#fileFlag").attr("value","fileNotNull");
		  		var tmp_indexForPoint = imgUrl.lastIndexOf(".");
		  		if(tmp_indexForPoint != -1){
		  			fileExtStr = coverUrl.substring(tmp_indexForPoint+1);
		  		}
		  	}
		  	//检测文件扩展名
		  	var allowFileExtList = '.bmp.png.gif.jpeg.jpg';
		  	if(allowFileExtList.indexOf(fileExtStr) < 0){
		  		alert("上传文件类型错误,本系统支持上传的文件类型有:"+allowFileExtList);	
		  		$("#imgUrl").attr("value","");
		  		return false;
		  	}
			
			//内容检测
			if(content =="" || content.length <=0){
				alert("请输入文章内容！");
				return false;
			}
		  	
			return true;
		}
	</script>
  </head>
  <body>
    <div class="formbody">
    <div class="formtitle">
    	<span>
    	<s:if test="#request.introduce.introduceId ==null ">新建文章</s:if>
    	<s:else>修改文章</s:else>
    	</span>
    </div>
    <ul class="forminfo">
    <form id="articleForm" name="articleForm" method="post" action="saveHomePageArticle.action" onsubmit="return validateDate()"  enctype="multipart/form-data">
    	<input type="hidden" id="introduceId" name="introduceId" value="<s:property  value='#request.introduce.introduceId'/>" />
    	<!-- 标记是否有封面图片需要上传 :  fileNotNull有   null无 -->
    	<input type="hidden" id="fileFlag" name="fileFlag" />
    	
    	<li><label>标题：</label><input id="title" name="title" type="text" class="dfinput" maxlength="20" value="<s:property  value='#request.introduce.title'/>"/></li>
    	<!-- 新建文章时，第一次上传封面 -->
    	<s:if test="#request.introduce.introduceId ==null ">
			<li><label>封面：</label><input name="file" id="imgUrl" type="file" class="dfinput" /></li>
    	</s:if>
    	<!-- 修改文章时，显示上一次上传的封面，并提供修改 -->
    	<s:else>
    		<li><label>使用中的封面：</label><img src='<s:property  value="#request.introduce.imgUrl"/>' title='上线' height='70' width='170' /></li>
    		<li><label>修改封面：</label><input name="file" id="imgUrl" type="file" class="dfinput" /></li>
    	</s:else>
    	 
		<li>
			<span>
			上线时间 ：<s:property  value="#request.introduce.onLineTime"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			下线时间：<s:property  value="#request.introduce.offLineTime"/>
			</span>
		</li>
		<li><label style="width:800px;">内容编辑：(推荐设置:上传图片完成，在图片上右键单击，选择“图片属性”，设置图片属性宽：300px，并且图片居中显示)</label></li>
		<li>
			<textarea name="content" id="content" style="width:330px;height:600px;" ><s:property  value='#request.introduce.content'/></textarea>
		</li>
		<li><input name="submit"  type="submit" class="btn" value="提交" class="dfinput" />
			<input name="reset" type="reset" class="btn" value="重置" class="dfinput" />
			<input name="back" type="button" class="btn" value="返回" class="dfinput" onclick="backToList();"/></li>
       </form>
    </div> 
  </body>
</html>
