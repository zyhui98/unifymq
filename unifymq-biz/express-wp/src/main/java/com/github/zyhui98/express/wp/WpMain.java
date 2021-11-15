package com.github.zyhui98.express.wp;

import cn.hutool.json.JSONUtil;
import com.github.zyhui98.express.wp.common.WpConfig;
import com.sf.wp.sdk.dto.OrderDetailParamDTO;
import com.sf.wp.sdk.dto.OrderFileQueryDTO;
import com.sf.wp.sdk.dto.OrderStatusParamsDTO;
import com.sf.wp.sdk.dto.create.OrderCreateRequestDTO;
import com.sf.wp.sdk.dto.create.OrderExtraInfo;
import com.sf.wp.sdk.dto.create.OrderInfo;
import com.sf.wp.sdk.dto.create.OrderLogisticsBaseInfo;
import com.sf.wp.sdk.server.impl.WpOrderServiceImpl;
import com.sf.wp.sdk.vo.CreateOrderResponseVO;
import com.sf.wp.sdk.vo.QueryStatusResponseVO;
import com.sf.wp.sdk.vo.WpOrderDetailVO;
import com.sf.wp.sdk.vo.WsContractInfoVO;
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
    public static String mOrderNo = "XN".concat(System.currentTimeMillis()+"");
    public static String company = "";

    public static void init() {
        company = WpConfig.getStr("api", "company");
        init(company);
    }

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
        CONFIG_MAP.put(HTTP_MAX_TIMEOUT, 10000);
    }

    public static void main(String[] args) {
        init();
        queryStatus("XN1636821244341");
        //getFile("XN1636360040643");
        //final CreateOrderResponseVO createOrderResponseVO = orderCreate();
        //if (!createOrderResponseVO.getData().getSuccessList().isEmpty()){
        //    final String merchantOrderNo = createOrderResponseVO.getData().getSuccessList().get(0).getMerchantOrderNo();
        //    log.info("merchantOrderNo:{}", merchantOrderNo);
        //    try {
        //        TimeUnit.SECONDS.sleep(1);
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //    queryStatus(merchantOrderNo);
        //    queryDetail(merchantOrderNo);
        //    getFile(merchantOrderNo);
        //}
        //[main] INFO com.github.zyhui98.express.wp.WpMain - CreateOrderResponseVO:CreateOrderResponseVO{code='0', message='成功', data=OrderCreateResultDTO{successList=[CreateOrderSuccDTO{merchantOrderNo='xn000001', wpOrderNo='WP211101000009', failureReason=''}], failureList=[]}}
        //[main] INFO com.github.zyhui98.express.wp.WpMain - queryStatusResponseVO:{"code":"0","data":[{"mailNo":"","orderNo":"WP211101000009","merchantOrderNo":"xn000001","status":-2,"info":"下单中"}],"message":"成功"}
    }

    public static void getFile(String mOrderNo){
        WpOrderServiceImpl wpOrderService = new WpOrderServiceImpl();
        OrderFileQueryDTO orderFileQueryDTO = new OrderFileQueryDTO();
        orderFileQueryDTO.setMerchantOrderNo(mOrderNo);
        final WsContractInfoVO wsContractInfoVO = wpOrderService.getOrderContract(WpConfig.getStr("api", WpConfig.API_ORDERCONTRACTURL), orderFileQueryDTO);
        log.info("wsContractInfoVO:{}", JSONUtil.toJsonStr(wsContractInfoVO));

    }

    public static QueryStatusResponseVO queryStatus(String merchantOrderNo) {
        // 实例化WpOrderServiceImpl工具类
        WpOrderServiceImpl wpOrderService = new WpOrderServiceImpl();
        // 构建传参
        OrderStatusParamsDTO queryParamsDTO = new OrderStatusParamsDTO();
        List<String> merchantOrderNoList = new ArrayList<>();
        merchantOrderNoList.add(merchantOrderNo);
        queryParamsDTO.setMerchantOrderNos(merchantOrderNoList);
        // 执行方法
        QueryStatusResponseVO queryStatusResponseVO = wpOrderService.queryStatus(WpConfig.getStr("api", WpConfig.API_ORDERSTATUS), queryParamsDTO);
        log.info("queryStatusResponseVO:{}", JSONUtil.toJsonStr(queryStatusResponseVO));
        return queryStatusResponseVO;
    }

    public static WpOrderDetailVO queryDetail(String merchantOrderNo) {
        // 实例化WpOrderServiceImpl工具类
        WpOrderServiceImpl wpOrderService = new WpOrderServiceImpl();
        // 构建传参
        OrderDetailParamDTO queryParamsDTO = new OrderDetailParamDTO();
        List<String> merchantOrderNoList = new ArrayList<>();
        merchantOrderNoList.add(merchantOrderNo);
        queryParamsDTO.setMerchantOrderNos(merchantOrderNoList);
        // 执行方法
        final WpOrderDetailVO wpOrderDetailVO = wpOrderService.queryOrderDetail(WpConfig.getStr("api", WpConfig.API_ORDERDETAILS), queryParamsDTO);
        log.info("wpOrderDetailVO:{}", JSONUtil.toJsonStr(wpOrderDetailVO));
        return wpOrderDetailVO;
    }

    public static CreateOrderResponseVO orderCreate() {

        WpOrderServiceImpl wpOrderService = new WpOrderServiceImpl();

        OrderCreateRequestDTO orderCreateRequestDTO = new OrderCreateRequestDTO();
        orderCreateRequestDTO.setMerchantCode(WpConfig.getStr(company, WpConfig.MY_MERCHANT_CODE));
        orderCreateRequestDTO.setProductCode(WpConfig.getStr(company, WpConfig.PRODUCTCODE));
        orderCreateRequestDTO.setAuthName(WpConfig.getStr(company, WpConfig.AUTHNAME));

        OrderInfo orderInfo = new OrderInfo();
        orderCreateRequestDTO.setOrderInfos(Arrays.asList(orderInfo));
        orderInfo.setOrderType(0);
        orderInfo.setMerchantOrderNo(mOrderNo);
        orderInfo.setMonthlyCardNo("9999999999");

        OrderLogisticsBaseInfo logisticsBaseInfo = new OrderLogisticsBaseInfo();
        orderInfo.setLogisticsBaseInfo(logisticsBaseInfo);
        logisticsBaseInfo.setdContact("朱云辉");
        logisticsBaseInfo.setdTel("13524080425");
        logisticsBaseInfo.setdAddress("广东省深圳市福田区华强北桑达工业区404栋首层");
        logisticsBaseInfo.setjContact("朱云辉");
        logisticsBaseInfo.setjCompany("小牛");
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

        orderInfo.setExtraInfos(createOrderExtraInfo("merchantOrderNo", mOrderNo,
                "partyType", "ALL_NM",
                "signPos1", "x: 100, y: 100,page:1,signDateBeanType:0）",
                "stringExt1", "12个月",
                "stringExt2", "标准套餐",
                "stringExt3", "abcdefg",
                "stringExt4", "iphone 13 pro",
                "stringExt5", "2021-10-31",
                "stringExt6", "1",
                "stringExt7", "3000"
        ));
        log.info("orderCreateRequestDTO:{}", JSONUtil.toJsonStr(orderCreateRequestDTO));
        final CreateOrderResponseVO createOrderResponseVO = wpOrderService.createOrder(WpConfig.getStr("api", WpConfig.API_ORDERCREATE),
                orderCreateRequestDTO);
        log.info("CreateOrderResponseVO:{}",createOrderResponseVO);

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
