package com.yqsh.diningsys.web.service.inve.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.inve.DgItemTypeMapper;
import com.yqsh.diningsys.web.model.inve.DgItemType;
import com.yqsh.diningsys.web.service.inve.DgItemTypeService;

/**
 * 物品类型实现类
 * @author jianglei
 * 日期 2016年10月19日 上午9:02:51
 *
 */
@Service
public class DgItemTypeImpl implements DgItemTypeService{

	@Autowired
    private DgItemTypeMapper itemTypeMapper;
	@Override
	public Page<DgItemType> getPageList(DgItemType itemType) {
		Integer totle = itemTypeMapper.countListByPage(itemType);
		List<DgItemType> list = itemTypeMapper.getPageList(itemType);
		return PageUtil.getPage(totle, itemType.getPage(),list, itemType.getRows());
	}
	@Override
	public int insert(DgItemType itemType) {
		return itemTypeMapper.insert(itemType);
	}
	@Override
	public int update(DgItemType itemType) {
		return itemTypeMapper.update(itemType);
	}
	@Override
	public int delete(List<String> id, String state) {
		return itemTypeMapper.delete(id, state);
	}
	@Override
	public DgItemType get(String id) {
		return itemTypeMapper.get(id);
	}
	@Override
	public List<DgItemType> listItemType(DgItemType itemType) {
		return itemTypeMapper.listItemType(itemType);
	}
	@Override
	@Transactional
	public void synData(List<DgItemType> listItemType) {
		//先删除数据库所有数据
		itemTypeMapper.delPhy();
		//批量插入数据
		if(null!=listItemType&&listItemType.size()>0){
			itemTypeMapper.insertBatch(listItemType);
		}
	}
}
