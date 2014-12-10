package de.fellmann.common;

import java.util.ArrayList;
import java.util.Vector;

/**
 * ArrayList with special functions for int lists.
 * 
 * @author Hanno Fellmann
 *
 */
public class IntList {
    final ArrayList<Integer> source;
	
    @SuppressWarnings("unchecked")
	private IntList(IntList toClone) {
    	source = (ArrayList<Integer>) toClone.source.clone();
    }
    
    public static IntList getFromTo(int from, int to)
    {
		IntList all = new IntList();

		for (int i = from; i <= to; i++) {
			all.add(i);
		}
		return all;
    }
    
    public IntList byval() {
    	return new IntList(this);
    }
    
	public int indexOf(int x) {
		for(int i=0;i<source.size();i++) {
		  if(source.get(i).equals(x)) {
			  return i;
		  }
		}
		return -1;
	}

	public IntList()
	{ 
		source = new ArrayList<Integer>();
	}

	public void addbegin(int x) 
	{
		source.add(0, x);
	}

	public void add(int x) 
	{ 
	 source.add(x);
	}

	public int get(int i)
	{
	  return source.get(i);
	}

	public int size()
	{
	  return source.size();
	}

	public void remove(int x) 
	{ 
	  int i=indexOf(x);
	  source.remove(i);
	}

	public void removeIndex(int i) 
	{ 
	  source.remove(i);
	}
	
	public void removeAll(IntList v) 
	{ 
	  for(int i=0;i<v.size();i++) {
		  remove(v.get(i));
	  }
	}

	public void clear()
	{
	  source.clear();
	}

	public boolean contains(int x) {
	  return (indexOf(x)>=0);
	}
}