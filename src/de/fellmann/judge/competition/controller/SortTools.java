package de.fellmann.judge.competition.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.data.Competitor;

public class SortTools
{
	public static void getPlacesByOrder(ArrayList<Competitor> competitors, Comparator<Competitor> comparator, int placeOffset, HashMap<Competitor, Place> result)
	{
		ArrayList<Competitor> toSort = new ArrayList<Competitor>(competitors);
		Collections.sort(toSort, comparator);

		int count = 1;
		for(int i=0;i<toSort.size();i+=count)
		{
			count = 1;
			while(toSort.size() > i + count)
			{
				if (comparator.compare(toSort.get(i), toSort.get(i+count)) == 0)
				{
					count++;
				}
				else
				{
					break;
				}
			}
			
			for(int j=i;j<i+count;j++)
			{
				result.put(toSort.get(j), new Place(i+placeOffset+1, i+count+placeOffset));
			}
		}
	}

	public static <T> void putOrAdd(Map<T, Integer> map, T key, int value)
	{
		Integer oldValue = map.get(key);
		if (oldValue == null)
		{
			map.put(key, value);
		}
		else
		{
			map.put(key, value + oldValue);
		}
	}
}
