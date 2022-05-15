package de.phoenix.wgtest.controller.management;

import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.repository.management.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/institutions")
public class InstitutionController {

    @Autowired
    InstitutionRepository institutionRepository;

    @Autowired
    DayCareRepository dayCareRepository;

    @Autowired
    HealthInsuranceRepository healthInsuranceRepository;

    @Autowired
    FoodSupplierRepository foodSupplierRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping( value = "/get/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Institution getInstitutionById(@PathVariable Long id) {
        Institution i = null;
        if (institutionRepository.findById(id).isPresent()) {
            i = institutionRepository.findById(id).get();
        }
        return i;
    }

    @GetMapping( value = "/get/daycare/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public DayCare getDaycareById(@PathVariable Long id) {
        DayCare d = null;
        if (dayCareRepository.findById(id).isPresent()) {
            d = dayCareRepository.findById(id).get();
        }
        return d;
    }

    @GetMapping( value = "/get/healthinsurance/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public HealthInsurance getHealthInsuranceById(@PathVariable Long id) {
        HealthInsurance h = null;
        if (healthInsuranceRepository.findById(id).isPresent()) {
            h = healthInsuranceRepository.findById(id).get();
        }
        return h;
    }

    @GetMapping( value = "/get/foodsupplier/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public FoodSupplier getFoodsupplierById(@PathVariable Long id) {
        FoodSupplier f = null;
        if (foodSupplierRepository.findById(id).isPresent()) {
            f = foodSupplierRepository.findById(id).get();
        }
        return f;
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }

    @GetMapping("/get/daycare/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<DayCare> getAllDaycares() {
        return dayCareRepository.findAll();
    }

    @GetMapping("/get/healthinsurance/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<HealthInsurance> getAllHealthinsurances() {
        return healthInsuranceRepository.findAll();
    }

    @GetMapping("/get/foodsupplier/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<FoodSupplier> getAllFoodsuppliers() {
        return foodSupplierRepository.findAll();
    }

    @GetMapping("/get/driver/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Institution> getAllDrivers() {
        List<Institution> all = new ArrayList<>();
        Optional<Role> optionalRole = roleRepository.findByTypeAndSpecification(ERole.DRIVER, null);

        if (optionalRole.isPresent()) {
            List<InstitutionRole> institutionRoles = optionalRole.get().getInstitutionRoles();
            for (InstitutionRole ir : institutionRoles) {
                Institution i = ir.getInstitution();
                if (!all.contains(i)) {
                    all.add(i);
                }
            }
        }

        return all;
    }
}
