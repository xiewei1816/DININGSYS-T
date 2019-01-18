package com.yqsh.diningsys.web.dao.api;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgGoodsConsign;

@Repository
public interface DgGoodsConsignMapper {

	List<DgGoodsConsign> selectGoodsConsign(Map<String, Object> params);

	int insertGoodsConsign(DgGoodsConsign dgc);

	int updateGoodsConsign(DgGoodsConsign dgc);
	
	int addGoodsConsignByQz(DgGoodsConsign dgc);
	
	int delete(DgGoodsConsign dgc);

	int deleteTrash(DgGoodsConsign dgc);

	int replyDgc(DgGoodsConsign dgc);
	
	/** 物品寄存种类 增、删、改、查 **/
	int insertGoodsType(Map<String, Object> params);

	int deleteGoodsType(Map<String, Object> params);

	int updateGoodsType(Map<String, Object> params);

	List<Map<String,Object>> selectGoodsType();
	
}
