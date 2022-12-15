package Pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.ActionInput;
import input.Input;
import input.MovieInput;
import input.UserInput;
import workflow.Actions;
import workflow.Errors;
import workflow.OutPrint;

import java.util.ArrayList;
import java.util.Objects;

public class Login extends Page{

    public static UserInput loginOnPage(final Input inputData, final ActionInput action,
                                   final ArrayNode output, final ObjectMapper objectMapper,
                                   final Page crtPage, UserInput crtUser,
                                   ArrayList<MovieInput> crtMovies) {
        boolean ok = false;
        for (UserInput user: inputData.getUsers()) {
            if (Objects.equals(user.getCredentials().getName(),
                    action.getCredentials().getName())
                    && Objects.equals(user.getCredentials().getPassword(),
                    action.getCredentials().getPassword())) {
                ok = true;
                crtPage.setPageType("homepage autentificat");
                crtUser = user;
                ObjectNode outputNode = output.addObject();
                outputNode.putPOJO("error", null);
                OutPrint.printCurrentUser(objectMapper, user, outputNode);
//
//                                    ArrayList<MovieInput> moviesNotBanned =
//                                            new ArrayList<MovieInput>();
//                                    for (MovieInput movie : inputData.getMovies()) {
//                                        if (!movie.getCountriesBanned().
//                                                contains(crtUser.getCredentials().getCountry())) {
//                                            moviesNotBanned.add(movie);
//                                        }
//                                    }
                OutPrint.printCurrentMoviesList(crtMovies, outputNode);

                break;
            }
        }
        if (!ok) {
            OutPrint.printError(output);
            crtPage.setPageType("homepage neautentificat");
        }

        return crtUser;
    }

    public static void loginChangePage(final ArrayNode output, final ActionInput action,
                                       final Page crtPage) {
        boolean ok = Errors.checkErrorChangePage(crtPage.getPageType(), action);
        if (ok) {
            crtPage.setPageType("login");
        } else {
            OutPrint.printError(output);
        }
    }
}
