package CSV;

import Commons.RegexMatches;
import Model.Custommer;
import Model.House;
import Model.Services;
import Views.csvReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CustommerCsv extends CsvCommon<Custommer> {
    public static final String FILE_HEADER = "name,birthDay,gender,idCard,phone,email,customerType,address,services";
    public static final String file_room = "src/Data/custommer.csv";
    public static final String Email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String idCard_pattern = "[0-9]{9}";
    public static final String gender_pattern = "(male)|(female)|(unknow)";
    public static final String name_pattern = "^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$";
    public static final String birthday_pattern = "[0-9]{2}(/)[0-9]{2}(/)[0-9]{4}";
    static Scanner sc = new Scanner(System.in);
    RegexMatches regexMatches = new RegexMatches();

    @Override
    public ArrayList<Custommer> getFileCsvToList() {
        String line = "";
        ArrayList<Custommer> list = new ArrayList<>();
        BufferedReader br = null;
        try {

            br = new BufferedReader(new FileReader(file_room));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals("name")) {
                    continue;
                }
                Custommer custommer = new Custommer();
                custommer.setName(values[0]);
                custommer.setBirthDay(values[1]);
                custommer.setGender(values[2]);
                custommer.setIdCard(values[3]);
                custommer.setPhone(values[4]);
                custommer.setEmail(values[5]);
                custommer.setCustomerType(values[6]);
                custommer.setAddress(values[7]);
                custommer.setServices(values[8]);
                list.add(custommer);

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
        return list;

    }

    @Override
    public void writeListToCsv(ArrayList<Custommer> list) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file_room);
            fileWriter.append(FILE_HEADER);
            fileWriter.append(csvReader.NEW_LINE_SEPARATOR);
            for(Custommer custommer : list){
                fileWriter.append(custommer.getName());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getBirthDay());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getGender());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getIdCard());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getPhone());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getEmail());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getCustomerType());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getAddress());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getServices());
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
        ArrayList<Custommer> list = getFileCsvToList();
        Custommer custommer =new Custommer();

        System.out.println("Nh???p name ");
        String name = sc.next();
        custommer.setName(name);
        while(!regexMatches.checkPattern(name, name_pattern)){
            System.out.println("T??n c?? ch??? c??i ?????u vi???t hoa");
            System.out.println("Nh???p l???i name: ");
            name = sc.next();
            if(regexMatches.checkPattern(name, name_pattern)){
                custommer.setName(name);
                break;
            }
        }

        System.out.println("Nh???p birthDay ");
        String birthDay = sc.next();
        custommer.setBirthDay(birthDay);
        int year = Integer.parseInt(birthDay.substring(6));
        int yearNow = java.time.LocalDate.now().getYear();
        while(!regexMatches.checkPattern(birthDay, birthday_pattern) || year <= 1900 || year > yearNow-18){
            if(!regexMatches.checkPattern(birthDay, birthday_pattern)){
                System.out.println("Birthday c?? d???nh d???ng dd/MM/yyyy ");
                System.out.println("Nh???p l???i birthDay: ");
                birthDay = sc.next();
                year = Integer.parseInt(birthDay.substring(6));
            }else if(year <= 1900 || year > yearNow -18){
                System.out.println("N??m sinh ph???i >1900 v?? nh??? h??n n??m hi???n t???i 18 n??m");
                System.out.println("Nh???p l???i birthDay: ");
                birthDay = sc.next();
                year = Integer.parseInt(birthDay.substring(6));
            }else{
                custommer.setBirthDay(birthDay);
                break;
            }

        }


        System.out.println("Nh???p gender ");
        String gender = sc.next();
        while (true){
            if(regexMatches.checkPattern(gender.toLowerCase(), gender_pattern)){
                custommer.setGender(gender);
            }else{
                System.out.println("Gender ph???i c?? d???ng male|female|unknow");
                System.out.println("Nh???p l???i gender: ");
                gender = sc.next();
            }
        }

        //        phone,email,customerType,address,services

    }

    @Override
    public void showAll() {
        ArrayList<Custommer> list = getFileCsvToList();
        for(Custommer custommer : list){
            System.out.println("-------------------");
            custommer.showInfor();
        }
        System.out.println("\n");
    }
}
