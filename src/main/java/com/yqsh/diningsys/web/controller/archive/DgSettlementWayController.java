package com.yqsh.diningsys.web.controller.archive;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.archive.*;
import com.yqsh.diningsys.web.service.archive.*;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.deskBusiness.DgProductPhaseLeftmenu;

@Controller
@RequestMapping(value = "/settlementWay")
public class DgSettlementWayController extends BaseController {

	@Autowired
    private DgSettlementWayTypeService settlementWayTypeService;
	
	@Autowired
    private DgSettlementWayService settlementWayService;
	
    @Autowired
    private DgPublicCode0Service dgPublicCode0Service;

    @Autowired
    private DgItemFileTypeService dgItemFileTypeService;

    @Autowired
    private DgItemFileService dgItemFileService;

	@RequestMapping("/index")
    public ModelAndView index(Model model){
        ModelAndView modelAndView = new ModelAndView("archive/settlementWay/settlementWay-index");
//      modelAndView.addObject("types", settlementWayTypeService.getAllList(new DgSettlementWayType()));
        //查询字典数据
    	model.addAttribute("list",dgPublicCode0Service.getAllDpcToPage());
        return modelAndView;
    }
	
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,DgSettlementWay settlementWay) {
		
    	com.yqsh.diningsys.core.util.Page<DgSettlementWay> pagebean = null;
		try {
			pagebean = settlementWayService.getPageList(settlementWay);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(settlementWay.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}
	
	@RequestMapping(value = "/getAllList")
	@ResponseBody
	public Object getAllList(HttpServletRequest request,HttpServletResponse response
							,DgSettlementWay settlementWay) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<DgSettlementWay> list = settlementWayService.getAllList(settlementWay);
			result.put("list", list);
			result.put("success", "OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "error");
		}
		return result;
	}
	
	@RequestMapping(value = "/getTreeList")
	@ResponseBody
	public Object getTreeList(HttpServletRequest request,HttpServletResponse response
							,DgSettlementWay settlementWay) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		List<DgSettlementWayType> typeList = settlementWayTypeService.getAllList(new DgSettlementWayType());
		for(DgSettlementWayType types : typeList){
			Map<String,Object> treedom = new HashMap<String,Object>();
			treedom.put("id", types.getId());
			treedom.put("pid", 0);
			treedom.put("name", types.getName());
			result.add(treedom);
		}
		return result;
	}
	
	@RequestMapping(value = "/getSettlementWayByID")
	@ResponseBody
	public Object getSettlementWayByID(HttpServletRequest request,HttpServletResponse response
			,DgSettlementWay settlementWay) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			DgSettlementWay dgSettlementWay = settlementWayService.getDgSettlementWayByID(settlementWay);
			result.put("settlementWay", dgSettlementWay);
			result.put("success", "OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "error");
		}
		return result;
	}
    
    @RequestMapping(value = "/saveSettlementWay")
	@ResponseBody
	public Object saveSettlementWay(HttpServletRequest request,
			HttpServletResponse response,DgSettlementWay settlementWay) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			settlementWay.setCreateUserid(getCurrentUser().getId() + "");
			settlementWayService.insertOrUpd(settlementWay);
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}
    
    @RequestMapping(value = "/deleteSettlementWay")
	@ResponseBody
	public Object deleteSettlementWay(HttpServletRequest request,
			HttpServletResponse response,DgSettlementWay settlementWay) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			List<String> list = new ArrayList<String>();
			for(String str : settlementWay.getEditIds().split(",")){
				list.add(str);
			}
			settlementWay.setIds(list);
			settlementWayService.deleteByIds(settlementWay);
			result.put("success","OK");
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", "false");
		}
		return result;
	}
    
    @RequestMapping(value = "/checkSettlementWayByName")
	@ResponseBody
	public Object checkSettlementWayByName(HttpServletRequest request,
			HttpServletResponse response,DgSettlementWay settlementWay) {
		Map<String,Object> result = new HashMap<String,Object>();
		int stat = 0;
		List<DgSettlementWay> checkList = settlementWayService.getAllList(settlementWay);
		if(settlementWay.getId() != null && settlementWay.getId() > 0){
			stat = checkList != null && checkList.size() != 0 ?
			checkList.get(0).getId() != settlementWay.getId() ? 2 : 1 : 1;
		}else{
			stat = checkList != null && checkList.size() != 0 ? 2 : 1;
		}
		result.put("state", stat);
		return result;
	}
    
    
    /**
     * 
     * 左侧树菜单
     * @return
     */
    @RequestMapping("/getTreeNodes")
    @ResponseBody
    public  List<DgProductPhaseLeftmenu> getTreeNodes()
    {
    	String id = getRequest().getParameter("id");
    	String childCount = getRequest().getParameter("childCount");
    	List<DgProductPhaseLeftmenu> menu = new ArrayList<DgProductPhaseLeftmenu>();
    	
    	//获取根节点
    	if(StringUtils.isEmpty(id))
    	{
    		DgProductPhaseLeftmenu m = new DgProductPhaseLeftmenu();
    		m.setId(-1);
    		m.setName("结算方式类型");
    		m.setChildCount(1); //大于0即可
    		m.setParentId(0);
    		menu.add(m);
    	}
    	else if(id.equals("-1"))
    	{
    			Map<String,Object> map = new HashMap<String,Object>();
    			map.put("cParent",32);
    			map.put("iDelFlg",0);
    			List<DgSettlementWayType> typeList = settlementWayTypeService.getAllList(new DgSettlementWayType());
    			
    			for(DgSettlementWayType a:typeList)
    			{
    				DgProductPhaseLeftmenu m = new DgProductPhaseLeftmenu();
    	    		m.setId(a.getId());
    	    		m.setName(a.getName());
    	    		m.setChildCount(1); //大于0即可
    	    		m.setParentId(-1);
    	    		menu.add(m);
    			}
    	}
    	return menu;
    }
    /**
     * 
     * 增加/删除后刷新select选项框
     * @return
     */
    @RequestMapping("/getAllType")
    @ResponseBody
    public  List<DgSettlementWayType> getAllType()
    {
		List<DgSettlementWayType> typeList = settlementWayTypeService.getAllList(new DgSettlementWayType());
    	return typeList;
    }

    /**
     * 跳转至结算方式排序
     * @param model
     * @return
     */
    @RequestMapping("/goRank")
    public ModelAndView trash(Model model){
        ModelAndView modelAndView = new ModelAndView("archive/settlementWay/settlementWay-rank");
        return modelAndView;
    }

    /**
     *
     * 获取结算方式排序数据
     * @return
     */
    @RequestMapping("/getSettleMentWayRankList")
    @ResponseBody
    public List getSettleMentWayRankList()
    {
        List<DgSettlementWay> settleMentWayRankList = settlementWayService.getSettleMentWayRankList();
        //初始化结算方式rank值
        List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = null;
        for (int i = 0; i < settleMentWayRankList.size(); i++) {
            map = new HashMap<>();
            DgSettlementWay dgSettlementWay = settleMentWayRankList.get(i);
            if(dgSettlementWay != null){
                map.put("id", dgSettlementWay.getId());
                map.put("number", dgSettlementWay.getNumber());
                map.put("name", dgSettlementWay.getName());
                map.put("rank", i+1);
                listMap.add(map);
                //初始化结算方式排序表
                Map<String,Object> settlementWayRankMap = settlementWayService.selSettlementWayRank(map);
                if(settlementWayRankMap == null){
                    settlementWayService.addSettlementWayRank(map);
                }
            }
        }
        return listMap;
    }

    //2017年10月17日11:53:43  和券相关的新增代码

    @RequestMapping("/wayItemFileType")
    public String wayItemFileType(Integer id,Integer type,Model model){
		DgSettlementWay dgSettlementWay = settlementWayService.selectDataById(id);
        model.addAttribute("id",id);
        model.addAttribute("type",type);
        model.addAttribute("itemIds",dgSettlementWay.getItemIds());
        if(type == 2){//大类页面
            return "archive/settlementWay/settlementWay-itemFileType";
        }else if(type == 3){//小类页面
			return "archive/settlementWay/settlementWay-itemFileSmallType";
		}else{//具体品项页面
			return "archive/settlementWay/settlementWay-itemFile";
		}
    }

    /**
     * 获取品项类别
     * @param flag 0：查询全部，1：查询已选，2：查询未选
     * @return
     */
    @RequestMapping("/selectAllItemFileType")
    public @ResponseBody List<DgItemFileType> selectAllItemFileType(Integer parentId,Integer type, Integer flag,String ids){
		try {
			//type 为1 的为无限制
			if(type == 2){//大类
				if(flag == 0){ //查询全部
					return dgItemFileTypeService.selectAllItemFileBigType();
				}else if(flag == 1){//查询已选
					return dgItemFileTypeService.selectWayItemYxBigType(ids);
				}else{//查询未选
					return dgItemFileTypeService.selectWayItemWxBigType(ids);
				}
			}else if(type == 3){//小类
				if(flag == 0){ //查询全部
					List<DgItemFileType> dgItemFileTypes = dgItemFileTypeService.selectAllItemFileType();
					DgItemFileType dgItemFileType = new DgItemFileType();
					dgItemFileType.setId(0);
					dgItemFileType.setName("全部");
					dgItemFileTypes.add(0,dgItemFileType);
					return dgItemFileTypes;
				}else if(flag == 1){//查询已选
					return dgItemFileTypeService.selectWayItemYxSmallType(ids);
				}else{//查询未选
					return dgItemFileTypeService.selectWayItemWxSmallType(parentId,ids);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取品项
	 * @param parentId 大类或者小类ID
	 * @param type 1：大类，2：小类
	 * @param flag 1：查询已选，2：查询未选
	 * @param ids
	 * @return
	 */
	@RequestMapping("/selectAllItemFile")
	public @ResponseBody List<DgItemFile> selectAllItemFile(Integer parentId,Integer type, Integer flag,String ids){
		try {
			if(flag == 1){//查询已选
                return dgItemFileService.selectItemFileInIdsTicket(ids);
            }else{//查询未选
                return dgItemFileService.selectItemFileNotInIds(parentId,type,ids);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    @ResponseBody
    @RequestMapping("/upateWayItemSet")
    public ResultInfo upateWayItemSet(Integer wayId, String ids){
        try {
            settlementWayService.upateWayItemSet(wayId,ids);
        } catch (Exception e) {
            e.printStackTrace();
            return returnFail();
        }
        return returnSuccess();
    }

    /**
     * 结算方式排序管理
     * @param id1 被移动的结算方式id
     * @param id2 被动移动的结算方式id
     * @param moveType 移动类型 0-上移；1-下移；2-置顶
     * @return
     */
    @RequestMapping(value = "/doRank")
    @ResponseBody
    public Object doRank(Integer id1,Integer id2,Integer moveType) {
        Map<String,Object> result = new HashMap<String,Object>();
        try{
        	// 向上移动
			if(moveType == 0){ 
				settlementWayService.updateSettlementWayRankMoveUp(id1);
				settlementWayService.updateSettlementWayRankMoveDown(id2);
				result.put("success", true);
			}
			// 向下移动
			if(moveType == 1){ 
				settlementWayService.updateSettlementWayRankMoveDown(id1);
				settlementWayService.updateSettlementWayRankMoveUp(id2);
				result.put("success", true);
			}
			// 置顶
			if(moveType == 2){ 
				List<String> ids = settlementWayService.selectSettlementWayRankMoveTopper(id1);
				if(ids.size() > 0){
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("id", id1);
					params.put("rank", 1);
					settlementWayService.updateSettlementWayRankMoveTopper(ids);
					settlementWayService.updateSettlementWayRank(params);
					result.put("success", true);
				}
			}
            result.put("success", true);
        }catch(Exception e){
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        return result;
    }
}
