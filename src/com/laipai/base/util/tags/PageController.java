package com.laipai.base.util.tags;
import javax.servlet.http.*;
/**
 * 分页控制类
 * @author chenguangyuan
 *
 */
public class PageController {
	private int totalItems; // 总数量

	private int itemsPerPage; // 每页显示数量

	private int currentPage; // 当前页

	private int totalPages; // 总页数

	private boolean hasNext; // 是否存在下一页

	private boolean hasPrevious; // 是否存在上一页

	private int startItem; // 当前页起始行

	private int endItem; // 当前页结束行

	private int nextPage;

	private int previousPage;

	private String description;

	private boolean totalSet;

	// 三种形式的构造函数
	public PageController(int totalItems) {

		this.itemsPerPage = 20;
		setTotalItems(totalItems);
		setCurrentPage(1);

	}

	public PageController(int totalItems, int rowsPerPage) {

		this.itemsPerPage = rowsPerPage;
		setTotalItems(totalItems);
		setCurrentPage(1);
	}

	public PageController(int totalItems, int rowsPerPage, int thisPage) {

		this.itemsPerPage = rowsPerPage;
		setTotalItems(totalItems);
		setCurrentPage(thisPage);
	}

	public void setTotalItems(int totalItems) {
		if (!this.totalSet) {
			this.totalItems = totalItems;
			if ((totalItems % itemsPerPage) != 0){
				this.totalPages = totalItems / itemsPerPage + 1;
			}else if(totalItems==0){
				this.totalPages =1;
			}
			else
				this.totalPages = totalItems / itemsPerPage;
			this.totalSet = true;
		}
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		this.nextPage = currentPage + 1;
		this.previousPage = currentPage - 1;

		// 计算当前页开始行和结束行
		if (currentPage * itemsPerPage < totalItems) {
			this.endItem = currentPage * itemsPerPage;
			this.startItem = currentPage * itemsPerPage - itemsPerPage + 1;
		} else {
			this.endItem = totalItems;
			this.startItem = itemsPerPage * (totalPages - 1) + 1;
		}
		// 如果输入的当前页超过总页数，则设置当前页为1
		if (((currentPage - 1) * this.itemsPerPage) > this.totalItems)
			this.currentPage = 1;

		// 是否存在前页和后页
		if (nextPage > totalPages) {
			hasNext = false;
		} else {
			hasNext = true;
		}
		if (previousPage == 0) {
			hasPrevious = false;
		} else {
			hasPrevious = true;
		}
		;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setEndItem(int endItem) {
		this.endItem = endItem;
	}

	public void setStartItem(int startItem) {
		this.startItem = startItem;
	}

	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getEndItem() {
		return endItem;
	}

	public int getStartItem() {
		return startItem;
	}

	public int getPreviousPage() {
		return previousPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public String getDescription() {
		String description = "Total:" + this.totalItems + " items "
				+ this.totalPages + " pages";
		return description;
	}

	/**
	 * 描述： 构造分页
	 * @param request
	 * @param pcName
	 * @param _totalItems 总条数
	 * @param _pageSize 每页条数
	 * @param _page 当前页
	 */
	public PageController(HttpServletRequest request, String pcName,
			int _totalItems, int _pageSize, int _page) {

//		if (request.getAttribute(pcName) == null) {
//			this.itemsPerPage = _pageSize;
//			setTotalItems(_totalItems);
//			// System.out.println("pc is null");
//		} else {
//			// System.out.println("pc is not null");
//			PageController pc = (PageController) request.getAttribute(pcName);
//			this.totalItems = pc.getTotalItems();
//			this.totalPages = pc.getTotalPages();
//			this.itemsPerPage = pc.getItemsPerPage();
//		}
		this.itemsPerPage = _pageSize;
		setTotalItems(_totalItems);
		this.setCurrentPage(_page);
	}
	public static int initPage(HttpServletRequest request) {
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		if (page < 1) {
			page = 1;
		}
		return page;
	}
	
	public static int initPage1(HttpServletRequest request) {
		int page = 1;
		if (request.getParameter("page1") != null) {
			page = Integer.parseInt(request.getParameter("page1"));
		}
		if (page < 1) {
			page = 1;
		}
		return page;
	}
}
