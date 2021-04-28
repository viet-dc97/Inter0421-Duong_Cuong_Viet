package model.dao;

import model.bean.Customer;
import model.bean.CustomerType;
import utils.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements IDao<Customer,Integer >{

    private CustomerType customerType;

    @Override
    public void insert(Customer entity) {
        String sql = "INSERT INTO `customer` (`customer_type_id`, `customer_name`, `customer_birthday`, `customer_gender`, `customer_id_card`," +
                " `customer_phone`, `customer_email`, `customer_address`)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)" ;
        JdbcHelper.update(sql, entity.getId(), entity.getName(), entity.getBirthday(), entity.isGender(), entity.getIdCard(),
                entity.getPhone(), entity.getEmail(), entity.getAddress());
    }

    @Override
    public void update(Customer entity) {
        String sql = "UPDATE `customer` SET `customer_name` = ?, `customer_birthday` = ?, `customer_gender` = ?," +
                " `customer_id_card` = ?, `customer_phone` = ?, `customer_email` = ?," +
                " `customer_address` = ? WHERE `customer_id` = ? ";
        JdbcHelper.update(sql, entity.getName(), entity.getBirthday(), entity.isGender(), entity.getIdCard(), entity.getPhone(),
                entity.getEmail(), entity.getAddress(), entity.getId());
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM `customer` where `customer_id` = ?";
        JdbcHelper.update(sql, id);
    }

    @Override
    public Customer selectById(Integer id) {
        String sql = "SELECT * FROM `customer` where `customer_id` = ?";
        List<Customer> list = selectBySql(sql, id);
        return list.size()>0 ? list.get(0) : null;
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM `customer`";
        return selectBySql(sql);
    }

    @Override
    public List<Customer> selectBySql(String sqlString, Object... argsObjects) {
        List<Customer> list = new ArrayList<>();

        try {
            ResultSet rs = null;
            rs = utils.JdbcHelper.query(sqlString, argsObjects);
            while (rs.next()) {
                Customer entity = readFromResultSet(rs);
                list.add(entity);
            }
            rs.getStatement().getConnection().close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Customer readFromResultSet(ResultSet rs) {
        Customer entity = new Customer();
        try {
            entity.setId(rs.getInt("customer_id"));
//            customerType.setId(rs.getInt("customer_type_id"));
            entity.setType(rs.getInt("customer_type_id"));
            entity.setName(rs.getString("customer_name"));
            entity.setBirthday(rs.getDate("customer_birthday"));
            entity.setGender(rs.getBoolean("customer_gender"));
            entity.setIdCard(rs.getString("customer_id_card"));
            entity.setPhone(rs.getString("customer_phone"));
            entity.setEmail(rs.getString("customer_email"));
            entity.setAddress(rs.getString("customer_address"));
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return entity;
    }

}
