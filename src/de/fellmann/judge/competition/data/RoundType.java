
package de.fellmann.judge.competition.data;


public enum RoundType {
	Qualification(true),
	Redance(false),
	SmallFinal(false),
	Final(true),
	End(true);
	
	private final boolean qualifying;
	
	private RoundType(boolean qualifying)
	{
		this.qualifying = qualifying;
	}

	public boolean isQualifying()
	{
		return qualifying;
	}
}
