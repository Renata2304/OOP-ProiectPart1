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

public class Login extends Page{

    private static Login instance = null;

    private Login() {}

    public static Login getLogin() {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }

    @Override
    public UserInput onPage(Input inputData, ActionInput action,
                            ArrayNode output, ObjectMapper objectMapper,
                            Page crtPage, UserInput crtUser, ArrayList<MovieInput> crtMovies) {
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
                OutPrint.printCurrentMoviesList(crtMovies, outputNode);
                OutPrint.printCurrentUser(objectMapper, user, outputNode);

                break;
            }
        }
        if (!ok) {
            OutPrint.printError(output);
            crtPage.setPageType("homepage neautentificat");
        }

        return crtUser;
    }

    @Override
    public void changePage(ArrayNode output, ActionInput action, Page crtPage) {
        boolean ok = Errors.checkErrorChangePage(crtPage.getPageType(), action);
        if (ok) {
            crtPage.setPageType("login");
        } else {
            OutPrint.printError(output);
        }
    }
}
