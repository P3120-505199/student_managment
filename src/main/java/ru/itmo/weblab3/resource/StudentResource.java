package ru.itmo.weblab3.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.weblab3.dto.StudentResponseDto;

import java.util.List;

@Path("/students")
public class StudentResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents() {
        StudentResponseDto student = new StudentResponseDto(
                1,
                "Иванов Иван Иванович",
                505115
        );

        List<StudentResponseDto> students = List.of(student);

        return Response.ok(students).build();
    }
}