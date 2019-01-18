package com.yqsh.diningsys.web.service.permission;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.permission.SysPerBusiness;
import com.yqsh.diningsys.web.model.permission.SysPerDiscount;
import com.yqsh.diningsys.web.model.permission.SysPerOverview;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务权限
 *
 * @author zhshuo create on 2016-11-04 14:27
 */
@Service
public interface BusinessPermissionService extends GenericService<SysPerOverview,Integer>{

    /**
     * 获取所有职务
     * @return
     */
    List<DgPublicCode0> selectAllPostData();

    /**
     * 业务权限之常规权限编辑
     * @param sysPerBusiness 常规权限信息
     * @param state 是否停用当前职务的业务操作权限
     * @param overViewId 业务权限 overview表的ID
     */
    void editSysPerBusiness(SysPerBusiness sysPerBusiness, Integer state, Integer overViewId);

    /**
     * 业务权限之优惠权限编辑
     * @param sysPerDiscount 优惠权限信息
     * @param overViewId  业务权限 overview表的ID
     */
    void editDiscountData(SysPerDiscount sysPerDiscount, Integer overViewId);

    /**
     * 业务权限赠送权限编辑
     * @param hasFreeData 是否存在赠送权限数据
     * @param zwCode 职务code
     * @param overViewId  业务权限 overview表的ID
     * @param  freeDataIds 赠送品项或者品项小类ID
     * @param  freeDataType 赠送的类型
     * @param  freeMaxPrice 赠送金额的最大单价
     * @param  freeTemporary 是否赠送临时品项
     * @param  freeTotalMoney 每日赠送累计金额
     */
    void editFreeData(Integer hasFreeData,String zwCode,Integer overViewId,String freeDataIds,Integer freeDataType,String freeTotalMoney,String freeMaxPrice,Integer freeTemporary);

    /**
     * 业务权限退单权限编辑
     * @param hasChargeBackData 是否存在退单权限数据
     * @param zwCode 职务Code
     * @param overViewId 业务权限 overview表的ID
     * @param chargeBackDataIds 退单的数据
     * @param chargeBackDataType 退单的类型
     * @param chargebackTemporary 退临时品项
     */
    void editChageBackData(Integer hasChargeBackData,String zwCode,Integer overViewId,String chargeBackDataIds,Integer chargeBackDataType,Integer chargebackTemporary);

    /**
     * 业务权限 变价权限
     * @param hasVariablePriceData
     * @param zwCode
     * @param overViewId
     * @param variablePriceDataIds
     * @param variablePriceDataType
     */
    void editVariablePriceData(Integer hasVariablePriceData,String zwCode,Integer overViewId,String variablePriceDataIds,Integer variablePriceDataType);

    /**
     * 根据职务ID查找常规业务权限具体信息
     * @param zwCode
     * @return
     */
    SysPerBusiness selectGeneralPerByZwCode(String zwCode);

    /**
     * 根据职务ID查找优惠业务权限具体信息
     * @param zwCode
     * @return
     */
    SysPerDiscount selectDiscountPerByZwCode(String zwCode);

    /**
     * 根据职务ID查找业务权限总览信息
     * @param zwCode
     * @return
     */
    SysPerOverview selecOverViewByZwCode(String zwCode);

    /**
     * 根据职务ID以及查询的类型 查询品项数据
     * @param zwCode
     * @param type
     * @return
     */
    List<DgItemFile> selectItemFileByZwCodeAndType(String zwCode,Integer type);

    /**
     * 根据职务ID以及查询的类型 查询品项小类数据
     * @param zwCode
     * @param type
     * @return
     */
    List<DgItemFileType> selectItemFileTypeByZwCodeAndType(String zwCode, Integer type);

    //2016年11月28日14:51:11

    /**
     * 获取所有一级菜单
     * @return
     */
    List<SysMenu> selectSysPerLevelOneData();

    String selectNextDutiesCode();

    /**
     * 增加新职位
     * @param dutiesCode
     * @param dutiesName
     */
    void addNewDuties(String dutiesCode,String dutiesName);

    /**
     * 根据用户获取用户对应职务的权限
     * @param userCode
     * @return
     */
    SysPerBusiness selectBusinessByUserCode(String userCode);

    /**
     * 根据用户获取用户对应的优惠权限
     * @return
     */
    SysPerDiscount selectDiscountByUserCode(String userCode);



}
