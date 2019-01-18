package com.yqsh.diningsys.web.service.archive.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.TbOrgMapper;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.service.archive.TbOrgService;

/**
 * 机构管理服务实现层
* @author xiewei
* @version 创建时间：2016年10月10日 下午3:17:20
 */
@Service
public class TbOrgServiceImpl implements TbOrgService {

    @Resource
    private TbOrgMapper tbOrgMapper;

    @Override
    public TbOrg selectByOrgName(String depName) {
        return tbOrgMapper.selectByOrgName(depName);
    }

    @Override
    public TbOrg authentication(TbOrg tbOrg) {
        return tbOrgMapper.authentication(tbOrg);
    }

    public List<TbOrg> selectByConAndPage(Page<TbOrg> page, Map params){
        return tbOrgMapper.selectByConAndPage(page,params);
    }

    @Override
    public TbOrg selectByPrimaryKey(Integer id) {
        return tbOrgMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertOrg(TbOrg tbOrg) {
        tbOrgMapper.insert(tbOrg);
        return tbOrg.getId();
    }

    @Override
    public int modifyUserByPrimaryKey(TbOrg tbOrg) {
        return tbOrgMapper.updateByPrimaryKeySelective(tbOrg);
    }

    @Override
    public int delUserByPrimaryKey(List ids) {
        return tbOrgMapper.deleteByPrimaryKeys(ids);
    }

	@Override
	public com.yqsh.diningsys.core.util.Page<TbOrg> getPageList(TbOrg tbOrg) {
		Integer totle = tbOrgMapper.countListByPage(tbOrg);
		List<TbOrg> list = tbOrgMapper.getListByPage(tbOrg);
		return PageUtil.getPage(totle, tbOrg.getPage(),list, tbOrg.getRows());
	}

	@Override
	public int insertOrUpdOrg(TbOrg tbOrg){
		int result = 0;
		if(tbOrg.getId() != null && tbOrg.getId() > 0){
			result = tbOrgMapper.update(tbOrg);
		}else{
			tbOrg.setCreateTime(new Date());
			result = tbOrgMapper.newInsert(tbOrg);
		}
		return result;
	}

	@Override
	public TbOrg getOrgByID(TbOrg tbOrg) {
		return tbOrgMapper.getOrgByID(tbOrg);
	}
	

	@Override
	public int deleteTrash(TbOrg tbOrg) {
		return tbOrgMapper.deleteTrash(tbOrg);
	}
	
	@Override
	public int replyOrg(TbOrg tbOrg) {
		return tbOrgMapper.replyDep(tbOrg);
	}

	@Override
	public int deleteByIds(TbOrg tbOrg) {
		return tbOrgMapper.delete(tbOrg);
	}

	@Override
	public List<TbOrg> getAllList(TbOrg tbOrg) {
		return tbOrgMapper.getAllList(tbOrg);
	}

	@Override
	public List<TbOrg> ajaxTreeOrg() {
		return tbOrgMapper.ajaxTreeOrg();
	}

	@Override
	public TbOrg getOwnOrg() {
		return tbOrgMapper.getOwnOrg();
	}

	@Override
	public List<TbOrg> selectAllTbOrg() {
		return tbOrgMapper.selectAllTbOrg();
	}

    @Override
    public TbOrg selectSelfShop() {
        return null;
    }
}
