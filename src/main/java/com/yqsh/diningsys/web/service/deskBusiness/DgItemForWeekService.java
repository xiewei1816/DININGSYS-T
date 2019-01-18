package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemForWeek;

public interface DgItemForWeekService extends GenericService<DgItemForWeek,Integer>{
    List<DgItemForWeek> getAllData(DgItemForWeek record);//获取所有数据
    List<Integer> getAllItemId();//获取所有itemID
    List<DgItemFile> selectItemByAdd(List<Integer> ids);
    int deleteAll();
    void deleteIds(String s);
    void deleteNotIn(List<Integer> ids);
    int deleteByItemId(int id);
    DgItemForWeek selectByItemId(Integer id);
    int insertChilds(List<DgItemForWeek> record);
    int saveItemForWeek(String data);
    void syngetItemForWeek(List<DgItemForWeek> listItemForWeek);
}
