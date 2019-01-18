package com.yqsh.diningsys.web.service.businessMan.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.businessMan.TbFydjMapper;
import com.yqsh.diningsys.web.model.businessMan.TbFydj;
import com.yqsh.diningsys.web.service.businessMan.TbFydjService;

/**
 * 费用登记服务实现层
* @author xiewei
* @version 创建时间：2016年10月10日 下午3:17:20
 */
@Service
public class TbFydjServiceImpl implements TbFydjService {

    @Resource
    private TbFydjMapper tbFydjMapper;

    @Override
    public TbFydj selectByFydjName(String FydjName) {
        return tbFydjMapper.selectByFydjName(FydjName);
    }

    @Override
    public TbFydj authentication(TbFydj tbFydj) {
        return tbFydjMapper.authentication(tbFydj);
    }

    public List<TbFydj> selectByConAndPage(Page<TbFydj> page, Map params){
        return tbFydjMapper.selectByConAndPage(page,params);
    }

    @Override
    public TbFydj selectByPrimaryKey(Integer id) {
        return tbFydjMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertFydj(TbFydj tbFydj) {
        tbFydjMapper.insert(tbFydj);
        return tbFydj.getId();
    }

    @Override
    public int modifyUserByPrimaryKey(TbFydj tbFydj) {
        return tbFydjMapper.updateByPrimaryKeySelective(tbFydj);
    }

    @Override
    public int delUserByPrimaryKey(List ids) {
        return tbFydjMapper.deleteByPrimaryKeys(ids);
    }

	@Override
	public com.yqsh.diningsys.core.util.Page<TbFydj> getPageList(TbFydj tbFydj) {
		Integer totle = tbFydjMapper.countListByPage(tbFydj);
		List<TbFydj> list = tbFydjMapper.getListByPage(tbFydj);
		return PageUtil.getPage(totle, tbFydj.getPage(),list, tbFydj.getRows());
	}

	@Override
	public int insertOrUpdFydj(TbFydj tbFydj){
		int result = 0;
		if(tbFydj.getId() != null && tbFydj.getId() > 0){
			result = tbFydjMapper.update(tbFydj);
		}else{
			tbFydj.setCreateTime(new Date());
			result = tbFydjMapper.newInsert(tbFydj);
		}
		return result;
	}

	@Override
	public TbFydj getFydjById(TbFydj tbFydj) {
		return tbFydjMapper.getFydjById(tbFydj);
	}
	

	@Override
	public int deleteTrash(TbFydj tbFydj) {
		return tbFydjMapper.deleteTrash(tbFydj);
	}
	
	@Override
	public int replyFydj(TbFydj tbFydj) {
		return tbFydjMapper.replyFydj(tbFydj);
	}

	@Override
	public int deleteByIds(TbFydj tbFydj) {
		return tbFydjMapper.delete(tbFydj);
	}

	@Override
	public List<TbFydj> getAllList(TbFydj tbFydj) {
		return tbFydjMapper.getAllList(tbFydj);
	}


}
