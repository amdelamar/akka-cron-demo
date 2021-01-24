# Akka Cron Demo

A simple Cron Scheduler service using Akka Actors. Underneath its using Quartz Scheduler and sends messages to actors at
the scheduled intervals for processing.

Requires [JDK 11](https://adoptopenjdk.net/) and [sbt 1.3+](https://www.scala-sbt.org/).

## Start

1. Start the Akka Http server:

```bash
$ cd akka-cron-demo
$ sbt run
```

## Send some requests

2. Open a new terminal and send a HTTP call to the API to create a cron job.

```bash
$ curl -X POST "127.0.0.1:8080/jobs" -H "Content-Type: text/plain" --data "*/10 * * ? * *"
```

## View the logs

3. Monitor the logs to see jobs are scheduled and trigger according to their schedule given.

```bash
[info] running com.amdelamar.App 
20:34:38.155 [INFO ] Initialized Scheduler Signaller of type: class org.quartz.core.SchedulerSignalerImpl
20:34:38.157 [INFO ] Quartz Scheduler v.2.3.2 created.
20:34:38.159 [INFO ] RAMJobStore initialized.
20:34:38.160 [INFO ] Scheduler meta-data: Quartz Scheduler (v2.3.2) 'QuartzScheduler~my-app' with instanceId 'my-app'
  Scheduler class: 'org.quartz.core.QuartzScheduler' - running locally.
  NOT STARTED.
  Currently in standby mode.
  Number of jobs executed: 0
  Using thread pool 'org.quartz.simpl.SimpleThreadPool' - with 8 threads.
  Using job-store 'org.quartz.simpl.RAMJobStore' - which does not support persistence. and is not clustered.

20:34:38.160 [INFO ] Quartz scheduler 'QuartzScheduler~my-app'
20:34:38.160 [INFO ] Quartz scheduler version: 2.3.2
20:34:38.161 [INFO ] Scheduler QuartzScheduler~my-app_$_my-app started.
[INFO] [01/23/2021 20:34:38.166] [my-app-akka.actor.default-dispatcher-5] [[QuartzScheduler~my-app]] Initialized calendars: 
20:34:38.664 [INFO ] App is running at http://localhost:8080/
20:34:41.463 [INFO ] Received cron job: Cron(d9ad67ee-03aa-4321-9d51-4952dfd14ad0,*/10 * * ? * *)
20:34:41.463 [INFO ] Received a new cron job: Cron(d9ad67ee-03aa-4321-9d51-4952dfd14ad0,*/10 * * ? * *)
[INFO] [01/23/2021 20:34:41.465] [my-app-akka.actor.default-dispatcher-6] [[QuartzScheduler~my-app]] Setting up scheduled job 'd9ad67ee-03aa-4321-9d51-4952dfd14ad0', with 'com.typesafe.akka.extension.quartz.QuartzCronSchedule@51253a19'
20:34:41.476 [INFO ] Successfully scheduled new cron job: Cron(d9ad67ee-03aa-4321-9d51-4952dfd14ad0,*/10 * * ? * *)
20:34:50.016 [INFO ] Triggered! Cron(d9ad67ee-03aa-4321-9d51-4952dfd14ad0,*/10 * * ? * *)
20:35:00.004 [INFO ] Triggered! Cron(d9ad67ee-03aa-4321-9d51-4952dfd14ad0,*/10 * * ? * *)
20:35:10.002 [INFO ] Triggered! Cron(d9ad67ee-03aa-4321-9d51-4952dfd14ad0,*/10 * * ? * *)
20:35:20.003 [INFO ] Triggered! Cron(d9ad67ee-03aa-4321-9d51-4952dfd14ad0,*/10 * * ? * *)
20:35:30.002 [INFO ] Triggered! Cron(d9ad67ee-03aa-4321-9d51-4952dfd14ad0,*/10 * * ? * *)
```

Our job had `*/10 * * ? * *` so it triggers every ten seconds. 
You can try out with other cron expressions, using hours, days, weeks, so you can imagine real work being done in production.

Combine this code with [Akka Persistence](https://github.com/amdelamar/akka-persistence-demo) to save the cron jobs in a database
and easily continue cron execution after a sudden reboot, JVM crash or power outage.