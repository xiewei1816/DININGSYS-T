package com.yqsh.diningsys.core.generic;

import com.yqsh.diningsys.web.model.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GenericService的实现类, 其他的自定义 ServiceImpl, 继承自它,可以获得常用的增删查改操作,
 * 未实现的方法有 子类各自实现
 * <p/>
 * Model : 代表数据库中的表 映射的Java对象类型
 * PK :代表对象的主键类型
 *
 */
public abstract class GenericServiceImpl<Model, PK> implements GenericService<Model, PK> {

    /**
     * 定义成抽象方法,由子类实现,完成dao的注入
     *
     * @return GenericDao实现类
     */
    public abstract GenericDao<Model, PK> getDao();

    @Override
    public int deleteByPrimaryKey(PK id) {
        return getDao().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Model record) {
        return getDao().insert(record);
    }

    @Override
    public int insertSelective(Model record) {
        return getDao().insertSelective(record);
    }

    @Override
    public Model selectByPrimaryKey(PK id) {
        return getDao().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Model record) {
        return getDao().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Model record) {
        return getDao().updateByPrimaryKey(record);
    }

    @Override
    public List<Integer> StringIdsTOList(String ids) {
        if(StringUtils.isEmpty(ids))return null;
        List list = new ArrayList();
        Collections.addAll(list,ids.split(","));
        return list;
    }

    @Override
    public List<Integer> arraysTOList(String ids) {
        if(StringUtils.isEmpty(ids))return null;
        List list = new ArrayList();
        Collections.addAll(list,ids.split(","));
        return list;
    }
    
    @Override
    public List<String> StringIdsTOListStr(String ids) {
        if(StringUtils.isEmpty(ids))return null;
        List list = new ArrayList();
        Collections.addAll(list,ids.split(","));
        return list;
    }

}
