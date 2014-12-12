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

public class JudgementForFinal
{
	final private int competitors;
	final private int dances;
	final private int components;
	final private double[][][] judgements;

	public JudgementForFinal(int dances, int competitors, int components)
	{
		this.dances = dances;
		this.competitors = competitors;
		this.components = components;

		judgements = new double[dances][competitors][components * 3];
	}

	public int getCompetitors()
	{
		return competitors;
	}

	public int getJudgesPerComponent()
	{
		return 3;
	}

	public int getDances()
	{
		return dances;
	}

	public void setJudgment(int dance, int competitor, int judge, double value)
	{
		judgements[dance][competitor][judge] = value;
	}

	public double getJudgment(int dance, int competitor, int judge)
	{
		return judgements[dance][competitor][judge];
	}

	public void setJudgment(int dance, int competitor, int component, int judge, double value)
	{
		judgements[dance][competitor][component * 3 + judge] = value;
	}

	public double getJudgment(int dance, int competitor, int component, int judge)
	{
		return judgements[dance][competitor][component * 3 + judge];
	}

	public boolean isSet(int dance, int competitor)
	{
		for (int j = 0; j < getComponents() * 3; j++)
		{
			if (judgements[dance][competitor][j] < 1)
			{
				return false;
			}
		}
		return true;
	}

	public int getComponents()
	{
		return components;
	}
}
