# WorkManager & Notification PlayGround

A practice project on using  Android Jetpack library and one of the android architecture component - WorkManager.
WorkManager is used for performing usually deferrable task which may not be intended to be performed immediately.
WorkManager has an edge over Job Scheduler, Job Dispatcher, Alarm manager and Broadcast receiver, because it

- Handles API level compatibility back to API level 14
- Works with or without Google Play services
- Supports for both asynchronous one-off and periodic tasks
- Supports for constraints such as network conditions, storage space, and charging status
- Supports chaining of complex work requests, including running work in parallel
- Output from one work request used as input for the next
- LiveData support to easily display work request state in UI

## Areas covered:
- OneTimeWorkRequest
- PeriodicWorkRequest
- Constraints
- Checking  Work Status
- Cancelling requests.
- Handling notification in worker class

## Proceses followed:
- Create a seperate class for handing the task and extend the worker class
- Override the default doWork method and class constructor
- Carry out task to be undertaken in the doWork method
- Create work request either oneTimeWorkRequest or PeriodicWorkRequest
- Add contraints if necessary
- Enqueue your work using the WorkManager  instance class.
