package de.fellmann.judge.competition.controller;

import java.util.ArrayList;
import java.util.HashMap;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.data.Competitor;

public abstract class RoundResult
{
	private HashMap<Competitor, Place> place = new HashMap<Competitor, Place>();
	private ArrayList<Competitor> qualified = new ArrayList<Competitor>();
	private ArrayList<Competitor> notQualified = new ArrayList<Competitor>();
	private ArrayList<Competitor> disqualified = new ArrayList<Competitor>();
	
	public HashMap<Competitor, Place> getPlace()
	{
		return place;
	}
	public void setPlace(HashMap<Competitor, Place> place)
	{
		this.place = place;
	}
	public ArrayList<Competitor> getQualified()
	{
		return qualified;
	}
	public void setQualified(ArrayList<Competitor> qualified)
	{
		this.qualified = qualified;
	}
	public ArrayList<Competitor> getNotQualified()
	{
		return notQualified;
	}
	public void setNotQualified(ArrayList<Competitor> notQualified)
	{
		this.notQualified = notQualified;
	}
	public ArrayList<Competitor> getDisqualified()
	{
		return disqualified;
	}
	public void setDisqualified(ArrayList<Competitor> disqualified)
	{
		this.disqualified = disqualified;
	}
}
