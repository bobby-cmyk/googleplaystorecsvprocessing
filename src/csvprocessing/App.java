package csvprocessing;

public class App {

    // Name
    // Category
    // Rating
    private String name;
    private String category;
    private double rating;

    public App(String name, String category, double rating) {
        this.name = name;
        this.category = category;
        this.rating = rating;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public double getRating() {
        return this.rating;
    }

    @Override
    public String toString() {
        return ("%s (Rating: %.1f)").formatted(this.name, this.rating);
    }
}