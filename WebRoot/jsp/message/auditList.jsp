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

</script>
  </head>
  
  <body>
   <div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">消息管理</a></li>
    <li><a href="#">审核通过</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
   <form id="checkForm" name="checkForm" method="post" action="<%=request.getContextPath()%>/checkAction_queryAllAppForm.action" enctype="multipart/form-data"> 
    <table class="tablelist" style="width:1390px;">
    	<thead>
    	<tr>
        <th><input name="" type="checkbox" value="" id="checkAll"/></th>
        <th>序号<i class="sort"><img src="images/px.gif" /></i></th>
        <th>申请人账号</th>
        <th>申请时间</th>
        <th>当前状态</th>
        </tr>
        </thead>
        <tbody>   
	<s:if test="#request.appformList!=null && #request.appformList.size()>0">
		<s:iterator value="#request.appformList" id="appform" status="st">
        <tr>
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
        </tr> 
     </s:iterator>
</s:if>        
    </table>
     <table width="98%" class="splitPage">
		<tr>
			<td class="zi_3F6293" align="right">
				<page:page name="pageController" frmName="checkForm" title="记录" unit="条记录" actionName="checkAction_queryAllAppForm.action"/>
			</td>
		</tr>
	</table>

 </form>
  </body>
</html>
