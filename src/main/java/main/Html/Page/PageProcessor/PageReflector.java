package main.java.main.Html.Page.PageProcessor;

import main.java.main.Html.Page.Page;
import main.java.main.Server.Server;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * The PageReflector class is responsible for processing classes annotated with the @Page annotation.
 * It uses reflection to scan for these classes and processes their annotations to set up server pages.
 */
public class PageReflector {

    private static File file;

    /**
     * Processes classes in the specified package for @Page annotations.
     *
     * @param packageName The name of the package to scan for annotated classes.
     */
    public static void processClasses(String packageName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        String path = packageName.replace('.', '/');
        try {
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                file = new File(resource.getFile());
                for (File directory : Objects.requireNonNull(file.listFiles())) {
                    processDirectories(directory, packageName).start();
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Check if you set your package name correctly.");
        }
    }

    private static Thread processDirectories(File directory, String packageName) {
        return new Thread(() -> {
            if (directory.isDirectory()) {
                for (File classFile : Objects.requireNonNull(directory.listFiles())) {
                    processFiles(directory, packageName, classFile).start();
                }
            }
        });
    }


    private static Thread processFiles(File directory, String packageName, File classFile) {
        return new Thread(() -> {
            if (classFile.getName().endsWith(".class")) {
                String className = STR."\{packageName}.\{classFile.getName().replace(".class", "")}.\{classFile.getName().replace(".class", "")}";
                System.out.println(STR."Processing directory: \{directory.getAbsolutePath()}, file: \{classFile.getName()}, class: \{className}");
                try {
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(Page.class)) {
                        processPageAnnotation(clazz, classFile.getName().replace(".class", ""));
                    }
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(STR."Class not found: \{className}, check if your package name is the same as the class name.");
                }
            }
        });
    }
    /**
     * Processes a single class annotated with @Page.
     * Extracts annotation data and sets up the corresponding server page.
     *
     * @param clazz The Class object representing the annotated class.
     * @throws IllegalArgumentException If there's an error reading the HTML file.
     */
    private static void processPageAnnotation(Class<?> clazz, String className) {
        try {

            Page pageAnnotation = clazz.getAnnotation(Page.class);
            String path = pageAnnotation.path();
            String method = pageAnnotation.method();
            String[] headers = pageAnnotation.headers();
            String html;

            String annotationHTML = pageAnnotation.html();

            ClassLoader classLoader = PageReflector.class.getClassLoader();
            URL resource = classLoader.getResource("main/java/main/Html/Page/PageProcessor/not_found.html");
            assert resource != null;
            String notFoundPath = new File(resource.toURI()).getAbsolutePath();

            if (Objects.equals(annotationHTML, "not_found.html")) {
                html = Files.readString(Path.of(notFoundPath));
            }  else {
                html = Files.readString(new File(STR."\{file}\\\{className}\\\{pageAnnotation.html()}").toPath());
            }

            Map<String, String> headerMap = parseHeaders(headers);

            Server.MakePage(method, path, html, headerMap);
        }  catch (Exception e) {
            throw new IllegalArgumentException(STR."Failed to read HTML file: \{e.getMessage()}");
        }
    }

    /**
     * Parses the headers from the @Page annotation.
     *
     * @param headers An array of header strings from the annotation.
     * @return A Map containing the parsed headers.
     */
    private static Map<String, String> parseHeaders(String[] headers) {
        Map<String, String> headerMap = new HashMap<>();
        for (String header : headers) {
            String[] parts = header.split(":", 2);
            if (parts.length == 2) {
                headerMap.put(parts[0].trim(), parts[1].trim());
            }
        }
        return headerMap;
    }
}
