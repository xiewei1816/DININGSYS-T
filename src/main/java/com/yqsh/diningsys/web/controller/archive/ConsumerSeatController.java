package com.yqsh.diningsys.web.controller.archive;

import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.YQSHTranslate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yqsh.diningsys.web.model.archive.DgCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.DgSettlementWayType;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.deskBusiness.DgProductPhaseLeftmenu;
import com.yqsh.diningsys.web.service.archive.DgAllowNumberService;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.DgPublicCode0Service;
import com.yqsh.diningsys.web.service.sysSettings.AutoSeqService;

import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping(value = "/consumerSeat")
public class ConsumerSeatController extends BaseController {

    @Autowired
    private DgConsumptionAreaService consumptionAreaService;
    @Autowired
    private DgConsumerSeatService consumerSeatService;
    @Autowired
    private DgAllowNumberService dgAllowNumberService;
    @Autowired
    private AutoSeqService seqService;
    @Autowired
    private DgPublicCode0Service dgPublicCode0Service;

    @RequestMapping("/index")
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView("archive/consumerSeat/consumerSeat-index");
        modelAndView.addObject("areas", consumptionAreaService.getAllList(new DgConsumptionArea()));
    	//查询字典数据
    	model.addAttribute("list",dgPublicCode0Service.getAllDpcToPage()); 
        return modelAndView;
    }

    @RequestMapping("/addOrUpdate")
    public String addOrUpdate(HttpServletRequest request, HttpServletResponse response, Integer id) {
        List<DgConsumptionArea> areaList = consumptionAreaService.getAllList(new DgConsumptionArea());
        request.setAttribute("areas", areaList);
        //查询字典数据
        request.setAttribute("list",dgPublicCode0Service.getAllDpcToPage());
        //客位容纳标准
        request.setAttribute("allowNumberList",dgAllowNumberService.getAllList());
        if (id != 0) {
            DgConsumerSeat consumerSeat = new DgConsumerSeat();
            consumerSeat.setId(id);
            DgConsumerSeat dgConsumerSeat = consumerSeatService.getDgConsumerSeatByID(consumerSeat);
            request.setAttribute("bean", dgConsumerSeat);
        } else {
            String currentNum = seqService.getSeq("consumer", 3, 0, "", 0, "");
            DgConsumerSeat seat = new DgConsumerSeat();
            seat.setNumber(currentNum);
            request.setAttribute("bean", seat);
        }
        return "archive/consumerSeat/consumerSeatInfo";
    }

    @RequestMapping(value = "/getPageList")
    @ResponseBody
    public Object getPageList(HttpServletRequest request, HttpServletResponse response, DgConsumerSeat consumerSeat) {

        com.yqsh.diningsys.core.util.Page<DgConsumerSeat> pagebean = null;
        try {
            pagebean = consumerSeatService.getPageList(consumerSeat);
            pagebean.setTotal(pagebean.getContext().getPageCount());
            pagebean.setPage(consumerSeat.getPage());
            pagebean.setRecords(pagebean.getContext().getTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }

    @RequestMapping("/getAllSeatWithServiceClass")
    @ResponseBody
    public List<DgConsumerSeat> getAllSeatWithServiceClass(Integer bisId){
        return consumerSeatService.getAllSeatWithServiceClass(bisId);
    }

    @RequestMapping(value = "/getAllList")
    @ResponseBody
    public Object getAllList(HttpServletRequest request, HttpServletResponse response,
             DgConsumerSeat consumerSeat) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<DgConsumerSeat> list = consumerSeatService.getAllList(consumerSeat);
            result.put("list", list);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "error");
        }
        return result;
    }




    @RequestMapping(value = "/toAddCardView")
    public String toAddCardView(HttpServletRequest request, HttpServletResponse response,
                             String id) {
        try {
            request.setAttribute("id",id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "archive/consumerSeat/addCardView";
    }

    @RequestMapping(value = "/getCardByConsumerSeat")
    public String getAllList(HttpServletRequest request, HttpServletResponse response,
                             String id) {
        try {
            request.setAttribute("id",id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "archive/consumerSeat/cardmanager";
    }

    @RequestMapping(value = "/saveCard")
    @ResponseBody
    public Object saveCard(HttpServletRequest request, HttpServletResponse response,
                           DgCard dgCard) {
        Map<String,Object> result = new HashMap<String,Object>();
        try{
        	dgCard.setCardnum(dgCard.getCardnum().replaceFirst("^0*", ""));
            consumerSeatService.saveCard(dgCard);
            result.put("success","OK");
        }catch(Exception e){
            e.printStackTrace();
            result.put("success","false");
        }
        return result;
    }

    @RequestMapping(value = "/getCards")
    @ResponseBody
    public Object getCards(HttpServletRequest request, HttpServletResponse response,
                              DgCard dgCard) {
        com.yqsh.diningsys.core.util.Page<DgCard> pagebean = null;
        try{
            pagebean = consumerSeatService.getCardsByConsumerSeatId(dgCard);
            pagebean.setTotal(pagebean.getContext().getPageCount());
            pagebean.setPage(dgCard.getPage());
            pagebean.setRecords(pagebean.getContext().getTotal());
        }catch(Exception e){
            e.printStackTrace();
        }
        return pagebean;
    }

    @RequestMapping(value = "/deleteCards")
    @ResponseBody
    public Object deleteCards(HttpServletRequest request, HttpServletResponse response,
                           DgCard dgCard) {
        Map<String,Object> result = new HashMap<String,Object>();
        try{
            consumerSeatService.deleteCardByid(dgCard);
            result.put("success", "OK");
        }catch(Exception e){
            e.printStackTrace();
            result.put("success","error");
        }
        return result;
    }


    @RequestMapping(value = "/getTreeList")
    @ResponseBody
    public Object getTreeList(HttpServletRequest request, HttpServletResponse response,
             DgConsumerSeat consumerSeat) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<DgConsumptionArea> areaList = consumptionAreaService.getAllList(new DgConsumptionArea());
        for (DgConsumptionArea area : areaList) {
            Map<String, Object> treedom = new HashMap<String, Object>();
            treedom.put("id", area.getId() + "-area");
            treedom.put("pid", 0);
            treedom.put("name", area.getName());
            result.add(treedom);

            DgConsumerSeat childSeat = new DgConsumerSeat();
            childSeat.setConsArea(area.getId());
            List<DgConsumerSeat> list = consumerSeatService.getAllList(childSeat);

            for (DgConsumerSeat seat : list) {
                Map<String, Object> tree = new HashMap<String, Object>();
                tree.put("id", seat.getId() + "-seat");
                tree.put("pid", area.getId() + "-area");
                tree.put("name", seat.getName());
                result.add(tree);
            }
        }
        return result;
    }

    @RequestMapping(value = "/getConsumerSeatByID")
    @ResponseBody
    public Object getConsumerSeatByID(HttpServletRequest request, HttpServletResponse response, DgConsumerSeat consumerSeat) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            DgConsumerSeat dgConsumerSeat = consumerSeatService.getDgConsumerSeatByID(consumerSeat);
            result.put("consumerSeat", dgConsumerSeat);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "error");
        }
        return result;
    }

    @RequestMapping(value = "/saveConsumerSeat")
    @ResponseBody
    public Object saveConsumerSeat(HttpServletRequest request,
            HttpServletResponse response, DgConsumerSeat consumerSeat) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //计算助记符
            String zjf = consumerSeat.getMnemonic();
            if(StringUtil.isBlank(zjf)){
                consumerSeat.setMnemonic(YQSHTranslate.getPYIndexStr(consumerSeat.getName(), true));
            }
            
            DgConsumerSeat dbCs = consumerSeatService.getConsumerSeatByNumber(consumerSeat.getNumber());
            if (dbCs != null) {
                if (consumerSeat.getId() != null && consumerSeat.getId() == dbCs.getId()) {  //更新情况
                    consumerSeat.setCreateUserid(getCurrentUser().getId() + "");
                    consumerSeatService.insertOrUpd(consumerSeat);
                    seqService.setUsedSeq("consumer", consumerSeat.getNumber());
                    result.put("success", "OK");
                } else {
                    result.put("success", "false");
                    result.put("error", "编号重复");
                }
            } else {
                consumerSeat.setCreateUserid(getCurrentUser().getId() + "");
                consumerSeatService.insertOrUpd(consumerSeat);
                seqService.setUsedSeq("consumer", consumerSeat.getNumber());
                result.put("success", "OK");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
            result.put("error", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteConsumerSeat")
    @ResponseBody
    public Object deleteConsumerSeat(HttpServletRequest request,
            HttpServletResponse response, DgConsumerSeat consumerSeat) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<String> list = new ArrayList<String>();
            for (String str : consumerSeat.getEditIds().split(",")) {
                list.add(str);
            }
            consumerSeat.setIds(list);
            consumerSeatService.deleteByIds(consumerSeat);
            result.put("success", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
        }
        return result;
    }

    @RequestMapping(value = "/checkConsumerSeatByName")
    @ResponseBody
    public Object checkConsumerSeatByName(HttpServletRequest request,
            HttpServletResponse response, DgConsumerSeat consumerSeat) {
        Map<String, Object> result = new HashMap<String, Object>();
        int stat = 0;
        List<DgConsumerSeat> checkList = consumerSeatService.getAllList(consumerSeat);
        if (consumerSeat.getId() != null && consumerSeat.getId() > 0) {
            stat = checkList != null && checkList.size() != 0
                    ? checkList.get(0).getId() != consumerSeat.getId() ? 2 : 1 : 1;
        } else {
            stat = checkList != null && checkList.size() != 0 ? 2 : 1;
        }
        result.put("state", stat);
        return result;
    }
    
    /**
     * 
     * 批量还原
     * @param ids
     * @return
     */
    
    @RequestMapping("/restore")
    @ResponseBody
    public  Object restore(String ids){
    	Map<String,Object> ret = new HashMap<String, Object>();
    	consumerSeatService.restore(ids);
    	ret.put("success", true);
    	return ret;
    }
    
    
    /**
     * 
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public  Object delete(String ids){
    	Map<String,Object> ret = new HashMap<String, Object>();
    	consumerSeatService.deleteByIds(ids);
    	ret.put("success", true);
    	return ret;
    }
    
    
    @RequestMapping("/trash")
	public ModelAndView trash(HttpServletRequest request,HttpServletResponse response,Model model){
		ModelAndView modelAndView = new ModelAndView("archive/consumerSeat/consumerSeat-trash");
		return modelAndView;
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
    		m.setName("消费区域");
    		m.setChildCount(1); //大于0即可
    		m.setParentId(0);
    		menu.add(m);
    	}
    	else if(id.equals("-1"))
    	{
    			Map<String,Object> map = new HashMap<String,Object>();
    			map.put("cParent",-1);
    			map.put("iDelFlg",0);
    			List<DgConsumptionArea> areaList = consumptionAreaService.getAllList(new DgConsumptionArea());
    			
    			for(DgConsumptionArea a:areaList)
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
    public  List<DgConsumptionArea> getAllType()
    {
    	List<DgConsumptionArea> areaList = consumptionAreaService.getAllList(new DgConsumptionArea());
    	return areaList;
    }
    
    @RequestMapping("/toQrCode")
    public String toQrCode(HttpServletRequest request, HttpServletResponse response, Integer id) {
        request.setAttribute("seatId", id);
        return "archive/consumerSeat/QrCorebttp";
    }
}
