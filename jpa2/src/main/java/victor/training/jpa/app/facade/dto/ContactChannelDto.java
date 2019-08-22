package victor.training.jpa.app.facade.dto;

import victor.training.jpa.app.domain.entity.ContactChannel;

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
