package com.amdelamar

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.amdelamar.cron.{Cron, CronJobActor}
import com.typesafe.scalalogging.Logger
import org.quartz.CronExpression

import java.text.ParseException
import java.util.UUID
import scala.concurrent.ExecutionContext

class JobHandler(implicit system: ActorSystem, ec: ExecutionContext) {
  private val logger = Logger(getClass.getName)
  private val cronActorRef = system.actorOf(Props(classOf[CronJobActor], ec))

  val routes: Route = {
    pathEndOrSingleSlash {
      complete("App is up.\n")
    } ~
    post {
      path("jobs") {
        entity(as[String]) { body =>
          try {
            val expression = new CronExpression(body)
            val job = Cron(UUID.randomUUID, expression)
            logger.info(s"Received cron job: $job")

            // Send to Actor for later processing
            cronActorRef ! job

            complete(StatusCodes.OK, s"Success: $job")
          } catch {
            case iae: IllegalArgumentException =>
              logger.error(s"Failed to create new cron schedule from input. ${iae.getMessage}")
              complete(StatusCodes.BadRequest, s"Failed: ${iae.getMessage}")

            case pe: ParseException =>
              logger.error(s"Failed to create new cron schedule from input. ${pe.getMessage}")
              complete(StatusCodes.BadRequest, s"Failed: ${pe.getMessage}")
          }

        }
      }
    }
  }

}
