package input;

import java.util.ArrayList;

public final class UserInput {
    private Credentials credentials;
    private int tokenCount;
    private int numFreePremiumMovies = 15;
    private ArrayList<MovieInput> purchasedMovies;
    private ArrayList<MovieInput> watchedMovies;
    private ArrayList<MovieInput> likedMovies;
    private ArrayList<MovieInput> ratedMovies;

    public UserInput() {

    }

    public UserInput(Credentials credentials) {
        this.credentials = credentials;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(int tokenCount) {
        this.tokenCount = tokenCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<MovieInput> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(ArrayList<MovieInput> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<MovieInput> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(ArrayList<MovieInput> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<MovieInput> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(ArrayList<MovieInput> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<MovieInput> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(ArrayList<MovieInput> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }
}