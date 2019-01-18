package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemPriceLadder;

public interface DgItemPriceLadderService extends GenericService<DgItemPriceLadder,Integer>{
    List<DgItemPriceLadder> getAllData(DgItemPriceLadder record);//获取所有数据
    List<Integer> getAllItemId();//获取所有itemID
    List<DgItemFile> selectItemByAdd(List<Integer> ids);
    int deleteAll();
    void deleteIds(String s);
    void deleteNotIn(List<Integer> ids);
    int deleteByItemId(int id);
    DgItemPriceLadder selectByItemId(Integer id);
    int insertChilds(List<DgItemPriceLadder> record);
    int saveItemPriceLadder(String data);
    void synItemPriceLadder(List<DgItemPriceLadder> listItemPriceLadder);
}
