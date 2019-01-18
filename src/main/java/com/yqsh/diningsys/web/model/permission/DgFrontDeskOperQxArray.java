package com.yqsh.diningsys.web.model.permission;

import java.util.List;

public class DgFrontDeskOperQxArray {
	private List<DgFrontDeskOperQx> deskQx;


	
	 public List<DgFrontDeskOperQx> getDeskQx() {
		return deskQx;
	}

	public void setDeskQx(List<DgFrontDeskOperQx> deskQx) {
		this.deskQx = deskQx;
	}

	public DgFrontDeskOperQxArray(List<DgFrontDeskOperQx> deskQx) {
		super();
		this.deskQx = deskQx;
	}
		  
	public DgFrontDeskOperQxArray() {
		super();
	}
}