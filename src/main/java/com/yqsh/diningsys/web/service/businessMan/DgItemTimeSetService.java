package com.yqsh.diningsys.web.service.businessMan;

import java.util.List;

import com.yqsh.diningsys.web.model.businessMan.DgItemTimeSet;

/**
 * 品项显示时间设置服务层
 * @author xiewei
 * 
 */
public interface DgItemTimeSetService{
	
	/**
	 * 获取所有消费品项显示设置信息
	 * @return
	 */
	List<DgItemTimeSet> getAllList();

	/**
	 * 新增、修改消费品项显示设置信息
	 * @param DgItemTimeSet
	 * @return
	 */
	int insertOrUpdDgItemTimeSet(DgItemTimeSet dgItemTimeSet);

}
