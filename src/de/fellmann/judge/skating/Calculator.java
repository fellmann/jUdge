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
import de.fellmann.judge.ResultRange;
import de.fellmann.judge.exceptions.CalculationException;

/**
 * Implementation of Skating calculation.
 *
 * @author Hanno Fellmann
 *
 */
public class Calculator
{
	private final JudgementForFinal judgement;

	private CalcMajority dancesCalc[];
	private CalcMajority skatingCalc;

	private Place result[];
	private double table10_val1[][];
	private double table10_val2[][];
	private double summe[];
	private boolean doSkating = true;

	private int validDances = 0;

	private boolean appliedSkating10 = false;
	private boolean appliedSkating11 = false;

	private boolean[] isValid;

	/**
	 * Create new calculator and calculate results.
	 *
	 * @param judgement
	 *            The judgement data.
	 */
	public Calculator(JudgementForFinal judgement)
	{
		this.judgement = judgement;
		recalculate();
	}

	/**
	 * Determine if Skating rule 10 was used.
	 *
	 * @return True, if rule 10 was applied.
	 */
	public boolean getAppliedSkating10()
	{
		return appliedSkating10;
	}

	/**
	 * Determine if Skating rule 11 was used.
	 *
	 * @return True, if rule 11 was applied.
	 */
	public boolean getAppliedSkating11()
	{
		return appliedSkating11;
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

	/**
	 * Returns the possible result for a competitor in a specific dance.
	 * <p>
	 * If the input is not set for the competitor, the range of all results
	 * still possible is beeing calculated.
	 *
	 * @param dance
	 *            Index of the dance.
	 * @param competitor
	 *            Index of the competitor.
	 * @return The range of possible results.
	 */
	public ResultRange getPossibleResult(int dance, int competitor)
	{
		if (dancesCalc[dance] != null)
		{
			return dancesCalc[dance].getPossibleResult(competitor);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Get the final result for a competitor.
	 *
	 * @param competitor
	 *            Index of the competitor.
	 * @return The place, or null if judgement not set.
	 */
	public Place getResult(int competitor)
	{
		return result[competitor];
	}

	/**
	 * Get the result in a specific for a competitor.
	 *
	 * @param competitor
	 *            Index of the competitor.
	 * @return The place, or null if judgement not set.
	 */
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

	/**
	 * Returns the sum over all dances for one competitor.
	 *
	 * @param competitor
	 *            Index of the competitor.
	 * @return The sum of all completed dances.
	 */
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
		return new TableEntry(table10_val1[x][y], table10_val2[x][y]);
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

	/**
	 * Manually recalculate results
	 * <p>
	 * (for example after change of input data)
	 */
	public void recalculate()
	{
		try
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
					table10_val1[i][j] = -1;
					table10_val2[i][j] = -1;
				}
			}

			skatingCalc = new CalcMajority(new JudgementForSkating(judgement));

			for (int d = 0; d < judgement.getDances(); d++)
			{
				dancesCalc[d] = new CalcMajority(new JudgementForDance(
				        judgement, d));
				if (judgement.isValid(d))
				{
					validDances++;
					isValid[d] = true;
				}
				else
				{
					isValid[d] = false;
				}
			}

			if (validDances > 0 && judgement.getCompetitors() > 0
			        && judgement.getJudges() > 0)
			{
				while (wantedPlace < judgement.getCompetitors() + 1)
				{
					curmaj = getByDanceSum(all);

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
							calcRecursive(wantedPlace, curmaj.byval());
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
		catch (final RuntimeException re)
		{
			throw new CalculationException(re);
		}
	}

	/**
	 * If not all judgements are ready, only dances with complete, valid input
	 * are used for calculation.
	 *
	 * @return The count of valid dances.
	 */
	public int getValidDances()
	{
		return validDances;
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

	private void calcRecursive(int wantedPlace, IntList which)
	{

		int wantedPlaceweg;

		IntList curmaj, curSums = new IntList();

		curmaj = getByPlaceCount(wantedPlace, which);

		if (curmaj.size() == 1)
		{
			wantedPlaceweg = curmaj.get(0);
			result[wantedPlaceweg] = new Place(wantedPlace);
			which.remove(wantedPlaceweg);

			if (which.size() > 0)
			{
				calcRecursive(wantedPlace + 1, which.byval());
			}
		}
		else
		{
			which.removeAll(curmaj);

			curSums = getByDigitSums(curmaj, wantedPlace);
			while (curSums.size() > 0)
			{
				if (curSums.size() == 1)
				{
					wantedPlaceweg = curSums.get(0);
					result[wantedPlaceweg] = new Place(wantedPlace);
					curmaj.remove(wantedPlaceweg);

					if (curmaj.size() > 0)
					{
						calcRecursive(wantedPlace + 1, curmaj.byval());
					}
				}
				else
				{
					getSkating(wantedPlace, curSums.byval());
					curmaj.removeAll(curSums);
				}
				wantedPlace += curSums.size();
				curSums = getByDigitSums(curmaj, wantedPlace);
			}

			if (which.size() > 0)
			{
				calcRecursive(wantedPlace, which.byval());
			}
		}
	}

	private IntList getByDanceSum(IntList remaining)
	{
		final IntList res = new IntList();
		double max = Double.MAX_VALUE;
		double cur;
		int competitor;

		for (int ix = 0; ix < remaining.size(); ix++)
		{
			competitor = remaining.get(ix);
			cur = 0;
			for (int dance = 0; dance < judgement.getDances(); dance++)
			{
				if (isValid[dance])
				{
					cur += dancesCalc[dance].getResult(competitor).toDouble();
				}
			}
			summe[competitor] = cur;
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

	private IntList getByDigitSums(IntList which, int aidx)
	{
		final IntList res = new IntList();
		double max = Double.MAX_VALUE;
		double cur;

		for (int competitor = 0; competitor < judgement.getCompetitors(); competitor++)
		{
			if (which.contains(competitor))
			{
				cur = 0;
				for (int dance = 0; dance < judgement.getDances(); dance++)
				{
					if (isValid[dance]
							&& dancesCalc[dance].getResult(competitor).toDouble() <= aidx)
					{
						cur += dancesCalc[dance].getResult(competitor).toDouble();
					}
				}
				table10_val2[competitor][aidx - 1] = cur;
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

	private IntList getByPlaceCount(int aidx, IntList which)
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
				for (int dance = 0; dance < judgement.getDances(); dance++)
				{
					if (isValid[dance]
					        && dancesCalc[dance].getResult(competitor).toInt() <= aidx*2)
					{
						cur++;
					}
				}
				table10_val1[competitor][aidx - 1] = cur;
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

	private IntList getByPlaceRule11(IntList remaining)
	{
		double maxP = 20000;
		int x;
		final IntList v = new IntList();
		for (int i = 0; i < remaining.size(); i++)
		{
			x = remaining.get(i);
			if (skatingCalc.getResult(x).toInt() < maxP)
			{
				maxP = skatingCalc.getResult(x).toInt();
				v.clear();
			}
			if (skatingCalc.getResult(x).toInt() == maxP)
			{
				v.add(x);
			}
		}
		return v;
	}

	private void getSkating(int wantedPlace, IntList remaining)
	{
		int competitor;
		IntList killed;

		appliedSkating11 = true;

		skatingCalc.getForSkating(wantedPlace - 1, wantedPlace, remaining.byval());

		killed = getByPlaceRule11(remaining);

		for (int ix = 0; ix < killed.size(); ix++)
		{
			competitor = killed.get(ix);
			result[competitor] = skatingCalc.getResult(competitor);
		}

		remaining.removeAll(killed);
		if (remaining.size() > 1)
		{
			calcRecursive(wantedPlace + killed.size(), remaining.byval());
		}
		else if (remaining.size() == 1)
		{
			result[remaining.get(0)] = new Place(wantedPlace + killed.size());
		}

	}

	private void init()
	{
		appliedSkating10 = false;
		appliedSkating11 = false;
		dancesCalc = new CalcMajority[judgement.getDances()];
		result = new Place[judgement.getCompetitors()];
		table10_val1 = new double[judgement.getCompetitors()][judgement.getCompetitors()];
		table10_val2 = new double[judgement.getCompetitors()][judgement.getCompetitors()];
		summe = new double[judgement.getCompetitors()];
		isValid = new boolean[judgement.getDances()];
	}

}
