package com.yqsh.diningsys.web.dao.sysSettings;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.sysSettings.DgUrlSetting;

@Repository
public interface DgUrlSettingMapper extends GenericDao<DgUrlSetting,Integer>{
	
	DgUrlSetting selectByPrimaryKey(Integer id);
    
    int insert(DgUrlSetting record);

    int insertSelective(DgUrlSetting record);
    
    int updateByPrimaryKey(DgUrlSetting record);
    
    /**
     * 分页查询
     * 
     */
    List<DgUrlSetting> getListByPage(DgUrlSetting page);
    
    int countListByPage(DgUrlSetting page);
    
    int selectCountByCodeOrName(DgUrlSetting codeorname);
    
    /**
     * 按code查询
     */
	DgUrlSetting selectByCode(String code);

    List<DgUrlSetting> selectAllData();
}