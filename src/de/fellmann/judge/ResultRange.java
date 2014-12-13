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

/**
 * Represents a range of possible results.
 *
 * @author Hanno Fellmann
 *
 */
public class ResultRange
{
	private final Place minPlace, maxPlace;

	/**
	 * Creates a new result range.
	 *
	 * @param minPlace
	 *            The minimum place.
	 * @param maxPlace
	 *            The maximum place.
	 */
	public ResultRange(Place minPlace, Place maxPlace)
	{
		this.minPlace = minPlace;
		this.maxPlace = maxPlace;
	}

	/**
	 * Return the minimum place.
	 *
	 * @return the lowest possible place.
	 */
	public Place getMinPlace()
	{
		return minPlace;
	}

	/**
	 * Return the maximum place.
	 *
	 * @return the highest possible place.
	 */
	public Place getMaxPlace()
	{
		return maxPlace;
	}

	/**
	 * Determines, if the place is exact.
	 * <p>
	 * This is the case, if lower and upper place are equal.
	 */
	public boolean isExact()
	{
		return maxPlace.equals(minPlace);
	}
}
