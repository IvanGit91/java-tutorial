package tutorial.lambda.s2;

import java.util.ArrayList;
import java.util.List;

public class List2 {

    private int id;

    private List<List3> elementi = new ArrayList<>();

    public List2() {
    }

    public List2(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<List3> getElementi() {
        return elementi;
    }

    public void setElementi(List<List3> elementi) {
        this.elementi = elementi;
    }


}
