package com.sam.jcc.cloud.project;

import com.sam.jcc.cloud.provider.UnsupportedCallException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.regex.Pattern;

import static com.sam.jcc.cloud.project.ProjectMetadataHelper.mavenProject;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Саша on 15.06.2017.
 */
//https://stackoverflow.com/questions/5205339/regular-expression-matching-fully-qualified-class-names
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProjectGeneratorHelperTest {

    private String ID_PATTERN;
    private Pattern FQCN;

    @Before
    public void setUp() {
        ID_PATTERN = "\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*";
        FQCN = Pattern.compile(ID_PATTERN + "(\\." + ID_PATTERN + ")*");
    }

    public boolean validateJavaIdentifier(String identifier) {
        return FQCN.matcher(identifier).matches();
    }

    @Test
    public void javaIdentifierValidationTest() {
        assertThat(validateJavaIdentifier("C")).isTrue();
        assertThat(validateJavaIdentifier("Cc")).isTrue();
        assertThat(validateJavaIdentifier("b.C")).isTrue();
        assertThat(validateJavaIdentifier("b.Cc")).isTrue();
        assertThat(validateJavaIdentifier("aAa.b.Cc")).isTrue();
        assertThat(validateJavaIdentifier("a.b.Cc")).isTrue();

        // after the initial character identifiers may use any combination of
        // letters and digits, underscores or dollar signs
        assertThat(validateJavaIdentifier("a.b.C_c")).isTrue();
        assertThat(validateJavaIdentifier("a.b.C$c")).isTrue();
        assertThat(validateJavaIdentifier("a.b.C9")).isTrue();
        assertThat(validateJavaIdentifier("a_bc")).isTrue();

        assertThat(validateJavaIdentifier(".C")).isFalse();
        assertThat(validateJavaIdentifier("b..C")).isFalse();
        assertThat(validateJavaIdentifier("b.9C")).isFalse();
        assertThat(validateJavaIdentifier("c.a-bc")).isFalse();
    }

    public String getValidJavaIdentifier(String identifier) {
        String trimmedIdentifier = identifier != null ? identifier.trim() : "";
        StringBuilder strBuilder = new StringBuilder();
        if(trimmedIdentifier.length() == 0){
            return "";
        }
        if(validateJavaIdentifier(trimmedIdentifier)) {
            return trimmedIdentifier;
        }
        String[]parts = trimmedIdentifier.split("\\.", -1) ;
        for(String part : parts){
            if(!validateJavaIdentifier(part)){
                part = fixJavaIdentifier(part);
            }
            if(!isEmpty(part)){
                strBuilder.append(part).append(".");
            }
        }
        return strBuilder.toString().replaceFirst(".$", "");
    }

    @Test
    public void javaIdentifierFixTest() {
        assertThat(getValidJavaIdentifier("my-project")).isEqualTo("myproject");
        assertThat(getValidJavaIdentifier("my-project.exp")).isEqualTo("myproject.exp");
    }

    public String[] getJavaPackageDerictories(String identifier) {
        String validIdentifier = getValidJavaIdentifier(identifier);
        return !isEmpty(validIdentifier) ? validIdentifier.split("\\.", -1) : new String[]{};
    }

    @Test
    public void javaPackageDerictoriesTest() {
        assertThat(getJavaPackageDerictories("my-project")[0]).isEqualTo("myproject");
        assertThat(getJavaPackageDerictories("my-project.exp")[0]).isEqualTo("myproject");
        assertThat(getJavaPackageDerictories("my-project.exp")[1]).isEqualTo("exp");
    }

    private String fixJavaIdentifier(String identifier){
        StringBuilder strBuilder = new StringBuilder();
        for(int i=0; i<identifier.length(); i++){
            char c = identifier.charAt(i);
            if(Character.isJavaIdentifierPart(c)){
                strBuilder.append(c);
            }
        }
        return strBuilder.toString();
    }

    private boolean isEmpty(String param){
        return (param == null || param.length() == 0);
    }
}
