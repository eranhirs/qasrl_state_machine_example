import mill._, mill.scalalib._

val kindProjectorVersion = "0.11.3"

val jjmVersion = "0.2.0"
val spacroVersion = "0.4.0"

val slf4jApiVersion = "1.7.30"

val scalatestVersion = "3.2.2"
val scalacheckVersion = "1.14.1"
val disciplineVersion = "1.0.0"

object `qasrl-state-machine-example` extends ScalaModule {
  def scalaVersion = "2.12.13"
  def artifactName = "qasrl-state-machine-example"
  def millSourcePath = build.millSourcePath / "example"
  def ivyDeps = Agg(
    ivy"org.julianmichael::qasrl-crowd::0.2.0",
    ivy"org.julianmichael::nlpdata::0.2.0",
    ivy"com.lihaoyi::pprint:0.5.2",
    ivy"org.julianmichael::jjm-core::$jjmVersion",
    ivy"org.julianmichael::jjm-io::$jjmVersion",
    ivy"org.typelevel:::kind-projector:$kindProjectorVersion",
    ivy"org.julianmichael::spacro::$spacroVersion",
    ivy"org.slf4j:slf4j-api:$slf4jApiVersion",
    ivy"org.scalatest::scalatest:$scalatestVersion",
    ivy"org.scalacheck::scalacheck:$scalacheckVersion",
    ivy"org.typelevel::discipline-core:$disciplineVersion",
    ivy"com.github.tototoshi::scala-csv:1.3.8"
  )
}