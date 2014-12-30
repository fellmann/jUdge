package de.fellmann.judge.competition.persistence;

import static org.junit.Assert.*;

import org.junit.Test;

import de.fellmann.judge.competition.data.Competition;

public class DataObjectProxyTest
{
	@Test
	public void testProxy()
	{
		Competition c = DataObjectProxy.createProxyFor(Competition.class, 123);
		assertEquals(c.getId(), 123);
		c.setCreatedTS(1234);
		assertEquals(c.getCreatedTS(), 1234);
		assertEquals(c.getId(), 123);

		c.setId(1235);
		assertEquals(c.getId(), 1235);
	}
}
