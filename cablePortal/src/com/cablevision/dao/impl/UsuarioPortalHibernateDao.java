package com.cablevision.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cablevision.dao.IUsuarioPortalDao;
import com.cablevision.vo.CvContrasenaHistorial;
import com.cablevision.vo.CvUsuarioPortal;

/**
 * The DAO class for the entities: CvContrasenaHistorial, CvUsuarioPortal.
 */
public class UsuarioPortalHibernateDao extends HibernateDaoSupport implements IUsuarioPortalDao {
	public UsuarioPortalHibernateDao() {
		super();
	}
	/**
	 * Return the persistent entities returned from a named query.
	 */
	public List findByNamedQuery(String queryName) {
		return getHibernateTemplate().findByNamedQuery(queryName);
	}
	/**
	 * Return the persistent entities returned from a named query with named parameters.
	 */
	public List findByNamedQuery(String queryName, String[] paramNames, Object[] paramValues) {
		if (paramNames.length != paramValues.length) {
			throw new IllegalArgumentException();
		}
		return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, paramValues);
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List findByExample(Object example) {
		return getHibernateTemplate().findByExample(example);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvContrasenaHistorial findCvContrasenaHistorialById(Long id) {
		return (CvContrasenaHistorial)getHibernateTemplate().load(CvContrasenaHistorial.class, id);
	}
	/**
	 * Return all persistent instances of the <code>CvContrasenaHistorial</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvContrasenaHistorial> findAllCvContrasenaHistorials() {
	 	return getHibernateTemplate().loadAll(CvContrasenaHistorial.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvContrasenaHistorial(CvContrasenaHistorial cvContrasenaHistorial) {
		List<CvContrasenaHistorial> listaContrasenas = findCvContrasenaHistorialByIdUser(cvContrasenaHistorial.getCvUsuarioPortal().getCupIdusuario());
		if(listaContrasenas.size() >= 3){
			Long idMenor = null;
			for(CvContrasenaHistorial contrasena : listaContrasenas){
				if(idMenor== null || (idMenor!= null && (idMenor.intValue() > contrasena.getCchIdContrasenaHistorial().intValue())) ){
					idMenor = contrasena.getCchIdContrasenaHistorial();
				}
			}
			removeCvContrasenaHistorial(findCvContrasenaHistorialById(idMenor));
		}

		getHibernateTemplate().saveOrUpdate(cvContrasenaHistorial);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvContrasenaHistorial(CvContrasenaHistorial cvContrasenaHistorial) {
		getHibernateTemplate().delete(cvContrasenaHistorial);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvUsuarioPortal findCvUsuarioPortalById(String id) {
		List<CvUsuarioPortal> usuario = getHibernateTemplate().find("from CvUsuarioPortal where cupIdusuario = ? ",new Object[]{id});
		
		if(usuario!=null&&usuario.size()>0){
			return usuario.get(0);
		}
		
		return null;
		
	}
	/**
	 * Return all persistent instances of the <code>CvUsuarioPortal</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvUsuarioPortal> findAllCvUsuarioPortals() {
	 	return getHibernateTemplate().loadAll(CvUsuarioPortal.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvUsuarioPortal(CvUsuarioPortal cvUsuarioPortal) {
		getHibernateTemplate().saveOrUpdate(cvUsuarioPortal);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvUsuarioPortal(CvUsuarioPortal cvUsuarioPortal) {
		getHibernateTemplate().delete(cvUsuarioPortal);
	}
	
	public boolean updateCvUsuarioPortal(String idUsuario, String foto) {
		Query query = getSession(true).createQuery("update CvUsuarioPortal set cupFoto = :foto" +
				" where cupIdusuario = :idUsuario");
		query.setParameter("foto", foto);
		query.setParameter("idUsuario", idUsuario);
		int result = query.executeUpdate();
		return result > 0;
	}
	
	/**
	 * Return all persistent instances of the <code>CvContrasenaHistorial</code> entity by user id.
	 * @param idUser
	 * @return List<CvContrasenaHistorial>
	 */
	@SuppressWarnings("unchecked")
	public  List<CvContrasenaHistorial> findCvContrasenaHistorialByIdUser(String idUser){		
		StringBuffer query = new StringBuffer();
		query.append(" from CvContrasenaHistorial cch where cch.cvUsuarioPortal.cupIdusuario = '");
		query.append(idUser);
		query.append("' order by cch.cchIdContrasenaHistorial desc");

		return getHibernateTemplate().find(query.toString());
	}
	
	public void removeSessionIds(){
		try{
			getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery("UPDATE CvUsuarioPortal SET cupSessionId = '' "+
												      "WHERE cupSessionId IS NOT NULL");
					return query.executeUpdate();
				}
			});
		} catch(RuntimeException re){
			re.printStackTrace();
			throw re;
		}
	}
	
}