/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yqsh.diningsys.web.service.sysSettings;

import com.yqsh.diningsys.web.model.SysAutoseq;

/**
 *
 * @author yqsh-zc
 */
public interface AutoSeqService {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAutoseq record);

    int insertSelective(SysAutoseq record);

    SysAutoseq selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAutoseq record);

    int updateByPrimaryKey(SysAutoseq record);
    
    //私有化  暂时不开放
    boolean initAutoSeq(String type, int ws, int hasparent, String parent, int hashead, String head);
    
    String getSeq(String type, int ws, int hasparent, String parent, int hashead, String head);
    
    boolean setUsedSeq(String type, String currentNum);
    
    boolean deleteSeqByType(String type);
}