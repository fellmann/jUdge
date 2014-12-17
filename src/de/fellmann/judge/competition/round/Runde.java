
package de.fellmann.judge.competition.round;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.Competitor;
import de.fellmann.judge.competition.Turnier;

public abstract class Runde
{
	private final RoundType rundenIndex;
	private final Turnier turnier;

	private final DecimalFormat df = new DecimalFormat("#.#");
	private final Runde lastRound;

	private final Map<Competitor, Boolean> disqualified = new HashMap<Competitor, Boolean>();

	public Runde(RoundType type, Turnier t, Runde letzte)
	{
		this.rundenIndex = type;
		this.turnier = t;
		this.lastRound = letzte;
	}

	public Turnier getTurnier()
	{
		return turnier;
	}

	public abstract Place getPlace(Competitor competitor);

	public abstract Runde getLastQualifying();

	public abstract List<Competitor> getQualified();

	public abstract List<Competitor> getNotQualified();

	public List<Competitor> getDisqualified()
	{
		final List<Competitor> list = new ArrayList<Competitor>(
				disqualified.size());
		for (final Entry<Competitor, Boolean> e : disqualified.entrySet())
		{
			list.add(e.getKey());
		}
		return list;
	}

	public RoundType getType()
	{
		return rundenIndex;
	}

	public boolean getDisqualified(Competitor competitor)
	{
		return Boolean.TRUE.equals(disqualified.get(competitor));
	}

	public void setDisqualified(Competitor competitor, boolean disqualified)
	{
		this.disqualified.put(competitor, disqualified);
	}

	public Runde getLastRound()
	{
		return lastRound;
	}
}
