package net.canadensys.web.i18n.annotation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * Scan from a specified base package to extract content of I18NTranslation annotations.
 * 
 * @author canadensys
 * 
 */
public class I18nTranslationHandler {

	private Map<String, String> resourceTranslateFormatMap = new HashMap<String, String>();

	public I18nTranslationHandler(String basePackage){
		Reflections reflections = new Reflections(
				new ConfigurationBuilder()
						.setUrls(
								ClasspathHelper
										.forPackage(basePackage))
						.setScanners(new MethodAnnotationsScanner()));

		Set<Method> annotated = reflections
				.getMethodsAnnotatedWith(I18nTranslation.class);
		Iterator<Method> annotatedIt = annotated.iterator();

		I18nTranslation annotation;
		while (annotatedIt.hasNext()) {
			annotation = annotatedIt.next()
					.getAnnotation(I18nTranslation.class);
			resourceTranslateFormatMap.put(annotation.resourceName(),
					annotation.translateFormat());
		}
	}

	public String getTranslationFormat(String resourceName) {
		return resourceTranslateFormatMap.get(resourceName);
	}

}
