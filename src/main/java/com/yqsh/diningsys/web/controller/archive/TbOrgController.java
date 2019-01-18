package com.yqsh.diningsys.web.controller.archive;

import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.YQSHTranslate;

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
import com.yqsh.diningsys.web.service.archive.DgPublicCode0Service;
import com.yqsh.diningsys.web.service.archive.TbOrgService;

/**
 * 机构管理控制器
 *
 * @author xiewei
 * @version 创建时间：2016年9月29日 上午10:36:12
 */
@Controller
@RequestMapping(value = "/archive/org")
public class TbOrgController extends BaseController {

    @Autowired
    private TbOrgService tbOrgService;
    @Autowired
    private DgPublicCode0Service dgPublicCode0Service;
    
    @RequestMapping("/index")
    public ModelAndView index(Model model) {
    	//查询字典数据
    	model.addAttribute("list",dgPublicCode0Service.getAllDpcToPage());
        ModelAndView modelAndView = new ModelAndView("archive/org/org-index");
        return modelAndView;
    }

    /**
     * 分页查询机构信息
     *
     * @param request
     * @param response
     * @param tbOrg
     * @return
     */
    @RequestMapping(value = "/getPageList")
    @ResponseBody
    public Object getPageList(HttpServletRequest request, HttpServletResponse response, TbOrg tbOrg) {
        com.yqsh.diningsys.core.util.Page<TbOrg> pagebean = null;
        try {
            pagebean = tbOrgService.getPageList(tbOrg);
            pagebean.setTotal(pagebean.getContext().getPageCount());
            pagebean.setPage(tbOrg.getPage());
            pagebean.setRecords(pagebean.getContext().getTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }

    /**
     * 通过机构ID查询机构信息
     *
     * @param request
     * @param response
     * @param tbOrg
     * @return
     */
    @RequestMapping(value = "/getOrgById")
    @ResponseBody
    public Object getorgById(HttpServletRequest request, HttpServletResponse response, TbOrg tbOrg) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            TbOrg org = tbOrgService.getOrgByID(tbOrg);
            result.put("org", org);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "error");
        }
        return result;
    }

    /**
     * 新增、修改机构信息保存
     *
     * @param request
     * @param response
     * @param tbOrg
     * @return
     */
    @RequestMapping(value = "/saveOrg")
    @ResponseBody
    public Object saveorg(HttpServletRequest request, HttpServletResponse response, TbOrg tbOrg) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String cKeyWD = tbOrg.getOrgSjm();
            if (StringUtil.isBlank(cKeyWD)) {
                tbOrg.setOrgSjm(YQSHTranslate.getPYIndexStr(tbOrg.getOrgName(), true));
            }
            tbOrgService.insertOrUpdOrg(tbOrg);
            result.put("success", "OK");
        } catch (Exception e) {
            result.put("success", "false");
            result.put("error", e.getMessage());
        }
        return result;
    }

    /**
     * 机构信息回收站
     *
     * @param request
     * @param response
     * @param tbOrg
     * @return
     */
    @RequestMapping(value = "/deleteOrgTrash")
    @ResponseBody
    public Object deleteorgTrash(HttpServletRequest request, HttpServletResponse response, TbOrg tbOrg) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<String> list = new ArrayList<String>();
            for (String str : tbOrg.getEditIds().split(",")) {
                list.add(str);
            }
            tbOrg.setIds(list);
            tbOrgService.deleteTrash(tbOrg);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
        }
        return result;
    }

    /**
     * 还原回收站机构信息
     *
     * @param request
     * @param response
     * @param tbOrg
     * @return
     */
    @RequestMapping(value = "/replyOrg")
    @ResponseBody
    public Object replyorg(HttpServletRequest request, HttpServletResponse response, TbOrg tbOrg) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<String> list = new ArrayList<String>();
            for (String str : tbOrg.getEditIds().split(",")) {
                list.add(str);
            }
            tbOrg.setIds(list);
            tbOrgService.replyOrg(tbOrg);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
        }
        return result;
    }

    /**
     * 删除机构信息
     *
     * @param request
     * @param response
     * @param tbOrg
     * @return
     */
    @RequestMapping(value = "/deleteOrg")
    @ResponseBody
    public Object deleteOrg(HttpServletRequest request,
            HttpServletResponse response, TbOrg tbOrg) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<String> list = new ArrayList<String>();
            for (String str : tbOrg.getEditIds().split(",")) {
                list.add(str);
            }
            tbOrg.setIds(list);
            tbOrgService.deleteByIds(tbOrg);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
        }
        return result;
    }

    /**
     * 导出机构信息Excel
     *
     * @param request
     * @param response
     * @param model
     * @param tbOrg
     * @return
     */
    @RequestMapping(value = "exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response, Model model, TbOrg tbOrg) {
        List<TbOrg> orgList = tbOrgService.getAllList(tbOrg);
        model.addAttribute("orgList", orgList);
        ModelAndView modelAndView = new ModelAndView("archive/org/exportXls");
        return modelAndView;
    }
}
