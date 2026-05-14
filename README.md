# FitPet

**FitPet** es una aplicación móvil Android de mascota virtual desarrollada como proyecto final para la materia de **Programación de Dispositivos Móviles**.

La aplicación busca motivar al usuario a caminar mediante una dinámica sencilla: mientras más pasos acumula durante el día, mejor será el estado de ánimo y energía de su mascota virtual. El proyecto combina una interfaz visual amigable, persistencia local de datos y una experiencia básica de gamificación.

---

## Descripción del proyecto

FitPet funciona como una mascota virtual fitness. El usuario puede aumentar sus pasos de forma manual mediante el botón **“Caminar (+100 pasos)”**. Conforme el contador de pasos avanza, la mascota cambia de estado, imagen y mensaje motivacional.

La app permite personalizar el nombre de la mascota, modificar la meta diaria de pasos y guardar el progreso localmente. También incluye herramientas de demostración para facilitar la presentación del proyecto en clase.

---

## Objetivo general

Desarrollar una aplicación móvil Android funcional, visualmente atractiva y de complejidad moderada, que integre conceptos de interfaz de usuario, navegación, estado, persistencia local y experiencia de usuario mediante una mascota virtual interactiva.

---

## Objetivos específicos

- Implementar una aplicación Android usando Kotlin y Jetpack Compose.
- Crear una interfaz moderna, minimalista y adaptable a orientación vertical y horizontal.
- Simular el progreso diario de pasos del usuario.
- Cambiar dinámicamente el estado de la mascota según el avance.
- Guardar el progreso, nombre de mascota y meta diaria usando DataStore.
- Permitir la personalización básica de la experiencia.
- Agregar herramientas de demostración para facilitar la exposición del proyecto.
- Documentar el desarrollo para revisión académica y técnica.

---

## Características principales

- Pantalla de bienvenida tipo **SplashScreen**.
- Mascota virtual con diferentes estados visuales.
- Contador de pasos diario.
- Barra de progreso hacia una meta personalizada.
- Cambio automático de imagen, estado y mensaje de la mascota.
- Pantalla de estadísticas.
- Pantalla de información sobre los estados de la mascota.
- Pantalla de ajustes.
- Personalización del nombre de la mascota.
- Personalización de la meta diaria de pasos.
- Persistencia local con DataStore.
- Reinicio automático diario del progreso.
- Herramientas de modo demo:
  - sumar +1000 pasos
  - completar meta
  - reiniciar progreso
- Mensajes de retroalimentación visual mediante Snackbar.
- Compatibilidad visual con orientación vertical y horizontal.
- Integración de logo e ícono de aplicación.

---

## Estados de la mascota

La mascota cambia de estado según el porcentaje de avance respecto a la meta diaria:

| Estado | Condición aproximada |
|---|---|
| Dormida | Progreso bajo |
| Despierta | Progreso inicial |
| Activa | Progreso medio |
| Feliz | Cerca de la meta |
| Meta cumplida | Meta diaria alcanzada o superada |

---

## Tecnologías utilizadas

- **Kotlin**
- **Android Studio**
- **Jetpack Compose**
- **Material 3**
- **Navigation Compose**
- **DataStore Preferences**
- **Gradle Kotlin DSL**
- **Git y GitHub**

---

## Estructura general del proyecto

```text
FitPet/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/fitpet/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── FitPetViewModel.kt
│   │   │   │   ├── FitPetDataStore.kt
│   │   │   │   ├── HomeScreen.kt
│   │   │   │   ├── StatsScreen.kt
│   │   │   │   ├── InfoScreen.kt
│   │   │   │   ├── SettingsScreen.kt
│   │   │   │   ├── SplashScreen.kt
│   │   │   │   └── NavigationUtils.kt
│   │   │   └── res/
│   │   │       ├── drawable/
│   │   │       │   ├── fitpet_icon.png
│   │   │       │   ├── splash_fitpet.png
│   │   │       │   ├── pet_sleepy.png
│   │   │       │   ├── pet_awake.png
│   │   │       │   ├── pet_active.png
│   │   │       │   ├── pet_happy.png
│   │   │       │   └── pet_goal.png
│   │   │       └── mipmap/
│   │   └── test/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
