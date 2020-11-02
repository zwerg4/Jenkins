def cattyorg = new JobsBuilder(this).gitHubOrganization({new CattyData()})

cattyorg.job("Catty_Screenshots") {
    htmlDescription(['Job is automatically started on a new commit or a new/updated pull request created on github.',
                     'This job runs the Pipeline defined in the Jenkinsfile.screenshots inside of the repository.',
                     'The Pipeline takes screenshots of the app and upload the artifacts'])

    jenkinsUsersPermissions(Permission.JobRead, Permission.JobBuild, Permission.JobCancel, Permission.JobWorkspace)
    anonymousUsersPermissions(Permission.JobRead) // allow anonymous users to see the results of PRs to fix their issues

    gitHubOrganization()
    jenkinsfilePath('Jenkinsfile.screenshots')
}

Views.basic(this, "Catty", "Catty/.+")
