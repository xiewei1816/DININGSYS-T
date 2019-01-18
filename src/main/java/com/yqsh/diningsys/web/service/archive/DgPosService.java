package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.archive.DgPos;

public interface DgPosService {
	
	com.yqsh.diningsys.core.util.Page<DgPos> getPageList(DgPos dgPos);
	
	int insertOrUpd(DgPos dgPos);
	
	DgPos getPosByID(DgPos dgPos);
	
	int deleteByIds(DgPos dgPos);
	
	List<DgPos> getAllList(DgPos dgPos);

	List<Map<String,Object>> getAllPosList();

	int deleteTrash(DgPos pos);

	int replyPos(DgPos pos);

	DgPos selectPosByPosCode(String posCode);

	DgPos selectPosByPosId(Integer posId);
	
    Integer checkHavaYxePos(DgPos dgPos);
}
