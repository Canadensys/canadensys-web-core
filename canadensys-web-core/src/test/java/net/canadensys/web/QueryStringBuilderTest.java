package net.canadensys.web;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Test the QueryStringBuilder
 * @author cgendreau
 *
 */
public class QueryStringBuilderTest {
	
	@Test
	public void testQueryStringBuilder(){
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("key1", "value1");
		QueryStringBuilder qsb = new QueryStringBuilder();
		qsb.add(paramMap).add("key2", "value2");
		assertEquals("?key1=value1&key2=value2", qsb.toQueryString());
	}
	
	@Test
	public void testEmptyQueryStringBuilder(){
		QueryStringBuilder qsb = new QueryStringBuilder();
		assertEquals("", qsb.toQueryString());
	}
}

