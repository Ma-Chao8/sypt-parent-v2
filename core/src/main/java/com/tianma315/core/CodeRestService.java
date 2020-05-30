package com.tianma315.core;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 提供获取码的业务
 * <p>
 * Created by TianMa-Android on 2018/9/4.
 */
public final class CodeRestService {

    private CodeRestService() {

    }

    private static class SingletonHolder {
        private final static CodeRestService instance = new CodeRestService();
    }

    public static CodeRestService getInstance() {
        return SingletonHolder.instance;
    }
    /* --------------------------------------------------------- */

    public static final String LOGISTICS_CODE = "{logistics_code}";
    public static final String PRODUCT_CODE = "{product_code}";

    /**
     * 获取套标接口
     */
    public static final String URL_PACK_CODE = "http://112.74.55.51:8080/cabby_code_v1/code/json/check/logistics/" + LOGISTICS_CODE + "/" + PRODUCT_CODE;

    private RestTemplate restTemplate;

    public RestTemplate getRestTemplate() {
        if (restTemplate == null)
            restTemplate = new RestTemplate();
        return restTemplate;
    }

    /**
     * @param url
     * @param method
     * @param entity
     * @param <E>
     * @return
     */
    private <E> E doRequest(String url, HttpMethod method, RequestEntity entity, ParameterizedTypeReference<E> reference) {
        return getRestTemplate().exchange(url, method, entity, reference).getBody();
    }


    /**
     * @param url
     * @param entity
     * @param <E>
     * @return
     */
    public <E> E get(String url, RequestEntity entity, ParameterizedTypeReference<E> reference) {
        return doRequest(url, HttpMethod.GET, entity, reference);
    }

    /**
     * @param url
     * @param entity
     * @param <E>
     * @return
     */
    public <E> E post(String url, RequestEntity entity, ParameterizedTypeReference<E> reference) {
        return doRequest(url, HttpMethod.POST, entity, reference);
    }


}
