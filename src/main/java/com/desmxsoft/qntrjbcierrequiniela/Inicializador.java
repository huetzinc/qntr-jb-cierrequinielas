package com.desmxsoft.qntrjbcierrequiniela;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.desmxsoft.qntrdtoracle.dao.impl.CierreQuinielaDAOImpl;
import com.desmxsoft.qntrdtoracle.dao.impl.ViewQuinielaFullDAOImpl;
import com.desmxsoft.qntrdtoracle.dto.CierreQuinielaDTO;
import com.desmxsoft.qntrdtoracle.dto.QuinielaDTO;
import com.desmxsoft.qntrdtoracle.dto.ViewQuinielaFullDTO;


public class Inicializador {
	
	public static AnnotationConfigApplicationContext appContext;

    public static void main(String[] args) {
        System.out.println("VPMF; Planes y trabajos");
        appContext = new AnnotationConfigApplicationContext();
        appContext.register(Configurador.class);
        appContext.refresh();
        
        // buscar quinielas
        //se tiene que registrar el cieer? si
        //join. null cierre y fecha posterior a la actual
        
        ViewQuinielaFullDAOImpl daoViewQuiniela = appContext.getBean(ViewQuinielaFullDAOImpl.class);
        CierreQuinielaDAOImpl daoCierreQuiniela = appContext.getBean(CierreQuinielaDAOImpl.class); 
    	List<ViewQuinielaFullDTO> quinielasPendientes = daoViewQuiniela.selecPendingToClose();
        // encontre n quinielas
        //cerrar quiniela //<-- 
        //generar reporte

        GeneradorReportePdf generador = appContext.getBean(GeneradorReportePdf.class);

        for(ViewQuinielaFullDTO quiniela : quinielasPendientes) {
        	generador.generarReporte(quiniela.getIdQuiniela());
            
        	CierreQuinielaDTO cierreQuiniela = new CierreQuinielaDTO();
            cierreQuiniela.setQuiniela(quiniela); 
            cierreQuiniela.setFechaHoraProcesamiento(new Date());
            cierreQuiniela.setNumNotificaciones(8); 
            cierreQuiniela.setUrlSabana("http://www.");
            daoCierreQuiniela.insertar(cierreQuiniela);
          //enviar email
            
        }
    }


}
