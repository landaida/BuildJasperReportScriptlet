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
	@Override
	public void afterGroupInit(String groupName) throws JRScriptletException
	{
	}
	@Override
	public void afterDetailEval() throws JRScriptletException
	{
		if (getFieldValue("CUENTA") != null)
		{
			BigDecimal mesAnterior = BigDecimal.ZERO;
			BigDecimal mesActual = BigDecimal.ZERO;
			BigDecimal mesActualEjercicio = BigDecimal.ZERO;
			BigDecimal total = BigDecimal.ZERO;
			
			for (int i = 1; i <= 12; i++) {
				if(getFieldValue("NATCTA").equals("D")){
					mesAnterior = fieldValueBigDecimal("SALINI");
					if(i<=(Integer)getParameterValue("P_MES")){
						mesAnterior = mesAnterior.add(fieldValueBigDecimal("CARGOS_"+i).subtract(fieldValueBigDecimal("ABONOS_"+i)));
					}
				}else{
					mesAnterior = fieldValueBigDecimal("SALINI");
					if(i<=(Integer)getParameterValue("P_MES")){
						if(getFieldValue("CUENTA").equals("1115") || getFieldValue("CUENTA").equals("1162")){
							mesAnterior = mesAnterior.add(fieldValueBigDecimal("CARGOS_"+i).subtract(fieldValueBigDecimal("ABONOS_"+i)));
						}else{
							mesAnterior = mesAnterior.subtract(fieldValueBigDecimal("CARGOS_"+i).add(fieldValueBigDecimal("ABONOS_"+i)));
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
						if(i<=(Integer)getParameterValue("P_MES")){
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
						if(i<=(Integer)getParameterValue("P_MES")){
							cuenta5.subtract(fieldValueBigDecimal("ABONOS_"+i).add(fieldValueBigDecimal("CARGOS_"+i)));
						}
					}
					mesAnterior = cuenta4.subtract(cuenta5);
				}
			}
			
			//making group fields
			setVariableValue("mes_anterior", mesAnterior);

			//making summaries
			setVariableValue("total_mes_anterior", variableValueBigDecimal("total_mes_anterior").add(mesAnterior));
		
		}
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
