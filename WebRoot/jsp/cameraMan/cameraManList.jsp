<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@taglib uri="/struts-tags" prefix="s"%>
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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/laydate/laydate.js"></script>
	<style type="text/css">  
	/*浮动 **/
   #div1 {
  background-color:#fff;
        border:5px solid rgba(0,0,0, 0.4);
        height:580px;
        left:50%;
        padding:1px; 
        width:700px;  
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
  
    </style>  	
<script type="text/javascript">
    $(function(){
               $(".click").click(function () { 
            window.location.href="<%=request.getContextPath()%>/cameraManAction_toAddCameraman.action";                
            }); 
            
              $("#checkAll").click(function(){
	          //alert(this.checked);
	         if(this.checked){
	          $("input[type='checkbox']").each(function(){this.checked=true;});
	         }else{
	          $("input[type='checkbox']").each(function(){this.checked=false;});
	         	         
	         }
	      
	      
	      })         
   });
  
   
  $(document).ready(function(){
  
  $("#deleteall").click(function(){
      deleteUser();
 });
   //查询
  $("#search").click(function(){
    searchUser();
  });
  function searchUser(){
  	var userAccount =$("#userAccount").val();
  	var nickName = $("#nick_name").val();
  	var registTime = $("#registTime").val();
  	document.location="<%=request.getContextPath()%>/cameraManAction_queryall.action?userAccount="+userAccount+"&nickName="+nickName+"&registTime="+registTime ;
  }

});
  function deleteUser(){
    var f = document.getElementsByName("ids");
			var c=0;	
			var ids = "";
			var id = "";
			var i = 0;
			for(var j=0; j<f.length ;j++ ){
			    if(f[j].checked){
			    	i++;
			        id = f[j].value;
			        ids = ids + "&userId=" + id;
			    }
			}

			if(i == 0){
			     alert("请至少选择一个用户!");
			     return;
			}

			if(confirm("确定要删除吗?"))
			{
				document.location="<%=request.getContextPath()%>/cameraManAction_deleteUser.action?" + ids;
			}
  }
  
    
    //图片上传依赖表单
function submitUpload(){
        //alert(111);
	var option = {
			url:"<%=request.getContextPath()%>/uploadPic.action",//如果option中存在url那么就提交到url上，如果不存在url，提交到表单的action上
			type:"post",
			dataType:"text",
			data:{
				imgsFile:"imgsFile"
			},
			success:function(responseText){
				 //把json字符串转换成json对象
				 //alert(responseText);
				var jsonObj = $.parseJSON(responseText);
				//alert(jsonObj.realPath);
				$("#ImgSrc").attr("src",jsonObj.realPath);
				$("#headImage").val(jsonObj.relativePath);  
			},
			error:function(){
				alert("系统错误");
			}
	};
	//通过ajax方式提交表单，页面没跳转
	$("#Form1").ajaxSubmit(option);
}
    
    function detailInfo(url){
    	location.href = url;
    }
 
  //查询注册时间时显示日历
laydate.skin('danlan');
</script>


</head>


<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">数据表</a></li>
    <li><a href="#">基本内容</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    <div class="tools">
    
    	<ul class="toolbar">
        <li class="click"><span><img src="images/t01.png" /></span>添加</li>
        <li id="deleteall"><span><img id="delete" src="images/t03.png" /></span>删除</li>
        </ul>        
        <ul class="toolbar1">
        <li ><span style="margin-top:0px">用户账号：
       		<input type="text" id="userAccount" name="用户账号" style="height:20px"/>
       	</span>
       	<span style="margin-top:0px">用户昵称：
       		<input type="text" id="nick_name" name="用户昵称" style="height:20px"/>
       	</span>
       	<span style="margin-top:0px">注册时间：
       		<input id="registTime" name="注册时间" style="height:20px"  class="laydate-icon" onclick="laydate({elem: '#registTime'});"/>
       	</span>
       	</li>
       	<li id="search" style="cursor:pointer"><span><img src="images/search.png"/></span>查询</li>
        </ul>
    
    </div>
    </div>
   <form id="userForm" name="userForm" method="post" action="<%=request.getContextPath()%>/cameraManAction_queryall.action" enctype="multipart/form-data"> 
    <table class="tablelist">
    	<thead>
    	<tr>
        <th><input name="" type="checkbox" value="" id="checkAll"/></th>
        <th>序号<i class="sort"><img src="images/px.gif" /></i></th>
        <th>用户账号</th>
        <th>用户昵称</th>
        <th>注册时间</th>
        <th>认证时间</th>
        <th>账号来源</th>
        <th>操作</th>
        </tr>
        </thead>
        <tbody>
        
   
	<s:if test="#request.userList!=null && #request.userList.size()>0">
		<s:iterator value="#request.userList" status="st">
        <tr ondblclick="detailInfo('<%=request.getContextPath()%>/cameraManAction_toeditCameraman.action?userId=<s:property value="userId"/>')">
        <td><input type="checkbox"  name="ids" value="<s:property value='userId'/>"  /></td>
        <td><s:property value="#st.getCount()"/></td>
        <td><s:property value="userName"/></td>
        <td><s:property value="nickName"/></td>
        <td><s:date name="registerTime" format="yyyy-MM-dd HH:mm:ss"/></td>
        <td><s:date name="validTime" format="yyyy-MM-dd HH:mm:ss"/></td>
        <td>
          <s:if test="accountSource==0">
                                用户注册
           </s:if>
            <s:if test="accountSource==1">
                                 微博用户
            </s:if>
             <s:if test="accountSource==2">
                                 微信用户
            </s:if>
        </li>     
        </td>

        <td><a href="<%=request.getContextPath()%>/cameraManAction_toeditCameraman.action?userId=<s:property value='userId'/>" class="tablelink">查看</a>    
        </tr> 
     </s:iterator>
</s:if>        
    </table>
     <table width="98%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="userForm" title="记录" unit="条记录" actionName="cameraManAction_queryall.action"/>
			</td>
		</tr>
	</table>

 </form>
    
    
    <div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
        
      <div class="tipinfo">
        <span><img src="images/ticon.png" /></span>
        <div class="tipright">
        <p>确认删除该信息</p>
        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
        </div>
        </div>
        
        <div class="tipbtn">
        <input name="" type="button"  class="sure" value="确定" />&nbsp;
        <input name="" type="button"  class="cancel" value="取消" />
        </div>
    
    </div>
    
    
    
    
     <!--新增用户浮动框  -->
    <div  id="div1">
    <!-- //<input type="button" value="关闭" id="close" /> -->
    <p class="close"><a href="javascript:void(0)" id="close">关闭</a></p>
    <div class="formbody">
    
    <div class="formtitle"><span>创建账号</span></div>
    
    <ul class="forminfo">
    <form id="Form1" name="Form1" method="post" action="<%=request.getContextPath()%>/adduser.action" enctype="multipart/form-data">
    <li><label>账号</label><input name="userName" type="text" class="dfinput" /><i>标题不能超过30个字符</i></li>
    <li><label>密码</label><input name="userPassword" type="password" class="dfinput" /><i>多个关键字用,隔开</i></li>
    <li><label>确认密码</label><cite><input name="" type="password"  class="dfinput" /></li>
    <li><label>昵称</label><input name="nickName" type="text" class="dfinput" /></li>
    <li><label>头像</label><img id='ImgSrc' src="images/jiejie.jpg" height="100" width="100" />
    <input type='hidden' name="headImage" id='headImage'  value='' reg2="^.+$" tip="亲！您忘记上传图片了。" />
    <input type='hidden' name="accountSource" value="0"/>
    <li><label>头像</label><input type="file" name='imgsFile' class="dfinput" onchange='submitUpload()'/></li>
    <li><input name="" type="submit" class="btn" value="确认保存" class="dfinput"/>
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
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>

</body>

</html>
