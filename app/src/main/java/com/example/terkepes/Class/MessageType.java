package com.example.terkepes.Class;

import com.google.gson.annotations.SerializedName;

public class MessageType {
    @SerializedName("messageTypeId")
    public String messageTypeId;
    @SerializedName("messageTypeName")
    public String messageTypeName;

    public MessageType(String messageTypeId, String messageTypeName) {
        this.messageTypeId = messageTypeId;
        this.messageTypeName = messageTypeName;
    }

    public String getMessageTypeId() {
        return messageTypeId;
    }

    public void setMessageTypeId(String messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

    public String getMessageTypeName() {
        return messageTypeName;
    }

    public void setMessageTypeName(String messageTypeName) {
        this.messageTypeName = messageTypeName;
    }
}
