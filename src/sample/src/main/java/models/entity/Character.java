package models.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Character extends BaseEntity<Long> implements Serializable {

    private static final long serialVersionUID = 3854999379449901523L;

    @NotEmpty
    private String name;

    @NotEmpty
    private String typology;

    public Character(Long id, String name, String typology) {
        this.id = id;
        this.name = name;
        this.typology = typology;
    }
}

