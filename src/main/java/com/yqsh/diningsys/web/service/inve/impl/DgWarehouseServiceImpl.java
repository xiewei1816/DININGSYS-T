package com.yqsh.diningsys.web.service.inve.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yqsh.diningsys.core.util.DateUtil;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.core.util.SerialNumberUtil;
import com.yqsh.diningsys.web.dao.inve.DgWarehouseMapper;
import com.yqsh.diningsys.web.enums.FaceImageType;
import com.yqsh.diningsys.web.model.inve.DgWarehouse;
import com.yqsh.diningsys.web.service.inve.DgWarehouseService;
/**
 * 仓库管理业务实现类
 * @author jianglei
 * 日期 2016年10月18日 下午2:38:03
 *
 */
@Service
public class DgWarehouseServiceImpl implements DgWarehouseService{

	@Autowired
	private DgWarehouseMapper warehouseMapper;
	@Override
	public Page<DgWarehouse> getPageList(DgWarehouse warehouse) {
		Integer totle = warehouseMapper.countListByPage(warehouse);
		List<DgWarehouse> list = warehouseMapper.getPageList(warehouse);
		return PageUtil.getPage(totle, warehouse.getPage(),list, warehouse.getRows());
	}

	@Override
	public synchronized int insert(DgWarehouse warehouse) {
		int relust=0;
		warehouse.setId(UUID.randomUUID().toString());
		warehouse.setState(DgWarehouse.STATE_NORMAL);
		warehouse.setCreateDate(DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
		String superNo="";
			//获取最新一条供应商信息
			DgWarehouse sup=warehouseMapper.findLastOne(null);
			if(null!=sup&&StringUtils.isNotBlank(sup.getWareNo())){
				superNo=SerialNumberUtil.createNo(FaceImageType.W.toString(), DgWarehouse.WARENO_DIGITS,sup.getWareNo());
			}else{
				superNo=SerialNumberUtil.createNo(FaceImageType.W.toString(), DgWarehouse.WARENO_DIGITS,null);
			}
			warehouse.setWareNo(superNo);
			relust=warehouseMapper.insert(warehouse);
		return relust;
	}
	@Override
	public int update(DgWarehouse warehouse) {
		return warehouseMapper.update(warehouse);
	}

	@Override
	public int delete(List<String> id, String state) {
		return warehouseMapper.delete(id, state);
	}

	@Override
	public DgWarehouse get(String id) {
		return warehouseMapper.get(id);
	}

	@Override
	public List<DgWarehouse> findListData(DgWarehouse ware) {
		return warehouseMapper.findListData(ware);
	}

	@Override
	@Transactional
	public void synWare(List<DgWarehouse> listWare) {
		//先删除数据库所有数据
		warehouseMapper.delPhy();
		//批量插入数据
		if(null!=listWare&&listWare.size()>0){
			warehouseMapper.insertBatch(listWare);
		}
	}
}
