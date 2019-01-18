package com.yqsh.diningsys.web.service.businessMan;

public interface DgConsumSeatManagerService {
	int saveConsumerSeatNext(String content,String seatId);
	int saveConsumerAreaManNext(String content,String areaId);
	int saveBffProNext(String childContent,String id,String type);
}
