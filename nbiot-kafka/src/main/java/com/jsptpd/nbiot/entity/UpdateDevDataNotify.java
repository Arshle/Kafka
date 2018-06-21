package com.jsptpd.nbiot.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateDevDataNotify extends BaseNotify
{   
    @Size(max=256)
    @NotNull
    private String deviceId;
    
    @Size(max=256)
    @NotNull
    private String gatewayId;
    
    @Size(max=256)
    private String requestId;
    
    @Valid
    @NotNull
    private DeviceServiceData service;

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }
    
    public String getGatewayId()
    {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId)
    {
        this.gatewayId = gatewayId;
    }

    public String getRequestId()
    {
        return requestId;
    }

    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
    }

    public DeviceServiceData getService()
    {
        return service;
    }

    public void setService(DeviceServiceData service)
    {
        this.service = service;
    }
   
}
