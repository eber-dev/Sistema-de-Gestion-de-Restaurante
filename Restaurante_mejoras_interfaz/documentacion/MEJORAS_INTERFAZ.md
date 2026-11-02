# Mejoras de navegación y reportes

- El menú principal se oculta al abrir una sección. Cada sección dispone del botón «Volver al menú principal» y la X regresa a la ventana anterior. Pedidos puede abrir Ventas sin mantener ambas visibles.
- Al guardar un producto se puede dejar el ID vacío: MySQL usa `AUTO_INCREMENT`. También se conserva la opción de indicar un ID positivo. En edición y eliminación se utiliza el ID seleccionado.
- El mensaje de confirmación de pedidos utiliza lenguaje orientado al usuario.
- Los reportes tienen encabezados, descripción del periodo y pie de página; productos incluye totales y una aclaración sobre los importes antes de descuentos generales.
- Los gráficos identifican el periodo, ejes y valores; el gráfico de productos usa barras horizontales y distingue productos con nombres iguales por su ID. El gráfico aparece en la aplicación; el PDF contiene la tabla.

Compilación Ant y pruebas automáticas con JDBC y datos de informe simulados completadas. El ID automático, la navegación y la versión modificada de los reportes aún deben probarse con MySQL y la interfaz en el equipo del usuario. Las pruebas que el usuario realizó anteriormente corresponden a la versión previa.

Para actualizar sin perder datos, use esta carpeta como un proyecto adicional y configure en su `db.properties` el mismo servidor y base `restaurante_intermedio`. La base y los productos ya registrados se mantienen en MySQL; el ZIP no incluye la contraseña. No es necesario ejecutar scripts de borrado.
