package com.yqsh.diningsys.web.service.archive.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.DgPublicCode0Mapper;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.service.archive.DgPublicCode0Service;

/**
 * 公共代码服务实现层
* @author xiewei
 */
@Service
public class DgPublicCode0ServiceImpl implements DgPublicCode0Service {

    @Resource
    private DgPublicCode0Mapper dgPublicCode0Mapper;

	@Override
	public com.yqsh.diningsys.core.util.Page<DgPublicCode0> getPageList(
			DgPublicCode0 dpc) {
		Integer totle = dgPublicCode0Mapper.countListByPage(dpc);
		List<DgPublicCode0> list = dgPublicCode0Mapper.getListByPage(dpc);
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				DgPublicCode0 get_dpc = list.get(i);
				get_dpc.setcParent("顶级公共代码");
			}
		}
		return PageUtil.getPage(totle, dpc.getPage(),list, dpc.getRows());
	}

	@Override
	public int insertOrUpdDpc(DgPublicCode0 dpc) {
		int result = 0;
		if(dpc.getId() != null && dpc.getId() > 0){
			result = dgPublicCode0Mapper.update(dpc);
		}else{
			dpc.setiSystem("N");
			result = dgPublicCode0Mapper.newInsert(dpc);
		}
		return result;
	}

	@Override
	public DgPublicCode0 getDpcById(DgPublicCode0 dpc) {
		return dgPublicCode0Mapper.getDpcById(dpc);
	}
	
	@Override
	public DgPublicCode0 getDpcParentNameById(DgPublicCode0 dpc) {
		return dgPublicCode0Mapper.getDpcParentNameById(dpc);
	}

	@Override
	public int insertDpc(DgPublicCode0 dpc) {
		dgPublicCode0Mapper.insert(dpc);
        return dpc.getId();
	}

	@Override
	public int deleteByIds(DgPublicCode0 dpc) {
		return dgPublicCode0Mapper.delete(dpc);
	}

	@Override
	public int deleteTrash(DgPublicCode0 dpc) {
		return dgPublicCode0Mapper.deleteTrash(dpc);
	}

	@Override
	public int replyDpc(DgPublicCode0 dpc) {
		return dgPublicCode0Mapper.replyDpc(dpc);
	}

	@Override
	public List<DgPublicCode0> selectAllDpc(Map<String,Object> params) {
		return dgPublicCode0Mapper.selectAllDpc(params);
	}

	@Override
	public List<DgPublicCode0> getAllList(DgPublicCode0 dpc) {
		return dgPublicCode0Mapper.getAllList(dpc);
	}

	@Override
	public DgPublicCode0 getLastcCode(DgPublicCode0 dpc) {
		return dgPublicCode0Mapper.getLastcCode(dpc);
	}

	@Override
	public List<DgPublicCode0> selectSmallDpc(Map<String, Object> params) {
		return dgPublicCode0Mapper.selectSmallDpc(params);
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public List<Map<String, List<DgPublicCode0>>> getAllDpcToPage() {
		Map<String,List<DgPublicCode0>> map = null;
		List<Map<String,List<DgPublicCode0>>> list = new ArrayList<Map<String,List<DgPublicCode0>>>();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("cParent", "1");
		params.put("iDelFlg", "0");
		List<DgPublicCode0> parentDpcs = dgPublicCode0Mapper.selectAllDpc(params);
		for (int i = 0; i < parentDpcs.size(); i++) {
			map = new HashMap<String, List<DgPublicCode0>>();
			String cKeyWD = parentDpcs.get(i).getcKeyWD();
			String pId = parentDpcs.get(i).getId()+"";
			List<DgPublicCode0> childDpcs = (List<DgPublicCode0>) getChildDpcsBypId(pId);
			map.put(cKeyWD, childDpcs);
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 根据父节点ID查询所有子节点
	 * @param pId
	 * @return
	 */
	public Object getChildDpcsBypId(String pId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("cParent", pId);
		params.put("iDelFlg", "0");
		List<DgPublicCode0> childDpcs = dgPublicCode0Mapper.selectAllDpc(params);
		return childDpcs;
	}

	@Override
	public List<DgPublicCode0> selectSmallByParentName(
			Map<String, Object> params) {
		return dgPublicCode0Mapper.selectSmallByParentName(params);
	}

	@Override
	public int updatePubCode(DgPublicCode0 pubCode) {
		return dgPublicCode0Mapper.updatePubCode(pubCode);
	}

	@Override
	public int isExists(DgPublicCode0 pubCode) {
		return dgPublicCode0Mapper.isExists(pubCode);
	}

	@Override
	public List<DgPublicCode0> selectPublicCodeByKey(String key) {
		return dgPublicCode0Mapper.selectPublicCodeByKey(key);
	}

}
