package com.yqsh.diningsys.web.service.api;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;

public interface APIDiningTabService {
	/**
	 * 客位信息查询
	 * @param org
	 * @return
	 */
	Map queryTable(String org);
	/**
	 * 
	 * 菜品信息查询
	 * @param org
	 * @return
	 */
	Map queryItemFile(String org);
	/**
	 * 加单
	 * @param org
	 * @return
	 */
	Map insertOrderItemFile(String org,int type);
	
	/**
	 * 预订点菜(易小二before)
	 */
	Map insertPreOrderItemFileBefor(String org);
}
