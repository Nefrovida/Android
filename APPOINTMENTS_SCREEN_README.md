# Pantalla de Consulta de Citas Médicas - NEFROVida

## Descripción
Pantalla completa de Android para consultar citas médicas, desarrollada con Kotlin, Jetpack Compose y Material Design 3.

## Estructura del Proyecto

### Archivos Creados

1. **`model/Appointment.kt`**
   - `Appointment`: Modelo de datos para representar una cita médica
   - `DayItem`: Modelo para representar un día en el selector

2. **`ui/screens/AppointmentsScreen.kt`**
   - `AppointmentsScreen`: Pantalla principal
   - `TopBarNefroVida`: Barra superior con logo e íconos
   - `DaySelector`: Selector de días desplazable
   - `DayCard`: Tarjeta individual de día
   - `ScheduleList`: Lista vertical de horas
   - `ScheduleRow`: Fila de hora con cita
   - `AppointmentCard`: Tarjeta de cita con nombre del paciente

3. **`ui/theme/Color.kt`**
   - Colores de NEFROVida:
     - `NefroBlue`: #1A237E (azul oscuro)
     - `NefroGreen`: #4CAF50 (verde)
     - `NefroLightGray`: #F5F5F5 (gris claro)
     - `NefroSelectedBlue`: #64B5F6 (azul claro para selección)
     - `NefroTextGray`: #757575 (gris texto)

## Características Implementadas

### 1. TopAppBar
- Logo "NEFROVida" con colores personalizados
- Ícono de perfil a la izquierda
- Ícono de ajustes a la derecha
- Fondo blanco

### 2. Selector de Fecha
- Encabezado del mes centrado
- Fila horizontal de días desplazable
- Día seleccionado con fondo azul claro
- Días no seleccionados con fondo blanco

### 3. Agenda del Día
- Lista vertical de horas de 8 AM a 4 PM
- Hora en formato AM/PM
- Tarjetas de citas con fondo gris claro
- Scroll vertical para toda la lista

## Cómo Usar

### Visualizar en Android Studio
1. Abre el proyecto en Android Studio
2. Navega a `ui/screens/AppointmentsScreen.kt`
3. Haz clic en el botón "Preview" en la esquina superior derecha
4. Verás la vista previa de la pantalla con datos de ejemplo

### Ejecutar en Emulador o Dispositivo
1. Conecta un dispositivo Android o inicia un emulador
2. Ejecuta la aplicación desde Android Studio
3. La pantalla se mostrará automáticamente con datos de ejemplo

## Datos de Ejemplo

Actualmente la aplicación usa datos hardcoded en `MainActivity.kt`:

```kotlin
val sampleAppointments = listOf(
    Appointment(
        id = 1,
        patientName = "Ramón Macías García",
        hour = 8,
        date = "2024-10-16"
    ),
    Appointment(
        id = 2,
        patientName = "Fabiola Rosales López",
        hour = 9,
        date = "2024-10-16"
    )
)
```

## Conexión a Backend (Preparación Futura)

### Estructura de Datos Preparada

El modelo `Appointment` está listo para integrarse con un backend:

```kotlin
data class Appointment(
    val id: Int,
    val patientName: String,
    val hour: Int, // 0-23
    val date: String // "YYYY-MM-DD"
)
```

### Pasos para Integrar con Backend

1. **Agregar Dependencias de Retrofit** (en `app/build.gradle.kts`):
```kotlin
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
```

2. **Crear una API Service**:
```kotlin
interface AppointmentApiService {
    @GET("appointments/{date}")
    suspend fun getAppointments(
        @Path("date") date: String
    ): List<Appointment>
}
```

3. **Implementar ViewModel**:
```kotlin
class AppointmentsViewModel : ViewModel() {
    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    fun loadAppointments(date: String) {
        viewModelScope.launch {
            try {
                val data = appointmentService.getAppointments(date)
                _appointments.value = data
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }
}
```

4. **Actualizar MainActivity**:
```kotlin
class MainActivity : ComponentActivity() {
    private val viewModel: AppointmentsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appointments by viewModel.appointments.collectAsState()

            AppointmentsScreen(
                appointments = appointments,
                // ...
            )
        }
    }
}
```

## Personalización

### Cambiar Colores
Edita `ui/theme/Color.kt`:
```kotlin
val NefroBlue = Color(0xFF1A237E) // Cambia el código hexadecimal
```

### Cambiar Rango de Horas
Edita `AppointmentsScreen.kt`, función `ScheduleList`:
```kotlin
val hours = (6..20).toList() // De 6 AM a 8 PM
```

### Agregar Más Información a las Citas
1. Actualiza el modelo `Appointment`:
```kotlin
data class Appointment(
    val id: Int,
    val patientName: String,
    val hour: Int,
    val date: String,
    val reason: String, // Nueva propiedad
    val duration: Int // Duración en minutos
)
```

2. Actualiza `AppointmentCard` para mostrar la nueva información

## Componentes Reutilizables

Todos los componentes están diseñados para ser reutilizables:

- `TopBarNefroVida`: Úsalo en otras pantallas de la app
- `DaySelector`: Reutilizable en calendarios
- `AppointmentCard`: Adaptable para mostrar diferentes tipos de tarjetas

## Mejoras Futuras Sugeridas

1. **Estado de Carga**: Agregar un indicador mientras se cargan las citas
2. **Manejo de Errores**: Mostrar mensajes cuando no hay conexión o falla el servidor
3. **Pull-to-Refresh**: Permitir actualizar las citas deslizando hacia abajo
4. **Filtros**: Agregar filtros por tipo de consulta o doctor
5. **Navegación**: Implementar navegación a detalles de cada cita
6. **Notificaciones**: Recordatorios de citas próximas
7. **Búsqueda**: Buscar pacientes en la lista
8. **Animaciones**: Transiciones suaves entre días

## Soporte
Para preguntas o problemas, contacta al equipo de desarrollo de NEFROVida.
