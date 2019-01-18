package com.yqsh.diningsys.core.util;

import java.util.Collections;
import java.util.List;


/**
 * 表示分页中的一页。
 * 
 * @author  vincent chen
 */
public class Page<E> implements java.io.Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2463438043399902580L;
	private boolean hasPre;//是否首页
    private boolean hasNext;//是否尾页
    private List<E> rows;//当前页包含的记录列表
    private int index;//当前页页码(起始为1)
    private IPageContext<E> context;
    
    private int page;
    private int total;
    private int records;
    
    public IPageContext<E> getContext() {
        return this.context;
    }

    public void setContext(IPageContext<E> context) {
        this.context = context;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isHasPre() {
        return this.hasPre;
    }

    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }

    public boolean isHasNext() {
        return this.hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<E> getItems() {
        return this.rows == null ? Collections.<E>emptyList() : this.rows;
    }

    public void setItems(List<E> items) {
        this.rows = items;
    }

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}
    
    
    
}
