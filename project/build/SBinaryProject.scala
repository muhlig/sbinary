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
    val scalacheck = "org.scala-tools.testing" %% "scalacheck" % "1.9" % "test"
    override def mainResources = super.mainResources +++ "LICENSE"
  }
}

trait NoPublish extends BasicManagedProject {
  override def deliverAction = publishAction
  override def publishAction = task { None }
}

