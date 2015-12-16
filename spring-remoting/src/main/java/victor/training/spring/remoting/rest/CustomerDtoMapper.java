package victor.training.spring.remoting.rest;

import java.util.List;

import org.springframework.stereotype.Component;

import victor.training.spring.remoting.dummy.Customer;

@Component
public class CustomerDtoMapper {

	public Customer toDomain(CustomerDto dto) {
		Customer entity = new Customer();
		entity.setName(dto.name);
		entity.setPhone(dto.phone);
		entity.setEmail(dto.email);
		return entity;
	}

	public CustomerDto toDto(Customer entity) {
		CustomerDto dto = new CustomerDto();
		dto.id = entity.getId();
		dto.name = entity.getName();
		dto.phone = entity.getPhone();
		dto.email = entity.getEmail();
		return dto;
	}

	public CustomerListDto toDtos(List<Customer> entities) {
		CustomerListDto listDto = new CustomerListDto();
		for (Customer entity : entities) {
			listDto.customer.add(toDto(entity));
		}
		return listDto;
	}

}
