package com.yqsh.diningsys.api.util;

import java.security.NoSuchAlgorithmException;


import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class AnsiiX99MacUtil {

	static{
        Security.addProvider(new BouncyCastleProvider());
    }
	
	public static String getMac(byte[] data, String macKey) {
		int group = (data.length + (8 - 1)) / 8;
		int offset = 0;
		byte[] edata = null;
		for (int i = 0; i < group; i++) {
			byte[] temp = new byte[8];
			if (i != group - 1) {
				System.arraycopy(data, offset, temp, 0, 8);
				offset += 8;
			} else {
				System.arraycopy(data, offset, temp, 0, data.length - offset);
			}
			if (i != 0) {
				temp = XOR(edata, temp);
			}
			edata = desedeEn(ByteUtils.hexStringToBytes(macKey),temp);
		}
		
		return ByteUtils.bytesToHexString(edata);
	}

	
	public static byte[] desedeEn(byte[] key,byte[] data){
        byte[] result = null;
        try {
            SecretKey secretKey = getSecretKeySpec(key);
            Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding","BC");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,new IvParameterSpec(new byte[8]));//?????????0
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    private static SecretKey getSecretKeySpec(byte[] keyB) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("Des");
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyB,"Des");
        return secretKeyFactory.generateSecret(secretKeySpec);
    }
	
	public static byte[] XOR(byte[] edata, byte[] temp) {
		byte[] result = new byte[8];
		for (int i = 0, j = result.length; i < j; i++) {
			result[i] = (byte) (edata[i] ^ temp[i]);
		}
		return result;
	}

	/*public static void main(String[] args) {
		String macKey = "6D9D4D1878262285";//"3132333435363738";
		//String sendStr = "1001<?xml version=\"1.0\" encoding=\"GBK\"?><Transaction><TransHeader><Version>1.0</Version><TransCode>1001</TransCode><ChannelId>00</ChannelId><BranchId>00000000</BranchId><OperatorId>000000</OperatorId><TransNo>2015071500000019</TransNo><TransDate>20150715</TransDate><TransTime>135922</TransTime></TransHeader><TransBody><Request><AcctNo>6223450010046008757</AcctNo><Amt>0.01</Amt><Remark></Remark></Request></TransBody></Transaction>";
		//String data = "8989<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"yes\"?><Transaction><TransBody><Request><Amt>23.89999999999999857891452847979962825775146484375</Amt><CardNo>0000002</CardNo><Remark></Remark></Request></TransBody><TransHeader><BranchId>00</BranchId><ChannelId>00</ChannelId><OperatorId>00????</OperatorId><TransCode>11</TransCode><TransDate>yyyyMMdd</TransDate><TransNo>11111</TransNo><TransTime>HHmmss</TransTime><Version>1.0</Version></TransHeader></Transaction>";
		//String mac = getMac(data.getBytes(), macKey);
		//System.out.println(mac);
		String sendStr = "0001001{ 'tableno' :'??????'}";
		System.out.println(sendStr.length());
		String mac1 = getMac(sendStr.getBytes(), macKey);
		System.out.println(mac1);
		
		System.out.println(UUID.randomUUID().toString().toUpperCase());
	}*/

}
