package com.yqsh.diningsys.core.entity;

/**
 * 系统常量定义
 *
 * @author zhshuo create on 2016-07-28 14:22
 */
public interface SystemConstants {
    /*SESSION*/

    String SESSION_USER = "login_user_info";

    String SESSION_USER_MENUS ="login_user_menus";

    /**
     * 客户端登录用户的保存，形式为List<Map>，定时任务，每几分钟进行一次扫描，超时未连接的用户进行删除
     */
    String SESSION_CLIENT_USER = "client_login_user";

    String DATE_FORMATE_TIME = "yyyy-MM-dd HH:mm:ss";

    String DATE_FORMATE_DATE = "yyyy-MM-dd";

    String DATE_FORMATE_YM = "yyyy-MM";

    String DATE_FORMATE_TIME_REGEXP = "^\\d{4}-\\d{2}-\\d{2}\\s{1}\\d{2}:\\d{2}:\\d{2}$";

    String DATE_FORMATE_DATE_REGEXP = "^\\d{4}-\\d{2}-\\d{2}$";

    String DATE_FORMATE_YM_REGEXP = "^\\d{4}-\\d{2}$";



    String DG_RECEPTION_CLEARING_WATER = "dg_reception_clearing_water";

}
