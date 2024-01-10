package com.crossnetcorp.banking.partyreferencedata;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ianache
 */
public class MyArchitectureTest {

    private static JavaClasses importedClasses;

    @BeforeClass
    public static void setUp() {
        System.out.println("TESTING");
        ImportOption ignoreTests = location -> {
            return !location.contains("/test/");
            // || location.contains("/infrastructure/");
        };

        importedClasses = new ClassFileImporter()
                .withImportOption(ignoreTests)
                .importPackages("partyreferencedata");

        importedClasses.stream().forEach(System.out::println);
    }

    @Test
    public void layeredArchitectureGuardian() {
//        ArchRule rule = ArchRuleDefinition.classes()
//                .that().resideInAPackage("..domain..")
//                .should().onlyBeAccessed()
//                .byAnyPackage("..domain..", "..application..")
//                .allowEmptyShould(true);
//        rule.check(importedClasses);

        Architectures.LayeredArchitecture layeredArchitecture =
                layeredArchitecture()
                .layer("Presentation").definedBy("..presentation..")
                .layer("Application").definedBy("..application..")
                .layer("Domain").definedBy("..domain..")
                .layer("Infrastructure").definedBy("..infrastructure..")
                .whereLayer("Domain").mayOnlyBeAccessedByLayers("Infrastructure","Application")
                .whereLayer("Infrastructure").mayOnlyBeAccessedByLayers("Application","Presentation")
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Presentation")
                .whereLayer("Presentation").mayNotBeAccessedByAnyLayer();
                
        layeredArchitecture.allowEmptyShould(true).check(importedClasses);
    }

}
