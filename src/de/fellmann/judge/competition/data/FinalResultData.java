
package de.fellmann.judge.competition.data;

import java.util.ArrayList;

public class FinalResultData extends ResultData
{
	private ArrayList<Value> mark = new ArrayList<Value>();

	public ArrayList<Value> getMark()
	{
		return mark;
	}

	public void setMark(ArrayList<Value> mark)
	{
		this.mark = mark;
	}
}
