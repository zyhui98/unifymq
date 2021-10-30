package com.github.zyhui98.express.wp.common;

import cn.hutool.setting.Setting;

/**
 * @author jonny
 * Created on 2021/10/30
 */
public class WpConfig {
    public static final String API_ORDERSTATUS = "orderStatus";
    public static final String API_ORDERCONTRACTURL= "orderContractUrl";
    public static final String API_ORDERCREATE= "orderCreate";
    public static final String WP_RSA_PUBLICKEY = "wp.rsa.publicKey";
    public static final String MY_RSA_PUBLICKEY = "my.rsa.publicKey";
    public static final String MY_RSA_PRIVATEKEY = "my.rsa.privateKey";
    public static final String MY_MERCHANT_CODE = "my.merchant.code";
    public static volatile Setting setting;
    public static final WpConfig INSTANT = new WpConfig();

    private WpConfig(){
        setting = new Setting("wp.setting", true);
    }


    public static String getStr(String group,String key){
        return setting.getMap(group).get(key);
    }

    public static String getStr(String group,String key, String value){
        return setting.getMap(group).getOrDefault(key, value);
    }


}
