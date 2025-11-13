package com.santy.nutridata.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionHelper extends SQLiteOpenHelper {

    public ConexionHelper(Context context) {
        super(context, Constantes.DB_NAME, null, Constantes.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constantes.CREAR_TABLA_USUARIOS);
        db.execSQL(Constantes.CREAR_TABLA_DATOS);
        db.execSQL(Constantes.CREAR_TABLA_FORMULAS_MEDICAS);
        insertarDatosIniciales(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 7) {
            db.execSQL("ALTER TABLE " + Constantes.TABLA_DATOS + " ADD COLUMN " + Constantes.CAMPO_TRATAMIENTO + " TEXT");
        }
        db.execSQL("DROP TABLE IF EXISTS " + Constantes.TABLA_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + Constantes.TABLA_DATOS);
        db.execSQL("DROP TABLE IF EXISTS " + Constantes.TABLA_FORMULAS_MEDICAS);
        onCreate(db);
    }

    private void insertarDatosIniciales(SQLiteDatabase db) {
        // Enfermedades Infecciosas
        insertarEnfermedad(db, "Infecciosas", "Gripe Común",
                "Acetaminofén 500mg\nIbuprofeno 400mg\nLoratadina 10mg",
                "Acetaminofén: 1 tableta cada 8 horas\nIbuprofeno: 1 tableta cada 12 horas\nLoratadina: 1 tableta cada 24 horas",
                "5-7 días",
                "Reposo, hidratación abundante, alimentación ligera, evitar cambios bruscos de temperatura",
                "Líquidos calientes, sopas, frutas ricas en vitamina C (naranja, limón, kiwi)",
                "2-3 litros de agua al día",
                "Evitar automedicación con antibióticos");

        insertarEnfermedad(db, "Infecciosas", "Amigdalitis Aguda",
                "Amoxicilina 500mg\nAcetaminofén 500mg\nIbuprofeno 400mg",
                "Amoxicilina: 1 tableta cada 8 horas\nAcetaminofén: 1 tableta cada 8 horas\nIbuprofeno: 1 tableta cada 12 horas",
                "7-10 días",
                "Gárgaras con agua salada, reposo vocal, humidificador ambiental",
                "Dieta blanda, evitar alimentos muy calientes o fríos, sopas y purés",
                "2-2.5 litros de agua al día",
                "Completar tratamiento antibiótico, consultar si fiebre persiste");

        insertarEnfermedad(db, "Infecciosas", "Infección Urinaria",
                "Nitrofurantoína 100mg\nAnalgésico urinario",
                "Nitrofurantoína: 1 cápsula cada 12 horas\nAnalgésico: 1 tableta cada 8 horas según dolor",
                "5-7 días",
                "Aumentar ingesta de líquidos, orinar frecuentemente, higiene adecuada",
                "Jugo de arándano, evitar cafeína y alcohol",
                "2.5-3 litros de agua al día",
                "Consultar si síntomas persisten después de 48 horas");

        insertarEnfermedad(db, "Infecciosas", "Bronquitis Aguda",
                "Amoxicilina 500mg\nSalbutamol inhalador\nAcetaminofén 500mg",
                "Amoxicilina: 1 tableta cada 8 horas\nSalbutamol: 2 inhalaciones cada 6 horas\nAcetaminofén: 1 tableta cada 8 horas",
                "7-10 días",
                "Reposo, humidificador, evitar humo y contaminantes",
                "Líquidos calientes, miel con limón, sopas nutritivas",
                "2-3 litros de agua al día",
                "Acudir a urgencias si dificultad respiratoria severa");

        // Enfermedades Cardiovasculares
        insertarEnfermedad(db, "Cardiovasculares", "Hipertensión Arterial",
                "Losartán 50mg\nHidroclorotiazida 25mg",
                "Losartán: 1 tableta cada 24 horas\nHidroclorotiazida: 1 tableta cada 24 horas",
                "Tratamiento crónico",
                "Control de presión semanal, ejercicio regular 30 min/día, control de peso",
                "Baja en sal, rica en potasio, evitar embutidos y enlatados",
                "1.5-2 litros de agua al día",
                "Evitar consumo de alcohol y tabaco, no suspender medicación");

        insertarEnfermedad(db, "Cardiovasculares", "Dislipidemia (Colesterol Alto)",
                "Atorvastatina 20mg\nÁcido Acetilsalicílico 100mg",
                "Atorvastatina: 1 tableta en la noche\nAAS: 1 tableta cada 24 horas",
                "Tratamiento crónico",
                "Ejercicio aeróbico 150 min/semana, control de peso",
                "Dieta baja en grasas saturadas, aumentar fibra, pescado azul 2 veces/semana",
                "2 litros de agua al día",
                "Evitar grasas trans, controlar perfil lipídico cada 6 meses");

        // Enfermedades Metabólicas
        insertarEnfermedad(db, "Metabólicas", "Diabetes Mellitus Tipo 2",
                "Metformina 850mg\nGlibenclamida 5mg",
                "Metformina: 1 tableta con el desayuno y cena\nGlibenclamida: 1 tableta con el desayuno",
                "Tratamiento crónico",
                "Monitoreo de glucosa en ayunas, examen de hemoglobina glicosilada cada 3 meses, cuidado de pies",
                "Distribuir carbohidratos en 5 comidas, evitar azúcares simples, fibra soluble",
                "2-2.5 litros de agua al día",
                "Evitar ayunos prolongados, monitorear hipoglucemias");

        insertarEnfermedad(db, "Metabólicas", "Anemia Ferropénica",
                "Sulfato ferroso 325mg\nÁcido fólico 1mg\nVitamina C 500mg",
                "Sulfato ferroso: 1 tableta cada 24 horas\nÁcido fólico: 1 tableta cada 24 horas\nVitamina C: 1 tableta cada 24 horas",
                "3-6 meses",
                "Tomar hierro en ayunas, evitar lácteos 2 horas antes/después, control hematológico",
                "Alimentos ricos en hierro: carnes rojas, espinacas, lentejas, frutos secos",
                "2 litros de agua al día",
                "Puede causar estreñimiento, heces oscuras, evitar té/café con comidas");

        insertarEnfermedad(db, "Metabólicas", "Hipotiroidismo",
                "Levotiroxina 50 mcg",
                "Levotiroxina: 1 tableta en ayunas 30 min antes del desayuno",
                "Tratamiento crónico",
                "Control TSH cada 6-12 meses, tomar medicación siempre a la misma hora",
                "Dieta balanceada, evitar exceso de yodo, alimentos bociógenos en moderación",
                "1.5-2 litros de agua al día",
                "No tomar con otros medicamentos, esperar 4 horas para suplementos de calcio/hierro");

        // Enfermedades Digestivas
        insertarEnfermedad(db, "Digestivas", "Gastritis Aguda",
                "Omeprazol 20mg\nDomperidona 10mg",
                "Omeprazol: 1 tableta 30 min antes del desayuno\nDomperidona: 1 tableta 15 min antes de comidas",
                "4-8 semanas",
                "Comer porciones pequeñas, evitar acostarse después de comer, manejar estrés",
                "Evitar picantes, café, alcohol, alimentos grasos, comidas irritantes",
                "1.5-2 litros de agua al día",
                "Evitar AINEs como ibuprofeno, aspirina");

        insertarEnfermedad(db, "Digestivas", "Diarrea Aguda",
                "Loperamida 2mg\nSolución rehidratante oral\nProbióticos",
                "Loperamida: 2 tabletas inicial, luego 1 después de cada deposición líquida\nSRO: 200ml después de cada deposición\nProbióticos: 1 cápsula cada 24 horas",
                "3-5 días",
                "Reposo intestinal las primeras 4-6 horas, higiene de manos frecuente",
                "Dieta astringente: arroz, manzana, plátano, pan tostado, zanahoria",
                "SRO + 2-3 litros de líquidos al día",
                "Si fiebre >38°C o sangre en heces, acudir a urgencias");

        insertarEnfermedad(db, "Digestivas", "Estreñimiento Crónico",
                "Lactulosa 15ml\nPsyllium 5g",
                "Lactulosa: 15ml cada 24 horas\nPsyllium: 1 sobre disuelto en agua 2 veces al día",
                "4-8 semanas",
                "Establecer rutina intestinal, ejercicio regular, no posponer defecación",
                "Alta en fibra: frutas, verduras, cereales integrales, legumbres",
                "2.5-3 litros de agua al día",
                "Aumentar fibra gradualmente, puede causar flatulencia inicial");

        // Enfermedades Respiratorias
        insertarEnfermedad(db, "Respiratorias", "Asma Bronquial",
                "Salbutamol inhalador\nBudesonida inhalador",
                "Salbutamol: 1-2 inhalaciones cada 4-6 horas según necesidad\nBudesonida: 2 inhalaciones cada 12 horas",
                "Tratamiento crónico",
                "Evitar desencadenantes, usar inhalador correctamente, plan de acción para crisis",
                "Dieta balanceada, mantener peso ideal, alimentos antiinflamatorios",
                "2 litros de agua al día",
                "Acudir a urgencias si dificultad respiratoria severa");

        insertarEnfermedad(db, "Respiratorias", "Rinitis Alérgica",
                "Loratadina 10mg\nFluticasona nasal",
                "Loratadina: 1 tableta cada 24 horas\nFluticasona: 2 aplicaciones en cada fosa nasal cada 24 horas",
                "Durante temporada alérgica",
                "Evitar alérgenos, uso de mascarilla, limpieza ambiental",
                "Alimentos con quercetina: manzanas, cebollas, té verde",
                "2 litros de agua al día",
                "Consultar si síntomas no mejoran en 1 semana");

        // Enfermedades Neurológicas
        insertarEnfermedad(db, "Neurológicas", "Migraña Crónica",
                "Sumatriptán 50mg\nIbuprofeno 400mg\nPropranolol 40mg",
                "Sumatriptán: 1 tableta al inicio del dolor\nIbuprofeno: 1 tableta cada 8 horas si dolor persiste\nPropranolol: 1 tableta cada 12 horas",
                "Sumatriptán: según episodios\nPropranolol: tratamiento continuo",
                "Reposo en lugar oscuro y silencioso, identificar desencadenantes, técnicas de relajación",
                "Evitar chocolates, quesos maduros, vino tinto, edulcorantes artificiales",
                "2 litros de agua al día",
                "No usar más de 2 días por semana para evitar cefalea por rebote");

        insertarEnfermedad(db, "Neurológicas", "Insomnio Transitorio",
                "Melatonina 3mg\nValeriana 500mg",
                "Melatonina: 1 tableta 30 min antes de dormir\nValeriana: 1 tableta 30 min antes de dormir",
                "2-4 semanas",
                "Higiene del sueño: horario regular, ambiente oscuro y silencioso, evitar pantallas antes de dormir",
                "Cena ligera 2-3 horas antes de dormir, evitar cafeína después de las 3pm",
                "1.5 litros de agua al día",
                "Evitar uso prolongado, consultar si insomnio persiste");

        // Enfermedades Musculoesqueléticas
        insertarEnfermedad(db, "Musculoesqueléticas", "Osteoartritis",
                "Acetaminofén 500mg\nGlucosamina 1500mg\nCondroitín 1200mg",
                "Acetaminofén: 1-2 tabletas cada 8 horas\nGlucosamina: 1 tableta cada 24 horas\nCondroitín: 1 tableta cada 24 horas",
                "Acetaminofén: según dolor\nGlucosamina/Condroitín: 3-6 meses",
                "Ejercicio de bajo impacto, control de peso, fisioterapia",
                "Alimentos antiinflamatorios: pescado azul, frutos secos, aceite de oliva",
                "2 litros de agua al día",
                "Evitar sobrecarga articular, usar calzado adecuado");

        insertarEnfermedad(db, "Musculoesqueléticas", "Lumbalgia Aguda",
                "Ibuprofeno 400mg\nRelajante muscular",
                "Ibuprofeno: 1 tableta cada 8 horas\nRelajante muscular: 1 tableta cada 12 horas",
                "5-7 días",
                "Reposo relativo 48 horas, aplicación de calor local, ejercicios de fortalecimiento progresivo",
                "Alimentos antiinflamatorios, adecuada ingesta de calcio y vitamina D",
                "2 litros de agua al día",
                "Evitar reposo prolongado, consultar si dolor se irradia a piernas");

        // Enfermedades Dermatológicas
        insertarEnfermedad(db, "Dermatológicas", "Dermatitis Atópica",
                "Hidrocortisona 1% crema\nAntihistamínico oral\nEmoliente",
                "Hidrocortisona: aplicar 2 veces al día en áreas afectadas\nAntihistamínico: 1 tableta cada 24 horas\nEmoliente: aplicar después del baño",
                "Hidrocortisona: 7-14 días\nEmoliente: uso continuo",
                "Baños cortos con agua tibia, usar ropa de algodón, evitar jabones perfumados",
                "Dieta balanceada, identificar posibles alergenos alimentarios",
                "2 litros de agua al día",
                "No usar corticoides tópicos en cara por largos periodos");

        insertarEnfermedad(db, "Dermatológicas", "Acné Moderado",
                "Peróxido de benzoilo 5%\nClindamicina tópica\nDoxiciclina 100mg",
                "Peróxido: aplicar una vez al día\nClindamicina: aplicar 2 veces al día\nDoxiciclina: 1 tableta cada 24 horas",
                "Peróxido/Clindamicina: 8-12 semanas\nDoxiciclina: 6-8 semanas",
                "Limpieza suave 2 veces al día, no manipular lesiones, protector solar",
                "Dieta baja en azúcares y lácteos, rica en frutas y verduras",
                "2-2.5 litros de agua al día",
                "Evitar exposición solar excesiva, puede causar fotosensibilidad");
    }

    private void insertarEnfermedad(SQLiteDatabase db, String categoria, String nombreEnfermedad,
                                    String medicamentos, String dosis, String duracion,
                                    String recomendaciones, String dieta, String hidratacion,
                                    String contraindicaciones) {
        db.execSQL("INSERT INTO " + Constantes.TABLA_FORMULAS_MEDICAS + " (" +
                Constantes.CAMPO_CATEGORIA + ", " +
                Constantes.CAMPO_NOMBRE_ENFERMEDAD + ", " +
                Constantes.CAMPO_MEDICAMENTOS + ", " +
                Constantes.CAMPO_DOSIS + ", " +
                Constantes.CAMPO_DURACION + ", " +
                Constantes.CAMPO_RECOMENDACIONES + ", " +
                Constantes.CAMPO_DIETA + ", " +
                Constantes.CAMPO_HIDRATACION + ", " +
                Constantes.CAMPO_CONTRAINDICACIONES + ") VALUES ('" +
                categoria + "', '" +
                nombreEnfermedad + "', '" +
                medicamentos + "', '" +
                dosis + "', '" +
                duracion + "', '" +
                recomendaciones + "', '" +
                dieta + "', '" +
                hidratacion + "', '" +
                contraindicaciones + "')");
    }
}