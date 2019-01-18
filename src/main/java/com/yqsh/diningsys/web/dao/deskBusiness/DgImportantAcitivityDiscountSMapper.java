package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantAcitivityDiscountS;
@Repository
public interface DgImportantAcitivityDiscountSMapper extends GenericDao<DgImportantAcitivityDiscountS,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgImportantAcitivityDiscountS record);

    int insertSelective(DgImportantAcitivityDiscountS record);

    DgImportantAcitivityDiscountS selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgImportantAcitivityDiscountS record);

    int updateByPrimaryKey(DgImportantAcitivityDiscountS record);
    

    List<DgImportantAcitivityDiscountS> seleByPid(Integer id);
    void deleteIds(List<Integer> ids);
    int deleteByGateId(int id);
    Map<String, Object> selectByItemId(Integer id);
    /**
	 * 物理删除所有数据，此方法慎用
	 * @author jianglei
	 * 日期 2017年1月18日 上午9:14:16
	 */
    void delPhy();
	/**
	 * 批量插入
	 * @author jianglei
	 * 日期 2017年1月18日 上午11:07:10
	 * @param listObj
	 */
    void insertBatch(@Param("listObj") List<DgImportantAcitivityDiscountS> listObj);
}