package common;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Album {

    private Long id;
    private String name;
    private Artist mainMusician;
    private List<Track> tracks;
    private List<Artist> musicians;

    public Album(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
