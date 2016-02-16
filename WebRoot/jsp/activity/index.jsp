<%@page import="java.util.logging.Logger"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎加入来拍社</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="报名">
<meta http-equiv="description" content="摄影师报名来拍社">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meat name="description" content="这是一个h5页面"/>
<link rel="stylesheet" href="../../scripts/swiper/css/swiper.min.css">
<link rel="stylesheet" href="../../scripts/swiper/css/animate.min.css">
<link rel="stylesheet" href="../../css/ApplyClub/adapter.css">
<script src="../../scripts/swiper/js/swiper.min.js"></script>
<script src="../../scripts/swiper/js/swiper.animate.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<style>
*{
	margin:0;
	padding:0;
}
html,body{
	height:100%;
}
.swiper-container {
	width: 100%;
    height: 100%;
	background:#fff;

}  

.swiper-slide{
	width:100%;
	height:auto;
	background:url(http://img.ilaipai.com/laipaiClubBg@800w_800h_80Q_1pr.jpg) no-repeat left top;
	background-size:100% 100%;
}
img{
	display:block;
	height: auto;
	max-width: 100%;
}

@-webkit-keyframes start {
	0%,30% {opacity: 0;-webkit-transform: translate(0,10px);}
	60% {opacity: 1;-webkit-transform: translate(0,0);}
	100% {opacity: 0;-webkit-transform: translate(0,-8px);}
}
@-moz-keyframes start {
	0%,30% {opacity: 0;-moz-transform: translate(0,10px);}
	60% {opacity: 1;-moz-transform: translate(0,0);}
	100% {opacity: 0;-moz-transform: translate(0,-8px);}
}
@keyframes start {
	0%,30% {opacity: 0;transform: translate(0,10px);}
	60% {opacity: 1;transform: translate(0,0);}
	100% {opacity: 0;transform: translate(0,-8px);}
}
.ani{
	position:absolute;
	}
.txt{
	position:absolute;
}
#array{
	position:absolute;z-index:999;-webkit-animation: start 1.5s infinite ease-in-out;
}

#left1{
width:88%;
left:20px;
/*margin-top:30%;*/
top:21%;
opacity:1;
z-index: 4;
visibility: visible;
}
#left2{
width:88%;
left:20px;
/*margin-top:55%;*/
top:33%;
opacity:1;
z-index: 2;
visibility: visible;
}
#left3{
width:88%;
left:20px;
/*margin-top:80%;*/
top:45%;
opacity:1;
z-index: 2;
visibility: visible;
}

#left4{
width:88%;
left:20px;
/*margin-top:105%;*/
top:58%;
opacity:1;
z-index: 2;
visibility: visible;
}
#left5{
width:88%;
left:20px;
/*margin-top:130%;*/
top:70%;
opacity:1;
z-index: 2;
visibility: visible;
}
#head1{
width: 60%;
text-align: center;
/*margin-top: 10%;*/
top:8%;
margin-left: 19%;
}
#buttom1{
width: 90%;
height:auto;
max-width:100%;
/*margin-top: 10%;*/
top:9%;
margin-left: 25px;
}
#buttom2{
width: 90%;
height:auto;
max-width:100%;
/*margin-top: 40%;*/
top:26%;
margin-left: 25px;
}
#buttom3{
width: 90%;
height:auto;
max-width:100%;
/*margin-top: 70%;*/
top:44%;
margin-left: 25px;
}
#buttom4{
width: 90%;
height:auto;
max-width:100%;
/*margin-top: 100%;*/
top:62%;
margin-left: 25px;
}
#btn{
width: 89%;
max-width:100%;
height:auto;
top: 85%;
margin-left: 5%;
}
</style>
<script type="text/javascript">


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
    //alert('已取消');
  },
  fail: function (res) {
    //alert(JSON.stringify(res));
  }
});

//分享到朋友圈
wx.onMenuShareTimeline({
    title: '我是摄影师，我申请加入来拍社。',
    link: 'api.ilaipai.com/LaiPai/jsp/activity/index.jsp',
    imgUrl: 'http://img.ilaipai.com/laipailogo@300w_300h.png',
    trigger: function (res) {
      // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
       
    },
    success: function (res) {
      //alert('已分享');
    },
    cancel: function (res) {
      //alert('已取消');
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
	title: "我是摄影师，我申请加入来拍社。", // 分享标题
	desc: '来拍社，中国最大的自由摄影师社群，最好的摄影师都在这儿。', // 分享描述
	link: 'http://api.ilaipai.com/LaiPai/jsp/activity/index.jsp', // 分享链接
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
</script>

</head>
<body>
<div class="swiper-container container-fluid">
    <div class="swiper-wrapper container-fluid">
    <!-------------slide1----------------->
        <section class="swiper-slide">
        
        <div>
  		<div style="position: absolute;">  
    		<img src="http://img.ilaipai.com/laipaiClub1@800h_800w_80Q_1pr.jpg" class="img-responsive" style="left:0;top:0%;width: 100%;height:auto;"/>  
  		</div>
  		<div style="position: absolute;top: 16%;" align="center">  
  			<img src="../../images/ApplyClub/header2.png" class="img-responsive" style="width:70%;height:auto;"/> 
		</div>
		</div>
        <img src="../../images/ApplyClub/arrow1.png" id="array" class="resize img-responsive"
   		style="opacity: 1;background: transparent;width:20px;height:15px; top:95%; left:47%;" > 
        </section>
     <!---------------slide2-------------->   
        <section class="swiper-slide">

        <img src="../../images/ApplyClub/head2.png" class="ani img-responsive" id="head1"
        swiper-animate-effect="bounceInDown" swiper-animate-duration="1s" swiper-animate-delay="0s" />
        
         <img src="../../images/ApplyClub/left1.png" class="ani img-responsive" id="left1" 
         swiper-animate-effect="bounceInLeft" swiper-animate-duration="1s" swiper-animate-delay="1s"/>
         
         <img src="../../images/ApplyClub/left2.png" class="ani img-responsive" id="left2" 
         swiper-animate-effect="bounceInLeft" swiper-animate-duration="1s" swiper-animate-delay="1s" />
       
       	 <img src="../../images/ApplyClub/left3.png" class="ani img-responsive" id="left3" 
         swiper-animate-effect="bounceInLeft" swiper-animate-duration="1s" swiper-animate-delay="1s"/>
         
         <img src="../../images/ApplyClub/left4.png" class="ani img-responsive" id="left4" 
         swiper-animate-effect="bounceInLeft" swiper-animate-duration="1s" swiper-animate-delay="1s"/>
       	
       	 <img src="../../images/ApplyClub/left5.png" class="ani img-responsive" id="left5" 
         swiper-animate-effect="bounceInLeft" swiper-animate-duration="1s" swiper-animate-delay="1s"/>
       	
       	 <img src="../../images/ApplyClub/arrow2.png" id="array" class="resize"
   style="opacity: 1;background: transparent;width:20px;height:15px; top:95%; left:47%;" > 
        </section>
        
       <!---------------slide3-------------->    
      <section class="swiper-slide">
      	<div class="container-fluid">
       	 <img src="../../images/ApplyClub/buttom1.png" class="ani img-responsive" id="buttom1" 
         swiper-animate-effect="fadeInUpBig" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
       	 <img src="../../images/ApplyClub/buttom3.png" class="ani img-responsive" id="buttom2" 
         swiper-animate-effect="fadeInUpBig" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
       	 <img src="../../images/ApplyClub/buttom4.png" class="ani img-responsive" id="buttom3" 
         swiper-animate-effect="fadeInUpBig" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
       	 <img src="../../images/ApplyClub/buttom2.png" class="ani img-responsive" id="buttom4" 
         swiper-animate-effect="fadeInUpBig" swiper-animate-duration="1s" swiper-animate-delay="0s"/>
       	</div>
       	 <img src="../../images/ApplyClub/btn.png" class="ani img-responsive" id="btn" 
         swiper-animate-effect="fadeInUpBig" swiper-animate-duration="1s" swiper-animate-delay="0s" 
         onclick="toSubmit()"/>
       	
       	<img src="../../images/ApplyClub/arrow2.png" id="array" class="resize img-responsive"
   style="opacity: 1;background: transparent;width:20px;height:15px; top:95%; left:47%;" > 
      </section>
    </div>
</div>


<script type="text/javascript">

function toSubmit(){
	   window.location.href="${pageContext.request.contextPath}/jsp/activity/ApplyClub.jsp";
}
</script>
<script>  

 /* scaleW=window.innerWidth/320;
scaleH=window.innerHeight/480;
var resizes = document.querySelectorAll('.resize');
        for (var j=0; j<resizes.length; j++) {
        resizes[j].style.width=parseInt(resizes[j].style.width)*scaleW+'px';
  		resizes[j].style.height=parseInt(resizes[j].style.height)*scaleH+'px';
  		resizes[j].style.top=parseInt(resizes[j].style.top)*scaleH+'px';
  		resizes[j].style.left=parseInt(resizes[j].style.left)*scaleW+'px'; 
       }     */
		  
  var mySwiper = new Swiper ('.swiper-container', {
   direction : 'vertical',
  // pagination: '.swiper-pagination',
  //virtualTranslate : true,
   mousewheelControl : true,
   onInit: function(swiper){
   swiperAnimateCache(swiper);
   swiperAnimate(swiper);
	  },
   onSlideChangeEnd: function(swiper){
	swiperAnimate(swiper);
    },
	onTransitionEnd: function(swiper){
	swiperAnimate(swiper);
    },
	 
	watchSlidesProgress: true,

        onProgress: function(swiper){
        for (var i = 0; i < swiper.slides.length; i++){
          var slide = swiper.slides[i];
          var progress = slide.progress;
          var translate = progress*swiper.height/4;  
		  scale = 1 - Math.min(Math.abs(progress * 0.5), 1);
          var opacity = 1 - Math.min(Math.abs(progress/2),0.5);
          slide.style.opacity = opacity;
		  es = slide.style;
		  es.webkitTransform = es.MsTransform = es.msTransform = es.MozTransform = es.OTransform = es.transform = 'translate3d(0,'+translate+'px,-'+translate+'px) scaleY(' + scale + ')';

        }
      },  
	
	       onSetTransition: function(swiper, speed) {
        for (var i = 0; i < swiper.slides.length; i++){
          es = swiper.slides[i].style;
		  es.webkitTransitionDuration = es.MsTransitionDuration = es.msTransitionDuration = es.MozTransitionDuration = es.OTransitionDuration = es.transitionDuration = speed + 'ms';

        } 
      }
	   }); 
       
  </script>
</body>

</html>