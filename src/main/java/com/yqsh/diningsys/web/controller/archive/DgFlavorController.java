/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yqsh.diningsys.web.controller.archive;

import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.YQSHTranslate;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.archive.DgFlavor;
import com.yqsh.diningsys.web.model.archive.DgProMethods;
import com.yqsh.diningsys.web.model.archive.DgProMethodsType;
import com.yqsh.diningsys.web.service.archive.DgFlavorService;
import com.yqsh.diningsys.web.service.sysSettings.AutoSeqService;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author yqsh-zc
 */
@Controller
@RequestMapping("/archive/flavor")
public class DgFlavorController extends BaseController{
    @Autowired
    private AutoSeqService seqService;
    @Autowired
    private DgFlavorService flavorService;
    
    @RequestMapping("/index")
    public String index() {
        return "archive/flavor/flavor_index";
    }
    
    @RequestMapping(value = "/getTreeNodes", method = RequestMethod.POST)
    @ResponseBody
    public List<DgFlavor> getTreeNodes() {
        List<DgFlavor> dgFlavors = flavorService.getTreeBean();
        return dgFlavors;
    }
    
    
    @RequestMapping(value = "/getFlavorByParentId")
    @ResponseBody
    public List<DgFlavor> getFlavorByParentId(Integer id,Integer pid) {
        if(pid == null || pid == -1){
            List<DgFlavor> dgFlavors = flavorService.getAllBeans("1");
            return dgFlavors;
        }else{
             List<DgFlavor> dgFlavors = flavorService.getFlavorByParentId(id);
            return dgFlavors;
        }
    }
    
    @RequestMapping("/toAddFlavor")
    public String toAddFlavor(Integer id,Integer pid, HttpServletRequest request) {
        try{
            if (id != null) {
                DgFlavor flavor = flavorService.selectByPrimaryKey(id);
                request.setAttribute("flavor", flavor);
            }else{
                String currentNum = seqService.getSeq("DgFlavor", 3, 0, "", 0, "");
                DgFlavor flavor = new DgFlavor();
                flavor.setNumber(currentNum);
                flavor.setParentid(pid);
                request.setAttribute("flavor", flavor);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "/archive/flavor/flavor_edit";
    }
    
    @RequestMapping("/saveFlavor")
    @ResponseBody
    public Object saveFlavor(DgFlavor flavor) {
        try {
                String cKeyWD = flavor.getZjf();
                if(StringUtil.isBlank(cKeyWD)){
                    flavor.setZjf(YQSHTranslate.getPYIndexStr(flavor.getName(), true));
                }
                DgFlavor dbFlavor = flavorService.getFlavorByNumber(flavor.getNumber());
                if(dbFlavor != null){
                    if(flavor.getId() != null && Objects.equals(flavor.getId(), dbFlavor.getId())){
                        seqService.setUsedSeq("DgFlavor", flavor.getNumber());
                        flavorService.insertOrUpdateBeans(flavor);
                    }else{
                         return returnFail("编号重复");
                    }
                }else{
                    seqService.setUsedSeq("DgFlavor", flavor.getNumber());
                    flavorService.insertOrUpdateBeans(flavor);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnSuccess();
    }
    
    
     @RequestMapping("/deleteFlavor")
    @ResponseBody
    public ResultInfo deleteFlavor(Integer id) {
        try {
            List<DgFlavor> childFlavors = flavorService.getFlavorByParentId(id);
            if(childFlavors != null && childFlavors.size() > 0){
                return returnFail("有子菜单的情况下不能删除,请逐级删除.");
            }
            flavorService.deleteByPrimaryKey(id);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }
}
