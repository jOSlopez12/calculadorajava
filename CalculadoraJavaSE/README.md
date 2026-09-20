# Calculadora — Java SE (POO)

Aplicación de escritorio stand-alone construida en **Java SE puro** (sin frameworks),
que implementa las cuatro operaciones aritméticas básicas usando **Programación
Orientada a Objetos**. Incluye pruebas unitarias automatizadas con JUnit 5.

## 1. Estructura del proyecto

```
CalculadoraJavaSE/
├── src/
│   ├── main/java/
│   │   ├── Main.java                     # Punto de entrada
│   │   ├── modelo/
│   │   │   ├── Operacion.java            # Interfaz (contrato POO)
│   │   │   ├── Suma.java
│   │   │   ├── Resta.java
│   │   │   ├── Multiplicacion.java
│   │   │   ├── Division.java
│   │   │   └── DivisionPorCeroException.java
│   │   ├── calculadora/
│   │   │   ├── Calculadora.java          # Orquesta las operaciones
│   │   │   └── TipoOperacion.java        # Enum de operaciones
│   │   └── ui/
│   │       └── CalculadoraGUI.java       # Interfaz gráfica (Swing, parte del JDK)
│   └── test/java/calculadora/
│       └── CalculadoraTest.java          # Pruebas unitarias (JUnit 5)
├── diagrama/
│   └── (aquí va tu diagrama_clases.png exportado desde DIA)
├── lib/                                  # Aquí va junit-platform-console-standalone.jar
├── .gitignore
└── README.md
```

### Diseño POO aplicado
- **Interfaz `Operacion`**: contrato común para todas las operaciones (abstracción).
- **`Suma`, `Resta`, `Multiplicacion`, `Division`**: cada una encapsula su propia
  lógica (encapsulamiento) e implementa `Operacion` (polimorfismo).
- **`Calculadora`**: no usa if/else ni switch para decidir qué operación ejecutar;
  usa un mapa de estrategias (`EnumMap<TipoOperacion, Operacion>`) y delega en el
  objeto correspondiente gracias al polimorfismo.
- **`DivisionPorCeroException`**: excepción propia para una regla de negocio clara.
- **`CalculadoraGUI`**: solo se encarga de la presentación (Swing, incluido en el
  JDK, por lo tanto no es un framework externo); delega todo el cálculo a
  `Calculadora` (separación de responsabilidades).

## 2. Cómo abrir el proyecto en IntelliJ IDEA Community (≤ 2025.2)

1. Abre IntelliJ IDEA → **File > Open** → selecciona la carpeta `CalculadoraJavaSE`.
2. Cuando pregunte por el SDK, selecciona un JDK 17 o superior instalado en tu Lubuntu
   (puedes instalarlo con `sudo apt install openjdk-17-jdk`).
3. Marca `src/main/java` como **Sources Root** y `src/test/java` como **Test Sources
   Root** (clic derecho sobre la carpeta → *Mark Directory as*).
4. Para las pruebas: clic derecho sobre `CalculadoraTest.java` → *Run* →
   IntelliJ te ofrecerá automáticamente **"Add JUnit5 to classpath"**; acéptalo
   (descarga la librería JUnit 5 una sola vez, es una librería de pruebas, no
   un framework de aplicación).
5. Ejecuta `Main.java` (clic derecho → *Run 'Main.main()'*) para abrir la
   calculadora gráfica.

## 3. Cómo compilar y ejecutar por línea de comandos (sin IDE)

```bash
# Compilar
cd CalculadoraJavaSE
mkdir -p out
javac -d out $(find src/main/java -name "*.java")

# Ejecutar la aplicación de escritorio
java -cp out Main
```

### Ejecutar las pruebas unitarias por línea de comandos

Descarga una sola vez el ejecutable de consola de JUnit (en una máquina con
internet) y colócalo en `lib/`:
`junit-platform-console-standalone-1.10.x.jar` (búscalo en Maven Central).

```bash
# Compilar producción y pruebas
javac -d out $(find src/main/java -name "*.java")
javac -cp "out:lib/junit-platform-console-standalone-1.10.2.jar" \
      -d out $(find src/test/java -name "*.java")

# Ejecutar las pruebas
java -jar lib/junit-platform-console-standalone-1.10.2.jar \
     -cp out --scan-classpath
```

## 4. Diagrama de clases con DIA

1. Instala DIA en Lubuntu: `sudo apt install dia`.
2. Crea un nuevo diagrama y, usando la paleta **UML**, agrega las clases:
   `Operacion` (interfaz), `Suma`, `Resta`, `Multiplicacion`, `Division`,
   `DivisionPorCeroException`, `Calculadora`, `TipoOperacion`, `CalculadoraGUI`
   y `Main`.
3. Dibuja las relaciones:
   - `Suma`, `Resta`, `Multiplicacion`, `Division` **implementan** `Operacion`
     (flecha punteada con triángulo hueco).
   - `Calculadora` **usa/compone** las clases de operación (asociación/composición).
   - `CalculadoraGUI` **usa** `Calculadora` (dependencia).
   - `Main` **crea** `CalculadoraGUI` (dependencia).
4. Usa la **vista de implementación** (mostrar atributos, métodos y visibilidad
   `+`/`-`), no solo los nombres de clase.
5. Exporta como imagen: **File > Export** → elige `.png` → guarda el archivo
   como `diagrama/diagrama_clases.png` dentro del proyecto.

## 5. Subir el proyecto a GitHub (cuenta gratuita)

```bash
cd CalculadoraJavaSE
git init
git add .
git commit -m "Entrega: Calculadora Java SE con POO, pruebas unitarias y diagrama DIA"

# Crea un repositorio vacío en https://github.com/new (sin README, sin licencia)
git remote add origin https://github.com/TU_USUARIO/calculadora-java-se.git
git branch -M main
git push -u origin main
```

Luego entrega el enlace del repositorio, por ejemplo:
`https://github.com/TU_USUARIO/calculadora-java-se`

Verifica antes de entregar que el repositorio incluya:
- [ ] Todo el código fuente (`src/`)
- [ ] Las pruebas unitarias (`src/test`)
- [ ] La imagen del diagrama de clases (`diagrama/diagrama_clases.png`)
- [ ] Este `README.md`
