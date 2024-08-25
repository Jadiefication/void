package main.java.main.Testing.routes.Home;

import main.java.main.Html.Page.Page;
import main.java.main.Html.Template.Variables;

@Page(path = "/home", method = "GET", headers = {"Content-Type:application/jsp"}, html = "home.jsp")
public class Home {

    @Variables
    public String name = "hello";
}
