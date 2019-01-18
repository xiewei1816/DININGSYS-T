package com.yqsh.diningsys.web.service.inve.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.util.DateUtil;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.core.util.SerialNumberUtil;
import com.yqsh.diningsys.web.dao.inve.DgSupplierMapper;
import com.yqsh.diningsys.web.enums.FaceImageType;
import com.yqsh.diningsys.web.model.inve.DgSupplier;
import com.yqsh.diningsys.web.service.inve.DgSupplierService;

/**
 * 供应商业务实现类
 * @author jianglei
 * 日期 2016年10月17日 上午10:03:12
 *
 */
@Service
public class DgSupplierServiceImpl implements DgSupplierService{
	
	@Autowired
	private DgSupplierMapper supplierMapper;

	@Override
	public Page<DgSupplier> getPageList(DgSupplier supplier) {
		Integer totle = supplierMapper.countListByPage(supplier);
		List<DgSupplier> list = supplierMapper.getPageList(supplier);
		return PageUtil.getPage(totle, supplier.getPage(),list, supplier.getRows());
	}

	@Override
	public synchronized int insert(DgSupplier supplier) {
		int relust=0;
		supplier.setId(UUID.randomUUID().toString());
		supplier.setState(DgSupplier.STATE_NORMAL);
		supplier.setCreateDate(DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
		String superNo="";
			//获取最新一条供应商信息
			DgSupplier sup=supplierMapper.findLastOne(null);
			if(null!=sup&&StringUtils.isNotBlank(sup.getSuperNo())){
				superNo=SerialNumberUtil.createNo(FaceImageType.S.toString(), DgSupplier.SUPERNO_DIGITS, sup.getSuperNo());
			}else{
				superNo=SerialNumberUtil.createNo(FaceImageType.S.toString(), DgSupplier.SUPERNO_DIGITS,null);
			}
			supplier.setSuperNo(superNo);
			relust=supplierMapper.insert(supplier);
		return relust;
	}

	@Override
	public int update(DgSupplier supplier) {
		return supplierMapper.update(supplier);
	}
	@Override
	public int delete(List<String> id,String state) {
		return supplierMapper.delete(id,state);
	}
	@Override
	public DgSupplier get(String id) {
		return supplierMapper.get(id);
	}

	@Override
	public List<DgSupplier> findListData(DgSupplier supplier) {
		return supplierMapper.findListData(supplier);
	}

	@Override
	public DgSupplier findLastOne(DgSupplier supplier) {
		return supplierMapper.findLastOne(supplier);
	}
}
