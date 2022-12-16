package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionInput;
import input.Input;
import input.MovieInput;
import input.UserInput;

import java.util.ArrayList;

public class Page {

    private String pageType;

    public UserInput onPage(final Input inputData, final ActionInput action,
                            final ArrayNode output, final ObjectMapper objectMapper,
                            final Page crtPage, UserInput crtUser,
                            ArrayList<MovieInput> crtMovies){
        return new UserInput();
    }

    public void changePage(final ArrayNode output, final ActionInput action,
                           final Page crtPage) {

    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(final String pageType) {
        this.pageType = pageType;
    }


}
