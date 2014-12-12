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

package de.fellmann.judge;

import java.text.DecimalFormat;

public class Place
{
	private final int placefrom, placeto;

	private static final DecimalFormat df = new DecimalFormat("#.#");

	public Place(int from, int to)
	{
		placefrom = from;
		placeto = to;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + placefrom;
		result = prime * result + placeto;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Place other = (Place) obj;
		if (placefrom != other.placefrom)
		{
			return false;
		}
		if (placeto != other.placeto)
		{
			return false;
		}
		return true;
	}

	public Place(int exactPlace)
	{
		placefrom = exactPlace;
		placeto = exactPlace;
	}

	public double getValue()
	{
		return (placefrom + placeto) / 2.;
	}

	@Override
	public String toString()
	{
		return df.format(getValue());
	}

	public String toStringFromTo()
	{
		if (placefrom != placeto)
		{
			return placefrom + "-" + placeto;
		}
		else
		{
			return placefrom + "";
		}
	}

	public String toStringFromToPoint()
	{
		if (placefrom != placeto)
		{
			return placefrom + ".-" + placeto + ".";
		}
		else
		{
			return placefrom + ".";
		}
	}

	public int getPlaceFrom()
	{
		return placefrom;
	}

	public int getPlaceTo()
	{
		return placeto;
	}
}
