package com.example.version1.Service;

import com.example.version1.Model.Utilisateur;
import com.example.version1.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Utilisateur createUser(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur getUserByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public Utilisateur updateUser(Long id, Utilisateur updatedUtilisateur) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        utilisateur.setNomComplet(updatedUtilisateur.getNomComplet());
        utilisateur.setEmail(updatedUtilisateur.getEmail());
        utilisateur.setRole(updatedUtilisateur.getRole());

        return utilisateurRepository.save(utilisateur);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void changerRole(Long idUtilisateur, String nouveauRole) {
        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        utilisateur.setRole(nouveauRole);
        utilisateurRepository.save(utilisateur);
    }

    public void deleteUser(Long id) {
        utilisateurRepository.deleteById(id);
    }
}

