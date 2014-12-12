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

package de.fellmann.judge.skating.export;

import de.fellmann.judge.skating.Calculator;
import de.fellmann.judge.skating.JudgementForFinal;

public class ExportHtml
{
	private final JudgementForFinal judgement;
	private final Calculator calculator;

	public ExportHtml(Calculator calculator)
	{
		this.calculator = calculator;
		this.judgement = calculator.getJudgement();
	}

	private void addDance(StringBuilder html, int dance)
	{
		html.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"1\">");

		html.append("<tr>");
		html.append("<td class=\"resultheader\">Nr&nbsp;</td>");

		html.append("<td class=\"resultheader\">Name&nbsp;</td>");
		html.append("<td align=\"center\" class=\"resultheader\">");
		html.append("Wertung");
		html.append("&nbsp;</td>");
		for (int competitor = 0; competitor < judgement.getCompetitors(); competitor++)
		{
			html.append("<td align=\"center\" class=\"resultheader\">");
			if (competitor > 0)
			{
				html.append("1.-" + (competitor + 1) + ".");
			}
			else
			{
				html.append("1.");
			}
			html.append("&nbsp;</td>");
		}
		html.append("<td align=\"center\" class=\"resultheader\">&nbsp;Platz&nbsp;</td>");
		html.append("</tr>");

		for (int competitor = 0; competitor < judgement.getCompetitors(); competitor++)
		{
			final int idx = competitor;
			html.append("<tr>");
			html.append("<td class=\"result\">" + idx + "&nbsp;</td>");

			html.append("<td class=\"result\">" + "Participant " + idx
			        + "&nbsp;</td>");
			html.append("<td align=\"center\" class=\"result\">");
			html.append(judgement.getAsString(dance, idx));
			html.append("&nbsp;</td>");
			for (int column = 0; column < judgement.getCompetitors(); column++)
			{
				html.append("<td align=\"center\" class=\"result\">");
				html.append(calculator.getMajorTable(dance, competitor, column));
				html.append("&nbsp;</td>");
			}
			html.append("<td align=\"center\" class=\"result\">&nbsp;");
			if (calculator.getResult(dance, idx) != null)
			{
				html.append(calculator.getResult(dance, idx).toStringFromTo());
			}
			html.append("&nbsp;</td>");
			html.append("</tr>");
		}

		html.append("</table><br>");
	}

	public String getHTML(String fontsize)
	{
		final StringBuilder html = new StringBuilder();

		html.append("<html><head><title>" + "Result" + "</title></head><body>");

		html.append("<b>" + "Result" + "</b><br><br>");

		for (int i = 0; i < judgement.getDances(); i++)
		{
			if (judgement.getDances() > 1)
			{
				html.append("<b>" + "Dance " + i + "</b><br>");
			}
			addDance(html, i);
		}

		addAusrechnung(html);

		html.append("</body></html>");

		return html.toString();
	}

	private void addAusrechnung(StringBuilder html)
	{

		if (judgement.getDances() > 1)
		{

			// ++++++++++++++++++++++++++++
			// Sums
			// ++++++++++++++++++++++++++++

			html.append("<b>Sums</b>");
			html.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"1\">");
			html.append("<tr>");
			html.append("<td class=\"resultheader\">Nr&nbsp;</td>");

			html.append("<td class=\"resultheader\">Name&nbsp;</td>");
			for (int i = 0; i < judgement.getDances(); i++)
			{
				html.append("<td align=\"center\" class=\"resultheader\">");
				html.append("Dance " + i);
				html.append("&nbsp;</td>");
			}
			html.append("<td align=\"center\" class=\"resultheader\">&nbsp;Summe&nbsp;</td>");
			html.append("<td align=\"center\" class=\"resultheader\">&nbsp;Platz&nbsp;</td>");
			html.append("</tr>");

			for (int r = 0; r < judgement.getCompetitors(); r++)
			{
				final int idx = r;
				html.append("<tr>");
				html.append("<td class=\"result\">" + idx + "&nbsp;</td>");

				html.append("<td class=\"result\">" + "Participant " + idx
				        + "&nbsp;</td>");
				for (int i = 0; i < judgement.getDances(); i++)
				{
					html.append("<td align=\"center\" class=\"result\">");
					if (calculator.getResult(i, idx) != null)
					{
						html.append(calculator.getResult(i, idx).toString());
					}
					html.append("&nbsp;</td>");
				}
				html.append("<td align=\"center\" class=\"result\">&nbsp;"
				        + calculator.getSum(idx) + "&nbsp;</td>");
				html.append("<td align=\"center\" class=\"result\">&nbsp;"
				        + calculator.getResult(idx).toStringFromTo()
				        + "&nbsp;</td>");
				html.append("</tr>");
			}

			html.append("</table><br>");

			// ++++++++++++++++++++++++++++
			// SKATING Table 10
			// ++++++++++++++++++++++++++++

			html.append("<b>Skating Table 10</b>");
			html.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"1\">");
			html.append("<tr>");
			html.append("<td class=\"resultheader\">Nr&nbsp;</td>");

			html.append("<td class=\"resultheader\">Name&nbsp;</td>");
			for (int t = 0; t < judgement.getCompetitors(); t++)
			{
				html.append("<td align=\"center\" class=\"resultheader\">");
				if (t > 0)
				{
					html.append("1.-" + (t + 1) + ".");
				}
				else
				{
					html.append("1.");
				}
				html.append("&nbsp;</td>");
			}
			html.append("<td align=\"center\" class=\"resultheader\">&nbsp;Platz&nbsp;</td>");
			html.append("</tr>");

			for (int r = 0; r < judgement.getCompetitors(); r++)
			{
				final int idx = r;
				html.append("<tr>");
				html.append("<td class=\"result\">" + idx + "&nbsp;</td>");

				html.append("<td class=\"result\">" + "Participant " + idx
				        + "&nbsp;</td>");
				for (int t = 0; t < judgement.getCompetitors(); t++)
				{
					html.append("<td align=\"center\" class=\"result\">");
					html.append(calculator.getTable10(r, t));
					html.append("&nbsp;</td>");
				}
				html.append("<td align=\"center\" class=\"result\">&nbsp;"
				        + calculator.getResult(idx) + "&nbsp;</td>");
				html.append("</tr>");
			}
			html.append("</table><br>");

			// ++++++++++++++++++++++++++++
			// SKATING Table 11
			// ++++++++++++++++++++++++++++

			html.append("<b>Skating Table 11</b>");
			html.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"1\">");
			html.append("<tr>");
			html.append("<td class=\"resultheader\">Nr&nbsp;</td>");

			html.append("<td class=\"resultheader\">Name&nbsp;</td>");
			for (int t = 0; t < judgement.getCompetitors(); t++)
			{
				html.append("<td align=\"center\" class=\"resultheader\">");
				if (t > 0)
				{
					html.append("1.-" + (t + 1) + ".");
				}
				else
				{
					html.append("1.");
				}
				html.append("&nbsp;</td>");
			}
			html.append("<td align=\"center\" class=\"resultheader\">&nbsp;Platz&nbsp;</td>");
			html.append("</tr>");

			for (int r = 0; r < judgement.getCompetitors(); r++)
			{
				final int idx = r;
				html.append("<tr>");
				html.append("<td class=\"result\">" + idx + "&nbsp;</td>");

				html.append("<td class=\"result\">" + "Participant " + idx
				        + "&nbsp;</td>");
				for (int t = 0; t < judgement.getCompetitors(); t++)
				{
					html.append("<td align=\"center\" class=\"result\">");
					html.append(calculator.getTable11(r, t));
					html.append("&nbsp;</td>");
				}
				html.append("<td align=\"center\" class=\"result\">&nbsp;"
				        + calculator.getResult(idx) + "&nbsp;</td>");
				html.append("</tr>");
			}

			html.append("</table><br>");
		}
	}

	private final String style = "b { font-size:FONTSIZE;  	font-family: Arial, Helvetica, Sans Serif; } TD.resultheader {  	border-style:solid none solid none;   	border-width:2px;   	border-color:#660000;  	color:#660000;   	font-weight:bold;   	font-size:FONTSIZE;  	font-family: Arial, Helvetica, Sans Serif;  }   TD.result {  	border-style:none none solid solid;   	border-width:1px;   	border-color:#660000;  	color:#000000;   	font-weight:normal;   	font-size:FONTSIZE;  	font-family: Arial, Helvetica, Sans Serif;  }   TD.info {  	border-style:none none solid none;   	border-width:1px;   	border-color:#660000;  	color:#000000;   	font-weight:normal;   	font-size:12px;  	font-family: Arial, Helvetica, Sans Serif;  }   TD.result_g {  	border-style:none none solid none;   	border-width:1px;   	border-color:#660000;  	color:#000000;   	font-weight:normal;   	font-size:FONTSIZE;  	font-family: Arial, Helvetica, Sans Serif;  	background-color:#FF8989;  }   TD.result_z {  	border-style:none none solid none;   	border-width:1px;   	border-color:#660000;  	color:#000000;   	font-weight:normal;   	font-size:FONTSIZE;  	font-family: Arial, Helvetica, Sans Serif;  	background-color: #F3C3C3;  }   TD.result_k {  	border-style:none none solid none;   	border-width:1px;   	border-color:#660000;  	color:#000000;   	font-weight:normal;   	font-size:FONTSIZE;  	font-family: Arial, Helvetica, Sans Serif;  }   TD.result_g_leg {  	border-style:none none none none;   	background-color:#FF8989;  }   TD.result_z_leg {  	border-style:none none none none;   	background-color:#F3C3C3;  }";

	public String encodeHtml(String s)
	{
		final StringBuilder out = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
		{
			final char c = s.charAt(i);
			if (c > 127 || c == '"' || c == '<' || c == '>') // || c == '\n' ||
				// c == '\r')
			{
				out.append("&#" + (int) c + ";");
			}
			else
			{
				out.append(c);
			}
		}
		return out.toString();
	}
}
