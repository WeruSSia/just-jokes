package com.example.just_jokes;

class JokeDto {
    private String id;
    private String content;
    private Integer upvotes;
    private Integer downvotes;

    String getId() {
        return id;
    }

    String getContent() {
        return content;
    }

    Integer getUpvotes() {
        return upvotes;
    }

    Integer getDownvotes() {
        return downvotes;
    }
}
