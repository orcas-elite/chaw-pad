package com.example.registry;

import com.example.registry.model.MicroserviceRealAnomalyInjection;
import com.example.registry.model.MicroserviceContinuousAnomalyInjection;
import com.example.registry.model.MicroserviceRampUpAnomalyInjection;

public interface ControlApi {
    public String listRegistrations();
    public String listInjections();
    public void addInitializationAnomaly(MicroserviceRampUpAnomalyInjection anomalyInjection);
    public void addContinuousAnomaly(MicroserviceContinuousAnomalyInjection anomalyInjection);
    public void addAnomaly(MicroserviceRealAnomalyInjection anomalyInjection);
    public void resetAll();
}
