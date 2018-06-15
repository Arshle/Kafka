/*
 * FileName: CloudMessage.java
 * Author:   Arshle
 * Date:     2018年06月12日
 * Description:
 */
package com.jsptpd.kafka.common.entity.message;

/**
 * 〈〉<br>
 * 〈〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class CloudMessage extends BaseSimpleMessage {

    private long id;
    private String branchName;
    private String domain;
    private String ingressIp;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIngressIp() {
        return ingressIp;
    }

    public void setIngressIp(String ingressIp) {
        this.ingressIp = ingressIp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
