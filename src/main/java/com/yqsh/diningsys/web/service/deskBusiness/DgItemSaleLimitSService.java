package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimitS;

public interface DgItemSaleLimitSService extends GenericService<DgItemSaleLimitS,Integer>{
    List<DgItemSaleLimitS> getAllData(Map<String,Object> record);//获取所有数据
    List<Integer> getAllItemId();//获取所有itemID
    List<DgItemSaleLimitS> selectItemByAdd(List<Integer> ids);
    int deleteAll(Integer id);
    void deleteIds(String s);
    void deleteNotIn(List<Integer> ids);
    int deleteByItemId(int id);
    int updateCount(DgItemSaleLimitS useCount);
    int insertChilds(List<DgItemSaleLimitS> record);
    /**
     * 根据相关条件删除数据
     * @author jianglei
     * 日期 2017年2月28日 上午10:53:14
     * @param itemId 品项id
     * @param limitId  主表id
     * @return
     */
    int deleteParams(Integer itemId,Integer limitId);
}