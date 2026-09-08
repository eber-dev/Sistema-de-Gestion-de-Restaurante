
# 📌 Avance seccion 1

En este avance se desarrolló la estructura base para la gestión de:

- Clientes
- Productos
- Categorías
- Precios
- Disponibilidad de productos

También se preparó una arquitectura por capas para separar las responsabilidades y facilitar la integración posterior con los módulos de pedidos, personalización, ventas, pagos, estados, sucursales y reportes.

La estructura fue diseñada aplicando los principios **SOLID**.

# 📁 Estructura del proyecto

```text
Source Packages
│
├── modelo
│   ├── Cliente.java
│   ├── Producto.java
│   └── Categoria.java
│
├── repositorio
│   ├── ClienteRepositorio.java
│   ├── ProductoRepositorio.java
│   └── CategoriaRepositorio.java
│
├── implementacion
│   ├── ClienteRepositorioMemoria.java
│   ├── ProductoRepositorioMemoria.java
│   └── CategoriaRepositorioMemoria.java
│
├── servicio
│   ├── ClienteServicio.java
│   ├── ProductoServicio.java
│   └── CategoriaServicio.java
│
├── vista
│   ├── FrmClientes.java
│   ├── FrmProductos.java
│   └── FrmCategorias.java
│
└── util
    └── Aplicacion.java
```

---

# 🏗️ Arquitectura

El sistema utiliza una arquitectura separada por responsabilidades:

```text
┌─────────────────────┐
│       VISTA         │
│      JFrame         │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│      SERVICIO       │
│  Reglas de negocio  │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│    REPOSITORIO      │
│      Interfaz       │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│   IMPLEMENTACIÓN    │
│ RepositorioMemoria  │
└──────────┬──────────┘
           │
           ▼
      ArrayList
```

# 📦 Paquete modelo

El paquete `modelo` contiene las entidades principales del sistema.

```text
modelo
├── Cliente.java
├── Producto.java
└── Categoria.java
```

---

# 👤 Cliente

Archivo:

```text
modelo/Cliente.java
```

La clase `Cliente` representa a una persona registrada en el restaurante.

## Atributos

```text
id
nombre
apellido
telefono
correo
direccion
```

## Responsabilidad

La clase solamente representa la información del cliente.

No se encarga de guardar datos, eliminar registros ni manejar la interfaz gráfica.

## Métodos principales

```java
getId()
setId()

getNombre()
setNombre()

getApellido()
setApellido()

getTelefono()
setTelefono()

getCorreo()
setCorreo()

getDireccion()
setDireccion()
```

También cuenta con constructores para crear objetos `Cliente`.

---

# 🗂️ Categoría

Archivo:

```text
modelo/Categoria.java
```

Representa una categoría de productos.

Ejemplos:

```text
Pizzas
Hamburguesas
Bebidas
Postres
Entradas
```

## Atributos

```text
id
nombre
descripcion
```

## Métodos principales

```java
getId()
setId()

getNombre()
setNombre()

getDescripcion()
setDescripcion()
```

También posee `toString()` para que el nombre de la categoría pueda mostrarse correctamente cuando el objeto sea utilizado en un `JComboBox`.

---

# 🍔 Producto

Archivo:

```text
modelo/Producto.java
```

Representa un producto disponible en el restaurante.

## Atributos

```text
id
nombre
descripcion
precio
disponible
categoria
```

## Relación con categoría

Cada producto pertenece a una categoría.

```text
Categoria
    │
    │ pertenece
    ▼
Producto
```

Ejemplo:

```text
Categoría: Pizzas

Productos:
- Pizza Margarita
- Pizza Hawaiana
- Pizza Napolitana
```

Dentro de `Producto` se mantiene la referencia a:

```java
Categoria categoria;
```

---

# 💰 Precio

El precio pertenece a la clase `Producto`.

```java
double precio;
```

El precio debe ser mayor que cero.

Ejemplos:

```text
20.00  → válido
35.50  → válido

0      → inválido
-10    → inválido
```

Las validaciones se realizan en `ProductoServicio`.

---

# 🟢 Disponibilidad

Los productos cuentan con:

```java
boolean disponible;
```

Esto permite indicar si un producto puede ser utilizado en un pedido.

Ejemplo:

```text
Pizza Margarita → Disponible
Pizza Especial  → No disponible
```

También se contempla la operación:

```java
cambiarDisponibilidad(int id, boolean disponible)
```

Esto permite activar o desactivar un producto.

---

# 🗄️ Paquete repositorio

El paquete `repositorio` contiene las interfaces que definen las operaciones de almacenamiento.

```text
repositorio
├── ClienteRepositorio.java
├── ProductoRepositorio.java
└── CategoriaRepositorio.java
```

Las interfaces permiten separar lo que se puede hacer de cómo se almacenan los datos.

---

# 👤 ClienteRepositorio

Archivo:

```text
repositorio/ClienteRepositorio.java
```

Define las operaciones para clientes.

```java
void guardar(Cliente cliente);

void actualizar(Cliente cliente);

void eliminar(int id);

Cliente buscarPorId(int id);

List<Cliente> listar();
```

---

# 🗂️ CategoriaRepositorio

Archivo:

```text
repositorio/CategoriaRepositorio.java
```

Define las operaciones para categorías.

```java
void guardar(Categoria categoria);

void actualizar(Categoria categoria);

void eliminar(int id);

Categoria buscarPorId(int id);

List<Categoria> listar();
```

---

# 🍔 ProductoRepositorio

Archivo:

```text
repositorio/ProductoRepositorio.java
```

Define las operaciones para productos.

```java
void guardar(Producto producto);

void actualizar(Producto producto);

void eliminar(int id);

Producto buscarPorId(int id);

List<Producto> listar();

List<Producto> listarDisponibles();
```

La operación:

```java
listarDisponibles()
```

permite obtener solamente los productos que actualmente están disponibles.

---

# 💾 Paquete implementacion

Este paquete contiene las implementaciones concretas de los repositorios.

```text
implementacion
├── ClienteRepositorioMemoria.java
├── ProductoRepositorioMemoria.java
└── CategoriaRepositorioMemoria.java
```

Actualmente se utiliza almacenamiento en memoria mediante `ArrayList`.

---

# ClienteRepositorioMemoria

Implementa:

```java
ClienteRepositorio
```

Utiliza una lista:

```java
ArrayList<Cliente>
```

para almacenar temporalmente los clientes.

Permite:

- Guardar clientes.
- Actualizar clientes.
- Eliminar clientes.
- Buscar clientes.
- Listar clientes.

---

# CategoriaRepositorioMemoria

Implementa:

```java
CategoriaRepositorio
```

Utiliza:

```java
ArrayList<Categoria>
```

Permite:

- Guardar categorías.
- Actualizar categorías.
- Eliminar categorías.
- Buscar categorías.
- Listar categorías.

---

# ProductoRepositorioMemoria

Implementa:

```java
ProductoRepositorio
```

Utiliza:

```java
ArrayList<Producto>
```

Permite:

- Guardar productos.
- Actualizar productos.
- Eliminar productos.
- Buscar productos.
- Listar productos.
- Obtener productos disponibles.

---

# ⚙️ Paquete servicio

El paquete `servicio` contiene la lógica de negocio.

```text
servicio
├── ClienteServicio.java
├── ProductoServicio.java
└── CategoriaServicio.java
```

Los servicios son importantes porque evitan colocar las reglas del sistema directamente dentro de los `JFrame`.

---

# ClienteServicio

Archivo:

```text
servicio/ClienteServicio.java
```

Se encarga de las operaciones relacionadas con clientes.

## Funciones

```java
guardar(Cliente cliente)
```

Guarda un cliente después de realizar las validaciones.

```java
actualizar(Cliente cliente)
```

Actualiza los datos de un cliente.

```java
eliminar(int id)
```

Elimina un cliente existente.

```java
buscarPorId(int id)
```

Busca un cliente.

```java
listar()
```

Obtiene todos los clientes.

---

# CategoriaServicio

Archivo:

```text
servicio/CategoriaServicio.java
```

Se encarga de las operaciones relacionadas con categorías.

## Funciones

```java
guardar(Categoria categoria)
```

Guarda una categoría.

```java
actualizar(Categoria categoria)
```

Actualiza una categoría.

```java
eliminar(int id)
```

Elimina una categoría.

```java
buscarPorId(int id)
```

Busca una categoría.

```java
listar()
```

Obtiene todas las categorías.

---

# ProductoServicio

Archivo:

```text
servicio/ProductoServicio.java
```

Se encarga de las operaciones y reglas de negocio relacionadas con productos.

## Funciones

```java
guardar(Producto producto)
```

Antes de guardar valida:

- Que el producto exista.
- Que tenga nombre.
- Que el precio sea mayor que cero.
- Que tenga categoría.

```java
actualizar(Producto producto)
```

Actualiza un producto después de validar sus datos.

```java
eliminar(int id)
```

Elimina un producto existente.

```java
buscarPorId(int id)
```

Busca un producto por su ID.

```java
listar()
```

Lista todos los productos.

```java
listarDisponibles()
```

Devuelve solamente productos disponibles.

```java
cambiarDisponibilidad(int id, boolean disponible)
```

Permite activar o desactivar un producto.

---

# 🔗 Clase Aplicacion

Archivo:

```text
util/Aplicacion.java
```

La clase `Aplicacion` centraliza los servicios y repositorios utilizados por el sistema.

Su objetivo es que las diferentes ventanas trabajen con las mismas instancias.

La estructura conceptual es:

```text
                    Aplicacion
                        │
          ┌─────────────┼─────────────┐
          │             │             │
          ▼             ▼             ▼
 ClienteServicio  CategoriaServicio  ProductoServicio
          │             │             │
          ▼             ▼             ▼
       Clientes      Categorias      Productos
```

Los formularios podrán obtener los servicios mediante:

```java
Aplicacion.getClienteServicio();

Aplicacion.getCategoriaServicio();

Aplicacion.getProductoServicio();
```

---

# 🔄 Ejemplo de uso

Para obtener los productos disponibles:

```java
List<Producto> productos =
    Aplicacion.getProductoServicio()
              .listarDisponibles();
```

Para buscar un cliente:

```java
Cliente cliente =
    Aplicacion.getClienteServicio()
              .buscarPorId(1);
```

Para obtener las categorías:

```java
List<Categoria> categorias =
    Aplicacion.getCategoriaServicio()
              .listar();
```

---

# 🖥️ Paquete vista

El paquete `vista` contendrá las interfaces gráficas desarrolladas mediante el diseñador visual de NetBeans.

```text
vista
├── FrmClientes.java
├── FrmProductos.java
└── FrmCategorias.java
```

Los formularios serán creados utilizando:

- JFrame Form
- JLabel
- JTextField
- JButton
- JTable
- JComboBox
- JCheckBox

---

# FrmClientes

Archivo:

```text
vista/FrmClientes.java
```

Será la ventana para gestionar clientes.

La interfaz tendrá:

```text
Nombre
Apellido
Teléfono
Correo
Dirección
```

Y botones:

```text
Guardar
Editar
Eliminar
Limpiar
```

También tendrá una tabla para visualizar los clientes registrados.

El formulario se conectará con:

```text
FrmClientes
     ↓
ClienteServicio
     ↓
ClienteRepositorio
```

---

# FrmCategorias

Archivo:

```text
vista/FrmCategorias.java
```

Será la ventana para gestionar categorías.

Campos:

```text
Nombre
Descripción
```

Botones:

```text
Guardar
Editar
Eliminar
Limpiar
```

También contará con una tabla para mostrar las categorías.

Flujo:

```text
FrmCategorias
      ↓
CategoriaServicio
      ↓
CategoriaRepositorio
```

---

# FrmProductos

Archivo:

```text
vista/FrmProductos.java
```

Será la ventana para gestionar productos.

Campos:

```text
Nombre
Descripción
Precio
Categoría
Disponibilidad
```

Elementos recomendados:

```text
JTextField     → Nombre
JTextArea      → Descripción
JTextField     → Precio
JComboBox      → Categoría
JCheckBox      → Disponible
JTable         → Productos
```

Botones:

```text
Guardar
Editar
Eliminar
Limpiar
```

La categoría se cargará desde:

```java
CategoriaServicio
```

y los productos se gestionarán mediante:

```java
ProductoServicio
```

---

# 🔌 Conexión de la vista con las clases

La vista no debería modificar directamente los `ArrayList`.

El flujo correcto será:

```text
Usuario
   ↓
JFrame
   ↓
Servicio
   ↓
Repositorio
   ↓
Datos
```

Por ejemplo:

```text
Usuario presiona "Guardar"
          ↓
FrmProductos
          ↓
crea Producto
          ↓
ProductoServicio.guardar()
          ↓
ProductoRepositorio.guardar()
          ↓
ProductoRepositorioMemoria
          ↓
ArrayList<Producto>
```

---

# 🧠 Principios SOLID

La estructura del proyecto fue diseñada aplicando los principios SOLID.

---

## S — Single Responsibility Principle

Cada clase tiene una responsabilidad específica.

```text
Cliente
    → representa los datos.

ClienteServicio
    → contiene reglas de negocio.

ClienteRepositorio
    → define operaciones de almacenamiento.

ClienteRepositorioMemoria
    → implementa el almacenamiento.

FrmClientes
    → interfaz gráfica.
```

De esta manera no se coloca toda la lógica dentro de una sola clase.

---

# O — Open/Closed Principle

Las clases pueden extenderse sin modificar innecesariamente el código existente.

Por ejemplo:

```text
ProductoRepositorio
        │
        ├── ProductoRepositorioMemoria
        │
        └── ProductoRepositorioBaseDatos
```

En el futuro podría crearse un repositorio conectado a una base de datos sin modificar `ProductoServicio`.

---

# L — Liskov Substitution Principle

Las implementaciones concretas pueden utilizarse mediante la interfaz correspondiente.

Ejemplo:

```java
ProductoRepositorio repositorio =
    new ProductoRepositorioMemoria();
```

El servicio trabaja con la interfaz y no necesita conocer los detalles internos de la implementación.

---

# I — Interface Segregation Principle

Las interfaces se encuentran separadas según su responsabilidad.

```text
ClienteRepositorio
ProductoRepositorio
CategoriaRepositorio
```

No existe una única interfaz con operaciones innecesarias para las diferentes entidades.

---

# D — Dependency Inversion Principle

Los servicios dependen de interfaces y no directamente de las implementaciones.

Ejemplo:

```java
private final ProductoRepositorio repositorio;
```

El servicio depende de:

```text
ProductoRepositorio
```

y no directamente de:

```text
ProductoRepositorioMemoria
```

Esto facilita cambiar posteriormente la forma de almacenamiento.

---

# 📊 Relación entre las clases

```text
                  Aplicacion
                      │
        ┌─────────────┼─────────────┐
        │             │             │
        ▼             ▼             ▼
 ClienteServicio CategoriaServicio ProductoServicio
        │             │             │
        ▼             ▼             ▼
ClienteRepositorio CategoriaRepositorio ProductoRepositorio
        │             │             │
        ▼             ▼             ▼
ClienteRepositorio  CategoriaRepositorio ProductoRepositorio
Memoria              Memoria             Memoria
        │             │             │
        ▼             ▼             ▼
    Clientes       Categorias       Productos
```



# ✅ Estado del Avance 1

## Modelos

- [x] Cliente
- [x] Producto
- [x] Categoría

## Repositorios

- [x] ClienteRepositorio
- [x] ProductoRepositorio
- [x] CategoriaRepositorio

## Implementaciones

- [x] ClienteRepositorioMemoria
- [x] ProductoRepositorioMemoria
- [x] CategoriaRepositorioMemoria

## Servicios

- [x] ClienteServicio
- [x] ProductoServicio
- [x] CategoriaServicio

## Funcionalidades

- [x] Gestión de clientes
- [x] Gestión de productos
- [x] Gestión de categorías
- [x] Gestión de precios
- [x] Gestión de disponibilidad
- [x] Consulta de productos disponibles
- [x] Relación Producto-Categoría
- [x] Validaciones básicas
- [x] Centralización de servicios

## Interfaz gráfica

- [ ] FrmClientes
- [ ] FrmCategorias
- [ ] FrmProductos
- [ ] Conexión de JFrame con servicios
- [ ] Tablas
- [ ] Botones CRUD
- [ ] ComboBox de categorías
- [ ] Control de disponibilidad

---

# 🚀 Próximo avance

En el siguiente avance se continuará con la interfaz gráfica.

Se implementarán:

- `FrmClientes`
- `FrmCategorias`
- `FrmProductos`

Cada formulario será conectado con su respectivo servicio.

Se implementarán las operaciones:

```text
Guardar
Editar
Eliminar
Limpiar
Listar
```

También se conectará:

```text
FrmProductos
      ↓
CategoriaServicio
      ↓
JComboBox
```

para permitir seleccionar una categoría existente al registrar un producto.

---

El siguiente objetivo es conectar la interfaz gráfica con esta estructura manteniendo la separación de responsabilidades y los principios SOLID.
````
