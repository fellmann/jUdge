
package de.fellmann.judge.competition.data;


public class Judge extends DataObject
{
	private Person person;
	
	private String judgeId;

	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
	}

	public String getJudgeId()
	{
		return judgeId;
	}

	public void setJudgeId(String judgeId)
	{
		this.judgeId = judgeId;
	}
}
