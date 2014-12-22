package de.fellmann.judge.competition;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.ReferenceByIdMarshallingStrategy;

import de.fellmann.judge.competition.controller.CompetitionController;
import de.fellmann.judge.competition.controller.QualificationRoundResult;
import de.fellmann.judge.competition.data.Competition;
import de.fellmann.judge.competition.data.Competitor;
import de.fellmann.judge.competition.data.CompetitorState;
import de.fellmann.judge.competition.data.Dance;
import de.fellmann.judge.competition.data.DanceCompetitorJudgeKey;
import de.fellmann.judge.competition.data.Event;
import de.fellmann.judge.competition.data.Judge;
import de.fellmann.judge.competition.data.QualificationResultData;
import de.fellmann.judge.competition.data.Round;
import de.fellmann.judge.competition.data.RoundType;

public class CompetitionTest
{
	@Test
	public void testCompetition() {
		Judge j[] = new Judge[] { new Judge(), new Judge(), new Judge(), new Judge(), new Judge() };
		Competitor c[] = new Competitor[] { new Competitor(), new Competitor(), new Competitor(), new Competitor(), new Competitor(), new Competitor(), new Competitor(), new Competitor(), new Competitor() };
		Dance d = new Dance();
		
		Competition comp = new Competition();
		for(Competitor cc : c)
		{
			comp.getCompetitors().add(cc);
			cc.setState(CompetitorState.CHECKED_IN);
		}
		for(Judge jj : j)
			comp.getJudges().add(jj);
		
		comp.getDances().add(d);
		
		Round round = new Round();
		round.setRoundType(RoundType.Qualification);
		
		comp.getRounds().add(round);
		QualificationResultData data = new QualificationResultData();
		round.setResultData(data);
		
		data.setMaxCrosses(6);
		data.setMinCrosses(6);
		data.setSumToQualify(3);
		
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[2], j[0]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[3], j[0]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[4], j[0]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[5], j[0]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[7], j[0]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[8], j[0]), true);
		
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[0], j[1]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[2], j[1]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[3], j[1]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[4], j[1]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[5], j[1]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[8], j[1]), true);

		data.getCross().put(new DanceCompetitorJudgeKey(d, c[0], j[2]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[2], j[2]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[3], j[2]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[4], j[2]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[5], j[2]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[8], j[2]), true);

		
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[0], j[3]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[2], j[3]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[4], j[3]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[5], j[3]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[7], j[3]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[8], j[3]), true);

		data.getCross().put(new DanceCompetitorJudgeKey(d, c[0], j[4]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[2], j[4]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[3], j[4]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[4], j[4]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[5], j[4]), true);
		data.getCross().put(new DanceCompetitorJudgeKey(d, c[8], j[4]), true);
		

		XStream xstream = new XStream();
		xstream.setMarshallingStrategy(new ReferenceByIdMarshallingStrategy());
		System.out.println(xstream.toXML(comp));
		
		CompetitionController controller = new CompetitionController(comp);
		controller.calculateAll();
		QualificationRoundResult firstRoundResult = (QualificationRoundResult)controller.getRoundResults().get(0);
		for(int i=0;i<c.length;i++) {
			System.out.println("Comp " + (i+1));
			System.out.println(firstRoundResult.getSumCompetitor().get(c[i]));
			if(firstRoundResult.getQualified().contains(c[i]))
				System.out.println("qualified");
			if(firstRoundResult.getNotQualified().contains(c[i]))
				System.out.println("not qualified");
			if(firstRoundResult.getPlace().get(c[i]) != null)
				System.out.println(firstRoundResult.getPlace().get(c[i]).toStringFromToPoint());
			System.out.println("--------");
		}
		System.out.println(controller.getRoundResults().get(0).getQualified());
		
	}
}
