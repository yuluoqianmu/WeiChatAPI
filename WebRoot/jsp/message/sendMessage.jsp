<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addMessage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.Jcrop.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.idTabs.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jquery/select-ui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/default.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath }/jquery/kindeditor-all-min.js"></script>
	 <script type="text/javascript">
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
	function check(){
		var title=document.getElementById("title").value;
		if(title==""){
			alert("请填写标题");
			return false;
		}
		var person=document.getElementsByName("person");
		for(var i=0;i<person.length;i++){
			if(person[i].checked){ 
				if(person[i].value=="one_userman"){
					if(document.getElementById("userman").value==""){
						alert("请填写用户");
						return false;
				 }
				}else if(person[i].value=="one_cameraman"){
					if(document.getElementById("cameraman").value==""){
						alert("请填写摄影师")
						return false;
					}
					
				}
			}
		}
	}
  </script>
  </head>
  
  <body>
  <br/>
   <ul class="forminfo">
    <form id="Form1" name="Form1" method="post" action="messageAction_addMessage.action" enctype="multipart/form-data" onsubmit="return check()">
    <li><label>标题</label><input id="title" name="title" type="text"  class="dfinput"/></li>
    <li><label>消息类型</label>
    	<input type="radio" name="messageType" id="systemMessage" value="5" checked="checked"/>系统消息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	<input type="radio" name="messageType" id="updateVersion" value="9" />版本升级
    	<input type="text" class="dfinput" id="versionNum" name="versionNum" value="请输入版本号" onblur="if(this.value==''){this.value=defaultValue}" onfocus="if(this.value==defaultValue){this.value=''}"/>
    </li>
    <li><label>发布对象</label>
    <br><input type="hidden"/></br>
    <br><input type="radio" id="person" name="person" value="all" checked="checked"/>全体用户+全体摄影师</br>
    <br><input type="radio" id="person" name="person" value="all_userman"/>全体用户</br>
    <br><input type="radio" id="person" name="person" value="all_cameraman"/>全体摄影师</br>
    <br><input type="radio" id="person" name="person" value="one_userman"/>单个用户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <select class="dfinput" name="userman" id="userman">
            <c:forEach items="${userList }" var="user">
            	<option value="${user.userName }">${user.nickName }</option>
            </c:forEach>
        </select>
        <%--<input type="text"  id="userman" name="userman" tip="输入用户账号" class="dfinput"/>--%></br>
    <br><input type="radio" id="person" name="person" value="one_cameraman"/>单个摄影师&nbsp;&nbsp;&nbsp;
    	<select class="dfinput" name="cameraman" id="cameraman">
    		<c:forEach items="${cameraList }" var="camera">
    			<option value="${camera.userName }">${camera.nickName }</option>
    		</c:forEach>
    	</select>
    	<%--<input type="text"  id="cameraman" name="cameraman" tip="输入摄影师账号" class="dfinput"/>--%></br>
    </li>
    <li><label style="width:800px;">内容编辑：(推荐设置:上传图片完成，在图片上右键单击，选择“图片属性”，设置图片属性宽：300px，并且图片居中显示)</label></li>
    <li>
      <textarea name="content" id="content" style="width:330px;height:600px;" ></textarea>
    </li>
    </ul>
    <li><input name="submit"  type="submit" class="btn" value="提交" class="dfinput" />
		<input name="reset" type="reset" class="btn" value="重置" class="dfinput" />
  </form>
          
  </body>
</html>
