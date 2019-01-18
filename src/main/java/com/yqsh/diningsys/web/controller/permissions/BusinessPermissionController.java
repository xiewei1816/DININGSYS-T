package com.yqsh.diningsys.web.controller.permissions;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysRoleMenu;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.permission.*;
import com.yqsh.diningsys.web.service.archive.DgItemFileService;
import com.yqsh.diningsys.web.service.archive.DgItemFileTypeService;
import com.yqsh.diningsys.web.service.base.SysMenuService;
import com.yqsh.diningsys.web.service.permission.BusinessPermissionService;
import com.yqsh.diningsys.web.service.permission.DgFrontDeskOperQxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-11-04 15:27
 */
@RequestMapping("/permission/businessPermisson")
@Controller
public class BusinessPermissionController extends BaseController {

    @Autowired
    private BusinessPermissionService businessPermissionService;

    @Autowired
    private DgItemFileService dgItemFileService;

    @Autowired
    private DgItemFileTypeService dgItemFileTypeService;

    @Autowired
    private DgFrontDeskOperQxService dgFrontDeskOperQxService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "permissions/sys_business/sys_business_index";
    }


    /**
     * 业务权限具体页面
     *
     * @param type
     * @return
     */
    @RequestMapping("/busPage/{type}")
    public String generalBus(@PathVariable Integer type, String zwCode, Model model) {
        model.addAttribute("zwCode", zwCode);
        SysPerOverview sysPerOverview = businessPermissionService.selecOverViewByZwCode(zwCode);
        model.addAttribute("sysPerOverview", sysPerOverview);
        if (type == 1) {
            SysPerBusiness sysPerBusiness = businessPermissionService.selectGeneralPerByZwCode(zwCode);
            model.addAttribute("sysPerBusiness", sysPerBusiness);
            return "permissions/sys_business/sys_bus_general";
        } else if (type == 2) {
            SysPerDiscount sysPerDiscount = businessPermissionService.selectDiscountPerByZwCode(zwCode);
            model.addAttribute("sysPerDiscount", sysPerDiscount);
            return "permissions/sys_business/sys_bus_discount";
        } else if (type == 3) {
            if (sysPerOverview == null) {
                model.addAttribute("hasFreeData", 0);
            }
            if (sysPerOverview != null && sysPerOverview.getFreeType() == null) {
                model.addAttribute("hasFreeData", 0);
            } else if (sysPerOverview != null && sysPerOverview.getFreeType() != null) {
                model.addAttribute("hasFreeData", 1);
            }
            return "permissions/sys_business/sys_bus_free";
        } else if (type == 4) {
            if (sysPerOverview == null) {
                model.addAttribute("hasChargeBackData", 0);
            }
            if (sysPerOverview != null && sysPerOverview.getChargebackType() == null) {
                model.addAttribute("hasChargeBackData", 0);
            } else if (sysPerOverview != null && sysPerOverview.getChargebackType() != null) {
                model.addAttribute("hasChargeBackData", 1);
            }
            return "permissions/sys_business/sys_bus_chargeBack";
        } else if (type == 5) {
            if (sysPerOverview == null) {
                model.addAttribute("hasVariablePriceData", 0);
            }
            if (sysPerOverview != null && sysPerOverview.getVariablePriceType() == null) {
                model.addAttribute("hasVariablePriceData", 0);
            } else if (sysPerOverview != null && sysPerOverview.getVariablePriceType() != null) {
                model.addAttribute("hasVariablePriceData", 1);
            }
            return "permissions/sys_business/sys_bus_variablePrice";
        }
        return "404";
    }


    //品项小类选择与品项类别选择相关

    /**
     * 具体品项选择界面
     * @param itemFileYxIds
     * @param model
     * @return
     */
    @RequestMapping("/itemFileChoosePage")
    public String itemFileChoosePage(String itemFileYxIds, Model model){
        model.addAttribute("itemFileYxIds",itemFileYxIds);
        return "permissions/sys_business/sys_bus_itemFile_choose";
    }

    /**
     * 品项小类选择界面
     * @param itemFileTypeYxIds
     * @param model
     * @return
     */
    @RequestMapping("/itemFileTypeChoosePage")
    public String itemFileTypeChoosePage(String itemFileTypeYxIds, Model model){
        model.addAttribute("itemFileTypeYxIds",itemFileTypeYxIds);
        return "permissions/sys_business/sys_bus_itemFileType_choose";
    }

    /**
     * 品项选择界面获取所有的品项分类
     * @return
     */
    @RequestMapping("/getAllItemFileType")
    @ResponseBody
    public List<DgItemFileType> getAllItemFileType(){
        try {
            List<DgItemFileType> dgItemFileTypes = dgItemFileTypeService.selectAllItemFileType();
            DgItemFileType dgItemFileType = new DgItemFileType();
            dgItemFileType.setId(0);
            dgItemFileType.setName("品项分类");
            dgItemFileTypes.add(0,dgItemFileType);
            return dgItemFileTypes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 品项小类选择界面，一级品项类别数据
     * @return
     */
    @RequestMapping("/getFirstLeveType")
    @ResponseBody
    public List<DgItemFileType> getFirstLeveType(){
        try {
            List<DgItemFileType> dgItemFileTypes = dgItemFileTypeService.getFirstLeveType();
            DgItemFileType dgItemFileType = new DgItemFileType();
            dgItemFileType.setId(0);
            dgItemFileType.setName("品项分类");
            dgItemFileTypes.add(0,dgItemFileType);
            return dgItemFileTypes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取第二级的品项类别
     * @param firstLevel
     * @return
     */
    @RequestMapping("/getSecondLeveType")
    @ResponseBody
    public List<DgItemFileType> getSecondLeveType(Integer firstLevel){
        try {
            return dgItemFileTypeService.getSecondLeveType(firstLevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据页面传入的已选品项ID获取已选品项的数据
     * @param inIds
     * @return
     */
    @RequestMapping("/getFileItemTypeInIds")
    @ResponseBody
    public List<DgItemFileType> getFileItemTypeInIds(String inIds){
        try {
            if(!StringUtils.isEmpty(inIds)){
                return dgItemFileTypeService.selectSecondItemFileTypeInIds(inIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取可选的品项数据
     * @param itemFileType
     * @param yxItemFileIds
     * @return
     */
    @RequestMapping("/getKxItemFile")
    @ResponseBody
    public List<DgItemFile> getKxItemFile(Integer itemFileType,String yxItemFileIds){
        try {
            return dgItemFileService.selectItemFileByTypeIdAndNotInIds(itemFileType,yxItemFileIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取可选的品项小类数据
     * @param parentId
     * @param yxItemFileTypeIds
     * @return
     */
    @RequestMapping("/getKxItemFileType")
    @ResponseBody
    public List<DgItemFileType> getKxItemFileType(Integer parentId,String yxItemFileTypeIds){
        try {
            return dgItemFileTypeService.selectItemFileTypeByTypeIdAndNotInIds(parentId,yxItemFileTypeIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有的职位
     * @return
     */
    @RequestMapping("/getAllPost")
    @ResponseBody
    public List<DgPublicCode0> getAllPost(){
        try {
            List<DgPublicCode0> dgPublicCodes = businessPermissionService.selectAllPostData();
            for(DgPublicCode0 dgPublicCode:dgPublicCodes){
                if(dgPublicCode.getPerBusId() == null && dgPublicCode.getId() != 16){
                    dgPublicCode.setcName(dgPublicCode.getcName()+"-未设置业务权限");
                }
            }
            return dgPublicCodes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 业务权限常规权限编辑
     * @param sysPerBusiness 常规权限信息
     * @param state 是否停用当前职务的业务操作权限
     * @param overViewId 业务权限 overview表的ID
     * @return
     */
    @RequestMapping("/editGeneralData")
    @ResponseBody
    public ResultInfo editGeneralData(SysPerBusiness sysPerBusiness,Integer state,Integer overViewId){
        try {
            businessPermissionService.editSysPerBusiness(sysPerBusiness,state,overViewId);
            return  returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    /**
     * 业务权限优惠权限编辑
     * @param sysPerDiscount
     * @param overViewId
     * @return
     */
    @RequestMapping("/editDiscountData")
    @ResponseBody
    public ResultInfo editDiscountData(SysPerDiscount sysPerDiscount, Integer overViewId){
        try {
            businessPermissionService.editDiscountData(sysPerDiscount,overViewId);
            return  returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    /**
     * 业务权限赠送权限编辑
     * @param hasFreeData 该职务是否存在赠送权限数据
     * @param zwCode 职务CODE
     * @param overViewId  业务权限 overview表的ID
     * @param  freeDataIds 赠送品项或者品项小类ID
     * @param  freeDataType 赠送的类型
     * @param  freeMaxPrice 赠送金额的最大单价
     * @param  freeTemporary 是否赠送临时品项
     * @param  freeTotalMoney 每日赠送累计金额
     * @return
     */
    @RequestMapping("/editFreeData")
    @ResponseBody
    public ResultInfo editFreeData(Integer hasFreeData,String zwCode,Integer overViewId,String freeDataIds,Integer freeDataType,String freeTotalMoney,String freeMaxPrice,Integer freeTemporary){
        try {
            businessPermissionService.editFreeData(hasFreeData,zwCode,overViewId,freeDataIds,freeDataType,freeTotalMoney,freeMaxPrice,freeTemporary);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    /**
     * 业务权限 退单权限
     * @param hasChargeBackData
     * @param zwCode
     * @param overViewId
     * @param chargeBackDataIds
     * @param chargeBackDataType
     * @param chargebackTemporary
     * @return
     */
    @RequestMapping("/editChageBackData")
    @ResponseBody
    public ResultInfo editChageBackData(Integer hasChargeBackData,String zwCode,Integer overViewId,String chargeBackDataIds,Integer chargeBackDataType,Integer chargebackTemporary){
        try {
            businessPermissionService.editChageBackData(hasChargeBackData,zwCode,overViewId,chargeBackDataIds,chargeBackDataType,chargebackTemporary);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    /**
     * 业务权限 变价权限
     * @param hasVariablePriceData
     * @param zwCode
     * @param overViewId
     * @param variablePriceDataIds
     * @param variablePriceDataType
     * @return
     */
    @RequestMapping("/editVariablePriceData")
    @ResponseBody
    public ResultInfo editVariablePriceData(Integer hasVariablePriceData,String zwCode,Integer overViewId,String variablePriceDataIds,Integer variablePriceDataType){
        try {
            businessPermissionService.editVariablePriceData(hasVariablePriceData,zwCode,overViewId,variablePriceDataIds,variablePriceDataType);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    /**6个表格的已有数据查询**/

    @RequestMapping("/getSelectItemFile/{zwCode}/{type}")
    @ResponseBody
    public List<DgItemFile> getFreeSelectItemFile(@PathVariable String zwCode,@PathVariable Integer type){
        return businessPermissionService.selectItemFileByZwCodeAndType(zwCode,type);
    }

    @RequestMapping("/getSelectItemFileType/{zwCode}/{type}")
    @ResponseBody
    public List<DgItemFileType> getFreeSelectItemFileType(@PathVariable String zwCode,@PathVariable Integer type){
        return businessPermissionService.selectItemFileTypeByZwCodeAndType(zwCode,type);
    }
    
    
    
    /**
     * 前台权限控制
     * @param model
     * @return
     */
    @RequestMapping("/toFrontDeskQxIndex")
    public String toFrontDeskQxIndex(Model model){
    	List<DgFrontDeskOperQx> qxs = dgFrontDeskOperQxService.seleAll();
        model.addAttribute("qxs",qxs);
        return "permissions/sys_business/sys_bus_front_desk_oper";
    }
    
    
	/**
	 * 保存前台权限
	 * @param qxs
	 * @return
	 */
    @RequestMapping("/saveFrontDeskQx")
    @ResponseBody
    public ResultInfo saveFrontDeskQx(DgFrontDeskOperQxArray qxs){
        try {
        	dgFrontDeskOperQxService.saveFrontDeskQx(qxs);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    //TODO 2016年12月1日13:06:16 前台添加职务时，树取消刷新

    /**
     * 获取后台菜单
     * @return
     */
    @RequestMapping("/getAllBakcgroudMenu/{empDuties}")
    @ResponseBody
    public List<SysMenu> getAllBakcgroudMenu(@PathVariable String empDuties){
        return sysMenuService.getAllBakcgroudMenu(empDuties);
    }

    /**
     * 获取前台菜单
     * @param empDuties
     * @return
     */
    @RequestMapping("/getAllDeskMenu/{empDuties}")
    @ResponseBody
    public List<SysMenu> getAllDeskMenu(@PathVariable String empDuties){
        return sysMenuService.getAllDeskMenu(empDuties);
    }

    /**
     * 编辑职务系统使用权限
     * @param dutiesCode
     * @param menuId
     * @param check
     * @return
     */
    @RequestMapping("/editDutiesMenu")
    @ResponseBody
    public ResultInfo editDutiesMenu(String dutiesCode,Integer menuId,Boolean check){
        //todo  暂时这样解决
        try {
            SysRoleMenu sysMenu = sysMenuService.selectCheckPermission(dutiesCode, menuId);
            if(check) //需要添加数据判断
            {
                if(sysMenu == null){
                    sysMenuService.editDutiesMenu(dutiesCode,menuId,check);
                }
            }else{ //删除需求数据判断
                if(sysMenu != null){
                    sysMenuService.editDutiesMenu(dutiesCode,menuId,check);
                }
            }
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/toDutiesAddPage")
    public String toDutiesAddPage(Model model){
        model.addAttribute("nextCode",businessPermissionService.selectNextDutiesCode());
        return "permissions/sys_business/sys_duties_add";
    }

    @RequestMapping("/addNewDuties")
    @ResponseBody
    public ResultInfo addNewDuties(String dutiesCode,String dutiesName){
        try {
            businessPermissionService.addNewDuties(dutiesCode,dutiesName);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

}
