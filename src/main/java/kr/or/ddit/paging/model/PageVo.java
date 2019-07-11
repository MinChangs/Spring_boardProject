package kr.or.ddit.paging.model;

public class PageVo {
	private int page; // 페이지 번호
	private int pageSize; // 페이지당 건수

	public PageVo() {

	}

	public PageVo(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page == 0 ? 1 : page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize == 0 ? 10 : pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "PageVo [page=" + page + ", pageSize=" + pageSize + "]";
	}

	
	//객체 비교가 아니라 서로 다른객체의 값만 비교 하기 위해서 hash코드와 equals를 구현
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getPage();
		result = prime * result + getPageSize();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageVo other = (PageVo) obj;
		if (getPage() != other.getPage())
			return false;
		if (getPageSize() != other.getPageSize())
			return false;
		return true;
	}
	
	
	private int board_id;
	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}


	


	public PageVo(int page, int pageSize, int board_id) {
		this.page = page;
		this.pageSize = pageSize;
		this.board_id = board_id;
	}

	

}
