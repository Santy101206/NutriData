package com.santy.nutridata.Model;

public class FormulaMedica {
    private int id;
    private String categoria;
    private String nombreEnfermedad;
    private String medicamentos;
    private String dosis;
    private String duracion;
    private String recomendaciones;
    private String dieta;
    private String hidratacion;
    private String contraindicaciones;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getNombreEnfermedad() { return nombreEnfermedad; }
    public void setNombreEnfermedad(String nombreEnfermedad) { this.nombreEnfermedad = nombreEnfermedad; }

    public String getMedicamentos() { return medicamentos; }
    public void setMedicamentos(String medicamentos) { this.medicamentos = medicamentos; }

    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }

    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }

    public String getRecomendaciones() { return recomendaciones; }
    public void setRecomendaciones(String recomendaciones) { this.recomendaciones = recomendaciones; }

    public String getDieta() { return dieta; }
    public void setDieta(String dieta) { this.dieta = dieta; }

    public String getHidratacion() { return hidratacion; }
    public void setHidratacion(String hidratacion) { this.hidratacion = hidratacion; }

    public String getContraindicaciones() { return contraindicaciones; }
    public void setContraindicaciones(String contraindicaciones) { this.contraindicaciones = contraindicaciones; }
}