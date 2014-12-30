package de.fellmann.judge.competition.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.data.Competition;
import de.fellmann.judge.competition.data.Competitor;
import de.fellmann.judge.competition.data.Dance;
import de.fellmann.judge.competition.data.FinalResultData;
import de.fellmann.judge.competition.data.Judge;
import de.fellmann.judge.competition.data.Value;
import de.fellmann.judge.competition.data.Round;
import de.fellmann.judge.competition.result.FinalRoundResult;
import de.fellmann.judge.competition.result.Result;
import de.fellmann.judge.skating.Calculator;
import de.fellmann.judge.skating.JudgementForFinal;

public class SortTools
{
	public static void getPlacesByOrder(ArrayList<Competitor> competitors, Comparator<Competitor> comparator, int placeOffset, ArrayList<Result> result)
	{
		ArrayList<Competitor> toSort = new ArrayList<Competitor>(competitors);
		Collections.sort(toSort, comparator);

		int count = 1;
		for(int i=0;i<toSort.size();i+=count)
		{
			count = 1;
			while(toSort.size() > i + count)
			{
				if (comparator.compare(toSort.get(i), toSort.get(i+count)) == 0)
				{
					count++;
				}
				else
				{
					break;
				}
			}
			
			for(int j=i;j<i+count;j++)
			{
				result.add(new Result(toSort.get(j), new Place(i+placeOffset+1, i+count+placeOffset)));
			}
		}
	}

	public static int findMark(ArrayList<Value> marks, Dance dance, Competitor competitor, Judge judge) {
		for(Value m : marks) {
			{
				if(m.getCompetitor() == competitor &&
				   m.getDance() == dance &&
				   m.getJudge() == judge)
				{
					return m.getMark();
				}
			}
			
		}
		return 0;
	}

	public static int putOrAddMark(ArrayList<Value> marks, Dance dance, Competitor competitor, Judge judge, int mark) {
		for(Value m : marks) {
			{
				if(m.getCompetitor() == competitor &&
				   m.getDance() == dance &&
				   m.getJudge() == judge)
				{
					m.setMark(mark + m.getMark());
					return m.getMark();
				}
			}
		}
		marks.add(new Value(dance, competitor, judge, mark));
		return 0;
	}

	public static void calculateFinalJudgement(Competition competition, Round round, FinalRoundResult roundResult)
	{
		FinalResultData finalResultData = (FinalResultData) round.getResultData();
		ArrayList<Competitor> participants = roundResult.getParticipants();
		JudgementForFinal judgement = new JudgementForFinal(competition.getDances().size(), participants.size(), competition.getJudges().size());
		
		for(int d = 0; d <competition.getDances().size();d++)
		{
			Dance dance = competition.getDances().get(d);
			for(int c = 0; c < participants.size(); c++)
			{
				Competitor competitor = participants.get(c);
				for(int j=0;j<competition.getJudges().size(); j++)
				{
					Judge judge = competition.getJudges().get(j);
					final Integer mark = SortTools.findMark(finalResultData.getMark(), dance, competitor, judge);
					if(mark != null)
					{
						judgement.setMark(d, c, j, (byte)(int)mark);
					}
				}
			}
		}
		
		Calculator calculator = new Calculator(judgement);
		
		roundResult.setCalculator(calculator);
		
		int resultOffset = roundResult.getQualified().size();
		for(int c = 0; c < participants.size(); c++)
		{
			Competitor competitor = participants.get(c);
			if(calculator.getResult(c) != null)
			{
				roundResult.getPlace().add(new Result(competitor, calculator.getResult(c).getWithOffset(resultOffset)));
			}
		}
	}
}
