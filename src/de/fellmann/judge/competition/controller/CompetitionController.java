package de.fellmann.judge.competition.controller;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.data.Competition;
import de.fellmann.judge.competition.data.Competitor;
import de.fellmann.judge.competition.data.CompetitorState;
import de.fellmann.judge.competition.data.DisqualificationMode;
import de.fellmann.judge.competition.data.ResultData;
import de.fellmann.judge.competition.data.Round;

public class CompetitionController
{
	private Competition competition;
	private ArrayList<RoundResult> roundResults = new ArrayList<RoundResult>();
	private ArrayList<Competitor> disqualified = new ArrayList<Competitor>();
	
	public CompetitionController(Competition competition)
	{
		this.competition = competition;
	}

	public Competition getCompetition()
	{
		return competition;
	}

	public void setCompetition(Competition competition)
	{
		this.competition = competition;
	}
	
	public void startNextRound(Class<? extends ResultData> resultProviderClass)
	{
		Round round = new Round();
		
		try
		{
			Constructor<? extends ResultData> constructor = resultProviderClass.getConstructor();
			round.setResultData(constructor.newInstance());
			competition.getRounds().add(round);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	public void calculateAll() {
		getRoundResults().clear();
		ArrayList<Competitor> preQualified = new ArrayList<Competitor>();
		ArrayList<Competitor> preNotQualified = new ArrayList<Competitor>();

		
		for(Competitor competitor : competition.getCompetitors()) {
			if(competitor.getState() == CompetitorState.CHECKED_IN) {
				preQualified.add(competitor);
			}
		}
		
		for(int i=0;i<competition.getRounds().size();i++)
		{
			Round round = competition.getRounds().get(i);

			ResultProvider provider = new ResultProvider();
			RoundResult roundResult = provider.calculate(competition, round, preQualified, preNotQualified);
			
			preQualified = roundResult.getQualified();
			preNotQualified = roundResult.getNotQualified();
			this.getRoundResults().add(roundResult);
		}
		
		for(Competitor competitor : competition.getCompetitors()) {
			for(int i=0;i<competition.getRounds().size();i++)
			{
				if(roundResults.get(i).getDisqualified().contains(competitor))
				{
					getDisqualified().add(competitor);
					break;
				}
			}
		}
	}

	private Round getRoundBefore(Round round)
	{
		if(round != null)
		{
			int indexOf = competition.getRounds().indexOf(round);
			if(indexOf > 0)
			{
				return competition.getRounds().get(indexOf - 1);
			}
		}
		return null;
	}

	public ArrayList<RoundResult> getRoundResults()
	{
		return roundResults;
	}

	public void setRoundResults(ArrayList<RoundResult> roundResults)
	{
		this.roundResults = roundResults;
	}
	
	public Place getPlace(Competitor competitor)
	{
		if(competition.getDisqualificationMode() == DisqualificationMode.DISQUALIFIED_LEAVE_EMPTY)
		{
			if(disqualified.contains(competitor))
				return null;
			return getPlaceWithDisqualified(competitor);
		}
		else {
			if(disqualified.contains(competitor))
			{
				return new Place(competition.getCompetitors().size());
			}
			Place place = getPlaceWithDisqualified(competitor);
			if(place != null) {
				Place placeResult = place;
				for(int i = 0; i < disqualified.size(); i++) {
					Place placeDisq = getPlaceWithDisqualified(disqualified.get(i));
					if(placeDisq != null) {
						if(place.compareTo(placeDisq) == 1)
						{
							placeResult = placeResult.getWithOffset(-1);
						}
						else if(place.equals(placeDisq))
						{
							placeResult = new Place(placeResult.getPlaceFrom(), placeResult.getPlaceTo() - 1);
						}
					}
				}
				return placeResult;
			}
			else {
				return null;
			}
						
		}
	}
	
	private Place getPlaceWithDisqualified(Competitor competitor)
	{
		for(int r = roundResults.size()-1; r>=0; r--)
		{
			Place place = roundResults.get(r).getPlace().get(competitor);
			if(place != null)
			{
				return place;
			}
		}
		return null;
	}

	public boolean getDisqualified(Competitor competitor)
	{
		return disqualified.contains(competitor);
	}
	
	public Round getPlacingRound(Competitor competitor)
	{
		for(int r = roundResults.size()-1; r>=0; r--)
		{
			Place place = roundResults.get(r).getPlace().get(competitor);
			if(place != null)
				return competition.getRounds().get(r);
		}
		return null;
	}
	
	public Round getDisqualifyingRound(Competitor competitor)
	{
		for(int r = roundResults.size()-1; r>=0; r--)
		{
			if(roundResults.get(r).getDisqualified().contains(competitor))
				return competition.getRounds().get(r);
		}
		return null;
	}

	public ArrayList<Competitor> getDisqualified()
	{
		return disqualified;
	}

	public void setDisqualified(ArrayList<Competitor> disqualified)
	{
		this.disqualified = disqualified;
	}
}
