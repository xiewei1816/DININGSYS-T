package com.yqsh.diningsys.web.service.archive.impl;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.DgProMethodsTypeMapper;
import com.yqsh.diningsys.web.model.archive.DgProMethodsType;
import com.yqsh.diningsys.web.service.archive.DgProMethodsTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-10-10 11:44
 */
@Service
public class DgProMethodsTypeServiceImpl extends GenericServiceImpl<DgProMethodsType,Integer> implements DgProMethodsTypeService{

    @Resource
    private DgProMethodsTypeMapper dgProMethodsTypeMapper;
    
    

    @Override
    public GenericDao<DgProMethodsType, Integer> getDao() {
        return dgProMethodsTypeMapper;
    }

    @Override
    public List<DgProMethodsType> selelctAllData() {
        return dgProMethodsTypeMapper.selelctAllTypes();
    }

    @Override
    public Page<DgProMethodsType> selectAllDataPage(DgProMethodsType dgProMethodsType) {
        Integer totle = dgProMethodsTypeMapper.countAllData(dgProMethodsType);
        List<DgProMethodsType> list = dgProMethodsTypeMapper.selectAllDataPage(dgProMethodsType);
        return PageUtil.getPage(totle, dgProMethodsType.getPage(),list, dgProMethodsType.getRows());
    }

    @Override
    public void updateData(DgProMethodsType dgProMethods) {
        dgProMethodsTypeMapper.updateByPrimaryKeySelective(dgProMethods);
    }

    @Override
    public void addData(DgProMethodsType dgProMethods) {
        dgProMethodsTypeMapper.insertSelective(dgProMethods);
    }

    @Override
    public void deleteData(String ids) {
        dgProMethodsTypeMapper.deleteData(arraysTOList(ids));
    }

    @Override
    public List<DgProMethodsType> selelctAllTypes() {
        return dgProMethodsTypeMapper.selelctAllTypes();
    }

	@Override
	public int deleteByPrimaryKey(int id) {
		return dgProMethodsTypeMapper.deleteByPrimaryKey(id);
	}
}
