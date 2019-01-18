package com.yqsh.diningsys.web.service.archive.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.yqsh.diningsys.core.util.StringUtil;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.DgConsumptionAreaMapper;
import com.yqsh.diningsys.web.dao.businessMan.DgAreaManagerMapper;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.businessMan.DgAreaManager;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;

@Service
public class DgConsumptionAreaServiceImpl implements DgConsumptionAreaService {
	
	@Resource
    private DgConsumptionAreaMapper consumptionAreaMapper;
	@Resource
	private DgAreaManagerMapper dgAreaManagerMapper;

	@Override
	public Page<DgConsumptionArea> getPageList(DgConsumptionArea consumptionArea) {
		Integer totle = consumptionAreaMapper.countListByPage(consumptionArea);
		List<DgConsumptionArea> list = consumptionAreaMapper.getListByPage(consumptionArea);
		return PageUtil.getPage(totle, consumptionArea.getPage(),list, consumptionArea.getRows());
	}

	@Override
	public int insertOrUpd(DgConsumptionArea consumptionArea) {
		int result = 0;
		if(consumptionArea.getId() != null && consumptionArea.getId() > 0){
			result = consumptionAreaMapper.update(consumptionArea);
		}else{
			consumptionArea.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			result = consumptionAreaMapper.newInsert(consumptionArea);
			dgAreaManagerMapper.insertSelective(new DgAreaManager(0, 0, 0, 0, 0, 0, 0, consumptionArea.getId()));
		}
		return result;
	}

	@Override
	public DgConsumptionArea getConsumptionAreaByID(
			DgConsumptionArea consumptionArea) {
		return consumptionAreaMapper.getConsumptionAreaByID(consumptionArea);
	}

	@Override
	public int deleteByIds(DgConsumptionArea consumptionArea) {
		return consumptionAreaMapper.delete(consumptionArea);
	}

    @Override
    public void deleteById(Integer id) {
		consumptionAreaMapper.deleteById(id);
		dgAreaManagerMapper.deleteByAreaId(id);
    }

    @Override
	public List<DgConsumptionArea> getAllList(DgConsumptionArea consumptionArea) {
		return consumptionAreaMapper.getAllList(consumptionArea);
	}

    @Override
    public List<DgConsumptionArea> selectAllArea() {
        return consumptionAreaMapper.selectAllArea();
    }

	@Override
	public List<Map<java.lang.String,Object>> getAreaByIds(String ids) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> list = null;
		if (!StringUtil.isBlank(ids)) {
			list = new ArrayList<String>();
			String s[] = ids.split(",");
			for(int i=0;i<s.length;i++){
				list.add(s[i]);
			}
		}
		map.put("ids",list);
		return consumptionAreaMapper.getAreaByIds(map);
	}
}
