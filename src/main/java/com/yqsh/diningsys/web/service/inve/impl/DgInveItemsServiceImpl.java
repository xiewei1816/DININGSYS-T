package com.yqsh.diningsys.web.service.inve.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.inve.DgInveItemsMapper;
import com.yqsh.diningsys.web.model.inve.DgInveItems;
import com.yqsh.diningsys.web.service.inve.DgInveItemsService;

/**
 * 物品管理接口实现
 * @author jianglei
 * 日期 2016年10月19日 上午10:31:41
 *
 */
@Service
public class DgInveItemsServiceImpl implements DgInveItemsService{
	@Autowired
	private DgInveItemsMapper inveItemsMapper;
	@Override
	public Page<DgInveItems> getPageList(DgInveItems items) {
		Integer totle = inveItemsMapper.countListByPage(items);
		List<DgInveItems> list = inveItemsMapper.getPageList(items);
		return PageUtil.getPage(totle, items.getPage(),list, items.getRows());
	}

	@Override
	public int insert(DgInveItems items) {
		return inveItemsMapper.insert(items);
	}

	@Override
	public int update(DgInveItems items) {
		return inveItemsMapper.update(items);
	}

	@Override
	public int delete(List<Map<String, String>> listMap) {
		return inveItemsMapper.delete(listMap);
	}

	@Override
	public DgInveItems get(String id) {
		return inveItemsMapper.get(id);
	}

	@Override
	public List<DgInveItems> findListData(DgInveItems items) {
		return inveItemsMapper.findListData(items);
	}

	@Override
	public int existsName(DgInveItems items) {
		return inveItemsMapper.existsName(items);
	}

	@Override
	public List<Map<String, Object>> getByTreeId(Map<String, Object> map) {
		return inveItemsMapper.getByTreeId(map);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<String, Object>> selectHaveItem(String list) {
	    List ids = new ArrayList();
        Collections.addAll(ids,list.split(","));
		return inveItemsMapper.selectHaveItem(ids);
	}

	@Override
	@Transactional
	public void synData(List<DgInveItems> listInveItems) {
		inveItemsMapper.delPhy();
		if(null!=listInveItems&&listInveItems.size()>0){
			inveItemsMapper.insertBatch(listInveItems);
		}
	}

    @Override
    public DgInveItems selectInveItemByCode(String code) {
        return inveItemsMapper.selectInveItemByCode(code);
    }

}
