package Pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.ActionInput;
import input.Input;
import input.MovieInput;
import input.UserInput;
import workflow.Errors;
import workflow.OutPrint;

import java.util.ArrayList;
import java.util.Objects;

public class Register extends Page{

    public static UserInput registerOnPage(final Input inputData, final ActionInput action,
                                      final ArrayNode output, final ObjectMapper objectMapper,
                                      final Page crtPage, UserInput crtUser,
                                      ArrayList<MovieInput> crtMovies) {
        boolean ok = true;
        for (UserInput user : inputData.getUsers()) {
            if (Objects.equals(user.getCredentials().getName(),
                    action.getCredentials().getName())) {
                ok = false;
                break;
            }
        }
        if (ok) {
            crtPage.setPageType("homepage autentificat");
            UserInput newUser = new UserInput(action.getCredentials());
            inputData.getUsers().add(newUser);
            crtUser = newUser;
            ObjectNode outputNode = output.addObject();
            outputNode.putPOJO("error", null);
            OutPrint.printCurrentUser(objectMapper, crtUser, outputNode);
            OutPrint.printCurrentMoviesList(crtMovies, outputNode);

        } else {
            OutPrint.printError(output);
            crtPage.setPageType("homepage neautentificat");
        }

        return crtUser;
    }

    public static void registerChangePage(final ArrayNode output, final ActionInput action,
                                          final Page crtPage) {
        boolean ok = Errors.checkErrorChangePage(crtPage.getPageType(), action);
        if (ok) {
            crtPage.setPageType("register");
        } else {
            OutPrint.printError(output);
        }
    }
}
