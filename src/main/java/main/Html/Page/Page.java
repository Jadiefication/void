package main.java.main.Html.Page;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Page {
    String path() default "/";

    String method() default "GET";

    String[] headers() default {"Content-Type:text/html"};

    String html() default "not_found.html";

}
