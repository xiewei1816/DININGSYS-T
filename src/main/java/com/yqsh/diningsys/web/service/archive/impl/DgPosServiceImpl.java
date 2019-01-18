package com.yqsh.diningsys.web.service.archive.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yqsh.diningsys.core.util.DateUtil;
import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.DgPosMapper;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.service.archive.DgPosService;
import org.springframework.util.StringUtils;

@Service
public class DgPosServiceImpl implements DgPosService {

	@Resource
    private DgPosMapper posMapper;	
	
	@Override
	public Page<DgPos> getPageList(DgPos dgPos) {
		if(!StringUtils.isEmpty(dgPos.getCrEndTime())){
            Date dateByFormat = DateUtil.getDateByFormat(dgPos.getCrEndTime(), "yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateByFormat);
			calendar.add(Calendar.DATE,1);
            String nextDay = DateUtil.dateToStr(calendar.getTime(),"yyyy-MM-dd");
            dgPos.setCrEndTime(nextDay);
        }

		Integer totle = posMapper.countListByPage(dgPos);
		List<DgPos> list = posMapper.getListByPage(dgPos);
		return PageUtil.getPage(totle, dgPos.getPage(),list, dgPos.getRows());
	}

	@Override
	public int insertOrUpd(DgPos dgPos) {
		int result = 0;
		if(dgPos.getId() != null && dgPos.getId() > 0){
			result = posMapper.update(dgPos);
		}else{
			dgPos.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			result = posMapper.newInsert(dgPos);
		}
		return result;
	}

	@Override
	public DgPos getPosByID(DgPos dgPos) {
		return posMapper.getPosByID(dgPos);
	}

	@Override
	public int deleteByIds(DgPos dgPos) {
		return posMapper.delete(dgPos);
	}

	@Override
	public List<DgPos> getAllList(DgPos dgPos) {
		return posMapper.getAllList(dgPos);
	}

	@Override
	public List<Map<String, Object>> getAllPosList() {
		return posMapper.getAllPosList();
	}

	@Override
	public int deleteTrash(DgPos pos) {
		return posMapper.deleteTrash(pos);
	}

	@Override
	public int replyPos(DgPos pos) {
		return posMapper.replyPos(pos);
	}

    @Override
    public DgPos selectPosByPosCode(String posCode) {
        return posMapper.getposByCode(posCode);
    }

	@Override
	public DgPos selectPosByPosId(Integer posId) {
		return posMapper.selectPosByPosId(posId);
	}

	@Override
	public Integer checkHavaYxePos(DgPos dgPos) {
		// TODO Auto-generated method stub
		return posMapper.checkHavaYxePos(dgPos);
	}

}
