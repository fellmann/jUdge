package de.fellmann.judge.competition;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thoughtworks.xstream.MarshallingStrategy;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.alias.ClassMapper;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.DataHolder;
import com.thoughtworks.xstream.core.DefaultConverterLookup;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.controller.CompetitionController;
import de.fellmann.judge.competition.data.Competition;
import de.fellmann.judge.competition.data.Competitor;
import de.fellmann.judge.competition.data.CompetitorState;
import de.fellmann.judge.competition.data.Dance;
import de.fellmann.judge.competition.data.DanceCompetitorJudgeKey;
import de.fellmann.judge.competition.data.DisqualificationMode;
import de.fellmann.judge.competition.data.FinalResultData;
import de.fellmann.judge.competition.data.Judge;
import de.fellmann.judge.competition.data.QualificationResultData;
import de.fellmann.judge.competition.data.Round;
import de.fellmann.judge.competition.data.RoundType;
import de.fellmann.judge.competition.persistence.DataObjectConverter;
import de.fellmann.judge.competition.result.QualificationRoundResult;

public class CompetitionTest
{
	@Test
	public void testCompetition() {
		Judge j[] = new Judge[] { new Judge(), new Judge(), new Judge(), new Judge(), new Judge() };
		Competitor c[] = new Competitor[] { new Competitor(), new Competitor(), new Competitor(), new Competitor(), new Competitor(), new Competitor(), new Competitor(), new Competitor(), new Competitor() };
		Dance d = new Dance();
		
		for(int i=0;i<c.length;i++)
		{
			c[i].setName("Competitor " + i);
		}
		
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

		
		CompetitionController controller = new CompetitionController(comp);
		Place[] expected1Round = new Place[] {new Place(5, 6), new Place(8, 9), new Place(1, 4), new Place(5, 6), new Place(1, 4), new Place(1, 4), new Place(8, 9), new Place(7), new Place(1, 4)};
		int[] expected1RoundSums = new int[] {4, 0, 5, 4, 5, 5, 0, 2, 5};
		for(int i=0;i<c.length;i++) {
			assertEquals(expected1Round[i], controller.getPlace(c[i]));
			assertEquals(expected1RoundSums[i], (int)((QualificationRoundResult)controller.getResult().getRoundResults().get(0)).getSumCompetitor().get(c[i]));
		}
		
		
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
		comp.getRounds().add(round);
		
		XStream xstream = new XStream();
		long time = System.currentTimeMillis();
		System.out.println(xstream.toXML(comp));
		time = System.currentTimeMillis() - time;
		System.out.println(time);
		controller = new CompetitionController(comp);
		controller.calculateAll();
		Place[] expected = new Place[] {new Place(5), new Place(9), new Place(2), new Place(6), new Place(3), new Place(1), new Place(7), new Place(8), new Place(4)};
		for(int i=0;i<c.length;i++) {
			assertEquals(expected[i], controller.getPlace(c[i]));
		}
		
	}
}
