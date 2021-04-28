package utils;

import java.sql.*;

public class JdbcHelper {
    static String driver = "com.mysql.cj.jdbc.Driver";
    static String url = "jdbc:mysql://localhost:3306/furama_module2";
    static String user = "root";
    static String password = "12345678";
    static Connection connection ;

    public JdbcHelper(){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException| SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        JdbcHelper.connection = connection;
    }


    public static PreparedStatement getStatement(String sql, Object... args){
        Connection conn = getConnection();
        PreparedStatement stmt = null;
        try {
            if(sql.trim().startsWith("{")){
                stmt = conn.prepareCall(sql); //Procedure
            }else{
                stmt = conn.prepareStatement(sql); //SQL
            }
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i+1, args[i]);
            }
            return stmt;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    };

    /**
     * * Thực hiện câu lệnh SQL thao tác (INSERT, UPDATE, DELETE) hoặc thủ tục
     * lưu thao tác dữ liệu * @param sql là câu lệnh SQL chứa có thể chứa tham
     * số. Nó có thể là một lời gọi thủ tục lưu * @param args là danh sách các
     * giá trị được cung cấp cho các tham số trong câu lệnh sql *
     */
    public static void update(String sql, Object... args){
        PreparedStatement stmt = null;
        try {
            stmt = getStatement(sql, args);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
//        }finally{
//            try {
//                stmt.getConnection().close();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
    };

    /**
     * * Thực hiện câu lệnh SQL truy vấn (SELECT) hoặc thủ tục lưu truy vấn dữ
     * liệu * @param sql là câu lệnh SQL chứa có thể chứa tham số. Nó có thể là
     * một lời gọi thủ tục lưu * @param args là danh sách các giá trị được cung
     * cấp cho các tham số trong câu lệnh sql
     */
    public static ResultSet query(String sql, Object... args) {
        PreparedStatement stmt = null;
        try {
            stmt = getStatement(sql, args);
            return stmt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
//        finally {
//            try {
//                stmt.getConnection().close();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
    };
}
