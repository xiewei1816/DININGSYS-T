package com.yqsh.diningsys.web.controller.businessMan;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.businessMan.DgItemShowRank;
import com.yqsh.diningsys.web.model.businessMan.DgItemTimeSet;
import com.yqsh.diningsys.web.service.archive.DgItemFileTypeService;
import com.yqsh.diningsys.web.service.businessMan.DgItemShowRankService;
import com.yqsh.diningsys.web.service.businessMan.DgItemTimeSetService;


/**
 * 消费品项显示设置控制器
 * @author xiewei
 * @version 创建时间：2016年10月29日 上午10:36:12
 */
@Controller
@RequestMapping(value = "/businessMan/itemShowRank")
public class DgItemShowRankController extends BaseController{
	
    @Autowired
    private DgItemShowRankService dgItemShowRankService;
    @Autowired
    private DgItemTimeSetService dgItemTimeSetService;
    @Autowired
    private DgItemFileTypeService dgItemFileTypeService;
	
    @RequestMapping("/index")
    public ModelAndView index(Model model){
    	//查询消费品项显示设置数据
    	List<DgItemTimeSet> list = dgItemTimeSetService.getAllList();
    	if(list.size() > 0){
    		DgItemTimeSet dgItemTimeSet = list.get(0);
    		model.addAttribute("dgItemTimeSet",dgItemTimeSet); 
    	}
        ModelAndView modelAndView = new ModelAndView("archive/cgds/cgds-index");
        return modelAndView;
    }
    
    /**
     * 通过消费品项显示设置ID查询消费品项显示设置信息
     * @param DgItemShowRank
     * @return
     */
    @RequestMapping(value = "/getDgItemShowRankById")
   	@ResponseBody
   	public Object getDgItemShowRankById(DgItemShowRank dgItemShowRank) {
   		Map<String,Object> result = new HashMap<String,Object>();
   		try{
   			DgItemShowRank fydj = dgItemShowRankService.getDgItemShowRankById(dgItemShowRank);
   			result.put("fydj", fydj);
   			result.put("success", "OK");
   		}catch(Exception e){
   			e.printStackTrace();
   			result.put("success", "error");
   		}
   		return result;
   	}
    
    /**
     * 新增、修改消费品项显示设置信息保存
     * @param dgItemShowRank
     * @return
     */
    @RequestMapping(value = "/saveDgItemShowRank")
	@ResponseBody
	public Object saveDgItemShowRank(String ids,String pxlx,String isShow,String isRank) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	DgItemShowRank dgItemShowRank = null;
    	try{
	    	for (int i = 0; i < ids.split(",").length; i++) {
				Integer id = Integer.parseInt(ids.split(",")[i]);
				//查询品项设置ID
		    	DgItemShowRank setShowRank = new DgItemShowRank();
		    	setShowRank.setPxId(id);
		    	setShowRank.setPxlx(pxlx);
		    	DgItemShowRank getShowRank = dgItemShowRankService.getDgItemShowRankByPxId(setShowRank);
		    	if(getShowRank != null){
		    		dgItemShowRank = new DgItemShowRank();
		    		dgItemShowRank.setId(getShowRank.getId());
			    	dgItemShowRank.setPxId(id);
			    	dgItemShowRank.setPxlx(pxlx);
			    	if(isShow != null){
			    		dgItemShowRank.setIsShow(isShow);	
			    		dgItemShowRank.setIsRank(getShowRank.getIsRank());
			    	}
			    	if(isRank != null){
			    		dgItemShowRank.setIsRank(isRank);
			    		dgItemShowRank.setIsShow(getShowRank.getIsShow());
			    	}
			    	dgItemShowRankService.insertOrUpdDgItemShowRank(dgItemShowRank);
		    	}else{
		    		dgItemShowRank = new DgItemShowRank();
		    		dgItemShowRank.setPxId(id);
		    		dgItemShowRank.setRank(id);
			    	dgItemShowRank.setPxlx(pxlx);
			    	if(isShow != null && isShow != ""){
			    		dgItemShowRank.setIsShow(isShow);	
			    	}else{
			    		dgItemShowRank.setIsShow("0");
			    	}
			    	if(isRank != null && isShow != ""){
			    		dgItemShowRank.setIsRank(isRank);
			    	}else{
			    		dgItemShowRank.setIsRank("0");
			    	}
			    	dgItemShowRankService.insertOrUpdDgItemShowRank(dgItemShowRank);
		    	}
	    	}
    		result.put("success", "OK");
    	}catch(Exception e){
    		result.put("success", "false");
    		result.put("error", e.getMessage());
    	}
		return result;
	}

    /**
     * 查询品项 "显示设置"信息
     * @param id
     * @param pxlx
     * @param isShow
     * @param isRank
     * @param param
     * @return
     */
    @RequestMapping("/selectDgItemFileList")
    @ResponseBody
    public List<DgItemFile> selectDgItemFileList(Integer id,String pxlx, String isShow,String isRank,String param){
    	
    	Map<String,Object> params = new HashMap<String, Object>();
        params.put("id",id);
        params.put("pxlx",pxlx);
        params.put("isShow",isShow);
        params.put("isRank",isRank);
        params.put("param", param);
        return dgItemShowRankService.selectDgItemFileList(params);
    }
    
    /**
     * 查询品项 "显示设置"信息 显示或排行
     * @param isShow
     * @param isRank
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("/selectDgItemFileNoShowRankList")
    @ResponseBody
    public List<DgItemFile> selectDgItemFileNoShowRankList(Integer ppxlId, String ppdlId, String pxlx, String isShow, String isRank, String param) throws UnsupportedEncodingException{
    	Map<String,Object> params = new HashMap<String, Object>();
    	params.put("ppxlId",ppxlId);
    	params.put("ppdlId",ppdlId);
        params.put("pxlx",pxlx);
        params.put("isShow",isShow);
        params.put("isRank",isRank);
        if(!StringUtils.isEmpty(param)){
        	params.put("param",new String(param.getBytes("ISO-8859-1"),"UTF-8"));
        }
        return dgItemShowRankService.selectDgItemFileNoShowRankList(params);
    }
    
    /**
     * 查询品项小类 "显示设置"信息 显示或排行
     * @param isShow
     * @param isRank
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("/selectDgSmallItemFileNoShowRankList")
    @ResponseBody
    public List<DgItemFile> selectDgSmallItemFileNoShowRankList(String id,String parentId,String isShow,String isRank,String pxlx,String param) throws UnsupportedEncodingException{
    	Map<String,Object> params = new HashMap<String, Object>();
    	params.put("id", id);
    	params.put("parentId", parentId);
        params.put("isShow",isShow);
        params.put("isRank",isRank);
        params.put("pxlx",pxlx);
        if(!StringUtils.isEmpty(param)){
        	params.put("param",new String(param.getBytes("ISO-8859-1"),"UTF-8"));
        }
        return dgItemShowRankService.selectDgSmallItemFileNoShowRankList(params);
    }
    /**
     * 查询品项小类 "显示设置"信息
     * @param id
     * @param pxlx
     * @param isShow
     * @param isRank
     * @param param
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("/selectDgItemFileSmallList")
    @ResponseBody
    public List<DgItemFileType> selectDgItemFileSmallList(Integer id,String pxlx, String isShow,String isRank,String param) throws UnsupportedEncodingException{
    	
    	Map<String,Object> params = new HashMap<String, Object>();
        params.put("id",id);
        params.put("pxlx",pxlx);
        params.put("isShow",isShow);
        params.put("isRank",isRank);
        if(!StringUtils.isEmpty(param)){
        	params.put("param",new String(param.getBytes("ISO-8859-1"),"UTF-8"));
        }
        return dgItemShowRankService.selectDgItemFileSmallList(params);
    }
     
    /**
     * 连表查询消费品项显示设置信息
     * @param dgItemShowRank
     * @return
     */
    @RequestMapping("/selectDgItemBySearch")
    @ResponseBody
    public List<DgItemFile> selectDgItemBySearch(Integer id,String ppdlId,String ppxlId,String isShow,String isRank,String param){
    	
    	Map<String,Object> params = new HashMap<String, Object>();
        params.put("id",id);
        params.put("ppdlId",ppdlId);
        params.put("ppxlId",ppxlId);
        params.put("isShow",isShow);
        params.put("isRank",isRank);
        params.put("param", param);
        return dgItemShowRankService.selectDgItemBySearch(params);
    }
    
    /**
     * 消费品项显示设置信息-排序移动
     * 之间戳，通过选择上移，就是将本记录与上一条记录rank值交换，下移就是将本条记录与下一条记录rank值交换（，置顶就是将本记录与rank值最小的记录交换）
     * @param id
     * @param moveType 移动类型（moveType:1 = 上移； moveType:0 = 下移 ）
     * @return
     */
    @RequestMapping(value = "/dgItemShowSetRank")
	@ResponseBody
	public Object dgItemShowSetRank(String isShow,String pxlx,Integer id,Integer moveType) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	Map<String,Object> params = new HashMap<String,Object>();
    	Map<String,Object> params2 = new HashMap<String,Object>();
    	DgItemShowRank upShowRank = null;
    	DgItemShowRank downShowRank = null;
    	Integer id1 = 0,rank1 = 0;
    	Integer id2 = 0,rank2 = 0;
    	
    	//根据品项id查询被移动品项的rank值
    	DgItemShowRank setShowRank = new DgItemShowRank();
    	setShowRank.setId(id);//Set(设置)参数
    	DgItemShowRank getShowRank = dgItemShowRankService.getDgItemShowRankById(setShowRank);
    	id1 = getShowRank.getId();
    	rank1 = getShowRank.getRank();
    	
    	//根据移动类型获取互换的rank值
    	if(moveType==1){ //向上移动
    		DgItemShowRank moveUpItemShowRank = new DgItemShowRank();
    		moveUpItemShowRank.setRank(rank1);
	    	upShowRank = dgItemShowRankService.getDgItemShowRankByMoveUp(moveUpItemShowRank);
	    	if(upShowRank != null){
	    		id2 = upShowRank.getId();
	    		rank2 = upShowRank.getRank();
	    		//互换renk值
	        	params.put("id", id1);
	        	params.put("rank", rank2);
	        	params2.put("id", id2);
	        	params2.put("rank", rank1);
	    		try{
	    			dgItemShowRankService.dgItemShowSetRank(params);
	    			dgItemShowRankService.dgItemShowSetRank(params2);
	    			result.put("success", "OK");
	    		}catch(Exception e){
	    			result.put("success", "false");
	    			result.put("error", e.getMessage());
	    		}
	    	}else{
	    		result.put("no", "NO");
	    	}
    	}else if(moveType==0){ //向下移动
    		DgItemShowRank moveDownItemShowRank = new DgItemShowRank();
    		moveDownItemShowRank.setRank(rank1);
    		downShowRank = dgItemShowRankService.getDgItemShowRankByMoveDown(moveDownItemShowRank);
    		if(downShowRank != null){
    			id2 = downShowRank.getId();
    			rank2 = downShowRank.getRank();
    			//互换renk值
    	    	params.put("id", id1);
    	    	params.put("rank", rank2);
    	    	params2.put("id", id2);
    	    	params2.put("rank", rank1);
    			try{
    				dgItemShowRankService.dgItemShowSetRank(params);
    				dgItemShowRankService.dgItemShowSetRank(params2);
    				result.put("success", "OK");
    			}catch(Exception e){
    				result.put("success", "false");
    				result.put("error", e.getMessage());
    			}
    		}else{
    			result.put("no", "NO");
    		}
    	}
		return result;
	}
    
    /**
     * 连表查询消费品项类型显示设置信息
     * @param dgItemShowRank
     * @return
     */
    @RequestMapping("/selectDgItemTypeBySearch")
    @ResponseBody
    public DgItemFileType selectDgItemTypeBySearch(Integer id){
    	DgItemFileType dgItemFileType = dgItemFileTypeService.selectByPrimaryKey(id);
        return dgItemFileType;
    }
    /**
     * 设置排行 - 品项
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/toAddCgdsRankItem")
    public ModelAndView toAddCgdsRankItem(HttpServletRequest request,HttpServletResponse response)
    {
		ModelAndView model = new ModelAndView("archive/cgds/cgds-rank-item");
    	return model;
    }
    /**
     * 设置排行 - 品项小类
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/toAddCgdRankSmallItem")
    public ModelAndView toAddCgdRankSmallItem(HttpServletRequest request,HttpServletResponse response)
    {
		ModelAndView model = new ModelAndView("archive/cgds/cgds-rank-smallitem");
    	return model;
    }
    /**
     * 设置显示 - 品项
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/toAddCgdShowItem")
    public ModelAndView toAddCgdShowItem(HttpServletRequest request,HttpServletResponse response)
    {
		ModelAndView model = new ModelAndView("archive/cgds/cgds-show-item");
    	return model;
    }
}
