package com.example.javawebtry.rest;

import com.example.javawebtry.entities.Course;
import com.example.javawebtry.persistence.CoursesDAO;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.RequestContextController;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Setter
@Getter
@ApplicationScoped
@Path("/simulate")
public class OptimisticLockSimulatorResource {

    @Inject
    private CoursesDAO coursesDAO;

    @Resource
    private ManagedExecutorService managedExecutorService;

    @Inject
    private RequestContextController requestContextController;

    private static final Logger logger = Logger.getLogger(OptimisticLockSimulatorResource.class.getName());

    @GET
    @Path("/optimistic-lock")
    public Response runSimulation() throws InterruptedException {
        managedExecutorService.submit(() -> {
            try {
                runWithRequestContext(() -> {
                    try {
                        updateCourseSimulatingOptimisticLock(30, "New Title 1", "New Professor 1");
                    } catch (OptimisticLockException ole) {
                        logger.severe("OptimisticLockException in thread 1: " + ole.getMessage());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (Exception e) {
                logger.severe("Exception in thread 1: " + e.getMessage());
            }
        });
        managedExecutorService.submit(() -> {
            try {
                runWithRequestContext(() -> {
                    try {
                        updateCourseSimulatingOptimisticLock(30, "New Title 2", "New Professor 2");
                    } catch (OptimisticLockException ole) {
                        logger.severe("OptimisticLockException in thread 2: " + ole.getMessage());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (Exception e) {
                logger.severe("Exception in thread 2: " + e.getMessage());
            }
        });

        TimeUnit.SECONDS.sleep(5);

        return Response.ok("Simulation completed").build();
    }

    private void runWithRequestContext(Runnable task) {
        requestContextController.activate();
        try {
            task.run();
        } finally {
            requestContextController.deactivate();
        }
    }

    @Transactional
    public void updateCourseSimulatingOptimisticLock(Integer courseId, String newTitle, String newProfessor) throws InterruptedException {
        try {
            Course course = coursesDAO.findOne(courseId);
            logger.info(Thread.currentThread().getName() + " - Fetched Course: " + course);

            Thread.sleep(2000);

            course.setTitle(newTitle);

            coursesDAO.update(course);

            logger.info(Thread.currentThread().getName() + " - Updated Course: " + course);

        } catch (OptimisticLockException ole) {
            logger.severe(Thread.currentThread().getName() + " - OptimisticLockException occurred: " + ole.getMessage());
            throw ole;
        } catch (Exception e) {
            logger.severe(Thread.currentThread().getName() + " - Exception occurred: " + e.getMessage());
            throw e;
        }
    }
}

