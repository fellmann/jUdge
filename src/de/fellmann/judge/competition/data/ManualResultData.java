
package de.fellmann.judge.competition.data;

import java.util.HashMap;

import de.fellmann.judge.Place;

public class ManualResultData extends ResultData
{
	private HashMap<Competitor, Boolean> qualified = new HashMap<Competitor, Boolean>();
	private HashMap<Competitor, Place> place = new HashMap<Competitor, Place>();
	
	public HashMap<Competitor, Boolean> getQualified()
	{
		return qualified;
	}
	public void setQualified(HashMap<Competitor, Boolean> qualified)
	{
		this.qualified = qualified;
	}
	public HashMap<Competitor, Place> getPlace()
	{
		return place;
	}
	public void setPlace(HashMap<Competitor, Place> place)
	{
		this.place = place;
	}
	
}
