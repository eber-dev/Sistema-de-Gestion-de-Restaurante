# Patrones y principios del curso

La siguiente matriz relaciona los 15 nombres de patrones del cronograma con clases reales. La presencia de una clase no sustituye la revisión del profesor sobre la calidad de su aplicación. Los patrones se conservan o agregan en el contexto de pedidos, ventas y reportes.

| Patrón | Clase o uso principal |
|---|---|
| Singleton | `ConexionDB`, `DAOFactory` |
| Prototype | `PedidoPrototype`, `Pedido.clonar()` |
| Factory Method | `PedidoFactory`, `PedidoFactoryConcreto` |
| Abstract Factory | `AbstractFactoryPedido`, `FactoryPedidoPresencial`, `FactoryPedidoVirtual` |
| Builder | `AbstractPedidoBuilder`, `PedidoBuilderConcreto` |
| Adapter | `PasarelaPago`, `YapeAdapter`, `VisaAdapter` |
| Facade | `VentaFacade` |
| Decorator | `Calculable`, `PedidoBase`, `DescuentoDecorator` |
| Composite | `TotalCompuesto`, `ImporteDetalle`, usado en el total de `Pedido` |
| Proxy | `ProxyReporteVentas`, `ProxyReporteProductos`, usados en reportes para creación diferida |
| Bridge | `DocumentoInforme`, `SalidaInforme`, `SalidaVista`, `SalidaPdf` |
| State | `EstadoPedido` y estados concretos, utilizados por `Pedido` |
| Observer | `PublicadorPedido`, `ObservadorPedido`, `AuditoriaPedido`, usados para registrar el evento de venta |
| Command | `ConfirmarPedidoComando` |
| Memento | `PedidoMemento`, utilizado por el comando para restaurar el estado en memoria cuando falla la persistencia |

Los proxies de reporte realizan carga diferida: no representan un sistema de seguridad. Las pasarelas son simuladas. El proyecto también separa vistas, controladores, servicios y DAO; por ejemplo `PedidoControlador` coordina una operación y `Pedido` conoce su propio total. Estos ejemplos ayudan a explicar asignación de responsabilidades, polimorfismo y bajo acoplamiento de GRASP/SOLID; no demuestran automáticamente que todas las reglas se cumplan en todas las clases.

## Otros temas del cronograma

| Tema | Ejemplo o alcance en el proyecto |
|---|---|
| SOLID: responsabilidad única | Las vistas presentan información; los DAO acceden a MySQL y `AuditoriaDAO` registra la auditoría. Hay clases existentes con más de una responsabilidad que pueden seguir mejorándose. |
| SOLID: abierto/cerrado | `PasarelaPago` permite añadir adaptadores; la selección en `VentaFacade` todavía necesita modificarse para registrar una nueva opción. |
| SOLID: sustitución de Liskov | Las implementaciones de `PasarelaPago` comparten el contrato `cobrar`. La compatibilidad entre clases requiere pruebas adicionales, no se garantiza solo por implementar una interfaz. |
| SOLID: segregación de interfaces | Los contratos de repositorio se dividen por entidad; `ObservadorPedido` expone la operación que necesita el publicador. |
| SOLID: inversión de dependencias | Servicios como `ProductoServicio` reciben un `ProductoRepositorio` en lugar de depender directamente de su DAO concreto. Existen dependencias concretas en otras partes del proyecto. |
| GRASP: alta cohesión y bajo acoplamiento | Separación por modelo, vista, controlador, servicio y repositorio; se trata de objetivos de diseño y no de garantías absolutas. |
| GRASP: controlador | `PedidoControlador` coordina las operaciones solicitadas desde la vista. |
| GRASP: creador | Las fábricas de pedidos y el builder crean los objetos correspondientes. |
| GRASP: experto en información | `Pedido` conoce sus detalles y calcula el total. |
| GRASP: fabricación pura | `ProductoServicio` concentra validaciones y delega almacenamiento. |
| GRASP: polimorfismo | `PasarelaPago` permite invocar el cobro de las diferentes pasarelas mediante el mismo contrato. |

Los antipatrones son materia de análisis: no se añadieron errores deliberados para representarlos. El cronograma también incluye conceptos, historia y clasificación de patrones; esos temas se explican en la exposición, no son clases adicionales obligatorias. Los 15 nombres GOF de la tabla anterior están representados, pero su valoración y profundidad corresponden al profesor.
