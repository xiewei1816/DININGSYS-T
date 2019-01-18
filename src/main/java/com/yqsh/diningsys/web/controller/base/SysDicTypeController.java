//package com.yqsh.diningsys.web.controller.base;
//
//import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
//import com.yqsh.diningsys.web.model.ResultInfo;
//import com.yqsh.diningsys.web.model.SysDicType;
//import com.yqsh.diningsys.web.service.base.SysDicTypeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * SysDicTypeController
// *
// * @author zhshuo create on 2016-08-01 10:45
// */
//@Controller
//@RequestMapping("/dicType")
//public class SysDicTypeController extends BaseController {
//
//    @Autowired
//    private SysDicTypeService sysDicTypeService;
//
//    @RequestMapping("/index")
//    public String index() {
//        return "system/dicType/dicType_index";
//    }
//
//    @RequestMapping("/showDicTypeAddPage")
//    public ModelAndView showDicTypeAddPage(){
//        ModelAndView modelAndView = new ModelAndView("system/dicType/dicType_add");
//        modelAndView.addObject("type","add");
//        return modelAndView;
//    }
//
//    @RequestMapping("/showDicTypeEditPage/{id}")
//    public ModelAndView showDicTypeEditPage(@PathVariable Integer id){
//        ModelAndView modelAndView = new ModelAndView("system/dicType/dicType_add");
//        SysDicType sysDicType = sysDicTypeService.selectByPrimaryKey(id);
//        modelAndView.addObject("dicType",sysDicType);
//        modelAndView.addObject("type","edit");
//        return modelAndView;
//    }
//
//    @RequestMapping("/getData")
//    @ResponseBody
//    public Page<SysDicType> getUserByCon(HttpServletRequest request){
//        String[] orderColumns = {"","dic_type_name","dic_type_code","create_time"};
//        try {
//            Page<SysDicType> page = null;
////            page = this.getPageParams(page,request,orderColumns);
//            sysDicTypeService.selectByConAndPage(page,page.getPageParam());
//            return page;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @RequestMapping(value = "/addDicType",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo addDicType(SysDicType sysDicType){
//        try {
//            sysDicTypeService.insert(sysDicType);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    @RequestMapping(value = "/editDicType",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo editDicType(SysDicType sysDicType){
//        try {
//            sysDicTypeService.updateByPrimaryKeySelective(sysDicType);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    @RequestMapping(value = "/delDicTypes",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo delDicTypes(String dicTypeIds){
//        try {
//            sysDicTypeService.deleteByPrimaryKeys(arrayToList(dicTypeIds.split(",")));
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//}
