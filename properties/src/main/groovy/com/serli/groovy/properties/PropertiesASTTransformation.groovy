package com.serli.groovy.properties

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.Expression

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
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
      String code = "new ConfigSlurper().parse(new File('" + annotatedClass.getNameWithoutPackage() + ".properties').toURL())".toString()
      def value = new AstBuilder().buildFromString(code)
      annotatedClass.addField(
          new FieldNode(
              annotatedClass.getNameWithoutPackage() + "Properties",
              1,
              new ClassNode(ConfigObject),
              annotatedClass,
              value[0].statements[0].getExpression()
          )
      )
  }
}