package workflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionInput;

import java.util.Objects;

public class Errors extends Actions {

    public Errors() {
    }

    public static boolean checkErrorChangePage(final String currPage, final ActionInput action) {
        String nextPage = action.getPage();
        switch (currPage) {
            case "homepage neautentificat" -> {
                if (nextPage.equals("login")) {
                    return true;
                }
                if (nextPage.equals("logout")) {
                    return false;
                }
                return nextPage.equals("register");
            }
            case "homepage autentificat", "movies", "see details", "upgrades" -> {
                if (Objects.equals(nextPage, "login") || nextPage.equals("register")) {
                    return false;
                }
                return true;
            }
            default -> {
                return true;
            }
        }
    }

    public static boolean checkErrorFeatureOnPage(final String currPage,
                                                  final ActionInput action) {
        if (currPage.equals("login") && Objects.equals(action.getFeature(), "login")) {
            return true;
        }
        if (currPage.equals("register") && Objects.equals(action.getFeature(), "register")) {
            return true;
        }
        if (currPage.equals("movies") && (Objects.equals(action.getFeature(), "search")
            || Objects.equals(action.getFeature(), "filter"))) {
            return true;
        }
        if (currPage.equals("upgrades") && (Objects.equals(action.getFeature(), "buy tokens")
            || Objects.equals(action.getFeature(), "buy premium account"))) {
            return true;
        }
        if (currPage.equals("see details") && (Objects.equals(action.getFeature(), "purchase")
                || Objects.equals(action.getFeature(), "watch")
                || Objects.equals(action.getFeature(), "like")
                || Objects.equals(action.getFeature(), "rate"))) {
            return true;
        }
        return false;
    }

}
