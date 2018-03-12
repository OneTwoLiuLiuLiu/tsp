package com.sunyard.frameworkset.conf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunyard.frameworkset.infrastructure.filter.ClientInfoFilter;
import com.sunyard.frameworkset.infrastructure.filter.LogFilter;
import com.sunyard.frameworkset.plugin.load.PluginResourceFilter;
import com.sunyard.frameworkset.plugin.login.interceptor.SessionInterceptor;
import com.sunyard.frameworkset.web.springmvc.mvc.FapBeanPostProcessor;
import com.sunyard.frameworkset.web.springmvc.mvc.FapContentNegotiatingViewResolver;
import com.sunyard.frameworkset.web.springmvc.mvc.FapMvcConf;
import com.sunyard.frameworkset.web.springmvc.mvc.RunBinderMvcInterceptor;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;
import org.springframework.web.servlet.view.xml.MarshallingView;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyingdan on 2016/8/3.
 */
@Configuration
@Profile( "dev" )
public class WebConfig extends FapMvcConf {


	@Value("${downloadFilePath}")
	private String downloadUrl;

	@Bean
	public BeanPostProcessor requestMappingHandlerMappingPostProcessor () {
		return new FapBeanPostProcessor ();
	}


	@Bean
	public Filter clientInfoFilter () {
		return new ClientInfoFilter ();
	}

	@Bean
	public Filter pluginResourceFilter () {
		return new PluginResourceFilter ();
	}

	@Bean
	public Filter logFilter () {
		return new LogFilter ();
	}

	@Bean
	public FilterRegistrationBean clientInfoFilterRegistration () {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean ();
		registrationBean.setFilter ( clientInfoFilter () );
		registrationBean.addUrlPatterns ( "/*" );
		return registrationBean;
	}

	@Bean
	@ConditionalOnProperty(name = "log.filter.enable", matchIfMissing = true)
	public FilterRegistrationBean logFilterRegistration () {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean ();
		registrationBean.setFilter ( logFilter () );
		registrationBean.addUrlPatterns ( "/*" );
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean pluginResourceFilterRegistration () {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean ();
		registrationBean.setFilter ( pluginResourceFilter () );
		registrationBean.addUrlPatterns ( "/plugin/*" );
		return registrationBean;
	}


	@Override
	public void addInterceptors ( InterceptorRegistry registry ) {
		registry.addInterceptor ( new SessionInterceptor () ).addPathPatterns ( "/**" );
		registry.addInterceptor ( new RunBinderMvcInterceptor () ).addPathPatterns ( "/**" );
		registry.addInterceptor(new PageClearInterceptor()).addPathPatterns("/**");
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation is empty.
	 *
	 * @param registry
	 */
	@Override
	public void addResourceHandlers ( ResourceHandlerRegistry registry ) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/" );
		registry.addResourceHandler("/download/**").addResourceLocations("file:"+downloadUrl );
	}

	@Override
	public void configureContentNegotiation ( ContentNegotiationConfigurer configurer ) {
		configurer.ignoreAcceptHeader ( true ).defaultContentType ( MediaType.TEXT_HTML );
	}

	@Bean
	public ViewResolver viewResolver ( ContentNegotiationManager manager, VelocityViewResolver velocityViewResolver ) {

		FapContentNegotiatingViewResolver contentNegotiatingViewResolver = new FapContentNegotiatingViewResolver ();
		contentNegotiatingViewResolver.setContentNegotiationManager ( manager );

		List< View > views = new ArrayList<> ();

		MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView ();
		ObjectMapper            objectMapper            = mappingJackson2JsonView.getObjectMapper ();
		objectMapper.setSerializationInclusion ( JsonInclude.Include.NON_NULL );
		views.add ( mappingJackson2JsonView );

		MarshallingView marshallingView = new MarshallingView ();
		Jaxb2Marshaller marshaller      = new Jaxb2Marshaller ();
		Class< ? >[]    classes         = boundClass ();
		if ( !ArrayUtils.isEmpty ( classes ) ) {
			marshaller.setClassesToBeBound ( boundClass () );
		}
		marshallingView.setMarshaller ( marshaller );
		views.add ( marshallingView );

		contentNegotiatingViewResolver.setDefaultViews ( views );

		List< ViewResolver > resolvers = new ArrayList<> ();
		resolvers.add ( velocityViewResolver );

		contentNegotiatingViewResolver.setViewResolvers ( resolvers );

		return contentNegotiatingViewResolver;
	}

	/**
	 * xml需要绑定的class
	 *
	 * @return
	 */
	protected Class< ? >[] boundClass () {
		return new Class[0];
	}

}
