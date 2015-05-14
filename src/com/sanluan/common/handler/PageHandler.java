package com.sanluan.common.handler;

import static com.sanluan.common.constants.CommonConstants.DEFAULT_PAGE_SIZE;

import java.util.List;

/**
 * @author zhangxd
 * 
 * @param
 */
public class PageHandler implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int totalCount;
	private int pageSize;
	private int pageNo;
	private List<?> list;

	/**
	 * @param pageNo
	 * @param pageSize
	 * @param totalCount
	 */
	public PageHandler(int pageNo, int pageSize, int totalCount) {
		setTotalCount(totalCount);
		setPageSize(pageSize);
		setPageNo(pageNo);
		init();
	}

	/**
	 * @param pageNo
	 * @param pageSize
	 * @param totalCount
	 * @param maxResults
	 */
	public PageHandler(int pageNo, int pageSize, int totalCount, Integer maxResults) {
		setTotalCount(null != maxResults && maxResults < totalCount ? maxResults : totalCount);
		setPageSize(pageSize);
		setPageNo(pageNo);
		init();
	}

	public void init() {
		pageSize = 1 > pageSize ? DEFAULT_PAGE_SIZE : pageSize;
		totalCount = 0 > totalCount ? 0 : totalCount;
		pageNo = 1 > pageNo ? 1 : pageNo > getTotalPage() ? getTotalPage() : pageNo;
	}

	public int getTotalPage() {
		int totalPage = totalCount / pageSize;
		return (0 == totalPage || 0 != totalCount % pageSize) ? ++totalPage : totalPage;
	}

	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	public int getLastResult() {
		return pageNo * pageSize > totalCount ? pageNo * pageSize : totalCount;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return
	 */
	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	/**
	 * @return
	 */
	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	/**
	 * @return
	 */
	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	/**
	 * @return
	 */
	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the list
	 */
	public List<?> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<?> list) {
		this.list = list;
	}
}
