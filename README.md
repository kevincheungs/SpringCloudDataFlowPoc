# SpringCloudDataFlow (SCDF) POC

## Setting up the server on your local (Note: shutting down the server will reset all configs and tasks as its stored in memory unless you connect the server to a database)
1. Download the latest Jar file
https://dataflow.spring.io/docs/installation/local/manual/
2. Run the jar to start the server
java -jar spring-cloud-dataflow-server-2.9.2.jar 
3. Default dashboard
http://localhost:9393/dashboard/#/apps

## Adding a task

1. Each of the folders is a standalone Java App. You will need to build them individually to get the Jar file, which you can then import to SCDF.
2. From the dashboard, click on Applications in the left side menu
3. Click Add Application(s)
4. Select Register one or more applications, you should get a screen like below
![image](https://user-images.githubusercontent.com/84427780/208990841-f22151ad-1467-4c43-ac94-94f368a7b942.png)
5. Fill it in with the name of your task, type is task, and uri is the path to the corresponding Jar file.
6. Then click Import Application(s) and it should register with the system.


## Creating a workflow

1. Click Tasks under Tasks/Jobs from the left side menu
2. Click Create Task
3. You should see all the tasks you've imported on the left. Drag them in the flow order you want.
4. You can add the same task multiple times (they just need a different name). You can also specify failure/success paths as seen below
![image](https://user-images.githubusercontent.com/84427780/208992652-d2ac4eca-bd87-44e0-bbb4-7608b1b42a9d.png)
5. Click Create Task and give it a name.

## Launching the workflow

You can launch the workflow through the UI by clicking on it in your list of tasks and then clicking the Launch Task button.

OR

You can invoke it via API

### Example CURL
curl --location --request POST 'http://localhost:9393/tasks/executions' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'name=compositeTimestamp' \
--data-urlencode 'properties=app.localTimestampTask.format=yyyy' \
--data-urlencode 'arguments=--composed-task-arguments=UpdatesTwo'

### Example Postman
<img width="1023" alt="image" src="https://user-images.githubusercontent.com/84427780/208993568-a1ff1808-70c4-4a17-817f-4234bfd81321.png">

### Headers
- name - this is the same name you used to create the workflow in the above section
- properties - maps to TimestampTaskProperties.java. You can configure the timestamp to be yyyy, yyyy-MM, yyyy-MM-dd and so on.
- arguments - maps to the public void run(String... strings) of the application.
