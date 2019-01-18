package com.yqsh.diningsys.web.dao;

import com.yqsh.diningsys.web.model.SysRoleMenu;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;

import com.yqsh.diningsys.web.model.permission.SysPerDiscount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByUsername(String userName);

    SysUser authentication(SysUser sysUser);

    int deleteByPrimaryKeys(List ids);

    int modifyUserPassword(List ids);

    int modifyUserState(Map params);

    int insertUserRole(Map params);

    int insertOneUserRole(Map params);

    int deleteUsderRoleByUserId(Integer id);

    int deleteUsderRoleByUserIds(List ids);

    SysUser selectByUsernameWithOutSelf(Map params);
    
    SysUser getLastEmpCode();

    /*员工管理移植的方法*/
    List<SysUser> getListByPage(SysUser user);

    Integer countListByPage(SysUser user);

    int deleteTrash(SysUser tbEmp);
    
    Integer checkHavaYxeEmp(SysUser user);

    int replyEmp(SysUser tbEmp);

    int delete(SysUser tbEmp);

    List<SysUser> getAllList(SysUser tbEmp);

    List<DgPublicCode0> getAllPostData();
    /*END*/

    /*系统权限使用相关*/
    List<SysUser> selectUsersByRoleId(Integer id);

    List<SysUser> selectAllUser();
    
    
    List<SysUser> getAllCooker();

    void deleteUser(Integer id);

	SysUser chekUserCode(String userCode);

    SysUser selectUserByAuthCode(@Param("authCode") String authorizationCode);

    SysRoleMenu selectMenuByUserZwAndMenuId(Map<String, Object> map);

    SysUser selectUserByUserCode(@Param("userCode") String userCode);

    SysPerDiscount selectSysUserDiscountByZwCode(@Param("empDuties") String empDuties);

    /*END*/
}