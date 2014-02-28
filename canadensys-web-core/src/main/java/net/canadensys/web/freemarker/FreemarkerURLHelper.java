package net.canadensys.web.freemarker;

import java.util.Locale;

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
	 * @param lang
	 * @param resourceName
	 * @param params
	 * @return
	 */
	public static String toI18nResource(String lang, String resourceName, String ... params){
		return I18nUrlBuilder.generateI18nResourcePath(lang,i18NTranslationHandler.getTranslationFormat(resourceName),params);
	}
	
	/**
	 * 
	 * @param hr Request object
	 * @param currentLanguage
	 * @param resourceName
	 * @param params
	 * @return
	 */
	public static String getLanguageSwitchPath(HttpRequestHashModel hr, String currentLanguage, String resourceName, String ... params){		
		String targetLanguage = null;
		//TODO find a better way to handle that
		if(Locale.ENGLISH.getLanguage().equals(currentLanguage)){
			targetLanguage = Locale.FRENCH.getLanguage();
		}
		else if(Locale.FRENCH.getLanguage().equals(currentLanguage)){
			targetLanguage = Locale.ENGLISH.getLanguage();
		}
		
		String path = I18nUrlBuilder.generateI18nResourcePath(targetLanguage,i18NTranslationHandler.getTranslationFormat(resourceName),params);
		//prepend the context path (e.g. /explorer)
		path = hr.getRequest().getContextPath() + path;
		
		if(StringUtils.isNotBlank(hr.getRequest().getQueryString())){
			path += "?"+hr.getRequest().getQueryString();
		}
		return path;
	}
	
	/**
	 * Replace or add a query parameter to the provided uri.
	 * @param uri
	 * @param name
	 * @param value
	 * @return
	 */
	public static String replaceQueryParameter(String uri, String name, String value){
		UriComponentsBuilder bldr = UriComponentsBuilder.fromUriString(uri);
		bldr.replaceQueryParam(name, value);
		return bldr.build().toUriString();
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
