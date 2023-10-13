package com.trainease.helper;

import com.trainease.entity.Batch;
import com.trainease.entity.Course;
import com.trainease.entity.User;
import com.trainease.dto.UserRole;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelParser {
    public static List<Course> parseCourseExcel(InputStream inputStream) throws IOException {
        List<Course> courses = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() != 0) {
                    Cell cell = row.getCell(0);
                    if (cell == null || cell.getCellType() == CellType.BLANK) {
                        break;
                    }
                    Course course = new Course();
                    Batch batch = Batch.builder().batchId(row.getCell(0).getStringCellValue()).build();
                    course.setBatch(batch);
                    course.setCourseId(row.getCell(1).getStringCellValue());
                    course.setCourseName(row.getCell(2).getStringCellValue());
                    course.setDescription(row.getCell(3).getStringCellValue());
                    course.setLink(row.getCell(4).getStringCellValue());
                    course.setDurationInHours((double) row.getCell(5).getNumericCellValue());
                    course.setEstimatedStartDate(row.getCell(6).getDateCellValue());
                    course.setEstimatedEndDate(row.getCell(7).getDateCellValue());
                    course.setSubjectMatterExpert(row.getCell(8).getStringCellValue());
                    courses.add(course);
                }
            }
        }

        return courses;
    }

    public static List<User> parseUserExcel(InputStream inputStream) throws IOException {
        List<User> users = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() != 0) {
                    Cell cell = row.getCell(0);
                    if (cell == null || cell.getCellType() == CellType.BLANK) {
                        break;
                    }
                    User user = new User();
                    user.setEmailId(row.getCell(0).getStringCellValue());
                    user.setName(row.getCell(1).getStringCellValue());
                    user.setRole(UserRole.valueOf(row.getCell(2).getStringCellValue()));
                    Batch batch = Batch.builder().batchId(row.getCell(3).getStringCellValue()).build();
                    user.setBatch(batch);
                    users.add(user);
                }
            }
        }
        return users;
    }

}
