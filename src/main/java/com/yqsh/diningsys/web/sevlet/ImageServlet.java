package com.yqsh.diningsys.web.sevlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片显示
 *
 * @author zhshuo create on 2016-11-04 9:11
 */
public class ImageServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ctx = getServletContext().getRealPath("/");
        String filePath = ctx +"upload/"+req.getParameter("filePath");

        String pxxtNo = ctx + "app/img/no-image-small.png";
        String pxdtNo = ctx + "app/img/no-image-big.png";

        FileInputStream fis = null;
        File imageFile = new File(filePath);
        if(!imageFile.exists()){
            if(filePath.contains("itemFilePathDt")){
                imageFile = new File(pxdtNo);
            }else{
                imageFile = new File(pxxtNo);
            }
        }

        fis = new FileInputStream(imageFile);

        int size =fis.available(); //得到文件大小
        byte data[]=new byte[size];
        fis.read(data);  //读数据
        fis.close();
        resp.setContentType("image/gif"); //设置返回的文件类型
        OutputStream os = resp.getOutputStream();
        os.write(data);
        os.flush();
        os.close();
    }
}
