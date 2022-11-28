package autopark.servlets;

import autopark.collection.VehicleCollection;
import autopark.infrastructure.core.impl.ApplicationContext;
import autopark.service.Fixer;
import autopark.service.MechanicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/info")
public class ViewInfoServlet extends HttpServlet {

    private VehicleCollection vehicleCollection;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext applicationContext = ApplicationContext.getInstance("autopark", getInterfaceToImplementation());
        vehicleCollection = applicationContext.getObject(VehicleCollection.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("cars", vehicleCollection.getVehiclesList().stream()
                                                                    .filter(vehiclesDto -> id == vehiclesDto.getId())
                                                                    .collect(Collectors.toList()));
        req.setAttribute("rents", vehicleCollection.getRentsList().stream()
                                                                    .filter(rentsDto -> id == rentsDto.getVehicleId())
                                                                    .collect(Collectors.toList()));
        getServletContext().getRequestDispatcher("/jsp/viewCarInfoJSP.jsp").forward(req, resp);
    }


    private Map<Class<?>, Class<?>> getInterfaceToImplementation() {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);
        return interfaceToImplementation;
    }
}