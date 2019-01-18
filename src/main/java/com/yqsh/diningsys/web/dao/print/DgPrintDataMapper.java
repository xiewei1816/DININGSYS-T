package com.yqsh.diningsys.web.dao.print;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.print.DgPrintData;
import org.springframework.stereotype.Repository;

@Repository
public interface DgPrintDataMapper extends GenericDao<DgPrintData,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgPrintData record);

    int insertSelective(DgPrintData record);

    DgPrintData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgPrintData record);

    int updateByPrimaryKeyWithBLOBs(DgPrintData record);

    int updateByPrimaryKey(DgPrintData record);
    
    int getCount();
    
    DgPrintData getFirstItem();
    
    /**
     * 根据服务流水id获取营业流水号
     * @param owId
     * @return
     */
    String selectOwNumByServiceId(Integer owId);
    
    /**
     * 根据营业流水id获取消费区域
     * @param owId
     * @return
     */
    Map getPrintOwServiceInfoByWaterId(Integer owId);
    /**
     * 根据服务流水id获取品项明细
     * @param owId
     * @return
     */
    List<Map> selePrintItem(Integer owId);
    /**
     * 根据服务流水id获取服务汇总明细
     * @param owId
     * @return
     */
    Map getPrintOwServiceInfo(Integer owId);
    
    
    List<Map> selePrintItemByReminder(List<Map> orgs);
    
    /**
     * 根据营业流水号获取打印数据
     */
    Map getPrintWaterInfo(Integer owId);
    
    /**
     * 根据团队号,获取团队下所有成员
     * @param member
     * @return
     */
    List<Map> selectAllTeamMember(Map orgs);
}