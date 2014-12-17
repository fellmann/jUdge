
package de.fellmann.judge.competition.round;

import java.text.DecimalFormat;
import java.util.Vector;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.Turnier;
import de.fellmann.judge.skating.Calculator;
import de.fellmann.judge.skating.JudgementForFinal;

public class Finale extends Runde
{
	public Finale(int index, Turnier t, Runde letzte)
	{
		super(index, t, letzte);
	}

	private int rundenIndex;
	protected Turnier turnier;
	protected byte preQualified[];
	private byte auslosung[];
	private byte auslosung_rev[];
	private String possible[];
	protected byte preQualifiedRev[];
	protected boolean disqualified[], qualified[];
	private Runde letzteRunde;

	private Place platz[];
	private byte wertung[][][];
	private boolean wertungOk[][][];
	JudgementForFinal judgement;
	Calculator mySkating;
	private boolean noAbort = true;
	private boolean doKreuze = false;
	private byte[][] summen;
	private byte[] summe;
	private byte minKreuze;
	private byte[][] summew;

	private boolean allowAbort = false;
	private final DecimalFormat df = new DecimalFormat("#.#");

	@Override
	public void setAllowAbort(boolean allow)
	{
		allowAbort = allow;
		for (int dance = 0; dance < turnier.getStartklasse().getDances(); dance++)
		{
			CalcWertungOk(dance);
		}
	}

	@Override
	public boolean getAllowAbort()
	{
		return allowAbort;
	}

	@Override
	public int getTableSize()
	{
		return getPreQualifiedCount();
	}

	@Override
	public int getPreQualifiedCount()
	{
		if (preQualified != null)
		{
			return preQualified.length;
		}
		else
		{
			return turnier.getTeilnehmerCount();
		}
	}

	@Override
	public void setAuslosungStartNr(byte t[])
	{
		auslosung = new byte[getPreQualifiedCount()];
		auslosung_rev = new byte[getPreQualifiedCount()];

		for (byte i = 0; i < getPreQualifiedCount(); i++)
		{
			// TODO: findTeilnehmer abprüfen >= 0
			auslosung_rev[i] = preQualifiedRev[turnier.findTeilnehmer(t[i])];
			auslosung[preQualifiedRev[turnier.findTeilnehmer(t[i])]] = i;
		}
	}

	@Override
	public boolean setAuslosungRev(byte t[])
	{
		auslosung = new byte[getPreQualifiedCount()];
		auslosung_rev = new byte[getPreQualifiedCount()];
		try
		{
			for (byte i = 0; i < getPreQualifiedCount(); i++)
			{
				auslosung_rev[i] = t[i];
				auslosung[t[i]] = i;
			}
		}
		catch (final Exception e)
		{

		}
		return checkAuslosung();
	}

	@Override
	public void clearAuslosung()
	{
		auslosung = null;
		auslosung_rev = null;
	}

	@Override
	public boolean setAuslosung(byte a[])
	{
		auslosung = new byte[getPreQualifiedCount()];
		auslosung_rev = new byte[getPreQualifiedCount()];
		try
		{
			for (byte i = 0; i < getPreQualifiedCount(); i++)
			{
				auslosung[i] = a[i];
				auslosung_rev[a[i]] = i;
			}
		}
		catch (final Exception e)
		{

		}
		return checkAuslosung();
	}

	private boolean checkAuslosung()
	{
		final boolean test[] = new boolean[getPreQualifiedCount()];
		try
		{
			for (byte i = 0; i < getPreQualifiedCount(); i++)
			{
				if (!test[auslosung[i]])
				{
					test[auslosung[i]] = true;
				}
				else
				{
					auslosung = null;
					auslosung_rev = null;
					return false;
				}
			}
		}
		catch (final Exception e)
		{
			return false;
		}
		return true;
	}

	@Override
	public int getAuslosung(int t)
	{
		if (auslosung != null && preQualifiedRev != null)
		{
			return auslosung[preQualifiedRev[t]];
		}
		else
		{
			return preQualifiedRev[t];
		}
	}

	@Override
	public int getAuslosungRev(int a)
	{
		if (auslosung != null && preQualified != null)
		{
			return preQualified[auslosung_rev[a]];
		}
		else
		{
			return preQualified[a];
		}
	}

	private String getTableEntry(double val[], int skating)
	{
		if (val[0] >= 0)
		{
			if (val[1] >= 0)
			{
				if (skating == 10)
				{
					return String.valueOf(val[0]) + " (" + dformat(val[1])
					        + ")";
				}
				else
				{
					return String.valueOf(val[0]) + " ("
					        + String.valueOf(val[1]) + ")";
				}
			}
			else
			{
				if (val[0] == 0)
				{
					return "-";
				}
				else
				{
					return String.valueOf(val[0]);
				}
			}
		}
		if (skating == 0)
		{
			return "---";
		}
		else
		{
			return "";
		}
	}

	@Override
	public String getSkatingTable11(int x, int y)
	{
		if (mySkating != null)
		{
			return mySkating.getTable11(y, x).toString();
		}

		return "";
	}

	@Override
	public String getSkatingTable10(int x, int y)
	{
		if (mySkating != null)
		{
			return mySkating.getTable10(y, x).toString();
		}

		return "";
	}

	@Override
	public String getMajorTable(int dance, int x, int y)
	{
		final String res = "";

		if (mySkating != null)
		{
			return mySkating.getMajorTable(dance, y, x).toString();
		}

		return res;
	}

	private void init(int index, Turnier t, Runde letzte)
	{
		rundenIndex = index;
		turnier = t;
		letzteRunde = letzte;
		this.qualified = new boolean[t.getCompetitors().length];
		this.disqualified = new boolean[t.getCompetitors().length];
		minKreuze = (byte) t.getMajority();

		platz = new Place[t.getCompetitors().length];
		possible = new String[t.getCompetitors().length];
		if (rundenIndex == RoundType.SmallFinal)
		{
			preQualified = letzte.getLastQualifying().getOnlyNotQualified();
		}
		else if (rundenIndex != RoundType.Start)
		{
			preQualified = letzte.getLastQualifying().getOnlyQualified();
			wertung = new byte[t.getStartklasse().getDances()][preQualified.length][t.getWertungsrichter()];
			summew = new byte[t.getStartklasse().getDances()][t.getWertungsrichter()];
			summen = new byte[t.getStartklasse().getDances()][preQualified.length];
			summe = new byte[preQualified.length];
		}
		if (rundenIndex == RoundType.SmallFinal
		        || rundenIndex == RoundType.Final)
		{
			wertung = new byte[t.getStartklasse().getDances()][preQualified.length][t.getWertungsrichter()];
			wertungOk = new boolean[t.getStartklasse().getDances()][preQualified.length][t.getWertungsrichter()];
			judgement = new JudgementForFinal(t.getStartklasse().getDances(),
			        preQualified.length, t.getWertungsrichter());
			/*
			 * try { Import(System.in); } catch (IOException e) {
			 * e.printStackTrace(); }
			 */
		}
		else if (rundenIndex >= RoundType.Zwischenrunde[0])
		{
			if (letzteRunde.getRundenIndex() == RoundType.Qualification)
			{
				rundenIndex = RoundType.Zwischenrunde[0];
			}
			else
			{
				rundenIndex = letzteRunde.getRundenIndex() + 1;
			}
		}

		judgement = new JudgementForFinal(t.getStartklasse().getDances(),
				getPreQualifiedCount(), t.getWertungsrichter());
		mySkating = new Calculator(judgement);

		if (preQualified != null)
		{
			preQualifiedRev = new byte[t.getCompetitors().length];
			for (byte i = 0; i < preQualified.length; i++)
			{
				preQualifiedRev[preQualified[i]] = i;
			}
		}
	}

	public Runde(int index, Turnier t, Runde letzte)
	{
		init(index, t, letzte);
	}

	@Override
	public byte[] getPreQualified()
	{
		return preQualified;
	}

	@Override
	public void setWertung(int dance, int team, int wertungsrichter, int value)
	{
		if (dance < turnier.getStartklasse().getDances()
		        && wertungsrichter < turnier.getWertungsrichter()
		        && value <= turnier.getCompetitors().length
		        && team < turnier.getCompetitors().length && dance >= 0
		        && wertungsrichter >= 0 && team >= 0 && value >= 0)
		{
			wertung[dance][preQualifiedRev[team]][wertungsrichter] = (byte) value;
			CalcWertungOk(dance);
			doKreuze = true;
		}
	}

	private boolean isSet(int team, int dance)
	{
		int wcount = 0;
		for (int w = 0; w < turnier.getWertungsrichter(); w++)
		{
			if (getWertung(dance, team)[w] > 0)
			{
				wcount++;
			}
		}
		return (wcount == turnier.getWertungsrichter());
	}

	@Override
	public boolean isComplete(int dance)
	{
		for (int i = 0; i < preQualified.length; i++)
		{
			if (!isOk(dance, preQualified[i]))
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isComplete()
	{
		for (int i = 0; i < turnier.getStartklasse().getDances(); i++)
		{
			if (!isComplete(i))
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean noAborts()
	{
		return noAbort;
	}

	@Override
	public Vector<Integer> getPossibleNextRounds()
	{
		final Vector<Integer> next = new Vector<Integer>();

		if (rundenIndex == RoundType.Start && getQualifiedCount() > 0)
		{
			next.add(RoundType.Qualification);
		}
		else if (getQualifiedCount() > 4)
		{
			int zidx = RoundType.Zwischenrunde[0];
			if (rundenIndex >= RoundType.Zwischenrunde[0])
			{
				zidx = rundenIndex + 1;
			}
			next.add(zidx);
		}

		if (turnier.getStartklasse().getDances() == 1
		        && rundenIndex == RoundType.Qualification
		        && getQualifiedCount() <= 7 && getNotQualifiedCount() > 1
		        && getQualifiedCount() > 2)
		{
			next.add(RoundType.SmallFinal);
		}

		if (rundenIndex == RoundType.Final)
		{
			next.add(RoundType.Result);
		}
		else if (rundenIndex == RoundType.SmallFinal
		        || (getQualifiedCount() <= 7 && getQualifiedCount() > 2))
		{
			next.add(RoundType.Final);
		}

		return next;
	}

	@Override
	public void preCalc(int dance)
	{
		final int offset = 0;
		boolean doCalc = false;

		if (rundenIndex == RoundType.SmallFinal
		        || rundenIndex == RoundType.Final)
		{

			for (int t = 0; t < preQualified.length; t++)
			{
				if (isOk(dance, preQualified[t]))
				{
					for (int j = 0; j < turnier.getWertungsrichter(); j++)
					{
						judgement.setMark(dance, t, j, wertung[dance][t][j]);
					}
					doCalc = true;
				}
			}
			if (doCalc && noAbort)
			{
				mySkating.recalculate();
			}

			/*
			 * if(rundenIndex == KleinesFinale) { offset =
			 * letzteRunde.getQualifiedCount(); }
			 */
			for (int t = 0; t < preQualified.length; t++)
			{
				if (isOk(dance, preQualified[t]) && noAbort)
				{
					if (mySkating.getPossibleResult(dance, t).getMinPlace().getValue() < mySkating.getPossibleResult(dance, t).getMaxPlace().getValue())
					{
						https: // github.com/fellmann/jUdge/wiki/Project-Description
						possible[preQualified[t]] = dformat(mySkating.getPossibleResult(dance, t).getMinPlace().getValue()
						        + offset)
						        + ". - "
						        + dformat(mySkating.getPossibleResult(dance, t).getMaxPlace().getValue()
						                + offset) + ".";
					}
					else
					{
						possible[preQualified[t]] = dformat(mySkating.getPossibleResult(dance, t).getMinPlace().getValue()
						        + offset)
						        + "";
					}
				}
				else
				{
					possible[preQualified[t]] = "";
				}
			}
		}
		else if (doKreuze)
		{
			for (int w = 0; w < turnier.getWertungsrichter(); w++)
			{
				summew[dance][w] = 0;
			}
			for (int i = 0; i < getPreQualifiedCount(); i++)
			{
				summen[dance][i] = 0;
				for (int w = 0; w < turnier.getWertungsrichter(); w++)
				{
					summen[dance][i] += wertung[dance][i][w];
					summew[dance][w] += wertung[dance][i][w];
				}
			}
		}
	}

	private String dformat(double val)
	{
		return df.format(val);
	}

	@Override
	public String getPossible(int team)
	{
		if (possible[team] != null)
		{
			return possible[team];
		}
		else
		{
			return "";
		}
	}

	@Override
	public int getWertung(int dance, int team, int wertungsrichter)
	{
		if (dance < turnier.getStartklasse().getDances()
		        && wertungsrichter < turnier.getWertungsrichter()
		        && team < turnier.getCompetitors().length && dance >= 0
		        && wertungsrichter >= 0 && team >= 0)
		{
			return wertung[dance][preQualifiedRev[team]][wertungsrichter];
		}
		else
		{
			return 0;
		}
	}

	@Override
	public byte[] getWertung(int dance, int team)
	{
		if (dance < turnier.getStartklasse().getDances()
		        && team < turnier.getCompetitors().length && dance >= 0
		        && team >= 0)
		{
			if (wertung == null || wertung[dance] == null
			        || preQualifiedRev == null)
			{
				System.out.println("dumm");
			}
			return wertung[dance][preQualifiedRev[team]];
		}
		else
		{
			return new byte[0];
		}
	}

	@Override
	public boolean isOk(int dance, int team)
	{
		for (int w = 0; w < turnier.getWertungsrichter(); w++)
		{
			if (!isWertungOk(dance, team, w))
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isWertungOk(int dance, int team, int wertungsrichter)
	{
		if (dance < turnier.getStartklasse().getDances()
		        && wertungsrichter < turnier.getWertungsrichter()
		        && team < turnier.getCompetitors().length && dance >= 0
		        && wertungsrichter >= 0 && team >= 0)
		{
			final int myTeam = preQualifiedRev[team];
			return wertungOk[dance][myTeam][wertungsrichter];
		}
		return false;
	}

	private void CalcWertungOk(int dance)
	{
		final int countTeams = wertung[0].length;
		int countLast;
		int aw;
		final int wantedCounts[] = new int[countTeams + 1];
		noAbort = true;

		if (wertungOk == null)
		{
			return;
		}

		for (int w = 0; w < turnier.getWertungsrichter(); w++)
		{
			countLast = 0;

			for (int t = 0; t < countTeams; t++)
			{
				if (wertung[dance][t][w] == countTeams)
				{
					countLast++;
				}
			}
			if (countLast > 1)
			{
				noAbort = false;
				if (!allowAbort)
				{
					countLast = 1;
				}
			}
			for (int t = 1; t <= countTeams - 1; t++)
			{
				if (t <= countTeams - countLast)
				{
					wantedCounts[t] = 1;
				}
				else
				{
					wantedCounts[t] = 0;
				}
			}
			wantedCounts[countTeams] = countLast;

			for (int t = 0; t < countTeams; t++)
			{
				aw = wertung[dance][t][w];
				if (aw > 0)
				{
					wantedCounts[aw]--;
				}
			}
			for (int t = 0; t < countTeams; t++)
			{
				aw = wertung[dance][t][w];
				wertungOk[dance][t][w] = false;
				if (aw > 0)
				{
					if (wantedCounts[aw] >= 0)
					{
						wertungOk[dance][t][w] = true;
					}
				}
			}

		}
	}

	@Override
	public boolean isWertungOk(int dance, int team, int wertungsrichter, int wertung)
	{
		if (dance < turnier.getStartklasse().getDances()
		        && wertungsrichter < turnier.getWertungsrichter()
		        && team < turnier.getCompetitors().length && dance >= 0
		        && wertungsrichter >= 0 && team >= 0)
		{
			final int myTeam = preQualifiedRev[team];
			if (wertung > 0)
			{

				for (int t = 0; t < this.wertung[0].length; t++)
				{
					if (t != myTeam)
					{
						if (wertung == this.wertung[dance][t][wertungsrichter])
						{
							return false;
						}
					}
				}

				return true;
			}
		}
		return false;
	}

	@Override
	public void tryAutoComplete(int dance)
	{
		if (dance < turnier.getStartklasse().getDances() && dance >= 0)
		{
			for (int w = 0; w < turnier.getWertungsrichter(); w++)
			{
				int wcount = 0, emptyIdx = -1;
				final boolean empty[] = new boolean[preQualified.length + 1];
				for (int t = 0; t < preQualified.length + 1; t++)
				{
					empty[t] = true;
				}
				for (int t = 0; t < preQualified.length; t++)
				{
					if (getWertung(dance, preQualified[t], w) > 0)
					{
						if (empty[getWertung(dance, preQualified[t], w)])
						{
							wcount++;
						}
						empty[getWertung(dance, preQualified[t], w)] = false;
					}
					else
					{
						emptyIdx = t;
					}
				}

				if (emptyIdx >= 0)
				{
					if (wcount == preQualified.length - 1)
					{
						for (int t = 1; t <= preQualified.length; t++)
						{
							if (empty[t])
							{
								setWertung(dance, preQualified[emptyIdx], w, t);
								break;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public Runde getLetzteRunde()
	{
		return letzteRunde;
	}

	@Override
	public int getMinRundenPlace()
	{
		return getQualifiedCount() + 1;
	}

	@Override
	public int getMaxRundenPlace()
	{
		return getQualifiedCount() + getNotQualifiedCount();
	}

	@Override
	public void calc()
	{
		int i;
		if (rundenIndex == RoundType.SmallFinal
		        || rundenIndex == RoundType.Final)
		{
			mySkating.recalculate();
			if (rundenIndex == RoundType.SmallFinal)
			{
				letzteRunde.getQualifiedCount();
			}
			for (int t = 0; t < preQualified.length; t++)
			{
				platz[preQualified[t]] = mySkating.getResult(t);
			}
			if (getDisqualifiedCount() > 0)
			{
				for (int t = 0; t < preQualified.length; t++)
				{
					final int tp = preQualified[t];
					for (int td = 0; td < preQualified.length; td++)
					{
						final int tdp = preQualified[td];
						if (!getDisqualified(tp)
						        && getDisqualified(tdp)
						        && (getPlace(tdp).getValue() < getPlace(tp).getValue()))
						{
							platz[tp] = platz[tp].getWithOffset(-1);
						}
					}
				}
				for (int t = 0; t < preQualified.length; t++)
				{
					if (getDisqualified(preQualified[t]))
					{
						platz[preQualified[t]] = new Place(
						        turnier.getTeilnehmerCount(),
						        turnier.getTeilnehmerCount());
					}
				}
			}

		}
		else if (rundenIndex == RoundType.Qualification
		        || rundenIndex >= RoundType.Zwischenrunde[0])
		{
			if (doKreuze)
			{
				for (int dance = 0; dance < turnier.getStartklasse().getDances(); dance++)
				{
					preCalc(dance);
				}
				for (i = 0; i < getPreQualifiedCount(); i++)
				{
					summe[i] = 0;
					for (int dance = 0; dance < turnier.getStartklasse().getDances(); dance++)
					{
						summe[i] += summen[dance][i];
					}

					qualified[preQualified[i]] = (summe[i] >= minKreuze);
				}

				int ksum = 0, uplatz = getMaxRundenPlace();
				Place kplatz;
				final Place disqualifiedp = new Place(
				        turnier.getTeilnehmerCount(),
				        turnier.getTeilnehmerCount());

				for (int kreuze = 0; kreuze <= turnier.getWertungsrichter(); kreuze++)
				{
					ksum = 0;
					for (i = 0; i < getPreQualifiedCount(); i++)
					{
						if (summe[i] == kreuze
						        && !disqualified[preQualified[i]])
						{
							ksum++;
						}
					}
					if (ksum > 0)
					{
						for (i = 0; i < getPreQualifiedCount(); i++)
						{
							kplatz = new Place(uplatz - ksum + 1, uplatz);
							if (summe[i] == kreuze)
							{
								if (!disqualified[preQualified[i]])
								{
									platz[preQualified[i]] = kplatz;
								}
								else
								{
									platz[preQualified[i]] = disqualifiedp;
								}
							}
						}
						uplatz = uplatz - ksum;
					}
				}
			}
			else
			{
				final Place weiter = new Place(1, getQualifiedCount()), nichtweiter = new Place(
				        getMinRundenPlace(), getMaxRundenPlace()), disqualifiedp = new Place(
				        turnier.getTeilnehmerCount(),
				        turnier.getTeilnehmerCount());
				for (i = 0; i < turnier.getCompetitors().length; i++)
				{
					if (platz[i] == null)
					{
						if (getQualified(i))
						{
							platz[i] = weiter;
						}
						else
						{
							if (letzteRunde.getLastQualifying().getQualified(i))
							{
								if (!disqualified[i])
								{
									platz[i] = nichtweiter;
								}
								else
								{
									platz[i] = disqualifiedp;
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public int getRundenIndex()
	{
		return rundenIndex;
	}

	@Override
	public void setRundenIndex(int rundenIndex)
	{
		this.rundenIndex = rundenIndex;
	}

	@Override
	public Turnier getTurnier()
	{
		return turnier;
	}

	@Override
	public Runde getLastQualifying()
	{
		if (rundenIndex == RoundType.SmallFinal)
		{
			return letzteRunde.getLastQualifying();
		}
		else
		{
			return this;
		}
	}

	@Override
	public void setTurnier(Turnier turnier)
	{
		this.turnier = turnier;
	}

	@Override
	public void setQualified(int index, boolean value)
	{
		qualified[index] = value;
		for (int t = 0; t < turnier.getCompetitors().length; t++)
		{
			platz[t] = null;
		}
	}

	@Override
	public boolean getQualified(int i)
	{
		return qualified[i] && !disqualified[i];
	}

	@Override
	public boolean getPreQualified(int id)
	{
		for (int i = 0; i < preQualified.length; i++)
		{
			if (preQualified[i] == id)
			{
				return true;
			}
		}
		return false;
	}

	/*
	 * public boolean[] getQualified() { return qualified; }
	 */

	@Override
	public byte[] getOnlyQualified()
	{
		final int tc = getQualifiedCount();
		final byte res[] = new byte[tc];
		int c = 0;
		for (int i = 0; i < turnier.getCompetitors().length; i++)
		{
			if (getQualified(i))
			{
				res[c] = (byte) i;
				c++;
			}
		}
		return res;
	}

	@Override
	public byte[] getOnlyNotQualified()
	{
		final int tc = getNotQualifiedCount();
		final byte res[] = new byte[tc];
		int c = 0;
		for (int i = 0; i < turnier.getCompetitors().length; i++)
		{
			if (letzteRunde.getLastQualifying().getQualified(i)
			        && !getQualified(i) && !disqualified[i])
			{
				res[c] = (byte) i;
				c++;
			}
		}
		return res;
	}

	@Override
	public String getID()
	{
		switch (rundenIndex)
		{
			case RoundType.Final:
				return "G";
			case RoundType.SmallFinal:
				return "K";
			case RoundType.Qualification:
				return "V";
			default:
				return "Z";
		}
	}

	@Override
	public String getName()
	{
		switch (rundenIndex)
		{
			case RoundType.Final:
				if (letzteRunde.getRundenIndex() == RoundType.SmallFinal)
				{
					return "Big Finals";
				}
				else
				{
					return "Finals";
				}
			case RoundType.SmallFinal:
				return "Small Finals";
			case RoundType.Qualification:
				return "First Round";
			case RoundType.Start:
				return "Starting list";
			case RoundType.Result:
				return "Results";
			default:
				if (turnier.getZwischenrundenCount() > 1)
				{
					return (getRundenIndex() - RoundType.Zwischenrunde[0] + 2)
					        + ". Round";
				}
				else
				{
					return "Second Round";
				}
		}
	}

	@Override
	public Place getPlace(int index)
	{
		if (platz[index] != null)
		{
			return platz[index];
		}
		else
		{
			if (turnier.getStartklasse().getDances() == 1)
			{
				final int d = getDisqualifiedCount();
				// TODO only calculate with -d, if not disqualified
				return letzteRunde.getPlace(index).getWithOffset(-d);
			}
			else
			{
				return letzteRunde.getPlace(index);
			}
		}
	}

	@Override
	public void setPlace(int index, Place p)
	{
		platz[index] = p;
	}

	@Override
	public boolean getDisqualified(int i)
	{
		return disqualified[i];
	}

	@Override
	public void setDisqualified(int i, boolean disqualified)
	{
		this.disqualified[i] = disqualified;
	}

	@Override
	public int getDisqualifiedCount()
	{
		int dc = 0;
		for (int i = 0; i < turnier.getTeilnehmerCount(); i++)
		{
			if (disqualified[i])
			{
				dc++;
			}
		}
		return dc;
	}

	@Override
	public int getNotQualifiedCount()
	{
		return letzteRunde.getLastQualifying().getQualifiedCount()
		        - getQualifiedCount() - getDisqualifiedCount();
	}

	@Override
	public String getWertungsString(int index, boolean spaceWertung, boolean doOffset, int dance)
	{
		int offset = 0;
		String wert = "";
		if (doOffset)
		{
			offset = (rundenIndex == RoundType.SmallFinal) ? offset = letzteRunde.getQualifiedCount()
			        : 0;
		}
		if (isSet(index, dance))
		{
			for (int w = 0; w < turnier.getWertungsrichter(); w++)
			{
				final int wval = (wertung[dance][preQualifiedRev[index]][w] + offset);
				if (wval > 9)
				{
					spaceWertung = true;
					break;
				}
			}
			for (int w = 0; w < turnier.getWertungsrichter(); w++)
			{
				final int wval = (wertung[dance][preQualifiedRev[index]][w] + offset);
				wert += wval;
				if (spaceWertung)
				{
					wert += " ";
				}
			}
		}

		return wert;
	}

	@Override
	public Place getDancePlace(int dance, int index)
	{
		if (turnier.getStartklasse().getDances() > dance && mySkating != null
				&& getPreQualified(index))
		{
			return mySkating.getResult(dance, preQualifiedRev[index]);
		}
		return null;
	}

	@Override
	public String getSumme(int index)
	{
		if (turnier.getStartklasse().getDances() > 0 && mySkating != null
		        && getPreQualified(index))
		{
			return dformat(mySkating.getSum(preQualifiedRev[index]));
		}
		return "";
	}

	@Override
	public int getKreuze(int dance, int team)
	{
		if (doKreuze)
		{
			return summen[dance][preQualifiedRev[team]];
		}
		else
		{
			return 0;
		}
	}

	@Override
	public int getKreuze(int team)
	{
		if (doKreuze)
		{
			return summe[preQualifiedRev[team]];
		}
		else
		{
			return 0;
		}
	}

	@Override
	public String getDetails(int index)
	{
		return getDetails(index, false);
	}

	@Override
	public String getDetails(int index, boolean spaceWertung)
	{
		if (getDisqualified(index))
		{
			return "disqualifiziert";
		}
		if (rundenIndex == RoundType.Final
		        || rundenIndex == RoundType.SmallFinal)
		{
			if (turnier.getStartklasse().getDances() > 1)
			{
				final String summe = getSumme(index);
				if (!summe.equals(""))
				{
					return "Summe " + summe;
				}
				else
				{
					return getName();
				}
			}
			else if (turnier.getStartklasse().getDances() == 1)
			{
				return getWertungsString(index, spaceWertung, true, 0);
			}
			else
			{
				return "";
			}
		}
		else if (rundenIndex == RoundType.Qualification)
		{
			return "Vorrunde";
		}
		else if (rundenIndex >= RoundType.Zwischenrunde[0])
		{
			if (turnier.getZwischenrundenCount() > 1)
			{
				return (rundenIndex - RoundType.Zwischenrunde[0] + 1)
				        + ". Zwischenrunde";
			}
			else
			{
				return "Zwischenrunde";
			}
		}
		else if (rundenIndex == RoundType.Start)
		{
			if (getQualified(index))
			{
				return "Turnierbeginn";
			}
			else
			{
				return "nicht angetreten";
			}
		}
		else
		{
			return "";
		}
	}

	@Override
	public int getQualifiedCount()
	{
		int c = 0;
		for (int i = 0; i < turnier.getCompetitors().length; i++)
		{
			if (getQualified(i))
			{
				c++;
			}
		}
		return c;
	}

	@Override
	public boolean isFinale()
	{
		// TODO Auto-generated method stub
		return rundenIndex == RoundType.Final
		        || rundenIndex == RoundType.SmallFinal;
	}

	@Override
	public void setMinKreuze(int summe)
	{
		minKreuze = (byte) summe;
	}

	@Override
	public boolean hasKreuze()
	{
		return doKreuze;
	}

	@Override
	public void setDoKreuze(boolean kreuze)
	{
		doKreuze = kreuze;
	}

	@Override
	public byte getKreuzeW(int dance, int w)
	{
		return summew[dance][w];
	}

	@Override
	public void toggleKreuz(int dance, int team, int wertungsrichter)
	{
		if (dance < turnier.getStartklasse().getDances()
		        && wertungsrichter < turnier.getWertungsrichter()
		        && team < turnier.getCompetitors().length && dance >= 0
		        && wertungsrichter >= 0 && team >= 0)
		{
			wertung[dance][preQualifiedRev[team]][wertungsrichter] = (byte) ((wertung[dance][preQualifiedRev[team]][wertungsrichter] > 0) ? 0
			        : 1);
			CalcWertungOk(dance);
			doKreuze = true;
		}
	}

	@Override
	public boolean hasAuslosung()
	{
		return auslosung != null;
	}

	@Override
	public String getFormationName()
	{
		if (getRundenIndex() == RoundType.Start)
		{
			return "Stellprobe";
		}
		else
		{
			return getName();
		}
	}
}
