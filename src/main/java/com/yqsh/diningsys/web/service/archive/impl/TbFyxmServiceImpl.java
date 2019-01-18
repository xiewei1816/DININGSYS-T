package com.yqsh.diningsys.web.service.archive.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.TbFyxmMapper;
import com.yqsh.diningsys.web.model.archive.TbFyxm;
import com.yqsh.diningsys.web.service.archive.TbFyxmService;
import com.yqsh.diningsys.web.service.sysSettings.AutoSeqService;

/**
 * 费用项目服务实现层
* @author xiewei
* @version 创建时间：2016年10月10日 下午3:17:20
 */
@Service
public class TbFyxmServiceImpl implements TbFyxmService {

    @Resource
    private TbFyxmMapper tbFyxmMapper;
    @Autowired
    private AutoSeqService seqService;

    @Override
    public TbFyxm selectByFyxmName(String FyxmName) {
        return tbFyxmMapper.selectByFyxmName(FyxmName);
    }

    @Override
    public TbFyxm authentication(TbFyxm tbFyxm) {
        return tbFyxmMapper.authentication(tbFyxm);
    }

    public List<TbFyxm> selectByConAndPage(Page<TbFyxm> page, Map params){
        return tbFyxmMapper.selectByConAndPage(page,params);
    }

    @Override
    public TbFyxm selectByPrimaryKey(Integer id) {
        return tbFyxmMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertFyxm(TbFyxm tbFyxm) {
        tbFyxmMapper.insert(tbFyxm);
        return tbFyxm.getId();
    }

    @Override
    public int modifyUserByPrimaryKey(TbFyxm tbFyxm) {
        return tbFyxmMapper.updateByPrimaryKeySelective(tbFyxm);
    }

    @Override
    public int delUserByPrimaryKey(List ids) {
        return tbFyxmMapper.deleteByPrimaryKeys(ids);
    }

	@Override
	public com.yqsh.diningsys.core.util.Page<TbFyxm> getPageList(TbFyxm tbFyxm) {
		Integer totle = tbFyxmMapper.countListByPage(tbFyxm);
		List<TbFyxm> list = tbFyxmMapper.getListByPage(tbFyxm);
		return PageUtil.getPage(totle, tbFyxm.getPage(),list, tbFyxm.getRows());
	}

	@Override
	public int insertOrUpdFyxm(TbFyxm tbFyxm){
		int result = 0;
		if(tbFyxm.getId() != null && tbFyxm.getId() > 0){
			tbFyxm.setUpdateTime(new Date());
			result = tbFyxmMapper.update(tbFyxm);
		}else{
	        //获取自动编号
	        String currentNum = seqService.getSeq("fyxm", 3, 0, "", 0, "");
	        tbFyxm.setFyxmNum(currentNum);
			tbFyxm.setCreateTime(new Date());
			result = tbFyxmMapper.newInsert(tbFyxm);
		}
		return result;
	}

	@Override
	public TbFyxm getFyxmById(TbFyxm tbFyxm) {
		return tbFyxmMapper.getFyxmById(tbFyxm);
	}
	

	@Override
	public int deleteTrash(TbFyxm tbFyxm) {
		return tbFyxmMapper.deleteTrash(tbFyxm);
	}
	
	@Override
	public int replyFyxm(TbFyxm tbFyxm) {
		return tbFyxmMapper.replyFyxm(tbFyxm);
	}

	@Override
	public int deleteByIds(TbFyxm tbFyxm) {
		return tbFyxmMapper.delete(tbFyxm);
	}

	@Override
	public List<TbFyxm> getAllList(TbFyxm tbFyxm) {
		return tbFyxmMapper.getAllList(tbFyxm);
	}

    @Override
    public TbFyxm getFyxmByNum(String fyxmName) {
        return tbFyxmMapper.getFyxmByNum(fyxmName);
    }


}
