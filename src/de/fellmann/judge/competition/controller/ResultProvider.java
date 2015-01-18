
package de.fellmann.judge.competition.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import de.fellmann.judge.competition.data.Competition;
import de.fellmann.judge.competition.data.Competitor;
import de.fellmann.judge.competition.data.ManualResultData;
import de.fellmann.judge.competition.data.QualificationResultData;
import de.fellmann.judge.competition.data.ResultData;
import de.fellmann.judge.competition.data.Round;
import de.fellmann.judge.competition.data.Value;
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

	public RoundResult calculate(Competition competition, Round round, ArrayList<Competitor> preQualified, ArrayList<Competitor> preNotQualified)
	{
		final ResultData resultData = round.getResultData();
		RoundResult roundResult = null;

		switch (round.getRoundType())
		{
			case Qualification:
				roundResult = new ManualRoundResult();

				if (resultData instanceof ManualResultData)
				{
					final ManualResultData manualResultData = (ManualResultData) resultData;

					for (final Competitor competitor : preQualified)
					{
						if (isDisqualified(round, competitor))
						{
							roundResult.getDisqualified().add(competitor);
						}
						else if (manualResultData.getQualified().contains(competitor))
						{
							roundResult.getQualified().add(competitor);
						}
						else
						{
							roundResult.getNotQualified().add(competitor);
						}
					}

					preQualified.size();
					roundResult.getDisqualified().size();
					roundResult.getQualified().size();

					for (final Result place : manualResultData.getPlace())
					{
						roundResult.getPlace().add(place);
					}
				}
				else if (resultData instanceof QualificationResultData)
				{
					final QualificationRoundResult qualificationRoundResult = new QualificationRoundResult();
					roundResult = qualificationRoundResult;
					roundResult.setParticipants(preQualified);

					final QualificationResultData qualificationResultData = (QualificationResultData) resultData;

					for (final Value m : qualificationResultData.getMark())
					{
						if (m.getCompetitor() != null)
						{
							if (m.getDance() != null && m.getJudge() != null)
							{
								SortTools.putOrAddMark(qualificationRoundResult.getSums(), null, m.getCompetitor(), null, m.getMark());
								SortTools.putOrAddMark(qualificationRoundResult.getSums(), null, m.getCompetitor(), m.getJudge(), m.getMark());
								SortTools.putOrAddMark(qualificationRoundResult.getSums(), m.getDance(), m.getCompetitor(), null, m.getMark());
							}
							else if (m.getJudge() != null)
							{
								SortTools.putOrAddMark(qualificationRoundResult.getSums(), null, m.getCompetitor(), null, m.getMark());
								SortTools.putOrAddMark(qualificationRoundResult.getSums(), null, m.getCompetitor(), m.getJudge(), m.getMark());
							}
							else if (m.getDance() != null)
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

					new ArrayList<Competitor>();
					final HashMap<Competitor, Integer> sums = new HashMap<Competitor, Integer>();
					for (final Competitor competitor : preQualified)
					{
						final int sumCompetitor = SortTools.findMark(qualificationRoundResult.getSums(), null, competitor, null);
						sums.put(competitor, sumCompetitor);
						if (isDisqualified(round, competitor))
						{
							roundResult.getDisqualified().add(competitor);
						}
						else
						{
							if (sumCompetitor >= qualificationResultData.getSumToQualify())
							{
								roundResult.getQualified().add(competitor);
							}
							else
							{
								roundResult.getNotQualified().add(competitor);
							}
						}
					}

					SortTools.getPlacesByOrder(preQualified, new Comparator<Competitor>() {
						public int compare(Competitor o1, Competitor o2)
						{
							final Integer o1sum = sums.get(o1);
							final Integer o2sum = sums.get(o2);
							return Integer.compare(o2sum == null ? 0 : o2sum, o1sum == null ? 0
									: o1sum);
						}
					}, 0, qualificationRoundResult.getPlace());
				}
				break;
			case Final:
			{
				final FinalRoundResult finalRoundResult = new FinalRoundResult();
				roundResult = finalRoundResult;

				for (final Competitor competitor : preQualified)
				{
					if (isDisqualified(round, competitor))
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
				final FinalRoundResult finalRoundResult = new FinalRoundResult();
				roundResult = finalRoundResult;

				for (final Competitor competitor : preQualified)
				{
					if (isDisqualified(round, competitor))
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
				throw new RuntimeException("Not implemented: "
						+ round.getRoundType());

		}

		for (final Competitor competitor : competition.getCompetitors())
		{
			if (isDisqualified(round, competitor)
					&& !roundResult.getDisqualified().contains(competitor))
			{
				roundResult.getDisqualified().add(competitor);
			}
		}

		return roundResult;
	}

	private boolean isDisqualified(Round round, Competitor competitor)
	{
		for (int d = 0; d < round.getDisqualified().size(); d++)
		{
			if (round.getDisqualified().get(d).getCompetitor().equals(competitor))
			{
				return true;
			}
		}
		return false;
	}
}
