package com.yqsh.diningsys.web.model.deskBusiness;

import com.yqsh.diningsys.core.util.BasePojo;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.TbBis;

/**
 * 服务员服务客位
 *
 * @author ls001
 *         table 'dg_service_class'
 */
public class ServiceClass extends BasePojo {

    /**
     *
     */
    private static final long serialVersionUID = -540946806227402488L;
    private Integer id;
    private Integer eatTimeId;            //市别
    private TbBis eatTime;                //市别实体
    private Integer seatId;            //客位
    private DgConsumerSeat seat;        //客位实体
    private Integer waiterId;            //服务员
    private SysUser waiter;                //服务员实体
    private String createTime;            //创建时间
    private String createUserid;        //创建数据用户
    private SysUser createUser;            //创建用户对象
    private Integer delFlag;            //删除标记（0未删除 1已删除）
    private String conditions;            //多查询条件
    private String crStartTime;            //创建开始时间（查询条件）
    private String crEndTime;            //创建结束时间（查询条件）
    private String checkName;
    private String seatIds;
    private String waiterIds;

    private String uuidKey;
    private String shopKey;
    public String getUuidKey() {
        return uuidKey;
    }

    public void setUuidKey(String uuidKey) {
        this.uuidKey = uuidKey;
    }

    public String getSeatIds() {
        return seatIds;
    }

    public String getWaiterIds() {
        return waiterIds;
    }

    public void setSeatIds(String seatIds) {
        this.seatIds = seatIds;
    }

    public void setWaiterIds(String waiterIds) {
        this.waiterIds = waiterIds;
    }

    public SysUser getWaiter() {
        return waiter;
    }

    public void setWaiter(SysUser waiter) {
        this.waiter = waiter;
    }

    public Integer getId() {
        return id;
    }

    public Integer getEatTimeId() {
        return eatTimeId;
    }

    public TbBis getEatTime() {
        return eatTime;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public DgConsumerSeat getSeat() {
        return seat;
    }

    public Integer getWaiterId() {
        return waiterId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getCreateUserid() {
        return createUserid;
    }

    public SysUser getCreateUser() {
        return createUser;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public String getConditions() {
        return conditions;
    }

    public String getCrStartTime() {
        return crStartTime;
    }

    public String getCrEndTime() {
        return crEndTime;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEatTimeId(Integer eatTimeId) {
        this.eatTimeId = eatTimeId;
    }

    public void setEatTime(TbBis eatTime) {
        this.eatTime = eatTime;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public void setSeat(DgConsumerSeat seat) {
        this.seat = seat;
    }

    public void setWaiterId(Integer waiterId) {
        this.waiterId = waiterId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public void setCreateUser(SysUser createUser) {
        this.createUser = createUser;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public void setCrStartTime(String crStartTime) {
        this.crStartTime = crStartTime;
    }

    public void setCrEndTime(String crEndTime) {
        this.crEndTime = crEndTime;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}

    
}
