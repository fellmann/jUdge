package de.fellmann.judge.competition.controller;

import java.util.Comparator;
import java.util.HashMap;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.PlaceSum;
import de.fellmann.judge.competition.data.Competition;
import de.fellmann.judge.competition.data.Competitor;
import de.fellmann.judge.competition.data.League;

public class LeagueController
{
	private League league;
	private HashMap<Competitor, Place> place = new HashMap<Competitor, Place>();
	private HashMap<Competitor, PlaceSum> sum = new HashMap<Competitor, PlaceSum>();
	
	public LeagueController(League league)
	{
		this.league = league;
		calculate();
	}
	
	public void calculate() {
		place.clear();
		
		for(Competition co : league.getCompetitions())
		{
			CompetitionController cc = new CompetitionController(co);
			for(Competitor competitor : league.getCompetitors()) {
				PlaceSum psum = sum.get(competitor);
				psum = PlaceSum.createOrAdd(psum, cc.getPlace(competitor));
				sum.put(competitor, psum);
			}
		}
		
		SortTools.getPlacesByOrder(league.getCompetitors(), new Comparator<Competitor>() {
			
			public int compare(Competitor o1, Competitor o2)
			{
				return PlaceSum.compare(sum.get(o1), sum.get(o2));
			}
		}, 0, place);
	}
}
