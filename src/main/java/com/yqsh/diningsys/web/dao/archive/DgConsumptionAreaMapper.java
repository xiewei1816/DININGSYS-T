package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;

public interface DgConsumptionAreaMapper {
	
	List<DgConsumptionArea> getListByPage(DgConsumptionArea consumptionArea);
	
    Integer countListByPage(DgConsumptionArea dgConsumptionArea);
    
    DgConsumptionArea getConsumptionAreaByID(DgConsumptionArea dgConsumptionArea);
    
	int newInsert(DgConsumptionArea dgConsumptionArea);
	
	int update(DgConsumptionArea dgConsumptionArea);
	
	int delete(DgConsumptionArea dgConsumptionArea);
	
	List<DgConsumptionArea> getAllList(DgConsumptionArea dgConsumptionArea);

	List<DgConsumptionArea> selectAllArea();

	List<Map<String, Object>> getAreaByIds(Map ids);

    void deleteById(Integer id);
}
