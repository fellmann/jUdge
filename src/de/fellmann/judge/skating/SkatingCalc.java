package de.fellmann.judge.skating;

import de.fellmann.common.IntList;

public class SkatingCalc {
	private JudgementForFinal judgement;
	
	private MajorCalc dancesCalc[];
	private MajorCalc skatingCalc = new MajorCalc();
	
	private Place platz[];
	private int platzierungen[][];
	private double tabelle1[][];
	private double tabelle2[][];
	private double summe[];
	private boolean doSkating = true;

	private boolean didSkating10 = false;
	private boolean didSkating11 = false;

	private void init() {
		dancesCalc = new MajorCalc[judgement.getDances()];
		platz = new Place[judgement.getCompetitors()];
		platzierungen = new int[judgement.getCompetitors()][judgement
				.getCompetitors()];
		tabelle1 = new double[judgement.getCompetitors()][judgement
				.getCompetitors()];
		tabelle2 = new double[judgement.getCompetitors()][judgement
				.getCompetitors()];
		summe = new double[judgement.getCompetitors()];
	}

	public boolean getDidSkating10() {
		return didSkating10;
	}

	public boolean getDidSkating11() {
		return didSkating11;
	}

	public void setDoSkating(boolean doSkating) {
		this.doSkating = doSkating;
	}

	private IntList getMaxSkatingPlatz(IntList remaining) {
		double maxP = 20000;
		int x;
		IntList v = new IntList();
		for (int i = 0; i < remaining.size(); i++) {
			x = remaining.get(i);
			if (skatingCalc.getPlatz(x).getPlace() < maxP) {
				maxP = skatingCalc.getPlatz(x).getPlace();
				v.clear();
			}
			if (skatingCalc.getPlatz(x).getPlace() == maxP) {
				v.add(x);
			}
		}
		return v;
	}

	void getSkating(int wantedPlace, IntList remaining) {
		int x;
		IntList killed;

		didSkating11 = true;

		skatingCalc.setSkating();
		skatingCalc.calcPlaces(wantedPlace - 1, wantedPlace,
				remaining.byval());

		killed = getMaxSkatingPlatz(remaining);

		for (int ix = 0; ix < killed.size(); ix++) {
			x = killed.get(ix);
			platz[x] = skatingCalc.getPlatz(x);
		}

		remaining.removeAll(killed);
		if (remaining.size() > 1) {
			getUnter(wantedPlace + killed.size(), remaining.byval());
		} else if (remaining.size() == 1) {
			platz[remaining.get(0)] = new Place(wantedPlace + killed.size());
		}

	}

	void getUnter(int gesuchterPlatz, IntList which) {

		int gesuchterPlatzweg;

		IntList curmaj, curSums = new IntList();

		curmaj = getMaxCount(gesuchterPlatz, which);

		if (curmaj.size() == 1) {
			gesuchterPlatzweg = curmaj.get(0);
			platz[gesuchterPlatzweg] = new Place(gesuchterPlatz);
			which.remove(gesuchterPlatzweg);

			if (which.size() > 0) {
				getUnter(gesuchterPlatz + 1, which.byval());
			}
		} else {
			which.removeAll(curmaj);

			curSums = getSums(curmaj, gesuchterPlatz);
			while (curSums.size() > 0) {
				if (curSums.size() == 1) {
					gesuchterPlatzweg = curSums.get(0);
					platz[gesuchterPlatzweg] = new Place(gesuchterPlatz);
					curmaj.remove(gesuchterPlatzweg);

					if (curmaj.size() > 0) {
						getUnter(gesuchterPlatz + 1, curmaj.byval());
					}
				} else {
					getSkating(gesuchterPlatz, curSums.byval());
					curmaj.removeAll(curSums);
				}
				gesuchterPlatz += curSums.size();
				curSums = getSums(curmaj, gesuchterPlatz);
			}

			if (which.size() > 0) {
				getUnter(gesuchterPlatz, which.byval());
			}
		}
	}

	IntList getSums(IntList which, int aidx) {
		IntList res = new IntList();
		double max = 999999;
		double cur;

		for (int acur = 0; acur < judgement.getCompetitors(); acur++) {
			if (which.contains(acur)) {
				cur = 0;
				for (int i = 0; i < judgement.getDances(); i++) {
					if (dancesCalc[i].getPlatz(acur).getPlace() <= aidx)
						cur += dancesCalc[i].getPlatz(acur).getPlace();
				}
				tabelle2[acur][aidx - 1] = cur;
				if (cur < max) {
					max = cur;
					res.clear();
					res.add(acur);
				} else if (max == cur) {
					res.add(acur);
				}
			}
		}

		return res;
	}

	public MajorCalc[] getMajorFunc() {
		return dancesCalc;
	}

	IntList getMaxCount(int aidx, IntList which) {
		IntList res = new IntList();
		int max = -1;
		int cur = 0;

		if (which.size() == 1)
			return which;

		for (int acur = 0; acur < judgement.getCompetitors(); acur++) {
			if (which.contains(acur)) {
				cur = 0;
				for (int i = 0; i < judgement.getDances(); i++) {
					if (dancesCalc[i].getPlatz(acur).getPlace() <= aidx)
						cur++;
				}
				tabelle1[acur][aidx - 1] = cur;
				if (cur > max) {
					max = cur;
					res.clear();
					res.add(acur);
				} else if (max == cur) {
					res.add(acur);
				}
			}
		}

		return res;
	}

	public void set(JudgementForFinal judgement) {
		this.judgement = judgement;
	}

	void getPlatzierungen() {
		for (int aidx = 0; aidx < judgement.getDances(); aidx++) {
			for (int acur = 0; acur < judgement.getCompetitors(); acur++) {
				platzierungen[acur][aidx] = 0;
				for (int ajur = 0; ajur < judgement.getJudges(); ajur++) {
					if (aidx + 1 >= judgement.getJudgment(aidx, acur, ajur)) {
						platzierungen[acur][aidx]++;
					}
				}
			}
		}

		for (int i = 0; i < judgement.getDances(); i++) {
			for (int j = 0; j < judgement.getCompetitors(); j++) {
				tabelle1[i][j] = -1;
				tabelle2[i][j] = -1;
			}
		}
	}

	public SkatingCalc() {
	}

	public Place getPlatz(int i) {
		return platz[i];
	}

	IntList getMinSums(IntList which) {
		IntList res = new IntList();
		double max = 9999;
		double cur;
		int x;

		for (int ix = 0; ix < which.size(); ix++) {
			x = which.get(ix);
			cur = dancesCalc[0].getPlatz(x).getPlace();
			for (int i = 1; i < judgement.getDances(); i++) {
				cur += dancesCalc[i].getPlatz(x).getPlace();
			}
			summe[x] = cur;
			// varTabelle2[x][aidx]=cur;
			if (cur < max) {
				max = cur;
				res.clear();
				res.add(x);
			} else if (cur == max) {
				res.add(x);
			}
		}

		return res;
	}

	public double getSumme(int i) {
		return summe[i];
	}

	public void calc() {
		init();

		IntList all = new IntList();
		int i, j;

		for (i = 0; i < judgement.getCompetitors(); i++) {
			all.add(i);
		}

		IntList curmaj;

		int gesuchterPlatz = 1;
		int gesuchterPlatzweg;

		didSkating10 = false;
		didSkating11 = false;

		for (i = 0; i < judgement.getCompetitors(); i++) {
			for (j = 0; j < judgement.getCompetitors(); j++) {
				tabelle1[i][j] = -1;
				tabelle2[i][j] = -1;
			}
		}

		JudgementForMajor allGrades = new JudgementForSkating(judgement);

		skatingCalc.set(allGrades);
		skatingCalc.calc();

		for (int d = 0; d < judgement.getDances(); d++) {
			MajorCalc maj = new MajorCalc();
			maj.set(new JudgementForDance(judgement, d));
			maj.calc();
			dancesCalc[d] = maj;
		}

		while (gesuchterPlatz < judgement.getCompetitors() + 1) {
			curmaj = getMinSums(all);
			// Genau eine Summe
			if (curmaj.size() == 1) {
				gesuchterPlatzweg = curmaj.get(0);

				platz[gesuchterPlatzweg] = new Place(gesuchterPlatz);
				all.remove(gesuchterPlatzweg);
			}

			// Mehrere Summen
			else {
				if (doSkating) {
					didSkating10 = true;
					getUnter(gesuchterPlatz, curmaj.byval());
					all.removeAll(curmaj);
				} else {
					for (i = 0; i < curmaj.size(); i++) {
						gesuchterPlatzweg = curmaj.get(i);
						platz[gesuchterPlatzweg] = new Place(gesuchterPlatz,
								gesuchterPlatz + curmaj.size() - 1);
					}
					all.removeAll(curmaj);
				}
			}

			gesuchterPlatz += curmaj.size();
		}
	}

	public double[] getTable10(int x, int y) {
		double[] vals = new double[2];
		vals[0] = tabelle1[x][y];
		vals[1] = tabelle2[x][y];
		return vals;
	}

	public double[] getTable11(int x, int y) {
		return skatingCalc.getTable(x, y);
	}

	int getCount() {
		return judgement.getCompetitors();
	}

	public int getDances() {
		return judgement.getDances();
	}

	int getJuryCount() {
		return judgement.getJudges();
	}

}
