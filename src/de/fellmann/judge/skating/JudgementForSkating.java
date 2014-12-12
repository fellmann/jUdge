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

class JudgementForSkating implements JudgementForMajor
{
	private final JudgementForFinal judgement;

	public JudgementForSkating(JudgementForFinal judgement)
	{
		this.judgement = judgement;
	}

	public JudgementForFinal getJudgement()
	{
		return judgement;
	}

	@Override
	public int getCompetitors()
	{
		return judgement.getCompetitors();
	}

	@Override
	public int getDances()
	{
		return 1;
	}

	@Override
	public int getJudges()
	{
		return judgement.getJudges() * judgement.getDances();
	}

	@Override
	public boolean isSet(int competitor)
	{
		return true;
	}

	@Override
	public byte getJudgement(int competitor, int judge)
	{
		return judgement.getMark(judge / judgement.getJudges(), competitor, judge
		        % judgement.getJudges());
	}
}
