package com.sam.jcc.cloud.project;

import com.sam.jcc.cloud.utils.project.ProjectPackageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
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
        assertThat(ProjectPackageHelper.isProjectPackageValid("C")).isFalse();
        assertThat(ProjectPackageHelper.isProjectPackageValid("Cc")).isFalse();
        assertThat(ProjectPackageHelper.isProjectPackageValid("b.Cc")).isFalse();
        assertThat(ProjectPackageHelper.isProjectPackageValid("aAa.b.Cc")).isFalse();
        assertThat(ProjectPackageHelper.isProjectPackageValid("aaa.b.cc")).isFalse();
        assertThat(ProjectPackageHelper.isProjectPackageValid("a.b.Cc")).isFalse();

        assertThat(ProjectPackageHelper.isProjectPackageValid("cc")).isTrue();
        assertThat(ProjectPackageHelper.isProjectPackageValid("b.C")).isTrue();

        // after the initial character identifiers may use any combination of
        // letters and digits, underscores or dollar signs
        assertThat(ProjectPackageHelper.isProjectPackageValid("a.b.C_c")).isFalse();
        assertThat(ProjectPackageHelper.isProjectPackageValid("a.b.C$c")).isFalse();
        assertThat(ProjectPackageHelper.isProjectPackageValid("a.b.C9")).isFalse();

        assertThat(ProjectPackageHelper.isProjectPackageValid("a_bc")).isTrue();
        assertThat(ProjectPackageHelper.isProjectPackageValid("a.b.c_c")).isTrue();

        assertThat(ProjectPackageHelper.isProjectPackageValid(".C")).isFalse();
        assertThat(ProjectPackageHelper.isProjectPackageValid("b..C")).isFalse();
        assertThat(ProjectPackageHelper.isProjectPackageValid("b.9C")).isFalse();
        assertThat(ProjectPackageHelper.isProjectPackageValid("c.a-bc")).isFalse();

        assertThat(ProjectPackageHelper.isProjectPackageValid("test.app")).isTrue();
        assertThat(ProjectPackageHelper.isProjectPackageValid("test..app")).isFalse();
        assertThat(ProjectPackageHelper.isProjectPackageValid(".test.app")).isFalse();
        assertThat(ProjectPackageHelper.isProjectPackageValid("test.app.")).isFalse();
        assertThat(ProjectPackageHelper.isProjectPackageValid("test.app..")).isFalse();
        assertThat(ProjectPackageHelper.isProjectPackageValid("..test.app")).isFalse();
    }

    @Test
    public void projectNameValidationTest() {

        /*assertThat(ProjectPackageHelper.isProjectNameValid("my-project")).isTrue();
        assertThat(ProjectPackageHelper.isProjectNameValid("com.samsolutions.my-project")).isTrue();
        assertThat(ProjectPackageHelper.isProjectNameValid("test.app.")).isTrue();
        assertThat(ProjectPackageHelper.isProjectNameValid("test.app-")).isTrue();
        assertThat(ProjectPackageHelper.isProjectNameValid("test9.app")).isTrue();
        assertThat(ProjectPackageHelper.isProjectNameValid("test.9app")).isTrue();
        assertThat(ProjectPackageHelper.isProjectNameValid("test.app9")).isTrue();
        assertThat(ProjectPackageHelper.isProjectNameValid("app.cloud")).isTrue();
        assertThat(ProjectPackageHelper.isProjectNameValid("app_cloud")).isTrue();
        assertThat(ProjectPackageHelper.isProjectNameValid("appcloud")).isTrue();
        assertThat(ProjectPackageHelper.isProjectNameValid("app_sam-checkstyle")).isTrue();

        assertThat(ProjectPackageHelper.isProjectNameValid("my%project")).isFalse();
        assertThat(ProjectPackageHelper.isProjectNameValid("my$project")).isFalse();
        assertThat(ProjectPackageHelper.isProjectNameValid("my*project")).isFalse();
        assertThat(ProjectPackageHelper.isProjectNameValid(".test.app")).isFalse();
        assertThat(ProjectPackageHelper.isProjectNameValid("-test.app")).isFalse();
        assertThat(ProjectPackageHelper.isProjectNameValid("_test.app")).isFalse();
        assertThat(ProjectPackageHelper.isProjectNameValid("9test.app")).isFalse();
        assertThat(ProjectPackageHelper.isProjectNameValid("cloud")).isFalse();
        assertThat(ProjectPackageHelper.isProjectNameValid("sam-checkstyle")).isFalse();
        assertThat(ProjectPackageHelper.isProjectNameValid("cloud.app")).isFalse();
        assertThat(ProjectPackageHelper.isProjectNameValid("cloudapp")).isFalse();
        assertThat(ProjectPackageHelper.isProjectNameValid("sam-checkstyle_app")).isFalse();
        assertThat(ProjectPackageHelper.isProjectNameValid("Test.app")).isFalse();
        assertThat(ProjectPackageHelper.isProjectNameValid("test.App")).isFalse();*/

    }

}
