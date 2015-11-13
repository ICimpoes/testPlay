package controllers.scheduler

import akka.actor.{ActorLogging, Actor}

class Receiver extends Actor with ActorLogging {
  override def receive: Receive = {
    case msg => log.info("Received message: {}", msg)
  }
}
