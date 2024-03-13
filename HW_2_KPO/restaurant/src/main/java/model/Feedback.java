package model;

public class Feedback {
    private int feedbackId;
    private int dishId;
    private String comment;
    private int rating;

    public Feedback() {
    }

    public Feedback(int dishId, String comment, int rating) {
        this.dishId = dishId;
        this.comment = comment;
        this.rating = rating;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("Рейтинг может приниматься значения только от 1 до 5!");
        }
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }
}
