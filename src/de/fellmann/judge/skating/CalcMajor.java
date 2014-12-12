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

/**
 * Majority Calculation Module Judgment System 1.0
 *
 * @author Hanno Fellmann
 *
 */
class CalcMajor
{
	private JudgementForMajor judgement = null;
	private double[][] table1;
	private double[][] table2;

	private Place[] result;
	private Place[] minPlace;
	private Place[] maxPlace;

	private byte[][] placeSums;

	private int majorit;

	private int calculatedCount = 0;
	private boolean calcOne = false;

	public CalcMajor(JudgementForMajor judgement)
	{
		this.judgement = judgement;
		if (judgement instanceof JudgementForDance)
		{
			calc();
		}
		else if (judgement instanceof JudgementForSkating)
		{
			init();
			countPlaces();
		}
	}

	public void calc()
	{
		int competitor, sc = 0;
		Place minunset = new Place(10000);
		Place maxunset = new Place(0);

		init();
		countPlaces();

		for (competitor = 0; competitor < judgement.getCompetitors(); competitor++)
		{
			if (judgement.isSet(competitor))
			{
				sc++;
				minPlace[competitor] = new Place(10000);
				maxPlace[competitor] = new Place(0);
			}
		}

		if (sc == judgement.getCompetitors())
		{
			calcForAll();
		}
		else
		{
			for (int i = 0; i < judgement.getCompetitors() - sc + 1; i++)
			{
				distributePossiblePlaces(i);
				calc(IntList.getFromTo(0, judgement.getCompetitors() - 1));

				for (competitor = 0; competitor < judgement.getCompetitors(); competitor++)
				{
					if (judgement.isSet(competitor))
					{
						if (result[competitor].getValue() < minPlace[competitor].getValue())
						{
							minPlace[competitor] = result[competitor];
						}
						if (result[competitor].getValue() > maxPlace[competitor].getValue())
						{
							maxPlace[competitor] = result[competitor];
						}
					}
					else
					{
						if (result[competitor].getValue() < minunset.getValue())
						{
							minunset = result[competitor];
						}
						if (result[competitor].getValue() > maxunset.getValue())
						{
							maxunset = result[competitor];
						}
					}
				}
			}

			clearTableUnset();

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

	public Place getMaxResult(int i)
	{
		return maxPlace[i];
	}

	public Place getMinResult(int i)
	{
		return minPlace[i];
	}

	public Place getResult(int i)
	{
		return result[i];
	}

	public byte getCountPlaces(int x, int y)
	{
		if (placeSums == null)
		{
			return 0;
		}
		if (x < placeSums.length && y < placeSums.length)
		{
			return placeSums[x][y];
		}
		else
		{
			return (byte) judgement.getJudges();
		}
	}

	public TableEntry getTable(int x, int y)
	{
		return new TableEntry(table1[x][y], table2[x][y]);
	}

	private void calc(IntList all)
	{
		countPlaces();
		countPlaces(0, 1, all.byval());
	}

	private void calcForAll()
	{
		init();
		calc(IntList.getFromTo(0, judgement.getCompetitors() - 1));
	}

	private void init()
	{
		placeSums = new byte[judgement.getCompetitors()][judgement.getCompetitors()];
		table1 = new double[judgement.getCompetitors()][judgement.getCompetitors()];
		table2 = new double[judgement.getCompetitors()][judgement.getCompetitors()];
		result = new Place[judgement.getCompetitors()];
		minPlace = new Place[judgement.getCompetitors()];
		maxPlace = new Place[judgement.getCompetitors()];

		for (int i = 0; i < judgement.getCompetitors(); i++)
		{
			minPlace[i] = null;
			maxPlace[i] = null;

			for (int j = 0; j < judgement.getCompetitors(); j++)
			{
				table1[i][j] = -1;
				table2[i][j] = -1;
			}
		}
	}

	private void clearTableUnset()
	{
		for (int i = 0; i < judgement.getCompetitors(); i++)
		{
			for (int j = 0; j < judgement.getCompetitors(); j++)
			{
				if (!judgement.isSet(j))
				{
					table1[i][j] = -1;
					table2[i][j] = -1;
				}
			}
		}
	}

	public void countPlaces(int startColumn, int wantedPlace, IntList remaining)
	{
		if (startColumn > judgement.getCompetitors() + 1)
		{
			return;
		}

		int gesuchterPlatzweg, x;

		IntList curmaj, curSums = new IntList();

		curmaj = getMaxMajors(startColumn, remaining.byval());
		while (curmaj.size() < 1)
		{
			startColumn++;
			curmaj = getMaxMajors(startColumn, remaining.byval());
			if (startColumn > judgement.getCompetitors() + 1)
			{
				return;
			}
		}

		if (curmaj.size() == 1)
		{
			gesuchterPlatzweg = curmaj.get(0);
			result[gesuchterPlatzweg] = new Place(wantedPlace, wantedPlace);
			calculatedCount++;
			remaining.remove(gesuchterPlatzweg);

			if (remaining.size() > 0)
			{
				countPlaces(startColumn, wantedPlace + 1, remaining.byval());
			}
		}
		else
		{
			remaining.removeAll(curmaj);

			curSums = getSums(curmaj.byval(), startColumn);
			while (curSums.size() > 0)
			{
				if (curSums.size() == 1)
				{
					gesuchterPlatzweg = curSums.get(0);
					result[gesuchterPlatzweg] = new Place(wantedPlace,
					        wantedPlace);
					calculatedCount++;
					curmaj.remove(gesuchterPlatzweg);

					if (curmaj.size() > 0)
					{
						countPlaces(startColumn, wantedPlace + 1, curmaj.byval());
					}
				}
				else
				{
					if (startColumn < judgement.getCompetitors() - 1)
					{
						countPlaces(startColumn + 1, wantedPlace, curSums.byval());
						curmaj.removeAll(curSums);
					}
					else
					{
						for (int ix = 0; ix < curSums.size(); ix++)
						{
							x = curSums.get(ix);
							result[x] = new Place(wantedPlace, wantedPlace
							        + curSums.size() - 1);
							calculatedCount++;
						}
						curmaj.removeAll(curSums);
					}
				}
				wantedPlace += curSums.size();
				curSums = getSums(curmaj.byval(), startColumn);
			}

			if (remaining.size() > 0)
			{
				countPlaces(startColumn, wantedPlace, remaining.byval());
			}
		}
	}

	private int chooseOne(IntList which, int column, boolean dogood)
	{
		int minp, mins, competitor, cp, cs, res = -1;

		minp = judgement.getJudges() - 1;
		mins = 0;

		for (int i = 0; i < which.size(); i++)
		{
			competitor = which.get(i);
			cp = placeSums[competitor][column];
			cs = getSum(competitor, column, dogood);
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

	private IntList getMaxMajors(int column, IntList which)
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
					table1[competitor][column] = getCountPlaces(competitor, column);
				}

				if (getCountPlaces(competitor, column) >= majorit
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
			placeSums[competitor][column] = 0;
			for (int ajur = 0; ajur < judgement.getJudges(); ajur++)
			{
				if (column + 1 >= judgement.getJudgement(competitor, ajur))
				{
					placeSums[competitor][column]++;
				}
			}
		}
		majorit = (judgement.getJudges() / 2) + 1;
	}

	// **************************************************************************

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
						placeSums[competitor][column] = 0;
					}
					else
					{
						placeSums[competitor][column] = placeSums[competitor][column - 1];
					}
				}
				csum += placeSums[competitor][column];
			}

			while (csum < (column + 1) * judgement.getJudges())
			{
				cidx = chooseOne(good, column, true);
				if (cidx >= 0)
				{
					placeSums[cidx][column]++;
				}
				else
				{
					cidx = chooseOne(bad, column, true);

					placeSums[cidx][column]++;
				}
				csum++;
			}
		}

	}

	private int getSum(int x, int column)
	{
		return getSum(x, column, true);
	}

	private int getSum(int x, int column, boolean dogood)
	{
		int res = 0;

		if (dogood)
		{
			res = placeSums[x][0];
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

	private IntList getSums(IntList remaining, int column)
	{
		final IntList res = new IntList();
		int max = 100;
		int cur;
		int x;

		for (int ix = 0; ix < remaining.size(); ix++)
		{
			x = remaining.get(ix);
			cur = getSum(x, column);
			if ((!calcOne || calculatedCount < 1)
			        && column < judgement.getCompetitors())
			{
				table2[x][column] = (byte) cur;
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

	public void setSkating()
	{
		calculatedCount = 0;
		calcOne = true;
	}

}
