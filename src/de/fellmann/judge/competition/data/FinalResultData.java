
package de.fellmann.judge.competition.data;

import java.util.HashMap;

public class FinalResultData extends ResultData
{
	private HashMap<DanceCompetitorJudgeKey, Integer> mark = new HashMap<DanceCompetitorJudgeKey, Integer>();

	public HashMap<DanceCompetitorJudgeKey, Integer> getMark()
	{
		return mark;
	}

	public void setMark(HashMap<DanceCompetitorJudgeKey, Integer> mark)
	{
		this.mark = mark;
	}
}
