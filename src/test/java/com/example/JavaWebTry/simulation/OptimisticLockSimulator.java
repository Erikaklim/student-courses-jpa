package com.example.JavaWebTry.simulation;

import com.example.javawebtry.entities.Course;

import javax.persistence.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class OptimisticLockSimulator {

    private static final Logger logger = Logger.getLogger(OptimisticLockSimulator.class.getName());
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("JavaWebPersistenceUnit");

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> updateCourseSimulatingOptimisticLock(1, "New Title 1", "New Professor 1"));
        executor.submit(() -> updateCourseSimulatingOptimisticLock(1, "New Title 2", "New Professor 2"));

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        emf.close();
    }

    public static void updateCourseSimulatingOptimisticLock(Integer courseId, String newTitle, String newProfessor) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Fetch the course
            Course course = em.find(Course.class, courseId);
            logger.info(Thread.currentThread().getName() + " - Fetched Course: " + course);

            // Simulate some processing time
            Thread.sleep(3000);

            // Update the course
            course.setTitle(newTitle);

            // Merge the changes
            em.merge(course);

            // Commit the transaction
            tx.commit();

            logger.info(Thread.currentThread().getName() + " - Updated Course: " + course);

        } catch (OptimisticLockException ole) {
            logger.severe(Thread.currentThread().getName() + " - OptimisticLockException occurred: " + ole.getMessage());
            if (tx.isActive()) {
                tx.rollback();
            }
        } catch (Exception e) {
            logger.severe(Thread.currentThread().getName() + " - Exception occurred: " + e.getMessage());
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }
}
