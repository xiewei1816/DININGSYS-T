package com.yqsh.diningsys.core.generic;

import java.util.List;

/**
 * 所有自定义Service的顶级接口,封装常用的增删查改操作
 * <p/>
 * Model : 代表数据库中的表 映射的Java对象类型
 * PK :代表对象的主键类型
 */
public interface GenericService<Model, PK> {

    int deleteByPrimaryKey(PK id);

    int insert(Model record);

    int insertSelective(Model record);

    Model selectByPrimaryKey(PK id);

    int updateByPrimaryKeySelective(Model record);

    int updateByPrimaryKey(Model record);

    List<Integer> StringIdsTOList(String ids);

    List<Integer> arraysTOList(String ids);
    
    List<String> StringIdsTOListStr(String ids);
}
