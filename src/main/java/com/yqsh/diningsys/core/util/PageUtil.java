package com.yqsh.diningsys.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;


/**
 * @author  vincent chen
 * @version 1.0, Sep 17, 2010
 * @since 1.0
 */
public class PageUtil implements java.io.Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6008872672101139393L;
	private static final Logger LOGGER = LoggerFactory.getLogger(PageUtil.class);
    /**
     * 获取主键时缓存
     */
    private static Map<Class<?>, Field> classPKMap = new WeakHashMap<Class<?>, Field>();
    
    /**
     * 不关心总记录数
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static int getPageStart(int pageNumber, int pageSize) {
        return (pageNumber - 1) * pageSize;
    }
    
    /**
     * 计算分页获取数据时游标的起始位置
     * 
     * @param totalCount 所有记录总和
     * @param pageNumber 页码,从1开始
     * @return
     */
    public static int getPageStart(int totalCount, int pageNumber, int pageSize) {
        int start = (pageNumber - 1) * pageSize;
        if (start >= totalCount) {
            start = 0;
        }

        return start;
    }

    /**
     * 构造分页对象
     * 
     * @param totalCount 满足条件的所有记录总和
     * @param pageNumber 本次分页的页码
     * @param items
     * @return
     */
    public static <E> Page<E> getPage(int totalCount, int pageNumber, List<E> items, int pageSize) {
        IPageContext<E> pageContext = new QuickPageContext<E>(totalCount, pageSize, items);
        return pageContext.getPage(pageNumber);
    }
    
}
