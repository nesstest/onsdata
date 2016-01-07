package gov.ons.ness.data.session.nessarea;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

public class NessAreaFacadeTest
{

	@Test
	public void testProcessSearchString()
	{
		// fail("Not yet implemented");
	}

	@Test
	public void testSqlBuilder()
	{
		// fail("Not yet implemented");
		String searchTerm = "st m";
		NessAreaFacade naf = new NessAreaFacade();
		String expectedOutput = "SELECT y.* FROM (SELECT x.*, CASE WHEN "
				+ "REPLACE(REPLACE(REPLACE(REPLACE(x.name, '-', ' '), '.', ''), ',', ''), '/', ' ') "
				+ "LIKE 'st m%' THEN 0 WHEN REPLACE(REPLACE(REPLACE(REPLACE(x.name, '-', ' ')"
				+ ", '.', ''), ',', ''), '/', ' ') LIKE '% st m%' THEN 1 end AS ord FROM "
				+ "(SELECT na1.extcode, na1.name, na1.type, na1.parent_extcode, na2.name AS parent_name "
				+ "FROM ness_area na1 JOIN ness_area na2 ON na1.parent_extcode = na2.extcode WHERE MATCH "
				+ "(na1.name) AGAINST ('st' IN BOOLEAN MODE) "
				+ "UNION "
				+ "SELECT na1.extcode, na1.name, na1.type, na1.parent_extcode, na2.name AS parent_name "
				+ "FROM ness_area na1 JOIN ness_area na2 ON na1.parent_extcode = na2.extcode "
				+ "WHERE MATCH (na1.name) AGAINST ('m*' IN BOOLEAN MODE)) x "
				+ "WHERE REPLACE(REPLACE(REPLACE(REPLACE(x.name, '-', ' '), '.', ''), ',', ''), '/', ' ') "
				+ "LIKE 'st m%' OR REPLACE(REPLACE(REPLACE(REPLACE(x.name, '-', ' '), '.', ''), ',', '')"
				+ ", '/', ' ') LIKE '% st m%') y "
				+ "ORDER BY y.ord, y.name";
		String actualOutput = naf.sqlBuilder(searchTerm);

		assertTrue(actualOutput, actualOutput.equals(expectedOutput));
	}

	@Test
	public void testSqlReplace()
	{
		Map<String, String> replaceMap = new LinkedHashMap<>();
		replaceMap.put("-", " ");
		replaceMap.put(".", "");
		replaceMap.put(",", "");
		replaceMap.put("/", " ");
		NessAreaFacade naf = new NessAreaFacade();
		String expectedOutput = "REPLACE(REPLACE(REPLACE(REPLACE(x.name, '-', ' '), '.', ''), ',', ''), '/', ' ')";
		
		String output = naf.sqlReplace();

		assertTrue(output, output.equals(expectedOutput));
	}

}
