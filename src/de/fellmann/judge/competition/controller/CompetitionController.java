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
import de.fellmann.judge.competition.result.CompetitionResult;
import de.fellmann.judge.competition.result.Result;
import de.fellmann.judge.competition.result.RoundResult;

public class CompetitionController
{
	private Competition competition;
	private CompetitionResult result = new CompetitionResult(); 
	
	public CompetitionController(Competition competition)
	{
		this.competition = competition;
		calculateAll();
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
		setResult(new CompetitionResult());
		
		getResult().setCompetition(competition);
		
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
			getResult().getRoundResults().add(roundResult);
		}
		
		for(Competitor competitor : competition.getCompetitors()) {
			for(int i=0;i<competition.getRounds().size();i++)
			{
				if(getResult().getRoundResults().get(i).getDisqualified().contains(competitor))
				{
					getResult().getDisqualified().add(competitor);
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
	
	public Place getPlace(Competitor competitor)
	{
		if(competition.getDisqualificationMode() == DisqualificationMode.DISQUALIFIED_LEAVE_EMPTY)
		{
			if(getResult().getDisqualified().contains(competitor))
				return null;
			return getPlaceWithDisqualified(competitor);
		}
		else {
			if(getResult().getDisqualified().contains(competitor))
			{
				return new Place(competition.getCompetitors().size());
			}
			Place place = getPlaceWithDisqualified(competitor);
			if(place != null) {
				Place placeResult = place;
				for(int i = 0; i < getResult().getDisqualified().size(); i++) {
					Place placeDisq = getPlaceWithDisqualified(getResult().getDisqualified().get(i));
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
	
	public int getBeatenCompetitors(Competitor competitor) {
		if(getResult().getDisqualified().contains(competitor))
			return 0;
		int sum = 0;
		Place place = getPlace(competitor);
		for(Competitor c : competition.getCompetitors())
		{
			if(competitor != c && !getResult().getDisqualified().contains(c))
			{
				if(place.getPlaceTo() < getPlace(c).getPlaceFrom())
					sum ++;
			}
		}
		return sum;
	}
	
	private Place getPlaceWithDisqualified(Competitor competitor)
	{
		for(int r = getResult().getRoundResults().size()-1; r>=0; r--)
		{
			Place place = null;
			for(Result result : getResult().getRoundResults().get(r).getPlace()) {
				if(result.getCompetitor().equals(competitor))
				{
					place = result.getPlace();
					break;
				}
			}
			if(place != null)
			{
				return place;
			}
		}
		return null;
	}
	
	public Round getPlacingRound(Competitor competitor)
	{
		for(int r = getResult().getRoundResults().size()-1; r>=0; r--)
		{
			Place place = null;
			for(Result result : getResult().getRoundResults().get(r).getPlace()) {
				if(result.getCompetitor().equals(competitor))
				{
					return competition.getRounds().get(r);
				}
			}
		}
		return null;
	}
	
	public Round getDisqualifyingRound(Competitor competitor)
	{
		for(int r = getResult().getRoundResults().size()-1; r>=0; r--)
		{
			if(getResult().getRoundResults().get(r).getDisqualified().contains(competitor))
				return competition.getRounds().get(r);
		}
		return null;
	}

	public CompetitionResult getResult()
	{
		return result;
	}

	public void setResult(CompetitionResult result)
	{
		this.result = result;
	}
}
