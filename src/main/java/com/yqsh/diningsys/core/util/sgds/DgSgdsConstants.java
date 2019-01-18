package com.yqsh.diningsys.core.util.sgds;

/**
 * 蜀国大师相关信息
 * @author xiewei
 * 2017年9月27日 下午2:06:23
 */
public class DgSgdsConstants {
	/**
	 * 请求方式get
	 */
	public static String REQUST_METHOD_GET = "GET";
	/**
	 * 请求方式POST
	 */
	public static String REQUST_METHOD_POST = "POST";
	/**
	 * 测试地址调用入口地址
	 */
	public static String SGDS_TEST_URL="http://open.qct.a4.366ec.cn/?";
	/**
	 * 测试配置端
	 */
	public static String SGDS_TEST_CONFIGURE ="http://qct.a4.366ec.cn/?";
	/**
	 * 正式地址调用入口地址
	 */
//	public static String SGDS_FORMAL_URL="https://qctApi.366ec.cn/?";
	public static String SGDS_FORMAL_URL="https://qctapi.366ec.cn/?";
	/**
	 * 正式配置端
	 */
	public static String SGDS_FORMAL_CONFIGURE ="http://qct.366ec.cn/?";
	/**
	 * 通过授权获取用户的令牌接口名
	 */
	public static String SGDS_Token_GetAccessToken = "Token.GetAccessToken";
	/**
	 * 通过会员手机号进行积分管理接口名
	 */
	public static String SGDS_User_AddIntegralByMobile = "User.AddIntegralByMobile";
	/**
	 * 接口版本号（1.0版传入1即可）
	 */
	public static String VERSION = "1";
	/**
	 * 用户唯一凭证
	 */
//	public static String appId = "73FCBA8C47545967";
	public static String appId = "E7E37E7D47A86203";
	/**
	 * Sign签名生成规则秘钥
	 */
//	public static String appSecret = "d3f4d471909c5d869ebeee695986bba8";
	public static String appSecret = "c7ebc9b4bcb5eaa953509b278869b8b2";

}
