package com.yqsh.diningsys.web.service.base.impl;

import com.yqsh.diningsys.core.util.FileUploadUtil;
import com.yqsh.diningsys.web.service.base.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-08-22 16:19
 */
@Service
public class CommonServiceImpl implements CommonService{

    @Override
    public String insertMultiVlues(HttpServletRequest request, MultipartFile multipartFile, String basePath) throws Exception {
        return FileUploadUtil.fileUpload(request,basePath,multipartFile);
    }
}
