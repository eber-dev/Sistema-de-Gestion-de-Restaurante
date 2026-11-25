# Revisión de la versión final para presentación

## Correcciones

- El total a cobrar y la confirmación usan el mismo cálculo que la venta: descuento y luego IGV, con importes redondeados a centavos. Al cambiar el método de pago se actualiza el total mostrado.
- El historial recupera la fecha real de `pagos.fecha_pago`. Como el esquema no guarda separadamente el descuento y el IGV históricos, indica que ese desglose no está disponible en lugar de mostrar valores inventados.
- Los pedidos con pagos registrados no se pueden eliminar, para conservar el historial de cobros. Un pedido sin pagos conserva la operación de eliminación existente.
- Al confirmar un pedido se vuelve a comprobar que los productos estén disponibles.
- La pantalla identifica las pasarelas simuladas y la boleta se presenta como simulada: no existe integración con pagos reales ni emisión fiscal electrónica.
- Se incluye nuevamente el ejemplo `db.local.properties.example` y se agrega el archivo personal al `.gitignore`. Si el archivo personal ya fue agregado al índice, debe retirarse del índice sin borrar la copia local; `.gitignore` no deja de seguir archivos que ya están preparados o versionados.

## Verificaciones realizadas

- Comparación del ZIP suministrado con el clon: 148 archivos de proyecto comparados, sin diferencias; no se compararon `.git`, archivos compilados ni configuración privada.
- Compilación completa mediante Ant y JDK 25 en una copia aislada.
- Pruebas automatizadas de importes para efectivo, tarjeta y billetera digital, inicialización, auditoría, identificadores, pedidos, rollback y reportes.
- MySQL 8.4 temporal aislado: instalación, CRUD y auditoría, registro de tres cobros, importes guardados, fecha histórica y fecha ausente, protección frente a eliminación de pedidos con pagos, producto no disponible, ambos reportes y gráficos, y rollback de auditoría. No se conectó ni se modificó la base personal.
- Comprobación automatizada de los componentes de la ventana de Ventas, sin mostrarla: el total cambia correctamente al seleccionar cada método. No sustituye la revisión visual con el usuario.

## Pendiente para terminar de validar

- Ejecutar en NetBeans y verificar visualmente el total al cambiar cada método de pago, la boleta, el historial y la pantalla de auditoría con datos de prueba.
- Verificar el proyecto en el equipo de presentación: JDK 25, servidor y credenciales MySQL, bibliotecas y resolución de pantalla.
- No se certifica la ausencia de todos los fallos. Las pasarelas de tarjeta y billetera son simuladas, y el inventario de ingredientes no se descuenta al vender.
