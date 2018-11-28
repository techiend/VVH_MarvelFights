/*
 * Contenido creado por los integrantes
 * del Grupo #2, prohibida su reutilizaci�n.
 * 
 *  - Alessandra Varisco
 *  - Carlos Hurtado
 *  - Carlos Verde
 */
package Clases;

import java.util.ArrayList;

public class Evento {
    public String nombre;
    public String fechaInicio;
    public String fechaFin;
    public String descripccion = "";
    public ArrayList<Inscrito> inscritos;

    public Evento() {
        this.inscritos = new ArrayList();
    }

    public Evento(String nombre, String fechaInicio) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.inscritos = new ArrayList();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getDescripccion() {
        return descripccion;
    }

    public void setDescripccion(String descripccion) {
        this.descripccion = descripccion;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public ArrayList<Inscrito> getInscritos() {
        return inscritos;
    }

    public Inscrito getInscrito(int i) {
        return inscritos.get(i);
    }

    public void setInscritos(Inscrito inscrito) {
        this.inscritos.add(inscrito);
    }
    
    
}
