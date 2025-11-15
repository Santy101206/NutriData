package com.santy.nutridata.Model;

public class Constantes {
    // Base de datos
    public static final String DB_NAME = "nutridata.db";
    public static final int DB_VERSION = 7;

    // TABLA USUARIOS
    public static final String TABLA_USUARIOS = "usuarios";
    public static final String CAMPO_USER = "usuario";
    public static final String CAMPO_PASS = "password";

    // TABLA USUARIOS
    public static final String BORRAR_TABLA_DATOS_PACIENTE_SI_EXISTE = "DROP TABLE IF EXISTS " + Constantes.TABLA_DATOS;
    public static final String BORRAR_TABLA_USUARIOS_SI_EXISTE = "DROP TABLE IF EXISTS " + Constantes.TABLA_USUARIOS;
    public static final String BORRAR_TABLA_FORMULAS_MEDICAS_SI_EXISTE = "DROP TABLE IF EXISTS " + Constantes.TABLA_FORMULAS_MEDICAS;

    // TABLA DATOS
    public static final String TABLA_DATOS = "datos";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_USER_DATOS = "usuario";
    public static final String CAMPO_NOMBRE_PACIENTE = "nombrePaciente";
    public static final String CAMPO_TIPO_SANGRE = "tipoSangre";
    public static final String CAMPO_DIRECCION = "direccion";
    public static final String CAMPO_PESO = "peso";
    public static final String CAMPO_ALTURA = "altura";
    public static final String CAMPO_EDAD = "edad";
    public static final String CAMPO_ENFERMEDADES = "enfermedades";
    public static final String CAMPO_ALERGIAS = "alergias";
    public static final String CAMPO_SINTOMAS = "sintomas";
    public static final String CAMPO_IMC = "imc";
    public static final String CAMPO_CLASIFICACION = "clasificacion";
    public static final String CAMPO_PRIORIDAD = "prioridad";
    public static final String CAMPO_MEDICO_RESPONSABLE = "medicoResponsable";
    public static final String CAMPO_RESP_NOMBRE = "respNombre";
    public static final String CAMPO_RESP_CEDULA = "respCedula";
    public static final String CAMPO_RESP_EDAD = "respEdad";
    public static final String CAMPO_RESP_TELEFONO = "respTelefono";
    public static final String CAMPO_RESP_RELACION = "respRelacion";
    public static final String CAMPO_TRATAMIENTO = "tratamiento";

    // TABLA FORMULAS_MEDICAS
    public static final String TABLA_FORMULAS_MEDICAS = "formulas_medicas";
    public static final String CAMPO_ID_FORMULA = "id_formula";
    public static final String CAMPO_CATEGORIA = "categoria";
    public static final String CAMPO_NOMBRE_ENFERMEDAD = "nombre_enfermedad";
    public static final String CAMPO_MEDICAMENTOS = "medicamentos";
    public static final String CAMPO_DOSIS = "dosis";
    public static final String CAMPO_DURACION = "duracion";
    public static final String CAMPO_RECOMENDACIONES = "recomendaciones";
    public static final String CAMPO_DIETA = "dieta";
    public static final String CAMPO_HIDRATACION = "hidratacion";
    public static final String CAMPO_CONTRAINDICACIONES = "contraindicaciones";

    // Sentencias SQL
    public static final String CREAR_TABLA_USUARIOS =
            "CREATE TABLE " + TABLA_USUARIOS + " (" +
                    CAMPO_USER + " TEXT PRIMARY KEY, " +
                    CAMPO_PASS + " TEXT)";

    public static final String CREAR_TABLA_DATOS =
            "CREATE TABLE " + TABLA_DATOS + " (" +
                    CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CAMPO_USER_DATOS + " TEXT, " +
                    CAMPO_NOMBRE_PACIENTE + " TEXT, " +
                    CAMPO_TIPO_SANGRE + " TEXT, " +
                    CAMPO_DIRECCION + " TEXT, " +
                    CAMPO_PESO + " REAL, " +
                    CAMPO_ALTURA + " REAL, " +
                    CAMPO_EDAD + " INTEGER, " +
                    CAMPO_ENFERMEDADES + " TEXT, " +
                    CAMPO_ALERGIAS + " TEXT, " +
                    CAMPO_SINTOMAS + " TEXT, " +
                    CAMPO_IMC + " REAL, " +
                    CAMPO_CLASIFICACION + " TEXT, " +
                    CAMPO_PRIORIDAD + " TEXT, " +
                    CAMPO_MEDICO_RESPONSABLE + " TEXT, " +
                    CAMPO_RESP_NOMBRE + " TEXT, " +
                    CAMPO_RESP_CEDULA + " TEXT, " +
                    CAMPO_RESP_EDAD + " INTEGER, " +
                    CAMPO_RESP_TELEFONO + " TEXT, " +
                    CAMPO_RESP_RELACION + " TEXT, " +
                    CAMPO_TRATAMIENTO + " TEXT)";

    public static final String CREAR_TABLA_FORMULAS_MEDICAS =
            "CREATE TABLE " + TABLA_FORMULAS_MEDICAS + " (" +
                    CAMPO_ID_FORMULA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CAMPO_CATEGORIA + " TEXT, " +
                    CAMPO_NOMBRE_ENFERMEDAD + " TEXT, " +
                    CAMPO_MEDICAMENTOS + " TEXT, " +
                    CAMPO_DOSIS + " TEXT, " +
                    CAMPO_DURACION + " TEXT, " +
                    CAMPO_RECOMENDACIONES + " TEXT, " +
                    CAMPO_DIETA + " TEXT, " +
                    CAMPO_HIDRATACION + " TEXT, " +
                    CAMPO_CONTRAINDICACIONES + " TEXT)";

    public static final String INSERTAR_DATOS_INICIALES =
            "INSERT INTO formulas_medicas " +
                    "(categoria, nombre_enfermedad, medicamentos, dosis, duracion, recomendaciones, dieta, hidratacion, contraindicaciones) VALUES " +

                    "('Infecciosas', 'Gripe Común', " +
                    "'Acetaminofén 500mg\nIbuprofeno 400mg\nLoratadina 10mg', " +
                    "'Acetaminofén: 1 tableta cada 8 horas\nIbuprofeno: 1 tableta cada 12 horas\nLoratadina: 1 tableta cada 24 horas', " +
                    "'5-7 días', " +
                    "'Reposo, hidratación abundante, alimentación ligera, evitar cambios bruscos de temperatura', " +
                    "'Líquidos calientes, sopas, frutas ricas en vitamina C (naranja, limón, kiwi)', " +
                    "'2-3 litros de agua al día', " +
                    "'Evitar automedicación con antibióticos')," +

                    "('Infecciosas', 'Amigdalitis Aguda', " +
                    "'Amoxicilina 500mg\nAcetaminofén 500mg\nIbuprofeno 400mg', " +
                    "'Amoxicilina: 1 tableta cada 8 horas\nAcetaminofén: 1 tableta cada 8 horas\nIbuprofeno: 1 tableta cada 12 horas', " +
                    "'7-10 días', " +
                    "'Gárgaras con agua salada, reposo vocal, humidificador ambiental', " +
                    "'Dieta blanda, evitar alimentos muy calientes o fríos, sopas y purés', " +
                    "'2-2.5 litros de agua al día', " +
                    "'Completar tratamiento antibiótico, consultar si fiebre persiste')," +

                    "('Infecciosas', 'Infección Urinaria', " +
                    "'Nitrofurantoína 100mg\nAnalgésico urinario', " +
                    "'Nitrofurantoína: 1 cápsula cada 12 horas\nAnalgésico: 1 tableta cada 8 horas según dolor', " +
                    "'5-7 días', " +
                    "'Aumentar ingesta de líquidos, orinar frecuentemente, higiene adecuada', " +
                    "'Jugo de arándano, evitar cafeína y alcohol', " +
                    "'2.5-3 litros de agua al día', " +
                    "'Consultar si síntomas persisten después de 48 horas')," +

                    "('Infecciosas', 'Bronquitis Aguda', " +
                    "'Amoxicilina 500mg\nSalbutamol inhalador\nAcetaminofén 500mg', " +
                    "'Amoxicilina: 1 tableta cada 8 horas\nSalbutamol: 2 inhalaciones cada 6 horas\nAcetaminofén: 1 tableta cada 8 horas', " +
                    "'7-10 días', " +
                    "'Reposo, humidificador, evitar humo y contaminantes', " +
                    "'Líquidos calientes, miel con limón, sopas nutritivas', " +
                    "'2-3 litros de agua al día', " +
                    "'Acudir a urgencias si dificultad respiratoria severa')," +

                    "('Cardiovasculares', 'Hipertensión Arterial', " +
                    "'Losartán 50mg\nHidroclorotiazida 25mg', " +
                    "'Losartán: 1 tableta cada 24 horas\nHidroclorotiazida: 1 tableta cada 24 horas', " +
                    "'Tratamiento crónico', " +
                    "'Control de presión semanal, ejercicio regular 30 min/día, control de peso', " +
                    "'Baja en sal, rica en potasio, evitar embutidos y enlatados', " +
                    "'1.5-2 litros de agua al día', " +
                    "'Evitar consumo de alcohol y tabaco, no suspender medicación')," +

                    "('Cardiovasculares', 'Dislipidemia (Colesterol Alto)', " +
                    "'Atorvastatina 20mg\nÁcido Acetilsalicílico 100mg', " +
                    "'Atorvastatina: 1 tableta en la noche\nAAS: 1 tableta cada 24 horas', " +
                    "'Tratamiento crónico', " +
                    "'Ejercicio aeróbico 150 min/semana, control de peso', " +
                    "'Dieta baja en grasas saturadas, aumentar fibra, pescado azul 2 veces/semana', " +
                    "'2 litros de agua al día', " +
                    "'Evitar grasas trans, controlar perfil lipídico cada 6 meses')," +

                    "('Metabólicas', 'Diabetes Mellitus Tipo 2', " +
                    "'Metformina 850mg\nGlibenclamida 5mg', " +
                    "'Metformina: 1 tableta con el desayuno y cena\nGlibenclamida: 1 tableta con el desayuno', " +
                    "'Tratamiento crónico', " +
                    "'Monitoreo de glucosa en ayunas, examen de hemoglobina glicosilada cada 3 meses, cuidado de pies', " +
                    "'Distribuir carbohidratos en 5 comidas, evitar azúcares simples, fibra soluble', " +
                    "'2-2.5 litros de agua al día', " +
                    "'Evitar ayunos prolongados, monitorear hipoglucemias')," +

                    "('Metabólicas', 'Anemia Ferropénica', " +
                    "'Sulfato ferroso 325mg\nÁcido fólico 1mg\nVitamina C 500mg', " +
                    "'Sulfato ferroso: 1 tableta cada 24 horas\nÁcido fólico: 1 tableta cada 24 horas\nVitamina C: 1 tableta cada 24 horas', " +
                    "'3-6 meses', " +
                    "'Tomar hierro en ayunas, evitar lácteos 2 horas antes/después, control hematológico', " +
                    "'Alimentos ricos en hierro: carnes rojas, espinacas, lentejas, frutos secos', " +
                    "'2 litros de agua al día', " +
                    "'Puede causar estreñimiento, heces oscuras, evitar té/café con comidas')," +

                    "('Metabólicas', 'Hipotiroidismo', " +
                    "'Levotiroxina 50 mcg', " +
                    "'Levotiroxina: 1 tableta en ayunas 30 min antes del desayuno', " +
                    "'Tratamiento crónico', " +
                    "'Control TSH cada 6-12 meses, tomar medicación siempre a la misma hora', " +
                    "'Dieta balanceada, evitar exceso de yodo, alimentos bociógenos en moderación', " +
                    "'1.5-2 litros de agua al día', " +
                    "'No tomar con otros medicamentos, esperar 4 horas para suplementos de calcio/hierro')," +

                    "('Digestivas', 'Gastritis Aguda', " +
                    "'Omeprazol 20mg\nDomperidona 10mg', " +
                    "'Omeprazol: 1 tableta 30 min antes del desayuno\nDomperidona: 1 tableta 15 min antes de comidas', " +
                    "'4-8 semanas', " +
                    "'Comer porciones pequeñas, evitar acostarse después de comer, manejar estrés', " +
                    "'Evitar picantes, café, alcohol, alimentos grasos, comidas irritantes', " +
                    "'1.5-2 litros de agua al día', " +
                    "'Evitar AINEs como ibuprofeno, aspirina')," +

                    "('Digestivas', 'Diarrea Aguda', " +
                    "'Loperamida 2mg\nSolución rehidratante oral\nProbióticos', " +
                    "'Loperamida: 2 tabletas inicial, luego 1 después de cada deposición líquida\nSRO: 200ml después de cada deposición\nProbióticos: 1 cápsula cada 24 horas', " +
                    "'3-5 días', " +
                    "'Reposo intestinal las primeras 4-6 horas, higiene de manos frecuente', " +
                    "'Dieta astringente: arroz, manzana, plátano, pan tostado, zanahoria', " +
                    "'SRO + 2-3 litros de líquidos al día', " +
                    "'Si fiebre >38°C o sangre en heces, acudir a urgencias')," +

                    "('Digestivas', 'Estreñimiento Crónico', " +
                    "'Lactulosa 15ml\nPsyllium 5g', " +
                    "'Lactulosa: 15ml cada 24 horas\nPsyllium: 1 sobre disuelto en agua 2 veces al día', " +
                    "'4-8 semanas', " +
                    "'Establecer rutina intestinal, ejercicio regular, no posponer defecación', " +
                    "'Alta en fibra: frutas, verduras, cereales integrales, legumbres', " +
                    "'2.5-3 litros de agua al día', " +
                    "'Aumentar fibra gradualmente, puede causar flatulencia inicial')," +

                    "('Respiratorias', 'Asma Bronquial', " +
                    "'Salbutamol inhalador\nBudesonida inhalador', " +
                    "'Salbutamol: 1-2 inhalaciones cada 4-6 horas según necesidad\nBudesonida: 2 inhalaciones cada 12 horas', " +
                    "'Tratamiento crónico', " +
                    "'Evitar desencadenantes, usar inhalador correctamente, plan de acción para crisis', " +
                    "'Dieta balanceada, mantener peso ideal, alimentos antiinflamatorios', " +
                    "'2 litros de agua al día', " +
                    "'Acudir a urgencias si dificultad respiratoria severa')," +

                    "('Respiratorias', 'Rinitis Alérgica', " +
                    "'Loratadina 10mg\nFluticasona nasal', " +
                    "'Loratadina: 1 tableta cada 24 horas\nFluticasona: 2 aplicaciones en cada fosa nasal cada 24 horas', " +
                    "'Durante temporada alérgica', " +
                    "'Evitar alérgenos, uso de mascarilla, limpieza ambiental', " +
                    "'Alimentos con quercetina: manzanas, cebollas, té verde', " +
                    "'2 litros de agua al día', " +
                    "'Consultar si síntomas no mejoran en 1 semana')," +

                    "('Neurológicas', 'Migraña Crónica', " +
                    "'Sumatriptán 50mg\nIbuprofeno 400mg\nPropranolol 40mg', " +
                    "'Sumatriptán: 1 tableta al inicio del dolor\nIbuprofeno: 1 tableta cada 8 horas si dolor persiste\nPropranolol: 1 tableta cada 12 horas', " +
                    "'Sumatriptán: según episodios\nPropranolol: tratamiento continuo', " +
                    "'Reposo en lugar oscuro y silencioso, identificar desencadenantes, técnicas de relajación', " +
                    "'Evitar chocolates, quesos maduros, vino tinto, edulcorantes artificiales', " +
                    "'2 litros de agua al día', " +
                    "'No usar más de 2 días por semana para evitar cefalea por rebote')," +

                    "('Neurológicas', 'Insomnio Transitorio', " +
                    "'Melatonina 3mg\nValeriana 500mg', " +
                    "'Melatonina: 1 tableta 30 min antes de dormir\nValeriana: 1 tableta 30 min antes de dormir', " +
                    "'2-4 semanas', " +
                    "'Higiene del sueño: horario regular, ambiente oscuro y silencioso, evitar pantallas antes de dormir', " +
                    "'Cena ligera 2-3 horas antes de dormir, evitar cafeína después de las 3pm', " +
                    "'1.5 litros de agua al día', " +
                    "'Evitar uso prolongado, consultar si insomnio persiste')," +

                    "('Musculoesqueléticas', 'Osteoartritis', " +
                    "'Acetaminofén 500mg\nGlucosamina 1500mg\nCondroitín 1200mg', " +
                    "'Acetaminofén: 1-2 tabletas cada 8 horas\nGlucosamina: 1 tableta cada 24 horas\nCondroitín: 1 tableta cada 24 horas', " +
                    "'Acetaminofén: según dolor\nGlucosamina/Condroitín: 3-6 meses', " +
                    "'Ejercicio de bajo impacto, control de peso, fisioterapia', " +
                    "'Alimentos antiinflamatorios: pescado azul, frutos secos, aceite de oliva', " +
                    "'2 litros de agua al día', " +
                    "'Evitar sobrecarga articular, usar calzado adecuado')," +

                    "('Musculoesqueléticas', 'Lumbalgia Aguda', " +
                    "'Ibuprofeno 400mg\nRelajante muscular', " +
                    "'Ibuprofeno: 1 tableta cada 8 horas\nRelajante muscular: 1 tableta cada 12 horas', " +
                    "'5-7 días', " +
                    "'Reposo relativo 48 horas, aplicación de calor local, ejercicios de fortalecimiento progresivo', " +
                    "'Alimentos antiinflamatorios, adecuada ingesta de calcio y vitamina D', " +
                    "'2 litros de agua al día', " +
                    "'Evitar reposo prolongado, consultar si dolor se irradia a piernas')," +

                    "('Dermatológicas', 'Dermatitis Atópica', " +
                    "'Hidrocortisona 1% crema\nAntihistamínico oral\nEmoliente', " +
                    "'Hidrocortisona: aplicar 2 veces al día en áreas afectadas\nAntihistamínico: 1 tableta cada 24 horas\nEmoliente: aplicar después del baño', " +
                    "'Hidrocortisona: 7-14 días\nEmoliente: uso continuo', " +
                    "'Baños cortos con agua tibia, usar ropa de algodón, evitar jabones perfumados', " +
                    "'Dieta balanceada, identificar posibles alergenos alimentarios', " +
                    "'2 litros de agua al día', " +
                    "'No usar corticoides tópicos en cara por largos periodos')," +

                    "('Dermatológicas', 'Acné Moderado', " +
                    "'Peróxido de benzoilo 5%\nClindamicina tópica\nDoxiciclina 100mg', " +
                    "'Peróxido: aplicar una vez al día\nClindamicina: aplicar 2 veces al día\nDoxiciclina: 1 tableta cada 24 horas', " +
                    "'Peróxido/Clindamicina: 8-12 semanas\nDoxiciclina: 6-8 semanas', " +
                    "'Limpieza suave 2 veces al día, no manipular lesiones, protector solar', " +
                    "'Dieta baja en azúcares y lácteos, rica en frutas y verduras', " +
                    "'2-2.5 litros de agua al día', " +
                    "'Evitar exposición solar excesiva, puede causar fotosensibilidad');";


}
