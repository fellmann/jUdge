
package de.fellmann.judge.skating;

import java.text.DecimalFormat;

public class TableEntry
{
	private final double val1, val2;

	private static DecimalFormat df = new DecimalFormat("#.#");

	public double getVal1()
	{
		return val1;
	}

	public double getVal2()
	{
		return val2;
	}

	public TableEntry(double val1, double val2)
	{
		super();
		this.val1 = val1;
		this.val2 = val2;
	}

	@Override
	public String toString()
	{
		if (val1 >= 0)
		{
			if (val2 >= 0)
			{
				return df.format(val1) + " (" + df.format(val2) + ")";
			}
			else
			{
				return String.valueOf(df.format(val1));
			}
		}
		return "";
	}
}
