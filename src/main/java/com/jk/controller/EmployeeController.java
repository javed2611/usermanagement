package com.jk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jk.entity.Employee;
import com.jk.service.EmailService;
import com.jk.service.EmployeeService;

import jakarta.mail.MessagingException;

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
		Employee e = new Employee();
		mav.addObject("emp", e);
		mav.setViewName("index");
		return mav;
	}

	@PostMapping("/addemp")
	public String saveEmployee(Employee e, Model model) {
		boolean employee = employeeService.addEmployee(e);
		if (employee) {
			model.addAttribute("smsg", "Added");
			model.addAttribute("emp", e);
		} else {
			model.addAttribute("emsg", "Not added");
		}
		return "redirect:data";
	}

	@GetMapping("/edit")
	public ModelAndView editEmployee(@RequestParam("eid") Integer eid) {
		ModelAndView mav = new ModelAndView();
		Employee e = employeeService.getEmployeeById(eid);
		mav.addObject("emp", e);
		mav.setViewName("index");
		return mav;
	}

	@GetMapping("/sendemail")
	public ModelAndView sendEmail(@RequestParam("email") String email, @RequestParam("name") String name)
			throws MessagingException {
		String subject = "Test mail";
		Map<String, Object> templateModel = new HashMap<>();
		templateModel.put("name", name);
		emailService.sendTemplateMail(email, subject, templateModel);
		ModelAndView mav = new ModelAndView();
		List<Employee> employee = employeeService.getAllEmployee();
		mav.addObject("emp", employee);
		mav.setViewName("data");
		return mav;
	}

	@PostMapping("/updateemp")
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
