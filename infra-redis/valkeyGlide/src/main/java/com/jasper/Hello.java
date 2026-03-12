package com.jasper;

import glide.api.GlideClient;
import glide.api.models.configuration.GlideClientConfiguration;
import glide.api.models.configuration.NodeAddress;
import glide.api.models.configuration.ServerCredentials;
import lombok.extern.slf4j.Slf4j;

/**
 * glide 和lettuce 都是异步 非阻塞 基于多路复用的
 * 但是lettuce是基于netty glide是基于 rust(JNI)
 * valkey 推荐 glide
 */
@Slf4j
public class Hello {
    public static void main(String[] args) {
        GlideClientConfiguration config = GlideClientConfiguration.builder()
                .address(NodeAddress.builder()
                        .host("localhost")
                        .port(6379).build())
                .credentials(
                        ServerCredentials.builder().password("passwd").build())
                .build();

        try (GlideClient glideClient = GlideClient.createClient(config).get()) {
            // 是异步非阻塞的 get 立即返回
            glideClient.set("username", "jasper").get();
            String string = glideClient.get("username").get();
            System.out.println(string);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
