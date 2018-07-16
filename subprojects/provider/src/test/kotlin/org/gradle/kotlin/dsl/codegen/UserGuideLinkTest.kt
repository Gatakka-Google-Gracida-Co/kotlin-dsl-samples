package org.gradle.kotlin.dsl.codegen

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat

import org.junit.Test

import java.net.HttpURLConnection
import java.net.URL


val linkedPlugins =
    listOf(
        "announce", "antlr", "application", "assembler", "assembler-lang",
        "base", "binary-base", "build-announcements", "build-dashboard", "build-init",
        "c", "c-lang", "checkstyle", "clang-compiler", "codenarc",
        "cpp", "cpp-executable", "cpp-lang", "cpp-library",
        "compare-gradle-builds", "component-base", "component-model-base",
        "cunit", "cunit-test-suite",
        "distribution",
        "ear", "eclipse", "eclipse-wtp",
        "findbugs",
        "gcc-compiler", "google-test", "google-test-test-suite",
        "groovy", "groovy-base",
        "help-tasks",
        "idea", "ivy-publish",
        "jacoco", "java", "java-base", "java-gradle-plugin", "java-lang",
        "java-library", "java-library-distribution", "jdepend", "junit-test-suite",
        "jvm-component", "jvm-resources",
        "maven", "maven-publish",
        "microsoft-visual-cpp-compiler",
        "native-component", "native-component-model",
        "objective-c", "objective-c-lang", "objective-cpp", "objective-cpp-lang", "osgi",
        "play", "play-application", "play-cofeescript", "play-ide", "play-javascript", "pmd",
        "project-report", "project-reports",
        "reporting-base",
        "scala", "scala-base", "signing", "standard-tool-chains",
        "visual-studio",
        "war",
        "windows-resource-script", "windows-resources")


val unlinkedPlugins =
    listOf(
        "coffeescript-base", "envjs", "javascript-base", "jshint",
        "language-base", "lifecycle-base",
        "publishing",
        "rhino",
        "scala-lang",
        "wrapper")


class UserGuideLinkTest {

    @Test
    fun `linked plugins`() {
        linkedPlugins.forEach {
            assertThat(
                "$it is linked",
                UserGuideLink.forPlugin(it),
                notNullValue())
        }
    }

    @Test
    fun `unlinked plugins`() {
        unlinkedPlugins.forEach {
            assertThat(
                "$it is not linked",
                UserGuideLink.forPlugin(it),
                nullValue())
        }
    }
}


class UserGuideLinkIntegrationTest {

    @Test
    fun `linked resources are available`() {
        linkedPlugins
            .mapNotNull { UserGuideLink.forPlugin(it) }
            .map { URL(it.replace("/current/", "/nightly/")) }
            .forEach { url ->
                (url.openConnection() as HttpURLConnection).run {
                    requestMethod = "HEAD"
                    assertThat(url.toString(), responseCode, equalTo(200))
                }
            }
    }
}
