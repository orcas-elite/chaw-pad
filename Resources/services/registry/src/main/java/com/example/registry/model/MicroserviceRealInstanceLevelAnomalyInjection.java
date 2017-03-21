package com.example.registry.model;

public class MicroserviceRealInstanceLevelAnomalyInjection extends MicroserviceRealAnomalyInjection {
    private String uuid;
    private String method;

    public MicroserviceRealInstanceLevelAnomalyInjection(String uuid, String method, long duration, int offset) {
        super(duration, offset);
        this.uuid = uuid;
        this.method = method;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getKey() {
        return this.uuid + ":" + this.method;
    }
}
