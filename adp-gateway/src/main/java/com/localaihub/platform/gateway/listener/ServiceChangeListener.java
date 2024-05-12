package com.localaihub.platform.gateway.listener;

import com.github.xiaoymin.knife4j.spring.gateway.Knife4jGatewayProperties;
import com.github.xiaoymin.knife4j.spring.gateway.discover.ServiceDiscoverHandler;
import com.github.xiaoymin.knife4j.spring.gateway.enums.GatewayStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.Objects;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/8 22:14
 */
@Slf4j
@AllArgsConstructor
public class ServiceChangeListener {

    final DiscoveryClient discoveryClient;
    final ServiceDiscoverHandler serviceDiscoverHandler;
    final Knife4jGatewayProperties knife4jGatewayProperties;

    @EventListener(classes = {ApplicationReadyEvent.class, HeartbeatEvent.class, RefreshRoutesEvent.class})
    public void discover() {
        log.debug("discover service.");
        List<String> services = discoveryClient.getServices();
        if (Objects.equals(knife4jGatewayProperties.getStrategy(), GatewayStrategy.DISCOVER)) {
            this.serviceDiscoverHandler.discover(services);
        }
    }
}
