package by.incubator.infrastructure.core;

import by.incubator.infrastructure.config.Config;

public interface Context {

    <T> T getObject(Class<T> type);

    Config getConfig();
}
