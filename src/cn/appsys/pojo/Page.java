package cn.appsys.pojo;
/**
 * 分页所需实体类
 * @author A
 *
 */
public class Page {
	private Integer currPageNo; // 当前页码 curr是current单词的简写，当前的意思
	private Integer pageSize; // 每一页显示的数据行数，页面大小
	private Integer totalCount; // 总的记录数。
	private Integer totalPageCount; // 总的页数

	public Integer getCurrPageNo() {
		return currPageNo;
	}

	public void setCurrPageNo(Integer currPageNo) {
		this.currPageNo = currPageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	// 计算总的页数
	public void setTotalCount(Integer totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;

			// 根据总记录数以及每页显示的数据行数，计算总的页数， 如：总记录数是52， 每页显示5条记录，问：总的页数为？
			this.totalPageCount = this.totalCount % this.pageSize == 0 
					? this.totalCount / this.pageSize
					: this.totalCount / this.pageSize + 1;
		}
	}

	public Integer getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(Integer totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
}
