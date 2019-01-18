package com.yqsh.diningsys.web.service.base.impl;

import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.SysMenuMapper;
import com.yqsh.diningsys.web.dao.SysRoleMapper;
import com.yqsh.diningsys.web.dao.SysUserMapper;
import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysRoleMenu;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.permission.SysPerDiscount;
import com.yqsh.diningsys.web.model.permission.SysPerOverview;
import com.yqsh.diningsys.web.service.base.UserService;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.*;

/**
 * 用户Service实现类
 *
 * @author StarZou
 * @since 2014年7月5日 上午11:54:24
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public SysUser selectByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    @Override
    public SysUser authentication(SysUser sysUser) {
        return sysUserMapper.authentication(sysUser);
    }


    @Override
    public SysUser selectByPrimaryKey(Integer id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertUser(SysUser sysUser) {
//        sysUser.setUserRelational(0);
//        sysUserMapper.insert(sysUser);
//        String[] userRoleIDs = sysUser.getUserRoleIDs().split(",");
//        String roleId = "";
//        for(String roleID:userRoleIDs){
//            roleId += "("+sysUser.getId()+","+roleID+"),";
//        }
//        if(roleId.endsWith(","))
//            roleId = roleId.substring(0,roleId.lastIndexOf(","));
//        Map params = new HashMap();
//        params.put("params",roleId);
//        insertUserRole(params);
        return sysUser.getId();
    }

    @Override
    public int modifyUserByPrimaryKey(SysUser sysUser) {
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    public int delUserByPrimaryKey(List ids) {
        sysUserMapper.deleteUsderRoleByUserIds(ids);
        return sysUserMapper.deleteByPrimaryKeys(ids);
    }

    @Override
    public int modifyUserPassword(List ids) {
        return sysUserMapper.modifyUserPassword(ids);
    }

    @Override
    public int modifyUserState(Map params) {
        return sysUserMapper.modifyUserState(params);
    }

    @Override
    public int insertUserRole(Map params) {
        return sysUserMapper.insertUserRole(params);
    }

    @Override
    public int updateUserRole(Integer id,String roleIds) {
        sysUserMapper.deleteUsderRoleByUserId(id);
        String[] userRoleIDs = roleIds.split(",");
        String roleId = "";
        for(String roleID:userRoleIDs){
            roleId += "("+id+","+roleID+"),";
        }
        if(roleId.endsWith(","))
            roleId = roleId.substring(0,roleId.lastIndexOf(","));
        Map params = new HashMap();
        params.put("params",roleId);
        return insertUserRole(params);
    }

    @Override
    public SysUser selectByUsernameWithOutSelf(Map params){
        return sysUserMapper.selectByUsernameWithOutSelf(params);
    }

    @Override
    public void updateUserBaseInfo(SysUser sysUser) {
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    public void updatePwd(String pwd, Integer userId) {
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setPassword(pwd);
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

	@Override
	public com.yqsh.diningsys.core.util.Page<SysUser> getPageList(SysUser user) {
		Integer totle = sysUserMapper.countListByPage(user);
		List<SysUser> list = sysUserMapper.getListByPage(user);
		return PageUtil.getPage(totle, user.getPage(),list, user.getRows());
	}

	@Override
	public int insertOrUpdUser(SysUser user) {
		/*int result = 0;
		if(user.getId() != null && user.getId() > 0){
			result = sysUserMapper.update(user);
		}else{
			user.setPassword("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92");
			user.setCreateTime(new Date());
			result = sysUserMapper.newInsert(user);
		}
		return result;*/
		return 0;
	}

	@Override
	public SysUser getUserByID(SysUser user) {
		/*return sysUserMapper.getUserByID(user);*/
		return null;
	}

	@Override
	public int deleteByIds(SysUser user) {
		return sysUserMapper.delete(user);
	}

	@Override
	public List<SysUser> getAllList(SysUser user) {
		return sysUserMapper.getAllList(user);
	}

    @Override
    public int insertOrUpdEmp(SysUser sysUser) {
        int result = 0;
        if(sysUser.getId() != null && sysUser.getId() > 0){
            result = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        }else{
        	sysUser.setPassword("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92");
			sysUser.setState("normal");
            sysUser.setCreateTime(new Date());
            result = sysUserMapper.insertSelective(sysUser);
        }
        return result;
    }

    @Override
    public SysUser getEmpById(SysUser tbEmp) {
        return sysUserMapper.selectByPrimaryKey(tbEmp.getId());
    }

    @Override
    public int deleteTrash(SysUser tbEmp) {
        return sysUserMapper.deleteTrash(tbEmp);
    }

    @Override
    public int replyEmp(SysUser tbEmp) {
        return sysUserMapper.replyEmp(tbEmp);
    }

    @Override
    public int insertEmp(SysUser tbEmp) {
        return sysUserMapper.insertSelective(tbEmp);
    }

    @Override
    public List<SysUser> selectUsersByRoleId(Integer id) {
        return sysUserMapper.selectUsersByRoleId(id);
    }

    @Override
    public List<SysUser> selectAllUser() {
        return sysUserMapper.selectAllUser();
    }

    @Override
    public void deleteUser(Integer id) {
        sysUserMapper.deleteUsderRoleByUserId(id);
        sysUserMapper.deleteUser(id);
    }

    @Override
    public void editUser(SysUser sysUser, String roleIds) {
        sysRoleMapper.deleteUserRoleByUserId(sysUser.getId());
        List<Map> maps = new ArrayList<>();
        for(String rid:roleIds.split(",")){
            Map map = new HashMap();
            map.put("userId",sysUser.getId());
            map.put("roleId",Integer.parseInt(rid));
            maps.add(map);
        }
        sysRoleMapper.insertRoleUser(maps);
    }

    @Override
    public Map<String, Object> getPerBusinessByUserId(Integer userid) {
        Map map = new HashMap();
        map.put("userid",userid);
        return sysRoleMapper.getPerBusinessByUserId(map);
    }

    @Override
	public SysUser getLastEmpCode() {
		return sysUserMapper.getLastEmpCode();
	}
	
	/**
	 * 编号叠加算法
	 * 
	 * @param lastCode
	 * @return
	 */
	public String getCodeByLastCode(String lastCode) {
		int lastCodeNo = Integer.parseInt(lastCode);
		int codeNo = lastCodeNo + 1;
		String code = codeNo + ""; // 编号
		String no = ""; // 填充“0”
		for (int i = 0; i < 3 - code.length(); i++) {
			no += "0";
		}
		return no + code;
	}

    @Override
    public List<DgPublicCode0> getAllPostData() {
        return sysUserMapper.getAllPostData();
    }

	@Override
	public Integer checkHavaYxeEmp(SysUser user) {
		// TODO Auto-generated method stub
		return sysUserMapper.checkHavaYxeEmp(user);
	}

	@Override
	public List<SysUser> getAllCooker() {
		// TODO Auto-generated method stub
		return sysUserMapper.getAllCooker();
	}

	@Override
	public SysUser chekUserCode(String userCode) {
		// TODO Auto-generated method stub
		return sysUserMapper.chekUserCode(userCode);
	}

    @Override
    public SysUser selectUserByAuthCode(String authorizationCode) {
        return sysUserMapper.selectUserByAuthCode(authorizationCode);
    }

    @Override
    public SysRoleMenu selectMenuByUserZwAndMenuId(String empDuties, Integer id) {
	    Map<String,Object> map = new HashMap<>();
        map.put("zwCode",empDuties);
        map.put("menuId",id);
        return sysUserMapper.selectMenuByUserZwAndMenuId(map);
    }

    @Override
    public SysRoleMenu selectRoleMenuByAuthCodeAndMenuCode(SysUser user, String menuCode) {
        SysMenu sysMenu = sysMenuMapper.selectMenuByMenuCode(menuCode);
        Map param = new HashedMap();
        param.put("zwCode",user.getEmpDuties());
        param.put("menuId",sysMenu.getId());
        return sysUserMapper.selectMenuByUserZwAndMenuId(param);
    }

    @Override
    public SysUser selectUserByUserCode(String userCode) {
        return sysUserMapper.selectUserByUserCode(userCode);
    }

    @Override
    public SysPerDiscount selectSysUserDiscountByZwCode(String empDuties) {
        return sysUserMapper.selectSysUserDiscountByZwCode(empDuties);
    }
}
