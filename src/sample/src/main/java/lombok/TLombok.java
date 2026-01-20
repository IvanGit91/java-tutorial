package lombok;

import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Date;

@Log
public class TLombok {

    public static void main(String[] args) {
        builderAndWithier();
        with();
        val();
        value();
    }

    public static void builderAndWithier() {
        Test test = new Test(1L, "asd", new Date(), true);
        Test testClassBuilder = Test.builder().id(2L).nome("ciao").build();
        Test testObjectWith = test.withNome("ciaone").withActive(false);
        Test testObjectToBuilder = test.toBuilder().nome("bella").active(true).build();

        // In questo modo gli oggetti hanno la stessa referenza
        Test testObjectWith2 = test.withNome("asd");
        //testObjectWith2.setName("dsd");
        log.info("1: " + (test == testObjectWith2));  // true

        // Non hano la stessa referenza
        Test testObjectBuilder2 = test.toBuilder().nome("asd").build();
        //testObjectBuilder2.setName("asd");
        log.info("2: " + (test == testObjectBuilder2)); //false

        log.info("ciao");
    }

    public static void with() {
        // When working with immutable objects, which by design don’t allow setters, we may need a similar object to the current one, but with only one property different.
        // This can be achieved using Lombok’s @With annotation:
        WithExample dto = new WithExample("dd", 3);
        WithExample dto2 = dto.withAge(2);
        System.out.println("dto2: " + dto2);
    }

    public static void val() {
        val immutableList = new ArrayList<Test>(); // immutable
        immutableList.add(new Test());
        var mutableList = new ArrayList<Test>();
        mutableList.add(new Test());

        val immutable = new Test();
        immutable.setNome("nome");
        var mutable = new Test();
        mutable.setNome("baba");

        val immutableStr = "ciao";
        //immutableStr = "bella";   // ERROR
        var mutableStr = "ok";
        mutableStr = "kooko";

        val valDTO = new Val(1L, "ds");
        valDTO.setId(4L);
        valDTO.setNome("sadsa");
        log.info("Value: " + valDTO);
    }

    public static void value() {
        val value = new Value();
        log.info("Value: " + value);
    }

}
