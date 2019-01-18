package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;

public interface DgConsumptionAreaService {
	
	com.yqsh.diningsys.core.util.Page<DgConsumptionArea> getPageList(DgConsumptionArea consumptionArea);
	
	int insertOrUpd(DgConsumptionArea consumptionArea);
	
	DgConsumptionArea getConsumptionAreaByID(DgConsumptionArea consumptionArea);
	
	int deleteByIds(DgConsumptionArea consumptionArea);

	void deleteById(Integer id);

	List<DgConsumptionArea> getAllList(DgConsumptionArea consumptionArea);

    List<DgConsumptionArea> selectAllArea();

	List<Map<String,Object>> getAreaByIds(String ids);
}
