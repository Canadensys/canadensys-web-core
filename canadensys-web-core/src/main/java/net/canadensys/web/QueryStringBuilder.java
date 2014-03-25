package net.canadensys.web;

import java.util.Map;

import org.springframework.web.util.UriComponentsBuilder;

/**
 * Builder specialized to build query string part of an URL.
 * @author cgendreau
 *
 */
public class QueryStringBuilder {
	private UriComponentsBuilder uriBuilder;
	
	public QueryStringBuilder (){
		this.uriBuilder = UriComponentsBuilder.newInstance();
	}
	
	public QueryStringBuilder add(Map<String,String> parameters){
		for(String currKey:parameters.keySet()){
			uriBuilder.replaceQueryParam(currKey, parameters.get(currKey));
		}
		return this;
	}
	
	public QueryStringBuilder add(String name, String value){
		uriBuilder.replaceQueryParam(name, value);
		return this;
	}
	
	/**
	 * Returning the query string starting with '?' character.
	 * @return query string or empty string
	 */
	public String toQueryString(){
		return uriBuilder.build().toUriString();
	}

}
