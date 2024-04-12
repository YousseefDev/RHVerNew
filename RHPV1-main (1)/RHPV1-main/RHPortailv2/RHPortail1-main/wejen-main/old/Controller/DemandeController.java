package com.example.version1.Controller;

import com.example.version1.Model.Demande;
import com.example.version1.Repository.DemandeRepository;
import com.example.version1.Service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
public class DemandeController {
    @Autowired
    private DemandeService demandeService;

    @GetMapping
    public ResponseEntity<List<Demande>> getAllDemandes() {
        List<Demande> demandes = demandeService.getAllDemandes();
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Demande> createDemande(@RequestBody Demande demande) {
        Demande createdDemande = demandeService.createDemande(demande);
        return new ResponseEntity<>(createdDemande, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Demande> getDemandeById(@PathVariable Long id) {
        Demande demande = demandeService.getDemandeById(id);
        return new ResponseEntity<>(demande, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Demande> updateDemande(@PathVariable Long id, @RequestBody Demande demande) {
        Demande updatedDemande = demandeService.updateDemande(id, demande);
        return new ResponseEntity<>(updatedDemande, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemande(@PathVariable Long id) {
        demandeService.deleteDemande(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/autorisations")
    public ResponseEntity<List<Demande>> getDemandesAutorisationsChefHierarchique() {
        // Appel à une méthode du service pour récupérer les demandes d'autorisations gérées par le chef hiérarchique
        List<Demande> demandes = demandeService.getDemandesAutorisationsChefHierarchique();
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @GetMapping("/conges")
    public ResponseEntity<List<Demande>> getDemandesCongesChefHierarchique() {
        // Appel à une méthode du service pour récupérer les demandes de congés gérées par le chef hiérarchique
        List<Demande> demandes = demandeService.getDemandesCongesChefHierarchique();
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @GetMapping("/mutations")
    public ResponseEntity<List<Demande>> getDemandesMutationsChefHierarchique() {
        // Appel à une méthode du service pour récupérer les demandes de mutations gérées par le chef hiérarchique
        List<Demande> demandes = demandeService.getDemandesMutationsChefHierarchique();
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    // Autres endpoints pour gérer les demandes



}



