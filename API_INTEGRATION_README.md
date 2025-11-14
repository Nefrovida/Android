# API Integration - Doctor Appointments Feature

## Overview
This document explains the API integration implementation for the doctor appointments feature in the NEFROVida Android application.

## Architecture

The implementation follows Clean Architecture with the following layers:

### 1. Data Layer
- **`AppointmentApiService`** (`data/remote/AppointmentApiService.kt`): Retrofit interface defining API endpoints
- **`AppointmentDto`** (`data/remote/dto/AppointmentDto.kt`): Data Transfer Object for API responses

### 2. Domain Layer
- **`Appointment`** (`domain/model/Appointment.kt`): Domain model for appointments
- **`DayItem`** (`domain/model/DayItem.kt`): Domain model for calendar days
- **`AppointmentRepository`** (`domain/repository/AppointmentRepository.kt`): Repository interface implementation

### 3. Presentation Layer
- **`AppointmentsViewModel`** (`presentation/viewmodel/AppointmentsViewModel.kt`): ViewModel managing UI state
- **`AppointmentsScreen`** (`presentation/screens/AppointmentsScreen.kt`): Compose UI screen

### 4. Dependency Injection
- **`NetworkModule`** (`di/NetworkModule.kt`): Hilt module providing network dependencies
- **`MainApplication`** (`MainApplication.kt`): Application class with Hilt configuration

## Configuration Required

### 1. API Base URL
Update the base URL in `NetworkModule.kt`:

```kotlin
private const val BASE_URL = "https://api.nefrovida.com/"
```

**Current Status**: Placeholder URL needs to be replaced with your actual API endpoint.

### 2. API Endpoints

The application expects the following endpoints:

#### Get Appointments by Date
```
GET /appointments?doctor_id={doctorId}&date={date}
```

**Query Parameters**:
- `doctor_id` (Int): ID of the doctor
- `date` (String): Date in format `YYYY-MM-DD`

**Response Format**:
```json
[
  {
    "id": 1,
    "patient_name": "Ramón Macías García",
    "hour": 8,
    "date": "2024-10-16",
    "doctor_id": 1,
    "status": "scheduled",
    "notes": "Optional notes"
  }
]
```

#### Get Appointments by Date Range
```
GET /appointments/range?doctor_id={doctorId}&start_date={startDate}&end_date={endDate}
```

**Query Parameters**:
- `doctor_id` (Int): ID of the doctor
- `start_date` (String): Start date in format `YYYY-MM-DD`
- `end_date` (String): End date in format `YYYY-MM-DD`

**Response Format**: Same as above

### 3. Doctor ID Configuration

Currently, the doctor ID is hardcoded in `AppointmentsViewModel.kt`:

```kotlin
private var currentDoctorId: Int = 1 // TODO: Obtain from login
```

**To Do**:
- Implement authentication/login flow
- Store doctor ID in SharedPreferences or DataStore after login
- Update `setDoctorId()` method when user logs in

## Dependencies Added

### Gradle Dependencies
The following dependencies were added to `gradle/libs.versions.toml` and `app/build.gradle.kts`:

- **Retrofit 2.9.0**: HTTP client for API calls
- **OkHttp 4.12.0**: HTTP logging interceptor
- **Hilt 2.48**: Dependency injection
- **ViewModel Compose 2.7.0**: ViewModel integration with Compose

## Data Flow

1. **UI Layer** → User selects a day
2. **ViewModel** → Calls repository with doctor ID and date
3. **Repository** → Makes API call via Retrofit
4. **API Service** → Fetches data from backend
5. **DTO** → Converts response to domain model
6. **ViewModel** → Updates UI state
7. **UI Layer** → Displays appointments

## UI States

The application handles the following UI states:

- **Loading**: Shows circular progress indicator while fetching data
- **Success**: Displays appointments list
- **Empty**: Shows appointments screen with no appointments
- **Error**: Shows error message with retry button

## Testing the Integration

### Using Mock API
For testing purposes, you can use a mock API service:

1. Create a mock server (e.g., using Mockoon, Postman Mock Server, or json-server)
2. Update the BASE_URL in `NetworkModule.kt`
3. Configure mock responses matching the expected format

### Sample Mock Response
```json
[
  {
    "id": 1,
    "patient_name": "Ramón Macías García",
    "hour": 8,
    "date": "2024-10-16",
    "doctor_id": 1,
    "status": "scheduled"
  },
  {
    "id": 2,
    "patient_name": "Fabiola Rosales López",
    "hour": 9,
    "date": "2024-10-16",
    "doctor_id": 1,
    "status": "scheduled"
  }
]
```

## Error Handling

The repository wraps API calls in `Result` types:
- **Success**: Contains list of appointments
- **Failure**: Contains exception with error message

Common errors handled:
- Network connectivity issues
- HTTP errors (404, 500, etc.)
- JSON parsing errors
- Timeout errors

## Security Considerations

1. **HTTPS**: Ensure your API uses HTTPS in production
2. **Authentication**: Implement token-based authentication (Bearer token)
3. **SSL Pinning**: Consider implementing SSL pinning for production
4. **ProGuard/R8**: Ensure proper obfuscation rules for Retrofit and Gson

## Next Steps

1. ✅ API service interface created
2. ✅ Repository implementation completed
3. ✅ ViewModel with state management completed
4. ✅ UI integrated with ViewModel
5. ⚠️ **TO DO**: Update API base URL with real endpoint
6. ⚠️ **TO DO**: Implement authentication flow
7. ⚠️ **TO DO**: Store and retrieve doctor ID from session
8. ⚠️ **TO DO**: Add pull-to-refresh functionality
9. ⚠️ **TO DO**: Add authentication token to API requests
10. ⚠️ **TO DO**: Implement offline caching (Room database)

## File Structure

```
app/src/main/java/com/example/nefrovida/
├── MainActivity.kt (Updated with ViewModel integration)
├── MainApplication.kt (Hilt configuration)
├── data/
│   └── remote/
│       ├── AppointmentApiService.kt (NEW)
│       └── dto/
│           └── AppointmentDto.kt (NEW)
├── di/
│   └── NetworkModule.kt (NEW)
├── domain/
│   ├── model/
│   │   ├── Appointment.kt (Existing)
│   │   └── DayItem.kt (Existing)
│   └── repository/
│       └── AppointmentRepository.kt (Updated)
└── presentation/
    ├── screens/
    │   └── AppointmentsScreen.kt (Existing)
    └── viewmodel/
        └── AppointmentsViewModel.kt (NEW)
```

## Build Configuration

Make sure to sync Gradle after all changes:

```bash
./gradlew clean build
```

Or in Android Studio: File → Sync Project with Gradle Files

## Troubleshooting

### Build Errors
If you encounter build errors:
1. Clean and rebuild: `./gradlew clean build`
2. Invalidate caches: File → Invalidate Caches / Restart
3. Ensure all dependencies are synced

### Network Errors
If API calls fail:
1. Check internet permission in AndroidManifest.xml (already added)
2. Verify BASE_URL is correct
3. Check device/emulator has internet connectivity
4. Review Logcat for detailed error messages

### Hilt Errors
If Hilt dependency injection fails:
1. Ensure `@HiltAndroidApp` is on MainApplication
2. Ensure `@AndroidEntryPoint` is on MainActivity
3. Rebuild project after adding Hilt annotations