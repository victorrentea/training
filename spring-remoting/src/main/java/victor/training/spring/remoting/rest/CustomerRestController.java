package victor.training.spring.remoting.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import victor.training.spring.remoting.dummy.Customer;
import victor.training.spring.remoting.dummy.CustomerSearchCriteria;
import victor.training.spring.remoting.dummy.CustomerServiceDummy;

@RestController
@RequestMapping(value = "rest/customers", 
	consumes = {"application/xml", "application/json"}, 
	produces = {"application/xml", "application/json"})
public class CustomerRestController {

	@Autowired
	private CustomerServiceDummy service;
	
	@Autowired
	private CustomerDtoMapper mapper;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Long> createCustomer(@RequestBody CustomerDto dto, UriComponentsBuilder uriBuilder) {
		Customer customer = mapper.toDomain(dto);
		Long id = service.createCustomer(customer);
		
		System.out.println("Customer dto name: " + dto.name);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriBuilder.path("/{id}").buildAndExpand(id).toUri());
		return new ResponseEntity<Long>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET, consumes="*/*")
	public CustomerDto getCustomerDto(@PathVariable("id") Long id) {
		Customer customer = service.findCustomer(id);
		return mapper.toDto(customer);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public CustomerDto updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDto dto) {
		Customer customer = mapper.toDomain(dto);
		Customer updatedCustomer = service.update(id, customer);
		return mapper.toDto(updatedCustomer);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, consumes="*/*")
	public CustomerListDto searchCustomers(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "email", required = false) String email
			) {
		CustomerSearchCriteria criteria = new CustomerSearchCriteria();
		criteria.setEmail(email);
		criteria.setPhone(phone);
		criteria.setName(name);
		
		List<Customer> customers = service.searchCustomers(criteria);
		return mapper.toDtos(customers);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE, consumes="*/*")
	public void deleteCustomer(@PathVariable("id") Long id) {
		service.deleteCustomer(id);
	}
}
