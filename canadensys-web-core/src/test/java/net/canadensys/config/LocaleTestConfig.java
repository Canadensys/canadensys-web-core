package net.canadensys.config;

import java.util.ArrayList;
import java.util.List;

import net.canadensys.dataportal.occurrence.controller.MockControllerResourceMapping;
import net.canadensys.web.i18n.SupportedLocaleChangeInterceptor;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Test configuration for SupportedLocaleChangeInterceptorTest class.
 * 
 * @author cgendreau
 *
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = MockControllerResourceMapping.class)
public class LocaleTestConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		SupportedLocaleChangeInterceptor slci = new SupportedLocaleChangeInterceptor();
		slci.setParamName("lang");
		slci.setDefaultLocale("en");
		
		List<String> supportedLocale = new ArrayList<String>();
		supportedLocale.add("en");
		supportedLocale.add("fr");
		slci.setSupportedLocale(supportedLocale);
	    registry.addInterceptor(slci);
	}

}
