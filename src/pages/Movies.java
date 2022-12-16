package pages;

public class Movies extends Page{
    private static Movies instance = null;

    private Movies() {}

    public static Movies getMovies() {
        if (instance == null) {
            instance = new Movies();
        }
        return instance;
    }
}
