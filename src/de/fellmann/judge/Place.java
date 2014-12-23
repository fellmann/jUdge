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

/**
 * Representation of a result place.
 * <p>
 * This class represents a place as result of a calculation. Either the place is
 * exact, or places can be shared between competitors. In this case, the place
 * range is used.
 *
 * @author Hanno Fellmann
 *
 */
public class Place implements Comparable<Place>
{
	private final int placefrom, placeto;

	private static final DecimalFormat df = new DecimalFormat("#.#");

	/**
	 * Create a new Place.
	 *
	 * @param exactPlace
	 *            The exact, not shared place.
	 */
	public Place(int exactPlace)
	{
		placefrom = exactPlace;
		placeto = exactPlace;
	}

	/**
	 * Create a new Place.
	 *
	 * @param from
	 *            Lower shared place.
	 * @param to
	 *            Higher shared place.
	 */
	public Place(int from, int to)
	{
		placefrom = from;
		placeto = to;
	}

	/**
	 * Determines if a place is shared.
	 *
	 * @return True, if the place is shared.
	 */
	public boolean isShared()
	{
		return placefrom != placeto;
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

	/**
	 * Returns the decimal value of this place.
	 * <p>
	 * If not shared, it equals the exact place. If shared, it equals the mean
	 * value of the lower and upper range.
	 * <p>
	 * For example: <br>
	 * 2.-3. place = 2.5 <br>
	 * 1.-3. place = 2
	 *
	 * @return
	 */
	public double getValue()
	{
		if (isShared())
		{
			return (placefrom + placeto) / 2.;
		}
		else
		{
			return placefrom;
		}
	}

	/**
	 * Returns the String representation of this place ({@link getValue}) as
	 * decimal value, formatted "#.#".
	 *
	 * @see getValue
	 */
	@Override
	public String toString()
	{
		return df.format(getValue());
	}

	/**
	 * Returns the representation of this place with the range.
	 * <p>
	 * Example: <br>
	 * Exact place: "2"<br>
	 * Shared place: "2-3"
	 */
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

	/**
	 * Returns the representation of this place with the range and a point.
	 * <p>
	 * Example: <br>
	 * Exact place: "2."<br>
	 * Shared place: "2.-3."
	 */
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

	/**
	 * Get the lower place range.
	 */
	public int getPlaceFrom()
	{
		return placefrom;
	}

	/**
	 * Get the higher place range.
	 */
	public int getPlaceTo()
	{
		return placeto;
	}

	/**
	 * Adds an offset to a place.
	 * 
	 * @param offset
	 *            The offset.
	 * @return A new place object.
	 */
	public Place getWithOffset(int offset)
	{
		return new Place(placefrom + offset, placeto + offset);
	}

	public int compareTo(Place o)
	{
		return Integer.compare(this.placefrom + this.placeto, o.placefrom + o.placeto);
	}
}
