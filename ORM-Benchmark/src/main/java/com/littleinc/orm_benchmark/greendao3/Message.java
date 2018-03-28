/*
 * Message.java
 *
 * Description:
 *
 * Author Deng Xinliang
 *
 * Ver 1.0, Mar 14, 2018, Deng Xinliang, Create file
 */
package com.littleinc.orm_benchmark.greendao3;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Message {
    @Id(autoincrement = true)
    @Property(nameInDb = "_id")
    public Long id;

    @Property(nameInDb = "client_id")
    public Long clientId;

    @Index
    @Property(nameInDb = "command_id")
    public Long commandId;

    @Property(nameInDb = "sorted_by")
    public Double sortedBy;

    @Property(nameInDb = "created_at")
    public Integer createdAt;

    public String content;

    @Property(nameInDb = "sender_id")
    public long senderId;

    @Property(nameInDb = "channel_id")
    public long channelId;

    @Transient
    public List<User> readers;

    @Override
    public String toString() {
        return id + ", " + content;
    }

    @Generated(hash = 1988887747)
    public Message(Long id, Long clientId, Long commandId, Double sortedBy,
            Integer createdAt, String content, long senderId, long channelId) {
        this.id = id;
        this.clientId = clientId;
        this.commandId = commandId;
        this.sortedBy = sortedBy;
        this.createdAt = createdAt;
        this.content = content;
        this.senderId = senderId;
        this.channelId = channelId;
    }

    @Generated(hash = 637306882)
    public Message() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return this.clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getCommandId() {
        return this.commandId;
    }

    public void setCommandId(long commandId) {
        this.commandId = commandId;
    }

    public double getSortedBy() {
        return this.sortedBy;
    }

    public void setSortedBy(double sortedBy) {
        this.sortedBy = sortedBy;
    }

    public int getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSenderId() {
        return this.senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getChannelId() {
        return this.channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    public void setSortedBy(Double sortedBy) {
        this.sortedBy = sortedBy;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

}
