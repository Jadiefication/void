package Metadata;

public class Metadata {
    public String tittle;
    public String description;

    public String getTittle() {
        return tittle;
    }

    public String getDescription() {
        return description;
    }

    public Metadata(String tittle, String description) {
        this.tittle = tittle;
        this.description = description;
    }
}
