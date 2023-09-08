package com.trainease.service;

import com.trainease.entity.BatchCourses;
import com.trainease.entity.Course;
import com.trainease.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getCoursesByBatchId(String batchId) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getBatchId().equals(batchId))
                .collect(Collectors.toList());
    }

    @Override
    public List<BatchCourses> getAllBatchDetails() {
        List<Course> allCourses = courseRepository.findAll();
        Map<String, List<Course>> coursesByBatchId = allCourses.stream()
                .collect(Collectors.groupingBy(Course::getBatchId));
        List<BatchCourses> batchCoursesList = new ArrayList<>();
        coursesByBatchId.forEach((batchId, courses) -> {
            BatchCourses batchCourses = new BatchCourses();
            batchCourses.setBatchId(batchId);
            batchCourses.setCourses(courses);
            batchCoursesList.add(batchCourses);
        });
        return batchCoursesList;
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }


}
