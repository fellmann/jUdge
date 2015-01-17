
package de.fellmann.judge.competition.data;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ ManualResultData.class, QualificationResultData.class,
        FinalResultData.class })
public abstract class ResultData extends DataObject
{
}
