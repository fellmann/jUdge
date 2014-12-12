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

package de.fellmann.common;

import java.util.ArrayList;

/**
 * ArrayList with special functions for lists of type int.
 *
 * @author Hanno Fellmann
 *
 */
public class IntList
{
	final ArrayList<Integer> source;

	@SuppressWarnings("unchecked")
	private IntList(IntList toClone)
	{
		source = (ArrayList<Integer>) toClone.source.clone();
	}

	public static IntList getFromTo(int from, int to)
	{
		final IntList all = new IntList();

		for (int i = from; i <= to; i++)
		{
			all.add(i);
		}
		return all;
	}

	public IntList byval()
	{
		return new IntList(this);
	}

	public int indexOf(int x)
	{
		for (int i = 0; i < source.size(); i++)
		{
			if (source.get(i).equals(x))
			{
				return i;
			}
		}
		return -1;
	}

	public IntList()
	{
		source = new ArrayList<Integer>();
	}

	public void addbegin(int x)
	{
		source.add(0, x);
	}

	public void add(int x)
	{
		source.add(x);
	}

	public int get(int i)
	{
		return source.get(i);
	}

	public int size()
	{
		return source.size();
	}

	public void remove(int x)
	{
		final int i = indexOf(x);
		source.remove(i);
	}

	public void removeIndex(int i)
	{
		source.remove(i);
	}

	public void removeAll(IntList v)
	{
		for (int i = 0; i < v.size(); i++)
		{
			remove(v.get(i));
		}
	}

	public void clear()
	{
		source.clear();
	}

	public boolean contains(int x)
	{
		return (indexOf(x) >= 0);
	}
}