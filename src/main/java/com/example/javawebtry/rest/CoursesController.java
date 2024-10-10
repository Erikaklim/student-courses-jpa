package com.example.javawebtry.rest;

import com.example.javawebtry.entities.Course;
import com.example.javawebtry.persistence.CoursesDAO;
import com.example.javawebtry.rest.contracts.CourseDTO;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.net.URI;

@Setter
@Getter
@ApplicationScoped
@Path("/courses")
public class CoursesController {
    @Inject
    private CoursesDAO coursesDAO;
    private FacesContext context = FacesContext.getCurrentInstance();

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id){
        Course course = coursesDAO.findOne(id);
        if(course == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setTitle(course.getTitle());

        return Response.ok(courseDTO).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") final Integer id, CourseDTO courseData) {
        try {
            Course existingCourse = coursesDAO.findOne(id);
            if (existingCourse == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted");
            }

            existingCourse.setTitle(courseData.getTitle());
            coursesDAO.update(existingCourse);
            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            System.out.println("Optimistic lock exception occurred");
            return Response.status(Response.Status.CONFLICT).build();
        } catch (Exception e){
            System.out.println("Internal server error occurred");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(CourseDTO courseData){
        String title = courseData.getTitle();

        Course newCourse = new Course();
        newCourse.setTitle(title);

        try{
            coursesDAO.persist(newCourse);
            return Response.status(Response.Status.CREATED).build();
        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
