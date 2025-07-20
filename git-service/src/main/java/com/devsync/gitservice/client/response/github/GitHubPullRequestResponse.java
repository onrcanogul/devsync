package com.devsync.gitservice.client.response.github;

public class GitHubPullRequestResponse {

    private long id;
    private String title;
    private User user;
    private Head head;
    private Base base;

    public static class User {
        private String login;
        public String getLogin() { return login; }
        public void setLogin(String login) { this.login = login; }
    }

    public static class Head {
        private String ref;
        public String getRef() { return ref; }
        public void setRef(String ref) { this.ref = ref; }
    }

    public static class Base {
        private Repo repo;
        public Repo getRepo() { return repo; }
        public void setRepo(Repo repo) { this.repo = repo; }
    }

    public static class Repo {
        private String name;
        private Owner owner;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public Owner getOwner() { return owner; }
        public void setOwner(Owner owner) { this.owner = owner; }
    }

    public static class Owner {
        private String login;

        public String getLogin() { return login; }
        public void setLogin(String login) { this.login = login; }
    }

    // --- Getters / Setters ---

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Head getHead() { return head; }
    public void setHead(Head head) { this.head = head; }

    public Base getBase() { return base; }
    public void setBase(Base base) { this.base = base; }
}
