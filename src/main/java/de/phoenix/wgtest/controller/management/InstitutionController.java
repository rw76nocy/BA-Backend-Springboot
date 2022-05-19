package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.services.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/institutions")
public class InstitutionController {

    @Autowired
    InstitutionService institutionService;

    @GetMapping( value = "/get/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Institution getInstitutionById(@PathVariable Long id) {
        return institutionService.getInstitutionById(id);
    }

    @GetMapping( value = "/get/daycare/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public DayCare getDaycareById(@PathVariable Long id) {
        return institutionService.getDaycareById(id);
    }

    @GetMapping( value = "/get/healthinsurance/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public HealthInsurance getHealthInsuranceById(@PathVariable Long id) {
        return institutionService.getHealthInsuranceById(id);
    }

    @GetMapping( value = "/get/foodsupplier/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public FoodSupplier getFoodsupplierById(@PathVariable Long id) {
        return institutionService.getFoodsupplierById(id);
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Institution> getAllInstitutions() {
        return institutionService.getAllInstitutions();
    }

    @GetMapping("/get/daycare/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<DayCare> getAllDaycares() {
        return institutionService.getAllDaycares();
    }

    @GetMapping("/get/healthinsurance/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<HealthInsurance> getAllHealthinsurances() {
        return institutionService.getAllHealthinsurances();
    }

    @GetMapping("/get/foodsupplier/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<FoodSupplier> getAllFoodsuppliers() {
        return institutionService.getAllFoodsuppliers();
    }

    @GetMapping("/get/driver/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Institution> getAllDrivers() {
        return institutionService.getAllDrivers();
    }
}
