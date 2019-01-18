package com.yqsh.diningsys.web.controller.archive;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.YQSHTranslate;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgProMethods;
import com.yqsh.diningsys.web.model.archive.DgProMethodsType;
import com.yqsh.diningsys.web.model.archive.TbDep;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.service.archive.DgProMethodsSerivce;
import com.yqsh.diningsys.web.service.archive.DgProMethodsTypeService;
import com.yqsh.diningsys.web.service.archive.TbDepService;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.sysSettings.AutoSeqService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-10-10 13:45
 */
@Controller
@RequestMapping("/archive/proMethods")
public class DgProMethodsController extends BaseController {

    @Autowired
    private DgProMethodsSerivce dgProMethodsSerivce;

    @Autowired
    private DgProMethodsTypeService dgProMethodsTypeService;
    @Autowired
    private AutoSeqService seqService;
    @Autowired
    private TbOrgService tbOrgService;
    @Autowired
    private TbDepService tbDepService;

    @RequestMapping("/index")
    public String index() {
        return "archive/proMethods/pro_methods_index";
    }

    @RequestMapping(value = "/getAllTypeData", method = RequestMethod.POST)
    @ResponseBody
    public List<DgProMethodsType> getAllTypeData() {
        List<DgProMethodsType> dgProMethodsTypes = dgProMethodsTypeService.selelctAllData();
        DgProMethodsType dgProMethodsType = new DgProMethodsType();
        dgProMethodsType.setId(0);
        dgProMethodsType.setPmtname("制作方法类别");
        dgProMethodsTypes.add(0, dgProMethodsType);
        return dgProMethodsTypes;
    }

    @RequestMapping(value = "/getAllProMethods")
    @ResponseBody
    public Page<DgProMethods> getAllDgProMethods(DgProMethods dgProMethods) {
        Page<DgProMethods> pagebean = null;
        try {
            pagebean = dgProMethodsSerivce.selelctAllData(dgProMethods);
            pagebean.setPage(dgProMethods.getPage());
            pagebean = (Page<DgProMethods>) pageResult(pagebean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }

    @RequestMapping("/DgProMethodsType")
    @ResponseBody
    public Page<DgProMethodsType> getAllDgProMethodsType(DgProMethodsType dgProMethodsType) {
        Page<DgProMethodsType> pagebean = null;
        try {
            pagebean = dgProMethodsTypeService.selectAllDataPage(dgProMethodsType);
            pagebean.setPage(dgProMethodsType.getPage());
            pagebean = (Page<DgProMethodsType>) pageResult(pagebean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }

    @RequestMapping("/toProMehtodsTypeEdit")
    public String toProMehtodsTypeEdit(Model model,Integer id,TbOrg tbOrg) {
    	//查询机构组织数据
    	model.addAttribute("listOrg",tbOrgService.getAllList(tbOrg)); 
        if (id != null) {
            DgProMethodsType dgProMethodsType = dgProMethodsTypeService.selectByPrimaryKey(id);
            model.addAttribute("dgProMethodsType", dgProMethodsType);
        }
        return "/archive/proMethods/pro_methods_type_edit";
    }

    @RequestMapping("/toProMehtodsEdit")
    public String toProMehtodsEdit(Model model,Integer pmtId,Integer id,TbOrg tbOrg) {
    	//查询机构组织数据
    	model.addAttribute("listOrg",tbOrgService.getAllList(tbOrg)); 
        List<DgProMethodsType> dgProMethodsTypes = dgProMethodsTypeService.selelctAllTypes();
        if (id != null) {
            DgProMethods dgProMethods = dgProMethodsSerivce.selectByPrimaryKey(id);
            //获取部门信息
            Integer depId = dgProMethods.getDept();
            TbDep set_dep = new TbDep();
			set_dep.setId(depId);
            TbDep dep = tbDepService.getDepById(set_dep);
            model.addAttribute("dep", dep);
            model.addAttribute("dgProMethods", dgProMethods);
        } else {
            String currentNum = seqService.getSeq("ProMethodsType", 3, 0, "", 0, "");
            DgProMethods dgProMethods = new DgProMethods();
            dgProMethods.setPmcode(currentNum);
            dgProMethods.setPmtid(pmtId);
            //获取部门信息
            Integer depId = dgProMethods.getDept();
            TbDep set_dep = new TbDep();
			set_dep.setId(depId);
            TbDep dep = tbDepService.getDepById(set_dep);
            model.addAttribute("dep", dep);
            model.addAttribute("dgProMethods", dgProMethods);
        }
        model.addAttribute("dgProMethodsTypes", dgProMethodsTypes);
        return "/archive/proMethods/pro_methods_edit";
    }

    @RequestMapping("/addProMethods")
    @ResponseBody
    public ResultInfo addProMethods(DgProMethods dgProMethods) {
        try {
            
            DgProMethods dbProMethod = dgProMethodsSerivce.getProMethodByNumber(dgProMethods.getPmcode());
            if (dbProMethod != null) {
                return returnFail("编码重复");
            } else {
                String cKeyWD = dgProMethods.getPmmnemonic();
                if(StringUtil.isBlank(cKeyWD)){
                    dgProMethods.setPmmnemonic(YQSHTranslate.getPYIndexStr(dgProMethods.getPmname(), true));
                }
                dgProMethods.setCreateTime(new Date());
                seqService.setUsedSeq("ProMethodsType", dgProMethods.getPmcode());
                dgProMethodsSerivce.insertSelective(dgProMethods);
                return returnSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/updateProMethods")
    @ResponseBody
    public ResultInfo updateProMethods(DgProMethods dgProMethods) {
        try {
            DgProMethods dbProMethod = dgProMethodsSerivce.getProMethodByNumber(dgProMethods.getPmcode());
            if (dbProMethod != null && dbProMethod.getId() == dgProMethods.getId()) {
                String cKeyWD = dgProMethods.getPmmnemonic();
                if(StringUtil.isBlank(cKeyWD)){
                    dgProMethods.setPmmnemonic(YQSHTranslate.getPYIndexStr(dgProMethods.getPmname(), true));
                }
                dgProMethodsSerivce.updateByPrimaryKey(dgProMethods);
                seqService.setUsedSeq("ProMethodsType", dgProMethods.getPmcode());
                return returnSuccess();
            } else {
                return returnFail("编码重复");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/addProMethodsWithSpecial")
    @ResponseBody
    public ResultInfo addProMethodsWithSpecial(DgProMethods dgProMethods) {
        try {
            dgProMethods.setCreateTime(new Date());
            dgProMethods.setIsSpecialMethods(1);
            dgProMethodsSerivce.insertSelective(dgProMethods);

            //品项档案功能中 专用制作方法添加
            DgProMethods addSpecilMethods = dgProMethodsSerivce.selectByPrimaryKey(dgProMethods.getId());
            JSONArray json = new JSONArray();
            json.add(addSpecilMethods);
            return returnSuccess(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/addProMethodsType")
    @ResponseBody
    public ResultInfo addProMethodsType(DgProMethodsType dgProMethodsType) {
        dgProMethodsType.setCreateTime(new Date());
        try {
            dgProMethodsTypeService.insertSelective(dgProMethodsType);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/editProMethodsType")
    @ResponseBody
    public ResultInfo editProMethodsType(DgProMethodsType dgProMethodsType) {
        try {
            dgProMethodsTypeService.updateByPrimaryKeySelective(dgProMethodsType);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }
    
    @RequestMapping("/deleteByPrimaryKey")
    @ResponseBody
    public ResultInfo deleteByPrimaryKey(int id) {
        try {
        	dgProMethodsTypeService.deleteByPrimaryKey(id);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/deleteProMethods")
    @ResponseBody
    public ResultInfo deleteProMethods(String ids) {
        try {
            dgProMethodsSerivce.deleteData(ids);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/editProMethods")
    @ResponseBody
    public ResultInfo editProMethods(DgProMethods dgProMethods) {
        try {
            int res = dgProMethodsSerivce.updateByPrimaryKeySelective(dgProMethods);
            JSONArray json = new JSONArray();
            if (res == 1) {
                json.add(dgProMethods);
            }
            return returnSuccess(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }
}