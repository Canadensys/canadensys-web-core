package net.canadensys.web.i18n;

import static org.junit.Assert.assertEquals;
import net.canadensys.web.i18n.annotation.I18nTranslation;
import net.canadensys.web.i18n.annotation.I18nTranslationHandler;

import org.junit.Test;

/**
 * Test I18nUrlBuilder with a mock annotated class.
 * @author cgendreau
 *
 */
public class I18nUrlBuilderTest {

	private static final String RESOURCES_RESOURCE_KEY = "resources";
	
	/**
	 * Test i18n URL building based on @I18NTranslation.
	 */
	@Test
	public void testAnnotationProcessing(){
		I18nTranslationHandler i18nTranslation = new I18nTranslationHandler("net.canadensys.web.i18n");
		
		String url = I18nUrlBuilder.generateI18nResourcePath("en", i18nTranslation.getTranslationFormat(RESOURCES_RESOURCE_KEY), "2");
		assertEquals("/en/resources_en/2",url);
		
		url = I18nUrlBuilder.generateI18nResourcePath("fr", i18nTranslation.getTranslationFormat(RESOURCES_RESOURCE_KEY), "2");
		assertEquals("/fr/ressources_fr/2",url);
		
		url = I18nUrlBuilder.generateI18nResourcePath("fr", i18nTranslation.getTranslationFormat(RESOURCES_RESOURCE_KEY), "2 3");
		assertEquals("/fr/ressources_fr/2%203",url);
	}
	
	/**
	 * Mock class that represents a annotated resource.
	 * @author cgendreau
	 *
	 */
	@SuppressWarnings("unused")
	private static class I18NTestClass{
		@I18nTranslation(resourceName=RESOURCES_RESOURCE_KEY,translateFormat="resources/{}")
		public void emptyMethod(){}
	}

}
