package com.sam.jcc.cloud.utils.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by gladivs on 15.06.2017.
 */
//https://stackoverflow.com/questions/5205339/regular-expression-matching-fully-qualified-class-names
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProjectPackageHelperTest {
    @Test
    public void javaIdentifierValidationTest() {
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("C")).isTrue();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("Cc")).isTrue();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("cc")).isTrue();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("b.C")).isTrue();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("b.Cc")).isTrue();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("aAa.b.Cc")).isTrue();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("aaa.b.cc")).isTrue();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("a.b.Cc")).isTrue();

        // after the initial character identifiers may use any combination of
        // letters and digits, underscores or dollar signs
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("a.b.C_c")).isTrue();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("a.b.c_c")).isTrue();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("a.b.C$c")).isTrue();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("a.b.C9")).isTrue();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("a_bc")).isTrue();

        assertThat(ProjectGeneratorHelper.validateJavaIdentifier(".C")).isFalse();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("b..C")).isFalse();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("b.9C")).isFalse();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("c.a-bc")).isFalse();

        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("test.app")).isTrue();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("test..app")).isFalse();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier(".test.app")).isFalse();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("test.app.")).isFalse();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("test.app..")).isFalse();
        assertThat(ProjectGeneratorHelper.validateJavaIdentifier("..test.app")).isFalse();
    }

    @Test
    public void projectPackageTest() {
        assertThat(ProjectGeneratorHelper.getProjectPackage("my%project")).isEqualTo("myproject");
        assertThat(ProjectGeneratorHelper.getProjectPackage("my$project")).isEqualTo("myproject");
        assertThat(ProjectGeneratorHelper.getProjectPackage("my-project")).isEqualTo("myproject");
        assertThat(ProjectGeneratorHelper.getProjectPackage("com.samsolutions.my-project")).isEqualTo("com.samsolutions.myproject");
        assertThat(ProjectGeneratorHelper.getProjectPackage(".C")).isEqualTo("C");
        assertThat(ProjectGeneratorHelper.getProjectPackage("b..C")).isEqualTo("b.C");
        assertThat(ProjectGeneratorHelper.getProjectPackage("b.*C")).isEqualTo("b.C");
        assertThat(ProjectGeneratorHelper.getProjectPackage("c.a-bc")).isEqualTo("c.abc");
    }

}
