package com.yqsh.diningsys.web.service.deskBusiness.impl;

import com.google.gson.Gson;
import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DeskBusinessSettingMapper;
import com.yqsh.diningsys.web.model.deskBusiness.*;
import com.yqsh.diningsys.web.model.deskBusiness.enums.DeskbusinessEnum;
import com.yqsh.diningsys.web.model.deskBusiness.enums.SerialRulEnum;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 *
 * Created by mrren on 2016/11/14.
 */
@Service("deskBusinessSettingService")
public class DeskBusinessSettingServiceImpl extends GenericServiceImpl<DeskBusinessSetting,Integer> implements DeskBusinessSettingService {

    //静态锁对象，用于同步代码块加锁
    private static byte[] lockyy = new byte[0];
    private static byte[] lockjs = new byte[0];
    private static byte[] lockjb = new byte[0];
    private static byte[] locktdhm = new byte[0];
    private static byte[] lockfw = new byte[0];

    public static Gson gson = new Gson();

    @Resource
    public DeskBusinessSettingMapper deskBusinessSettingMapper;

    @Override
    public DeskBusinessSetting getDeskBusinessSetting() {
        return deskBusinessSettingMapper.getDeskBusinessSetting();
    }

    @Override
    public void updateDeskBusinessSetting(DeskBusinessSetting deskBusinessSetting) {
        deskBusinessSettingMapper.updateDeskBusinessSetting(deskBusinessSetting);
    }

    @PostConstruct
    public void initFlowNumber(){
        int count = deskBusinessSettingMapper.getSerialRulNumberCount();
        if(count < SerialRulEnum.values().length) {
            initDBSFlowNumber();
        }
    }


    @Override
    public Map<String, Object> getDeskBusinessSettingDetail(DeskbusinessEnum dbEnum, String settingName) {
        DeskBusinessSetting dbs = deskBusinessSettingMapper.getDeskBusinessSetting();
        Map<String, Object> result = new HashMap<String, Object>();
        if (dbEnum.equals(DeskbusinessEnum.seatServ)) {
            String seatServ =  dbs.getSeatServ();
            Map<String, Object> map = gson.fromJson(seatServ, Map.class);
            Object settingObj = map.get(settingName);
            if (settingObj != null) {
                result = gson.fromJson(String.valueOf(settingObj), Map.class);
            }
        }
        return result;
    }

    @Override
    public void initDeskBusinessSetting() {
        DeskBusinessSetting dbs = this.getDeskBusinessSetting();
        if (dbs == null) {
            dbs = new DeskBusinessSetting();
            //初始化数据格式为json
            dbs.setSeatServ(gson.toJson(new DBSSeetServDTO()));//客座设置
            dbs.setBillServ(gson.toJson(new DBSBillServDTO()));//账单设置
            dbs.setPrinterSetting(gson.toJson(new DBSPrinterSettingDTO()));//打印设置
            dbs.setSerialRul(gson.toJson(new DBSSerialRulDTO()));//序列号生成规则
            dbs.setLoungeSetting(gson.toJson(new DBSLoungeSettingDTO()));//雅座设置
            deskBusinessSettingMapper.addDeskBusinessSetting(dbs);
        }

    }

    @Override
    public void initDBSFlowNumber() {
        deskBusinessSettingMapper.deleteFlowNumber();
        deskBusinessSettingMapper.addFlowNumberOfYY();
        deskBusinessSettingMapper.addFlowNumberOfJS();
        deskBusinessSettingMapper.addFlowNumberOfJB();
        deskBusinessSettingMapper.addFlowNumberOfFW();
        deskBusinessSettingMapper.addFlowNumberOfYD();
        deskBusinessSettingMapper.addFlowNumberOfTDHM();
    }

    /**
     * 根据流水类型，生成流水号
     * @param organ
     * @param posNum        pos号
     * @param number        生成流水个数
     * @param type          流水类型，枚举类型,当该参数为TDHM时，organ，posNum两个参数可以为空字符串（""）
     * @return              流水号们json组织机构代码
     */
    @Override
    public String createFlowNumber(String organ, String posNum, int number, SerialRulEnum type) {
        DecimalFormat df=new DecimalFormat("000000");
        DeskBusinessSetting deskBusinessSetting = this.getDeskBusinessSetting();
        DBSSerialRulDTO dbsSerialRulDTO = gson.fromJson(deskBusinessSetting.getSerialRul(), DBSSerialRulDTO.class);
        StringBuffer result = new StringBuffer("");
        List<String> flowList = new ArrayList<String>();
        if ("YY".equals(type.toString())){
            DBSJournalBusiness dbsJournalBusiness = dbsSerialRulDTO.getDbsJournalBusiness();
            if("1".equals(dbsJournalBusiness.getIsNeedPrefix())){
                result.append(dbsJournalBusiness.getPrefix());
            }
            if ("1".equals(dbsJournalBusiness.getIsNeedOrganCode())) {
                result.append(organ);
            }
            result.append(posNum);
            if ("1".equals(dbsJournalBusiness.getIsNeedDateString())) {
                if ("1".equals(dbsJournalBusiness.getDateFormat())) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    result.append(sdf.format(new Date()));
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("-yyyyMMdd-");
                    result.append(sdf.format(new Date()));
                }

            }
            synchronized (lockyy) {
                String isBeginWithOne = dbsJournalBusiness.getIsOrderBeginWithOne();
//                for (int i = 0; i < number; i++) {
//                    int maxNum = deskBusinessSettingMapper.getSerialRulNumber(type.toString(), isBeginWithOne);
//                    flowList.add(result.toString()+df.format(maxNum + 1));
//                    deskBusinessSettingMapper.updateSerialRul(maxNum + 1, type.toString());
//                }
                String list = deskBusinessSettingMapper.getAutoOwNum(number,"YY",isBeginWithOne,result.toString());
                flowList = StringIdsTOListStr(list);
            }
        }
        if ("JS".equals(type.toString())){
            DBSJournalSettlement dbsJournalSettlement = dbsSerialRulDTO.getDbsJournalSettlement();
            if("1".equals(dbsJournalSettlement.getIsNeedPrefix())){
                result.append(dbsJournalSettlement.getPrefix());
            }
            if ("1".equals(dbsJournalSettlement.getIsNeedOrganCode())) {
                result.append(organ);
            }
            result.append(posNum);
            if ("1".equals(dbsJournalSettlement.getIsNeedDateString())) {
                if ("1".equals(dbsJournalSettlement.getDateFormat())) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    result.append(sdf.format(new Date()));
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("-yyyyMMdd-");
                    result.append(sdf.format(new Date()));
                }
            }
            synchronized (lockjs) {
                String isBeginWithOne = dbsJournalSettlement.getIsOrderBeginWithOne();
                for (int i = 0; i < number; i++) {
                    int maxNum = deskBusinessSettingMapper.getSerialRulNumber(type.toString(), isBeginWithOne);
                    flowList.add(result.toString()+df.format(maxNum + 1));
                    deskBusinessSettingMapper.updateSerialRul(maxNum + 1, type.toString());
                }
            }
        }
        if ("JB".equals(type.toString())){
            DBSJournalOffwork dbsJournalOffwork = dbsSerialRulDTO.getDbsJournalOffwork();
            if("1".equals(dbsJournalOffwork.getIsNeedPrefix())){
                result.append(dbsJournalOffwork.getPrefix());
            }
            if ("1".equals(dbsJournalOffwork.getIsNeedOrganCode())) {
                result.append(organ);
            }
            result.append(posNum);
            if ("1".equals(dbsJournalOffwork.getIsNeedDateString())) {
                if ("1".equals(dbsJournalOffwork.getDateFormat())) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    result.append(sdf.format(new Date()));
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("-yyyyMMdd-");
                    result.append(sdf.format(new Date()));
                }
            }

            synchronized (lockjb) {
                String isBeginWithOne = dbsJournalOffwork.getIsOrderBeginWithOne();
                for (int i = 0; i < number; i++) {
                    int maxNum = deskBusinessSettingMapper.getSerialRulNumber(type.toString(), isBeginWithOne);
                    flowList.add(result.toString()+df.format(maxNum + 1));
                    deskBusinessSettingMapper.updateSerialRul(maxNum + 1, type.toString());
                }
            }
        }

        if ("TDHM".equals(type.toString())){
            DecimalFormat df1=new DecimalFormat("0000000000");
            result.append("8888");
            synchronized (locktdhm) {
                for (int i = 0; i < number; i++) {
                    int maxNum = deskBusinessSettingMapper.getSerialRulNumber(type.toString(), "0");
                    flowList.add(result.toString()+df1.format(maxNum + 1));
                    deskBusinessSettingMapper.updateSerialRul(maxNum + 1, type.toString());
                }
            }
        }
        if ("FW".equals(type.toString())){
            DecimalFormat df1=new DecimalFormat("000000");
            result.append("FW88");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            result.append(sdf.format(new Date()));
            synchronized (lockfw) {
                for (int i = 0; i < number; i++) {
                    int maxNum = deskBusinessSettingMapper.getSerialRulNumber(type.toString(), "1");
                    flowList.add(result.toString()+df1.format(maxNum + 1));
                    deskBusinessSettingMapper.updateSerialRul(maxNum + 1, type.toString());
                }
            }
        }
        if ("YD".equals(type.toString())){
            result.append("YD88");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            result.append(sdf.format(new Date()));
            DecimalFormat df1=new DecimalFormat("000000");
            synchronized (locktdhm) {
                for (int i = 0; i < number; i++) {
                    int maxNum = deskBusinessSettingMapper.getSerialRulNumber(type.toString(), "1");
                    flowList.add(result.toString()+df1.format(maxNum + 1));
                    deskBusinessSettingMapper.updateSerialRul(maxNum + 1, type.toString());
                }
            }
        }
        return gson.toJson(flowList);
    }

    @Override
    public String createOpenClassNumber(String organ, String posNum) {
        DecimalFormat df=new DecimalFormat("000000");
        DeskBusinessSetting deskBusinessSetting = this.getDeskBusinessSetting();
        DBSSerialRulDTO dbsSerialRulDTO = gson.fromJson(deskBusinessSetting.getSerialRul(), DBSSerialRulDTO.class);
        StringBuffer result = new StringBuffer("");
        DBSJournalOffwork dbsJournalOffwork = dbsSerialRulDTO.getDbsJournalOffwork();
        if("1".equals(dbsJournalOffwork.getIsNeedPrefix())){
            result.append(dbsJournalOffwork.getPrefix());
        }
        if ("1".equals(dbsJournalOffwork.getIsNeedOrganCode())) {
            result.append(organ);
        }
        result.append(posNum);
        if ("1".equals(dbsJournalOffwork.getIsNeedDateString())) {
            if ("1".equals(dbsJournalOffwork.getDateFormat())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                result.append(sdf.format(new Date()));
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("-yyyyMMdd-");
                result.append(sdf.format(new Date()));
            }
        }

        synchronized (lockjb) {
            String isBeginWithOne = dbsJournalOffwork.getIsOrderBeginWithOne();
            int maxNum = deskBusinessSettingMapper.getSerialRulNumber("JB", isBeginWithOne);
            result.append(df.format(maxNum + 1));
            deskBusinessSettingMapper.updateSerialRul(maxNum + 1, "JB");
        }

        return result.toString();
    }

    @Override
    public void updateOpenClassNumber() {
        DeskBusinessSetting deskBusinessSetting = this.getDeskBusinessSetting();
        DBSSerialRulDTO dbsSerialRulDTO = gson.fromJson(deskBusinessSetting.getSerialRul(), DBSSerialRulDTO.class);
        DBSJournalOffwork dbsJournalOffwork = dbsSerialRulDTO.getDbsJournalOffwork();
        String isBeginWithOne = dbsJournalOffwork.getIsOrderBeginWithOne();
        int maxNum = deskBusinessSettingMapper.getSerialRulNumber("JB", isBeginWithOne);
        deskBusinessSettingMapper.updateSerialRul(maxNum + 1, "JB");
    }

    @Override
    public GenericDao<DeskBusinessSetting, Integer> getDao() {
        return deskBusinessSettingMapper;
    }

	@Override
	public Map getFlowRul(String organ, String posNum, SerialRulEnum type) {
		// TODO Auto-generated method stub
		Map ret = new HashMap();
		DecimalFormat df=new DecimalFormat("000000");
        DeskBusinessSetting deskBusinessSetting = this.getDeskBusinessSetting();
        DBSSerialRulDTO dbsSerialRulDTO = gson.fromJson(deskBusinessSetting.getSerialRul(), DBSSerialRulDTO.class);
        StringBuffer result = new StringBuffer("");
        List<String> flowList = new ArrayList<String>();
        if ("YY".equals(type.toString())){
            DBSJournalBusiness dbsJournalBusiness = dbsSerialRulDTO.getDbsJournalBusiness();
            if("1".equals(dbsJournalBusiness.getIsNeedPrefix())){
                result.append(dbsJournalBusiness.getPrefix());
            }
            if ("1".equals(dbsJournalBusiness.getIsNeedOrganCode())) {
                result.append(organ);
            }
            result.append(posNum);
            if ("1".equals(dbsJournalBusiness.getIsNeedDateString())) {
                if ("1".equals(dbsJournalBusiness.getDateFormat())) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    result.append(sdf.format(new Date()));
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("-yyyyMMdd-");
                    result.append(sdf.format(new Date()));
                }

            }
            String isBeginWithOne = dbsJournalBusiness.getIsOrderBeginWithOne();
            ret.put("isBeginWithOne",isBeginWithOne);
            ret.put("headStr",result.toString());
        }
        return ret;
	}
}
