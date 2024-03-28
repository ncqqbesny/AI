package com.hdpt.device.handler.netty;

import com.alibaba.fastjson.JSONObject;


public class Message {
	/**
	 * 
	 */
	private String Id;
    /**
     * 数据长度
     */
    private Integer len;

    /**
     * 接收的通讯数据body
     */
    private String content;

    /**
     * 消息类型
     */
    private Integer msgType;

    public Message(Object object) {
        String str = object.toString();
        JSONObject jsonObject = JSONObject.parseObject(str);
        msgType = Integer.valueOf(jsonObject.getString("msg_type"));
        content = jsonObject.getString("body");
        len = str.length();
    }

    public String toJsonString() {
        return "{" +
                "\"msg_type\": " + msgType + ",\n" +
                "\"body\": " + content +
                "}";
    }
	// setter、getter 。。。。

	public Integer getLen() {
		return len;
	}

	public void setLen(Integer len) {
		this.len = len;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
    
	
}

