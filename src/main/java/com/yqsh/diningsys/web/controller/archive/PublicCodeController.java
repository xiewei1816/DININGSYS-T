package com.yqsh.diningsys.web.controller.archive;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgPublicCode;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.service.archive.PublicCodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 公共代码
 *
 * @author zhshuo create on 2016-09-28 11:04
 */
@Controller
@RequestMapping("/archive/publicCode")
public class PublicCodeController extends BaseController{

    @Autowired
    private PublicCodeService publicCodeService;

    @RequestMapping("/index")
    public String index(){
        return "archive/publicCode/publicCode_index";
    }

    @RequestMapping("/addPublicCode")
    public ResultInfo addPublicCode(DgPublicCode dgPublicCode){
        try {
            publicCodeService.addData(dgPublicCode);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }
    
    @ResponseBody
    @RequestMapping("/savePublicCode")
    public ResultInfo savePublicCode(DgPublicCode dgPublicCode){
        try {
        	if(dgPublicCode.getCgpcid() == null || dgPublicCode.getCgpcid().equals("")){
        		DgPublicCode ids = new DgPublicCode();
        		ids.setCparent("33");
        		List<DgPublicCode> list = publicCodeService.findListData(ids);
        		int id = 330000;
        		for(DgPublicCode idf : list){
        			Integer cgid = Integer.parseInt(idf.getCgpcid());
        			id = cgid > id ? cgid : id;
        		}
        		dgPublicCode.setCgpcid((id+1) + "");
        		 publicCodeService.addData(dgPublicCode);
        	}else{
        		 publicCodeService.updateByPrimaryKeySelective(dgPublicCode);
        	}
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/editPublicCode")
    public ResultInfo editPublicCode(DgPublicCode dgPublicCode){ 
        try {
            publicCodeService.updateByPrimaryKeySelective(dgPublicCode);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @ResponseBody
    @RequestMapping("/deleteDataWithLogic")
    public ResultInfo deleteDataWithLogic(String id){
        try {
            publicCodeService.deleteDataWithLogic(id);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/deleteData")
    public ResultInfo deleteData(String id){
        try {
            publicCodeService.deleteData(id);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/getPublicCodeById/{id}")
    public List<DgPublicCode> getPublicCodeById(@PathVariable String id){
        return publicCodeService.selectDataById(id);
    }
    
    @ResponseBody
    @RequestMapping("/getPublicCodeByIds")
    public List<DgPublicCode> getPublicCodeByIds(HttpServletRequest request,HttpServletResponse response
    		,DgPublicCode dgPublicCode){
    	List<DgPublicCode> list = publicCodeService.findListData(dgPublicCode);
        return list;
    }

    @RequestMapping("/getAllDataTree")
    @ResponseBody
    public List<DgPublicCode> getAllDataTree(){
        return publicCodeService.selectAllData();
    }
    @RequestMapping("/getAllDataTreeByPar")
    @ResponseBody
    public List<DgPublicCode> getAllDataTreeByPar(HttpServletRequest request,HttpServletResponse response
    		,DgPublicCode dgPublicCode){
        return publicCodeService.findListData(dgPublicCode);
    }

    @RequestMapping(value = "/getPageList",method = RequestMethod.POST)
    @ResponseBody
    public Page<DgPublicCode> getPageList(DgPublicCode dgPublicCode) {
        Page<DgPublicCode> pagebean = null;
        try {
            pagebean = publicCodeService.getPageList(dgPublicCode);
            pagebean.setPage(dgPublicCode.getPage());
            pagebean = (Page<DgPublicCode>)pageResult(pagebean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }

}
