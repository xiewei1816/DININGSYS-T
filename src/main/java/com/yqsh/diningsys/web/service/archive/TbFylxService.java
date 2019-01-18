package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbFylx;
import com.yqsh.diningsys.web.model.archive.TbRfct;

/**
 * 费用类型服务层
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:36:00
 */
public interface TbFylxService {

	/**
	 * 分页查询费用类型信息
	 * @param TbFylx
	 * @return
	 */
	com.yqsh.diningsys.core.util.Page<TbFylx> getPageList(TbFylx tbFylx);
	
	/**
	 * 新增、修改费用类型信息
	 * @param TbFylx
	 * @return
	 */
	int insertOrUpdFylx(TbFylx tbFylx);
	
	/**
	 * 通过费用类型ID查询费用类型信息
	 * @param TbFylx
	 * @return
	 */
	TbFylx getFylxById(TbFylx tbFylx);
	
	/**
	 * 删除费用类型信息
	 * @param TbFylx
	 * @return
	 */
	int deleteByIds(TbFylx tbFylx);
	
	/**
	 * 费用类型信息回收站
	 * @param TbFylx
	 * @return
	 */
	int deleteTrash(TbFylx tbFylx);
	
	/**
	 * 还原回收站费用类型信息
	 * @param TbFylx
	 * @return
	 */
	int replyFylx(TbFylx tbFylx);
	
	/**
	 * 查询最后一条记录获取代码进行叠加算法
	 * @return
	 */
	TbFylx getLastFylxCode();
	
	List<TbFylx> getAllList(TbFylx tbFylx);
	TbFylx authentication(TbFylx tbFylx);
	TbFylx selectByFylxName(String FylxName);
    List<TbFylx> selectByConAndPage(Page<TbFylx> page, Map params);
    TbFylx selectByPrimaryKey(Integer id);
    int insertFylx(TbFylx tbFylx);
    int modifyUserByPrimaryKey(TbFylx tbFylx);
    int delUserByPrimaryKey(List ids);


}
