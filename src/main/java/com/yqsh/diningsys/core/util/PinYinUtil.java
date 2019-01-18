package com.yqsh.diningsys.core.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字转拼音工具类
 * @author jianglei
 * 日期 2016年10月19日 下午2:57:06
 *
 */
public class PinYinUtil {
	/** 
     * 得到每个汉字首字母，英文字符与数字不变 
     *  
     * @param chineseStr 
     * @return 
     */  
    public static String getFirstSpellUpperCase(String chineseStr) {  
        StringBuffer sb = new StringBuffer();  
        char[] input = chineseStr.toCharArray();  
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();  
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);  
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        if (input != null) {  
            for (char c : input) {  
                if (c > 128) {  
                    try {  
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, format);  
                        if (temp != null) {  
                            sb.append(temp[0].charAt(0));  
                        }  
                    } catch (BadHanyuPinyinOutputFormatCombination e) {  
                        e.printStackTrace();  
                    }  
                } else {  
                    sb.append(c);  
                }  
            }  
        }  
        return sb.toString().replaceAll("\\W", "").trim();
    } 
    public static void main(String[] args) {
    	String str="中国人a21";
		System.out.println(str+"，转换后:"+getFirstSpellUpperCase(str));
	}
}
