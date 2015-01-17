
package de.fellmann.judge.competition.data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;

public abstract class DataObject implements Comparable<DataObject>
{
	private static int maxID = 0;

	private String id = String.valueOf(maxID++);

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final DataObject other = (DataObject) obj;
		if (id == null)
		{
			if (other.id != null)
			{
				return false;
			}
		}
		else if (!id.equals(other.id))
		{
			return false;
		}
		return true;
	}

	protected long version = 0;
	protected long editTS = System.currentTimeMillis();

	@XmlTransient
	public long getEditTS()
	{
		return editTS;
	}

	public void setEditTS(long editTS)
	{
		this.editTS = editTS;
	}

	@XmlTransient
	public long getVersion()
	{
		return version;
	}

	public void setVersion(long version)
	{
		this.version = version;
	}

	public int compareTo(DataObject o)
	{
		return getId().compareTo(o.getId());
	}

	@XmlID
	@XmlAttribute
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
