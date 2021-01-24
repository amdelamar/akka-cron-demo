package com.amdelamar.cron

import akka.actor.Actor
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import com.typesafe.scalalogging.Logger

import scala.concurrent.ExecutionContext

class CronJobActor(implicit ec: ExecutionContext) extends Actor {
  private val logger = Logger(getClass.getName)

  val quartzScheduler = QuartzSchedulerExtension(context.system)

  override def receive: Receive = {
    case c: Cron =>
      logger.info(s"Received a new cron job: ${c}")

      try {
        quartzScheduler.createJobSchedule(
          name = c.id.toString,
          receiver = self,
          msg = CronTrigger(c),
          cronExpression = c.expression.toString)
        logger.info(s"Successfully scheduled new cron job: ${c}")
      } catch {
        case iae: IllegalArgumentException =>
          logger.error(s"Failed to schedule new cron job: ${c} " +
            s"due to: ${iae.getClass.getName} ${iae.getMessage}")
      }

    case CronTrigger(c) =>
      logger.info(s"Triggered! ${c}")
  }
}
