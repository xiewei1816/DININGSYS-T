package com.yqsh.diningsys.core.util;

/**
 * 系统常量定义
 * @author vincent chen
 *
 */
public class Constants implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6776558624878361254L;

	public static final int DEFAULT_PAGE_SIZE = 20;
    
    public static final String DBTYPE_STR="string";
    
    public static final String DBTYPE_INT="int";
    
    public static final String DB_STUID="STUID";
    /**
     * 支付类型:WX,微信支付
     */
    public static final String  PAY_TYPE_WX="WX";
    /**
     * 支付类型:ZFB,支付宝支付
     */
    public static final String  PAY_TYPE_ZFB="ZFB";
}
