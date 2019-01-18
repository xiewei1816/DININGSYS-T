package com.yqsh.diningsys.web.service.inve.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.inve.DgInventoryMapper;
import com.yqsh.diningsys.web.model.inve.DgInventory;
import com.yqsh.diningsys.web.service.inve.DgInventoryService;

/**
 * 库存管理业务实现类
 * @author jianglei
 * 日期 2016年10月19日 下午5:06:40
 *
 */
@Service
public class DgInventoryServiceImpl implements DgInventoryService{
	@Autowired
	private DgInventoryMapper inventoryMapper;
	@Override
	public Page<DgInventory> getPageList(DgInventory inventory) {
		Integer totle = inventoryMapper.countListByPage(inventory);
		List<DgInventory> list = inventoryMapper.getPageList(inventory);
		return PageUtil.getPage(totle, inventory.getPage(),list, inventory.getRows());
	}

	@Override
	public int insert(DgInventory inventory) {
		return inventoryMapper.insert(inventory);
	}

	@Override
	public int update(DgInventory inventory) {
		return inventoryMapper.update(inventory);
	}

	@Override
	public DgInventory get(String id) {
		return inventoryMapper.get(id);
	}

	@Override
	public List<DgInventory> findListData(DgInventory inve) {
		return inventoryMapper.findListData(inve);
	}

	@Override
	public List<DgInventory> exportXls(DgInventory inve) {
		return inventoryMapper.exportXls(inve);
	}

	@Override
	public List<Map<String,Object>> ajaxInveItems(DgInventory inve, String itemTypeId) {
		return inventoryMapper.ajaxInveItems(inve, itemTypeId);
	}
	
}
