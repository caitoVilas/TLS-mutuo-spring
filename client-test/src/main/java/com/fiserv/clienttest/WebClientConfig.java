package com.fiserv.clienttest;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * @author caito Vilas
 * Cliente que se conecta a un servidor SSL/TLS
 * prueba de comunicacion mutual TLS con intercambio de certificados PKCS12
 * Configuración del webclient
 * JAVA 17 con Spring Boot 3.3.4
 */
@Configuration
public class WebClientConfig {

    private static final String CERTIFICATE_PATH = "c:/certificates/";
    @Bean
    WebClient webClient() throws Exception {

        //carga keystore
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try {
            //InputStream keystoreFile = new FileInputStream(CERTIFICATE_PATH + "client-keystore.p12");
            InputStream keystoreFile = new FileInputStream(CERTIFICATE_PATH + "cli-keystore.p12");
            keyStore.load(keystoreFile, "secret".toCharArray());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //configurar el manager
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, "secret".toCharArray());

        //cargar el truststore
        KeyStore trustStore = KeyStore.getInstance("PKCS12");
        try {
            //InputStream truststoreFile = new FileInputStream(CERTIFICATE_PATH + "client-truststore.p12");
            InputStream truststoreFile = new FileInputStream(CERTIFICATE_PATH + "cli-truststore.jks");
            trustStore.load(truststoreFile, "secret".toCharArray());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //configurar el truststore
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        //construir conexto SSL
        SslContext sslContext = SslContextBuilder
                .forClient()
                .keyManager(keyManagerFactory)
                .trustManager(trustManagerFactory)
                .build();

        //cea el http client
        HttpClient httpClient = HttpClient.create().secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));

        //constuir el webclient
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
