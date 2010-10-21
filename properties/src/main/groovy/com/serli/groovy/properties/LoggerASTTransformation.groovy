package com.serli.groovy.properties

import org.codehaus.groovy.transform.GroovyASTTransformation
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.ast.AnnotationNode

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class LoggerASTTransformation implements ASTTransformation {

  public void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
      // use guard clauses as a form of defensive programming.
      if (!astNodes) return
      if (!astNodes[0]) return
      if (!astNodes[1]) return
      if (!(astNodes[0] instanceof AnnotationNode)) return
      if (astNodes[0].classNode?.name != WithLogging.class.getName()) return
      if (!(astNodes[1] instanceof MethodNode)) return
      MethodNode annotatedMethod = astNodes[1]
      ClassNode declaringClass = astNodes[1].declaringClass
      println "found $annotatedMethod.name in $declaringClass.name annotated with @WithLogging"
      Statement startMessage = createPrintlnAst("Starting $annotatedMethod.name")
      Statement endMessage = createPrintlnAst("Ending $annotatedMethod.name")
      List existingStatements = annotatedMethod.getCode().getStatements()
      existingStatements.add(0, startMessage)
      existingStatements.add(endMessage)
  }

  private Statement createPrintlnAst(String message) {
      return new ExpressionStatement(
          new MethodCallExpression(
              new VariableExpression("this"),
              new ConstantExpression("println"),
              new ArgumentListExpression(
                  new ConstantExpression(message)
              )
          )
      )
  }
}
