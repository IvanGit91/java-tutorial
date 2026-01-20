package lombok.builder;


import lombok.Test;

public class TBuilderLombok {

    public static void main(String[] args) {
        // Con lombok, @Test2DTOBuilder(toBuilder = true)
        Test testClassBuilder = Test.builder().id(2L).nome("ciao").build();
    }

}
