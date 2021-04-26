package CSV;

import Commons.RegexMatches;
import Model.Villa;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class VillaCsv extends CsvCommon<Villa>{
    RegexMatches regexMatches = new RegexMatches();
    public static final String FILE_HEADER = "id,serviceName,acreage,rentalCost,maxNumOfPeople,rentalType,roomType,description,pollArea,floors";
    public static final String file_room = "src/Data/villa.csv";
    static Scanner sc = new Scanner(System.in);
    private static final String CHECK_ID_VILLA = "^SV+[VL]+[-]\\d{4}$";
    private static final String CHECK_NAME = "^[A-Z][a-z]{0,}+$";
    private static final String CHECK_RENT_TYPE = "(DAY)|(MONTH)|(YEAR)|(HOUR)";

    @Override
    public ArrayList<Villa> getFileCsvToList() {
        String line = "";
        ArrayList<Villa> listVilla = new ArrayList<>();
        BufferedReader br = null;
        try {

            br = new BufferedReader(new FileReader(file_room));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals("id")) {
                    continue;
                }
                Villa villa = new Villa();
                villa.setId(values[0]);
                villa.setServiceName(values[1]);
                villa.setAcreage(Double.parseDouble(values[2]));
                villa.setRentalCost(Double.parseDouble(values[3]));
                villa.setMaxNumOfPeople(Integer.parseInt(values[4]));
                villa.setRentalType(values[5]);
                villa.setRoomType(values[6]);
                villa.setDescription(values[7]);
                villa.setPollArea(Integer.parseInt(values[8]));
                villa.setFloors(Integer.parseInt(values[9]));
                listVilla.add(villa);

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
        return listVilla;
    }

    @Override
    public void writeListToCsv(ArrayList<Villa> list) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file_room);
            fileWriter.append(FILE_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);
            for(Villa villa : list){
                fileWriter.append(villa.getId());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(villa.getServiceName());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(villa.getAcreage()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(villa.getRentalCost()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(villa.getMaxNumOfPeople()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(villa.getRentalType());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(villa.getRoomType());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(villa.getDescription());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(villa.getPollArea()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(villa.getFloors()));

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
        ArrayList<Villa> list = getFileCsvToList();
        Villa villa =new Villa();

        System.out.println("Nhập id ");
        String id = sc.next();
        villa.setId(id);
        while(!regexMatches.checkPattern(id, CHECK_ID_VILLA)){
            System.out.println("Id có định dạng là 'SVVL-XXXX'");
            System.out.println("Nhập lại Id: ");
            id = sc.next();
            if(regexMatches.checkPattern(id, CHECK_ID_VILLA)){
                villa.setId(id);
                break;
            }
        }

        System.out.println("Nhập serviceName ");
        String serviceName = sc.next();
        villa.setServiceName(serviceName);
        while(!regexMatches.checkPattern(serviceName, CHECK_NAME)){
            System.out.println("Name viết hoa ký tự đầu, các ký tự sau là ký tự bình thường");
            System.out.println("Nhập lại serviceName: ");
            serviceName = sc.next();
            if(regexMatches.checkPattern(serviceName, CHECK_NAME)){
                villa.setServiceName(serviceName);
                break;
            }
        }

        System.out.println("Nhập acreage ");
        Double acreage = sc.nextDouble();
        villa.setAcreage(acreage);
        while(acreage < 30){
            System.out.println("Diện tích phải lớn hơn 30m2");
            System.out.println("Nhập lại acreage: ");
            acreage = sc.nextDouble();
            if(acreage >= 30){
                villa.setAcreage(acreage);
                break;
            }
        }

        System.out.println("Nhập rentalCost ");
        Double rentalCost = sc.nextDouble();
        villa.setRentalCost(rentalCost);
        while(rentalCost <= 0){
            System.out.println("Chi phí thuê phải lớn hơn 0");
            System.out.println("Nhập lại rentalCost: ");
            rentalCost = sc.nextDouble();
            if(rentalCost > 0){
                villa.setRentalCost(rentalCost);
                break;
            }
        }

        System.out.println("Nhập maxNumOfPeople ");
        int maxNumOfPeople = sc.nextInt();
        villa.setMaxNumOfPeople(maxNumOfPeople);
        while(maxNumOfPeople <= 0 || maxNumOfPeople >=20){
            System.out.println("Số lượng người tối đa phải >0 và nhỏ hơn <20");
            System.out.println("Nhập lại maxNumOfPeople: ");
            maxNumOfPeople = sc.nextInt();
            if(maxNumOfPeople > 0 && maxNumOfPeople <20){
                villa.setMaxNumOfPeople(maxNumOfPeople);
                break;
            }
        }

        System.out.println("Nhập rentalType ");
        String rentalType = sc.next();
        villa.setRentalType(rentalType);
        while(!regexMatches.checkPattern(rentalType, CHECK_RENT_TYPE)){
            System.out.println("RentalType có định dạng DAY | MONTH | YEAR | HOUR");
            System.out.println("Nhập lại rentalType: ");
            rentalType = sc.next();
            if(regexMatches.checkPattern(rentalType, CHECK_RENT_TYPE)){
                villa.setRentalType(rentalType);
                break;
            }
        }

        System.out.println("Nhập roomType ");
        villa.setRoomType(sc.next());
        System.out.println("Nhập description ");
        villa.setDescription(sc.next());

        System.out.println("Nhập pollArea ");
        int pollArea = sc.nextInt();
        villa.setPollArea(pollArea);
        while(pollArea < 30){
            System.out.println("Diện tích hồ bơi phải lớn hơn 30m2");
            System.out.println("Nhập lại pollArea: ");
            pollArea = sc.nextInt();
            if(pollArea >= 30){
                villa.setPollArea(pollArea);
                break;
            }
        }

        System.out.println("Nhập floors ");
        int floors = sc.nextInt();
        villa.setFloors(floors);
        while(floors <= 0){
            System.out.println("Số tầng phải là số nguyên dương");
            System.out.println("Nhập lại floors: ");
            floors = sc.nextInt();
            if(floors > 0){
                villa.setFloors(floors);
                break;
            }
        }

        list.add(villa);
        writeListToCsv(list);
        System.out.println("Complete");
    }

    @Override
    public void showAll() {
        ArrayList<Villa> listVilla = getFileCsvToList();
        for(Villa villa : listVilla){
            System.out.println("-------------------");
            villa.showInfor();
        }
        System.out.println("\n");
    }
}
