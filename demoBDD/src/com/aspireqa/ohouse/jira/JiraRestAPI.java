package com.aspireqa.ohouse.jira;

public enum JiraRestAPI {

IssueDetailById("/rest/api/latest/issue/"),
AttachmentContent("/rest/api/2/attachment/"),
AddingCommentToIssue("/comment?expand"),
CurrentTransitionStateOfIssue("/transitions"),
ProjectDetails("/rest/api/latest/project");
private final String label;

private JiraRestAPI(String label) {
this.label = label;
}

public String getLabel() {
return label;
}

@Override
public String toString() {
return label;
}


}
