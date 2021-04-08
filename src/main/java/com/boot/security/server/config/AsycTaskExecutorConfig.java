package com.boot.security.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置、启用异步
 */
@EnableAsync(proxyTargetClass = true)
@Configuration
public class AsycTaskExecutorConfig {
	/**
	 * 当在一个方法上标注了@Async注解之后，在被调用的时候主线程会主动使用多线程来调用此方法，
	 * 但是当我们需要线程池来对多线程进行管理的时候就需要使用到配置类线程池的Bean
	 * ThreadPoolTaskExecutor这个类则是spring包下的，是sring为我们提供的线程池类
	 * @return
	 */
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(50);
		taskExecutor.setMaxPoolSize(100);
		return taskExecutor;
	}
}
