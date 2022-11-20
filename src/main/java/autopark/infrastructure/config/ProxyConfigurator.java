package autopark.infrastructure.config;

import autopark.infrastructure.core.Context;

public interface ProxyConfigurator {

    <T> T makeProxy(T object, Class<T> implementation, Context context);
}
