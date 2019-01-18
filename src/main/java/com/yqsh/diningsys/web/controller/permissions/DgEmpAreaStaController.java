package com.yqsh.diningsys.web.controller.permissions;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.permission.DgEmpAreaSta;
import com.yqsh.diningsys.web.service.permission.DgEmpAreaStaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工区域统计信息权限
 *
 * @author zhshuo create on 2016-11-25 上午10:42
 */
@RequestMapping("/permission/empAreaSta")
@Controller
public class DgEmpAreaStaController extends BaseController{

    @Autowired
    private DgEmpAreaStaService dgEmpAreaStaService;

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("isOpen",dgEmpAreaStaService.selectEmpAreaStaPer());
        return "permissions/emp_area_sta/emp_area_sta_index";
    }

    /**
     * 获取所有员工
     * @return
     */
    @RequestMapping("/selectAllEmp")
    @ResponseBody
    public List<SysUser> selectAllEmp(){
        return dgEmpAreaStaService.selectAllEmp();
    }

    /**
     * 根据员工获取该员工的具体的区域权限
     * @param empCode
     * @return
     */
    @RequestMapping("/selectAllEmpAreaSta")
    @ResponseBody
    public List<DgEmpAreaSta> selectAllEmpAreaSta(String empCode){
        Map param = new HashMap();
        param.put("empCode",empCode);
        return dgEmpAreaStaService.selectAllArea(param);
    }

    /**
     * 点击表格里面的checkbox触发事件
     * @param empCode
     * @param areaCode
     * @param isOpen
     * @return
     */
    @RequestMapping("/editEmpAreaStaPermission")
    @ResponseBody
    public ResultInfo editEmpAreaStaPermission(String empCode,String areaCode,Integer isOpen){
        try {
            dgEmpAreaStaService.editEmpAreaStaPermission(empCode,areaCode,isOpen);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    /**
     * 启用员工查看消费区域统计信息权限
     * @param isOpen
     * @return
     */
    @RequestMapping("/editMasterEmpAreaSta/{isOpen}")
    @ResponseBody
    public ResultInfo editMasterEmpAreaSta(@PathVariable Integer isOpen){
        try {
            dgEmpAreaStaService.editMasterEmpAreaSta(isOpen);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    /**
     * 点击全部启用或者全部不启用
     * @param empCode
     * @param isOpen
     * @return
     */
    @RequestMapping("/editEmpAreaStaWithAll")
    @ResponseBody
    public ResultInfo editEmpAreaStaWithAll(String empCode,Integer isOpen){
        try {
            dgEmpAreaStaService.editEmpAreaWithAll(empCode,isOpen);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

}
