package com.yqsh.diningsys.web.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * 支付接口测试
 * @author jianglei
 * 日期 2017年1月13日 下午1:15:12
 *
 */

import com.yqsh.diningsys.core.util.Constants;
import com.yqsh.diningsys.web.service.pay.DgPayInterface;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext.xml")
public class DgPayTest {
	private MockHttpServletRequest request;    
    private MockHttpServletResponse response;  
    @Before  
    public void setUp(){  
        request = new MockHttpServletRequest();    
        request.setCharacterEncoding("UTF-8");    
        response = new MockHttpServletResponse();
    }
	
	@Autowired
	private DgPayInterface dgPayInterFace;
	
	@Test
	public void payTest(){
		String auth_code="130027113702230844";
		String body="客户付款";
		
		dgPayInterFace.dgPayemnt(0.01, "1", Constants.PAY_TYPE_WX, "123456", "321", auth_code, body, request, response);
	}
	
	@Test
	public void ddTest(){
		System.out.println(Double.parseDouble("200.0")==200);
	}
}
