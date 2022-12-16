package input;

import java.util.ArrayList;

class Filters {

    Sort sort = new Sort();
    Contains contains = new Contains();

    public Filters(final Sort sort, final Contains contains) {
        this.sort = sort;
        this.contains = contains;
    }

    public class Sort {
        private String rating;
        private String duration;

        public String getRating() {
            return rating;
        }

        public void setRating(final String rating) {
            this.rating = rating;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(final String duration) {
            this.duration = duration;
        }
    }

    public class Contains {
        private ArrayList<String> actors;
        private ArrayList<String> genre;

        public ArrayList<String> getActors() {
            return actors;
        }

        public void setActors(final ArrayList<String> actors) {
            this.actors = actors;
        }

        public ArrayList<String> getGenre() {
            return genre;
        }

        public void setGenre(final ArrayList<String> genre) {
            this.genre = genre;
        }
    }

}
