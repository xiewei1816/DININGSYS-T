package com.yqsh.diningsys.web.service.archive.impl;

import com.alibaba.fastjson.JSON;
import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.archive.DgItemFileTypeMapper;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.archive.DgYxeConsItemShow;
import com.yqsh.diningsys.web.service.archive.DgItemFileTypeService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-10-13 16:56
 */
@Service
public class DgItemFileTypeServiceImpl extends GenericServiceImpl<DgItemFileType,Integer> implements DgItemFileTypeService{

    @Resource
    private DgItemFileTypeMapper dgItemFileTypeMapper;

    @Override
    public GenericDao<DgItemFileType, Integer> getDao() {
        return dgItemFileTypeMapper;
    }

    @Override
    public List<DgItemFileType> selectAllItemFileType() {
        return dgItemFileTypeMapper.selectAllItemFileType();
    }

    @Override
    public List<DgItemFileType> getFirstLeveType() {
        return dgItemFileTypeMapper.getFirstLeveType();
    }

    @Override
    public List<DgItemFileType> getSecondLeveType(Integer id) {
        return dgItemFileTypeMapper.getSecondLeveType(id);
    }

    @Override
    public List<DgItemFileType> selectAllItemFileBigType(Integer id) {
        Map param = new HashMap();
        param.put("id",id);
        return dgItemFileTypeMapper.selectAllItemFileBigType(param);
    }

    @Override
    public List<DgItemFileType> selectAllItemFileSmallType() {
        return dgItemFileTypeMapper.selectAllItemFileSmallType();
    }

    @Override
    public List<DgItemFileType> getItemFileTypeByParent(Integer parentId) {
        Map param = new HashMap();
        param.put("parentId",parentId);
        return dgItemFileTypeMapper.getItemFileTypeByParent(param);
    }

    @Override
    public List<DgItemFileType> selectSecondItemFileTypeInIds(String inIds) {
        Map<String,Object> map = new HashMap<>();
        map.put("list",arraysTOList(inIds));
        List<DgItemFileType> dgItemFileTypes = dgItemFileTypeMapper.selectSecondItemFileTypeInIds(map);
        return dgItemFileTypes;
    }

    @Override
    public List<DgItemFileType> selectItemFileTypeByTypeIdAndNotInIds(Integer id, String notInIds) {
        Map param = new HashMap();
        param.put("parentId",id);
        param.put("list",arraysTOList(notInIds));
        return dgItemFileTypeMapper.selectItemFileTypeByTypeIdAndNotInIds(param);
    }

    @Override
    public DgItemFileType getItemFileById(Integer id) {
        return dgItemFileTypeMapper.getItemFileById(id);
    }

    @Override
    public DgItemFileType getDgItemFileTypeByName(Integer pId,String name) {
    	 Map param = new HashMap();
         param.put("pId",pId);
         param.put("name",name);
        return dgItemFileTypeMapper.getDgItemFileTypeByName(param);
    }

	@Override
	public List<DgItemFileType> selectAllBigType() {
		return dgItemFileTypeMapper.selectAllBigType();
	}

	@Override
	@Transactional
	public void synData(List<DgItemFileType> listObj) {
		dgItemFileTypeMapper.delPhy();
		if(null!=listObj&&listObj.size()>0){
			dgItemFileTypeMapper.insertBatch(listObj);
		}
	}

    @Override
    public List<DgItemFileType> selectAllItemFileBigType() {
        return dgItemFileTypeMapper.selectAllItemFileBigTypeNoParam();
    }

    @Override
    public List<DgItemFileType> selectWayItemYxBigType(String ids) {
        List<Integer> integers = JSON.parseArray(ids, Integer.class);
        if(integers != null && integers.size() > 0){
            integers.removeAll(Collections.singleton(null));
            return dgItemFileTypeMapper.selectWayItemYxBigType(integers);
        }
        return null;
    }

    @Override
    public List<DgItemFileType> selectWayItemWxBigType(String ids) {
        List<Integer> integers = JSON.parseArray(ids, Integer.class);
        if(integers != null && integers.size() > 0){
            integers.removeAll(Collections.singleton(null));
        }
        return dgItemFileTypeMapper.selectWayItemWxBigType(integers);
    }

    @Override
    public List<DgItemFileType> selectWayItemYxSmallType(String ids) {
        List<Integer> integers = JSON.parseArray(ids, Integer.class);
        if(integers != null && integers.size() > 0){
            integers.removeAll(Collections.singleton(null));

            return dgItemFileTypeMapper.selectWayItemYxSmallType(integers);
        }
        return null;
    }

    @Override
    public List<DgItemFileType> selectWayItemWxSmallType(Integer parentId, String ids) {
        List<Integer> integers = JSON.parseArray(ids, Integer.class);
        if(integers != null && integers.size() > 0){
            integers.removeAll(Collections.singleton(null));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("parentId",parentId==0?null:parentId);
        map.put("list",integers);
        return dgItemFileTypeMapper.selectWayItemWxSmallType(map);
    }

	@Override
	public List<DgItemFileType> getItemFileSmallTypeRankList() {
		return dgItemFileTypeMapper.getItemFileSmallTypeRankList();
	}

	@Override
	public Map<String, Object> selItemFileSmallTypeRank(Map<String, Object> map) {
		return dgItemFileTypeMapper.selItemFileSmallTypeRank(map);
	}

	@Override
	public void addItemFileSmallTypeRank(Map<String, Object> map) {
		dgItemFileTypeMapper.addItemFileSmallTypeRank(map);
	}

	@Override
	public int updateItemFileSmallTypeRankMoveUp(Integer id) {
		return dgItemFileTypeMapper.updateItemFileSmallTypeRankMoveUp(id);
	}

	@Override
	public int updateItemFileSmallTypeRankMoveDown(Integer id) {
		return dgItemFileTypeMapper.updateItemFileSmallTypeRankMoveDown(id);
	}

	@Override
	public List<String> selectItemFileRankSmallTypeMoveTopper(Integer id) {
		return dgItemFileTypeMapper.selectItemFileRankSmallTypeMoveTopper(id);
	}

	@Override
	public void updateItemFileRankSmallTypeMoveTopper(List<String> ids) {
		dgItemFileTypeMapper.updateItemFileRankSmallTypeMoveTopper(ids);
	}

	@Override
	public void updateItemFileSmallTypeRank(Map<String, Object> params) {
		dgItemFileTypeMapper.updateItemFileSmallTypeRank(params);
	}


	@Override
	public List<Map> selectYxeConsumerItems(int consumerId) {
		// TODO Auto-generated method stub
        List<Map> types = dgItemFileTypeMapper.selectYxeConsItemFileType(consumerId);
		//出初始化树结构根目录
        Map type = new HashMap();
        type.put("id",0);
        type.put("parent_id",0);
        type.put("name","品项管理");
        type.put("checked","false");
        type.put("open","1");
        types.add(0, type);  
        List<Map> items = dgItemFileTypeMapper.selectYxeConsItemFile(consumerId);
        for(Map m:items){
        	m.put("parent_id", m.get("ppxl_id"));
        }
        types.addAll(items);
		return types;
	}

	@Override
	public void insertYxeConsumers(int consumerId,String itemIds) {
		// TODO Auto-generated method stub
		List<String> ids = StringIdsTOListStr(itemIds);
		List<DgYxeConsItemShow> items = new ArrayList<DgYxeConsItemShow>();
		if(!StringUtils.isEmpty(ids)){
			for(String i:ids){
				DgYxeConsItemShow s = new DgYxeConsItemShow();
				s.setConsId(consumerId);
				s.setItemId(Integer.valueOf(i));
				items.add(s);
			}	
		}
		dgItemFileTypeMapper.delYxeConsItems(consumerId);
		if(!items.isEmpty()){
			dgItemFileTypeMapper.insertYxeConsItems(items);	
		}
	}

}
