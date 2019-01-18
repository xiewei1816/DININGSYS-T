package com.yqsh.diningsys.web.service.base;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.SysOrgan;

import java.util.List;

/**
 * 这里用一句话来说明该类的作用
 *
 * @author zhshuo create on 2016-07-29 11:04
 */
public interface SysOrganService extends GenericService<SysOrgan,Integer> {

    List<SysOrgan> selectAllSysOrgan();

    List<Integer> selectDeleteIdByParentId(Integer id);

    Integer getNextMaxOrderNo(Integer id);

}
