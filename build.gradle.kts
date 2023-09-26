plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // there should be no dependencies
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
}

tasks.test {
    useJUnitPlatform()
}