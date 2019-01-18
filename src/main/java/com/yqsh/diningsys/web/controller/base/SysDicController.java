//package com.yqsh.diningsys.web.controller.base;
//
//import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
//import com.yqsh.diningsys.web.model.ResultInfo;
//import com.yqsh.diningsys.web.model.SysDic;
//import com.yqsh.diningsys.web.model.SysDicType;
//import com.yqsh.diningsys.web.service.base.SysDicService;
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
//import java.util.List;
//
///**
// * SysDicController
// *
// * @author zhshuo create on 2016-08-01 10:45
// */
//@Controller
//@RequestMapping("/dic")
//public class SysDicController extends BaseController {
//
//    @Autowired
//    private SysDicService sysDicService;
//
//    @Autowired
//    private SysDicTypeService sysDicTypeService;
//
//    @RequestMapping("/index")
//    public String index() {
//        return "system/dic/dic_index";
//    }
//
//    @RequestMapping("/showDicAddPage")
//    public ModelAndView showDicAddPage(){
//        ModelAndView modelAndView = new ModelAndView("system/dic/dic_add");
//        List<SysDicType> sysDicTypeList = sysDicTypeService.getAllDicType();
//        modelAndView.addObject("sysDicTypeList",sysDicTypeList);
//        modelAndView.addObject("type","add");
//        return modelAndView;
//    }
//
//    @RequestMapping("/showDicEditPage/{id}")
//    public ModelAndView showDicEditPage(@PathVariable Integer id){
//        ModelAndView modelAndView = new ModelAndView("system/dic/dic_add");
//        SysDic sysDic = sysDicService.selectByPrimaryKey(id);
//        List<SysDicType> sysDicTypeList = sysDicTypeService.getAllDicType();
//        modelAndView.addObject("sysDicTypeList",sysDicTypeList);
//        modelAndView.addObject("dic",sysDic);
//        modelAndView.addObject("type","edit");
//        return modelAndView;
//    }
//
//    @RequestMapping("/getData")
//    @ResponseBody
//    public Page<SysDic> getUserByCon(HttpServletRequest request){
//        String[] orderColumns = {"","dic_name","dic_code","dic_type","create_time"};
//        try {
//            Page<SysDic> page = null;
////            page = this.getPageParams(page,request,orderColumns);
//            sysDicService.selectByConAndPage(page,page.getPageParam());
//            return page;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @RequestMapping(value = "/addDic",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo addDic(SysDic sysDic){
//        try {
//            sysDicService.insert(sysDic);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    @RequestMapping(value = "/editDic",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo editDic(SysDic sysDic){
//        try {
//            sysDicService.updateByPrimaryKeySelective(sysDic);
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//
//    @RequestMapping(value = "/delDics",method = RequestMethod.POST)
//    @ResponseBody
//    public ResultInfo delDics(String dicIds){
//        try {
//            sysDicService.deleteByPrimaryKeys(arrayToList(dicIds.split(",")));
//            return returnSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFail();
//    }
//}
