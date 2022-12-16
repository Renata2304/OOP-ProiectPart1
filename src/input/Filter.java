package input;

import java.util.ArrayList;

public class Filter {

    private Filter() {

    }

    private Sort sort;
    private Contains contains;

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Contains getContains() {
        return contains;
    }

    public void setContains(Contains contains) {
        this.contains = contains;
    }

}
