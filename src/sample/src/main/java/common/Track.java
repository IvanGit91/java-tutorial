package common;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Track {

    private Long id;
    private String name;
    private Integer length;
    private Album album;

    public Track(Long id, String name, Integer length) {
        this.id = id;
        this.name = name;
        this.length = length;
    }
}
