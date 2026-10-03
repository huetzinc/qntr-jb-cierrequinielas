package com.desmxsoft.qntrjbcierrequiniela;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.desmxsoft.bean.PartidoBean;
import com.desmxsoft.qntrdtoracle.dto.PapeletaView;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Service
public class GeneradorReportePdf {

    @Autowired
    private HelperDatos helper;

    public void generarReporte(Integer idQuiniela) {
        try {
            System.out.println("Iniciando prueba de generación de PDF...");

            // 1. Configurar el resolvedor de plantillas
            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
            templateResolver.setPrefix("templates/"); // Carpeta dentro de src/main/resources
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode("HTML");
            templateResolver.setCharacterEncoding("UTF-8");

            // 2. Instanciar SpringTemplateEngine (USA SpEL, NO REQUIERE OGNL)
            SpringTemplateEngine templateEngine = new SpringTemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);

            Map<String, Object> infoQuiniela = helper.obtenerInfoQuiniela(idQuiniela);
            
            List<PapeletaView> papeletas = (List<PapeletaView>) infoQuiniela.get("papeletas");
             
            // 3. Llenar el contexto de Thymeleaf
            Context contextThymeleaf = new Context();
            contextThymeleaf.setVariable("titulo", "Quiniela Huetzin Jornada 4 Liga MX");
            contextThymeleaf.setVariable("descripcion", "La quiniela se cierra el 28 de julio a las 20 hrs, partidos de la liga MX");
            contextThymeleaf.setVariable("papeletas", papeletas);
            
            List<PartidoBean> partidosBean = (List<PartidoBean>)infoQuiniela.get("partidos");
            List<String> partidos = partidosBean.stream().map(partido -> partido.getTituloPartido()).collect(Collectors.toList());

            contextThymeleaf.setVariable("partidos", partidos);

            // 4. Procesar la plantilla
            String htmlRenderizado = templateEngine.process("reporte_quiniela", contextThymeleaf);
            System.out.println("htmlRenderizado -> " + htmlRenderizado);

            // 5. Generar PDF
            String rutaPdfSalida = System.getProperty("user.home") + "/Desktop/ReporteQuinielaPrueba.pdf";
            
            try (OutputStream outputStream = new FileOutputStream(rutaPdfSalida)) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode();
                builder.withHtmlContent(htmlRenderizado, null);
                builder.toStream(outputStream);
                builder.run();
            }

            System.out.println("¡PDF generado con éxito! Revisa tu Escritorio: " + rutaPdfSalida);

        } catch (Exception e) {
            System.err.println("Error al generar el PDF de prueba:");
            e.printStackTrace();
        }
    }
}