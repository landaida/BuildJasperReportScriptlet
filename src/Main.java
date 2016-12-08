import java.util.HashMap;

import net.sf.jasperreports.engine.JRException;
public class Main {
	public static void main(String[] args) throws JRException {
		HashMap<String, Object> paramters = new HashMap<String, Object>();
		paramters.put("P_MES", 12);
		paramters.put("P_SECTOR", 1);
		paramters.put("P_ANO", 4);

		
//		ReportHelper.exportTXT("CapitalTrabajo", paramters);
		
		
		
//		paramters.put("P_DIRE", "010301010101041");
//		ReportHelper.exportTXT("EstadoAvancePresupuestalEgresosFinalidadFuncionProgramaProyectoFf", paramters);

		
		
		paramters.put("P_DIRE", "030402");
		ReportHelper.exportTXT("EstadoAvancePresupuestalEgresosFinalidadFuncionSubFuncion", null);
		
		
		
//		paramters.put("P_DIRE", "A00100010301010201011");
//		ReportHelper.exportTXT("EstadoAvancePresupuestalEgresosDgDaFunProgProyFf", paramters);
		
		
//		paramters.put("P_DIRE", "020");
//		ReportHelper.exportTXT("EstadoAvancePresupuestalEgresosFuenteFinanciamiento", paramters);
		
	}
}
