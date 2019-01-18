package com.yqsh.diningsys.web.model.print;

import java.io.Serializable;

import com.yqsh.diningsys.core.util.BasePojo;

public class DgPrintData extends BasePojo implements Serializable{
    private Integer id;

    private String printType;

    private String uniqueIdentif;

    private Integer success;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType == null ? null : printType.trim();
    }

    public String getUniqueIdentif() {
        return uniqueIdentif;
    }

    public void setUniqueIdentif(String uniqueIdentif) {
        this.uniqueIdentif = uniqueIdentif == null ? null : uniqueIdentif.trim();
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}