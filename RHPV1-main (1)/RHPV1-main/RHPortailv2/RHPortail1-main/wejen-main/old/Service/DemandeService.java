package com.example.version1.Service;

import com.example.version1.Model.Demande;
import com.example.version1.Repository.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DemandeService {
    @Autowired
    private DemandeRepository demandeRepository;

    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    public Demande createDemande(Demande demande) {
        return demandeRepository.save(demande);
    }

    public Demande getDemandeById(Long id) {
        return demandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));
    }

    public Demande updateDemande(Long id, Demande updatedDemande) {
        Demande demande = getDemandeById(id);
        demande.setTitre(updatedDemande.getTitre());
        demande.setDescription(updatedDemande.getDescription());
        demande.setValidee(updatedDemande.isValidee());
        return demandeRepository.save(demande);
    }

    public void deleteDemande(Long id) {
        demandeRepository.deleteById(id);
    }



    public List<Demande> getDemandesAutorisationsChefHierarchique() {
        return demandeRepository.findByTypeDemandeAndServiceAndStatut("AUTORISATION", "ServiceXYZ", "EN_ATTENTE");
    }

    public List<Demande> getDemandesCongesChefHierarchique() {
        return demandeRepository.findByTypeDemandeAndServiceAndStatut("CONGE", "ServiceXYZ", "EN_ATTENTE");
    }

    public List<Demande> getDemandesMutationsChefHierarchique() {
        return demandeRepository.findByTypeDemandeAndServiceAndStatut("MUTATION", "ServiceXYZ", "EN_ATTENTE");
    }


}
