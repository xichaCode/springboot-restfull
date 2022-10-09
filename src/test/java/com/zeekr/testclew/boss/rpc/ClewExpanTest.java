package com.zeekr.testclew.boss.rpc;


import com.alibaba.fastjson.JSON;
import com.zeekr.testclew.tools.YamlDataHelper;
import groovy.util.logging.Slf4j;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @ClassName ClewExpanTest
 * @Description TODO
 * @Author xibu
 * @Date ClewExpanTest 16:12
 * @Version 1.0
 **/
@Slf4j
public class ClewExpanTest extends YamlDataHelper {
    Logger logger = LoggerFactory.getLogger(ClewExpanTest.class);

    @Test
    public void testClewExpan(){
        given()
                .formParam("sourceId","source009")
                .formParam("areaCode","320100")
                .formParam("mobile","17811272821")
        .when()
                .post("https://zeekr-gateway-server-test.lkhaowu.com/clewboss/openapi/clew/access/new")
        .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testClewExpanJsonTest(){
        String body = "{\"sourceId\":\"source009\",\"areaCode\":\"320100\",\"mobile\":\"17811272821\"}";
        given()
                .contentType(ContentType.JSON)
                .header("x-validate-appid","clewweb")
//                .header("x-validate-appid","clewweb")
                .header("x-validate-channel","channelrl")
                .header("x-validate-date","2022-09-03 20:20:33")
                .header("x-validate-token","AT4165bba94fa5423d81e53f727eeba9b1")
                .header("x-validate-debug",true)
                .header("x-validate-nonce","101a0ce7380-2b86-11ed-b001-c3cd48e0a013")
                .header("x-validate-organid","1564608266841886700")
                .header("x-validate-sign","d79c2b138903faeae908b94d3ed25efc")
                .header("x-validate-signtype","MD5")
                .header("x-validate-udid","a4caed00-f82a-11ec-8cfc-679cee11d5a9")
        .and()
        .body(body)
        .when()
                .post("https://zeekr-gateway-server-test.lkhaowu.com/clewboss/openapi/clew/access/new")
        .then()
                .statusCode(200)
                .body("value.state", Matchers.is(0))
                .and()
                .log()
                .all()
                .extract()
                .response();

    }

    @Test(dataProvider = "yamlDataMethod")
    public void testYamlData(Map<String,String> param){
        String zeekrStroedId = param.get("zeekrStoreId");
        String tmallStoreId =  param.get("tmallStoreId");
        String paramList = "[{\"zeekrStoreId\":\"Z03111\",\"tmallStoreId\":\"TM103111\"},{\"zeekrStoreId\":\"Z022\"," +
                "\"tmallStoreId\":\"TM1022\"}]";
        String pamList = "[{}]" +
//        System.out.println(param.get("zeekrStoreId")+"\t"+param.get("tmallStoreId"));
        given()
                .contentType(ContentType.JSON)
                .header("appKey","NjAwMDAwMDc5NDc2MTg5NQ==")
                .header("appSecret","cU5t3vNNahtbvLmnj9GhJhyFQnpumHrKuzcsiFg0/4A=")
                .header("sign","vecKq6Ty1fCj/RKElP25Ajj0Y3JNVnJyIvKZL0tH0+pWT9EYXIbDS+/KNnHwgT7t")
                .and()
                .body(zeekrStroedId)
                .when()
                .post("http://openapi-test.lkhaowu.com/zeekrlife-rpc-order/v1/tmall/store/batchSave")
                .then()
                .statusCode(200)
//                .body("value.state", Matchers.is(0))
                .and()
                .log()
                .all()
                .extract()
                .response();
    }


    @Test
    public void testBatchSave(){
        String paramList = "[{\"zeekrStoreId\":\"Z03111\",\"tmallStoreId\":\"TM103111\"},{\"zeekrStoreId\":\"Z022\"," +
                "\"tmallStoreId\":\"TM1022\"}]";
        given()
                .contentType(ContentType.JSON)
                .header("appKey","NjAwMDAwMDc5NDc2MTg5NQ==")
                .header("appSecret","cU5t3vNNahtbvLmnj9GhJhyFQnpumHrKuzcsiFg0/4A=")
                .header("sign","vecKq6Ty1fCj/RKElP25Ajj0Y3JNVnJyIvKZL0tH0+pWT9EYXIbDS+/KNnHwgT7t")
                .and()
                .body(JSON.toJSON(paramList).toString())
        .when()
                .post("http://openapi-test.lkhaowu.com/zeekrlife-rpc-order/v1/tmall/store/batchSave")
        .then()
                .statusCode(200)
//                .body("value.state", Matchers.is(0))
                .and()
                .log()
                .all()
                .extract()
                .response();
    }
}
