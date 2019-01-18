package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbRfc;

/**
 * 退菜原因服务层
 *
 * @author xiewei
 * @version 创建时间：2016年9月29日 上午9:36:00
 */
public interface TbRfcService {

    /**
     * 分页查询退菜原因信息
     *
     * @param tbRfc
     * @return
     */
    com.yqsh.diningsys.core.util.Page<TbRfc> getPageList(TbRfc tbRfc);

    /**
     * 新增、修改退菜原因信息
     *
     * @param tbRfc
     * @return
     */
    int insertOrUpdRfc(TbRfc tbRfc);

    /**
     * 通过退菜原因ID查询退菜原因信息
     *
     * @param tbRfc
     * @return
     */
    TbRfc getRfcById(TbRfc tbRfc);

    /**
     * 删除退菜原因信息
     *
     * @param tbRfc
     * @return
     */
    int deleteByIds(TbRfc tbRfc);

    /**
     * 退菜原因信息回收站
     *
     * @param tbRfc
     * @return
     */
    int deleteTrash(TbRfc tbRfc);

    /**
     * 还原回收站退菜原因信息
     *
     * @param tbRfc
     * @return
     */
    int replyRfc(TbRfc tbRfc);

    List<TbRfc> getAllList(TbRfc tbRfc);

    TbRfc authentication(TbRfc tbRfc);

    TbRfc selectByRfcName(String RfcName);

    List<TbRfc> selectByConAndPage(Page<TbRfc> page, Map params);

    TbRfc selectByPrimaryKey(Integer id);
    
    int insertRfc(TbRfc tbRfc);

    int modifyUserByPrimaryKey(TbRfc tbRfc);

    int delUserByPrimaryKey(List ids);
    
    TbRfc getTbRfcByNumber(String number);

}