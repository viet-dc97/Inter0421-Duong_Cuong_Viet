package Model;

public class Room extends  Services{
//    -	Riêng Phòng sẽ có thêm thông tin: Dịch vụ miễn phí đi kèm.
    private String freeService;

    public Room() {
    }

    public Room(String id, String serviceName, double acreage, double rentalCost, int maxNumOfPeople, String rentalType, String freeService) {
        super(id, serviceName, acreage, rentalCost, maxNumOfPeople, rentalType);
        this.freeService = freeService;
    }

    public String getFreeService() {
        return freeService;
    }

    public void setFreeService(String freeService) {
        this.freeService = freeService;
    }

    @Override
    public void showInfor() {
        System.out.println("id= " + super.getId() +
                ", serviceName= " + super.getServiceName() +
                ", acreage= " + super.getAcreage() +
                ", rentalCost= " + super.getRentalCost() +
                ", maxNumOfPeople= " + super.getMaxNumOfPeople() +
                ", rentalType= " + super.getRentalType() +
                ", freeService= " + getFreeService()

        );
    }
}
