package com.yqsh.diningsys.web.service.archive.impl;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.DgPublicCodeMapper;
import com.yqsh.diningsys.web.model.archive.DgPublicCode;
import com.yqsh.diningsys.web.service.archive.PublicCodeService;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-09-28 11:30
 */
@Service
public class PublicCodeServiceImpl extends GenericServiceImpl<DgPublicCode,Integer> implements PublicCodeService {

    @Resource
    private DgPublicCodeMapper dgPublicCodeMapper;

    @Override
    public GenericDao<DgPublicCode, Integer> getDao() {
        return dgPublicCodeMapper;
    }

    @Override
    public List<DgPublicCode> selectAllData() {
        return dgPublicCodeMapper.selectAllData(null);
    }

    @Override
    public List<DgPublicCode> selectDataById(String cgpcid) {
        return dgPublicCodeMapper.selectAllData(cgpcid);
    }

    @Override
    public void addData(DgPublicCode dgPublicCode) {
        dgPublicCodeMapper.insertSelective(dgPublicCode);
    }

    @Override
    public void deleteDataWithLogic(String cgpcid) {
        dgPublicCodeMapper.deleteDataWithLogic(cgpcid);
    }

    @Override
    public void deleteData(String cgpcid) {
        dgPublicCodeMapper.deleteByPrimaryKey(cgpcid);
    }

    @Override
    public Page<DgPublicCode> getPageList(DgPublicCode dgPublicCode) {
        Integer totle = dgPublicCodeMapper.countListByPage(dgPublicCode);
        List<DgPublicCode> list = dgPublicCodeMapper.getListByPage(dgPublicCode);
        return PageUtil.getPage(totle, dgPublicCode.getPage(),list, dgPublicCode.getRows());
    }

	@Override
	public List<DgPublicCode> findListData(DgPublicCode publicCode) {
		return dgPublicCodeMapper.findListData(publicCode);
	}
}
