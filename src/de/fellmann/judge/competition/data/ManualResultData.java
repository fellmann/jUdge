
package de.fellmann.judge.competition.data;

import java.util.ArrayList;

import de.fellmann.judge.competition.result.Result;

public class ManualResultData extends ResultData
{
	private ArrayList<Competitor> qualified = new ArrayList<Competitor>();
	private ArrayList<Result> place = new ArrayList<Result>();
	
	public ArrayList<Competitor> getQualified()
	{
		return qualified;
	}
	public void setQualified(ArrayList<Competitor> qualified)
	{
		this.qualified = qualified;
	}
	public ArrayList<Result> getPlace()
	{
		return place;
	}
	public void setPlace(ArrayList<Result> place)
	{
		this.place = place;
	}
}
