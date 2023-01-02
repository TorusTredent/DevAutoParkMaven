package autopark.servlets;

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
import java.util.Map;

@WebServlet("/viewTypes")
public class ViewCarTypesServlet extends HttpServlet {

    DtoService dtoService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext applicationContext = ApplicationContext.getInstance("autopark", getInterfaceToImplementation());
        dtoService = applicationContext.getObject(DtoService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("types", dtoService.getTypes());
        getServletContext().getRequestDispatcher("/jsp/viewTypesJSP.jsp").forward(req, resp);
    }


    private Map<Class<?>, Class<?>> getInterfaceToImplementation() {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);
        return interfaceToImplementation;
    }
}