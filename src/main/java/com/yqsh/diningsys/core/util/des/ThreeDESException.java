package com.yqsh.diningsys.core.util.des;

/** 
 * 3DES异常类.
 * @ClassName: ThreeDESException
 * @author hepx
 */
public class ThreeDESException extends RuntimeException {

	/**
	 * @Fields serialVersionUID : 序列化ID.
	 */
	private static final long serialVersionUID = 962301413622399815L;

	/** 
	 * 创建新的实例.
	 * @param msg 消息
	 * @param throwable 异常
	 */
	public ThreeDESException(String msg,Throwable throwable)
	{
		super(msg,throwable);
	}

}
