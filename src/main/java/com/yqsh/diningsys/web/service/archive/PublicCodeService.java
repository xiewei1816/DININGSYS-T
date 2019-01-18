package com.yqsh.diningsys.web.service.archive;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.archive.DgPublicCode;

import java.util.List;

/**
 * 公共代码 service
 *
 * @author zhshuo create on 2016-09-28 11:10
 */
public interface PublicCodeService extends GenericService<DgPublicCode,Integer>{

    List<DgPublicCode> selectAllData();

    List<DgPublicCode> selectDataById(String cgpcid);

    void addData(DgPublicCode dgPublicCode);

    void deleteDataWithLogic(String cgpcid);

    void deleteData(String cgpcid);

    Page<DgPublicCode> getPageList(DgPublicCode user);
    /**
     * 根据相关条件获取公共代码
     * @author jianglei
     * 日期 2016年10月19日 下午1:08:10
     * @param publicCode
     * @return
     */
    List<DgPublicCode> findListData(DgPublicCode publicCode);
}
