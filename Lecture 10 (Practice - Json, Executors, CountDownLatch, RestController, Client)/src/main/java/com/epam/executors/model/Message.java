package com.epam.executors.model;

/**
 * Message should contain encoded BASE64 format to transmit it
 */
public class Message {
    private String content;
    private String matchTemplate;

    public Message(String content, String matchTemplate) {
        this.content = content;
        this.matchTemplate = matchTemplate;
    }

    public String getMatchTemplate() {
        return matchTemplate;
    }

    public void setMatchTemplate(String matchTemplate) {
        this.matchTemplate = matchTemplate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", matchTemplate='" + matchTemplate + '\'' +
                '}';
    }
}
