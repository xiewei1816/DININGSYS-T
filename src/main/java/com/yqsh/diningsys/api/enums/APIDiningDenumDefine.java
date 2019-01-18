package com.yqsh.diningsys.api.enums;

public enum APIDiningDenumDefine {
	S0000("0000","成功"),
	S0001("0001","MAC 验证失败"),
	S0002("0002","JSON 字符串错误"),
	S0003("0003","接口请求无法处理"),
	S0004("0004","台位(桌位)不存在"),
	S0005("0005","商品(菜品)不存在"),
	S0006("0006","台位(桌位)未开台"),
	S0007("0007","挂单失败,部分菜品不存在"),
	S0008("0008","单号已经被使用"),
	S9999("9999","未知错误");
	
	private String code;

    private String value;


    APIDiningDenumDefine(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String APIDiningDenumDefine(String code){
        for(APIDiningDenumDefine apiDiningDenumDefine:APIDiningDenumDefine.values()){
            if(apiDiningDenumDefine.code.equals(code)){
                return apiDiningDenumDefine.value;
            }
        }
        return null;
    }
}
