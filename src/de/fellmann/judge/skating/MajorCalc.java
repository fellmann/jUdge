package de.fellmann.judge.skating;

import de.fellmann.common.IntList;

/**
 * Majority Calculation Module Judgment System 1.0
 * 
 * @author Hanno Fellmann
 *
 */
class MajorCalc {
	private JudgementForMajor judgement = null;
	private double[][] tabelle1;
	private double[][] tabelle2;

	private Place[] result;
	private Place[] minPlace;
	private Place[] maxPlace;

	private byte[][] placeSums;

	private int majorit;

	private int calculatedCount = 0;
	private boolean calcOne = false;
	private boolean isCalc;

	void setSkating() {
		calculatedCount = 0;
		calcOne = true;
	}

	void calcPlaces(int startColumn, int wantedPlace, IntList remaining) {
		if (startColumn > judgement.getCompetitors() + 1) {
			return;
		}

		int gesuchterPlatzweg, x;

		IntList curmaj, curSums = new IntList();

		curmaj = getMaxMajors(startColumn, remaining.byval());
		while (curmaj.size() < 1) {
			startColumn++;
			curmaj = getMaxMajors(startColumn, remaining.byval());
			if (startColumn > judgement.getCompetitors() + 1) {
				return;
			}
		}

		if (curmaj.size() == 1) {
			gesuchterPlatzweg = curmaj.get(0);
			result[gesuchterPlatzweg] = new Place(wantedPlace, wantedPlace);
			calculatedCount++;
			remaining.remove(gesuchterPlatzweg);

			if (remaining.size() > 0) {
				calcPlaces(startColumn, wantedPlace + 1, remaining.byval());
			}
		} else {
			remaining.removeAll(curmaj);

			curSums = getSums(curmaj.byval(), startColumn);
			while (curSums.size() > 0) {
				if (curSums.size() == 1) {
					gesuchterPlatzweg = curSums.get(0);
					result[gesuchterPlatzweg] = new Place(wantedPlace,
							wantedPlace);
					calculatedCount++;
					curmaj.remove(gesuchterPlatzweg);

					if (curmaj.size() > 0) {
						calcPlaces(startColumn, wantedPlace + 1, curmaj.byval());
					}
				} else {
					if (startColumn < judgement.getCompetitors() - 1) {
						calcPlaces(startColumn + 1, wantedPlace,
								curSums.byval());
						curmaj.removeAll(curSums);
					} else {
						for (int ix = 0; ix < curSums.size(); ix++) {
							x = curSums.get(ix);
							result[x] = new Place(wantedPlace, wantedPlace
									+ curSums.size() - 1);
							calculatedCount++;
						}
						curmaj.removeAll(curSums);
					}
				}
				wantedPlace += curSums.size();
				curSums = getSums(curmaj.byval(), startColumn);
			}

			if (remaining.size() > 0) {
				calcPlaces(startColumn, wantedPlace, remaining.byval());
			}
		}
	}

	IntList getSums(IntList remaining, int column) {
		IntList res = new IntList();
		int max = 100;
		int cur;
		int x;

		for (int ix = 0; ix < remaining.size(); ix++) {
			x = remaining.get(ix);
			cur = getSum(x, column);
			if ((!calcOne || calculatedCount < 1)
					&& column < judgement.getCompetitors())
				tabelle2[x][column] = (byte) cur;
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

	int getSum(int x, int column) {
		return getSum(x, column, true);
	}

	int getSum(int x, int column, boolean dogood) {
		int res = 0;

		if (dogood) {
			res = placeSums[x][0];
			for (int i = 1; i <= column; i++) {
				res += (getPlatzierung(x, i) - getPlatzierung(x, i - 1))
						* (i + 1);
			}
		} else {
			res = 0;
			for (int i = judgement.getCompetitors() - 2; i >= column; i++) {
				res += (getPlatzierung(x, i + 1) - getPlatzierung(x, i - 11))
						* (i + 1);
			}
		}

		return res;
	}

	IntList getMaxMajors(int column, IntList which) {
		IntList res = new IntList();
		int max = -1;

		for (int competitor = 0; competitor < judgement.getCompetitors(); competitor++) {
			if (which.contains(competitor)) {
				if ((!calcOne || calculatedCount < 1)
						&& column < judgement.getCompetitors())
					tabelle1[competitor][column] = getPlatzierung(competitor, column);

				if (getPlatzierung(competitor, column) >= majorit
						&& getPlatzierung(competitor, column) > max) {
					max = getPlatzierung(competitor, column);
					res.clear();
					res.add(competitor);
				} else if (max == getPlatzierung(competitor, column)) {
					res.add(competitor);
				}
			}
		}

		return res;
	}

	void getPlatzierungen() {
		for (int competitor = 0; competitor < judgement.getCompetitors(); competitor++) {
			if (judgement.isSet(competitor)) {
				getPlatzierungen(competitor);
			}
		}
	}

	void getPlatzierungen(int competitor) {
		for (int column = 0; column < judgement.getCompetitors(); column++) {
			placeSums[competitor][column] = 0;
			for (int ajur = 0; ajur < judgement.getJudges(); ajur++) {
				if (column + 1 >= judgement.getJudgement(competitor, ajur)) {
					placeSums[competitor][column]++;
				}
			}
		}
		majorit = (judgement.getJudges() / 2) + 1;
	}

	public void set(JudgementForMajor grades) {
		this.judgement = grades;
	}

	public void set(int teilnehmer, int teilnehmerCount, int juryCount,
			byte wertungen[]) {
		throw new RuntimeException("not implemented");
		// varGrades = varGrades.getJudgement(competitor, ajur);
		// for (int i = 0; i < juryCount; i++) {
		// JudgementForCompetitor judgement = new JudgementForCompetitor();
		// for (int j = 0; j < wertungen.length; j++) {
		// judgement.addGrade(new Judgement(wertungen[i]));
		// }
		// varGrades.addJudgement(judgement);
		// }
	}

	public JudgementForMajor getJudgement() {
		return judgement;
	}

	public MajorCalc() {
		clear();
	}

	public void clearInit() {
	}

	public void clear() {
		isCalc = false;
	}

	boolean getCalced() {
		return isCalc;
	}

	public Place getPlatz(int i) {
		return result[i];
	}

	public void calcInternal() {
		init();
		calc(IntList.getFromTo(0, judgement.getCompetitors()-1));
		isCalc = true;
	}

	private void init() {
		placeSums = new byte[judgement.getCompetitors()][judgement
				.getCompetitors()];
		tabelle1 = new double[judgement.getCompetitors()][judgement
				.getCompetitors()];
		tabelle2 = new double[judgement.getCompetitors()][judgement
				.getCompetitors()];
		result = new Place[judgement.getCompetitors()];
		minPlace = new Place[judgement.getCompetitors()];
		maxPlace = new Place[judgement.getCompetitors()];
		
		for (int i = 0; i < judgement.getCompetitors(); i++) {
			for (int j = 0; j < judgement.getCompetitors(); j++) {
				tabelle1[i][j] = -1;
				tabelle2[i][j] = -1;
			}
			minPlace[i] = null;
			maxPlace[i] = null;
		}
	}

	private void calc(IntList all) {
		getPlatzierungen();
		calcPlaces(0, 1, all.byval());
	}

	public double[] getTable(int x, int y) {
		double vals[] = new double[2];
		if (tabelle1 == null) {
			vals[0] = -1;
			vals[1] = -1;
		} else {
			vals[0] = tabelle1[x][y];
			vals[1] = tabelle2[x][y];
		}
		return vals;
	}

	public int getCount() {
		return judgement.getCompetitors();
	}

	public int getJudges() {
		return judgement.getJudges();
	}

	public byte getPlatzierung(int c, int i) {
		if (placeSums == null)
			return 0;
		if (c < placeSums.length && i < placeSums.length) {
			return placeSums[c][i];
		} else {
			return (byte) judgement.getJudges();
		}
	}

	// **************************************************************************

	public void calc() {
		int competitor, sc = 0;
		Place minunset = new Place(100);
		Place maxunset = new Place(0);

		init();
		getPlatzierungen();

		for (competitor = 0; competitor < judgement.getCompetitors(); competitor++) {
			if (judgement.isSet(competitor)) {
				sc++;
				minPlace[competitor] = new Place(100);
				maxPlace[competitor] = new Place(0);
			} 
		}

		if (sc == judgement.getCompetitors()) {
			calcInternal();
		} else {
			for (int i = 0; i < judgement.getCompetitors() - sc + 1; i++) {
				getPrePlatzierungen(i, true);
				calc(IntList.getFromTo(0, judgement.getCompetitors()-1));

				for (competitor = 0; competitor < judgement.getCompetitors(); competitor++) {
					if (judgement.isSet(competitor)) {
						if (result[competitor].getPlace() < minPlace[competitor].getPlace())
							minPlace[competitor] = result[competitor];
						if (result[competitor].getPlace() > maxPlace[competitor].getPlace())
							maxPlace[competitor] = result[competitor];
					} else {
						if (result[competitor].getPlace() < minunset.getPlace())
							minunset = result[competitor];
						if (result[competitor].getPlace() > maxunset.getPlace())
							maxunset = result[competitor];
					}
				}
			}

		}
		
		for (competitor = 0; competitor < judgement.getCompetitors(); competitor++) {
			if (!judgement.isSet(competitor)) {
				minPlace[competitor] = minunset;
				maxPlace[competitor] = maxunset;
			} 
		}

	}

	public Place getMinPlace(int i) {
		return minPlace[i];
	}

	public Place getMaxPlace(int i) {
		return maxPlace[i];
	}

	void getPrePlatzierungen(int gc, boolean dogood) {
		IntList good = new IntList(), bad = new IntList(), gone = new IntList();
		int competitor, column, csum, cidx;

		for (competitor = 0; competitor < judgement.getCompetitors(); competitor++) {
			if (judgement.isSet(competitor)) {
				gone.add(competitor);
			} else {
				if (good.size() < gc) {
					good.add(competitor);
				} else {
					bad.add(competitor);
				}
			}
		}

		for (column = 0; column < judgement.getCompetitors(); column++) {
			csum = 0;

			for (competitor = 0; competitor < judgement.getCompetitors(); competitor++) {
				if (!judgement.isSet(competitor)) {
					if (column == 0)
						placeSums[competitor][column] = 0;
					else
						placeSums[competitor][column] = placeSums[competitor][column - 1];
				}
				csum += placeSums[competitor][column];
			}

			while (csum < (column + 1) * judgement.getJudges()) {
				cidx = chooseOne(good, column, true);
				if (cidx >= 0) {
					placeSums[cidx][column]++;
				} else {
					cidx = chooseOne(bad, column, true);

					placeSums[cidx][column]++;
				}
				csum++;
			}
		}

	}

	int chooseOne(IntList which, int column, boolean dogood) {
		int minp, mins, competitor, cp, cs, res = -1;

		minp = judgement.getJudges() - 1;
		mins = 0;

		for (int i = 0; i < which.size(); i++) {
			competitor = which.get(i);
			cp = placeSums[competitor][column];
			cs = getSum(competitor, column, dogood);
			if (cp < judgement.getJudges()) {
				if (cp < minp || (cp == minp && cs > mins)) {
					minp = cp;
					mins = cs;
					res = competitor;
				}
			}
		}

		return res;
	}

}
