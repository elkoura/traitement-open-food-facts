package service;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;

    public Categorie() {}

    public Categorie(String libelle) {
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categorie that = (Categorie) o;
        return Objects.equals(id, that.id) && Objects.equals(libelle, that.libelle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle);
    }

    public static Categorie save(EntityManager em, Categorie categorie) {
        em.getTransaction().begin();
        em.persist(categorie);
        em.getTransaction().commit();
        return categorie;
    }

    // Implémentation de findByLibelle
    public static Optional<Categorie> findByLibelle(EntityManager em, String libelle) {
        try {
            TypedQuery<Categorie> query = em.createQuery("SELECT c FROM Categorie c WHERE c.libelle = :libelle", Categorie.class);
            query.setParameter("libelle", libelle);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
