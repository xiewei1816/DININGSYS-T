package com.yqsh.diningsys.web.service.permission.impl;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.PinYinUtil;
import com.yqsh.diningsys.web.dao.SysMenuMapper;
import com.yqsh.diningsys.web.dao.SysUserMapper;
import com.yqsh.diningsys.web.dao.permission.*;
import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.permission.SysPerBusiness;
import com.yqsh.diningsys.web.model.permission.SysPerDiscount;
import com.yqsh.diningsys.web.model.permission.SysPerOverview;
import com.yqsh.diningsys.web.service.permission.BusinessPermissionService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-11-04 14:29
 */
@Service
public class BusinessPermissionServiceImpl extends GenericServiceImpl<SysPerOverview,Integer> implements BusinessPermissionService{

    @Resource
    private SysPerOverviewMapper sysPerOverviewMapper;

    @Resource
    private SysPerBusinessMapper sysPerBusinessMapper;

    @Resource
    private SysPerDiscountMapper sysPerDiscountMapper;

    @Resource
    private SysPerItemfileMapper sysPerItemfileMapper;

    @Resource
    private SysPerItemfiletypeMapper sysPerItemfiletypeMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public GenericDao<SysPerOverview, Integer> getDao() {
        return sysPerOverviewMapper;
    }

    @Override
    public List<DgPublicCode0> selectAllPostData() {
        return sysPerOverviewMapper.selectAllPostData();
    }

    @Override
    public void editSysPerBusiness(SysPerBusiness sysPerBusiness, Integer state, Integer overViewId) {
        if(state == null){
            state = 0;
        }
        //overViewId为空，当前职务为设置业务权限，没有数据，先添加overView表数据
        SysPerOverview sysPerOverview = new SysPerOverview();
        sysPerOverview.setState(state);
        if(overViewId == null){
            sysPerOverview = overViewCreate(sysPerBusiness.getZwCode());
            sysPerOverviewMapper.insert(sysPerOverview);
        }
        if(sysPerBusiness.getId() == null){
            sysPerBusinessMapper.insertSelective(sysPerBusiness);
        }else if(overViewId != null && sysPerBusiness.getId() != null){
            //修改为页面传入的最新状态
            sysPerOverview.setId(overViewId);
            sysPerOverviewMapper.updateStateById(sysPerOverview);
            sysPerBusinessMapper.updateByPrimaryKey(sysPerBusiness);
        }
    }

    @Override
    public void editDiscountData(SysPerDiscount sysPerDiscount, Integer overViewId) {
        //overViewId为空，当前职务为设置业务权限，没有数据，先添加overView表数据
        if(overViewId == null){
            SysPerOverview sysPerOverview = overViewCreate(sysPerDiscount.getZwCode());
            sysPerOverviewMapper.insert(sysPerOverview);
        }
        if(sysPerDiscount.getId() == null){
            if(sysPerDiscount.getDeyh() == null){
                sysPerDiscount.setMddeyhOne(null);
                sysPerDiscount.setMddeyhTwo(null);
                sysPerDiscount.setDeyhType(null);
            }else{
                if(sysPerDiscount.getDeyhType() == 1){
                    sysPerDiscount.setMddeyhTwo(null);
                }else if(sysPerDiscount.getDeyhType() == 2){
                    sysPerDiscount.setMddeyhOne(null);
                }
            }

            if(sysPerDiscount.getPcceqx() == null){
                sysPerDiscount.setPcceType(null);
                sysPerDiscount.setMdpccexzOne(null);
                sysPerDiscount.setMdpccexzTwo(null);
            }else{
                if(sysPerDiscount.getPcceType() == 1){
                    sysPerDiscount.setMdpccexzTwo(null);
                }else if(sysPerDiscount.getPcceType() == 2){
                    sysPerDiscount.setMdpccexzOne(null);
                }
            }

            sysPerDiscountMapper.insert(sysPerDiscount);
        }else if(overViewId != null && sysPerDiscount.getId() != null){
            if(sysPerDiscount.getDeyh() == null){
                sysPerDiscount.setMddeyhOne(null);
                sysPerDiscount.setMddeyhTwo(null);
                sysPerDiscount.setDeyhType(null);
            }else{
                if(sysPerDiscount.getDeyhType() == 1){
                    sysPerDiscount.setMddeyhTwo(null);
                }else if(sysPerDiscount.getDeyhType() == 2){
                    sysPerDiscount.setMddeyhOne(null);
                }
            }

            if(sysPerDiscount.getPcceqx() == null){
                sysPerDiscount.setPcceType(null);
                sysPerDiscount.setMdpccexzOne(null);
                sysPerDiscount.setMdpccexzTwo(null);
            }else{
                if(sysPerDiscount.getPcceType() == 1){
                    sysPerDiscount.setMdpccexzTwo(null);
                }else if(sysPerDiscount.getPcceType() == 2){
                    sysPerDiscount.setMdpccexzOne(null);
                }
            }
            sysPerDiscountMapper.updateByPrimaryKey(sysPerDiscount);
        }
    }

    @Override
    public void editFreeData(Integer hasFreeData,String zwCode,Integer overViewId,String freeDataIds,Integer freeDataType,String freeTotalMoney,String freeMaxPrice,Integer freeTemporary) {
        SysPerOverview sysPerOverview = new SysPerOverview();
        if(overViewId == null){ //overView为空，先添加overView表业务权限数据
            sysPerOverview = overViewCreate(zwCode);
            sysPerOverview.setFreeMaxPrice(freeMaxPrice);
            sysPerOverview.setFreeTemporary(freeTemporary);
            sysPerOverview.setFreeTotalMoney(freeTotalMoney);
            if(!StringUtils.isEmpty(freeDataIds)){
                sysPerOverview.setFreeType(freeDataType);
            }
            sysPerOverviewMapper.insert(sysPerOverview);
        }else{
            sysPerOverview.setFreeMaxPrice(freeMaxPrice);
            sysPerOverview.setFreeTemporary(freeTemporary);
            sysPerOverview.setFreeTotalMoney(freeTotalMoney);
            if(!StringUtils.isEmpty(freeDataIds)){
                sysPerOverview.setFreeType(freeDataType);
            }
            sysPerOverview.setId(overViewId);
            sysPerOverviewMapper.updateFreeDataById(sysPerOverview);
        }

        //存在赠送权限数据
        if(hasFreeData == 1){ //1有数据、2无数据
            Map param = new HashMap();
            param.put("zwCode",zwCode);
            param.put("type",1);
            sysPerItemfiletypeMapper.deleteMutliDataByZwIdAndType(param);
            sysPerItemfileMapper.deleteMutliDataByZwIdAndType(param);
            if(!StringUtils.isEmpty(freeDataIds)) {
                itemDataInsert(freeDataIds,zwCode,freeDataType,1);
            }
        }else if(hasFreeData == 0){//不存在赠送权限数据
            itemDataInsert(freeDataIds,zwCode,freeDataType,1);
        }
    }

    @Override
    public void editChageBackData(Integer hasChargeBackData, String zwCode, Integer overViewId, String chargeBackDataIds, Integer chargeBackDataType, Integer chargebackTemporary) {
        SysPerOverview sysPerOverview = new SysPerOverview();
        if(overViewId == null){ //overView为空，先添加overView表业务权限数据
            sysPerOverview = overViewCreate(zwCode);
            sysPerOverview.setChargebackTemporary(chargebackTemporary);
            if(!StringUtils.isEmpty(chargeBackDataIds)){
                sysPerOverview.setChargebackType(chargeBackDataType);
            }
            sysPerOverviewMapper.insert(sysPerOverview);
        }else{
            sysPerOverview.setChargebackTemporary(chargebackTemporary);
            if(!StringUtils.isEmpty(chargeBackDataIds)){
                sysPerOverview.setChargebackType(chargeBackDataType);
            }
            sysPerOverview.setId(overViewId);
            sysPerOverviewMapper.updateChargeBackDataById(sysPerOverview);
        }

        //存在退单权限数据
        if(hasChargeBackData == 1){ //1有数据、2无数据
            Map param = new HashMap();
            param.put("zwCode",zwCode);
            param.put("type",2);
            sysPerItemfiletypeMapper.deleteMutliDataByZwIdAndType(param);
            sysPerItemfileMapper.deleteMutliDataByZwIdAndType(param);
            if(!StringUtils.isEmpty(chargeBackDataIds)) {
                itemDataInsert(chargeBackDataIds,zwCode,chargeBackDataType,2);
            }
        }else if(hasChargeBackData == 0){//不存在退单权限数据
            itemDataInsert(chargeBackDataIds,zwCode,chargeBackDataType,2);
        }
    }

    @Override
    public void editVariablePriceData(Integer hasVariablePriceData, String zwCode, Integer overViewId, String variablePriceDataIds, Integer variablePriceDataType) {
        SysPerOverview sysPerOverview = new SysPerOverview();
        if(overViewId == null){ //overView为空，先添加overView表业务权限数据
            sysPerOverview = overViewCreate(zwCode);
            if(!StringUtils.isEmpty(variablePriceDataIds)){
                sysPerOverview.setVariablePriceType(variablePriceDataType);
            }
            sysPerOverviewMapper.insert(sysPerOverview);
        }else{
            if(!StringUtils.isEmpty(variablePriceDataIds)){
                sysPerOverview.setVariablePriceType(variablePriceDataType);
            }
            sysPerOverview.setId(overViewId);
            sysPerOverviewMapper.updateVarialePriceDataById(sysPerOverview);
        }

        //存在变价权限数据
        if(hasVariablePriceData == 1){ //1有数据、2无数据
            Map param = new HashMap();
            param.put("zwCode",zwCode);
            param.put("type",3);
            sysPerItemfiletypeMapper.deleteMutliDataByZwIdAndType(param);
            sysPerItemfileMapper.deleteMutliDataByZwIdAndType(param);
            if(!StringUtils.isEmpty(variablePriceDataIds)) {
                itemDataInsert(variablePriceDataIds,zwCode,variablePriceDataType,3);
            }
        }else if(hasVariablePriceData == 0){//不存在变价权限数据
            itemDataInsert(variablePriceDataIds,zwCode,variablePriceDataType,3);
        }
    }

    @Override
    public SysPerBusiness selectGeneralPerByZwCode(String zwCode) {
        Map param = new HashedMap();
        param.put("zwCode",zwCode);
        return sysPerBusinessMapper.selectGeneralPerByZwCode(param);
    }

    @Override
    public SysPerDiscount selectDiscountPerByZwCode(String zwCode) {
        Map param = new HashedMap();
        param.put("zwCode",zwCode);
        return sysPerDiscountMapper.selectDiscountPerByZwCode(param);
    }

    @Override
    public SysPerOverview selecOverViewByZwCode(String zwCode) {
        Map param = new HashedMap();
        param.put("zwCode",zwCode);
        return sysPerOverviewMapper.selecOverViewByZwCode(param);
    }

    @Override
    public List<DgItemFile> selectItemFileByZwCodeAndType(String zwCode, Integer type) {
        Map param = new HashMap();
        param.put("zwCode",zwCode);
        param.put("type",type);
        return sysPerItemfileMapper.selectItemFileByZwCodeAndType(param);
    }

    @Override
    public List<DgItemFileType> selectItemFileTypeByZwCodeAndType(String zwCode, Integer type) {
        Map param = new HashMap();
        param.put("zwCode",zwCode);
        param.put("type",type);
        return sysPerItemfileMapper.selectItemFileTypeByZwCodeAndType(param);
    }

    private SysPerOverview overViewCreate(String zwCode){
        SysPerOverview sysPerOverview = new SysPerOverview();
        sysPerOverview.setQxsfysz(1);
        sysPerOverview.setZwCode(zwCode);
        return sysPerOverview;
    }

    /**
     * itemFile或者itemFileType新增或者修改
     * @param freeDataIds
     * @param zwCode
     * @param selectDataType
     * @param dataType //1赠送、2退单、3变价
     */
    private void itemDataInsert(String freeDataIds,String zwCode,Integer selectDataType,Integer dataType){
        if(!StringUtils.isEmpty(freeDataIds)){ //新增时，数据为空，不插入品项/品项小类数据
            Map param = new HashMap();
            param.put("zwCode",zwCode);
            if(dataType == 1){
                param.put("type",1); //1赠送、2退单、3变价
            }else if(dataType == 2){
                param.put("type",2); //1赠送、2退单、3变价
            }else if(dataType == 3){
                param.put("type",3); //1赠送、2退单、3变价
            }
            param.put("list",arraysTOList(freeDataIds));
            if(selectDataType == 1){ //1小类、2具体品项
                sysPerItemfiletypeMapper.inserMultiData(param);
            }else{
                sysPerItemfileMapper.inserMultiData(param);
            }
        }
    }

    //TODO 2016年11月28日14:45:46 将系统使用权限、业务使用权限一级员工区域统计信息查看权限合并

    @Override
    public List<SysMenu> selectSysPerLevelOneData() {
        return sysMenuMapper.selectSysPerLevelOneData();
    }

    @Override
    public String selectNextDutiesCode() {
       Integer cuCode =  Integer.parseInt(sysPerBusinessMapper.selectNextDutiesCode().get("maxCode").toString());
        if(cuCode < 100){
            return "0"+(cuCode+1);
        }
        return cuCode+"";
    }

    @Override
    public void addNewDuties(String dutiesCode, String dutiesName) {
        Map param = new HashMap();
        param.put("dutiesCode",dutiesCode);
        param.put("dutiesName",dutiesName);
        param.put("dutiesOrder",Integer.parseInt(dutiesCode));
        param.put("sp",PinYinUtil.getFirstSpellUpperCase(dutiesName));
        sysPerBusinessMapper.addNewDuties(param);
    }

    @Override
    public SysPerBusiness selectBusinessByUserCode(String userCode) {

        SysUser sysUser = sysUserMapper.selectByUsername(userCode);
        Map param = new HashedMap();
        param.put("zwCode",sysUser.getEmpDuties());
        SysPerOverview sysPerOverview = sysPerOverviewMapper.selecOverViewByZwCode(param);

        if(sysPerOverview != null && sysPerOverview.getState() != null && sysPerOverview.getState() != 1){
            return sysPerBusinessMapper.selectGeneralPerByZwCode(param);
        }
        return null;
    }

    @Override
    public SysPerDiscount selectDiscountByUserCode(String userCode) {
        SysUser sysUser = sysUserMapper.selectByUsername(userCode);
        Map param = new HashedMap();
        param.put("zwCode",sysUser.getEmpDuties());
        return sysPerBusinessMapper.selectDiscountPerByZwCode(param);
    }
}
