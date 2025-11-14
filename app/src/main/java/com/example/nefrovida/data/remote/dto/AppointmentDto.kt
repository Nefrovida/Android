import com.google.gson.annotations.SerializedName

// Enums
enum class AppointmentType {
    @SerializedName("PRESENCIAL")
    PRESENCIAL,

    @SerializedName("VIRTUAL")
    VIRTUAL,
}

enum class AppointmentStatus {
    @SerializedName("PROGRAMMED")
    PROGRAMMED,

    @SerializedName("COMPLETED")
    COMPLETED,

    @SerializedName("CANCELLED")
    CANCELLED,

    @SerializedName("NO_SHOW")
    NO_SHOW,
}

enum class Gender {
    @SerializedName("MALE")
    MALE,

    @SerializedName("FEMALE")
    FEMALE,

    @SerializedName("OTHER")
    OTHER,
}

// Data Classes
data class UserDto(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("parent_last_name")
    val parentLastName: String,
    @SerializedName("maternal_last_name")
    val maternalLastName: String,
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("gender")
    val gender: Gender,
    @SerializedName("registration_date")
    val registrationDate: String,
    @SerializedName("role_id")
    val roleId: Int,
)

data class DoctorDto(
    @SerializedName("doctor_id")
    val doctorId: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("speciality")
    val speciality: String,
    @SerializedName("license")
    val license: String,
    @SerializedName("user")
    val user: UserDto,
)

data class AppointmentDto(
    @SerializedName("appointment_id")
    val appointmentId: Int,
    @SerializedName("doctor_id")
    val doctorId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("general_cost")
    val generalCost: String,
    @SerializedName("community_cost")
    val communityCost: String,
    @SerializedName("doctor")
    val doctor: DoctorDto,
)

data class PatientDto(
    @SerializedName("patient_id")
    val patientId: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("curp")
    val curp: String,
    @SerializedName("user")
    val user: UserDto,
)

data class PatientAppointmentDto(
    @SerializedName("patient_appointment_id")
    val patientAppointmentId: Int,
    @SerializedName("patient_id")
    val patientId: String,
    @SerializedName("appointment_id")
    val appointmentId: Int,
    @SerializedName("date_hour")
    val dateHour: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("appointment_type")
    val appointmentType: AppointmentType,
    @SerializedName("link")
    val link: String?,
    @SerializedName("place")
    val place: String?,
    @SerializedName("appointment_status")
    val appointmentStatus: AppointmentStatus,
    @SerializedName("appointment")
    val appointment: AppointmentDto,
    @SerializedName("patient")
    val patient: PatientDto,
)

// Response wrapper
data class PatientAppointmentResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: List<PatientAppointmentDto>,
)
