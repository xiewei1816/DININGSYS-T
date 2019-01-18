package com.yqsh.diningsys.web.service.archive.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.TbEmpMapper;
import com.yqsh.diningsys.web.model.archive.TbEmp;
import com.yqsh.diningsys.web.service.archive.TbEmpService;

/**
 * 员工管理服务实现层
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:51:09
 */
@Service
public class TbEmpServiceImpl implements TbEmpService {

    @Resource
    private TbEmpMapper tbEmpMapper;

    @Override
    public TbEmp selectByEmpName(String empName) {
        return tbEmpMapper.selectByEmpName(empName);
    }

    @Override
    public TbEmp authentication(TbEmp tbEmp) {
        return tbEmpMapper.authentication(tbEmp);
    }

    public List<TbEmp> selectByConAndPage(Page<TbEmp> page, Map params){
        return tbEmpMapper.selectByConAndPage(page,params);
    }

    @Override
    public TbEmp selectByPrimaryKey(Integer id) {
        return tbEmpMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertEmp(TbEmp tbEmp) {
        tbEmpMapper.insert(tbEmp);
        return tbEmp.getId();
    }

    @Override
    public int modifyUserByPrimaryKey(TbEmp TbEmp) {
        return tbEmpMapper.updateByPrimaryKeySelective(TbEmp);
    }

    @Override
    public int delUserByPrimaryKey(List ids) {
        return tbEmpMapper.deleteByPrimaryKeys(ids);
    }

	@Override
	public com.yqsh.diningsys.core.util.Page<TbEmp> getPageList(TbEmp tbEmp) {
		Integer totle = tbEmpMapper.countListByPage(tbEmp);
		List<TbEmp> list = tbEmpMapper.getListByPage(tbEmp);
		return PageUtil.getPage(totle, tbEmp.getPage(),list, tbEmp.getRows());
	}

	@Override
	public int insertOrUpdEmp(TbEmp tbEmp){
		int result = 0;
		if(tbEmp.getId() != null && tbEmp.getId() > 0){
			result = tbEmpMapper.update(tbEmp);
		}else{
			tbEmp.setCreateTime(new Date());
			result = tbEmpMapper.newInsert(tbEmp);
		}
		return result;
	}

	@Override
	public TbEmp getEmpById(TbEmp tbEmp) {
		return tbEmpMapper.getEmpById(tbEmp);
	}
	

	@Override
	public int deleteTrash(TbEmp tbEmp) {
		return tbEmpMapper.deleteTrash(tbEmp);
	}
	
	@Override
	public int replyEmp(TbEmp tbEmp) {
		return tbEmpMapper.replyEmp(tbEmp);
	}

	@Override
	public int deleteByIds(TbEmp tbEmp) {
		return tbEmpMapper.delete(tbEmp);
	}

	@Override
	public List<TbEmp> getAllList(TbEmp tbEmp) {
		return tbEmpMapper.getAllList(tbEmp);
	}

}
