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
 * we do not support/understand the provided locale.
 * 
 * The check is done on the value provided as text.
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
	
	public void setDefaultLocale(String defaultLocale){
		this.defaultLocale = StringUtils.parseLocaleString(defaultLocale);
	}
	
	/**
	 * Comma separated list of supported locale.
	 * 
	 * @param supportedLocales
	 */
	public void setSupportedLocales(String supportedLocales){
		this.supportedLocale.clear();
		
		for(String currLang : supportedLocales.split(",")){
			this.supportedLocale.add(currLang.toLowerCase());
		}
	}
	
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
