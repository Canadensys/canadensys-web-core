package net.canadensys.web.i18n;

import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.google.common.collect.Lists;

/**
 * Override of Spring's LocaleChangeInterceptor to avoid setting an invalid Locale in the LocaleResolver if
 * the provided locale is not supported/understood 
 * 
 * The comparison of the Locale is run on the value provided as text.
 * 
 * Partially solves https://jira.spring.io/browse/SPR-9456
 * 
 * @author cgendreau
 *
 */
public class SupportedLocaleChangeInterceptor extends LocaleChangeInterceptor {
	
	public final List<String> supportedLocale = Lists.newArrayList();
	public Locale defaultLocale = Locale.getDefault();
	
	public SupportedLocaleChangeInterceptor(){
		supportedLocale.add(defaultLocale.toString());
	}
	
	/**
	 * Set the locale to use in case the provided locale is not supported.
	 * 
	 * @param defaultLocale
	 * @throws IllegalStateException in case of an invalid locale specification
	 */
	public void setDefaultLocale(String defaultLocale){
		this.defaultLocale = StringUtils.parseLocaleString(defaultLocale);
	}
	
	/**
	 * Set locale(s) supported by the current web application.
	 * 
	 * @param supportedLocale
	 * @throws IllegalStateException in case of an invalid locale specification
	 */
	public void setSupportedLocale(List<String> supportedLocale){
		this.supportedLocale.clear();
		
		for(String currLocale : supportedLocale){
			this.supportedLocale.add(currLocale.toLowerCase());
			// just try to parse it, IllegalStateException will be thrown if not valid
			StringUtils.parseLocaleString(currLocale);
		}
	}
	
	/**
	 * Keep the original code but add a check on the Locale before setting it into the localeResolver.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {
		String newLocale = request.getParameter(getParamName());
		if (newLocale != null) {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			if (localeResolver == null) {
				throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
			}
			
			// if we do not support the Locale, use the defaultLocale.
			if(!supportedLocale.contains(newLocale.toLowerCase())){
				newLocale = defaultLocale.toString();
			}
			
			localeResolver.setLocale(request, response, StringUtils.parseLocaleString(newLocale));
		}
		// Proceed in any case.
		return true;
	}

}
