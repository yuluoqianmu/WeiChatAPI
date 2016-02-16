package com.laipai.app.common;

import java.io.Serializable;
import java.util.List;

public class Page<T extends Object> implements Serializable {

	private int rowCount;
	private int pageCount;
	private int pageSize;
	private int prePage;
	private int nextPage;
	private int page;

	private List<T> list;

	public Page() {

	}

	public Page(int page, int rowCount, int pageSize, List<T> list) {
		this.rowCount = rowCount;
		this.pageSize = pageSize;
		this.list = list;
		this.page = page;

		this.pageCount = (rowCount + pageSize - 1) / pageSize;

		this.prePage = page - 1;
		if (prePage < 1) prePage = 1;

		this.nextPage = page + 1;
		if (nextPage > pageCount) this.nextPage = pageCount;

	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
