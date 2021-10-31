package com.github.zyhui98.express.wp;

import cn.hutool.json.JSONUtil;
import com.github.zyhui98.express.wp.common.WpConfig;
import com.sf.wp.sdk.dto.OrderStatusParamsDTO;
import com.sf.wp.sdk.dto.create.OrderCreateRequestDTO;
import com.sf.wp.sdk.dto.create.OrderExtraInfo;
import com.sf.wp.sdk.dto.create.OrderInfo;
import com.sf.wp.sdk.dto.create.OrderLogisticsBaseInfo;
import com.sf.wp.sdk.server.impl.WpOrderServiceImpl;
import com.sf.wp.sdk.vo.CreateOrderResponseVO;
import com.sf.wp.sdk.vo.QueryStatusResponseVO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
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
@Slf4j
public class WpMain {
    public static String group = "xn";

    public static void init(String group) {
        //客户固定入参
        //微派公钥
        CONFIG_MAP.put(SERVER_PUBLIC_KEY, WpConfig.getStr(group, WpConfig.WP_RSA_PUBLICKEY));
        //商户私钥
        CONFIG_MAP.put(CLIENT_PRIVATE_KEY, WpConfig.getStr(group, WpConfig.MY_RSA_PUBLICKEY));
        //商户号
        CONFIG_MAP.put(MERCHANT_CODE, WpConfig.getStr(group, WpConfig.MY_MERCHANT_CODE));
        CONFIG_MAP.put(API_VERSION, "V4.0.0");
        CONFIG_MAP.put(PROXY_URL, "");
        CONFIG_MAP.put(HTTP_MAX_TIMEOUT, 20000);
    }

    public static void main(String[] args) {
        init(group);
        final CreateOrderResponseVO createOrderResponseVO = orderCreate();
        if (!createOrderResponseVO.getData().getSuccessList().isEmpty()){
            final String merchantOrderNo = createOrderResponseVO.getData().getSuccessList().get(0).getMerchantOrderNo();
            log.info("merchantOrderNo:{}", merchantOrderNo);
            queryStatus(merchantOrderNo);
        }
    }

    public static QueryStatusResponseVO queryStatus(String orderNo) {
        // 实例化WpOrderServiceImpl工具类
        WpOrderServiceImpl wpOrderService = new WpOrderServiceImpl();
        // 构建传参
        OrderStatusParamsDTO queryParamsDTO = new OrderStatusParamsDTO();
        List<String> orderNoList = new ArrayList<>();
        orderNoList.add("xn000001");
        queryParamsDTO.setOrderNos(orderNoList);
        // 执行方法
        QueryStatusResponseVO queryStatusResponseVO = wpOrderService.queryStatus(WpConfig.getStr("api", WpConfig.API_ORDERSTATUS), queryParamsDTO);
        log.info("queryStatusResponseVO:{}", JSONUtil.toJsonStr(queryStatusResponseVO));
        return queryStatusResponseVO;
    }

    public static CreateOrderResponseVO orderCreate() {
        WpOrderServiceImpl wpOrderService = new WpOrderServiceImpl();

        OrderCreateRequestDTO orderCreateRequestDTO = new OrderCreateRequestDTO();
        orderCreateRequestDTO.setMerchantCode(WpConfig.getStr(group, WpConfig.MY_MERCHANT_CODE));
        orderCreateRequestDTO.setProductCode(WpConfig.getStr(group, WpConfig.PRODUCTCODE));
        orderCreateRequestDTO.setAuthName(WpConfig.getStr(group, WpConfig.AUTHNAME));

        OrderInfo orderInfo = new OrderInfo();
        orderCreateRequestDTO.setOrderInfos(Arrays.asList(orderInfo));
        orderInfo.setOrderType(0);
        orderInfo.setMerchantOrderNo("xn000001");
        orderInfo.setMonthlyCardNo("9999999999");

        OrderLogisticsBaseInfo logisticsBaseInfo = new OrderLogisticsBaseInfo();
        orderInfo.setLogisticsBaseInfo(logisticsBaseInfo);
        logisticsBaseInfo.setdContact("张三");
        logisticsBaseInfo.setdTel("13524080425");
        logisticsBaseInfo.setdAddress("广东省深圳市福田区华强北桑达工业区404栋首层");
        logisticsBaseInfo.setjContact("朱云辉");
        logisticsBaseInfo.setjTel("13524080425");
        logisticsBaseInfo.setjAddress("上海市闵行区园秀路秀文路莘庄中心3楼");

        //"depositumInfo":"1",
        //        "depositumNo":"1",
        //        "payMethod":"0",
        //        "expressType":"1",
        //        "isDoCall":"0"
        logisticsBaseInfo.setDepositumInfo("手机");
        logisticsBaseInfo.setDepositumNo("1");
        logisticsBaseInfo.setPayMethod("0");
        logisticsBaseInfo.setExpressType("2");

        orderInfo.setExtraInfos(createOrderExtraInfo("商户订单号", "xn000001",
                "签署人类型", "个人",
                "签名区", "接受人签名",
                "租期", "12个月",
                "发货时间", "2021-10-31",
                "设备串码 IMEI", "abcdefg",
                "型号配置", "iphone 13 pro",
                "套餐", "标准套餐",
                "数量", "1",
                "设备押金或价值（元）", "3000",
                "月结卡号", "9999999999"
        ));
        log.info("orderCreateRequestDTO:{}", JSONUtil.toJsonStr(orderCreateRequestDTO));
        final CreateOrderResponseVO createOrderResponseVO = wpOrderService.createOrder(WpConfig.getStr("api", WpConfig.API_ORDERCREATE),
                orderCreateRequestDTO);
        log.info("CreateOrderResponseVO:{}",
                createOrderResponseVO);

        return  createOrderResponseVO;


    }

    public static List<OrderExtraInfo> createOrderExtraInfo(String... strings) {
        List<OrderExtraInfo> orderExtraInfos = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            OrderExtraInfo orderExtraInfo = new OrderExtraInfo();
            orderExtraInfo.setExtraKey(strings[i]);
            orderExtraInfo.setExtraValue(strings[++i]);
            orderExtraInfos.add(orderExtraInfo);
        }
        return orderExtraInfos;

    }

}
