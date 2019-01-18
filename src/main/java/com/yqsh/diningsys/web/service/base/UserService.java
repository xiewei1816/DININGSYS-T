package com.yqsh.diningsys.web.service.base;


import com.yqsh.diningsys.web.model.SysRoleMenu;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.permission.SysPerDiscount;

import java.util.List;
import java.util.Map;

/**
 * 用户 业务 接口
 * 
 * @author StarZou
 * @since 2014年7月5日 上午11:53:33
 **/
public interface UserService {

	com.yqsh.diningsys.core.util.Page<SysUser> getPageList(SysUser user);
	
	int insertOrUpdUser(SysUser user);
	
	SysUser getUserByID(SysUser user);
	int deleteByIds(SysUser user);
	
	List<SysUser> getAllList(SysUser user);
    /**
     * 用户验证
     * 
     * @param user
     * @return
     */
    SysUser authentication(SysUser user);

    /**
     * 根据用户名查询用户
     * 
     * @param username
     * @return
     */
    SysUser selectByUsername(String username);

    SysUser selectByPrimaryKey(Integer id);

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    int insertUser(SysUser sysUser);

    /**
     * 修改用户
     * @param sysUser
     * @return
     */
    int modifyUserByPrimaryKey(SysUser sysUser);

    /**
     * 删除用户
     * @param ids
     * @return
     */
    int delUserByPrimaryKey(List ids);

    /**
     * 用户密码初始化
     * @param ids
     * @return
     */
    int modifyUserPassword(List ids);

    /**
     * 修改用户状态
     * @param params
     * @return
     */
    int modifyUserState(Map params);

    /**
     * 用户角色插入
     * @param params
     * @return
     */
    int insertUserRole(Map params);

    int updateUserRole(Integer id,String roleIds);

    SysUser selectByUsernameWithOutSelf(Map params);

    /*个人中心*/
    void updateUserBaseInfo(SysUser sysUser);

    void updatePwd(String pwd,Integer userId);

    /**
     * 新增、修改员工信息
     * @param tbEmp
     * @return
     */
    int insertOrUpdEmp(SysUser tbEmp);

    /**
     * 通过员工ID查询员工信息
     * @param tbEmp
     * @return
     */
    SysUser getEmpById(SysUser tbEmp);

    /**
     * 员工信息回收站
     * @param tbEmp
     * @return
     */
    int deleteTrash(SysUser tbEmp);

    /**
     * 还原回收站员工信息
     * @param tbEmp
     * @return
     */
    int replyEmp(SysUser tbEmp);

    /**
     * 查询最后一条记录获取代码进行叠加算法
     * @return
     */
    SysUser getLastEmpCode();
    
    int insertEmp(SysUser tbEmp);

    List<SysUser> selectUsersByRoleId(Integer id);

    List<SysUser> selectAllUser();

    void deleteUser(Integer id);

    void editUser(SysUser sysUser,String roleIds);


    /**
     * 通过用户ID获取业务使用权限常规
     * @param userid
     * @return
     */
    Map<String,Object> getPerBusinessByUserId(Integer userid);

    /**2016年12月18日11:16:07 员工职务修改*/

    /**
     * 获取所有的职务信息
     * @return
     */
    List<DgPublicCode0> getAllPostData();
    
    
    Integer checkHavaYxeEmp(SysUser user);
    
    /**
     * 获取所有厨师
     * @return
     */
    List<SysUser> getAllCooker();

    /**
     * 根据useCode查询是否存在
     * @param userCode
     * @return
     */
	SysUser chekUserCode(String userCode);

    SysUser selectUserByAuthCode(String authorizationCode);

    SysRoleMenu selectMenuByUserZwAndMenuId(String empDuties, Integer id);

    /**
     * 根据授权码和菜单code查询该授权码是否有该权限
     * @param user 授权码
     * @param menuCode 菜单code
     * @return
     */
    SysRoleMenu selectRoleMenuByAuthCodeAndMenuCode(SysUser user, String menuCode);

    SysUser selectUserByUserCode(String userCode);

    SysPerDiscount selectSysUserDiscountByZwCode(String empDuties);
}
