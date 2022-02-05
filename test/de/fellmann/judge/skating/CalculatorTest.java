/*
 * ================================================================
 *
 * jUdge
 * Open Source judging calculation library for dancesport
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

package de.fellmann.judge.skating;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CalculatorTest {
    @Test
    public void testBeispielA() {
        testBeispiel("15112 1,22541 2,33323 3,44234 4,51455 5,66666 6;1,2,3,4,5,6");
    }

    @Test
    public void testBeispielB() {
        testBeispiel("11144 1,32211 2,25522 3,43453 4,54335 5,66666 6;1,2,3,4,5,6");
    }

    @Test
    public void testBeispielC() {
        testBeispiel("11155 1,22514 2,55222 3,33461 4,44333 5,66646 6;1,2,3,4,5,6");
    }

    @Test
    public void testBeispielD() {
        testBeispiel("21511 1,12255 2,56122 3,33336 4,44464 5,65643 6;1,2,3,4,5,6");
    }

    @Test
    public void testBeispielE() {
        testBeispiel("21511 1,12255 2.5,55122 2.5,33336 4,44464 5,66643 6;1,2.5,2.5,4,5,6");
    }

    @Test
    public void testBeispielF() {
        testBeispiel("1 1,3 3,2 2,4 4,5 5,6 6;2 2,1 1,3 3,4 4,5 5,6 6;1 1,2 2,3 3,4 4,5 5,6 6;2 2,1 1,3 3,4 4,5 5,6 6;2 2,4 4,3 3,1 1,6 6,5 5;1,2,3,4,5,6");
    }

    @Test
    public void testBeispielG() {
        testBeispiel("1 1,3 3,2 2,4 4,6 6,5 5;1 1,3 3,5 5,2 2,6 6,4 4;2 2,1 1,4 4,3 3,5 5,6 6;5 5,2 2,4 4,6 6,1 1,3 3;1,2,4,3,6,5");
    }

    @Test
    public void testBeispielH() {
        testBeispiel("3 3,2 2,1 1,4 4,5 5,6 6;1 1,2 2,4 4,5 5,3 3,6 6;1 1,3 3,2 2,6 6,5 5,4 4;1 1,2 2,5 5,4 4,3 3,6 6;1 1,6 6,3 3,2 2,5 5,4 4;1,2,3,4,5,6");
    }

    @Test
    public void testBeispielJ() {
        testBeispiel("1 1,3 3,2 2,6 6,5 5,4 4;1 1,4 4,2 2,3 3,5 5,6 6;2 2,1 1,3 3,4 4,5 5,6 6;3 3,2 2,1 1,5 5,4 4,6 6;2 2,1 1,3 3,5 5,4 4,6 6;1,2,3,4,5,6");
    }

    @Test
    public void testBeispielK() {
        testBeispiel("2 2,1 1,6 6,4 4,5 5,3 3;1 1,2 2,6 6,4 4,3 3,5 5;5 5,2 2,1 1,4 4,3 3,6 6;3 3,6 6,1 1,4 4,2 2,5 5;2 2,6 6,3 3,1 1,4 4,5 5;1,2,3,4,5,6");
    }

    @Test
    public void testBeispielM() {
        testBeispiel(
                "11253 1,22525 2,45311 3,33636 4,66144 5,54462 6;" +
                        "12234 1,53122 2,31313 3,44441 4,25555 5,66666 6;" +
                        "11223 1.5,32112 1.5,53361 3,26634 4,44556 5.5,65445 5.5;" +
                        "22515 2,11252 1,56323 4,33136 3,64664 6,45441 5;" +
                        "26261 2,15152 1,32424 4,51513 3,44636 6,63345 5;" +
                        "2,1,3,4,6,5"
        );
    }

    @Test
    public void testBeispielN() {
        testBeispiel(
                "11253 1,22525 2,33636 4,45311 3,66144 5,54462 6;" +
                        "12234 1,53122 2,44441 4,31313 3,25555 5,66666 6;" +
                        "11111 1,23456 4,34562 4,45623 4,52234 2,66345 6;" +
                        "11252 1,56323 4,22515 2,33136 3,45441 5,64664 6;" +
                        "15152 1,32424 4,26261 2,51513 3,63345 5,44636 6;" +
                        "1,2,3,4,5,6"
        );
    }

    @Test
    public void testBeispielP() {
        testBeispiel(
                "22525 2,33636 4,45311 3,11253 1,66144 5,54462 6;" +
                        "12234 1,31313 3,25555 5,44441 4,66666 6,53122 2;" +
                        "11111 1,23456 4,34562 4,45623 4,52234 2,66345 6;" +
                        "33136 3,11252 1,45441 5,56323 4,22515 2,64664 6;" +
                        "26261 2,44636 6,15152 1,63345 5,51514 4,32423 3;" +
                        "1,2,3,4,5,6"
        );
    }

    @Test
    public void testEdge1() {
        testBeispiel("1 1;1");
    }

    @Test
    public void testEdge2() {
        testBeispiel(
                "1 1,2 2,3 3,4 4,5 5;" +
                        "2 2,3 3,4 4,5 5,1 1;" +
                        "3 3,4 4,5 5,1 1,2 2;" +
                        "4 4,5 5,1 1,2 2,3 3;" +
                        "5 5,1 1,2 2,3 3,4 4;" +
                        "3,3,3,3,3");
    }

    private void testBeispiel(String text) {
        String[] dances = text.split(";");
        int countDances = Math.max(dances.length - 1, 1);
        int countCompetitors = dances[0].split(",").length;
        int countJudges = dances[0].split(",")[0].split(" ")[0].length();

        JudgementForFinal judgement = new JudgementForFinal(countDances, countCompetitors, countJudges);

        for (int d = 0; d < countDances; d++) {
            String[] competitors = dances[d].split(",");
            for (int c = 0; c < countCompetitors; c++) {
                String[] competitor = competitors[c].split(" ");
                for (int j = 0; j < countJudges; j++) {
                    judgement.setMark(d, c, j, (byte) (competitor[0].charAt(j) - '0'));
                }
            }
        }

        Calculator calculator = new Calculator(judgement);

        for (int d = 0; d < countDances; d++) {
            String[] competitors = dances[d].split(",");
            for (int c = 0; c < countCompetitors; c++) {
                String[] competitor = competitors[c].split(" ");
                assertEquals("dance " + d + ", competitor " + c, Double.parseDouble(competitor[1]), calculator.getResult(d, c).getValue(), 0.01);
            }
        }
        if (dances.length > countDances) {
            String[] competitors = dances[dances.length - 1].split(",");
            for (int c = 0; c < countCompetitors; c++) {
                assertEquals("competitor " + c + " result", Double.parseDouble(competitors[c]), calculator.getResult(c).getValue(), 0.01);
            }
        }
    }
}
