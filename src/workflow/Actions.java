package workflow;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pages.Movies;
import pages.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public final class Actions{

    /**
     *
     * @param objectMapper
     * @param inputData
     * @param output
     */
    public static void action(final ObjectMapper objectMapper, final Input inputData,
                              final ArrayNode output) {

        Page crtPage = new Page();
        crtPage.setPageType("homepage neautentificat");
        ArrayList<MovieInput> crtMovies = new ArrayList<MovieInput>();
        UserInput crtUser = new UserInput();

        for (ActionInput action: inputData.getActions()) {
            switch (action.getType()) {
                case "on page"-> {
                    boolean verif = Errors.checkErrorFeatureOnPage(crtPage.getPageType(), action);
                    if (!verif) {
                        OutPrint.printError(output);
                        break;
                    }
                    switch (action.getFeature()) {
                        case "register" -> {
                            crtUser = pages.Register.getRegister().onPage(inputData, action,
                                    output, objectMapper, crtPage, crtUser, crtMovies);
                        }
                        case "login" -> {
                            crtUser = pages.Login.getLogin().onPage(inputData, action, output,
                                    objectMapper, crtPage, crtUser, crtMovies);
                        }
                        case "search" -> {
                            ArrayList<MovieInput> search = new ArrayList<>();
                            for (MovieInput movie : crtMovies) {
                                if (movie.getName().startsWith(action.getStartsWith())) {
                                    search.add(movie);
                                }
                            }
                            ObjectNode outputNode = output.addObject();
                            outputNode.putPOJO("error", null);
                            OutPrint.printCurrentMoviesList(search, outputNode);
                            OutPrint.printCurrentUser(objectMapper, crtUser, outputNode);
                        }
                        case "filter" -> {
                            crtMovies = new ArrayList<>();
                            for (MovieInput movie : inputData.getMovies()) {
                                if (!movie.getCountriesBanned().
                                        contains(crtUser.getCredentials().getCountry())) {
                                    crtMovies.add(movie);
                                }
                            }
                            if (action.getFilters() != null) {
                                if (action.getFilters().getSort() != null) {
                                    crtMovies.sort(Movies.getComparator(action.getFilters().getSort()));
                                }
                                if (action.getFilters().getContains() != null) {
                                    ArrayList<String> genreFilter = action.getFilters().
                                            getContains().getGenre();
                                    ArrayList<String> actorsFilter = action.getFilters().
                                            getContains().getActors();

                                    if (genreFilter != null) {
                                        crtMovies.removeIf(movie -> !movie.getGenres().
                                                containsAll(genreFilter));
                                    }
                                    if (actorsFilter != null) {
                                        crtMovies.removeIf(movie -> !movie.getActors().
                                                containsAll(actorsFilter));
                                    }
                                }
                                ObjectNode outputNode = output.addObject();
                                outputNode.putPOJO("error", null);
                                OutPrint.printCurrentMoviesList(crtMovies, outputNode);
                                OutPrint.printCurrentUser(objectMapper, crtUser, outputNode);
                            }
                        }
                        case "buy tokens"-> {
                            crtUser.getCredentials().
                                    setBalance(Integer.
                                            toString(Integer.parseInt(crtUser.getCredentials().
                                                    getBalance()) - Integer.parseInt(action.
                                                    getCount())));
                            crtUser.setTokenCount(crtUser.getTokenCount()
                                    + Integer.parseInt(action.getCount()));
                        }
                        case "buy premium account" -> {
                            if (Objects.equals(crtUser.getCredentials().getAccountType(),
                                    "premium")) {
                                OutPrint.printError(output);
                            } else {
                                crtUser.setTokenCount(crtUser.getTokenCount() - 10);
                                crtUser.getCredentials().setAccountType("premium");
                                crtUser.setNumFreePremiumMovies(15);
                            }
                        }
                        case "purchase" -> {
                            if (Objects.equals(crtUser.getCredentials().getAccountType(),
                                                "premium")) {
                                if (crtUser.getNumFreePremiumMovies() > 0) {
                                    crtUser.setNumFreePremiumMovies(crtUser.
                                            getNumFreePremiumMovies() - 1);
                                } else {
                                    crtUser.setTokenCount(crtUser.getTokenCount() - 2);
                                }
                            } else {
                                crtUser.setTokenCount(crtUser.getTokenCount() - 2);
                            }
                            crtUser.getCurrentMovie().setPurchased(true);
                            crtUser.getPurchasedMovies().add(crtUser.getCurrentMovie());

                            ObjectNode outputNode = output.addObject();
                            outputNode.putPOJO("error", null);
                            ArrayList<MovieInput> currentMovie = new ArrayList<>();
                            currentMovie.add(crtUser.getCurrentMovie());
                            OutPrint.printCurrentMoviesList(currentMovie, outputNode);
                            OutPrint.printCurrentUser(objectMapper, crtUser, outputNode);
                        }
                        case "watch" -> {
                            if (!crtUser.getPurchasedMovies().contains(crtUser.getCurrentMovie())) {
                                OutPrint.printError(output);
                            } else {
                                crtUser.getWatchedMovies().add(crtUser.getCurrentMovie());
                                crtUser.getCurrentMovie().setWatched(true);

                                ObjectNode outputNode = output.addObject();
                                outputNode.putPOJO("error", null);
                                ArrayList<MovieInput> currentMovie = new ArrayList<>();
                                currentMovie.add(crtUser.getCurrentMovie());
                                OutPrint.printCurrentMoviesList(currentMovie, outputNode);
                                OutPrint.printCurrentUser(objectMapper, crtUser, outputNode);
                            }
                        }
                        case "like" -> {
                            if (!crtUser.getPurchasedMovies().contains(crtUser.getCurrentMovie())) {
                                OutPrint.printError(output);
                            } else if (!crtUser.getCurrentMovie().isWatched()) {
                                OutPrint.printError(output);
                            } else {
                                crtUser.getLikedMovies().add(crtUser.getCurrentMovie());
                                crtUser.getCurrentMovie().
                                        setNumLikes(crtUser.getCurrentMovie().getNumLikes() + 1);

                                ObjectNode outputNode = output.addObject();
                                outputNode.putPOJO("error", null);
                                ArrayList<MovieInput> currentMovie = new ArrayList<>();
                                currentMovie.add(crtUser.getCurrentMovie());
                                OutPrint.printCurrentMoviesList(currentMovie, outputNode);
                                OutPrint.printCurrentUser(objectMapper, crtUser, outputNode);
                            }
                        }
                        case "rate" -> {
                            if (!crtUser.getPurchasedMovies().contains(crtUser.getCurrentMovie())) {
                                OutPrint.printError(output);
                            } else if (!crtUser.getWatchedMovies().contains(crtUser.getCurrentMovie())) {
                                OutPrint.printError(output);
                            } else if (action.getRate() > 5) {
                                OutPrint.printError(output);
                            } else {
                                crtUser.getRatedMovies().add(crtUser.getCurrentMovie());
                                crtUser.getCurrentMovie().setNumRatings(crtUser.
                                        getCurrentMovie().getNumRatings() + 1);
                                crtUser.getCurrentMovie().setRating(crtUser.
                                        getCurrentMovie().getRating() + action.getRate());

                                ObjectNode outputNode = output.addObject();
                                outputNode.putPOJO("error", null);
                                ArrayList<MovieInput> currentMovie = new ArrayList<>();
                                currentMovie.add(crtUser.getCurrentMovie());
                                OutPrint.printCurrentMoviesList(currentMovie, outputNode);
                                OutPrint.printCurrentUser(objectMapper, crtUser, outputNode);
                            }
                        }
                        default -> {
                        }
                    }
                }
                case "change page" -> {
                    boolean ok = Errors.checkErrorChangePage(crtPage.getPageType(),
                            action);
                    if (!ok) {
                        OutPrint.printError(output);
                    } else {
                        switch (action.getPage()) {
                            case "register" -> {
                                pages.Register.getRegister().changePage(output, action, crtPage);
                            }
                            case "login" -> {
                                pages.Login.getLogin().changePage(output, action, crtPage);
                            }
                            case "logout" -> {
                                crtMovies.removeAll(crtMovies);
                                crtUser = new UserInput();
                                crtPage.setPageType("homepage neautentificat");
                            }
                            case "movies" -> {
                                crtPage.setPageType("movies");
                                crtMovies = new ArrayList<>();
                                for (MovieInput movie : inputData.getMovies()) {
                                    if (!movie.getCountriesBanned().
                                            contains(crtUser.getCredentials().getCountry())) {
                                        crtMovies.add(movie);
                                    }
                                }
                                ObjectNode outputNode = output.addObject();
                                outputNode.putPOJO("error", null);
                                OutPrint.printCurrentMoviesList(crtMovies, outputNode);
                                OutPrint.printCurrentUser(objectMapper, crtUser, outputNode);
                            }
                            case "see details" -> {
                                ok = false;
                                for (MovieInput movie : crtMovies) {
                                    if (Objects.equals(movie.getName(), action.getMovie())) {
                                        ok = true;
                                        crtUser.setCurrentMovie(movie);
                                        break;
                                    }
                                }
                                if (!ok) {
                                    OutPrint.printError(output);
                                } else {
                                    crtPage.setPageType("see details");
                                    ObjectNode outputNode = output.addObject();
                                    outputNode.putPOJO("error", null);
                                    ArrayNode arrayNode = output.arrayNode();
                                    ArrayList<MovieInput> currentMovie = new ArrayList<>();
                                    currentMovie.add(crtUser.getCurrentMovie());
                                    OutPrint.printCurrentMoviesList(currentMovie, outputNode);
                                    OutPrint.printCurrentUser(objectMapper, crtUser, outputNode);
                                }
                            }
                            case "upgrades" -> {
                                crtPage.setPageType("upgrades");
                            }
                            default -> {
                            }
                        }
                    }
                }
            }
        }
    }
}
