package de.fellmann.judge.skating;

public class Place {
	private int placefrom, placeto;
	
	public Place(int from, int to) {
		placefrom = from;
		placeto = to;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + placefrom;
		result = prime * result + placeto;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (placefrom != other.placefrom)
			return false;
		if (placeto != other.placeto)
			return false;
		return true;
	}

	public Place(int exactPlace) {
		placefrom = exactPlace;
		placeto = exactPlace;
	}
	
	public double getPlace() {
		return (placefrom + placeto)/2;
	}
	
	public String toString() {
		return toStringFromTo();
	}
	
	public String toStringFromTo() {
		if(placefrom!=placeto) {
			return placefrom + "-" + placeto;
		}
		else {
			return placefrom+"";
		}
	}
	
	public String toStringFromToPoint() {
		if(placefrom!=placeto) {
			return placefrom + ".-" + placeto+".";
		}
		else {
			return placefrom+".";
		}
	}
	
	public int getPlaceFrom() {
		return placefrom;
	}
	
	public int getPlaceTo() {
		return placeto;
	}
}
