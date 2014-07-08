package net.canadensys.web.i18n;

import static org.junit.Assert.assertEquals;
import net.canadensys.dataportal.occurrence.controller.MockControllerMapping;
import net.canadensys.web.i18n.annotation.I18nTranslationHandler;

import org.junit.Test;

/**
 * Test I18nUrlBuilder with a mock annotated class.
 * @author cgendreau
 *
 */
public class I18nUrlBuilderTest {

	/**
	 * Test i18n URL building based on @I18NTranslation.
	 */
	@Test
	public void testAnnotationProcessing(){
		I18nTranslationHandler i18nTranslation = new I18nTranslationHandler("net.canadensys.web.i18n");
		
		String url = I18nUrlBuilder.generateI18nResourcePath("en", i18nTranslation.getTranslationFormat(MockControllerMapping.RESOURCES_RESOURCE_KEY), "2");
		assertEquals("/en/resources_en/2",url);
		
		url = I18nUrlBuilder.generateI18nResourcePath("fr", i18nTranslation.getTranslationFormat(MockControllerMapping.RESOURCES_RESOURCE_KEY), "2");
		assertEquals("/fr/ressources_fr/2",url);
		
		url = I18nUrlBuilder.generateI18nResourcePath("fr", i18nTranslation.getTranslationFormat(MockControllerMapping.RESOURCES_RESOURCE_KEY), "2 3");
		assertEquals("/fr/ressources_fr/2%203",url);
		
		//test with trailing word
		url = I18nUrlBuilder.generateI18nResourcePath("fr", i18nTranslation.getTranslationFormat(MockControllerMapping.CONTACT_RESOURCE_KEY), "2");
		assertEquals("/fr/ressources_fr/2/contact_fr",url);
	}
	
	/**
	 * This is more to show the current incorrect behavior.
	 */
	@Test
	public void testGenerateI18nResourcePathNonExistingLanguage(){
		I18nTranslationHandler i18nTranslation = new I18nTranslationHandler("net.canadensys.web.i18n");
		String url = I18nUrlBuilder.generateI18nResourcePath("it", i18nTranslation.getTranslationFormat(MockControllerMapping.RESOURCES_RESOURCE_KEY), "2");
		
		//assertEquals("/it/ressources_"+Locale.getDefault().getLanguage()+"/2",url);
	}

}
