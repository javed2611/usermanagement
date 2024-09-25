package com.jk.service;

import java.util.List;

import com.jk.entity.Employee;

public interface EmployeeService {
	public List<Employee> getAllEmployee();

	public boolean addEmployee(Employee e);

	public void deleteEmployee(Integer empId);

	public Employee getEmployeeById(Integer empId);
}
