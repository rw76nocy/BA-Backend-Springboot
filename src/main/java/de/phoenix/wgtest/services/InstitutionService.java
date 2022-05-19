package de.phoenix.wgtest.services;

import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.repository.management.*;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionService {

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

    public Institution getInstitutionById(Long id) {
        return institutionRepository.findById(id).orElse(null);
    }

    public DayCare getDaycareById(Long id) {
        return dayCareRepository.findById(id).orElse(null);
    }

    public HealthInsurance getHealthInsuranceById(Long id) {
        return healthInsuranceRepository.findById(id).orElse(null);
    }

    public FoodSupplier getFoodsupplierById(Long id) {
        return foodSupplierRepository.findById(id).orElse(null);
    }

    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }

    public List<DayCare> getAllDaycares() {
        return dayCareRepository.findAll();
    }

    public List<HealthInsurance> getAllHealthinsurances() {
        return healthInsuranceRepository.findAll();
    }

    public List<FoodSupplier> getAllFoodsuppliers() {
        return foodSupplierRepository.findAll();
    }

    public List<Institution> getAllDrivers() {
        Role role = roleRepository.findByTypeAndSpecification(ERole.DRIVER, null).orElse(null);
        if (role == null) {
            return List.of();
        }
        return StreamEx.of(role.getInstitutionRoles())
                .map(InstitutionRole::getInstitution)
                .distinct()
                .toList();
    }
}
