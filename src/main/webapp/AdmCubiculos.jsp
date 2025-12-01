<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="resources/css/AdmCubiculos.css" rel="stylesheet" type="text/css"/>      
        <link href="resources/css/Admin.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/Admin-Display.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.4.38/sweetalert2.min.css" />
        <title>Gestión de Cubículos</title>
    </head>
    <body class="parent-container">
        <jsp:include page="components/navegadorAdm.jsp"/>
        <script src="resources/scrip/AdmPng.js" type="text/javascript"></script>
        
        <div class="container mt-4">
            <h1>Gestión de Cubículos</h1>
            <hr>
            
            <!-- Selector de Local -->
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="locales" class="form-label"><strong>Seleccionar Local:</strong></label>
                    <select id="locales" class="form-select" onchange="cargarCubiculos(this.value)">
                        <option value="">Seleccione un Local</option>
                    </select>
                </div>
                <div class="col-md-6 text-end align-self-end">
                    <button type="button" class="btn btn-success" onclick="abrirModalRegistro()">
                        Registrar Nuevo Cubículo
                    </button>
                </div>
            </div>

            <!-- Tabla de Cubículos -->
            <div class="table-responsive">
                <table class="table table-striped table-hover" id="cubiculosTable">
                    <thead class="table-dark">
                        <tr>
                            <th>Código</th>
                            <th>Nivel</th>
                            <th>Tipo</th>
                            <th>Capacidad</th>
                            <th>Precio/Hora</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Los datos se cargarán aquí dinámicamente -->
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Modal para Registrar/Editar Cubículo -->
        <div class="modal fade" id="modalCubiculo" tabindex="-1" aria-labelledby="modalCubiculoLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalCubiculoLabel">Registrar Cubículo</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="formCubiculo" method="POST" action="<%= request.getContextPath()%>/cntCubiculos">
                            <input type="hidden" name="accion" id="accionForm" value="registrar">
                            <input type="hidden" name="codCubiculo" id="codCubiculo">
                            
                            <div class="mb-3">
                                <label for="codLocal" class="form-label">Local *</label>
                                <select class="form-select" id="codLocal" name="codLocal" required>
                                    <option value="">Seleccione un local</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="codNivel" class="form-label">Nivel *</label>
                                <select class="form-select" id="codNivel" name="codNivel" required>
                                    <option value="">Seleccione un nivel</option>
                                    <option value="1">Nivel 1</option>
                                    <option value="2">Nivel 2</option>
                                    <option value="3">Nivel 3</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="tipoCubiculo" class="form-label">Tipo de Cubículo *</label>
                                <select class="form-select" id="tipoCubiculo" name="tipoCubiculo" required onchange="actualizarPrecio()">
                                    <option value="">Seleccione un tipo</option>
                                    <option value="General" data-precio="5.00">General (S/. 5.00/hora)</option>
                                    <option value="VIP" data-precio="8.00">VIP (S/. 8.00/hora)</option>
                                    <option value="ULTRA VIP" data-precio="12.00">ULTRA VIP (S/. 12.00/hora)</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="capacidad" class="form-label">Capacidad (personas) *</label>
                                <input type="number" class="form-control" id="capacidad" name="capacidad" min="1" max="10" required>
                            </div>

                            <div class="mb-3">
                                <label for="precioHora" class="form-label">Precio por Hora (S/.) *</label>
                                <input type="number" class="form-control" id="precioHora" name="precioHora" step="0.01" min="0" required readonly>
                            </div>

                            <div class="mb-3">
                                <label for="estadoCubiculo" class="form-label">Estado *</label>
                                <select class="form-select" id="estadoCubiculo" name="estadoCubiculo" required>
                                    <option value="Disponible">Disponible</option>
                                    <option value="Ocupado">Ocupado</option>
                                    <option value="Mantenimiento">Mantenimiento</option>
                                    <option value="Fuera de Servicio">Fuera de Servicio</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="descripcion" class="form-label">Descripción</label>
                                <textarea class="form-control" id="descripcion" name="descripcion" rows="3"></textarea>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                                <button type="submit" class="btn btn-success" id="btnGuardar">Guardar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal para Asignar Cliente -->
        <div class="modal fade" id="modalAsignar" tabindex="-1" aria-labelledby="modalAsignarLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalAsignarLabel">Asignar Cliente al Cubículo</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="formAsignar" method="POST" action="<%= request.getContextPath()%>/cntCubiculos">
                            <input type="hidden" name="accion" value="crearReserva">
                            <input type="hidden" name="codCubiculo" id="codCubiculoAsignar">
                            
                            <div class="mb-3">
                                <label for="codCliente" class="form-label">Cliente *</label>
                                <select class="form-select" id="codCliente" name="codCliente" required>
                                    <option value="">Seleccione un cliente</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="fecha" class="form-label">Fecha *</label>
                                <input type="date" class="form-control" id="fecha" name="fecha" required>
                            </div>

                            <div class="mb-3">
                                <label for="horaInicio" class="form-label">Hora de Inicio *</label>
                                <input type="datetime-local" class="form-control" id="horaInicio" name="horaInicio" required>
                            </div>

                            <div class="mb-3">
                                <label for="horaFin" class="form-label">Hora de Fin *</label>
                                <input type="datetime-local" class="form-control" id="horaFin" name="horaFin" required>
                            </div>

                            <div class="mb-3">
                                <label for="tiempo" class="form-label">Duración (minutos) *</label>
                                <input type="number" class="form-control" id="tiempo" name="tiempo" min="15" step="15" required>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                                <button type="submit" class="btn btn-primary">Asignar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.4.38/sweetalert2.all.min.js"></script>
        
        <script type="text/javascript">
            var clientes = [];
            
            $(document).ready(function () {
                cargarLocales();
                cargarClientes();
                establecerFechaActual();
            });

            function establecerFechaActual() {
                var hoy = new Date().toISOString().split('T')[0];
                $('#fecha').val(hoy);
            }

          function cargarLocales() {
    $.ajax({
        url: '<%= request.getContextPath()%>/cntCubiculos',
        method: 'GET',
        dataType: 'json',
        data: { accion: 'listarLocales' },
        success: function (data) {

            // limpiar selects
            $('#locales').empty().append('<option value="">Seleccione un Local</option>');
            $('#codLocal').empty().append('<option value="">Seleccione un Local</option>');

            // agregar locales correctamente respetando mayúsculas
            data.forEach(function(local) {
                $('#locales').append(
                    '<option value="' + local.CodLocal + '">' + local.Nombre + '</option>'
                );
                $('#codLocal').append(
                    '<option value="' + local.CodLocal + '">' + local.Nombre + '</option>'
                );
            });
        },
        error: function (error) {
            console.error('Error al cargar locales:', error);
            Swal.fire('Error', 'No se pudieron cargar los locales', 'error');
        }
    });
}

            function cargarClientes() {
                $.ajax({
                    url: '<%= request.getContextPath()%>/cntCubiculos',
                    method: 'GET',
                    dataType: 'json',
                    data: { accion: 'listarClientes' },
                    success: function (data) {
                        clientes = data;
                        var selectClientes = $('#codCliente');
                        selectClientes.empty();
                        selectClientes.append('<option value="">Seleccione un cliente</option>');
                        
                        data.forEach(function (cliente) {
                            var option = '<option value="' + cliente.codCliente + '">' + 
                                          cliente.nombres + ' ' + cliente.apePaterno + ' ' + cliente.apeMaterno + 
                                          '</option>';
                            selectClientes.append(option);
                        });
                    },
                    error: function (error) {
                        console.error('Error al cargar clientes:', error);
                    }
                });
            }

           

            function abrirModalRegistro() {
                $('#modalCubiculoLabel').text('Registrar Cubículo');
                $('#accionForm').val('registrar');
                $('#formCubiculo')[0].reset();
                $('#codCubiculo').val('');
                $('#estadoCubiculo').val('Disponible');
                $('#modalCubiculo').modal('show');
            }

            function editarCubiculo(codCubiculo) {
                $.ajax({
                    url: '<%= request.getContextPath()%>/cntCubiculos',
                    method: 'GET',
                    dataType: 'json',
                    data: { accion: 'obtener', codCubiculo: codCubiculo },
                    success: function (cubiculo) {
                        $('#modalCubiculoLabel').text('Editar Cubículo');
                        $('#accionForm').val('actualizar');
                        $('#codCubiculo').val(cubiculo.codCubiculo);
                        $('#codLocal').val(cubiculo.codLocal);
                        $('#codNivel').val(cubiculo.codNivel);
                        $('#tipoCubiculo').val(cubiculo.tipoCubiculo || 'General');
                        $('#capacidad').val(cubiculo.capacidad || 1);
                        $('#precioHora').val(cubiculo.precioHora || 5.00);
                        $('#estadoCubiculo').val(cubiculo.estadoCubiculo);
                        $('#descripcion').val(cubiculo.descripcion || '');
                        $('#modalCubiculo').modal('show');
                    },
                    error: function (error) {
                        console.error('Error al obtener cubículo:', error);
                        Swal.fire('Error', 'No se pudo cargar la información del cubículo', 'error');
                    }
                });
            }

            function eliminarCubiculo(codCubiculo) {
                Swal.fire({
                    title: '¿Está seguro?',
                    text: "Esta acción no se puede deshacer",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#d33',
                    cancelButtonColor: '#3085d6',
                    confirmButtonText: 'Sí, eliminar',
                    cancelButtonText: 'Cancelar'
                }).then((result) => {
                    if (result.isConfirmed) {
                        $.ajax({
                            url: '<%= request.getContextPath()%>/cntCubiculos',
                            method: 'POST',
                            data: { accion: 'eliminar', codCubiculo: codCubiculo },
                            success: function (response) {
                                Swal.fire('Eliminado', 'El cubículo ha sido eliminado correctamente', 'success');
                                cargarCubiculos($('#locales').val());
                            },
                            error: function (error) {
                                console.error('Error al eliminar:', error);
                                Swal.fire('Error', 'No se pudo eliminar el cubículo', 'error');
                            }
                        });
                    }
                });
            }

            function asignarCliente(codCubiculo) {
                $('#codCubiculoAsignar').val(codCubiculo);
                $('#modalAsignar').modal('show');
            }

            function actualizarPrecio() {
                var tipo = $('#tipoCubiculo').find(':selected');
                var precio = tipo.data('precio');
                if (precio) {
                    $('#precioHora').val(precio);
                }
            }

            $('#formCubiculo').on('submit', function(e) {
                e.preventDefault();
                
                $.ajax({
                    url: $(this).attr('action'),
                    method: 'POST',
                    data: $(this).serialize(),
                    success: function(response) {
                        $('#modalCubiculo').modal('hide');
                        Swal.fire('Éxito', 'Cubículo guardado correctamente', 'success');
                        cargarCubiculos($('#locales').val());
                    },
                    error: function(xhr, status, error) {
                        console.error('Error completo:', xhr);
                        let mensaje = 'No se pudo guardar el cubículo';
                        if (xhr.responseText) {
                            try {
                                let response = JSON.parse(xhr.responseText);
                                mensaje = response.error || response.message || mensaje;
                            } catch(e) {
                                mensaje = xhr.responseText;
                            }
                        }
                        Swal.fire('Error', mensaje, 'error');
                    }
                });
            });

            $('#formAsignar').on('submit', function(e) {
                e.preventDefault();
                
                $.ajax({
                    url: $(this).attr('action'),
                    method: 'POST',
                    data: $(this).serialize(),
                    success: function(response) {
                        $('#modalAsignar').modal('hide');
                        Swal.fire('Éxito', 'Cliente asignado correctamente', 'success');
                        cargarCubiculos($('#locales').val());
                    },
                    error: function(error) {
                        console.error('Error:', error);
                        Swal.fire('Error', 'No se pudo asignar el cliente', 'error');
                    }
                });
            });
            function cargarCubiculos(CodLocal) {
    if (!CodLocal) {
        $('#cubiculosTable tbody').empty();
        return;
    }

    $.ajax({
        url: '<%= request.getContextPath()%>/cntCubiculos',
        method: 'GET',
        dataType: 'json',
        data: { accion: 'listar', codLocal: CodLocal }, // << IMPORTANTE (codLocal)
        success: function (data) {
            var tableBody = $('#cubiculosTable tbody');
            tableBody.empty();

            console.log("Cubículos recibidos:", data);

            data.forEach(function (cubiculo) {

                var estadoClass =
                    cubiculo.estadoCubiculo === 'Disponible' ? 'text-success' :
                    cubiculo.estadoCubiculo === 'Ocupado' ? 'text-danger' :
                    'text-warning';

                var row =
                    '<tr>' +
                        '<td>' + cubiculo.codCubiculo + '</td>' +
                        '<td>' + cubiculo.codNivel + '</td>' +
                        '<td>' + (cubiculo.tipoCubiculo || "General") + '</td>' +
                        '<td>' + (cubiculo.capacidad || 0) + '</td>' +
                        '<td>S/. ' + (cubiculo.precioHora || 0).toFixed(2) + '</td>' +
                        '<td class="' + estadoClass + '"><strong>' + cubiculo.estadoCubiculo + '</strong></td>' +
                        '<td>' +
                            '<button class="btn btn-sm btn-primary" onclick="asignarCliente(' + cubiculo.codCubiculo + ')" ' +
                                (cubiculo.estadoCubiculo !== 'Disponible' ? 'disabled' : '') +
                            '>Asignar</button> ' +

                            '<button class="btn btn-sm btn-warning" onclick="editarCubiculo(' + cubiculo.codCubiculo + ')">Editar</button> ' +

                            '<button class="btn btn-sm btn-danger" onclick="eliminarCubiculo(' + cubiculo.codCubiculo + ')">Eliminar</button>' +
                        '</td>' +
                    '</tr>';

                tableBody.append(row);
            });
        },

        error: function (error) {
            console.error("Error al cargar cubículos:", error);
            Swal.fire('Error', 'No se pudieron cargar los cubículos', 'error');
        }
    });
}

        </script>
    </body>
</html>