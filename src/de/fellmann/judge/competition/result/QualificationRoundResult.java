package de.fellmann.judge.competition.result;

import java.util.HashMap;

import de.fellmann.judge.competition.data.Competitor;
import de.fellmann.judge.competition.data.CompetitorJudgeKey;
import de.fellmann.judge.competition.data.DanceCompetitorJudgeKey;
import de.fellmann.judge.competition.data.DanceCompetitorKey;

public class QualificationRoundResult extends RoundResult
{
	private HashMap<DanceCompetitorKey, Integer> sumDanceCompetitor = new HashMap<DanceCompetitorKey, Integer>();
	private HashMap<CompetitorJudgeKey, Integer> sumCompetitorJudge = new HashMap<CompetitorJudgeKey, Integer>();
	private HashMap<Competitor, Integer> sumCompetitor = new HashMap<Competitor, Integer>();
	
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

}
