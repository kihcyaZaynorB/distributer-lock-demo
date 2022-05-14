plugins {
    id("java")
}

group = "io.sutsaehpeh"
version = "1.0-SNAPSHOT"

val springBootVersion = "2.3.12.RELEASE"

repositories {
    maven(url = "https://maven.aliyun.com/repository/public/")
    mavenLocal()
    mavenCentral()
}

dependencies {

    implementation("javax.xml.bind:jaxb-api:2.3.1")

    implementation("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")

    implementation("org.apache.zookeeper:zookeeper:3.8.0")

    implementation("org.apache.curator:curator-recipes:5.2.1")

    implementation("com.alibaba:druid-spring-boot-starter:1.2.9")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${springBootVersion}")

    annotationProcessor("org.springframework.boot:spring-boot-starter-data-jpa:${springBootVersion}")

    implementation("org.springframework.boot:spring-boot-starter-data-redis:${springBootVersion}")

    implementation("org.redisson:redisson:3.17.1")

    runtimeOnly("mysql:mysql-connector-java:5.1.49")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
