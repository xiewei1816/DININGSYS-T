package com.yqsh.diningsys.core.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 * 相关流水号生产方式
 * @author jianglei
 * 日期 2016年10月21日 下午4:46:54
 *
 */
public class SerialNumberUtil {
	
	/**
	 * 流水号
	 * @author jianglei
	 * 日期 2016年10月21日 下午5:04:57
	 * @param strType 流水前缀
	 * @return 返回流水号
	 */
	public static String createInserialNum(String strType){
		Random random = new Random();
        int a=random.nextInt(10);//生成0到9的随机数
        StringBuffer sb=new StringBuffer();
        sb.append(strType).append(System.nanoTime()).append(a);
		return sb.toString();
	}
	/**
	 * 根据规则生成编号
	 * @author jianglei
	 * 日期 2016年11月2日 下午2:20:24
	 * @param prefix 前缀
	 * @param digits 流水位数
	 * @param Str 需要截取的字符串
	 * @return 返回根据规则生成的字符串
	 */
	public static String createNo(String prefix ,int digits,String Str){
		StringBuffer sb=new StringBuffer();
		String superNo="";
		sb.append(prefix);
		if(StringUtils.isNotBlank(Str)){
			String erNo=Str.substring(prefix.length());
			superNo=String.valueOf(Integer.parseInt(erNo)+1);
		}else{
			superNo="1";
		}
		int len=digits-superNo.length();
		if(len>0){
			for (int i = 0; i <len; i++) {
				sb.append("0");
			}
			sb.append(superNo);
		}
		return sb.toString();
	}
	/**
	 * 生成订单号:由时间(17位)加随机数(num)组成（17+num(位)）位字符串
	 * @author jianglei
	 * 日期 2017年1月12日 下午2:06:46
	 * num 表示生产几位随机数
	 * @return
	 */
	public static String cretatOrderNo(int num){
		Random random = new Random();
        StringBuffer sb=new StringBuffer();
        sb.append(DateUtil.dateToStr(new Date(),DateUtil.YYYYMMDDHHMMSSSSS));
        for(int i=0;i<num;i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	/**
	 * 根据uuid生产32位随机字符串
	 * @author jianglei
	 * 日期 2017年1月12日 下午2:14:48
	 * @return
	 */
	public static String create_nonce_str() {
		return UUID.randomUUID().toString().replace("-","");
	}
	public static void main(String[] args) {
		System.out.println("测试值:"+SerialNumberUtil.createNo("S", 5,null));
	}
}
