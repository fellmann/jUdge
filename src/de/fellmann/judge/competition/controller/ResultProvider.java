package de.fellmann.judge.competition.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import de.fellmann.judge.competition.data.Competition;
import de.fellmann.judge.competition.data.Competitor;
import de.fellmann.judge.competition.data.ManualResultData;
import de.fellmann.judge.competition.data.Value;
import de.fellmann.judge.competition.data.QualificationResultData;
import de.fellmann.judge.competition.data.ResultData;
import de.fellmann.judge.competition.data.Round;
import de.fellmann.judge.competition.result.EndRoundResult;
import de.fellmann.judge.competition.result.FinalRoundResult;
import de.fellmann.judge.competition.result.ManualRoundResult;
import de.fellmann.judge.competition.result.QualificationRoundResult;
import de.fellmann.judge.competition.result.Result;
import de.fellmann.judge.competition.result.RoundResult;

public class ResultProvider
{
	public ResultProvider()
	{
	}

	public RoundResult calculate(Competition competition,
			Round round,
			ArrayList<Competitor> preQualified,
			ArrayList<Competitor> preNotQualified)
	{
		ResultData resultData = round.getResultData();
		RoundResult roundResult = null;
		
		switch(round.getRoundType())
		{
		case Qualification:
			roundResult = new ManualRoundResult();
			
			if (resultData instanceof ManualResultData)
			{
				ManualResultData manualResultData = (ManualResultData) resultData;

				for (Competitor competitor : preQualified)
				{
					if (round.getDisqualified().contains(competitor))
					{
						roundResult.getDisqualified().add(competitor);
					}
					else if (manualResultData
							.getQualified().contains(competitor))
					{
						roundResult.getQualified().add(competitor);
					}
					else
					{
						roundResult.getNotQualified().add(competitor);
					}
				}

				int firstPlaceOffset = preQualified.size() - roundResult.getDisqualified().size()
						- roundResult.getQualified().size();

				for (Result place : manualResultData.getPlace())
				{
					roundResult.getPlace().add(place);
				}
			}
			else if (resultData instanceof QualificationResultData)
			{
				final QualificationRoundResult qualificationRoundResult = new QualificationRoundResult();
				roundResult = qualificationRoundResult;
				roundResult.setParticipants(preQualified);
				
				QualificationResultData qualificationResultData = (QualificationResultData) resultData;

				for(Value m : qualificationResultData.getMark())
				{
					if(m.getCompetitor() != null)
					{
						if(m.getDance() != null && m.getJudge() != null)
						{
							SortTools.putOrAddMark(qualificationRoundResult.getSums(), null, m.getCompetitor(), null, m.getMark());
							SortTools.putOrAddMark(qualificationRoundResult.getSums(), null, m.getCompetitor(), m.getJudge(), m.getMark());
							SortTools.putOrAddMark(qualificationRoundResult.getSums(), m.getDance(), m.getCompetitor(), null, m.getMark());
						}
						else if(m.getJudge() != null)
						{
							SortTools.putOrAddMark(qualificationRoundResult.getSums(), null, m.getCompetitor(), null, m.getMark());
							SortTools.putOrAddMark(qualificationRoundResult.getSums(), null, m.getCompetitor(), m.getJudge(), m.getMark());
						}
						else if(m.getDance() != null)
						{
							SortTools.putOrAddMark(qualificationRoundResult.getSums(), null, m.getCompetitor(), null, m.getMark());
							SortTools.putOrAddMark(qualificationRoundResult.getSums(), m.getDance(), m.getCompetitor(), null, m.getMark());
						}
						else
						{
							SortTools.putOrAddMark(qualificationRoundResult.getSums(), null, m.getCompetitor(), null, m.getMark());
						}
					}
				}
				
				ArrayList<Competitor> toSort = new ArrayList<Competitor>();
				final HashMap<Competitor, Integer> sums = new HashMap<Competitor, Integer>();
				for (Competitor competitor : preQualified)
				{
					int sumCompetitor = SortTools.findMark(qualificationRoundResult.getSums(), null, competitor, null);
					sums.put(competitor, sumCompetitor);
					if (round.getDisqualified().contains(
							competitor))
					{
						roundResult.getDisqualified().add(competitor);
					}
					else {
						if(sumCompetitor >= qualificationResultData.getSumToQualify())
						{
							roundResult.getQualified().add(competitor);
						}
						else
						{
							roundResult.getNotQualified().add(competitor);
						}
					}
				}
				
				SortTools.getPlacesByOrder(preQualified,  new Comparator<Competitor>() {
					public int compare(Competitor o1, Competitor o2)
					{
						Integer o1sum = sums.get(o1);
						Integer o2sum = sums.get(o2);
						return Integer.compare(o2sum == null ? 0 : o2sum, o1sum == null ? 0 : o1sum);
					}
				}, 0, qualificationRoundResult.getPlace());
			}
			break;
		case Final:
			{
				FinalRoundResult finalRoundResult = new FinalRoundResult();
				roundResult = finalRoundResult;
				
				for(Competitor competitor : preQualified)
				{
					if (round.getDisqualified().contains(
							competitor))
					{
						roundResult.getDisqualified().add(competitor);
					}
					else
					{
						roundResult.getNotQualified().add(competitor);
					}
				}
				roundResult.setParticipants(preQualified);
				
				SortTools.calculateFinalJudgement(competition, round, finalRoundResult);
				break;
			}
		case SmallFinal:
			{
				FinalRoundResult finalRoundResult = new FinalRoundResult();
				roundResult = finalRoundResult;
				
				for(Competitor competitor : preQualified)
				{
					if (round.getDisqualified().contains(
							competitor))
					{
						roundResult.getDisqualified().add(competitor);
					}
					else
					{
						roundResult.getQualified().add(competitor);
					}
				}
				roundResult.setParticipants(preNotQualified);
				
				SortTools.calculateFinalJudgement(competition, round, finalRoundResult);
				break;
			}
		case End:
			roundResult = new EndRoundResult();
			
			break;
		default:
			throw new RuntimeException("Not implemented: " + round.getRoundType());
			
		}

		for(Competitor competitor : competition.getCompetitors())
		{
			if(round.getDisqualified().contains(competitor) && !roundResult.getDisqualified().contains(competitor))
			{
				roundResult.getDisqualified().add(competitor);
			}
		}
		
		return roundResult;
	}
}
