# Instalación, auditoría y verificación

## Ejecutar en una computadora

1. Instale JDK 25, Apache NetBeans y MySQL Server 8.0 o superior. El proyecto contiene MySQL Connector/J, FlatLaf y las bibliotecas de JasperReports en `lib`.
2. Abra en NetBeans la carpeta `restaurante` (contiene `build.xml` y `nbproject`). Para evitar bloqueos de OneDrive, se recomienda trabajar en una carpeta local no sincronizada. El diseño visual de la rama se conserva.
3. Copie `db.local.properties.example` como `db.local.properties` en esa carpeta `restaurante`. Configure su usuario y escriba su propia contraseña después de `db.password=`. Este archivo está ignorado por Git y no se copia al JAR. No escriba contraseñas en el archivo compartido `src/restaurante/config/db.properties`. Las variables de entorno `RESTAURANTE_DB_*` siguen teniendo prioridad sobre el archivo local.
4. Por defecto se usa `localhost:3306` y `restaurante_intermedio`. Si su servidor utiliza otro puerto o base, ajuste las propiedades en el archivo local; no necesita cambiar el servidor. Con `db.autoinit=true` y permisos suficientes, el programa crea la base y las tablas faltantes de manera no destructiva. Los datos de demostración solo se insertan si la base estaba vacía.
5. Ejecute **Clean and Build** y después **Run**. Si usa una base existente y establece `db.autoinit=false`, primero seleccione esa base en MySQL Workbench y ejecute `src/restaurante/config/sql/03_auditoria.sql`. Este script usa `CREATE TABLE IF NOT EXISTS` y no borra datos. Haga respaldo de su base antes de actualizarla.

Si NetBeans informa que no se puede borrar `build/classes` en una carpeta sincronizada, cierre la aplicación y pruebe una copia local fuera de OneDrive, o configure las propiedades `build.dir` y `dist.dir` en `nbproject/private/private.properties` con rutas locales propias. Ese archivo es individual y Git lo ignora. No es necesario cambiar puertos ni servicios.

Si MySQL rechaza el inicio, el mensaje distingue credenciales, servidor, base inexistente y permisos cuando el conector proporciona el código correspondiente. Revise que el servicio esté iniciado, que host/puerto coincidan y que `db.local.properties` esté junto a `build.xml`; no comparta la contraseña. En el JAR ejecutado por consola el directorio actual debe contener el archivo local, o puede indicarse con `-Drestaurante.config=ruta/al/archivo`.

## ¿Qué registra la tabla de auditoría?

La tabla `auditoria` registra fecha, entidad, ID, acción, detalle breve y la cuenta MySQL que ejecutó la operación. El botón **Ver auditoría** del menú muestra los últimos 500 registros. No hay inicio de sesión individual en la aplicación; por eso la cuenta MySQL no identifica necesariamente a la persona que hizo el cambio.

Se registran altas, cambios y eliminaciones de clientes, categorías y productos; creación, eliminación y cambios de estado de pedidos realizados mediante el DAO; y cobros. El historial conserva el identificador y el detalle aunque luego se elimine el registro original. La escritura de auditoría ocurre en la misma transacción JDBC; si falla, la operación se revierte. No captura cambios hechos directamente desde Workbench u otras aplicaciones. `pedido_eventos` se conserva como registro específico de estados utilizado por Observer.

Los nuevos registros de productos y categorías incluyen el nombre y, en actualizaciones, los campos modificados con valores anteriores y nuevos. Los de clientes identifican al cliente y muestran cambios de nombre; para teléfono, correo y dirección solo indican que se modificaron, sin conservar sus valores en la auditoría. Los cobros incluyen pedido, método y total. La columna admite hasta 255 caracteres: los textos largos se recortan. Los registros anteriores sin detalle se muestran como «Sin detalle registrado»; no se inventan valores históricos. Seleccione una fila para leer su detalle debajo de la tabla.

Para comprobarlo, cree un producto de prueba, modifíquelo y abra **Ver auditoría**; debe encontrar las acciones. Use datos de prueba. También puede consultar `SELECT * FROM auditoria ORDER BY id_auditoria DESC;` desde la base seleccionada en Workbench.

## Resultados de verificación

El proyecto compiló con Ant y JDK 25. Se probaron con MySQL 8.4 aislado la inicialización de una base temporal, altas, modificaciones y eliminaciones de prueba, registro de auditoría, pedido y cobro, generación de ambos reportes y gráficos con datos MySQL, y el rollback de un producto y un cobro al simular un fallo de auditoría. La actualización de detalles se probó con MySQL aislado para creación, modificación y eliminación, datos personales de contacto y rollback. Las pruebas no utilizaron ni cambiaron la base personal. Las pruebas JDBC simuladas también se ejecutaron. Queda pendiente comprobar visualmente la nueva vista de detalles en el equipo del usuario.

El registro y eliminación de pedidos y el cobro consultan o modifican al menos cinco tablas, como se detalla en `PRUEBAS_Y_TRANSACCIONES.md`. Las operaciones sencillas de mantenimiento de clientes, productos o categorías utilizan una transacción con su tabla y `auditoria`; no alcanzan cinco tablas. Si el profesor exige cinco tablas para absolutamente toda transacción, este punto requiere aclaración o una regla de negocio adicional; no se han añadido consultas artificiales para aumentar el conteo.
