
package de.fellmann.judge.competition.round;

import java.util.ArrayList;
import java.util.List;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.Competitor;
import de.fellmann.judge.competition.Turnier;

public class QualificationRound extends Runde
{
	private final ResultProvider resultProvider;

	public QualificationRound(Turnier t, Runde letzte,
	        ResultProvider resultProvider)
	{
		super(RoundType.Qualification, t, letzte);

		if (resultProvider == null)
		{
			throw new IllegalArgumentException();
		}
		this.resultProvider = resultProvider;
	}

	@Override
	public Runde getLastQualifying()
	{
		return getLastRound();
	}

	@Override
	public List<Competitor> getQualified()
	{
		final List<Competitor> list = new ArrayList<Competitor>();
		for (final Competitor competitor : getLastRound().getQualified())
		{
			if (resultProvider.getQualified(competitor)
			        && !getDisqualified(competitor))
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
		for (final Competitor competitor : getLastRound().getQualified())
		{
			if (!resultProvider.getQualified(competitor)
			        || getDisqualified(competitor))
			{
				list.add(competitor);
			}
		}
		return list;
	}

	@Override
	public Place getPlace(Competitor competitor)
	{
		return resultProvider.getPlace(competitor);
	}
}
