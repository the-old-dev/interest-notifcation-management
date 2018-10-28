package org.inm.server;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class SpringAnnotatedClassScanner<T extends Annotation> {

	public List<Class<?>> findAnnotatedClasses(String scanStartPackage, Class<T> annotationClass)
			throws ClassNotFoundException {

		List<Class<?>> found = new ArrayList<Class<?>>();
		ClassPathScanningCandidateComponentProvider provider = createComponentScanner(annotationClass);

		for (BeanDefinition beanDef : provider.findCandidateComponents(scanStartPackage)) {
			found.add(Class.forName(beanDef.getBeanClassName()));
		}

		return found;
	}

	private ClassPathScanningCandidateComponentProvider createComponentScanner(Class<T> annotationClass) {

		// Don't pull default filters (@Component, etc.):
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);

		provider.addIncludeFilter(new AnnotationTypeFilter(annotationClass));

		return provider;
	}

}
