plugins {
    kotlin("jvm")
}

val versionGraphql: String by project
val versionGraphqlTools: String by project
val versionGraphqlDatetime: String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":core"))

    // GraphQL
    implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:$versionGraphql")
    implementation("com.graphql-java-kickstart:graphiql-spring-boot-starter:$versionGraphql")
    implementation("com.graphql-java-kickstart:graphql-java-tools:$versionGraphqlTools")
    implementation("com.zhokhov.graphql:graphql-datetime-spring-boot-starter:$versionGraphqlDatetime")
    testImplementation("com.graphql-java-kickstart:graphql-spring-boot-starter-test:$versionGraphql")
}