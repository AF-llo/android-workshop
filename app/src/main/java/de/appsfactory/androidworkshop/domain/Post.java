package de.appsfactory.androidworkshop.domain;

public class Post {

    private String id;

    private String userId;

    private String title;

    private String body;

    public Post(String id, String userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
