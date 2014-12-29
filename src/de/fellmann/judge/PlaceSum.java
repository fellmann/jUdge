package de.fellmann.judge;


public class PlaceSum implements Comparable<PlaceSum>
{
	public int sum = 0;
	
	public static PlaceSum createOrAdd(PlaceSum last, Place add)
	{
		if(last == null)
		{
			PlaceSum sum = new PlaceSum();
			sum.add(add);
			return sum;
		}
		else
		{
			last.add(add);
			return last;
		}
	}
	
	public static int compare(PlaceSum p1, PlaceSum p2) {
		return Integer.compare(p1.sum, p2.sum);
	}
	
	public void add(Place place)
	{
		if(place != null)
			sum += place.getPlaceFrom() + place.getPlaceTo();
	}
	
	public double getValue() 
	{
		if(sum % 2 == 1)
			return sum / 2.0;
		else
			return sum / 2;
	}
	
	public int getSortValue()
	{
		return sum;
	}

	public int compareTo(PlaceSum o)
	{
		return Integer.compare(sum, o.sum);
	}
}
