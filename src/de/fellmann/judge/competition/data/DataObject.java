package de.fellmann.judge.competition.data;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

public abstract class DataObject implements Comparable<DataObject>
{
	protected String uuid = UUID.randomUUID().toString();
	protected long version = 0;
	protected long createdTS = System.currentTimeMillis();
	protected String createdAuthor = System.getProperty("user.name");
	protected long editedTS = createdTS;
	protected String editedAuthor = System.getProperty("user.name");
	
	public long getCreatedTS()
	{
		return createdTS;
	}

	public void setCreatedTS(long createdTS)
	{
		this.createdTS = createdTS;
	}

	public String getCreatedAuthor()
	{
		return createdAuthor;
	}

	public void setCreatedAuthor(String createdAuthor)
	{
		this.createdAuthor = createdAuthor;
	}

	public long getEditedTS()
	{
		return editedTS;
	}

	public void setEditedTS(long editedTS)
	{
		this.editedTS = editedTS;
	}

	public String getEditedAuthor()
	{
		return editedAuthor;
	}

	public void setEditedAuthor(String editedAuthor)
	{
		this.editedAuthor = editedAuthor;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public long getVersion()
	{
		return version;
	}

	public void setVersion(long version)
	{
		this.version = version;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		result = prime * result + (int) (version ^ (version >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataObject other = (DataObject) obj;
		if (uuid == null)
		{
			if (other.uuid != null)
				return false;
		}
		else if (!uuid.equals(other.uuid))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
	
	public int compareTo(DataObject o)
	{
		return uuid.compareTo(o.uuid);
	}
}
