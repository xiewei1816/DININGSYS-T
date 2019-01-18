/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yqsh.diningsys.web.service.sysSettings.impl;

import com.yqsh.diningsys.web.dao.SysAutoseqMapper;
import com.yqsh.diningsys.web.model.SysAutoseq;
import com.yqsh.diningsys.web.service.sysSettings.AutoSeqService;
import io.netty.util.internal.StringUtil;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 *
 * @author yqsh-zc
 */
@Service
public class AutoSeqServiceimpl implements AutoSeqService{
    @Resource
    private SysAutoseqMapper mapper;
    
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysAutoseq record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(SysAutoseq record) {
        return mapper.insertSelective(record);
    }

    @Override
    public SysAutoseq selectByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysAutoseq record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysAutoseq record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public boolean initAutoSeq(String type, int ws, int hasparent, String parent, int hashead, String head) {
        boolean flag = true;
        try{
            SysAutoseq seq = mapper.getSeqByType(type);
            if(seq != null){
                flag = false;
            }else{
                 SysAutoseq  bean = new SysAutoseq();
                 bean.setType(type);
                 bean.setRownum(ws);
                 bean.setCurrentnum(0);
                 bean.setHasparent(hasparent);
                 bean.setHashead(hashead);
                 bean.setHead(head);
                 mapper.insert(bean);
            }
        }catch(Exception e){
            e.printStackTrace();
            flag = false;
        }
        return true;
    }
    
    
    private String getBCurentNum(int rownum,String cRow)
    {
        int n = rownum - cRow.length();
        StringBuffer nrow = new StringBuffer();
        for(int i = 0;i<n;i++){
            nrow.append("0");
        }
       return nrow.toString()+cRow;
    }

    private String getCurrentNum(SysAutoseq bean)
    {
        String cRow = bean.getCurrentnum()+"";
        int rownum = bean.getRownum();
        String result = "error";
        if(cRow.length() < rownum){
            result = getBCurentNum(rownum,cRow);
        }
        if(bean.getHashead() == 1 && bean.getHead() != null && !bean.getHead().equals("")){
            result = bean.getHead()+result;
        }
        
        if(bean.getHasparent() == 1 && bean.getParent() != null && !bean.getParent().equals("")){
            result = bean.getParent() + result;
        }
        return result;
    }
    
    @Override
    public String getSeq(String type, int ws, int hasparent, String parent, int hashead, String head) {
        try{
            SysAutoseq seq = mapper.getSeqByType(type);
            if(seq == null){
                 SysAutoseq  bean = new SysAutoseq();
                 bean.setType(type);
                 bean.setRownum(ws);
                 bean.setCurrentnum(1); //初始化从1开始
                 bean.setHasparent(hasparent);
                 bean.setParent(parent);
                 bean.setHashead(hashead);
                 bean.setHead(head);
                 mapper.insert(bean);
                 seq = bean;
            }
            //组装返回数据
            String currentNum = getCurrentNum(seq);
             return currentNum;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean setUsedSeq(String type,String currentNum) {
        boolean flag = true;
        try{
            SysAutoseq seq = mapper.getSeqByType(type);
            String currentDBNum = getCurrentNum(seq);
            if(currentDBNum.equals(currentNum)){
                    int cn = seq.getCurrentnum();
                    cn++;
                    seq.setCurrentnum(cn);
                    mapper.updateByPrimaryKey(seq);
                }
        }catch(Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
    
    @Override
    public boolean deleteSeqByType(String type) {
        boolean flag = true;
        try{
            mapper.deleteSeqByType(type);
        }catch(Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
}