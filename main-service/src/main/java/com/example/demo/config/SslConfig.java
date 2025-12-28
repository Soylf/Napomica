package com.example.demo.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
public class SslConfig {

    @Bean
    @Primary
    public RestTemplate restTemplate() throws Exception {

        // SSL
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null, TrustAllStrategy.INSTANCE)
                .build();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext,
                NoopHostnameVerifier.INSTANCE
        );

        PoolingHttpClientConnectionManager cm =
                PoolingHttpClientConnectionManagerBuilder.create()
                        .setSSLSocketFactory(sslsf)
                        .build();

        CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

        RestTemplate restTemplate = new RestTemplate(
                new HttpComponentsClientHttpRequestFactory(client)
        );

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }
}
