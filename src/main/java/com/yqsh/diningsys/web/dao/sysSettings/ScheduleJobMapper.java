package com.yqsh.diningsys.web.dao.sysSettings;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.sysSettings.DgUrlSetting;
import com.yqsh.diningsys.web.model.sysSettings.ScheduleJob;

@Repository
public interface ScheduleJobMapper extends GenericDao<DgUrlSetting,Integer>{

	List<ScheduleJob> findAllScheduleJob(String jobname);

	void insert(ScheduleJob job);

	ScheduleJob selectById(String jobId);

	void updateById(ScheduleJob job);

	void deleteById(String jobId);

}
