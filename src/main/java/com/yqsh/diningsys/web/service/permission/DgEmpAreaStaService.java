package com.yqsh.diningsys.web.service.permission;

import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.permission.DgEmpAreaSta;

import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-11-25 上午11:02
 */
public interface DgEmpAreaStaService {

    /**
     * 查看是否已经启用员工查看消费区域统计信息权限
     * @return
     */
    Integer selectEmpAreaStaPer();

    /**
     * 获取所有的员工
     * @return
     */
    List<SysUser> selectAllEmp();

    /**
     * 根据员工获取员工的消费区域统计信息查看权限
     * @param param
     * @return
     */
    List<DgEmpAreaSta> selectAllArea(Map param);

    /**
     * 对员工消费区域统计信息查看权限进行修改
     * @param empCode
     * @param areaCode
     * @param isOpen
     */
    void editEmpAreaStaPermission(String empCode,String areaCode,Integer isOpen);

    /**
     * 修改启用员工查看消费区域统计信息权限
     * @param isOpen
     */
    void editMasterEmpAreaSta(Integer isOpen);

    /**
     * 页面点击全部启用或者全部不启用
     * @param empCode
     * @param isOpen 1全部启用，0全部不启用
     */
    void editEmpAreaWithAll(String empCode,Integer isOpen);

}
