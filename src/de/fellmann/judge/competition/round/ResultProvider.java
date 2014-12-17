
package de.fellmann.judge.competition.round;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.Competitor;

public interface ResultProvider
{
	public boolean getQualified(Competitor competitor);

	public Place getPlace(Competitor competitor);
}
