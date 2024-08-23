package main.Html.Page.PageProcessor;

import main.Html.Page.Page;
import main.Server.Server;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("Html.Page.Page")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class PageProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Page.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                try {
                    TypeElement classElement = (TypeElement) element;
                    Page annotation = classElement.getAnnotation(Page.class);

                    String[] headerArray = annotation.headers();
                    Map<String, String> headerMap = parseHeaders(headerArray);

                    String html = Files.readString(new File(annotation.html()).toPath());

                    Server.MakePage(annotation.method(), annotation.path(), html, headerMap);
                } catch (IOException e) {
                    throw new IllegalArgumentException("Failed to read HTML file: " + e.getMessage());
                }
            }
        }
        return true;
    }

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
