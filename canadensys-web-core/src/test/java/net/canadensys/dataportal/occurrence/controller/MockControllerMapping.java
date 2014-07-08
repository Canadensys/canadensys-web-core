package net.canadensys.dataportal.occurrence.controller;

import net.canadensys.web.i18n.annotation.I18nTranslation;

/**
 * Mock class that represents a annotated resource.
 * @author cgendreau
 *
 */
public class MockControllerMapping {
	public static final String RESOURCES_RESOURCE_KEY = "resources";
	public static final String CONTACT_RESOURCE_KEY = "contact";
	
	@I18nTranslation(resourceName=RESOURCES_RESOURCE_KEY,translateFormat="resources/{}")
	public void emptyMethod(){}
	
	@I18nTranslation(resourceName=CONTACT_RESOURCE_KEY,translateFormat="resources/{}/contact")
	public void emptyMethod2(){}

}
