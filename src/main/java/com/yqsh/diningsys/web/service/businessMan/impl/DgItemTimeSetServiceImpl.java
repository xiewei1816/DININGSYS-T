package com.yqsh.diningsys.web.service.businessMan.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.web.dao.businessMan.DgItemTimeSetMapper;
import com.yqsh.diningsys.web.model.businessMan.DgItemTimeSet;
import com.yqsh.diningsys.web.service.businessMan.DgItemTimeSetService;

/**
 * 消费品项显示设置实现层
 * @author xiewei
 *
 */
@Service
public class DgItemTimeSetServiceImpl implements DgItemTimeSetService{

	 @Resource
	 private DgItemTimeSetMapper dgItemTimeSetMapper;
	 
	 @Override
	 public List<DgItemTimeSet> getAllList() {
		 return dgItemTimeSetMapper.getAllList();
	 }
	 
	 @Override
	 public int insertOrUpdDgItemTimeSet(DgItemTimeSet dgItemTimeSet) {
		 int result = 0;
		 if(dgItemTimeSet.getId() != null && dgItemTimeSet.getId() > 0){
		 	 dgItemTimeSet.setUpdateTime(new Date());
			 result = dgItemTimeSetMapper.update(dgItemTimeSet);
		 }else{
			 dgItemTimeSet.setCreateTime(new Date());
			 result = dgItemTimeSetMapper.newInsert(dgItemTimeSet);
		 }
		 return result;
	 }
}


