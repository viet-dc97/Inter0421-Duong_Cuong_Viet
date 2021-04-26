package Model;

public abstract class Services {
//    Tất cả các dịch vụ này sẽ bao có các thông tin: Tên dịch vụ, Diện tích sử dụng, Chi phí thuê,
//    Số lượng người tối đa, Kiểu thuê (bao gồm thuê theo năm, tháng, ngày, giờ).
    private String id;
    private String serviceName;
    private double acreage;
    private double rentalCost;
    private int maxNumOfPeople;
    private String rentalType;

    public Services() {
    }

    public Services(String id, String serviceName, double acreage, double rentalCost, int maxNumOfPeople, String rentalType) {
        this.id = id;
        this.serviceName = serviceName;
        this.acreage = acreage;
        this.rentalCost = rentalCost;
        this.maxNumOfPeople = maxNumOfPeople;
        this.rentalType = rentalType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getAcreage() {
        return acreage;
    }

    public void setAcreage(double acreage) {
        this.acreage = acreage;
    }

    public double getRentalCost() {
        return rentalCost;
    }

    public void setRentalCost(double rentalCost) {
        this.rentalCost = rentalCost;
    }

    public int getMaxNumOfPeople() {
        return maxNumOfPeople;
    }

    public void setMaxNumOfPeople(int maxNumOfPeople) {
        this.maxNumOfPeople = maxNumOfPeople;
    }

    public String getRentalType() {
        return rentalType;
    }

    public void setRentalType(String rentalType) {
        this.rentalType = rentalType;
    }


    public abstract void showInfor();
}
