package com.yqsh.diningsys.web.service.sysSettings;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.sysSettings.DgUrlSetting;

import java.util.List;

public interface DgUrlSettingService extends GenericService<DgUrlSetting,Integer>{
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
    Page<DgUrlSetting> getListByPage(DgUrlSetting page);
    
    
    int insertOrUpdate(DgUrlSetting page);
    
    /**
     * 按code查询
     */
	DgUrlSetting selectByCode(String code);

    List<DgUrlSetting> selectAllData();
}
