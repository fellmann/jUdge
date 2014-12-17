
package de.fellmann.common;

public class Nationality
{
	private final String countryCode;
	private final String countryString;

	public Nationality(String code)
	{
		final String cc = CountryCodes.getInstance().find(code);
		if (cc != null)
		{
			this.countryCode = code.toUpperCase();
			this.countryString = cc.toLowerCase();
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

	public String getCountryString()
	{
		return countryString;
	}
}
