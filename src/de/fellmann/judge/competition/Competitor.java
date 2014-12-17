
package de.fellmann.judge.competition;

import java.util.UUID;

import de.fellmann.common.Nationality;

public class Competitor
{
	private final String uuid = UUID.randomUUID().toString();

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Competitor other = (Competitor) obj;
		if (uuid == null)
		{
			if (other.uuid != null)
			{
				return false;
			}
		}
		else if (!uuid.equals(other.uuid))
		{
			return false;
		}
		return true;
	}

	private String verein, name = "A";
	private String anzeigename = null;
	private int startnummer;
	private boolean team;
	private Nationality nationality;

	@Override
	public String toString()
	{
		return getName();
	}

	public String getAnzeigename()
	{
		if (anzeigename != null && anzeigename.length() > 0)
		{
			return anzeigename;
		}
		else
		{
			return getName();
		}
	}

	public void setAnzeigename(String name)
	{
		anzeigename = name;
	}

	public boolean isTeam()
	{
		return team;
	}

	public void setTeam(boolean team)
	{
		this.team = team;
	}

	public String getVerein()
	{
		return verein;
	}

	public void setVerein(String verein)
	{
		this.verein = verein;
	}

	public String getEndergebnisName()
	{
		if (team)
		{
			return getAnzeigename();
		}
		else
		{
			return name + "\n" + verein;
		}
	}

	public String getName()
	{
		String result = "";
		if (team)
		{
			result = verein;
			if (name.length() > 0)
			{
				result += " " + name;
			}
			if (nationality != null)
			{
				result += " (" + nationality.getCountryCode() + ")";
			}
		}
		else
		{
			result = name;
		}
		return result;
	}

	public String getNameValue()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getStartnummer()
	{
		return startnummer;
	}

	public void setStartnummer(int startnummer)
	{
		this.startnummer = startnummer;
	}

	public Competitor(int startnummer, String verein, String name,
			boolean isTeam)
	{
		setVerein(verein);
		setName(name);
		setStartnummer(startnummer);
		setTeam(isTeam);
	}

	public Nationality getNationality()
	{
		return nationality;
	}

	public void setNationality(Nationality nationality)
	{
		this.nationality = nationality;
	}
}
