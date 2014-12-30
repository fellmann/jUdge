package de.fellmann.judge.competition.result;

import java.util.ArrayList;

import de.fellmann.judge.competition.data.Value;

public class QualificationRoundResult extends RoundResult
{
	private ArrayList<Value> sums = new ArrayList<Value>();

	public ArrayList<Value> getSums()
	{
		return sums;
	}

	public void setSums(ArrayList<Value> sums)
	{
		this.sums = sums;
	}
}
