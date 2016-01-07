package gov.ons.ness.data.resources;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import gov.ons.ness.data.data.NessAreaDTO;
import gov.ons.ness.data.data.NessAreaSearchDTO;
import gov.ons.ness.data.entity.NessArea;
import gov.ons.ness.data.entity.PostCode;
import gov.ons.ness.data.session.nessarea.NessAreaFacade;
import gov.ons.ness.data.session.postcode.PostCodeFacade;

@Path("nessdata")
public class NessAreaResource
{
	@Inject
	private NessAreaFacade nessAreaFacade;

	@Inject
	private PostCodeFacade postCodeFacade;

	private Logger logger = Logger
	      .getLogger(NessAreaResource.class.getSimpleName());

	@GET
	@Path("/getchildren/{parentextcode}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getChildren(@PathParam("parentextcode") String parentExtcode)
	{
		// e.g.
		// http://onsdatav3-glassfishtest.rhcloud.com/data-web/rs/nessdata/getchildren/E05002319

		logger.log(Level.INFO, "getChildren: parentExtcode = " + parentExtcode);

		List<NessArea> children = nessAreaFacade.findChildren(parentExtcode);

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

		for (NessArea na : children)
		{
			arrBuilder.add(Json.createObjectBuilder()
			      .add("extcode", na.getExtcode()).add("name", na.getName()));
		}

		JsonObject output = Json.createObjectBuilder()
		      .add("children", arrBuilder.build()).build();

		return output.toString();
	}

	@GET
	@Path("/getparent/{childextcode}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getParent(@PathParam("childextcode") String childExtcode)
	{
		// e.g.
		// http://onsdatav3-glassfishtest.rhcloud.com/data-web/rs/nessdata/getparent/E00082600
		// http://localhost:8080/data-web/rs/nessdata/getparent/E00082600

		logger.log(Level.INFO, "getParent: childExtcode = " + childExtcode);

		NessAreaDTO parent = new NessAreaDTO(
		      nessAreaFacade.findParent(childExtcode));

		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

		if (parent.hasWard())
		{
			objectBuilder.add("ward",
			      Json.createObjectBuilder()
			            .add("extcode", parent.getNessArea().getWardExtcode())
			            .add("name", parent.getNessArea().getWardName()));
		}
		
		if (parent.hasParli())
		{
			objectBuilder.add("pcon",
			      Json.createObjectBuilder()
			            .add("extcode", parent.getNessArea().getParliExtcode())
			            .add("name", parent.getNessArea().getParliName()));
		}

		if (parent.hasLa())
		{
			objectBuilder.add("la",
			      Json.createObjectBuilder()
			            .add("extcode", parent.getNessArea().getLaExtcode())
			            .add("name", parent.getNessArea().getLaName()));
		}

		if (parent.hasRegion())
		{
			objectBuilder.add("region",
			      Json.createObjectBuilder()
			            .add("extcode", parent.getNessArea().getRegExtcode())
			            .add("name", parent.getNessArea().getRegName()));
		}

		if (parent.hasCountry())
		{
			objectBuilder.add("country",
			      Json.createObjectBuilder()
			            .add("extcode", parent.getNessArea().getCountryExtcode())
			            .add("name", parent.getNessArea().getCountryName()));
		}

		JsonObject internal = objectBuilder.build();
		JsonObject output = Json.createObjectBuilder().add("parent", internal)
		      .build();

		return output.toString();
	}

	/**
	 * Not yet properly implemented.
	 * 
	 * @param term
	 * @return
	 */
	@GET
	@Path("/basicsearch")
	@Produces({ MediaType.APPLICATION_JSON })
	public String basicSearch(@QueryParam("term") String term)
	{
		// e.g.
		// http://onsdatav3-glassfishtest.rhcloud.com/data-web/rs/nessdata/basicsearch?term=fareham

		logger.log(Level.INFO, "getWards: wardName = " + term);

		List<NessArea> results = nessAreaFacade.searchBasic("%" + term + "%");

		StringBuilder sb = new StringBuilder();
		sb.append("[");

		for (NessArea nasr : results)
		{
			JsonObject searchItem = Json.createObjectBuilder()
			      .add("label", nasr.getName()).add("value", nasr.getName())
			      .add("type", nasr.getType()).add("extcode", nasr.getExtcode())
			      .build();
			sb.append(searchItem.toString()).append(",");
		}

		if (results.size() > 0)
		{
			sb.deleteCharAt(sb.length() - 1); // Lop off the trailing comma
		}
		
		sb.append("]");

		return sb.toString();
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON })
	public String search(@QueryParam("term") String term)
	{

		// e.g.
		// http://onsdatav3-glassfishtest.rhcloud.com/data-web/rs/nessdata/search?term=fareham
		// http://localhost:8080/data-web/rs/nessdata/search?term=felin
		
		logger.log(Level.INFO, "search: searchName = " + term);

		// The SQL way
		List<NessAreaSearchDTO> results = nessAreaFacade
		      .searchName(term);

		// The Java way
		
//		List<NessAreaSearchDTO> results1 = nessAreaFacade
//		      .searchName(term.toLowerCase() + "%");
//		List<NessAreaSearchDTO> results2 = nessAreaFacade
//		      .searchName("% " + term.toLowerCase() + "%");
//		List<NessAreaSearchDTO> results3 = nessAreaFacade
//		      .searchName("%" + term.toLowerCase() + "%");
//		
//		List<NessAreaSearchDTO> results = new ArrayList<>();
//		
//		results2.removeAll(results1);
//		results3.removeAll(results1);
//		results3.removeAll(results2);
//		
//		Collections.sort(results1);
//		Collections.sort(results2);
//		Collections.sort(results3);
//		
//		results.addAll(results1);
//		results.addAll(results2);
//		results.addAll(results3);		

		StringBuilder sb = new StringBuilder();
		sb.append("[");

		for (NessAreaSearchDTO nasr : results)
		{
			JsonObject searchItem = Json.createObjectBuilder()
			      .add("label", nasr.getName()).add("value", nasr.getName())
			      .add("type", nasr.getType()).add("extcode", nasr.getExtcode())
			      .add("envelope", nasr.getEnvelope())
			      .add("parent_extcode", nasr.getParentExtcode())
			      .add("parent_name", nasr.getParentName())
			      .build();
			sb.append(searchItem.toString()).append(",");
		}

		if (results.size() > 0)
		{
			sb.deleteCharAt(sb.length() - 1); // Lop off the trailing comma
		}
		
		sb.append("]");

		return sb.toString();
	}

	@GET
	@Path("/getchildrenextcode/{parentextcode}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getChildrenExtcode(
	      @PathParam("parentextcode") String parentExtcode)
	{
		// e.g.
		// http://onsdatav3-glassfishtest.rhcloud.com/data-web/rs/nessdata/getchildrenextcode/E05002319
		// http://localhost:8080/data-web/rs/nessdata/getchildrenextcode/E05002319

		logger.log(Level.INFO,
		      "getChildrenExtcode: parentExtcode = " + parentExtcode);

		List<NessArea> children = nessAreaFacade.findChildren(parentExtcode);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"children\":[");

		for (NessArea na : children)
		{
			sb.append("\"").append(na.getExtcode()).append("\"").append(",");
		}

		if (children.size() > 0)
		{
			sb.deleteCharAt(sb.length() - 1); // Lop off the trailing comma
		}
		
		sb.append("]}");

		return sb.toString();
	}

	@GET
	@Path("/getpostcode/{postcode}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getPostCode(@PathParam("postcode") String postcode)
	{
		// e.g.
		// http://onsdatav3-glassfishtest.rhcloud.com/data-web/rs/nessdata/getpostcode/po138js
		// http://localhost:8080/data-web/rs/nessdata/getpostcode/po138js

		logger.log(Level.INFO, "getPostCode: postcode = " + postcode);
		
		if (postcode.length() < 5)
		{
			return "No postcode found for " + postcode;
		}
		
		String processedPostcode = processPostcode(postcode);
		
		logger.log(Level.INFO, "getPostCode: processed postcode = " + processedPostcode);

		
		PostCode pc = postCodeFacade.findPostCode(processedPostcode);
		
		if (pc == null)
		{
			return "No postcode found for " + postcode;
		}

		JsonObject attributes = Json.createObjectBuilder()
		      .add("attributes",
		            Json.createObjectBuilder().add("pcds", pc.getPcds())
		                  .add("doterm",
		                        (String) (pc.getDoterm() != null ? pc.getDoterm().toString()
		                              : ""))
		                  .add("ctrycd", pc.getCtrycd())
		                  .add("ctrynm", pc.getCtrynm())
		                  .add("gorcd", pc.getGorcd()).add("gornm", pc.getGornm())
		                  .add("pconcd", pc.getPconcd())
		                  .add("pconnm", pc.getPconnm())
		                  .add("ccgcd", pc.getCcgcd()).add("ccgnm", pc.getCcgnm())
		                  .add("oa11cd", pc.getOa11cd())
		                  .add("lauacd", pc.getLauacd())
		                  .add("lauanm", pc.getLauanm())
		                  .add("wardcd", pc.getWardcd())
		                  .add("wardnm", pc.getWardnm())
		                  .add("oaeast1m", pc.getOaeast1m())
		                  .add("oanrth1m", pc.getOanrth1m()).build())
		      .build();

		JsonObject features = Json.createObjectBuilder()
		      .add("features", Json.createArrayBuilder().add(attributes).build())
		      .build();

		return features.toString();
	}

	private String processPostcode(String postcode)
	{
		// If there's no space add one.
		StringBuilder output = new StringBuilder();
		
		output.append(postcode);

		if (!postcode.contains(" "))
		{
			output.insert(postcode.length() - 3, " ");
		}

		return output.toString().toUpperCase();
	}
	
	@GET
	@Path("/getenvelope/{extcode}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getEnvelope(@PathParam("extcode") String extcode)
	{
		// e.g.
		// http://onsdatav3-glassfishtest.rhcloud.com/data-web/rs/nessdata/getenvelope/E00082600
		// http://localhost:8080/data-web/rs/nessdata/getenvelope/E00082600

		logger.log(Level.INFO, "getEnvelope: extcode = " + extcode);

		NessArea na = nessAreaFacade.findEnvelope(extcode);
		
		return Json.createObjectBuilder().add("envelope", na.getEnvelope()).build().toString();
	}
}
