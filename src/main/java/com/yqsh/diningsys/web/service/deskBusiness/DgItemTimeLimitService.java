package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTimeLimit;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTimeLimitP;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTimeLimitPic;

public interface DgItemTimeLimitService extends GenericService<DgItemTimeLimit,Integer>{
    List<DgItemTimeLimit> getAllData(DgItemTimeLimit record);//获取所有数据
    List<Integer> getAllItemId();//获取所有itemID
    List<DgItemFile> selectItemByAdd(List<Integer> ids);
    int deleteAll();
    void deleteIds(String s);
    void deleteNotIn(List<Integer> ids);
    int deleteByItemId(int id);
    
    DgItemTimeLimit selectByItemId(Integer id);
    int insertAll(DgItemTimeLimitP p,List<DgItemTimeLimit> record,String zPics,String hPics);
    int insertAllPic(String zPics,String hPics);
    /**
     * 获取一个
     * @return
     */
    DgItemTimeLimit getOne();
    
    int picHCount();
    
    int picZCount();
    
    /**
     * 所有横线图
     * @return
     */
    List<DgItemTimeLimitPic> selectAllHPic();
    
    /**
     * 所有纵向图
     * @return
     */
    List<DgItemTimeLimitPic> selectAllZPic();
    Map<String,Object> saveItemTimeLimit(String data,String zPics,String hPics);
}
