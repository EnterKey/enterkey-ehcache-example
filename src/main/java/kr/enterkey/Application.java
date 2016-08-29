package kr.enterkey;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	super.addResourceHandlers(registry);
    	registry.addResourceHandler("/**/*.html").addResourceLocations("/");
    	registry.addResourceHandler("/**/*.htm").addResourceLocations("/");
    	registry.addResourceHandler("/**/*.js").addResourceLocations("/");
    	registry.addResourceHandler("/**/*.css").addResourceLocations("/");
    	registry.addResourceHandler("/**/*.jpg").addResourceLocations("/");
    	registry.addResourceHandler("/**/*.png").addResourceLocations("/");
    	registry.addResourceHandler("/**/*.gif").addResourceLocations("/");
    	registry.addResourceHandler("/**/*.ico").addResourceLocations("/");
    	registry.addResourceHandler("/**/*.ttf").addResourceLocations("/");
    	registry.addResourceHandler("/**/*.woff").addResourceLocations("/");

	}

	@Bean
    public ViewResolver viewResolver() {
    	ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();

    	List<View> defaultViews = new ArrayList<View>();
    	defaultViews.add(new MappingJackson2JsonView());
    	resolver.setDefaultViews(defaultViews);
    	resolver.setOrder(0);

    	List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
    	InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
    	internalResourceViewResolver.setPrefix("/WEB-INF/views/");
    	internalResourceViewResolver.setSuffix(".jsp");
    	internalResourceViewResolver.setOrder(1);
    	viewResolvers.add(internalResourceViewResolver);
    	resolver.setViewResolvers(viewResolvers);

    	return resolver;
    }

	@Bean
	public HandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
		requestMappingHandlerMapping.setUseSuffixPatternMatch(true);
		return requestMappingHandlerMapping;
	}

}