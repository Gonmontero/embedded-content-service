package com.embed.command;

import com.embed.common.endpoint.EndpointGenerator;

public class CommandFactoryBase<T> {

    private final Class<T> type;
    private final T service;

    public CommandFactoryBase(Class<T> type, String endpoint) {
        this.type = type;
        service = EndpointGenerator.buildConnector(this.type, endpoint);
    }

    protected T getService() {
        return service;
    }
}
