package com.yqsh.diningsys.web.service.archive.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.TbZdbzMapper;
import com.yqsh.diningsys.web.model.archive.TbZdbz;
import com.yqsh.diningsys.web.service.archive.TbZdbzService;

/**
 * 整单备注服务实现层
* @author xiewei
* @version 创建时间：2016年10月10日 下午3:17:20
 */
@Service
public class TbZdbzServiceImpl implements TbZdbzService {

    @Resource
    private TbZdbzMapper tbZdbzMapper;

    @Override
    public TbZdbz selectByZdbzName(String ZdbzName) {
        return tbZdbzMapper.selectByZdbzName(ZdbzName);
    }

    @Override
    public TbZdbz authentication(TbZdbz tbZdbz) {
        return tbZdbzMapper.authentication(tbZdbz);
    }

    public List<TbZdbz> selectByConAndPage(Page<TbZdbz> page, Map params){
        return tbZdbzMapper.selectByConAndPage(page,params);
    }

    @Override
    public TbZdbz selectByPrimaryKey(Integer id) {
        return tbZdbzMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertZdbz(TbZdbz tbZdbz) {
        tbZdbzMapper.insert(tbZdbz);
        return tbZdbz.getId();
    }

    @Override
    public int modifyUserByPrimaryKey(TbZdbz tbZdbz) {
        return tbZdbzMapper.updateByPrimaryKeySelective(tbZdbz);
    }

    @Override
    public int delUserByPrimaryKey(List ids) {
        return tbZdbzMapper.deleteByPrimaryKeys(ids);
    }

	@Override
	public com.yqsh.diningsys.core.util.Page<TbZdbz> getPageList(TbZdbz tbZdbz) {
		Integer totle = tbZdbzMapper.countListByPage(tbZdbz);
		List<TbZdbz> list = tbZdbzMapper.getListByPage(tbZdbz);
		return PageUtil.getPage(totle, tbZdbz.getPage(),list, tbZdbz.getRows());
	}

	@Override
	public int insertOrUpdZdbz(TbZdbz tbZdbz){
		int result = 0;
		if(tbZdbz.getId() != null && tbZdbz.getId() > 0){
			result = tbZdbzMapper.update(tbZdbz);
		}else{
			tbZdbz.setCreateTime(new Date());
			result = tbZdbzMapper.newInsert(tbZdbz);
		}
		return result;
	}

	@Override
	public TbZdbz getZdbzById(TbZdbz tbZdbz) {
		return tbZdbzMapper.getZdbzById(tbZdbz);
	}
	

	@Override
	public int deleteTrash(TbZdbz tbZdbz) {
		return tbZdbzMapper.deleteTrash(tbZdbz);
	}
	
	@Override
	public int replyZdbz(TbZdbz tbZdbz) {
		return tbZdbzMapper.replyZdbz(tbZdbz);
	}

	@Override
	public int deleteByIds(TbZdbz tbZdbz) {
		return tbZdbzMapper.delete(tbZdbz);
	}

	@Override
	public List<TbZdbz> getAllList(TbZdbz tbZdbz) {
		return tbZdbzMapper.getAllList(tbZdbz);
	}

    @Override
    public TbZdbz getZdbzByNum(String numbser) {
        return tbZdbzMapper.getZdbzByNum(numbser); 
    }

}
