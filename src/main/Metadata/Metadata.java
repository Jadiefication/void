package main.Metadata;

/**
 * The Metadata class represents metadata information for a page.
 */
public class Metadata {
    public String tittle;
    public String description;

    public String getTittle() {
        return tittle;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Constructs a new Metadata instance with the given title and description.
     *
     * @param tittle The title of the page.
     * @param description The description of the page.
     */
    public Metadata(String tittle, String description) {
        this.tittle = tittle;
        this.description = description;
    }
}
