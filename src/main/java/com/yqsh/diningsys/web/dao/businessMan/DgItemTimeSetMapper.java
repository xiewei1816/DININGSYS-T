package com.yqsh.diningsys.web.dao.businessMan;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.businessMan.DgItemTimeSet;

@Repository
public interface DgItemTimeSetMapper extends GenericDao<DgItemTimeSet,Integer> {

	int update(DgItemTimeSet dgItemTimeSet);

	int newInsert(DgItemTimeSet dgItemTimeSet);

	List<DgItemTimeSet> getAllList();
    
}