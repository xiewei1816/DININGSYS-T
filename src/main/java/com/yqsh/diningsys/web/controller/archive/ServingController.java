package com.yqsh.diningsys.web.controller.archive;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.YQSHTranslate;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgServing;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.service.archive.DgServingService;
import com.yqsh.diningsys.web.service.archive.TbOrgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 上菜状态
 *
 * @author zhshuo create on 2016-09-29 15:32
 */
@RequestMapping("/archive/serving")
@Controller
public class ServingController extends BaseController{

    @Autowired
    private DgServingService DgServing;
    @Autowired
    private TbOrgService tbOrgService;
    
    @RequestMapping("/index")
    public String index(){
        return "/archive/serving/serving_index";
    }

    @RequestMapping("/toEdit")
    public String toEdit(Model model,Integer id,TbOrg tbOrg){
    	//查询机构组织数据
    	model.addAttribute("listOrg",tbOrgService.getAllList(tbOrg));
    	if(id != null){
            DgServing dgServing = DgServing.selectByPrimaryKey(id);
            model.addAttribute("dgServing",dgServing);
        }else{
            model.addAttribute("maxCode",DgServing.selectMaxNum());
        }
        return "/archive/serving/serving_edit";
    }

    @RequestMapping(value = "/getPageList",method = RequestMethod.POST)
    @ResponseBody
    public Page<DgServing> getAllData(DgServing dgServing){
        Page<DgServing> pagebean = null;
        try {
            pagebean = DgServing.getAllData(dgServing);
            pagebean.setPage(dgServing.getPage());
            pagebean = (Page<DgServing>)pageResult(pagebean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }

    @RequestMapping(value = "/addData",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo addData(DgServing dgServing){
        try {
            String cKeyWD = dgServing.getMnemonic();
            if(StringUtil.isBlank(cKeyWD)){
                dgServing.setMnemonic(YQSHTranslate.getPYIndexStr(dgServing.getSname(), true));
            }
            DgServing.addData(dgServing);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping(value = "/editData",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editData(DgServing dgServing){
        try {
            String cKeyWD = dgServing.getMnemonic();
            if(StringUtil.isBlank(cKeyWD)){
                dgServing.setMnemonic(YQSHTranslate.getPYIndexStr(dgServing.getSname(), true));
            }
            DgServing.updateData(dgServing);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/delData")
    @ResponseBody
    public ResultInfo delData(String ids){
        try {
            DgServing.deleteData(ids);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }


}
