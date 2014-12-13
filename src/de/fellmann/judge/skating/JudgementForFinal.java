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

import de.fellmann.judge.exceptions.JudgingException;
import de.fellmann.judge.exceptions.MarkOutOfRangeException;

/**
 * Judgement input data object for Skating system, including input validation.
 * <p>
 * Represents all marks of a final round.
 *
 * @author Hanno Fellmann
 *
 */
public class JudgementForFinal
{
	private boolean allowAbort = false;
	final private int competitors;
	final private int judges;
	final private int dances;
	final private byte[][][] marks;
	final private boolean[][][] marksValid;
	private boolean aborted = false;
	private boolean dirtyFlagValidation = false;

	/**
	 * Creates a new input data object.
	 *
	 * @param dances
	 *            Count of dances.
	 * @param competitors
	 *            Count of competitors.
	 * @param judges
	 *            Count of judges.
	 */
	public JudgementForFinal(int dances, int competitors, int judges)
	{
		this.dances = dances;
		this.competitors = competitors;
		this.judges = judges;

		marks = new byte[dances][competitors][judges];
		marksValid = new boolean[dances][competitors][judges];
	}

	/**
	 * The count of competitors participating in this final.
	 *
	 * @return The count of competitors.
	 */
	public int getCompetitors()
	{
		return competitors;
	}

	/**
	 * The count of judges participating in this final.
	 *
	 * @return The count of judges.
	 */
	public int getJudges()
	{
		return judges;
	}

	/**
	 * The count of dances this final consists of.
	 *
	 * @return The count of dances.
	 */
	public int getDances()
	{
		return dances;
	}

	/**
	 * Set a shown mark
	 *
	 * @param dance
	 *            Index of the dance.
	 * @param competitor
	 *            Index of the competitor.
	 * @param judge
	 *            Index of the judge.
	 * @param value
	 *            Shown mark.
	 *            <p>
	 *            Possible values: 0 = not set, 1 to competitor count = mark
	 *
	 * @throws JudgingException
	 *             when input is of range.
	 */
	public synchronized void setMark(int dance, int competitor, int judge, Byte value)
	{
		checkBounds(dance, competitor, judge);

		if (value >= 0 && value <= competitors)
		{
			if (marks[dance][competitor][judge] != value)
			{
				marks[dance][competitor][judge] = value;
				dirtyFlagValidation = true;
			}
		}
		else
		{
			throw new MarkOutOfRangeException("Input " + value
					+ " out of range!");
		}
	}

	/**
	 * Get a shown mark
	 *
	 * @param dance
	 *            Index of the dance.
	 * @param competitor
	 *            Index of the competitor.
	 * @param judge
	 *            Index of the judge.
	 * @return Shown mark.
	 *         <p>
	 *         Possible values: 0 = not set, 1 to competitor count = mark
	 *
	 */
	public byte getMark(int dance, int competitor, int judge)
	{
		checkBounds(dance, competitor, judge);

		return marks[dance][competitor][judge];
	}

	/**
	 * Determine if the judgement for one competitor in a specific dance is
	 * valid.
	 *
	 * @param dance
	 *            Index of the dance.
	 * @param competitor
	 *            Index of the competitor.
	 * @return true, if the judgement input is valid.
	 *
	 */
	public boolean isValid(int dance, int competitor)
	{
		for (int j = 0; j < judges; j++)
		{
			if (!isValid(dance, competitor, j))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Determine if the judgement for a whole dance is valid.
	 *
	 * @param dance
	 *            Index of the dance.
	 * @return true, if the judgement input is valid.
	 *
	 */
	public boolean isValid(int dance)
	{
		for (int c = 0; c < competitors; c++)
		{
			if (!isValid(dance, c))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Determine if the whole judgement is valid.
	 *
	 * @return true, if the judgement input is valid.
	 *
	 */
	public boolean isValid()
	{
		for (int d = 0; d < dances; d++)
		{
			if (!isValid(d))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Determine if a specific mark is valid.
	 *
	 * @param dance
	 *            Index of the dance.
	 * @param competitor
	 *            Index of the competitor.
	 * @param judge
	 *            Index of the judge.
	 * @return true, if the judgement input is valid.
	 *
	 */
	public boolean isValid(int dance, int competitor, int judge)
	{
		validateMarks();

		return marksValid[dance][competitor][judge];
	}

	/**
	 * Check if input is only valid when accepting abortion judgement.
	 *
	 * @see setAllowAbort
	 * @return True, if abortion judgement has been detected.
	 */
	public boolean isAborted()
	{
		return aborted;
	}

	/**
	 * Returns if abortion judgement is allowed.
	 *
	 * @return True, if allowed.
	 * @see setAllowAbort
	 */
	public boolean isAllowAbort()
	{
		return allowAbort;
	}

	/**
	 * Set abortion judgement allowance.
	 * <p>
	 * If several participants abort their performance, it might happen that all
	 * these couples get judged with the lowest possible mark. So, in a final of
	 * 6 competitors, if two abort their performance, both can be judged with
	 * the mark "6". <br>
	 * This is by default not allowed by input validation. To accept such input,
	 * allowAbort must be set to true explicitely.
	 *
	 * @param allowAbort
	 *            true to allow abortion input.
	 */
	public void setAllowAbort(boolean allowAbort)
	{
		this.allowAbort = allowAbort;
	}

	/**
	 * Returns the judgement for a competitor in a specific dance.
	 *
	 * @param dance
	 *            Index of the dance.
	 * @param competitor
	 *            Index of the competitor.
	 * @return The judgement, for example "11232".
	 */
	public String toString(int dance, int competitor)
	{
		final StringBuilder result = new StringBuilder();
		for (int j = 0; j < judges; j++)
		{
			result.append(getMark(dance, competitor, j));
		}
		return result.toString();
	}

	private void checkBounds(int dance, int competitor, int judge)
	{
		if (dance < 0 || competitor < 0 || judge < 0 || dance >= dances
				|| competitor >= competitors || judge >= judges)
		{
			throw new IndexOutOfBoundsException();
		}
	}

	private synchronized void validateMarks()
	{
		if (dirtyFlagValidation)
		{
			dirtyFlagValidation = false;

			for (int dance = 0; dance < dances; dance++)
			{

				int countLast;
				int aw;
				final int wantedCounts[] = new int[competitors + 1];
				aborted = false;

				for (int judge = 0; judge < judges; judge++)
				{
					countLast = 0;

					for (int competitor = 0; competitor < competitors; competitor++)
					{
						if (marks[dance][competitor][judge] == competitors)
						{
							countLast++;
						}
					}
					if (countLast > 1)
					{
						aborted = true;
						if (!isAllowAbort())
						{
							countLast = 1;
						}
					}
					for (int t = 1; t <= competitors - 1; t++)
					{
						if (t <= competitors - countLast)
						{
							wantedCounts[t] = 1;
						}
						else
						{
							wantedCounts[t] = 0;
						}
					}
					wantedCounts[competitors] = countLast;

					for (int t = 0; t < competitors; t++)
					{
						aw = marks[dance][t][judge];
						if (aw > 0)
						{
							wantedCounts[aw]--;
						}
					}
					for (int t = 0; t < competitors; t++)
					{
						aw = marks[dance][t][judge];
						marksValid[dance][t][judge] = false;
						if (aw > 0)
						{
							if (wantedCounts[aw] >= 0)
							{
								marksValid[dance][t][judge] = true;
							}
						}
					}

				}
			}
		}
	}
}
