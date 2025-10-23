public class TurfModel {
    private int turfId;
    private String name;
    private String address;
    private int pricePerHour;
    private String operatingHours;
    private String category;
    private String imagePath;

    public TurfModel(int turfId, String name, String address, int pricePerHour, String operatingHours, String category, String imagePath) {
        this.turfId = turfId;
        this.name = name;
        this.address = address;
        this.pricePerHour = pricePerHour;
        this.operatingHours = operatingHours;
        this.category = category;
        this.imagePath = imagePath;
    }

    public TurfModel(int turfId, String name, String address, int pricePerHour, String operatingHours) {
        this(turfId, name, address, pricePerHour, operatingHours, null, null);
    }

    public TurfModel(int turfId, String name, String address, int pricePerHour, String operatingHours, String category) {
        this(turfId, name, address, pricePerHour, operatingHours, category, null);
    }

    public int getTurfId() {
        return turfId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCategory() {
        return category;
    }
}
