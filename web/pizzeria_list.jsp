
<%@page import="es.rubiales.pizzeria.beans.Pizzeria"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            out.println("Aplicación ejecutada en la máquina: ");
            try {
                BufferedReader br = new BufferedReader(new FileReader("/etc/hostname"));
                out.println("<b>" + br.readLine() + "</b>");
            } catch(FileNotFoundException ex) {
                out.println("<b>Nombre de servidor no encontrado</b><br>");
                out.println("Sólo se detectarán los nombres de servidores Ubuntu");
            }
        %>
    <center>
        <div style="background-color: blue;"><h1>Lista de Pizzas</h1></div>
        <hr>
        <table border="1" >
            <tr>
                <th style="background-color: #66ccff;">Nombre</th>
                <th style="background-color: #66ccff;">Precio</th>
                <th style="background-color: #66ccff;">Ingredientes</th>
                <th colspan="2" style="background-color: #66ccff;">Funciones</th>
            </tr>
        <% 
            ArrayList<Pizzeria> pizzeriaList = (ArrayList)request.getAttribute("pizzeriaList"); 
            for(Pizzeria pizzeria: pizzeriaList) {
                out.println("<tr>");
                out.println("<td style='background-color: #ccffff;'>"+pizzeria.getName()+"</td>");
                out.println("<td style='background-color: #ccffff;'>"+pizzeria.getPrice()+"</td>");
                out.println("<td style='background-color: #ccffff;'>"+pizzeria.getComments()+"</td>");
                //Enlace para editar el registro
                String editLink = "Main?action=E&id="+pizzeria.getId();
                out.println("<td style='background-color: #ccffff;'><a href='"+editLink+"'>Editar</td>");
                //Enlace para eliminar el registro con confirmación por parte del usuario
                String deleteLink = "Main?action=D&id="+pizzeria.getId();
                String deleteConfirmText = "Confirme que desea eliminar la pizza:\\n"+pizzeria.getName();
                out.println("<td style='background-color: #ccffff;'><a href='"+deleteLink+"' onCLick='return confirm(\""+deleteConfirmText+"\")'>Suprimir</td>");
                
                out.println("</tr>");
            }
        %>
        </table>
        <br>
        <form method="get" action="Main">
            <input type="hidden" name="action" value="I">
            <input type="submit" value="Nueva Pizza">
        </form>
        <form method="get" action="Main" target="_blank">
            <input type="hidden" name="action" value="X">
            <input type="submit" value="Exportar XML">
        </form>
    </center>
    </body>
</html>
