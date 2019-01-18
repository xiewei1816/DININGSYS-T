package com.yqsh.diningsys.core.util;

import com.yqsh.diningsys.core.entity.SystemConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 文件工具类
 *
 * @author zhshuo create on 2016-08-15 14:24
 */
public class FileUploadUtil {

    /**
     * 图片上传
     * @param path
     * @param file
     * @return
     * @throws Exception
     */
    public static String fileUpload(HttpServletRequest request,String path, MultipartFile file) throws Exception {
        String ctxPath = request.getSession().getServletContext()
                .getRealPath("/");
        String filePath = ctxPath + "/upload/" + path;
        String oldfileName = file.getOriginalFilename();
        oldfileName = oldfileName.substring(oldfileName.lastIndexOf("."));
        String fileName = System.currentTimeMillis() + oldfileName;
        File targetFile = new File(filePath, fileName);
        File fpath = new File(filePath);
        if (!fpath.exists()) {
            fpath.mkdirs();
        }
        file.transferTo(targetFile);
        return path + "/" + fileName;
    }
}
