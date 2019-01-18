package com.yqsh.diningsys.web.service.api;

		import java.util.List;
		import java.util.Map;

		import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingScheme;

public interface APIBffService {
	Map getBffInfo(String openNum);
	Map updateBff(String openNum,Integer id);
	List<DgSeatChargingScheme> getAllBff();
}
