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
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

public class EventoC{
    public String nombre;
    public Date fechaInicio;
    public String fechaFin;
    public String descripccion = "";
    public JSONArray inscritos;
    public int numGroups = 0;

    public EventoC() {
        this.inscritos = new JSONArray();
    }

    public EventoC(String nombre, Date fechaInicio) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.inscritos = new JSONArray();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
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

    public JSONArray getInscritos() {
        return inscritos;
    }

    public void setInscritos(JSONArray inscritos){
        this.inscritos = inscritos;
        this.numGroups = this.inscritos.length() / 3;
        System.out.println("Numero total de grupos posibles: "+this.numGroups);
    }
    
}
