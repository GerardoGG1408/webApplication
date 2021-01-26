<%-- 
    Document   : register
    Created on : 27 dic 2020, 0:30:59
    Author     : gerardo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    boolean error = false;
    String parametro = request.getParameter("message");

    if ( parametro != null ) {
        if ( parametro.equals("error") ) {
            error = true;
        } else {
            error = false;
        }
    } else {
        error = false;
    }
%>

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
        <title>Registro</title>
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <%@include file="navbar.jsp"%>

        <!-- Formulario de registro -->

        <div class="container">
            <div class="row align-items-center" style="height: 90vh">
                <div class="col-lg-4">
                    <div class="justify-content-center">
                        <img src="images/escudoESCOM.png" class="rounded mx-auto d-block p-3 mb-5 img-fluid " alt="...">
                    </div>
                </div>

                <div class="col-lg-8">                    
                    <div class="justify-content-center ">
                        <div class="card shadow-lg p-3 mb-5 bg-white rounded">
                            <div class="card-header">
                                <h3>
                                    Registro
                                </h3>
                            </div>
                            <div class="card-body">
                                <form action="fileServlet" method="post" enctype = "multipart/form-data">
                                    <div class="form-floating mb-3">
                                        <input type="text" class="form-control" id="nombreUsuario" placeholder="Nombre" name="nombreUsuario" required>
                                        <label for="nombreUsuario">Nombre</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input type="text" class="form-control" id="apellidoUsuario" placeholder="Apellido" name="apellidoUsuario" required>
                                        <label for="apellidoUsuario">Apellido</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input type="email" class="form-control" id="correUsuario" placeholder="Correo" name="correUsuario" required>
                                        <label for="correUsuario">Correo</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input type="password" class="form-control" id="passUsuario" placeholder="Password" name="passUsuario" required>
                                        <label for="passUsuario">Contraseña</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input type="text" class="form-control" id="nickUsuario" placeholder="Usuario" name="nickUsuario" required>
                                        <label for="nickUsuario">Usuario</label>
                                    </div>
                                    <div class="input-group mb-3">
                                        <input type="file" class="form-control" id="avatar" name="file" accept="image/*" required>
                                    </div>
                                    <div class="d-grid gap-2 col-6 mx-auto">
                                        <input type="submit" class="btn btn-primary" name="submit" value="Registrarse">
                                    </div>
                                </form>
                            </div>
                        </div>

                    </div>
                </div>

                <% if (error) { %>

                <div class="d-flex justify-content-center">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <h4 class="alert-heading text-center">Error</h4>
                        <p class="text-center">Ocurrio un error durante el registro del usuario. <br> Puede que su usuario o email ya han sido registrados</p>

                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </div>

                <% }%>

            </div>  
        </div>       

        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js"></script>
        <!-- Font Awesome JS -->
        <script src="https://kit.fontawesome.com/ea8cc6a337.js"></script>
    </body>
</html>
