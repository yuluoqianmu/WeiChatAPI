<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <base href="<%=basePath%>">
<title>菜单页</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/css/select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/select-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/editor/kindeditor.js"></script>

  </head>
<script type="text/javascript">
    KE.show({
        id : 'content7',
        cssPath : './index.css'
    });
  </script>
  
<script type="text/javascript">
$(document).ready(function(e) {
    $(".select1").uedSelect({
		width : 345			  
	});
	$(".select2").uedSelect({
		width : 167  
	});
	$(".select3").uedSelect({
		width : 100
	});
});
</script>
</head>
<frameset rows="19%,*"   frameborder="no" border="0" framespacing="0">

  <frame src="<%=request.getContextPath() %>/toTop.action?userId=${user.userId}"/>
  <frame src="<%=request.getContextPath() %>/queryUserById.action?userId=${user.userId}" name="view_frame"/>

</frameset> 
<body>
	<%-- <div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">用户管理</a></li>
    <li><a href="#">用户列表</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    
    <div id="usual1" class="usual"> 
    
    <div class="itab">
  	<ul> 
  	<frameset>
  	    <frame src="top.html"/>
  		<frame src="userinfo.jsp" name="orderFrame" id="orderFrame"></frame>
  	</frameset>
    <li><a href="#tab1" class="selected">基础信息</a></li> 
    <li><a href="<%=request.getContextPath() %>/order.jsp" target="orderFrame">预约</a></li>
    <li><a href="#tab2">预约</a></li>
    <li><a href="#tab3">评论</a></li>
    <li><a href="#tab4">关注</a></li>
    <li><a href="#tab5">喜欢</a></li> 
    
  	</ul>
    </div> 
    
  	<div id="tab1" class="tabson">
    <div class="formbody">
    <ul class="forminfo">
    <form id="Form1" name="Form1" method="post" action="<%=request.getContextPath()%>/adduser.action" enctype="multipart/form-data">
    <li><label>账号:</label><input name="userName" type="text" class="dfinput" readonly="readonly" value="<s:property value='#request.user.userName'/>"/></li>
    <li><label>密码:</label><input name="userPassword" type="password" class="dfinput" value="<s:property value='#request.user.userPassword'/>" readonly="readonly"/><i><a href="#">重置密码</a></i></li>
    <li><label>注册时间:</label><input name="registerTime" type="text" class="dfinput" readonly="readonly"/></li>
    <li><label>第三方登录:</label><input name="accountSource" type="text" class="dfinput" readonly="readonly"/></li>
    <li><label>昵称:</label><input name="nickName" type="text" class="dfinput" value="<s:property value='#request.user.nickName'/>" readonly="readonly"/><i><a href="#">修改昵称</a></li>
    <li><label>手机号:</label><input name="mobile" type="text" class="dfinput" readonly="readonly"  value="<s:property value='#request.user.mobile'/>"/></li>
    <li><label>头像:</label><img id='ImgSrc' src="<%=request.getContextPath()%><s:property value='#request.user.headImage'/>" height="100" width="100" /><i><a href="#">修改头像</a></i></li>
    <input type='hidden' name="headImage" id='headImage'  value='' reg2="^.+$" tip="亲！您忘记上传图片了。" />
    <input type='hidden' name="accountSource" value='0'/>
    <li style='display:none'><label>请上传图片</label><input type="file" name='imgsFile' class="dfinput" onchange='submitUpload()'/></li>
    </li>
    </ul>
    
       </form>
    </div>
        
    
       
  <div class="formtitle"><span>登录日志</span></div>   
     <ul class="forminfo">
     <li>登录日志</li>
     <li>时间</li>
      </ul>
    
    </div> 
    
    </div> 
    
    
  	<div id="tab2" class="tabson">
    
    
    <ul class="seachform">
    
    <li><label>综合查询</label><input name="" type="text" class="scinput" /></li>
    <li><label>指派</label>  
    <div class="vocation">
    <select class="select3">
    <option>全部</option>
    <option>其他</option>
    </select>
    </div>
    </li>
    
    <li><label>重点客户</label>  
    <div class="vocation">
    <select class="select3">
    <option>全部</option>
    <option>其他</option>
    </select>
    </div>
    </li>
    
    <li><label>客户状态</label>  
    <div class="vocation">
    <select class="select3">
    <option>全部</option>
    <option>其他</option>
    </select>
    </div>
    </li>
    
    <li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="查询"/></li>
    
    </ul>
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
        <th><input name="" type="checkbox" value="" checked="checked"/></th>
        <th>编号<i class="sort"><img src="images/px.gif" /></i></th>
        <th>标题</th>
        <th>用户</th>
        <th>籍贯</th>
        <th>发布时间</th>
        <th>是否审核</th>
        <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr>
        <td><input name="" type="checkbox" value="" /></td>
        <td>20130908</td>
        <td>王金平幕僚：马英九声明字字见血 人活着没意思</td>
        <td>admin</td>
        <td>江苏南京</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td><a href="#" class="tablelink">查看</a>     <a href="#" class="tablelink"> 删除</a></td>
        </tr> 
        
        <tr>
        <td><input name="" type="checkbox" value="" /></td>
        <td>20130907</td>
        <td>温州19名小学生中毒流鼻血续：周边部分企业关停</td>
        <td>uimaker</td>
        <td>山东济南</td>
        <td>2013-09-08 14:02</td>
        <td>未审核</td>
        <td><a href="#" class="tablelink">查看</a>     <a href="#" class="tablelink">删除</a></td>
        </tr>
        
        <tr>
        <td><input name="" type="checkbox" value="" /></td>
        <td>20130906</td>
        <td>社科院:电子商务促进了农村经济结构和社会转型</td>
        <td>user</td>
        <td>江苏无锡</td>
        <td>2013-09-07 13:16</td>
        <td>未审核</td>
        <td><a href="#" class="tablelink">查看</a>     <a href="#" class="tablelink">删除</a></td>
        </tr>
        
        <tr>
        <td><input name="" type="checkbox" value="" /></td>
        <td>20130905</td>
        <td>江西&quot;局长违规建豪宅&quot;：局长检讨</td>
        <td>admin</td>
        <td>北京市</td>
        <td>2013-09-06 10:36</td>
        <td>已审核</td>
        <td><a href="#" class="tablelink">查看</a>     <a href="#" class="tablelink">删除</a></td>
        </tr>
        
        <tr>
        <td><input name="" type="checkbox" value="" /></td>
        <td>20130907</td>
        <td>温州19名小学生中毒流鼻血续：周边部分企业关停</td>
        <td>uimaker</td>
        <td>山东济南</td>
        <td>2013-09-08 14:02</td>
        <td>未审核</td>
        <td><a href="#" class="tablelink">查看</a>     <a href="#" class="tablelink">删除</a></td>
        </tr>
    
        </tbody>
    </table>
    
   
  
    
    </div>  
       
	</div> 
 
	<script type="text/javascript"> 
      $("#usual1 ul").idTabs(); 
    </script>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>
    
    
    
    
    
    </div>


--%></body>

</html>
