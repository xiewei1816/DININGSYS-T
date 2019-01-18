package com.yqsh.diningsys.web.controller.archive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.archive.TbZdbz;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.archive.TbZdbzService;
import com.yqsh.diningsys.web.service.sysSettings.AutoSeqService;

/**
 * 整单备注控制器
 *
 * @author xiewei
 * @version 创建时间：2016年9月29日 上午10:36:12
 */
@Controller
@RequestMapping(value = "/archive/zdbz")
public class TbZdbzController extends BaseController {

    @Autowired
    private TbZdbzService tbZdbzService;
    @Autowired
    private TbOrgService tbOrgService;
    @Autowired
    private AutoSeqService seqService;

    @RequestMapping("/index")
    public ModelAndView index(Model model, TbOrg tbOrg, TbZdbz tbZdbz) {
        model.addAttribute("listOrg", tbOrgService.getAllList(tbOrg)); //查询机构组织数据
        //查询整单备注类型数据
        tbZdbz.setIsDel("0");
        model.addAttribute("listZdbz", tbZdbzService.getAllList(tbZdbz));
        ModelAndView modelAndView = new ModelAndView("archive/zdbz/zdbz-index");
        //获取自动编号
//        String currentNum = seqService.getSeq("zdbz", 2, 0, "", 0, "");
//        modelAndView.addObject("number",currentNum);
        return modelAndView;
    }

    /**
     * 跳转至回收站
     *
     * @param model
     * @return
     */
    @RequestMapping("/trash")
    public ModelAndView trash(Model model) {
        ModelAndView modelAndView = new ModelAndView("archive/zdbz/zdbz-trash");
        return modelAndView;
    }

    /**
     * 分页查询整单备注信息
     *
     * @param request
     * @param response
     * @param tbZdbz
     * @return
     */
    @RequestMapping(value = "/getPageList")
    @ResponseBody
    public Object getPageList(HttpServletRequest request, HttpServletResponse response, TbZdbz tbZdbz) {
        com.yqsh.diningsys.core.util.Page<TbZdbz> pagebean = null;
        try {
            pagebean = tbZdbzService.getPageList(tbZdbz);
            pagebean.setTotal(pagebean.getContext().getPageCount());
            pagebean.setPage(tbZdbz.getPage());
            pagebean.setRecords(pagebean.getContext().getTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }

    /**
     * 通过整单备注类型ID查询整单备注信息
     *
     * @param request
     * @param response
     * @param tbZdbz
     * @return
     */
    @RequestMapping(value = "/getZdbzById")
    @ResponseBody
    public Object getRfcById(HttpServletRequest request, HttpServletResponse response, TbZdbz tbZdbz) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            tbZdbz.setIsDel("0");//查询未被删除的整单备注信息
            TbZdbz zdbz = tbZdbzService.getZdbzById(tbZdbz);
            result.put("zdbz", zdbz);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "error");
        }
        return result;
    }

    /**
     * 新增、修改整单备注信息保存
     *
     * @param request
     * @param response
     * @param tbZdbz
     * @return
     */
    @RequestMapping(value = "/saveZdbz")
    @ResponseBody
    public Object saveRfc(HttpServletRequest request, HttpServletResponse response, TbZdbz tbZdbz) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            TbZdbz dbZdbz = tbZdbzService.getZdbzByNum(tbZdbz.getZdbzNum());
            if(dbZdbz != null){
                if(dbZdbz.getId() != null && tbZdbz.getId() == dbZdbz.getId()){
                    tbZdbzService.insertOrUpdZdbz(tbZdbz);
                    seqService.setUsedSeq("zdbz", tbZdbz.getZdbzNum());
                    result.put("success", "OK");
                }else{
                    result.put("success", "false");
                    result.put("error", "编号重复");
                }
            }else{
                tbZdbzService.insertOrUpdZdbz(tbZdbz);
                seqService.setUsedSeq("zdbz", tbZdbz.getZdbzNum());
                result.put("success", "OK");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
            result.put("error", e.getMessage());
        }
        return result;
    }

    /**
     * 整单备注信息回收站
     *
     * @param request
     * @param response
     * @param tbZdbz
     * @return
     */
    @RequestMapping(value = "/deleteZdbzTrash")
    @ResponseBody
    public Object deleteZdbzrash(HttpServletRequest request, HttpServletResponse response, TbZdbz tbZdbz) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<String> list = new ArrayList<String>();
            for (String str : tbZdbz.getEditIds().split(",")) {
                list.add(str);
            }
            tbZdbz.setIds(list);
            tbZdbzService.deleteTrash(tbZdbz);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
        }
        return result;
    }

    /**
     * 还原回收站整单备注信息
     *
     * @param request
     * @param response
     * @param tbZdbz
     * @return
     */
    @RequestMapping(value = "/replyZdbz")
    @ResponseBody
    public Object replyZdbz(HttpServletRequest request, HttpServletResponse response, TbZdbz tbZdbz) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<String> list = new ArrayList<String>();
            for (String str : tbZdbz.getEditIds().split(",")) {
                list.add(str);
            }
            tbZdbz.setIds(list);
            tbZdbzService.replyZdbz(tbZdbz);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
        }
        return result;
    }

    /**
     * 删除整单备注信息
     *
     * @param request
     * @param response
     * @param tbZdbz
     * @return
     */
    @RequestMapping(value = "/deleteZdbz")
    @ResponseBody
    public Object deleteRfc(HttpServletRequest request,
            HttpServletResponse response, TbZdbz tbZdbz) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<String> list = new ArrayList<String>();
            for (String str : tbZdbz.getEditIds().split(",")) {
                list.add(str);
            }
            tbZdbz.setIds(list);
            tbZdbzService.deleteByIds(tbZdbz);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
        }
        return result;
    }

    /**
     * 导出整单备注信息Excel
     *
     * @param request
     * @param response
     * @param model
     * @param tbZdbz
     * @return
     */
    @RequestMapping(value = "exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response, Model model, TbZdbz tbZdbz) {
        List<TbZdbz> zdbzList = tbZdbzService.getAllList(tbZdbz);
        model.addAttribute("zdbzList", zdbzList);
        ModelAndView modelAndView = new ModelAndView("archive/zdbz/exportXls");
        return modelAndView;
    }
}
