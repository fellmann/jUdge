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

/**
 * Majority Calculation Module Judgment System 1.0
 *
 * @author Hanno Fellmann
 *
 */
class CalcMajority
{
	private JudgementForMajor judgement = null;
	private double[][] table_val1;
	private double[][] table_val2;

	private Place[] result;
	private Place[] minPlace;
	private Place[] maxPlace;

	private byte[][] countPlaces;

	private final int majority;

	private int calculatedCount = 0;
	private boolean calcOne = false;

	public CalcMajority(JudgementForMajor judgement)
	{
		this.judgement = judgement;

		majority = (judgement.getJudges() / 2) + 1;

		if (judgement instanceof JudgementForDance)
		{
			calc();
		}
		else if (judgement instanceof JudgementForSkating)
		{
			init();
			countPlaces();

			calcOne = true;
		}
	}

	public void calc()
	{
		int competitor, sc = 0;
		Place minunset = new Place(Integer.MAX_VALUE);
		Place maxunset = new Place(Integer.MIN_VALUE);

		init();
		countPlaces();

		for (competitor = 0; competitor < judgement.getCompetitors(); competitor++)
		{
			if (judgement.isSet(competitor))
			{
				sc++;
				minPlace[competitor] = new Place(Integer.MAX_VALUE);
				maxPlace[competitor] = new Place(Integer.MIN_VALUE);
			}
		}

		if (sc == judgement.getCompetitors())
		{
			calculateForAll();
		}
		else
		{
			for (int i = 0; i < judgement.getCompetitors() - sc + 1; i++)
			{
				distributePossiblePlaces(i);
				calculate(IntList.getFromTo(0, judgement.getCompetitors() - 1));

				for (competitor = 0; competitor < judgement.getCompetitors(); competitor++)
				{
					if (judgement.isSet(competitor))
					{
						if (result[competitor].compareTo(minPlace[competitor]) < 0)
						{
							minPlace[competitor] = result[competitor];
						}
						if (result[competitor].compareTo(maxPlace[competitor]) > 0)
						{
							maxPlace[competitor] = result[competitor];
						}
					}
					else
					{
						if (result[competitor].compareTo(minunset) < 0)
						{
							minunset = result[competitor];
						}
						if (result[competitor].compareTo(maxunset) > 0)
						{
							maxunset = result[competitor];
						}
					}
				}
			}

			clearTablesForUnset();

			for (competitor = 0; competitor < judgement.getCompetitors(); competitor++)
			{
				if (!judgement.isSet(competitor))
				{
					minPlace[competitor] = minunset;
					maxPlace[competitor] = maxunset;
					result[competitor] = null;
				}

				if (minPlace[competitor].equals(maxPlace[competitor]))
				{
					result[competitor] = minPlace[competitor];
				}
				else
				{
					result[competitor] = null;
				}
			}
		}

	}

	public void getForSkating(int startColumn, int wantedPlace, IntList remaining)
	{
		calculatedCount = 0;
		calcRecursive(startColumn, wantedPlace, remaining);
	}

	public byte getCountPlaces(int x, int y)
	{
		if (countPlaces == null)
		{
			return 0;
		}
		if (x < countPlaces.length && y < countPlaces.length)
		{
			return countPlaces[x][y];
		}
		else
		{
			return (byte) judgement.getJudges();
		}
	}

	public ResultRange getPossibleResult(int competitor)
	{
		if (result[competitor] == null)
		{
			return new ResultRange(minPlace[competitor], maxPlace[competitor]);
		}
		else
		{
			return new ResultRange(result[competitor], result[competitor]);
		}
	}

	public Place getResult(int competitor)
	{
		return result[competitor];
	}

	public TableEntry getTable(int x, int y)
	{
		return new TableEntry(table_val1[x][y], table_val2[x][y]);
	}

	private void calculate(IntList all)
	{
		countPlaces();
		calcRecursive(0, 1, all.byval());
	}

	private void calculateForAll()
	{
		init();
		calculate(IntList.getFromTo(0, judgement.getCompetitors() - 1));
	}

	private void clearTablesForUnset()
	{
		for (int x = 0; x < judgement.getCompetitors(); x++)
		{
			if (!judgement.isSet(x))
			{
				for (int y = 0; y < judgement.getCompetitors(); y++)
				{
					table_val1[x][y] = -1;
					table_val2[x][y] = -1;
					countPlaces[x][y] = -1;
				}
			}
		}
	}

	private void countPlaces()
	{
		for (int competitor = 0; competitor < judgement.getCompetitors(); competitor++)
		{
			if (judgement.isSet(competitor))
			{
				countPlaces(competitor);
			}
		}
	}

	private void countPlaces(int competitor)
	{
		for (int column = 0; column < judgement.getCompetitors(); column++)
		{
			countPlaces[competitor][column] = 0;
			for (int ajur = 0; ajur < judgement.getJudges(); ajur++)
			{
				if (column + 1 >= judgement.getJudgement(competitor, ajur))
				{
					countPlaces[competitor][column]++;
				}
			}
		}
	}

	private void calcRecursive(int startColumn, int wantedPlace, IntList remaining)
	{
		if (startColumn > judgement.getCompetitors() + 1)
		{
			return;
		}

		int competitor, x;

		IntList byMaxMajority, byDigitSum = new IntList();

		byMaxMajority = getByMaxMajorities(startColumn, remaining.byval());
		while (byMaxMajority.size() < 1)
		{
			startColumn++;
			byMaxMajority = getByMaxMajorities(startColumn, remaining.byval());
			if (startColumn > judgement.getCompetitors() + 1)
			{
				return;
			}
		}

		if (byMaxMajority.size() == 1)
		{
			competitor = byMaxMajority.get(0);
			result[competitor] = new Place(wantedPlace, wantedPlace);
			calculatedCount++;
			remaining.remove(competitor);

			if (remaining.size() > 0)
			{
				calcRecursive(startColumn, wantedPlace + 1, remaining.byval());
			}
		}
		else
		{
			remaining.removeAll(byMaxMajority);

			byDigitSum = getByDigitSums(byMaxMajority.byval(), startColumn);
			while (byDigitSum.size() > 0)
			{
				if (byDigitSum.size() == 1)
				{
					competitor = byDigitSum.get(0);
					result[competitor] = new Place(wantedPlace, wantedPlace);
					calculatedCount++;
					byMaxMajority.remove(competitor);

					if (byMaxMajority.size() > 0)
					{
						calcRecursive(startColumn, wantedPlace + 1, byMaxMajority.byval());
					}
				}
				else
				{
					if (startColumn < judgement.getCompetitors() - 1)
					{
						calcRecursive(startColumn + 1, wantedPlace, byDigitSum.byval());
						byMaxMajority.removeAll(byDigitSum);
					}
					else
					{
						for (int ix = 0; ix < byDigitSum.size(); ix++)
						{
							x = byDigitSum.get(ix);
							result[x] = new Place(wantedPlace, wantedPlace
							        + byDigitSum.size() - 1);
							calculatedCount++;
						}
						byMaxMajority.removeAll(byDigitSum);
					}
				}
				wantedPlace += byDigitSum.size();
				byDigitSum = getByDigitSums(byMaxMajority.byval(), startColumn);
			}

			if (remaining.size() > 0)
			{
				calcRecursive(startColumn, wantedPlace, remaining.byval());
			}
		}
	}

	/**
	 * Distribute place for precalculation
	 */
	private int distributePlace(IntList which, int column, boolean dogood)
	{
		int minp, mins, competitor, cp, cs, res = -1;

		minp = judgement.getJudges() - 1;
		mins = 0;

		for (int i = 0; i < which.size(); i++)
		{
			competitor = which.get(i);
			cp = countPlaces[competitor][column];
			cs = getDigitSum(competitor, column, dogood);
			if (cp < judgement.getJudges())
			{
				if (cp < minp || (cp == minp && cs > mins))
				{
					minp = cp;
					mins = cs;
					res = competitor;
				}
			}
		}

		return res;
	}

	/**
	 * Distribute all remaining places for precalculation
	 */
	private void distributePossiblePlaces(int countOfGoodOnes)
	{
		final IntList good = new IntList(), bad = new IntList(), gone = new IntList();
		int competitor, column, csum, cidx;

		for (competitor = 0; competitor < judgement.getCompetitors(); competitor++)
		{
			if (judgement.isSet(competitor))
			{
				gone.add(competitor);
			}
			else
			{
				if (good.size() < countOfGoodOnes)
				{
					good.add(competitor);
				}
				else
				{
					bad.add(competitor);
				}
			}
		}

		for (column = 0; column < judgement.getCompetitors(); column++)
		{
			csum = 0;

			for (competitor = 0; competitor < judgement.getCompetitors(); competitor++)
			{
				if (!judgement.isSet(competitor))
				{
					if (column == 0)
					{
						countPlaces[competitor][column] = 0;
					}
					else
					{
						countPlaces[competitor][column] = countPlaces[competitor][column - 1];
					}
				}
				csum += countPlaces[competitor][column];
			}

			while (csum < (column + 1) * judgement.getJudges())
			{
				cidx = distributePlace(good, column, true);
				if (cidx >= 0)
				{
					countPlaces[cidx][column]++;
				}
				else
				{
					cidx = distributePlace(bad, column, true);

					if (cidx < 0)
					{
						cidx = 0;
					}
					countPlaces[cidx][column]++;
				}
				csum++;
			}
		}

	}

	private IntList getByDigitSums(IntList remaining, int column)
	{
		final IntList res = new IntList();
		int max = Integer.MAX_VALUE;
		int cur;
		int x;

		for (int ix = 0; ix < remaining.size(); ix++)
		{
			x = remaining.get(ix);
			cur = getDigitSum(x, column);
			if ((!calcOne || calculatedCount < 1)
			        && column < judgement.getCompetitors())
			{
				table_val2[x][column] = (byte) cur;
			}
			if (cur < max)
			{
				max = cur;
				res.clear();
				res.add(x);
			}
			else if (cur == max)
			{
				res.add(x);
			}
		}

		return res;
	}

	private IntList getByMaxMajorities(int column, IntList which)
	{
		final IntList res = new IntList();
		int max = -1;

		for (int competitor = 0; competitor < judgement.getCompetitors(); competitor++)
		{
			if (which.contains(competitor))
			{
				if ((!calcOne || calculatedCount < 1)
				        && column < judgement.getCompetitors())
				{
					table_val1[competitor][column] = getCountPlaces(competitor, column);
				}

				if (getCountPlaces(competitor, column) >= majority
				        && getCountPlaces(competitor, column) > max)
				{
					max = getCountPlaces(competitor, column);
					res.clear();
					res.add(competitor);
				}
				else if (max == getCountPlaces(competitor, column))
				{
					res.add(competitor);
				}
			}
		}

		return res;
	}

	private int getDigitSum(int x, int column)
	{
		return getDigitSum(x, column, true);
	}

	private int getDigitSum(int x, int column, boolean dogood)
	{
		int res = 0;

		if (dogood)
		{
			res = countPlaces[x][0];
			for (int i = 1; i <= column; i++)
			{
				res += (getCountPlaces(x, i) - getCountPlaces(x, i - 1))
				        * (i + 1);
			}
		}
		else
		{
			res = 0;
			for (int i = judgement.getCompetitors() - 2; i >= column; i++)
			{
				res += (getCountPlaces(x, i + 1) - getCountPlaces(x, i - 11))
				        * (i + 1);
			}
		}

		return res;
	}

	private void init()
	{
		calculatedCount = 0;
		countPlaces = new byte[judgement.getCompetitors()][judgement.getCompetitors()];
		table_val1 = new double[judgement.getCompetitors()][judgement.getCompetitors()];
		table_val2 = new double[judgement.getCompetitors()][judgement.getCompetitors()];
		result = new Place[judgement.getCompetitors()];
		minPlace = new Place[judgement.getCompetitors()];
		maxPlace = new Place[judgement.getCompetitors()];

		for (int i = 0; i < judgement.getCompetitors(); i++)
		{
			minPlace[i] = null;
			maxPlace[i] = null;

			for (int j = 0; j < judgement.getCompetitors(); j++)
			{
				table_val1[i][j] = -1;
				table_val2[i][j] = -1;
			}
		}
	}
}
