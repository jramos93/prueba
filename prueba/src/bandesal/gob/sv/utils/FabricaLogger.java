package bandesal.gob.sv.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class FabricaLogger {
	private static final String LOG_SISTEMA = "BANDESAL";

	static {
		String archivo = "/app/bandesal/conf/Log4j.properties";
		PropertyConfigurator.configure(archivo);
	}

	public static Logger getLogger() {
		return Logger.getLogger(LOG_SISTEMA);
	}

}
