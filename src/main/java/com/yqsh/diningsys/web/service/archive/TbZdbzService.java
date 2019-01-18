package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbZdbz;

/**
 * 整单备注服务层
 *
 * @author xiewei
 * @version 创建时间：2016年9月29日 上午9:36:00
 */
public interface TbZdbzService {

    /**
     * 分页查询整单备注信息
     *
     * @param TbZdbz
     * @return
     */
    com.yqsh.diningsys.core.util.Page<TbZdbz> getPageList(TbZdbz tbZdbz);

    /**
     * 新增、修改整单备注信息
     *
     * @param TbZdbz
     * @return
     */
    int insertOrUpdZdbz(TbZdbz tbZdbz);

    /**
     * 通过整单备注ID查询整单备注信息
     *
     * @param TbZdbz
     * @return
     */
    TbZdbz getZdbzById(TbZdbz tbZdbz);

    /**
     * 删除整单备注信息
     *
     * @param TbZdbz
     * @return
     */
    int deleteByIds(TbZdbz tbZdbz);

    /**
     * 整单备注信息回收站
     *
     * @param TbZdbz
     * @return
     */
    int deleteTrash(TbZdbz tbZdbz);

    /**
     * 还原回收站整单备注信息
     *
     * @param TbZdbz
     * @return
     */
    int replyZdbz(TbZdbz tbZdbz);

    List<TbZdbz> getAllList(TbZdbz tbZdbz);

    TbZdbz authentication(TbZdbz tbZdbz);

    TbZdbz selectByZdbzName(String ZdbzName);

    List<TbZdbz> selectByConAndPage(Page<TbZdbz> page, Map params);

    TbZdbz selectByPrimaryKey(Integer id);

    int insertZdbz(TbZdbz tbZdbz);

    int modifyUserByPrimaryKey(TbZdbz tbZdbz);

    int delUserByPrimaryKey(List ids);

    TbZdbz getZdbzByNum(String numbser);
}
