package utility;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class for Java reflection operations.
 * Provides methods to dynamically invoke getters/setters,
 * inspect fields and methods, and handle JPA annotations.
 */
@Log4j2
public class UtilsReflection {

    /**
     * Sets the non-null field values of an entity using setters.
     *
     * @param entity  Object to modify
     * @param methods Map of field name -> value
     */
    public static void setClassNotNullMethods(Object entity, Map<String, Object> methods) {
        methods.forEach((key, value) -> callSetter(entity, key, value));
    }

    /**
     * Dynamically invokes the getter of a field.
     *
     * @param obj       Target object
     * @param fieldName Field name
     * @return Field value
     */
    @SneakyThrows
    public static Object callGetter(Object obj, String fieldName) {
        PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
        return pd.getReadMethod().invoke(obj);
    }

    /**
     * Dynamically invokes the getter of a field from the superclass.
     *
     * @param obj       Target object
     * @param fieldName Field name
     * @return Field value
     */
    @SneakyThrows
    public static Object callGetterSuperclass(Object obj, String fieldName) {
        return obj.getClass().getSuperclass().getMethod("get" + UtilsManipulation.capitalize(fieldName)).invoke(obj);
    }

    /**
     * Invokes a getter that returns a list and logs it.
     *
     * @param v        Target object
     * @param listName List field name
     */
    public static <T, E> void callGetterList(E v, String listName) {
        List<T> pro = (List<T>) UtilsReflection.callGetter(v, listName);
        log.info("List {}: {}", listName, pro);
    }

    /**
     * Dynamically invokes the setter of a field.
     *
     * @param obj       Target object
     * @param fieldName Field name
     * @param value     Value to set
     */
    @SneakyThrows
    public static void callSetter(Object obj, String fieldName, Object value) {
        PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
        pd.getWriteMethod().invoke(obj, value);
    }

    /**
     * Invokes a static method without parameters.
     *
     * @param clazz      Target class
     * @param methodName Name of the method to invoke
     */
    public static <T> void callMethod(Class<T> clazz, String methodName) {
        try {
            Method method = clazz.getMethod(methodName);
            method.invoke(null);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException e) {
            log.error("Error invoking method {}: {}", methodName, e.getMessage(), e);
        }
    }

    /**
     * Invokes a static method with a string parameter.
     *
     * @param clazz      Target class
     * @param methodName Name of the method to invoke
     * @param param      String parameter
     */
    public static <T> void callMethod(Class<T> clazz, String methodName, String param) {
        try {
            Method method = clazz.getMethod(methodName, String.class);
            method.invoke(null, param);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException e) {
            log.error("Error invoking method {} with parameter {}: {}", methodName, param, e.getMessage(), e);
        }
    }

    /**
     * Gets all methods of a class, including inherited ones.
     *
     * @param clazz Class to analyze
     * @return Collection of methods
     */
    public static Collection<Method> getMethods(Class<?> clazz) {
        Map<String, Method> fields = new HashMap<>();
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (!fields.containsKey(method.getName())) {
                    fields.put(method.getName(), method);
                }
            }
            clazz = clazz.getSuperclass();
        }
        Collection<Method> returnCollection = fields.values();
        returnCollection.forEach(e -> log.info("M: {} {}", e.getName(), e.getParameterTypes()));
        return returnCollection;
    }

    /**
     * Gets filtered getter method names.
     *
     * @param clazz   Class to analyze
     * @param filters Array of names to exclude
     * @return Set of getter method names
     */
    public static Set<String> getMethodsFiltered(Class<?> clazz, String[] filters) {
        Set<String> methods = new HashSet<>();
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().startsWith("get")) {
                    String name = method.getName().replace("get", "");
                    name = String.valueOf(name.charAt(0)).toLowerCase() + name.substring(1);
                    if (!Arrays.stream(filters).anyMatch(name::equals)) {
                        methods.add(name);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return methods;
    }

    /**
     * Searches for a field by name in the class and its superclasses.
     *
     * @param clazz  Class to search in
     * @param target Field name to search for
     * @return The found field, null if not found
     */
    public static Field getField(Class<?> clazz, String target) {
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (target.equals(field.getName())) {
                    return field;
                }
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    /**
     * Gets all fields of a class, including inherited ones.
     *
     * @param clazz Class to analyze
     * @return Collection of fields
     */
    public static Collection<Field> getFields(Class<?> clazz) {
        Map<String, Field> fields = new HashMap<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!fields.containsKey(field.getName())) {
                    fields.put(field.getName(), field);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields.values();
    }

    /**
     * Creates a new instance of a class using the default constructor.
     *
     * @param clazz Class to instantiate
     * @return New instance, null on error
     */
    public static <E> E createContents(Class<E> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            log.error("Error creating instance of {}: {}", clazz.getName(), e.getMessage(), e);
        }
        return null;
    }

    /**
     * Gets property names with null values.
     *
     * @param source Object to analyze
     * @return Array of null property names
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    /**
     * Gets the list of null property names or empty lists.
     *
     * @param source Object to analyze
     * @return List of null property names or empty lists
     */
    public static List<String> getNullPropertyNamesList(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> {
                    boolean res = false;
                    Object obj = wrappedSource.getPropertyValue(propertyName);
                    if (obj != null) {
                        if (obj instanceof List<?> list) {
                            res = list.isEmpty();
                            // Note: for now always exclude lists to avoid error
                            // "org.hibernate.PersistentObjectException: detached entity passed to persist"
                        }
                    } else {
                        res = true;
                    }
                    return res;
                })
                .collect(Collectors.toList());
    }

    /**
     * Gets the list of non-null property names (excluding lists and classes).
     *
     * @param source Object to analyze
     * @return List of non-null property names
     */
    public static List<String> getNotNullPropertyNamesList(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> {
                    boolean res = true;
                    Object obj = wrappedSource.getPropertyValue(propertyName);
                    if (obj != null) {
                        if (obj instanceof List<?> || obj instanceof Class<?>) {
                            res = false;
                        }
                    } else {
                        res = false;
                    }
                    return res;
                })
                .collect(Collectors.toList());
    }

    /**
     * Gets a map of getter methods with non-null values.
     *
     * @param entity     Object to analyze
     * @param superclass If true, also includes superclass methods
     * @param filters    Method names to exclude
     * @return Map of method name -> value
     */
    public static Map<String, Object> getClassNotNullMethods(Object entity, boolean superclass, String... filters) {
        Map<String, Object> methods = new HashMap<>();
        Class<?> clazz = entity.getClass();
        while (clazz != null) {
            Map<String, Object> meths = Stream.of(clazz.getDeclaredMethods())
                    .filter(m -> Arrays.stream(filters).noneMatch(m.getName()::equals) && m.getName().startsWith("get"))
                    .map(m -> {
                        String meth = m.getName().replace("get", "");
                        return String.valueOf(meth.charAt(0)).toLowerCase() + meth.substring(1);
                    })
                    .filter(m -> callGetter(entity, m) != null)
                    .collect(Collectors.toMap(m -> m, m -> callGetter(entity, m)));
            methods.putAll(meths);
            clazz = superclass ? clazz.getSuperclass() : null;
        }
        return methods;
    }

    /**
     * Finds the first field with a specific annotation.
     *
     * @param clazz           Class to analyze
     * @param annotationClass Annotation class to search for
     * @return Optional containing the field, empty if not found
     */
    public static Optional<Field> getFirstAnnotatedField(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.getDeclaredAnnotationsByType(annotationClass).length > 0)
                .findFirst();
    }

    /**
     * Finds all fields with a specific annotation.
     *
     * @param clazz           Class to analyze
     * @param annotationClass Annotation class to search for
     * @return List of annotated fields
     */
    public static List<Field> getAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.getDeclaredAnnotationsByType(annotationClass).length > 0)
                .collect(Collectors.toList());
    }

    /**
     * Finds all fields with any of the specified annotations.
     *
     * @param clazz             Class to analyze
     * @param annotationClasses List of annotation classes to search for
     * @return List of annotated fields
     */
    public static List<Field> getAnnotatedFields(Class<?> clazz, List<Class<? extends Annotation>> annotationClasses) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> annotationClasses.stream()
                        .anyMatch(a -> f.getDeclaredAnnotationsByType(a).length > 0))
                .collect(Collectors.toList());
    }

    /**
     * Finds all fields annotated with @NotNull or @NotEmpty.
     *
     * @param clazz Class to analyze
     * @return List of required fields
     */
    public static List<Field> getNotEmptyNotNull(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.getDeclaredAnnotationsByType(NotNull.class).length > 0
                        || f.getDeclaredAnnotationsByType(NotEmpty.class).length > 0)
                .collect(Collectors.toList());
    }

    /**
     * Checks if an entity has required fields with null values.
     *
     * @param entity Entity to verify
     * @return true if there are null required fields
     */
    public static <T> Boolean checkInvalidElements(T entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.getDeclaredAnnotationsByType(NotNull.class).length > 0
                        || f.getDeclaredAnnotationsByType(NotEmpty.class).length > 0)
                .anyMatch(f -> callGetter(entity, f.getName()) == null);
    }

    /**
     * Finds the name of the field annotated with @Id.
     *
     * @param entity Entity to analyze
     * @return ID field name, null if not found
     */
    public static <T> String findAndGetIdFieldName(T entity) {
        Optional<Field> field = UtilsReflection.getFirstAnnotatedField(entity.getClass(), Id.class);
        return field.map(Field::getName).orElse(null);
    }

    /**
     * Finds and returns the value of the field annotated with @Id.
     *
     * @param entity Entity to analyze
     * @return ID value, null if not found
     */
    @SuppressWarnings("unchecked")
    public static <T, ID extends Serializable> ID findAndGetId(T entity) {
        Optional<Field> field = UtilsReflection.getFirstAnnotatedField(entity.getClass(), Id.class);
        return field.map(f -> (ID) UtilsReflection.callGetter(entity, f.getName())).orElse(null);
    }

    /**
     * Finds fields whose type matches a target class.
     * Also supports List and Set fields with matching generic type.
     *
     * @param clazz       Class to analyze
     * @param targetClass Target class to search for
     * @return List of matching fields
     */
    public static List<Field> equalsTargetClassFields(Class<?> clazz, Class<?> targetClass) {
        return Arrays.stream(clazz.getDeclaredFields()).filter(f -> {
            Class<?> classToCheck = f.getType();
            if (f.getType().isAssignableFrom(List.class) || f.getType().isAssignableFrom(Set.class)) {
                ParameterizedType listType = (ParameterizedType) f.getGenericType();
                classToCheck = (Class<?>) listType.getActualTypeArguments()[0];
            }
            return classToCheck.equals(targetClass);
        }).collect(Collectors.toList());
    }

    /**
     * Finds fields with JPA relationships that have mappedBy equal to the specified name.
     *
     * @param clazz     Class to analyze
     * @param fieldName mappedBy field name to search for
     * @return List of fields with matching mappedBy
     */
    public static List<Field> equalsTargetClassFieldMappedBy(Class<?> clazz, String fieldName) {
        return Arrays.stream(clazz.getDeclaredFields()).filter(f -> {
            OneToOne oneToOne = f.getAnnotation(OneToOne.class);
            OneToMany oneToMany = f.getAnnotation(OneToMany.class);
            ManyToMany manyToMany = f.getAnnotation(ManyToMany.class);
            String mappedBy = oneToOne != null ? oneToOne.mappedBy()
                    : oneToMany != null ? oneToMany.mappedBy()
                    : manyToMany != null ? manyToMany.mappedBy() : "";
            return mappedBy.equals(fieldName);
        }).collect(Collectors.toList());
    }

    /**
     * Creates a new instance using the default constructor.
     *
     * @param clazz Class to instantiate
     * @return New instance
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T> T callDefaultConstructor(Class<?> clazz) {
        Constructor<?> constructor = clazz.getConstructor();
        return (T) constructor.newInstance();
    }
}
