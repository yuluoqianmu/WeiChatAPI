package com.laipai.base.util.tags;
import java.io.*;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.jsp.tagext.*;
/**
 * 自定义分页标签类
 * @author chenguangyuan
 *
 */
public class PageTag extends TagSupport {
	private String name;

	private String unit;

	private String title;
	
	private String frmName;
	private String actionName;

	public String getFrmName() {
		return frmName;
	}

	public void setFrmName(String frmName) {
		this.frmName = frmName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public String getUnit() {
		return unit;
	}

	public String getTitle() {
		return title;
	}

	public PageTag() {
	}

	public int doEndTag() {
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			StringBuffer entListSB = new StringBuffer("");
			PageController pc = (PageController) request.getAttribute(name);

			if (pc == null)
				pageContext.getOut().println("");
			else {
				entListSB.append("<script type=\"text/javascript\">\n");
				entListSB.append("var currentPage="+pc.getCurrentPage()+";var totalPage="+pc.getTotalPages()+";");
				entListSB.append("function nextpage(pageNum){\n");
				entListSB.append("if(pageNum==''||!/^[0-9]\\d*$/.test(pageNum)||pageNum>totalPage){document.getElementById('currentPage').value=currentPage;return ;}");
				entListSB.append("document."+frmName+".action=\""+actionName+"?page=\"+pageNum;\n");
				entListSB.append("var result = false;\n");
				entListSB.append("var form = document."+frmName+"\n");
				entListSB.append("if(form.fireEvent){\n");
				entListSB.append("result=form.fireEvent('onsubmit');\n");
				entListSB.append("}else if(document.createEvent){\n");
				entListSB.append("var ev = document.createEvent('HTMLEvents');\n");
				entListSB.append("ev.initEvent('submit', false, true);\n");
				entListSB.append("result = form.dispatchEvent(ev);}\n");
				entListSB.append("if(result){document."+frmName+".submit();}}\n");
				entListSB.append("</script>\n");
				entListSB.append("<div style='float:right;margin-top:5px;'>");
				if (pc.isHasPrevious())
					entListSB.append( "<span title='首页'  name=\"first\" id=\"first\" onclick=\"nextpage(1);\" style=\"float:left;width:18px;height:24px;cursor: pointer;background: url('images/pagination_icons.png') no-repeat 0 0;\" ></span>");
				if (pc.isHasPrevious())
					entListSB.append("<span title='上一页'  name=\"forward\" id=\"forward\" onclick=\"nextpage("+pc.getPreviousPage()+");\" style=\"float:left;width:18px;height:24px;cursor: pointer;background: url('images/pagination_icons.png') no-repeat -16px 0;\" ></span>");
				if (pc.isHasNext())
					entListSB.append("<span title='下一页'  name=\"next\" id=\"next\" onclick=\"nextpage("+pc.getNextPage()+");\" style=\"float:left;width:18px;height:24px;cursor: pointer;background: url('images/pagination_icons.png') no-repeat -32px 0;\"  ></span>");
				if(pc.isHasNext())
				entListSB.append("<span title='末页'  name=\"last\" id=\"last\" onclick=\"nextpage("+pc.getTotalPages()+");\" style=\"float:left;width:18px;height:24px;cursor: pointer;background: url('images/pagination_icons.png') no-repeat -48px 0;\" ></span>");
				entListSB.append(
						//"每页显示 "
						//+ pc.getItemsPerPage()
						//+ " 共 "
						"<div style='float:left;'>" +
						"page "+
						"<input size='2' id='currentPage'  onChange=\"nextpage(this.value);\" value='"+pc.getCurrentPage()+"' style='text-align:center;border:1px;border-style:solid;border-color:#95B8E7;'>"
						+ " of "
						+ pc.getTotalPages()
						+ " 共 "
						+ pc.getTotalItems()
						+ "&nbsp;"
						+ unit+"</div>&nbsp;"
						//+ title
				);
		if(pc.getTotalPages()>1){
			entListSB.append("<select  id=\"page\" onchange=\"nextpage(this.value);\" >");
					for(int i=1;i<=pc.getTotalPages();i++){
						if(i==pc.getCurrentPage()){
							entListSB.append("<option value='"+i+"' selected>"+i+"</option>");
						}else{
							entListSB.append("<option value='"+i+"'>"+i+"</option>");
						}
					}
					entListSB.append("</select></div>");
					
		}		
			
				pageContext.getOut().println(entListSB.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

}
