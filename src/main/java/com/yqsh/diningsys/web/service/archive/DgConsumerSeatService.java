package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.archive.DgCard;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;
import org.apache.ibatis.annotations.Param;

public interface DgConsumerSeatService {
	
	com.yqsh.diningsys.core.util.Page<DgConsumerSeat> getPageList(DgConsumerSeat dgConsumerSeat);
	
	int insertOrUpd(DgConsumerSeat dgConsumerSeat);
	
	DgConsumerSeat getDgConsumerSeatByID(DgConsumerSeat dgConsumerSeat);

	DgConsumerSeat selectByPrimaryKey(Integer id);

    DgConsumerSeat getConsumerSeatById(Integer id);
	
	int deleteByIds(DgConsumerSeat dgConsumerSeat);
	
	List<DgConsumerSeat> getAllList(DgConsumerSeat dgConsumerSeat);
        
	DgConsumerSeat getConsumerSeatByNumber(String number);
	
	
	int deleteByIds(String ids);
	
	int restore(String ids);
	

	/*前台接口使用*/
	/**
	 *
	 * @param areas
	 * @param areaId
	 * @param state
	 * @return
	 */
	List<Map<String,Object>> getConsumerSeatByArea(String areas,Integer areaId,Integer state);
	
	/**
	 * 更新客位状态
	 * @param seat
	 * @return
	 */
	int updateState(DgConsumerSeat seat);
	
	List<DgConsumerSeat> getAllFreeSeat(Integer pos);

	Page<DgCard> getCardsByConsumerSeatId(DgCard record);

	void deleteCardByid(DgCard record);

	void saveCard(DgCard record);

    List<DgConsumerSeat> getAllSeatWithServiceClass(Integer bisId);

    List<DgConsumerSeat> selectDataByAreaId(Integer id);

    List<DgConsumerSeat> selectAllClearingSeat();

	DgReceptionClearingWater selectEndTime(Integer id);

	void modifySeatSeat(Integer id);

    Integer updateSeatPDState(Map<String,Object> param);
    
    int updateSeatState(DgConsumerSeat seat);
    
    List<Map> selectAllSeat();
    
    Integer updateSeatByUuidKey(Map seatInfos);
    
    DgConsumerSeat selectSeatIdByUuidKey(String uuidKey);
    
	List<DgConsumerSeat> getAllSeat(Integer pos);

	List<DgConsumerSeat> selectByIds(List<DgOpenWater> dows);

	int selectAllUseSeat();
}
