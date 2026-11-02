# Transacciones, cambios y pruebas

## Operaciones transaccionales principales

El requisito acordado cuenta tablas consultadas y modificadas. Las lecturas no equivalen a actualizaciones.

| Operación | Tablas participantes | Cambios |
|---|---|---|
| Registrar pedido | `empleados`, `clientes`, `productos`, `pedidos`, `detalle_pedido`; `mesas` cuando corresponde | Consulta y bloquea datos relacionados; inserta pedido y detalles. |
| Eliminar pedido | `pedidos`, `detalle_pedido`, `productos`, `pagos`, `pedido_eventos` | Consulta las filas relacionadas y elimina los registros asociados, como ya hacía la operación original. |
| Registrar venta | `pedidos`, `detalle_pedido`, `productos`, `pagos`, `pedido_eventos`; `mesas` cuando corresponde | Inserta el pago y la auditoría, actualiza el pedido y, si corresponde, libera la mesa. |

Las operaciones usan conexión JDBC, `setAutoCommit(false)`, `commit()` y `rollback()` ante fallos. El registro de pago y el cambio de estado se realizan en la misma conexión. Para ventas se verifica el estado del pedido y la existencia de pagos, y se bloquean filas para evitar operaciones concurrentes incompatibles. La eliminación conserva el comportamiento original y borra registros asociados: use una base de prueba para ensayarla.

No se implementó descuento de inventario: las tablas de ingredientes/inventario de la base original no incluyen una relación de recetas con los productos. Agregarla sin reglas de negocio inventaría una estructura y cantidades no definidas.

## Verificación realizada

- Compilación completa con Ant y JDK 25: exitosa.
- Pruebas automatizadas con JDBC simulado: registro y eliminación de pedidos y venta, incluyendo confirmación, reversión ante un fallo y participación de tablas; no sustituyen una prueba de motor MySQL.
- Generación de códigos de pedido, comportamiento de Composite y restauración de Memento: verificados con pruebas locales.
- Los dos diseños JRXML se compilaron y generaron PDF en memoria con datos simulados; también se probó el caso sin datos. Esto no comprueba sus consultas en MySQL ni la ventana gráfica.
- Inicialización vacía y base existente: comportamiento ensayado con metadatos y conexiones simuladas.

Se intentó iniciar una instancia MySQL aislada para una prueba real, pero el entorno no permitió conectar con ella; se detuvo. No se ejecutaron scripts en su servidor ni se modificó su base.

## Pendiente en el equipo del usuario

1. En una base nueva de prueba, abrir el proyecto con NetBeans y probar la creación automática y conexión con su usuario MySQL.
2. Abrir las pantallas existentes; comprobar productos, clientes, categorías, pedidos y ventas con datos de prueba.
3. Registrar una venta y comprobar pago, estado, evento y mesa. Forzar un fallo en un entorno de prueba y comprobar que no quedan registros parciales. Verificar la operación de eliminar solo con datos que se puedan borrar.
4. Generar ambos reportes con datos reales, revisar las pestañas y exportar el PDF. El gráfico se muestra en pantalla y no forma parte del PDF.
5. Revisar con el profesor la interpretación de los patrones, las operaciones y cualquier requisito adicional. Hasta completar las pruebas reales no se afirma que el proyecto esté listo para presentar.

Las fuentes de las pruebas simuladas están en `verificacion/`. Su propósito es apoyar el diagnóstico, no poblar ni modificar una base real.

## Cambios principales

Configuración externa de conexión e inicialización; transacciones de pedidos y ventas; estado `Listo` y códigos de pedido únicos; patrones Composite, Observer, Memento, Bridge y proxies de reportes; reportes Jasper de ventas/productos y gráficos; documentación y bibliotecas incluidas. Se mantuvieron las ventanas, el proyecto Ant/NetBeans y la estructura SQL originales como base.
