package com.yqsh.diningsys.web.dao.permission;

import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.permission.DgEmpAreaSta;
import org.springframework.stereotype.Repository;

import java.net.Inet4Address;
import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-11-25 上午10:45
 */
@Repository
public interface DgEmpAreaStaMapper {

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
     * 获取所有未删除的区域
     * @return
     */
    List<DgEmpAreaSta> selectAllNoDelArea();

    /**
     * 删除原来的权限数据单条
     * @param param
     */
    void deleteEmpAreaStaData(Map param);

    /**
     * 删除原来的权限数据，根据员工编码删除
     * @param param
     */
    void deleteEmpAreaStaDataByEmpCode(Map param);

    /**
     * 批量插入数据
     * @param param
     */
    void insertEmpAreaStaData(Map param);

    DgEmpAreaSta selectDataByEmpCodeAndAreaCode(Map param);

    void editEmpAreaSta(Map param);

    List<String> selectAllAreaCode();

    void insertEmpAreaStaDataMulti(Map param);

}
