package com.yqsh.diningsys.web.controller.archive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.YQSHTranslate;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.service.archive.DgPublicCode0Service;
import com.yqsh.diningsys.web.service.sysSettings.SysLogService;

/**
 * 公共代码控制器
 * 
 * @author xiewei
 * @version 创建时间：2016年9月29日 上午10:36:12
 */
@Controller
@RequestMapping(value = "/archive/dpc")
public class DgPublicCode0Controller extends BaseController {

	@Autowired
	private DgPublicCode0Service dgPublicCode0Service;

	private Set<String> childList = new HashSet<String>();
	private Set<String> parentList = new HashSet<String>();
	
	@Autowired 
	private SysLogService sysLogService;
	@RequestMapping("/index")
	public ModelAndView index(Model model, DgPublicCode0 dpc) {
		// 查询公共代码数据
		dpc.setiDelFlg("0");
		model.addAttribute("listDpc", dgPublicCode0Service.getAllList(dpc));
		ModelAndView modelAndView = new ModelAndView("archive/dpc/dpc-index");
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
		ModelAndView modelAndView = new ModelAndView("archive/dpc/dpc-trash");
		return modelAndView;
	}

	@RequestMapping("/toDpcFormEdit")
	public String toGiftFormEdit(Model model, Integer id, String cParent,
			String cParentName){
		if (id != null) { // 修改
			//查询修改对象
			DgPublicCode0 set_dpc = new DgPublicCode0();
			set_dpc.setId(id);
			DgPublicCode0 dpc = dgPublicCode0Service.getDpcById(set_dpc);
			model.addAttribute("dpc", dpc);
			//查询修改对象对应“父级代码和父级名称”
			cParent = dpc.getcParent();
			set_dpc.setId(Integer.parseInt(cParent));
			DgPublicCode0 get_dpc = dgPublicCode0Service.getDpcParentNameById(set_dpc);
			
			if(get_dpc != null)
				cParentName = get_dpc.getcCode() + "-" + get_dpc.getcName();
			else
				cParentName = "顶级公共代码";
			
			model.addAttribute("cParentName", cParentName);
		} else {
			DgPublicCode0 set_dpc = new DgPublicCode0();
			set_dpc.setcParent(cParent);
			String cCode = getLastcCode(set_dpc);
			DgPublicCode0 dpc = new DgPublicCode0();
			dpc.setcCode(cCode);
			dpc.setcParent(cParent);
			model.addAttribute("dpc", dpc);
			model.addAttribute("cParentName", cParentName);
		}
		return "archive/dpc/dpc-edit";
	}

	/**
	 * 通过上级代码查询最后一条记录获取代码进行叠加算法
	 * 
	 * @param dgDpc
	 * @return
	 */
	public String getLastcCode(DgPublicCode0 dgDpc) {
		String cCode = null;
		try {
			DgPublicCode0 dpc = dgPublicCode0Service.getLastcCode(dgDpc);
			// 如果该节点下有子节点，则查询最大代码进行算法叠加
			if (dpc != null) {
				String lastCode = dpc.getcCode();
				cCode = getCodeByLastCode(lastCode);
			} else {
				cCode = "001"; // 初始化代码
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cCode;
	}

	/**
	 * 编号叠加算法
	 * 
	 * @param lastCode
	 * @return
	 */
	public String getCodeByLastCode(String lastCode) {
		int lastCodeNo = Integer.parseInt(lastCode);
		int codeNo = lastCodeNo + 1;
		String code = codeNo + ""; // 编号
		String no = ""; // 填充“0”
		for (int i = 0; i < 3 - code.length(); i++) {
			no += "0";
		}
		return no + code;
	}

	/**
	 * 分页查询公共代码信息
	 * 
	 * @param request
	 * @param response
	 * @param dpc
	 * @return
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpServletRequest request,
			HttpServletResponse response, DgPublicCode0 dpc) {
		com.yqsh.diningsys.core.util.Page<DgPublicCode0> pagebean = null;
		try {
			pagebean = dgPublicCode0Service.getPageList(dpc);
			pagebean.setTotal(pagebean.getContext().getPageCount());
			pagebean.setPage(dpc.getPage());
			pagebean.setRecords(pagebean.getContext().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagebean;
	}

	/**
	 * 根据部门cParent获取公共代码信息
	 * 
	 * @param cParent
	 * @return
	 */
	@RequestMapping("/selectSmallDpc")
	@ResponseBody
	public List<DgPublicCode0> selectSmallDep(String id, String iDelFlg, Integer cParent) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("iDelFlg", iDelFlg);
		params.put("cParent", cParent);

		// 根据cParent值查询父级
		DgPublicCode0 set_dpc = new DgPublicCode0();
		set_dpc.setId(cParent);
		DgPublicCode0 get_dpc = dgPublicCode0Service.getDpcById(set_dpc);
		List<DgPublicCode0> dgPublicCode0s = dgPublicCode0Service
				.selectSmallDpc(params);
		for (int i = 0; i < dgPublicCode0s.size(); i++) {
			DgPublicCode0 dpc = dgPublicCode0s.get(i);
			dpc.setcName(get_dpc.getcCode() + dpc.getcCode() + "-" + dpc.getcName());
			dpc.setcParent(get_dpc.getcCode() + "-" + dpc.getcParent());
		}
		return dgPublicCode0s;
	}
	
	/**
	 * 通过公共代码ID查询公共代码信息
	 * 
	 * @param request
	 * @param response
	 * @param dpc
	 * @return
	 */
	@RequestMapping(value = "/getDpcById")
	@ResponseBody
	public Object getDpcById(HttpServletRequest request,
			HttpServletResponse response, DgPublicCode0 dgDpc) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			dgDpc.setiDelFlg("0");// 查询未被删除的公共代码信息
			DgPublicCode0 dpc = dgPublicCode0Service.getDpcById(dgDpc);
			result.put("dpc", dpc);
			result.put("success", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error");
		}
		return result;
	}
	
    /**
     * 公共代码管理-判断名称是否已经存在、判断速记码是都已经存在
     * @param dpc
     * @return
     */
    @RequestMapping("/checkParamIsHaved")
    @ResponseBody
    public Object checkParamIsHaved(DgPublicCode0 dpc){
    	Map<String,Object> result = new HashMap<String, Object>();
    	Integer cNameCount = 0;
    	Integer cKeyWDCount = 0;
    	
    	//判断名称是否已经存在
    	if(dpc.getcName() != null){
    		DgPublicCode0 cName = new DgPublicCode0();
    		cName.setcName(dpc.getcName());
    		List<DgPublicCode0> cNames = dgPublicCode0Service.getAllList(cName);
    		if(cNames.size() == 0){
    			cNameCount = 0;
    		}
    		if(cNames.size() == 1 && cNames.get(0).getId().equals(dpc.getId())){
    			cNameCount = 0;
    		}else{
    			cNameCount = cNames.size();
    		}
    		if(cNames.size() > 1){
    			cNameCount = cNames.size();
    		}
    	}
    	//判断速记码是都已经存在
    	if(dpc.getcKeyWD() != null){
    		DgPublicCode0 cKeyWD = new DgPublicCode0();
    		cKeyWD.setcKeyWD(dpc.getcKeyWD());
    		List<DgPublicCode0> cKeyWDs = dgPublicCode0Service.getAllList(cKeyWD);
    		if(cKeyWDs.size() == 0){
    			cKeyWDCount = 0;
    		}
    		if((cKeyWDs.size() == 1 && cKeyWDs.get(0).getId().equals(dpc.getId())) ){
    			cKeyWDCount = 0;
    		}else{
    			cKeyWDCount = cKeyWDs.size();
    		}
    		if(cKeyWDs.size() > 1){
    			cKeyWDCount = cKeyWDs.size();
    		}
    	}
        result.put("cNameCount", cNameCount);
        result.put("cKeyWDCount", cKeyWDCount);
        
        return result;
    }

	/**
	 * 新增、修改公共代码信息保存
	 * 
	 * @param request
	 * @param response
	 * @param dpc
	 * @return
	 */
	@RequestMapping(value = "/saveDpc")
	@ResponseBody
	public Object saveDpc(HttpServletRequest request,
			HttpServletResponse response, DgPublicCode0 dpc) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
                        String cKeyWD = dpc.getcKeyWD();
                        if(StringUtil.isBlank(cKeyWD)){
                            dpc.setcKeyWD(YQSHTranslate.getPYIndexStr(dpc.getcName(), true));
                        }
			dgPublicCode0Service.insertOrUpdDpc(dpc);
			result.put("success", "OK");
		} catch (Exception e) {
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}

	/**
	 * 公共代码信息回收站
	 * 
	 * @param request
	 * @param response
	 * @param dpc
	 * @return
	 */
	@RequestMapping(value = "/deleteDpcTrash")
	@ResponseBody
	public Object deleteDpcTrash(DgPublicCode0 dpc) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<String> list = new ArrayList<String>();
			if(dpc.getEditIds() != null)
			{
				for (String id : dpc.getEditIds().split(",")) {
					//如果该回收对象有相对的子节点，则回收该对象时回收该对象子节点 - 需求变更为有子节点的父节点不能删除
					String iDelFlg = "0";
					List<String> ls = getAllSmallDpc(id,iDelFlg);
					if(ls != null && ls.size() > 0){
						result.put("success", "hasChild");
						return result;
						//如果需求为删除子节点就取消注释
//						for (int i = 0; i < ls.size(); i++) {
//							list.add(ls.get(i));
//						}
					}
					list.add(id);
				}	
				//判断回收公共代码中是否存在系统自带公共代码  true=存在
				boolean flag = checkiSystem(list);
				if(flag){
					result.put("success", "isSys");
				}else{
					dpc.setIds(list);
					dgPublicCode0Service.deleteTrash(dpc);
					result.put("success", "OK");
				}
			}
			else
			{
				list.add(""+dpc.getId());
				dpc.setIds(list);
				dgPublicCode0Service.deleteTrash(dpc);
				result.put("success", "OK");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
		}finally{
			childList.clear();
		}
		return result;
	}
	
	/**
	 * 判断是否是系统自带字典
	 * @param ids
	 * @return
	 */
	public boolean checkiSystem(List<String> ids){
		boolean flag = false;
		DgPublicCode0 set_dpc = new DgPublicCode0();
		for (int i = 0; i < ids.size(); i++) {
			set_dpc.setId(Integer.parseInt(ids.get(i)));
			DgPublicCode0 get_dpc = dgPublicCode0Service.getDpcById(set_dpc);
			if(get_dpc != null){
				String iSystem = get_dpc.getiSystem();
				if("Y".equals(iSystem)){
					flag = true;
				}
			}
		}
		
		return flag;
		
	}

	/**
	 * 还原回收站公共代码信息
	 * 
	 * @param request
	 * @param response
	 * @param dpc
	 * @return
	 */
	@RequestMapping(value = "/replyDpc")
	@ResponseBody
	public Object replyDep(HttpServletRequest request,
			HttpServletResponse response, DgPublicCode0 dpc) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<String> list = new ArrayList<String>();
			for (String id : dpc.getEditIds().split(",")) {
				//如果该还原对象有相对的子节点，则还原该对象时还原该对象子节点
				List<String> ls = getAllSmallDpc(id,null);
				if(ls != null){
					for (int i = 0; i < ls.size(); i++) {
						list.add(ls.get(i));
					}
				}
				//如果该还原对象有删除状态的父节点，则还原该对象时还原该对象父节点
				List<String> rls = getAllParentDpc(id);
				if(rls != null && rls.size() > 0){
					for (int i = 0; i < rls.size(); i++) {
						list.add(rls.get(i));
					}
				}
   				list.add(id);
			}
			dpc.setIds(list);
			dgPublicCode0Service.replyDpc(dpc);
			result.put("success", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
		}finally{
			childList.clear();
			parentList.clear();
		}
		return result;
	}

	/**
	 * 删除公共代码信息
	 * 
	 * @param request
	 * @param response
	 * @param dpc
	 * @return
	 */
	@RequestMapping(value = "/deleteDpc")
	@ResponseBody
	public Object deleteDep(HttpServletRequest request,
			HttpServletResponse response, DgPublicCode0 dpc) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<String> list = new ArrayList<String>();
			if(dpc.getEditIds() != null)
			{
			for (String id : dpc.getEditIds().split(",")) {
				//如果该删除对象有相对的子节点，则删除该对象时删除该对象子节点 - 需求变更为有子节点的父节点不能删除
				String iDelFlg = "1";
				List<String> ls = getAllSmallDpc(id,iDelFlg);
				if(ls != null && ls.size() > 0){
					result.put("success", "hasChild");
					return result;
					//如果需求为删除子节点就取消注释
//					for (int i = 0; i < ls.size(); i++) {
//						list.add(ls.get(i));
//					}
				}
				list.add(id);
				}
			dpc.setIds(list);
			dgPublicCode0Service.deleteByIds(dpc);
			result.put("success", "OK");
			}
			else
			{
				list.add(""+dpc.getId());
				dpc.setIds(list);
				dgPublicCode0Service.deleteByIds(dpc);
				result.put("success", "OK");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
		}finally{
	   		childList.clear();
		}
		return result;
	}
	
	/**
	 * 根据父节点id查询是否有子节点，如果有返回子节点list
	 * @param pId
	 * @return
	 */
	public List<String> getAllSmallDpc(String pId,String iDelFlg){
		List<String> listIds = new ArrayList<String>();
		DgPublicCode0 set_dpc = new DgPublicCode0();
		set_dpc.setcParent(pId);
		set_dpc.setiDelFlg(iDelFlg);
		List<DgPublicCode0> get_dpc = dgPublicCode0Service.getAllList(set_dpc);
		if(get_dpc != null){
			for (int i = 0; i < get_dpc.size(); i++) {
				String id = get_dpc.get(i).getId()+"";
				childList.add(id);
				getAllSmallDpc(id,iDelFlg);
			}
		}
		
		Iterator<String> it = childList.iterator();  
		while (it.hasNext()) {  
		  String sid = it.next();  
		  listIds.add(sid);
		}
		return listIds;	
	}
	
	/**
	 * 根据子节点id查询是否有父节点，如果有返回父节点id
	 * @param id
	 * @return
	 */
	public List<String> getAllParentDpc(String id){
		List<String> listIds = new ArrayList<String>();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("iDelFlg", "1");
		List<DgPublicCode0> get_dpc = dgPublicCode0Service.selectAllDpc(params);
		if(get_dpc != null && get_dpc.size() > 0){
			String pId = get_dpc.get(0).getcParent()+"";
			parentList.add(pId);
			getAllParentDpc(pId);
		}
		Iterator<String> it = parentList.iterator();  
		while (it.hasNext()) {  
		  String sid = it.next();  
		  listIds.add(sid);
		} 
		return listIds;	
	}

	/**
	 * 获取所有公共代码信息
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/selectAllDpc")
	@ResponseBody
	public List<DgPublicCode0> selectAllDpc(String id,String iDelFlg,String cParent) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("iDelFlg", iDelFlg);
		params.put("cParent", cParent);
		List<DgPublicCode0> dgPublicCode0s = dgPublicCode0Service.selectAllDpc(params);
		
		//出初始化树结构根目录
		DgPublicCode0 initDpc = new DgPublicCode0();
		initDpc.setId(1);
		initDpc.setcName("公共代码");
		dgPublicCode0s.add(0, initDpc);
		List<DgPublicCode0> dpcs = setStyle(dgPublicCode0s);
		return dpcs;
	}
	
	/*
	 * 设置公共代码显示格式
	 */
	public List<DgPublicCode0> setStyle(List<DgPublicCode0> dgPublicCode0s){
		for (int i = 0; i < dgPublicCode0s.size(); i++) {
			DgPublicCode0 dpc = dgPublicCode0s.get(i);
			if(dpc.getId() > 1){
//				//获取父级代码
//				DgPublicCode0 set_dpc = new DgPublicCode0();
//				set_dpc.setId(Integer.parseInt(dpc.getcParent()));
//				DgPublicCode0 get_dpc = dgPublicCode0Service.getDpcById(set_dpc);
//				if(get_dpc != null){
//					String parentCode = get_dpc.getcCode();
//					dpc.setcName(parentCode + dpc.getcCode() + "-" + dpc.getcName());
//				}else{
					dpc.setcName(dpc.getcCode() + "-" + dpc.getcName());
//				}
			}
			
		}
		return dgPublicCode0s;
		
	}
	
	/**
	 * 通过公共代码ID查询公共代码父节点ID
	 * @param request
	 * @param response
	 * @param dpc
	 * @return
	 */
	@RequestMapping(value = "/getcParentIdById")
	@ResponseBody
	public Object getcParentIdById(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		try {
			List<DgPublicCode0> dpcs = dgPublicCode0Service.selectAllDpc(params);
			if(dpcs != null && dpcs.size() > 0){
				DgPublicCode0 dpc = dpcs.get(0);
				result.put("dpc", dpc);
				result.put("success", "OK");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error");
		}
		return result;
	}

	/**
	 * 导出公共代码信息Excel
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param dpc
	 * @return
	 */
	@RequestMapping(value = "exportXls")
	public ModelAndView exportXls(Model model, DgPublicCode0 dpc) {
		List<DgPublicCode0> dpcList = dgPublicCode0Service.getAllList(dpc);
		for (int i = 0; i < dpcList.size(); i++) {
			DgPublicCode0 set_dpc = dpcList.get(i);
			String cParent = set_dpc.getcParent();
			if(cParent == null){
				String defaultcParent = "顶级公共代码";
				set_dpc.setcParent(defaultcParent);
			}
		}
		model.addAttribute("dpcList", dpcList);
		ModelAndView modelAndView = new ModelAndView("archive/dpc/exportXls");
		return modelAndView;
	}
	
}
