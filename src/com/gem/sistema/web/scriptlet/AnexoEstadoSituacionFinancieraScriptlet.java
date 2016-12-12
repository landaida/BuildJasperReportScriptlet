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
public class AnexoEstadoSituacionFinancieraScriptlet extends JRDefaultScriptlet
{
	@Override
	public void afterGroupInit(String groupName) throws JRScriptletException
	{
		BigDecimal saldoInicial = BigDecimal.ZERO;
		BigDecimal saldoFinal = BigDecimal.ZERO;
		
		if(groupName.equals("GROUP_GRUPO") && getFieldValue("CUENTA") != null){
			saldoInicial = fieldValueBigDecimal("SALINI");
			for (int i = 1; i < (Integer)getParameterValue("P_MES"); i++) {
				if(getFieldValue("NATCTA").equals("D")){
					saldoInicial = saldoInicial.add(fieldValueBigDecimal("CARGOS_"+i)).subtract(fieldValueBigDecimal("ABONOS_"+i));
				}else{
					if(getFieldValue("CUENTA").equals("1115") || getFieldValue("CUENTA").equals("1162")){
						saldoInicial = saldoInicial.add(fieldValueBigDecimal("CARGOS_"+i)).subtract(fieldValueBigDecimal("ABONOS_"+i));
					}else{
						saldoInicial = saldoInicial.subtract(fieldValueBigDecimal("CARGOS_"+i)).add(fieldValueBigDecimal("ABONOS_"+i));
					}
				}
			}
			BigDecimal deber = fieldValueBigDecimal("CARGOS_"+getParameterValue("P_MES"));
			BigDecimal haber = fieldValueBigDecimal("ABONOS_"+getParameterValue("P_MES"));
			
			if(getFieldValue("NATCTA").equals("D")){
				saldoFinal = saldoFinal.add(saldoInicial).add(deber).subtract(haber);
			}else{
				if(getFieldValue("CUENTA").equals("1115") || getFieldValue("CUENTA").equals("1162")){
					saldoFinal = saldoFinal.add(saldoInicial).add(deber).subtract(haber);
				}else{
					saldoFinal = saldoFinal.add(saldoInicial).subtract(deber).add(haber);
				}
			}
			
			//making group fields
			setVariableValue("saldo_inicial", saldoInicial);
			setVariableValue("deber", deber);
			setVariableValue("haber", haber);
			setVariableValue("saldo_final", saldoFinal);

			//making summaries
			setVariableValue("total_saldo_inicial", variableValueBigDecimal("total_saldo_inicial").add(saldoInicial));
			setVariableValue("total_deber", variableValueBigDecimal("total_deber").add(fieldValueBigDecimal("CARGOS_"+getParameterValue("P_MES"))));
			setVariableValue("total_haber", variableValueBigDecimal("total_haber").add(fieldValueBigDecimal("ABONOS_"+getParameterValue("P_MES"))));
			

		}
	}
	@Override
	public void afterDetailEval() throws JRScriptletException
	{
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
