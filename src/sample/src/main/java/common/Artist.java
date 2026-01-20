package common;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Artist {

    private Long id;
    private String name;
    private String nationality;
    private boolean isSolo;
    private List<Member> members;
    private List<Album> albums;

    public Artist(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
