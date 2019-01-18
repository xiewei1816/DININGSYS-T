package com.yqsh.diningsys.web.model.archive;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 品项档案
 */
public class DgItemFile {
    private Integer id;

    private String num;

    private String zjf;

    private String name;

    private String unit;

    /**
     * 品项大类ID
     */
    private Integer ppdlId;

    /**
     * 品项小类ID
     */
    private Integer ppxlId;

    private Integer xxlxId;

    private Integer pxld;

    private Integer jbiao;

    private Integer dyfz;

    private Integer yybf;

    private Double zxddsl;

    private Double standardPrice;

    private Double costPrice;

    private String ywmc;

    private String qtmc;

    private String gg;

    private String sptm;

    private String sm;

    private Integer bzzzsc;

    private Integer cs;

    private Integer tssjdOne;

    private Integer tssjdTwo;

    private Integer isTc;

    private Integer tcywhpxxl;

    private Integer gjjefssl;

    private Integer jsqtsxgsl;

    private Integer cyzdxf;

    private String rfidCard;

    private Integer lspx;

    private Integer yxdz = 0;

    private String zyzfids;

    private String ggzyzfids;

    private String pxdt;

    private String pxxt;

    private String yyxgids;

    private String pxxtsm;

    private Date createTime;

    private Date updateTime;

    private Integer itemFileId;
    
    private String pxdtFile;
    private String pxxtFile;
    private Integer yxe = 0;


    //用来区分修改时的图片是否重新上传或者已经删除

    private Integer reUploadPxdt; //1代表重新上传，这时isDelPxdt为0

    private Integer isDelPxdt;//1代表删除，这时reUploadPxdt为0

    private Integer reUploadPxxt;//1代表重新上传，这时isDelPxdt为0

    private Integer isDelPxxt;//1代表重新上传，reUploadPxxt

    //查询字段
    private String csName;//厨师姓名
    private String bName;//大类名称
    private String cName;//小类名称
    private Integer disable;//品项停用
    private String bNum;//大类编码
    private String sNum;//小类编码
    private Integer colorItemId;//对应查询的颜色品项id(发送到餐台)
    private String colorItemNum;//对应查询的颜色品项编码(发送到餐台)
    //新增字段
    private Integer delFlag; //删除标志，0正常、1表示已删除

    private String uuidKey;

    private String pxxtFileOl;

    private String pxdtFileOl;

    private Integer sfwm = 0; //是否外卖
    
    private Integer showCt = 0;//是否在餐台上显示
    
    private String colorGate; //盘子颜色
    
    private Integer rank; //品项排序值


    public String getPxxtFileOl() {
        return pxxtFileOl;
    }

    public void setPxxtFileOl(String pxxtFileOl) {
        this.pxxtFileOl = pxxtFileOl;
    }

    public String getPxdtFileOl() {
        return pxdtFileOl;
    }

    public void setPxdtFileOl(String pxdtFileOl) {
        this.pxdtFileOl = pxdtFileOl;
    }

    public String getUuidKey() {
        return uuidKey;
    }

    public void setUuidKey(String uuidKey) {
        this.uuidKey = uuidKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    public String getZjf() {
        return zjf;
    }

    public void setZjf(String zjf) {
        this.zjf = zjf == null ? null : zjf.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getPpdlId() {
        return ppdlId;
    }

    public void setPpdlId(Integer ppdlId) {
        this.ppdlId = ppdlId;
    }

    public Integer getPpxlId() {
        return ppxlId;
    }

    public void setPpxlId(Integer ppxlId) {
        this.ppxlId = ppxlId;
    }

    public Integer getXxlxId() {
        return xxlxId;
    }

    public void setXxlxId(Integer xxlxId) {
        this.xxlxId = xxlxId;
    }

    public Integer getPxld() {
        return pxld;
    }

    public void setPxld(Integer pxld) {
        this.pxld = pxld;
    }

    public Integer getJbiao() {
        return jbiao;
    }

    public void setJbiao(Integer jbiao) {
        this.jbiao = jbiao;
    }

    public Integer getDyfz() {
        return dyfz;
    }

    public void setDyfz(Integer dyfz) {
        this.dyfz = dyfz;
    }

    public Integer getYybf() {
        return yybf;
    }

    public void setYybf(Integer yybf) {
        this.yybf = yybf;
    }

    public Double getZxddsl() {
        return zxddsl;
    }

    public void setZxddsl(Double zxddsl) {
        this.zxddsl = zxddsl;
    }

    public Double getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(Double standardPrice) {
        this.standardPrice = standardPrice;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc == null ? null : ywmc.trim();
    }

    public String getQtmc() {
        return qtmc;
    }

    public void setQtmc(String qtmc) {
        this.qtmc = qtmc == null ? null : qtmc.trim();
    }

    public String getGg() {
        return gg;
    }

    public void setGg(String gg) {
        this.gg = gg == null ? null : gg.trim();
    }

    public String getSptm() {
        return sptm;
    }

    public void setSptm(String sptm) {
        this.sptm = sptm == null ? null : sptm.trim();
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm == null ? null : sm.trim();
    }

    public Integer getBzzzsc() {
        return bzzzsc;
    }

    public void setBzzzsc(Integer bzzzsc) {
        this.bzzzsc = bzzzsc;
    }

    public Integer getCs() {
        return cs;
    }

    public void setCs(Integer cs) {
        this.cs = cs;
    }

    public Integer getTssjdOne() {
        return tssjdOne;
    }

    public void setTssjdOne(Integer tssjdOne) {
        this.tssjdOne = tssjdOne;
    }

    public Integer getTssjdTwo() {
        return tssjdTwo;
    }

    public void setTssjdTwo(Integer tssjdTwo) {
        this.tssjdTwo = tssjdTwo;
    }

    public Integer getIsTc() {
        return isTc;
    }

    public void setIsTc(Integer isTc) {
        this.isTc = isTc;
    }

    public Integer getTcywhpxxl() {
        return tcywhpxxl;
    }

    public void setTcywhpxxl(Integer tcywhpxxl) {
        this.tcywhpxxl = tcywhpxxl;
    }

    public Integer getGjjefssl() {
        return gjjefssl;
    }

    public void setGjjefssl(Integer gjjefssl) {
        this.gjjefssl = gjjefssl;
    }

    public Integer getJsqtsxgsl() {
        return jsqtsxgsl;
    }

    public void setJsqtsxgsl(Integer jsqtsxgsl) {
        this.jsqtsxgsl = jsqtsxgsl;
    }

    public Integer getCyzdxf() {
        return cyzdxf;
    }

    public void setCyzdxf(Integer cyzdxf) {
        this.cyzdxf = cyzdxf;
    }

    public String getRfidCard() {
        return rfidCard;
    }

    public void setRfidCard(String rfidCard) {
        this.rfidCard = rfidCard == null ? null : rfidCard.trim();
    }

    public Integer getLspx() {
        return lspx;
    }

    public void setLspx(Integer lspx) {
        this.lspx = lspx;
    }

    public Integer getYxdz() {
        return yxdz;
    }

    public void setYxdz(Integer yxdz) {
        this.yxdz = yxdz;
    }

    public String getZyzfids() {
        return zyzfids;
    }

    public void setZyzfids(String zyzfids) {
        this.zyzfids = zyzfids == null ? null : zyzfids.trim();
    }

    public String getGgzyzfids() {
        return ggzyzfids;
    }

    public void setGgzyzfids(String ggzyzfids) {
        this.ggzyzfids = ggzyzfids == null ? null : ggzyzfids.trim();
    }

    public String getPxdt() {
        return pxdt;
    }

    public void setPxdt(String pxdt) {
        this.pxdt = pxdt == null ? null : pxdt.trim();
    }

    public String getPxxt() {
        return pxxt;
    }

    public void setPxxt(String pxxt) {
        this.pxxt = pxxt == null ? null : pxxt.trim();
    }

    public String getYyxgids() {
        return yyxgids;
    }

    public void setYyxgids(String yyxgids) {
        this.yyxgids = yyxgids == null ? null : yyxgids.trim();
    }

    public String getPxxtsm() {
        return pxxtsm;
    }

    public void setPxxtsm(String pxxtsm) {
        this.pxxtsm = pxxtsm == null ? null : pxxtsm.trim();
    }

    public String getCreateTime() {
        return createTime!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime):null;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(updateTime):null;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getItemFileId() {
        return itemFileId;
    }

    public void setItemFileId(Integer itemFileId) {
        this.itemFileId = itemFileId;
    }

    public Integer getReUploadPxdt() {
        return reUploadPxdt;
    }

    public void setReUploadPxdt(Integer reUploadPxdt) {
        this.reUploadPxdt = reUploadPxdt;
    }

    public Integer getIsDelPxdt() {
        return isDelPxdt;
    }

    public void setIsDelPxdt(Integer isDelPxdt) {
        this.isDelPxdt = isDelPxdt;
    }

    public Integer getReUploadPxxt() {
        return reUploadPxxt;
    }

    public void setReUploadPxxt(Integer reUploadPxxt) {
        this.reUploadPxxt = reUploadPxxt;
    }

    public Integer getIsDelPxxt() {
        return isDelPxxt;
    }

    public void setIsDelPxxt(Integer isDelPxxt) {
        this.isDelPxxt = isDelPxxt;
    }

	public String getCsName() {
		return csName;
	}

	public void setCsName(String csName) {
		this.csName = csName;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getPxdtFile() {
		return pxdtFile;
	}

	public void setPxdtFile(String pxdtFile) {
		this.pxdtFile = pxdtFile;
	}

	public String getPxxtFile() {
		return pxxtFile;
	}

	public void setPxxtFile(String pxxtFile) {
		this.pxxtFile = pxxtFile;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getYxe() {
		return yxe;
	}

	public void setYxe(Integer yxe) {
		this.yxe = yxe;
	}

	public Integer getDisable() {
		return disable;
	}

	public void setDisable(Integer disable) {
		this.disable = disable;
	}

	public String getbNum() {
		return bNum;
	}

	public void setbNum(String bNum) {
		this.bNum = bNum;
	}

	public String getsNum() {
		return sNum;
	}

	public void setsNum(String sNum) {
		this.sNum = sNum;
	}

	public Integer getSfwm() {
		return sfwm;
	}

	public void setSfwm(Integer sfwm) {
		this.sfwm = sfwm;
	}

	public Integer getShowCt() {
		return showCt;
	}

	public void setShowCt(Integer showCt) {
		this.showCt = showCt;
	}

	public String getColorGate() {
		return colorGate;
	}

	public void setColorGate(String colorGate) {
		this.colorGate = colorGate;
	}

	public Integer getColorItemId() {
		return colorItemId;
	}

	public void setColorItemId(Integer colorItemId) {
		this.colorItemId = colorItemId;
	}

	public String getColorItemNum() {
		return colorItemNum;
	}

	public void setColorItemNum(String colorItemNum) {
		this.colorItemNum = colorItemNum;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
}