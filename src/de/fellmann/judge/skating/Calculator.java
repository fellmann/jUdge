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

import de.fellmann.common.IntList;
import de.fellmann.judge.Place;

public class Calculator
{
	private final JudgementForFinal judgement;

	private CalcMajor dancesCalc[];
	private CalcMajor skatingCalc;

	private Place result[];
	private int platzierungen[][];
	private double tabelle1[][];
	private double tabelle2[][];
	private double summe[];
	private boolean doSkating = true;

	private boolean appliedSkating10 = false;
	private boolean appliedSkating11 = false;

	public Calculator(JudgementForFinal judgement)
	{
		this.judgement = judgement;
		recalculate();
	}

	public boolean getAppliedSkating10()
	{
		return appliedSkating10;
	}

	public boolean getAppliedSkating11()
	{
		return appliedSkating11;
	}

	public int getCountPlace(int x, int y)
	{
		return platzierungen[x][y];
	}

	/**
	 * Returns the judgement input data
	 */
	public JudgementForFinal getJudgement()
	{
		return judgement;
	}

	/**
	 * Returns the majority calculation table
	 *
	 * @param dance
	 * @param x
	 *            The table column, 0 &lt; x &lt; competitors
	 * @param y
	 *            The table row, 0 &lt; x &lt; competitors
	 * @return
	 */
	public TableEntry getMajorTable(int dance, int x, int y)
	{
		return dancesCalc[dance].getTable(x, y);
	}

	public Place getMaxResult(int dance, int competitor)
	{
		if (dancesCalc[dance] != null)
		{
			return dancesCalc[dance].getMaxResult(competitor);
		}
		else
		{
			return null;
		}
	}

	public Place getMinResult(int dance, int competitor)
	{
		if (dancesCalc[dance] != null)
		{
			return dancesCalc[dance].getMinResult(competitor);
		}
		else
		{
			return null;
		}
	}

	public Place getResult(int competitor)
	{
		return result[competitor];
	}

	public Place getResult(int dance, int competitor)
	{
		if (dancesCalc[dance] != null)
		{
			return dancesCalc[dance].getResult(competitor);
		}
		else
		{
			return null;
		}
	}

	public double getSum(int competitor)
	{
		return summe[competitor];
	}

	/**
	 * Returns the calculation table for Skating rule 10
	 *
	 * @param x
	 *            The table column, 0 &lt; x &lt; competitors
	 * @param y
	 *            The table row, 0 &lt; x &lt; competitors
	 */
	public TableEntry getTable10(int x, int y)
	{
		return new TableEntry(tabelle1[x][y], tabelle2[x][y]);
	}

	/**
	 * Returns the calculation table for Skating rule 11
	 *
	 * @param x
	 *            The table column, 0 &lt; x &lt; competitors
	 * @param y
	 *            The table row, 0 &lt; x &lt; competitors
	 */
	public TableEntry getTable11(int x, int y)
	{
		return skatingCalc.getTable(x, y);
	}

	public void recalculate()
	{
		init();

		final IntList all = new IntList();
		int i, j;

		for (i = 0; i < judgement.getCompetitors(); i++)
		{
			all.add(i);
		}

		IntList curmaj;

		int wantedPlace = 1;
		int wantedPlaceweg;

		appliedSkating10 = false;
		appliedSkating11 = false;

		for (i = 0; i < judgement.getCompetitors(); i++)
		{
			for (j = 0; j < judgement.getCompetitors(); j++)
			{
				tabelle1[i][j] = -1;
				tabelle2[i][j] = -1;
			}
		}

		skatingCalc = new CalcMajor(new JudgementForSkating(judgement));

		for (int d = 0; d < judgement.getDances(); d++)
		{
			dancesCalc[d] = new CalcMajor(new JudgementForDance(judgement, d));
		}

		if (judgement.isValid())
		{
			while (wantedPlace < judgement.getCompetitors() + 1)
			{
				curmaj = getMinSums(all);

				// Exactly one competitor
				if (curmaj.size() == 1)
				{
					wantedPlaceweg = curmaj.get(0);

					result[wantedPlaceweg] = new Place(wantedPlace);
					all.remove(wantedPlaceweg);
				}

				// More than one competitor share minimal sum
				else
				{
					if (doSkating)
					{
						appliedSkating10 = true;
						getUnter(wantedPlace, curmaj.byval());
						all.removeAll(curmaj);
					}
					else
					{
						for (i = 0; i < curmaj.size(); i++)
						{
							wantedPlaceweg = curmaj.get(i);
							result[wantedPlaceweg] = new Place(wantedPlace,
							        wantedPlace + curmaj.size() - 1);
						}
						all.removeAll(curmaj);
					}
				}

				wantedPlace += curmaj.size();
			}
		}
	}

	/**
	 * Set Skating Rules 10 and 11 enabled
	 *
	 * @param doSkating
	 *            If true, Rules 10 and 11 will be used.
	 */
	public void setDoSkating(boolean doSkating)
	{
		this.doSkating = doSkating;
	}

	private IntList getMaxCount(int aidx, IntList which)
	{
		final IntList res = new IntList();
		int max = -1;
		int cur = 0;

		if (which.size() == 1)
		{
			return which;
		}

		for (int competitor = 0; competitor < judgement.getCompetitors(); competitor++)
		{
			if (which.contains(competitor))
			{
				cur = 0;
				for (int i = 0; i < judgement.getDances(); i++)
				{
					if (dancesCalc[i].getResult(competitor).getValue() <= aidx)
					{
						cur++;
					}
				}
				tabelle1[competitor][aidx - 1] = cur;
				if (cur > max)
				{
					max = cur;
					res.clear();
					res.add(competitor);
				}
				else if (max == cur)
				{
					res.add(competitor);
				}
			}
		}

		return res;
	}

	private IntList getMaxSkatingPlatz(IntList remaining)
	{
		double maxP = 20000;
		int x;
		final IntList v = new IntList();
		for (int i = 0; i < remaining.size(); i++)
		{
			x = remaining.get(i);
			if (skatingCalc.getResult(x).getValue() < maxP)
			{
				maxP = skatingCalc.getResult(x).getValue();
				v.clear();
			}
			if (skatingCalc.getResult(x).getValue() == maxP)
			{
				v.add(x);
			}
		}
		return v;
	}

	// private void countPlaces()
	// {
	// for (int aidx = 0; aidx < judgement.getDances(); aidx++)
	// {
	// for (int competitor = 0; competitor < judgement.getCompetitors();
	// competitor++)
	// {
	// platzierungen[competitor][aidx] = 0;
	// for (int judge = 0; judge < judgement.getJudges(); judge++)
	// {
	// if (aidx + 1 >= judgement.getMark(aidx, competitor, judge))
	// {
	// platzierungen[competitor][aidx]++;
	// }
	// }
	// }
	// }
	//
	// for (int i = 0; i < judgement.getDances(); i++)
	// {
	// for (int j = 0; j < judgement.getCompetitors(); j++)
	// {
	// tabelle1[i][j] = -1;
	// tabelle2[i][j] = -1;
	// }
	// }
	// }

	private IntList getMinSums(IntList remaining)
	{
		final IntList res = new IntList();
		double max = 9999;
		double cur;
		int competitor;

		for (int ix = 0; ix < remaining.size(); ix++)
		{
			competitor = remaining.get(ix);
			cur = dancesCalc[0].getResult(competitor).getValue();
			for (int i = 1; i < judgement.getDances(); i++)
			{
				cur += dancesCalc[i].getResult(competitor).getValue();
			}
			summe[competitor] = cur;
			// varTabelle2[x][aidx]=cur;
			if (cur < max)
			{
				max = cur;
				res.clear();
				res.add(competitor);
			}
			else if (cur == max)
			{
				res.add(competitor);
			}
		}

		return res;
	}

	private void getSkating(int wantedPlace, IntList remaining)
	{
		int competitor;
		IntList killed;

		appliedSkating11 = true;

		skatingCalc.setSkating();
		skatingCalc.countPlaces(wantedPlace - 1, wantedPlace, remaining.byval());

		killed = getMaxSkatingPlatz(remaining);

		for (int ix = 0; ix < killed.size(); ix++)
		{
			competitor = killed.get(ix);
			result[competitor] = skatingCalc.getResult(competitor);
		}

		remaining.removeAll(killed);
		if (remaining.size() > 1)
		{
			getUnter(wantedPlace + killed.size(), remaining.byval());
		}
		else if (remaining.size() == 1)
		{
			result[remaining.get(0)] = new Place(wantedPlace + killed.size());
		}

	}

	private IntList getSums(IntList which, int aidx)
	{
		final IntList res = new IntList();
		double max = 999999;
		double cur;

		for (int competitor = 0; competitor < judgement.getCompetitors(); competitor++)
		{
			if (which.contains(competitor))
			{
				cur = 0;
				for (int i = 0; i < judgement.getDances(); i++)
				{
					if (dancesCalc[i].getResult(competitor).getValue() <= aidx)
					{
						cur += dancesCalc[i].getResult(competitor).getValue();
					}
				}
				tabelle2[competitor][aidx - 1] = cur;
				if (cur < max)
				{
					max = cur;
					res.clear();
					res.add(competitor);
				}
				else if (max == cur)
				{
					res.add(competitor);
				}
			}
		}

		return res;
	}

	private void getUnter(int wantedPlace, IntList which)
	{

		int wantedPlaceweg;

		IntList curmaj, curSums = new IntList();

		curmaj = getMaxCount(wantedPlace, which);

		if (curmaj.size() == 1)
		{
			wantedPlaceweg = curmaj.get(0);
			result[wantedPlaceweg] = new Place(wantedPlace);
			which.remove(wantedPlaceweg);

			if (which.size() > 0)
			{
				getUnter(wantedPlace + 1, which.byval());
			}
		}
		else
		{
			which.removeAll(curmaj);

			curSums = getSums(curmaj, wantedPlace);
			while (curSums.size() > 0)
			{
				if (curSums.size() == 1)
				{
					wantedPlaceweg = curSums.get(0);
					result[wantedPlaceweg] = new Place(wantedPlace);
					curmaj.remove(wantedPlaceweg);

					if (curmaj.size() > 0)
					{
						getUnter(wantedPlace + 1, curmaj.byval());
					}
				}
				else
				{
					getSkating(wantedPlace, curSums.byval());
					curmaj.removeAll(curSums);
				}
				wantedPlace += curSums.size();
				curSums = getSums(curmaj, wantedPlace);
			}

			if (which.size() > 0)
			{
				getUnter(wantedPlace, which.byval());
			}
		}
	}

	private void init()
	{
		dancesCalc = new CalcMajor[judgement.getDances()];
		result = new Place[judgement.getCompetitors()];
		platzierungen = new int[judgement.getCompetitors()][judgement.getCompetitors()];
		tabelle1 = new double[judgement.getCompetitors()][judgement.getCompetitors()];
		tabelle2 = new double[judgement.getCompetitors()][judgement.getCompetitors()];
		summe = new double[judgement.getCompetitors()];
	}

}
