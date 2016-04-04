package co.com.binariasystems.mastercentral.web.utils;

import org.apache.commons.lang3.StringUtils;

import co.com.binariasystems.commonsmodel.enumerated.PersonType;
import co.com.binariasystems.mastercentral.shared.business.dto.ThirdPersonDTO;

import com.vaadin.data.Item;
import com.vaadin.data.util.PropertyValueGenerator;

public class ThirdPersonBusinessNameColumnGenerator extends PropertyValueGenerator<String> {

	@Override
	public String getValue(Item item, Object itemId, Object propertyId) {
		if(propertyId == null) return null;
		ThirdPersonDTO thirdPerson = (ThirdPersonDTO)itemId;
		return PersonType.PERSONA_JURIDICA.equals(thirdPerson.getPersonType()) ?
				thirdPerson.getBusinessName() :
					generateCompleteName(thirdPerson);
	}
	
	private String generateCompleteName(ThirdPersonDTO thirdPerson){
		String firstName = StringUtils.defaultString(thirdPerson.getFirstName());
		String middleName = StringUtils.defaultString(thirdPerson.getMiddleName());
		String lastName = StringUtils.defaultString(thirdPerson.getLastName());
		String secondLastName = StringUtils.defaultString(thirdPerson.getSecondLasName());
		
		return new StringBuilder()
		.append(firstName)
		.append(middleName.isEmpty() ? "" : " ").append(middleName)
		.append(lastName.isEmpty() ? "" : " ").append(lastName)
		.append(secondLastName.isEmpty() ? "" : " ").append(secondLastName)
		.toString();
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

}
