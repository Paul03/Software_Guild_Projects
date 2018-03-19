package com.thesoftwareguild.addressbookweb.dao;

import com.thesoftwareguild.addressbookweb.dto.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AddressDaoDbImpl implements AddressDao {

    private JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_ADDRESS = "INSERT INTO Address (first_name, last_name, street_address, city, state, zip_code) VALUES(?, ?, ?, ?, ?, ?);";
    private static final String SQL_SELECT_ADDRESS = "SELECT * FROM Address WHERE id=?;";
    private static final String SQL_UPDATE_ADDRESS = "UPDATE Address SET first_name=?, last_name=?, street_address=?, city=?, state=?, zip_code=? WHERE id=?;";
    private static final String SQL_DELETE_ADDRESS = "DELETE FROM Address WHERE id=?;";
    private static final String SQL_SELECT_ALL_ADDRESS = "SELECT * FROM Address";
    private static final String SQL_SELECT_LAST_NAME = "SELECT * FROM Address WHERE last_name LIKE ?;";
    private static final String SQL_SELECT_CITY = "SELECT * FROM Address WHERE city LIKE ?;";
    private static final String SQL_SELECT_STATE = "SELECT * FROM Address WHERE state LIKE ?;";
    private static final String SQL_SELECT_ZIP_CODE = "SELECT * FROM Address WHERE zip_code LIKE ?;";

    public AddressDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Address create(Address address) {

        jdbcTemplate.update(SQL_INSERT_ADDRESS,
                address.getFirstName(),
                address.getLastName(),
                address.getStreetAddress(),
                address.getCity(),
                address.getState(),
                address.getZipCode());

        Integer newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);

        address.setId(newId);

        return address;

    }

    @Override
    public Address read(Integer id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_ADDRESS, new AddressMapper(), id);
    }

    @Override
    public void update(Address address) {

        jdbcTemplate.update(SQL_UPDATE_ADDRESS,
                address.getFirstName(),
                address.getLastName(),
                address.getStreetAddress(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getId());

    }

    @Override
    public void delete(Address address) {
        jdbcTemplate.update(SQL_DELETE_ADDRESS, address.getId());
    }

    @Override
    public List<Address> listAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ADDRESS, new AddressMapper());
    }

    @Override
    public List<Address> findByLastName(String lastName) {
        return jdbcTemplate.query(SQL_SELECT_LAST_NAME, new AddressMapper(), lastName);
    }

    @Override
    public List<Address> findByCity(String cityName) {
        return jdbcTemplate.query(SQL_SELECT_CITY, new AddressMapper(), cityName);
    }

    @Override
    public List<Address> findByState(String stateName) {
        return jdbcTemplate.query(SQL_SELECT_STATE, new AddressMapper(), stateName);
    }

    @Override
    public List<Address> findByZip(String zipCode) {
        return jdbcTemplate.query(SQL_SELECT_ZIP_CODE, new AddressMapper(), zipCode);
    }

    private final class AddressMapper implements RowMapper<Address> {

        @Override
        public Address mapRow(ResultSet resultSet, int i) throws SQLException {

            Address address = new Address();

            address.setId(resultSet.getInt("id"));
            address.setFirstName(resultSet.getString("first_name"));
            address.setLastName(resultSet.getString("last_name"));
            address.setStreetAddress(resultSet.getString("street_address"));
            address.setCity(resultSet.getString("city"));
            address.setState(resultSet.getString("state"));
            address.setZipCode(resultSet.getString("zip_code"));
            

            return address;

        }
    }

}