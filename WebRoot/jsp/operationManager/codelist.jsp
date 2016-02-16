<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>"/>
    <title>邀请码列表</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/style1.css" rel="stylesheet" type="text/css" />
<link href="css/select.css" rel="stylesheet" type="text/css" />
<link href="css/mystyle.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/css/showLoading.css" rel="stylesheet" type="text/css" />
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.showLoading.min.js"></script>
     <style type="text/css">
    /*浮动 **/
   #div1 {
  background-color:#fff;
        border:5px solid rgba(0,0,0, 0.4);
        height:350px;
        left:50%;
        padding:1px; 
        width:530px;  
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

#tip{
  background-color:#FCFCFC;
  margin:0 auto;
  margin-left: 600px;
  margin-top: 150px;
  width: 200px;
  height: 50px;
  display: none;

}

 .labelfontstyle{
     font-family:"微软雅黑 ";
     font-size:14px; 
     color: green;   
    }
     </style>
    
    
    <script type="text/javascript">
            $(function () { 
      
        var $win = $(window),  
                $div = $('#div1'),  
                $mask = $('#mask'),  
                $close = $('#close'),  
                flag = false;  
      
//        $(".click")
       $("#addInvite").click(function () {                  
            var clientH = $win.height(),  
                clientW = $win.width(),  
                divH = $div.height(),  
                divW = $div.width();  
            var t = (clientH - divH) / 2 + $win.scrollTop();  
            var l = (clientW - divW) / 2 + $win.scrollLeft();  
            $div.css('display', 'block').offset({ 'top': t , 'left': l });  
      
            $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
            flag = true;  
      
            $close.click(function () {  
                flag = false;  
                $div.hide();  
                $mask.hide();  
            });  
      
      
            $(window).scroll(setMask).resize(setMask);  
      
            function setMask() {  
                if (flag) {  
                    $div.css('display', 'block').offset({ 'top': ($win.height() - $div.height()) / 2 + $win.scrollTop(), 'left': ($win.width() - $div.width()) / 2 + $win.scrollLeft() });  
                    $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
                } else {  
                    $div.hide();  
                    $mask.hide();  
                }  
            }  
        });
        
        $("#Form1").submit(function(){
           if($("#codeDigits").val() == "")
		{
			if($("#warn1").length == 0)
			{
				$("#codeDigits").after("<i><font id='warn1' color='red'>位数不能为空</font></i>");
			}
			return false;
		}
		else
		{
		  if(!isIntegerD($("#codeDigits").val())){
		    if($("#warn1").length == 0)
			{
				$("#codeDigits").after("<i><font id='warn1' color='red'>请输入小于或等于6的数字!</font></i>");
			}else{
			    $("#warn1").text("请输入数字");
			}
			return false;
		  }else{
			$("#warn1").remove();
			}
		}
		if($("#codeNumber").val() == "")
		{
			if($("#warn2").length == 0)
			{
				$("#codeNumber").after("<i><font id='warn2' color='red'>邀请码生成个数不能为空</font></i>");
			}
			return false;
		}
		else
		{
		  if(!isIntegerN($("#codeNumber").val())){
		    if($("#warn2").length == 0)
			{
				$("#codeNumber").after("<i><font id='warn2' color='red'>请输入小于或等于5000的数字!</font></i>");
			}else{
			    $("#warn2").text("请输入数字");
			}
			return false;
		  }else{
			$("#warn2").remove();
			}
		}
          $("#div1").showLoading(); 
           return true;
        
        });
        
/*          $("#checkAll").click(function(){
	          //alert(this.checked);
	         if(this.checked){
	          $("input[type='checkbox']").each(function(){this.checked=true;});
	         }else{
	          $("input[type='checkbox']").each(function(){this.checked=false;});
	         	         
	         }
	      
	      
	      })  
	    $("#password").change(function(){
		var one = $("#userPassword").val();
		var two = $(this).val();
		if(one != "")
		{
			if(two != one)
			{
				if($("#error").length == 0)
				{
					$("#password").after("<i><font id='error' color='red'>两次密码输入不一致！</font></i>")
				}
			}
			 else
			{
				$("#error").remove();
			} 
		}
	}) */     
  

 
  
     
  

    
    
    
    
    });
       
   function isIntegerD( str ){
    if(str>6){
     return false;
    }
    var regu = /^[-]{0,1}[0-9]{1,}$/;
    return regu.test(str);
  }
  
   function isIntegerN( str ){
    if(str>5000){
     return false;
    }
    var regu = /^[-]{0,1}[0-9]{1,}$/;
    return regu.test(str);
  }
  
  function sendCode(codeId){
   if (confirm("确定要发出该邀请码?")) {
     //alert(codeId); 
    /*  var page=$("#hiddenPage").val(); */

      $.ajax({
             url:"sendCode.action",
			type:"post",
			dataType:"json",
			data:{
				codeId:codeId
			},
			success:function(responseText){
				 //alert("发送成功"); 
				 location.replace(location.href);
			},
			error:function(){
				alert("系统错误");
			}
			
			
         });
   
   
   }
  }
  $(document).ready(function(){
   $("#sendUnused").click(function(){
      sendUnused();
   });
   $("#sendUsed").click(function(){
      sendUsed();
   });
   $("#unsendUnused").click(function(){
     unsendUnused();
   });
  });
  function sendUnused()
  {
    document.location="<%=request.getContextPath()%>/Invite_queryAllCode.action?status=0";
  }
  function sendUsed()
  {
    document.location="<%=request.getContextPath()%>/Invite_queryAllCode.action?status=1";
  }
  function unsendUnused()
   {
      document.location="<%=request.getContextPath()%>/Invite_queryAllCode.action?status=2";
   }
    </script>
   
  </head>
  
  <body>
  <div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">运营管理</a></li>
    <li><a href="#">邀请码</a></li>
    </ul>
    </div>
  <div class="rightinfo">
   <div class="tools">
    <!-- style="margin-left: 9px;" -->
        <ul class="toolbar">
       <li class="click" id="addInvite"><span><img src="images/t01.png" /></span>增加邀请码</li>
       <!--  <li id="deleteall"><span><img id="delete" src="images/t03.png" /></span>删除</li>  -->
        </ul>        
        <ul class="toolbar1">
        <!-- style="float:left; width:400px;" -->
        <div>
        <ul class="toolbar"><!-- style="visibility: hidden;" value="${status}" -->
         <input type="text" name="status" id="status" style="visibility: hidden;">
         <li class="click" id="sendUnused"><span>已发未用</span><span>${sendUnused}</span></li>
         <li class="click" id="sendUsed"><span>已发已用</span><span>${sendUsed}</span></li>
         <li class="click" id="unsendUnused"><span>未发未用</span><span>${unsendUnused}</span></li>
        </ul>        
        <ul class="toolbar1">
          
        </div>
        </ul>
    </div>
    </div>
   <form id="codeForm" name="codeForm" method="post" action="<%=request.getContextPath()%>/Invite_queryAllCode.action" enctype="multipart/form-data"> 
   <input type="hidden" id="hiddenPage" name="currentPage" value="${pageController.currentPage }"/>
    <table style="margin-left:10px; width:1300px;" class="tablelist">
       	<thead>
    	<tr>
       <!--   <th><input name="" type="checkbox" value="" id="checkAll"/></th>  -->
        <th>序号<i class="sort"><img src="images/px.gif" /></i></th>
        <th>邀请码</th>
        <th>是否发送</th>
        <th>发送时间</th>
        <th>使用人账号</th>
        <th>邀请码状态</th>
        <th>生成时间</th>
        <th>操作</th>
        </tr>
        </thead>
        <tbody>
     <c:if test="${invitelist != null && fn:length(invitelist)>0}">
	 <c:forEach items="${invitelist}" var ="item" varStatus="i">
         <tr>
        <td>${i.index + 1}</td>
        <td>${item.inviteCode }</td>
        <td>
        <c:if test="${item.isSend==0 }">
                        未发送
        </c:if>
         <c:if test="${item.isSend==1 }">
                        已发送
        </c:if>       
        </td>      
        <td><fmt:formatDate value="${item.sendtime }" pattern="yyyy/MM/dd HH:mm:ss"/></td>
        <td>
        <c:if test="${item.lpUser!=null}">
          ${item.lpUser.userName}
        </c:if>  
        </td> 
        <td>
        <c:if test="${item.inviteStatus==0 }">
                        未使用
        </c:if>
         <c:if test="${item.inviteStatus==1 }">
                        已使用
        </c:if>       
        </td>
              
        <td><fmt:formatDate value="${item.createtime }" pattern="yyyy/MM/dd HH:mm:ss"/></td>
         <td><a href="javascript:void(0)"  onclick="sendCode('${item.inviteId }')" class="tablelink">发出 <font style="color: #329E1C;"> (发出后才可使用)</font></a></td>
        </tr> 
    </c:forEach>
       </c:if>
        </tbody>
        </table>
        <table width="98%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="codeForm" title="记录" unit="条记录" actionName="Invite_queryAllCode.action"/>
			</td>
		</tr>
	    </table>

        </form>
        </div>
        
         <!--新增用户浮动框  -->
    <div  id="div1">
    <!-- //<input type="button" value="关闭" id="close" /> -->
    <p class="close"><a href="javascript:void(0)" id="close">关闭</a></p>
    <div class="formbody">
    
    <div class="formtitle"><span>创建账号</span></div>
    
     <ul class="forminfo">
    <form id="Form1" name="Form1" method="post" action="<%=request.getContextPath()%>/Invite_addInviteCode.action" enctype="multipart/form-data">
    <li><label>邀请码位数</label><input id="codeDigits" name="codeDigits" type="text" class="myinput"/></li>
    <li><label>邀请码个数</label><input id="codeNumber" name="codeNumber" type="text"  id="psw1" class="myinput"/></li>
    <li style="margin-top:50px;"><input name="" type="submit" class="btn"  value="确认保存"  class="dfinput"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input name="" type="reset" class="btn" value="重置" class="dfinput"/>
    </li>
    </ul>
    
    
    </div>
        
       </form>
          
    </div> 
    <div  id="mask">
    </div>  
    
    
  </body>
</html>
