package tutorial.models.album;

import java.util.List;

public class Artist {

    private final Long id;
    private final String name;
    private String nationality;
    private boolean isSolo;
    private List<Member> members;
    private List<Album> albums;

    public Artist(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public boolean isSolo() {
        return isSolo;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Album> getAlbums() {
        return albums;
    }
}
