# Sistema de gestión de restaurante

Proyecto Java Swing para Apache NetBeans y Ant, ampliado a partir del avance original. Conserva su estructura y su configuración MySQL; incluye informes JasperReports, gráficos y ejemplos de patrones de diseño. Consulte `documentacion/` para conocer los cambios y las pruebas pendientes.

## Preparación en Windows

1. Extraiga el proyecto en una carpeta local, preferentemente fuera de OneDrive para evitar bloqueos durante Clean and Build. Abra en NetBeans la carpeta que contiene `build.xml` y `nbproject`.
2. El proyecto original está configurado para JDK 25. Seleccione ese JDK en NetBeans, o instálelo si no está disponible. Las bibliotecas JDBC, JasperReports y JFreeChart están incluidas en `lib` y configuradas con rutas relativas.
3. Inicie su MySQL Server. La configuración predeterminada es `localhost`, puerto `3306`, base `restaurante_intermedio` y usuario `root`. El puerto puede ajustarse sin modificar la configuración del servidor.
4. Configure las credenciales por una de las dos opciones siguientes. No envíe su contraseña por mensajes ni entregue un proyecto que la contenga.

### Opción recomendada: variables de entorno

Cierre las instancias abiertas de NetBeans. Abra PowerShell y ejecute lo siguiente; escriba su contraseña cuando se solicite, sin enviarla a nadie. Ajuste la ruta del ejecutable si su instalación es distinta:

```powershell
$env:RESTAURANTE_DB_USER = 'root'
$env:RESTAURANTE_DB_PASSWORD = [System.Net.NetworkCredential]::new('', (Read-Host 'Contraseña de MySQL' -AsSecureString)).Password
& 'C:\Program Files\Apache NetBeans\bin\netbeans64.exe'
```

Para un puerto o una base diferentes, antes de abrir NetBeans establezca, por ejemplo, `$env:RESTAURANTE_DB_PORT = '3306'` o `$env:RESTAURANTE_DB_NAME = 'restaurante_prueba'`. También existen `RESTAURANTE_DB_HOST` y `RESTAURANTE_DB_AUTOINIT`. Las variables tienen prioridad sobre el archivo de propiedades. Se aplican a NetBeans iniciado desde esa misma ventana de PowerShell y a sus procesos hijos.

### Opción de la captura: archivo de propiedades

En `src/restaurante/config/db.properties`, escriba su usuario y contraseña en `db.user` y `db.password`; ajuste host, puerto y nombre si es necesario. Al compilar, el archivo se copia a `build` y puede quedar dentro del JAR. Antes de compartir, quite la contraseña del archivo y vuelva a generar el proyecto; revise también los artefactos y copias previas. El ZIP entregado no contiene una contraseña personal.

## Inicialización de la base

`db.autoinit=true` permite crear la base configurada si no existe y crear tablas faltantes con `CREATE TABLE IF NOT EXISTS`. Si no existían tablas, añade datos de ejemplo con `INSERT IGNORE`; si la base ya contiene tablas, no inserta esos datos. No elimina tablas. El usuario MySQL necesita permisos suficientes.

Para la primera revisión se recomienda una base nueva de pruebas, configurada mediante `RESTAURANTE_DB_NAME`, con auto-inicialización activa. Para usar una base existente, haga una copia de seguridad y compruebe que el esquema coincide; puede establecer `RESTAURANTE_DB_AUTOINIT=false` para evitar la inicialización automática. `CREATE TABLE IF NOT EXISTS` no actualiza columnas de tablas antiguas. Los scripts están en `src/restaurante/config/sql/`.

5. En NetBeans ejecute **Clean and Build** y después **Run**. Si el compilador indica que falta JDK 25, seleccione la plataforma adecuada en las propiedades del proyecto.
6. En el menú principal abra **Reportes**, elija ventas o productos, indique fechas `AAAA-MM-DD` y genere el informe. La pestaña de gráfico se muestra en la aplicación; **Guardar PDF** exporta el informe tabular, no el gráfico.
7. Pruebe pedidos y ventas únicamente con datos de prueba. Revise `documentacion/PRUEBAS_Y_TRANSACCIONES.md` antes de dar el sistema por listo para presentar.

Las opciones de Yape y Visa son simulaciones del proyecto, no pagos reales. La base original no relaciona recetas de productos con ingredientes, por lo que no se descuenta inventario al vender.
