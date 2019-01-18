package com.yqsh.diningsys.web.dao;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.SysUser;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-08-17 16:08
 */
@Repository
public interface PersonalCenterMapper extends GenericDao<SysUser,Integer> {

    void updateUserIcon(Map param);

}
