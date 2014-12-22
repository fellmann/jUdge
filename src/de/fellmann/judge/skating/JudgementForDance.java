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

package de.fellmann.judge.skating;

class JudgementForDance implements JudgementForMajor
{
	private final JudgementForFinal judgement;
	private final int dance;

	public JudgementForDance(JudgementForFinal judgement, int dance)
	{
		this.judgement = judgement;
		this.dance = dance;
	}

	public JudgementForFinal getJudgement()
	{
		return judgement;
	}

	public int getDance()
	{
		return dance;
	}

	public int getDances()
	{
		return judgement.getDances();
	}

	public int getJudges()
	{
		return judgement.getJudges();
	}

	public boolean isSet(int competitor)
	{
		return judgement.isValid(dance, competitor);
	}

	public byte getJudgement(int competitor, int judge)
	{
		return judgement.getMark(dance, competitor, judge);
	}

	public int getCompetitors()
	{
		return judgement.getCompetitors();
	}
}
