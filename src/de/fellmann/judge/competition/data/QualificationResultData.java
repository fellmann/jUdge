
package de.fellmann.judge.competition.data;

import java.util.ArrayList;

public class QualificationResultData extends ResultData
{
	private ArrayList<Value> mark = new ArrayList<Value>();
	private int sumToQualify = Integer.MAX_VALUE;
	private int minCrosses = 0;
	private int maxCrosses = Integer.MAX_VALUE;

	public int getSumToQualify()
	{
		return sumToQualify;
	}
	public void setSumToQualify(int sumToQualify)
	{
		this.sumToQualify = sumToQualify;
	}
	public int getMinCrosses()
	{
		return minCrosses;
	}
	public void setMinCrosses(int minCrosses)
	{
		this.minCrosses = minCrosses;
	}
	public int getMaxCrosses()
	{
		return maxCrosses;
	}
	public void setMaxCrosses(int maxCrosses)
	{
		this.maxCrosses = maxCrosses;
	}
	public ArrayList<Value> getMark()
	{
		return mark;
	}
	public void setMark(ArrayList<Value> mark)
	{
		this.mark = mark;
	}
}
