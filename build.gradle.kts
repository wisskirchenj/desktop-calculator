plugins {
    application
}

application {
    mainClass.set("de.cofinpro.calculator.ApplicationRunner")
}

java.toolchain {
    languageVersion.set(JavaLanguageVersion.of(20))
}

configurations["compileOnly"]
    .extendsFrom(configurations["annotationProcessor"])

group = "de.cofinpro"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.20.0")

    val lombokVersion = "1.18.28"
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-junit-jupiter:5.4.0")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}