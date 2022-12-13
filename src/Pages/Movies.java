package Pages;

public class Movies {

    private static Movies instance = null;

    private Movies() {}

    public static Movies getMovies() {
        if (instance == null) {
            instance = new Movies();
        }
        return instance;
    }
}
