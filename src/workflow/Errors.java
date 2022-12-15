package workflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionInput;

public class Errors extends Actions {

    public Errors() {
    }

    public static void printError(ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();

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
//                if (!nextPage.equals("login")) {
//                    return !nextPage.equals("register");
//                }
//                return false;
                if (nextPage.equals("login") || nextPage.equals(("register"))) {
                    return false;
                }
                return true;
            }
            default -> {
                return true;
            }
        }
    }

    public static boolean checkFeatureOnPage(final String currPage, final ActionInput action) {
        if (currPage.equals("login") && action.getFeature().equals("login")) {
            return true;
        }
        if (currPage.equals("register") && action.getFeature().equals("register")) {
            return true;
        }
        if (currPage.equals("movies") && (action.getFeature().equals("search")
            || action.getFeature().equals("filter"))) {
            return true;
        }
        if (currPage.equals("upgrades") && (action.getFeature().equals("buy tokens")
            || action.getFeature().equals("buy premium account"))) {
            return true;
        }
        if (currPage.equals("see details") && (action.getFeature().equals("purchase")
            || action.getFeature().equals("watch") || action.getFeature().equals("like")
                || action.getFeature().equals("rate"))) {
            return true;
        }
        return false;
    }

}
