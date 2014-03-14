<?xml version="1.0" encoding="UTF-8"?>
<%-- La línea anterior debe ir siempre la primera si se genera un XML --%>
<%@page import="pizzeria.Pizzeria"%>
<%@page import="java.util.ArrayList"%>


<%-- Se informa que el contenido va a ser XML --%>
<%@page contentType="text/xml" pageEncoding="UTF-8"%>

<pizzas>
<% 
    ArrayList<Pizzeria> pizzeriaList = (ArrayList)request.getAttribute("pizzeriaList"); 
    for(Pizzeria pizzeria: pizzeriaList) {
        out.println("<pizza>");
        out.println("<id>"+pizzeria.getId()+"</id>");
        out.println("<nombre>"+pizzeria.getPrice()+"</nombre>");
        out.println("<ingredientes>"+pizzeria.getComments()+"</ingredientes>");
        out.println("<photo_file_name>"+pizzeria.getPhotoFileName()+"</photo_file_name>");
        out.println("</pizza>");
    }
%>
</pizzas>
