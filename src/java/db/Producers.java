/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author pnienhue
 */
public class Producers {
    @Produces
    @PersistenceContext(unitName= "KBSE_Nienhueser_Koschman_Schaefer_OldemeierPU")
    private EntityManager em;
}
