package autopark.servlets;

import autopark.dto.VehiclesDto;
import autopark.dto.service.DtoService;
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
import java.util.List;
import java.util.Map;

import static autopark.dto.service.DtoService.*;

@WebServlet("/viewReport")
public class ViewReportServlet extends HttpServlet {

    private DtoService dtoService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext applicationContext = ApplicationContext.getInstance("autopark", getInterfaceToImplementation());
        dtoService = applicationContext.getObject(DtoService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VehiclesDto> vehiclesDtoList =  dtoService.getVehicles();
        req.setAttribute("averageTax", calculateAverageTax(vehiclesDtoList));
        req.setAttribute("averageIncome", calculateAverageIncome(vehiclesDtoList));
        req.setAttribute("totalProfit", calculateTotalProfit(vehiclesDtoList));
        req.setAttribute("cars", vehiclesDtoList);
        getServletContext().getRequestDispatcher("/jsp/viewReportJSP.jsp").forward(req, resp);
    }


    private Map<Class<?>, Class<?>> getInterfaceToImplementation() {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);
        return interfaceToImplementation;
    }
}
