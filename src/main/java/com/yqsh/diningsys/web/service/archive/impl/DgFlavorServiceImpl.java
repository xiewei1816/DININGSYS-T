/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yqsh.diningsys.web.service.archive.impl;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.web.dao.archive.DgFlavorMapper;
import com.yqsh.diningsys.web.model.archive.DgFlavor;
import com.yqsh.diningsys.web.service.archive.DgFlavorService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author yqsh-zc
 */
@Service
public class DgFlavorServiceImpl extends GenericServiceImpl<DgFlavor,Integer> implements DgFlavorService{

     @Resource
    private DgFlavorMapper mapper;
    
    @Override
    public GenericDao<DgFlavor, Integer> getDao() {
        return mapper;
    }

    @Override
    public List<DgFlavor> getAllBeans(String hasParent) {
        if(hasParent.equals("1")){
           return mapper.getAllBeansWithOutRoot();
        }else{
            return mapper.getAllBeans();
        }
        
    }

    @Override
    public List<DgFlavor> getFlavorByParentId(Integer id) {
         return mapper.getFlavorByParentId(id);
    }

    @Override
    public DgFlavor getFlavorByNumber(String number) {
        return mapper.getFlavorByNumber(number);
    }

    @Override
    public void insertOrUpdateBeans(DgFlavor flavor) {
        if(StringUtil.isBlank(flavor.getIsonly())){
                flavor.setIsonly("0");
        }
        if(flavor.getId() != null){
            mapper.updateByPrimaryKey(flavor);
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            flavor.setCreatetime(sdf.format(new Date()));
            mapper.insert(flavor);
        }
    }

    @Override
    public List<DgFlavor> getTreeBean() {
        return mapper.getTreeBean();
    }
}
