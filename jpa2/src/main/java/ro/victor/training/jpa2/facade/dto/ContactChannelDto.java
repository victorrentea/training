package ro.victor.training.jpa2.facade.dto;

import ro.victor.training.jpa2.domain.entity.ContactChannel;

public class ContactChannelDto {
	public ContactChannel.Type type;
	public String value;
	
	public ContactChannelDto() {
	}
	public ContactChannelDto(ContactChannel contactChannel) {
		type = contactChannel.getType();
		value = contactChannel.getValue();
	}
}
