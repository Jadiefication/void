package main.java.main.Html.Metadata.MetadataProcessor;

import main.java.main.Html.Metadata.Metadata;
import main.java.main.Html.Page.Page;
import main.java.main.Html.Page.PageProcessor.PageReflector;

import java.io.File;
import java.lang.reflect.Method;
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
public class MetadataReflector {
    private static File file;

    /**
      * Processes classes in the specified package for @Page annotations.
      *
      * @param packageName The name of the package to scan for annotated classes.
      */
    public static void processClasses(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        try {
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                file = new File(resource.getFile());
                System.out.println("Processing directory: " + file.getAbsolutePath());
                for (File classFile : Objects.requireNonNull(file.listFiles())) {
                    String className = packageName + "." + classFile.getName().replace(".class", "");
                    try {
                        Class<?> clazz = Class.forName(className);
                        if (clazz.isAnnotationPresent(Page.class)) {
                            processMetadataAnnotation(clazz);
                        }
                    } catch (ClassNotFoundException _) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
      * Processes a single class annotated with @Page.
      * Extracts annotation data and sets up the corresponding server page.
      *
      * @param clazz The Class object representing the annotated class.
      * @throws IllegalArgumentException If there's an error reading the HTML file.
      */
    private static void processMetadataAnnotation(Class<?> clazz) {
        try {
            Method method = clazz.getMethod("Javascript");
            Metadata metadata = method.getAnnotation(Metadata.class);
            String html;

            Page pageAnnotation = clazz.getAnnotation(Page.class);
            String htmlPath = pageAnnotation.html();

            ClassLoader classLoader = PageReflector.class.getClassLoader();
            URL resource = classLoader.getResource("main/java/main/Html/Page/PageProcessor/not_found.html");
            assert resource != null;
            String notFoundPath = new File(resource.toURI()).getAbsolutePath();

            Path filePath = Objects.equals(htmlPath, "not_found.html")
                    ? Path.of(notFoundPath)
                    : new File(file + "\\" + pageAnnotation.html()).toPath();

            html = Files.readString(filePath);

            if(html.contains("<head>")) {
                html = html.replace("<head>", "<head><meta name=\"tittle\" content=\"" + metadata.title() + "\"> <meta name=\"description\" content=\"" + metadata.description() + "\"> </head>");
            }  else {
                html = html.replace("<html>", "<html><head> <meta name=\"tittle\" content=\"" + metadata.title() + "\"> <meta name=\"description\" content=\"" + metadata.description() + "\"> </head>");
            }

            Files.writeString(filePath, html);

        } catch (Exception e) {
            throw new IllegalArgumentException(STR."Class \{clazz.getName()} must have a method named 'Javascript'.");
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
