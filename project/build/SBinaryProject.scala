import sbt._

class SBinaryProject(info: ProjectInfo) extends ParentProject(info) with NoPublish {
  // publishing
  override def managedStyle = ManagedStyle.Maven

  val publishTo = "Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/releases/"

  Credentials(Path.userHome / ".ivy2" / ".credentials", log)

  lazy val core = project("core", "SBinary", new CoreProject(_))

  lazy val treeExample = project("examples" / "bt", "Binary Tree Example", new ExampleProject(_), core)

  class ExampleProject(info: ProjectInfo) extends DefaultProject(info) with NoPublish  {
    override def scratch = true
  }

  class CoreProject(info: ProjectInfo) extends DefaultProject(info) with TemplateProject {
    def scalacheckVersion = if (buildScalaVersion == "2.8.1") "1.8" else "1.9-SNAPSHOT"
    override def repositories = super.repositories ++ Set(ScalaToolsSnapshots)
    val scalacheck = "org.scala-tools.testing" %% "scalacheck" % scalacheckVersion % "test"
    override def mainResources = super.mainResources +++ "LICENSE"
  }
}

trait NoPublish extends BasicManagedProject {
  override def deliverAction = publishAction
  override def publishAction = task { None }
}

