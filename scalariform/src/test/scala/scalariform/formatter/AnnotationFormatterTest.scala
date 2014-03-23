package scalariform.formatter

import scalariform.parser.{CompilationUnit, ScalaParser}
import scalariform.formatter.preferences.{DoubleIndentClassDeclaration, AlignParameters, ForceAnnotationToNextLine, FormattingPreferences}

class AnnotationFormatterTest extends AbstractFormatterTest {

  override def debug: Boolean = false

  def parse(parser: ScalaParser) = parser.compilationUnitOrScript()

  type Result = CompilationUnit

  def format(formatter: ScalaFormatter, result: Result) = formatter.format(result)(FormatterState(indentLevel = 0))

//  "def asdf(@annotation one: Int, @a @b(c) two: String)" ==> "def asdf(@annotation one: Int, @a @b(c) two: String)"
//
//  """class X {
//    |  def asdf(
//    |    @annotation one: Int,
//    |    @a @b(c) two: String
//    |  ) = ???
//    |}""" ==>
//  """class X {
//    |  def asdf(
//    |    @annotation one: Int,
//    |    @a @b(c) two: String
//    |  ) = ???
//    |}"""

  {

    implicit val formattingPreferences = FormattingPreferences.setPreference(ForceAnnotationToNextLine, true)

    """class X {
      |  def asdf(
      |    @annotation one: Int,
      |    @a @b(c) two: String
      |  ) = ???
      |}""" ==>
    """class X {
      |  def asdf(
      |    @annotation
      |    one: Int,
      |    @a
      |    @b(c)
      |    two: String
      |  ) = ???
      |}"""

//    """class A extends B {
//      |  @SomeImportantAnnotation(param = true) override val param: Int = 1
//      |
//      |  @NotSoImportantAnnotation(param = false) def description: String = "Not so important"
//      |}""" ==>
//    """class A extends B {
//      |  @SomeImportantAnnotation(param = true)
//      |  override val param: Int = 1
//      |
//      |  @NotSoImportantAnnotation(param = false)
//      |  def description: String = "Not so important"
//      |}"""

//    """@Annotation class X{}""" ==>
//    """@Annotation
//      |class X {}"""
//
//    """def secondMethod(@Argument arg: Int)""" ==> """def secondMethod(@Argument arg: Int)"""

  }

  {
    implicit val formattingPreferences = FormattingPreferences.
      setPreference(ForceAnnotationToNextLine, true).
      setPreference(AlignParameters, true).
      setPreference(DoubleIndentClassDeclaration, true)
//    """def a(@annotation x: Int = 1)"""  ==>
//    """def a(@annotation x: Int = 1)"""

//    """def a(
//      |  @annotation x: Int = 1,
//      |  @a1 @a2 y: String = "args",
//      |  @a3 z: Boolean = false
//      |)"""  ==>
//    """def a(
//      |  @annotation
//      |  x: Int     = 1,
//      |  @a1
//      |  @a2
//      |  y: String  = "args",
//      |  @a3
//      |  z: Boolean = false
//      |)"""

    // test with implicit
    // test with structural typing
  }
}