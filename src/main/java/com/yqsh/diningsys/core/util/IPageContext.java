package com.yqsh.diningsys.core.util;

/**
 * 分页上下文环境。用于计算Page。
 *
 */
public interface IPageContext<E> {
    
    /**
     * 默认设定每页显示记录数为10
     */
    int DEFAULT_PAGE_SIZE = Constants.DEFAULT_PAGE_SIZE;
    
    /**
     * 计算总页数.
     * 
     * @return
     */
    int getPageCount();
    

    /**
     * 返回 Page 对象.
     * 
     * @param index
     * @return
     */
    Page<E> getPage(int index);
    
    /**
     * 每页显示的记录数量
     * 
     * @return
     */
    int getPageSize();
    
    /**
     * 计算总记录数
     * 
     * @return
     */
    int getTotal();
    
}
