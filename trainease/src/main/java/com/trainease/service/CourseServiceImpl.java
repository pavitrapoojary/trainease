package com.trainease.service;

import com.trainease.helper.ExcelParser;
import com.trainease.entity.Course;
import com.trainease.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.trainease.helper.S3FileUploader.uploadFileToS3;

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
        if (courseRepository.findById(course.getCourseId()).isPresent()) {
            throw new IllegalArgumentException("Course already exists with the given course ID.");
        }
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
        List<Course> newCoursesToBeAdded = new ArrayList<>();
        for (Course course : courses) {
            Optional<Course> existingCourse = courseRepository.findById(course.getCourseId());
            if (existingCourse.isEmpty()) {
                newCoursesToBeAdded.add(course);
            }
        }
        return courseRepository.saveAll(newCoursesToBeAdded);
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
        uploadFileToS3(excelFile, "courses/");
        return courseRepository.saveAll(courses);
    }

}
