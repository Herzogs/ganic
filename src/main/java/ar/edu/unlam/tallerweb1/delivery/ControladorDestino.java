package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Email.Email;
import ar.edu.unlam.tallerweb1.domain.Excepciones.EnvioFueraDeZonaException;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
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


    @RequestMapping(path = "/seleccionarDestino", method = RequestMethod.GET)
    public ModelAndView irASeleccionDestino(@RequestParam(value = "dist") Float distancia, HttpServletRequest request){
        Email temp = (Email) request.getSession().getAttribute("email");
        ModelMap model = new ModelMap();
        System.err.println(distancia);
        try {
           temp.setRecargo(obtenerCostoEnvio(distancia));
           request.getSession().setAttribute("email",temp);
       } catch (EnvioFueraDeZonaException e) {
           model.put("msg","Fuera De rango de Envio");
           return new ModelAndView("destino",model);
       } catch (NullPointerException exc){
            model.put("msg","Debe seleccionar un destino para continuar");
            return new ModelAndView("destino",model);
        }
        System.err.println(temp.getRecargo());
        Sandwich sandwich = (Sandwich) request.getSession().getAttribute("sandwich");
        System.err.println(sandwich);

        return new ModelAndView("redirect:/pago");
    }

    private Float obtenerCostoEnvio(Float dist) throws EnvioFueraDeZonaException {
        float recargo = 0F;
        if(dist > 3000F)
            throw new EnvioFueraDeZonaException("Envio fuera de Zona de Reparto");
        if (dist > 300F && dist <= 1000F)
            recargo = 100F;
        if(dist > 1000F &&dist <= 2000F)
            recargo = 200F;
        if(dist > 2000F)
            recargo = 250F;
        return recargo;
    }

    @RequestMapping(path = "/destino", method = RequestMethod.GET)
    public ModelAndView renderizadoPaginaDestino(){
        return new ModelAndView("destino");
    }
}
