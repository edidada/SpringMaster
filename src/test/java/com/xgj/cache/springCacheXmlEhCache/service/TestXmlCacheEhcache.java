package com.xgj.cache.springCacheXmlEhCache.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xgj.cache.springCacheXmlEhCache.domain.LittleArtisan;

public class TestXmlCacheEhcache {
	ClassPathXmlApplicationContext context = null;
	LittleArtisanSpringCacheService service = null;
	LittleArtisan littleArtisan;

	@Before
	public void initContext() {
		// ����Spring ����
		context = new ClassPathXmlApplicationContext(
				"classpath:conf_spring_ehcache.xml");
	}

	@Test
	public void testXmlCache() {

		service = context.getBean("littleArtisanSpringCacheService",
				LittleArtisanSpringCacheService.class);
		// ��һ�� �����ݿ����
		littleArtisan = service.getArtisan("littleArtisan");
		printArtisan();
		// �ڶ��� �ӻ������
		littleArtisan = service.getArtisan("littleArtisan");
		printArtisan();
		// ��ջ���
		service.reloadArtisan();
		// �ٴβ�ѯ�������ݿ����
		service.getArtisan("littleArtisan");
		printArtisan();
		// �ֲ�ѯ���ӻ������
		service.getArtisan("littleArtisan");
		printArtisan();
	}

	private void printArtisan() {
		System.out.println(littleArtisan.getArtisanName() + "||"
				+ littleArtisan.getArtisanDesc());
	}

	@After
	public void releaseContext() {
		if (context != null) {
			context.close();
		}
	}
}
