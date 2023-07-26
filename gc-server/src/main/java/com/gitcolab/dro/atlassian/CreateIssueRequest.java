package com.gitcolab.dro.atlassian;

import com.gitcolab.dro.project.GithubIssueEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateIssueRequest {
        private Fields fields;

        public CreateIssueRequest(GithubIssueEvent githubIssueEvent,String projectId,String reporterId) {
                String title=githubIssueEvent.getIssue().getTitle();
                String issueTypeId=title.contains("Bug") || title.contains("bug") ? "10005":"10004";
                StringBuilder body=new StringBuilder();
                body.append("Issue Description:\n");

                String descriptionOfIssue=githubIssueEvent.getIssue().getBody() != null && !githubIssueEvent.getIssue().getBody().equals("null") ? githubIssueEvent.getIssue().getBody() :githubIssueEvent.getIssue().getTitle();
                body.append(descriptionOfIssue);

                body.append("\n\nReference:\n");

                body.append(githubIssueEvent.getIssue().getUrl());

                body.append("\n\nGithub Reporter:\n");
                body.append(githubIssueEvent.getIssue().getUser().getLogin());

                ContentItem[] contentItems = {
                        new ContentItem(body.toString() , "text")
                };
                Content[] contents={
                        new Content(contentItems,"paragraph")
                };

                Description description = new Description(contents, "doc", 1);
                IssueType issueType = new IssueType(issueTypeId);
                Reporter reporter = new Reporter(reporterId);
                com.gitcolab.dro.atlassian.Project project = new com.gitcolab.dro.atlassian.Project(projectId);

                StringBuilder summary = new StringBuilder();
                summary.append(title);
                summary.append(" by ");
                summary.append(githubIssueEvent.getIssue().getUser().getLogin());

                this.fields = new Fields(description, issueType, project, reporter, summary.toString());
        }
}
