package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class CacheTest {

	@Test
	public void test() throws InterruptedException {
		String[] cacheNames = new String[18];
		for (int i = 0; i < cacheNames.length; i++) {
			cacheNames[i] = "sceneV" + i;
		}
		CacheManager cacheManager = new CaffeineCacheManager();
		int threads = 200;
		ExecutorService threadPool = Executors.newFixedThreadPool(threads);
		for (int i = 0; i < threads; i++) {
			threadPool.submit(() -> {
				for(;;) {
					int index = ThreadLocalRandom.current().nextInt(cacheNames.length);
					String cacheName = cacheNames[index];
					cacheManager.getCache(cacheName).get("test");
				}
			});
		}
		TimeUnit.MINUTES.sleep(5);
	}

}
