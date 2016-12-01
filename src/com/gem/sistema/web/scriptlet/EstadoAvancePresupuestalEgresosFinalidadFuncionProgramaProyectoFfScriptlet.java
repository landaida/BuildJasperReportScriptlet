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
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class EstadoAvancePresupuestalEgresosFinalidadFuncionProgramaProyectoFfScriptlet extends JRDefaultScriptlet
{

	BigDecimal total_aprobado = BigDecimal.ZERO;
	BigDecimal total_pagado = BigDecimal.ZERO;
	BigDecimal total_devengado = BigDecimal.ZERO;
	BigDecimal total_ejercido = BigDecimal.ZERO;
	BigDecimal total_ampliacion = BigDecimal.ZERO;
	BigDecimal total_reduccion = BigDecimal.ZERO;
	BigDecimal total_comprometido = BigDecimal.ZERO;
	BigDecimal total_porejercer = BigDecimal.ZERO;
	BigDecimal total_modificado = BigDecimal.ZERO;

	/**
	 *
	 */
	public void beforeReportInit() throws JRScriptletException
	{
		//System.out.println("call beforeReportInit");
	}


	/**
	 *
	 */
	public void afterReportInit() throws JRScriptletException
	{
		//System.out.println("call afterReportInit");
	}


	/**
	 *
	 */
	public void beforePageInit() throws JRScriptletException
	{
		//System.out.println("call   beforePageInit : PAGE_NUMBER = " + this.getVariableValue("PAGE_NUMBER"));
	}


	/**
	 *
	 */
	public void afterPageInit() throws JRScriptletException
	{
		setVariableValue("total_aprobado", total_aprobado);
		setVariableValue("total_pagado", total_pagado);
		setVariableValue("total_devengado", total_devengado);
		setVariableValue("total_ejercido", total_ejercido);
		setVariableValue("total_ampliacion", total_ampliacion);
		setVariableValue("total_reduccion", total_reduccion);
		setVariableValue("total_comprometido", total_comprometido);
		setVariableValue("total_porejercer", total_porejercer);
		setVariableValue("total_modificado", total_modificado);
		//System.out.println("call   afterPageInit  : PAGE_NUMBER = " + this.getVariableValue("PAGE_NUMBER"));
	}


	/**
	 *
	 */
	public void beforeColumnInit() throws JRScriptletException
	{
		//System.out.println("call     beforeColumnInit");
	}


	/**
	 *
	 */
	public void afterColumnInit() throws JRScriptletException
	{
		//System.out.println("call     afterColumnInit");
	}


	/**
	 *
	 */
	public void beforeGroupInit(String groupName) throws JRScriptletException
	{
	}


	/**
	 *
	 */
	public void afterGroupInit(String groupName) throws JRScriptletException
	{
		if (groupName.equals("GROUP_PARTIDA"))
		{
			BigDecimal aprobado = BigDecimal.ZERO;
			BigDecimal padre_aprobado = BigDecimal.ZERO;
			BigDecimal pagado = BigDecimal.ZERO;
			BigDecimal devengado = BigDecimal.ZERO;
			BigDecimal ejercido = BigDecimal.ZERO;
			BigDecimal ampliacion = BigDecimal.ZERO;
			BigDecimal reduccion = BigDecimal.ZERO;
			BigDecimal comprometido = BigDecimal.ZERO;
			BigDecimal porejercer = BigDecimal.ZERO;
			BigDecimal modificado = BigDecimal.ZERO;
			for (int i = 1; i <= 12; i++) {
				aprobado = aprobado.add((BigDecimal)getFieldValue("AUTO1_"+i));
				if(getFieldValue("PARTIDA").toString().substring(1, 4).equals("000")){
					padre_aprobado = padre_aprobado.add((BigDecimal)this.getFieldValue("AUTO1_"+i));
				}
				if(i<=(Integer)getParameterValue("P_MES")){
					pagado = pagado.add(fieldValueBigDecimal("EJPA1_"+i));
					devengado = devengado.add(fieldValueBigDecimal("EJXPA1_"+i));
					ejercido = ejercido.add(fieldValueBigDecimal("TOEJE1_"+i));
					ampliacion = ampliacion.add(fieldValueBigDecimal("AMPLI1_"+i));
					reduccion = reduccion.add(fieldValueBigDecimal("REDU1_"+i));
					comprometido = comprometido.add(fieldValueBigDecimal("COMPRO1_"+i));
				}
			}
			modificado = (aprobado.add(ampliacion).subtract(reduccion));
			porejercer = (aprobado.add(ampliacion).subtract(reduccion)).subtract(ejercido);
			
			setVariableValue("aprobado", aprobado);
			setVariableValue("total_aprobado", padre_aprobado);
			setVariableValue("pagado", pagado);
			setVariableValue("devengado", devengado);
			setVariableValue("ejercido", ejercido);
			setVariableValue("ampliacion", ampliacion);
			setVariableValue("reduccion", reduccion);
			setVariableValue("comprometido", comprometido);
			setVariableValue("porejercer", porejercer);
			setVariableValue("modificado", modificado);
			
			if(getFieldValue("PARTIDA").toString().equals("3531")){
				System.out.println("3531");
				System.out.println(getFieldValue("EJXPA"));
				System.out.println(aprobado);
				System.out.println(pagado);
				System.out.println(devengado);
				System.out.println(getVariableValue("aprobado"));
			}
			
			total_aprobado = total_aprobado.add(aprobado);
			total_pagado = total_pagado.add(pagado);
			total_devengado = total_devengado.add(devengado);
			total_ejercido = total_ejercido.add(ejercido);
			total_ampliacion = total_ampliacion.add(ampliacion);
			total_reduccion = total_reduccion.add(reduccion);
			total_comprometido = total_comprometido.add(comprometido);
			total_porejercer = total_porejercer.add(porejercer);
			total_modificado = total_modificado.add(modificado);
		
		}
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

	/**
	 *
	 */
	public void beforeDetailEval() throws JRScriptletException
	{
		//System.out.println("        detail");
	}


	/**
	 *
	 */
	public void afterDetailEval() throws JRScriptletException
	{
	}

	/**
	 *
	 */
	public String hello() throws JRScriptletException
	{
		return "Hello! I'm the report's scriptlet 6789.";
	}

}
