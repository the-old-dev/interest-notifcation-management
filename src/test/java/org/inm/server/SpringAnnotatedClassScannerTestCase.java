package org.inm.server;

import java.util.List;

import org.inm.AfterApplicationStartup;
import org.inm.server.dummy.AnnotatedDummy1;
import org.inm.server.dummy.subfolder.AnnotatedDummy2;
import org.junit.Assert;
import org.junit.Test;

public class SpringAnnotatedClassScannerTestCase {

	@Test
	public void testAfterApplicationStartupAnnotation() throws Exception {
		
		// Scan for annotations
		SpringAnnotatedClassScanner<AfterApplicationStartup> scanner = new SpringAnnotatedClassScanner<>();
		List<Class<?>> found = scanner.findAnnotatedClasses("org.inm.server.dummy", AfterApplicationStartup.class);
		
		// test found
		Assert.assertEquals(2, found.size());
		Assert.assertTrue(found.contains(AnnotatedDummy1.class));
		Assert.assertTrue(found.contains(AnnotatedDummy2.class));
		
	}
	
}
