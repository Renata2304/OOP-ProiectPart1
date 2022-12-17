package pages;

import input.MovieInput;
import input.Sort;

import java.util.Comparator;

public class Movies extends Page{
    private static Movies instance = null;

    private Movies() {}

    public static Movies getMovies() {
        if (instance == null) {
            instance = new Movies();
        }
        return instance;
    }

    public static Comparator<MovieInput> getComparator(final Sort sort) {
        Comparator<MovieInput> comparator;
        if (sort.getDuration() == null) {
            comparator = Comparator.comparing(MovieInput :: getRating);
            if (sort.getRating().equals("decreasing")) {
                comparator = Comparator.comparing(MovieInput::getRating, Comparator.reverseOrder());
            }
            return comparator;
        }
        comparator = Comparator.comparing(MovieInput::getDuration);
        if (sort.getDuration().equals("decreasing")) {
            comparator = Comparator.comparing(MovieInput::getDuration, Comparator.reverseOrder());
        }
        if (sort.getRating() != null) {
            comparator = comparator.thenComparing(MovieInput::getRating);
            if (sort.getRating().equals("decreasing")) {
                comparator = comparator.reversed();
            }
        }
        return comparator;
    }
}
