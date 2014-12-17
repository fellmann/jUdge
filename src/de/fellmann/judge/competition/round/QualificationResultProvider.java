
package de.fellmann.judge.competition.round;

import java.util.HashMap;
import java.util.Map;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.Competitor;
import de.fellmann.judge.competition.Judge;

public class QualificationResultProvider implements ResultProvider
{

	private final Map<Competitor, Boolean>[][] cross;
	private final Map<Competitor, Integer> sum[];
	private final Map<Competitor, Integer> sumCalc[];

	@SuppressWarnings("unchecked")
	public QualificationResultProvider(int dances, int judges)
	{
		cross = new HashMap[dances][judges];
		sum = new HashMap[dances];
		sumCalc = new HashMap[dances];
	}

	public boolean getQualified(Competitor competitor, Judge judge)
	{
		final Map<Competitor, Boolean> map = cross.get(judge);
		if (map != null)
		{
			return Boolean.TRUE.equals(map.get(competitor));
		}
		else
		{
			return false;
		}
	}

	public void setQualified(Competitor competitor, Judge judge, boolean qualified)
	{
		Map<Competitor, Boolean> map = cross.get(judge);
		if (map == null)
		{
			map = new HashMap<Competitor, Boolean>();
			cross.put(judge, map);
		}
		map.put(competitor, qualified);
	}

	@Override
	public Place getPlace(Competitor competitor)
	{
		return place.get(competitor);
	}

	public void setPlace(Competitor competitor, Place place)
	{
		this.place.put(competitor, place);
		calc();
	}

	@Override
	public boolean getQualified(Competitor competitor)
	{
		return false;
	}

	private void calc()
	{
		for (int dance = 0; dance < turnier.getStartklasse().getDances(); dance++)
		{
			preCalc(dance);
		}
		for (i = 0; i < getPreQualifiedCount(); i++)
		{
			summe[i] = 0;
			for (int dance = 0; dance < turnier.getStartklasse().getDances(); dance++)
			{
				summe[i] += summen[dance][i];
			}

			qualified[preQualified[i]] = (summe[i] >= minKreuze);
		}

		int ksum = 0, uplatz = getMaxRundenPlace();
		Place kplatz;
		final Place disqualifiedp = new Place(turnier.getTeilnehmerCount(),
				turnier.getTeilnehmerCount());

		for (int kreuze = 0; kreuze <= turnier.getWertungsrichter(); kreuze++)
		{
			ksum = 0;
			for (i = 0; i < getPreQualifiedCount(); i++)
			{
				if (summe[i] == kreuze && !disqualified[preQualified[i]])
				{
					ksum++;
				}
			}
			if (ksum > 0)
			{
				for (i = 0; i < getPreQualifiedCount(); i++)
				{
					kplatz = new Place(uplatz - ksum + 1, uplatz);
					if (summe[i] == kreuze)
					{
						if (!disqualified[preQualified[i]])
						{
							platz[preQualified[i]] = kplatz;
						}
						else
						{
							platz[preQualified[i]] = disqualifiedp;
						}
					}
				}
				uplatz = uplatz - ksum;
			}
		}
	}

}
