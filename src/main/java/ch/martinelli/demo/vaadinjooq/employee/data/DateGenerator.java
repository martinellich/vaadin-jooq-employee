package ch.martinelli.demo.vaadinjooq.employee.data;

import ch.martinelli.demo.vaadinjooq.employee.model.Sequences;
import ch.martinelli.demo.vaadinjooq.employee.model.tables.Department;
import ch.martinelli.demo.vaadinjooq.employee.model.tables.Employee;
import ch.martinelli.demo.vaadinjooq.employee.model.tables.records.EmployeeRecord;
import net.datafaker.Faker;
import org.jooq.DSLContext;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DateGenerator implements ApplicationRunner {

    private final DSLContext dslContext;

    public DateGenerator(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        var itDepartment = dslContext.newRecord(Department.DEPARTMENT);
        itDepartment.setId(dslContext.nextval(Sequences.DEPARTMENT_SEQ).intValue());
        itDepartment.setName("Information Technology");
        itDepartment.store();

        var hrDepartment = dslContext.newRecord(Department.DEPARTMENT);
        hrDepartment.setId(dslContext.nextval(Sequences.DEPARTMENT_SEQ).intValue());
        hrDepartment.setName("Human Resources");
        hrDepartment.store();

        var faker = new Faker();
        for (int i = 0; i < 100; i++) {
            EmployeeRecord employeeRecord = dslContext.newRecord(Employee.EMPLOYEE);
            employeeRecord.setId(dslContext.nextval(Sequences.EMPLOYEE_SEQ).intValue());
            employeeRecord.setFirstName(faker.name().firstName());
            employeeRecord.setLastName(faker.name().lastName());
            if (i % 7 == 0) {
                employeeRecord.setDepartmentId(hrDepartment.getId());
            } else {
                employeeRecord.setDepartmentId(itDepartment.getId());
            }
            employeeRecord.store();
        }
    }
}
