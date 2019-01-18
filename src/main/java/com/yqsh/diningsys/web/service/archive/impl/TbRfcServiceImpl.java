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
import com.yqsh.diningsys.web.dao.SysAutoseqMapper;
import com.yqsh.diningsys.web.dao.archive.TbRfcMapper;
import com.yqsh.diningsys.web.model.archive.TbRfc;
import com.yqsh.diningsys.web.service.archive.TbRfcService;
import com.yqsh.diningsys.web.service.sysSettings.AutoSeqService;

/**
 * 退菜原因服务实现层
 *
 * @author xiewei
 * @version 创建时间：2016年10月10日 下午3:17:20
 */
@Service
public class TbRfcServiceImpl implements TbRfcService {

    @Resource
    private TbRfcMapper tbRfcMapper;
    @Autowired
    private AutoSeqService seqService;

    @Override
    public TbRfc selectByRfcName(String RfcName) {
        return tbRfcMapper.selectByRfcName(RfcName);
    }

    @Override
    public TbRfc authentication(TbRfc tbRfc) {
        return tbRfcMapper.authentication(tbRfc);
    }

    public List<TbRfc> selectByConAndPage(Page<TbRfc> page, Map params) {
        return tbRfcMapper.selectByConAndPage(page, params);
    }

    @Override
    public TbRfc selectByPrimaryKey(Integer id) {
        return tbRfcMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertRfc(TbRfc tbRfc) {
        tbRfcMapper.insert(tbRfc);
        return tbRfc.getId();
    }

    @Override
    public int modifyUserByPrimaryKey(TbRfc tbRfc) {
        return tbRfcMapper.updateByPrimaryKeySelective(tbRfc);
    }

    @Override
    public int delUserByPrimaryKey(List ids) {
        return tbRfcMapper.deleteByPrimaryKeys(ids);
    }

    @Override
    public com.yqsh.diningsys.core.util.Page<TbRfc> getPageList(TbRfc tbRfc) {
        Integer totle = tbRfcMapper.countListByPage(tbRfc);
        List<TbRfc> list = tbRfcMapper.getListByPage(tbRfc);
        return PageUtil.getPage(totle, tbRfc.getPage(), list, tbRfc.getRows());
    }

    @Override
    public int insertOrUpdRfc(TbRfc tbRfc) {
        int result = 0;
        if (tbRfc.getId() != null && tbRfc.getId() > 0) {
            result = tbRfcMapper.update(tbRfc);
        } else {
            //获取自动编号
            String currentNum = seqService.getSeq("rfc", 5, 0, "", 1, "T");
            tbRfc.setRfcCode(currentNum);
            tbRfc.setCreateTime(new Date());
            result = tbRfcMapper.newInsert(tbRfc);
        }
        return result;
    }

    @Override
    public TbRfc getRfcById(TbRfc tbRfc) {
        return tbRfcMapper.getRfcById(tbRfc);
    }

    @Override
    public int deleteTrash(TbRfc tbRfc) {
        return tbRfcMapper.deleteTrash(tbRfc);
    }

    @Override
    public int replyRfc(TbRfc tbRfc) {
        return tbRfcMapper.replyRfc(tbRfc);
    }

    @Override
    public int deleteByIds(TbRfc tbRfc) {
        return tbRfcMapper.delete(tbRfc);
    }

    @Override
    public List<TbRfc> getAllList(TbRfc tbRfc) {
        return tbRfcMapper.getAllList(tbRfc);
    }

    @Override
    public TbRfc getTbRfcByNumber(String number) {
        return tbRfcMapper.getTbRfcByNumber(number);
    }

}
