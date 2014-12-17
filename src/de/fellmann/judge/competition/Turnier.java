
package de.fellmann.judge.competition;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.round.Runde;
import de.fellmann.judge.competition.round.StartRound;

public class Turnier
{
	private Date datum;
	private String ort;
	private String name;
	private List<Competitor> teilnehmer = new ArrayList<Competitor>();
	private Klasse startklasse;
	private final ArrayList<Runde> runden = new ArrayList<Runde>();
	private int activeRound = 0;
	private final List<Judge> judges;
	private final NumberFormat format = DecimalFormat.getInstance();
	private final String myID;
	private final String impString = null;
	Calendar cal = Calendar.getInstance();
	private int m_Changed = 0;

	public void reset()
	{
		runden.clear();
		activeRound = 0;
		runden.clear();
		runden.add(new StartRound(this));
	}

	public Turnier(Date date, String ort, String name,
	        List<Competitor> teilnehmer, Klasse startklasse,
	        List<Judge> wertungsrichter, String id)
	{
		this.datum = date;
		this.ort = ort;
		this.name = name;
		this.startklasse = startklasse;
		this.judges = wertungsrichter;
		this.teilnehmer = teilnehmer;

		cal.setTime(date);
		myID = id;

		reset();
	}

	public String getID()
	{
		return myID;
	}

	public String getDateString()
	{
		return getDate() + "." + (getMonth() + 1) + "." + getYear() % 100;
	}

	public String getDateStringLong()
	{
		return getDate() + "." + (getMonth() + 1) + "." + getYear();
	}

	public void setName(String nname)
	{
		name = nname;
	}

	public String getName()
	{
		return name;
	}

	public StartRound getFirstRound()
	{
		return (StartRound) runden.get(0);
	}

	public Runde getCurrentRunde()
	{
		return runden.get(activeRound);
	}

	public int getWertungsrichter()
	{
		return judges.size();
	}

	public Judge getWertungsrichter(int index)
	{
		return judges.get(index);
	}

	public Place getPlace(int i)
	{
		return runden.get(activeRound).getPlace(teilnehmer.get(i));
	}

	public boolean nextRoundSet()
	{
		return runden.size() > activeRound + 1;
	}

	public Runde goNextRunde()
	{
		if (nextRoundSet())
		{
			activeRound++;
		}
		return getCurrentRunde();
	}

	public void startRunde(Runde runde)
	{
		activeRound++;
		while (activeRound < runden.size())
		{
			runden.remove(runden.size());
		}

		runden.add(runde);
	}

	@Override
	public String toString()
	{
		return getBezeichnung();
	}

	public String getBezeichnung()
	{
		String result = getDateStringLong() + " | " + ort + " | "
				+ startklasse.getTitle();
		if (name != null && name.length() > 0)
		{
			result = name + " \n" + result;
		}
		System.out.println(result);
		return result;
	}

	public void goBackRunde()
	{
		if (activeRound > 0)
		{
			activeRound--;
		}
	}

	public Competitor getTeilnehmer(int t)
	{
		return teilnehmer.get(t);
	}

	public List<Competitor> getCompetitors()
	{
		return teilnehmer;
	}

	public Klasse getStartklasse()
	{
		return startklasse;
	}

	public boolean isToday()
	{
		final Calendar cal1 = Calendar.getInstance(), cal2 = Calendar.getInstance();

		cal2.setTime(getDatum());
		if (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
				|| (cal1.get(Calendar.HOUR_OF_DAY) < 4 && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) + 1))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public Date getDatum()
	{
		return datum;
	}

	public void setDatum(Date datum)
	{
		this.datum = datum;
		cal.setTime(datum);
	}

	public String getOrt()
	{
		return ort;
	}

	public void setOrt(String ort)
	{
		this.ort = ort;
	}

	public void setStartklasse(Klasse klasse)
	{
		startklasse = klasse;
	}

	public int getChanged()
	{
		return m_Changed;
	}

	public void setChanged(int changed)
	{
		m_Changed = changed;
	}

	public int getTeilnehmerCount()
	{
		return teilnehmer.size();
	}

	public int getDate()
	{
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public int getMonth()
	{
		return cal.get(Calendar.MONTH);
	}

	public int getYear()
	{
		return cal.get(Calendar.YEAR);
	}

	public int getMajority()
	{
		return (judges.size() + 1) / 2;
	}

	public String getWertungsrichterName(int t)
	{
		return Character.toString((char) ('A' + t));
	}

	public int findTeilnehmer(int startnr)
	{
		for (int t = 0; t < getTeilnehmerCount(); t++)
		{
			if (teilnehmer.get(t).getStartnummer() == startnr)
			{
				return t;
			}
		}
		return -1;
	}

	public Runde getRunde(int r)
	{
		return runden.get(r);
	}

	public int getRundenCount()
	{
		return runden.size();
	}
}
