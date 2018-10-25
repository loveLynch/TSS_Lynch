package com.silver.tss.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String topicId;

    private String topicName;

    private String topicType;

    private String topicDescription;

    private Integer topicMaxSelected;

    private Integer topicRealSelected;
    private Integer topicRealSelected1;
    private Integer topicRealSelected2;
    private Integer topicRealSelected3;


    private Boolean yn;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId == null ? null : topicId.trim();
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName == null ? null : topicName.trim();
    }

    public String getTopicType() {
        return topicType;
    }

    public void setTopicType(String topicType) {
        this.topicType = topicType == null ? null : topicType.trim();
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription == null ? null : topicDescription.trim();
    }

    public Integer getTopicMaxSelected() {
        return topicMaxSelected;
    }

    public void setTopicMaxSelected(Integer topicMaxSelected) {
        this.topicMaxSelected = topicMaxSelected;
    }

    public Integer getTopicRealSelected() {
        return topicRealSelected;
    }

    public Integer getTopicRealSelected1() {
        return topicRealSelected1;
    }

    public void setTopicRealSelected1(Integer topicRealSelected1) {
        this.topicRealSelected1 = topicRealSelected1;
    }

    public Integer getTopicRealSelected2() {
        return topicRealSelected2;
    }

    public void setTopicRealSelected2(Integer topicRealSelected2) {
        this.topicRealSelected2 = topicRealSelected2;
    }

    public Integer getTopicRealSelected3() {
        return topicRealSelected3;
    }

    public void setTopicRealSelected3(Integer topicRealSelected3) {
        this.topicRealSelected3 = topicRealSelected3;
    }

    public void setTopicRealSelected(Integer topicRealSelected) {
        this.topicRealSelected = topicRealSelected;
    }

    public Boolean getYn() {
        return yn;
    }

    public void setYn(Boolean yn) {
        this.yn = yn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", topicId='" + topicId + '\'' +
                ", topicName='" + topicName + '\'' +
                ", topicType='" + topicType + '\'' +
                ", topicDescription='" + topicDescription + '\'' +
                ", topicMaxSelected=" + topicMaxSelected +
                ", topicRealSelected=" + topicRealSelected +
                ", topicRealSelected1=" + topicRealSelected1 +
                ", topicRealSelected2=" + topicRealSelected2 +
                ", topicRealSelected3=" + topicRealSelected3 +
                ", yn=" + yn +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}