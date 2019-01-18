package com.yqsh.diningsys.web.service.archive.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.TbBisMapper;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.service.archive.TbBisService;

/**
 * 营业市别管理服务实现层
* @author xiewei
* @version 创建时间：2016年10月10日 下午3:17:20
 */
@Service("tbBisServiceImpl")
public class TbBisServiceImpl implements TbBisService {

    @Resource
    private TbBisMapper tbBisMapper;

    @Override
    public TbBis selectByBisName(String BisName) {
        return tbBisMapper.selectByBisName(BisName);
    }

    @Override
    public TbBis authentication(TbBis tbBis) {
        return tbBisMapper.authentication(tbBis);
    }

    public List<TbBis> selectByConAndPage(Page<TbBis> page, Map params){
        return tbBisMapper.selectByConAndPage(page,params);
    }

    @Override
    public TbBis selectByPrimaryKey(Integer id) {
        return tbBisMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertBis(TbBis tbBis) {
        tbBisMapper.insert(tbBis);
        return tbBis.getId();
    }

    @Override
    public int modifyUserByPrimaryKey(TbBis tbBis) {
        return tbBisMapper.updateByPrimaryKeySelective(tbBis);
    }

    @Override
    public int delUserByPrimaryKey(List ids) {
        return tbBisMapper.deleteByPrimaryKeys(ids);
    }

	@Override
	public com.yqsh.diningsys.core.util.Page<TbBis> getPageList(TbBis tbBis) {
		Integer totle = tbBisMapper.countListByPage(tbBis);
		List<TbBis> list = tbBisMapper.getListByPage(tbBis);
		return PageUtil.getPage(totle, tbBis.getPage(),list, tbBis.getRows());
	}

	@Override
	public int insertOrUpdBis(TbBis tbBis){
		int result = 0;
		if(tbBis.getId() != null && tbBis.getId() > 0){
			result = tbBisMapper.update(tbBis);
		}else{
			tbBis.setCreateTime(new Date());
			result = tbBisMapper.newInsert(tbBis);
		}
		return result;
	}

	@Override
	public TbBis getBisByID(TbBis tbBis) {
		return tbBisMapper.getBisByID(tbBis);
	}
	

	@Override
	public int deleteTrash(TbBis tbBis) {
		return tbBisMapper.deleteTrash(tbBis);
	}
	
	@Override
	public int replyBis(TbBis tbBis) {
		return tbBisMapper.replyBis(tbBis);
	}

	@Override
	public int deleteByIds(TbBis tbBis) {
		return tbBisMapper.delete(tbBis);
	}

	@Override
	public List<TbBis> getAllList(TbBis tbBis) {
		return tbBisMapper.getAllList(tbBis);
	}

	@Override
	public List<TbBis> selectAllBis() {
		return tbBisMapper.selectAllBis();
	}

	@Override
	public int calculateSJD(Map src) {
		// TODO Auto-generated method stub
		return tbBisMapper.calculateSJD(src);
	}

    @Override
    public TbBis selectHasSameTimeAndOrg(TbBis tbBis) {
        return tbBisMapper.selectHasSameTimeAndOrg(tbBis);
    }

	@Override
	public TbBis selectHasSameNameAndOrg(TbBis tbBis) {
		return tbBisMapper.selectHasSameNameAndOrg(tbBis);
	}
}
