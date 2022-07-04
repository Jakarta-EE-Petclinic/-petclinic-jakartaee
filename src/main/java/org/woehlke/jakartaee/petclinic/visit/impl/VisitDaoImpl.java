package org.woehlke.jakartaee.petclinic.visit.impl;

import jakarta.validation.constraints.NotNull;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.visit.VisitDao;
import org.woehlke.jakartaee.petclinic.visit.Visit;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.PostActivate;
import jakarta.ejb.PrePassivate;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 07.01.14
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
@Log
@Stateless
public class VisitDaoImpl implements VisitDao {

    private static final long serialVersionUID = 892248114140040519L;

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Override
    public List<Visit> getAll() {
        String qlString = "select p from Visit p order by p.date";
        TypedQuery<Visit> q = entityManager.createQuery(qlString, Visit.class);
        List<Visit> list = q.getResultList();
        return list;
    }

    @Override
    public Visit findById(long id) {
        return entityManager.find(Visit.class, id);
    }

    private Visit updateSearchindex(@NotNull Visit visit) {
        String element1[] = visit.getDate().toLocaleString().split("\\W");
        String element2[] = visit.getDescription().split("\\W");
        StringBuilder b = new StringBuilder();
        for(String e: element1){
            b.append(e);
            b.append(" ");
        }
        for(String e: element2) {
            b.append(e);
            b.append(" ");
        }
        visit.setSearchindex(b.toString());
        return visit;
    }

    @Override
    public Visit addNew(@org.jetbrains.annotations.NotNull @NotNull Visit visit) {
        visit.setUuid(UUID.randomUUID());
        visit = updateSearchindex(visit);
        log.info("addNew Visit: " + visit.toString());
        entityManager.persist(visit);
        return visit;
    }

    @Override
    public Visit update(@NotNull Visit visit) {
        visit = updateSearchindex(visit);
        log.info("addNew Visit: " + visit.toString());
        return entityManager.merge(visit);
    }

    @Override
    public void delete(long id) {
        Visit visit = entityManager.find(Visit.class, id);
        visit = updateSearchindex(visit);
        log.info("delete Visit: " + visit.toString());
        entityManager.remove(visit);
    }


    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: "+VisitDaoImpl.class.getSimpleName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy: "+VisitDaoImpl.class.getSimpleName());
    }

    @PrePassivate
    public void prePassivate() {
        log.info("prePassivate: "+VisitDaoImpl.class.getSimpleName());
    }

    @PostActivate
    public void postActivate() {
        log.info("postActivate: "+VisitDaoImpl.class.getSimpleName());
    }
}
