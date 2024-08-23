package main.Testing.home;

import main.Html.Page.Page;

@Page(path = "/", method = "GET", headers = {"Content-Type:text/html"}, html = "./home.html")
public class Home {
}
