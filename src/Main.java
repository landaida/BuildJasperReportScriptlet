import java.util.HashMap;

import net.sf.jasperreports.engine.JRException;
public class Main {
	public static void main(String[] args) throws JRException {
		HashMap<String, Object> paramters = new HashMap<String, Object>();
		paramters.put("P_MES", 12);
		paramters.put("P_SECTOR", 2);
		paramters.put("P_ANO", 4);
		paramters.put("P_DIRE", "010301010101041");
		//ReportHelper.exportTXT("CapitalTrabajo", paramters);
//		ReportHelper.fill("CapitalTrabajo", paramters);
//		ReportHelper.text("CapitalTrabajo");
		ReportHelper.exportTXT("EstadoAvancePresupuestalEgresosFinalidadFuncionProgramaProyectoFf", paramters);
	}
}
