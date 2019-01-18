package com.yqsh.diningsys.web.service.archive.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.TbFylxMapper;
import com.yqsh.diningsys.web.model.archive.TbFylx;
import com.yqsh.diningsys.web.model.archive.TbRfct;
import com.yqsh.diningsys.web.service.archive.TbFylxService;

/**
 * 费用类型服务实现层
* @author xiewei
* @version 创建时间：2016年10月10日 下午3:17:20
 */
@Service
public class TbFylxServiceImpl implements TbFylxService {

    @Resource
    private TbFylxMapper tbFylxMapper;

    @Override
    public TbFylx selectByFylxName(String fylxName) {
        return tbFylxMapper.selectByFylxName(fylxName);
    }

    @Override
    public TbFylx authentication(TbFylx tbFylx) {
        return tbFylxMapper.authentication(tbFylx);
    }

    public List<TbFylx> selectByConAndPage(Page<TbFylx> page, Map params){
        return tbFylxMapper.selectByConAndPage(page,params);
    }

    @Override
    public TbFylx selectByPrimaryKey(Integer id) {
        return tbFylxMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertFylx(TbFylx tbFylx) {
        tbFylxMapper.insert(tbFylx);
        return tbFylx.getId();
    }

    @Override
    public int modifyUserByPrimaryKey(TbFylx tbFylx) {
        return tbFylxMapper.updateByPrimaryKeySelective(tbFylx);
    }

    @Override
    public int delUserByPrimaryKey(List ids) {
        return tbFylxMapper.deleteByPrimaryKeys(ids);
    }

	@Override
	public com.yqsh.diningsys.core.util.Page<TbFylx> getPageList(TbFylx tbFylx) {
		Integer totle = tbFylxMapper.countListByPage(tbFylx);
		List<TbFylx> list = tbFylxMapper.getListByPage(tbFylx);
		return PageUtil.getPage(totle, tbFylx.getPage(),list, tbFylx.getRows());
	}

	@Override
	public int insertOrUpdFylx(TbFylx tbFylx){
		int result = 0;
		String fylxCode = null;
		if(tbFylx.getId() != null && tbFylx.getId() > 0){
			tbFylx.setUpdateTime(new Date());
			result = tbFylxMapper.update(tbFylx);
		}else{
			//费用类型编号自动添加
			TbFylx fylx = getLastFylxCode();
        	// 如果该节点下有子节点，则查询最大代码进行算法叠加
			if (fylx != null) {
				String lastCode = fylx.getfylxNum();
				fylxCode = getCodeByLastCode(lastCode);
			} else {
				fylxCode = "001"; // 初始化代码
			}
			tbFylx.setfylxNum(fylxCode);
			tbFylx.setCreateTime(new Date());
			result = tbFylxMapper.newInsert(tbFylx);
		}
		return result;
	}

	@Override
	public TbFylx getFylxById(TbFylx tbFylx) {
		return tbFylxMapper.getFylxById(tbFylx);
	}
	

	@Override
	public int deleteTrash(TbFylx tbFylx) {
		return tbFylxMapper.deleteTrash(tbFylx);
	}
	
	@Override
	public int replyFylx(TbFylx tbFylx) {
		return tbFylxMapper.replyFylx(tbFylx);
	}

	@Override
	public int deleteByIds(TbFylx tbFylx) {
		return tbFylxMapper.delete(tbFylx);
	}

	@Override
	public List<TbFylx> getAllList(TbFylx tbFylx) {
		return tbFylxMapper.getAllList(tbFylx);
	}

	@Override
	public TbFylx getLastFylxCode() {
		return tbFylxMapper.getLastFylxCode();
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
		for (int i = 0; i < 3 - code.length(); i++) {
			no += "0";
		}
		return no + code;
	}

}
