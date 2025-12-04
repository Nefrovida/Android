# Registro de Pacientes (Backend) y cómo integrarlo en Android

Este documento explica exclusivamente el flujo de registro de *pacientes* en el backend de Nefrovida y muestra ejemplos concretos para integrarlo desde una aplicación Android (Kotlin) usando Retrofit/OkHttp.

**Resumen rápido**
- **Endpoint**: `POST /api/auth/register`
- **Rol por defecto para paciente**: `DEFAULT_ROLES.PATIENT = 3` (el backend asigna PATIENT cuando no se especifica `role_id`).
- **Campo obligatorio para pacientes**: `curp` (CURP única en la tabla `patients`).
- **Estado tras registrar**: el usuario se crea con `user_status = PENDING` y `active = false`. No se retornan tokens; la cuenta requiere aprobación de un administrador.

---

## Cómo funciona en el backend (flujo)

1. El cliente hace `POST /api/auth/register` con un JSON que contiene datos del usuario.
2. El backend valida si el `username` ya existe. Si existe, devuelve 409.
3. Se determina `role_id` (si no viene, por defecto es `PATIENT`).
4. Para `PATIENT`, el backend exige `curp`. Si falta, devuelve 409.
5. La contraseña se cifra con bcrypt y se crea un registro en `users` con `user_status = 'PENDING'` y `active = false`.
6. Se crea la entrada en `patients` con `user_id` y `curp`.
7. El controlador responde `201` con un mensaje indicando que la cuenta está pendiente de aprobación y devuelve los datos del usuario (sin tokens).

Archivos clave en el backend (referencia):
- `POST /api/auth/register` → ruta en `backend/src/routes/auth.routes.ts`
- Lógica de creación → `backend/src/service/auth.service.ts` (función `register`)
- Tipos → `backend/src/types/auth.types.ts` (`RegisterRequest`)
- Modelo de BD → `backend/prisma/schema.prisma` (tablas `users` y `patients`)

---

## Esquema del request para pacientes

Contenido mínimo esperado en el body (JSON) para registrar un paciente:

{
  "name": "Juan",
  "parent_last_name": "Pérez",
  "maternal_last_name": "López",
  "phone_number": "5512345678",
  "username": "juan.perez@example.com",
  "password": "Password1#",
  "birthday": "1990-05-10",   // fecha ISO
  "gender": "MALE",           // enum: MALE | FEMALE | OTHER
  "curp": "CURPDEJEMPLO123456" // OBLIGATORIO para pacientes
}

Notas:
- `birthday` puede enviarse como string ISO (el backend lo convierte a `Date`).
- `gender` debe usar los valores del enum Prisma: `MALE`, `FEMALE`, `OTHER`.

---

## Respuestas relevantes

- Éxito: HTTP 201

  {
    "message": "Registro exitoso, tu cuenta está pendiente de aprobación por un administrador.",
    "user": { "user_id": "...", "name": "Juan", "username": "juan...", "role_id": 3 },
    "pending": true
  }

- Errores comunes:
  - 409 Conflict: usuario ya existe o falta `curp` para paciente.
  - 400 Bad Request: campos obligatorios faltantes o formato inválido.
  - 500 Server Error: error inesperado.

---

## Consideraciones para la app Android

- El endpoint es `POST https://<HOST>/api/auth/register`.
- No se espera que el registro devuelva tokens (la cuenta queda `PENDING`). Para logueo y obtener tokens usa `POST /api/auth/login`.
- CORS y cookies: el servidor establece cookies cuando responde a `login`, pero en registro no se devuelven tokens. En móviles, normalmente usarás la respuesta JSON (no las cookies).

Recomendaciones de UX:
- Mostrar un mensaje claro: "Registro realizado. Tu cuenta está pendiente de aprobación por un administrador.".
- Ofrecer pantalla de login después de registro.
- Validar en cliente la password policy y campos requeridos (evita roundtrips innecesarios).

---

## Ejemplo Android (Kotlin) con Retrofit + Coroutines

1) Dependencias (Gradle):

usar las ya existentes

2) DTOs (Kotlin)

```kotlin
data class RegisterRequest(
  val name: String,
  val parent_last_name: String,
  val maternal_last_name: String?,
  val phone_number: String,
  val username: String,
  val password: String,
  val birthday: String, // "YYYY-MM-DD" o ISO
  val gender: String,   // "MALE" | "FEMALE" | "OTHER"
  val curp: String
)

data class RegisterResponse(
  val message: String,
  val user: UserSummary,
  val pending: Boolean
)

data class UserSummary(
  val user_id: String,
  val name: String,
  val username: String,
  val role_id: Int
)
```

3) Interfaz Retrofit

```kotlin
interface AuthApi {
  @POST("/api/auth/register")
  suspend fun register(@Body body: RegisterRequest): Response<RegisterResponse>
}
```

4) Crear cliente Retrofit

```kotlin
val moshi = Moshi.Builder().build()

val client = OkHttpClient.Builder()
  .callTimeout(30, TimeUnit.SECONDS)
  .build()

val retrofit = Retrofit.Builder()
  .baseUrl("https://tu-backend.example.com")
  .client(client)
  .addConverterFactory(MoshiConverterFactory.create(moshi))
  .build()

val authApi = retrofit.create(AuthApi::class.java)
```

5) Llamada desde un ViewModel (ejemplo con coroutines)

```kotlin
viewModelScope.launch {
  val request = RegisterRequest(
    name = "Juan",
    parent_last_name = "Pérez",
    maternal_last_name = "López",
    phone_number = "5512345678",
    username = "juan@example.com",
    password = "Password1#",
    birthday = "1990-05-10",
    gender = "MALE",
    curp = "CURP123456"
  )

  val response = authApi.register(request)
  if (response.isSuccessful) {
    val body = response.body()
    // Mostrar mensaje de éxito y estado pending
  } else {
    // Manejar errores (409, 400, etc.). Puedes parsear response.errorBody().
  }
}
```

---

## Ejemplo con cURL (útil para pruebas)

```bash
curl -X POST "https://3001/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "name":"Juan",
    "parent_last_name":"Pérez",
    "phone_number":"5512345678",
    "username":"juan@example.com",
    "password":"Password1#",
    "birthday":"1990-05-10",
    "gender":"MALE",
    "curp":"CURP123456"
  }'
```

---

## Errores y cómo manejarlos en Android

- 409 Conflict: mostrar mensaje "Usuario ya existe" o "Falta CURP".
- 400 Bad Request: campos inválidos — parsear y mostrar campos faltantes.
- 500: reintentar o mostrar mensaje de error genérico.

Cuando sea posible, validar en cliente:
- Formato de CURP (si tienen regla), existencia de caracteres inválidos.
- Política de contraseña (mostrar ayuda a usuario).

---

## Notas de seguridad y producción

- Usar HTTPS en producción.
- No enviar contraseñas en query params.
- Si desean que la app móvil reciba tokens al registrar (no implementado por defecto), hay dos opciones:
  1. Cambiar backend para que apruebe y devuelva tokens automáticamente (no recomendable sin validar identidad).
  2. Tras registro, pedir al usuario que haga login (`/api/auth/login`) y allí obtener tokens.

---

## Resumen / Pasos rápidos para implementar en Android

1. Construir UI con campos requeridos (incluyendo `curp`).
2. Validar localmente contraseña, campos obligatorios, y formato de `birthday`/`gender`.
3. POST a `/api/auth/register` con JSON.
4. Manejar respuesta 201 mostrando el mensaje de cuenta pendiente.
5. Permitir al usuario intentar login cuando sea aprobado por admin.

---

Si quieres, puedo:
- Añadir un ejemplo de validación de CURP en Kotlin.
- Preparar una pequeña pantalla de ejemplo (Activity/Fragment + ViewModel).
- Modificar el backend para devolver tokens automáticamente en caso de que queráis registrar y activar al paciente inmediatamente (necesita cambio de política).
