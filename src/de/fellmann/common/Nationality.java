
package de.fellmann.common;

import de.fellmann.judge.competition.data.DataObject;

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
