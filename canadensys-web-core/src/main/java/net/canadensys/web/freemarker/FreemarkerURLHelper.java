package net.canadensys.web.freemarker;

import net.canadensys.web.QueryStringBuilder;
import net.canadensys.web.i18n.I18nUrlBuilder;
import net.canadensys.web.i18n.annotation.I18nTranslationHandler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.ConfigurableMimeFileTypeMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import freemarker.ext.servlet.HttpRequestHashModel;

/**
 * Helper class to manage URL from Freemarker HttpRequestHashModel in Servlet environment.
 * Wrap the class with freemarker.ext.beans.BeansWrapper
 * In your template use something like : ${URLHelper.getURLReplaceQueryParam(Request,"lang","en")}
 * @author canadensys
 *
 */
public class FreemarkerURLHelper {
	
	public static final String LANG_PARAM = "lang";
	
	//TODO find a way to set base package
	private static final I18nTranslationHandler i18NTranslationHandler = 
			new I18nTranslationHandler("net.canadensys.dataportal.occurrence.controller");
	
	/**
	 * Get absolute URL from HttpRequestHashModel and add or change a query string parameter.
	 * @param hr HttpRequestHashModel from Freemarker template (variable Request)
	 * @param name query string parameter to add or change
	 * @param value value of the query string parameter
	 * @return absolute URL as String
	 */
	public static String getURLReplaceQueryParam(HttpRequestHashModel hr, String name, String value){
		UriComponentsBuilder bldr = ServletUriComponentsBuilder.fromRequest(hr.getRequest());
		bldr.replaceQueryParam(name,value);
		return bldr.build().toUriString();
	}
	
	/**
	 * Get a new instance of QueryStringBuilder.
	 * @return
	 */
	public static QueryStringBuilder newQueryStringBuilder(){
		return new QueryStringBuilder();
	}
	
	/**
	 * Get absolute URL from HttpRequestHashModel and remove a query string parameter.
	 * @param hr from Freemarker template (variable Request)
	 * @param name query string parameter to remove
	 * @return absolute URL as String
	 */
	public static String getURLRemoveQueryParam(HttpRequestHashModel hr, String name){
		UriComponentsBuilder bldr = ServletUriComponentsBuilder.fromRequest(hr.getRequest());
		bldr.replaceQueryParam(name);
		return bldr.build().toUriString();
	}
	
	/**
	 * Replace or add a query parameter in the current request and return the new query part of the request.
	 * @param hr HttpRequestHashModel from Freemarker template (variable Request)
	 * @param name query string parameter to add or change
	 * @param value value of the query string parameter
	 * @return query part only e.g. view=table&filter=auto
	 */
	public static String replaceCurrentQueryParam(HttpRequestHashModel hr, String name, String value){
		UriComponentsBuilder bldr = ServletUriComponentsBuilder.fromRequest(hr.getRequest());
		bldr.replaceQueryParam(name,value);
		return bldr.build().getQuery();
	}
	
	/**
	 * Replace, add or remove query parameters in the current request and return the new query part of the request.
	 * You can remove a query parameter by providing an empty string or null as value.
	 * @param hr
	 * @param params array of name/value like params[0]="name" and params[1] = "values". Length of the array
	 * must be even.
	 * @return query part only e.g. view=table&filter=auto or null if the params array is odd.
	 */
	public static String replaceCurrentQueryParams(HttpRequestHashModel hr, String ... params){
		UriComponentsBuilder bldr = ServletUriComponentsBuilder.fromRequest(hr.getRequest());
		
		if(params.length%2 != 0){
			return null;
		}
		for(int i=0;i<params.length;i+=2){
			//if the value is blank, remove the parameter
			if(StringUtils.isBlank(params[i+1])){
				bldr.replaceQueryParam(params[i]);
			}
			else{
				bldr.replaceQueryParam(params[i],params[i+1]);
			}
		}
		return bldr.build().getQuery();
	}
	
	/**
	 * Build a i18n resource path from a resource name.
	 * This function relies on I18nTranslationHandler.
	 * The returned resource path starts with the language.
	 * e.g. toI18nResource("en", "search) produces "/en/search"
	 * @param lang
	 * @param resourceName
	 * @return resource path or null if no translation format could be found.
	 */
	public static String toI18nResource(String lang, String resourceName){
		return I18nUrlBuilder.generateI18nResourcePath(lang,i18NTranslationHandler.getTranslationFormat(resourceName),(String)null);
	}
	
	/**
	 * Build a i18n resource path from a resource name and parameters.
	 * Parameters will be URL-encoded.
	 * @param lang
	 * @param resourceName
	 * @param params
	 * @return
	 */
	public static String toI18nResource(String lang, String resourceName, String ... params){
		return I18nUrlBuilder.generateI18nResourcePath(lang,i18NTranslationHandler.getTranslationFormat(resourceName),params);
	}
	
	/**
	 * Get an URI with a provided language as query parameter.
	 * Then parameter will be added or replaced.
	 * e.g. 
	 * -getUriWithLanguage("/search?q=cal","fr") will produce /search?q=cal&lang=fr
	 * -getUriWithLanguage("/search?q=cal&lang=en","fr") will produce /search?q=cal&lang=fr
	 * 
	 * @param uri
	 * @param lang
	 * @return
	 */
	public static String getUriWithLanguage(String uri, String lang){
		UriComponentsBuilder bldr = UriComponentsBuilder.fromUriString(uri);
		bldr.replaceQueryParam(LANG_PARAM,lang);
		return bldr.build().toUriString();
	}
	
	/**
	 * Get the mime type (e.g. image/x-png) of a file designed by a url(or not).
	 * @param url
	 * @return mime type image/jpeg text/html
	 */
	public static String getMimeFileType(String url){
		ConfigurableMimeFileTypeMap cmft = new ConfigurableMimeFileTypeMap();
		return cmft.getContentType(url);
	}
}
