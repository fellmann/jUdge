
package de.fellmann.judge.competition.data;

import java.util.HashMap;

public class QualificationResultData extends ResultData
{
	private HashMap<DanceCompetitorJudgeKey, Boolean> cross = new HashMap<DanceCompetitorJudgeKey, Boolean>();
	private HashMap<DanceCompetitorKey, Integer> sumDanceCompetitor = new HashMap<DanceCompetitorKey, Integer>();
	private HashMap<CompetitorJudgeKey, Integer> sumCompetitorJudge = new HashMap<CompetitorJudgeKey, Integer>();
	private HashMap<Competitor, Integer> sumCompetitor = new HashMap<Competitor, Integer>();
	private int sumToQualify = Integer.MAX_VALUE;
	private int minCrosses = 0;
	private int maxCrosses = Integer.MAX_VALUE;
	
	public HashMap<DanceCompetitorJudgeKey, Boolean> getCross()
	{
		return cross;
	}
	public void setCross(HashMap<DanceCompetitorJudgeKey, Boolean> cross)
	{
		this.cross = cross;
	}
	public HashMap<DanceCompetitorKey, Integer> getSumDanceCompetitor()
	{
		return sumDanceCompetitor;
	}
	public void setSumDanceCompetitor(
			HashMap<DanceCompetitorKey, Integer> sumDanceCompetitor)
	{
		this.sumDanceCompetitor = sumDanceCompetitor;
	}
	public HashMap<CompetitorJudgeKey, Integer> getSumCompetitorJudge()
	{
		return sumCompetitorJudge;
	}
	public void setSumCompetitorJudge(
			HashMap<CompetitorJudgeKey, Integer> sumCompetitorJudge)
	{
		this.sumCompetitorJudge = sumCompetitorJudge;
	}
	public HashMap<Competitor, Integer> getSumCompetitor()
	{
		return sumCompetitor;
	}
	public void setSumCompetitor(HashMap<Competitor, Integer> sumCompetitor)
	{
		this.sumCompetitor = sumCompetitor;
	}
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
}
