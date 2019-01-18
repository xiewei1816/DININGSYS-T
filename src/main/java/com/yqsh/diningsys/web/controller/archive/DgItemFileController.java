package com.yqsh.diningsys.web.controller.archive;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.io.Files;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.YQSHTranslate;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileColor;
import com.yqsh.diningsys.web.model.archive.DgItemFilePackage;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.archive.DgItemFileZccf;
import com.yqsh.diningsys.web.model.archive.DgNutrition;
import com.yqsh.diningsys.web.model.archive.DgProMethods;
import com.yqsh.diningsys.web.model.archive.DgProMethodsType;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.inve.DgItemType;
import com.yqsh.diningsys.web.service.archive.DgItemFileColorService;
import com.yqsh.diningsys.web.service.archive.DgItemFileService;
import com.yqsh.diningsys.web.service.archive.DgItemFileTypeService;
import com.yqsh.diningsys.web.service.archive.DgItemFileZccfService;
import com.yqsh.diningsys.web.service.archive.DgProMethodsSerivce;
import com.yqsh.diningsys.web.service.archive.DgProMethodsTypeService;
import com.yqsh.diningsys.web.service.archive.DgPublicCode0Service;
import com.yqsh.diningsys.web.service.deskBusiness.DgJointDeleteService;
import com.yqsh.diningsys.web.service.inve.DgInveItemsService;
import com.yqsh.diningsys.web.service.inve.DgItemTypeService;
import com.yqsh.diningsys.web.service.sysSettings.AutoSeqService;

/**
 * 品项档案
 *
 * @author zhshuo create on 2016-10-13 16:52
 */
@Controller
@RequestMapping("/archive/itemFile")
public class DgItemFileController extends BaseController {

    @Autowired
    private DgItemFileService dgItemFileService;

    @Autowired
    private DgItemFileTypeService dgItemFileTypeService;

    @Autowired
    private DgProMethodsTypeService dgProMethodsTypeService;

    @Autowired
    private DgProMethodsSerivce dgProMethodsSerivce;

    @Autowired
    private DgJointDeleteService dgJointDeleteService;
    @Autowired
    private AutoSeqService seqService;
    
	@Autowired
	private DgInveItemsService inveItemsService;

	@Autowired
	private DgItemTypeService dgItemTypeService;
	
	@Autowired
	private DgItemFileZccfService dgItemFileZccfService;

	@Autowired
	private DgPublicCode0Service dgPublicCode0Service;

	@Autowired
	private DgItemFileColorService dgItemFileColorService;
    @RequestMapping("/index")
    public String index() {
        return "archive/itemFile/item_file_index";
    }

    /**
     * 品项类别 add
     *
     * @param flag 1 大类 2 小类
     * @param id id
     * @param model
     * @return
     */
    @RequestMapping("/toAddFileTypeEdit")
    public String toAddFileBigTypeEdit(Integer flag, Integer id, Model model) {

        String currentNum = "";
        if (flag == 1) {
            currentNum = seqService.getSeq("dgitem-big-type", 4, 0, "", 0, "");
            //公共代码查询品牌
            List<DgPublicCode0> pp = dgPublicCode0Service.selectPublicCodeByKey("PP");
            //公共代码查询品项类型
            List<DgPublicCode0> pxlxgl = dgPublicCode0Service.selectPublicCodeByKey("PXLXGL");
            model.addAttribute("pp", pp);
            model.addAttribute("pxlxgl", pxlxgl);
        } else if (flag == 2) {
            DgItemFileType dgitem = dgItemFileTypeService.selectByPrimaryKey(id);
            currentNum = seqService.getSeq("dgitem-small-" + dgitem.getNum(), 4, 1, dgitem.getNum(), 0, "");
            List<DgItemFileType> dgItemFileBigTypes = new ArrayList<DgItemFileType>();
            dgItemFileBigTypes.add(dgitem);
            model.addAttribute("dgItemFileBigTypes", dgItemFileBigTypes);
            List<DgProMethodsType> dgProMethodsTypes = dgProMethodsTypeService.selelctAllTypes();
            model.addAttribute("dgProMethodsTypes", dgProMethodsTypes);
            List<DgPublicCode0> sczt = dgPublicCode0Service.selectPublicCodeByKey("SCZT");
            model.addAttribute("sczt", sczt);
        }
        DgItemFileType dgItemFileType = null;
        dgItemFileType = new DgItemFileType();
        dgItemFileType.setNum(currentNum);
        model.addAttribute("dgItemFileType", dgItemFileType);
        model.addAttribute("number", currentNum);
        model.addAttribute("flag", flag);
        return "archive/itemFile/item_file_type_edit";
    }

    /**
     * 品项类别 edit
     *
     * @param flag 1 大类 2 小类
     * @param id id
     * @param model
     * @return
     */
    @RequestMapping("/toFileTypeEdit")
    public String toFileBigTypeEdit(Integer flag, Integer id, Model model) {
        if (flag == 2) {
            List<DgItemFileType> dgItemFileBigTypes = dgItemFileTypeService.selectAllItemFileBigType(null);
            model.addAttribute("dgItemFileBigTypes", dgItemFileBigTypes);
            List<DgProMethodsType> dgProMethodsTypes = dgProMethodsTypeService.selelctAllTypes();
            model.addAttribute("dgProMethodsTypes", dgProMethodsTypes);
            List<DgPublicCode0> sczt = dgPublicCode0Service.selectPublicCodeByKey("SCZT");
            model.addAttribute("sczt", sczt);
        }else{
            //公共代码查询品牌
            List<DgPublicCode0> pp = dgPublicCode0Service.selectPublicCodeByKey("PP");
            //公共代码查询品项类型
            List<DgPublicCode0> pxlxgl = dgPublicCode0Service.selectPublicCodeByKey("PXLXGL");
            model.addAttribute("pp", pp);
            model.addAttribute("pxlxgl", pxlxgl);
        }
        DgItemFileType dgItemFileType = null;
        if (id != null) {
            dgItemFileType = dgItemFileTypeService.selectByPrimaryKey(id);
            model.addAttribute("nodeParentId", dgItemFileType.getpId());
            model.addAttribute("qyz", dgItemFileType.getQyzzfflbxd());
            model.addAttribute("qids", dgItemFileType.getQyzzfflbids());
        }
        model.addAttribute("dgItemFileType", dgItemFileType);
        model.addAttribute("flag", flag);
        return "archive/itemFile/item_file_type_edit";
    }


    @ResponseBody
    @RequestMapping("/getItemFileSmallType/{bigTypeId}")
    public List<DgItemFileType> getItemFileSmallType(@PathVariable Integer bigTypeId){
        try {
            return dgItemFileService.selectSmallItemType(bigTypeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 品项修改
     *
     * @param id
     * @param nodeParentId
     * @param model
     * @return
     */
    @RequestMapping("/toItemFileEdit")
    public String toItemFileEdit(Integer id, Integer nodeId, Integer nodeParentId, Model model) {
        //所有品项大类
        List<DgItemFileType> dgItemFileTypes = dgItemFileTypeService.selectAllItemFileBigType();
        model.addAttribute("dgItemFileTypes", dgItemFileTypes);

        //销售类型
        List<DgPublicCode0> xslx = dgPublicCode0Service.selectPublicCodeByKey("XSLX");
        model.addAttribute("xslx", xslx);

        //品项辣度
        List<DgPublicCode0> pxld = dgPublicCode0Service.selectPublicCodeByKey("PXLD");
        model.addAttribute("pxld", pxld);

        //角标
        List<DgPublicCode0> jb = dgPublicCode0Service.selectPublicCodeByKey("JB");
        model.addAttribute("jb", jb);

        //打印分组
        List<DgPublicCode0> dyfz = dgPublicCode0Service.selectPublicCodeByKey("DYFZ");
        model.addAttribute("dyfz", dyfz);

        //获取所有的厨师
        List<SysUser> allCook = dgItemFileService.selectAllCook();
        model.addAttribute("allCook", allCook);

        //获取所有的基本单位
        List<DgPublicCode0> jbdw = dgPublicCode0Service.selectPublicCodeByKey("JBDW");
        model.addAttribute("jbdw", jbdw);

        /*树节点ID*/
        if (id != null) {
            DgItemFile dgItemFile = dgItemFileService.selectByPrimaryKey(id);
            model.addAttribute("dgItemFile", dgItemFile);

            if (!StringUtils.isEmpty(dgItemFile.getGgzyzfids())) {
                List<DgProMethods> ggMethods = dgItemFileService.selectGgzyz(dgItemFile.getGgzyzfids());
                model.addAttribute("ggMethods", ggMethods);
            }

            if (!StringUtils.isEmpty(dgItemFile.getZyzfids())) {
                List<DgProMethods> zyMethods = dgItemFileService.selectGgzyz(dgItemFile.getZyzfids());
                model.addAttribute("zyMethods", zyMethods);
            }

            if (!StringUtils.isEmpty(dgItemFile.getYyxgids())) {
                List<DgNutrition> nutritionInIds = dgItemFileService.getNutritionInIds(dgItemFile.getYyxgids());
                model.addAttribute("nutritionInIds", nutritionInIds);
            }
            List<DgItemFileZccf> zccfMethods = dgItemFileZccfService.getZccfByItemId(id);
            model.addAttribute("zccfMethods", zccfMethods);
            
            model.addAttribute("nodeParentId", dgItemFile.getPpdlId());
            model.addAttribute("nodeId", dgItemFile.getPpxlId());
        } else {
            DgItemFileType dbsmall = dgItemFileTypeService.selectByPrimaryKey(nodeId);
            String currentNum = seqService.getSeq("dgitem-" + dbsmall.getNum(), 4, 1, dbsmall.getNum(), 0, "");
            DgItemFile dgItemFile = new DgItemFile();
            dgItemFile.setNum(currentNum);
            model.addAttribute("dgItemFile", dgItemFile);
            model.addAttribute("nodeParentId", nodeParentId);
            model.addAttribute("nodeId", nodeId);
        }
        return "archive/itemFile/item_file_edit";
    }

    @RequestMapping("/getAllItemFileType")
    @ResponseBody
    public List<DgItemFileType> getAllItemFileType(Integer flag) {
        List<DgItemFileType> dgItemFileTypes = dgItemFileTypeService.selectAllItemFileType();
        if (flag != null) {
            DgItemFileType dgItemFileType = new DgItemFileType();
            dgItemFileType.setId(0);
            dgItemFileType.setName("品项分类");
            dgItemFileTypes.add(0, dgItemFileType);
        }
        return dgItemFileTypes;
    }

    @RequestMapping("/getAllBigItemFileType")
    @ResponseBody
    public List<DgItemFileType> getAllBigItemFileType(Integer packageId) {
        return dgItemFileTypeService.selectAllItemFileBigType(packageId);
    }

    @RequestMapping("/getItemFileTypeByParent")
    @ResponseBody
    public List<DgItemFileType> getItemFileTypeByParent(Integer parentId) {
        List<DgItemFileType> dgItemFileTypes = dgItemFileTypeService.getItemFileTypeByParent(parentId);
        return dgItemFileTypes;
    }

    /**
     * 查找品项大类下面的品项小类
     *
     * @param bItemTypeId
     * @return
     */
    @RequestMapping("/getSmallItemType")
    @ResponseBody
    public List<DgItemFileType> getSmallItemType(Integer bItemTypeId) {
        return dgItemFileService.selectSmallItemType(bItemTypeId);
    }

    /**
     * 根据品项类别查找具体的品项
     *
     * @param sItemTypeId
     * @return
     */
    @RequestMapping("/getItemFileByTypeId")
    @ResponseBody
    public List<DgItemFile> getItemFileByTypeId(Integer sItemTypeId) {
        return dgItemFileService.selectDataBySmallType(sItemTypeId);
    }

    @RequestMapping("/addItemFile")
    @ResponseBody
    public Object addItemFile(HttpServletRequest request) {
        try {
        	String name = request.getParameter("name");
            String id = request.getParameter("id");
            Map<String,Object> ret = new HashMap<String, Object>();
            DgItemFile dbItemFileName = dgItemFileService.getDgItemFileByName(name);

            if(dbItemFileName != null){
            	if (id != null && !id.equals("") && dbItemFileName.getId() == Integer.parseInt(id)) {
                    int itemId = dgItemFileService.insertSelectiveCus(request);
                    String ppxlid = request.getParameter("ppxlId");
                    DgItemFileType dgsmall = dgItemFileTypeService.selectByPrimaryKey(Integer.parseInt(ppxlid));
                    seqService.setUsedSeq("dgitem-" + dgsmall.getNum(), request.getParameter("num"));
                    ret.put("success",true);
                    ret.put("id",itemId);
                    return ret;
                } else {
                    return returnFail("名称重复");
                }
            }

        	int itemId = dgItemFileService.insertSelectiveCus(request);
            String ppxlid = request.getParameter("ppxlId");
            DgItemFileType dgsmall = dgItemFileTypeService.selectByPrimaryKey(Integer.parseInt(ppxlid));
            seqService.setUsedSeq("dgitem-" + dgsmall.getNum(), request.getParameter("num"));
            ret.put("success",true);
            ret.put("id",itemId);
            return ret;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/addItemFileType")
    @ResponseBody
    public ResultInfo addItemFileType(DgItemFileType dgItemFileType) {
    	Integer pId = 0;
    	if(dgItemFileType.getpId() != null){
    		pId = dgItemFileType.getpId();
    	}
        try {
            String zjf = dgItemFileType.getZjf();
            if(StringUtil.isBlank(zjf)){
                dgItemFileType.setZjf(YQSHTranslate.getPYIndexStr(dgItemFileType.getName(), true));
            }
            DgItemFileType dbItemType = dgItemFileTypeService.getDgItemFileTypeByName(pId,dgItemFileType.getName());
            if (dgItemFileType.getId() != null) { //修改
				if(dbItemType != null){
					if (dgItemFileType.getId() != null && dbItemType.getId() == dgItemFileType.getId()) {
						if (dgItemFileType.getpId() == null) {
							dgItemFileType.setpId(0);
							seqService.setUsedSeq("dgitem-big-type", dgItemFileType.getNum());
						} else {
							DgItemFileType pitem = dgItemFileTypeService.selectByPrimaryKey(dgItemFileType.getpId());
							seqService.setUsedSeq("dgitem-small-" + pitem.getNum(), dgItemFileType.getNum());
						}
						dgItemFileTypeService.updateByPrimaryKey(dgItemFileType);
						return returnSuccess();
					} else {
						return returnFail("名称重复");
					}
				}else{
					dgItemFileTypeService.updateByPrimaryKey(dgItemFileType);
					return returnSuccess();
				}
            } else {
            	if(dbItemType == null){
	                if (dgItemFileType.getpId() == null) {
	                    dgItemFileType.setpId(0);
	                    seqService.setUsedSeq("dgitem-big-type", dgItemFileType.getNum());
	                } else {
	                    DgItemFileType pitem = dgItemFileTypeService.selectByPrimaryKey(dgItemFileType.getpId());
	                    seqService.setUsedSeq("dgitem-small-" + pitem.getNum(), dgItemFileType.getNum());
	                }
	                dgItemFileTypeService.insertSelective(dgItemFileType);
	                return returnSuccess();
            	}else{
            		return returnFail("名称重复");
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    /*制作方法*/
    @RequestMapping("/toPublicProMethodsChoose")
    public String toPublicProMethodsChoose(String InIds, Model model) {
        if (!StringUtils.isEmpty(InIds)) {
            List<DgProMethods> proMeInIds = dgProMethodsSerivce.getPubProMeInIds(InIds);
            model.addAttribute("proMeInIds", proMeInIds);
        }
        return "archive/itemFile/item_file_publicProMethods";
    }

    @RequestMapping("/getAllProMethodsType")
    @ResponseBody
    public List<DgProMethodsType> getAllProMethodsType() {
        return dgProMethodsTypeService.selelctAllTypes();
    }

    /**
     * 根据ztree node获取排除指定ID的数据
     *
     * @param withOutIds
     * @param pmtid
     * @return
     */
    @RequestMapping("/getProMethodsByType")
    @ResponseBody
    public List<DgProMethods> getProMethodsByType(String withOutIds, Integer pmtid) {
        return dgProMethodsSerivce.getProMethodsByType(withOutIds, pmtid);
    }

    @RequestMapping("/getPubProMeByIds")
    @ResponseBody
    public List<DgProMethods> getPubProMeByIds(String ids) {
        return dgProMethodsSerivce.getPubProMeInIds(ids);
    }

    /*制作方法end*/

 /*营养效果*/
    @RequestMapping("/showNuritionIndex")
    public String showNuritionIndex() {
        return "archive/itemFile/item_file_nutrition_index";
    }

    @RequestMapping("/nutritionEdit")
    public String nutritionEdit(Integer id, Model model) {
        if (id != null) {
            DgNutrition dgNutrition = dgItemFileService.selectNutritionById(id);
            model.addAttribute("dgNutrition", dgNutrition);
        }
        return "archive/itemFile/item_file_nutrition_edit";
    }

    @RequestMapping("/selectAllNutrition")
    @ResponseBody
    public Page<DgNutrition> selectAllNutrition(DgNutrition dgNutrition) {
        Page<DgNutrition> pagebean = null;
        try {
            pagebean = dgItemFileService.selectAllNutrition(dgNutrition);
            pagebean.setPage(dgNutrition.getPage());
            pagebean = (Page<DgNutrition>) pageResult(pagebean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pagebean;
    }

    @RequestMapping("/editNurition")
    @ResponseBody
    public ResultInfo editNurition(DgNutrition dgNutrition) {
        try {
            dgItemFileService.editNutrition(dgNutrition);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/delNurition")
    @ResponseBody
    public ResultInfo delNurition(String ids) {
        try {
            dgItemFileService.delNutrition(ids);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/nutritionChoose")
    public String nutritionChoose(String ids, Model model) {
        List<DgNutrition> nutritionNotInIds = dgItemFileService.getNutritionNotInIds(ids);
        if (ids != null) {
            List<DgNutrition> nutritionInIds = dgItemFileService.getNutritionInIds(ids);
            model.addAttribute("nutritionInIds", nutritionInIds);
        }
        model.addAttribute("nutritionNotInIds", nutritionNotInIds);
        return "archive/itemFile/item_file_nutrition_choose";
    }

    /*营养效果end*/
 /*套餐 start*/
    @RequestMapping("/tcIndex/{id}")
    public String tcIndex(@PathVariable Integer id, Model model) {
        /*套餐基本信息查询*/
        DgItemFile dgItemFile = dgItemFileService.selectByPrimaryKey(id);

        /*套餐固定的基本信息*/
        DgItemFilePackage packageBaseInfo = dgItemFileService.selectTcBaseInfo(id);
        model.addAttribute("packageBaseInfo", packageBaseInfo);

        model.addAttribute("dgItemFile", dgItemFile);
        return "archive/itemFile/item_file_tc_index";
    }

    @RequestMapping("/getPackageTableInfo/{packageId}")
    @ResponseBody
    public Map<String, Object> getPackageTableInfo(@PathVariable Integer packageId) {
        Map<String, Object> res = new HashMap<>();
        List<Map> bmap = dgItemFileService.selectTcBaseInfoMap(packageId);
        List<Map> bxInfo = dgItemFileService.selectTcBxInfo(packageId);
        List<Map> kxInfo = dgItemFileService.selectTcKxInfo(packageId);
        List<Map> thpxInfo = dgItemFileService.selectTcThpxInfo(packageId);
        Map<String, List<Map>> thpxMap = new HashMap<>();
        for (int i = 0; i < thpxInfo.size(); i++) {
            Map thInfo = thpxInfo.get(i);
            String bxpxId = thInfo.get("bxpxid").toString();
            if (thpxMap.containsKey(bxpxId)) {
                thpxMap.get(bxpxId).add(thInfo);
            } else {
                List<Map> value = new ArrayList<>();
                value.add(thInfo);
                thpxMap.put(bxpxId, value);
            }
        }
        res.put("bxInfo", bxInfo);
        res.put("kxInfo", kxInfo);
        res.put("thpxInfo", thpxMap);
        res.put("bmap", bmap);
        return res;
    }

    /**
     * 套餐品项选择页面
     *
     * @param id 当前选择的套餐
     * @param tcYxPxIds 套餐已经选择的品项
     * @param model
     * @return
     */
    @RequestMapping("/toTcPxChoose")
    public String toTcPxChoose(Integer id, String tcYxPxIds, Model model) {
        model.addAttribute("selectedTcId", id);
        model.addAttribute("tcYxPxIds", tcYxPxIds);
        return "archive/itemFile/item_file_tc_px_choose";
    }

    /**
     * 点击套餐品项选择，查询的品项数据
     *
     * @param id 树节点 品项类别的ID
     * @param selectedTcId 当前正在操作的套餐品项ID 需排除
     * @param tcPxNotInIds 已经选择的品项ID 需排除
     * @return
     */
    @RequestMapping("/tcPxChooseData")
    @ResponseBody
    public List<DgItemFile> tcPxChooseData(Integer id, Integer selectedTcId, String tcPxNotInIds) {
        List<DgItemFile> dgItemFiles = dgItemFileService.selectItemFileInTc(id, selectedTcId, tcPxNotInIds);
        return dgItemFiles;
    }

    @RequestMapping("/getItemFileInIds")
    @ResponseBody
    public List<DgItemFile> getItemFileInIds(String inIds) {
        if (!StringUtils.isEmpty(inIds)) {
            return dgItemFileService.selectItemFileInIds(inIds);
        }
        return null;
    }

    /**
     * 根据已选表格获取未选的不是套餐的品项
     *
     * @param inIds
     * @return
     */
    @RequestMapping("/getItemFileYxInIds")
    @ResponseBody
    public List<DgItemFile> getItemFileYxInIds(String inIds) {
        if (!StringUtils.isEmpty(inIds)) {
            return dgItemFileService.getItemFileYxInIds(inIds);
        }
        return null;
    }

    /**
     * 套餐信息修改
     *
     * @param bxpxData 必选品项数据
     * @param countData 必选品项统计数据
     * @param kxpxData 可选品项数据
     * @param thpxData 替换品项数据
     * @param slxdData 数量限定数据
     * @param tcName 套餐名称
     * @param tcPrice 套餐价格
     * @param tcType 套餐类型
     * @param tcWithNum 宴会套餐数量跟套餐明细有关
     * @param canUpdate 允许修改可选品数量
     * @param minNum 可选品项最小数
     * @param maxNum 可选品项最大数
     * @param itemFileId 套餐的 itemFile 表ID
     * @param tcId 套餐的 itemFile 表ID
     * @return
     */
    @RequestMapping("/editTc")
    @ResponseBody
    public ResultInfo editTc(String bxpxData, String countData, String kxpxData, String thpxData, String slxdData,
            String tcName, Double tcPrice, String tcType, String tcWithNum, String canUpdate,
            String minNum, String maxNum, String tcId, String itemFileId) {
        try {
            dgItemFileService.editTc(bxpxData, countData, kxpxData, thpxData, slxdData,
                    tcName, tcPrice, tcType, tcWithNum, canUpdate,
                    minNum, maxNum, tcId, itemFileId);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    /*套餐 end*/
    @RequestMapping("/deleteItemFile")
    @ResponseBody
    public ResultInfo deleteItemFile(Integer id) {
        try {
            dgItemFileService.deleteItemFile(id);
            dgJointDeleteService.deleteJointItem(id);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/delItemType")
    @ResponseBody
    public ResultInfo delItemType(Integer id) {
        try {
            DgItemFileType dgitem = dgItemFileTypeService.selectByPrimaryKey(id);
            dgItemFileTypeService.deleteByPrimaryKey(id);
            dgJointDeleteService.deleteJointGate(id);
            //删除数据库计数器 
            if (dgitem.getpId() == 0) {
                seqService.deleteSeqByType("dgitem-small-" + dgitem.getNum());
            } else {
                seqService.deleteSeqByType("dgitem-" + dgitem.getNum());
            }
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }
    
    
    
    /**
     * 组成成分
     *
     * @return
     */
    @RequestMapping("/toZccfChoose")
    public String toZccfChoose() {
        return "archive/itemFile/item_file_zccf";
    }
    
    /**
     * 
     * 组成成分,左侧树菜单
     * @return
     */
    @RequestMapping("/getTreeNodes")
    @ResponseBody
    public  List<DgItemType> getTreeNodes()
    {
    	String id = getRequest().getParameter("id");
    	List<DgItemType> menu = new ArrayList<DgItemType>();
    	
    	//获取根节点
    	if(StringUtils.isEmpty(id))
    	{
    		DgItemType m =new DgItemType();
    		m.setId("-1");
    		m.setItemTypeName("成分分类");
    		menu.add(m);
    	}
    	else
    	{    		
    		if(id.equals("-1"))
    		{
    			DgItemType src = new DgItemType();
    			src.setState("0");
    			//获取父类
    			menu = dgItemTypeService.listItemType(src);
    		}
    	}	
    	return menu;
    }
    
    /**
     * 获取未包含的组成成分
     * @param request
     * @return
     */
    
    @RequestMapping("/getZccfItem")
    @ResponseBody
    public Object getZccfItem(HttpServletRequest request)
    {
    	List<Map<String,Object>> items;
    	String ids = request.getParameter("ids");
    	String treeId = request.getParameter("treeId");
    	String search = request.getParameter("search");
    	List idlist = new ArrayList();
	    Collections.addAll(idlist,ids.split(","));
	    if(StringUtils.isEmpty(search))
	    {
	    	if(treeId == null || treeId.equals("-1"))
	    	{
	    		Map<String,Object> map = new HashMap<String, Object>();
	    		map.put("ids",idlist);
	    		items = inveItemsService.getByTreeId(map);
	    	}
	    	else
	    	{
	    		Map<String,Object> map = new HashMap<String, Object>();
	    		map.put("ids",idlist);
	    		map.put("treeId",treeId);
	    		items = inveItemsService.getByTreeId(map);
	    	}	
	    }
	    else
	    {
    		Map<String,Object> map = new HashMap<String, Object>();
    		map.put("ids",idlist);
    		map.put("search",search);
    		items = inveItemsService.getByTreeId(map);
	    }
	    return items;
    }
    /**
     * 
     * 包含的成分
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHaveDish")
    @ResponseBody
    public Object getHaveDish(HttpServletRequest request)
    {
    	
    	String ids = request.getParameter("ids");
    	if(StringUtils.isEmpty(ids))
    	{
    		return null;
    	}
    	else
    	{
    		List<Map<String,Object>> items = inveItemsService.selectHaveItem(ids);
    		return items;
    	}
    }
    
    /**
     * 
     * 包含的成分
     * @param request
     * @return
     */
    @RequestMapping(value = "/addItemFileNext")
    @ResponseBody
    public Object addItemFileNext(HttpServletRequest request)
    {
    	
    	try{
    		dgItemFileZccfService.insertList(request);
    		return returnSuccess();
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	 return returnFail();
    }

    @RequestMapping("/saveItemIcon")
    @ResponseBody
    public ResultInfo saveItemIcon(HttpServletRequest request) {
        String fileName = null;
        try {
            MultiValueMap<String, MultipartFile> fileMaps = ((DefaultMultipartHttpServletRequest) request).getMultiFileMap();

            Iterator itf = fileMaps.entrySet().iterator();
            while (itf.hasNext()) {
                Map.Entry<String, List<MultipartFile>> m = (Map.Entry<String, List<MultipartFile>>) itf.next();
                MultipartFile multipartFile = m.getValue().get(0);
                if (!StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
                    String filePath = request.getServletContext()
                            .getRealPath("/itemicon");
                    String oldfileName = multipartFile.getOriginalFilename();
                    oldfileName = oldfileName.substring(oldfileName.lastIndexOf("."));
                    fileName = System.currentTimeMillis() + oldfileName;
                    File targetFile = new File(filePath, fileName);
                    File fpath = new File(filePath);
                    if (!fpath.exists()) {
                        fpath.mkdirs();
                    }
                    multipartFile.transferTo(targetFile);
                }
            }
            return returnSuccess(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }
    
    @RequestMapping(value = "/toCatLafbttp", method = RequestMethod.POST)
	public String toCatLafbttp(String imageName,Model model) {
        model.addAttribute("imagename", imageName);
		return "archive/itemFile/catNewsbttp";
	}
	
	@RequestMapping("/catNewsBttp")
	@ResponseBody
	public ResultInfo catNewsBttp(HttpServletRequest request) {
		String imagename = request.getParameter("imagename");
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		String x = request.getParameter("x");
		String y = request.getParameter("y");
		String boundx = request.getParameter("boundx");
		String boundy = request.getParameter("boundy");

        String path = request.getServletContext().getRealPath(
                "/itemconcat");
        File fpath = new File(path);
        if (!fpath.exists()) {
            fpath.mkdirs();
        }

        String oldImage = request.getServletContext().getRealPath(
                "/itemicon")
                + File.separator + imagename;
        File oldFile = new File(oldImage);
        try {
            if(StringUtil.isEmpty(width)){
                String catimagepath = request.getServletContext().getRealPath(
                        "/itemconcat")
                        + File.separator + imagename;
                File newFile = new File(catimagepath);

                Files.copy(oldFile,newFile);

                oldFile.delete();

                return returnSuccess(imagename);
            }else{
                String imagepath = request.getServletContext().getRealPath(
                        "/itemicon")
                        + File.separator + imagename;
                File picture = new File(imagepath);
                FileInputStream is = new FileInputStream(picture);
                BufferedImage sourceImg = ImageIO.read(is);
                int realW = sourceImg.getWidth();
                int realH = sourceImg.getHeight();
                is.close();
                double bx = Double.parseDouble(boundx);
                double by = Double.parseDouble(boundy);
                double catW = (realW / bx) * Integer.parseInt(width);
                double catH = (realH / by) * Integer.parseInt(height);

                double realX = (realW / bx) * Integer.parseInt(x);
                double realY = (realH / by) * Integer.parseInt(y);

                Iterator<ImageReader> it = ImageIO
                        .getImageReadersByFormatName(imagename.substring(imagename
                                .lastIndexOf(".") + 1));
                ImageReader reader = it.next();
                is = new FileInputStream(picture);
                ImageInputStream iis = ImageIO.createImageInputStream(is);
                reader.setInput(iis, true);

                ImageReadParam param = reader.getDefaultReadParam();
                Rectangle rect = new Rectangle((int) realX, (int) realY,
                        (int) catW, (int) catH);
                param.setSourceRegion(rect);
                String catimagepath = request.getServletContext().getRealPath(
                        "/itemconcat")
                        + File.separator + imagename;
                BufferedImage bi = reader.read(0, param);
                ImageIO.write(bi, imagename.substring(imagename.lastIndexOf(".") + 1), new File(catimagepath));
                is.close();
                iis.close();
                oldFile.delete();
                return returnSuccess(imagename);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
	}
	
	
	//品项组成成分
    @RequestMapping("/zccfManager.jspx")
    public String zccfManager(Model model) {
    	String id = getRequest().getParameter("id");
        List<DgItemFileZccf> zccfMethods = dgItemFileZccfService.getZccfByItemId(Integer.valueOf(id));
        model.addAttribute("zccfMethods", zccfMethods);
        return "archive/itemFile/item_file_zccf_edit";
    }
    
  //颜色编辑主页
    @RequestMapping("/colorManager")
    public String colorManager(Model model) {
        return "archive/itemFile/item_file_color_manager";
    }
    
    
    @RequestMapping("/getColorDate")
    @ResponseBody
    public List<DgItemFileColor> getColorDate(String shopKey) {
    	List<DgItemFileColor> dgItemFileColors = dgItemFileColorService.selectAllItemFileColor();
        return dgItemFileColors;
    }

    /**
     * 2017年6月19日13:42:54
     * @param searchText 模糊搜索的条件
     * @param searchFlag 所搜的标记，
     *                   PS：1、代表左边树节点的选择为顶级节点，查询全部大类和小类
     *                      2、代表左边树节点选的是大类
     *                      3、代表左边树节点选的是小类
     * @param typeId 大类|小类 ID
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchItemFile")
    public List<DgItemFile> searchItemFile(String searchText,Integer searchFlag,Integer typeId){
        List<DgItemFile> dgItemFiles = dgItemFileService.
                searchItemFile(searchText,searchFlag,typeId);
        return dgItemFiles;
    }
    
    /**
     * 跳转至品项排序
     * @param model
     * @return
     */
    @RequestMapping("/goRank")
    public ModelAndView trash(Model model){
    	List<DgItemFileType> itemFileSmallTypeList = dgItemFileTypeService.getItemFileSmallTypeRankList();
    	model.addAttribute("itemFileSmallTypeList", itemFileSmallTypeList);
        ModelAndView modelAndView = new ModelAndView("archive/itemFile/item_file_rank");
        return modelAndView;
    }
    
   /********************* 品项排序 start *********************/ 
   /**
    *
    * 获取品项排序数据
    * @return
    */
   @RequestMapping("/getItemFileRankList")
   @ResponseBody
   public List getItemFileRankList(Integer ppxlId)
   {
       Integer itemFileRankCount = dgItemFileService.getItemFileRankCount();
       if(itemFileRankCount == 0){
    	   //查询所有要初始排序值的品项
    	   List<DgItemFile> itemFileRankList = dgItemFileService.getItemFileRankList(null);
    	   //初始化品项rank值
    	   //注：品项排序初始化rank排序值为查询数据顺序
    	   Map<String,Object> map = null;
    	   for (int i = 0; i < itemFileRankList.size(); i++) {
    		   map = new HashMap<>();
    		   DgItemFile dgItemFile = itemFileRankList.get(i);
    		   if(dgItemFile != null){
    			   map.put("id", dgItemFile.getId());
    			   map.put("rank", i+1);
    			   //添加排序值至品项排序表
    			   dgItemFileService.addItemFileRank(map);
    		   }
    	   }
       }else{
    	   //查询所有没有初始排序值的品项
    	   List<DgItemFile> itemFileNotRankList = dgItemFileService.getItemFileNotRankList();
    	   Map<String,Object> map = null;
    	   for (int i = 0; i < itemFileNotRankList.size(); i++) {
    		   DgItemFile dgItemFile = itemFileNotRankList.get(i);
    		   //获取品项小类Id查询该小类下品项rank最大值
    		   Integer xlId = dgItemFile.getPpxlId();
    		   Integer largeRank = dgItemFileService.getItemFileLargeRankByPpxlId(xlId);
    		   //添加该品项rank值
    		   map = new HashMap<>();
    		   Integer id = dgItemFile.getId();
    		   map.put("id", id);
    		   if(largeRank == null){
    			   largeRank = 10000;
    		   }
			   map.put("rank", largeRank + 1);
			   //根据最大rank值递增比最大rank值大的rank排序值
			   dgItemFileService.updateItemFileRankIncreaseByRank(largeRank);
    		   dgItemFileService.addItemFileRank(map);
    	   }
       }
       
       List<DgItemFile> itemFileRankList = dgItemFileService.getItemFileRankList(ppxlId);
       return itemFileRankList;
   }
    
    /**
     * 品项排序管理
     * @param id1 被移动的品项id
     * @param id2 被动移动的品项id
     * @param moveType 移动类型 0-上移；1-下移；2-置顶
     * @return
     */
    @RequestMapping(value = "/doItemRank")
    @ResponseBody
    public Object doItemRank(Integer id1,Integer rank1,Integer id2,Integer rank2,Integer moveType) {
        Map<String,Object> result = new HashMap<String,Object>();
        try{
        	// 向上移动或者 向下移动
			if(moveType == 0 || moveType == 1){ 
				dgItemFileService.updateItemFileRankMove(id1,rank2);
				dgItemFileService.updateItemFileRankMove(id2,rank1);
				/*dgItemFileService.updateItemFileRankMoveUp(id1);
				dgItemFileService.updateItemFileRankMoveDown(id2);*/
				result.put("success", true);
			}
			// 向下移动
			/*if(moveType == 1){ 
				dgItemFileService.updateItemFileRankMoveDown(id1);
				dgItemFileService.updateItemFileRankMoveUp(id2);
				result.put("success", true);
			}*/
			// 置顶
			if(moveType == 2){ 
				
				DgItemFile dgItemFile = dgItemFileService.selectByPrimaryKey(id1);
				//获取品项小类Id查询该小类下品项rank最小值
    		    Integer xlId = dgItemFile.getPpxlId();
    		    Integer smallRank = dgItemFileService.getItemFileSmallRankByPpxlId(xlId);
    		    //置顶品项rank值小的品项rank + 1
    		    Map<String,Object> params = new HashMap<String,Object>();
				params.put("xlId", xlId);
				params.put("checkRank", rank1);
    		    dgItemFileService.updateItemFileRankIncreaseByRankAndPpxlId(params);
    		    //将置顶品项设置最小rank值
    		    params.put("id", id1);
				params.put("rank", smallRank);
    		    dgItemFileService.updateItemFileRank(params);
    		    result.put("success", true);
				
			}
        }catch(Exception e){
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        return result;
    }
    
    /********************* 品项排序 end *********************/ 
    /********************* 品项小类排序 start *********************/ 
    
    /**
    *
    * 获取品项小类排序数据
    * @return
    */
   @RequestMapping("/getItemFileSmallTypeRankList")
   @ResponseBody
   public List getItemFileSmallTypeRankList()
   {
       List<DgItemFileType> itemFileSmallTypeRankList = dgItemFileTypeService.getItemFileSmallTypeRankList();
       //初始化品项rank值
       List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
       Map<String,Object> map = null;
       for (int i = 0; i < itemFileSmallTypeRankList.size(); i++) {
           map = new HashMap<>();
           DgItemFileType dgItemFileType = itemFileSmallTypeRankList.get(i);
           if(dgItemFileType != null){
               map.put("id", dgItemFileType.getId());
               map.put("num", dgItemFileType.getNum());
               map.put("name", dgItemFileType.getName());
               map.put("rank", i+1);
               listMap.add(map);
               //初始化结算方式排序表
               Map<String,Object> itemFileSmallTypeRankMap = dgItemFileTypeService.selItemFileSmallTypeRank(map);
               if(itemFileSmallTypeRankMap == null){
            	   dgItemFileTypeService.addItemFileSmallTypeRank(map);
               }
           }
       }
       return listMap;
   }
   
    /**
     * 品项小类排序管理
     * @param id1 被移动的品项小类id
     * @param id2 被动移动的品项小类id
     * @param moveType 移动类型 0-上移；1-下移；2-置顶
     * @return
     */
    @RequestMapping(value = "/doItemSmallTypeRank")
    @ResponseBody
    public Object doItemSmallTypeRank(Integer id1,Integer id2,Integer moveType) {
        Map<String,Object> result = new HashMap<String,Object>();
        try{
        	// 向上移动
			if(moveType == 0){ 
				dgItemFileTypeService.updateItemFileSmallTypeRankMoveUp(id1);
				dgItemFileTypeService.updateItemFileSmallTypeRankMoveDown(id2);
				result.put("success", true);
			}
			// 向下移动
			if(moveType == 1){ 
				dgItemFileTypeService.updateItemFileSmallTypeRankMoveDown(id1);
				dgItemFileTypeService.updateItemFileSmallTypeRankMoveUp(id2);
				result.put("success", true);
			}
			// 置顶
			if(moveType == 2){ 
				List<String> ids = dgItemFileTypeService.selectItemFileRankSmallTypeMoveTopper(id1);
				if(ids.size() > 0){
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("id", id1);
					params.put("rank", 1);
					dgItemFileTypeService.updateItemFileRankSmallTypeMoveTopper(ids);
					dgItemFileTypeService.updateItemFileSmallTypeRank(params);
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
    
    /********************* 品项小类排序 end *********************/
    /**
     * 根据品项类别查找具体的品项
     *
     * @param sItemTypeId
     * @return
     */
    @RequestMapping("/exportXls")
    public String exportXls(Model model,Integer sItemTypeId) {
        List<DgItemFile> dgItemFiles =  dgItemFileService.selectDataBySmallType(sItemTypeId);
        model.addAttribute("dgItemFiles",dgItemFiles);
        return "archive/itemFile/exportXls";
    }

}