<%-- 
   Document   : index
   Created on : 7 ene 2021, 18:48:29
   Author     : gerardo
--%>

<%@page import="com.ipn.mx.modelo.dto.DestinatarioDTO"%>
<%@page import="com.ipn.mx.modelo.dto.AmistadDTO"%>
<%@page import="com.ipn.mx.modelo.dao.IntercambiosDAO"%>
<%@page import="com.ipn.mx.modelo.dto.IntercambiosDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.List"%>
<%@page import="com.ipn.mx.modelo.entidades.usuario"%>
<%@page import="com.ipn.mx.modelo.dao.UsuarioDAO"%>
<%@page import="com.ipn.mx.modelo.dto.UsuarioDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style>
    @viewport{
        zoom: 1.0;
        width: extend-to-zoom;
    }
    @-ms-viewport{
        width: extend-to-zoom;
        zoom: 1.0;
    }
</style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1"> 
        <title>Inicio</title>
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .abs-center {
                display: flex;
                align-items: center;
                justify-content: center;
                min-height: 90vh;
            }
        </style>
    </head>
    <body>
        <%@include file="navbar.jsp"%>


        <% if (logged) {
                List<UsuarioDTO> amigos = null;
                List<IntercambiosDTO> misIntercambios = null;
                List<IntercambiosDTO> solicitudesIntercambio = null;
                List<DestinatarioDTO> darRegalo = null;
                UsuarioDAO dao = new UsuarioDAO();
                IntercambiosDAO daoInt = new IntercambiosDAO();
                UsuarioDTO dto = new UsuarioDTO();
                usuario user = new usuario();

                user.setEmail(userLogged);

                dto.setEntidad(user);

                try {
                    amigos = dao.obtenerAmigos(dto);
                    solicitudesIntercambio = daoInt.obtenerSolicitudesIntercambio(dto);
                    darRegalo = daoInt.obtenerDestinatario(dto);
                    misIntercambios = daoInt.read(dto);
                } catch (SQLException ex) {
                    out.println(ex.getMessage());
                }
        %>
        <div class="container-fluid">
            <div class="row min-vh-100 flex-column flex-md-row">
                <aside class="col-12 col-md-3 col-xl-2 p-0 bg-dark flex-shrink-1">
                    <nav class="navbar navbar-expand-md navbar-dark bg-dark flex-md-column flex-row align-items-center py-2 text-center sticky-top">
                        <div class="text-center p-3">
                            <img src='<% out.println(img); %>' alt="profile picture" class="img-fluid rounded-circle my-4 p-1 d-none d-md-block shadow" height="300px" width="300px"> 
                            <a href="#" class="navbar-brand mx-0 font-weight-bold text-nowrap"><% out.println(username); %></a>
                        </div>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse order-last" id="navbarTogglerDemo02">
                            <ul class="navbar-nav flex-column w-100 justify-content-center">
                                <li class="nav-item">
                                    <a href="#" class="nav-link">Amigos</a>
                                </li>
                                <% if (amigos != null) {
                                        if (amigos.size() > 0) {
                                            for (int i = 0; i < amigos.size(); i++) {
                                                if (amigos.get(i).getEntidad().getStatus() == 1) {
                                %>
                                <li class="list-group-item bg-dark">
                                    <div class="container">
                                        <div class="row">
                                            <div class="col-lg-2 col">
                                                <img src="<% out.println(amigos.get(i).getEntidad().getRutaIMG()); %>" class="rounded-circle" height="20px" width="20px">
                                            </div>
                                            <div class="col-6">
                                                <p class="text-light bg-dark"><% out.println(amigos.get(i).getEntidad().getUsername()); %></p>
                                            </div>
                                            <div class="col-lg-2 col">
                                                <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                                                    <a href="userServlet?accion=removeFriend&friendId=<% out.println(amigos.get(i).getEntidad().getEmail()); %>" class="btn btn-secondary btn-sm"><i class="fas fa-user-minus"></i></a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <%
                                        }
                                    }
                                } else { %>
                                <li class="nav-item"><p class="text-light bg-dark">No tienes amigos</p></li>
                                    <% } %>
                                    <% } else { %>
                                <li class="nav-item"><p class="text-light bg-dark">No tienes amigos</p></li>
                                    <% } %>

                                <li class="nav-item">
                                    <a href="#" class="nav-link">Solicitudes de amistad</a>
                                </li>
                                <% if (amigos != null) {
                                        if (amigos.size() > 0) {
                                            for (int i = 0; i < amigos.size(); i++) {
                                                if (amigos.get(i).getEntidad().getStatus() == 0) {
                                %>
                                <li class="list-group-item bg-dark">
                                    <div class="container">
                                        <div class="row">
                                            <div class="col-lg-2 col">
                                                <img src="<% out.println(amigos.get(i).getEntidad().getRutaIMG()); %>" class="rounded-circle" height="20px" width="20px">
                                            </div>
                                            <div class="col-6">
                                                <p class="text-light bg-dark"><% out.println(amigos.get(i).getEntidad().getUsername()); %></p>
                                            </div>
                                            <div class="col-lg-2 col">
                                                <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                                                    <a href="userServlet?accion=removeFriend&friendId=<% out.println(amigos.get(i).getEntidad().getEmail()); %>" class="btn btn-secondary btn-sm"><i class="fas fa-user-minus"></i></a>
                                                    <a href="userServlet?accion=acceptFriend&friendId=<% out.println(amigos.get(i).getEntidad().getEmail()); %>" class="btn btn-secondary btn-sm"><i class="fas fa-user-plus"></i></a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <%
                                        }
                                    }
                                } else { %>
                                <li class="nav-item"><p class="text-light bg-dark">No tienes solicitudes de amistad</p></li>
                                    <% } %>
                                    <% } else { %>
                                <li class="nav-item"><p class="text-light bg-dark">No tienes solicitudes de amistad</p></li>
                                    <% } %>
                                <li class="nav-item">
                                    <a href="#" class="nav-link">Solicitudes de intercambio</a>
                                    <% if (solicitudesIntercambio != null) {
                                            if (solicitudesIntercambio.size() > 0) {
                                                for (int i = 0; i < solicitudesIntercambio.size(); i++) {
                                                    if (solicitudesIntercambio.get(i).getEntidad().getStatus_sol() == 0) {
                                    %>
                                <li class="list-group-item bg-dark">
                                    <div class="container">
                                        <div class="row">
                                            <div class="col-6">
                                                <p class="text-light bg-dark"><% out.println(solicitudesIntercambio.get(i).getEntidad().getNombre()); %></p>
                                            </div>
                                            <div class="col-lg-2 col">
                                                <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                                                    <a href="intercambioServlet?accion=aceptarSolicitud&intercambioId=<% out.println(solicitudesIntercambio.get(i).getEntidad().getId()); %>" class="btn btn-secondary btn-sm"><i class="fas fa-check-circle"></i></a>
                                                    <a href="intercambioServlet?accion=removeSolicitud&intercambioId=<% out.println(solicitudesIntercambio.get(i).getEntidad().getId()); %>" class="btn btn-secondary btn-sm"><i class="fas fa-user-minus"></i></a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <%
                                        }
                                    }
                                } else { %>
                                <li class="nav-item"><p class="text-light bg-dark">No tienes solicitudes de intercambios</p></li>
                                    <% } %>
                                    <% } else { %>
                                <li class="nav-item"><p class="text-light bg-dark">No tienes solicitudes de intercambios</p></li>
                                    <% } %>
                                </li>
                            </ul>
                        </div>
                    </nav>

                </aside>
                <main class="col px-0 flex-grow-1">
                    <div class="container py-3">
                        <div class="abs-center">
                            <% if (request.getAttribute("usuarios") == null && request.getParameter("form") == null && request.getParameter("preferences") == null) { %>


                            <div class="container-fluid">
                                <a class="btn btn-secondary btn-lg" href="index.jsp?form=true">Crear un nuevo Intercambio</a>
                                <a class="btn btn-secondary btn-lg" href="index.jsp?preferences=true">Editar mis preferencias</a>
                                <br/>
                                <hr/>
                                <h3>Mis intercambios</h3>
                                <hr/>
                                <br/>

                                <% if (misIntercambios != null) { %>

                                <% if (misIntercambios.size() > 0) { %>

                                <% for (int i = 0; i < misIntercambios.size(); i++) { %>

                                <div class="accordion accordion-flush" id="accordionFlushExample<% out.print(i); %>">
                                    <div class="accordion-item">
                                        <h2 class="accordion-header" id="flush-heading<% out.print(i); %>">
                                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapse<% out.print(i); %>" aria-expanded="false" aria-controls="flush-collapse<% out.print(i); %>">
                                                <% out.println(misIntercambios.get(i).getEntidad().getNombre()); %>
                                            </button>
                                        </h2>
                                        <div id="flush-collapse<% out.print(i); %>" class="accordion-collapse collapse" aria-labelledby="flush-heading<% out.print(i); %>" data-bs-parent="#accordionFlushExample<% out.print(i); %>">
                                            <div class="accordion-body">

                                                <h6><p class="text-break">Folio: <% out.print(misIntercambios.get(i).getEntidad().getId()); %></p></h6>

                                                <div class="container">
                                                    <div class="row align-items-start m-4">
                                                        <div class="col-lg">
                                                            <i class="far fa-clock fa-2x"></i>
                                                        </div>
                                                        <div class="col-lg">
                                                            <% out.print(misIntercambios.get(i).getEntidad().getDateShipped()); %>
                                                        </div>
                                                    </div>
                                                    <div class="row align-items-center m-4">
                                                        <div class="col-lg">
                                                            <i class="fas fa-gifts fa-2x"></i>
                                                        </div>
                                                        <div class="col-lg">
                                                            <% out.print(misIntercambios.get(i).getEntidad().getTypeGift()); %>
                                                        </div>
                                                    </div>
                                                    <div class="row align-items-end m-4">
                                                        <div class="col-lg">
                                                            <i class="fas fa-money-bill-alt fa-2x"></i>
                                                        </div>
                                                        <div class="col-lg">
                                                            <% out.print(misIntercambios.get(i).getEntidad().getGiftValue()); %>
                                                        </div>
                                                    </div>
                                                </div>

                                                <p class="text-break">
                                                    Comentarios: <% out.print(misIntercambios.get(i).getEntidad().getComentario()); %>
                                                </p>

                                                <% if (misIntercambios.get(i).getEntidad().getStatus().equals("CREATED")) { %>

                                                <div class="btn-group btn-group" role="group">
                                                    <a class="btn btn-outline-secondary" href="register.jsp">Crear parejas</a>
                                                    <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#friendsModal<% out.print(i); %>">
                                                        Invitar Amigos
                                                    </button>
                                                    <a class="btn btn-outline-secondary" href="intercambioServlet?accion=delete&id=<% out.print(misIntercambios.get(i).getEntidad().getId()); %>">Eliminar intercambio</a>
                                                </div>

                                                <div class="modal fade" id="friendsModal<% out.print(i); %>" tabindex="-1" aria-labelledby="friendsModal<% out.print(i); %>" aria-hidden="true">
                                                    <form action="intercambioServlet?accion=addFriends" method="post">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title">Mis amigos</h5>
                                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    Folio:
                                                                    <input type="text" name="folio" value="<% out.print(misIntercambios.get(i).getEntidad().getId()); %>">
                                                                    <br/>
                                                                    <hr/>
                                                                    <br/>
                                                                    <ul class="list-group">
                                                                        <% if (amigos != null) {
                                                                                if (amigos.size() > 0) {
                                                                                    for (int j = 0; j < amigos.size(); j++) {
                                                                                        if (amigos.get(j).getEntidad().getStatus() == 1) {
                                                                        %>

                                                                        <li class="list-group-item">
                                                                            <input class="form-check-input me-1" type="checkbox" name="amigos" value="<% out.print(amigos.get(j).getEntidad().getEmail()); %>">
                                                                            <% out.println(amigos.get(j).getEntidad().getUsername()); %>
                                                                        </li>

                                                                        <%
                                                                                }
                                                                            }
                                                                        } else { %>
                                                                        <li class="list-group-item"><p>No tienes amigos</p></li>
                                                                            <% } %>
                                                                            <% } else { %>
                                                                        <li class="list-group-item"><p>No tienes amigos</p></li>
                                                                            <% } %>
                                                                    </ul>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                                    <input type="submit" class="btn btn-secondary" value="Invitar amigos">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>

                                                </div>


                                                <% } %>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <% } %>

                                <% } else { %>

                                No has creado intercambios

                                <% } %>

                                <% } else { %>

                                No has creado intercambios

                                <% } %>

                                <br/>
                                <hr/>
                                <h3>Intercambios aceptados</h3>
                                <hr/>
                                <br/>

                                <% if (darRegalo != null) {
                                        if (darRegalo.size() > 0) {
                                            for (int i = 0; i < darRegalo.size(); i++) {
                                                if (darRegalo.get(i).getEntidad().getStatus() == 1) {
                                %>
                                <div class="accordion accordion-flush" id="InvitadoaccordionFlushExample<% out.print(i); %>">
                                    <div class="accordion-item">
                                        <h2 class="accordion-header" id="Invitadoflush-heading<% out.print(i); %>">
                                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#Invitadoflush-collapse<% out.print(i); %>" aria-expanded="false" aria-controls="Invitadoflush-collapse<% out.print(i); %>">
                                                <% out.println(darRegalo.get(i).getEntidad().getNombreIntercambio()); %>
                                            </button>
                                        </h2>
                                        <div id="Invitadoflush-collapse<% out.print(i); %>" class="accordion-collapse collapse" aria-labelledby="Invitadoflush-heading<% out.print(i); %>" data-bs-parent="#InvitadoaccordionFlushExample<% out.print(i); %>">
                                            <div class="accordion-body">

                                                <h6><p class="text-break">Folio: <% out.print(darRegalo.get(i).getEntidad().getIntercambioId()); %></p></h6>

                                                <div class="container">
                                                    <div class="row align-items-start m-4">
                                                        <div class="col-lg">
                                                            <i class="fas fa-envelope-square fa-2x"></i>
                                                        </div>
                                                        <div class="col-lg">
                                                            <% out.print(darRegalo.get(i).getEntidad().getUserEmail()); %>
                                                        </div>
                                                    </div>
                                                    <div class="row align-items-center m-4">
                                                        <div class="col-lg">
                                                            <i class="fas fa-user fa-2x"></i>
                                                        </div>
                                                        <div class="col-lg">
                                                            <% out.print(darRegalo.get(i).getEntidad().getUsername()); %>
                                                        </div>
                                                    </div>
                                                    <div class="row align-items-end m-4">
                                                        <div class="col-lg">
                                                            <i class="fas fa-gift fa-2x"></i>
                                                        </div>
                                                        <div class="col-lg">
                                                            <% out.print(darRegalo.get(i).getEntidad().getPreferencias()); %>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%
                                        }
                                    }
                                } else {
                                %>No has aceptado invitaciones<%
                                    }
                                } else {
                                %>No has aceptado invitaciones<%
                                    }
                                %>

                            </div>
                            <% } else if (request.getAttribute("usuarios") != null) {
                                List<UsuarioDTO> usuarios_buscados = null;
                                usuarios_buscados = (List) request.getAttribute("usuarios");
                                if (usuarios_buscados.size() == 1 && usuarios_buscados.get(0).getEntidad().getEmail().equals(userLogged)) {
                            %> 
                            <h3> <p>No se encontraron usuarios</p> </h3> 
                            <div class="d-grid gap-2 col-6 mx-auto">
                                <a class="btn btn-secondary btn-lg" href="index.jsp">Regresar</a>
                            </div>
                            <%
                            } else {
                            %>
                            <table class="table">
                                <% for (int i = 0; i < usuarios_buscados.size(); i++) {%>
                                <% if (!usuarios_buscados.get(i).getEntidad().getEmail().equals(userLogged)) { %>
                                <tr class="align-middle">
                                    <td><img src="<% out.println(usuarios_buscados.get(i).getEntidad().getRutaIMG()); %>" class="rounded-circle" height="100px" width="100px"></td>
                                    <th scope="row"><% out.println(usuarios_buscados.get(i).getEntidad().getUsername()); %></th>
                                    <td><a href="userServlet?accion=addFriend&friendId=<%out.println(usuarios_buscados.get(i).getEntidad().getEmail());%>" class="btn btn-secondary"><i class="fas fa-user-plus"></i></a></td>
                                </tr>  
                                <% }
                                    }%>
                            </table>
                            <% }
                            %>


                            <%
                            } else if (request.getParameter("form") != null) {
                            %>

                            <%
                                boolean error = false;
                                String parametro = request.getParameter("message");

                                if (parametro != null) {
                                    if (parametro.equals("error")) {
                                        error = true;
                                    } else {
                                        error = false;
                                    }
                                } else {
                                    error = false;
                                }
                            %>
                            <div class="d-grid gap-2 col-6 mx-auto">
                                <form action="intercambioServlet?accion=crear" method="post">
                                    <h3>Crear tu intercambio</h3>
                                    <hr/>
                                    <br/>
                                    <div class="form-floating mb-3">
                                        <input type="text" class="form-control" id="nombreIntercambio" placeholder="Nombre" name="nombreIntercambio" required>
                                        <label for="nombreIntercambio">Nombre del intercambio</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input type="date" class="form-control" id="dateShipped" placeholder="Fecha de entrega" name="dateShipped" required>
                                        <label for="dateShipped">Fecha de entrega</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input type="text" class="form-control" id="giftType" placeholder="Tipo de regalo" name="giftType" required>
                                        <label for="giftType">Tipo de regalo</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input type="number" min="10" max="1000" class="form-control" id="giftValue" placeholder="Password" name="giftValue" required>
                                        <label for="giftValue">Costo maximo</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <textarea class="form-control" placeholder="Comentarios" id="giftComment" name="giftComment"></textarea>
                                        <label for="giftComment">Comentarios</label>
                                    </div>
                                    <div class="d-grid gap-2 col-6 mx-auto">
                                        <input type="submit" class="btn btn-secondary btn-lg" name="submit" value="Crear">
                                        <a class="btn btn-secondary btn-lg" href="index.jsp">Regresar</a>
                                    </div>
                                </form>
                                <% if (error) { %>

                                <div class="d-flex justify-content-center">
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        <h4 class="alert-heading text-center">Error</h4>
                                        <p class="text-center">Ocurrio un error durante la creacion del intercambio. <br> Puede que ya tengas un intercambio con ese nombre.</p>

                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                </div>

                                <% }%>
                            </div>
                            <%} else if (request.getParameter("preferences") != null) {%>
                            <%
                                boolean error = false;
                                String parametro = request.getParameter("message");

                                if (parametro != null) {
                                    if (parametro.equals("error")) {
                                        error = true;
                                    } else {
                                        error = false;
                                    }
                                } else {
                                    error = false;
                                }
                            %>
                            <div class="d-grid gap-2 col-6 mx-auto">
                                <form action="userServlet?accion=preferencias" method="post">
                                    <h3>Editar mis preferencias</h3>
                                    <hr/>
                                    <br/>
                                    <div class="form-floating mb-3">
                                        <textarea class="form-control" placeholder="Preferencias" id="preferencias" name="preferencias"></textarea>
                                        <label for="giftComment">Preferencias</label>
                                    </div>
                                    <div class="d-grid gap-2 col-6 mx-auto">
                                        <input type="submit" class="btn btn-secondary btn-lg" name="submit" value="Actualizar">
                                        <a class="btn btn-secondary btn-lg" href="index.jsp">Regresar</a>
                                    </div>
                                </form>
                                <% if (error) { %>

                                <div class="d-flex justify-content-center">
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        <h4 class="alert-heading text-center">Error</h4>
                                        <p class="text-center">Ocurrio un error durante la actualizacion de preferencias. <br> Intentalo de nuevo.</p>

                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                </div>

                                <% }%>
                            </div>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </main>
            </div>
        </div>
        <% } else { %>
        <div class="align-items-center">
            <br />
            <br />
            <br />
            <br />
            <br />
            <img src="images/logo.jpeg" alt="alt" class="rounded mx-auto d-block"/>

            <div class="d-grid gap-2 col-6 mx-auto">
                <div class="btn-group btn-group-lg" role="group" aria-label="...">
                    <a class="btn btn-outline-secondary btn-lg" href="register.jsp">Registrate</a>
                    <a class="btn btn-outline-secondary btn-lg" href="login.jsp">Inicia sesion</a>
                </div>
                <br/>
                <hr/>
                <br/>
                
                <p>
                <p class="fw-bolder">Boomer grin</p> Es una plataforma la cual te permite realizar intercambios para fechas especiales, registrate en nuestra pagina para que puedas buscar a tus amigos e invitarlos a tu intercambio que organizaras!
                </p>
                
                <br/>
                <hr/>
                <br/>

                <h3 class="text-center">Desarrollado por:</h3>
                <div class="table-responsive">
                    <table class="table table-borderless">
                        <tr class="align-middle">
                            <td class="text-center"><i class="fas fa-user-tie fa-3x"></i></td>
                            <th scope="row">Gerardo González Garzon </th>
                        </tr>
                    </table>
                </div>
                <br/>
                <hr/>
                <br/>
                <div class="table-responsive">
                    <table class="table table-borderless">
                        <tr class="align-middle">
                            <th scope="row"><h5 class="text-center">Contactanos</h5></th>
                            <td class="text-center"><i class="fab fa-instagram-square fa-3x"></i></i></td>
                            <td class="text-center"><i class="fab fa-whatsapp-square fa-3x"></i></td>
                            <td class="text-center"><i class="fab fa-facebook-square fa-3x"></i></td>
                            <td class="text-center"><i class="fab fa-twitter fa-3x"></i></td>
                            <td class="text-center"><i class="fab fa-youtube fa-3x"></i></td>
                        </tr>
                    </table>
                </div>
            </div> 
        </div>
        <% }%>

        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js"></script>
        <!-- Font Awesome JS -->
        <script src="https://kit.fontawesome.com/ea8cc6a337.js"></script>
    </body>
</html>


