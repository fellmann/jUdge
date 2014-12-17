
package de.fellmann.judge.competition.round;

import java.util.HashMap;
import java.util.Map;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.Competitor;

public class ManualResultProvider implements ResultProvider
{
	private final Map<Competitor, Boolean> qualified = new HashMap<Competitor, Boolean>();
	private final Map<Competitor, Place> place = new HashMap<Competitor, Place>();

	@Override
	public boolean getQualified(Competitor competitor)
	{
		return Boolean.TRUE.equals(qualified.get(competitor));
	}

	public void setQualified(Competitor competitor, boolean qualified)
	{
		this.qualified.put(competitor, qualified);
	}

	@Override
	public Place getPlace(Competitor competitor)
	{
		return place.get(competitor);
	}

	public void setPlace(Competitor competitor, Place place)
	{
		this.place.put(competitor, place);
	}

}
