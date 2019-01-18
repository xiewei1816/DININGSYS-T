package com.yqsh.diningsys.web.dao;

import com.yqsh.diningsys.web.model.DgCommonsVariable;
import org.springframework.stereotype.Repository;

/**
 * 公共变量
 *
 * @author zhshuo create on 2016-11-16 上午9:40
 */
@Repository
public interface DgCommonsVariableMapper {

    DgCommonsVariable selectByCode(DgCommonsVariable dgCommonsVariable);

    void updateValueByCode(DgCommonsVariable dgCommonsVariable);

}
