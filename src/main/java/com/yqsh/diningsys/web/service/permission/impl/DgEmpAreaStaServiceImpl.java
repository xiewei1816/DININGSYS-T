package com.yqsh.diningsys.web.service.permission.impl;

import com.yqsh.diningsys.web.dao.permission.DgEmpAreaStaMapper;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.permission.DgEmpAreaSta;
import com.yqsh.diningsys.web.service.permission.DgEmpAreaStaService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.Param;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-11-25 上午11:02
 */
@Service
public class DgEmpAreaStaServiceImpl implements DgEmpAreaStaService {

    @Resource
    private DgEmpAreaStaMapper dgEmpAreaStaMapper;

    @Override
    public Integer selectEmpAreaStaPer() {
        return dgEmpAreaStaMapper.selectEmpAreaStaPer();
    }

    @Override
    public List<SysUser> selectAllEmp() {
        return dgEmpAreaStaMapper.selectAllEmp();
    }

    @Override
    public List<DgEmpAreaSta> selectAllArea(Map param) {
        List<DgEmpAreaSta> dgEmpAreaStasNoDel = dgEmpAreaStaMapper.selectAllNoDelArea();
        List<DgEmpAreaSta> dgEmpAreaStas = dgEmpAreaStaMapper.selectAllArea(param);
        if(dgEmpAreaStas.size() > 0){
            for(DgEmpAreaSta dgEmpAreaSta:dgEmpAreaStasNoDel){
                for(DgEmpAreaSta dgEmpAreaSta1:dgEmpAreaStas){
                    if(dgEmpAreaSta.getAreaCode().equals(dgEmpAreaSta1.getAreaCode())){
                        dgEmpAreaSta.setIsOpen(1);
                    }
                }
            }
        }
        return dgEmpAreaStasNoDel;
    }

    @Override
    public void editEmpAreaStaPermission(String empCode, String areaCode, Integer isOpen) {
        Map param = new HashMap();
        param.put("empCode",empCode);
        param.put("areaCode",areaCode);
        param.put("isOpen",isOpen);
        DgEmpAreaSta dgEmpAreaSta = dgEmpAreaStaMapper.selectDataByEmpCodeAndAreaCode(param);
        if(dgEmpAreaSta == null){
            if(isOpen == 1){
                dgEmpAreaStaMapper.insertEmpAreaStaData(param);
            }
        }else{
            if(isOpen == 0){
                dgEmpAreaStaMapper.deleteEmpAreaStaData(param);
            }
        }
    }

    @Override
    public void editMasterEmpAreaSta(Integer isOpen) {
        Map param = new HashMap();
        param.put("isOpen",isOpen);
        dgEmpAreaStaMapper.editEmpAreaSta(param);
    }

    @Override
    public void editEmpAreaWithAll(String empCode, Integer isOpen) {
        Map param = new HashMap();
        param.put("empCode",empCode);
        if(isOpen == 1){
            param.put("list",dgEmpAreaStaMapper.selectAllAreaCode());
            dgEmpAreaStaMapper.deleteEmpAreaStaDataByEmpCode(param);
            dgEmpAreaStaMapper.insertEmpAreaStaDataMulti(param);
        }else{
            dgEmpAreaStaMapper.deleteEmpAreaStaDataByEmpCode(param);
        }
    }
}
