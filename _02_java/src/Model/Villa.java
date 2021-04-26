package Model;

public class Villa extends Services {
    private String roomType;
    private String description;
    private int pollArea;
    private int floors;

    public Villa() {
    }

    public Villa(String id, String serviceName, double acreage, double rentalCost, int maxNumOfPeople, String rentalType, String roomType, String description, int pollArea, int floors) {
        super(id, serviceName, acreage, rentalCost, maxNumOfPeople, rentalType);
        this.roomType = roomType;
        this.description = description;
        this.pollArea = pollArea;
        this.floors = floors;
    }


    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPollArea() {
        return pollArea;
    }

    public void setPollArea(int pollArea) {
        this.pollArea = pollArea;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }


    @Override
    public void showInfor() {
        System.out.println("id= " + super.getId() +
                ", serviceName= " + super.getServiceName() +
                ", acreage= " + super.getAcreage() +
                ", rentalCost= " + super.getRentalCost() +
                ", maxNumOfPeople= " + super.getMaxNumOfPeople() +
                ", rentalType= " + super.getRentalType() +
                ", roomType= " + getRoomType()  +
                ", description= " + getDescription()  +
                ", pollArea= " + getPollArea() +
                ", floors= " + getFloors()

        );
    }

}
