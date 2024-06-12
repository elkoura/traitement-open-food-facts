package service;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
public class Marque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;

    public Marque() {}

    public Marque(String libelle) {
        this.libelle = libelle;
    }

    // Getters and Setters

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
        Marque marque = (Marque) o;
        return Objects.equals(id, marque.id) && Objects.equals(libelle, marque.libelle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle);
    }

    public static Optional<Marque> findByLibelle(EntityManager em, String libelle) {
        try {
            return Optional.of(em.createQuery("SELECT m FROM Marque m WHERE m.libelle = :libelle", Marque.class)
                    .setParameter("libelle", libelle)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public static Marque save(EntityManager em, Marque marque) {
        em.getTransaction().begin();
        em.persist(marque);
        em.getTransaction().commit();
        return marque;
    }
}
