package com.example.version1.Repository;

import com.example.version1.Model.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Long> {

    List<Demande> findByTypeDemandeAndServiceAndStatut(String typeDemande, String service, String enAttente);

     List <Demande>findAll() ;

}
