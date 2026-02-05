package newfeatures.v6;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.Console;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Java6 {
    public static void main(String[] args) throws ScriptException {
        // Scripting Support (JSR 223):
        // This allowed developers to embed scripting languages such as JavaScript, Groovy, and others directly into their Java applications.
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        String script = "print('Hello, Java 1.6 Scripting!')";
        engine.eval(script);

        // 2. Pluggable Annotations (JSR 269):

        // 3. Java Compiler API (JSR 199):
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, null, null, "path/to/YourClass.java");
        if (result == 0) {
            System.out.println("Compilation succeeded!");
        } else {
            System.out.println("Compilation failed.");
        }

        // 4. Console Class:
        Console console = System.console();
        if (console != null) {
            String userInput = console.readLine("Enter your name: ");
            console.printf("Hello, %s!\n", userInput);
        } else {
            System.out.println("Console not available.");
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface MyAnnotation {
        String value();
    }

    public class AnnotationProcessorExample {
        @MyAnnotation("Example Value")
        public void myMethod() {
            // Method implementation
        }
    }
}
