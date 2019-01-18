package com.yqsh.diningsys.web.controller.archive;

import java.util.ArrayList;
import java.util.List;







import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.DgItemFileService;
import com.yqsh.diningsys.web.service.archive.DgItemFileTypeService;
@Controller
@RequestMapping(value = "/yxe")
public class DgYxeController extends BaseController{
	@Autowired
	private DgConsumptionAreaService dgConsumptionAreaService;
	@Autowired
	private DgItemFileTypeService dgItemFileTypeService;
	@Autowired
	private DgItemFileService dgItemFileService;
	
    @RequestMapping("/yxeConsumerItem")
    public String index() {
        return "archive/consumerSeat/yxeConsumerItem";
    }
    
	/**
     * 获取所有区域
     * @param flag
     * @return
     */
    @RequestMapping("/selectAllConsumption")
    @ResponseBody
    public List<DgConsumptionArea> selectAllType(){
    	DgConsumptionArea area = new DgConsumptionArea();
    	area.setDelFlag(0);
        List<DgConsumptionArea> areas = dgConsumptionAreaService.getAllList(area);
        for(DgConsumptionArea a:areas){
        	a.setParentId("0");
        }
		//出初始化树结构根目录
        DgConsumptionArea type = new DgConsumptionArea();
        type.setId(0);
        type.setParentId("-1");
        type.setName("区域展示");
        areas.add(0, type);  
        return areas;
    }
    
    /**
     * 获取所有品项
     * @param flag
     * @return
     */
    @RequestMapping("/selectAllItem")
    @ResponseBody
    public List<Map> selectAllItem(Integer consId){
    	if(consId != null){
        	List<Map> resmap = dgItemFileTypeService.selectYxeConsumerItems(consId);
            return resmap;	
    	} else {
    		return null;
    	}
    }
    
    @RequestMapping("/insertYxeConsumerItem")
    @ResponseBody
    public Object insertYxeConsumerItem(Integer consumerId,String ids){
    	try{
    		dgItemFileTypeService.insertYxeConsumers(consumerId, ids);
    		return returnSuccess();
    	} catch(Exception e){
    		e.printStackTrace();
    		return returnFail(e.getMessage());
    	}
    }
}
