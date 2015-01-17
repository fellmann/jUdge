
package de.fellmann.judge.competition.data;

public class Judge extends DataObject
{
	private String name = "A";
	private Club club;

	private String judgeId;

	public String getJudgeId()
	{
		return judgeId;
	}

	public void setJudgeId(String judgeId)
	{
		this.judgeId = judgeId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Club getClub()
	{
		return club;
	}

	public void setClub(Club club)
	{
		this.club = club;
	}
}
