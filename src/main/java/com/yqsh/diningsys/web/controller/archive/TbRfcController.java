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
import com.yqsh.diningsys.web.model.archive.TbRfc;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.archive.TbRfct;
import com.yqsh.diningsys.web.service.archive.TbRfcService;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.archive.TbRfctService;
import com.yqsh.diningsys.web.service.sysSettings.AutoSeqService;

/**
 * 退菜原因控制器
 *
 * @author xiewei
 * @version 创建时间：2016年9月29日 上午10:36:12
 */
@Controller
@RequestMapping(value = "/archive/rfc")
public class TbRfcController extends BaseController {

    @Autowired
    private TbRfcService tbRfcService;
    @Autowired
    private TbRfctService tbRfctService;
    @Autowired
    private TbOrgService tbOrgService;
    @Autowired
    private AutoSeqService seqService;

    @RequestMapping("/index")
    public ModelAndView index(Model model, TbOrg tbOrg, TbRfc tbRfc, TbRfct tbRfct) {
        model.addAttribute("listOrg", tbOrgService.getAllList(tbOrg)); //查询机构组织数据
        //查询退菜原因数据
        tbRfc.setIsDel("0");
        model.addAttribute("listRfc", tbRfcService.getAllList(tbRfc));
        //查询退菜原因类型数据
        tbRfct.setIsDel("0");
        model.addAttribute("listRfct", tbRfctService.getAllList(tbRfct));
        ModelAndView modelAndView = new ModelAndView("archive/rfc/rfc-index");
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
        ModelAndView modelAndView = new ModelAndView("archive/rfc/rfc-trash");
        return modelAndView;
    }

    /**
     * 分页查询退菜原因类型信息
     *
     * @param request
     * @param response
     * @param tbRfc
     * @return
     */
    @RequestMapping(value = "/getPageList")
    @ResponseBody
    public Object getPageList(HttpServletRequest request, HttpServletResponse response, TbRfc tbRfc) {
        com.yqsh.diningsys.core.util.Page<TbRfc> pagebean = null;
        try {
            pagebean = tbRfcService.getPageList(tbRfc);
            pagebean.setTotal(pagebean.getContext().getPageCount());
            pagebean.setPage(tbRfc.getPage());
            pagebean.setRecords(pagebean.getContext().getTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }

    /**
     * 通过退菜原因类型ID查询退菜原因类型信息
     *
     * @param request
     * @param response
     * @param tbRfc
     * @return
     */
    @RequestMapping(value = "/getRfcById")
    @ResponseBody
    public Object getRfcById(HttpServletRequest request, HttpServletResponse response, TbRfc tbRfc) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            tbRfc.setIsDel("0");//查询未被删除的退菜原因类型信息
            TbRfc rfc = tbRfcService.getRfcById(tbRfc);
            result.put("rfc", rfc);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "error");
        }
        return result;
    }

    /**
     * 新增、修改退菜原因类型信息保存
     *
     * @param request
     * @param response
     * @param tbRfc
     * @return
     */
    @RequestMapping(value = "/saveRfc")
    @ResponseBody
    public Object saveRfc(HttpServletRequest request, HttpServletResponse response, TbRfc tbRfc) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            
            String zjf = tbRfc.getRfcZjf();
            if(StringUtil.isBlank(zjf)){
                tbRfc.setRfcZjf(YQSHTranslate.getPYIndexStr(tbRfc.getRfcName(), true));
            }
            TbRfc dbRfc = tbRfcService.getTbRfcByNumber(tbRfc.getRfcCode());
            if (dbRfc != null) {
                if (tbRfc.getId() != null && tbRfc.getId() == dbRfc.getId()) {
                    tbRfcService.insertOrUpdRfc(tbRfc);
                    seqService.setUsedSeq("rfc", tbRfc.getRfcCode());
                    result.put("success", "OK");
                } else {
                    result.put("success", "false");
                    result.put("error", "编号重复");
                }
            } else {
                tbRfcService.insertOrUpdRfc(tbRfc);
                seqService.setUsedSeq("rfc", tbRfc.getRfcCode());
                result.put("success", "OK");
            }
        } catch (Exception e) {
            result.put("success", "false");
            result.put("error", e.getMessage());
        }
        return result;
    }

    /**
     * 退菜原因类型信息回收站
     *
     * @param request
     * @param response
     * @param tbRfc
     * @return
     */
    @RequestMapping(value = "/deleteRfcTrash")
    @ResponseBody
    public Object deleteRfcTrash(HttpServletRequest request, HttpServletResponse response, TbRfc tbRfc) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<String> list = new ArrayList<String>();
            for (String str : tbRfc.getEditIds().split(",")) {
                list.add(str);
            }
            tbRfc.setIds(list);
            tbRfcService.deleteTrash(tbRfc);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
        }
        return result;
    }

    /**
     * 还原回收站退菜原因类型信息
     *
     * @param request
     * @param response
     * @param tbRfc
     * @return
     */
    @RequestMapping(value = "/replyRfc")
    @ResponseBody
    public Object replyRfc(HttpServletRequest request, HttpServletResponse response, TbRfc tbRfc) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<String> list = new ArrayList<String>();
            for (String str : tbRfc.getEditIds().split(",")) {
                list.add(str);
            }
            tbRfc.setIds(list);
            tbRfcService.replyRfc(tbRfc);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
        }
        return result;
    }

    /**
     * 删除退菜原因类型信息
     *
     * @param request
     * @param response
     * @param tbRfc
     * @return
     */
    @RequestMapping(value = "/deleteRfc")
    @ResponseBody
    public Object deleteRfc(HttpServletRequest request,
            HttpServletResponse response, TbRfc tbRfc) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<String> list = new ArrayList<String>();
            for (String str : tbRfc.getEditIds().split(",")) {
                list.add(str);
            }
            tbRfc.setIds(list);
            tbRfcService.deleteByIds(tbRfc);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
        }
        return result;
    }

    /**
     * 导出退菜原因类型信息Excel
     *
     * @param request
     * @param response
     * @param model
     * @param tbRfc
     * @return
     */
    @RequestMapping(value = "exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response, Model model, TbRfc tbRfc) {
        List<TbRfc> RfcList = tbRfcService.getAllList(tbRfc);
        model.addAttribute("rfcList", RfcList);
        ModelAndView modelAndView = new ModelAndView("archive/rfc/exportXls");
        return modelAndView;
    }
}
