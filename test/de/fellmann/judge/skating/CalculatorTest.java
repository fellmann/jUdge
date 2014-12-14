/*
 * ================================================================
 *
 * jUdge
 * Open Source judging calculation library for dancesport
 *
 * Copyright 2014, Hanno Fellmann
 *
 * ================================================================
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package de.fellmann.judge.skating;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CalculatorTest
{
	private static String BEISPIEL_B = "11144,32211,25522,43453,54335,66666";
	private static String BEISPIEL_B_RESULT = "1;2;3;4;5;6;1,2,3,4,5,6";

	private static String BEISPIEL_P = "22525,33636,45311,11253,66144,54462;12234,31313,25555,44441,66666,53122;11111,23456,34562,45623,52234,66345;33136,11252,45441,56323,22515,64664;26261,44636,15152,63345,51514,32423,";
	private static String BEISPIEL_P_INCOMPLETE = "22525,33636,45311,11253,00000,00000;12204,31313,25055,44441,60066,53122;11111,00000,34562,45623,52234,66345;33136,11252,45441,56323,02505,64664;26261,40636,15102,63305,01514,30423,";
	private static String BEISPIEL_P_RESULT = "2,1,1,3,2;4,3,4,1,6;3,5,4,5,1;1,4,4,4,5;5,6,2,2,4;6,2,6,6,3;1,2,3,4,5,6";
	
	private static String BEISPIEL_E = "1";
	private static String BEISPIEL_E_RESULT = "1;1";

	@Test
	public void testBeispielB()
	{
		final Calculator test = new Calculator(
		        createJudgmentFromString(BEISPIEL_B, 1, 6, 5));

		assertResultFromString(test, BEISPIEL_B_RESULT);
	}

	@Test
	public void testBeispielP()
	{
		final Calculator test = new Calculator(
		        createJudgmentFromString(BEISPIEL_P, 5, 6, 5));

		assertResultFromString(test, BEISPIEL_P_RESULT);
	}
	
	@Test
	public void testBeispielEdgeCase1()
	{
		final Calculator test = new Calculator(
		        createJudgmentFromString(BEISPIEL_E, 1, 1, 1));

		assertResultFromString(test, BEISPIEL_E_RESULT);
	}

	@Test
	public void testBeispielP_parts()
	{
		final Calculator test = new Calculator(
		        createJudgmentFromString(BEISPIEL_P_INCOMPLETE, 5, 6, 5));
		for (int c = 0; c < 6; c++)
		{
			for (int j = 0; j < 5; j++)
			{
				assertEquals(test.getJudgement().isValid(0, c, j), c < 4);
			}
		}

		assertPreResultFromString(test, BEISPIEL_P_RESULT);
	}

	private void assertPreResultFromString(Calculator test, String result)
	{
		final String[] resultForCompetitor = result.split(";");
		for (int competitor = 0; competitor < test.getJudgement().getCompetitors(); competitor++)
		{
			final String[] resultForDance = resultForCompetitor[competitor].split(",");

			for (int dance = 0; dance < test.getJudgement().getDances(); dance++)
			{
				if (test.getResult(dance, competitor) != null)
				{
					assertEquals(Double.valueOf(resultForDance[dance]), test.getResult(dance, competitor).getValue(), 0.01);
				}
				else
				{
					assertTrue(Double.valueOf(resultForDance[dance]) >= test.getPossibleResult(dance, competitor).getMinPlace().getValue() - 0.01);
					assertTrue(Double.valueOf(resultForDance[dance]) <= test.getPossibleResult(dance, competitor).getMaxPlace().getValue() + 0.01);
				}
			}
		}
	}

	private void assertResultFromString(Calculator test, String result)
	{

		final String[] resultForCompetitor = result.split(";");
		for (int competitor = 0; competitor < test.getJudgement().getCompetitors(); competitor++)
		{
			final String[] resultForDance = resultForCompetitor[competitor].split(",");

			for (int dance = 0; dance < test.getJudgement().getDances(); dance++)
			{
				assertEquals(Double.valueOf(resultForDance[dance]), test.getResult(dance, competitor).getValue(), 0.01);
			}
		}
		final String[] resultForFinal = resultForCompetitor[test.getJudgement().getCompetitors()].split(",");
		for (int competitor = 0; competitor < test.getJudgement().getCompetitors(); competitor++)
		{
			assertEquals(Double.valueOf(resultForFinal[competitor]), test.getResult(competitor).getValue(), 0.01);
		}
	}

	private JudgementForFinal createJudgmentFromString(String gradesString, int dances, int competitors, int judges)
	{
		final String[] judgmentsForDance = gradesString.split(";");

		final JudgementForFinal jff = new JudgementForFinal(dances,
		        competitors, judges);

		for (int d = 0; d < judgmentsForDance.length; d++)
		{
			final String[] judgmentsForCompetitor = judgmentsForDance[d].split(",");

			for (int i = 0; i < judgmentsForCompetitor.length; i++)
			{
				for (int j = 0; j < judgmentsForCompetitor[i].length(); j++)
				{
					if (judgmentsForCompetitor[i].charAt(j) > '0'
					        && judgmentsForCompetitor[i].charAt(j) < '9')
					{
						jff.setMark(d, i, j, (byte) (judgmentsForCompetitor[i].charAt(j) - '0'));
					}
				}
			}
		}

		return jff;
	}
}
