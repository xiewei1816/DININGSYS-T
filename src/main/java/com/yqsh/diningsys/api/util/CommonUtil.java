package com.yqsh.diningsys.api.util;

import com.yqsh.diningsys.core.entity.SystemConstants;
import com.yqsh.diningsys.web.controller.sysSettings.SystemBackupController;
import com.yqsh.diningsys.web.model.SysUser;

import org.apache.shiro.SecurityUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CommonUtil {

	public static SysUser keepConnect(String token) {
		try {
			List<Map> clientUsers = (List<Map>) SecurityUtils.getSubject()
					.getSession()
					.getAttribute(SystemConstants.SESSION_CLIENT_USER);
			SysUser sysUser = null;
			for (Map user : clientUsers) {
				if (user.get("token").equals(token)) {
					user.put("lastConnectTime", System.currentTimeMillis());
					sysUser = (SysUser) user.get("user");
				}
			}
			return sysUser;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getPropertiesKeyValue(String fileName, String key) {
		InputStream is = CommonUtil.class.getResourceAsStream(fileName);
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(p.contains(key)){
			return p.getProperty(key);
		}
		return null;
	}
}
