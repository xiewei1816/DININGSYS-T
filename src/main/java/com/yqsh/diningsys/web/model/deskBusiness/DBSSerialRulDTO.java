package com.yqsh.diningsys.web.model.deskBusiness;

/**
 * 流水生成dto
 * Created by mrren on 2016/11/15.
 */
public class DBSSerialRulDTO {

    //营业流水
    private DBSJournalBusiness dbsJournalBusiness;
    //结算流水
    private DBSJournalSettlement dbsJournalSettlement;
    //接班流水
    private DBSJournalOffwork dbsJournalOffwork;

    public DBSJournalBusiness getDbsJournalBusiness() {
        return dbsJournalBusiness;
    }

    public void setDbsJournalBusiness(DBSJournalBusiness dbsJournalBusiness) {
        this.dbsJournalBusiness = dbsJournalBusiness;
    }

    public DBSJournalSettlement getDbsJournalSettlement() {
        return dbsJournalSettlement;
    }

    public void setDbsJournalSettlement(DBSJournalSettlement dbsJournalSettlement) {
        this.dbsJournalSettlement = dbsJournalSettlement;
    }

    public DBSJournalOffwork getDbsJournalOffwork() {
        return dbsJournalOffwork;
    }

    public void setDbsJournalOffwork(DBSJournalOffwork dbsJournalOffwork) {
        this.dbsJournalOffwork = dbsJournalOffwork;
    }
}
