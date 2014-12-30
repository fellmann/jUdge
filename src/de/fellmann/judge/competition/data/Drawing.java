package de.fellmann.judge.competition.data;

import java.util.ArrayList;

public class Drawing extends DataObject
{
	private ArrayList<Heat> heats = new ArrayList<Heat>();

	public ArrayList<Heat> getHeats()
	{
		return heats;
	}

	public void setHeats(ArrayList<Heat> heats)
	{
		this.heats = heats;
	}
}
