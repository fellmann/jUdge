
package de.fellmann.judge.competition.round;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.Competitor;
import de.fellmann.judge.competition.Turnier;

public class StartRound extends Runde
{
	private final Map<Competitor, Boolean> absent = new HashMap<Competitor, Boolean>();

	public StartRound(Turnier t)
	{
		super(RoundType.Start, t, null);
	}

	public void setAbsent(Competitor competitor, boolean absent)
	{
		this.absent.put(competitor, absent);
	}

	public boolean getAbsent(Competitor competitor)
	{
		return Boolean.TRUE.equals(this.absent.get(competitor));
	}

	@Override
	public Place getPlace(Competitor competitor)
	{
		return new Place(1, getTurnier().getCompetitors().size());
	}

	@Override
	public Runde getLastQualifying()
	{
		return this;
	}

	@Override
	public List<Competitor> getQualified()
	{
		final List<Competitor> list = new ArrayList<Competitor>();
		for (final Competitor competitor : getTurnier().getCompetitors())
		{
			if (!getAbsent(competitor) && !getDisqualified(competitor))
			{
				list.add(competitor);
			}
		}
		return list;
	}

	@Override
	public List<Competitor> getNotQualified()
	{
		final List<Competitor> list = new ArrayList<Competitor>();
		for (final Competitor competitor : getTurnier().getCompetitors())
		{
			if (getAbsent(competitor) || getDisqualified(competitor))
			{
				list.add(competitor);
			}
		}
		return list;
	}
}
