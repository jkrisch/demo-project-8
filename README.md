# Demo Projects - Build Automation & CI/CD with Jenkins

## increment version

use the respective command (see increment version stage) and extract it using the regex.
In order to push the change to the repo and avoid an endless loop (due to the webhook trigger from before) we use a plugin: Ingore Committer Strategy.<br>
In the job config we define the author email for which we want to ignore the commits.
