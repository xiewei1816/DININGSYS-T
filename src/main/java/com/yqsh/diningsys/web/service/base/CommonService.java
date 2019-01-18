package com.yqsh.diningsys.web.service.base;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-08-22 16:18
 */
public interface CommonService {

    String insertMultiVlues(HttpServletRequest request, MultipartFile multipartFile, String basePath) throws Exception;

}
