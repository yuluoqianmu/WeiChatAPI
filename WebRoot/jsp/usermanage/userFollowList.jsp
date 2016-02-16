<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/WEB-INF/page.tld" prefix="page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>菜单页</title>
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/datepicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.autocomplete.js"></script>
	<link href="<%=request.getContextPath()%>/css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />	
	<style type="text/css">  
		/*浮动 **/
	   #menuList {
	  		background-color:#fff;
	        border:5px solid rgba(0,0,0,0.4);
	        height:400px;
	        left:70%;
	        padding:1px; 
	        width:580px;  
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
      
       $(".click").click(function () {                  

			if($(this).attr("followId"))
			{
				var ajaxResult =$.ajax({url:"<%=request.getContextPath()%>/userFollowGetFollowById.action?followId=" + $(this).attr("followId") + "&time=" + new Date(), async:false});
	            var responseText = ajaxResult.responseText;
	            //alert(" responseText = " + responseText);
	            if(responseText != null)
	            {
	            	var resourceJson = eval("(" + responseText + ")");
	            	var resourceIds =  eval("(" + resourceJson.result + ")");
	            	//alert('xxx = ' + resourceIds);
	            	//checkResurce(resourceIds);
	            	//设置表单
	            	$("#userId").val(resourceIds.userId);
	            	$("#userName").val(resourceIds.userName);
	            	$("#cameraId").val(resourceIds.cameraId);
	            	$("#cameraName").val(resourceIds.cameraName);
	            	$("#followTime").val(resourceIds.followTime);
	            }
            }
            else
            {
	            	$("#userId").val('');
	            	$("#userName").val('');
	            	$("#cameraId").val('');
	            	$("#cameraName").val('');
	            	$("#followTime").val('');
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

	  //var data1 = [{"userId":"1","userName":"xbl"},{"userId":"2c9288864a61058f014a61ac161e0001","userName":"lxd"}];
	  //'<%=request.getContextPath()%>/searchUserInfoByUserName.action'
	  $("#userName").autocomplete('<%=request.getContextPath()%>/searchUserInfoByUserName.action', {
			max : 12,
			extraParams:{userName:function(){return $("#userName").val()}},
			autoFocus: true,
			minChars : 1,
			/* width : 152, */
			cacheLength:1,
			matchContains : true,
			mustMatch : true,
			autoFill : true,
			matchCase:true,
			formatItem : function(row, i, max) {
				//alert("formatItem   " + row[0] + ", " + row[1]);
				return row[0];
			},formatResult: function(row, i, max){
			    return row[0];
			}

		}).result(function(event, item) {
			//alert("result-------------" + item[1] + ", id = " + item[0]);
			$("#userId").val(item[1]);
			//alert('value  = ' + $("#userId").val());
		});


	  $("#cameraName").autocomplete('<%=request.getContextPath()%>/searchUserInfoByUserName.action', {
			max : 12,
			extraParams:{userName:function(){return $("#cameraName").val()}},
			autoFocus: true,
			minChars : 1,
			/* width : 152, */
			cacheLength:1,
			matchContains : true,
			mustMatch : true,
			autoFill : true,
			matchCase:true,
			formatItem : function(row, i, max) {
				//alert("formatItem   " + row[0] + ", " + row[1]);
				return row[0];
			},formatResult: function(row, i, max){
			    return row[0];
			}

		}).result(function(event, item) {
			//alert("result-------------" + item[1] + ", id = " + item[0]);
			$("#cameraId").val(item[1]);
			//alert('value  = ' + $("#userId").val());
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

});

	function checkForm()
	{   
	    var checkflag=true;
       	var userId=$("#userId").val();
        var cameraId=$("#cameraId").val();
       	if(userId==cameraId){
       	 alert('亲~太任性了吧，怎么可以自己关注自己呢？');
       	 return false;
       	}
       	if($("#followTime").val() == "")
       	{
       		alert('请正确输入关注时间');
       		return false;
       	} 
       checkFollow(userId,cameraId);
       var checkflag=$("#checkflag").val();
        if(checkflag=="YES"){
          alert('此对关系已经建立过了，请不要重复建立');
       		return false;
        }
		return true;
	}

   function checkFollow(userId,cameraId){
            $.ajax({
            async:false,
            type: "post",
            url: "checkFollow.action",           
            dataType: "text",           
            data: {userId:userId, cameraId:cameraId},
            success:function(data){
               $("#checkflag").val(data);
                     }
         });

   
   }
 function  deletefollowByid(followId){
    if(confirm("确定要删除吗?")){
       var option = {
			url:"deleteFollowById.action",
			type:"post",
			dataType:"json",
			data:{
				followId:followId
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
function deleteUser(){
    var f = document.getElementsByName("followId");
			var c=0;	
			var ids = "";
			var id = "";
			var i = 0;
			for(var j=0; j<f.length ;j++ ){
			    if(f[j].checked){
			    	i++;
			        id = f[j].value;
			        ids = ids + "&followId=" + id;
			    }
			}

			if(i == 0){
			     alert("请至少选择一条关注!");
			     return;
			}

			if(confirm("确定要删除吗?"))
			{
				document.location="<%=request.getContextPath()%>/deletefollow.action?" + ids;
			}
  }
	</script>

</head>

<body>

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">首页</a>
			</li>
			<li><a href="#">用户管理</a>
			</li>
			<li><a href="#">关注管理</a>
			</li>
		</ul>
	</div>

	<div class="rightinfo">
		<div class="tools">
			<ul class="toolbar">
				<li class="click"><span><img src="images/t01.png" />
				</span>添加</li>
				<li id="deleteall"><span><img id="delete"
						src="images/t03.png" />
				</span>删除</li>
			</ul>
			<ul class="toolbar1">
			</ul>
		</div>
	</div>
	
	<form id="userForm" name="userForm" method="post" action="<%=request.getContextPath()%>/queryall.action" enctype="multipart/form-data"> 
    <table class="tablelist">
		<thead>
			<tr>
				<th><input name="checkAllFollowId"  type="checkbox" value="" id="checkAll" /></th>
				<th>序号<i class="sort"><img src="images/px.gif" /></i></th>
				<th>关注者</th>
				<th>关注时间</th>
				<th>关注的摄影师</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<s:if test="#request.followList!=null && #request.followList.size()>0">
		<s:iterator var="follow" value="#request.followList" status="st">
			<tr>
				<td><input type="checkbox"  name="followId" value="<s:property value='#follow.followId'/>"/></td>
				<td><s:property value="#st.count"/></td>
				<td><s:property value="#follow.userName"/></td>
				<td><s:date name="#follow.followTime" format="yyyy-MM-dd hh:mm:ss"/></td>
				<td><s:property value="#follow.cameraName"/></td>
				<td><a href="javascript:void(0)" onclick="deletefollowByid('<s:property value='#follow.followId'/>')" class="tablelink">删除</a></td>    
			</tr> 
		</s:iterator>
		</s:if>
		</tbody>
	</table>
    

     <table width="98%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="userForm" title="记录" unit="条记录" actionName="userFollowList.action"/>
			</td>
		</tr>
	</table>

	</form>

     <!--新增用户浮动框  -->
	<div id="menuList">
		<p class="closeP">
			<a href="javascript:void(0)" id="close">关闭</a>
		</p>
		<div class="formbody" id="formbody">

			<div class="formtitle">
				<span>建立关注</span>
			</div>

    		<ul class="forminfo">
    			<form id="resourceForm" name="resourceForm" method="post" action="<%=request.getContextPath()%>/userFollowEdit.action" onsubmit="return checkForm();">
			    <input id="followId" name="followId" type="text" value=""/>
			    <li>
			    	<label>关注者</label>
			    	<input id="userName" name="userName" type="text" class="dfinput" />
			    	<input id="userId" name="userId" value="<s:property value="#request.userId"/>" type="hidden">
			    	<i></i>
			    </li>
			    <li>
			    	<label>摄影师</label>
			    	<input id="cameraName" name="cameraName" type="text" class="dfinput" />
			    	<input id="cameraId" name="cameraId" value="<s:property value="#request.cameraId"/>" type="hidden">
			    	<i></i>
			    </li>
			    <li><label>关注时间</label>
			    	<input id="followTime" name="followTime" type="text" onFocus="WdatePicker({followTime:'',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartdate:true,maxDate:'%y-%M-%d'})" class="dfinput"/><i>请选择时间</i></li>
			    	<input id="checkflag" name="checkflag" type="hidden"  />
			    </br>
			     </br>
			    <li>
			    	<input name="" type="submit" class="btn" value="确认保存" class="dfinput"/>
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

	<div id="mask"></div>  
    <script type="text/javascript">
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
    
</body>
</html>