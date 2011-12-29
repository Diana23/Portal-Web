<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>

<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>

<jsp:scriptlet>
	RespuestaToMyAccount account = (RespuestaToMyAccount)request.getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
	String nombreContacto = account.getNombreContacto() != null ? account.getNombreContacto() : "";
</jsp:scriptlet>





                   


						<div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>" >
							BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
						</div>
				
				
				
					<div class="clear"></div>
					
					<div class="wcs-marg">
						<div class="span-16 last textcenter">
							<p class="marg-rig15"><h3><strong>Test de velocidad</strong></h3>Calcula la velocidad de tu conexi&oacute;n de CABLEVISION&reg; con esta sencilla herramienta.<br/>Exclusivo para Clientes conectados a trav&eacute;s del servicio de Internet de Alta Velocidad de CABLEVISION&reg;.</p>
                             <iframe src="http://192.168.200.189:81/noc_velocidad" width="640" height="400" scrolling="no">
							  <p>Este navegador no soporta iframes.</p>
							</iframe>
							<br />
						</div>
						<div class="clear"><br/></div>
						<div class="span-16">
								<a href="${pageContext.request.contextPath}/medidor" title="Reiniciar Test" class="btn-small fright">Reiniciar Test</a>
						</div>
					
						<div class="clear"></div>
					</div>
					
					<!--<h2 class="side ico-caracteristicas padd-long-left">Caracter&iacute;sticas</h2>-->
					<div class="span-16  wcs-marg bord-toped">
						<div class="span-8">
							<div class="padding-right20">					
								<h3 class="title-content bs-2-lines">¿C&oacute;mo se calcula la velocidad?</h3><br>
								<p>La velocidad de tu conexi&oacute;n a Internet de CABLEVISION&reg; se hace descargando un archivo de 500KB desde el servidor de CABLEVISION&reg; hasta tu PC o MAC y calculando el tiempo que tarda hasta completarse la descarga. Posteriormente aplicamos una f&oacute;rmula sencilla para determinar tu velocidad de conexi&oacute;n a Internet de Alta Velocidad de CABLEVISION&reg;. 
 <br>
							    <br>
									<a href="${pageContext.request.contextPath}/contratar" title="P&Iacute;delo Ahora" class="btn-small fright">P&iacute;delo Ahora</a>
								</p>
							  <div class="clear"></div>
							</div>
						</div>						
						<div class="span-8 last">
								<div class="imgspeedtest"></div><!--<img src="img/Av_img01.png" alt="Velocidades vertiginosas de descarga y env&iacute;o de datos." />-->
						</div>						
						<div class="clear"></div>
					</div>
					
                    <div class=" span-16 bord-toped img-resultado ">
						<div class="span-5">&nbsp;

						</div>						
						<div style="margin-top:15px" class="span-12 last">
							<h3 class="title-content bs-2-lines">¿De qu&eacute; manera interpreto los resultados?</h3><br>
							<p>Esta medici&oacute;n indica el tiempo que se tarda tu computadora en descargar una cantidad de datos determinados. La velocidad de conexi&oacute;n a Internet se mide en Mbps (Megabits por segundo, o millones de bits por segundo).</p>
						</div>						
						<div class="clear"></div>
					</div>
                    
                    
                    
                    
                    					
					
					<!--<div class="wcs-marg bord-toped">
						<div class="span-8">
							<div class="padding-right20" >					
								<img src="img/internetVel.png" alt="CABLEVISION<sup>Â®</sup> On Demand" />
							</div>
						</div>						
						<div class="span-8 last">
							<h3 class="title-content bs-2-lines">¿Cu&aacute;l es el secreto de la Velocidad de nuestro Internet?</h3><br/>
							<p>
								En lugar de un m&oacute;dem est&aacute;ndar de computadora conectado a una lÃ­nea telefÃ³nica local, CABLEVISION<sup>Â®</sup> te proporciona un Cable MÃ³dem de Alta Velocidad que conecta tu computadora a la avanzada red de Fibra Ã“ptica mejorada de CABLEVISION<sup>Â®</sup>. Â¿Y quÃ© conexiÃ³n crees que tiene m&aacute;s ancho de banda? SÃºper r&aacute;pido. SÃºper simple. <br /><br />
								<a href="#" title="Saber m&aacute;s" class="btn-small fright">Saber m&aacute;s</a>
							</p>
						</div>						
						<div class="clear"></div>
					</div>-->					
		
					<div class="span-16  wcs-marg bord-toped">
						<div class="span-8">
							<div class="padding-right20">					
								<h3 class="title-content bs-2-lines">¿Por qu&eacute; cambia el resultado al repetir las pruebas en el mismo equipo y Cablemodem? </h3><br>
								<p>Los resultados que obtienes mediante la prueba de velocidad var&iacute;a de una mil&eaacute;sima de segundo a otra, debido a que el ancho de banda nunca es constante, esto se debe a diferentes factores f&iacute;sicos como la distancia entre el Cablemodem y tu equipo, muros por los que atraviesa la se&ntilde;al, y l&oacute;gicos como congesti&oacute;n en la red (muchos equipos conectados simult&aacute;neamente), horarios pico y aplicaciones abiertas.</p>


	<br><br>						
<!--									<table class="wrap-btns-dis">
										<tr>
											<td width="150px" align="right">
												<input type="text" class="cod-postal" name="disponibilidad" />
												<span class="disp-error-small">Error! Lorem ipsum sid arnet</span>	
											</td>
											<td width="150px">
												<a href="#" title="Ver Disponibilidad" class="btn-small bs-2-lines fright">Ver Disponibilidad</a>
											</td>
										</tr>			
									</table>-->
						
								<div class="clear"></div>
							</div>
						</div>						
						<div class="span-8 last">
							<div class="imgvariaciones"></div><!--<img src="img/Av_img02.png" alt="CABLEVISION<sup>&reg;</sup> On Demand" class="floatimgs" />-->
						</div>						
						<div class="clear"></div>
					</div>
					
					
					<div class="span-16  wcs-marg bord-toped">
						<div class="span-8">
							<div class="padding-right20">					
								<div class="imgtransferencia"></div><!--<img src="img/Av_img03.png" alt="CABLEVISION<sup>&reg;</sup> On Demand" class="floatimgs"  />-->
							</div>
						</div>						
						<div class="span-8 last">
							<h3 class="title-content">¿Cu&aacute;l es la diferencia entre ancho de Banda y tasa de transferencia?
</h3><br>

							<p>Ancho de Banda corresponde a la capacidad para transferir informaci&oacute;n o datos entre tu  equipo y la nube de Internet, mientras que la tasa de transferencia se refiere a la cantidad de informaci&oacute;n o datos que pueden ser transferidos, de forma real y en un momento determinado, desde un Servidor hacia tu computadora. 

<br><br>
							</p>
						</div>						
						<div class="clear"></div>
					</div>					
		<div class="span-16  wcs-marg bord-toped">
						<div class="span-8">
							<div class="padding-right20">					
								<h3 class="title-content bs-2-lines">¿Por qu&eacute; mi navegaci&oacute;n es m&aacute;s lenta de lo que contrat&eacute;? </h3><br>
								<p>Tu velocidad de navegaci&oacute;n puede verse afectada por diferentes factores, tales como distancia entre el Cablemodem y tu equipo, muros por los que atraviesa la se&ntilde;al; y l&oacute;gicos como congesti&oacute;n en la red (muchos equipos conectados simult&aacute;neamente), la hora del d&iacute;a, el tr&aacute;fico que existe en la red a la hora en que te conectas y aplicaciones abiertas.
</p>

	<br><br>						
<!--									<table class="wrap-btns-dis">
										<tr>
											<td width="150px" align="right">
												<input type="text" class="cod-postal" name="disponibilidad" />
												<span class="disp-error-small">Error! Lorem ipsum sid arnet</span>	
											</td>
											<td width="150px">
												<a href="#" title="Ver Disponibilidad" class="btn-small bs-2-lines fright">Ver Disponibilidad</a>
											</td>
										</tr>			
									</table>-->
						
								<div class="clear"></div>
							</div>
						</div>						
						<div class="span-8 last">
							<div class="imgvelocidad"></div><!--<img src="img/Av_img02.png" alt="CABLEVISION<sup>&reg;</sup> On Demand" class="floatimgs" />-->
						</div>						
						<div class="clear"></div>
					</div>
					
                    <div class="span-16  wcs-marg bord-toped">
						<div class="span-8">
							<div class="padding-right20">					
								<div class="imgafectconexion"></div><!--<img src="img/Av_img03.png" alt="CABLEVISION<sup>&reg;</sup> On Demand" class="floatimgs"  />-->
							</div>
						</div>						
						<div class="span-8 last">
							<h3 class="title-content">¿Qu&eacute; factores afectan la calidad de mi conexi&oacute;n?</h3><br>							
                            <ul class="bullet-fact">
								Muchos factores influyen en la velocidad a la que est&aacute;s navegando, entre ellos:<br><br>
								<li>Condiciones f&iacute;sicas y l&oacute;gicas de tu computadora.</li>
								<li>La distancia entre el Cablemodem y el sitio desde el que te conectas a Internet.</li>
								<li>Muros u obst&aacute;culos por los que atraviesa la se&ntilde;al.</li>
								<li>El n&uacute;mero de dispositivos conectados a tu Cablemodem v&iacute;a Ethernet o WiFi (La velocidad contratada se reparte entre las conexiones / aplicaciones 
   existentes y usadas).</li>
								<li>Virus, SPAM, Troyanos o Gusanos.</li>
								<li>Utilizaci&oacute;n de programas de intercambio de archivos P2P (punto a punto).</li>
</ul>
<a target="_blank" href="http://www.cablevision.net.mx/paquetes/elige" class="linkhigh">Incrementa tu velocidad, conoce nuestros paquetes.</a>

<br>
							
						</div>						
						<div class="clear"></div>
					</div>

				

