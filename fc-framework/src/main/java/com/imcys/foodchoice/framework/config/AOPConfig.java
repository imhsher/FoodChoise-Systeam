package com.imcys.foodchoice.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP配置【暂时未使用】
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AOPConfig {
}
