package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import com.example.nefrovida.domain.model.Appointment

fun AppointmentDto.toAppointment(): Appointment =
    Appointment(
        id = this.id,
        date = this.date,
        doctorFirstName = this.doctor.firstName,
        doctorLastName = this.doctor.lastName,
        specialty = this.doctor.specialty,
        requirements = null,
    )

fun AppointmentDetailDto.toAppointment(): Appointment =
    Appointment(
        id = this.id,
        date = this.date,
        doctorFirstName = this.doctor.firstName,
        doctorLastName = this.doctor.lastName,
        specialty = this.doctor.specialty,
        requirements = this.requirements,
    )
