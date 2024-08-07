package Main.home;

import Metadata.Metadata;
import Routing.Page.Page;

public class Home {

    public static final Metadata metadata = new Metadata("Test", "Test");

    public static void home() {
        Page.Page(metadata, "/", "<html><body><p>Hello, world!</p></body></html>");

    }
}
