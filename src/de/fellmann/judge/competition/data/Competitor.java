
package de.fellmann.judge.competition.data;


public class Competitor extends DataObject
{
	private Person person;
	private int countStarRounds;
	private CompetitorState state;

	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
	}

	public int getCountStarRounds()
	{
		return countStarRounds;
	}

	public void setCountStarRounds(int countStarRounds)
	{
		this.countStarRounds = countStarRounds;
	}

	public CompetitorState getState()
	{
		return state;
	}

	public void setState(CompetitorState state)
	{
		this.state = state;
	}
}
