package com.cablevision.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cablevision.controller.mcafeedownloads.McafeeDownloadsController.ReportBean;
import com.cablevision.dao.IMcafeeDownloadsDao;
import com.cablevision.vo.CvMcafee;
import com.cablevision.vo.CvMcafeeDownload;
import com.cablevision.vo.CvMcafeeHistorico;
import com.cablevision.vo.CvMcafeeReset;
import com.cablevision.vo.CvMcafeeUser;
import com.cablevision.vo.CvMcafeesuscribed;

/**
 * The DAO class for the entities: CvMcafeeDownload, CvMcafeeReset,
 * CvMcafeeUser.
 */
public class McafeeDownloadsHibernateDao extends HibernateDaoSupport implements
		IMcafeeDownloadsDao {
	public McafeeDownloadsHibernateDao() {
		super();
	}

	/**
	 * Return the persistent entities returned from a named query.
	 */
	public List findByNamedQuery(String queryName) {
		return getHibernateTemplate().findByNamedQuery(queryName);
	}
	@Override
	public List getResumenDownloads(final String tipoProducto){
		try{
			return (List)getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					
					String query1="select COUNT(DISTINCT(MUS_ACCOUNT)),  to_date(to_char(MUS_DATESUSCRIBE,'mm/yyyy'),'mm/yyyy') from CV_MCAFEEUSERS " +
							"group by to_date(to_char(MUS_DATESUSCRIBE,'mm/yyyy'),'mm/yyyy') "+ 
					"order by to_date(to_char(MUS_DATESUSCRIBE,'mm/yyyy'),'mm/yyyy') asc ";
					
					String query2 ="select count(*),  to_date(to_char(MCA_DATESUSCRIBE,'mm/yyyy'),'mm/yyyy') from cv_mcafee "+
					" group by to_date(to_char(MCA_DATESUSCRIBE,'mm/yyyy'),'mm/yyyy') "+  
					" order by to_date(to_char(MCA_DATESUSCRIBE,'mm/yyyy'),'mm/yyyy') asc"; 
					
					String query ="";
					if(tipoProducto.equalsIgnoreCase("ANTERIOR"))
						query = query1;
					else
						query = query2;
					
					return session.createSQLQuery(query).list();
				}
			});
		} catch(RuntimeException re){
			re.printStackTrace();
			throw re;
		}
	}
	@Override
	public List getResumenPorFecha(final Date fechaInicio, final Date fechaFinal, final String status, final String tipoProducto){
		try{
			return (List)getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Calendar finalCalendar = Calendar.getInstance();
					Query query = null;
					Query queryNuevoProducto = null;
					List resultado = null;
					
					
					
					if(fechaFinal==null){
						finalCalendar.setTime(fechaInicio);
					}else{
						finalCalendar.setTime(fechaFinal);
					}
					finalCalendar.add(Calendar.DATE, 1);
					
					
					System.out.println("FECHA INICIO::"+fechaInicio);
					System.out.println("FECHA FINAL::"+finalCalendar.getTime());
					
					/*
					 "select count(*) from (select U.MUS_ACCOUNTID from cv_mcafeesuscribed d, cv_mcafeeusers u "+
							"where d.mte_iduser = u.mus_id and TRIM(u.mus_cvstatus)=:STATUS "+
							"AND D.MTE_SUSCRIBEDATE < :FECHAINICIO  "+
							"GROUP BY(U.MUS_ACCOUNTID)) "+
							"union all "+
							"select count(*) from (select U.MUS_ACCOUNTID from cv_mcafeesuscribed d, cv_mcafeeusers u "+
							"where d.mte_iduser = u.mus_id and TRIM(u.mus_cvstatus)=:STATUS "+
							"AND D.MTE_SUSCRIBEDATE >= :FECHAINICIO AND D.MTE_SUSCRIBEDATE<:FECHAFINAL "+
							"GROUP BY(U.MUS_ACCOUNTID))"); 
					 */
					
					if(tipoProducto.equalsIgnoreCase("ANTERIOR")){
						String _query = "select count(*) from (select U.MUS_ACCOUNT from  cv_mcafeeusers u "+
						"where ";
						if(!status.equalsIgnoreCase("TODOS"))
							_query = _query + "TRIM(u.mus_cvstatus)=:STATUS AND ";
						_query = _query + " u.MUS_DATESUSCRIBE < :FECHAINICIO  "+
						"GROUP BY(U.MUS_ACCOUNT)) "+
						"union all "+
						"select count(*) from (select U.MUS_ACCOUNT from cv_mcafeeusers u "+
						"where ";
						if(!status.equalsIgnoreCase("TODOS"))
							_query = _query + "TRIM(u.mus_cvstatus)=:STATUS AND ";
						_query = _query + " u.MUS_DATESUSCRIBE >= :FECHAINICIO AND u.MUS_DATESUSCRIBE<:FECHAFINAL "+
						"GROUP BY(U.MUS_ACCOUNT))";
					query = session.createSQLQuery(_query);
					if(!status.equalsIgnoreCase("TODOS"))
						query.setParameter("STATUS", status);
					
						query.setParameter("FECHAINICIO", fechaInicio);
						query.setParameter("FECHAFINAL", finalCalendar.getTime());
						resultado = query.list();
						
					}else if(tipoProducto.equalsIgnoreCase("NUEVO")){
						String _querynuevo = "select count(*) from (select U.mca_account from cv_mcafee u "+ 
						"where ";
						
						if(!status.equalsIgnoreCase("TODOS"))
						_querynuevo = _querynuevo + "TRIM(u.mca_cvstatus)=:STATUS AND "; 
						
						_querynuevo = _querynuevo +" U.MCA_DATESUSCRIBE < :FECHAINICIO "+ 
						"GROUP BY(U.mca_account)) "+ 
						"union all "+ 
						"select count(*) from (select U.mca_account from cv_mcafee u "+ 
						"where ";
						if(!status.equalsIgnoreCase("TODOS"))
							_querynuevo = _querynuevo + "TRIM(u.mca_cvstatus)=:STATUS AND ";
						
						_querynuevo = _querynuevo + " U.MCA_DATESUSCRIBE >= :FECHAINICIO AND u.MCA_DATESUSCRIBE<:FECHAFINAL "+ 
						"GROUP BY(U.mca_account))";
						
					queryNuevoProducto = session.createSQLQuery(_querynuevo);
						if(!status.equalsIgnoreCase("TODOS"))
							queryNuevoProducto.setParameter("STATUS", status);
					
						queryNuevoProducto.setParameter("FECHAINICIO", fechaInicio);
						queryNuevoProducto.setParameter("FECHAFINAL", finalCalendar.getTime());
						resultado =  queryNuevoProducto.list();
						System.out.println("resultado del query::"+resultado);
					}
					return resultado;
				}
			});
		} catch(RuntimeException re){
			re.printStackTrace();
			throw re;
		}
	}
	
	
	private List<CvMcafeeHistorico> convierteBeanHistorico(List<CvMcafee> lista){
		
		List<CvMcafeeHistorico> listtmp = new ArrayList<CvMcafeeHistorico>();
	
		if(lista != null){
			Iterator<CvMcafee> iterNuevo = lista.iterator();
			
			while(iterNuevo.hasNext()){
				CvMcafeeHistorico tmp = new CvMcafeeHistorico();
				CvMcafee mcafee = iterNuevo.next();
				tmp.setMcaAccountNumber(mcafee.getMcaAccount());
				tmp.setMcaCvStatus(mcafee.getMcaCvstatus());
				tmp.setMcaFechaInicio(mcafee.getMcaDatestatus());
				tmp.setMcaMotivo(mcafee.getMcaMotivo());
				tmp.setMcaReference(mcafee.getMcaReference());
				listtmp.add(tmp);
			}
		}
		return listtmp;
	}
	
	private List<CvMcafeeHistorico> convierteBeanHistoricoAnt(List<CvMcafeeUser> lista){
		
		List<CvMcafeeHistorico> listtmp = new ArrayList<CvMcafeeHistorico>();
	
		if(lista != null){
			Iterator<CvMcafeeUser> iter = lista.iterator();
			
			while(iter.hasNext()){
				CvMcafeeHistorico tmp = new CvMcafeeHistorico();
				CvMcafeeUser mcafee = iter.next();
				tmp.setMcaAccountNumber(mcafee.getMusAccount());
				tmp.setMcaCvStatus(mcafee.getMusCvstatus());
				tmp.setMcaFechaInicio(mcafee.getMusDatesuscribe());
				tmp.setMcaMotivo(mcafee.getMusCvmotivo());
				tmp.setMcaReference(mcafee.getMusReference());
				listtmp.add(tmp);
			}
		}
		return listtmp;
	}
	public List<CvMcafeeHistorico> getReporteDesglosado(ReportBean params){
		
		List listaRetorno;
		List listAnterior;
		String query = "";
		ArrayList<CvMcafeeUser> resultado = null;
		ArrayList<CvMcafee> resultadoNuevo = null;
		ArrayList<CvMcafeeHistorico> resultadoHistorico = new ArrayList<CvMcafeeHistorico>();
		List<CvMcafeeHistorico> tmplist = null;
		String status = params.getEstatus();
		System.out.println("ESTATUS::"+status);
		String mesLetra;
		
		Calendar calendar = Calendar.getInstance();
		String fechaLetra;
		Date fechaInicio;
		Date fechaFinal;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//POR CUENTA
		if(params.getSelConsulta().equals("CUENTA")){
				
			if(params.getTipoProducto().equalsIgnoreCase("ANTERIOR")){

				StringBuffer queryString = new StringBuffer();
				
				if(!status.equalsIgnoreCase("TODOS")){
					queryString.append("from CvMcafeeUser where musAccount = ? and trim(musCvstatus) = ? ");
					Object[] _params = {Long.parseLong(params.getCuenta()),status};
					
					listAnterior =getHibernateTemplate().find(queryString.toString(), _params);
				}else{
					queryString.append("from CvMcafeeUser where musAccount = ?");
					Object[] _params = {Long.parseLong(params.getCuenta())};
					listAnterior =getHibernateTemplate().find(queryString.toString(), _params);
				}
				
				List<CvMcafeeHistorico> listtmp =  convierteBeanHistoricoAnt(listAnterior);
				resultadoHistorico.addAll(0, listtmp);
				
			}else if (params.getTipoProducto().equalsIgnoreCase("NUEVO")){
				DetachedCriteria criteria = DetachedCriteria.forClass(CvMcafee.class);
				criteria.add(Property.forName("mcaAccount").eq(Long.parseLong(params.getCuenta())));
				if(!status.equalsIgnoreCase("TODOS")){
					criteria.add(Property.forName("mcaCvstatus").eq(status));
				}
				resultadoNuevo = (ArrayList<CvMcafee>)getHibernateTemplate().findByCriteria(criteria,0,9999);
				
				DetachedCriteria criteriaHist = DetachedCriteria.forClass(CvMcafeeHistorico.class);
				criteriaHist.add(Property.forName("mcaAccountNumber").eq(Long.parseLong(params.getCuenta())));
				criteriaHist.add(Property.forName("mcaTipoProducto").eq(params.getTipoProducto()));
				if(!status.equalsIgnoreCase("TODOS")){
					criteriaHist.add(Property.forName("mcaCvStatus").eq(status));
				}
				
				//resultadoHistorico 
				tmplist= getHibernateTemplate().findByCriteria(criteriaHist,0,Integer.MAX_VALUE);
				resultadoHistorico.addAll(0, tmplist);
				
				List<CvMcafeeHistorico> listtmp = convierteBeanHistorico(resultadoNuevo);
				resultadoHistorico.addAll(0, listtmp);
			}
			
		}else//POR MES 
			if(params.getSelConsulta().equals("MES")){
				
				
				String mes = params.getMes();
				
				if(mes != null && !mes.equals("") && !mes.equals("...")){
					try{
						mesLetra = mes.substring(5);
						
						mes = mes.concat("/01");//como solo trae anho y mes le concateno el dia 01 para que la fecha de inicio sea apartir del 1er dia del mes
						mes = mes.replace('/', '-');//le doy formato yyyy-mm-dd
						fechaInicio = sdf.parse(mes);
						
						calendar.setTime(fechaInicio);//apartir de la fecha de inicio obtengo la final
						calendar.add(Calendar.MONTH, 1);//le sumo 1 mes
						calendar.add(Calendar.DATE, -1);//le resto 1 dia para obtener el ultimo dia del mes
						
						fechaFinal = calendar.getTime();
						
						//fechaLetra = meses[mesNumero-1].concat(" ").concat(mes.substring(0, 4));//Abril 2008
						
						if(params.getTipoProducto().equalsIgnoreCase("ANTERIOR")){
							StringBuffer queryString = new StringBuffer();
							
							if(!status.equalsIgnoreCase("TODOS")){
								queryString.append("from CvMcafeeUser where musDatesuscribe >= ? and musDatesuscribe <= ? and trim(musCvstatus) = ? ");
								Object[] _params = {fechaInicio,fechaFinal,status};								
								
								listAnterior =getHibernateTemplate().find(queryString.toString(), _params);
							}else{
								queryString.append("from CvMcafeeUser where musDatesuscribe >= ? and musDatesuscribe <= ?");
								Object[] _params = {fechaInicio,fechaFinal};
								listAnterior =getHibernateTemplate().find(queryString.toString(), _params);
							}
							
							List<CvMcafeeHistorico> listtmp =  convierteBeanHistoricoAnt(listAnterior);
							resultadoHistorico.addAll(0, listtmp);
							
							
						}else if (params.getTipoProducto().equalsIgnoreCase("NUEVO")){
							DetachedCriteria criteria = DetachedCriteria.forClass(CvMcafee.class);
							criteria.add(Property.forName("mcaDatestatus").between(fechaInicio, fechaFinal));
							if(!status.equalsIgnoreCase("TODOS")){
								criteria.add(Property.forName("mcaCvstatus").eq(status));
							}
							resultadoNuevo = (ArrayList)getHibernateTemplate().findByCriteria(criteria,0,Integer.MAX_VALUE);
							
							DetachedCriteria criteriaHist = DetachedCriteria.forClass(CvMcafeeHistorico.class);
							criteriaHist.add(Property.forName("mcaFechaInicio").ge(fechaInicio));
							criteriaHist.add(Property.forName("mcaFechaFin").le(fechaFinal));
							criteriaHist.add(Property.forName("mcaTipoProducto").eq(params.getTipoProducto()));
							if(!status.equalsIgnoreCase("TODOS")){
								criteriaHist.add(Property.forName("mcaCvStatus").eq(status));
							}
							//resultadoHistorico 
							tmplist= getHibernateTemplate().findByCriteria(criteriaHist,0,Integer.MAX_VALUE);
							resultadoHistorico.addAll(0, tmplist);
							
							List<CvMcafeeHistorico> listtmp = convierteBeanHistorico(resultadoNuevo);
							resultadoHistorico.addAll(0, listtmp);
							
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
			
		}else// POR DIA 
			if(params.getSelConsulta().equals("DIA")){
				String dia = params.getDia();
				
				if(dia != null && !dia.equals("") && !dia.equals("...")){
					try{
						fechaInicio = sdf.parse(dia);
						Calendar cinicial = Calendar.getInstance();
						Calendar cfinal = Calendar.getInstance();
						
						
						cinicial.setTime(fechaInicio);
						cfinal.setTime(fechaInicio);
						
						cinicial.add(Calendar.DATE , -1);
						cfinal.add(Calendar.DATE , 1);
						
						//fechaInicio = cinicial.getTime();
						fechaFinal = cfinal.getTime();
						
						System.out.println("fechaInicio::"+fechaInicio);
						System.out.println("fechaFinal::"+fechaFinal);
						
						if(params.getTipoProducto().equalsIgnoreCase("ANTERIOR")){
							
							StringBuffer queryString = new StringBuffer();
							
							if(!status.equalsIgnoreCase("TODOS")){
								queryString.append("from CvMcafeeUser where musDatesuscribe >= ? and musDatesuscribe <= ? and trim(musCvstatus) = ? ");
								Object[] _params = {fechaInicio,fechaFinal,status};								
								
								listAnterior =getHibernateTemplate().find(queryString.toString(), _params);
							}else{
								queryString.append("from CvMcafeeUser where musDatesuscribe >= ? and musDatesuscribe <= ?");
								Object[] _params = {fechaInicio,fechaFinal};
								listAnterior =getHibernateTemplate().find(queryString.toString(), _params);
							}
							
							List<CvMcafeeHistorico> listtmp =  convierteBeanHistoricoAnt(listAnterior);
							resultadoHistorico.addAll(0, listtmp);
							
						}else if (params.getTipoProducto().equalsIgnoreCase("NUEVO")){
							DetachedCriteria criteria = DetachedCriteria.forClass(CvMcafee.class);
							criteria.add(Property.forName("mcaDatestatus").between(fechaInicio, fechaFinal));
							if(!status.equalsIgnoreCase("TODOS")){
								criteria.add(Property.forName("mcaCvstatus").eq(status));
							}
							resultadoNuevo = (ArrayList)getHibernateTemplate().findByCriteria(criteria,0,Integer.MAX_VALUE);
							
							DetachedCriteria criteriaHist = DetachedCriteria.forClass(CvMcafeeHistorico.class);
							criteriaHist.add(Property.forName("mcaFechaInicio").between(fechaInicio, fechaFinal));
							criteriaHist.add(Property.forName("mcaFechaFin").between(fechaInicio, fechaFinal));
							criteriaHist.add(Property.forName("mcaTipoProducto").eq(params.getTipoProducto()));
							if(!status.equalsIgnoreCase("TODOS")){
								criteriaHist.add(Property.forName("mcaCvStatus").eq(status));
							}
							tmplist= getHibernateTemplate().findByCriteria(criteriaHist,0,Integer.MAX_VALUE);
							resultadoHistorico.addAll(0, tmplist);
							
							List<CvMcafeeHistorico> listtmp = convierteBeanHistorico(resultadoNuevo);
							resultadoHistorico.addAll(0, listtmp);
							
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			
		}else// POR PERIODO 
			if(params.getSelConsulta().equals("PERIODO")){
				try{
				fechaInicio = sdf.parse(params.getFechaDe());
				fechaFinal = sdf.parse(params.getFechaA());
				
				if(params.getTipoProducto().equalsIgnoreCase("ANTERIOR")){
					StringBuffer queryString = new StringBuffer();
					
					if(!status.equalsIgnoreCase("TODOS")){
						queryString.append("from CvMcafeeUser where musDatesuscribe >= ? and musDatesuscribe <= ? and trim(musCvstatus) = ? ");
						Object[] _params = {fechaInicio,fechaFinal,status};								
						
						listAnterior =getHibernateTemplate().find(queryString.toString(), _params);
					}else{
						queryString.append("from CvMcafeeUser where musDatesuscribe >= ? and musDatesuscribe <= ?");
						Object[] _params = {fechaInicio,fechaFinal};
						listAnterior =getHibernateTemplate().find(queryString.toString(), _params);
					}
					
					List<CvMcafeeHistorico> listtmp =  convierteBeanHistoricoAnt(listAnterior);
					resultadoHistorico.addAll(0, listtmp);
					
				}else if (params.getTipoProducto().equalsIgnoreCase("NUEVO")){
					DetachedCriteria criteria = DetachedCriteria.forClass(CvMcafee.class);
					criteria.add(Property.forName("mcaDatestatus").between(fechaInicio,fechaFinal));
					if(!status.equalsIgnoreCase("TODOS")){
						criteria.add(Property.forName("mcaCvstatus").eq(status));
					}
					resultadoNuevo = (ArrayList)getHibernateTemplate().findByCriteria(criteria,0,Integer.MAX_VALUE);
					
					DetachedCriteria criteriaHist = DetachedCriteria.forClass(CvMcafeeHistorico.class);
					criteriaHist.add(Property.forName("mcaFechaInicio").ge(fechaInicio));
					criteriaHist.add(Property.forName("mcaFechaFin").le(fechaInicio));
					criteriaHist.add(Property.forName("mcaTipoProducto").eq(params.getTipoProducto()));
					if(!status.equalsIgnoreCase("TODOS")){
						criteriaHist.add(Property.forName("mcaCvStatus").eq(status));
					}
					tmplist = getHibernateTemplate().findByCriteria(criteriaHist,0,Integer.MAX_VALUE);
					resultadoHistorico.addAll(0, tmplist);
					
					List<CvMcafeeHistorico> listtmp = convierteBeanHistorico(resultadoNuevo);
					resultadoHistorico.addAll(0, listtmp);
				
				}
				}catch(Exception e){
					e.printStackTrace();
				}
				
			
		}
		
		return resultadoHistorico;
	}
	
	@Override
	public List getOrigen(final Date fechaInicio, final Date fechaFinal, final String status){
		try{
			return (List)getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Calendar finalCalendar = Calendar.getInstance();
					if(fechaFinal==null){
						finalCalendar.setTime(fechaInicio);
					}else{
						finalCalendar.setTime(fechaFinal);
					}
					finalCalendar.add(Calendar.DATE, 1);
					Query query = session.createSQLQuery(
							"select COUNT(*) , s.MTE_SOURCE from cv_mcafeesuscribed s, cv_mcafeeusers u "+
							" where s.mte_iduser = u.mus_id and TRIM(u.mus_cvstatus) = :STATUS AND "+
					" s.mte_suscribedate >= :FECHAINICIO AND s.MTE_SUSCRIBEDATE < :FECHAFINAL group by s.MTE_SOURCE");
					
					query.setParameter("STATUS", status);
					query.setParameter("FECHAINICIO", fechaInicio);
					query.setParameter("FECHAFINAL", finalCalendar.getTime());
					return query.list();
				}
			});
		} catch(RuntimeException re){
			re.printStackTrace();
			throw re;
		}
	}
	@Override
	public CvMcafeesuscribed getSuscribdByAccount(Integer account){
		List list = getHibernateTemplate().find("from CvMcafeesuscribed where mteAccount=? ",new Long(account));
		
		if(list.size()>0){
			return (CvMcafeesuscribed)list.get(0);
		}else{
			return null;
		}
	}
	
	public List<CvMcafeeDownload> getDownloadsByUser(Integer idUser){
		return getHibernateTemplate().find("from CvMcafeeDownload where mdlIduser=?",idUser);
	}
	@Override
	public List getSuscripciones(final Date fechaInicio, final Date fechaFinal, final String status,int inicio,int maxResults,String tipoProducto){
		System.out.println("fecha inicio::"+fechaInicio+" fechaFinal::"+fechaFinal);
		List resultado =  null; 
		
		Calendar finalCalendar = Calendar.getInstance();
		if(fechaFinal==null){
			finalCalendar.setTime(fechaInicio);
		}else{
			finalCalendar.setTime(fechaFinal);
		}
		finalCalendar.add(Calendar.DATE, 1);
		
		if(tipoProducto.equalsIgnoreCase("ANTERIOR")){
			
			/*DetachedCriteria criteria = DetachedCriteria.forClass(CvMcafeesuscribed.class);
			
			criteria.add(Property.forName("mteSuscribedate").ge(fechaInicio));
			criteria.add(Property.forName("mteSuscribedate").lt(finalCalendar.getTime()));
			criteria.add(Property.forName("mteStatus").eq(status));
			*/
			
			String queryString = "from CvMcafeeUser where musDatesuscribe >= ? and musDatesuscribe < ? ";
			
			if(!status.equalsIgnoreCase("TODOS")){
				queryString = queryString + " and TRIM(musCvstatus)= ?";
			
				Object[] params = {fechaInicio,fechaFinal,status};
				resultado =getHibernateTemplate().find(queryString, params);
			}else{
				Object[] params = {fechaInicio,fechaFinal};
				resultado =getHibernateTemplate().find(queryString, params);
			}
			
			
			/*DetachedCriteria criteria = DetachedCriteria.forClass(CvMcafeeUser.class);
			
			criteria.add(Property.forName("musDatesuscribe").ge(fechaInicio));
			criteria.add(Property.forName("musDatesuscribe").lt(finalCalendar.getTime()));
			criteria.add(Property.forName("musCvstatus").eq(status)); 
		
			resultado = getHibernateTemplate().findByCriteria(criteria,inicio,maxResults);
			*/
			//System.out.println("RESULTADO::"+resultado);
		}else{
			DetachedCriteria criteria = DetachedCriteria.forClass(CvMcafee.class);
			
			criteria.add(Property.forName("mcaDatesuscribe").ge(fechaInicio));
			criteria.add(Property.forName("mcaDatesuscribe").lt(finalCalendar.getTime()));
			if(!status.equalsIgnoreCase("TODOS"))
				criteria.add(Property.forName("mcaCvstatus").eq(status));
			
			
			resultado = getHibernateTemplate().findByCriteria(criteria,inicio,maxResults);
		}
		if(resultado == null){
			System.out.println("NO SE ENCONTRARON RESULTADOS PARA EL DETALLE DE REPORTE MCAFEE");
			resultado = new ArrayList();
		}
		
		return resultado;
	}

	/**
	 * Return the persistent entities returned from a named query with named
	 * parameters.
	 */
	public List findByNamedQuery(String queryName, String[] paramNames,
			Object[] paramValues) {
		if (paramNames.length != paramValues.length) {
			throw new IllegalArgumentException();
		}
		return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName,
				paramNames, paramValues);
	}

	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List findByExample(Object example) {
		return getHibernateTemplate().findByExample(example);
	}

	/**
	 * Find an entity by its id (primary key).
	 * 
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvMcafeeDownload findCvMcafeeDownloadById(Long id) {
		return (CvMcafeeDownload) getHibernateTemplate().load(
				CvMcafeeDownload.class, id);
	}

	/**
	 * Return all persistent instances of the <code>CvMcafeeDownload</code>
	 * entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafeeDownload> findAllCvMcafeeDownloads() {
		return getHibernateTemplate().loadAll(CvMcafeeDownload.class);
	}

	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeDownload(CvMcafeeDownload cvMcafeeDownload) {
		getHibernateTemplate().saveOrUpdate(cvMcafeeDownload);
	}

	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeDownload(CvMcafeeDownload cvMcafeeDownload) {
		getHibernateTemplate().delete(cvMcafeeDownload);
	}

	/**
	 * Find an entity by its id (primary key).
	 * 
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvMcafeeReset findCvMcafeeResetById(Long id) {
		return (CvMcafeeReset) getHibernateTemplate().load(CvMcafeeReset.class,
				id);
	}

	/**
	 * Return all persistent instances of the <code>CvMcafeeReset</code>
	 * entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafeeReset> findAllCvMcafeeResets() {
		return getHibernateTemplate().loadAll(CvMcafeeReset.class);
	}

	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeReset(CvMcafeeReset cvMcafeeReset) {
		getHibernateTemplate().saveOrUpdate(cvMcafeeReset);
	}

	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeReset(CvMcafeeReset cvMcafeeReset) {
		getHibernateTemplate().delete(cvMcafeeReset);
	}

	/**
	 * Return all persistent instances of the <code>CvMcafeeUser</code>
	 * entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafeeUser> findAllCvMcafeeUsers() {
		return getHibernateTemplate().loadAll(CvMcafeeUser.class);
	}

	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeUser(CvMcafeeUser cvMcafeeuser) {
		getHibernateTemplate().saveOrUpdate(cvMcafeeuser);
	}
	
	public void updateCvMcafeeUserStatus(Long id, String status, String motivo, Timestamp fechaStatus){
		Query query = getSession(true).createQuery("update CvMcafeeUser set musCvstatus = :STATUS , musCvmotivo = :MOTIVO ,musCvdatestatus = :FECHASTATUS " +
		" where musId = :ID");
		query.setParameter("STATUS", status);
		query.setParameter("MOTIVO", motivo);
		query.setParameter("FECHASTATUS", fechaStatus);
		query.setParameter("ID", id);
		query.executeUpdate();
	}
	
	@Override
	public void persistCvMcafeesuscribed(CvMcafeesuscribed suscribed) {
		getHibernateTemplate().saveOrUpdate(suscribed);
	}
	@Override
	public void aumentarDownload(final String account){
		try{
			getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createSQLQuery(
							"update cv_mcafeesuscribed "+
							" set mte_attemps=mte_attemps+1 where mte_account=:ACCOUNT");
					
					query.setParameter("ACCOUNT", account);
					return query.executeUpdate();
				}
			});
		} catch(RuntimeException re){
			re.printStackTrace();
			throw re;
		}
		
		
	}

	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeUser(CvMcafeeUser cvMcafeeuser) {
		getHibernateTemplate().delete(cvMcafeeuser);
	}

	@Override
	public CvMcafeeUser getMcafeeUserByAccount(Long account) {
		String queryString = "from CvMcafeeUser where musAccount=?";
		CvMcafeeUser temp = null;
		
		List<CvMcafeeUser> mcafeeUsers = getHibernateTemplate().find(queryString, account);
		if (mcafeeUsers.size() > 0){
			temp = mcafeeUsers.get(0);
			
			if(mcafeeUsers.size() > 1)
				temp.setRegistroMultiple(true);
			else 
				temp.setRegistroMultiple(false);
		}
		
		return temp;
	}

	@Override
	public List<CvMcafeeDownload> getMcafeeDownloadsByUserAccount(Long account) {
		String queryString = "from CvMcafeeDownload where mdlIduser=?";
		List<CvMcafeeDownload> mcafeeDownloads = getHibernateTemplate().find(
				queryString, account);
		return mcafeeDownloads;
	}

	@Override
	public CvMcafeeReset getMcafeeReset(Long account) {
		String queryString = "from CvMcafeeReset where mreAccount=?";
		List<CvMcafeeReset> mcafeeReset = getHibernateTemplate().find(
				queryString, account);
		if (mcafeeReset.size() > 0)
			return mcafeeReset.get(0);
		return null;
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return  The found entity instance or null if the entity does not exist.
	 */
	public CvMcafee findCvMcafeeById(Long id) {
		return (CvMcafee) getHibernateTemplate().load(CvMcafee.class, id);
	}

	/**
	 * Return all persistent instances of the <code>CvMcafee</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafee> findAllCvMcafees() {
		return getHibernateTemplate().loadAll(CvMcafee.class);
	}

	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafee(CvMcafee cvMcafee) {
		getHibernateTemplate().saveOrUpdate(cvMcafee);
	}

	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafee(CvMcafee cvMcafee) {
		getHibernateTemplate().delete(cvMcafee);
	}
	
	/**
	 * Cambiar el estatus de un cvmacafee dado
	 */	
	public void updateCvMcafeeStatus(Long id, String status, String motivo, Timestamp fechaStatus){
		Query query = getSession(true).createQuery("update CvMcafee set mcaCvstatus = :STATUS , mcaMotivo = :MOTIVO ,mcaDatestatus = :FECHASTATUS " +
		" where mcaId = :ID");
		query.setParameter("STATUS", status);
		query.setParameter("MOTIVO", motivo);
		query.setParameter("FECHASTATUS", fechaStatus);
		query.setParameter("ID", id);
		query.executeUpdate();
	}
	
	public CvMcafee getMcafeeByAccount(Long account) {
		String queryString = "from CvMcafee where mcaAccount=?";
		List<CvMcafee> mcafee = getHibernateTemplate().find(queryString, account);
		
		CvMcafee temp = null;
		
		if (mcafee.size() > 0){
			temp = mcafee.get(0);
			if(mcafee.size() > 1)
				temp.setRegistroMultiple(true);
			else
				temp.setRegistroMultiple(false);
		}
		return temp;
	}
	
	public void persistCvMcafeeHistorico(CvMcafeeHistorico datoHistorico){
		getHibernateTemplate().saveOrUpdate(datoHistorico);
	}
}