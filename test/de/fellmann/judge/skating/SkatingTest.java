package de.fellmann.judge.skating;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.fellmann.judge.skating.JudgementForFinal;
import de.fellmann.judge.skating.Place;
import de.fellmann.judge.skating.SkatingCalc;

public class SkatingTest {
	@Test
	public void testBeispielP() {
		SkatingCalc test = new SkatingCalc();
		test.set(createJudgmentFromString("22525,33636,45311,11253,66144,54462;12234,31313,25555,44441,66666,53122;11111,23456,34562,45623,52234,66345;33136,11252,45441,56323,22515,64664;26261,44636,15152,63345,51514,32423,"
				, 5, 6, 5));

		test.calc();
		
		assertResultFromString(test, "2,1,1,3,2;4,3,4,1,6;3,5,4,5,1;1,4,4,4,5;5,6,2,2,4;6,2,6,6,3");

	}

	private void assertResultFromString(SkatingCalc test, String result) {
		assertEquals(new Place(1), test.getPlatz(0));
		assertEquals(new Place(2), test.getPlatz(1));
		assertEquals(new Place(3), test.getPlatz(2));
		assertEquals(new Place(4), test.getPlatz(3));
		assertEquals(new Place(5), test.getPlatz(4));
		assertEquals(new Place(6), test.getPlatz(5));
		
		String [] resultForDance = result.split(";");
		
		for(int i=0;i<resultForDance.length;i++)
		{
			String[] resultForCompetitor = resultForDance[i].split(",");
			for(int j=0;j<resultForCompetitor.length;j++)
			{
				assertEquals(Double.valueOf(resultForCompetitor[j]), test.getMajorFunc()[j].getPlatz(i).getPlace(), 0.01);
			}
		}
	}

	private JudgementForFinal createJudgmentFromString(String gradesString, int dances, int competitors, int judges) {
		String[] judgmentsForDance = gradesString.split(";");

		JudgementForFinal jff = new JudgementForFinal(dances, competitors, judges);
		
		for(int d=0;d<judgmentsForDance.length;d++)
		{
			String[] judgmentsForCompetitor = judgmentsForDance[d].split(",");

			for (int i = 0; i < judgmentsForCompetitor.length; i++) {
				for (int j = 0; j < judgmentsForCompetitor[j].length(); j++) {
					if(judgmentsForCompetitor[i].charAt(j)>'0' && judgmentsForCompetitor[i].charAt(j)<'9')
						jff.setJudgment(d, i,  j, (byte)(judgmentsForCompetitor[i].charAt(j)-'0'));
				}
			}
		}

		return jff;
	}
}
