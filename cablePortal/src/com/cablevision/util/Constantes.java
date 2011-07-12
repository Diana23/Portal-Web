package com.cablevision.util;

public class Constantes {

	/**
	 * Constante para guardar en sesion la informacion de la cuenta del usuario
	 */
	public final static String SESSION_MI_CUENTA="_MI_CUENTA";

	/**
	 * Constante para guardar en sesion la informacion de la cuenta del usuario
	 */
	public final static String SESSION_ACCOUNT_ID="_ACCOUNT_ID";

	/**
	 * Constante para guardar en session el password del usuario, es necesario
	 * por que vitria lo ocupa si el usuario no modifica la contrase\u00F1a
	 */
	public final static String SESSION_MI_PASSWD="_PASSWORD";

	/**
	 * Patron para sustituir el inicio de la url del contenido proveniente de UCM
	 */
	public final static String UCM_CONTENIDO_REMPLAZO = "/contentserver/";

	/**
	 * Cadena para encriptar el password
	 */
	public final static String ENCRIPT_PASSWD="AB23FB4";

	/**
	 * Constante para guardar en cache datos del contenido Estructurado
	 */
	public final static String CABLEVISION_CONTENIDO = "CABLEVISION_CONTENIDO_CACHE";
	public final static String CONTENIDO_CACHE_KEY = "contenido.cache.key.";
	public final static String TEMPLETES_CACHE_KEY = "templates.cache.key.";
	
	/**
	* Constante para guardar en session a que url se debe ir despues 
	 * de hacer el login
	 */
	public final static String SESSION_LOGIN_NEXT_URI="_NEXT_URI";

	/**
	 * Nombre del Log de Seguridad
	 */
	public static final String SEGURIDAD_LOG_NAME="com.cablevision.seguridad.Bitacora";

	/**
	 * Acciones del log de seguridad 
	 */
	public static final String LOG_ACCION_ENTRADA="ENTRADA";
	public static final String LOG_ACCION_SALIDA="SALIDA";

	/**
	 * Estados del log de seguridad 
	 */
	public static final String LOG_ESTADO_INGRESO_EXITOSO = "INGRESO-EXITOSO";
	public static final String LOG_ESTADO_INGRESO_FALLO = "INGRESO-FALLO";
	public static final String LOG_ESTADO_CAMBIO_CONTRASENIA = "CAMBIO-CONTRASEÑA";
	public static final String LOG_ESTADO_BLOQUEO_CONTRASENIA = "BLOQUEO-CONTRASEÑA";
	public static final String LOG_ESTADO_SALIDA_EXISTOSO = "SALIDA-EXISTOSO";

	/**
	 * Constante para guardar si la contraseña del usuario loggeado tiene mas de 90 dias
	 * */
	public static final String CONTRASENA_EXPIRADA = "_CONTRASENA_EXPIRADA";

	/**
	 * Constante para guardar si un usuario logueado existe en vitria
	 */
	public static final String USUARIO_EN_VITRIA = "_USUARIO_EN_VITRIA";

	public static final Integer CANALES_ORDENAR_NOMBRE_CANAL = 1;
	public static final Integer CANALES_ORDENAR_NUMERO_CANAL = 2;
	public static final Integer CANALES_ORDENAR_CATEGORIAS = 3;
	public static final Integer CANALES_ORDENAR_PAQUETES = 4;
	
	/**
	 * Constante para guardar en cache datos de los combos para ordenes administrativas
	 */
	public final static String CABLEVISION_COMBOS = "CABLEVISION_COMBOS_NAME";
	public final static String COMBO_CACHE_KEY = "combo.cache.key.";

	/**
	 * Constante para guardar en cache los canales de la seccion canales
	 */
	public final static String CABLEVISION_CANALES = "CABLEVISION_CANALES_CACHE";
	public final static String CANALES_CACHE_KEY = "canales.cache.key";
	
	/**
	 * Constante para guardar en cache un mapa con las url nuevas a usarse a partir de Julio del 2011
	 */
	public final static String CABLEVISION_URL_NUEVAS = "CABLEVISION_URL_CACHE";
	public final static String URL_CACHE_KEY = "url.cache.key.";
	
	public static final String CABLEVISION_URL = "CABLEVISION_URL_REDIRECT";
}

