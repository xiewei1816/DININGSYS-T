package com.yqsh.diningsys.web.service.api;

import java.math.BigDecimal;
import java.util.List;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgCashPledge;

public interface DgCashPledgeService {
	
	/**
	 * 通过开单编号获取所有押金记录
	 * @param owNum 开单编号
	 * @param cpType 押金类型
	 * @return
	 */
	List<DgCashPledge> selectCashPledge(String owNum,String cpType);
	
	/**
	 * 登记押金
	 * @param owNum 开单编号
	 * @param cpType 押金类型
	 * @param cpCurrency 币种
	 * @param cpMoney 金额
	 * @param regTime 登记时间
	 * @param refInfo 参考信息
	 * @param remark 备注
	 * @return
	 */
	int regMoney(String owNum,String cpType,String cpCurrency,Double cpMoney,String refInfo,String remark);
	
	/**
	 * 根据开单编号修改打印次数
	 * @param owNum 开单编号
	 * @return
	 */
	int updetePrintNumber(String owNum);

	/**
	 * 修改押金
	 * @param owNum 开单编号
	 * @param cpType 押金类型
	 * @param cpCurrency 币种
	 * @param cpMoney 金额
	 * @param regTime 登记时间
	 * @param refInfo 参考信息
	 * @param remark 备注
	 * @return
	 */
	int updateCashPledge(String owNum,String cpType,String cpCurrency,Double cpMoney,String refInfo,String remark);
}
