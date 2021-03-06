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

        System.out.println("Nh???p id ");
        String id = sc.next();
        villa.setId(id);
        while(!regexMatches.checkPattern(id, CHECK_ID_VILLA)){
            System.out.println("Id c?? ?????nh d???ng l?? 'SVVL-XXXX'");
            System.out.println("Nh???p l???i Id: ");
            id = sc.next();
            if(regexMatches.checkPattern(id, CHECK_ID_VILLA)){
                villa.setId(id);
                break;
            }
        }

        System.out.println("Nh???p serviceName ");
        String serviceName = sc.next();
        villa.setServiceName(serviceName);
        while(!regexMatches.checkPattern(serviceName, CHECK_NAME)){
            System.out.println("Name vi???t hoa k?? t??? ?????u, c??c k?? t??? sau l?? k?? t??? b??nh th?????ng");
            System.out.println("Nh???p l???i serviceName: ");
            serviceName = sc.next();
            if(regexMatches.checkPattern(serviceName, CHECK_NAME)){
                villa.setServiceName(serviceName);
                break;
            }
        }

        System.out.println("Nh???p acreage ");
        Double acreage = sc.nextDouble();
        villa.setAcreage(acreage);
        while(acreage < 30){
            System.out.println("Di???n t??ch ph???i l???n h??n 30m2");
            System.out.println("Nh???p l???i acreage: ");
            acreage = sc.nextDouble();
            if(acreage >= 30){
                villa.setAcreage(acreage);
                break;
            }
        }

        System.out.println("Nh???p rentalCost ");
        Double rentalCost = sc.nextDouble();
        villa.setRentalCost(rentalCost);
        while(rentalCost <= 0){
            System.out.println("Chi ph?? thu?? ph???i l???n h??n 0");
            System.out.println("Nh???p l???i rentalCost: ");
            rentalCost = sc.nextDouble();
            if(rentalCost > 0){
                villa.setRentalCost(rentalCost);
                break;
            }
        }

        System.out.println("Nh???p maxNumOfPeople ");
        int maxNumOfPeople = sc.nextInt();
        villa.setMaxNumOfPeople(maxNumOfPeople);
        while(maxNumOfPeople <= 0 || maxNumOfPeople >=20){
            System.out.println("S??? l?????ng ng?????i t???i ??a ph???i >0 v?? nh??? h??n <20");
            System.out.println("Nh???p l???i maxNumOfPeople: ");
            maxNumOfPeople = sc.nextInt();
            if(maxNumOfPeople > 0 && maxNumOfPeople <20){
                villa.setMaxNumOfPeople(maxNumOfPeople);
                break;
            }
        }

        System.out.println("Nh???p rentalType ");
        String rentalType = sc.next();
        villa.setRentalType(rentalType);
        while(!regexMatches.checkPattern(rentalType, CHECK_RENT_TYPE)){
            System.out.println("RentalType c?? ?????nh d???ng DAY | MONTH | YEAR | HOUR");
            System.out.println("Nh???p l???i rentalType: ");
            rentalType = sc.next();
            if(regexMatches.checkPattern(rentalType, CHECK_RENT_TYPE)){
                villa.setRentalType(rentalType);
                break;
            }
        }

        System.out.println("Nh???p roomType ");
        villa.setRoomType(sc.next());
        System.out.println("Nh???p description ");
        villa.setDescription(sc.next());

        System.out.println("Nh???p pollArea ");
        int pollArea = sc.nextInt();
        villa.setPollArea(pollArea);
        while(pollArea < 30){
            System.out.println("Di???n t??ch h??? b??i ph???i l???n h??n 30m2");
            System.out.println("Nh???p l???i pollArea: ");
            pollArea = sc.nextInt();
            if(pollArea >= 30){
                villa.setPollArea(pollArea);
                break;
            }
        }

        System.out.println("Nh???p floors ");
        int floors = sc.nextInt();
        villa.setFloors(floors);
        while(floors <= 0){
            System.out.println("S??? t???ng ph???i l?? s??? nguy??n d????ng");
            System.out.println("Nh???p l???i floors: ");
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
