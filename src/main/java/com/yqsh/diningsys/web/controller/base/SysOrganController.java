//package com.yqsh.diningsys.web.controller.base;
//
//import com.yqsh.diningsys.web.model.ResultInfo;
//import com.yqsh.diningsys.web.model.SysOrgan;
//import com.yqsh.diningsys.web.service.base.SysOrganService;
//import net.sf.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.List;
//
///**
// * 基础数据模块-组织管理
// *
// * @author zhshuo create on 2016-07-29 11:18
// */
//@Controller
//@RequestMapping("/sysOrgan")
//public class SysOrganController extends BaseController{
//
//    @Autowired
//    private SysOrganService sysOrganService;
//
//
//    @RequestMapping("/index")
//    public String index(){
//        return "system/organ/organ_index";
//    }
//
//    @RequestMapping("/getIndexTableData")
//    public ModelAndView getIndexTableData(){
//        ModelAndView modelAndView = new ModelAndView("system/organ/organ_index_table");
//        List<SysOrgan> allSysOrgan = sysOrganService.selectAllSysOrgan();
//        modelAndView.addObject("allSysOrgan",allSysOrgan);
//        return modelAndView;
//    }
//
//    @RequestMapping("/toAdd")
//    public ModelAndView showAddPage(){
//        ModelAndView modelAndView = new ModelAndView("system/organ/organ_add");
//        List<SysOrgan> parentSysOrgans = sysOrganService.selectAllSysOrgan();
//        modelAndView.addObject("parentSysOrgans",parentSysOrgans);
//        modelAndView.addObject("type","add");
//        return modelAndView;
//    }
//
//    @RequestMapping("/showEditPage/{id}")
//    public ModelAndView showEditPage(@PathVariable Integer id){
//        ModelAndView modelAndView = new ModelAndView("system/organ/organ_add");
//        SysOrgan sysOrgan = sysOrganService.selectByPrimaryKey(id);
//        List<SysOrgan> parentSysOrgans = sysOrganService.selectAllSysOrgan();
//        modelAndView.addObject("parentSysOrgans",parentSysOrgans);
//        modelAndView.addObject("sysOrgan",sysOrgan);
//        modelAndView.addObject("type","edit");
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/addSysOrgan",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo addSysOrgan(SysOrgan sysOrgan){
//        try {
//            sysOrganService.insertSelective(sysOrgan);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    @RequestMapping(value = "/editSysOrgan",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo editSysOrgan(SysOrgan sysOrgan){
//        try {
//            sysOrganService.updateByPrimaryKeySelective(sysOrgan);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    @RequestMapping(value = "/delOrgan",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo delOrgan(Integer id){
//        try {
//            sysOrganService.deleteByPrimaryKey(id);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    @RequestMapping(value = "/getSysOrganData",method = RequestMethod.POST)
//    @ResponseBody
//    public String getsysOrganData(){
//        JSONObject jsonObject = new JSONObject();
//        List<SysOrgan> allSysOrgan = sysOrganService.selectAllSysOrgan();
//        jsonObject.put("allSysOrgan",allSysOrgan);
//        return jsonObject.toString();
//    }
//}
