
package de.fellmann.judge.competition.round;

public enum RoundType {
	Start(StartRound.class),
	Qualification(QualificationRound.class),
	SmallFinal(StartRound.class),
	Final(StartRound.class),
	Result(StartRound.class);

	private final Class<? extends Runde> implementor;

	private RoundType(Class<? extends Runde> implementor)
	{
		this.implementor = implementor;
	}

	public Class<? extends Runde> getImplementor()
	{
		return implementor;
	}
}
