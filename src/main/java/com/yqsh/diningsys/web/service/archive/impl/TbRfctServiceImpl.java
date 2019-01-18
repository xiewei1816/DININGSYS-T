package com.yqsh.diningsys.web.service.archive.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.TbRfctMapper;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.TbRfct;
import com.yqsh.diningsys.web.service.archive.TbRfctService;

/**
 * 退菜原因类型服务实现层
* @author xiewei
* @version 创建时间：2016年10月10日 下午3:17:20
 */
@Service
public class TbRfctServiceImpl implements TbRfctService {

    @Resource
    private TbRfctMapper tbRfctMapper;

    @Override
    public TbRfct selectByRfctName(String rfctName) {
        return tbRfctMapper.selectByRfctName(rfctName);
    }

    @Override
    public TbRfct authentication(TbRfct tbRfct) {
        return tbRfctMapper.authentication(tbRfct);
    }

    public List<TbRfct> selectByConAndPage(Page<TbRfct> page, Map params){
        return tbRfctMapper.selectByConAndPage(page,params);
    }

    @Override
    public TbRfct selectByPrimaryKey(Integer id) {
        return tbRfctMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertRfct(TbRfct tbRfct) {
        tbRfctMapper.insert(tbRfct);
        return tbRfct.getId();
    }

    @Override
    public int modifyUserByPrimaryKey(TbRfct tbRfct) {
        return tbRfctMapper.updateByPrimaryKeySelective(tbRfct);
    }

    @Override
    public int delUserByPrimaryKey(List ids) {
        return tbRfctMapper.deleteByPrimaryKeys(ids);
    }

	@Override
	public com.yqsh.diningsys.core.util.Page<TbRfct> getPageList(TbRfct tbRfct) {
		Integer totle = tbRfctMapper.countListByPage(tbRfct);
		List<TbRfct> list = tbRfctMapper.getListByPage(tbRfct);
		return PageUtil.getPage(totle, tbRfct.getPage(),list, tbRfct.getRows());
	}

	@Override
	public int insertOrUpdRfct(TbRfct tbRfct){
		int result = 0;
		String rfctCode = null;
		if(tbRfct.getId() != null && tbRfct.getId() > 0){
			tbRfct.setUpdateTime(new Date());
			result = tbRfctMapper.update(tbRfct);
		}else{
			//退菜原因类型编号自动添加
			TbRfct rfct = getLastRfctCode();
        	// 如果该节点下有子节点，则查询最大代码进行算法叠加
			if (rfct != null) {
				String lastCode = rfct.getRfctCode();
				rfctCode = getCodeByLastCode(lastCode);
			} else {
				rfctCode = "01"; // 初始化代码
			}
			tbRfct.setRfctCode(rfctCode);
			tbRfct.setCreateTime(new Date());
			result = tbRfctMapper.newInsert(tbRfct);
		}
		return result;
	}

	@Override
	public TbRfct getRfctById(TbRfct tbRfct) {
		return tbRfctMapper.getRfctById(tbRfct);
	}
	

	@Override
	public int deleteTrash(TbRfct tbRfct) {
		return tbRfctMapper.deleteTrash(tbRfct);
	}
	
	@Override
	public int replyRfct(TbRfct tbRfct) {
		return tbRfctMapper.replyRfct(tbRfct);
	}

	@Override
	public int deleteByIds(TbRfct tbRfct) {
		return tbRfctMapper.delete(tbRfct);
	}

	@Override
	public List<TbRfct> getAllList(TbRfct tbRfct) {
		return tbRfctMapper.getAllList(tbRfct);
	}

	@Override
	public TbRfct getLastRfctCode() {
		return tbRfctMapper.getLastRfctCode();
	}

	/**
	 * 编号叠加算法
	 * @param lastCode
	 * @return
	 */
	public String getCodeByLastCode(String lastCode) {
		int lastCodeNo = Integer.parseInt(lastCode);
		int codeNo = lastCodeNo + 1;
		String code = codeNo + ""; // 编号
		String no = ""; // 填充“0”
		for (int i = 0; i < 2 - code.length(); i++) {
			no += "0";
		}
		return no + code;
	}
}
