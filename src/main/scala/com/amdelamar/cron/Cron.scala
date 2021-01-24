package com.amdelamar.cron

import org.quartz.CronExpression

import java.util.UUID

/**
 * A cron schedule. Here are some example expressions:
 * <ul>
 * <li> "*\/10 * * ? * *" Every ten seconds of every hour, every day.</li>
 * <li> "* * * * *" Every minute of every hour, every day.</li>
 * <li> "0 * * * *" Every hour, on the hour, every day.</li>
 * <li> "34 12 * * 1" On Monday at 12:34pm.</li>
 * <li> "0 9-17 * * *" Every hour from 9am to 5pm.</li>
 * <li> "0,30 9-17 * * 1-5" On the hour and half hour of every hour between 9am to 5pm on weekdays.</li>
 * </ul>
 *
 * @see http://www.quartz-scheduler.org/api/2.1.7/org/quartz/CronExpression.html
 *
 * @param id A uuid for this schedule.
 * @param expression The cron schedule expression.
 */
case class Cron(id: UUID, expression: CronExpression)

/**
 * An instance of a triggered time for a given cron schedule.
 * @param cron A cron schedule.
 */
case class CronTrigger(cron: Cron)
