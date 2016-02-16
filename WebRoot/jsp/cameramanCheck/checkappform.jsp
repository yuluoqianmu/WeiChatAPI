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
 <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.lightbox.packed.css" type="text/css" media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.lightbox.js"></script>
  </head>
 <style>
  #appdata{
    margin-left:50px;
    margin-top:40px;
  }
   .tablehead{
    font-family:"微软雅黑";
    font-size:24px;
    }
    
    .tablebody{
     font-family:"微软雅黑";
     font-size:15px;    
    }
    .buttonstyle{
     font-family:"微软雅黑";
     font-size:20px;    
    }
      #div1 {
        background-color:#E5EFF8;
        border:5px solid rgba(0,0,0, 0.4);
        height:300px;
        left:50%;
        padding:1px; 
        width:580px;  
        position:absolute;  
        display:none;  
        left:0;  
        top:0;  
        z-index:2; 
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
/*遮罩**/
     #mask{  
        display:none;  
        position:absolute;  
        background-color:black;  
        filter:alpha(opactiy=20);  
        opacity:0.2;  
        z-index:1;  
    }
    
     .fontstyle{
     font-family:"微软雅黑";
     font-size:17px;    
    }
 </style>
 <script type="text/javascript">
     $(function () {  
        var $win = $(window),  
                $div = $('#div1'),  
                $mask = $('#mask'),  
                $close = $('#close'),  
                flag = false;  
      
      
       $("#reject").click(function () {                  
            var clientH = $win.height(),  
                clientW = $win.width(),  
                divH = $div.height(),  
                divW = $div.width();  
            var t = (clientH - divH) / 2 + $win.scrollTop();  
            var l = (clientW - divW) / 2 + $win.scrollLeft();  
            $div.show(500).offset({ 'top': t , 'left': l });       
            $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
            flag = true;  
      
            $close.click(function () {  
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
     //驳回申请
   $("#btnSubmit").click(function(){
	 var option = {
			dataType:"text",
			success:function(responseText){
			     flag = false;  
                $div.hide();  
                $mask.hide(); 
			    
			   alert("该用户已被驳回"); 
			    $("#passbtn").hide();
			    $("#registbut").hide();
			    $("#addstatus").hide();
			    $("#addstatus").after("<font class='fontstyle' color='red'>未通过</font>");             
			},
			error:function(){
				alert("系统错误");
			}
	};
	//通过ajax方式提交表单，页面没跳转
	$("#Form1").ajaxSubmit(option);
}  ); 
        
     $("#pass").click(function(){
     // alert(1111);
       var formId = $('#appformId').val();
       var userId=$('#userId').val();
       var status=1;
      // alert(formId);
          $.ajax({
    		  type:"post",
  			  url:"passAppform.action",
  			  data:{formId:formId,status:status,userId:userId},
  			  async:true,
  			  dataType:"json", 
  			  success:function(data){
  			    alert("该用户已通过认证");
  			    $("#passbtn").hide();
			    $("#registbut").hide();
  			    $("#buttonDiv").hide();
  			    $("#addstatus").hide();
  			    $("#addstatus").after("<font class='fontstyle' color='green'>通过</font>");   
  			  },
    	  });      
     
    
    }); 	
    });
    
   var flag=false;
function DrawImage(ImgD){
var image=new Image();
var iwidth = 300;            //定义允许图片宽度，当宽度大于这个值时等比例缩小
var iheight = 200;            //定义允许图片高度，当宽度大于这个值时等比例缩小
image.src=ImgD.src;
if(image.width>0 && image.height>0){         //假如图片长宽都不为零
flag=true;
     if(image.height/image.width>= iheight/iwidth){       //通过正弦值判断图片缩放后是否偏高
      if(image.height>iheight){        //如果图片比设定的要高
       ImgD.height=iheight;
       ImgD.width=(image.width*iheight)/image.height;
      }else{
       ImgD.width=image.width;
       ImgD.height=image.height;
      }
 
     ImgD.alt=image.width+"×"+image.height;
     }
 
     else{           //如果图片比例 小于 设定的比例
      if(image.width>iwidth){
       ImgD.width=iwidth;
       ImgD.height=(image.height*iwidth)/image.width;
      }else{
       ImgD.width=image.width;
       ImgD.height=image.height;
      }
     ImgD.alt=image.width+"×"+image.height;
     }
}
}	 
    
    
    
    
/*  $(function(){
	$("#manualGroup a").lightbox();		   
}); */
  
    </script> 
  <body>
    <div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a >摄影师认证审核</a></li>
    <li><a >${appform.lpUser.userName }</a></li>
    </ul>
    </div>
    
    <div id="appdata">
     <input type="hidden" name="appformId" value="${appform.appformId}" id="appformId"/>
      <div id="table">
        <table id="mytable" class="tablelist" style="margin-left:1%; width:75%;border:1px;cellpadding:2px; border-collapse:separate; border-spacing:10px;">
         <thead>
          <tr ><td width="18%"><font class="tablehead">申请信息</font></td>
             <td></td>
             <td></td>
             <td></td>
             <td>
             <div id="passbtn">
              <c:if test="${appform.checkStatus==0}">
               <input name="" type="button" id="pass"  class="btn" value="认证通过" class="dfinput"/>
             <!-- <a href="javascript:void(0)" id="pass"><font class="buttonstyle">认证通过</font></a></td> -->
             </c:if>
             </div>
             <td>
             <div id="registbut">
             <c:if test="${appform.checkStatus==0}">
              <input name="" type="button" id="reject"  class="btn" value="驳        回" class="dfinput"/>
            <!--  <a id="reject" href="javascript:void(0)" ><font class="buttonstyle">驳&nbsp;&nbsp;&nbsp;&nbsp;回</font></a> -->
             </c:if>
              </div>
             </td>
            
          </tr>
         </thead>
          <tbody >
           <tr >
           <td width="5%" class="tablebody">当前状态：</td>
           
           <td class="tablebody">
           <div id="addstatus">
           <c:if test="${appform.checkStatus==0 }"><font class="fontstyle" color='black'>待审</font></c:if>
           <c:if test="${appform.checkStatus==1 }"><font class="fontstyle" color='green'>通过</font></c:if>
           <c:if test="${appform.checkStatus==-1 }"><font class="fontstyle" color='red'>未通过</font></c:if>
           </div>
            <div id="addstatus">
            </div>
           </td>
           </tr>
           <tr class="tablebody">
           <td width="10%" class="tablebody">姓名</td><td class="tablebody">${appform.lpUser.realName}</td>
           <td width="10%" class="tablebody">性别</td>
           <td width="17%" class="tablebody">
            <c:if test="${appform.lpUser.userGender==0 }">男</c:if>
            <c:if test="${appform.lpUser.userGender==1 }">女</c:if>
           </td>
           <td  width="10%" class="tablebody">所在地</td><td width="15%" class="tablebody">${appform.lpUser.lpCity.cityName }</td>
           </tr>
           <tr >
            <td width="15%" class="tablebody">联系电话</td><td class="tablebody">${appform.lpUser.mobile}</td>
            <td width="15%" class="tablebody">所用器材</td><td class="tablebody">${appform.lpUser.grapherCarmer}</td>
           </tr>
            <tr >
             <td width="15%" class="tablebody">拍摄风格</td><td  width="35%" style="word-break:break-all;" class="tablebody">
               <c:forEach items="${listStyle }" var="style">
    		<c:out value="${style.styleName }"></c:out>
    	       </c:forEach>
             
             </td>
            </tr>
             <tr >
             <td width="5%" class="tablebody">个人简介</td>
             </tr>
          </tbody>        
        </table>
      <div id="personDesc" style=" margin-left:50px;">
       <textarea cols="80" rows="4" name="grapherDesc" readonly="readonly" >${appform.lpUser.grapherDesc} </textarea> 
      </div>
      </div>
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</br>
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</br>
      <div id="imag">
       <span style="margin-left: 5%;"> <font class="tablehead">身份证信息</font></span>
       </br>
       </br>
        <div id="IDcard" style="margin-left:5%;">
         <span style="text-align:left;">
            <a href="${appform.idcardImg}" rel="lightbox" title="身份证">
         <img src="${appform.idcardImg}" width="300px" onload="javascript:DrawImage(this)" title="点击看大图"/></a></span>       
        </div>
        <div style="margin-top: 4%;" id="gallery">
         <span style="margin-left: 5%;"><font class="tablehead">作品</font></span>
         
         </br>
         </br>
   <ul class="myimglist" style="margin-left: 4%;">
    <p id="manualGroup">
    <li style="text-align: center;">
   
    <a href="${appform.photoOne }" title="作品一" rel="lightbox-myGroup">
    <img style="margin-top: 6%;" src="${appform.photoOne }" onload="javascript:DrawImage(this)" width="300px"  title="点击看大图"/>
    </a>
    
    </li>    
     <li style="text-align: center;">
     <a href="${appform.photoTwo }" title="作品二" rel="lightbox-myGroup">
     <img style="margin-top: 6%;" src="${appform.photoTwo }" onload="javascript:DrawImage(this)" width="300px"  title="点击看大图"/>
     </a>
      </li>
     <li style="text-align: center;">
     
     <a href="${appform.photoThree }" title="作品三" rel="lightbox-myGroup"> 
     <img style="margin-top: 6%;" src="${appform.photoThree }" onload="javascript:DrawImage(this)" width="300px"  title="点击看大图"/>
     </a>
     </li>
     </p>
    </ul>
        
        </div>
      </div>    
    </div>
  <div id="label1" style="margin-left:5%;width:90%" class="formtitle"><span>申请记录</span></div>  
    <div id="logdiv"  style="width:90%;margin-left: 6%;" >
      <iframe id='log_iframe' style="height:50%; width:90%" scrolling="no" name='log_iframe' src="<%=request.getContextPath()%>/checkAction_queryappformLog.action?userId=${appform.lpUser.userId}" ></iframe>
    </div>
    
	<div  id="div1" >
    <!-- //<input type="button" value="关闭" id="close" /> -->
    <p class="close"><a href="javascript:void(0)" id="close">关闭</a></p>
    <div class="formbody">
	 <div class="formtitle"><span>驳回理由</span></div>
     <ul class="forminfo">
    <form id="Form1" name="Form1" method="post" action="<%=request.getContextPath()%>/passAppform.action">
     <li><label> <font class="tablebody">驳回理由</font></label>
      <textarea cols="50" rows="6" name="rejectReason" > </textarea> 
     </li>
     <input type='hidden'  name="formId"  value="${appform.appformId}"/>
     <input type='hidden' id="userId" name="userId"  value="${appform.lpUser.userId}"/>
     <input type='hidden'  name="status"  value="-1"/>
     <br><br>
     <li> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;
    <!-- <input name="" type="button"  onclick="addComment()" class="btn" value="确认保存" class="dfinput"/> -->
     <input name="" type="button" id="btnSubmit"  class="btn" value="确认保存" class="dfinput"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input name="" type="reset" class="btn" value="重置" class="dfinput"/>
    </ul>    
    </form>
    </div>
    </div>
      <div  id="mask">
   </div>   
  </body>
</html>
