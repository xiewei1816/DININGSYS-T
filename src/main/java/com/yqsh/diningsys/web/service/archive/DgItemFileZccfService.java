package com.yqsh.diningsys.web.service.archive;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFileZccf;

public interface DgItemFileZccfService extends GenericDao<DgItemFileZccf,Integer>{
    /**
     * 根据品项id获取组成成分
     * @param id
     * @return
     */
    List<DgItemFileZccf> getZccfByItemId(Integer id);
    
    
    /**
     * 
     * 查询已有成分
     */
    List<DgItemFileZccf> selectHaveItem(String s);
    
    
   /**
    * 
    * 批量插入
    */
    int insertList(HttpServletRequest request);
    
    /**
     * 删除itemId所有数据
     */
    int deleteByItemId(Integer id);
}