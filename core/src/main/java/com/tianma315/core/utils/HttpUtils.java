package com.tianma315.core.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 提供获取码的业务
 * <p>
 * Created by TianMa-Android on 2018/9/4.
 */
public final class HttpUtils {

    private HttpUtils() {
    }

    private static class SingletonHolder {
        private final static HttpUtils instance = new HttpUtils();
    }

    public static HttpUtils getInstance() {
        return SingletonHolder.instance;
    }

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
