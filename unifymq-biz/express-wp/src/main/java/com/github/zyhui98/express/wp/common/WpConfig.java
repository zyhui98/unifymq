package com.github.zyhui98.express.wp.common;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.setting.Setting;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jonny
 * Created on 2021/10/30
 */
public class WpConfig {
    private static final WpConfig INSTANCE = new WpConfig();
    public static final String AUTHNAME = "authName";
    public static final String PRODUCTCODE = "productCode";
    public static final String API_ORDERSTATUS = "orderStatus";
    public static final String API_ORDERDETAILS = "orderDetails";
    public static final String API_ORDERCONTRACTURL= "orderContractUrl";
    public static final String API_ORDERCREATE= "orderCreate";
    public static final String WP_RSA_PUBLICKEY = "wpPublicKey";
    public static final String MY_RSA_PUBLICKEY = "publicKey";
    public static final String MY_RSA_PRIVATEKEY = "privateKey";
    public static final String MY_MERCHANT_CODE = "merchantCode";
    public static final String WP_SETTING = "wp.setting";
    public static volatile Setting setting;
    public static volatile Map<String, Map<String,Object>> settingMap = new ConcurrentHashMap<>(16);

    private WpConfig(){
        if(ResourceUtil.getStreamSafe(WP_SETTING) != null){
            setting = new Setting(WP_SETTING, true);
        }
    }


    public static String getStr(String group,String key){
        return getStr(group,key, null);
    }

    public static String getStr(String group,String key, String value){
        if(!settingMap.isEmpty()){
             if(settingMap.containsKey(group)){
                 return String.valueOf(settingMap.get(group).getOrDefault(key,value));
             } else  {
                 return null;
            }
        }
        return setting.getMap(group).getOrDefault(key, value);
    }


}
