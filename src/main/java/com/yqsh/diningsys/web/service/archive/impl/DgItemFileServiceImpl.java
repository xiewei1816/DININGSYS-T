package com.yqsh.diningsys.web.service.archive.impl;

import java.lang.reflect.Field;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscount;
import com.yqsh.diningsys.web.model.report.ItemFileSell;
import com.yqsh.diningsys.web.util.TableQueryUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.YQSHTranslate;
import com.yqsh.diningsys.web.dao.DgNutritionMapper;
import com.yqsh.diningsys.web.dao.archive.DgItemFileMapper;
import com.yqsh.diningsys.web.dao.archive.DgItemFilePackageBxMapper;
import com.yqsh.diningsys.web.dao.archive.DgItemFilePackageKxMapper;
import com.yqsh.diningsys.web.dao.archive.DgItemFilePackageMapper;
import com.yqsh.diningsys.web.dao.archive.DgItemFilePackageSlxdMapper;
import com.yqsh.diningsys.web.dao.archive.DgItemFilePackageThMapper;
import com.yqsh.diningsys.web.dao.archive.DgItemFileTypeMapper;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFilePackage;
import com.yqsh.diningsys.web.model.archive.DgItemFilePackageBx;
import com.yqsh.diningsys.web.model.archive.DgItemFilePackageKx;
import com.yqsh.diningsys.web.model.archive.DgItemFilePackageSlxd;
import com.yqsh.diningsys.web.model.archive.DgItemFilePackageTh;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.archive.DgNutrition;
import com.yqsh.diningsys.web.model.archive.DgProMethods;
import com.yqsh.diningsys.web.service.archive.DgItemFileService;
import com.yqsh.diningsys.web.service.archive.DgProMethodsSerivce;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-10-13 16:55
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
public class DgItemFileServiceImpl extends GenericServiceImpl<DgItemFile,Integer> implements DgItemFileService{

    @Resource
    private DgItemFileMapper dgItemFileMapper;
    @Resource
    private DgItemFileTypeMapper dgItemFileTypeMapper;
    @Resource
    private DgNutritionMapper dgNutritionMapper;
    @Resource
    private DgProMethodsSerivce DgProMethodsSerivce;
    @Resource
    private DgItemFilePackageMapper dgItemFilePackageMapper;
    @Resource
    private DgItemFilePackageBxMapper dgItemFilePackageBxMapper;
    @Resource
    private DgItemFilePackageKxMapper dgItemFilePackageKxMapper;
    @Resource
    private DgItemFilePackageSlxdMapper dgItemFilePackageSlxdMapper;
    @Resource
    private DgItemFilePackageThMapper dgItemFilePackageThMapper;
    @Override
    public GenericDao<DgItemFile, Integer> getDao() {
        return dgItemFileMapper;
    }

    /**
     * 查询品项数据
     * @param id 品项大类ID
     * @return
     */
    @Override
    public List<DgItemFileType> selectSmallItemType(Integer id){
        return dgItemFileTypeMapper.selectSmallItemTypeId(id);
    }

    @Override
    public List<DgItemFile> selectDataBySmallType(Integer id) {
        Map param = new HashMap();
        param.put("id",id);
        return dgItemFileMapper.selectDataByItemTypeId(param);
    }

    @Override
    public Page<DgNutrition> selectAllNutrition(DgNutrition dgNutrition) {
        List<DgNutrition> list = dgNutritionMapper.selectAllDgNutrition(dgNutrition);
        return PageUtil.getPage(list.size(), dgNutrition.getPage(),list, dgNutrition.getRows());
    }

    @Override
    public DgNutrition selectNutritionById(Integer id) {
        return dgNutritionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void editNutrition(DgNutrition dgNutrition) {
        if(dgNutrition.getId() != null){
            dgNutritionMapper.updateByPrimaryKeySelective(dgNutrition);
        }else
            dgNutritionMapper.insertSelective(dgNutrition);
    }

    @Override
    public void delNutrition(String ids) {
        dgNutritionMapper.deleteNutritionByIds(arraysTOList(ids));
    }

    @Override
    public List<DgNutrition> getNutritionNotInIds(String ids) {
        return dgNutritionMapper.getNutritionNotInIds(arraysTOList(ids));
    }

    @Override
    public List<DgNutrition> getNutritionInIds(String ids) {
        return dgNutritionMapper.getNutritionInIds(arraysTOList(ids));
    }

    @Override
    public int insertSelectiveCus(HttpServletRequest request) {
        try {
            DgItemFile dgItemFile = new DgItemFile();
            Map<String, String[]> StringsValues = request.getParameterMap();
            Iterator it = StringsValues.entrySet().iterator();
            Class itemFileClass = dgItemFile.getClass();
            while (it.hasNext()){
                Map.Entry<String,String[]> entry = (Map.Entry)it.next();
                Field field = itemFileClass.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                if(StringUtils.isEmpty(entry.getValue()[0])){
                    field.set(dgItemFile,null);
                }else{
                    if(field.getType() == Integer.class){
                        field.set(dgItemFile,Integer.parseInt(entry.getValue()[0]));
                    }else if(field.getType() == Double.class){
                        field.set(dgItemFile,Double.parseDouble(entry.getValue()[0]));
                    }else{
                        field.set(dgItemFile,entry.getValue()[0]);
                    }
                }
            }

//            MultiValueMap<String,MultipartFile> fileMaps = ((DefaultMultipartHttpServletRequest) request).getMultiFileMap();
//
//            Iterator itf = fileMaps.entrySet().iterator();
//            while(itf.hasNext()){
//                Map.Entry<String, List<MultipartFile>> m = (Map.Entry<String, List<MultipartFile>>) itf.next();
//                MultipartFile multipartFile = m.getValue().get(0);
//                if (!StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
//                    String uploadPth = null;
//                    if(m.getKey().equalsIgnoreCase("pxdt")){
//                        uploadPth = "itemFilePathDt";
//                    }else{
//                        uploadPth = "itemFilePathXt";
//                    }
//                    String fileName = FileUploadUtil.fileUpload(request,uploadPth,multipartFile);
//                    if(m.getKey().equalsIgnoreCase("pxdt")){
//                        dgItemFile.setPxdt(fileName);
//                    }else{
//                        dgItemFile.setPxxt(fileName);
//                    }
//                }
//            }
            
            
            String cKeyWD = dgItemFile.getZjf();
            if (StringUtil.isBlank(cKeyWD)) {
                dgItemFile.setZjf(YQSHTranslate.getPYIndexStr(dgItemFile.getName(), true));
            }

            if(dgItemFile.getId() != null){
                /*图片操作*/
                DgItemFile dgItemFile1 = dgItemFileMapper.selectByPrimaryKey(dgItemFile.getId());

                //品项大图被删除
                if(dgItemFile.getIsDelPxdt() != null && dgItemFile.getIsDelPxdt()==1){
                    dgItemFile.setPxdt(null);
                }

                //品项小图被删除
                if(dgItemFile.getIsDelPxxt() != null &&dgItemFile.getIsDelPxxt()==1){
                    dgItemFile.setPxxt(null);
                }

                //品项大图未变化，使用原来的
                if(dgItemFile.getReUploadPxdt() == null && dgItemFile.getIsDelPxdt() == null){
                    dgItemFile.setPxdt(dgItemFile1.getPxdt());
                }

                //品项小图未变化，使用原来的
                if(dgItemFile.getReUploadPxxt() == null && dgItemFile.getIsDelPxxt() == null){
                    dgItemFile.setPxxt(dgItemFile1.getPxxt());
                }
                //设置修改时间
                dgItemFile.setUpdateTime(new Date());

                 dgItemFileMapper.updateByPrimaryKeySelective(dgItemFile);
                return dgItemFile.getId();
            }else{
                DgItemTypeDiscount dgItemTypeDiscount = dgItemFileMapper.selectDefaultDiscount(dgItemFile.getPpxlId());
                if(dgItemTypeDiscount != null){
                    if(dgItemTypeDiscount.getDiscount() == 1){
                        dgItemFile.setYxdz(1);
                    }
                }

                dgItemFileMapper.insertSelective(dgItemFile);
                return dgItemFile.getId();
            }

        } catch (Exception e) {
            throw new RuntimeException("addItemFile Exception",e);
        }
    }

    @Override
    public List<DgProMethods> selectGgzyz(String ids) {
        return DgProMethodsSerivce.getPubProMeInIds(ids);
    }

    @Override
    public List<DgItemFile> selectItemFileInTc(Integer id,Integer selectedTcId,String tcPxNotInIds) {
        Map param = new HashMap();
        param.put("id",id);
        param.put("selectedTcId",selectedTcId);
        param.put("list",arraysTOList(tcPxNotInIds));
        return dgItemFileMapper.selectItemFileInTc(param);
    }

    @Override
    public List<DgItemFile> selectItemFileInIds(String ids) {
        return dgItemFileMapper.selectItemFileInIds(arraysTOList(ids));
    }

    @Override
    public List<DgItemFile> getItemFileYxInIds(String ids) {
        return dgItemFileMapper.getItemFileYxInIds(arraysTOList(ids));
    }

    @Override
    public void editTc(String bxpxData,String countData,String kxpxData,String thpxData,String slxdData,
                       String tcName,Double tcPrice,String tcType,String tcWithNum,String canUpdate,
                       String minNum,String maxNum,String tcId,String itemFileId){
        /*套餐修改，先清表，后重新添加数据*/
        if(!StringUtils.isEmpty(tcId)){
            Integer packageId = Integer.parseInt(tcId);
            dgItemFilePackageMapper.deleteByPrimaryKey(packageId);
            dgItemFilePackageBxMapper.deleteByPakcageId(packageId);
            dgItemFilePackageKxMapper.deleteByPackageId(packageId);
            dgItemFilePackageThMapper.deleteByPackageId(packageId);
            dgItemFilePackageSlxdMapper.deleteByPackageId(packageId);
        }

        try {
            Gson gson = new Gson();
            List<Map> bxpxList = gson.fromJson(bxpxData,List.class);
            Map countMap = gson.fromJson(countData,Map.class);
            List<Map> kxpxList = gson.fromJson(kxpxData,List.class);
            Map thpxMap = gson.fromJson(thpxData,Map.class);
            List<Map> slxdList = gson.fromJson(slxdData,List.class);

            /*修改套餐品项基本数据*/
            DgItemFile dgItemFile = new DgItemFile();
            dgItemFile.setIsTc(1);
            dgItemFile.setId(Integer.parseInt(itemFileId));
            dgItemFile.setName(tcName);
            dgItemFile.setStandardPrice(tcPrice);
            dgItemFileMapper.updateByPrimaryKeySelective(dgItemFile);

           /*先添加套餐数据*/
            Integer packageNextId = dgItemFilePackageMapper.getNextPrimaryKey();
            packageNextId = packageNextId==null?1:packageNextId+1;

            DgItemFilePackage dgItemFilePackage = new DgItemFilePackage();
            dgItemFilePackage.setId(packageNextId);
            dgItemFilePackage.setItemFileId(Integer.parseInt(itemFileId));
            dgItemFilePackage.setPackageType(Integer.parseInt(tcType));
            dgItemFilePackage.setPackageBanquet(Integer.parseInt(tcWithNum));
            dgItemFilePackage.setPackageSum(Integer.parseInt(countMap.get("num")+""));
            dgItemFilePackage.setPackageAmountSum(Integer.parseInt(countMap.get("count")+""));
            dgItemFilePackage.setPackageStandardpriceSum(Double.parseDouble(countMap.get("standardPrice")+""));
            dgItemFilePackage.setPackageStandardpriceSumNum(Double.parseDouble(countMap.get("countXPrice")+""));
            dgItemFilePackage.setItemFileAddprice(Double.parseDouble(countMap.get("pxjj")+""));
            dgItemFilePackage.setCanupdateItemNum(Integer.parseInt(canUpdate));
            dgItemFilePackage.setMinNum(StringUtils.isEmpty(minNum)?null:Integer.parseInt(minNum));
            dgItemFilePackage.setMaxNum(StringUtils.isEmpty(maxNum)?null:Integer.parseInt(maxNum));

            dgItemFilePackageMapper.insertSelective(dgItemFilePackage);
        /*套餐基本数据添加完毕*/

        /*套餐必选品项 循环插入*/
            for(Map bxpxMap:bxpxList){
                DgItemFilePackageBx dgItemFilePackageBx = new DgItemFilePackageBx();
                dgItemFilePackageBx.setItemFileId(checkMapHasKeyReIn(bxpxMap,"itemFileId"));
                dgItemFilePackageBx.setPackageId(packageNextId);
                dgItemFilePackageBx.setItemAmount(checkMapHasKeyReIn(bxpxMap,"count"));
                dgItemFilePackageBx.setItemPrice(checkMapHasKeyReDou(bxpxMap,"standardPrice"));
                dgItemFilePackageBx.setItemAddprice(checkMapHasKeyReDou(bxpxMap,"pxjj"));
                if(bxpxMap.get("sljj").equals("是")){
                    dgItemFilePackageBx.setAddpriceNum(1);
                    dgItemFilePackageBx.setItemAmountPrice(checkMapHasKeyReDou(bxpxMap,"countXPrice"));
                }else{
                    dgItemFilePackageBx.setAddpriceNum(0);
                }
                dgItemFilePackageBxMapper.insertSelective(dgItemFilePackageBx);
            }

        /*套餐可选品项循环插入*/
            for(Map kxpxMap:kxpxList){
                DgItemFilePackageKx dgItemFilePackageKx = new DgItemFilePackageKx();
                dgItemFilePackageKx.setPackageId(packageNextId);
                dgItemFilePackageKx.setItemFileId(checkMapHasKeyReIn(kxpxMap,"itemFileId"));
                dgItemFilePackageKx.setItemAmount(checkMapHasKeyReIn(kxpxMap,"count"));
                dgItemFilePackageKx.setItemAddprice(checkMapHasKeyReDou(kxpxMap,"pxjj"));
                if(kxpxMap.get("sljj").equals("是")){
                    dgItemFilePackageKx.setAddpriceNum(1);
                }else{
                    dgItemFilePackageKx.setAddpriceNum(0);
                }

                dgItemFilePackageKxMapper.insertSelective(dgItemFilePackageKx);
            }

            /*套餐必选品项的替换品项循环插入*/
            Iterator iterator = thpxMap.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry entry = (Map.Entry) iterator.next();
                if(entry.getValue().getClass() == ArrayList.class){
                    ArrayList<Map> entryValue = (ArrayList<Map>) entry.getValue();
                    for(Map map:entryValue){
                        DgItemFilePackageTh dgItemFilePackageTh = new DgItemFilePackageTh();
                        dgItemFilePackageTh.setPackageId(packageNextId);
                        dgItemFilePackageTh.setBxItemFileId(Integer.parseInt(entry.getKey().toString()));
                        dgItemFilePackageTh.setItemFileId(checkMapHasKeyReIn(map,"itemFileId"));
                        dgItemFilePackageTh.setItemAmout(checkMapHasKeyReIn(map,"count"));
                        dgItemFilePackageTh.setItemReplaceprice(checkMapHasKeyReDou(map,"thjj"));
                        if(map.get("sljj").equals("是")){
                            dgItemFilePackageTh.setAddpriceNum(1);
                        }else{
                            dgItemFilePackageTh.setAddpriceNum(0);
                        }
                        dgItemFilePackageThMapper.insertSelective(dgItemFilePackageTh);
                    }
                }else if(entry.getValue().getClass() == LinkedTreeMap.class){
                    Map entryValue = (Map) entry.getValue();
                    DgItemFilePackageTh dgItemFilePackageTh = new DgItemFilePackageTh();
                    dgItemFilePackageTh.setPackageId(packageNextId);
                    dgItemFilePackageTh.setBxItemFileId(Integer.parseInt(entry.getKey().toString()));
                    dgItemFilePackageTh.setItemFileId(checkMapHasKeyReIn(entryValue,"itemFileId"));
                    dgItemFilePackageTh.setItemAmout(checkMapHasKeyReIn(entryValue,"count"));
                    dgItemFilePackageTh.setItemReplaceprice(checkMapHasKeyReDou(entryValue,"thjj"));
                    if(entryValue.get("sljj").equals("是")){
                        dgItemFilePackageTh.setAddpriceNum(1);
                    }else{
                        dgItemFilePackageTh.setAddpriceNum(0);
                    }
                    dgItemFilePackageThMapper.insertSelective(dgItemFilePackageTh);
                }
            }

        /*套餐品项大类数量限定循环加入*/
            for(Map map:slxdList){
                DgItemFilePackageSlxd dgItemFilePackageSlxd = new DgItemFilePackageSlxd();
                dgItemFilePackageSlxd.setPackageId(packageNextId);
                dgItemFilePackageSlxd.setItemFileTypeId(checkMapHasKeyReIn(map,"id"));
                dgItemFilePackageSlxd.setMaxNum(checkMapHasKeyReIn(map,"zdxdsl"));

                dgItemFilePackageSlxdMapper.insertSelective(dgItemFilePackageSlxd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String checkHashZero(String key){
        if(key.contains(".0")){
           return key.substring(0,key.lastIndexOf(".0"));
        }
        return key;
    }

    private Integer checkMapHasKeyReIn(Map map,String key) throws Exception{
        if(map.containsKey(key)){
            String value = map.get(key).toString();
            value = checkHashZero(value);
            if(!StringUtils.isEmpty(value)){
                return Integer.parseInt(value);
            }
        }
        return null;
    }

    @SuppressWarnings("unused")
	private String checkMapHasKey(Map map,String key) throws Exception{
        if(map.containsKey(key)){
            String value = map.get(key).toString();
            return StringUtils.isEmpty(value)?null:value;
        }
        return null;
    }

    private Double checkMapHasKeyReDou(Map map,String key) throws Exception{
        if(map.containsKey(key)){
            String value = map.get(key).toString();
            if(!StringUtils.isEmpty(value)){
                return Double.parseDouble(value);
            }
        }
        return null;
    }

    @Override
    public DgItemFilePackage selectTcBaseInfo(Integer id) {
        return dgItemFileMapper.selectTcBaseInfo(id);
    }

    @Override
    public List<Map> selectTcBaseInfoMap(Integer id) {
        return dgItemFileMapper.selectTcBaseInfoMap(id);
    }

    @Override
    public List<Map> selectTcBxInfo(Integer id) {
        return dgItemFileMapper.selectTcBxInfo(id);
    }

    @Override
    public List<Map> selectTcKxInfo(Integer id) {
        return dgItemFileMapper.selectTcKxInfo(id);
    }

    @Override
    public List<Map> selectTcSlxdInfo(Integer id) {
        return dgItemFileMapper.selectTcSlxdInfo(id);
    }

    @Override
    public List<Map> selectTcThpxInfo(Integer id) {
        return dgItemFileMapper.selectTcThpxInfo(id);
    }

    @Override
    public void deleteItemFile(Integer id) {
        DgItemFilePackage dgItemFilePackage = dgItemFileMapper.selectTcBaseInfo(id);
        dgItemFileMapper.deleteByPrimaryKey(id);
        if(dgItemFilePackage != null){
            dgItemFilePackageBxMapper.deleteByPakcageId(dgItemFilePackage.getId());
            dgItemFilePackageKxMapper.deleteByPackageId(dgItemFilePackage.getId());
            dgItemFilePackageMapper.deleteByPrimaryKey(dgItemFilePackage.getId());
            dgItemFilePackageSlxdMapper.deleteByPackageId(dgItemFilePackage.getId());
            dgItemFilePackageThMapper.deleteByPackageId(dgItemFilePackage.getId());
        }
    }

	@Override
    public List<DgItemFile> selectItemFileByTypeIdAndNotInIds(Integer id, String notInIds) {
        Map param = new HashMap();
        param.put("itemTypeId",id);
        param.put("list",arraysTOList(notInIds));
        return dgItemFileMapper.selectItemFileByTypeIdAndNotInIds(param);
    }

	@Override
	public List<DgItemFile> getAllItemCooker(Map<String,Object> map) {
		return dgItemFileMapper.getAllItemCooker(map);
	}

    @Override
    public DgItemFile getDgItemFileByNumber(String number) {
        return dgItemFileMapper.getDgItemFileByNumber(number);
    }
    
    @Override
    public DgItemFile getDgItemFileByName(String name) {
        return dgItemFileMapper.getDgItemFileByName(name);
    }

	@Override
	public int updateCsList(List<DgItemFile> records) {
		return dgItemFileMapper.updateCsList(records);
	}
	
	@Override
	public Page<ItemFileSell> getItemFileSellDetailsPageList(ItemFileSell itemFileSell) {
		Integer totle = dgItemFileMapper.countItemFileSellDetailsListByPage(itemFileSell);
		List<ItemFileSell> list = dgItemFileMapper.selectItemFileSellDetailsListByPage(itemFileSell);
		return PageUtil.getPage(totle, itemFileSell.getPage(),list, itemFileSell.getRows());
	}
	
	@Override
	public Page<ItemFileSell> getItemFileSellSummaryPageList(ItemFileSell itemFileSell) {
		Integer totle = dgItemFileMapper.countItemFileSellSummaryListByPage(itemFileSell);
		List<ItemFileSell> list = dgItemFileMapper.selectItemFileSellSummaryListByPage(itemFileSell);
		return PageUtil.getPage(totle, itemFileSell.getPage(),list, itemFileSell.getRows());
	}

    @Override
    public List<Map> selectItemFileSellDetails(String startTime, String endTime,
                                               Integer itemFileType, Integer searchDataType, Integer bis, String itemFileName,String serviceType) {

        Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("itemFileType",itemFileType);
        map.put("itemFileName",itemFileName);
        map.put("bis",bis);
        map.put("serviceType",serviceType);

        List<String> strings = TableQueryUtil.tableNameUtilWithMonthRange(startTime, endTime);

        map.put("tableSuffixList",strings);

        if(searchDataType == 1)return dgItemFileMapper.selectItemFileSellDetails(map);

        return dgItemFileMapper.selectItemFileSellSummary(map);
    }

    @Override
    public List<SysUser> selectAllCook() {
        return dgItemFileMapper.selectAllCook();
    }

	@Override
	@Transactional
	public void synItemFileTc(List<DgItemFilePackage> listItemFilePackage,
			List<DgItemFilePackageBx> listItemFilePackageBx, List<DgItemFilePackageKx> listItemFilePackageKx,
			List<DgItemFilePackageSlxd> listItemFilePackageSlxd, List<DgItemFilePackageTh> listItemFilePackageTh) {
		dgItemFilePackageMapper.delPhy();
		if(null!=listItemFilePackage&&listItemFilePackage.size()>0){
			dgItemFilePackageMapper.insertBatch(listItemFilePackage);
		}
		dgItemFilePackageBxMapper.delPhy();
		if(null!=listItemFilePackageBx&&listItemFilePackageBx.size()>0){
			dgItemFilePackageBxMapper.insertBatch(listItemFilePackageBx);
		}
		dgItemFilePackageKxMapper.delPhy();
		if(null!=listItemFilePackageKx&&listItemFilePackageKx.size()>0){
			dgItemFilePackageKxMapper.insertBatch(listItemFilePackageKx);
		}
		dgItemFilePackageSlxdMapper.delPhy();
		if(null!=listItemFilePackageSlxd&&listItemFilePackageSlxd.size()>0){
			dgItemFilePackageSlxdMapper.insertBatch(listItemFilePackageSlxd);
		}
		dgItemFilePackageThMapper.delPhy();
		if(null!=listItemFilePackageTh&&listItemFilePackageTh.size()>0){
			dgItemFilePackageThMapper.insertBatch(listItemFilePackageTh);
		}
	}

    @Override
    public List<DgItemFile> searchItemFile(String searchText, Integer searchFlag, Integer typeId) {
        Map<String,Object> map = new HashMap<>();
        map.put("searchText",searchText);
        map.put("searchFlag",searchFlag);
        map.put("typeId",typeId);
        return dgItemFileMapper.searchItemFile(map);
    }

    @Override
    public List<DgItemFile> selectItemFileNotInIds(Integer parentId, Integer type, String ids) {
        Map<String,Object> map = new HashMap<>();

        List<Integer> integers = JSON.parseArray(ids, Integer.class);
        if(integers != null && integers.size() > 0){
            integers.removeAll(Collections.singleton(null));
        }

        map.put("parentId",parentId);
        map.put("type",type);
        map.put("list",integers);

        return dgItemFileMapper.selectItemFileNotInIds(map);
    }

    @Override
    public List<DgItemFile> selectItemFileInIdsTicket(String ids) {
        List<Integer> integers = JSON.parseArray(ids, Integer.class);

        if(integers != null && integers.size() > 0){
            integers.removeAll(Collections.singleton(null));
        }
        if(integers.size() > 0 )
            return dgItemFileMapper.selectItemFileInIdsTicket(integers);
        return null;
    }

    
    @Override
    public DgItemFile selectByPrimaryKey(Integer id) {
        return dgItemFileMapper.selectByPrimaryKey(id);
    }

	@Override
	public List<DgItemFile> getItemFileRankList(Integer ppxlId) {
		return dgItemFileMapper.getItemFileRankList(ppxlId);
	}
	
	@Override
	public List<DgItemFile> getItemFileNotRankList() {
		return dgItemFileMapper.getItemFileNotRankList();
	}

	@Override
	public Integer getItemFileLargeRankByPpxlId(Integer xlId) {
		return dgItemFileMapper.getItemFileLargeRankByPpxlId(xlId);
	}

	@Override
	public Integer getItemFileSmallRankByPpxlId(Integer xlId) {
		return dgItemFileMapper.getItemFileSmallRankByPpxlId(xlId);
	}
	
	@Override
	public void updateItemFileRankIncreaseByRank(Integer largeRank) {
		dgItemFileMapper.updateItemFileRankIncreaseByRank(largeRank);
	}
	
	@Override
	public void updateItemFileRankIncreaseByRankAndPpxlId(Map<String, Object> map) {
		dgItemFileMapper.updateItemFileRankIncreaseByRankAndPpxlId(map);
	}
	
	@Override
	public Map<String, Object> selItemFileRank(Map<String, Object> map) {
		return dgItemFileMapper.selItemFileRank(map);
	}

	@Override
	public void addItemFileRank(Map<String, Object> map) {
		dgItemFileMapper.addItemFileRank(map);
	}

	@Override
	public Integer getItemFileRankCount() {
		return dgItemFileMapper.getItemFileRankCount();
	}

	@Override
	public int updateItemFileRankMove(Integer id, Integer rank) {
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		map.put("rank", rank);
		return dgItemFileMapper.updateItemFileRankMove(map);
	}

	@Override
	public int updateItemFileRankMoveUp(Integer id) {
		return dgItemFileMapper.updateItemFileRankMoveUp(id);
	}

	@Override
	public int updateItemFileRankMoveDown(Integer id) {
		return dgItemFileMapper.updateItemFileRankMoveDown(id);
	}

	@Override
	public List<String> selectItemFileRankMoveTopper(Integer id) {
		return dgItemFileMapper.selectItemFileRankMoveTopper(id);
	}

	@Override
	public void updateItemFileRankMoveTopper(List<String> ids) {
		dgItemFileMapper.updateItemFileRankMoveTopper(ids);
	}

	@Override
	public void updateItemFileRank(Map<String, Object> params) {
		dgItemFileMapper.updateItemFileRank(params);
	}

	@Override
	public Integer getItemFileRankByItemFileId(Integer itemFileId) {
		return dgItemFileMapper.getItemFileRankByItemFileId(itemFileId);
	}

}


