package com.semicolok.config.spring.redis;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.redis.RedisConnectionFailureException;

public class CacheInterceptor extends org.springframework.cache.interceptor.CacheInterceptor {
	private static final long serialVersionUID = -5707358265656716546L;

	public Object invoke(final MethodInvocation invocation) throws Throwable {
		try {
			return super.invoke(invocation);
		} catch (RedisConnectionFailureException th1) {
			return invocation.proceed();
		}
	}
}