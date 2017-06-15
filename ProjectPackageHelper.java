package com.sam.jcc.cloud.utils.project;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

/**
 * Created by gladivs on 15.06.2017.
 */
public final class ProjectPackageHelper {

    private static final String ARTIFACT_REGEX = "[a-z0-9_\\-.]+";
    private static final String BASE_PACKAGE_PATTERNT = ARTIFACT_REGEX.replace("-", "");
    private static final Pattern FULL_PACKAGE_PATTERNT = Pattern.compile(BASE_PACKAGE_PATTERNT + "(\\." + BASE_PACKAGE_PATTERNT + ")*");

    private static Set<String> keywords;

    // The list of java keywords that cannot be used as identifiers
    // according to 3.9 Keywords chapter of Oracle doc.
    static {
        keywords = new HashSet<>();
        keywords.add("abstract");
        keywords.add("continue");
        keywords.add("for");
        keywords.add("new");
        keywords.add("switch");
        keywords.add("assert");
        keywords.add("default");
        keywords.add("if");
        keywords.add("package");
        keywords.add("synchronized");
        keywords.add("boolean");
        keywords.add("do");
        keywords.add("goto");
        keywords.add("private");
        keywords.add("this");
        keywords.add("break");
        keywords.add("double");
        keywords.add("implements");
        keywords.add("protected");
        keywords.add("throw");
        keywords.add("byte");
        keywords.add("else");
        keywords.add("import");
        keywords.add("public");
        keywords.add("throws");
        keywords.add("case");
        keywords.add("enum");
        keywords.add("instanceof");
        keywords.add("return");
        keywords.add("transient");
        keywords.add("catch");
        keywords.add("extends");
        keywords.add("int");
        keywords.add("short");
        keywords.add("try");
        keywords.add("char");
        keywords.add("final");
        keywords.add("interface");
        keywords.add("static");
        keywords.add("void");
        keywords.add("class");
        keywords.add("finally");
        keywords.add("long");
        keywords.add("strictfp");
        keywords.add("volatile");
        keywords.add("const");
        keywords.add("float");
        keywords.add("native");
        keywords.add("super");
        keywords.add("while");
    }


    private ProjectPackageHelper() {

    }

    public static boolean isProjectNameValid(String projectName) {
        if (isNull(projectName)) {
            return false;
        }
        if (!projectName.trim().toLowerCase().equals(projectName)) {
            return false;
        }
        if(projectName.startsWith("cloud")||projectName.startsWith("sam-checkstyle")) {
            return false;
        }
        if (!projectName.matches(ARTIFACT_REGEX)) {
            return false;
        }
        if(isNameKeyword(projectName)) {
            return false;
        }
        return true;
    }

    private static String getValidProjectPackage(String identifier) {
        String trimmedIdentifier = isNull(identifier) ? identifier.trim().toLowerCase() : null;
        StringBuilder strBuilder = new StringBuilder();
        String[]parts = trimmedIdentifier.split("\\.", -1) ;
        for(String part : parts){
            if(!isProjectPackageValid(part)){
                part = fixJavaIdentifier(part);
            }
            if(!isEmpty(part)){
                strBuilder.append(part).append(".");
            }
        }
        return strBuilder.toString().replaceFirst(".$", "");
    }

    public static boolean isProjectPackageValid(String identifier) {
        if (isNull(identifier)) {
            return false;
        }
        if (!identifier.matches(ARTIFACT_REGEX)) {
            return false;
        }
        if(isNameKeyword(identifier)) {
            return false;
        }
        return true;
    }

    private static String fixJavaIdentifier(String identifier){
        StringBuilder strBuilder = new StringBuilder();
        for(int i=0; i<identifier.length(); i++){
            char c = identifier.charAt(i);
            if(!"-".equals(c)){
                strBuilder.append(c);
            }
        }
        return strBuilder.toString();
    }

    private static boolean isNameKeyword(String name){
        String[] parts = name.split("\\.", -1);
        for(String part : parts) {
            if(keywords.contains(part)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmpty(String param){
        return (param == null || param.length() == 0);
    }

}
