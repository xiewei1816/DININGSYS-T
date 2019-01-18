package com.yqsh.diningsys.web.service.base;

import com.yqsh.diningsys.web.model.DgCommonsVariable;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-11-16 上午9:43
 */
public interface DgCommonsVariableService {

    DgCommonsVariable selectByCode(DgCommonsVariable dgCommonsVariable);

    void updateValueByCode(DgCommonsVariable dgCommonsVariable);

}
