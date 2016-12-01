import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleTextReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

public class ReportHelper {
	
	public static void fill(String simpleName, Map<String, Object> parameters) throws JRException
	{
		String pathReports = System.getProperty("user.dir");
		String reportName = pathReports + File.separator + simpleName + ".jasper";

		JasperFillManager.fillReportToFile(reportName, parameters, DBHelper.con());
	}
	
	public static void text(String simpleName) throws JRException
	{
		String pathReports = System.getProperty("user.dir");
		String reportName = simpleName + ".jrprint";
		JRTextExporter exporter = new JRTextExporter();
		File sourceFile = new File(pathReports, reportName);
		JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);
		File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".txt");

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleWriterExporterOutput(destFile));
		exporter.exportReport();

	}
	
	public static void exportTXT(String simpleName, Map<String, Object> parameters) {
		String pathReports = System.getProperty("user.dir");
		String reportName = pathReports + File.separator + simpleName + ".jasper";
		try {
			JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(reportName, parameters, DBHelper.con());

			JRTextExporter exporter = new JRTextExporter();
			SimpleExporterInput exporterInput = new SimpleExporterInput(jprint);
			exporter.setExporterInput(exporterInput);
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HHmmss");
			File file = new File(simpleName + " " + format.format(date)+" .txt");
			SimpleWriterExporterOutput exporterOutput = new SimpleWriterExporterOutput(file);
			exporter.setExporterOutput(exporterOutput);
			SimpleTextReportConfiguration configuration = new SimpleTextReportConfiguration();
			configuration.setCharWidth(3.96f);
			configuration.setCharHeight(13.948f);
			exporter.setConfiguration(configuration);

			exporter.exportReport();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
