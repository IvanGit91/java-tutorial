package tutorial.models.album;

public class Track {

    private final Long id;
    private final String name;
    private final Integer length;
    private Album album;

    public Track(Long id, String name, Integer length) {
        this.id = id;
        this.name = name;
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getLength() {
        return length;
    }

    public Album getAlbum() {
        return album;
    }
}
