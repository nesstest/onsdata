package gov.ons.ness.data.session.nessarea;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.ons.ness.data.data.NessAreaSearchDTO;
import gov.ons.ness.data.entity.NessArea;
import gov.ons.ness.data.session.AbstractFacade;

@Stateless
public class NessAreaFacade extends AbstractFacade<NessArea>
{
	@PersistenceContext(unitName = "NessDataEJB")
	private EntityManager em;

	private Logger logger = Logger
	      .getLogger(NessAreaFacade.class.getSimpleName());

	Map<String, String> replaceMap;
	Map<String, String> mysqlReplaceMap;

	public NessAreaFacade()
	{
		super(NessArea.class);

		replaceMap = new LinkedHashMap<>();
		mysqlReplaceMap = new LinkedHashMap<>();

		replaceMap.put("-", " ");
		replaceMap.put(".", "");
		replaceMap.put(",", "");
		replaceMap.put("'", "''");
		replaceMap.put("/", " ");

		mysqlReplaceMap.put("-", " ");
		mysqlReplaceMap.put(".", "");
		mysqlReplaceMap.put(",", "");
		mysqlReplaceMap.put("/", " ");
	}

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public List<NessArea> findChildren(String parentExtcode)
	{
		logger.log(Level.INFO, "findChildren: parentExtcode = " + parentExtcode);

		@SuppressWarnings("unchecked")
		List<NessArea> children = (List<NessArea>) getEntityManager()
		      .createNamedQuery("NessArea.findChildren")
		      .setParameter("parentExtcode", parentExtcode).getResultList();

		return children;
	}

	public NessArea findParent(String childExtcode)
	{
		logger.log(Level.INFO, "findParent: childExtcode = " + childExtcode);

		return (NessArea) getEntityManager()
		      .createNamedQuery("NessArea.findParent")
		      .setParameter("childExtcode", childExtcode).getSingleResult();
	}

	public List<NessArea> searchBasic(String term)
	{
		logger.log(Level.INFO, "searchBasic: wardName = " + term);

		@SuppressWarnings("unchecked")
		List<NessArea> results = (List<NessArea>) getEntityManager()
		      .createNamedQuery("NessArea.basicSearch")
		      .setParameter("searchTerm", term).getResultList();

		return results;
	}

	public List<NessAreaSearchDTO> searchName(String searchName)
	{
		logger.log(Level.INFO, "searchName: searchName = " + searchName);

		// @SuppressWarnings("unchecked")

		// The SQL way
		@SuppressWarnings("unchecked")
		List<NessAreaSearchDTO> results = (List<NessAreaSearchDTO>) getEntityManager()
		      .createNativeQuery(sqlBuilder(processSearchString(searchName)),
		            "SearchResult")
		      .getResultList();

		return results;
	}
	
	public NessArea findEnvelope(String extcode)
	{
		logger.log(Level.INFO, "findEnvelope: extcode = " + extcode);

		return (NessArea) getEntityManager()
		      .createNamedQuery("NessArea.findEnvelope")
		      .setParameter("extcode", extcode).getSingleResult();
	}

	String processSearchString(String searchTerm)
	{
		// Remove special characters from the search string since they are not
		// used in the actual search
		String output = searchTerm;

		for (String key : replaceMap.keySet())
		{
			output = output.replace(key, replaceMap.get(key));
		}

		return output;
	}

	String sqlBuilder(String searchTerm)
	{
		String sqlReplace = sqlReplace();
		String sqlUnion = sqlUnion(searchTerm);

		// Build Query
		StringBuilder sb = new StringBuilder();

		sb.append("SELECT y.* FROM (SELECT x.*, CASE WHEN ").append(sqlReplace)
		      .append(" LIKE '").append(searchTerm).append("%' THEN 0 WHEN ")
		      .append(sqlReplace).append(" LIKE '% ").append(searchTerm)
		      .append("%' THEN 1 end AS ord FROM (").append(sqlUnion)
		      .append(") x WHERE ").append(sqlReplace).append(" LIKE '")
		      .append(searchTerm).append("%' OR ").append(sqlReplace)
		      .append(" LIKE '% ").append(searchTerm)
		      .append("%') y ORDER BY  y.type = 'Country', y.type = 'Region', ")
		      .append("y.type = 'Local Authority', y.type = 'Ward', y.ord, y.name");

		logger.log(Level.INFO, "SQL: " + sb.toString());
		
		return sb.toString();
	}

	String sqlReplace()
	{
		String output = "";

		String replace = "REPLACE(, '', '')";

		StringBuilder sb;
		int i = 0;

		for (String key : mysqlReplaceMap.keySet())
		{
			if (i == 0)
			{
				sb = new StringBuilder(replace);
				sb.insert(sb.indexOf(","), "x.name");
				sb.insert(sb.indexOf("'") + 1, key);
				sb.insert(sb.lastIndexOf("'"), mysqlReplaceMap.get(key));
				output = sb.toString();
			}
			else
			{
				sb = new StringBuilder(replace);
				sb.insert(sb.indexOf("'") + 1, key);
				sb.insert(sb.lastIndexOf("'"), mysqlReplaceMap.get(key));
				sb.insert(sb.indexOf(","), output);
				output = sb.toString();
			}

			i++;
		}

		return output;
	}

	String sqlUnion(String searchTerm)
	{
		String[] terms = searchTerm.split(" ");
		StringBuilder sb = new StringBuilder();
		String initial = "SELECT na1.extcode, na1.name, na1.envelope, na1.type, IFNULL(na1.parent_extcode, '') "
				+ "AS parent_extcode, IFNULL(na2.name, '') AS parent_name FROM ness_area na1 "
		      + "LEFT OUTER JOIN ness_area na2 ON na1.parent_extcode = na2.extcode WHERE MATCH (na1.name) AGAINST ('";

		for (int i = 0; i < terms.length; i++)
		{
			if (i < terms.length - 1)
			{
				sb.append(initial).append(terms[i])
				      .append("' IN BOOLEAN MODE) UNION ");
			}
			else
			{
				sb.append(initial).append(terms[i]).append("*' IN BOOLEAN MODE)");
			}
		}

		return sb.toString();
	}
}
