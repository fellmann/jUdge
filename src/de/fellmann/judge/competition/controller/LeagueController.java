package de.fellmann.judge.competition.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import de.fellmann.judge.PlaceSum;
import de.fellmann.judge.competition.data.Competition;
import de.fellmann.judge.competition.data.Competitor;
import de.fellmann.judge.competition.data.League;
import de.fellmann.judge.competition.result.Result;
import de.fellmann.judge.competition.result.ResultSum;

public class LeagueController
{
	private League league;
	private ArrayList<Result> place = new ArrayList<Result>();
	private ArrayList<ResultSum> sum = new ArrayList<ResultSum>();
	
	public LeagueController(League league)
	{
		this.league = league;
		calculate();
	}
	
	public void calculate() {
		place.clear();
		sum.clear();
		
		final HashMap<Competitor, ResultSum> sums = new HashMap<Competitor, ResultSum>();
		for(Competitor competitor : league.getCompetitors()) {
			PlaceSum competititorSum = new PlaceSum();
			for(Competition co : league.getCompetitions())
			{
				CompetitionController cc = new CompetitionController(co);
				competititorSum.add(cc.getPlace(competitor));
			}
			sum.add(new ResultSum(competitor, competititorSum));
		}
		
		SortTools.getPlacesByOrder(league.getCompetitors(), new Comparator<Competitor>() {
			
			public int compare(Competitor o1, Competitor o2)
			{
				return PlaceSum.compare(sums.get(o1).getPlaceSum(), sums.get(o2).getPlaceSum());
			}
		}, 0, place);
	}
}
