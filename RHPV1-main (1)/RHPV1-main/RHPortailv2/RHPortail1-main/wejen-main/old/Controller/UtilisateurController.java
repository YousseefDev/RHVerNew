package com.example.version1.Controller;

import com.example.version1.Model.Utilisateur;
import com.example.version1.Service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/register")
    public ResponseEntity<Utilisateur> registerUser(@RequestBody Utilisateur utilisateur) {
        Utilisateur createdUser = utilisateurService.createUser(utilisateur);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @PutMapping("/{idUtilisateur}/changer-role")
    public ResponseEntity<String> changerRoleUtilisateur(@PathVariable Long idUtilisateur, @RequestParam String nouveauRole) {
        utilisateurService.changerRole(idUtilisateur, nouveauRole);
        return new ResponseEntity<>("Le rôle de l'utilisateur a été modifié avec succès", HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Utilisateur> getUserByEmail(@PathVariable String email) {
        Utilisateur utilisateur = utilisateurService.getUserByEmail(email);
        return new ResponseEntity<>(utilisateur, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUser(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        Utilisateur updatedUser = utilisateurService.updateUser(id, utilisateur);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        utilisateurService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

