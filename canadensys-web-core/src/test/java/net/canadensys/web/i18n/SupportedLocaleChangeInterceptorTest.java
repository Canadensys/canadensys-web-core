package net.canadensys.web.i18n;

import static org.junit.Assert.assertEquals;
import net.canadensys.config.LocaleTestConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Test the behavior of SupportedLocaleChangeInterceptor using mock objects.
 * 
 * @author cgendreau
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LocaleTestConfig.class, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class SupportedLocaleChangeInterceptorTest {
	
	@Autowired
    private WebApplicationContext wac;
	
	private SessionLocaleResolver localeResolver = new SessionLocaleResolver();
	
	@Autowired
	private RequestMappingHandlerMapping handlerMapping;
	
	/**
	 * Test regular Locale change with a supported Locale.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSupportedLocale() throws Exception {

	    MockHttpServletRequest request = new MockHttpServletRequest("GET","/test");
	    request.addParameter("lang", "fr");
	    request.setAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE, localeResolver);
	    
	    MockHttpServletResponse response = new MockHttpServletResponse();

        HandlerExecutionChain chain = handlerMapping.getHandler(request);

        for(HandlerInterceptor interceptor : chain.getInterceptors()){
            interceptor.preHandle(request, response, chain.getHandler());
        }
		
		assertEquals("fr", localeResolver.resolveLocale(request).toString());
	}
	
	/**
	 * Test the SupportedLocaleChangeInterceptor with an illegal Locale value.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIllegalLocaleValue() throws Exception {

	    MockHttpServletRequest request = new MockHttpServletRequest("GET","/test");
	    request.addParameter("lang", "no-lang");
	    request.setAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE, localeResolver);
	    
	    MockHttpServletResponse response = new MockHttpServletResponse();

        HandlerExecutionChain chain = handlerMapping.getHandler(request);

        for(HandlerInterceptor interceptor : chain.getInterceptors()){
            interceptor.preHandle(request, response, chain.getHandler());
        }
		
		assertEquals("en", localeResolver.resolveLocale(request).toString());
	}
	
	/**
	 * Test the SupportedLocaleChangeInterceptor with a not supported Locale value.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testNotSupportedLocaleValue() throws Exception {

	    MockHttpServletRequest request = new MockHttpServletRequest("GET","/test");
	    request.addParameter("lang", "it");
	    request.setAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE, localeResolver);
	    
	    MockHttpServletResponse response = new MockHttpServletResponse();

        HandlerExecutionChain chain = handlerMapping.getHandler(request);

        for(HandlerInterceptor interceptor : chain.getInterceptors()){
            interceptor.preHandle(request, response, chain.getHandler());
        }
		
		assertEquals("en", localeResolver.resolveLocale(request).toString());
	}

}
