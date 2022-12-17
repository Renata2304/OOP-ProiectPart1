package pages;

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

    private static Register instance = null;

    private Register() {}

    public static Register getRegister() {
        if (instance == null) {
            instance = new Register();
        }
        return instance;
    }

    @Override
    public UserInput onPage(Input inputData, ActionInput action, ArrayNode output,
                            ObjectMapper objectMapper, Page crtPage, UserInput crtUser,
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
            OutPrint.printCurrentMoviesList(crtMovies, outputNode);
            OutPrint.printCurrentUser(objectMapper, crtUser, outputNode);

        } else {
            OutPrint.printError(output);
            crtPage.setPageType("homepage neautentificat");
        }

        return crtUser;
    }

    @Override
    public void changePage(ArrayNode output, ActionInput action, Page crtPage) {
        boolean ok = Errors.checkErrorChangePage(crtPage.getPageType(), action);
        if (ok) {
            crtPage.setPageType("register");
        } else {
            OutPrint.printError(output);
        }
    }

}
