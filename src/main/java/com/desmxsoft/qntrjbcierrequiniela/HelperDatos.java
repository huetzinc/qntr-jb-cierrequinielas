package com.desmxsoft.qntrjbcierrequiniela;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desmxsoft.bean.PartidoBean;
import com.desmxsoft.qntrdtoracle.dao.impl.ObtenerTablaQuinielaDAOImplOracle;
import com.desmxsoft.qntrdtoracle.dao.impl.PartidoQuinielaDAOImpl;
import com.desmxsoft.qntrdtoracle.dto.PapeletaView;
import com.desmxsoft.qntrdtoracle.dto.PartidoQuinielaDTO;
import com.desmxsoft.qntrdtoracle.dto.QuinielaDTO;


@Service
public class HelperDatos {

	@Autowired
	private ObtenerTablaQuinielaDAOImplOracle storeProcedure;

	@Autowired
	private PartidoQuinielaDAOImpl daoPartidoQuiniela;

	Map<String, Object> obtenerInfoQuiniela(Integer idQuiniela) {
		Map<String, Object> infoQuiniela = storeProcedure.obtenerTablaQuiniela(idQuiniela);
		
		Integer numPartidos = (Integer) infoQuiniela.get("numPartidos");
		List<Integer> idsPartidos = (List<Integer>) infoQuiniela.get("idsPartidos");
		List<PapeletaView> papeletas = (List<PapeletaView>)infoQuiniela.get("filas");
		
		for(PapeletaView pv : papeletas) {
			pv.setNickname(pv.getJugador().getNickname());
		}
		
		System.out.println("numPartidos ->" + numPartidos);
		System.out.println("idsPartidos ->" + idsPartidos);
		System.out.println("Per papeleta ->" + papeletas.get(0));
		
		QuinielaDTO quiniela = new QuinielaDTO();
		quiniela.setIdQuiniela(idQuiniela);
		List<PartidoQuinielaDTO> asignacionesPartidos = daoPartidoQuiniela.selecXQuiniela(quiniela);
		List<PartidoBean> listaPartidos = asignacionesPartidos.stream()
		        .map(dto -> new PartidoBean(dto.getPartido()))
		        .collect(Collectors.toList());
		
		System.out.println("listaPartidos ->" + listaPartidos);
		
		Map<String, Object> response = new HashMap<>();
		response.put("numPartidos", numPartidos);
		response.put("idsPartidos", idsPartidos);
		response.put("papeletas", papeletas);
		response.put("partidos", listaPartidos);
		
        return response;
	}

}
