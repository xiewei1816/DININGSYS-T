package com.yqsh.diningsys.web.service.pay.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.pay.DgPayWaterMapper;
import com.yqsh.diningsys.web.model.pay.DgPayWater;
import com.yqsh.diningsys.web.service.pay.DgPayWaterService;
/**
 * 支付流水业务实现类
 * @author jianglei
 * 日期 2017年1月12日 上午10:06:20
 *
 */
@Service
public class DgPayWaterServiceImpl implements DgPayWaterService{

	@Autowired
	private DgPayWaterMapper dgPayWaterMapper;
	
	
	@Override
	public Page<DgPayWater> getPageList(DgPayWater payWater) {
		Integer totle = dgPayWaterMapper.countListByPage(payWater);
		List<DgPayWater> list = dgPayWaterMapper.getPageList(payWater);
		return PageUtil.getPage(totle, payWater.getPage(),list,payWater.getRows());
	}

	@Override
	public Integer countListByPage(DgPayWater payWater) {
		return dgPayWaterMapper.countListByPage(payWater);
	}

	@Override
	public int insert(DgPayWater payWater) {
		return dgPayWaterMapper.insert(payWater);
	}

	@Override
	public int update(String outTradeNo,String tradeTime,String threeTradeNo,String payPeopleInfo) {
		DgPayWater water=new DgPayWater();
		water.setPayState(DgPayWater.PAYSTATE_OK);
		water.setOutTradeNo(outTradeNo);
		water.setTradeDate(tradeTime);
		water.setThreeTradeNo(threeTradeNo);
		water.setPayPeopleInfo(payPeopleInfo);
		return dgPayWaterMapper.update(water);
	}

	@Override
	public DgPayWater get(String id) {
		return dgPayWaterMapper.get(id);
	}

	@Override
	public List<DgPayWater> findAllObj(DgPayWater water) {
		return dgPayWaterMapper.findAllObj(water);
	}

	@Override
	public void deleteByOpenWater(String orderNo,String payType) {
		dgPayWaterMapper.deleteByOpenWater(orderNo,payType);
	}
}
