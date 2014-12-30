package de.fellmann.judge.competition.data;

public abstract class DataObject implements Comparable<DataObject>
{
	private static int maxID = 0;
	
	private int id = maxID ++;
	protected long version = 0;
	protected long editTS = System.currentTimeMillis();
	
	public long getEditTS()
	{
		return editTS;
	}

	public void setEditTS(long editTS)
	{
		this.editTS = editTS;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (id != other.id)
			return false;
		return true;
	}

	public long getCreatedTS()
	{
		return editTS;
	}

	public void setCreatedTS(long createdTS)
	{
		this.editTS = createdTS;
	}

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
		return Integer.compare(id, o.id);
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
