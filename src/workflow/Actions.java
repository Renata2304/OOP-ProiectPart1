package workflow;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pages.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.*;

import java.util.ArrayList;

public class Actions{

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
                        case "register"-> {
                            crtUser = pages.Register.getRegister().onPage(inputData, action,
                                    output, objectMapper, crtPage, crtUser, crtMovies);
                        }
                        case "login"-> {
                            crtUser = pages.Login.getLogin().onPage(inputData, action, output,
                                    objectMapper, crtPage, crtUser, crtMovies);
                        }
                        case "search"-> {
                            ArrayList<MovieInput> search = new ArrayList<>();
                            for (MovieInput movie : inputData.getMovies()) {
                                if (movie.getName().startsWith(action.getStartsWith())) {
                                    search.add(movie);
                                }
                            }
                            ObjectNode outputNode = output.addObject();
                            outputNode.putPOJO("error", null);
                            OutPrint.printCurrentMoviesList(search, outputNode);
                            OutPrint.printCurrentUser(objectMapper, crtUser, outputNode);
                        }
                        default -> {
                        }
                    }
                }
                case "change page"-> {
                    switch (action.getPage()) {
                        case "register"-> {
                            pages.Register.getRegister().changePage(output, action, crtPage);
                        }
                        case "login"-> {
                            pages.Login.getLogin().changePage(output, action, crtPage);
                        }
                        case "logout"-> {
                            boolean ok = Errors.checkErrorChangePage(crtPage.getPageType(),
                                                                     action);
                            if (ok) {
                                crtPage.setPageType("homepage neautentificat");
                            } else {
                                OutPrint.printError(output);
                            }
                        }
                        case "movies"-> {
                            boolean ok = Errors.checkErrorChangePage(crtPage.getPageType(),
                                    action);
                            if (ok) {
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
                                crtPage.setPageType("movies");
                            } else {
                                OutPrint.printError(output);
                            }
                        }
                        default -> {
                        }
                    }
                }
            }
        }
    }
}
