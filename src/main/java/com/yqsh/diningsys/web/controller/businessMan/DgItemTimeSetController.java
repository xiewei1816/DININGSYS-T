package com.yqsh.diningsys.web.controller.businessMan;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.businessMan.DgItemTimeSet;
import com.yqsh.diningsys.web.service.businessMan.DgItemTimeSetService;


/**
 * 品项显示时间设置控制器
 * @author xiewei
 * @version 创建时间：2016年10月29日 上午10:36:12
 */
@Controller
@RequestMapping(value = "/businessMan/itemTimeSet")
public class DgItemTimeSetController extends BaseController{
	
    @Autowired
    private DgItemTimeSetService dgItemTimeSetService;

    /**
     * 新增、修改品项显示时间设置保存
     * @param request
     * @param response
     * @param saveDgItemTimeSet
     * @return
     */
    @RequestMapping(value = "/saveDgItemTimeSet")
	@ResponseBody
	public Object saveDgItemTimeSet(HttpServletRequest request,HttpServletResponse response,DgItemTimeSet dgItemTimeSet) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			dgItemTimeSetService.insertOrUpdDgItemTimeSet(dgItemTimeSet);
			result.put("success", "OK");
		}catch(Exception e){
			result.put("success", "false");
			result.put("error", e.getMessage());
		}
		return result;
	}

    /**
     * 根据多条件查询消费品项显示设置信息
     * @param dgItemTimeSet
     * @return
     */
    @RequestMapping(value="getAllList")
    @ResponseBody
   	public Object getAllList(){
   		List<DgItemTimeSet> dgItemTimeSetList = dgItemTimeSetService.getAllList();
   		if(dgItemTimeSetList.get(0) != null){
   			return dgItemTimeSetList.get(0);
   		}
   		return "";
   	}
}
