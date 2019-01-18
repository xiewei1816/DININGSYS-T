package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscountS;
@Repository
public interface DgItemMemberDiscountSMapper extends GenericDao<DgItemMemberDiscountS,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemMemberDiscountS record);

    int insertSelective(DgItemMemberDiscountS record);

    DgItemMemberDiscountS selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemMemberDiscountS record);

    int updateByPrimaryKey(DgItemMemberDiscountS record);
    
    List<DgItemMemberDiscountS> seleByPid(Integer id);
    void deleteIds(List<Integer> ids);
    
    int deleteByItemId(int id);
    DgItemMemberDiscountS selctByPIdAndItemID(Map src);
    
    void insertChilds(List<DgItemMemberDiscountS> s);
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
    void insertBatch(@Param("listObj") List<DgItemMemberDiscountS> listObj);
}