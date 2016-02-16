<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'galary.jsp' starting page</title>
    
   <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
    <style>
    #imgbox img{
    }
   #img {
   		border:#ccc 3px solid;
   		width:200px; height:150px;
   		
   		}
   #img:hover{
   		border:#ccc 5px solid;
   		width:200px; height:150px;
   		}
  #addImg h3{
  	padding: 0px 0px 0px 5px;
  	height:27px;
	line-height:27px; 
	color: #393939;
	font-size: 12px;
	font-weight: normal;
  }
   #addImg h3 img{
    vertical-align:middle
  }
  
    #twoDIV{
    margin-left:50px;  
    margin-top:20px;  
   }
    #mypage{
    left:50px; 
    width: 90% 
    }
   
   
   </style>
   
   <script type="text/javascript">
   
    function toEditGallery(galleryId){
     var JSP="galleryJSP";
     window.location.href="galleryManAction_toEdit.action?galaryId="+galleryId+"&jsp="+JSP;
    }
    
    function  deleteGallery(galleryId){
    if(confirm("确定要删除吗?")){
       var option = {
			url:"deleteGalleryAjax.action",
			type:"post",
			dataType:"json",
			data:{
				galleryId:galleryId
			},
			success:function(responseText){
				 alert("删除成功"); 
				 location.reload();
			},
			error:function(){
				alert("系统错误");
			}
	};
    
	    $.ajax(option);
	    }
    }
    $(function (){
     $("#buttonDiv").click(function(){
       var userId=$("#cameraId").val();
        window.location.href="cameraManAction_userAddGallery.action?userId="+userId;
      }) 
    });
   var flag=false;
function DrawImage(ImgD){
var image=new Image();
var iwidth = 169;            //定义允许图片宽度，当宽度大于这个值时等比例缩小
var iheight = 127;            //定义允许图片高度，当宽度大于这个值时等比例缩小
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
   </script>
  <body>
   <div class="rightinfo">
      <div class="tools">
    
    	<ul class="toolbar">
        <li class="click" id="buttonDiv"><span><img src="images/t01.png" /></span>添加作品集</li>
        </ul>    
    </div>
   
    <form id="galleryForm" name="galleryForm" method="post" action="<%=request.getContextPath() %>/queryGalary.action" enctype="multipart/form-data">
    <input type="hidden" name="userId" value="${userId }" id="cameraId">
    <ul class="classlist">
	<s:if test="#request.galaryInfo!=null && #request.galaryInfo.size()>0">
    <c:forEach items="${galaryInfo}" var="galary">
		
    <li>
    <span style="text-align: center;"><img width="169px"  src="${galary.galaryCover}" onclick="toEditGallery('${galary.galaryId}')" onload="javascript:DrawImage(this)"/></span>
    <%-- <input type="hidden" name="galaryId" value="${userId}"/> --%>
    <div class="lright"> 
    <h2>${galary.subjectName }</h2>
    <p>发布时间:
    <fmt:formatDate value="${galary.creatTime }"  pattern="yyyy/MM/dd HH:mm"/></p>
    <p>浏览数: <c:if test="${galary!=null && galary.controlSource == 0}">
         		${galary.viewNumber}.
       </c:if>
         	<c:if test="${galary!=null && galary.controlSource == 1}">
         		${galary.lpGalaryExtends.viewNumber } .
         	</c:if> 
         	 
             评论数：${galary.commentNumber } .</p> 
    <p>赞数： <c:if test="${galary!=null && galary.controlSource == 0}">
         		${galary.likeNumber} .
       </c:if>
         	<c:if test="${galary!=null && galary.controlSource == 1}">
         		${galary.lpGalaryExtends.likeNumber } .
         	</c:if> 
    </p>
    <p>价格：${galary.lpService.lpServiceDetail.price } 元</p>
    <p><a href="javascript:deleteGallery('${galary.galaryId}');"><font color="green">删除</font></a></p>
     </div>
    </li>
    </c:forEach>
   
   
   </s:if> 
     <s:else>
      <!-- <li style="background-color:#E5EFF8;">该用户没有作品集</li>  -->
             该用户没有作品集  
     </s:else>
   </ul>     
  
    <div class="clear"></div>
    <div class="pagin">
    	 <table width="80%" class="splitPage">
		<tr id=null>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="galleryForm" title="记录" unit="条记录" actionName="queryGalary.action"/>
			</td>
		</tr>
	 </table>
    </div>
     </form>
      </div>    
  </body>
</html>
