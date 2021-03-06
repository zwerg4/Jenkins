import groovy.json.*
import java.util.*
import java.util.HashMap;

HashMap<String, Boolean> multibranchMap = new HashMap<String, Boolean>();
HashMap<String, String> jobsMap = new HashMap<String, String>();

def nonDslMap = [
        'fullName': "type"
]

int countJOBDSL = 0
int countNonJobDSL = 0
int countJOBDSLInLab = 0
int countNonJobDSLInLab = 0


jobs = Jenkins.instance.getAllItems()
jobs.each { j ->
  if (j instanceof com.cloudbees.hudson.plugins.folder.Folder) { return }

  String strDescr = ""
  String strName  = ""
  strDescr = j.getDescription()
  strName = j.fullName

  boolean isJOBDSL = false;
  boolean isInLab = false;
  boolean isMultiBranch = false;

  if(strDescr != null)
  {
    isJOBDSL = strDescr.indexOf("generated by JobDSL") !=-1? true : false;
    isMultiBranch = strDescr.indexOf("multibranch pipeline") != -1 ? true : false
  }
  if(strName != null)
  {
    isInLab = strName.indexOf("lab/") !=-1? true: false;
  }

  if(isMultiBranch)
  {
    multibranchMap.put(j.fullName, isMultiBranch);
  }
  if(!isJOBDSL && !isInLab && !isMultiBranch)
  {
    jobsMap.put(j.fullName, j.getClass());
  }
}


for (String i : jobsMap.keySet())
{
  String[] arrOfStr = i.split("/", 2);

  if(!multibranchMap.containsKey(arrOfStr[0]))
  {
    nonDslMap.put(i,"nonDSL")
    countNonJobDSL += 1;
  }
  else{ countJOBDSL += 1;}
}


def json = new groovy.json.JsonBuilder()
json nonDslJobs: nonDslMap

println groovy.json.JsonOutput.prettyPrint(json.toString())


