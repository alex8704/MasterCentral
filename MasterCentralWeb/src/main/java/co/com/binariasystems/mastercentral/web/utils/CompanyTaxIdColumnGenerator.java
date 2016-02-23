package co.com.binariasystems.mastercentral.web.utils;

import co.com.binariasystems.mastercentral.shared.business.dto.CompanyDTO;

import com.vaadin.data.Item;
import com.vaadin.data.util.PropertyValueGenerator;

public class CompanyTaxIdColumnGenerator extends PropertyValueGenerator<String> {

	@Override
	public String getValue(Item item, Object itemId, Object propertyId) {
		if(propertyId == null) return null;
		CompanyDTO company = (CompanyDTO)itemId;
		return new StringBuilder()
		.append(company.getTaxIdentification())
		.append(" - ")
		.append(company.getCheckDigit())
		.toString();
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

}
