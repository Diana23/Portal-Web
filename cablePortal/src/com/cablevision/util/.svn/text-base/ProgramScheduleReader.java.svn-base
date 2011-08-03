/**
 * 
 */
package com.cablevision.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.zip.GZIPInputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.access.DefaultLocatorFactory;
import org.springframework.jdbc.UncategorizedSQLException;

import com.cablevision.service.IChannelService;
import com.cablevision.service.IProgramScheduleService;
import com.cablevision.vo.CvProgramRecord;
import com.cablevision.vo.CvScheduleRecord;
import com.cablevision.vo.CvScheduleRecordPK;
import com.cablevision.vo.CvStationRecord;
import com.cablevision.vo.CvTimeZoneRecord;
import com.cablevision.vo.CvTranslationRecord;
import com.cablevision.vo.CvTranslationRecordPK;
import com.cablevision.vo.Cvchannel;

/**
 * Clase usada para leer los campos en los archivos para la programacion y llenar las tablas
 * @author <a href="mailto:jorge.ruiz@jwmsolutions.com"> Jorge Ruiz Aquino </a>
 */
public class ProgramScheduleReader {
	private static IProgramScheduleService programScheduleService;
	private static IChannelService channelService;
	/**
	 * Esta variable debe ser llenada con datos antes de usarse; 
	 * usando el m\u00E9todo populateChannelMap()
	 */
	private Map<String, Cvchannel> channels;
	
	private Map<String, CvProgramRecord> programas;
	
	public ProgramScheduleReader() {
		channels = new HashMap<String, Cvchannel>();
		programas = new HashMap<String, CvProgramRecord>();
	}
	
	/**
	 * Llena el mapa channels con el key:station_num y el value:tms_chan
	 * solo con aquellos que est\u00E9n activos dependiendo de la fecha
	 */
	public void populateChannelMap() throws IOException, FileNotFoundException {
		File file = new File(ConfigurationHelper.getPropiedad("programacion.temporal", null) + "/" + ConfigurationHelper.getPropiedad("programacion.archivo.channel", null));
		
		String stringRow = null;
		if(file.exists()) {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream (file), "CP850" );
			BufferedReader br = new BufferedReader (inputStreamReader);
			
			//Ir por los canales que estan en CV_CHANNEL
			Map<String,Cvchannel> cvChannelsMap = new HashMap<String, Cvchannel>();
			try {
				List<Cvchannel> cvChannels = getChannelService().findAllCvchannels();
				
				for (Cvchannel cvchannel : cvChannels) {
					cvChannelsMap.put(cvchannel.getIdchannel().toString(), cvchannel);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			while((stringRow = br.readLine()) != null) {
				int count = 0;
				boolean digital = false;
				String key = "";
				String value = "";
				Date effectiveDate = null;
				Date expirationDate = null;
				for (String token : stringRow.split("\\|")) {
					token = token.trim();
					switch(++count) {
					case 3: digital = (token.toUpperCase().equals("X"))?true:false; break;
					case 4: key = (!token.equals(""))?token:null; break;
					case 5: value = token; break;
					case 7: // Format: yyyymmdd
						if(token.equals(""))
							break;
						effectiveDate = formatDate(token, "yyyyMMdd");
						break;
					case 8: // Format: yyyymmdd
						if(token.equals(""))
							break;
						expirationDate = formatDate(token, "yyyyMMdd");
						break;
					}
				}
				Calendar d1 = Calendar.getInstance();
				Calendar d2 = Calendar.getInstance();
				boolean active = false;
				if(expirationDate == null && effectiveDate != null) {
					active = true;
				} else if(expirationDate != null) {
					d2.setTimeInMillis(expirationDate.getTime());
					if(d2.after(d1))
						active = true;
				}
				if(active && digital) {
					if(cvChannelsMap.containsKey(value)){
						channels.put(key, cvChannelsMap.get(value));
					}
				}
			}
		}
	}
	
	/**
	 * Obtiene todos los archivos necesarios para la programaci\u00F3n desde el FTP,
	 * los guarda en disco, los descomprime y guarda nuevamente en disco
	 * @throws IOException
	 */
	public void getFilesFromFTP() throws IOException {
		String host = ConfigurationHelper.getPropiedad("programacion.ftp.host", null);
		String directory = ConfigurationHelper.getPropiedad("programacion.ftp.directory", null);
		String username = ConfigurationHelper.getPropiedad("programacion.ftp.usr", null);
		String password = ConfigurationHelper.getPropiedad("programacion.ftp.pwd", null);
		String localPath = ConfigurationHelper.getPropiedad("programacion.temporal", null);
		String[] txtFiles = {
				ConfigurationHelper.getPropiedad("programacion.archivo.programrecord", null),
				ConfigurationHelper.getPropiedad("programacion.archivo.stationrecord", null),
				ConfigurationHelper.getPropiedad("programacion.archivo.schedulerecord", null),
				ConfigurationHelper.getPropiedad("programacion.archivo.translationrecord", null),
				ConfigurationHelper.getPropiedad("programacion.archivo.timezonerecord", null),
				ConfigurationHelper.getPropiedad("programacion.archivo.channel", null)
		};


		// Descarga de FTP y lo guarda en disco
		FTPClient client= new FTPClient();
		client.connect(host);
		client.login(username, password);
		client.enterLocalPassiveMode();
		client.cwd(directory);
		client.setFileType(FTPClient.BINARY_FILE_TYPE);
		for(int i=0; i<txtFiles.length; i++) {
			String txtFile = txtFiles[i];
			String gzipFile = txtFiles[i] + ".gz";
			File f = new File(localPath);
			f.mkdirs();
			FileOutputStream fos = new FileOutputStream(localPath + gzipFile);
			try {
				client.retrieveFile(gzipFile, fos);
			} catch(FTPConnectionClosedException cce) {
				System.out.println("Exception on " + Calendar.getInstance().getTime().toString());
				cce.printStackTrace();
			}
			fos.flush();
			fos.close();

			InputStream is = new FileInputStream(localPath+gzipFile);
			GZIPInputStream gzip = new GZIPInputStream(is);
			InputStreamReader isr = new InputStreamReader(gzip);
			BufferedReader br = new BufferedReader(isr);
			File uncompressed = new File(localPath + txtFile);
			if(uncompressed.exists()) {
				uncompressed.delete();
				//uncompressed.createNewFile();
			}
			
			byte[] bytes = new byte[1024];
			FileOutputStream out = new FileOutputStream(uncompressed);
			int bytesRead;
			
			while((bytesRead = gzip.read(bytes))!=-1){
				out.write(bytes,0,bytesRead);
			}

			fos.flush();
			fos.close();
			is.close();
			gzip.close();
			isr.close();
			br.close();
		}
		client.logout();
		client.disconnect();
	}
	
	/**
	 * Abre un archivo de texto bien formado y decide el tipo de objeto que eber\u00E1 ser persistido en BD.
	 * @param fileName El nombre del archivo de texto a cargar
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	public void loadFile(String fileName) throws FileNotFoundException, IOException, Exception {
		File file = new File(ConfigurationHelper.getPropiedad("programacion.temporal", null) + fileName);
		String stringRow = null;
		Object vo = null;
		int voUsingOption = (fileName.equals(ConfigurationHelper.getPropiedad("programacion.archivo.programrecord", null)))? 1 : 
				(fileName.equals(ConfigurationHelper.getPropiedad("programacion.archivo.stationrecord", null)))? 2: 
					(fileName.equals(ConfigurationHelper.getPropiedad("programacion.archivo.schedulerecord", null)))? 3 : 
					(fileName.equals(ConfigurationHelper.getPropiedad("programacion.archivo.translationrecord", null)))? 4 : 
						(fileName.equals(ConfigurationHelper.getPropiedad("programacion.archivo.timezonerecord", null)))? 5 : -1;

		if ( file.exists() ) {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream (file), "CP850" );
			BufferedReader br = new BufferedReader (inputStreamReader);
			
			List<Map<String, String>> toIndex = new ArrayList<Map<String,String>>();
			List collection = new ArrayList();
			while((stringRow = br.readLine()) != null) {
				switch(voUsingOption) {
				case 1: vo = generateProgramRecordVo(stringRow); break;
				case 2: vo = generateStationRecordVo(stringRow); break;
				case 3: vo = generateScheduleRecordVo(stringRow); break;
				case 4: vo = generateTranslationRecordVo(stringRow); break;
				case 5: vo = generateTimeZoneRecordVo(stringRow); break;
				}
				if(vo != null)
					collection.add(vo);
				else
					continue;
				
				if(voUsingOption==3){
					CvScheduleRecord voSchedule = (CvScheduleRecord)vo;
					//Indexar en Solr
					CvProgramRecord program = programas.get(voSchedule.getCompId().getSkeDatabaseKey());
					
					if(program!=null){
						Map<String, String> scheduleRecord =  new HashMap<String, String>();
						scheduleRecord.put("id", voSchedule.getCompId().getSkeDatabaseKey()+voSchedule.getCompId().getSkeStationNum().toString()+voSchedule.getCompId().getSkeAirDateTime());
						
						
						if(StringUtils.isNotEmpty(program.getProEpiTitle())&&!program.getProEpiTitle().equals(program.getProTitle())){
							scheduleRecord.put("titulo", program.getProTitle()+" - "+program.getProEpiTitle());
						}else{
							scheduleRecord.put("titulo", program.getProTitle());
						}
						scheduleRecord.put("canal", voSchedule.getCompId().getSkeStationNum().toString());
						scheduleRecord.put("descripcion", program.getProDesc());
						scheduleRecord.put("fechaini", SolrHelper.Date2UTC(voSchedule.getCompId().getSkeAirDateTime()));
						scheduleRecord.put("fechafin", SolrHelper.Date2UTC(voSchedule.getSkeEndDateTime()));
						scheduleRecord.put("tipo", "programacion");
						toIndex.add(scheduleRecord);
						
					}else{
						System.out.println(">>>>>>>> Program null"+voSchedule.getCompId().getSkeDatabaseKey());
					}
					
				}
				
				if(collection.size() == 500) {
					System.out.println("Guardando "+collection.size()+" datos ("+voUsingOption+")");
					try {
						switch(voUsingOption) {
						case 1: getProgramScheduleService().saveAllProgramRecords(collection); break;
						case 2: getProgramScheduleService().saveAllStationRecords(collection); break;
						case 3: getProgramScheduleService().saveAllScheduleRecords(collection); break;
						case 4: getProgramScheduleService().saveAllTranslationRecords(collection); break;
						case 5: getProgramScheduleService().saveAllTimeZoneRecords(collection); break;
						}
						System.out.println("Datos Guardados");
						collection = null;
						collection = new ArrayList();
					} catch (UncategorizedSQLException usqle) {
						System.out.println(vo.toString());
						usqle.printStackTrace();
					}
					try {
						SolrHelper.indexarContenido(toIndex);
						SolrHelper.getSolrServer().commit();
						toIndex = new ArrayList<Map<String,String>>();
					} catch (Exception e) {
						
					}
				}
			}
			try {
				System.out.println("Guardando "+collection.size()+" datos ("+voUsingOption+")");
				switch(voUsingOption) {
				
				case 1: getProgramScheduleService().saveAllProgramRecords(collection); break;
				case 2: getProgramScheduleService().saveAllStationRecords(collection); break;
				case 3: getProgramScheduleService().saveAllScheduleRecords(collection); break;
				case 4: getProgramScheduleService().saveAllTranslationRecords(collection); break;
				case 5: getProgramScheduleService().saveAllTimeZoneRecords(collection); break;
				
				}
				SolrHelper.indexarContenido(toIndex);
				SolrHelper.getSolrServer().commit();
				System.out.println("Datos Guardados");
			} catch (UncategorizedSQLException usqle) {
				System.out.println(vo.toString());
				usqle.printStackTrace();
			}
		}
	}
	
	/**
	 * Crea un objeto de tipo CvProgramRecord obteniendo los campos de la linea dada
	 * @param stringRow La linea con los datos del registro a crearse
	 * @return Un objeto CvProgramRecord para guardar
	 */
	private CvProgramRecord generateProgramRecordVo(String stringRow) {
		CvProgramRecord vo = new CvProgramRecord();
		int count = 0;
		String temp = "";
		for (String token : stringRow.split("\\|")) {
			token = token.trim();
			switch(++count) {
			case 1: vo.setProDatabaseKey(token); break;
			case 2: vo.setProTitle(token); break;
			case 3: vo.setProReducedTitle1(token); break;
			case 4: vo.setProReducedTitle2(token); break;
			case 5: vo.setProReducedTitle3(token); break;
			case 6: vo.setProReducedTitle4(token); break;
			case 7: vo.setProAltTitle(token); break;
			case 8: vo.setProReducedDesc1(token); break;
			case 9: vo.setProReducedDesc2(token); break;
			case 10: vo.setProReducedDesc3(token); break;
			case 11: vo.setProAdvisoryDesc1(token); break;
			case 12: vo.setProAdvisoryDesc2(token); break;
			case 13: vo.setProAdvisoryDesc3(token); break;
			case 14: vo.setProAdvisoryDesc4(token); break;
			case 15: vo.setProAdvisoryDesc5(token); break;
			case 16: vo.setProAdvisoryDesc6(token); break;
			case 17: vo.setProCastFirstName1(token); break;
			case 18: vo.setProCastLastName1(token); break;
			case 19: vo.setProCastRoleDesc1(token); break;
			case 20: vo.setProCastFirstName2(token); break;
			case 21: vo.setProCastLastName2(token); break;
			case 22: vo.setProCastRoleDesc2(token); break;
			case 23: vo.setProCastFirstName3(token); break;
			case 24: vo.setProCastLastName3(token); break;
			case 25: vo.setProCastRoleDesc3(token); break;
			case 26: vo.setProCastFirstName4(token); break;
			case 27: vo.setProCastLastName4(token); break;
			case 28: vo.setProCastRoleDesc4(token); break;
			case 29: vo.setProCastFirstName5(token); break;
			case 30: vo.setProCastLastName5(token); break;
			case 31: vo.setProCastRoleDesc5(token); break;
			case 32: vo.setProCastFirstName6(token); break;
			case 33: vo.setProCastLastName6(token); break;
			case 34: vo.setProCastRoleDesc6(token); break;
			case 35: vo.setProCastFirstName7(token); break;
			case 36: vo.setProCastLastName7(token); break;
			case 37: vo.setProCastRoleDesc7(token); break;
			case 38: vo.setProCastFirstName8(token); break;
			case 39: vo.setProCastLastName8(token); break;
			case 40: vo.setProCastRoleDesc8(token); break;
			case 41: vo.setProCastFirstName9(token); break;
			case 42: vo.setProCastLastName9(token); break;
			case 43: vo.setProCastRoleDesc9(token); break;
			case 44: vo.setProCastFirstName10(token); break;
			case 45: vo.setProCastLastName10(token); break;
			case 46: vo.setProCastRoleDesc10(token); break;
			case 47: vo.setProCastFirstName11(token); break;
			case 48: vo.setProCastLastName11(token); break;
			case 49: vo.setProCastRoleDesc11(token); break;
			case 50: vo.setProCastFirstName12(token); break;
			case 51: vo.setProCastLastName12(token); break;
			case 52: vo.setProCastRoleDesc12(token); break;
			case 53: vo.setProCastFirstName13(token); break;
			case 54: vo.setProCastLastName13(token); break;
			case 55: vo.setProCastRoleDesc13(token); break;
			case 56: vo.setProCastFirstName14(token); break;
			case 57: vo.setProCastLastName14(token); break;
			case 58: vo.setProCastRoleDesc14(token); break;
			case 59: vo.setProCastFirstName15(token); break;
			case 60: vo.setProCastLastName15(token); break;
			case 61: vo.setProCastRoleDesc15(token); break;
			case 62: vo.setProCastFirstName16(token); break;
			case 63: vo.setProCastLastName16(token); break;
			case 64: vo.setProCastRoleDesc16(token); break;
			case 65: vo.setProCastFirstName17(token); break;
			case 66: vo.setProCastLastName17(token); break;
			case 67: vo.setProCastRoleDesc17(token); break;
			case 68: vo.setProCastFirstName18(token); break;
			case 69: vo.setProCastLastName18(token); break;
			case 70: vo.setProCastRoleDesc18(token); break;
			case 71: vo.setProCastFirstName19(token); break;
			case 72: vo.setProCastLastName19(token); break;
			case 73: vo.setProCastRoleDesc19(token); break;
			case 74: vo.setProCastFirstName20(token); break;
			case 75: vo.setProCastLastName20(token); break;
			case 76: vo.setProCastRoleDesc20(token); break;
			case 77: vo.setProCreditsFirstName1(token); break;
			case 78: vo.setProCreditsLastName1(token); break;
			case 79: vo.setProCreditsRoleDesc1(token); break;
			case 80: vo.setProCreditsFirstName2(token); break;
			case 81: vo.setProCreditsLastName2(token); break;
			case 82: vo.setProCreditsRoleDesc2(token); break;
			case 83: vo.setProCreditsFirstName3(token); break;
			case 84: vo.setProCreditsLastName3(token); break;
			case 85: vo.setProCreditsRoleDesc3(token); break;
			case 86: vo.setProCreditsFirstName4(token); break;
			case 87: vo.setProCreditsLastName4(token); break;
			case 88: vo.setProCreditsRoleDesc4(token); break;
			case 89: vo.setProCreditsFirstName5(token); break;
			case 90: vo.setProCreditsLastName5(token); break;
			case 91: vo.setProCreditsRoleDesc5(token); break;
			case 92: vo.setProCreditsFirstName6(token); break;
			case 93: vo.setProCreditsLastName6(token); break;
			case 94: vo.setProCreditsRoleDesc6(token); break;
			case 95: vo.setProCreditsFirstName7(token); break;
			case 96: vo.setProCreditsLastName7(token); break;
			case 97: vo.setProCreditsRoleDesc7(token); break;
			case 98: vo.setProCreditsFirstName8(token); break;
			case 99: vo.setProCreditsLastName8(token); break;
			case 100: vo.setProCreditsRoleDesc8(token); break;
			case 101: vo.setProCreditsFirstName9(token); break;
			case 102: vo.setProCreditsLastName9(token); break;
			case 103: vo.setProCreditsRoleDesc9(token); break;
			case 104: vo.setProCreditsFirstName10(token); break;
			case 105: vo.setProCreditsLastName10(token); break;
			case 106: vo.setProCreditsRoleDesc10(token); break;
			case 107: vo.setProCreditsFirstName11(token); break;
			case 108: vo.setProCreditsLastName11(token); break;
			case 109: vo.setProCreditsRoleDesc11(token); break;
			case 110: vo.setProCreditsFirstName12(token); break;
			case 111: vo.setProCreditsLastName12(token); break;
			case 112: vo.setProCreditsRoleDesc12(token); break;
			case 113: vo.setProCreditsFirstName13(token); break;
			case 114: vo.setProCreditsLastName13(token); break;
			case 115: vo.setProCreditsRoleDesc13(token); break;
			case 116: vo.setProCreditsFirstName14(token); break;
			case 117: vo.setProCreditsLastName14(token); break;
			case 118: vo.setProCreditsRoleDesc14(token); break;
			case 119: vo.setProCreditsFirstName15(token); break;
			case 120: vo.setProCreditsLastName15(token); break;
			case 121: vo.setProCreditsRoleDesc15(token); break;
			case 122: vo.setProCreditsFirstName16(token); break;
			case 123: vo.setProCreditsLastName16(token); break;
			case 124: vo.setProCreditsRoleDesc16(token); break;
			case 125: vo.setProCreditsFirstName17(token); break;
			case 126: vo.setProCreditsLastName17(token); break;
			case 127: vo.setProCreditsRoleDesc17(token); break;
			case 128: vo.setProCreditsFirstName18(token); break;
			case 129: vo.setProCreditsLastName18(token); break;
			case 130: vo.setProCreditsRoleDesc18(token); break;
			case 131: vo.setProCreditsFirstName19(token); break;
			case 132: vo.setProCreditsLastName19(token); break;
			case 133: vo.setProCreditsRoleDesc19(token); break;
			case 134: vo.setProCreditsFirstName19(token); break;
			case 135: vo.setProCreditsLastName19(token); break;
			case 136: vo.setProCreditsRoleDesc19(token); break;
			case 137: vo.setProGenreDesc1(token); break;
			case 138: vo.setProGenreDesc2(token); break;
			case 139: vo.setProGenreDesc3(token); break;
			case 140: vo.setProGenreDesc4(token); break;
			case 141: vo.setProGenreDesc5(token); break;
			case 142: vo.setProGenreDesc6(token); break;
			case 143: vo.setProDesc(token); break;
			case 144: vo.setProYear((!token.equals(""))?Integer.parseInt(token):null); break;
			case 145: vo.setProMpaaRating(token); break;
			case 146: vo.setProStarRating(token); break;
			case 147: vo.setProRunTime(token); break;
			case 148: vo.setProColorCode(token); break;
			case 149: vo.setProProgramLanguage(token); break;
			case 150: vo.setProOrgCountry(token); break;
			case 151: vo.setProMadeForTv((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 152: vo.setProSourceType(token); break;
			case 153: vo.setProShowType(token); break;
			case 154: vo.setProHoliday(token); break;
			case 155: vo.setProSynEpiNum(token); break;
			case 156: vo.setProAltSynEpiNum(token); break;
			case 157: vo.setProEpiTitle(token); break;
			case 158: break;
			case 159: break;
			case 160: vo.setProDescAndCasts(token); break;
			case 161: vo.setProReducedDesc(token); break;
			case 162: vo.setProOrgStudio(token); break;
			case 163: temp = token; break;
			case 164: // Format: yyyymmdd(token) + hhmm(temp)
				if(token.equals(""))
					break;
				String dateGMT = temp+token;
				vo.setProGameDateTime(GMTToUTC6(dateGMT)); break;
			case 165: vo.setProGameTimeZone(token); break;
			case 166: // Format: yyyymmdd
				if(token.equals(""))
					break;
				dateGMT = token;
				vo.setProOrgAirDate(formatDate(token, "yyyyMMdd")); 
				break;
			case 167: break;
			case 168: vo.setPro500Desc(token); break;
			}
		}
		
		//Agregamos el vo al mapa para poder tomar datos a la hora de indexar informacion en solr
		programas.put(vo.getProDatabaseKey(), vo);
		return vo;
	}
	
	/**
	 * Crea un objeto de tipo CvScheduleRecord obteniendo los campos de la linea dada
	 * @param stringRow La linea con los datos del registro a crearse
	 * @return Un objeto CvScheduleRecord para guardar
	 */
	private CvScheduleRecord generateScheduleRecordVo(String stringRow) {
		CvScheduleRecord vo = new CvScheduleRecord();
		int count = 0;
		String temp = "";
		CvScheduleRecordPK comp = new CvScheduleRecordPK();
		for (String token : stringRow.split("\\|")) {
			token = token.trim();
			switch(++count) {
			case 1:
				try {
					if(!channels.isEmpty() && channels.containsKey(token))
						comp.setSkeStationNum(channels.get(token).getIdchannel()); 
					else
						return null;
//						comp.setSkeStationNum(num);
				} catch(Exception e) {
					e.printStackTrace();
//					comp.setSkeStationNum(num);
					return null;
				}
				break;
			case 2: comp.setSkeDatabaseKey(token); break;
			case 3: temp = token; break;
			case 4: // Format: yyyymmdd(temp) + hhmm(token)
				if(token.equals(""))
					break;
				String dateGMT = temp+token;
				comp.setSkeAirDateTime(GMTToUTC6(dateGMT));
				vo.setCompId(comp);
				break;
			case 5: vo.setSkeDuration(token); break;
			case 6: vo.setSkePartNum((!token.equals(""))?Integer.parseInt(token):null); break;
			case 7: vo.setSkeNumOfParts((!token.equals(""))?Integer.parseInt(token):null); break;
			case 8: vo.setSkeCc((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 9: vo.setSkeStereo((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 10: break;
			case 11: vo.setSkeLiveTapeDelay(token); break;
			case 12: vo.setSkeSubtitled((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 13: vo.setSkePremiereFinale(token); break;
			case 14: vo.setSkeJoinedInProgress((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 15: vo.setSkeCableInTheClassroom((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 16: vo.setSkeTvRating(token); break;
			case 17: vo.setSkeSap((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 18: break;
			case 19: vo.setSkeSexRating((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 20: vo.setSkeViolenceRating((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 21: vo.setSkeLanguageRating((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 22: vo.setSkeDialogRating((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 23: vo.setSkeFvRating((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 24: vo.setSkeEnhanced((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 25: vo.setSkeThreeD((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 26: vo.setSkeLetterbox((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 27: vo.setSkeHdtv((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 28: vo.setSkeDolby(token); break;
			case 29: vo.setSkeDvs((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 30: break;
			case 31: vo.setSkeNew(token); break;
			case 32: vo.setSkeNetSynSource(token); break;
			case 33: vo.setSkeNetSynType(token); break;
			case 34: vo.setSkeSubjectToBlackout(token); break;
			case 35: vo.setSkeTimeApproximate(token); break;
			case 36: vo.setSkeDubbed((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			case 37: vo.setSkeDubbedLanguage(token); break;
			case 38: vo.setSkeEi(token); break;
			case 39: vo.setSkeSapLanguage(token); break;
			case 40: vo.setSkeSubtitledLanguage(token); break;
			case 41: vo.setSkeLeftInProgress((!token.equals(""))?((token.charAt(0) == 'Y')?true:false):false); break;
			}
		}
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(vo.getCompId().getSkeAirDateTime());
		int hours = Integer.parseInt(vo.getSkeDuration().substring(0, 2));
		int mins = Integer.parseInt(vo.getSkeDuration().substring(2));
		endTime.add(Calendar.HOUR_OF_DAY, hours);
		endTime.add(Calendar.MINUTE, mins);
		endTime.set(Calendar.SECOND, 0);
		vo.setSkeEndDateTime(endTime.getTime());
		
		
		
		return vo;
	}
	
	/**
	 * Crea un objeto de tipo CvStationRecord obteniendo los campos de la linea dada
	 * @param stringRow La linea con los datos del registro a crearse
	 * @return Un objeto CvStationRecord para guardar
	 */
	private CvStationRecord generateStationRecordVo(String stringRow) {
		CvStationRecord vo = new CvStationRecord();
		int count = 0;
		String canal = null;
		for (String token : stringRow.split("\\|")) {
			token = token.trim();
			switch(++count) {
			case 1:
				canal = token;
				try {
					if(!channels.isEmpty() && channels.containsKey(token))
						vo.setStnStationNum(channels.get(token).getIdchannel()); 
					else
						return null;
//						vo.setStnStationNum(num);
				} catch(Exception e) {
					e.printStackTrace();
//					vo.setStnStationNum(num);
					return null;
				}
				break;
			case 2: vo.setStnStationTimeZone(token); break;
			case 3: vo.setStnStationName(token); break;
			case 4: vo.setStnStationCallSign(token); break;
			case 5: vo.setStnStationAffil(token); break;
			case 6: vo.setStnStationCity(token); break;
			case 7: vo.setStnStationState(token); break;
			case 8: vo.setStnStationZipCode(token); break;
			case 9: vo.setStnStationCountry(token); break;
			case 10: vo.setStnDmaName(token); break;
			case 11: vo.setStnDmaNum((!token.equals(""))? Long.parseLong(token):null); break;
			case 12: vo.setStnFccChannelNum((!token.equals(""))?Integer.parseInt(token):null); break;
			case 13: vo.setStnStationLanguage(token); break;
			}
		}
		vo.setStnCnlLogo(channels.get(canal).getLogo());
		return vo;
	}
	
	/**
	 * Crea un objeto de tipo CvTranslationRecord obteniendo los campos de la linea dada
	 * @param stringRow La linea con los datos del registro a crearse
	 * @return Un objeto CvTranslationRecord para guardar
	 */
	private CvTranslationRecord generateTranslationRecordVo(String stringRow) {
		CvTranslationRecord vo = new CvTranslationRecord();
		int count = 0;
		String temp = "";
		for (String token : stringRow.split("\\|")) {
			token = token.trim();
			switch(++count) {
			case 1: temp = token;
			case 2: vo.setCompId(new CvTranslationRecordPK(temp, token)); break;
			case 3: vo.setTrlLanguageTrans(token); break;
			}
		}
		return vo;
	}
	
	/**
	 * Crea un objeto de tipo CvTimeZoneRecord obteniendo los campos de la linea dada
	 * @param stringRow La linea con los datos del registro a crearse
	 * @return Un objeto CvTimeZoneRecord para guardar
	 */
	private CvTimeZoneRecord generateTimeZoneRecordVo(String stringRow) {
		CvTimeZoneRecord vo = new CvTimeZoneRecord();
		int count = 0;
		String temp = "";
		for (String token : stringRow.split("\\|")) {
			token = token.trim();
			switch(++count) {
			case 1: vo.setTzrTimeZoneName(token); break;
			case 2: temp = token; break;
			case 3: /// Format: yyyymmdd(temp) + hhmm(token)
				if(token.equals(""))
					break;
				String dateGMT = temp+token;
				vo.setTzrDateTime1(GMTToUTC6(dateGMT)); break;
			case 4: temp = token; break;
			case 5: // Format: yyyymmdd(temp) + hhmm(token)
				if(token.equals(""))
					break;
				dateGMT = temp+token;
				vo.setTzrDateTime2(GMTToUTC6(dateGMT)); break;
			case 6: vo.setTzrUtcStdOffset((!token.equals(""))?Integer.parseInt(token):null); break;
			}
		}
		return vo;
	}
	
	/**
	 * Borra todos los registros de las tablas 
	 * CvProgramRecord, CvScheduleRecord, CvStationRecord, 
	 * CvTranslationRecord y CvTimeZoneRecord
	 */
	public void deleteAllTableRecords() {
		getProgramScheduleService().deleteAllFromObject(CvScheduleRecord.class);
		getProgramScheduleService().deleteAllFromObject(CvProgramRecord.class);
		getProgramScheduleService().deleteAllFromObject(CvStationRecord.class);
		getProgramScheduleService().deleteAllFromObject(CvTranslationRecord.class);
		getProgramScheduleService().deleteAllFromObject(CvTimeZoneRecord.class);
	}
	
	public static Date GMTToUTC6(String dateGMT) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddkkmm");		
		format.setTimeZone(TimeZone.getTimeZone("GMT"));		
		try {
			return format.parse(dateGMT);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date formatDate(String date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);		
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public IProgramScheduleService getProgramScheduleService() {
		if (programScheduleService == null) {
			BeanFactory factory = DefaultLocatorFactory.getInstance("classpath*:/com/cablevision/conf/cablevisionBeanRefContext.xml")
			.useBeanFactory("cablevision-context")
			.getFactory();
			
			programScheduleService = (IProgramScheduleService) factory.getBean("ProgramScheduleService");
		}
		return programScheduleService;
	}
	
	public IChannelService getChannelService() {
		if (channelService == null) {
			BeanFactory factory = DefaultLocatorFactory.getInstance("classpath*:/com/cablevision/conf/cablevisionBeanRefContext.xml")
			.useBeanFactory("cablevision-context")
			.getFactory();
			
			channelService = (IChannelService) factory.getBean("ChannelService");
		}
		return channelService;
	}
}
