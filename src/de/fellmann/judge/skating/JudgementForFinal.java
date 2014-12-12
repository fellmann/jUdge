
package de.fellmann.judge.skating;

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

	public JudgementForFinal(int dances, int competitors, int judges)
	{
		this.dances = dances;
		this.competitors = competitors;
		this.judges = judges;

		marks = new byte[dances][competitors][judges];
		marksValid = new boolean[dances][competitors][judges];
	}

	public int getCompetitors()
	{
		return competitors;
	}

	public int getJudges()
	{
		return judges;
	}

	public int getDances()
	{
		return dances;
	}

	/**
	 * Set a shown mark
	 *
	 * @param dance
	 *            Index of the dance
	 * @param competitor
	 *            Index of the competitor
	 * @param judge
	 *            Index of the judge
	 * @param value
	 *            Shown mark. Possible values: 0 = not set, 1 to competitor
	 *            count = mark
	 *
	 * @throws JudgingException
	 *             when input is of range.
	 */
	public synchronized void setMark(int dance, int competitor, int judge, Byte value)
	{
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
			throw new JudgingException("Input out of range!");
		}
	}

	public byte getMark(int dance, int competitor, int judge)
	{
		return marks[dance][competitor][judge];
	}

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

	public boolean isValid(int dance, int competitor, int judge)
	{
		synchronized (this)
		{
			validateMarks();
		}

		return marksValid[dance][competitor][judge];
	}

	private synchronized void validateMarks()
	{
		for (int dance = 0; dance < dances; dance++)
		{

			int countLast;
			int aw;
			final int wantedCounts[] = new int[competitors + 1];
			aborted = false;

			if (dirtyFlagValidation)
			{
				dirtyFlagValidation = false;
			}

			for (int w = 0; w < judges; w++)
			{
				countLast = 0;

				for (int t = 0; t < competitors; t++)
				{
					if (marks[dance][t][w] == competitors)
					{
						countLast++;
					}
				}
				if (countLast > 1)
				{
					aborted = false;
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
					aw = marks[dance][t][w];
					if (aw > 0)
					{
						wantedCounts[aw]--;
					}
				}
				for (int t = 0; t < competitors; t++)
				{
					aw = marks[dance][t][w];
					marksValid[dance][t][w] = false;
					if (aw > 0)
					{
						if (wantedCounts[aw] >= 0)
						{
							marksValid[dance][t][w] = true;
						}
					}
				}

			}
		}
	}

	public boolean isAborted()
	{
		return aborted;
	}

	public boolean isAllowAbort()
	{
		return allowAbort;
	}

	public void setAllowAbort(boolean allowAbort)
	{
		this.allowAbort = allowAbort;
	}

	public String getAsString(int dance, int competitor)
	{
		final StringBuilder result = new StringBuilder();
		for (int j = 0; j < judges; j++)
		{
			result.append(getMark(dance, competitor, j));
		}
		return result.toString();
	}
}
