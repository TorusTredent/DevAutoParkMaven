<%@ page import="java.util.List" %>
<%@ page import="autopark.dto.VehiclesDto" %><%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 23.11.2022
  Time: 12:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Результаты диагностики</title>
    <link rel = stylesheet href = "../resources/css/style.css" />
</head>
<body>
<div class = "center-flex-full-vh">
    <div class="vertical-center">
        <a class="ml-20" href="/">На главную</a>
        <br />
        <br />
        <hr />
        <br />
        <table>
            <caption>Машины</caption>
            <tr>
                <th>Тип</th>
                <th>Модель</th>
                <th>Номер</th>
                <th>Масса</th>
                <th>Дата выпуска</th>
                <th>Цвет</th>
                <th>Модель двигателя</th>
                <th>Пробег</th>
                <th>Бак</th>
                <th>Была исправна</th>
                <th>Починена</th>
            </tr>
            <%
                List<Long> brokenCars = (List<Long>) request.getAttribute("brokenCars");
                List<VehiclesDto> carsDtoList = (List<VehiclesDto>) request.getAttribute("cars");
                String broken = "";
            %>
            <%for (VehiclesDto vehiclesDto : carsDtoList) {
                if (brokenCars.contains(vehiclesDto.getId())) {
                    broken = "нет";
                } else {
                    broken = "да";
                }
            %>
            <tr>
                <td><%=vehiclesDto.getTypeName()%></td>
                <td><%=vehiclesDto.getModel()%></td>
                <td><%=vehiclesDto.getStateNumber()%></td>
                <td><%=vehiclesDto.getWeight()%></td>
                <td><%=vehiclesDto.getYear()%></td>
                <td><%=vehiclesDto.getColor()%></td>
                <td><%=vehiclesDto.getEngineName()%></td>
                <td><%=vehiclesDto.getMileage()%></td>
                <td><%=vehiclesDto.getTankCapacity()%></td>
                <td><%=broken%></td>
                <td>да</td>
            </tr>
            <%}%>
        </table>
        <br />
        <hr />
        <br />
    </div>
</div>
</body>
</html>
