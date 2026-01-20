package tutorial.annotation;

import java.lang.reflect.Method;

public class TAnnotation {

    public static void main(String[] args) {

        Class businessLogicClass = AnnotationTest.class;
        for (Method method : businessLogicClass.getMethods()) {
            Todo todoAnnotation = method.getAnnotation(Todo.class);
            if (todoAnnotation != null) {
                System.out.println(" Method Name : " + method.getName());
                System.out.println(" Author : " + todoAnnotation.author());
                System.out.println(" Priority : " + todoAnnotation.priority());
                System.out.println(" Status : " + todoAnnotation.status());
            }
        }

    }


    @Todo(priority = Todo.Priority.MEDIUM, author = "Yashwant", status = Todo.Status.STARTED)
    public void incompleteMethod1() {
        //Some business logic is written
        //But it’s not complete yet
    }

    @Author("Yashwant")
    public void someMethod() {
    }

}
