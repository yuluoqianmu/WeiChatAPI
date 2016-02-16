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
<title>申请列表</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/style1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.form.js"></script>


<script type="text/javascript">
 $(function(){

            
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
	   $('.tablelist tbody tr:odd').addClass('odd');
   $("#deleteall").click(function(){
      deleteUser();
   });
   $("#waitCheck").click(function()
    {
      waitCheck();
    });
   $("#hasChecked").click(function()
    {
      hasChecked();
    });
   $("#unChecked").click(function()
    {
      unChecked();
    });
   });
  function waitCheck()
  {
    //$("#checkStatus").value = "0";
    document.location="<%=request.getContextPath()%>/queryAllAppForm.action?status=0";
  }
  function hasChecked()
  {
    //$("#checkStatus").value = "1";
    document.location="<%=request.getContextPath()%>/queryAllAppForm.action?status=1";
  }
  function unChecked()
  {
   // $("#checkStatus").value = "-1";
    document.location="<%=request.getContextPath()%>/queryAllAppForm.action?status=-1";
  }
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
			        ids = ids + "&appformId=" + id;
			    }
			}

			if(i == 0){
			     alert("请至少选择一个申请!");
			     return;
			}

			if(confirm("确定要删除吗?"))
			{
				document.location="<%=request.getContextPath()%>/deleteAppform.action?" + ids;
			}
  }

     function detailInfo(url){
    	 location.href = url;
     }
</script>
  </head>
  
  <body>
   <div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">摄影师审核</a></li>
    <li><a href="#">审核信息</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    <div class="tools">
    
    	<ul class="toolbar">
      <!--   <li class="click"><span><img src="images/t01.png" /></span>添加</li> -->
        <li id="deleteall"><span><img id="delete" src="images/t03.png" /></span>删除</li>
        </ul>        
        <ul class="toolbar1">
        
        <div style="float:left; width:500px;">
        <ul class="toolbar"><!-- style="visibility: hidden;" value="${status}" -->
         <input type="text" name="status" id="status" style="visibility: hidden;">
         <li class="click" id="waitCheck"><span>待审</span><span>${appformData.await }</span></li>
         <li class="click" id="hasChecked"><span>通过</span><span>${appformData.pass }</span></li>
         <li class="click" id="unChecked"><span>未通过</span><span>${appformData.fail }</span></li>
        </ul>        
        <ul class="toolbar1">
          
        </div>
    </ul>
    
    </div>
    </div>
    
   <form id="checkForm" name="checkForm" method="post" action="<%=request.getContextPath()%>/queryAllAppForm.action" enctype="multipart/form-data"> 
    <table class="tablelist">
    	<thead>
    	<tr align="center">
        <th width="4%"><input name="" type="checkbox" value="" id="checkAll"/></th>
        <th width="4%">序号<i class="sort"><img src="images/px.gif" /></i></th>
        <th width="10%">申请人账号</th>
        <th width="10%">申请时间</th>
        <th width="10%">当前状态</th>
        <th width="10%">操作</th>
        </tr>
        </thead>
        <tbody>   
	<s:if test="#request.appformList!=null && #request.appformList.size()>0">
		<s:iterator value="#request.appformList" id="appform" status="st">
        <tr align="center" ondblclick="detailInfo('<%=request.getContextPath()%>/checkAction_queryById.action?appformId=<s:property value="#appform.appformId"/>');">
        <td><input type="checkbox"  name="ids" value="<s:property value='#appform.appformId'/>"  /></td>
        <td><s:property value="#st.index+1"/></td>
        <td><s:property value="#appform.lpUser.userName"/></td>
        <td><s:date name="#appform.applyTime" format="yyyy-MM-dd hh:mm:ss"/></td>
        <td>
          <s:if test="#appform.checkStatus==0">
                                待审
           </s:if>
            <s:elseif test="#appform.checkStatus==1">
                                 通过
            </s:elseif>
             <s:else >
                                  未通过
             </s:else>
        </li>     
        </td>
        <td><a href="<%=request.getContextPath()%>/checkAction_queryById.action?appformId=<s:property value='#appform.appformId'/>" class="tablelink">查看</a>   
        </tr> 
     </s:iterator>
</s:if>        
    </table>
     <table width="98%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="checkForm" title="记录" unit="条记录" actionName="queryAllAppForm.action"/>
			</td>
		</tr>
	</table>

 </form>
  </body>
</html>
