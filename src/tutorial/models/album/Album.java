package tutorial.models.album;

import java.util.List;

public class Album {

    private final Long id;
    private final String name;
    private Artist mainMusician;
    private List<Track> tracks;
    private List<Artist> musicians;

    public Album(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Artist getMainMusician() {
        return mainMusician;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Artist> getMusicians() {
        return musicians;
    }
}
