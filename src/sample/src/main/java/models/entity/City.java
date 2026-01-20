package models.entity;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"parkingZoneList"})
@ToString(exclude = {"parkingZoneList"})
public class City extends BaseEntity<Long> implements Serializable {

    private static final long serialVersionUID = 6689583543736770436L;

    @NaturalId
    private String name;

    private String administrativeAreaLevel2Code;

    private Double latitude;

    private Double longitude;

    private Boolean stickerRequired;

    private String url;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ParkingZone> parkingZoneList = new ArrayList<>();

    public City(Long id, String name, String administrativeAreaLevel2Code, Double latitude, Double longitude, Boolean stickerRequired, String url) {
        this.id = id;
        this.name = name;
        this.administrativeAreaLevel2Code = administrativeAreaLevel2Code;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stickerRequired = stickerRequired;
        this.url = url;
    }

    public City(Long id, String name, String administrativeAreaLevel2Code) {
        this.id = id;
        this.name = name;
        this.administrativeAreaLevel2Code = administrativeAreaLevel2Code;
    }

    public City(Long id, String name, String administrativeAreaLevel2Code, Double latitude) {
        this.id = id;
        this.name = name;
        this.administrativeAreaLevel2Code = administrativeAreaLevel2Code;
        this.latitude = latitude;
    }

    public City(Long id, String name, String administrativeAreaLevel2Code, List<ParkingZone> parkingZoneList) {
        this.id = id;
        this.name = name;
        this.administrativeAreaLevel2Code = administrativeAreaLevel2Code;
        this.parkingZoneList = parkingZoneList;
    }

    public City(City c) {
        this.id = c.getId();
        this.name = c.getName();
        this.administrativeAreaLevel2Code = c.getAdministrativeAreaLevel2Code();
        this.latitude = c.getLatitude();
        this.longitude = c.getLongitude();
        this.stickerRequired = c.getStickerRequired();
        this.url = c.getUrl();
        this.parkingZoneList = c.getParkingZoneList();
    }

    public City(City c, Boolean shapes) {
        this.id = c.getId();
        this.name = c.getName();
        this.administrativeAreaLevel2Code = c.getAdministrativeAreaLevel2Code();
        this.latitude = c.getLatitude();
        this.longitude = c.getLongitude();
        this.stickerRequired = c.getStickerRequired();
        this.url = c.getUrl();
        if (Boolean.TRUE.equals(shapes))
            this.parkingZoneList = c.getParkingZoneList();
        else {
            c.getParkingZoneList().forEach(p -> p.setShapeList(new HashSet<>()));
            this.parkingZoneList = c.getParkingZoneList();
        }
    }

    @PrePersist
    public void prepersist() {
        //this.setBaseName("base name");
    }
}
