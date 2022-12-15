package workflow;

import Pages.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.tools.javac.Main;
import input.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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
                    boolean verif = Errors.checkFeatureOnPage(crtPage.getPageType(), action);
                    if (!verif) {
                        OutPrint.printError(output);
                        break;
                    }
                    switch (action.getFeature()) {
                        case "register"-> {
                            crtUser = Pages.Register.registerOnPage(inputData, action, output,
                                    objectMapper, crtPage, crtUser, crtMovies);
//                            boolean ok = true;
//                            for (UserInput user : inputData.getUsers()) {
//                                if (Objects.equals(user.getCredentials().getName(),
//                                        action.getCredentials().getName())) {
//                                    ok = false;
//                                    break;
//                                }
//                            }
//                            if (ok) {
//                                crtPage.setPageType("homepage autentificat");
//                                UserInput newUser = new UserInput(action.getCredentials());
//                                inputData.getUsers().add(newUser);
//                                crtUser = newUser;
//                                ObjectNode outputNode = output.addObject();
//                                outputNode.putPOJO("error", null);
//                                OutPrint.printCurrentUser(objectMapper, crtUser, outputNode);
//                                OutPrint.printCurrentMoviesList(crtMovies, outputNode);
//
//                            } else {
//                                OutPrint.printError(output);
//                                crtPage.setPageType("homepage neautentificat");
//                            }
                        }
                        case "login"-> {
                            crtUser = Pages.Login.loginOnPage(inputData, action, output,
                                    objectMapper, crtPage, crtUser, crtMovies);
//                            boolean ok = false;
//                            for (UserInput user: inputData.getUsers()) {
//                                if (Objects.equals(user.getCredentials().getName(),
//                                    actions.getCredentials().getName())
//                                    && Objects.equals(user.getCredentials().getPassword(),
//                                        actions.getCredentials().getPassword())) {
//                                    ok = true;
//                                    crtPage.setPageType("homepage autentificat");
//                                    crtUser = user;
//                                    ObjectNode outputNode = output.addObject();
//                                    outputNode.putPOJO("error", null);
//                                    OutPrint.printCurrentUser(objectMapper, user, outputNode);
////
////                                    ArrayList<MovieInput> moviesNotBanned =
////                                            new ArrayList<MovieInput>();
////                                    for (MovieInput movie : inputData.getMovies()) {
////                                        if (!movie.getCountriesBanned().
////                                                contains(crtUser.getCredentials().getCountry())) {
////                                            moviesNotBanned.add(movie);
////                                        }
////                                    }
//                                    OutPrint.printCurrentMoviesList(crtMovies, outputNode);
//
//                                    break;
//                                }
//                            }
//                            if (!ok) {
//                                OutPrint.printError(output);
//                                crtPage.setPageType("homepage neautentificat");
//                            }
                        }
                        default -> {
                        }
                    }
                }
                case "change page"-> {
                    switch (action.getPage()) {
                        case "register"-> {
                            Pages.Register.registerChangePage(output, action, crtPage);
//                            boolean ok = Errors.checkErrorChangePage(crtPage.getPageType(), action);
//                            if (ok) {
//                                crtPage.setPageType("register");
//                            } else {
//                                OutPrint.printError(output);
//                            }
                        }
                        case "login"-> {
                            Pages.Login.loginChangePage(output, action, crtPage);
//                            boolean ok = Errors.checkErrorChangePage(crtPage.getPageType(), action);
//                            if (ok) {
//                                crtPage.setPageType("login");
//                            } else {
//                                OutPrint.printError(output);
//                            }
                        }
                        case "logout"-> {
                            boolean ok = Errors.checkErrorChangePage(crtPage.getPageType(), action);
                            if (ok) {
                                crtPage.setPageType("homepage neautentificat");
                            } else {
                                OutPrint.printError(output);
                            }
                        }
                        default -> {}
                    }
                }
            }
        }
    }
}
