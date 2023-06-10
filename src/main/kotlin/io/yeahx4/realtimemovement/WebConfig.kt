package io.yeahx4.realtimemovement

import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@Controller
@EnableWebMvc
class WebConfig: WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
    }
}
