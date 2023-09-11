package com.trainease.service;

import com.trainease.entity.BatchWiseCourses;
import com.trainease.entity.Course;
import com.trainease.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getCoursesByBatchId(String batchId) {
        return courseRepository.findAll()
                .stream()
                .filter(course -> course.getBatchId().equals(batchId))
                .collect(Collectors.toList());
    }

    @Override
    public List<BatchWiseCourses> getAllBatchWiseCourses() {
        List<Course> allCourses = courseRepository.findAll();
        Map<String, List<Course>> coursesByBatchId = allCourses.stream()
                .collect(Collectors.groupingBy(Course::getBatchId));
        List<BatchWiseCourses> batchWiseCoursesList = new ArrayList<>();
        coursesByBatchId.forEach((batchId, courses) -> {
            BatchWiseCourses batchWiseCourses = new BatchWiseCourses();
            batchWiseCourses.setBatchId(batchId);
            batchWiseCourses.setCourses(courses);
            batchWiseCoursesList.add(batchWiseCourses);
        });
        return batchWiseCoursesList;
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public String deleteCourseByCourseId(String courseId) {
        if (courseRepository.findById(courseId).isPresent()) {
            courseRepository.deleteById(courseId);
            return "Course ID : " + courseId + " has been deleted successfully!";
        }
        return "Course ID : " + courseId + " does not exist.";
    }


}
