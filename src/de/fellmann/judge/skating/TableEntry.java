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

import java.text.DecimalFormat;

/**
 * Class representing a cell entry in a calculation table for Majority
 * calculation or Skating calculation.
 * <p>
 * One cell uses the format "val1 (val2)", with val1 representing for example
 * the count of places, and val2 representing the digit sum of places.
 *
 * @author Hanno Fellmann
 *
 */
public class TableEntry
{
	private final double val1, val2;

	private static DecimalFormat df = new DecimalFormat("#.#");

	/**
	 * Returns the first value.
	 *
	 * @return The first value.
	 */
	public double getVal1()
	{
		return val1;
	}

	/**
	 * Returns the second value.
	 *
	 * @return The second value.
	 */
	public double getVal2()
	{
		return val2;
	}

	/**
	 * Creates a new TableEntry.
	 * 
	 * @param val1
	 *            The first value.
	 * @param val2
	 *            The second value.
	 */
	public TableEntry(double val1, double val2)
	{
		super();
		this.val1 = val1;
		this.val2 = val2;
	}

	/**
	 * Converts the table cell to String representation
	 * <p>
	 * If val2 and val1 are set: "val1 (val2)" <br>
	 * If only val1 is set: "val1" <br>
	 * Else: ""
	 * <p>
	 * Decimals are each formatted as "#.#"
	 */
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
