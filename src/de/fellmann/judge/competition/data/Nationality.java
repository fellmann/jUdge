
package de.fellmann.judge.competition.data;

import de.fellmann.common.CountryCodes;

public class Nationality extends DataObject
{
	private String countryCode;
	private String countryString;

	public Nationality() {
		
	}

	public Nationality(String code)
	{
		final String cc = CountryCodes.getInstance().find(code);
		if (cc != null)
		{
			this.setCountryCode(code.toUpperCase());
			this.setCountryString(cc.toLowerCase());
		}
		else
		{
			throw new RuntimeException("Country not found");
		}
	}

	public String getCountryCode()
	{
		return countryCode;
	}

	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}

	public String getCountryString()
	{
		return countryString;
	}

	public void setCountryString(String countryString)
	{
		this.countryString = countryString;
	}
}
