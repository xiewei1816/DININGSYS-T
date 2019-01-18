package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DgConsumerSeatMapper {
	
	List<DgConsumerSeat> getListByPage(DgConsumerSeat dgConsumerSeat);
	
    Integer countListByPage(DgConsumerSeat dgConsumerSeat);
    
    DgConsumerSeat getDgConsumerSeatByID(DgConsumerSeat dgConsumerSeat);

    DgConsumerSeat selectByPrimaryKey(Integer id);

	int newInsert(DgConsumerSeat dgConsumerSeat);
	
	int update(DgConsumerSeat dgConsumerSeat);
	
	int delete(DgConsumerSeat dgConsumerSeat);
	
	List<DgConsumerSeat> getAllList(DgConsumerSeat dgConsumerSeat);
        
    DgConsumerSeat getConsumerSeatByNumber(String number);

    DgConsumerSeat getConsumerSeatById(Integer id);

	List<Map<String, Object>> getConsumerSeatByArea(Map areas);
	
	int deleteByIds(DgConsumerSeat dgConsumerSeat);
	
	int restore(DgConsumerSeat dgConsumerSeat);
	
	/**
	 * 更新客位状态
	 * @param seat
	 * @return
	 */
	int updateState(DgConsumerSeat seat);
	
	List<DgConsumerSeat> getAllFreeSeat(Map<String,Object> param);
	
	List<DgConsumerSeat> getAllSeatByPos(Map<String,Object> param);

    List<DgConsumerSeat> getAllSeatWithServiceClass(@Param("bisId") Integer bisId);

	List<DgConsumerSeat> selectDataByAreaId(Integer id);

    List<DgConsumerSeat> selectAllClearingSeat();

	DgReceptionClearingWater selectEndTime(Integer id);

	void modifySeatSeat(Integer id);

    Integer updateSeatPDState(Map<String, Object> param);
    
    
    int updateSeatState(DgConsumerSeat seat);
    
    /**
     * 获取所有客位信息(上传店外)
     */
    List<Map> selectAllSeat();
    
    Integer updateSeatByUuidKey(Map seatInfos);
    
    DgConsumerSeat selectSeatIdByUuidKey(String uuidKey);

    List<DgConsumerSeat> selectAllUsingSeat(Map<String, Object> map);

	List<DgOpenWater> selectCurrentTransferOpenWater(Map<String, Object> map);

	List<DgConsumerSeat> selectByIds(@Param("ids")List<DgOpenWater> dows);

	int selectAllUseSeat();
}
