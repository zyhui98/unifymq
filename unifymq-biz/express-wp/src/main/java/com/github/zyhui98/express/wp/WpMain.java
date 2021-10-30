package com.github.zyhui98.express.wp;

import cn.hutool.json.JSONUtil;
import com.github.zyhui98.express.wp.common.WpConfig;
import com.sf.wp.sdk.dto.OrderStatusParamsDTO;
import com.sf.wp.sdk.server.impl.WpOrderServiceImpl;
import com.sf.wp.sdk.vo.QueryStatusResponseVO;

import java.util.ArrayList;
import java.util.List;

import static com.sf.wp.sdk.constant.ConfigMapConst.API_VERSION;
import static com.sf.wp.sdk.constant.ConfigMapConst.CLIENT_PRIVATE_KEY;
import static com.sf.wp.sdk.constant.ConfigMapConst.CONFIG_MAP;
import static com.sf.wp.sdk.constant.ConfigMapConst.HTTP_MAX_TIMEOUT;
import static com.sf.wp.sdk.constant.ConfigMapConst.MERCHANT_CODE;
import static com.sf.wp.sdk.constant.ConfigMapConst.PROXY_URL;
import static com.sf.wp.sdk.constant.ConfigMapConst.SERVER_PUBLIC_KEY;

/**
 * @author jonny
 * Created on 2021/10/30
 */
public class WpMain {
    public static void main(String[] args) {
        //客户固定入参
        //微派公钥
        final String xn = "xn";

        CONFIG_MAP.put(SERVER_PUBLIC_KEY, WpConfig.getStr(xn,WpConfig.WP_RSA_PUBLICKEY));
        //商户私钥
        CONFIG_MAP.put(CLIENT_PRIVATE_KEY, WpConfig.getStr(xn,WpConfig.MY_RSA_PUBLICKEY));
        //商户号
        CONFIG_MAP.put(MERCHANT_CODE, WpConfig.getStr(xn,WpConfig.MY_MERCHANT_CODE));
        CONFIG_MAP.put(API_VERSION, "V4.0.0");
        CONFIG_MAP.put(PROXY_URL, "");
        CONFIG_MAP.put(HTTP_MAX_TIMEOUT, 20000);


        queryStatus();

    }

    public static void queryStatus(){
        // 实例化WpOrderServiceImpl工具类
        WpOrderServiceImpl wpOrderService = new WpOrderServiceImpl();
        // 构建传参
        OrderStatusParamsDTO queryParamsDTO = new OrderStatusParamsDTO();
        List<String> orderNoList = new ArrayList<>();
        orderNoList.add("WP210809000002");
        queryParamsDTO.setOrderNos(orderNoList);
        // 执行方法
        QueryStatusResponseVO queryStatusResponseVO = wpOrderService.queryStatus(WpConfig.getStr("api",WpConfig.API_ORDERSTATUS),queryParamsDTO);
        System.out.println(JSONUtil.toJsonStr(queryStatusResponseVO));
    }
}
