package models.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString
public class ParkingZone extends BaseEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1261215699201544820L;

    private String name;

    private String description;

    private String vendorId;

    private String vendorName;

    private String vendorUrl;

    private String notices;

    private Boolean stallCodeRequired;

    private City city;

    private Set<Shape> shapeList = new HashSet<>();

    public ParkingZone(String name, String description, City city) {
        this.name = name;
        this.description = description;
        this.city = city;
    }
}
