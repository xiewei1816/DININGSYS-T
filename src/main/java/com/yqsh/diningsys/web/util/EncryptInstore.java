package com.yqsh.diningsys.web.util;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.StringUtils;

public class EncryptInstore {


	private final static String DES = "DES";

	public static void main(String[] args) throws Exception {
		// String data = "75b9a71b-611e-4091-b49d-9ef8b717fbff";//店铺ID
		String data = "36456b2c-723a-4570-bc27-bb5e7a491200";// 店铺ID

		String key = "!@#$%http://www.yqsh.com&&123456789^*";// 约定的key
		System.out.println("加密：" + encryptData(data, key));
		System.out.println("解密：" + decryptData(encryptData(data, key), key));
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encryptData(String data, String key) {
		try{
			if (StringUtils.isNotBlank(data) && StringUtils.isNotBlank(key)) {
				byte[] bt;
				bt = encrypt(data.getBytes(), key.getBytes());
				// String strs = new BASE64Encoder().encode(bt);
				String strs = byte2hex(bt);
				return strs;
			} else {
				throw new Exception("data is null");
			}	
		} catch(Exception e){
			return null;
		}

	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decryptData(String data, String key) throws Exception {
		if (StringUtils.isNotBlank(data) && StringUtils.isNotBlank(key)) {
			// return new String();
			// BASE64Decoder decoder = new BASE64Decoder();
			// byte[] buf = decoder.decodeBuffer(data);
			// byte[] bt = decrypt(buf, key.getBytes());
			byte[] buf = hex2byte(data.getBytes());
			byte[] bt = decrypt(buf, key.getBytes());
			return new String(bt);
		} else {
			throw new Exception("data is null");
		}

	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException("长度不是偶数");
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

}