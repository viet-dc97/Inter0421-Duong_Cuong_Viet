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

        System.out.println("Nhập id ");
        String id = sc.next();
        house.setId(id);
        while(!regexMatches.checkPattern(id, CHECK_ID_HOUSE)){
            System.out.println("Id có định dạng là 'SVHO-XXXX'");
            System.out.println("Nhập lại Id: ");
            id = sc.next();
            if(regexMatches.checkPattern(id, CHECK_ID_HOUSE)){
                house.setId(id);
                break;
            }
        }

        System.out.println("Nhập serviceName ");
        String serviceName = sc.next();
        house.setServiceName(serviceName);
        while(!regexMatches.checkPattern(serviceName, CHECK_NAME)){
            System.out.println("Name viết hoa ký tự đầu, các ký tự sau là ký tự bình thường");
            System.out.println("Nhập lại serviceName: ");
            serviceName = sc.next();
            if(regexMatches.checkPattern(serviceName, CHECK_NAME)){
                house.setServiceName(serviceName);
                break;
            }
        }

        System.out.println("Nhập acreage ");
        Double acreage = sc.nextDouble();
        house.setAcreage(acreage);
        while(acreage < 30){
            System.out.println("Diện tích phải lớn hơn 30m2");
            System.out.println("Nhập lại acreage: ");
            acreage = sc.nextDouble();
            if(acreage >= 30){
                house.setAcreage(acreage);
                break;
            }
        }

        System.out.println("Nhập rentalCost ");
        Double rentalCost = sc.nextDouble();
        house.setRentalCost(rentalCost);
        while(rentalCost <= 0){
            System.out.println("Chi phí thuê phải lớn hơn 0");
            System.out.println("Nhập lại rentalCost: ");
            rentalCost = sc.nextDouble();
            if(rentalCost > 0){
                house.setRentalCost(rentalCost);
                break;
            }
        }

        System.out.println("Nhập maxNumOfPeople ");
        int maxNumOfPeople = sc.nextInt();
        house.setMaxNumOfPeople(maxNumOfPeople);
        while(maxNumOfPeople <= 0 || maxNumOfPeople >=20){
            System.out.println("Số lượng người tối đa phải >0 và nhỏ hơn <20");
            System.out.println("Nhập lại maxNumOfPeople: ");
            maxNumOfPeople = sc.nextInt();
            if(maxNumOfPeople > 0 && maxNumOfPeople <20){
                house.setMaxNumOfPeople(maxNumOfPeople);
                break;
            }
        }

        System.out.println("Nhập rentalType ");
        String rentalType = sc.next();
        house.setRentalType(rentalType);
        while(!regexMatches.checkPattern(rentalType, CHECK_RENT_TYPE)){
            System.out.println("RentalType có định dạng DAY | MONTH | YEAR | HOUR");
            System.out.println("Nhập lại rentalType: ");
            rentalType = sc.next();
            if(regexMatches.checkPattern(rentalType, CHECK_RENT_TYPE)){
                house.setRentalType(rentalType);
                break;
            }
        }

        System.out.println("Nhập roomType ");
        house.setRoomType(sc.next());
        System.out.println("Nhập description ");
        house.setDescription(sc.next());


        System.out.println("Nhập floors ");
        int floors = sc.nextInt();
        house.setFloors(floors);
        while(floors <= 0){
            System.out.println("Số tầng phải là số nguyên dương");
            System.out.println("Nhập lại floors: ");
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
