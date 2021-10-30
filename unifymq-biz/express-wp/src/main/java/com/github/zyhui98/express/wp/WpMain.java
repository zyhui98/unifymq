package com.github.zyhui98.express.wp;

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
        CONFIG_MAP.put(SERVER_PUBLIC_KEY, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjUrPvvjHMRNNgbUqln+JbL9hTtqet9jlUouNEsfLH1ErCX1jfY5Nj97ToptNYqnAzra8oXJEJgc9MqTBevNR8JdhhQDVIP0qN3JKKT7PKL0BLwcRC4xcJDWHR0zgZBg4Szrg0feZGgQluzfEqZpzjjNUTprgHzzbhy5FZAz/XcflBA1YU+UOrmcONN2RkzIuwHOYdy+hcz8n/Dz4ikM3RGAxixDXRRDnwJ6kqkYkpCO89EJaxkK8ZYepfhCzLAfr2F6R5qVY9F6CyK4yj/D/AXH8RViUfI3mg+704Wfq0J/1TeanezR8UUyH98kW+So+24zdWQLF83tbpd9oH1eFhQIDAQAB");
        //商户私钥
        CONFIG_MAP.put(CLIENT_PRIVATE_KEY, "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCNSs+++McxE02BtSqWf4lsv2FO2p632OVSi40Sx8sfUSsJfWN9jk2P3tOim01iqcDOtryhckQmBz0ypMF681Hwl2GFANUg/So3ckopPs8ovQEvBxELjFwkNYdHTOBkGDhLOuDR95kaBCW7N8SpmnOOM1ROmuAfPNuHLkVkDP9dx+UEDVhT5Q6uZw403ZGTMi7Ac5h3L6FzPyf8PPiKQzdEYDGLENdFEOfAnqSqRiSkI7z0QlrGQrxlh6l+ELMsB+vYXpHmpVj0XoLIrjKP8P8BcfxFWJR8jeaD7vThZ+rQn/VN5qd7NHxRTIf3yRb5Kj7bjN1ZAsXze1ul32gfV4WFAgMBAAECggEAfO7ipS1bDFTSGtfzkWGyRTswMsZyw4OAuq10j1+khsuBr4F04Idco9NW9Rg3o/8P4niIcIUWJz4NaeNuxWWzVXa1nyJuUh4pHx/q3W+RQ6dkDY5FDx4MN2QxeuVKIJed5z9nnaE+eleieVnFhmZwuRM//8VIWba5Ml3Cs8YeY2/Lsc4m/fRM8BbYqHeiMpnymFocAvmowaG9FgPExyyo1PhHHeI43KJhHeKI8/wPq6OC/VfONC4k3zOx1Skszw6WBVyk3zBX2GnYjOPdhFkybcP82tsH/si1VswZTjRUsRO+BD2ZvYuRgkQEc1izk/UcUbQWZggZrgRcnqA73lTNIQKBgQDzhE3IOYQdXt1DK8wFQzLGEyMgm1AFZauLAVRL3iygyuQ1dKHjrrhPVxngm5NW2Y/IQhCC7wjvlrcI4r1/SMQvOyImLTA05sGMsY0q/4Tgc9BQJGZmlndejR0g3zvxOkEh97iokRldxkZTB6cfvo8C9t3q7apWgfgDOkQURG9vLQKBgQCUiQEGYsmccyZtaiQsrFOWPVc8BoXvs1I+/r+c9FHa9qQcL44kNXljjbJO84nbohtXdVQvCXLl9enO18EfnA3nEX0aeIjRKBJsTv4+MlX9/+JTpvKrmaZ2gfYXqNJJp+7k+Np/UEo1BDrQUcM/JDJmuBXLYTjrv1x3jmIjdz2muQKBgCpQhyOPuwFPhqsxZRZVAvGgSa5Uzqbhb9mLtZYpWR/noZgYWDk0FxrSS9DXt9aTn4Rw1mWqG6dmTQ/iNoNzpYN/hwOTPpkyr9c+0wTxuak29q+2Y4TYCI4Jf1JC+CuGr+KzJQdFj17YIUg0QDon5rnhI9a6zaqSkwIckc/jxGlRAoGAEM0SbZt8/JJV1Wh9IQR5C/RySpreSYe6FOAR4noMTD07wOhsJpJZ4bXkZmPLwykp+JGP7SYfdf2D7d6fvKiVNf7XbMgXibkDpHam8XgWG/32psmqh1iA6MS4hmKVLrmtHv87D82QJ0EMOgYVlK3Oean25SNhIeFqXyee2C4jrhkCgYBx38Nm+MoFUgnWxZWR7SelRxNJV2bB7I+ONTJMX7sib/YqTkTtw6b4VUSGbYjYsobZ9wPdDO2ftPw8ZN9AXYM6LSVwmb7zo4WTe8trRCs1eB85YyIUKSWGSm4f35IiSZUoxWdIoKX8t9kZ2Su/ROoE1KAUPSdx9BAeTxWMO5+oaw==");
        //商户号
        CONFIG_MAP.put(MERCHANT_CODE, "B2019100001");
        CONFIG_MAP.put(API_VERSION, "V4.0.0");
        CONFIG_MAP.put(PROXY_URL, "");
        CONFIG_MAP.put(HTTP_MAX_TIMEOUT, 20000);


    }

    public static void createOrder(){
        // 实例化WpOrderServiceImpl工具类
        WpOrderServiceImpl wpOrderService = new WpOrderServiceImpl();
        // 构建传参
        OrderStatusParamsDTO queryParamsDTO = new OrderStatusParamsDTO();
        List<String> orderNoList = new ArrayList<>();
        orderNoList.add("WP210809000002");
        queryParamsDTO.setOrderNos(orderNoList);
        // 执行方法
        QueryStatusResponseVO queryStatusResponseVO = wpOrderService.queryStatus("http://wp...com:45402/...",queryParamsDTO);
    }
}
