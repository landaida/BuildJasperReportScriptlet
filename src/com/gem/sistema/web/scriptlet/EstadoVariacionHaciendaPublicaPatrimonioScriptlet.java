package com.gem.sistema.web.scriptlet;





import java.math.BigDecimal;
import java.util.Objects;

/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2016 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 * @author Ariel Landaida (landaida.1@gmail.com)
 */
public class EstadoVariacionHaciendaPublicaPatrimonioScriptlet extends JRDefaultScriptlet
{
	private static enum TipoMes{MES_ANTERIOR, MES_ACTUAL};
	
	@Override
	public void afterDetailEval() throws JRScriptletException
	{
		if (getFieldValue("CUENTA") != null)
		{
			calcularMes(TipoMes.MES_ANTERIOR);
			calcularMes(TipoMes.MES_ACTUAL);
		}
	}
	
	
	private void calcularMes(TipoMes tipo)  throws JRScriptletException {
		BigDecimal valorMes = BigDecimal.ZERO;
		int P_MES = (Integer)getParameterValue("P_MES");
		
		if(getFieldValue("NATCTA").equals("D")){
			valorMes = fieldValueBigDecimal("SALINI");
			for (int i = 1; tipo.equals(TipoMes.MES_ACTUAL) ? i <= P_MES : i < P_MES; i++) {
				valorMes = valorMes.add(fieldValueBigDecimal("CARGOS_"+i).subtract(fieldValueBigDecimal("ABONOS_"+i)));
			}
		}else{
			valorMes = fieldValueBigDecimal("SALINI");
			for (int i = 1; tipo.equals(TipoMes.MES_ACTUAL) ? i <= P_MES : i < P_MES; i++) {
				if(getFieldValue("CUENTA").equals("1115") || getFieldValue("CUENTA").equals("1162")){
					valorMes = valorMes.add(fieldValueBigDecimal("CARGOS_"+i).subtract(fieldValueBigDecimal("ABONOS_"+i)));
				}else{
					valorMes = valorMes.subtract(fieldValueBigDecimal("CARGOS_"+i).add(fieldValueBigDecimal("ABONOS_"+i)));
				}
				
			}
		}
		if(fieldValueInteger("CUENTA").equals(3211)){
			BigDecimal cuenta4 = BigDecimal.ZERO;
			BigDecimal cuenta5 = BigDecimal.ZERO;
			
			if(fieldValueInteger("CUENTA") >= 4100  
					&& fieldValueInteger("CUENTA") <= 4399 
					&& !getFieldValue("CUENTA").toString().substring(3, 2).equals("0")){
				
				cuenta4.add(fieldValueBigDecimal("SALINI"));
				for (int i = 1; tipo.equals(TipoMes.MES_ACTUAL) ? i <= P_MES : i < P_MES; i++) {
					cuenta4.add(fieldValueBigDecimal("ABONOS_"+i).subtract(fieldValueBigDecimal("CARGOS_"+i)));
				}
			}
			if(getFieldValue("CUENTA").equals("5100")
					|| getFieldValue("CUENTA").equals("5200")
					|| getFieldValue("CUENTA").equals("5300")
					|| getFieldValue("CUENTA").equals("5400")
					|| getFieldValue("CUENTA").equals("5500")
					|| getFieldValue("CUENTA").equals("5600")
					|| getFieldValue("CUENTA").equals("5700")
					){
				cuenta5.add(fieldValueBigDecimal("SALINI"));
				for (int i = 1; tipo.equals(TipoMes.MES_ACTUAL) ? i <= P_MES : i < P_MES; i++) {
					cuenta5.subtract(fieldValueBigDecimal("ABONOS_"+i).add(fieldValueBigDecimal("CARGOS_"+i)));
				}
			}
			valorMes = cuenta4.subtract(cuenta5);
		}
	
		String variableName = tipo.equals(TipoMes.MES_ACTUAL) ? "actual" : "anterior";
		
		//making group fields
		setVariableValue("mes_"+variableName, valorMes);

		//making summaries
		setVariableValue("total_mes_"+variableName, variableValueBigDecimal("total_mes_"+variableName).add(valorMes));
	}
	
	private BigDecimal variableValueBigDecimal(String variableName){
		BigDecimal retorno = BigDecimal.ZERO;
		try {
			Object value = getVariableValue(variableName);
			if(!Objects.isNull(value)){
				retorno = (BigDecimal)value;
			}
		} catch (JRScriptletException e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	private BigDecimal fieldValueBigDecimal(String fieldName){
		BigDecimal retorno = BigDecimal.ZERO;
		try {
			Object value = getFieldValue(fieldName);
			if(!Objects.isNull(value)){
				retorno = (BigDecimal)value;
			}
		} catch (JRScriptletException e) {
			e.printStackTrace();
		}
		return retorno;
	}
	private Integer fieldValueInteger(String fieldName){
		Integer retorno = 0;
		try {
			Object value = getFieldValue(fieldName);
			if(!Objects.isNull(value)){
				retorno = Integer.valueOf((String) value);
			}
		} catch (JRScriptletException e) {
			e.printStackTrace();
		}
		return retorno;
	}

}
