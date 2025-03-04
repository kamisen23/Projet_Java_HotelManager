package com.hotel.dao;

import com.hotel.model.Employee;
import com.hotel.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    
    public void add(Employee employee) throws SQLException {
        String sql = "INSERT INTO employees (first_name, last_name, email, phone, position, salary, hire_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPhone());
            pstmt.setString(5, employee.getPosition());
            pstmt.setDouble(6, employee.getSalary());
            pstmt.setDate(7, Date.valueOf(employee.getHireDate() != null ? employee.getHireDate() : LocalDate.now()));
            
            pstmt.executeUpdate();
        }
    }

    public void update(Employee employee) throws SQLException {
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, phone = ?, " +
                    "position = ?, salary = ?, hire_date = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPhone());
            pstmt.setString(5, employee.getPosition());
            pstmt.setDouble(6, employee.getSalary());
            pstmt.setDate(7, Date.valueOf(employee.getHireDate()));
            pstmt.setInt(8, employee.getId());
            
            pstmt.executeUpdate();
        }
    }

    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void delete(Employee employee) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, employee.getId());
            pstmt.executeUpdate();
        }
    }

    public Employee getById(int id) throws SQLException {
        String sql = "SELECT * FROM employees WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractEmployeeFromResultSet(rs);
            }
        }
        return null;
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY last_name, first_name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                employees.add(extractEmployeeFromResultSet(rs));
            }
        }
        return employees;
    }

    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY last_name, first_name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setPosition(rs.getString("position"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setHireDate(rs.getDate("hire_date").toLocalDate());
                employees.add(employee);
            }
        }
        return employees;
    }

    public int getTotalEmployees() throws SQLException {
        String sql = "SELECT COUNT(*) FROM employees";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public double getTotalSalary() throws SQLException {
        String sql = "SELECT NVL(SUM(salary), 0) FROM employees";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0.0;
        }
    }

    private Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setEmail(rs.getString("email"));
        employee.setPhone(rs.getString("phone"));
        employee.setPosition(rs.getString("position"));
        employee.setSalary(rs.getDouble("salary"));
        employee.setHireDate(rs.getDate("hire_date").toLocalDate());
        return employee;
    }
}
