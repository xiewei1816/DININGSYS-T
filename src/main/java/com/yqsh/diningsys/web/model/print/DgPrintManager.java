package com.yqsh.diningsys.web.model.print;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgPrintManager extends BasePojo implements Serializable{
    private Integer id;

    private String name;

    private String num;

    private Integer type;

    private Integer disable = 0;

    private Integer trash;

    private String areaIds;

    private Integer qOZ;
    
    private Integer zt = 0;

    private Integer ct = 0;
    //外卖
    private Integer wm = 0;
    //查询字段
    private String areaNames;
    //是否打印单品转台0-不打印，1-打印
    private Integer dp = 0;
    //打印份数
    private Integer copies;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDisable() {
        return disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }

    public Integer getTrash() {
        return trash;
    }

    public void setTrash(Integer trash) {
        this.trash = trash;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds == null ? null : areaIds.trim();
    }

    public Integer getqOZ() {
        return qOZ;
    }

    public void setqOZ(Integer qOZ) {
        this.qOZ = qOZ;
    }

	public String getAreaNames() {
		return areaNames;
	}

	public void setAreaNames(String areaNames) {
		this.areaNames = areaNames;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getWm() {
		return wm;
	}

	public void setWm(Integer wm) {
		this.wm = wm;
	}

	public Integer getCt() {
		return ct;
	}

	public void setCt(Integer ct) {
		this.ct = ct;
	}

    public Integer getDp() {
        return dp;
    }

    public void setDp(Integer dp) {
        this.dp = dp;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }
}