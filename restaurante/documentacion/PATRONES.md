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
