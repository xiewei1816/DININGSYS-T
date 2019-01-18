package com.yqsh.diningsys.web.sevlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;

public class BarCode2DServlet extends HttpServlet{
	/**   
	 * @Fields serialVersionUID : serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 1L;
	
	private static final String SIZE = "msize";
	private static final String IMAGETYPE = "JPEG";
	
	@Autowired
	private DgConsumerSeatService dgConsumerSeatService;
	
	public void init(ServletConfig config) throws ServletException {
		 super.init();
		 SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
		         config.getServletContext());
	}
	 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String seatId = req.getParameter("id");
		DgConsumerSeat seat = dgConsumerSeatService.selectByPrimaryKey(Integer.valueOf(seatId));
		if (seat != null) {
			String keyCode = "http://"+CacheUtil.getURLInCache("ONLINE_DOMAIN")+"/icatering/h5/auth/queue/openTable?shopId="+CacheUtil.getURLInCache("DOWN_DATA_SHOPKEY")+"&tableId="+seat.getUuidKey();
			ServletOutputStream stream = null;
			try {
				int size=800;
				String msize = req.getParameter(SIZE);
				if (msize != null && !"".equals(msize.trim())) {
					try{
						size=Integer.valueOf(msize);
					} catch (NumberFormatException e) {
						//TODO output to log
					}
				}
				stream = resp.getOutputStream();
				QRCodeWriter writer = new QRCodeWriter();
				BitMatrix m = writer.encode(keyCode, BarcodeFormat.QR_CODE, size, size);
				MatrixToImageWriter.writeToStream(m, IMAGETYPE, stream);
			} catch (WriterException e) {
				e.printStackTrace();
			} finally {
				if (stream != null) {
					stream.flush();
					stream.close();
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
