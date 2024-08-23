package main.java.main.Testing.routes;

import main.java.main.Html.Metadata.Metadata;
import main.java.main.Html.Page.Page;

@Page(path = "/home", method = "GET", headers = {"Content-Type:text/html"}, html = "home.html")
public class Home {

    @Metadata(title = "Home", description = "Home page")
    public static void Javascript() {

    }
}
