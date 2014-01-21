package scalariform.formatter

import scalariform.parser.{CompilationUnit, FunDefOrDcl, FullDefOrDcl, ScalaParser}
import scalariform.formatter.preferences._
import scalariform.parser.CompilationUnit
import scalariform.formatter.FormatterState

class MultipleParameterListsFormatterTest extends AbstractFormatterTest {

  // format: OFF
  {
    implicit val formatting = FormattingPreferences.setPreference(BreakMultipleParameterGroups,true)

    """def f(x: Int)
      |(y: Int): Int = {
      |}
      |""" =/=>
    """def f(x: Int)(y: Int): Int = {
      |}
      |""" because "we need a more accurate way to detect the current line length"

    """def f(x: Int)
      |     (y: Int)
      |     (z: Int): Int = {
      |}
    """ =/=>
    """def f(x: Int)(y: Int)(z: Int): Int = {
      |}
      |""" because "we need a more accurate way to detect the current line length"

    """def f(x: Int)(y: Int): Int = {
      |}
      |""" ==>
    """def f(x: Int)
      |     (y: Int): Int = {
      |}
      |"""

    """def f(x: Int)
      |     (y: Int)(z: Int): Int = {
      |}
    """ ==>
    """def f(x: Int)
      |     (y: Int)
      |     (z: Int): Int = {
      |}
      |"""

    // See issue #73
    """def mergeMapsCombiningValueMaps[A, B, C](collisionFunc: (C, C) => C)(m1: Map[A, Map[Seq[B], C]], m2: Map[A, Map[Seq[B], C]]): Map[A, Map[Seq[B], C]] = {
      |  mergeMaps(m1, m2)((m11, m22) => mergeMaps(m11, m22)(collisionFunc))
      |}""" ==>
    """def mergeMapsCombiningValueMaps[A, B, C](collisionFunc: (C, C) => C)
      |                                        (m1: Map[A, Map[Seq[B], C]], m2: Map[A, Map[Seq[B], C]]): Map[A, Map[Seq[B], C]] = {
      |  mergeMaps(m1, m2)((m11, m22) => mergeMaps(m11, m22)(collisionFunc))
      |}"""
  }

  {
    implicit val formatting = FormattingPreferences.
      setPreference(BreakMultipleParameterGroups,true).
      setPreference(AlignParameters, true).
      setPreference(PreserveDanglingCloseParenthesis, true)

    """def f(a: Int = 0,
      |  bb: String = "",
      |  ccc: Boolean = false)(dd: Int,
      |  eee: String,
      |  ffff: Boolean
      |): Int""" ==>
    """def f(a:   Int     = 0,
      |      bb:  String  = "",
      |      ccc: Boolean = false)
      |     (dd:   Int,
      |      eee:  String,
      |      ffff: Boolean
      |      ): Int"""

    """def f(a: Int = 0,
      |  bb: String = "",
      |  ccc: Boolean = false
      |  )(dd: Int,
      |  eee: String,
      |  ffff: Boolean
      |): Int""" ==>
    """def f(a:   Int     = 0,
      |      bb:  String  = "",
      |      ccc: Boolean = false
      |     )
      |     (dd:   Int,
      |      eee:  String,
      |      ffff: Boolean
      |      ): Int"""

    """def f(
      |  a: Int = 0,
      |  bb: String = "",
      |  ccc: Boolean = false
      |)(
      |  dd: Int,
      |  eee: String,
      |  ffff: Boolean
      |): Int""" ==>
      """def f(
        |  a:   Int     = 0,
        |  bb:  String  = "",
        |  ccc: Boolean = false
        |)
        |(
        |  dd:   Int,
        |  eee:  String,
        |  ffff: Boolean
        |): Int"""

    """def f(
      |  a: Int = 0,
      |  bb: String = "",
      |  ccc: Boolean = false)(dd: Int,
      |  eee: String,
      |  ffff: Boolean
      |): Int""" ==>
    """def f(
      |  a:   Int     = 0,
      |  bb:  String  = "",
      |  ccc: Boolean = false)(dd:   Int,
      |                        eee:  String,
      |                        ffff: Boolean): Int"""
  }

  // format: ON

  override  def debug = false

  def parse(parser: ScalaParser) = parser.compilationUnitOrScript()

  type Result = CompilationUnit

  def format(formatter: ScalaFormatter, result: Result) = formatter.format(result)(FormatterState(indentLevel = 0))

}
