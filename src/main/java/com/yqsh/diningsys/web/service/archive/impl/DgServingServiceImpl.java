package com.yqsh.diningsys.web.service.archive.impl;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.DgServingMapper;
import com.yqsh.diningsys.web.model.archive.DgServing;
import com.yqsh.diningsys.web.service.archive.DgServingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-09-29 16:37
 */
@Service
public class DgServingServiceImpl extends GenericServiceImpl<DgServing,Integer> implements DgServingService {

    @Resource
    private DgServingMapper dgServingMapper;

    @Override
    public GenericDao<DgServing, Integer> getDao() {
        return dgServingMapper;
    }

    @Override
    public Page<DgServing> getAllData(DgServing record) {
        Integer totle = dgServingMapper.countAllData(record);
        List<DgServing> list = dgServingMapper.getAllData(record);
        return PageUtil.getPage(totle, record.getPage(),list, record.getRows());
    }

    @Override
    public int addData(DgServing dgServing) {
        dgServing.setCreateTime(new Date());
        return dgServingMapper.insertSelective(dgServing);
    }

    @Override
    public int updateData(DgServing dgServing) {
        return dgServingMapper.updateByPrimaryKeySelective(dgServing);
    }

    @Override
    public int deleteDataWithLogic(String ids) {
        List id = new ArrayList();
        Collections.addAll(id,ids.split(","));
        return dgServingMapper.deletDataWithLogic(id);
    }

    @Override
    public int deleteData(String ids) {
        List id = new ArrayList();
        Collections.addAll(id,ids.split(","));
        return dgServingMapper.deletData(id);
    }

    @Override
    public DgServing selectById(DgServing dgServing) {
        return dgServingMapper.selectByPrimaryKey(dgServing.getId());
    }

	@Override
	public List<DgServing> getAll() {
		// TODO Auto-generated method stub
		return dgServingMapper.getAll();
	}

    @Override
    public String selectMaxNum() {
    	String zero = "";
    	Integer maxCode = 0;
        String code =  dgServingMapper.selectMaxNum();
        if(code != null){
        	maxCode = Integer.parseInt(code) + 1;
        }else{
        	maxCode = 1;
        }
        for (int i = 0; i < 3 - maxCode.toString().length(); i++) {
        	zero += "0";
		}
        return zero+(maxCode);
    }

}
