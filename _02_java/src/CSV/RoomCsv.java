package CSV;

import Commons.RegexMatches;
import Model.Room;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RoomCsv extends CsvCommon<Room> {
    public static final String FILE_HEADER = "id,serviceName,acreage,rentalCost,maxNumOfPeople,rentalType,freeService";
    public static final String file_room = "src/Data/room.csv";
    private static final String CHECK_ID_ROOM = "^SV+[RO]+[-]\\d{4}$";
    private static final String CHECK_NAME = "^[A-Z][a-z]{0,}+$";
    private static final String CHECK_FREE_SERVICE = "(MASSAGE)|(KARAOKE)|(DRINK)|(FOOD)|(CAR)";
    private static final String CHECK_RENT_TYPE = "(DAY)|(MONTH)|(YEAR)|(HOUR)";
    RegexMatches regexMatches = new RegexMatches();

    static Scanner sc = new Scanner(System.in);

    @Override
    public ArrayList<Room> getFileCsvToList() {
        String line = "";
        ArrayList<Room> listRoom = new ArrayList<>();
        BufferedReader br = null;
        try {

            br = new BufferedReader(new FileReader(file_room));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals("id")) {
                    continue;
                }
                Room room = new Room();
                room.setId(values[0]);
                room.setServiceName(values[1]);
                room.setAcreage(Double.parseDouble(values[2]));
                room.setRentalCost(Double.parseDouble(values[3]));
                room.setMaxNumOfPeople(Integer.parseInt(values[4]));
                room.setRentalType(values[5]);
                room.setFreeService(values[6]);
                listRoom.add(room);


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listRoom;
    }

    @Override
    public void writeListToCsv(ArrayList<Room> list) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file_room);
            fileWriter.append(FILE_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);
            for(Room room : list){
                fileWriter.append(room.getId());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(room.getServiceName());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(room.getAcreage()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(room.getRentalCost()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(room.getMaxNumOfPeople()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(room.getRentalType());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(room.getFreeService());
                fileWriter.append(NEW_LINE_SEPARATOR);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void addNew() {
        ArrayList<Room> list = getFileCsvToList();
        Room room =new Room();


        System.out.println("Nhập id ");
        String id = sc.next();
        room.setId(id);
        while(!regexMatches.checkPattern(id, CHECK_ID_ROOM)){
            System.out.println("Id có định dạng là 'SVRO-XXXX'");
            System.out.println("Nhập lại Id: ");
            id = sc.next();
            if(regexMatches.checkPattern(id, CHECK_ID_ROOM)){
                room.setId(id);
                break;
            }
        }

        System.out.println("Nhập serviceName ");
        String serviceName = sc.next();
        room.setServiceName(serviceName);
        while(!regexMatches.checkPattern(serviceName, CHECK_NAME)){
            System.out.println("Name viết hoa ký tự đầu, các ký tự sau là ký tự bình thường");
            System.out.println("Nhập lại serviceName: ");
            serviceName = sc.next();
            if(regexMatches.checkPattern(serviceName, CHECK_NAME)){
                room.setServiceName(serviceName);
                break;
            }
        }

        System.out.println("Nhập acreage ");
        Double acreage = sc.nextDouble();
        room.setAcreage(acreage);
        while(acreage < 30){
            System.out.println("Diện tích phải lớn hơn 30m2");
            System.out.println("Nhập lại acreage: ");
            acreage = sc.nextDouble();
            if(acreage >= 30){
                room.setAcreage(acreage);
                break;
            }
        }

        System.out.println("Nhập rentalCost ");
        Double rentalCost = sc.nextDouble();
        room.setRentalCost(rentalCost);
        while(rentalCost <= 0){
            System.out.println("Chi phí thuê phải lớn hơn 0");
            System.out.println("Nhập lại rentalCost: ");
            rentalCost = sc.nextDouble();
            if(rentalCost > 0){
                room.setRentalCost(rentalCost);
                break;
            }
        }

        System.out.println("Nhập maxNumOfPeople ");
        int maxNumOfPeople = sc.nextInt();
        room.setMaxNumOfPeople(maxNumOfPeople);
        while(maxNumOfPeople <= 0 || maxNumOfPeople >=20){
            System.out.println("Số lượng người tối đa phải >0 và nhỏ hơn <20");
            System.out.println("Nhập lại maxNumOfPeople: ");
            maxNumOfPeople = sc.nextInt();
            if(maxNumOfPeople > 0 && maxNumOfPeople <20){
                room.setMaxNumOfPeople(maxNumOfPeople);
                break;
            }
        }

        System.out.println("Nhập rentalType ");
        String rentalType = sc.next();
        room.setRentalType(rentalType);
        while(!regexMatches.checkPattern(rentalType, CHECK_RENT_TYPE)){
            System.out.println("RentalType có định dạng DAY | MONTH | YEAR | HOUR");
            System.out.println("Nhập lại rentalType: ");
            rentalType = sc.next();
            if(regexMatches.checkPattern(rentalType, CHECK_RENT_TYPE)){
                room.setRentalType(rentalType);
                break;
            }
        }


        System.out.println("Nhập freeService ");
        String freeService = sc.next();
        room.setFreeService(freeService);
        while(!regexMatches.checkPattern(freeService, CHECK_FREE_SERVICE)){
            System.out.println("Free sevrice có dạng MASSAGE | KARAOKE | DRINK | FOOD | CAR");
            System.out.println("Nhập lại freeService: ");
            freeService = sc.next();
            if(regexMatches.checkPattern(freeService, CHECK_FREE_SERVICE)){
                room.setRentalType(freeService);
                break;
            }
        }

        list.add(room);
        writeListToCsv(list);
        System.out.println("Complete");
    }

    @Override
    public void showAll() {
        ArrayList<Room> listRoom = getFileCsvToList();
        for(Room room : listRoom){
            System.out.println("-------------------");
            room.showInfor();
        }
    }
}

