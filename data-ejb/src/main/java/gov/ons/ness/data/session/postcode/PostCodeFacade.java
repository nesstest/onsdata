package gov.ons.ness.data.session.postcode;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import gov.ons.ness.data.entity.PostCode;
import gov.ons.ness.data.session.AbstractFacade;

@Stateless
public class PostCodeFacade extends AbstractFacade<PostCode>
{
	@PersistenceContext(unitName = "NessDataEJB")
	private EntityManager em;

	private Logger logger = Logger
	      .getLogger(PostCodeFacade.class.getSimpleName());

	public PostCodeFacade()
	{
		super(PostCode.class);
	}

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}
	
	public PostCode findPostCode(String postcode)
	{
		logger.log(Level.INFO, "findPostCode: postcode = " + postcode);

		try 
		{
			return (PostCode) getEntityManager()
			      .createNamedQuery("NessArea.findPostCode")
			      .setParameter("postcode", postcode).getSingleResult();
		}
		catch (NoResultException nre)
		{
			return null;
		}
	}
}
