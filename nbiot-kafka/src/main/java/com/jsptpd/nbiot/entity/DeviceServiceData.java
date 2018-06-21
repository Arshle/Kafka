package com.jsptpd.nbiot.entity;

import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DeviceServiceData
{
    @Size(max=256)
    @NotNull
    private String serviceId;
    
    @Size(max=256)
    private String serviceType;

    private ObjectNode data;
    
    @Size(max=256)
    private String eventTime;
    
    public String getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }

    public String getServiceType()
    {
        return serviceType;
    }

    public void setServiceType(String serviceType)
    {
        this.serviceType = serviceType;
    }

    public ObjectNode getData() {
		return data;
	}

	public void setData(ObjectNode data) {
		this.data = data;
	}

    public String getEventTime()
    {
        return eventTime;
    }

    public void setEventTime(String eventTime)
    {
        this.eventTime = eventTime;
    }

/*    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DeviceService [serviceId=");
        builder.append(serviceId);
        builder.append(", serviceType=");
        builder.append(serviceType);
        builder.append(", data=");
        builder.append(data);
        builder.append(", eventTime=");
        builder.append(eventTime);
        builder.append("]");
        return builder.toString();
    }*/
}
