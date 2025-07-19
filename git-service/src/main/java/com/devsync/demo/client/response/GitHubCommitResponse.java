package com.devsync.demo.client.response;

public class GitHubCommitResponse {
    private String sha;
    private InnerCommit commit;

    public static class InnerCommit {
        private String message;
        private Committer committer;

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public Committer getCommitter() { return committer; }
        public void setCommitter(Committer committer) { this.committer = committer; }
    }

    public static class Committer {
        private String date;

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
    }

    public String getSha() { return sha; }
    public void setSha(String sha) { this.sha = sha; }

    public InnerCommit getCommit() { return commit; }
    public void setCommit(InnerCommit commit) { this.commit = commit; }
}
