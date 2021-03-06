package CSV;

import Commons.RegexMatches;
import Model.House;
import Views.csvReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HouseCsv extends CsvCommon<House> {
    public static final String FILE_HEADER = "id,serviceName,acreage,rentalCost,maxNumOfPeople,rentalType,roomType,description,floors";
    public static final String file_room = "src/Data/house.csv";
    static Scanner sc = new Scanner(System.in);
    private static final String CHECK_ID_HOUSE = "^SV+[HO]+[-]\\d{4}$";
    private static final String CHECK_NAME = "^[A-Z][a-z]{0,}+$";
    private static final String CHECK_RENT_TYPE = "(DAY)|(MONTH)|(YEAR)|(HOUR)";
    RegexMatches regexMatches = new RegexMatches();

    @Override
    public ArrayList<House> getFileCsvToList() {
        String line = "";
        ArrayList<House> listHouse = new ArrayList<>();
        BufferedReader br = null;
        try {

            br = new BufferedReader(new FileReader(file_room));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals("id")) {
                    continue;
                }
                House house = new House();
                house.setId(values[0]);
                house.setServiceName(values[1]);
                house.setAcreage(Double.parseDouble(values[2]));
                house.setRentalCost(Double.parseDouble(values[3]));
                house.setMaxNumOfPeople(Integer.parseInt(values[4]));
                house.setRentalType(values[5]);
                house.setRoomType(values[6]);
                house.setDescription(values[7]);
                house.setFloors(Integer.parseInt(values[8]));
                listHouse.add(house);

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
        return listHouse;
    }

    @Override
    public void writeListToCsv(ArrayList<House> list) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file_room);
            fileWriter.append(FILE_HEADER);
            fileWriter.append(csvReader.NEW_LINE_SEPARATOR);
            for(House house : list){
                fileWriter.append(house.getId());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(house.getServiceName());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(String.valueOf(house.getAcreage()));
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(String.valueOf(house.getRentalCost()));
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(String.valueOf(house.getMaxNumOfPeople()));
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(house.getRentalType());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(house.getRoomType());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(house.getDescription());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(String.valueOf(house.getFloors()));

                fileWriter.append(csvReader.NEW_LINE_SEPARATOR);


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
        ArrayList<House> list = getFileCsvToList();
        House house =new House();

        System.out.println("Nh???p id ");
        String id = sc.next();
        house.setId(id);
        while(!regexMatches.checkPattern(id, CHECK_ID_HOUSE)){
            System.out.println("Id c?? ?????nh d???ng l?? 'SVHO-XXXX'");
            System.out.println("Nh???p l???i Id: ");
            id = sc.next();
            if(regexMatches.checkPattern(id, CHECK_ID_HOUSE)){
                house.setId(id);
                break;
            }
        }

        System.out.println("Nh???p serviceName ");
        String serviceName = sc.next();
        house.setServiceName(serviceName);
        while(!regexMatches.checkPattern(serviceName, CHECK_NAME)){
            System.out.println("Name vi???t hoa k?? t??? ?????u, c??c k?? t??? sau l?? k?? t??? b??nh th?????ng");
            System.out.println("Nh???p l???i serviceName: ");
            serviceName = sc.next();
            if(regexMatches.checkPattern(serviceName, CHECK_NAME)){
                house.setServiceName(serviceName);
                break;
            }
        }

        System.out.println("Nh???p acreage ");
        Double acreage = sc.nextDouble();
        house.setAcreage(acreage);
        while(acreage < 30){
            System.out.println("Di???n t??ch ph???i l???n h??n 30m2");
            System.out.println("Nh???p l???i acreage: ");
            acreage = sc.nextDouble();
            if(acreage >= 30){
                house.setAcreage(acreage);
                break;
            }
        }

        System.out.println("Nh???p rentalCost ");
        Double rentalCost = sc.nextDouble();
        house.setRentalCost(rentalCost);
        while(rentalCost <= 0){
            System.out.println("Chi ph?? thu?? ph???i l???n h??n 0");
            System.out.println("Nh???p l???i rentalCost: ");
            rentalCost = sc.nextDouble();
            if(rentalCost > 0){
                house.setRentalCost(rentalCost);
                break;
            }
        }

        System.out.println("Nh???p maxNumOfPeople ");
        int maxNumOfPeople = sc.nextInt();
        house.setMaxNumOfPeople(maxNumOfPeople);
        while(maxNumOfPeople <= 0 || maxNumOfPeople >=20){
            System.out.println("S??? l?????ng ng?????i t???i ??a ph???i >0 v?? nh??? h??n <20");
            System.out.println("Nh???p l???i maxNumOfPeople: ");
            maxNumOfPeople = sc.nextInt();
            if(maxNumOfPeople > 0 && maxNumOfPeople <20){
                house.setMaxNumOfPeople(maxNumOfPeople);
                break;
            }
        }

        System.out.println("Nh???p rentalType ");
        String rentalType = sc.next();
        house.setRentalType(rentalType);
        while(!regexMatches.checkPattern(rentalType, CHECK_RENT_TYPE)){
            System.out.println("RentalType c?? ?????nh d???ng DAY | MONTH | YEAR | HOUR");
            System.out.println("Nh???p l???i rentalType: ");
            rentalType = sc.next();
            if(regexMatches.checkPattern(rentalType, CHECK_RENT_TYPE)){
                house.setRentalType(rentalType);
                break;
            }
        }

        System.out.println("Nh???p roomType ");
        house.setRoomType(sc.next());
        System.out.println("Nh???p description ");
        house.setDescription(sc.next());


        System.out.println("Nh???p floors ");
        int floors = sc.nextInt();
        house.setFloors(floors);
        while(floors <= 0){
            System.out.println("S??? t???ng ph???i l?? s??? nguy??n d????ng");
            System.out.println("Nh???p l???i floors: ");
            floors = sc.nextInt();
            if(floors > 0){
                house.setFloors(floors);
                break;
            }
        }

        list.add(house);
        writeListToCsv(list);
        System.out.println("Complete");
    }

    @Override
    public void showAll() {
        ArrayList<House> listRoom = getFileCsvToList();
        for(House house : listRoom){
            System.out.println("-------------------");
            house.showInfor();
        }
    }
}
