package com.jk.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jk.entity.Employee;
import com.jk.service.EmailService;
import com.jk.service.EmployeeService;

@Controller
public class EmployeeController {

	private EmployeeService employeeService;
	private EmailService emailService;

	public EmployeeController(EmployeeService employeeService, EmailService emailService) {
		this.employeeService = employeeService;
		this.emailService = emailService;
	}

	@GetMapping("/")
	public ModelAndView employee() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}

	@GetMapping("/addemp")
	public ModelAndView saveEmployee(Employee e) {
		ModelAndView mav = new ModelAndView();
		boolean employee = employeeService.addEmployee(e);
		if (employee) {
			mav.addObject("smsg", "Added");
		} else {
			mav.addObject("emsg", "Not added");
		}
		mav.setViewName("index");
		return mav;
	}

	@GetMapping("/edit")
	public ModelAndView editEmployee(@RequestParam("eid") Integer eid) {
		ModelAndView mav = new ModelAndView();
		Employee e = employeeService.getEmployeeById(eid);

		if (e != null) {
			mav.addObject("emp", e);
		} else {
			mav.addObject("emsg", "Employee not found");
		}
		mav.setViewName("edit");
		return mav;
	}
	@GetMapping("/sendemail")
	public ModelAndView sendEmail(@RequestParam("email") String email) {
		String subject = "Test mail";
		String body = "This is test mail";
		emailService.sendMail(email, subject, body);
		ModelAndView mav = new ModelAndView();
		List<Employee> employee = employeeService.getAllEmployee();
		mav.addObject("emp", employee);
		mav.setViewName("data");
		return mav;
	}

	@GetMapping("/updateemp")
	public ModelAndView updateEmployee(Employee e) {
		ModelAndView mav = new ModelAndView();
		boolean updated = employeeService.addEmployee(e);
		if (updated) {
			mav.addObject("smsg", "Updated successfully");
		} else {
			mav.addObject("emsg", "Update failed");
		}
		mav.setViewName("index");
		return mav;
	}

	@GetMapping("/data")
	public ModelAndView displayEmployees() {
		ModelAndView mav = new ModelAndView();
		List<Employee> employee = employeeService.getAllEmployee();
		mav.addObject("emp", employee);
		mav.setViewName("data");
		return mav;
	}

	@GetMapping("/delete")
	public ModelAndView removeEmployees(@RequestParam("eid") Integer empId) {
		employeeService.deleteEmployee(empId);
		ModelAndView mav = new ModelAndView();
		List<Employee> employee = employeeService.getAllEmployee();
		mav.addObject("emp", employee);
		mav.setViewName("data");
		return mav;
	}
}
