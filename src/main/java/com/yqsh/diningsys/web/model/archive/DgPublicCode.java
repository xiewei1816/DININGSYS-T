package com.yqsh.diningsys.web.model.archive;

import com.yqsh.diningsys.core.util.BasePojo;

/**
 * public_code
 */
public class DgPublicCode extends BasePojo{

    private String cgpcid;

    private Integer ilocalsno;

    private String cgcorpcode;

    private String ccode;

    private String cname;

    private String ckeywd;

    private String cparent;

    private Integer ilevel;

    private Integer isystem;

    private Integer idelflg;

    private Integer iorder;

    private Integer iuploadflg;

    private Integer idownloadflg;

    private String cexpid;

    private String parentName;

    /**
     * 2016年11月9日16:10:49 判断当前职务是否已经设置了权限
     */
    private Integer perBusId;

    public String getCgpcid() {
        return cgpcid;
    }

    public void setCgpcid(String cgpcid) {
        this.cgpcid = cgpcid == null ? null : cgpcid.trim();
    }

    public Integer getIlocalsno() {
        return ilocalsno;
    }

    public void setIlocalsno(Integer ilocalsno) {
        this.ilocalsno = ilocalsno;
    }

    public String getCgcorpcode() {
        return cgcorpcode;
    }

    public void setCgcorpcode(String cgcorpcode) {
        this.cgcorpcode = cgcorpcode == null ? null : cgcorpcode.trim();
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode == null ? null : ccode.trim();
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public String getCkeywd() {
        return ckeywd;
    }

    public void setCkeywd(String ckeywd) {
        this.ckeywd = ckeywd == null ? null : ckeywd.trim();
    }

    public String getCparent() {
        return cparent;
    }

    public void setCparent(String cparent) {
        this.cparent = cparent == null ? null : cparent.trim();
    }

    public Integer getIlevel() {
        return ilevel;
    }

    public void setIlevel(Integer ilevel) {
        this.ilevel = ilevel;
    }

    public Integer getIsystem() {
        return isystem;
    }

    public void setIsystem(Integer isystem) {
        this.isystem = isystem;
    }

    public Integer getIdelflg() {
        return idelflg;
    }

    public void setIdelflg(Integer idelflg) {
        this.idelflg = idelflg;
    }

    public Integer getIorder() {
        return iorder;
    }

    public void setIorder(Integer iorder) {
        this.iorder = iorder;
    }

    public Integer getIuploadflg() {
        return iuploadflg;
    }

    public void setIuploadflg(Integer iuploadflg) {
        this.iuploadflg = iuploadflg;
    }

    public Integer getIdownloadflg() {
        return idownloadflg;
    }

    public void setIdownloadflg(Integer idownloadflg) {
        this.idownloadflg = idownloadflg;
    }

    public String getCexpid() {
        return cexpid;
    }

    public void setCexpid(String cexpid) {
        this.cexpid = cexpid == null ? null : cexpid.trim();
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getPerBusId() {
        return perBusId;
    }

    public void setPerBusId(Integer perBusId) {
        this.perBusId = perBusId;
    }
}