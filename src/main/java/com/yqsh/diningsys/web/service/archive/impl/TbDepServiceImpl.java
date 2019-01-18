package com.yqsh.diningsys.web.service.archive.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.TbDepMapper;
import com.yqsh.diningsys.web.model.archive.TbDep;
import com.yqsh.diningsys.web.service.archive.TbDepService;

/**
 * 部门管理服务实现层
* @author xiewei
* @version 创建时间：2016年10月10日 下午3:17:20
 */
@Service
@SuppressWarnings("rawtypes")
public class TbDepServiceImpl implements TbDepService {

    @Resource
    private TbDepMapper tbDepMapper;

    @Override
    public TbDep selectByDepName(String depName) {
        return tbDepMapper.selectByDepName(depName);
    }

    @Override
    public TbDep authentication(TbDep tbDep) {
        return tbDepMapper.authentication(tbDep);
    }

    public List<TbDep> selectByConAndPage(Page<TbDep> page, Map params){
        return tbDepMapper.selectByConAndPage(page,params);
    }

    @Override
    public TbDep selectByPrimaryKey(Integer id) {
        return tbDepMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertDep(TbDep tbDep) {
        tbDepMapper.insert(tbDep);
        return tbDep.getId();
    }

    @Override
    public int modifyUserByPrimaryKey(TbDep tbDep) {
        return tbDepMapper.updateByPrimaryKeySelective(tbDep);
    }

    
	@Override
    public int delUserByPrimaryKey(List ids) {
        return tbDepMapper.deleteByPrimaryKeys(ids);
    }

	@Override
	public com.yqsh.diningsys.core.util.Page<TbDep> getPageList(TbDep tbDep) {
		Integer totle = tbDepMapper.countListByPage(tbDep);
		List<TbDep> list = tbDepMapper.getListByPage(tbDep);
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				TbDep get_dep = list.get(i);
				get_dep.setDepDepartment("顶级部门");
			}
		}
		return PageUtil.getPage(totle, tbDep.getPage(),list, tbDep.getRows());
	}

	@Override
	public int insertOrUpdDep(TbDep tbDep){
		int result = 0;
		if(tbDep.getId() != null && tbDep.getId() > 0){
			result = tbDepMapper.update(tbDep);
		}else{
			tbDep.setCreateTime(new Date());
			result = tbDepMapper.newInsert(tbDep);
		}
		return result;
	}

	@Override
	public TbDep getDepById(TbDep tbDep) {
		return tbDepMapper.getDepById(tbDep);
	}
	

	@Override
	public int deleteTrash(TbDep tbDep) {
		return tbDepMapper.deleteTrash(tbDep);
	}
	
	@Override
	public int replyDep(TbDep tbDep) {
		return tbDepMapper.replyDep(tbDep);
	}

	@Override
	public int deleteByIds(TbDep tbDep) {
		return tbDepMapper.delete(tbDep);
	}

	@Override
	public List<TbDep> getAllList(TbDep tbdep) {
		return tbDepMapper.getAllList(tbdep);
	}

	@Override
	public List<TbDep> selectAllDep(Map<String,Object> params) {
		return tbDepMapper.selectAllDep(params);
	}

	@Override
	public List<TbDep> selectSmallDep(Map<String,Object> params) {
		return tbDepMapper.selectSmallDep(params);
	}

	@Override
	public TbDep getLastDepCode(TbDep tbDep) {
		return tbDepMapper.getLastDepCode(tbDep);
	}

	@Override
	public TbDep getDepDepartmentNameById(TbDep dep) {
		return tbDepMapper.getDepDepartmentNameById(dep);
	}

	@Override
	@Transactional
	public void synDep(List<TbDep> listDep) {
		tbDepMapper.delPhy();
		if(null!=listDep&&listDep.size()>0){
			tbDepMapper.insertBatch(listDep);
		}
	}

}
