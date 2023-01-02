package autopark.servlets;

import autopark.collection.VehicleCollection;
import autopark.dto.service.DtoService;
import autopark.infrastructure.core.impl.ApplicationContext;
import autopark.service.Fixer;
import autopark.service.MechanicService;
import autopark.service.Workroom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/viewDiagnostic")
public class ViewDiagnosticServlet extends HttpServlet {

    private DtoService dtoService;
    private Workroom workroom;
    private VehicleCollection vehicleCollection;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext applicationContext = ApplicationContext.getInstance("autopark", getInterfaceToImplementation());
        dtoService = applicationContext.getObject(DtoService.class);
        workroom = applicationContext.getObject(Workroom.class);
        vehicleCollection = applicationContext.getObject(VehicleCollection.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("brokenCars", workroom.getRepairedVehiclesIds(vehicleCollection.getVehiclesList()));
        req.setAttribute("cars", vehicleCollection.getVehiclesList());
        getServletContext().getRequestDispatcher("/jsp/viewDiagnosticJSP.jsp").forward(req, resp);
    }


    private Map<Class<?>, Class<?>> getInterfaceToImplementation() {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);
        return interfaceToImplementation;
    }
}
