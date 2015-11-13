import akka.actor.Props
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import controllers.scheduler.Receiver
import play.api.libs.concurrent.Akka
import play.api.{Application, GlobalSettings}

object Global extends GlobalSettings {

  override def onStart(application: Application) = {
    implicit val app = application
    val receiver = Akka.system.actorOf(Props[Receiver], "Receiver")
    val scheduler = QuartzSchedulerExtension(Akka.system)
    scheduler.schedule("Every30Seconds", receiver, "Hi there!")
  }
}
