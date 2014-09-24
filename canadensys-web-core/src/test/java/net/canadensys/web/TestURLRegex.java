package net.canadensys.web;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test URL_REGEX defined in global-functions.ftl
 * This regex is not too strict we prefer to display a wrong URL than blocking a valid URL.
 * Also trying to keep the regex simple since it's not easy to deal with all the possible URL
 * @author cgendreau
 *
 */
public class TestURLRegex {
	
	private static final String URL_REGEX = "^(https?|ftp)://(-\\.)?([^\\s/?\\.#-]+\\.?)+(/[\\S ]*)?$";
	
	@Test
	public void testValidUrl(){
		assertTrue("http://www.google.com".matches(URL_REGEX));
		assertTrue("https://www.google.com".matches(URL_REGEX));
		assertTrue("http://www.google.com/?q=image".matches(URL_REGEX));
		assertTrue("http://www.google.com/resource with space".matches(URL_REGEX));
		assertTrue("http://www.google.com/image.jpg".matches(URL_REGEX));
	}
	
	@Test
	public void showFalsePositiveUrl(){
		assertTrue("http://www/google/com".matches(URL_REGEX));
		assertTrue("https://www.google.c".matches(URL_REGEX));
		assertTrue("https://www.google.comcomcom".matches(URL_REGEX));
	}
	
	@Test
	public void testInvalidUrl(){
		assertFalse("htt://www.google.com".matches(URL_REGEX));
		assertFalse("www.google.com".matches(URL_REGEX));
		assertFalse("http://www.google.co m".matches(URL_REGEX));
		assertFalse("this/is/not/an/url".matches(URL_REGEX));
	}

}
