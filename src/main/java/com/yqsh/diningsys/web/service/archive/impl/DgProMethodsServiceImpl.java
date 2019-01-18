package com.yqsh.diningsys.web.service.archive.impl;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.DgProMethodsMapper;
import com.yqsh.diningsys.web.model.archive.DgProMethods;
import com.yqsh.diningsys.web.service.archive.DgProMethodsSerivce;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-10-10 11:44
 */
@Service
public class DgProMethodsServiceImpl extends GenericServiceImpl<DgProMethods,Integer> implements DgProMethodsSerivce{

    @Resource
    private DgProMethodsMapper dgProMethodsMapper;

    @Override
    public GenericDao<DgProMethods, Integer> getDao() {
        return dgProMethodsMapper;
    }

    @Override
    public Page<DgProMethods> selelctAllData(DgProMethods dgProMethods) {
        Integer totle = dgProMethodsMapper.countAllData(dgProMethods);
        List<DgProMethods> list = dgProMethodsMapper.selectAllData(dgProMethods);
        return PageUtil.getPage(totle, dgProMethods.getPage(),list, dgProMethods.getRows());
    }

    @Override
    public void updateData(DgProMethods dgProMethods) {
        dgProMethodsMapper.updateByPrimaryKeySelective(dgProMethods);
    }

    @Override
    public void addData(DgProMethods dgProMethods) {
        dgProMethodsMapper.insertSelective(dgProMethods);
    }

    @Override
    public void deleteData(String ids) {
        dgProMethodsMapper.deleteData(arraysTOList(ids));
    }

    @Override
    public List<DgProMethods> getProMethodsByType(String ids,Integer id) {
        Map param = new HashMap();
        List<Integer> withOutIds = arraysTOList(ids);
        param.put("list",StringUtils.isEmpty(ids) ? null :withOutIds);
        param.put("id",id);
        return dgProMethodsMapper.getProMethodsByType(param);
    }

    @Override
    public List<DgProMethods> getPubProMeInIds(String ids) {
        return dgProMethodsMapper.getPubProMeInIds(arraysTOList(ids));
    }

    @Override
    public List<DgProMethods> getPubProMeNotInIds(String ids) {
        return dgProMethodsMapper.getPublicProMethodsWithOutIds(arraysTOList(ids));
    }

    @Override
    public DgProMethods getProMethodByNumber(String number) {
        return dgProMethodsMapper.getProMethodByNumber(number);
    }
}
