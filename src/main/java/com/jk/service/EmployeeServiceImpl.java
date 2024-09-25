package com.jk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jk.entity.Employee;
import com.jk.repo.EmployeeRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private EmployeeRepo employeeRepo;

	public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	@Override
	public List<Employee> getAllEmployee() {
		return employeeRepo.findAll();
	}

	@Override
	public boolean addEmployee(Employee e) {
		Employee save = employeeRepo.save(e);
		return save.getFirstName() != null;
	}

	@Override
	public void deleteEmployee(Integer empId) {
		employeeRepo.deleteById(empId);
	}

	@Override
	public Employee getEmployeeById(Integer empId) {
		return employeeRepo.findById(empId).orElse(null);
	}
}
