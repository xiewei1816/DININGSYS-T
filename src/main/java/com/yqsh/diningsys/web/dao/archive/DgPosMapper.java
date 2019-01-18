package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.archive.DgPos;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DgPosMapper {

	List<DgPos> getListByPage(DgPos dgPos);
	
    Integer countListByPage(DgPos dgPos);
    
    DgPos getPosByID(DgPos dgPos);
    
	int newInsert(DgPos dgPos);
	
	int update(DgPos dgPos);
	
	int delete(DgPos dgPos);
	
	List<DgPos> getAllList(DgPos dgPos);

	List<Map<String, Object>> getAllPosList();

	int deleteTrash(DgPos pos);

	int replyPos(DgPos pos);

    DgPos getposByCode(@Param("posCode") String posid);

    DgPos selectPosByPosId(@Param("posId") Integer posId);

    DgPos selectPosByPosNumber(@Param("posNum") String posNum);

    Integer checkHavaYxePos(DgPos dgPos);
}
