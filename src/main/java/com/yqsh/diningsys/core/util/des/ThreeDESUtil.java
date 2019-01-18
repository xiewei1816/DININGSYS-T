package com.yqsh.diningsys.core.util.des;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * 3DES工具类.
 * 
 * @ClassName: ThreeDES
 * @author hepx
 */
public final class ThreeDESUtil {

	private static final String UTF8 = "UTF-8";
	private static final int NUMBER = 0XFF;
	private static final int NUMBER_16 = 16;
	/**
	 * @Fields ALGORITHM : 算法.
	 */
	private static final String ALGORITHM = "DESede";
	/**
	 * 创建新的实例.
	 */
	private ThreeDESUtil() {
	}
	/**
	 * 
	 * 转换成十六进制字符串.
	 * @param b  byte[]
	 * @return String
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & NUMBER));
			if (stmp.length() == 1) {
				hs = hs.concat("0".concat(stmp));
			} else {
				hs = hs.concat(stmp);
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * 
	 * 将16进制字符串转换成字节码.
	 * @param hex 转换内容
	 * @return   byte[]
	 */
	public static byte[] hex2byte(String hex) {
		byte[] bts = new byte[hex.length() / 2];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), NUMBER_16);
		}
		return bts;
	}
	/**
	 * 加密
	 * @author jianglei
	 * 2016年7月16日 下午2:33:26
	 * @param str 加密字符串
	 * @param desKey 加密key
	 * @return
	 */
	public static String encrypt(String str,String desKey) {
		try {
			/** 根据给定的字节数组构造一个密钥。 */
			SecretKey deskey = new SecretKeySpec(desKey.getBytes(UTF8), ALGORITHM);

			/** 加密 **/
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] buf = c1.doFinal(Base64.encodeBase64(str.getBytes(UTF8)));
			return byte2hex(buf);
		} catch (java.security.NoSuchAlgorithmException e1) {
			throw new ThreeDESException(e1.getMessage(), e1);
		} catch (javax.crypto.NoSuchPaddingException e2) {
			throw new ThreeDESException(e2.getMessage(), e2);
		} catch (java.lang.Exception e3) {
			throw new ThreeDESException(e3.getMessage(), e3);
		}
	}
	/**
	 * 
	 * @author jianglei
	 * 2016年7月16日 下午2:33:57
	 * @param str 明文字符串
	 * @param desKey 解密key
	 * @return
	 */
	public static String decrypt(String str,String desKey) {
		try {
			/** 根据给定的字节数组构造一个密钥。 */
			SecretKey deskey = new SecretKeySpec(desKey.getBytes(UTF8), ALGORITHM);
			/** 解密 **/
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			byte[] buf = c1.doFinal(hex2byte(str));
			return new String(Base64.decodeBase64(buf),UTF8);
		} catch (java.security.NoSuchAlgorithmException e1) {
			throw new ThreeDESException(e1.getMessage(), e1);
		} catch (javax.crypto.NoSuchPaddingException e2) {
			throw new ThreeDESException(e2.getMessage(), e2);
		} catch (java.lang.Exception e3) {
			throw new ThreeDESException(e3.getMessage(), e3);
		}
	}
	/**
	 * 加密方式，不转16进制
	 * @author jianglei
	 * 2016年7月16日 下午2:33:26
	 * @param str 加密字符串
	 * @param desKey 加密key
	 * @return
	 */
	public static String encryptGeneral(String str,String desKey) {
		try {
			/** 根据给定的字节数组构造一个密钥。 */
			SecretKey deskey = new SecretKeySpec(desKey.getBytes(UTF8), ALGORITHM);

			/** 加密 **/
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] buf = c1.doFinal(str.getBytes(UTF8));
			return new String(Base64.encodeBase64(buf));
		} catch (java.security.NoSuchAlgorithmException e1) {
			throw new ThreeDESException(e1.getMessage(), e1);
		} catch (javax.crypto.NoSuchPaddingException e2) {
			throw new ThreeDESException(e2.getMessage(), e2);
		} catch (java.lang.Exception e3) {
			throw new ThreeDESException(e3.getMessage(), e3);
		}
	}
	/**
	 * 解密方式，不转16进制
	 * @author jianglei
	 * 2016年7月16日 下午2:33:57
	 * @param str 明文字符串
	 * @param desKey 解密key
	 * @return
	 */
	public static String decryptGeneral(String str,String desKey) {
		try {
			/** 根据给定的字节数组构造一个密钥。 */
			SecretKey deskey = new SecretKeySpec(desKey.getBytes(UTF8), ALGORITHM);
			/** 解密 **/
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			byte[] buf = c1.doFinal(Base64.decodeBase64(str.getBytes()));
			return new String(buf,UTF8);
		} catch (java.security.NoSuchAlgorithmException e1) {
			throw new ThreeDESException(e1.getMessage(), e1);
		} catch (javax.crypto.NoSuchPaddingException e2) {
			throw new ThreeDESException(e2.getMessage(), e2);
		} catch (java.lang.Exception e3) {
			throw new ThreeDESException(e3.getMessage(), e3);
		}
	}
}
