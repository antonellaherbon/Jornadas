# Cambios

## 41 · Programa sin señal
- Nueva tabla de Room `edicion`: tiene una fila si hubo al menos una descarga exitosa, aunque haya venido vacía.
- Si falla la petición y hay datos guardados, la lista sigue visible con un aviso.
- Si nunca se descargó nada, se muestra un aviso con un botón para reintentar.

## 42 · Agenda personal
- El DAO pasó a ser una clase abstracta que hace todo en una sola transacción: lee charlas están marcadas en ese momento, borra todas las charlas, inserta el programa nuevo con las marcas que había.
- Las charlas retiradas desaparecen del catálogo y de Mi agenda, y si una vuelve, vuelve sin marcar.
- Las marcas se leen recién cuando llega la respuesta.
- La base ahora esta en la versión 2 con una migración (`MIGRACION_1_2`) que crea la tabla `edicion` sin tocar las charlas ni las marcas.

## 43 · Volver al punto de lectura
- Los filtros de día y tema pasaron a `rememberSaveable` en `Portada`.
- Los filtros sobreviven a la rotación y a que Android mate el proceso. Una tarea nueva arranca sin filtros.

## 44 · Control de descargas
- Se sacó la recarga de `MainActivity.onStart()` y el `LaunchedEffect` de `Catalogo`, que pedían el programa en cada rotación, al volver de una ficha o al cambiar de pestaña.
- `cargaInicial()` hace una sola carga automática por proceso.
- Si se toca Actualizar durante una petición, el pedido queda anotado y se hace una petición más cuando termina la actual.
