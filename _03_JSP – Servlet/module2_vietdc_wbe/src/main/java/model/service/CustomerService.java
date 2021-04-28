package model.service;

import model.bean.Customer;
import model.dao.CustomerDao;

import java.util.List;

public class CustomerService implements IService<Customer, Integer>{
    private CustomerDao customerDao = new CustomerDao();

    @Override
    public void insert(Customer entity) {
        customerDao.insert(entity);
    }

    @Override
    public void update(Customer entity) {
        customerDao.update(entity);
    }

    @Override
    public void delete(Integer id) {
        customerDao.delete(id);
    }

    @Override
    public Customer selectById(Integer id) {
        return customerDao.selectById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

}
