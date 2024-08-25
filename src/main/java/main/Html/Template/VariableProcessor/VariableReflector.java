package main.java.main.Html.Template.VariableProcessor;

import java.lang.reflect.Field;

public class VariableReflector {
    private static void processVariable(String packageName) {
        // Get the class loader for the package
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            // Load the class
            Class<?> clazz = classLoader.loadClass(packageName);

            // Get the class name
            String className = clazz.getSimpleName();

            // Get the class variables
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static Thread processVariableThread(Field field) {
        return new Thread(() -> {

        });
    }
}
