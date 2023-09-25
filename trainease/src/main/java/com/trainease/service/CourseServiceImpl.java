package com.trainease.service;

import com.trainease.ExcelParser;
import com.trainease.entity.Course;
import com.trainease.entity.User;
import com.trainease.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getCoursesByBatchId(String batchId) {
        return courseRepository.findAll()
                .stream()
                .filter(course -> course.getBatch().getBatchId().equals(batchId))
                .collect(Collectors.toList());
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Course updatedCourse) {
        if (updatedCourse == null || updatedCourse.getCourseId() == null) {
            throw new IllegalArgumentException("Invalid course data or course ID.");
        }
        Optional<Course> existingCourseOptional = courseRepository.findById(updatedCourse.getCourseId());
        if (existingCourseOptional.isPresent()) {
            Course existingCourse = existingCourseOptional.get();
            existingCourse.setCourseName(updatedCourse.getCourseName());
            existingCourse.setDescription(updatedCourse.getDescription());
            existingCourse.setLink(updatedCourse.getLink());
            existingCourse.setDurationInHours(updatedCourse.getDurationInHours());
            existingCourse.setEstimatedStartDate(updatedCourse.getEstimatedStartDate());
            existingCourse.setEstimatedEndDate(updatedCourse.getEstimatedEndDate());
            existingCourse.setSubjectMatterExpert(updatedCourse.getSubjectMatterExpert());
            return courseRepository.save(existingCourse);
        }
        throw new EntityNotFoundException("Course not found.");
    }

    @Override
    public List<Course> createCourses(List<Course> courses) {
        return courseRepository.saveAll(courses);
    }


    @Override
    public String deleteCourseByCourseId(String courseId) {
        if (courseRepository.findById(courseId).isPresent()) {
            courseRepository.deleteById(courseId);
            return "Course ID : " + courseId + " has been deleted successfully!";
        }
        return "Course ID : " + courseId + " does not exist.";
    }

    @Override
    public List<Course> saveCoursesFromExcel(MultipartFile excelFile) throws IOException {
        List<Course> courses = ExcelParser.parseCourseExcel(excelFile.getInputStream());
        courseRepository.saveAll(courses);
        return courses;
    }

}
