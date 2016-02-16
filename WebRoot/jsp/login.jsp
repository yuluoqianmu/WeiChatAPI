<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ page isELIgnored="false" %>
<%@taglib uri="/struts-tags"  prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML><HEAD><TITLE>和佳汇智</TITLE>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css">
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<META name=keywords content=短信,短信平台,短信服务,短信营销,和佳汇智>

<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/faviconyh.ico" type="image/x-icon" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.3.2.min.js"></script>
<SCRIPT language=JavaScript type=text/JavaScript>

		var mainName=".qymas.com";
		var host=document.domain;
	    var childDomain="";
	    if(host.indexOf("www.")>-1){
	    	childDomain=host.substring(4,host.indexOf(mainName));
	    }
	    else{
	    	childDomain=host.substring(0,host.indexOf(mainName));
	    }
	    if(host.indexOf(mainName)>-1){
	    $.ajax({
      	  type:"post",
      	  url:"loadOrgLogo.action",
      	  data:{domain:childDomain},
      	  async:true,
      	  success:function(data){
      		  var r=eval("("+data+")");
      		  if(r.orgconfig.logo!=null&&r.orgconfig.logo!=""){
      		  $("#logo").append("<img src="+r.orgconfig.logo+" height='43' width='313'>");
      		  }
      		  $("#copyright").append("<span>"+r.orgconfig.copyright+"</span>");
      		  $("#companyEn").append("<span>"+r.orgconfig.companyEn+"</span>");
      		  $("#companyCn").append("<span>"+r.orgconfig.companyCn+"</span>");
      		  $("#companyTel").append("<span>"+r.orgconfig.companyTel+"</span>");
      	  }
        },'json');
	    }
		String.prototype.Trim = function() 
		{ 
		return this.replace(/(^\s*)|(\s*$)/g, ""); 
		} 

		var type=1;//验证码类型：1：手机验证码登录
		
		function submitfrom() {
			var userName = $("#userName").val();
			userName=userName.replace(/(^\s*)|(\s*$)/g, "");
			if(userName==""){
				$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;用户名不能为空");
				return false;
			}
			var userPass=$("#userPass").val();
			 if(userPass=="")
			 {
				 document.getElementById('userPass').focus();
				  $("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;密码不能为空");
				 return false;
			 }
			if($("#secure").attr("checked")){
				if(!validateNameMobile()){
					return false;
				}
				var vercode=$("#vercode").val();
				var mobile=$("#mobile").val();
				if(vercode!=""){
					var flag;
					$.ajax({  
		          		type : "post",
		          		url : "checkLoginVercode.action",  
		          		data : "userName=" + userName+"&mobile="+mobile+"&vercode="+vercode+"&type="+type,
		          		async : false,
		          		success : function(data){  
							var result=eval("("+data+")");
							flag=result.message;
		          		} ,
		          		error: function(data) {
			    			$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;脚本处理错"); 
		          		} 
		          	});
					if(flag=="false"){
						$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;验证码不正确"); 
						return false;
					}
				}else{
					 $("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;请输入验证码");
					 return false;
				}
			}
  			return true;
		}
		function reset() { 
  			document.form.reset();
		}
<%--
		function changeImg(){
			document.getElementById("checkCodeImg").src="imageServlet?nowDate="+new Date();
			//document.getElementById('checkCodeImg').src="imageServlet";
		}
--%>	
		function onKeyDown()
		{
			if (event.keyCode == 13)
			{
				event.returnValue=false;
				event.cancel = true;

				document.form.submit();
			}
		}
		
		function loginOnload()
		{
			if(this != top)
			{
				top.location = top.document.location.href;
			}
			
			document.getElementById('userName').focus();
		}

		function pwdfind(){
			var userName = $("#userName").val();
			location.href = "pwdfind.action?userName="+userName;
		}
		//进入页面判断安全登录按钮是否勾选
		//点击获取验证码按钮触发的事件
		$(document).ready(function(){
			$("#secure").click(function(){
				if($(this).attr("checked")){
					$("#secureLogin").show();
					$(".dlpage").height("250px");
					$("#secure").attr("value","0");
			    }else{
					$("#secureLogin").hide();
					$(".dlpage").height("220px");
					$("#secure").attr("value","1");
				}
			});
			$("#btn").click(function(){
				if(validateNameMobile() && checkSendVercodeNum()){
					sendVercode();
					time(this);
				}
			});
			
			
		});
		
	
		function validateNameMobile(){

			var userName = $("#userName").val();
			userName=userName.replace(/(^\s*)|(\s*$)/g, "");
			if(userName==""){
				$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;用户名不能为空");
				return false;
			}
			var mobile = $("#mobile").val();
			if(mobile==""){
				$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;手机号不能为空");
				return false;
			}else{
				if(!/^1[3|4|5|7|8][0-9]\d{8}$/.test(mobile)){
					$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;手机号格式有误");
					return false;
				}
			}
			var errorCode = "";
			if(userName!=""){
				$.ajax({  
	          		type : "post",
	          		url : "checkLoginUserName.action",  
	          		data : "userName=" + userName,
	          		async : false,
	          		success : function(data){  
						var result=eval("("+data+")");
						errorCode = result.message;
	          		} ,
	          		error: function(data) {
	          			$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;脚本处理错");
	          		} 
	          	}); 
				if(errorCode=="1"){
					$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;用户名错误");
					return false;
				}
				if(errorCode=="2"){
					$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;该用户已经被锁,请联系管理员");
					return false;
				}
			}

			if(mobile!=""){
				$.ajax({  
	          		type : "post",
	          		url : "checkBindUserMobile.action",  
	          		data : "userName=" + userName+"&mobile="+mobile,
	          		async : false,
	          		success : function(data){  
						var result=eval("("+data+")");
						errorCode = result.message;
	          		} ,
	          		error: function(data) {
	          			$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;脚本处理错");
	          		} 
	          	}); 
				if(errorCode=="1"){
          			$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;手机号未绑定");
					return false;
				}else if(errorCode=="0"){
          			$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;检测手机是否绑定出现异常，请联系系统管理员");
					return false;
				}
				
			}
			
			return true;
		}

		function checkSendVercodeNum(){
			var userName = $("#userName").val();
			userName=userName.replace(/(^\s*)|(\s*$)/g, "");
			var mobile = $("#mobile").val();
			var errorCode = "";
			$.ajax({  
          		type : "post",
          		url : "checkSendVercodeNum.action",  
          		data : "userName=" + userName+"&mobile="+mobile+"&vercodeType=1",
          		async : false,
          		success : function(data){  
					var result=eval("("+data+")");
					errorCode = result.message;
          		} ,
          		error: function(data) {
          			$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;脚本处理错");
          		} 
          	}); 
			if(errorCode!="1"){
      			$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;今天发送验证码次数已达"+errorCode+"次，不能再发");
				return false;
			}
			return true;			
		}
		
		var wait=60;
		function time(o) {
				if (wait == 0) {
					o.removeAttribute("disabled");			
					o.value="获取验证码";
					wait = 60;
				} else {
					o.setAttribute("disabled", true);
					o.value="重新发送(" + wait + ")";
					wait--;
					setTimeout(function() {
						time(o)
					},
					1000)
				}
			}
		//发送验证码
		function sendVercode(){
			var userName = $("#userName").val();
			var mobile = $("#mobile").val();
			var mb={userName:userName,mobile:mobile,type:type};
			$.post("sendVercode.action",mb,function(data){
				var result=eval("("+data+")");
				if(result.message=="9"){
          			$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;验证码发送失败!");
					return false;
				}else if(result.message=="3"){
          			$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/cha.gif'>&nbsp;验证码扣费用户余额不足!");
					return false;
				}else{
          			$("#show_msg").empty().append("<img src='<%=request.getContextPath()%>/images/icon_xz.gif'>&nbsp;验证码发送成功");
					return;
				}
			});
		}
		
</SCRIPT>

</HEAD>
<body onload="loginOnload()">
   <div class="dlmain">
     <div class="dlhead">
       <div class="dllogo" id="logo"></div>
       <div class="dlname"><img src="<%=request.getContextPath()%>/images/login/titlename.png" width="334" height="43" /></div>
     </div>
     <form method="post" name="form" action="login.action" onsubmit="return submitfrom()">
     <div class="cel"></div>
     <!-- /css/style.css文件定义了背景图片 -->
     <div class="dlcontent02">
       <div class="dlpage">
         <div class="dltext">
           <div class="left">用户名：</div>
           <div class="right" style="width:188px">
             <input name="userName" id="userName" type="text" onpaste="return false"  value="${userName}" class="dlinput" maxlength="15" />
           </div>
         </div>
         <div class="cel"></div>
         <div class="dltext">
           <div class="left">密&nbsp;&nbsp;&nbsp;码：</div>
           <div class="right" style="width:188px"><input onpaste="return false" id="userPass" name="userPass" type="password" class="dlinput" maxlength="15"/></div>
         </div>
         <%--<div class="cel"></div>
         <div class="dltext" style="width:185px">
           <div class="left">验证码：</div>
           <div class="right" style="width:85px"><input  type="text" name="vercode" id="vercode" style="width:75px" class="dlinput" maxlength="4"/></div>
         </div>
         <div style="float:right; width:94px; height:33px;"><img src="imageServlet" name="d" id="checkCodeImg" onclick="changeImg()"  width="94" height="35" style="background-color: #CCCCCC" align="absmiddle"></div>
         --%>
         <div id="secureLogin" style="display: none;">
          <div class="cel"></div>
         	<div class="dltext">
				 <div class="left">手机号：</div>
				<div class="right" style="width:188px">
					<input id="mobile" name="mobile" type="text" class="dlinput"  maxlength="15"/>
					</div>
				<span id="showMobile" ></span>
			</div>
		<div class="cel"></div>
       	  <div class="dltext" style="background-image: url('<%=request.getContextPath()%>/images/login/yanzhengkuang.png');">
		 	<div class="left">验证码：</div>
			<div class="right" style="width:188px" >
			<input id="vercode" name="vercode" height="100" size="6" type="text" class="dlinput"  maxlength="6"/>
			<input type="button" style="margin-left:6px;" id="btn"  value="获取验证码" />
		</div>
		</div>
         </div>
         <div class="cel"></div>
         <div>
         &nbsp;&nbsp;&nbsp;&nbsp;
         <label for="secure">
		 <input name="secure" id="secure" value="1" type="checkbox" />
		 	安全登录
		 </label>
		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       	 <a href="javascript:pwdfind();">
       	 <span class="zi_9d9d9d" style="float:center; width:100px">忘记密码？</span>
       	 </a>
         </div>
         <div class="cel"><div style="color:red;font-size:15px;text-align: center;" id="show_msg">
         <s:if test="#request.loginerror != null">
         <img src="<%=request.getContextPath()%>/images/cha.gif">&nbsp;<s:property value="#request.loginerror"/>
         </s:if>
         </div></div>
         <div class="cel"></div>
         <div style="float:left; height:50px; width:290px; text-align:center">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center"><input  type="submit" value="" style="background-image:url('<%=request.getContextPath()%>/images/login/dl.gif'); border:0px;width:102px;height:35px;cursor: pointer;"></td>
    <td align="center"><input  type="reset" value=""  style="background-image:url('<%=request.getContextPath()%>/images/login/cz.gif'); border:0px;width:102px;height:35px;cursor: pointer;"></td>
  </tr>
</table>

         </div>
       </div>
     </div>
     </form>
     <div class="dlfoot"><span id="companyCn"></span>  |  <span id="companyEn"></span>  |  <span id="companyTel"></span>  |  <span id="copyright"></span></div>
   </div>

</body>
</HTML>
