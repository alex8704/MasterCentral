package co.com.binariasystems.mastercentral.web.utils;

import co.com.binariasystems.mastercentral.shared.business.dto.ThirdPersonDTO;

import com.vaadin.data.Item;
import com.vaadin.data.util.PropertyValueGenerator;

public class ThirdPersonIdentificationColumnGenerator extends PropertyValueGenerator<String> {

	@Override
	public String getValue(Item item, Object itemId, Object propertyId) {
		if(propertyId == null) return null;
		ThirdPersonDTO thirdPerson = (ThirdPersonDTO)itemId;
		return new StringBuilder()
		.append(thirdPerson.getIdentificationType().getShortName())
		.append(" - ")
		.append(thirdPerson.getIdentificationNumber())
		.toString();
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

}
