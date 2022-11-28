package autopark.servlets;

import autopark.collection.VehicleCollection;
import autopark.dto.service.DtoService;
import autopark.infrastructure.core.impl.ApplicationContext;
import autopark.service.Fixer;
import autopark.service.MechanicService;
import autopark.service.ScheduleService;
import autopark.service.Workroom;
import javafx.concurrent.ScheduledService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/viewPlannedDiagnostic")
public class ViewPlannedDiagnosticServlet extends HttpServlet {

    private DtoService dtoService;
    private Workroom workroom;
    private VehicleCollection vehicleCollection;
    private ScheduleService scheduleService;
    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext applicationContext = ApplicationContext.getInstance("autopark", getInterfaceToImplementation());
        workroom = applicationContext.getObject(Workroom.class);
        dtoService = applicationContext.getObject(DtoService.class);
        vehicleCollection = applicationContext.getObject(VehicleCollection.class);
        scheduleService = applicationContext.getObject(ScheduleService.class);
        scheduleService.checkIsBrokenVehicle(workroom, vehicleCollection);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("updateTime", scheduleService.getCurrentTime().getMinute() - scheduleService.getUpdateTime().getMinute());
        req.setAttribute("brokenCars", scheduleService.getRepairedVehiclesIds());
        req.setAttribute("cars", vehicleCollection.getVehiclesList());
        getServletContext().getRequestDispatcher("/jsp/viewPlannedDiagnosticJSP.jsp").forward(req, resp);
    }


    private Map<Class<?>, Class<?>> getInterfaceToImplementation() {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);
        return interfaceToImplementation;
    }
}