package tw.timhsu.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebAppConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/stylesheets/**").addResourceLocations("/WEB-INF/resources/stylesheets/");
		 registry.addResourceHandler("/static/**") .addResourceLocations("classpath:/static/");
	}

}
