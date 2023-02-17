package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.model.management.Person;
import de.phoenix.wgtest.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('MANAGEMENT') or hasRole('ADMIN')")
    public List<Person> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping( value = "/get/employee/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('MANAGEMENT') or hasRole('ADMIN')")
    public Person getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping( value = "/get/supervisor/all/{livingGroup}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('MANAGEMENT') or hasRole('ADMIN')")
    public List<Person> getAllEmployeesByLivingGroup(@PathVariable String livingGroup) {
        return employeeService.getAllEmployeesByLivingGroup(livingGroup);
    }

    @GetMapping( value = "/get/all/{livingGroup}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('MANAGEMENT') or hasRole('ADMIN')")
    public List<Person> getEmployeesByLivingGroup(@PathVariable String livingGroup) {
        return employeeService.getEmployeesByLivingGroup(livingGroup);
    }

    @GetMapping( value = "/get/{livingGroup}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('MANAGEMENT') or hasRole('ADMIN')")
    public List<Person> getEmployeesByLivingGroupWithoutAccount(@PathVariable String livingGroup) {
        return employeeService.getEmployeesByLivingGroupWithoutAccount(livingGroup);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('MANAGEMENT') or hasRole('ADMIN')")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody Person person) {
        return employeeService.addEmployee(person);
    }

    @PutMapping(value = "/put/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('MANAGEMENT') or hasRole('ADMIN')")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody Person person) {
        return employeeService.updateEmployee(id, person);
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('MANAGEMENT') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }
}
