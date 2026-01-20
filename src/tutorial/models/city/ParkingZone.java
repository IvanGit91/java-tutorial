package tutorial.models.city;

public class ParkingZone {

    private final String name;
    private final String description;
    private final City city;
    private Long id;
    private String vendorId;
    private String vendorName;
    private String vendorUrl;
    private String notices;
    private Boolean stallCodeRequired;

    public ParkingZone(String name, String description, City city) {
        this.name = name;
        this.description = description;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getVendorId() {
        return vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getVendorUrl() {
        return vendorUrl;
    }

    public String getNotices() {
        return notices;
    }

    public Boolean getStallCodeRequired() {
        return stallCodeRequired;
    }

    public City getCity() {
        return city;
    }
}
