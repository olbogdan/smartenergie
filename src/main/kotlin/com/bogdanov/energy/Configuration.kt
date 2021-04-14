package com.bogdanov.energy

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor
import org.springframework.web.client.RestTemplate
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class Configuration : WebMvcConfigurer {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

    @Bean
    fun deviceResolverHandlerInterceptor(): DeviceResolverHandlerInterceptor =
        DeviceResolverHandlerInterceptor()

    @Bean
    fun deviceHandlerMethodArgumentResolver(): DeviceHandlerMethodArgumentResolver =
        DeviceHandlerMethodArgumentResolver()

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(deviceResolverHandlerInterceptor())
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver?>) {
        resolvers.add(deviceHandlerMethodArgumentResolver())
    }
}