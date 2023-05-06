package chefmark;

public class Rating {
    private int rating = -1;

    /**
     * empty constructor for rating
     */
    public Rating() {
    }

    /**
     * 
     * @return
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * 
     * @param rating
     */
    public void changeRating(int rating) {
        this.rating = rating;
    }
}
