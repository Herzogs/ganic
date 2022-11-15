package ar.edu.unlam.tallerweb1.delivery;

import ar.edu.unlam.tallerweb1.domain.Email.Email;
import ar.edu.unlam.tallerweb1.domain.Excepciones.EnvioFueraDeZonaException;
import ar.edu.unlam.tallerweb1.domain.Sandwich.Sandwich;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorDestino {



    public ControladorDestino() {
    }


    @RequestMapping(path = "/seleccionarDestino", method = RequestMethod.POST)
    public ModelAndView irASeleccionDestino(@ModelAttribute("formDestino") FormularioDestino dest, HttpServletRequest request){
        ModelMap model = new ModelMap();
        try {
           request.getSession().setAttribute("RECARGO",obtenerCostoEnvio(dest.getDistance()));
           request.getSession().setAttribute("DESTINO",dest.getDestino());
       } catch (EnvioFueraDeZonaException e) {
           model.put("msg","Fuera De rango de Envio");
           return new ModelAndView("destino",model);
       } catch (NullPointerException exc){
            model.put("msg","Debe seleccionar un destino para continuar");
            return new ModelAndView("destino",model);
        }
        return new ModelAndView("redirect:/prepago");
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
        ModelMap mod = new ModelMap();
        mod.put("formDestino", new FormularioDestino());
        return new ModelAndView("destino", mod);
    }
}
