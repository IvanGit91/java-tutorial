package models.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Shape extends BaseEntity<Long> implements Serializable {

    private static final long serialVersionUID = 5326039044673255484L;

    private String geometry;  // Format WKT

    private String fillColor;  //rgb hex a 8 bit (#rrggbb)

    private String strokeColor;  //rgb hex a 8 bit (#rrggbb)

    private ParkingZone parkingZone;
}
