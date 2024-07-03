package com.employee;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private KafkaTemplate<String,EmployeeEvent> kafkaTemplate;

	@Override
	public Employee saveEmployee(Employee emp) {
		Employee savedEmployee = employeeRepository.save(emp);
		kafkaTemplate.send("Employee-Topic",new EmployeeEvent("employee-added", savedEmployee));
		return savedEmployee;
	}

	@Override
	public Employee getEmployee(Integer empId)  {
		Optional<Employee> findById = employeeRepository.findById(empId);
		if(findById.isPresent()) {
		return findById.get();
		}
		return null;
	}
	
	

}
