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
	<link href="css/style1.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>

	<style type="text/css">  
	/*浮动 **/
   #menuList {
  		background-color:#fff;
        border:5px solid rgba(0,0,0, 0.4);
        height:680px;
        left:70%;
        padding:1px; 
        width:700px;  
        position:absolute;  
        display:none;  
        left:0;  
        top:0;  
        z-index:2;
        overflow: scroll;
      }
       
        #div1 {
  background-color:#fff;
        border:5px solid rgba(0,0,0, 0.4);
        height:400px;
        left:50%;
        padding:1px; 
        width:600px;  
        position:absolute;  
        display:none;  
        left:20%;  
        top:12%;  
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
	#menuList p {
		margin:0 0 12px;
		height:24px;
		line-height:24px;
		background:#CCCCCC;
	}
	#menuList p.closeP {
		text-align:right;
		padding-right:10px;
	}
	#menuList p.closeP a {
		 color:#fff;
		 text-decoration:none;
	} 
  
    </style>  	
<script type="text/javascript">
       $(function () {  
        var $win = $(window),  
                $menuList = $('#menuList'),  
                $mask = $('#mask'),  
                $close = $('#close'),  
                flag = false;  
      
      
      	function checkResurce(resourceIds)
	    {
		     $("[name='ResourceId']").each(function(){
		        var $this=$(this);
		        $.each(resourceIds,function(index2,item2){
				   if($this.val()==item2){
				      $this.attr("checked",true);
				   }
				})		
	     })
	   }
      
       $(".click").click(function () {                  
            //alert($("#userIdInput"));
            //alert("userId = " + $(this).attr("userId"));
            $("#userIdInput").val($(this).attr("userId"));
            //alert($("#userIdInput").val());
            //ajax获取数据
            var ajaxResult =$.ajax({url:"<%=request.getContextPath()%>/privilegeJsonGetResourceByUserId.action?userId=" + $(this).attr("userId") + "&time=" + new Date(), async:false});
            var responseText = ajaxResult.responseText;
            //alert(" responseText = " + responseText);
            if(responseText != null)
            {
            	var resourceJson = eval("(" + responseText + ")");
            	var resourceIds =  eval("(" + resourceJson.result + ")");
            	//alert(resourceIds);
            	checkResurce(resourceIds);
            }
            //alert(ajaxResult.result)
            var clientH = $win.height();
            var clientW = $win.width(); 
            var divH = $menuList.height();
            var divW = $menuList.width();  
            var t = (clientH - divH) / 2 + $win.scrollTop();  
            var l = (clientW - divW) / 2 + $win.scrollLeft();  
            $menuList.css('display', 'block').offset({ 'top': t , 'left': l });  
      
            $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
            flag = true;  
      
            $close.click(function () {  
                flag = false;  
                $menuList.hide();  
                $mask.hide();  
            });  
      
      
            $(window).scroll(setMask).resize(setMask);  
      
            function setMask() {  
                if (flag) {  
                    $menuList.css('display', 'block').offset({ 'top': ($win.height() - $menuList.height()) / 2 + $win.scrollTop(), 'left': ($win.width() - $menuList.width()) / 2 + $win.scrollLeft() });  
                    $mask.show().css({ width: clientW + 'px', height: clientH + 'px' }).offset({ left: $win.scrollLeft(), top: $win.scrollTop() });  
                } else {  
                    $menuList.hide();  
                    $mask.hide();  
                }  
            }  
        });
    });     
  	
  	$(document).ready(function(){
	  $("#deleteall").click(function(){
	    deleteUser();
	});
	
	
	$("#adduser").click(function(){
	             
	    $("#div1").show();
	
	
	})
	$('#closeuser').click(function(){
	  //alert(111);
	 $("#div1").hide();
	
	})
	
 	
	

});



</script>

<script language="JavaScript" type="text/JavaScript">
	<!--
	
		/*
		 * 提交权限
		 */		
		function check(name)
		{
			var visitor = document.forms[0].visitor_id.value;
			var f = document.getElementsByName("ResourceId");
			var c=0;	   
			for(var i=0; i<f.length ;i++ )
			{
			    if(f[i].checked)
			    {
			    	 c++;
			    }
			}
			if(c==0)
			{
			     alert("请选择权限!");
			     return;
			}
			if(confirm("确定授予这 " + c + " 个权限吗?"))
			{
				document.editForm.submit();
			}
		}
		
		function checkSub(id)
		{
			var srcE = document.getElementById("Resource_" + id);
			var isChecked = srcE.checked;
			//alert('isChecked = ' + isChecked);
			
			 $("[name='ResourceId']").each(function(){
			    //alert($(this).attr('resourceParentId'))
			    //alert($(this).attr('resourceParentId') + "  " + id + ", ? " + ($(this).attr('resourceParentId') == id));
			    if($(this).attr('resourceParentId') == id)
			    {
			    	//alert("bing-----------------------");
			    	$(this).attr('checked', isChecked);
			    }
			 });
		}
		
		/*
		 * 检查父权限是否点选上了
		 */
		function checkParent(id, pid)
		{
			var srcE = document.getElementById("Resource_" + id);
			var isChecked = srcE.checked;
			
			//检查是否还有子权限，如果有的话，就在调用checkSub(id)方法
			
			var psrcE = document.getElementById("Resource_" + pid);
			if(isChecked && psrcE != null)
			{
				psrcE.checked = isChecked;
				if(psrcE.resourceParentId)
				{
					checkParent(pid, psrcE.resourceParentId);
				}
				
			}
		}
	
		function reset()
		{
			document.forms[0].reset();
		}
		
		function checkForm()
		{
			 //alert($("#userIdInput").val());
		}
		
		
		function changeEmployee (userId){
		  var url = "<%=request.getContextPath()%>/toeditEmployee.action?userId="+userId;
		  window.location.href=url;
		}
		    /**校验登录名是否出现重复*/
    function checkUser(o){
    	//alert(o.value);//dom的写法
    	//alert($(o).val());//jquery的写法
    	var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
    	var logonName = $(o).val();
    	if(reg.test(logonName)){
    	 $("#userAccount").html("<font id='wrn4' color='red'>用户名不能包含汉字</font>");
    	  $(o).val("");
    	  $(o)[0].focus();
    	  return false;
		  
    	}
    	//以登录名作为查询条件，查询该登录名是否在数据库表中存在记录
    	$.post("checkUser.action",{"userAccount":logonName},function(data){
    		//如果栈顶是模型驱动的对象，取值的时候应该使用data.message的方式
    		//如果栈顶是模型驱动的对象的某个属性，取值的时候应该使用data即可
    	 	if(data==1){
    		   if($("#wrn1")){
    		   $("#wrn1").remove();
    		   }
    		   if($("#wrn2")){
    		   $("#wrn2").remove();
    		  } if($("#wrn4")){
    		   $("#wrn4").remove();
    		  }
    		  
    		  if($("#wrn3").length == 0){
				$("#userAccount").html("<font id='wrn3' color='red'账号不能为空</font>");
				$(o)[0].focus();
				}
			}                      
            else if(data==2){
			   if($("#wrn1")){
    		   $("#warn1").remove();
    		   }
    		   if($("#wrn3")){
    		   $("#wrn3").remove();
    		  }
    		  if($("#wrn4")){
    		   $("#wrn4").remove();
    		  }
    		   if($("#wrn2").length == 0){
				$("#userAccount").html("<font id='wrn2' color='red'>账号已经存在</font>");
				$(o)[0].focus();
				}
			}
			else{
			     if($("#wrn2")){
    		   $("#wrn2").remove();
    		   }
    		   if($("#wrn3")){
    		   $("#wrn3").remove();
    		  }
    		  if($("#wrn4")){
    		   $("#wrn4").remove();
    		  }
    		   $("#warn1").remove();
    		  if($("#wrn1").length == 0)
				{
				$("#userAccount").html("<font id='wrn1' color='green'>该账号可以使用</font>");
				}
			}
    	});
    }
		//-->
</script>

</head>


<body>

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a>
			</li>
			<li><a href="javascript:void(0)">运营管理</a>
			</li>
			<li><a href="javascript:void(0)">权限管理</a>
			</li>
		</ul>
	</div>

	<div class="rightinfo">
	   <div class="tools">
    
        <ul class="toolbar" style="margin-left: 9px;">
       <li  id="adduser"><span><img src="images/t01.png" /></span>增加管理员</li>
       <!--  <li id="deleteall"><span><img id="delete" src="images/t03.png" /></span>删除</li>  -->
        </ul>        
        <ul class="toolbar1">
        </ul>
    
    </div>
    <form id="privilegeForm" name="privilegeForm" method="post" action="<%=request.getContextPath()%>/privilegeUserList.action.action" enctype="multipart/form-data">
	    <table class="tablelist">
			<thead>
				<tr>
					<th>序号<i class="sort"><img src="images/px.gif" />
					</i>
					</th>
					<th>用户账号</th>
					<th>用户姓名</th>
					<th>注册时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>   
			<s:if test="#request.userList!=null && #request.userList.size()>0">
				<s:iterator var="user" value="#request.userList" status="st">
		        <tr>
			        <td><s:property value="#st.count"/></td>
			        <td><s:property value="userName"/><s:if test="#user.userType == 3">(超级管理员)</s:if></td>
			        <td><s:property value="realName"/></td>
			        <td><s:date name="registerTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			        <td>
			        	<s:if test="#user.userType == 1">
				        <a href="<%=request.getContextPath()%>/privilegeChange2Admin.action?userId=<s:property value='userId'/>" class="tablelink">升级为管理员</a>
			        	</s:if>
			        	<s:if test="#user.userType == 2">
				        <a href=" javascript:void(0)" class="click" userId="<s:property value='userId'/>">查看权限并授权</a>
				         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				        <a href=" javascript:changeEmployee('<s:property value='userId'/>')"  >修改信息</a>
			        	</s:if>
					</td>        	
		        </tr> 
		    	</s:iterator>
			</s:if>   
			</tbody>
		</table>
	   
    <table width="98%" class="splitPage">
		<tr id=null>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="privilegeForm" title="记录" unit="条记录" actionName="privilegeUserList.action"/>
			</td>
		</tr>
	</table>
    
	
</form>

	</div>
	
     <!--新增用户浮动框  -->
	<div id="menuList">
		<p class="closeP">
			<a id="close">关闭</a>
		</p>
		<div class="formbody" id="formbody">

			<div class="formtitle">
				<span>为管理员授权</span>
			</div>
    		
    		<ul class="forminfo">
    			<form id="resourceForm" name="resourceForm" method="post" action="<%=request.getContextPath()%>/priveliegeAddPrivilege.action" onsubmit="checkForm()">
				<input type="hidden" id="userIdInput" name="userId" value=""/>
				<s:if test="#request.resourceList!=null && #request.resourceList.size()>0">
					<s:iterator var="resource1" value="#request.resourceList" status="resourceStatus">
						<s:if test="#resource1.resourcePid == \"0\"">
							<input type="checkbox" id="Resource_<s:property value="resourceId"/>" resourceId="<s:property value="resourceId"/>" name="ResourceId" value="<s:property value="resourceId"/>" onClick="javascript:checkSub('<s:property value="resourceId"/>')" resourceParentId="<s:property value="resourcePid"/>"/>
							<Label FOR="ids_<s:property value="resourceId"/>"><font class="zi_3F6293"><s:property value="resourceName"/></font></Label><br/>
							
							<s:iterator var="resource2" value="#request.resourceList2" status="resourceStatus2">
								<s:if test="#resource2.resourcePid == #resource1.resourceId">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="Resource_<s:property value="resourceId"/>" resourceId="<s:property value="resourceId"/>" name="ResourceId" value="<s:property value="resourceId"/>" onClick="javascript:checkParentAndChild('<s:property value="resourceId"/>','<s:property value="resourcePid"/>')"  resourceParentId="<s:property value="resourcePid"/>"/>
									<Label FOR="ids_<s:property value="resourceId"/>"><font class="zi_3F6293"><s:property value="resourceName"/></font></Label><br/>
								</s:if>
							</s:iterator>
						</s:if>
					</s:iterator>
				</s:if>

			    <li><input name="" type="submit" class="btn" value="确认保存" class="dfinput"/>
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    <input name="" type="reset" class="btn" value="重置" class="dfinput"/>
			    </li>

				</form>
			</ul>
			
		</div>
	</div>
        <!--新增用户浮动框  -->
    <div  id="div1">
    <!-- //<input type="button" value="关闭" id="close" /> -->
    <p id="closeuser" class="close"><a href="javascript:void(0)" id="close">关闭</a></p>
    <div class="formbody">
    
    <div class="formtitle"><span>创建账号</span></div>
    
     <ul class="forminfo">
    <form id="Form1" name="Form1" method="post" action="<%=request.getContextPath()%>/addEmployee.action" >
    <li><label>账号</label><input id="userName" name="userName" type="text"  class="dfinput" onblur="checkUser(this);" tip="账号不能为空"/><i id="userAccount"></i></li>
    <li><label>密码</label><input id="userPassword" name="userPassword" type="password" class="dfinput" id="psw1" tip="密码不能为空"/></li>
    <li><label>邮箱</label><cite><input name="userEmail"   type="text"  class="dfinput" id="password"  tip="请再次输入密码"/><i id="checkPSW"></i></li></li>
    <li><label>姓名</label><input name="realName" id="realName" type="text" class="dfinput" rg1="S{1,}" tip="昵称不能为空" /></li>
    <li style="margin-left: 10%;"><input name="" type="submit"  class="btn" value="确认保存" onsubmit="return toVaild()" class="dfinput"/>
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
    
    
 
	<div id="mask"></div>  
    <script type="text/javascript">
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>

</body>

</html>