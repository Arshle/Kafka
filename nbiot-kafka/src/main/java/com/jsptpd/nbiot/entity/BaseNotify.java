package com.jsptpd.nbiot.entity;

import com.jsptpd.kafka.common.entity.message.BaseSimpleMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public abstract class BaseNotify extends BaseSimpleMessage
{

    public static final String BIND_DEVICE = "bindDevice";
    public static final String DEVICE_INFO_CHANGED = "deviceInfoChanged";
    public static final String DEVICE_DATA_CHANGED = "deviceDataChanged";
    public static final String DEVICE_DELETED = "deviceDeleted";
    public static final String RULE_EVENT = "ruleEvent";
    public static final String DEVICE_DATAS_CHANGED = "deviceDatasChanged";
    //public static final String SERVICE_INFO_CHANGED = "serviceInfoChanged";
    //public static final String DEVICE_ADDED = "deviceAdded";
    //public static final String MESSAGE_CONFIRM = "messageConfirm";
    public static final String COMMAND_RSP = "commandRsp";
    //public static final String DEVICE_EVENT = "deviceEvent";
    //public static final String APP_DELETED = "appDeleted";
    
    @Pattern(regexp = "(bindDevice|deviceInfoChanged|deviceDataChanged|deviceDeleted|ruleEvent|deviceDatasChanged|commandRsp)")//"(appDeleted|serviceInfoChanged|deviceInfoChanged|deviceDataChanged|deviceAdded|deviceDeleted|bindDevice|messageConfirm|commandRsp|deviceEvent|ruleEvent)")
    @NotNull
    private String notifyType;
    
    public String getNotifyType()
    {
        return notifyType;
    }

    public void setNotifyType(String notifyType)
    {
        this.notifyType = notifyType;
    }

    /*@Override
    public String toString()
    {
        return "NotifyDTOCloud2NA [notifyType=" + notifyType + "]";
    }*/
}
