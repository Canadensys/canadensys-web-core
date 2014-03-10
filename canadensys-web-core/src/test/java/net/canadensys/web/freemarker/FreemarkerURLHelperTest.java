package net.canadensys.web.freemarker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.template.ObjectWrapper;

/**
 * Tests related to FreemarkerURLHelper
 */
public class FreemarkerURLHelperTest {

	@Test
	public void testGetURLReplaceQueryParam() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setRequestURI("/resources/uom-occurrence");
		request.addParameter("test", "1");
		HttpRequestHashModel hr = new HttpRequestHashModel(request,
				ObjectWrapper.DEFAULT_WRAPPER);

		String url = FreemarkerURLHelper.getURLReplaceQueryParam(hr, "test","2");
		assertEquals("http://localhost/resources/uom-occurrence?test=2", url);
	}
	
	@Test
	public void testReplaceCurrentQueryParam() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setRequestURI("/resources/uom-occurrence");
		request.setQueryString("test=1&test2=1");
		HttpRequestHashModel hr = new HttpRequestHashModel(request,
				ObjectWrapper.DEFAULT_WRAPPER);

		String url = FreemarkerURLHelper.replaceCurrentQueryParam(hr, "test","2");
		//the order of the query parameters is always the same?
		assertEquals("test2=1&test=2", url);
	}
	
	@Test
	public void testReplaceCurrentQueryParams() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setRequestURI("/resources/uom-occurrence");
		request.setQueryString("test=1&test2=1&test3=1");
		HttpRequestHashModel hr = new HttpRequestHashModel(request,
				ObjectWrapper.DEFAULT_WRAPPER);

		String url = FreemarkerURLHelper.replaceCurrentQueryParams(hr, "test","2","test3","3");
		//the order of the query parameters is always the same?
		assertEquals("test2=1&test=2&test3=3", url);
		
		//test with odd number of parameters
		url = FreemarkerURLHelper.replaceCurrentQueryParams(hr, "test");
		assertNull(url);
	}
	
	/**
	 * Test different functions to remove query parameters.
	 */
	@Test
	public void testRemoveQueryParamQueryParams(){
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		request.setRequestURI("/resources/uom-occurrence");
		request.setQueryString("test=1&test2=1&test3=1");
		HttpRequestHashModel hr = new HttpRequestHashModel(request,
				ObjectWrapper.DEFAULT_WRAPPER);

		//using getURLRemoveQueryParam
		String url = FreemarkerURLHelper.getURLRemoveQueryParam(hr, "test");
		assertEquals("http://localhost/resources/uom-occurrence?test2=1&test3=1", url);
		
		//using replaceCurrentQueryParams
		url = FreemarkerURLHelper.replaceCurrentQueryParams(hr, "test","");
		assertEquals("test2=1&test3=1", url);
		
		url = FreemarkerURLHelper.replaceCurrentQueryParams(hr, "test2",null);
		assertEquals("test=1&test3=1", url);
	}
}
