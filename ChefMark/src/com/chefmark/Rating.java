package chefmark;

public class Rating {
    private int rating = -1;

    public Rating() {
    }

    public int getRating() {
        return this.rating;
    }

    public void changeRating(int rating) {
        this.rating = rating;
    }
}
