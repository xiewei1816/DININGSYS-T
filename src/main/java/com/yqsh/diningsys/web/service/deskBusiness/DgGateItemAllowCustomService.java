package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;



import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgGateItemAllowCustom;

public interface DgGateItemAllowCustomService extends GenericService<DgGateItemAllowCustom,Integer>{
    // 查询是否有小类id,没有就创建
    int selectByGateItemId(int id);
    //获取所有数据
    List<DgGateItemAllowCustom> getAll();
    int deleteByGateItemId(int id);
    int deleteByGateId(int id);
}
