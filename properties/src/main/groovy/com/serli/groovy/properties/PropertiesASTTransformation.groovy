package com.serli.groovy.properties

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
public class PropertiesASTTransformation implements ASTTransformation {

    public void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        // use guard clauses as a form of defensive programming.
        if (!astNodes) return
        if (!astNodes[0]) return
        if (!astNodes[1]) return
        if (!(astNodes[0] instanceof AnnotationNode)) return
        if (astNodes[0].classNode?.name != WithProperties.class.getName()) return
        if (!(astNodes[1] instanceof ClassNode)) return
        ClassNode annotatedClass = astNodes[1]
        println "found $annotatedClass.name annotated with @WithProperties"
        def configValue = createConfig(annotatedClass)
        annotatedClass.addField(
            new FieldNode(
                "config",
                1,
                new ClassNode(ConfigObject),
                annotatedClass,
                configValue[0].statements[0].getExpression()
            )
        )
        /** def bundleValue = createBundle(annotatedClass)
        annotatedClass.addField(
            new FieldNode(
                "bundle",
                1,
                new ClassNode(ConfigObject),
                annotatedClass,
                bundleValue[0].statements[0].getExpression()
            )
        )  **/
    }

    private List<ASTNode> createConfig(ClassNode annotatedClass) {
        String file = "/META-INF/" + annotatedClass.getNameWithoutPackage() + "Config.properties"
        String configUrl = getClass().getResource(file).toString();
        String configCode = "new ConfigSlurper().parse(new URL('" + configUrl + "'))".toString()
        return new AstBuilder().buildFromString(configCode)
    }

    private List<ASTNode> createBundle(ClassNode annotatedClass) {
        String configCode = ("ResourceBundle.getBundle('"
            + annotatedClass.getNameWithoutPackage() + "')").toString()
        return new AstBuilder().buildFromString(configCode)
    }
}