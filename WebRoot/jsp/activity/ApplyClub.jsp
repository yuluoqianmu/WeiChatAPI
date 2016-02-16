<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@ page import="com.tencent.Sign" %>
<%@ page import="com.tencent.SignParam" %>
<%
String str = request.getRequestURL().toString();
if(request.getQueryString()!=null && !"".equals(request.getQueryString().trim())){
	str = str+"?"+request.getQueryString().trim();
}
if(str.indexOf("#")>0){
str = str.substring(0,str.indexOf("#"));
}

System.out.println("request:"+str);
SignParam param = Sign.signWithCache("wxc6361d9bea12abe9", "6cd6d38ef467accd7fe022fd69cc4078",str );
String timestamp = param.getTimestamp();
String nonceStr = param.getNonceStr();
String signature = param.getSign();
%>
<!DOCTYPE HTML>
<html>
<head>
<title>欢迎加入来拍社</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="报名">
<meta http-equiv="description" content="摄影师报名来拍社">
<!-- IE8一下支持-->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meat name="description" content="这是一个h5页面"/>
<meta name="img" content="${pageContext.request.contextPath}/images/001.jpg"/>

<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/css/validate/validate.css" rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/scripts/icheck/skins/red.css" rel="stylesheet">
<link href="../../css/ApplyClub/applyClub.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.11.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/validate/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validate.js" charset="UTF-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.metadata.js"></script>
<script src="${pageContext.request.contextPath}/scripts/icheck/icheck.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
wx.config({
    debug: false,
    appId: 'wxc6361d9bea12abe9',
    timestamp: '<%=timestamp%>',
    nonceStr: '<%=nonceStr%>',
    signature: '<%=signature%>',
    jsApiList: [
      'checkJsApi',
      'onMenuShareTimeline',
      'onMenuShareAppMessage',
      'onMenuShareQQ',
      'onMenuShareWeibo',
      'hideMenuItems',
      'showMenuItems',
      'hideAllNonBaseMenuItem',
      'showAllNonBaseMenuItem',
      'translateVoice',
      'startRecord',
      'stopRecord',
      'onRecordEnd',
      'playVoice',
      'pauseVoice',
      'stopVoice',
      'uploadVoice',
      'downloadVoice',
      'chooseImage',
      'previewImage',
      'uploadImage',
      'downloadImage',
      'getNetworkType',
      'openLocation',
      'getLocation',
      'hideOptionMenu',
      'showOptionMenu',
      'closeWindow',
      'scanQRCode',
      'chooseWXPay',
      'openProductSpecificView',
      'addCard',
      'chooseCard',
      'openCard'
    ]
});


wx.ready(function(){
	
	wx.showMenuItems({
		 menuList: ['menuItem:share:appMessage',//发送给朋友
		            'menuItem:share:timeline',//分享到朋友圈
		            'menuItem:favorite',//收藏
		            'menuItem:profile',//查看公众号（已添加）
		            'menuItem:addContact',//查看公众号（未添加）
		            'menuItem:openWithSafari',//在Safari中打开
		            'menuItem:share:email',//邮件
		            'menuItem:share:qq',//分享到qq
		            'menuItem:share:QZone',//分享到qq空间
		            'menuItem:share:weiboApp',//分享到微博
		            'menuItem:copyUrl',//复制链接
		            'menuItem:setFont',//调整字体
		            'menuItem:refresh'//刷新
		            ],
		         success: function (res) {
		          //alert('已显示“阅读模式”，“分享到朋友圈”，“复制链接”等按钮');
		        },
		        fail: function (res) {
		          //alert(JSON.stringify(res));
		        }
			});
					 //分享给朋友
				    wx.onMenuShareAppMessage({
				      title: '我是摄影师，我申请加入来拍社。',
				      desc: '来拍社，中国最大的自由摄影师社群，最好的摄影师都在这儿。',
				      link: 'api.ilaipai.com/LaiPai/jsp/activity/index.jsp',
				      imgUrl: 'http://img.ilaipai.com/laipailogo@300w_300h.png',
				      trigger: function (res) {
				        // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
				       // alert('用户点击发送给朋友');
				      },
				      success: function (res) {
				       // alert('已分享');
				      },
				      cancel: function (res) {
				       // alert('已取消');
				      },
				      fail: function (res) {
				       // alert(JSON.stringify(res));
				      }
				    });
				    
					//分享到朋友圈
				    wx.onMenuShareTimeline({
				        title: '我是摄影师，我申请加入来拍社。',
				        link: 'api.ilaipai.com/LaiPai/jsp/activity/index.jsp',
				        imgUrl: 'http://img.ilaipai.com/laipailogo@300w_300h.png',
				        trigger: function (res) {
				          // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
				         // alert('用户点击分享到朋友圈');
				        },
				        success: function (res) {
				        //  alert('已分享');
				        },
				        cancel: function (res) {
				         // alert('已取消');
				        },
				        fail: function (res) {
				         // alert(JSON.stringify(res));
				        }
				      });
					//分享到QQ
				    wx.onMenuShareQQ({
				        title: '我是摄影师，我申请加入来拍社。', // 分享标题
				        desc: '来拍社，中国最大的自由摄影师社群，最好的摄影师都在这儿。', // 分享描述
				        link: 'api.ilaipai.com/LaiPai/jsp/activity/index.jsp', // 分享链接
				        imgUrl: 'http://img.ilaipai.com/laipailogo@300w_300h.png', // 分享图标
				        success: function () { 
				           // 用户确认分享后执行的回调函数
				        	//alert('已分享');
				        },
				        cancel: function () { 
				           // 用户取消分享后执行的回调函数
				        }
				    });
					//分享到QQ
					wx.onMenuShareWeibo({
	    				title: '我是摄影师，我申请加入来拍社。', // 分享标题
	    				desc: '来拍社，中国最大的自由摄影师社群，最好的摄影师都在这儿。', // 分享描述
	    				link: 'api.ilaipai.com/LaiPai/jsp/activity/index.jsp', // 分享链接
	   					imgUrl: 'http://img.ilaipai.com/laipailogo@300w_300h.png', // 分享图标
	    				success: function () { 
	       				// 用户确认分享后执行的回调函数
	    					//alert('已分享');
	    				},
	    				cancel: function () { 
	       				 // 用户取消分享后执行的回调函数
	    				}
						});
				});

$(document).ready(function(){
$('input').iCheck({
    checkboxClass: 'icheckbox_square-red',
    radioClass: 'iradio_square-red',
    increaseArea: '20%' // optional
  });
});


function doSubmit() {
    validateForm();
    //do something else...
}
function ajaxSubmit(){
	  
	var userName = $("#userName").val().trim(); 
	var phoneNum = $("#phoneNum").val().trim();
	var sex = $("input[type='radio']:checked").val();
	var wechat = $("#wechat").val().trim();
	var city = $("#city").val().trim();
	var cameraType = $("#camraType").val().trim();
	var str="";    
	  $('input[name="shootType"]:checked').each(function(){    
		 str+=$(this).val()+",";   
	  }); 
	  var chk_value = str.substring(0,str.length-1).trim(); 
	 $.ajax({
			url:"${pageContext.request.contextPath}/applyClub",
			type:"POST",
			async: false,
			data:{
				  "userName":userName,
				  "sex":sex,
				  "phoneNum":phoneNum,
				  "wechat":wechat,
				  "city":city,
				  "cameraType":cameraType,
				  "chk_value":chk_value},  
			//data:$("#myForm").serialize(),
			dataType:"json",
			success:function(data){
				
				
				var shareTitle = "我是"+data.username+"，我已加入中国最大的摄影师社群来拍社"; 
				//alert(shareTitle);
				
				//显示功能按钮
		wx.showMenuItems({
		 menuList: ['menuItem:share:appMessage',//发送给朋友
		            'menuItem:share:timeline',//分享到朋友圈
		            'menuItem:favorite',//收藏
		            'menuItem:profile',//查看公众号（已添加）
		            'menuItem:addContact',//查看公众号（未添加）
		            'menuItem:openWithSafari',//在Safari中打开
		            'menuItem:share:email',//邮件
		            'menuItem:share:qq',//分享到qq
		            'menuItem:share:QZone',//分享到qq空间
		            'menuItem:share:weiboApp',//分享到微博
		            'menuItem:copyUrl',//复制链接
		            'menuItem:setFont',//调整字体
		            'menuItem:refresh'//刷新
		            ],
		         success: function (res) {
		          //alert('已显示“阅读模式”，“分享到朋友圈”，“复制链接”等按钮');
		        },
		        fail: function (res) {
		          //alert(JSON.stringify(res));
		        }
			});
					 //分享给朋友
				    wx.onMenuShareAppMessage({
				      title: shareTitle,
				      desc: '来拍社，中国最大的自由摄影师社群，最好的摄影师都在这儿。',
				      link: 'api.ilaipai.com/LaiPai/jsp/activity/index.jsp',
				      imgUrl: 'http://img.ilaipai.com/laipailogo@300w_300h.png',
				      trigger: function (res) {
				        // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
				       // alert('用户点击发送给朋友');
				      },
				      success: function (res) {
				       // alert('已分享');
				      },
				      cancel: function (res) {
				       // alert('已取消');
				      },
				      fail: function (res) {
				       // alert(JSON.stringify(res));
				      }
				    });
				    
					//分享到朋友圈
				    wx.onMenuShareTimeline({
				        title: shareTitle,
				        link: 'api.ilaipai.com/LaiPai/jsp/activity/index.jsp',
				        imgUrl: 'http://img.ilaipai.com/laipailogo@300w_300h.png',
				        trigger: function (res) {
				          // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
				         // alert('用户点击分享到朋友圈');
				        },
				        success: function (res) {
				        //  alert('已分享');
				        },
				        cancel: function (res) {
				         // alert('已取消');
				        },
				        fail: function (res) {
				         // alert(JSON.stringify(res));
				        }
				      });
					//分享到QQ
				    wx.onMenuShareQQ({
				        title: shareTitle, // 分享标题
				        desc: '来拍社，中国最大的自由摄影师社群，最好的摄影师都在这儿。', // 分享描述
				        link: 'api.ilaipai.com/LaiPai/jsp/activity/index.jsp', // 分享链接
				        imgUrl: 'http://img.ilaipai.com/laipailogo@300w_300h.png', // 分享图标
				        success: function () { 
				           // 用户确认分享后执行的回调函数
				        	//alert('已分享');
				        },
				        cancel: function () { 
				           // 用户取消分享后执行的回调函数
				        }
				    });
					//分享到QQ
					wx.onMenuShareWeibo({
	    				title: shareTitle, // 分享标题
	    				desc: '来拍社，中国最大的自由摄影师社群，最好的摄影师都在这儿。', // 分享描述
	    				link: 'api.ilaipai.com/LaiPai/jsp/activity/index.jsp', // 分享链接
	   					imgUrl: 'http://img.ilaipai.com/laipailogo@300w_300h.png', // 分享图标
	    				success: function () { 
	       				// 用户确认分享后执行的回调函数
	    					//alert('已分享');
	    				},
	    				cancel: function () { 
	       				 // 用户取消分享后执行的回调函数
	    				}
						});
					if(data.result!=0){
						alert(data.message);
					}else{
					//模态窗体弹出层
					$("#myModal").modal();
					}
			},
			error:function(data){
				alert(data.message);
				
			}
		});  
}
//重置
$(function(){
$("#toReset").click(function(){
	$("#userName").val("");
	$("#phoneNum").val("");
	$("#wechat").val("");
	$("#city").val("");
	$("#camraType").val("");
	$("input[type='radio']").each(function(){
		if(this.checked){
			this.checked=false;
		}
		});
	$("input[type='checkbox']").each(function(){
		if(this.checked){
		this.checked=false;
		}
		});
});
});

 
  </script>

</head>
<body>

	<div class="container">
	
		<div id="header">
		 <header style="text-align: center;">
			<image style="width:100%;" src="${pageContext.request.contextPath}/images/ApplyClub/acheader.png"/>
		 </header>
		</div>
		
		<form id="myForm"  onsubmit="return false;">
		
			<div class="form-group" style="margin-bottom: 0;">
			<label for="userName" class="text-muted">您的姓名</label>
			<input type="text" name="userName" class="form-control" id="userName" placeholder="请输入您的姓名">
			</div>
			
			<div class="form-group" style="margin-bottom: 0;">
			<label for="sex" id="sex" class="text-muted">性别</label>
			<label class="checkbox-inline">
      		<input type="radio" name="sex" id="sex1" style="vertical-align: middle;" value="0"> 
      		<font style="color: #505050;vertical-align: middle;">男</font>
   			</label>
  			 <label class="checkbox-inline">
      		<input type="radio" name="sex" id="sex2" value="1" style="vertical-align: middle;" > 
      		<font style="color: #505050;vertical-align: middle;">女</font>
  			</label>
			</div>
			
			<div class="form-group" style="margin-top:0;">
			<label for="phoneNum" class="text-muted">电话</label>
			<input type="text" name="phoneNum" class="form-control" id="phoneNum" placeholder="请输入您的电话">
			</div>
			
			<div class="form-group" style="margin-top: 30px;">
			<label for="wechat" class="text-muted">微信</label>
			<input type="text" name="wechat" class="form-control" id="wechat" placeholder="请输入您的微信号">
			</div>
			
			<div class="form-group" style="margin-top: 30px;">
			 <label for="city" class="text-muted">所在城市</label>
      		<input type="text" name="city" class="form-control" id="city" placeholder="请输入您所在的城市">
			</div>
			
			<div class="form-group" style="margin-top: 30px;">
			<label for="camraType" class="text-muted">相机类型</label>
			<input type="text" name="cameraType" class="form-control" id="camraType" placeholder="请输入您的相机类型">
			</div>
			
			<div class="form-group" style="margin-top: 30px;">
			 <label class="text-muted" id="shotType">拍摄类型</label>
			 
			 <div id="style1">
			 
			 <span class="check">
			<input type="checkbox" name="shootType" id="shootType1" value="婚纱摄影"> 
			<span class="textType">
			婚纱摄影</span></span>
			
			<span class="check">
			<input type="checkbox" name="shootType" id="shootType2" value="婚礼纪实">
			<span class="textType">
			 婚礼纪实</span></span>
			
			<span class="check">
			<input type="checkbox" name="shootType" id="shootType3" value="海外旅拍"> 
			<span class="textType">
			海外旅拍</span></span>
			</div>
			
			<div id="style2">
			
			<span class="check">
			<input type="checkbox" name="shootType" id="shootType4" value="亲子"> 
			<span class="textType">
			亲子</span></span>
			
			<span class="check">
			<input type="checkbox" name="shootType" id="shootType5" value="写真"> 
			<span class="textType">
			写真</span></span>
		
			<span class="check">
			<input type="checkbox" name="shootType" id="shootType6" value="全家福"> 
			<span class="textType">
			全家福</span></span>
			
			</div>
			
			<div id="style3">
			<span class="check">
			<input type="checkbox" name="shootType" id="shootType7" value="时尚"> 
			<span class="textType">
			时尚</span></span>
			
			<span class="check">
			<input type="checkbox" name="shootType" id="shootType8" value="商业人像">
			<span class="textType"> 
			商业人像</span></span>
			</div>
			</div>
			
			<div class="form-actions" style="margin-top: 30px;">  
			<button type="button" class="btn btn-primary" id="toReset">
			<font style="color:#ff3a4e;font-size: 13px;">重置</font></button>
  			<button type="submit" class="btn btn-primary" id="toSubmit" onclick="doSubmit()">点击提交</button>  
			</div> 
			 
		</form>
	</div>
	
	<!-- 模态窗体 -->
	<div class="modal fade" id="myModal" 
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
		<div class="modal-content" >
		<div class="modal-body">
			<img style="width: 100%;height: auto;" src="${pageContext.request.contextPath}/images/ApplyClub/tanceng2.png"/>
		</div>
		</div>
		</div>
		</div>
	
</body>
</html>