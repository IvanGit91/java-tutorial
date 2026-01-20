package tutorial.models.city;

import java.util.ArrayList;
import java.util.List;


public class City {
    private final Long id;

    private final String name;

    private final String administrativeAreaLevel2Code;

    private Double latitude;

    private Double longitude;

    private Boolean stickerRequired;

    private String url;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAdministrativeAreaLevel2Code() {
        return administrativeAreaLevel2Code;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Boolean getStickerRequired() {
        return stickerRequired;
    }

    public String getUrl() {
        return url;
    }

    public List<ParkingZone> getParkingZoneList() {
        return parkingZoneList;
    }

    public void setParkingZoneList(List<ParkingZone> parkingZoneList) {
        this.parkingZoneList = parkingZoneList;
    }
}
