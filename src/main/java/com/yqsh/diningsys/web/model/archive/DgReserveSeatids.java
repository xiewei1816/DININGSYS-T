package com.yqsh.diningsys.web.model.archive;

public class DgReserveSeatids {
    private Integer id;

    private Integer seatId;

    private Integer reserveId;
    
    private String seatName;

    
    private Integer state;//状态 1/-1 null 为初始化状态
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getReserveId() {
        return reserveId;
    }

    public void setReserveId(Integer reserveId) {
        this.reserveId = reserveId;
    }

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	} 
	
	
}