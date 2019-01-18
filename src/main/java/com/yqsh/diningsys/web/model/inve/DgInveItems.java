package com.yqsh.diningsys.web.model.inve;

import com.yqsh.diningsys.core.util.BasePojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 物品实体
 *
 * @author jianglei
 * 日期 2016年10月19日 上午10:10:41
 */
@SuppressWarnings("serial")
public class DgInveItems extends BasePojo implements Serializable {
    /**
     * 物品单位：查询公共代码的key
     */
    public static final String UNIT_PUBLIC_CODE_PARENT = "55";
    private String id;         //编号
    private String itemNo;     //编码
    private String itemName;   //物品名称
    private String unit;       //单位
    private BigDecimal price;  //单价
    private String itemTypeId; //物品类型编号
    private String state;      //状态 0表示正常，1表示已删除
    private int minStorage;    //最小存储
    private int maxStorage;    //最大存储
    private String inCode;     //助记码
    private String spellCode;  //拼音简码
    private String outWareId;  //出品仓库ID
    private String remark;     //备注

    public DgInveItems() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(String itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getMinStorage() {
        return minStorage;
    }

    public void setMinStorage(int minStorage) {
        this.minStorage = minStorage;
    }

    public int getMaxStorage() {
        return maxStorage;
    }

    public void setMaxStorage(int maxStorage) {
        this.maxStorage = maxStorage;
    }

    public String getInCode() {
        return inCode;
    }

    public void setInCode(String inCode) {
        this.inCode = inCode;
    }

    public String getSpellCode() {
        return spellCode;
    }

    public void setSpellCode(String spellCode) {
        this.spellCode = spellCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getOutWareId() {
        return outWareId;
    }

    public void setOutWareId(String outWareId) {
        this.outWareId = outWareId;
    }
}
