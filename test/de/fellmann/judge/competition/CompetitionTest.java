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
import de.fellmann.judge.competition.data.DisqualificationMode;
import de.fellmann.judge.competition.data.Event;
import de.fellmann.judge.competition.data.FinalResultData;
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
		
		comp.setDisqualificationMode(DisqualificationMode.DISQUALIFIED_LASTPLACE_RAISE);
		
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
		

		round = new Round();
		round.setRoundType(RoundType.SmallFinal);
		
		comp.getRounds().add(round);
		FinalResultData finalData = new FinalResultData();
		round.setResultData(finalData);
		
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[1], j[0]), 3);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[1], j[1]), 3);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[1], j[2]), 3);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[1], j[3]), 3);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[1], j[4]), 3);
		
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[6], j[0]), 1);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[6], j[1]), 1);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[6], j[2]), 1);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[6], j[3]), 1);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[6], j[4]), 2);
		
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[7], j[0]), 2);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[7], j[1]), 2);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[7], j[2]), 2);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[7], j[3]), 2);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[7], j[4]), 1);
		
		
		
		round = new Round();
		round.setRoundType(RoundType.Final);
		
		comp.getRounds().add(round);
		finalData = new FinalResultData();
		round.setResultData(finalData);
		
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[0], j[0]), 2);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[0], j[1]), 6);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[0], j[2]), 4);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[0], j[3]), 5);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[0], j[4]), 5);
		
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[2], j[0]), 4);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[2], j[1]), 2);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[2], j[2]), 2);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[2], j[3]), 1);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[2], j[4]), 3);
		
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[3], j[0]), 6);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[3], j[1]), 5);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[3], j[2]), 6);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[3], j[3]), 6);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[3], j[4]), 6);
		
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[4], j[0]), 1);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[4], j[1]), 3);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[4], j[2]), 3);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[4], j[3]), 4);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[4], j[4]), 1);
		
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[5], j[0]), 5);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[5], j[1]), 1);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[5], j[2]), 1);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[5], j[3]), 2);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[5], j[4]), 4);
	
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[8], j[0]), 3);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[8], j[1]), 4);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[8], j[2]), 5);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[8], j[3]), 3);
		finalData.getMark().put(new DanceCompetitorJudgeKey(d, c[8], j[4]), 2);
		
		round = new Round();
		round.setRoundType(RoundType.End);
		round.getDisqualified().put(c[4], true);
		comp.getRounds().add(round);
		
		XStream xstream = new XStream();
		xstream.setMarshallingStrategy(new ReferenceByIdMarshallingStrategy());
		System.out.println(xstream.toXML(comp));
		
		CompetitionController controller = new CompetitionController(comp);
		controller.calculateAll();
		for(int i=0;i<c.length;i++) {
			System.out.println("-----");
			System.out.println("Comp " + (i+1));
			if(controller.getDisqualified(c[i]))
			{
				System.out.println("Disqualified");
			}
				System.out.println(controller.getPlace(c[i]).toStringFromToPoint());
				System.out.println(controller.getPlacingRound(c[i]).getRoundType());
		}
		
	}
}
