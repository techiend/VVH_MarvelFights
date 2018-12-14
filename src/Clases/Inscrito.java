/*
 * Contenido creado por los integrantes
 * del Grupo #2, prohibida su reutilizaci�n.
 * 
 *  - Alessandra Varisco
 *  - Carlos Hurtado
 *  - Carlos Verde
 */
package Clases;

public class Inscrito {
    
    public int ID_HPG; 
    public int ID_GA;
    public int ID_Inscrito; // ID del PERSONAJE o PERSONAJE_NO_COMBATE
    public boolean Personaje; // Para saber si es un Personaje (true) o Personaje_no_comabte (false)

    public Inscrito() {
    }

    public Inscrito(int ID_HPG, int ID_GA, int ID_Inscrito, boolean Personaje) {
        this.ID_HPG = ID_HPG;
        this.ID_GA = ID_GA;
        this.ID_Inscrito = ID_Inscrito;
        this.Personaje = Personaje;
    }

    public int getID_HPG() {
        return ID_HPG;
    }

    public void setID_HPG(int ID_HPG) {
        this.ID_HPG = ID_HPG;
    }

    public int getID_GA() {
        return ID_GA;
    }

    public void setID_GA(int ID_GA) {
        this.ID_GA = ID_GA;
    }

    public int getID_Inscrito() {
        return ID_Inscrito;
    }

    public void setID_Inscrito(int ID_Inscrito) {
        this.ID_Inscrito = ID_Inscrito;
    }

    public boolean isPersonaje() {
        return Personaje;
    }

    public void setPersonaje(boolean Personaje) {
        this.Personaje = Personaje;
    }
    
}
