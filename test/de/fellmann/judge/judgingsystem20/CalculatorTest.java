
package de.fellmann.judge.judgingsystem20;

import org.junit.Test;

import de.fellmann.judge.judgingsystem20.Calculator.JudgingSystem;

public class CalculatorTest
{
	@Test
	public void testFormationLatinWM2014()
	{
		final Calculator calc = new Calculator(
		        createJudgementFromString("10,9,9.5,9.5,10,10,10,10,9.5,9.5,10,10"),
		        JudgingSystem.JUDGING_SYSTEM_2_1);

		System.out.println(calc.getSumCompetitor(0));

	}

	private JudgementForFinal createJudgementFromString(String string)
	{
		final JudgementForFinal result = new JudgementForFinal(1, 1, 4);
		final String dancesString[] = string.split(",");
		for (int d = 0; d < dancesString.length; d++)
		{
			result.setJudgment(0, 0, d, Double.valueOf(dancesString[d]));
		}
		return result;
	}
}
