dependencies {
    testImplementation(platform(rootProject.libs.junit.bom))
    testImplementation(rootProject.libs.bundles.testing)
    api(rootProject.libs.guava)
}
