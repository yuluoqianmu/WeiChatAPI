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
	<c:if test="${!empty msg}">
		if ('${msg}' != '') {
	    	alert("${msg}");
		}
	</c:if>
		function backToList()
		{
			window.location.href="queryAllArticle.action";
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
//				K('input[name=getHtml]').click(function(e) {
//					alert(editor.html());
//				});
//				K('input[name=isEmpty]').click(function(e) {
//					alert(editor.isEmpty());
//				});
//				K('input[name=getText]').click(function(e) {
//					alert(editor.text());
//				});
//				K('input[name=selectedHtml]').click(function(e) {
//					alert(editor.selectedHtml());
//				});
//				K('input[name=setHtml]').click(function(e) {
//					editor.html('<h3>Hello KindEditor</h3>');
//				});
//				K('input[name=setText]').click(function(e) {
//					editor.text('<h3>Hello KindEditor</h3>');
//				});
//				K('input[name=insertHtml]').click(function(e) {
//					editor.insertHtml('<strong>插入HTML</strong>');
//				});
//				K('input[name=appendHtml]').click(function(e) {
//					editor.appendHtml('<strong>添加HTML</strong>');
//				});
				K('input[name=clear]').click(function(e) {
					editor.html('');
				});
				
		});
		
		function contentAppend(content){
			if(editor.html().indexOf(content)==-1){
			   var img="<img width=320 src="+content+">";
			   editor.appendHtml(img);
			}
		} 
		
		function validateDate(){
			var laipaiId = "<s:property  value='#request.club.laipaiId'/>";
			var coverUrl = $("#coverUrl").val();
			var tile = $("#tile").val();
			var viewNumber = $("#viewNumber").val();
			var likeNumber = $("#likeNumber").val();
			var laipaiClubIndex = $("#laipaiClubIndex").val();
			var fileExtStr = "";
			//内容检测
			if(tile =="" || tile.length <=0){
				alert("请输入文章标题！");
				return false;
			}
			//封面图片
		  	if(coverUrl == "" || coverUrl.length <= 0){
		  		if(laipaiId == "" || laipaiId.length <= 0){
		  			alert("请选择封面图片！");	
		  			$("#coverUrl").attr("value","");
			  		return false;
				}
		  	}else{
		  		$("#fileFlag").attr("value","fileNotNull");
		  		var tmp_indexForPoint = coverUrl.lastIndexOf(".");
		  		if(tmp_indexForPoint != -1){
		  			fileExtStr = coverUrl.substring(tmp_indexForPoint+1);
		  		}
		  	}
		  	//检测文件扩展名
		  	var allowFileExtList = '.bmp.png.gif.jpeg.jpg';
		  	if(allowFileExtList.indexOf(fileExtStr) < 0){
		  		alert("上传文件类型错误,本系统支持上传的文件类型有:"+allowFileExtList);	
		  		$("#coverUrl").attr("value","");
		  		return false;
		  	}
		  	//检测数字
		  	var testNum = /^[0-9]\d*$/; 
		  	if(viewNumber!="" && !testNum.test(viewNumber)){
		  		alert("浏览量必须整数");	
		  		return false;
		  	}
		  	if(likeNumber!="" && !testNum.test(likeNumber)){
		  		alert("喜欢量必须为整数");	
		  		return false;
		  	}
		  	if(laipaiClubIndex!="" && !testNum.test(laipaiClubIndex)){
		  		alert("位置必须为整数");	
		  		return false;
		  	}
		  	if(viewNumber>999999999){
		  	    alert("浏览量不要超过999999999");	
		  		return false;
		  	}
		  	if(likeNumber>999999999){
		  	    alert("赞量不要超过999999999");	
		  		return false;
		  	}
		  	if(laipaiClubIndex>999999999){
		  	    alert("位置不要超过999999999");	
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
    	<s:if test="#request.club.laipaiId ==null ">新建文章</s:if>
    	<s:else>修改文章</s:else>
    	</span>
    </div>
    <ul class="forminfo">
    <form id="articleForm" name="articleForm" method="post" action="saveArticle.action" onsubmit="return validateDate()"  enctype="multipart/form-data">
    	<input type="hidden" id="laipaiId" name="laipaiId" value="<s:property  value='#request.club.laipaiId'/>" />
    	<!-- 标记是否有封面图片需要上传 :  fileNotNull有   null无 -->
    	<input type="hidden" id="fileFlag" name="fileFlag" />
    	
    	<li><label>标题：</label><input id="tile" name="tile" type="text" class="dfinput" maxlength="20" value="<s:property  value='#request.club.tile'/>"/><i>建议不超过15字</i></li>
    	<!-- 新建文章时，第一次上传封面 -->
    	<s:if test="#request.club.laipaiId ==null ">
			<li><label>封面：</label><input name="file" id="coverUrl" type="file" class="dfinput" /></li>
    	</s:if>
    	<!-- 修改文章时，显示上一次上传的封面，并提供修改 -->
    	<s:else>
    		<li><label>使用中的封面：</label><img src='<s:property  value="#request.club.coverUrl"/>' height='200' /></li>
    		<li><label>修改封面：</label><input name="file" id="coverUrl" type="file" class="dfinput" /></li>
    	</s:else>
    	 
		<li>
			<span>
			浏览量 ：<input type="text" style="width:7%" name="viewNumber" id="viewNumber" class="dfinput" value="<s:property  value='#request.club.viewNumber'/>"/>
			&nbsp;&nbsp;喜欢量：<input type="text" style="width:7%" name="likeNumber" id="likeNumber" class="dfinput" value="<s:property  value='#request.club.likeNumber'/>"/>
			&nbsp;&nbsp;位置：<input type="text" style="width:7%" name="laipaiClubIndex" id="laipaiClubIndex" class="dfinput" value="<s:property  value='#request.club.laipaiClubIndex'/>"/>
			</span>
		</li>
		<li><label style="width:800px;">内容编辑：(推荐设置:上传图片完成，在图片上右键单击，选择“图片属性”，设置图片属性宽：320px，并且图片居中显示)</label></li>
		<li>
			<textarea name="content" id="content" style="width:500px;height:600px;word-break:break-all;" ><s:property  value='#request.club.content'/></textarea>
		</li>
		<li><input name="submit"  type="submit" class="btn" value="提交" class="dfinput" />
			<input name="reset" type="reset" class="btn" value="重置" class="dfinput" />
			<input name="back" type="button" class="btn" value="返回" class="dfinput" onclick="backToList();"/></li>
       </form>
    </div> 
  </body>
</html>
