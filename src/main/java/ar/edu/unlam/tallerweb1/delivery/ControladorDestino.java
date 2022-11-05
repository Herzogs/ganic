package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Email.Email;
import ar.edu.unlam.tallerweb1.domain.Excepciones.EnvioFueraDeZonaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorDestino {

    private Float distancia;

    public ControladorDestino() {
        this.distancia = 0F;
    }


    @RequestMapping(path = "seleccionarDestino", method = RequestMethod.POST)
    public ModelAndView irASeleccionDestino(@RequestParam(value = "distancia") Float distancia, HttpServletRequest request){
        Email temp = (Email) request.getSession().getAttribute("email");
        ModelMap model = new ModelMap();
        try {
           temp.setRecargo(obtenerCostoEnvio(distancia));
           request.getSession().setAttribute("email",temp);

       } catch (EnvioFueraDeZonaException e) {
           model.put("msg","Fuera De rango de Envio");
           return new ModelAndView("redirect:/destino",model);
       }
        System.err.println(temp.getRecargo());

        return new ModelAndView("/exito");
    }

    private Float obtenerCostoEnvio(Float dist) throws EnvioFueraDeZonaException {
        Float recargo = 0F;
        if(dist > 300F){
            if(dist <= 1000F)
                recargo = 100F;
            if(dist <= 2000F)
                recargo = 200F;
            else throw new EnvioFueraDeZonaException("Envio fuera de Zona de Reparto");
        }
        return recargo;
    }

    @RequestMapping(path = "/destino", method = RequestMethod.GET)
    public ModelAndView renderizadoPaginaDestino(){
        ModelMap model = new ModelMap();
        model.put("distancia",distancia);
        return new ModelAndView("destino",model);
    }
}
