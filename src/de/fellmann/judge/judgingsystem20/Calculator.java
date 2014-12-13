/*
 * ================================================================
 *
 * jUdge - Open Source judging calculation library for dancesport
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

package de.fellmann.judge.judgingsystem20;

import de.fellmann.judge.exceptions.JudgingException;

public class Calculator
{
	public static enum JudgingSystem {
		/**
		 * Judging system 1.0,
		 *
		 * lowest and highest score are eleminated
		 */
		JUDGING_SYSTEM_1_0,
		/**
		 * Judging system 2.0,
		 *
		 * lowest and highest score are weighted 50%
		 */
		JUDGING_SYSTEM_2_0,
		/**
		 * Judging system 2.1,
		 *
		 * lowest and highest score are weighted 1 / (1 + diff_to_median) ^ 2
		 */
		JUDGING_SYSTEM_2_1
	}

	private final JudgementForFinal judgement;
	private final JudgingSystem version;
	private final double[] sumCompetitor;
	private final double[][] sumDances;
	private final double[][][] sumComponents;

	public double getSumCompetitor(int competitor)
	{
		return sumCompetitor[competitor];
	}

	public double getSumDance(int competitor, int dance)
	{
		return sumDances[competitor][dance];
	}

	public double getSumComponent(int competitor, int dance, int component)
	{
		return sumComponents[competitor][dance][component];
	}

	public Calculator(JudgementForFinal judgement, JudgingSystem version)
	{
		this.judgement = judgement;
		this.version = version;
		sumCompetitor = new double[judgement.getCompetitors()];
		sumDances = new double[judgement.getCompetitors()][judgement.getDances()];
		sumComponents = new double[judgement.getCompetitors()][judgement.getDances()][judgement.getComponents()];
		recalculate();
	}

	public void recalculate()
	{
		for (int competitor = 0; competitor < judgement.getCompetitors(); competitor++)
		{
			sumCompetitor[competitor] = 0;
			for (int dance = 0; dance < judgement.getDances(); dance++)
			{
				sumDances[competitor][dance] = 0;
				for (int component = 0; component < judgement.getComponents(); component++)
				{
					sumComponents[competitor][dance][component] = calcComponent(dance, competitor, component);
					sumDances[competitor][dance] += sumComponents[dance][competitor][component];
				}
				sumCompetitor[competitor] += sumDances[competitor][dance];
			}
		}
	}

	private double calcComponent(int dance, int competitor, int component)
	{
		final double[] ar = new double[] {
		        judgement.getJudgment(dance, competitor, component, 0),
		        judgement.getJudgment(dance, competitor, component, 1),
		        judgement.getJudgment(dance, competitor, component, 2) };

		sort(ar);

		double fac1;
		double fac2;

		switch (version)
		{
			case JUDGING_SYSTEM_1_0:
			{
				fac1 = 0;
				fac2 = 0;
				break;
			}
			case JUDGING_SYSTEM_2_0:
			{
				fac1 = 0.5;
				fac2 = 0.5;
				break;
			}
			case JUDGING_SYSTEM_2_1:
			{
				fac1 = 1 / (1 + (ar[1] - ar[0]) * (ar[1] - ar[0]));
				fac2 = 1 / (1 + (ar[2] - ar[1]) * (ar[2] - ar[1]));
				break;
			}
			default:
			{
				throw new JudgingException("Unknown judging system!");
			}
		}

		return (ar[0] * fac1 + ar[1] + ar[2] * fac2) / (1 + fac1 + fac2);
	}

	private void sort(double ar[])
	{
		if (ar[1] < ar[0])
		{
			swap(ar, 0, 1);
		}
		if (ar[2] < ar[1])
		{
			swap(ar, 2, 1);
		}
		if (ar[1] < ar[0])
		{
			swap(ar, 0, 1);
		}
	}

	private void swap(double[] ar, int x, int y)
	{

		final double s = ar[x];
		ar[x] = ar[y];
		ar[y] = s;
	}

}