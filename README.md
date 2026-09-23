# Avance 1

Este módulo establece los cimientos del sistema para la gestión de **Clientes**, **Productos**, **Categorías**, **Precios** y **Disponibilidad**. Todo el código ha sido diseñado aplicando los **principios SOLID** y una **arquitectura por capas** para garantizar escalabilidad e independencia de la interfaz gráfica.

---

## 📁 1. Estructura del Proyecto

El proyecto se divide en paquetes que separan claramente las responsabilidades:

```text
Source Packages
├── modelo           # Entidades (Cliente, Producto, Categoria)
├── repositorio      # Interfaces de acceso a datos
├── implementacion   # Implementaciones en memoria (ArrayList)
├── servicio         # Lógica de negocio y validaciones
├── vista            # Interfaces gráficas (JFrame - NetBeans)
└── util             # Utilidades y centralizador (Aplicacion.java)

```

---

## 2. Arquitectura por Capas

El flujo de información es unidireccional y desacoplado:

```text
[ VISTA (JFrame) ] 
       │  (Solicita acción / UI)
       ▼
[ SERVICIO ] 
       │  (Aplica reglas de negocio y validaciones)
       ▼
[ REPOSITORIO (Interfaz) ] 
       │  (Define operaciones CRUD)
       ▼
[ IMPLEMENTACIÓN (Memoria) ] 
       │  (Almacena en ArrayList)
       ▼
   [ DATOS ]

```

---

## 3. ¿Cómo usar los Servicios en el Equipo?

Para evitar crear múltiples instancias o mezclar lógica en los formularios (`JFrame`), centralizamos el acceso mediante la clase **`Aplicacion`**.

### Ejemplos prácticos para implementar en las vistas:

* **Obtener productos disponibles (ej. para un menú o combo):**
```java
List<Producto> productosDisponibles = Aplicacion.getProductoServicio().listarDisponibles();

```


* **Buscar un cliente por ID:**
```java
Cliente cliente = Aplicacion.getClienteServicio().buscarPorId(1);

```


* **Guardar un producto validando reglas de negocio:**
```java
Producto nuevoP = new Producto(...);
Aplicacion.getProductoServicio().guardar(nuevoP); // Valida precio > 0, categoría existente, etc.

```



---

## Estado Actual del Avance

### Backend & Lógica (Completado)

* [x] Modelos de datos (`Cliente`, `Producto`, `Categoria`)
* [x] Interfaces de Repositorios y sus implementaciones en memoria (`ArrayList`)
* [x] Capa de Servicios con validaciones de negocio
* [x] Centralizador de servicios (`Aplicacion.java`)

### Interfaz Gráfica (Pendiente para el Siguiente Avance)

* [ ] `FrmClientes` (Gestión visual de clientes)
* [ ] `FrmCategorias` (Gestión visual de categorías)
* [ ] `FrmProductos` (Gestión visual de productos con JComboBox y JCheckBox)
* [ ] Conexión definitiva de los `JFrame` con la capa de servicios.

---

## 🚀 Próximos Pasos

En el siguiente avance implementaremos los formularios gráficos (`JFrame`) en el paquete `vista`, conectando cada botón (Guardar, Editar, Eliminar, Listar) con su respectivo servicio.

```

```
