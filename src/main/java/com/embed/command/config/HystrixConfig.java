package com.embed.command.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class HystrixConfig {

    public enum GroupKey {
        PROVIDER_SERVICE ("PROVIDER_SERVICE", "provider_key");

        private final String name;

        private final String key;

        GroupKey (String name, String key) {
            this.name = name;
            this.key = key;
        }

        public String getName() { return name; }

        public String getKey() { return key; }

    }

    private static final int HTTP_EXECUTION_TIMEOUT_IN_MILLIS = 10000;

    private static final int THREAD_CORE_POOL_SIZE = 450;

    private HystrixConfig() {
    }

    /**
     * Initializes a Setter for Http Commands.
     *
     *
     * @param groupKey the group key
     * @return Hystrix initialized setter
     */
    public static final HystrixCommand.Setter initHttpCommandSetter(GroupKey groupKey) {
        final com.netflix.hystrix.HystrixCommandProperties.Setter defaultCommandPropertySettings = initSetterWithTimeout(HTTP_EXECUTION_TIMEOUT_IN_MILLIS);
        return initHttpCommandSetter(groupKey, defaultCommandPropertySettings);
    }

    private static HystrixCommandProperties.Setter initSetterWithTimeout(final int timeoutInMillis) {

        return HystrixCommandProperties
                .Setter()
                .withExecutionTimeoutInMilliseconds(timeoutInMillis)
                .withFallbackEnabled(true)
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD);
    }


    private static final HystrixCommand.Setter initHttpCommandSetter(GroupKey groupKey,
                                                                     HystrixCommandProperties.Setter commandPropertySettings) {
        HystrixThreadPoolProperties.Setter threadPoolPropertiesDefaults = HystrixThreadPoolProperties.Setter().withCoreSize(THREAD_CORE_POOL_SIZE);

        return HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey.name()))
                .andCommandKey(HystrixCommandKey.Factory.asKey(groupKey.name() + "_" + groupKey.getKey()))
                .andCommandPropertiesDefaults(commandPropertySettings).andThreadPoolPropertiesDefaults(threadPoolPropertiesDefaults);
    }
}
