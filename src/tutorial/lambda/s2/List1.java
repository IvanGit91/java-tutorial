package tutorial.lambda.s2;

import java.util.ArrayList;
import java.util.List;

public class List1 {

    private int id;

    private List<List2> elementi = new ArrayList<>();

    public List1() {
    }

    public List1(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<List2> getElementi() {
        return elementi;
    }

    public void setElementi(List<List2> elementi) {
        this.elementi = elementi;
    }


}
