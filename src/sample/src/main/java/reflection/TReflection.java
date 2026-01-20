package reflection;

import lombok.extern.log4j.Log4j2;
import models.entity.Character;
import models.entity.City;
import org.hibernate.annotations.NaturalId;
import utility.UtilsCommon;
import utility.UtilsReflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public class TReflection {

    public static void main(String[] args) {
        // printClass();
        // printClass2();
        //readAnnotation();
        UtilsReflection.getFirstAnnotatedField(City.class, NaturalId.class);

        Character p = new Character(1L, "ciao", "xa");
        List<Field> values = UtilsReflection.getNotEmptyNotNull(p.getClass());
        Boolean b = UtilsReflection.checkInvalidElements(p);
        log.info(p);

        UtilsReflection.callDefaultConstructor(City.class);

        var lista = List.of("ciao");
        UtilsCommon.listToArray(lista);
    }

    public static void printClass() {
        City c = new City();
        String[] ret = UtilsReflection.getNullPropertyNames(c);
    }

    public static void printClass2() {
        City city = new City();
        String[] ret = getMethodsFiltered2(city, false, "getId");
        System.out.println("RET: ");
    }


    public static String[] getMethodsFiltered2(Object entity, boolean superclass, String... filters) {
        Set<String> methods = new HashSet<>();
        Class<?> clazz = entity.getClass();
        while (clazz != null) {
            Set<String> meths = Stream.of(clazz.getDeclaredMethods())
                    .filter(m -> Arrays.stream(filters).noneMatch(m.getName()::equals) && m.getName().startsWith("get"))
                    .map(m -> {
                        String meth = m.getName().replace("get", "");
                        return String.valueOf(meth.charAt(0)).toLowerCase() + meth.substring(1);
                    })
                    .filter(m -> UtilsReflection.callGetter(entity, m) == null)
                    .collect(Collectors.toSet());
            methods.addAll(meths);
            clazz = superclass ? clazz.getSuperclass() : null;
        }
        return methods.toArray(new String[0]);
    }


    public static void readAnnotation() {
        for (Field field : City.class.getDeclaredFields()) {
            Annotation[] annotations = field.getDeclaredAnnotationsByType(NaturalId.class);
            if (annotations.length > 0)
                System.out.println(field.getName());
        }
    }

}
